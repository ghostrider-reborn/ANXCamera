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
import com.bumptech.glide.j;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.m;
import com.bumptech.glide.request.a.f;
import com.bumptech.glide.request.target.o;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.l;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

class GifFrameLoader {
    private final d Bb;
    private final com.bumptech.glide.b.a Pj;
    private boolean Qj;
    private boolean Rj;
    private j<Bitmap> Sj;
    private boolean Tj;
    private DelayTarget Uj;
    private com.bumptech.glide.load.j<Bitmap> Vf;
    @Nullable
    private OnEveryFrameListener Vj;
    private final List<a> callbacks;
    private DelayTarget current;
    final m da;
    private Bitmap firstFrame;
    private final Handler handler;
    private boolean isRunning;
    private DelayTarget next;

    @VisibleForTesting
    static class DelayTarget extends com.bumptech.glide.request.target.m<Bitmap> {
        private final Handler handler;
        final int index;
        private Bitmap resource;
        private final long xl;

        DelayTarget(Handler handler2, int i, long j) {
            this.handler = handler2;
            this.index = i;
            this.xl = j;
        }

        public void a(@NonNull Bitmap bitmap, @Nullable f<? super Bitmap> fVar) {
            this.resource = bitmap;
            this.handler.sendMessageAtTime(this.handler.obtainMessage(1, this), this.xl);
        }

        /* access modifiers changed from: package-private */
        public Bitmap tg() {
            return this.resource;
        }
    }

    @VisibleForTesting
    interface OnEveryFrameListener {
        void m();
    }

    public interface a {
        void m();
    }

    private class b implements Handler.Callback {
        static final int Nj = 1;
        static final int Oj = 2;

        b() {
        }

        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                GifFrameLoader.this.onFrameReady((DelayTarget) message.obj);
                return true;
            } else if (i != 2) {
                return false;
            } else {
                GifFrameLoader.this.da.d((o<?>) (DelayTarget) message.obj);
                return false;
            }
        }
    }

    GifFrameLoader(c cVar, com.bumptech.glide.b.a aVar, int i, int i2, com.bumptech.glide.load.j<Bitmap> jVar, Bitmap bitmap) {
        this(cVar.Cf(), c.i(cVar.getContext()), aVar, (Handler) null, b(c.i(cVar.getContext()), i, i2), jVar, bitmap);
    }

    GifFrameLoader(d dVar, m mVar, com.bumptech.glide.b.a aVar, Handler handler2, j<Bitmap> jVar, com.bumptech.glide.load.j<Bitmap> jVar2, Bitmap bitmap) {
        this.callbacks = new ArrayList();
        this.da = mVar;
        handler2 = handler2 == null ? new Handler(Looper.getMainLooper(), new b()) : handler2;
        this.Bb = dVar;
        this.handler = handler2;
        this.Sj = jVar;
        this.Pj = aVar;
        a(jVar2, bitmap);
    }

    private static com.bumptech.glide.load.c Ik() {
        return new com.bumptech.glide.e.d(Double.valueOf(Math.random()));
    }

    private void Jk() {
        if (this.isRunning && !this.Qj) {
            if (this.Rj) {
                i.a(this.Uj == null, "Pending target must be null when starting from the first frame");
                this.Pj.s();
                this.Rj = false;
            }
            DelayTarget delayTarget = this.Uj;
            if (delayTarget != null) {
                this.Uj = null;
                onFrameReady(delayTarget);
                return;
            }
            this.Qj = true;
            long uptimeMillis = SystemClock.uptimeMillis() + ((long) this.Pj.I());
            this.Pj.advance();
            this.next = new DelayTarget(this.handler, this.Pj.v(), uptimeMillis);
            this.Sj.b(com.bumptech.glide.request.f.h(Ik())).load((Object) this.Pj).c(this.next);
        }
    }

    private void Kk() {
        Bitmap bitmap = this.firstFrame;
        if (bitmap != null) {
            this.Bb.a(bitmap);
            this.firstFrame = null;
        }
    }

    private static j<Bitmap> b(m mVar, int i, int i2) {
        return mVar.Kf().b(com.bumptech.glide.request.f.b(com.bumptech.glide.load.engine.o.NONE).s(true).q(true).l(i, i2));
    }

    private int getFrameSize() {
        return l.g(Gg().getWidth(), Gg().getHeight(), Gg().getConfig());
    }

    private void start() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.Tj = false;
            Jk();
        }
    }

    private void stop() {
        this.isRunning = false;
    }

    /* access modifiers changed from: package-private */
    public Bitmap Gg() {
        DelayTarget delayTarget = this.current;
        return delayTarget != null ? delayTarget.tg() : this.firstFrame;
    }

    /* access modifiers changed from: package-private */
    public void Hg() {
        i.a(!this.isRunning, "Can't restart a running animation");
        this.Rj = true;
        DelayTarget delayTarget = this.Uj;
        if (delayTarget != null) {
            this.da.d((o<?>) delayTarget);
            this.Uj = null;
        }
    }

    /* access modifiers changed from: package-private */
    public Bitmap Z() {
        return this.firstFrame;
    }

    /* access modifiers changed from: package-private */
    public void a(com.bumptech.glide.load.j<Bitmap> jVar, Bitmap bitmap) {
        i.checkNotNull(jVar);
        this.Vf = jVar;
        i.checkNotNull(bitmap);
        this.firstFrame = bitmap;
        this.Sj = this.Sj.b(new com.bumptech.glide.request.f().c(jVar));
    }

    /* access modifiers changed from: package-private */
    public void a(a aVar) {
        if (this.Tj) {
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
    public com.bumptech.glide.load.j<Bitmap> ba() {
        return this.Vf;
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.callbacks.clear();
        Kk();
        stop();
        DelayTarget delayTarget = this.current;
        if (delayTarget != null) {
            this.da.d((o<?>) delayTarget);
            this.current = null;
        }
        DelayTarget delayTarget2 = this.next;
        if (delayTarget2 != null) {
            this.da.d((o<?>) delayTarget2);
            this.next = null;
        }
        DelayTarget delayTarget3 = this.Uj;
        if (delayTarget3 != null) {
            this.da.d((o<?>) delayTarget3);
            this.Uj = null;
        }
        this.Pj.clear();
        this.Tj = true;
    }

    /* access modifiers changed from: package-private */
    public ByteBuffer getBuffer() {
        return this.Pj.getData().asReadOnlyBuffer();
    }

    /* access modifiers changed from: package-private */
    public int getCurrentIndex() {
        DelayTarget delayTarget = this.current;
        if (delayTarget != null) {
            return delayTarget.index;
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public int getFrameCount() {
        return this.Pj.getFrameCount();
    }

    /* access modifiers changed from: package-private */
    public int getHeight() {
        return Gg().getHeight();
    }

    /* access modifiers changed from: package-private */
    public int getLoopCount() {
        return this.Pj.F();
    }

    /* access modifiers changed from: package-private */
    public int getSize() {
        return this.Pj.x() + getFrameSize();
    }

    /* access modifiers changed from: package-private */
    public int getWidth() {
        return Gg().getWidth();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void onFrameReady(DelayTarget delayTarget) {
        OnEveryFrameListener onEveryFrameListener = this.Vj;
        if (onEveryFrameListener != null) {
            onEveryFrameListener.m();
        }
        this.Qj = false;
        if (this.Tj) {
            this.handler.obtainMessage(2, delayTarget).sendToTarget();
        } else if (!this.isRunning) {
            this.Uj = delayTarget;
        } else {
            if (delayTarget.tg() != null) {
                Kk();
                DelayTarget delayTarget2 = this.current;
                this.current = delayTarget;
                for (int size = this.callbacks.size() - 1; size >= 0; size--) {
                    this.callbacks.get(size).m();
                }
                if (delayTarget2 != null) {
                    this.handler.obtainMessage(2, delayTarget2).sendToTarget();
                }
            }
            Jk();
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setOnEveryFrameReadyListener(@Nullable OnEveryFrameListener onEveryFrameListener) {
        this.Vj = onEveryFrameListener;
    }
}
