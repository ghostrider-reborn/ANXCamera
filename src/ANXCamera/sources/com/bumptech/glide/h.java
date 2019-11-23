package com.bumptech.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.CheckResult;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.widget.ImageView;
import com.bumptech.glide.load.engine.g;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.SingleRequest;
import com.bumptech.glide.request.a;
import com.bumptech.glide.request.b;
import com.bumptech.glide.request.c;
import com.bumptech.glide.request.d;
import com.bumptech.glide.request.e;
import com.bumptech.glide.request.f;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.target.n;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.k;
import java.io.File;
import java.net.URL;

/* compiled from: RequestBuilder */
public class h<TranscodeType> implements g<h<TranscodeType>>, Cloneable {
    protected static final f cv = new f().b(g.gW).c(Priority.LOW).m(true);
    private final f bB;
    private final i bJ;
    private final c bj;
    private final e bp;
    @Nullable
    private e<TranscodeType> cA;
    @Nullable
    private h<TranscodeType> cB;
    @Nullable
    private h<TranscodeType> cC;
    @Nullable
    private Float cD;
    private boolean cE;
    private boolean cF;
    private boolean cG;
    private final Context context;
    private final Class<TranscodeType> cw;
    @NonNull
    protected f cx;
    @NonNull
    private j<?, ? super TranscodeType> cy;
    @Nullable
    private Object cz;

    /* renamed from: com.bumptech.glide.h$2  reason: invalid class name */
    /* compiled from: RequestBuilder */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] cJ = new int[ImageView.ScaleType.values().length];

        static {
            cK = new int[Priority.values().length];
            try {
                cK[Priority.LOW.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                cK[Priority.NORMAL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                cK[Priority.HIGH.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                cK[Priority.IMMEDIATE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                cJ[ImageView.ScaleType.CENTER_CROP.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                cJ[ImageView.ScaleType.CENTER_INSIDE.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                cJ[ImageView.ScaleType.FIT_CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
            try {
                cJ[ImageView.ScaleType.FIT_START.ordinal()] = 4;
            } catch (NoSuchFieldError e8) {
            }
            try {
                cJ[ImageView.ScaleType.FIT_END.ordinal()] = 5;
            } catch (NoSuchFieldError e9) {
            }
            try {
                cJ[ImageView.ScaleType.FIT_XY.ordinal()] = 6;
            } catch (NoSuchFieldError e10) {
            }
            try {
                cJ[ImageView.ScaleType.CENTER.ordinal()] = 7;
            } catch (NoSuchFieldError e11) {
            }
            try {
                cJ[ImageView.ScaleType.MATRIX.ordinal()] = 8;
            } catch (NoSuchFieldError e12) {
            }
        }
    }

    protected h(c cVar, i iVar, Class<TranscodeType> cls, Context context2) {
        this.cE = true;
        this.bj = cVar;
        this.bJ = iVar;
        this.cw = cls;
        this.bB = iVar.T();
        this.context = context2;
        this.cy = iVar.b(cls);
        this.cx = this.bB;
        this.bp = cVar.O();
    }

    protected h(Class<TranscodeType> cls, h<?> hVar) {
        this(hVar.bj, hVar.bJ, cls, hVar.context);
        this.cz = hVar.cz;
        this.cF = hVar.cF;
        this.cx = hVar.cx;
    }

    @NonNull
    private Priority a(@NonNull Priority priority) {
        switch (priority) {
            case LOW:
                return Priority.NORMAL;
            case NORMAL:
                return Priority.HIGH;
            case HIGH:
            case IMMEDIATE:
                return Priority.IMMEDIATE;
            default:
                throw new IllegalArgumentException("unknown priority: " + this.cx.aZ());
        }
    }

    private c a(n<TranscodeType> nVar, @Nullable e<TranscodeType> eVar, @Nullable d dVar, j<?, ? super TranscodeType> jVar, Priority priority, int i, int i2, f fVar) {
        a aVar;
        a aVar2;
        if (this.cC != null) {
            aVar2 = new a(dVar);
            aVar = aVar2;
        } else {
            aVar = null;
            aVar2 = dVar;
        }
        c b2 = b(nVar, eVar, aVar2, jVar, priority, i, i2, fVar);
        if (aVar == null) {
            return b2;
        }
        int eE = this.cC.cx.eE();
        int eG = this.cC.cx.eG();
        if (k.t(i, i2) && !this.cC.cx.eF()) {
            eE = fVar.eE();
            eG = fVar.eG();
        }
        aVar.a(b2, this.cC.a(nVar, eVar, (d) aVar, this.cC.cy, this.cC.cx.aZ(), eE, eG, this.cC.cx));
        return aVar;
    }

    private c a(n<TranscodeType> nVar, e<TranscodeType> eVar, f fVar, d dVar, j<?, ? super TranscodeType> jVar, Priority priority, int i, int i2) {
        Context context2 = this.context;
        e eVar2 = this.bp;
        Object obj = this.cz;
        Class<TranscodeType> cls = this.cw;
        return SingleRequest.a(context2, eVar2, obj, cls, fVar, i, i2, priority, nVar, eVar, this.cA, dVar, this.bp.V(), jVar.ap());
    }

    private <Y extends n<TranscodeType>> Y a(@NonNull Y y, @Nullable e<TranscodeType> eVar, @NonNull f fVar) {
        k.fo();
        i.checkNotNull(y);
        if (this.cF) {
            f ep = fVar.ep();
            c b2 = b(y, eVar, ep);
            c dS = y.dS();
            if (!b2.c(dS) || a(ep, dS)) {
                this.bJ.d((n<?>) y);
                y.j(b2);
                this.bJ.a(y, b2);
                return y;
            }
            b2.recycle();
            if (!((c) i.checkNotNull(dS)).isRunning()) {
                dS.begin();
            }
            return y;
        }
        throw new IllegalArgumentException("You must call #load() before calling #into()");
    }

    private boolean a(f fVar, c cVar) {
        return !fVar.eC() && cVar.isComplete();
    }

    private c b(n<TranscodeType> nVar, e<TranscodeType> eVar, @Nullable d dVar, j<?, ? super TranscodeType> jVar, Priority priority, int i, int i2, f fVar) {
        d dVar2 = dVar;
        Priority priority2 = priority;
        if (this.cB != null) {
            if (!this.cG) {
                j<?, ? super TranscodeType> jVar2 = this.cB.cE ? jVar : this.cB.cy;
                Priority aZ = this.cB.cx.eD() ? this.cB.cx.aZ() : a(priority2);
                int eE = this.cB.cx.eE();
                int eG = this.cB.cx.eG();
                if (k.t(i, i2) && !this.cB.cx.eF()) {
                    eE = fVar.eE();
                    eG = fVar.eG();
                }
                com.bumptech.glide.request.h hVar = new com.bumptech.glide.request.h(dVar2);
                c a2 = a(nVar, eVar, fVar, (d) hVar, jVar, priority2, i, i2);
                this.cG = true;
                com.bumptech.glide.request.h hVar2 = hVar;
                c a3 = this.cB.a(nVar, eVar, (d) hVar, jVar2, aZ, eE, eG, this.cB.cx);
                this.cG = false;
                hVar2.a(a2, a3);
                return hVar2;
            }
            throw new IllegalStateException("You cannot use a request as both the main request and a thumbnail, consider using clone() on the request(s) passed to thumbnail()");
        } else if (this.cD == null) {
            return a(nVar, eVar, fVar, dVar2, jVar, priority2, i, i2);
        } else {
            com.bumptech.glide.request.h hVar3 = new com.bumptech.glide.request.h(dVar2);
            e<TranscodeType> eVar2 = eVar;
            com.bumptech.glide.request.h hVar4 = hVar3;
            j<?, ? super TranscodeType> jVar3 = jVar;
            int i3 = i;
            int i4 = i2;
            hVar3.a(a(nVar, eVar2, fVar, (d) hVar4, jVar3, priority2, i3, i4), a(nVar, eVar2, fVar.clone().h(this.cD.floatValue()), (d) hVar4, jVar3, a(priority2), i3, i4));
            return hVar3;
        }
    }

    private c b(n<TranscodeType> nVar, @Nullable e<TranscodeType> eVar, f fVar) {
        return a(nVar, eVar, (d) null, this.cy, fVar.aZ(), fVar.eE(), fVar.eG(), fVar);
    }

    @NonNull
    private h<TranscodeType> k(@Nullable Object obj) {
        this.cz = obj;
        this.cF = true;
        return this;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public f Y() {
        return this.bB == this.cx ? this.cx.clone() : this.cx;
    }

    @CheckResult
    /* renamed from: Z */
    public h<TranscodeType> clone() {
        try {
            h<TranscodeType> hVar = (h) super.clone();
            hVar.cx = hVar.cx.clone();
            hVar.cy = hVar.cy.clone();
            return hVar;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @CheckResult
    @NonNull
    public h<TranscodeType> a(float f) {
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("sizeMultiplier must be between 0 and 1");
        }
        this.cD = Float.valueOf(f);
        return this;
    }

    @NonNull
    public h<TranscodeType> a(@Nullable h<TranscodeType> hVar) {
        this.cC = hVar;
        return this;
    }

    @CheckResult
    @NonNull
    public h<TranscodeType> a(@NonNull j<?, ? super TranscodeType> jVar) {
        this.cy = (j) i.checkNotNull(jVar);
        this.cE = false;
        return this;
    }

    @CheckResult
    @NonNull
    public h<TranscodeType> a(@Nullable e<TranscodeType> eVar) {
        this.cA = eVar;
        return this;
    }

    @CheckResult
    @NonNull
    public h<TranscodeType> a(@Nullable h<TranscodeType>... hVarArr) {
        h<TranscodeType> hVar = null;
        if (hVarArr == null || hVarArr.length == 0) {
            return b((h) null);
        }
        for (int length = hVarArr.length - 1; length >= 0; length--) {
            h<TranscodeType> hVar2 = hVarArr[length];
            if (hVar2 != null) {
                hVar = hVar == null ? hVar2 : hVar2.b(hVar);
            }
        }
        return b(hVar);
    }

    @NonNull
    public ViewTarget<ImageView, TranscodeType> a(@NonNull ImageView imageView) {
        k.fo();
        i.checkNotNull(imageView);
        f fVar = this.cx;
        if (!fVar.ec() && fVar.eb() && imageView.getScaleType() != null) {
            switch (AnonymousClass2.cJ[imageView.getScaleType().ordinal()]) {
                case 1:
                    fVar = fVar.clone().ee();
                    break;
                case 2:
                    fVar = fVar.clone().ei();
                    break;
                case 3:
                case 4:
                case 5:
                    fVar = fVar.clone().eg();
                    break;
                case 6:
                    fVar = fVar.clone().ei();
                    break;
            }
        }
        return (ViewTarget) a(this.bp.a(imageView, this.cw), (e) null, fVar);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public <Y extends n<TranscodeType>> Y a(@NonNull Y y, @Nullable e<TranscodeType> eVar) {
        return a(y, eVar, Y());
    }

    @NonNull
    public b<TranscodeType> aa() {
        return h(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    @NonNull
    public n<TranscodeType> ab() {
        return i(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    /* access modifiers changed from: protected */
    @CheckResult
    @NonNull
    public h<File> ac() {
        return new h(File.class, this).b(cv);
    }

    @CheckResult
    @NonNull
    /* renamed from: b */
    public h<TranscodeType> a(@Nullable Bitmap bitmap) {
        return k(bitmap).b(f.a(g.gV));
    }

    @CheckResult
    @NonNull
    /* renamed from: b */
    public h<TranscodeType> a(@Nullable Drawable drawable) {
        return k(drawable).b(f.a(g.gV));
    }

    @CheckResult
    @NonNull
    /* renamed from: b */
    public h<TranscodeType> a(@Nullable Uri uri) {
        return k(uri);
    }

    @CheckResult
    @NonNull
    public h<TranscodeType> b(@Nullable h<TranscodeType> hVar) {
        this.cB = hVar;
        return this;
    }

    @CheckResult
    @NonNull
    public h<TranscodeType> b(@NonNull f fVar) {
        i.checkNotNull(fVar);
        this.cx = Y().g(fVar);
        return this;
    }

    @CheckResult
    @NonNull
    /* renamed from: b */
    public h<TranscodeType> a(@Nullable File file) {
        return k(file);
    }

    @CheckResult
    @NonNull
    /* renamed from: b */
    public h<TranscodeType> a(@Nullable @RawRes @DrawableRes Integer num) {
        return k(num).b(f.j(com.bumptech.glide.e.a.n(this.context)));
    }

    @Deprecated
    @CheckResult
    /* renamed from: b */
    public h<TranscodeType> a(@Nullable URL url) {
        return k(url);
    }

    @CheckResult
    @NonNull
    /* renamed from: b */
    public h<TranscodeType> a(@Nullable byte[] bArr) {
        h<TranscodeType> k = k(bArr);
        if (!k.cx.es()) {
            k = k.b(f.a(g.gV));
        }
        return !k.cx.et() ? k.b(f.i(true)) : k;
    }

    @NonNull
    public <Y extends n<TranscodeType>> Y b(@NonNull Y y) {
        return a(y, (e) null);
    }

    @Deprecated
    @CheckResult
    public <Y extends n<File>> Y c(@NonNull Y y) {
        return ac().b(y);
    }

    @Deprecated
    public b<TranscodeType> g(int i, int i2) {
        return h(i, i2);
    }

    @NonNull
    public b<TranscodeType> h(int i, int i2) {
        final RequestFutureTarget requestFutureTarget = new RequestFutureTarget(this.bp.U(), i, i2);
        if (k.fr()) {
            this.bp.U().post(new Runnable() {
                public void run() {
                    if (!requestFutureTarget.isCancelled()) {
                        h.this.a(requestFutureTarget, requestFutureTarget);
                    }
                }
            });
        } else {
            a(requestFutureTarget, requestFutureTarget);
        }
        return requestFutureTarget;
    }

    @NonNull
    public n<TranscodeType> i(int i, int i2) {
        return b(com.bumptech.glide.request.target.k.b(this.bJ, i, i2));
    }

    @CheckResult
    @NonNull
    /* renamed from: j */
    public h<TranscodeType> load(@Nullable Object obj) {
        return k(obj);
    }

    @Deprecated
    @CheckResult
    public b<File> j(int i, int i2) {
        return ac().h(i, i2);
    }

    @CheckResult
    @NonNull
    /* renamed from: n */
    public h<TranscodeType> m(@Nullable String str) {
        return k(str);
    }
}
