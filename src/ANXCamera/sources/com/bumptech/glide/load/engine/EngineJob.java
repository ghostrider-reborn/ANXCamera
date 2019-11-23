package com.bumptech.glide.load.engine;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DecodeJob;
import com.bumptech.glide.request.g;
import com.bumptech.glide.util.a.a;
import com.bumptech.glide.util.a.c;
import com.bumptech.glide.util.k;
import java.util.ArrayList;
import java.util.List;

class EngineJob<R> implements DecodeJob.a<R>, a.c {
    private static final EngineResourceFactory hs = new EngineResourceFactory();
    private static final Handler ht = new Handler(Looper.getMainLooper(), new a());
    private static final int hu = 1;
    private static final int hv = 2;
    private static final int hw = 3;
    private final com.bumptech.glide.load.engine.b.a bD;
    private final com.bumptech.glide.load.engine.b.a bx;
    private final com.bumptech.glide.load.engine.b.a by;
    private DataSource dataSource;
    private boolean fE;
    private p<?> fF;
    private volatile boolean fc;
    private final c ge;
    private final Pools.Pool<EngineJob<?>> gf;
    private boolean gn;
    private boolean hA;
    private boolean hB;
    private GlideException hC;
    private boolean hD;
    private List<g> hE;
    private k<?> hF;
    private DecodeJob<R> hG;
    private final com.bumptech.glide.load.engine.b.a hl;
    private final h hm;
    private final List<g> hx;
    private final EngineResourceFactory hy;
    private boolean hz;
    private com.bumptech.glide.load.c key;

    @VisibleForTesting
    static class EngineResourceFactory {
        EngineResourceFactory() {
        }

        public <R> k<R> a(p<R> pVar, boolean z) {
            return new k<>(pVar, z, true);
        }
    }

    private static class a implements Handler.Callback {
        a() {
        }

        public boolean handleMessage(Message message) {
            EngineJob engineJob = (EngineJob) message.obj;
            switch (message.what) {
                case 1:
                    engineJob.bC();
                    return true;
                case 2:
                    engineJob.bE();
                    return true;
                case 3:
                    engineJob.bD();
                    return true;
                default:
                    throw new IllegalStateException("Unrecognized message: " + message.what);
            }
        }
    }

    EngineJob(com.bumptech.glide.load.engine.b.a aVar, com.bumptech.glide.load.engine.b.a aVar2, com.bumptech.glide.load.engine.b.a aVar3, com.bumptech.glide.load.engine.b.a aVar4, h hVar, Pools.Pool<EngineJob<?>> pool) {
        this(aVar, aVar2, aVar3, aVar4, hVar, pool, hs);
    }

    @VisibleForTesting
    EngineJob(com.bumptech.glide.load.engine.b.a aVar, com.bumptech.glide.load.engine.b.a aVar2, com.bumptech.glide.load.engine.b.a aVar3, com.bumptech.glide.load.engine.b.a aVar4, h hVar, Pools.Pool<EngineJob<?>> pool, EngineResourceFactory engineResourceFactory) {
        this.hx = new ArrayList(2);
        this.ge = c.fv();
        this.by = aVar;
        this.bx = aVar2;
        this.hl = aVar3;
        this.bD = aVar4;
        this.hm = hVar;
        this.gf = pool;
        this.hy = engineResourceFactory;
    }

    private com.bumptech.glide.load.engine.b.a bB() {
        return this.hz ? this.hl : this.hA ? this.bD : this.bx;
    }

    private void c(g gVar) {
        if (this.hE == null) {
            this.hE = new ArrayList(2);
        }
        if (!this.hE.contains(gVar)) {
            this.hE.add(gVar);
        }
    }

    private boolean d(g gVar) {
        return this.hE != null && this.hE.contains(gVar);
    }

    private void release(boolean z) {
        k.fo();
        this.hx.clear();
        this.key = null;
        this.hF = null;
        this.fF = null;
        if (this.hE != null) {
            this.hE.clear();
        }
        this.hD = false;
        this.fc = false;
        this.hB = false;
        this.hG.release(z);
        this.hG = null;
        this.hC = null;
        this.dataSource = null;
        this.gf.release(this);
    }

    public void a(GlideException glideException) {
        this.hC = glideException;
        ht.obtainMessage(2, this).sendToTarget();
    }

    /* access modifiers changed from: package-private */
    public void a(g gVar) {
        k.fo();
        this.ge.fw();
        if (this.hB) {
            gVar.c(this.hF, this.dataSource);
        } else if (this.hD) {
            gVar.a(this.hC);
        } else {
            this.hx.add(gVar);
        }
    }

    public void b(DecodeJob<?> decodeJob) {
        bB().execute(decodeJob);
    }

    /* access modifiers changed from: package-private */
    public void b(g gVar) {
        k.fo();
        this.ge.fw();
        if (this.hB || this.hD) {
            c(gVar);
            return;
        }
        this.hx.remove(gVar);
        if (this.hx.isEmpty()) {
            cancel();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean bA() {
        return this.gn;
    }

    /* access modifiers changed from: package-private */
    public void bC() {
        this.ge.fw();
        if (this.fc) {
            this.fF.recycle();
            release(false);
        } else if (this.hx.isEmpty()) {
            throw new IllegalStateException("Received a resource without any callbacks to notify");
        } else if (!this.hB) {
            this.hF = this.hy.a(this.fF, this.fE);
            this.hB = true;
            this.hF.acquire();
            this.hm.a(this, this.key, this.hF);
            int size = this.hx.size();
            for (int i = 0; i < size; i++) {
                g gVar = this.hx.get(i);
                if (!d(gVar)) {
                    this.hF.acquire();
                    gVar.c(this.hF, this.dataSource);
                }
            }
            this.hF.release();
            release(false);
        } else {
            throw new IllegalStateException("Already have resource");
        }
    }

    /* access modifiers changed from: package-private */
    public void bD() {
        this.ge.fw();
        if (this.fc) {
            this.hm.a(this, this.key);
            release(false);
            return;
        }
        throw new IllegalStateException("Not cancelled");
    }

    /* access modifiers changed from: package-private */
    public void bE() {
        this.ge.fw();
        if (this.fc) {
            release(false);
        } else if (this.hx.isEmpty()) {
            throw new IllegalStateException("Received an exception without any callbacks to notify");
        } else if (!this.hD) {
            this.hD = true;
            this.hm.a(this, this.key, (k<?>) null);
            for (g next : this.hx) {
                if (!d(next)) {
                    next.a(this.hC);
                }
            }
            release(false);
        } else {
            throw new IllegalStateException("Already failed once");
        }
    }

    @NonNull
    public c br() {
        return this.ge;
    }

    public void c(DecodeJob<R> decodeJob) {
        this.hG = decodeJob;
        (decodeJob.bi() ? this.by : bB()).execute(decodeJob);
    }

    public void c(p<R> pVar, DataSource dataSource2) {
        this.fF = pVar;
        this.dataSource = dataSource2;
        ht.obtainMessage(1, this).sendToTarget();
    }

    /* access modifiers changed from: package-private */
    public void cancel() {
        if (!this.hD && !this.hB && !this.fc) {
            this.fc = true;
            this.hG.cancel();
            this.hm.a(this, this.key);
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public EngineJob<R> init(com.bumptech.glide.load.c cVar, boolean z, boolean z2, boolean z3, boolean z4) {
        this.key = cVar;
        this.fE = z;
        this.hz = z2;
        this.hA = z3;
        this.gn = z4;
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean isCancelled() {
        return this.fc;
    }
}
