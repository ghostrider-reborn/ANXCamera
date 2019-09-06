package com.bumptech.glide.load.engine.b;

import android.os.Build.VERSION;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import java.io.File;
import java.util.regex.Pattern;

/* compiled from: RuntimeCompat */
final class g {
    private static final String TAG = "GlideRuntimeCompat";
    private static final String sh = "cpu[0-9]+";
    private static final String th = "/sys/devices/system/cpu/";

    private g() {
    }

    static int availableProcessors() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        return VERSION.SDK_INT < 17 ? Math.max(wk(), availableProcessors) : availableProcessors;
    }

    /* JADX INFO: finally extract failed */
    private static int wk() {
        File[] fileArr;
        String str = TAG;
        ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            fileArr = new File(th).listFiles(new f(Pattern.compile(sh)));
            StrictMode.setThreadPolicy(allowThreadDiskReads);
        } catch (Throwable th2) {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            throw th2;
        }
        return Math.max(1, fileArr != null ? fileArr.length : 0);
    }
}
