package net.anubis.picker;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: analysis-workspace/extracted/run.oEA4CbVx/net/anubis/compat/android-picker/classes.dex */
public final class AndroidImagePicker {
    private static final long MAX_BYTES = 67108864;
    private static final ConcurrentHashMap<String, Request> REQUESTS = new ConcurrentHashMap<>();
    private static final long TIMEOUT_MS = 600000;

    private AndroidImagePicker() {
    }

    public static String launch(ClassLoader classLoader, final Object obj, String str) throws IOException {
        if (str == null) {
            return "The image request has no result directory.";
        }
        try {
            File canonicalFile = new File(str).getCanonicalFile();
            if (canonicalFile.isDirectory() && canonicalFile.canWrite()) {
                if (new File(canonicalFile, "result.txt").exists()) {
                    return "This image request has already finished.";
                }
                final Request request = new Request(canonicalFile, classLoader);
                ConcurrentHashMap<String, Request> concurrentHashMap = REQUESTS;
                if (concurrentHashMap.putIfAbsent(canonicalFile.getPath(), request) != null) {
                    return "An image request is already using this directory.";
                }
                try {
                    Handler handler = new Handler(Looper.getMainLooper());
                    request.handler = handler;
                    if (handler.post(new Runnable() { // from class: net.anubis.picker.AndroidImagePicker.1
                        @Override // java.lang.Runnable
                        public void run() throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
                            request.locateActivity(obj);
                        }
                    })) {
                        return null;
                    }
                    concurrentHashMap.remove(canonicalFile.getPath(), request);
                    return "The launcher's Android interface is unavailable.";
                } catch (RuntimeException unused) {
                    REQUESTS.remove(canonicalFile.getPath(), request);
                    return "The launcher's Android interface is unavailable.";
                }
            }
            return "The image request directory is not writable.";
        } catch (IOException | SecurityException unused2) {
            return "The image request directory is not accessible.";
        }
    }

    public static void cancel(String str) {
        if (str == null) {
            return;
        }
        try {
            Request request = REQUESTS.get(new File(str).getCanonicalPath());
            if (request != null) {
                request.finish("cancel", "");
            }
        } catch (IOException | SecurityException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Activity asActivity(Object obj) {
        Context baseContext;
        if (!(obj instanceof Context)) {
            return null;
        }
        Context context = (Context) obj;
        int i = 0;
        while (i < 16 && context != null) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            if (!(context instanceof ContextWrapper) || (baseContext = ((ContextWrapper) context).getBaseContext()) == context) {
                return null;
            }
            i++;
            context = baseContext;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Class<?> findClass(ClassLoader classLoader, String str) throws ClassNotFoundException {
        return Class.forName(str, true, classLoader);
    }

    private static final class Request {
        final File directory;
        volatile boolean done;
        Handler handler;
        Application hostApplication;
        Class<?> hostClass;
        int hostTask;
        volatile Closeable input;
        Application.ActivityLifecycleCallbacks lifecycle;
        final ClassLoader loader;
        volatile boolean received;
        Object registration;
        boolean started;
        Method unregister;
        final String registryKey = "anubis.image." + Long.toHexString(System.nanoTime());
        WeakReference<Activity> registeredActivity = new WeakReference<>(null);
        final Runnable unavailable = new Runnable() { // from class: net.anubis.picker.AndroidImagePicker.Request.1
            @Override // java.lang.Runnable
            public void run() {
                if (Request.this.started || Request.this.done) {
                    return;
                }
                Request.this.finish("error", "No active launcher game screen was found.");
            }
        };
        final Runnable timeout = new Runnable() { // from class: net.anubis.picker.AndroidImagePicker.Request.2
            @Override // java.lang.Runnable
            public void run() {
                Request.this.finish("error", "Image selection timed out. Open the image selector again.");
            }
        };

        Request(File file, ClassLoader classLoader) {
            this.directory = file;
            this.loader = classLoader;
        }

        void locateActivity(Object obj) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
            Activity activityAsActivity;
            if (this.done) {
                return;
            }
            Activity activityAsActivity2 = AndroidImagePicker.asActivity(obj);
            if (activityAsActivity2 != null) {
                open(activityAsActivity2);
                return;
            }
            Activity activityZalithActivity = zalithActivity();
            if (activityZalithActivity != null) {
                open(activityZalithActivity);
                return;
            }
            String[][] strArr = {new String[]{"com.movtery.zalithlauncher.context.ContextsKt", "getGlobalContext"}, new String[]{"com.movtery.zalithlauncher.context.ContextExecutor", "getActivity"}};
            for (int i = 0; i < 2; i++) {
                String[] strArr2 = strArr[i];
                try {
                    activityAsActivity = AndroidImagePicker.asActivity(AndroidImagePicker.findClass(this.loader, strArr2[0]).getMethod(strArr2[1], null).invoke(null, null));
                } catch (LinkageError | ReflectiveOperationException | RuntimeException unused) {
                }
                if (activityAsActivity != null) {
                    open(activityAsActivity);
                    return;
                }
                continue;
            }
            String[][] strArr3 = {new String[]{"git.artdeell.mojo.utils.lifecycle.ContextExecutor", "executeActivity", "git.artdeell.mojo.utils.lifecycle.ActivityRunnable"}, new String[]{"net.kdt.pojavlaunch.utils.lifecycle.ContextExecutor", "executeActivity", "net.kdt.pojavlaunch.utils.lifecycle.ActivityRunnable"}, new String[]{"net.kdt.pojavlaunch.lifecycle.ContextExecutor", "execute", "net.kdt.pojavlaunch.lifecycle.ContextExecutorTask"}, new String[]{"net.kdt.pojavlaunch.utils.lifecycle.ContextExecutor", "execute", "net.kdt.pojavlaunch.utils.lifecycle.ContextExecutorTask"}};
            for (int i2 = 0; i2 < 4; i2++) {
                String[] strArr4 = strArr3[i2];
                try {
                    Class<?> clsFindClass = AndroidImagePicker.findClass(this.loader, strArr4[2]);
                    AndroidImagePicker.findClass(this.loader, strArr4[0]).getMethod(strArr4[1], clsFindClass).invoke(null, Proxy.newProxyInstance(this.loader, new Class[]{clsFindClass}, new InvocationHandler() { // from class: net.anubis.picker.AndroidImagePicker.Request.3
                        @Override // java.lang.reflect.InvocationHandler
                        public Object invoke(Object obj2, Method method, Object[] objArr) {
                            if (method.getDeclaringClass() == Object.class) {
                                return AndroidImagePicker.objectMethod(obj2, method, objArr);
                            }
                            if ("executeWithActivity".equals(method.getName()) && objArr != null && objArr.length > 0) {
                                final Activity activityAsActivity3 = AndroidImagePicker.asActivity(objArr[0]);
                                if (activityAsActivity3 == null) {
                                    return null;
                                }
                                Request.this.handler.post(new Runnable() { // from class: net.anubis.picker.AndroidImagePicker.Request.3.1
                                    @Override // java.lang.Runnable
                                    public void run() throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
                                        Request.this.open(activityAsActivity3);
                                    }
                                });
                                return null;
                            }
                            if (!"executeWithApplication".equals(method.getName())) {
                                return null;
                            }
                            Request.this.finish("error", "Bring the launcher game screen to the foreground and try again.");
                            return null;
                        }
                    }));
                    this.handler.postDelayed(this.unavailable, 5000L);
                    return;
                } catch (LinkageError | ReflectiveOperationException | RuntimeException unused2) {
                }
            }
            finish("error", "This launcher does not expose a supported Android image-selection screen.");
        }

        Activity zalithActivity() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            Object objInvoke;
            Class<?> superclass;
            ClassLoader classLoader;
            try {
                objInvoke = AndroidImagePicker.findClass(this.loader, "com.movtery.zalithlauncher.bridge.ZLNativeInvoker").getMethod("getStaticLauncher", null).invoke(null, null);
            } catch (LinkageError | ReflectiveOperationException | RuntimeException unused) {
            }
            if (objInvoke == null || (classLoader = (superclass = objInvoke.getClass()).getClassLoader()) == null) {
                return null;
            }
            int i = 0;
            for (superclass = objInvoke.getClass(); i < 8 && superclass != null && superclass != Object.class; superclass = superclass.getSuperclass()) {
                if (superclass.getClassLoader() != classLoader) {
                    break;
                }
                for (Field field : superclass.getDeclaredFields()) {
                    if (!Modifier.isStatic(field.getModifiers()) && Activity.class.isAssignableFrom(field.getType())) {
                        field.setAccessible(true);
                        Activity activity = (Activity) field.get(objInvoke);
                        if (activity != null) {
                            return activity;
                        }
                    }
                }
                i++;
            }
            return null;
        }

        void open(Activity activity) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
            if (this.done || this.started) {
                return;
            }
            if (activity.isFinishing() || activity.isDestroyed()) {
                finish("error", "The launcher game screen is closing. Try again after reopening it.");
                return;
            }
            this.started = true;
            this.handler.removeCallbacks(this.unavailable);
            this.hostClass = activity.getClass();
            this.hostTask = activity.getTaskId();
            this.hostApplication = activity.getApplication();
            Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() { // from class: net.anubis.picker.AndroidImagePicker.Request.4
                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityPaused(Activity activity2) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityResumed(Activity activity2) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivitySaveInstanceState(Activity activity2, Bundle bundle) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStarted(Activity activity2) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStopped(Activity activity2) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityCreated(final Activity activity2, Bundle bundle) {
                    if (Request.this.done || Request.this.received || activity2.getClass() != Request.this.hostClass || activity2.getTaskId() != Request.this.hostTask) {
                        return;
                    }
                    Request.this.handler.post(new Runnable() { // from class: net.anubis.picker.AndroidImagePicker.Request.4.1
                        @Override // java.lang.Runnable
                        public void run() throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
                            Request.this.register(activity2, false);
                        }
                    });
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityDestroyed(Activity activity2) {
                    if (Request.this.done || Request.this.received || Request.this.registeredActivity.get() != activity2 || activity2.isChangingConfigurations()) {
                        return;
                    }
                    Request.this.finish("cancel", "");
                }
            };
            this.lifecycle = activityLifecycleCallbacks;
            Application application = this.hostApplication;
            if (application != null) {
                application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
            }
            this.handler.postDelayed(this.timeout, AndroidImagePicker.TIMEOUT_MS);
            register(activity, true);
        }

        void register(Activity activity, boolean z) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
            if (this.done || this.received) {
                return;
            }
            try {
                Class clsFindClass = AndroidImagePicker.findClass(this.loader, "androidx.activity.result.ActivityResultRegistry");
                Class<?> clsFindClass2 = AndroidImagePicker.findClass(this.loader, "androidx.activity.result.contract.ActivityResultContract");
                Class<?> clsFindClass3 = AndroidImagePicker.findClass(this.loader, "androidx.activity.result.ActivityResultCallback");
                Class clsFindClass4 = AndroidImagePicker.findClass(this.loader, "androidx.activity.result.ActivityResultLauncher");
                final Class clsFindClass5 = AndroidImagePicker.findClass(this.loader, "androidx.activity.result.ActivityResult");
                Object objInvoke = activity.getClass().getMethod("getActivityResultRegistry", null).invoke(activity, null);
                Object objNewInstance = AndroidImagePicker.findClass(this.loader, "androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult").getConstructor(null).newInstance(null);
                final ContentResolver contentResolver = activity.getApplicationContext().getContentResolver();
                Object objNewProxyInstance = Proxy.newProxyInstance(this.loader, new Class[]{clsFindClass3}, new InvocationHandler() { // from class: net.anubis.picker.AndroidImagePicker.Request.5
                    /* JADX WARN: Removed duplicated region for block: B:23:0x003d A[Catch: RuntimeException -> 0x0096, ReflectiveOperationException -> 0x009c, TRY_ENTER, TryCatch #2 {ReflectiveOperationException -> 0x009c, RuntimeException -> 0x0096, blocks: (B:15:0x002e, B:18:0x0032, B:23:0x003d, B:25:0x0043, B:28:0x0068, B:31:0x006f, B:32:0x0090), top: B:37:0x002e }] */
                    /* JADX WARN: Removed duplicated region for block: B:25:0x0043 A[Catch: RuntimeException -> 0x0096, ReflectiveOperationException -> 0x009c, TryCatch #2 {ReflectiveOperationException -> 0x009c, RuntimeException -> 0x0096, blocks: (B:15:0x002e, B:18:0x0032, B:23:0x003d, B:25:0x0043, B:28:0x0068, B:31:0x006f, B:32:0x0090), top: B:37:0x002e }] */
                    @Override // java.lang.reflect.InvocationHandler
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct add '--show-bad-code' argument
                    */
                    public java.lang.Object invoke(java.lang.Object r7, java.lang.reflect.Method r8, java.lang.Object[] r9) {
                        /*
                            r6 = this;
                            java.lang.Class r0 = r8.getDeclaringClass()
                            java.lang.Class<java.lang.Object> r1 = java.lang.Object.class
                            if (r0 != r1) goto Ld
                            java.lang.Object r7 = net.anubis.picker.AndroidImagePicker.access$200(r7, r8, r9)
                            return r7
                        Ld:
                            java.lang.String r7 = "onActivityResult"
                            java.lang.String r8 = r8.getName()
                            boolean r7 = r7.equals(r8)
                            r8 = 0
                            if (r7 == 0) goto La1
                            net.anubis.picker.AndroidImagePicker$Request r7 = net.anubis.picker.AndroidImagePicker.Request.this
                            boolean r7 = r7.done
                            if (r7 != 0) goto La1
                            net.anubis.picker.AndroidImagePicker$Request r7 = net.anubis.picker.AndroidImagePicker.Request.this
                            boolean r7 = r7.received
                            if (r7 == 0) goto L28
                            goto La1
                        L28:
                            java.lang.String r7 = "The launcher could not return the selected image."
                            java.lang.String r0 = "error"
                            if (r9 == 0) goto L36
                            int r1 = r9.length     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            if (r1 != 0) goto L32
                            goto L36
                        L32:
                            r1 = 0
                            r9 = r9[r1]     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            goto L37
                        L36:
                            r9 = r8
                        L37:
                            java.lang.String r1 = ""
                            java.lang.String r2 = "cancel"
                            if (r9 != 0) goto L43
                            net.anubis.picker.AndroidImagePicker$Request r9 = net.anubis.picker.AndroidImagePicker.Request.this     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            r9.finish(r2, r1)     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            return r8
                        L43:
                            java.lang.Class r3 = r2     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            java.lang.String r4 = "getResultCode"
                            java.lang.reflect.Method r3 = r3.getMethod(r4, r8)     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            java.lang.Object r3 = r3.invoke(r9, r8)     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            int r3 = r3.intValue()     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            java.lang.Class r4 = r2     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            java.lang.String r5 = "getData"
                            java.lang.reflect.Method r4 = r4.getMethod(r5, r8)     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            java.lang.Object r9 = r4.invoke(r9, r8)     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            android.content.Intent r9 = (android.content.Intent) r9     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            r4 = -1
                            if (r3 != r4) goto L90
                            if (r9 == 0) goto L90
                            android.net.Uri r3 = r9.getData()     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            if (r3 != 0) goto L6f
                            goto L90
                        L6f:
                            net.anubis.picker.AndroidImagePicker$Request r1 = net.anubis.picker.AndroidImagePicker.Request.this     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            r2 = 1
                            r1.received = r2     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            android.net.Uri r9 = r9.getData()     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            net.anubis.picker.AndroidImagePicker$Request r1 = net.anubis.picker.AndroidImagePicker.Request.this     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            r1.unregisterCallback()     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            java.lang.Thread r1 = new java.lang.Thread     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            net.anubis.picker.AndroidImagePicker$Request$5$1 r3 = new net.anubis.picker.AndroidImagePicker$Request$5$1     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            r3.<init>()     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            java.lang.String r9 = "Anubis selected image"
                            r1.<init>(r3, r9)     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            r1.setDaemon(r2)     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            r1.start()     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            goto La1
                        L90:
                            net.anubis.picker.AndroidImagePicker$Request r9 = net.anubis.picker.AndroidImagePicker.Request.this     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            r9.finish(r2, r1)     // Catch: java.lang.RuntimeException -> L96 java.lang.ReflectiveOperationException -> L9c
                            goto La1
                        L96:
                            net.anubis.picker.AndroidImagePicker$Request r9 = net.anubis.picker.AndroidImagePicker.Request.this
                            r9.finish(r0, r7)
                            goto La1
                        L9c:
                            net.anubis.picker.AndroidImagePicker$Request r9 = net.anubis.picker.AndroidImagePicker.Request.this
                            r9.finish(r0, r7)
                        La1:
                            return r8
                        */
                        throw new UnsupportedOperationException("Method not decompiled: net.anubis.picker.AndroidImagePicker.Request.AnonymousClass5.invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[]):java.lang.Object");
                    }
                });
                unregisterCallback();
                this.unregister = clsFindClass4.getMethod("unregister", null);
                Object objInvoke2 = clsFindClass.getMethod("register", String.class, clsFindClass2, clsFindClass3).invoke(objInvoke, this.registryKey, objNewInstance, objNewProxyInstance);
                this.registration = objInvoke2;
                this.registeredActivity = new WeakReference<>(activity);
                if (!this.done && !this.received) {
                    if (z) {
                        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
                        intent.addCategory("android.intent.category.OPENABLE");
                        intent.setType("image/*");
                        intent.putExtra("android.intent.extra.MIME_TYPES", new String[]{"image/png", "image/jpeg", "image/gif"});
                        intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", false);
                        intent.addFlags(1);
                        clsFindClass4.getMethod("launch", Object.class).invoke(objInvoke2, intent);
                        return;
                    }
                    return;
                }
                unregisterCallback();
            } catch (LinkageError unused) {
                finish("error", "This launcher does not include the required Android image-selector support.");
            } catch (ReflectiveOperationException unused2) {
                finish("error", "This launcher cannot open the Android image selector. Update the launcher and try again.");
            } catch (RuntimeException unused3) {
                finish("error", "Android could not open the image selector. Check that a Documents app is enabled.");
            }
        }

        void copyImage(ContentResolver contentResolver, Uri uri) {
            int i;
            int i2;
            File file = new File(this.directory, "selected.part");
            try {
                try {
                } catch (IOException unused) {
                    finish("error", "Could not read the selected image. Save it on this device and try again.");
                } catch (RuntimeException unused2) {
                    finish("error", "Android did not grant access to the selected image. Select it again.");
                }
                if (!this.done) {
                    String scheme = uri.getScheme();
                    if (!"content".equalsIgnoreCase(scheme) && !"file".equalsIgnoreCase(scheme)) {
                        finish("error", "The selected image provider returned an unsupported link.");
                    } else {
                        if (!file.createNewFile()) {
                            throw new IOException("Existing temporary file");
                        }
                        try {
                            InputStream inputStreamOpenInputStream = contentResolver.openInputStream(uri);
                            try {
                                if (inputStreamOpenInputStream == null) {
                                    throw new IOException("No image stream");
                                }
                                this.input = inputStreamOpenInputStream;
                                byte[] bArr = new byte[8];
                                int i3 = 0;
                                while (i3 < 8 && !this.done && (i2 = inputStreamOpenInputStream.read()) != -1) {
                                    bArr[i3] = (byte) i2;
                                    i3++;
                                }
                                String strImageExtension = AndroidImagePicker.imageExtension(bArr, i3);
                                if (strImageExtension == null) {
                                    finish("error", "Choose a PNG, JPG or GIF image.");
                                    if (inputStreamOpenInputStream != null) {
                                        inputStreamOpenInputStream.close();
                                    }
                                } else {
                                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                                    try {
                                        fileOutputStream.write(bArr, 0, i3);
                                        long j = i3;
                                        byte[] bArr2 = new byte[32768];
                                        while (!this.done && (i = inputStreamOpenInputStream.read(bArr2)) != -1) {
                                            if (i == 0) {
                                                int i4 = inputStreamOpenInputStream.read();
                                                if (i4 == -1) {
                                                    break;
                                                }
                                                bArr2[0] = (byte) i4;
                                                i = 1;
                                            }
                                            j += i;
                                            if (j > AndroidImagePicker.MAX_BYTES) {
                                                finish("error", "The image is larger than 64 MB. Choose a smaller image.");
                                                fileOutputStream.close();
                                                if (inputStreamOpenInputStream != null) {
                                                    inputStreamOpenInputStream.close();
                                                }
                                            } else {
                                                fileOutputStream.write(bArr2, 0, i);
                                            }
                                        }
                                        fileOutputStream.flush();
                                        fileOutputStream.getFD().sync();
                                        fileOutputStream.close();
                                        if (inputStreamOpenInputStream != null) {
                                            inputStreamOpenInputStream.close();
                                        }
                                        synchronized (this) {
                                            if (!this.done) {
                                                String str = "selected." + strImageExtension;
                                                File file2 = new File(this.directory, str);
                                                if (file2.exists() || !file.renameTo(file2)) {
                                                    throw new IOException("Unable to complete image copy");
                                                }
                                                finish("ok", str);
                                                return;
                                            }
                                        }
                                    } finally {
                                    }
                                }
                                this.input = null;
                            } finally {
                            }
                        } finally {
                            this.input = null;
                        }
                    }
                }
            } finally {
                AndroidImagePicker.deleteTemporary(file);
            }
        }

        synchronized void finish(String str, String str2) {
            if (this.done) {
                return;
            }
            this.done = true;
            AndroidImagePicker.writeResult(this.directory, str, str2);
            AndroidImagePicker.REQUESTS.remove(this.directory.getPath(), this);
            final Closeable closeable = this.input;
            if (closeable != null) {
                Thread thread = new Thread(new Runnable() { // from class: net.anubis.picker.AndroidImagePicker.Request.6
                    @Override // java.lang.Runnable
                    public void run() throws IOException {
                        try {
                            closeable.close();
                        } catch (IOException unused) {
                        }
                    }
                }, "Anubis image stream close");
                thread.setDaemon(true);
                thread.start();
            }
            this.handler.post(new Runnable() { // from class: net.anubis.picker.AndroidImagePicker.Request.7
                @Override // java.lang.Runnable
                public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    Request.this.handler.removeCallbacks(Request.this.unavailable);
                    Request.this.handler.removeCallbacks(Request.this.timeout);
                    Request.this.unregisterCallback();
                    if (Request.this.hostApplication != null && Request.this.lifecycle != null) {
                        Request.this.hostApplication.unregisterActivityLifecycleCallbacks(Request.this.lifecycle);
                    }
                    Request.this.hostApplication = null;
                    Request.this.lifecycle = null;
                }
            });
        }

        void unregisterCallback() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            Method method;
            Object obj = this.registration;
            this.registration = null;
            this.registeredActivity.clear();
            if (obj == null || (method = this.unregister) == null) {
                return;
            }
            try {
                method.invoke(obj, null);
            } catch (ReflectiveOperationException | RuntimeException unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Object objectMethod(Object obj, Method method, Object[] objArr) {
        if ("hashCode".equals(method.getName())) {
            return Integer.valueOf(System.identityHashCode(obj));
        }
        if (!"equals".equals(method.getName())) {
            if ("toString".equals(method.getName())) {
                return "Anubis image selection callback";
            }
            return null;
        }
        boolean z = false;
        if (objArr != null && objArr.length == 1 && obj == objArr[0]) {
            z = true;
        }
        return Boolean.valueOf(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String imageExtension(byte[] bArr, int i) {
        if (i >= 8 && (bArr[0] & 255) == 137 && bArr[1] == 80 && bArr[2] == 78 && bArr[3] == 71 && bArr[4] == 13 && bArr[5] == 10 && bArr[6] == 26 && bArr[7] == 10) {
            return "png";
        }
        if (i >= 3 && (bArr[0] & 255) == 255 && (bArr[1] & 255) == 216 && (bArr[2] & 255) == 255) {
            return "jpg";
        }
        if (i < 6 || bArr[0] != 71 || bArr[1] != 73 || bArr[2] != 70 || bArr[3] != 56) {
            return null;
        }
        byte b = bArr[4];
        if ((b == 55 || b == 57) && bArr[5] == 97) {
            return "gif";
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeResult(File file, String str, String str2) {
        File fileCreateTempFile = null;
        try {
            fileCreateTempFile = File.createTempFile("result-", ".tmp", file);
            byte[] bytes = (str + "\n" + str2.replace('\r', ' ').replace('\n', ' ') + "\n").getBytes("UTF-8");
            FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
            try {
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
                fileOutputStream.getFD().sync();
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException unused) {
            if (fileCreateTempFile == null) {
                return;
            }
        } catch (SecurityException unused2) {
            if (fileCreateTempFile == null) {
                return;
            }
        } catch (Throwable th) {
            if (fileCreateTempFile != null) {
                deleteTemporary(fileCreateTempFile);
            }
            throw th;
        }
        if (!fileCreateTempFile.renameTo(new File(file, "result.txt"))) {
            throw new IOException("Cannot commit image result");
        }
        if (fileCreateTempFile != null) {
            deleteTemporary(fileCreateTempFile);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void deleteTemporary(File file) {
        try {
            file.delete();
        } catch (SecurityException unused) {
        }
    }
}
