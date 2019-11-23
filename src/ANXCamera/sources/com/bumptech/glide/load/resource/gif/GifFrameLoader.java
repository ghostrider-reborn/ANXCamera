package com.bumptech.glide.load.resource.gif;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.c;
import com.bumptech.glide.h;
import com.bumptech.glide.i;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.engine.g;
import com.bumptech.glide.request.a.f;
import com.bumptech.glide.request.target.l;
import com.bumptech.glide.request.target.n;
import com.bumptech.glide.util.k;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

class GifFrameLoader {
    final i bJ;
    private final d bm;
    private final List<a> callbacks;
    private com.bumptech.glide.load.i<Bitmap> hX;
    private final Handler handler;
    private boolean isRunning;
    private DelayTarget nA;
    private Bitmap nB;
    private DelayTarget nC;
    @Nullable
    private OnEveryFrameListener nD;
    private final com.bumptech.glide.b.a nu;
    private boolean nv;
    private boolean nw;
    private h<Bitmap> nx;
    private DelayTarget ny;
    private boolean nz;

    @VisibleForTesting
    static class DelayTarget extends l<Bitmap> {
        private final Handler handler;
        final int index;
        private final long nE;
        private Bitmap nF;

        DelayTarget(Handler handler2, int i, long j) {
            this.handler = handler2;
            this.index = i;
            this.nE = j;
        }

        public void a(@NonNull Bitmap bitmap, @Nullable f<? super Bitmap> fVar) {
            this.nF = bitmap;
            this.handler.sendMessageAtTime(this.handler.obtainMessage(1, this), this.nE);
        }

        /* access modifiers changed from: package-private */
        public Bitmap dx() {
            return this.nF;
        }
    }

    @VisibleForTesting
    interface OnEveryFrameListener {
        void dr();
    }

    public interface a {
        void dr();
    }

    private class b implements Handler.Callback {
        static final int nG = 1;
        static final int nH = 2;

        b() {
        }

        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                GifFrameLoader.this.onFrameReady((DelayTarget) message.obj);
                return true;
            } else if (message.what != 2) {
                return false;
            } else {
                GifFrameLoader.this.bJ.d((n<?>) (DelayTarget) message.obj);
                return false;
            }
        }
    }

    GifFrameLoader(c cVar, com.bumptech.glide.b.a aVar, int i, int i2, com.bumptech.glide.load.i<Bitmap> iVar, Bitmap bitmap) {
        this(cVar.L(), c.g(cVar.getContext()), aVar, (Handler) null, a(c.g(cVar.getContext()), i, i2), iVar, bitmap);
    }

    GifFrameLoader(d dVar, i iVar, com.bumptech.glide.b.a aVar, Handler handler2, h<Bitmap> hVar, com.bumptech.glide.load.i<Bitmap> iVar2, Bitmap bitmap) {
        this.callbacks = new ArrayList();
        this.bJ = iVar;
        handler2 = handler2 == null ? new Handler(Looper.getMainLooper(), new b()) : handler2;
        this.bm = dVar;
        this.handler = handler2;
        this.nx = hVar;
        this.nu = aVar;
        a(iVar2, bitmap);
    }

    private static h<Bitmap> a(i iVar, int i, int i2) {
        return iVar.ai().b(com.bumptech.glide.request.f.a(g.gV).k(true).m(true).p(i, i2));
    }

    private void dt() {
        if (this.isRunning && !this.nv) {
            if (this.nw) {
                com.bumptech.glide.util.i.a(this.nC == null, "Pending target must be null when starting from the first frame");
                this.nu.ax();
                this.nw = false;
            }
            if (this.nC != null) {
                DelayTarget delayTarget = this.nC;
                this.nC = null;
                onFrameReady(delayTarget);
                return;
            }
            this.nv = true;
            long uptimeMillis = SystemClock.uptimeMillis() + ((long) this.nu.av());
            this.nu.advance();
            this.nA = new DelayTarget(this.handler, this.nu.aw(), uptimeMillis);
            this.nx.b(com.bumptech.glide.request.f.j(dw())).load(this.nu).b(this.nA);
        }
    }

    private void du() {
        if (this.nB != null) {
            this.bm.d(this.nB);
            this.nB = null;
        }
    }

    private static com.bumptech.glide.load.c dw() {
        return new com.bumptech.glide.e.d(Double.valueOf(Math.random()));
    }

    private int getFrameSize() {
        return k.i(ds().getWidth(), ds().getHeight(), ds().getConfig());
    }

    private void start() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.nz = false;
            dt();
        }
    }

    private void stop() {
        this.isRunning = false;
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [java.lang.Object, com.bumptech.glide.load.i<android.graphics.Bitmap>, com.bumptech.glide.load.i] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Unknown variable types count: 1 */
    public void a(com.bumptech.glide.load.i<Bitmap> r2, Bitmap bitmap) {
        this.hX = (com.bumptech.glide.load.i) com.bumptech.glide.util.i.checkNotNull(r2);
        this.nB = (Bitmap) com.bumptech.glide.util.i.checkNotNull(bitmap);
        this.nx = this.nx.b(new com.bumptech.glide.request.f().b((com.bumptech.glide.load.i<Bitmap>) r2));
    }

    /* access modifiers changed from: package-private */
    public void a(a aVar) {
        if (this.nz) {
            throw new IllegalStateException("Cannot subscribe to a cleared frame loader");
        } else if (!this.callbacks.contains(aVar)) {
            boolean isEmpty = this.callbacks.isEmpty();
            this.callbacks.add(aVar);
            if (isEmpty) {
                start();
            }
        } else {
            throw new IllegalStateException("Cannot subscribe twice in a row");
        }
    }

    /* access modifiers changed from: package-private */
    public void b(a aVar) {
        this.callbacks.remove(aVar);
        if (this.callbacks.isEmpty()) {
            stop();
        }
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.callbacks.clear();
        du();
        stop();
        if (this.ny != null) {
            this.bJ.d((n<?>) this.ny);
            this.ny = null;
        }
        if (this.nA != null) {
            this.bJ.d((n<?>) this.nA);
            this.nA = null;
        }
        if (this.nC != null) {
            this.bJ.d((n<?>) this.nC);
            this.nC = null;
        }
        this.nu.clear();
        this.nz = true;
    }

    /* access modifiers changed from: package-private */
    public Bitmap dj() {
        return this.nB;
    }

    /* access modifiers changed from: package-private */
    public com.bumptech.glide.load.i<Bitmap> dk() {
        return this.hX;
    }

    /* access modifiers changed from: package-private */
    public Bitmap ds() {
        return this.ny != null ? this.ny.dx() : this.nB;
    }

    /* access modifiers changed from: package-private */
    public void dv() {
        com.bumptech.glide.util.i.a(!this.isRunning, "Can't restart a running animation");
        this.nw = true;
        if (this.nC != null) {
            this.bJ.d((n<?>) this.nC);
            this.nC = null;
        }
    }

    /* access modifiers changed from: package-private */
    public ByteBuffer getBuffer() {
        return this.nu.getData().asReadOnlyBuffer();
    }

    /* access modifiers changed from: package-private */
    public int getCurrentIndex() {
        if (this.ny != null) {
            return this.ny.index;
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public int getFrameCount() {
        return this.nu.getFrameCount();
    }

    /* access modifiers changed from: package-private */
    public int getHeight() {
        return ds().getHeight();
    }

    /* access modifiers changed from: package-private */
    public int getLoopCount() {
        return this.nu.az();
    }

    /* access modifiers changed from: package-private */
    public int getSize() {
        return this.nu.aA() + getFrameSize();
    }

    /* access modifiers changed from: package-private */
    public int getWidth() {
        return ds().getWidth();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void onFrameReady(DelayTarget delayTarget) {
        if (this.nD != null) {
            this.nD.dr();
        }
        this.nv = false;
        if (this.nz) {
            this.handler.obtainMessage(2, delayTarget).sendToTarget();
        } else if (!this.isRunning) {
            this.nC = delayTarget;
        } else {
            if (delayTarget.dx() != null) {
                du();
                DelayTarget delayTarget2 = this.ny;
                this.ny = delayTarget;
                for (int size = this.callbacks.size() - 1; size >= 0; size--) {
                    this.callbacks.get(size).dr();
                }
                if (delayTarget2 != null) {
                    this.handler.obtainMessage(2, delayTarget2).sendToTarget();
                }
            }
            dt();
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setOnEveryFrameReadyListener(@Nullable OnEveryFrameListener onEveryFrameListener) {
        this.nD = onEveryFrameListener;
    }
}
