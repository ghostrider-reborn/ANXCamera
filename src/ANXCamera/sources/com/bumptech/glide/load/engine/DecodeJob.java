package com.bumptech.glide.load.engine;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.util.Pools.Pool;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.Registry.NoResultEncoderAvailableException;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.j;
import com.bumptech.glide.load.resource.bitmap.o;
import com.bumptech.glide.util.a.g;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class DecodeJob<R> implements com.bumptech.glide.load.engine.f.a, Runnable, Comparable<DecodeJob<?>>, com.bumptech.glide.util.a.d.c {
    private static final String TAG = "DecodeJob";
    private final d Ce;
    private com.bumptech.glide.e Eb;
    private o Fe;
    private final g<R> Pe = new g<>();
    private final List<Throwable> Qe = new ArrayList();
    private final g Re = g.newInstance();
    private final c<?> Se = new c<>();
    private final e Te = new e();
    private s Ue;
    private volatile boolean Vd;
    private Stage Ve;
    private RunReason We;
    private long Xe;
    private boolean Ye;
    private Thread Ze;
    private com.bumptech.glide.load.c _e;
    private com.bumptech.glide.load.c af;
    private Object bf;
    private a<R> callback;
    private DataSource cf;
    private com.bumptech.glide.load.a.d<?> df;
    private volatile f ef;
    private volatile boolean ff;
    private int height;
    private Object model;
    private com.bumptech.glide.load.g options;
    private int order;
    private final Pool<DecodeJob<?>> pool;
    private Priority priority;
    private com.bumptech.glide.load.c signature;
    private int width;

    private enum RunReason {
        INITIALIZE,
        SWITCH_TO_SOURCE_SERVICE,
        DECODE_DATA
    }

    private enum Stage {
        INITIALIZE,
        RESOURCE_CACHE,
        DATA_CACHE,
        SOURCE,
        ENCODE,
        FINISHED
    }

    interface a<R> {
        void a(A<R> a2, DataSource dataSource);

        void a(DecodeJob<?> decodeJob);

        void a(GlideException glideException);
    }

    private final class b<Z> implements a<Z> {
        private final DataSource dataSource;

        b(DataSource dataSource2) {
            this.dataSource = dataSource2;
        }

        @NonNull
        public A<Z> a(@NonNull A<Z> a2) {
            return DecodeJob.this.a(this.dataSource, a2);
        }
    }

    private static class c<Z> {
        private z<Z> Le;
        private i<Z> encoder;
        private com.bumptech.glide.load.c key;

        c() {
        }

        /* access modifiers changed from: 0000 */
        public <X> void a(com.bumptech.glide.load.c cVar, i<X> iVar, z<X> zVar) {
            this.key = cVar;
            this.encoder = iVar;
            this.Le = zVar;
        }

        /* access modifiers changed from: 0000 */
        public void a(d dVar, com.bumptech.glide.load.g gVar) {
            try {
                dVar.n().a(this.key, new C0099e(this.encoder, this.Le, gVar));
            } finally {
                this.Le.unlock();
            }
        }

        /* access modifiers changed from: 0000 */
        public void clear() {
            this.key = null;
            this.encoder = null;
            this.Le = null;
        }

        /* access modifiers changed from: 0000 */
        public boolean kg() {
            return this.Le != null;
        }
    }

    interface d {
        com.bumptech.glide.load.engine.a.a n();
    }

    private static class e {
        private boolean Me;
        private boolean Ne;
        private boolean Oe;

        e() {
        }

        private boolean w(boolean z) {
            return (this.Oe || z || this.Ne) && this.Me;
        }

        /* access modifiers changed from: 0000 */
        public synchronized boolean lg() {
            this.Ne = true;
            return w(false);
        }

        /* access modifiers changed from: 0000 */
        public synchronized boolean onFailed() {
            this.Oe = true;
            return w(false);
        }

        /* access modifiers changed from: 0000 */
        public synchronized boolean release(boolean z) {
            this.Me = true;
            return w(z);
        }

        /* access modifiers changed from: 0000 */
        public synchronized void reset() {
            this.Ne = false;
            this.Me = false;
            this.Oe = false;
        }
    }

    DecodeJob(d dVar, Pool<DecodeJob<?>> pool2) {
        this.Ce = dVar;
        this.pool = pool2;
    }

    private <Data> A<R> a(com.bumptech.glide.load.a.d<?> dVar, Data data, DataSource dataSource) throws GlideException {
        if (data == null) {
            dVar.cleanup();
            return null;
        }
        try {
            long Gh = com.bumptech.glide.util.e.Gh();
            A<R> a2 = a(data, dataSource);
            if (Log.isLoggable(TAG, 2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Decoded result ");
                sb.append(a2);
                a(sb.toString(), Gh);
            }
            return a2;
        } finally {
            dVar.cleanup();
        }
    }

    private <Data> A<R> a(Data data, DataSource dataSource) throws GlideException {
        return a(data, dataSource, this.Pe.c(data.getClass()));
    }

    private <Data, ResourceType> A<R> a(Data data, DataSource dataSource, x<Data, ResourceType, R> xVar) throws GlideException {
        com.bumptech.glide.load.g b2 = b(dataSource);
        com.bumptech.glide.load.a.e k = this.Eb.getRegistry().k(data);
        try {
            return xVar.a(k, b2, this.width, this.height, new b(dataSource));
        } finally {
            k.cleanup();
        }
    }

    private Stage a(Stage stage) {
        int i = h.Je[stage.ordinal()];
        if (i == 1) {
            return this.Fe.ng() ? Stage.DATA_CACHE : a(Stage.DATA_CACHE);
        } else if (i == 2) {
            return this.Ye ? Stage.FINISHED : Stage.SOURCE;
        } else if (i == 3 || i == 4) {
            return Stage.FINISHED;
        } else {
            if (i == 5) {
                return this.Fe.og() ? Stage.RESOURCE_CACHE : a(Stage.RESOURCE_CACHE);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Unrecognized stage: ");
            sb.append(stage);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private void a(String str, long j) {
        a(str, j, (String) null);
    }

    private void a(String str, long j, String str2) {
        String str3;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" in ");
        sb.append(com.bumptech.glide.util.e.g(j));
        sb.append(", load key: ");
        sb.append(this.Ue);
        if (str2 != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(", ");
            sb2.append(str2);
            str3 = sb2.toString();
        } else {
            str3 = "";
        }
        sb.append(str3);
        sb.append(", thread: ");
        sb.append(Thread.currentThread().getName());
        Log.v(TAG, sb.toString());
    }

    @NonNull
    private com.bumptech.glide.load.g b(DataSource dataSource) {
        com.bumptech.glide.load.g gVar = this.options;
        if (VERSION.SDK_INT < 26 || gVar.a(o.aj) != null) {
            return gVar;
        }
        if (dataSource == DataSource.RESOURCE_DISK_CACHE || this.Pe.jg()) {
            gVar = new com.bumptech.glide.load.g();
            gVar.b(this.options);
            gVar.a(o.aj, Boolean.valueOf(true));
        }
        return gVar;
    }

    private void b(A<R> a2, DataSource dataSource) {
        lk();
        this.callback.a(a2, dataSource);
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: type inference failed for: r3v1, types: [com.bumptech.glide.load.engine.A] */
    /* JADX WARNING: type inference failed for: r3v6, types: [com.bumptech.glide.load.engine.z] */
    /* JADX WARNING: type inference failed for: r3v7 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    private void c(A<R> a2, DataSource dataSource) {
        if (a2 instanceof v) {
            ((v) a2).initialize();
        }
        z zVar = 0;
        if (this.Se.kg()) {
            r3 = z.f(a2);
            zVar = r3;
            a2 = r3;
        }
        b(a2, dataSource);
        this.Ve = Stage.ENCODE;
        try {
            if (this.Se.kg()) {
                this.Se.a(this.Ce, this.options);
            }
            if (zVar != 0) {
                zVar.unlock();
            }
            lg();
        } catch (Throwable th) {
            if (zVar != 0) {
                zVar.unlock();
            }
            throw th;
        }
    }

    private void fk() {
        if (Log.isLoggable(TAG, 2)) {
            long j = this.Xe;
            StringBuilder sb = new StringBuilder();
            sb.append("data: ");
            sb.append(this.bf);
            sb.append(", cache key: ");
            sb.append(this._e);
            sb.append(", fetcher: ");
            sb.append(this.df);
            a("Retrieved data", j, sb.toString());
        }
        A a2 = null;
        try {
            a2 = a(this.df, (Data) this.bf, this.cf);
        } catch (GlideException e2) {
            e2.a(this.af, this.cf);
            this.Qe.add(e2);
        }
        if (a2 != null) {
            c(a2, this.cf);
        } else {
            jk();
        }
    }

    private int getPriority() {
        return this.priority.ordinal();
    }

    private f gk() {
        int i = h.Je[this.Ve.ordinal()];
        if (i == 1) {
            return new B(this.Pe, this);
        }
        if (i == 2) {
            return new C0097c(this.Pe, this);
        }
        if (i == 3) {
            return new E(this.Pe, this);
        }
        if (i == 4) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unrecognized stage: ");
        sb.append(this.Ve);
        throw new IllegalStateException(sb.toString());
    }

    private void hk() {
        if (this.Te.onFailed()) {
            ik();
        }
    }

    private void ik() {
        this.Te.reset();
        this.Se.clear();
        this.Pe.clear();
        this.ff = false;
        this.Eb = null;
        this.signature = null;
        this.options = null;
        this.priority = null;
        this.Ue = null;
        this.callback = null;
        this.Ve = null;
        this.ef = null;
        this.Ze = null;
        this._e = null;
        this.bf = null;
        this.cf = null;
        this.df = null;
        this.Xe = 0;
        this.Vd = false;
        this.model = null;
        this.Qe.clear();
        this.pool.release(this);
    }

    private void jk() {
        this.Ze = Thread.currentThread();
        this.Xe = com.bumptech.glide.util.e.Gh();
        boolean z = false;
        while (!this.Vd && this.ef != null) {
            z = this.ef.q();
            if (z) {
                break;
            }
            this.Ve = a(this.Ve);
            this.ef = gk();
            if (this.Ve == Stage.SOURCE) {
                K();
                return;
            }
        }
        if ((this.Ve == Stage.FINISHED || this.Vd) && !z) {
            notifyFailed();
        }
    }

    private void kk() {
        int i = h.Ie[this.We.ordinal()];
        if (i == 1) {
            this.Ve = a(Stage.INITIALIZE);
            this.ef = gk();
            jk();
        } else if (i == 2) {
            jk();
        } else if (i == 3) {
            fk();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Unrecognized run reason: ");
            sb.append(this.We);
            throw new IllegalStateException(sb.toString());
        }
    }

    private void lg() {
        if (this.Te.lg()) {
            ik();
        }
    }

    private void lk() {
        this.Re.Mh();
        if (!this.ff) {
            this.ff = true;
            return;
        }
        throw new IllegalStateException("Already notified");
    }

    private void notifyFailed() {
        lk();
        this.callback.a(new GlideException("Failed to load resource", (List<Throwable>) new ArrayList<Throwable>(this.Qe)));
        hk();
    }

    public void K() {
        this.We = RunReason.SWITCH_TO_SOURCE_SERVICE;
        this.callback.a(this);
    }

    /* JADX WARNING: type inference failed for: r12v6, types: [com.bumptech.glide.load.c] */
    /* JADX WARNING: type inference failed for: r12v7, types: [com.bumptech.glide.load.engine.d] */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    @NonNull
    public <Z> A<Z> a(DataSource dataSource, @NonNull A<Z> a2) {
        j jVar;
        A<Z> a3;
        EncodeStrategy encodeStrategy;
        C c2;
        Class cls = a2.get().getClass();
        i iVar = null;
        if (dataSource != DataSource.RESOURCE_DISK_CACHE) {
            j d2 = this.Pe.d(cls);
            jVar = d2;
            a3 = d2.transform(this.Eb, a2, this.width, this.height);
        } else {
            a3 = a2;
            jVar = null;
        }
        if (!a2.equals(a3)) {
            a2.recycle();
        }
        if (this.Pe.d(a3)) {
            iVar = this.Pe.c(a3);
            encodeStrategy = iVar.a(this.options);
        } else {
            encodeStrategy = EncodeStrategy.NONE;
        }
        i iVar2 = iVar;
        if (!this.Fe.a(!this.Pe.e(this._e), dataSource, encodeStrategy)) {
            return a3;
        }
        if (iVar2 != null) {
            int i = h.Ke[encodeStrategy.ordinal()];
            if (i == 1) {
                c2 = new C0098d(this._e, this.signature);
            } else if (i == 2) {
                C c3 = new C(this.Pe.V(), this._e, this.signature, this.width, this.height, jVar, cls, this.options);
                c2 = c3;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown strategy: ");
                sb.append(encodeStrategy);
                throw new IllegalArgumentException(sb.toString());
            }
            z f2 = z.f(a3);
            this.Se.a(c2, iVar2, f2);
            return f2;
        }
        throw new NoResultEncoderAvailableException(a3.get().getClass());
    }

    /* access modifiers changed from: 0000 */
    public DecodeJob<R> a(com.bumptech.glide.e eVar, Object obj, s sVar, com.bumptech.glide.load.c cVar, int i, int i2, Class<?> cls, Class<R> cls2, Priority priority2, o oVar, Map<Class<?>, j<?>> map, boolean z, boolean z2, boolean z3, com.bumptech.glide.load.g gVar, a<R> aVar, int i3) {
        this.Pe.a(eVar, obj, cVar, i, i2, oVar, cls, cls2, priority2, gVar, map, z, z2, this.Ce);
        this.Eb = eVar;
        this.signature = cVar;
        this.priority = priority2;
        this.Ue = sVar;
        this.width = i;
        this.height = i2;
        this.Fe = oVar;
        this.Ye = z3;
        this.options = gVar;
        this.callback = aVar;
        this.order = i3;
        this.We = RunReason.INITIALIZE;
        this.model = obj;
        return this;
    }

    public void a(com.bumptech.glide.load.c cVar, Exception exc, com.bumptech.glide.load.a.d<?> dVar, DataSource dataSource) {
        dVar.cleanup();
        GlideException glideException = new GlideException("Fetching data failed", (Throwable) exc);
        glideException.a(cVar, dataSource, dVar.M());
        this.Qe.add(glideException);
        if (Thread.currentThread() != this.Ze) {
            this.We = RunReason.SWITCH_TO_SOURCE_SERVICE;
            this.callback.a(this);
            return;
        }
        jk();
    }

    public void a(com.bumptech.glide.load.c cVar, Object obj, com.bumptech.glide.load.a.d<?> dVar, DataSource dataSource, com.bumptech.glide.load.c cVar2) {
        this._e = cVar;
        this.bf = obj;
        this.df = dVar;
        this.cf = dataSource;
        this.af = cVar2;
        if (Thread.currentThread() != this.Ze) {
            this.We = RunReason.DECODE_DATA;
            this.callback.a(this);
            return;
        }
        fk();
    }

    /* renamed from: b */
    public int compareTo(@NonNull DecodeJob<?> decodeJob) {
        int priority2 = getPriority() - decodeJob.getPriority();
        return priority2 == 0 ? this.order - decodeJob.order : priority2;
    }

    public void cancel() {
        this.Vd = true;
        f fVar = this.ef;
        if (fVar != null) {
            fVar.cancel();
        }
    }

    @NonNull
    public g getVerifier() {
        return this.Re;
    }

    /* access modifiers changed from: 0000 */
    public boolean mg() {
        Stage a2 = a(Stage.INITIALIZE);
        return a2 == Stage.RESOURCE_CACHE || a2 == Stage.DATA_CACHE;
    }

    /* access modifiers changed from: 0000 */
    public void release(boolean z) {
        if (this.Te.release(z)) {
            ik();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0014, code lost:
        if (r1 != null) goto L_0x0016;
     */
    public void run() {
        String str = TAG;
        com.bumptech.glide.load.a.d<?> dVar = this.df;
        try {
            if (this.Vd) {
                notifyFailed();
                if (dVar != null) {
                    dVar.cleanup();
                }
                return;
            }
            kk();
        } catch (Throwable th) {
            if (dVar != null) {
                dVar.cleanup();
            }
            throw th;
        }
    }
}
