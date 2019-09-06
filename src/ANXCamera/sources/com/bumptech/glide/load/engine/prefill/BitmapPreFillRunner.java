package com.bumptech.glide.load.engine.prefill;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.a.o;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.resource.bitmap.f;
import com.bumptech.glide.util.l;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

final class BitmapPreFillRunner implements Runnable {
    static final long Ah = TimeUnit.SECONDS.toMillis(1);
    static final long INITIAL_BACKOFF_MS = 40;
    static final long MAX_DURATION_MS = 32;
    @VisibleForTesting
    static final String TAG = "PreFillRunner";
    private static final Clock yh = new Clock();
    static final int zh = 4;
    private final d Bb;
    private final o Cb;
    private boolean Vd;
    private final Handler handler;
    private final b uh;
    private final Clock vh;
    private final Set<c> wh;
    private long xh;

    @VisibleForTesting
    static class Clock {
        Clock() {
        }

        /* access modifiers changed from: 0000 */
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

    public BitmapPreFillRunner(d dVar, o oVar, b bVar) {
        this(dVar, oVar, bVar, yh, new Handler(Looper.getMainLooper()));
    }

    @VisibleForTesting
    BitmapPreFillRunner(d dVar, o oVar, b bVar, Clock clock, Handler handler2) {
        this.wh = new HashSet();
        this.xh = INITIAL_BACKOFF_MS;
        this.Bb = dVar;
        this.Cb = oVar;
        this.uh = bVar;
        this.vh = clock;
        this.handler = handler2;
    }

    private long I() {
        long j = this.xh;
        this.xh = Math.min(4 * j, Ah);
        return j;
    }

    private boolean i(long j) {
        return this.vh.now() - j >= 32;
    }

    private long xk() {
        return this.Cb.getMaxSize() - this.Cb.J();
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public boolean allocate() {
        Bitmap bitmap;
        long now = this.vh.now();
        while (!this.uh.isEmpty() && !i(now)) {
            c remove = this.uh.remove();
            if (!this.wh.contains(remove)) {
                this.wh.add(remove);
                bitmap = this.Bb.c(remove.getWidth(), remove.getHeight(), remove.getConfig());
            } else {
                bitmap = Bitmap.createBitmap(remove.getWidth(), remove.getHeight(), remove.getConfig());
            }
            int j = l.j(bitmap);
            if (xk() >= ((long) j)) {
                this.Cb.a(new a(), f.a(bitmap, this.Bb));
            } else {
                this.Bb.a(bitmap);
            }
            String str = TAG;
            if (Log.isLoggable(str, 3)) {
                StringBuilder sb = new StringBuilder();
                sb.append("allocated [");
                sb.append(remove.getWidth());
                sb.append("x");
                sb.append(remove.getHeight());
                sb.append("] ");
                sb.append(remove.getConfig());
                sb.append(" size: ");
                sb.append(j);
                Log.d(str, sb.toString());
            }
        }
        return !this.Vd && !this.uh.isEmpty();
    }

    public void cancel() {
        this.Vd = true;
    }

    public void run() {
        if (allocate()) {
            this.handler.postDelayed(this, I());
        }
    }
}
