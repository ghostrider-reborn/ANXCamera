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
public final class q {
    @VisibleForTesting
    static final int BYTES_PER_ARGB_8888_PIXEL = 4;
    private static final String TAG = "MemorySizeCalculator";
    private static final int _g = 2;
    private final int Xg;
    private final int Yg;
    private final int Zg;
    private final Context context;

    /* compiled from: MemorySizeCalculator */
    public static final class a {
        @VisibleForTesting
        static final int MEMORY_CACHE_TARGET_SCREENS = 2;
        static final int Sg = (Build.VERSION.SDK_INT < 26 ? 4 : 1);
        static final float Tg = 0.4f;
        static final float Ug = 0.33f;
        static final int Vg = 4194304;
        ActivityManager Mg;
        float Ng = 2.0f;
        float Og = ((float) Sg);
        float Pg = 0.4f;
        float Qg = Ug;
        int Rg = 4194304;
        final Context context;
        c screenDimensions;

        public a(Context context2) {
            this.context = context2;
            this.Mg = (ActivityManager) context2.getSystemService("activity");
            this.screenDimensions = new b(context2.getResources().getDisplayMetrics());
            if (Build.VERSION.SDK_INT >= 26 && q.a(this.Mg)) {
                this.Og = 0.0f;
            }
        }

        public q build() {
            return new q(this);
        }

        public a c(float f2) {
            i.a(f2 >= 0.0f, "Bitmap pool screens must be greater than or equal to 0");
            this.Og = f2;
            return this;
        }

        public a d(float f2) {
            i.a(f2 >= 0.0f && f2 <= 1.0f, "Low memory max size multiplier must be between 0 and 1");
            this.Qg = f2;
            return this;
        }

        public a e(float f2) {
            i.a(f2 >= 0.0f && f2 <= 1.0f, "Size multiplier must be between 0 and 1");
            this.Pg = f2;
            return this;
        }

        public a f(float f2) {
            i.a(f2 >= 0.0f, "Memory cache screens must be greater than or equal to 0");
            this.Ng = f2;
            return this;
        }

        public a s(int i) {
            this.Rg = i;
            return this;
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public a setActivityManager(ActivityManager activityManager) {
            this.Mg = activityManager;
            return this;
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public a setScreenDimensions(c cVar) {
            this.screenDimensions = cVar;
            return this;
        }
    }

    /* compiled from: MemorySizeCalculator */
    private static final class b implements c {
        private final DisplayMetrics Wg;

        b(DisplayMetrics displayMetrics) {
            this.Wg = displayMetrics;
        }

        public int A() {
            return this.Wg.widthPixels;
        }

        public int p() {
            return this.Wg.heightPixels;
        }
    }

    /* compiled from: MemorySizeCalculator */
    interface c {
        int A();

        int p();
    }

    q(a aVar) {
        this.context = aVar.context;
        this.Zg = a(aVar.Mg) ? aVar.Rg / 2 : aVar.Rg;
        int a2 = a(aVar.Mg, aVar.Pg, aVar.Qg);
        float A = (float) (aVar.screenDimensions.A() * aVar.screenDimensions.p() * 4);
        int round = Math.round(aVar.Og * A);
        int round2 = Math.round(A * aVar.Ng);
        int i = a2 - this.Zg;
        int i2 = round2 + round;
        if (i2 <= i) {
            this.Yg = round2;
            this.Xg = round;
        } else {
            float f2 = (float) i;
            float f3 = aVar.Og;
            float f4 = aVar.Ng;
            float f5 = f2 / (f3 + f4);
            this.Yg = Math.round(f4 * f5);
            this.Xg = Math.round(f5 * aVar.Og);
        }
        if (Log.isLoggable(TAG, 3)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Calculation complete, Calculated memory cache size: ");
            sb.append(R(this.Yg));
            sb.append(", pool size: ");
            sb.append(R(this.Xg));
            sb.append(", byte array size: ");
            sb.append(R(this.Zg));
            sb.append(", memory class limited? ");
            sb.append(i2 > a2);
            sb.append(", max size: ");
            sb.append(R(a2));
            sb.append(", memoryClass: ");
            sb.append(aVar.Mg.getMemoryClass());
            sb.append(", isLowMemoryDevice: ");
            sb.append(a(aVar.Mg));
            Log.d(TAG, sb.toString());
        }
    }

    private String R(int i) {
        return Formatter.formatFileSize(this.context, (long) i);
    }

    private static int a(ActivityManager activityManager, float f2, float f3) {
        boolean a2 = a(activityManager);
        float memoryClass = (float) (activityManager.getMemoryClass() * 1024 * 1024);
        if (a2) {
            f2 = f3;
        }
        return Math.round(memoryClass * f2);
    }

    @TargetApi(19)
    static boolean a(ActivityManager activityManager) {
        if (Build.VERSION.SDK_INT >= 19) {
            return activityManager.isLowRamDevice();
        }
        return true;
    }

    public int vg() {
        return this.Zg;
    }

    public int wg() {
        return this.Xg;
    }

    public int xg() {
        return this.Yg;
    }
}
