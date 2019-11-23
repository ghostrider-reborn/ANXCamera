package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools;
import com.bumptech.glide.util.a.a;
import com.bumptech.glide.util.a.c;
import com.bumptech.glide.util.i;

/* compiled from: LockedResource */
final class o<Z> implements p<Z>, a.c {
    private static final Pools.Pool<o<?>> hR = a.b(20, new a.C0013a<o<?>>() {
        /* renamed from: bK */
        public o<?> create() {
            return new o<>();
        }
    });
    private final c ge = c.fv();
    private boolean hJ;
    private p<Z> hS;
    private boolean isLocked;

    o() {
    }

    @NonNull
    static <Z> o<Z> f(p<Z> pVar) {
        o<Z> oVar = (o) i.checkNotNull(hR.acquire());
        oVar.g(pVar);
        return oVar;
    }

    private void g(p<Z> pVar) {
        this.hJ = false;
        this.isLocked = true;
        this.hS = pVar;
    }

    private void release() {
        this.hS = null;
        hR.release(this);
    }

    @NonNull
    public Class<Z> bH() {
        return this.hS.bH();
    }

    @NonNull
    public c br() {
        return this.ge;
    }

    @NonNull
    public Z get() {
        return this.hS.get();
    }

    public int getSize() {
        return this.hS.getSize();
    }

    public synchronized void recycle() {
        this.ge.fw();
        this.hJ = true;
        if (!this.isLocked) {
            this.hS.recycle();
            release();
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void unlock() {
        this.ge.fw();
        if (this.isLocked) {
            this.isLocked = false;
            if (this.hJ) {
                recycle();
            }
        } else {
            throw new IllegalStateException("Already unlocked");
        }
    }
}
