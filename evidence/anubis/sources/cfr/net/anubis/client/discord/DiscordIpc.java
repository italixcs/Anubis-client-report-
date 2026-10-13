/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonParser
 *  com.sun.jna.Platform
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package net.anubis.client.discord;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.sun.jna.Platform;
import java.io.Closeable;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.SocketTimeoutException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public final class DiscordIpc
implements Closeable {
    private static final int OP_HANDSHAKE = 0;
    private static final int OP_FRAME = 1;
    private static final int OP_CLOSE = 2;
    private static final int OP_PING = 3;
    private static final int OP_PONG = 4;
    private static final int RPC_VERSION = 1;
    private static final int SOCKET_SLOTS = 10;
    private static final String[] UNIX_TEMP_VARIABLES = new String[]{"XDG_RUNTIME_DIR", "TMPDIR", "TMP", "TEMP"};
    private static final boolean WINDOWS = Platform.isWindows();
    private static final long REPLY_TIMEOUT_NANOS = TimeUnit.SECONDS.toNanos(5L);
    private static final long READ_POLL_NANOS = TimeUnit.MILLISECONDS.toNanos(20L);
    private static final int MAX_FRAME_BYTES = 0x100000;
    private static final long PID = ProcessHandle.current().pid();
    private static final Gson GSON = new GsonBuilder().serializeNulls().create();
    private final Pipe pipe;
    private final ByteBuffer header = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);

    private DiscordIpc(Pipe pipe) {
        this.pipe = pipe;
    }

    public static DiscordIpc connect(String applicationId) throws IOException {
        Pipe pipe;
        Pipe pipe2 = pipe = WINDOWS ? DiscordIpc.openWindowsPipe() : DiscordIpc.openUnixSocket();
        if (pipe == null) {
            return null;
        }
        DiscordIpc ipc = new DiscordIpc(pipe);
        try {
            ipc.handshake(applicationId);
            return ipc;
        }
        catch (IOException | RuntimeException failure) {
            ipc.close();
            throw failure;
        }
    }

    public void setActivity(JsonObject activity) throws IOException, RefusedException {
        JsonObject reply;
        String nonce = UUID.randomUUID().toString();
        JsonObject args = new JsonObject();
        args.addProperty("pid", (Number)PID);
        args.add("activity", (JsonElement)(activity != null ? activity : JsonNull.INSTANCE));
        JsonObject command = new JsonObject();
        command.addProperty("cmd", "SET_ACTIVITY");
        command.add("args", (JsonElement)args);
        command.addProperty("nonce", nonce);
        this.send(1, command);
        long deadline = System.nanoTime() + REPLY_TIMEOUT_NANOS;
        while ((reply = this.answer(this.readFrame(deadline, true))) == null || !nonce.equals(DiscordIpc.string(reply, "nonce"))) {
        }
        if ("ERROR".equals(DiscordIpc.string(reply, "evt"))) {
            throw new RefusedException(DiscordIpc.errorMessage(reply));
        }
    }

    public void poll() throws IOException {
        Frame frame;
        while ((frame = this.readFrame(System.nanoTime() + REPLY_TIMEOUT_NANOS, false)) != null) {
            this.answer(frame);
        }
    }

    @Override
    public void close() {
        try {
            this.pipe.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private void handshake(String applicationId) throws IOException {
        JsonObject reply;
        JsonObject hello = new JsonObject();
        hello.addProperty("v", (Number)1);
        hello.addProperty("client_id", applicationId);
        this.send(0, hello);
        long deadline = System.nanoTime() + REPLY_TIMEOUT_NANOS;
        while (true) {
            if ((reply = this.answer(this.readFrame(deadline, true))) == null || !"DISPATCH".equals(DiscordIpc.string(reply, "cmd"))) {
                continue;
            }
            String event = DiscordIpc.string(reply, "evt");
            if ("READY".equals(event)) {
                return;
            }
            if ("ERROR".equals(event)) break;
        }
        throw new IOException("Discord refused the handshake: " + DiscordIpc.errorMessage(reply));
    }

    private JsonObject answer(Frame frame) throws IOException {
        return switch (frame.opcode()) {
            case 1 -> frame.json();
            case 3 -> {
                this.send(4, frame.body());
                yield null;
            }
            case 2 -> throw new IOException("Discord closed the connection: " + DiscordIpc.errorMessage(frame.json()));
            default -> null;
        };
    }

    private Frame readFrame(long deadline, boolean wait) throws IOException {
        this.header.clear();
        if (!wait && this.pipe.readWaiting(this.header) == 0) {
            return null;
        }
        this.readFully(this.header, deadline);
        this.header.flip();
        int opcode = this.header.getInt();
        int length = this.header.getInt();
        if (length < 0 || length > 0x100000) {
            throw new IOException("Discord sent a frame of " + length + " bytes");
        }
        ByteBuffer body = ByteBuffer.allocate(length);
        this.readFully(body, deadline);
        return new Frame(opcode, body.array());
    }

    private void readFully(ByteBuffer buffer, long deadline) throws IOException {
        while (buffer.hasRemaining()) {
            if (this.pipe.readWaiting(buffer) > 0) continue;
            if (System.nanoTime() - deadline >= 0L) {
                throw new SocketTimeoutException("Discord did not answer in time");
            }
            LockSupport.parkNanos(READ_POLL_NANOS);
        }
    }

    private void send(int opcode, JsonObject body) throws IOException {
        this.send(opcode, GSON.toJson((JsonElement)body).getBytes(StandardCharsets.UTF_8));
    }

    private void send(int opcode, byte[] body) throws IOException {
        ByteBuffer frame = ByteBuffer.allocate(8 + body.length).order(ByteOrder.LITTLE_ENDIAN);
        frame.putInt(opcode).putInt(body.length).put(body).flip();
        this.pipe.write(frame, System.nanoTime() + REPLY_TIMEOUT_NANOS);
    }

    private static String errorMessage(JsonObject body) {
        JsonElement data = body.get("data");
        String message = DiscordIpc.string(data != null && data.isJsonObject() ? data.getAsJsonObject() : body, "message");
        return message != null ? message : body.toString();
    }

    private static String string(JsonObject object, String key) {
        JsonElement value = object.get(key);
        return value != null && value.isJsonPrimitive() ? value.getAsString() : null;
    }

    private static Pipe openWindowsPipe() {
        for (int slot = 0; slot < 10; ++slot) {
            try {
                return new WindowsPipe(new RandomAccessFile("\\\\.\\pipe\\discord-ipc-" + slot, "rw"));
            }
            catch (FileNotFoundException fileNotFoundException) {
                continue;
            }
        }
        return null;
    }

    private static Pipe openUnixSocket() {
        for (Path folder : DiscordIpc.unixFolders()) {
            for (int slot = 0; slot < 10; ++slot) {
                Path socket = folder.resolve("discord-ipc-" + slot);
                if (!Files.exists(socket, new LinkOption[0])) continue;
                try {
                    SocketChannel channel = SocketChannel.open(StandardProtocolFamily.UNIX);
                    try {
                        channel.connect(UnixDomainSocketAddress.of(socket));
                        channel.configureBlocking(false);
                        return new UnixPipe(channel);
                    }
                    catch (IOException failure) {
                        channel.close();
                        throw failure;
                    }
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
        return null;
    }

    private static Set<Path> unixFolders() {
        LinkedHashSet<Path> roots = new LinkedHashSet<Path>();
        for (String variable : UNIX_TEMP_VARIABLES) {
            String value = System.getenv(variable);
            if (value == null || value.isBlank()) continue;
            try {
                roots.add(Path.of(value, new String[0]));
            }
            catch (InvalidPathException invalidPathException) {
                // empty catch block
            }
        }
        roots.add(Path.of("/tmp", new String[0]));
        LinkedHashSet<Path> folders = new LinkedHashSet<Path>();
        for (Path root : roots) {
            folders.add(root);
            DiscordIpc.addSubfolders(folders, root.resolve("app"), "*");
            DiscordIpc.addSubfolders(folders, root, "snap.*");
        }
        return folders;
    }

    private static void addSubfolders(Set<Path> folders, Path parent, String glob) {
        if (!Files.isDirectory(parent, new LinkOption[0])) {
            return;
        }
        try (DirectoryStream<Path> children = Files.newDirectoryStream(parent, glob);){
            for (Path child : children) {
                if (!Files.isDirectory(child, new LinkOption[0])) continue;
                folders.add(child);
            }
        }
        catch (IOException | SecurityException exception) {
            // empty catch block
        }
    }

    @Environment(value=EnvType.CLIENT)
    private static interface Pipe
    extends Closeable {
        public int readWaiting(ByteBuffer var1) throws IOException;

        public void write(ByteBuffer var1, long var2) throws IOException;
    }

    @Environment(value=EnvType.CLIENT)
    private record Frame(int opcode, byte[] body) {
        JsonObject json() throws IOException {
            try {
                JsonElement parsed = JsonParser.parseString((String)new String(this.body, StandardCharsets.UTF_8));
                if (parsed.isJsonObject()) {
                    return parsed.getAsJsonObject();
                }
            }
            catch (JsonParseException jsonParseException) {
                // empty catch block
            }
            throw new IOException("Discord sent a frame that is not a JSON object");
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static final class RefusedException
    extends Exception {
        RefusedException(String message) {
            super(message);
        }
    }

    @Environment(value=EnvType.CLIENT)
    private record WindowsPipe(RandomAccessFile file) implements Pipe
    {
        @Override
        public int readWaiting(ByteBuffer into) throws IOException {
            long waiting = this.file.length();
            if (waiting <= 0L) {
                return 0;
            }
            int read = this.file.read(into.array(), into.arrayOffset() + into.position(), (int)Math.min(waiting, (long)into.remaining()));
            if (read < 0) {
                throw new EOFException("Discord hung up");
            }
            into.position(into.position() + read);
            return read;
        }

        @Override
        public void write(ByteBuffer from, long deadline) throws IOException {
            this.file.write(from.array(), from.arrayOffset() + from.position(), from.remaining());
            from.position(from.limit());
        }

        @Override
        public void close() throws IOException {
            this.file.close();
        }
    }

    @Environment(value=EnvType.CLIENT)
    private record UnixPipe(SocketChannel channel) implements Pipe
    {
        @Override
        public int readWaiting(ByteBuffer into) throws IOException {
            int read = this.channel.read(into);
            if (read < 0) {
                throw new EOFException("Discord hung up");
            }
            return read;
        }

        @Override
        public void write(ByteBuffer from, long deadline) throws IOException {
            while (from.hasRemaining()) {
                if (this.channel.write(from) > 0) continue;
                if (System.nanoTime() - deadline >= 0L) {
                    throw new SocketTimeoutException("Discord stopped reading");
                }
                LockSupport.parkNanos(READ_POLL_NANOS);
            }
        }

        @Override
        public void close() throws IOException {
            this.channel.close();
        }
    }
}

