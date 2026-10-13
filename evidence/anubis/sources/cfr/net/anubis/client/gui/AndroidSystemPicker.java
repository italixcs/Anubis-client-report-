/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  imgui.ImGui
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package net.anubis.client.gui;

import imgui.AnubisAndroidNative;
import imgui.ImGui;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public final class AndroidSystemPicker {
    static final long MAX_IMAGE_BYTES = 0x4000000L;
    static final int MAX_STATUS_BYTES = 4096;
    private static final String RESOURCE = "/net/anubis/compat/android-picker/";
    private static final String POPUP = "Image selection##AnubisSystemPicker";
    private static final ExecutorService IO = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "Anubis Android image selection");
        t.setDaemon(true);
        return t;
    });
    private static Request current;
    private static Result completed;
    private static Path completedDirectory;
    private static Path consumedDirectory;
    private static Path runtimeDirectory;
    private static String error;
    private static boolean showError;
    private static boolean dismissError;

    private AndroidSystemPicker() {
    }

    private static native String nativeOpen(String var0, String var1);

    private static native void nativeCancel(String var0);

    public static synchronized void open() {
        Request request;
        if (!AnubisAndroidNative.isAndroidRuntime() || current != null || completed != null) {
            return;
        }
        AndroidSystemPicker.cleanConsumed();
        error = null;
        dismissError = true;
        current = request = new Request();
        IO.execute(() -> AndroidSystemPicker.run(request));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void run(Request request) {
        Result result;
        Path directory = null;
        boolean opened = false;
        try {
            Path runtime = AndroidSystemPicker.prepareRuntime();
            directory = AndroidSystemPicker.privateSubdirectory(runtime, "selection-");
            if (request.cancelled) {
                return;
            }
            String failure = AndroidSystemPicker.nativeOpen(runtime.resolve("classes.dex").toString(), directory.toString());
            if (failure != null) {
                throw new IOException(failure);
            }
            opened = true;
            long deadline = System.nanoTime() + 600000000000L;
            while (true) {
                if (request.cancelled) {
                    result = new Result("cancel", null, null);
                    break;
                }
                result = AndroidSystemPicker.readResult(directory);
                if (!"pending".equals(result.status)) {
                    break;
                }
                if (System.nanoTime() >= deadline) {
                    throw new IOException("Image selection timed out. Please try again.");
                }
                Thread.sleep(150L);
            }
        }
        catch (IOException | LinkageError | RuntimeException exception) {
            result = new Result("error", null, AndroidSystemPicker.safeMessage(exception.getMessage()));
        }
        catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            result = new Result("cancel", null, null);
        }
        finally {
            if (opened && directory != null) {
                try {
                    AndroidSystemPicker.nativeCancel(directory.toString());
                }
                catch (LinkageError | RuntimeException exception) {}
            }
            if (request.cancelled) {
                AndroidSystemPicker.cleanup(directory);
            }
        }
        Class<AndroidSystemPicker> clazz = AndroidSystemPicker.class;
        synchronized (AndroidSystemPicker.class) {
            if (current != request || request.cancelled) {
                AndroidSystemPicker.cleanup(directory);
                // ** MonitorExit[var4_3] (shouldn't be in output)
                return;
            }
            current = null;
            if ("ok".equals(result.status)) {
                completed = result;
                completedDirectory = directory;
            } else {
                AndroidSystemPicker.cleanup(directory);
                if ("error".equals(result.status)) {
                    error = result.message;
                    showError = true;
                    System.err.println("[Anubis image picker] " + error);
                }
            }
            // ** MonitorExit[var4_3] (shouldn't be in output)
            return;
        }
    }

    public static synchronized Path takeSelection() {
        AndroidSystemPicker.cleanConsumed();
        if (completed == null) {
            return null;
        }
        Path path = AndroidSystemPicker.completed.path;
        consumedDirectory = completedDirectory;
        completed = null;
        completedDirectory = null;
        return path;
    }

    private static void cleanConsumed() {
        Path old = consumedDirectory;
        consumedDirectory = null;
        if (old != null) {
            IO.execute(() -> AndroidSystemPicker.cleanup(old));
        }
    }

    public static synchronized boolean blocksEditor() {
        return current != null || error != null;
    }

    public static synchronized boolean handleEscape() {
        if (current == null && error == null) {
            return false;
        }
        AndroidSystemPicker.cancel();
        return true;
    }

    public static synchronized void cancel() {
        if (current != null) {
            AndroidSystemPicker.current.cancelled = true;
        }
        current = null;
        completed = null;
        Path old = completedDirectory;
        completedDirectory = null;
        if (old != null) {
            IO.execute(() -> AndroidSystemPicker.cleanup(old));
        }
        AndroidSystemPicker.cleanConsumed();
        error = null;
        showError = false;
        dismissError = true;
    }

    public static synchronized void render() {
        if (showError) {
            ImGui.openPopup((String)POPUP);
            showError = false;
            dismissError = false;
        }
        if (!ImGui.isPopupOpen((String)POPUP)) {
            return;
        }
        float width = Math.max(200.0f, ImGui.getIO().getDisplaySizeX() - 32.0f);
        ImGui.setNextWindowSize((float)Math.min(560.0f, width), (float)0.0f, (int)1);
        if (ImGui.beginPopupModal((String)POPUP, (int)320)) {
            try {
                if (dismissError || error == null) {
                    ImGui.closeCurrentPopup();
                    dismissError = false;
                } else {
                    ImGui.textWrapped((String)error);
                    if (ImGui.button((String)"OK")) {
                        error = null;
                        ImGui.closeCurrentPopup();
                    }
                }
            }
            finally {
                ImGui.endPopup();
            }
        }
    }

    static Result readResult(Path directory) throws IOException {
        if (!Files.isDirectory(directory, LinkOption.NOFOLLOW_LINKS)) {
            throw new IOException("Invalid selection directory.");
        }
        Path status = directory.resolve("result.txt");
        if (!Files.exists(status, LinkOption.NOFOLLOW_LINKS)) {
            return new Result("pending", null, null);
        }
        if (!Files.isRegularFile(status, LinkOption.NOFOLLOW_LINKS)) {
            throw new IOException("Invalid image selection result.");
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (InputStream stream = Files.newInputStream(status, LinkOption.NOFOLLOW_LINKS);){
            int count;
            byte[] buffer = new byte[512];
            while ((count = stream.read(buffer)) != -1) {
                if (bytes.size() + count > 4096) {
                    throw new IOException("Image selection result is too large.");
                }
                bytes.write(buffer, 0, count);
            }
        }
        String[] lines = new String(bytes.toByteArray(), StandardCharsets.UTF_8).split("\\r?\\n", -1);
        String state = lines.length > 0 ? lines[0] : "";
        String value = lines.length > 1 ? lines[1] : "";
        for (int i = 2; i < lines.length; ++i) {
            if (lines[i].isEmpty()) continue;
            throw new IOException("Invalid image selection result.");
        }
        if ("cancel".equals(state)) {
            return new Result(state, null, null);
        }
        if ("error".equals(state)) {
            return new Result(state, null, AndroidSystemPicker.safeMessage(value));
        }
        if (!"ok".equals(state) || value.isEmpty() || value.length() > 240 || value.indexOf(47) >= 0 || value.indexOf(92) >= 0 || value.indexOf(58) >= 0 || value.equals(".") || value.equals("..") || AndroidSystemPicker.hasControl(value)) {
            throw new IOException("Invalid image selection result.");
        }
        String lower = value.toLowerCase(Locale.ROOT);
        if (!(lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".gif"))) {
            throw new IOException("Choose a PNG, JPG or GIF image.");
        }
        Path selected = directory.resolve(value);
        if (!Files.isRegularFile(selected, LinkOption.NOFOLLOW_LINKS) || !Files.isReadable(selected)) {
            throw new IOException("The selected image could not be read.");
        }
        long size = Files.size(selected);
        if (size <= 0L || size > 0x4000000L) {
            throw new IOException("Choose an image smaller than 64 MiB.");
        }
        return new Result("ok", selected, null);
    }

    private static boolean hasControl(String value) {
        for (int i = 0; i < value.length(); ++i) {
            if (!Character.isISOControl(value.charAt(i))) continue;
            return true;
        }
        return false;
    }

    private static String safeMessage(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "Android's image picker could not be opened on this launcher.";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length() && result.length() < 350; ++i) {
            char c = text.charAt(i);
            result.append(Character.isISOControl(c) ? (char)' ' : (char)c);
        }
        return result.toString();
    }

    private static Path prepareRuntime() throws IOException {
        if (runtimeDirectory != null) {
            return runtimeDirectory;
        }
        String arch = System.getProperty("os.arch", "").toLowerCase(Locale.ROOT);
        if (!(arch.equals("aarch64") || arch.equals("arm64") || arch.equals("arm64-v8a"))) {
            throw new IOException("This Android build requires an ARM64 Java runtime.");
        }
        ArrayList<String> candidates = new ArrayList<String>();
        candidates.add(System.getProperty("imgui.library.path"));
        candidates.add(System.getenv("MOD_ANDROID_RUNTIME"));
        candidates.add(System.getenv("TMPDIR"));
        candidates.add(System.getProperty("java.io.tmpdir"));
        IOException failure = new IOException("The launcher has no writable private cache for Android's image picker.");
        for (String candidate : candidates) {
            if (candidate == null || candidate.isEmpty()) continue;
            Path directory = null;
            try {
                Path parent = Paths.get(candidate, new String[0]).toRealPath(new LinkOption[0]);
                String path = parent.toString().replace('\\', '/');
                if (!path.startsWith("/data/") && !path.startsWith("/mnt/expand/") || path.startsWith("/data/media/")) continue;
                directory = AndroidSystemPicker.privateSubdirectory(parent, "anubis-system-picker-");
                AndroidSystemPicker.extract("classes.dex", directory.resolve("classes.dex"), true);
                AndroidSystemPicker.extract("arm64-v8a/libanubis_picker.so", directory.resolve("libanubis_picker.so"), false);
                System.load(directory.resolve("libanubis_picker.so").toString());
                runtimeDirectory = directory;
                return directory;
            }
            catch (IOException | SecurityException exception) {
                AndroidSystemPicker.cleanup(directory);
                failure.addSuppressed(exception);
            }
        }
        throw failure;
    }

    private static Path privateSubdirectory(Path parent, String prefix) throws IOException {
        return Files.createTempDirectory(parent, prefix, PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------")));
    }

    private static void extract(String resource, Path output, boolean dex) throws IOException {
        try (InputStream input = AndroidSystemPicker.class.getResourceAsStream(RESOURCE + resource);){
            if (input == null) {
                throw new IOException("Android image picker resource is missing.");
            }
            try (OutputStream stream = Files.newOutputStream(output, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);){
                int count;
                Files.setPosixFilePermissions(output, PosixFilePermissions.fromString(dex ? "r--------" : "r-x------"));
                byte[] buffer = new byte[8192];
                while ((count = input.read(buffer)) != -1) {
                    stream.write(buffer, 0, count);
                }
            }
        }
        output.toFile().deleteOnExit();
    }

    private static void cleanup(Path directory) {
        if (directory == null || !Files.isDirectory(directory, LinkOption.NOFOLLOW_LINKS)) {
            return;
        }
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(directory);){
            for (Path entry : entries) {
                if (Files.isDirectory(entry, LinkOption.NOFOLLOW_LINKS)) continue;
                Files.deleteIfExists(entry);
            }
        }
        catch (IOException | SecurityException exception) {
            // empty catch block
        }
        try {
            Files.deleteIfExists(directory);
        }
        catch (IOException | SecurityException exception) {
            // empty catch block
        }
    }

    @Environment(value=EnvType.CLIENT)
    private static final class Request {
        volatile boolean cancelled;

        private Request() {
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class Result {
        final String status;
        final Path path;
        final String message;

        Result(String status, Path path, String message) {
            this.status = status;
            this.path = path;
            this.message = message;
        }
    }
}

