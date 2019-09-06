package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
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
            int length = mj.list().length;
            if (length < 700) {
                z = true;
            }
            this.kj = z;
            if (!this.kj && Log.isLoggable("Downsampler", 5)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Excluding HARDWARE bitmap config because we're over the file descriptor limit, file descriptors ");
                sb.append(length);
                sb.append(", limit ");
                sb.append(700);
                Log.w("Downsampler", sb.toString());
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

    /* access modifiers changed from: 0000 */
    @TargetApi(26)
    public boolean a(int i, int i2, Options options, DecodeFormat decodeFormat, boolean z, boolean z2) {
        if (!z || VERSION.SDK_INT < 26 || decodeFormat == DecodeFormat.PREFER_ARGB_8888_DISALLOW_HARDWARE || z2) {
            return false;
        }
        boolean z3 = i >= 128 && i2 >= 128 && Hk();
        if (z3) {
            options.inPreferredConfig = Config.HARDWARE;
            options.inMutable = false;
        }
        return z3;
    }
}
