package com.bumptech.glide.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.SystemClock;

/* compiled from: LogTime */
public final class e {
    private static final double Yl;

    static {
        double d2 = 1.0d;
        if (Build.VERSION.SDK_INT >= 17) {
            d2 = 1.0d / Math.pow(10.0d, 6.0d);
        }
        Yl = d2;
    }

    private e() {
    }

    @TargetApi(17)
    public static long Gh() {
        return Build.VERSION.SDK_INT >= 17 ? SystemClock.elapsedRealtimeNanos() : SystemClock.uptimeMillis();
    }

    public static double g(long j) {
        return ((double) (Gh() - j)) * Yl;
    }
}
