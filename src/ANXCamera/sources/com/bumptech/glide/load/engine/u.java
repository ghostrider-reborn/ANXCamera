package com.bumptech.glide.load.engine;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.c;
import com.bumptech.glide.util.i;

/* compiled from: EngineResource */
class u<Z> implements A<Z> {
    private final boolean Kf;
    private int Lf;
    private c key;
    private a listener;
    private final A<Z> resource;
    private final boolean yf;
    private boolean za;

    /* compiled from: EngineResource */
    interface a {
        void a(c cVar, u<?> uVar);
    }

    u(A<Z> a2, boolean z, boolean z2) {
        i.checkNotNull(a2);
        this.resource = a2;
        this.yf = z;
        this.Kf = z2;
    }

    /* access modifiers changed from: package-private */
    public void a(c cVar, a aVar) {
        this.key = cVar;
        this.listener = aVar;
    }

    /* access modifiers changed from: package-private */
    public void acquire() {
        if (this.za) {
            throw new IllegalStateException("Cannot acquire a recycled resource");
        } else if (Looper.getMainLooper().equals(Looper.myLooper())) {
            this.Lf++;
        } else {
            throw new IllegalThreadStateException("Must call acquire on the main thread");
        }
    }

    @NonNull
    public Z get() {
        return this.resource.get();
    }

    public int getSize() {
        return this.resource.getSize();
    }

    public void recycle() {
        if (this.Lf > 0) {
            throw new IllegalStateException("Cannot recycle a resource while it is still acquired");
        } else if (!this.za) {
            this.za = true;
            if (this.Kf) {
                this.resource.recycle();
            }
        } else {
            throw new IllegalStateException("Cannot recycle a resource that has already been recycled");
        }
    }

    /* access modifiers changed from: package-private */
    public void release() {
        if (this.Lf <= 0) {
            throw new IllegalStateException("Cannot release a recycled or not yet acquired resource");
        } else if (Looper.getMainLooper().equals(Looper.myLooper())) {
            int i = this.Lf - 1;
            this.Lf = i;
            if (i == 0) {
                this.listener.a(this.key, this);
            }
        } else {
            throw new IllegalThreadStateException("Must call release on the main thread");
        }
    }

    /* access modifiers changed from: package-private */
    public A<Z> tg() {
        return this.resource;
    }

    public String toString() {
        return "EngineResource{isCacheable=" + this.yf + ", listener=" + this.listener + ", key=" + this.key + ", acquired=" + this.Lf + ", isRecycled=" + this.za + ", resource=" + this.resource + '}';
    }

    /* access modifiers changed from: package-private */
    public boolean ug() {
        return this.yf;
    }

    @NonNull
    public Class<Z> z() {
        return this.resource.z();
    }
}
