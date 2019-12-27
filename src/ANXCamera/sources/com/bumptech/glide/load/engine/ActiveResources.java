package com.bumptech.glide.load.engine;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.u;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.l;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

final class ActiveResources {
    private static final int se = 1;
    private final boolean Sb;
    @VisibleForTesting
    final Map<c, ResourceWeakReference> activeEngineResources = new HashMap();
    @Nullable
    private volatile DequeuedResourceCallback cb;
    private u.a listener;
    private final Handler pa = new Handler(Looper.getMainLooper(), new C0095a(this));
    @Nullable
    private ReferenceQueue<u<?>> pe;
    @Nullable
    private Thread qe;
    private volatile boolean re;

    @VisibleForTesting
    interface DequeuedResourceCallback {
        void y();
    }

    @VisibleForTesting
    static final class ResourceWeakReference extends WeakReference<u<?>> {
        final c key;
        @Nullable
        A<?> resource;
        final boolean yf;

        ResourceWeakReference(@NonNull c cVar, @NonNull u<?> uVar, @NonNull ReferenceQueue<? super u<?>> referenceQueue, boolean z) {
            super(uVar, referenceQueue);
            A<?> a2;
            i.checkNotNull(cVar);
            this.key = cVar;
            if (!uVar.ug() || !z) {
                a2 = null;
            } else {
                A<?> tg = uVar.tg();
                i.checkNotNull(tg);
                a2 = tg;
            }
            this.resource = a2;
            this.yf = uVar.ug();
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            this.resource = null;
            clear();
        }
    }

    ActiveResources(boolean z) {
        this.Sb = z;
    }

    private ReferenceQueue<u<?>> dk() {
        if (this.pe == null) {
            this.pe = new ReferenceQueue<>();
            this.qe = new Thread(new C0096b(this), "glide-active-resources");
            this.qe.start();
        }
        return this.pe;
    }

    /* access modifiers changed from: package-private */
    public void a(@NonNull ResourceWeakReference resourceWeakReference) {
        l.Ih();
        this.activeEngineResources.remove(resourceWeakReference.key);
        if (resourceWeakReference.yf) {
            A<?> a2 = resourceWeakReference.resource;
            if (a2 != null) {
                u uVar = new u(a2, true, false);
                uVar.a(resourceWeakReference.key, this.listener);
                this.listener.a(resourceWeakReference.key, uVar);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(u.a aVar) {
        this.listener = aVar;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public u<?> b(c cVar) {
        ResourceWeakReference resourceWeakReference = this.activeEngineResources.get(cVar);
        if (resourceWeakReference == null) {
            return null;
        }
        u<?> uVar = (u) resourceWeakReference.get();
        if (uVar == null) {
            a(resourceWeakReference);
        }
        return uVar;
    }

    /* access modifiers changed from: package-private */
    public void b(c cVar, u<?> uVar) {
        ResourceWeakReference put = this.activeEngineResources.put(cVar, new ResourceWeakReference(cVar, uVar, dk(), this.Sb));
        if (put != null) {
            put.reset();
        }
    }

    /* access modifiers changed from: package-private */
    public void bg() {
        while (!this.re) {
            try {
                this.pa.obtainMessage(1, (ResourceWeakReference) this.pe.remove()).sendToTarget();
                DequeuedResourceCallback dequeuedResourceCallback = this.cb;
                if (dequeuedResourceCallback != null) {
                    dequeuedResourceCallback.y();
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void d(c cVar) {
        ResourceWeakReference remove = this.activeEngineResources.remove(cVar);
        if (remove != null) {
            remove.reset();
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setDequeuedResourceCallback(DequeuedResourceCallback dequeuedResourceCallback) {
        this.cb = dequeuedResourceCallback;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void shutdown() {
        this.re = true;
        Thread thread = this.qe;
        if (thread != null) {
            thread.interrupt();
            try {
                this.qe.join(TimeUnit.SECONDS.toMillis(5));
                if (this.qe.isAlive()) {
                    throw new RuntimeException("Failed to join in time");
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
