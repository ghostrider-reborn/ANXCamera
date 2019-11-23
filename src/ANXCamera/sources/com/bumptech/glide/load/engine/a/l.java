package com.bumptech.glide.load.engine.a;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.VisibleForTesting;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import com.bumptech.glide.util.i;

/* compiled from: MemorySizeCalculator */
public final class l {
    @VisibleForTesting
    static final int BYTES_PER_ARGB_8888_PIXEL = 4;
    private static final String TAG = "MemorySizeCalculator";
    private static final int jp = 2;
    private final Context context;
    private final int jq;
    private final int jr;
    private final int js;

    /* compiled from: MemorySizeCalculator */
    public static final class a {
        @VisibleForTesting
        static final int MEMORY_CACHE_TARGET_SCREENS = 2;
        static final int jt = (Build.VERSION.SDK_INT < 26 ? 4 : 1);
        static final float ju = 0.4f;
        static final float jv = 0.33f;
        static final int jw = 4194304;
        final Context context;
        float jA = ((float) jt);
        float jB = 0.4f;
        float jC = jv;
        int jD = 4194304;
        ActivityManager jx;
        c jy;
        float jz = 2.0f;

        public a(Context context2) {
            this.context = context2;
            this.jx = (ActivityManager) context2.getSystemService("activity");
            this.jy = new b(context2.getResources().getDisplayMetrics());
            if (Build.VERSION.SDK_INT >= 26 && l.a(this.jx)) {
                this.jA = 0.0f;
            }
        }

        public a A(int i) {
            this.jD = i;
            return this;
        }

        public a c(float f) {
            i.a(f >= 0.0f, "Memory cache screens must be greater than or equal to 0");
            this.jz = f;
            return this;
        }

        public l cm() {
            return new l(this);
        }

        public a d(float f) {
            i.a(f >= 0.0f, "Bitmap pool screens must be greater than or equal to 0");
            this.jA = f;
            return this;
        }

        public a e(float f) {
            i.a(f >= 0.0f && f <= 1.0f, "Size multiplier must be between 0 and 1");
            this.jB = f;
            return this;
        }

        public a f(float f) {
            i.a(f >= 0.0f && f <= 1.0f, "Low memory max size multiplier must be between 0 and 1");
            this.jC = f;
            return this;
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public a setActivityManager(ActivityManager activityManager) {
            this.jx = activityManager;
            return this;
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public a setScreenDimensions(c cVar) {
            this.jy = cVar;
            return this;
        }
    }

    /* compiled from: MemorySizeCalculator */
    private static final class b implements c {
        private final DisplayMetrics jE;

        b(DisplayMetrics displayMetrics) {
            this.jE = displayMetrics;
        }

        public int cn() {
            return this.jE.widthPixels;
        }

        public int co() {
            return this.jE.heightPixels;
        }
    }

    /* compiled from: MemorySizeCalculator */
    interface c {
        int cn();

        int co();
    }

    l(a aVar) {
        this.context = aVar.context;
        this.js = a(aVar.jx) ? aVar.jD / 2 : aVar.jD;
        int a2 = a(aVar.jx, aVar.jB, aVar.jC);
        float cn = (float) (aVar.jy.cn() * aVar.jy.co() * 4);
        int round = Math.round(aVar.jA * cn);
        int round2 = Math.round(cn * aVar.jz);
        int i = a2 - this.js;
        int i2 = round2 + round;
        if (i2 <= i) {
            this.jr = round2;
            this.jq = round;
        } else {
            float f = ((float) i) / (aVar.jA + aVar.jz);
            this.jr = Math.round(aVar.jz * f);
            this.jq = Math.round(f * aVar.jA);
        }
        if (Log.isLoggable(TAG, 3)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Calculation complete, Calculated memory cache size: ");
            sb.append(z(this.jr));
            sb.append(", pool size: ");
            sb.append(z(this.jq));
            sb.append(", byte array size: ");
            sb.append(z(this.js));
            sb.append(", memory class limited? ");
            sb.append(i2 > a2);
            sb.append(", max size: ");
            sb.append(z(a2));
            sb.append(", memoryClass: ");
            sb.append(aVar.jx.getMemoryClass());
            sb.append(", isLowMemoryDevice: ");
            sb.append(a(aVar.jx));
            Log.d(TAG, sb.toString());
        }
    }

    private static int a(ActivityManager activityManager, float f, float f2) {
        boolean a2 = a(activityManager);
        float memoryClass = (float) (activityManager.getMemoryClass() * 1024 * 1024);
        if (a2) {
            f = f2;
        }
        return Math.round(memoryClass * f);
    }

    @TargetApi(19)
    static boolean a(ActivityManager activityManager) {
        if (Build.VERSION.SDK_INT >= 19) {
            return activityManager.isLowRamDevice();
        }
        return true;
    }

    private String z(int i) {
        return Formatter.formatFileSize(this.context, (long) i);
    }

    public int cj() {
        return this.jr;
    }

    public int ck() {
        return this.jq;
    }

    public int cl() {
        return this.js;
    }
}
