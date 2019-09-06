package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools.Pool;
import com.bumptech.glide.util.a.d;
import com.bumptech.glide.util.a.d.c;
import com.bumptech.glide.util.a.g;
import com.bumptech.glide.util.i;

/* compiled from: LockedResource */
final class z<Z> implements A<Z>, c {
    private static final Pool<z<?>> Rf = d.b(20, new y());
    private A<Z> Qf;
    private final g Re = g.newInstance();
    private boolean isLocked;
    private boolean za;

    z() {
    }

    @NonNull
    static <Z> z<Z> f(A<Z> a2) {
        z<Z> zVar = (z) Rf.acquire();
        i.checkNotNull(zVar);
        z<Z> zVar2 = zVar;
        zVar2.i(a2);
        return zVar2;
    }

    private void i(A<Z> a2) {
        this.za = false;
        this.isLocked = true;
        this.Qf = a2;
    }

    private void release() {
        this.Qf = null;
        Rf.release(this);
    }

    @NonNull
    public Z get() {
        return this.Qf.get();
    }

    public int getSize() {
        return this.Qf.getSize();
    }

    @NonNull
    public g getVerifier() {
        return this.Re;
    }

    public synchronized void recycle() {
        this.Re.Mh();
        this.za = true;
        if (!this.isLocked) {
            this.Qf.recycle();
            release();
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized void unlock() {
        this.Re.Mh();
        if (this.isLocked) {
            this.isLocked = false;
            if (this.za) {
                recycle();
            }
        } else {
            throw new IllegalStateException("Already unlocked");
        }
    }

    @NonNull
    public Class<Z> z() {
        return this.Qf.z();
    }
}
