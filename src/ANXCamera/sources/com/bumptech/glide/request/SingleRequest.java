package com.bumptech.glide.request;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pools;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.e;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.p;
import com.bumptech.glide.request.a.g;
import com.bumptech.glide.request.target.m;
import com.bumptech.glide.request.target.n;
import com.bumptech.glide.util.a.a;
import com.bumptech.glide.util.a.c;
import com.bumptech.glide.util.k;

public final class SingleRequest<R> implements c, g, m, a.c {
    private static final String TAG = "Request";
    private static final Pools.Pool<SingleRequest<?>> hR = a.a(150, new a.C0013a<SingleRequest<?>>() {
        /* renamed from: eU */
        public SingleRequest<?> create() {
            return new SingleRequest<>();
        }
    });
    private static final String pE = "Glide";
    private static final boolean pG = Log.isLoggable(TAG, 2);
    private Engine bl;
    private e bp;
    private e<R> cA;
    private Context context;
    private Class<R> cw;
    private f cx;
    @Nullable
    private Object cz;
    private p<R> fF;
    private Priority fY;
    private final c ge;
    private int height;
    private boolean pF;
    @Nullable
    private e<R> pH;
    private d pI;
    private n<R> pJ;
    private g<? super R> pK;
    private Engine.b pL;
    private Status pM;
    private Drawable pN;
    private Drawable pu;
    private int pw;
    private int px;
    private Drawable pz;
    private long startTime;
    @Nullable
    private final String tag;
    private int width;

    private enum Status {
        PENDING,
        RUNNING,
        WAITING_FOR_SIZE,
        COMPLETE,
        FAILED,
        CANCELLED,
        CLEARED,
        PAUSED
    }

    SingleRequest() {
        this.tag = pG ? String.valueOf(super.hashCode()) : null;
        this.ge = c.fv();
    }

    private void G(String str) {
        Log.v(TAG, str + " this: " + this.tag);
    }

    private Drawable W(@DrawableRes int i) {
        return com.bumptech.glide.load.resource.b.a.a((Context) this.bp, i, this.cx.getTheme() != null ? this.cx.getTheme() : this.context.getTheme());
    }

    private static int a(int i, float f) {
        return i == Integer.MIN_VALUE ? i : Math.round(f * ((float) i));
    }

    public static <R> SingleRequest<R> a(Context context2, e eVar, Object obj, Class<R> cls, f fVar, int i, int i2, Priority priority, n<R> nVar, e<R> eVar2, e<R> eVar3, d dVar, Engine engine, g<? super R> gVar) {
        SingleRequest<R> acquire = hR.acquire();
        if (acquire == null) {
            acquire = new SingleRequest<>();
        }
        acquire.b(context2, eVar, obj, cls, fVar, i, i2, priority, nVar, eVar2, eVar3, dVar, engine, gVar);
        return acquire;
    }

    /* JADX INFO: finally extract failed */
    private void a(GlideException glideException, int i) {
        this.ge.fw();
        int logLevel = this.bp.getLogLevel();
        if (logLevel <= i) {
            Log.w(pE, "Load failed for " + this.cz + " with size [" + this.width + "x" + this.height + "]", glideException);
            if (logLevel <= 4) {
                glideException.t(pE);
            }
        }
        this.pL = null;
        this.pM = Status.FAILED;
        this.pF = true;
        try {
            if ((this.cA == null || !this.cA.a(glideException, this.cz, this.pJ, eR())) && (this.pH == null || !this.pH.a(glideException, this.cz, this.pJ, eR()))) {
                eN();
            }
            this.pF = false;
            eT();
        } catch (Throwable th) {
            this.pF = false;
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0093, code lost:
        if (r7.pH.a(r9, r7.cz, r7.pJ, r10, r6) == false) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0080, code lost:
        if (r7.cA.a(r9, r7.cz, r7.pJ, r10, r6) == false) goto L_0x0082;
     */
    private void a(p<R> pVar, R r, DataSource dataSource) {
        boolean eR = eR();
        this.pM = Status.COMPLETE;
        this.fF = pVar;
        if (this.bp.getLogLevel() <= 3) {
            Log.d(pE, "Finished loading " + r.getClass().getSimpleName() + " from " + dataSource + " for " + this.cz + " with size [" + this.width + "x" + this.height + "] in " + com.bumptech.glide.util.e.f(this.startTime) + " ms");
        }
        this.pF = true;
        try {
            if (this.cA != null) {
            }
            if (this.pH != null) {
            }
            this.pJ.a(r, this.pK.a(dataSource, eR));
            this.pF = false;
            eS();
        } catch (Throwable th) {
            this.pF = false;
            throw th;
        }
    }

    private void b(Context context2, e eVar, Object obj, Class<R> cls, f fVar, int i, int i2, Priority priority, n<R> nVar, e<R> eVar2, e<R> eVar3, d dVar, Engine engine, g<? super R> gVar) {
        this.context = context2;
        this.bp = eVar;
        this.cz = obj;
        this.cw = cls;
        this.cx = fVar;
        this.px = i;
        this.pw = i2;
        this.fY = priority;
        this.pJ = nVar;
        this.pH = eVar2;
        this.cA = eVar3;
        this.pI = dVar;
        this.bl = engine;
        this.pK = gVar;
        this.pM = Status.PENDING;
    }

    private Drawable eB() {
        if (this.pz == null) {
            this.pz = this.cx.eB();
            if (this.pz == null && this.cx.eA() > 0) {
                this.pz = W(this.cx.eA());
            }
        }
        return this.pz;
    }

    private void eL() {
        if (this.pF) {
            throw new IllegalStateException("You can't start or clear loads in RequestListener or Target callbacks. If you're trying to start a fallback request when a load fails, use RequestBuilder#error(RequestBuilder). Otherwise consider posting your into() or clear() calls to the main thread using a Handler instead.");
        }
    }

    private Drawable eM() {
        if (this.pN == null) {
            this.pN = this.cx.ew();
            if (this.pN == null && this.cx.ex() > 0) {
                this.pN = W(this.cx.ex());
            }
        }
        return this.pN;
    }

    private void eN() {
        if (eQ()) {
            Drawable drawable = null;
            if (this.cz == null) {
                drawable = eB();
            }
            if (drawable == null) {
                drawable = eM();
            }
            if (drawable == null) {
                drawable = ez();
            }
            this.pJ.f(drawable);
        }
    }

    private boolean eO() {
        return this.pI == null || this.pI.d(this);
    }

    private boolean eP() {
        return this.pI == null || this.pI.f(this);
    }

    private boolean eQ() {
        return this.pI == null || this.pI.e(this);
    }

    private boolean eR() {
        return this.pI == null || !this.pI.dQ();
    }

    private void eS() {
        if (this.pI != null) {
            this.pI.h(this);
        }
    }

    private void eT() {
        if (this.pI != null) {
            this.pI.i(this);
        }
    }

    private Drawable ez() {
        if (this.pu == null) {
            this.pu = this.cx.ez();
            if (this.pu == null && this.cx.ey() > 0) {
                this.pu = W(this.cx.ey());
            }
        }
        return this.pu;
    }

    private void m(p<?> pVar) {
        this.bl.d(pVar);
        this.fF = null;
    }

    public void a(GlideException glideException) {
        a(glideException, 5);
    }

    public void begin() {
        eL();
        this.ge.fw();
        this.startTime = com.bumptech.glide.util.e.fn();
        if (this.cz == null) {
            if (k.t(this.px, this.pw)) {
                this.width = this.px;
                this.height = this.pw;
            }
            a(new GlideException("Received null model"), eB() == null ? 5 : 3);
        } else if (this.pM == Status.RUNNING) {
            throw new IllegalArgumentException("Cannot restart a running request");
        } else if (this.pM == Status.COMPLETE) {
            c(this.fF, DataSource.MEMORY_CACHE);
        } else {
            this.pM = Status.WAITING_FOR_SIZE;
            if (k.t(this.px, this.pw)) {
                q(this.px, this.pw);
            } else {
                this.pJ.a(this);
            }
            if ((this.pM == Status.RUNNING || this.pM == Status.WAITING_FOR_SIZE) && eQ()) {
                this.pJ.e(ez());
            }
            if (pG) {
                G("finished run method in " + com.bumptech.glide.util.e.f(this.startTime));
            }
        }
    }

    @NonNull
    public c br() {
        return this.ge;
    }

    public void c(p<?> pVar, DataSource dataSource) {
        this.ge.fw();
        this.pL = null;
        if (pVar == null) {
            a(new GlideException("Expected to receive a Resource<R> with an object of " + this.cw + " inside, but instead got null."));
            return;
        }
        Object obj = pVar.get();
        if (obj == null || !this.cw.isAssignableFrom(obj.getClass())) {
            m(pVar);
            StringBuilder sb = new StringBuilder();
            sb.append("Expected to receive an object of ");
            sb.append(this.cw);
            sb.append(" but instead got ");
            sb.append(obj != null ? obj.getClass() : "");
            sb.append("{");
            sb.append(obj);
            sb.append("} inside Resource{");
            sb.append(pVar);
            sb.append("}.");
            sb.append(obj != null ? "" : " To indicate failure return a null Resource object, rather than a Resource object containing null data.");
            a(new GlideException(sb.toString()));
        } else if (!eO()) {
            m(pVar);
            this.pM = Status.COMPLETE;
        } else {
            a(pVar, obj, dataSource);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0044 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    public boolean c(c cVar) {
        if (!(cVar instanceof SingleRequest)) {
            return false;
        }
        SingleRequest singleRequest = (SingleRequest) cVar;
        if (this.px != singleRequest.px || this.pw != singleRequest.pw || !k.e(this.cz, singleRequest.cz) || !this.cw.equals(singleRequest.cw) || !this.cx.equals(singleRequest.cx) || this.fY != singleRequest.fY) {
            return false;
        }
        if (this.cA != null) {
            return singleRequest.cA != null;
        }
        if (singleRequest.cA != null) {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void cancel() {
        eL();
        this.ge.fw();
        this.pJ.b(this);
        this.pM = Status.CANCELLED;
        if (this.pL != null) {
            this.pL.cancel();
            this.pL = null;
        }
    }

    public void clear() {
        k.fo();
        eL();
        this.ge.fw();
        if (this.pM != Status.CLEARED) {
            cancel();
            if (this.fF != null) {
                m(this.fF);
            }
            if (eP()) {
                this.pJ.d(ez());
            }
            this.pM = Status.CLEARED;
        }
    }

    public boolean dM() {
        return isComplete();
    }

    public boolean isCancelled() {
        return this.pM == Status.CANCELLED || this.pM == Status.CLEARED;
    }

    public boolean isComplete() {
        return this.pM == Status.COMPLETE;
    }

    public boolean isFailed() {
        return this.pM == Status.FAILED;
    }

    public boolean isPaused() {
        return this.pM == Status.PAUSED;
    }

    public boolean isRunning() {
        return this.pM == Status.RUNNING || this.pM == Status.WAITING_FOR_SIZE;
    }

    public void pause() {
        clear();
        this.pM = Status.PAUSED;
    }

    public void q(int i, int i2) {
        this.ge.fw();
        if (pG) {
            G("Got onSizeReady in " + com.bumptech.glide.util.e.f(this.startTime));
        }
        if (this.pM == Status.WAITING_FOR_SIZE) {
            this.pM = Status.RUNNING;
            float eH = this.cx.eH();
            this.width = a(i, eH);
            this.height = a(i2, eH);
            if (pG) {
                G("finished setup for calling load in " + com.bumptech.glide.util.e.f(this.startTime));
            }
            Engine.b a2 = this.bl.a(this.bp, this.cz, this.cx.bb(), this.width, this.height, this.cx.bH(), this.cw, this.fY, this.cx.aY(), this.cx.eu(), this.cx.ev(), this.cx.bf(), this.cx.ba(), this.cx.eC(), this.cx.eI(), this.cx.eJ(), this.cx.eK(), this);
            this.pL = a2;
            if (this.pM != Status.RUNNING) {
                this.pL = null;
            }
            if (pG) {
                G("finished onSizeReady in " + com.bumptech.glide.util.e.f(this.startTime));
            }
        }
    }

    public void recycle() {
        eL();
        this.context = null;
        this.bp = null;
        this.cz = null;
        this.cw = null;
        this.cx = null;
        this.px = -1;
        this.pw = -1;
        this.pJ = null;
        this.cA = null;
        this.pH = null;
        this.pI = null;
        this.pK = null;
        this.pL = null;
        this.pN = null;
        this.pu = null;
        this.pz = null;
        this.width = -1;
        this.height = -1;
        hR.release(this);
    }
}
