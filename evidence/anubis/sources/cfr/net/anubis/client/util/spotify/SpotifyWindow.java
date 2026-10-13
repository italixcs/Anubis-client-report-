/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Pointer
 *  com.sun.jna.platform.win32.Kernel32
 *  com.sun.jna.platform.win32.User32
 *  com.sun.jna.platform.win32.WinDef$DWORD
 *  com.sun.jna.platform.win32.WinDef$HWND
 *  com.sun.jna.platform.win32.WinDef$WORD
 *  com.sun.jna.platform.win32.WinNT$HANDLE
 *  com.sun.jna.platform.win32.WinUser$INPUT
 *  com.sun.jna.platform.win32.WinUser$WNDENUMPROC
 *  com.sun.jna.ptr.IntByReference
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package net.anubis.client.util.spotify;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.IntByReference;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public final class SpotifyWindow {
    private static final String EXECUTABLE = "spotify.exe";
    private static final String MAIN_CLASS = "Chrome_WidgetWin_0";
    private static final String CLASS_FAMILY = "Chrome_WidgetWin";
    private static final long PID_CACHE_MS = 30000L;
    private static final int PID_CACHE_MAX = 512;
    private static final long RECHECK_MS = 10000L;
    private static final String TRACK_SEPARATOR = " - ";
    private static final int KEYEVENTF_EXTENDEDKEY = 1;
    private static final int KEYEVENTF_KEYUP = 2;
    private final char[] textBuffer = new char[512];
    private final char[] classBuffer = new char[64];
    private final char[] pathBuffer = new char[1024];
    private final IntByReference pidOut = new IntByReference();
    private final IntByReference sizeInOut = new IntByReference();
    private final Map<Integer, Boolean> spotifyPids = new HashMap<Integer, Boolean>();
    private long pidCacheSince;
    private WinDef.HWND window;
    private long foundAt;
    private WinDef.HWND best;
    private int bestScore;
    private final WinUser.WNDENUMPROC finder = this::consider;

    public String readTitle() {
        String title;
        long now = System.currentTimeMillis();
        if (this.window != null && User32.INSTANCE.IsWindow(this.window) && this.isSpotify(this.processOf(this.window)) && ((title = this.text(this.window)).contains(TRACK_SEPARATOR) || !title.isEmpty() && now - this.foundAt < 10000L)) {
            return title;
        }
        this.window = this.find();
        this.foundAt = now;
        return this.window == null ? null : this.text(this.window);
    }

    public static void tap(MediaKey key) {
        SpotifyWindow.send(key.virtualKey, 1);
        SpotifyWindow.send(key.virtualKey, 3);
    }

    private WinDef.HWND find() {
        long now = System.currentTimeMillis();
        if (now - this.pidCacheSince > 30000L || this.spotifyPids.size() > 512) {
            this.spotifyPids.clear();
            this.pidCacheSince = now;
        }
        this.best = null;
        this.bestScore = 0;
        User32.INSTANCE.EnumWindows(this.finder, Pointer.NULL);
        WinDef.HWND found = this.best;
        this.best = null;
        return found;
    }

    private boolean consider(WinDef.HWND hwnd, Pointer data) {
        if (!this.isSpotify(this.processOf(hwnd))) {
            return true;
        }
        String title = this.text(hwnd);
        if (title.isEmpty() || title.equals("Default IME") || title.equals("MSCTFIME UI") || title.startsWith("GDI+ Window")) {
            return true;
        }
        String windowClass = this.className(hwnd);
        int score = 1;
        if (windowClass.equals(MAIN_CLASS)) {
            score += 4;
        } else if (windowClass.startsWith(CLASS_FAMILY)) {
            score += 2;
        }
        if (title.contains(TRACK_SEPARATOR)) {
            score += 3;
        } else if (title.startsWith("Spotify")) {
            ++score;
        }
        if (User32.INSTANCE.IsWindowVisible(hwnd)) {
            ++score;
        }
        if (score > this.bestScore) {
            this.bestScore = score;
            this.best = hwnd;
        }
        return true;
    }

    private int processOf(WinDef.HWND hwnd) {
        User32.INSTANCE.GetWindowThreadProcessId(hwnd, this.pidOut);
        return this.pidOut.getValue();
    }

    private boolean isSpotify(int pid) {
        if (pid <= 0) {
            return false;
        }
        Boolean cached = this.spotifyPids.get(pid);
        if (cached != null) {
            return cached;
        }
        boolean spotify = EXECUTABLE.equals(this.imageName(pid));
        this.spotifyPids.put(pid, spotify);
        return spotify;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String imageName(int pid) {
        WinNT.HANDLE process = Kernel32.INSTANCE.OpenProcess(4096, false, pid);
        if (process == null) {
            return "";
        }
        try {
            this.sizeInOut.setValue(this.pathBuffer.length);
            if (!Kernel32.INSTANCE.QueryFullProcessImageName(process, 0, this.pathBuffer, this.sizeInOut)) {
                String string = "";
                return string;
            }
            String path = new String(this.pathBuffer, 0, Math.min(this.sizeInOut.getValue(), this.pathBuffer.length));
            int slash = path.lastIndexOf(92);
            String string = path.substring(slash + 1).toLowerCase(Locale.ROOT);
            return string;
        }
        finally {
            Kernel32.INSTANCE.CloseHandle(process);
        }
    }

    private String text(WinDef.HWND hwnd) {
        int length = User32.INSTANCE.GetWindowText(hwnd, this.textBuffer, this.textBuffer.length);
        return length <= 0 ? "" : new String(this.textBuffer, 0, Math.min(length, this.textBuffer.length));
    }

    private String className(WinDef.HWND hwnd) {
        int length = User32.INSTANCE.GetClassName(hwnd, this.classBuffer, this.classBuffer.length);
        return length <= 0 ? "" : new String(this.classBuffer, 0, Math.min(length, this.classBuffer.length));
    }

    private static void send(int virtualKey, int flags) {
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(1L);
        input.input.setType("ki");
        input.input.ki.wVk = new WinDef.WORD((long)virtualKey);
        input.input.ki.wScan = new WinDef.WORD(0L);
        input.input.ki.dwFlags = new WinDef.DWORD((long)flags);
        input.input.ki.time = new WinDef.DWORD(0L);
        User32.INSTANCE.SendInput(new WinDef.DWORD(1L), new WinUser.INPUT[]{input}, input.size());
    }

    @Environment(value=EnvType.CLIENT)
    public static enum MediaKey {
        PLAY_PAUSE(179),
        NEXT(176),
        PREVIOUS(177);

        final int virtualKey;

        private MediaKey(int virtualKey) {
            this.virtualKey = virtualKey;
        }
    }
}

