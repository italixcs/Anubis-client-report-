package net.anubis.client.module.impl.misc.noinvleak;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinGDI;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.glfw.GLFWNativeWin32;

@Environment(EnvType.CLIENT)
/* loaded from: classes-only.jar:net/anubis/client/module/impl/misc/noinvleak/OverlayWindow.class */
final class OverlayWindow {
    private static final String CLASS_NAME = "AnubisNoInvLeakOverlay";
    private static final int WS_EX_TOOLWINDOW = 128;
    private static final int WS_EX_NOACTIVATE = 134217728;
    private static final int WDA_EXCLUDEFROMCAPTURE = 17;
    private static final WinUser.WindowProc PROC = (hwnd, message, wParam, lParam) -> {
        return User32.INSTANCE.DefWindowProc(hwnd, message, wParam, lParam);
    };
    private static boolean classRegistered;
    private final WinDef.POINT destination = new WinDef.POINT();
    private final WinDef.POINT source = new WinDef.POINT(0, 0);
    private final WinUser.SIZE size = new WinUser.SIZE();
    private final WinUser.BLENDFUNCTION blend = new WinUser.BLENDFUNCTION();
    private WinDef.HWND hwnd;
    private WinDef.HDC memory;
    private WinDef.HBITMAP bitmap;
    private WinNT.HANDLE previousBitmap;
    private Pointer bitsPointer;
    private IntBuffer bits;
    private int capacityWidth;
    private int capacityHeight;
    private boolean visible;

    @Environment(EnvType.CLIENT)
    /* loaded from: classes-only.jar:net/anubis/client/module/impl/misc/noinvleak/OverlayWindow$Affinity.class */
    private interface Affinity extends StdCallLibrary {
        public static final Affinity INSTANCE = Native.load("user32", Affinity.class, W32APIOptions.DEFAULT_OPTIONS);

        boolean SetWindowDisplayAffinity(WinDef.HWND hwnd, int i);
    }

    OverlayWindow(long glfwWindow) {
        WinDef.HMODULE hmoduleGetModuleHandle = Kernel32.INSTANCE.GetModuleHandle((String) null);
        if (!classRegistered) {
            WinUser.WNDCLASSEX cls = new WinUser.WNDCLASSEX();
            cls.lpfnWndProc = PROC;
            cls.hInstance = hmoduleGetModuleHandle;
            cls.lpszClassName = CLASS_NAME;
            if (User32.INSTANCE.RegisterClassEx(cls).intValue() == 0) {
                throw failure("RegisterClassEx");
            }
            classRegistered = true;
        }
        WinDef.HWND owner = new WinDef.HWND(new Pointer(GLFWNativeWin32.glfwGetWin32Window(glfwWindow)));
        this.hwnd = User32.INSTANCE.CreateWindowEx(134742176, CLASS_NAME, "", Integer.MIN_VALUE, 0, 0, 1, 1, owner, (WinDef.HMENU) null, hmoduleGetModuleHandle, (WinDef.LPVOID) null);
        if (this.hwnd == null) {
            throw failure("CreateWindowEx");
        }
        if (!Affinity.INSTANCE.SetWindowDisplayAffinity(this.hwnd, WDA_EXCLUDEFROMCAPTURE)) {
            RuntimeException failure = failure("SetWindowDisplayAffinity");
            destroy();
            throw failure;
        }
        this.blend.BlendOp = (byte) 0;
        this.blend.SourceConstantAlpha = (byte) -1;
        this.blend.AlphaFormat = (byte) 1;
    }

    IntBuffer pixels(int width, int height) {
        if (width > this.capacityWidth || height > this.capacityHeight) {
            int nextWidth = Math.max(width, this.capacityWidth);
            int nextHeight = Math.max(height, this.capacityHeight);
            releaseBitmap();
            WinGDI.BITMAPINFO info = new WinGDI.BITMAPINFO();
            info.bmiHeader.biWidth = nextWidth;
            info.bmiHeader.biHeight = -nextHeight;
            info.bmiHeader.biPlanes = (short) 1;
            info.bmiHeader.biBitCount = (short) 32;
            info.bmiHeader.biCompression = 0;
            PointerByReference bitsOut = new PointerByReference();
            this.bitmap = GDI32.INSTANCE.CreateDIBSection((WinDef.HDC) null, info, 0, bitsOut, (Pointer) null, 0);
            if (this.bitmap == null) {
                throw failure("CreateDIBSection");
            }
            if (this.memory == null) {
                this.memory = GDI32.INSTANCE.CreateCompatibleDC((WinDef.HDC) null);
                if (this.memory == null) {
                    throw failure("CreateCompatibleDC");
                }
            }
            this.previousBitmap = GDI32.INSTANCE.SelectObject(this.memory, this.bitmap);
            this.capacityWidth = nextWidth;
            this.capacityHeight = nextHeight;
            this.bitsPointer = bitsOut.getValue();
            this.bits = this.bitsPointer.getByteBuffer(0L, nextWidth * nextHeight * 4).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
        }
        this.bitsPointer.setMemory(0L, this.capacityWidth * height * 4, (byte) 0);
        return this.bits;
    }

    int stride() {
        return this.capacityWidth;
    }

    void present(int screenX, int screenY, int width, int height) {
        this.destination.x = screenX;
        this.destination.y = screenY;
        this.size.cx = width;
        this.size.cy = height;
        if (!User32.INSTANCE.UpdateLayeredWindow(this.hwnd, (WinDef.HDC) null, this.destination, this.size, this.memory, this.source, 0, this.blend, 2)) {
            throw failure("UpdateLayeredWindow");
        }
        if (!this.visible) {
            User32.INSTANCE.ShowWindow(this.hwnd, 4);
            this.visible = true;
        }
    }

    void move(int screenX, int screenY) {
        User32.INSTANCE.SetWindowPos(this.hwnd, (WinDef.HWND) null, screenX, screenY, 0, 0, 21);
    }

    void hide() {
        if (this.visible) {
            User32.INSTANCE.ShowWindow(this.hwnd, 0);
            this.visible = false;
        }
    }

    void destroy() {
        releaseBitmap();
        if (this.memory != null) {
            GDI32.INSTANCE.DeleteDC(this.memory);
            this.memory = null;
        }
        if (this.hwnd != null) {
            User32.INSTANCE.DestroyWindow(this.hwnd);
            this.hwnd = null;
        }
        this.visible = false;
    }

    private void releaseBitmap() {
        if (this.bitmap == null) {
            return;
        }
        if (this.previousBitmap != null) {
            GDI32.INSTANCE.SelectObject(this.memory, this.previousBitmap);
        }
        GDI32.INSTANCE.DeleteObject(this.bitmap);
        this.bitmap = null;
        this.previousBitmap = null;
        this.bitsPointer = null;
        this.bits = null;
        this.capacityWidth = 0;
        this.capacityHeight = 0;
    }

    private static RuntimeException failure(String call) {
        return new IllegalStateException(call + " failed with error " + Kernel32.INSTANCE.GetLastError());
    }
}
