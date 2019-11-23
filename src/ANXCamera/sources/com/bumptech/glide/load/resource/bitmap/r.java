package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import com.bumptech.glide.load.DecodeFormat;
import java.io.File;

/* compiled from: HardwareConfigState */
final class r {
    private static final File mA = new File("/proc/self/fd");
    private static final int mB = 50;
    private static final int mC = 700;
    private static volatile r mF = null;
    private static final int mz = 128;
    private volatile int mD;
    private volatile boolean mE = true;

    private r() {
    }

    static r cY() {
        if (mF == null) {
            synchronized (r.class) {
                if (mF == null) {
                    mF = new r();
                }
            }
        }
        return mF;
    }

    private synchronized boolean cZ() {
        int i = this.mD + 1;
        this.mD = i;
        if (i >= 50) {
            boolean z = false;
            this.mD = 0;
            if (mA.list().length < 700) {
                z = true;
            }
            this.mE = z;
            if (!this.mE && Log.isLoggable("Downsampler", 5)) {
                Log.w("Downsampler", "Excluding HARDWARE bitmap config because we're over the file descriptor limit, file descriptors " + r2 + ", limit " + 700);
            }
        }
        return this.mE;
    }

    /* access modifiers changed from: package-private */
    @TargetApi(26)
    public boolean a(int i, int i2, BitmapFactory.Options options, DecodeFormat decodeFormat, boolean z, boolean z2) {
        if (!z || Build.VERSION.SDK_INT < 26 || decodeFormat == DecodeFormat.PREFER_ARGB_8888_DISALLOW_HARDWARE || z2) {
            return false;
        }
        boolean z3 = i >= 128 && i2 >= 128 && cZ();
        if (z3) {
            options.inPreferredConfig = Bitmap.Config.HARDWARE;
            options.inMutable = false;
        }
        return z3;
    }
}
