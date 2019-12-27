package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import com.bumptech.glide.load.DecodeFormat;
import java.io.File;

/* compiled from: HardwareConfigState */
final class t {
    private static volatile t instance = null;
    private static final int lj = 128;
    private static final File mj = new File("/proc/self/fd");
    private static final int nj = 50;
    private static final int oj = 700;
    private volatile int jj;
    private volatile boolean kj = true;

    private t() {
    }

    private synchronized boolean Hk() {
        int i = this.jj + 1;
        this.jj = i;
        if (i >= 50) {
            boolean z = false;
            this.jj = 0;
            if (mj.list().length < 700) {
                z = true;
            }
            this.kj = z;
            if (!this.kj && Log.isLoggable("Downsampler", 5)) {
                Log.w("Downsampler", "Excluding HARDWARE bitmap config because we're over the file descriptor limit, file descriptors " + r2 + ", limit " + 700);
            }
        }
        return this.kj;
    }

    static t getInstance() {
        if (instance == null) {
            synchronized (t.class) {
                if (instance == null) {
                    instance = new t();
                }
            }
        }
        return instance;
    }

    /* access modifiers changed from: package-private */
    @TargetApi(26)
    public boolean a(int i, int i2, BitmapFactory.Options options, DecodeFormat decodeFormat, boolean z, boolean z2) {
        if (!z || Build.VERSION.SDK_INT < 26 || decodeFormat == DecodeFormat.PREFER_ARGB_8888_DISALLOW_HARDWARE || z2) {
            return false;
        }
        boolean z3 = i >= 128 && i2 >= 128 && Hk();
        if (z3) {
            options.inPreferredConfig = Bitmap.Config.HARDWARE;
            options.inMutable = false;
        }
        return z3;
    }
}
