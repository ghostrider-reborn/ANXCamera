package com.bumptech.glide.load.engine;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.k;
import com.bumptech.glide.util.i;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

final class ActiveResources {
    private static final int fx = 1;
    @VisibleForTesting
    final Map<c, ResourceWeakReference> activeEngineResources = new HashMap();
    private final boolean bE;
    private final Handler bF = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            ActiveResources.this.a((ResourceWeakReference) message.obj);
            return true;
        }
    });
    @Nullable
    private Thread fA;
    private volatile boolean fB;
    @Nullable
    private volatile DequeuedResourceCallback fC;
    private k.a fy;
    @Nullable
    private ReferenceQueue<k<?>> fz;

    @VisibleForTesting
    interface DequeuedResourceCallback {
        void aS();
    }

    @VisibleForTesting
    static final class ResourceWeakReference extends WeakReference<k<?>> {
        final boolean fE;
        @Nullable
        p<?> fF;
        final c key;

        ResourceWeakReference(@NonNull c cVar, @NonNull k<?> kVar, @NonNull ReferenceQueue<? super k<?>> referenceQueue, boolean z) {
            super(kVar, referenceQueue);
            this.key = (c) i.checkNotNull(cVar);
            this.fF = (!kVar.bG() || !z) ? null : (p) i.checkNotNull(kVar.bF());
            this.fE = kVar.bG();
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            this.fF = null;
            clear();
        }
    }

    ActiveResources(boolean z) {
        this.bE = z;
    }

    private ReferenceQueue<k<?>> aQ() {
        if (this.fz == null) {
            this.fz = new ReferenceQueue<>();
            this.fA = new Thread(new Runnable() {
                public void run() {
                    Process.setThreadPriority(10);
                    ActiveResources.this.aR();
                }
            }, "glide-active-resources");
            this.fA.start();
        }
        return this.fz;
    }

    /* access modifiers changed from: package-private */
    public void a(c cVar) {
        ResourceWeakReference remove = this.activeEngineResources.remove(cVar);
        if (remove != null) {
            remove.reset();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(c cVar, k<?> kVar) {
        ResourceWeakReference put = this.activeEngineResources.put(cVar, new ResourceWeakReference(cVar, kVar, aQ(), this.bE));
        if (put != null) {
            put.reset();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(@NonNull ResourceWeakReference resourceWeakReference) {
        com.bumptech.glide.util.k.fo();
        this.activeEngineResources.remove(resourceWeakReference.key);
        if (resourceWeakReference.fE && resourceWeakReference.fF != null) {
            k kVar = new k(resourceWeakReference.fF, true, false);
            kVar.a(resourceWeakReference.key, this.fy);
            this.fy.b(resourceWeakReference.key, kVar);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(k.a aVar) {
        this.fy = aVar;
    }

    /* access modifiers changed from: package-private */
    public void aR() {
        while (!this.fB) {
            try {
                this.bF.obtainMessage(1, (ResourceWeakReference) this.fz.remove()).sendToTarget();
                DequeuedResourceCallback dequeuedResourceCallback = this.fC;
                if (dequeuedResourceCallback != null) {
                    dequeuedResourceCallback.aS();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public k<?> b(c cVar) {
        ResourceWeakReference resourceWeakReference = this.activeEngineResources.get(cVar);
        if (resourceWeakReference == null) {
            return null;
        }
        k<?> kVar = (k) resourceWeakReference.get();
        if (kVar == null) {
            a(resourceWeakReference);
        }
        return kVar;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setDequeuedResourceCallback(DequeuedResourceCallback dequeuedResourceCallback) {
        this.fC = dequeuedResourceCallback;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void shutdown() {
        this.fB = true;
        if (this.fA != null) {
            this.fA.interrupt();
            try {
                this.fA.join(TimeUnit.SECONDS.toMillis(5));
                if (this.fA.isAlive()) {
                    throw new RuntimeException("Failed to join in time");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
