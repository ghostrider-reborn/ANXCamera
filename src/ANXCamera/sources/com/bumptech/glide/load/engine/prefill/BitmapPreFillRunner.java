package com.bumptech.glide.load.engine.prefill;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.a.j;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.resource.bitmap.f;
import com.bumptech.glide.util.k;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

final class BitmapPreFillRunner implements Runnable {
    static final long INITIAL_BACKOFF_MS = 40;
    static final long MAX_DURATION_MS = 32;
    @VisibleForTesting
    static final String TAG = "PreFillRunner";
    private static final Clock kd = new Clock();
    static final int ke = 4;
    static final long kf = TimeUnit.SECONDS.toMillis(1);
    private final d bm;
    private final j bn;
    private boolean fc;
    private final Handler handler;
    private final b kg;
    private final Clock kh;
    private final Set<c> ki;
    private long kj;

    @VisibleForTesting
    static class Clock {
        Clock() {
        }

        /* access modifiers changed from: package-private */
        public long now() {
            return SystemClock.currentThreadTimeMillis();
        }
    }

    private static final class a implements c {
        a() {
        }

        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            throw new UnsupportedOperationException();
        }
    }

    public BitmapPreFillRunner(d dVar, j jVar, b bVar) {
        this(dVar, jVar, bVar, kd, new Handler(Looper.getMainLooper()));
    }

    @VisibleForTesting
    BitmapPreFillRunner(d dVar, j jVar, b bVar, Clock clock, Handler handler2) {
        this.ki = new HashSet();
        this.kj = INITIAL_BACKOFF_MS;
        this.bm = dVar;
        this.bn = jVar;
        this.kg = bVar;
        this.kh = clock;
        this.handler = handler2;
    }

    private boolean c(long j) {
        return this.kh.now() - j >= 32;
    }

    private long cx() {
        return this.bn.getMaxSize() - this.bn.ci();
    }

    private long cy() {
        long j = this.kj;
        this.kj = Math.min(this.kj * 4, kf);
        return j;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean allocate() {
        Bitmap bitmap;
        long now = this.kh.now();
        while (!this.kg.isEmpty() && !c(now)) {
            c cz = this.kg.cz();
            if (!this.ki.contains(cz)) {
                this.ki.add(cz);
                bitmap = this.bm.g(cz.getWidth(), cz.getHeight(), cz.getConfig());
            } else {
                bitmap = Bitmap.createBitmap(cz.getWidth(), cz.getHeight(), cz.getConfig());
            }
            int p = k.p(bitmap);
            if (cx() >= ((long) p)) {
                this.bn.b(new a(), f.a(bitmap, this.bm));
            } else {
                this.bm.d(bitmap);
            }
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "allocated [" + cz.getWidth() + "x" + cz.getHeight() + "] " + cz.getConfig() + " size: " + p);
            }
        }
        return !this.fc && !this.kg.isEmpty();
    }

    public void cancel() {
        this.fc = true;
    }

    public void run() {
        if (allocate()) {
            this.handler.postDelayed(this, cy());
        }
    }
}
