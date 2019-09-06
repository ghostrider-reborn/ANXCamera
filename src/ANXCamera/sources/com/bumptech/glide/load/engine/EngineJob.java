package com.bumptech.glide.load.engine;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools.Pool;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.b.b;
import com.bumptech.glide.request.g;
import com.bumptech.glide.util.a.d.c;
import com.bumptech.glide.util.l;
import java.util.ArrayList;
import java.util.List;

class EngineJob<R> implements a<R>, c {
    private static final Handler Gf = new Handler(Looper.getMainLooper(), new a());
    private static final int Hf = 1;
    private static final int If = 2;
    private static final int Jf = 3;
    private static final EngineResourceFactory Sd = new EngineResourceFactory();
    private boolean Af;
    private boolean Bf;
    private boolean Cf;
    private List<g> Df;
    private u<?> Ef;
    private DecodeJob<R> Ff;
    private final b Mb;
    private final b Nb;
    private final b Rb;
    private final com.bumptech.glide.util.a.g Re;
    private volatile boolean Vd;
    private boolean Ye;
    private DataSource dataSource;
    private GlideException exception;
    private com.bumptech.glide.load.c key;
    private final b lf;
    private final r listener;
    private final Pool<EngineJob<?>> pool;
    private A<?> resource;
    private final List<g> wf;
    private final EngineResourceFactory xf;
    private boolean yf;
    private boolean zf;

    @VisibleForTesting
    static class EngineResourceFactory {
        EngineResourceFactory() {
        }

        public <R> u<R> a(A<R> a2, boolean z) {
            return new u<>(a2, z, true);
        }
    }

    private static class a implements Callback {
        a() {
        }

        public boolean handleMessage(Message message) {
            EngineJob engineJob = (EngineJob) message.obj;
            int i = message.what;
            if (i == 1) {
                engineJob.rg();
            } else if (i == 2) {
                engineJob.qg();
            } else if (i == 3) {
                engineJob.pg();
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Unrecognized message: ");
                sb.append(message.what);
                throw new IllegalStateException(sb.toString());
            }
            return true;
        }
    }

    EngineJob(b bVar, b bVar2, b bVar3, b bVar4, r rVar, Pool<EngineJob<?>> pool2) {
        this(bVar, bVar2, bVar3, bVar4, rVar, pool2, Sd);
    }

    @VisibleForTesting
    EngineJob(b bVar, b bVar2, b bVar3, b bVar4, r rVar, Pool<EngineJob<?>> pool2, EngineResourceFactory engineResourceFactory) {
        this.wf = new ArrayList(2);
        this.Re = com.bumptech.glide.util.a.g.newInstance();
        this.Nb = bVar;
        this.Mb = bVar2;
        this.lf = bVar3;
        this.Rb = bVar4;
        this.listener = rVar;
        this.pool = pool2;
        this.xf = engineResourceFactory;
    }

    private void c(g gVar) {
        if (this.Df == null) {
            this.Df = new ArrayList(2);
        }
        if (!this.Df.contains(gVar)) {
            this.Df.add(gVar);
        }
    }

    private boolean d(g gVar) {
        List<g> list = this.Df;
        return list != null && list.contains(gVar);
    }

    private b mk() {
        return this.zf ? this.lf : this.Af ? this.Rb : this.Mb;
    }

    private void release(boolean z) {
        l.Ih();
        this.wf.clear();
        this.key = null;
        this.Ef = null;
        this.resource = null;
        List<g> list = this.Df;
        if (list != null) {
            list.clear();
        }
        this.Cf = false;
        this.Vd = false;
        this.Bf = false;
        this.Ff.release(z);
        this.Ff = null;
        this.exception = null;
        this.dataSource = null;
        this.pool.release(this);
    }

    public void a(A<R> a2, DataSource dataSource2) {
        this.resource = a2;
        this.dataSource = dataSource2;
        Gf.obtainMessage(1, this).sendToTarget();
    }

    public void a(DecodeJob<?> decodeJob) {
        mk().execute(decodeJob);
    }

    public void a(GlideException glideException) {
        this.exception = glideException;
        Gf.obtainMessage(2, this).sendToTarget();
    }

    /* access modifiers changed from: 0000 */
    public void a(g gVar) {
        l.Ih();
        this.Re.Mh();
        if (this.Bf) {
            gVar.a(this.Ef, this.dataSource);
        } else if (this.Cf) {
            gVar.a(this.exception);
        } else {
            this.wf.add(gVar);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(g gVar) {
        l.Ih();
        this.Re.Mh();
        if (this.Bf || this.Cf) {
            c(gVar);
            return;
        }
        this.wf.remove(gVar);
        if (this.wf.isEmpty()) {
            cancel();
        }
    }

    public void c(DecodeJob<R> decodeJob) {
        this.Ff = decodeJob;
        (decodeJob.mg() ? this.Nb : mk()).execute(decodeJob);
    }

    /* access modifiers changed from: 0000 */
    public void cancel() {
        if (!this.Cf && !this.Bf && !this.Vd) {
            this.Vd = true;
            this.Ff.cancel();
            this.listener.a(this, this.key);
        }
    }

    @NonNull
    public com.bumptech.glide.util.a.g getVerifier() {
        return this.Re;
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public EngineJob<R> init(com.bumptech.glide.load.c cVar, boolean z, boolean z2, boolean z3, boolean z4) {
        this.key = cVar;
        this.yf = z;
        this.zf = z2;
        this.Af = z3;
        this.Ye = z4;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public boolean isCancelled() {
        return this.Vd;
    }

    /* access modifiers changed from: 0000 */
    public void pg() {
        this.Re.Mh();
        if (this.Vd) {
            this.listener.a(this, this.key);
            release(false);
            return;
        }
        throw new IllegalStateException("Not cancelled");
    }

    /* access modifiers changed from: 0000 */
    public void qg() {
        this.Re.Mh();
        if (this.Vd) {
            release(false);
        } else if (this.wf.isEmpty()) {
            throw new IllegalStateException("Received an exception without any callbacks to notify");
        } else if (!this.Cf) {
            this.Cf = true;
            this.listener.a(this, this.key, null);
            for (g gVar : this.wf) {
                if (!d(gVar)) {
                    gVar.a(this.exception);
                }
            }
            release(false);
        } else {
            throw new IllegalStateException("Already failed once");
        }
    }

    /* access modifiers changed from: 0000 */
    public void rg() {
        this.Re.Mh();
        if (this.Vd) {
            this.resource.recycle();
            release(false);
        } else if (this.wf.isEmpty()) {
            throw new IllegalStateException("Received a resource without any callbacks to notify");
        } else if (!this.Bf) {
            this.Ef = this.xf.a(this.resource, this.yf);
            this.Bf = true;
            this.Ef.acquire();
            this.listener.a(this, this.key, this.Ef);
            int size = this.wf.size();
            for (int i = 0; i < size; i++) {
                g gVar = (g) this.wf.get(i);
                if (!d(gVar)) {
                    this.Ef.acquire();
                    gVar.a(this.Ef, this.dataSource);
                }
            }
            this.Ef.release();
            release(false);
        } else {
            throw new IllegalStateException("Already have resource");
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean sg() {
        return this.Ye;
    }
}
