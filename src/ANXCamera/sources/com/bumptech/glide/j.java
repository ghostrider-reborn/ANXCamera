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
import com.bumptech.glide.load.engine.o;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.SingleRequest;
import com.bumptech.glide.request.a;
import com.bumptech.glide.request.b;
import com.bumptech.glide.request.c;
import com.bumptech.glide.request.d;
import com.bumptech.glide.request.e;
import com.bumptech.glide.request.f;
import com.bumptech.glide.request.i;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.util.l;
import java.io.File;
import java.net.URL;

/* compiled from: RequestBuilder */
public class j<TranscodeType> implements Cloneable, g<j<TranscodeType>> {
    protected static final f Ec = new f().a(o.DATA).a(Priority.LOW).q(true);
    @Nullable
    private Float Ac;
    private boolean Bc;
    private boolean Cc;
    private boolean Dc;
    private final e Eb;
    private final c Kb;
    private final Context context;
    private final m da;
    @Nullable
    private Object model;
    private final f ta;
    private final Class<TranscodeType> uc;
    @NonNull
    protected f vc;
    @NonNull
    private n<?, ? super TranscodeType> wc;
    @Nullable
    private e<TranscodeType> xc;
    @Nullable
    private j<TranscodeType> yc;
    @Nullable
    private j<TranscodeType> zc;

    protected j(c cVar, m mVar, Class<TranscodeType> cls, Context context2) {
        this.Bc = true;
        this.Kb = cVar;
        this.da = mVar;
        this.uc = cls;
        this.ta = mVar.W();
        this.context = context2;
        this.wc = mVar.a(cls);
        this.vc = this.ta;
        this.Eb = cVar.Ef();
    }

    protected j(Class<TranscodeType> cls, j<?> jVar) {
        this(jVar.Kb, jVar.da, cls, jVar.context);
        this.model = jVar.model;
        this.Cc = jVar.Cc;
        this.vc = jVar.vc;
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [com.bumptech.glide.request.d] */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r0v4, types: [com.bumptech.glide.request.a] */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r3v7 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 4 */
    private c a(com.bumptech.glide.request.target.o<TranscodeType> oVar, @Nullable e<TranscodeType> eVar, @Nullable d dVar, n<?, ? super TranscodeType> nVar, Priority priority, int i, int i2, f fVar) {
        a aVar;
        ? r3;
        if (this.zc != null) {
            ? aVar2 = new a(dVar);
            aVar = aVar2;
            r3 = aVar2;
        } else {
            aVar = 0;
            r3 = dVar;
        }
        c b2 = b(oVar, eVar, r3, nVar, priority, i, i2, fVar);
        if (aVar == 0) {
            return b2;
        }
        int fh = this.zc.vc.fh();
        int eh = this.zc.vc.eh();
        if (l.o(i, i2) && !this.zc.vc.th()) {
            fh = fVar.fh();
            eh = fVar.eh();
        }
        int i3 = fh;
        int i4 = eh;
        j<TranscodeType> jVar = this.zc;
        com.bumptech.glide.request.target.o<TranscodeType> oVar2 = oVar;
        e<TranscodeType> eVar2 = eVar;
        d dVar2 = aVar;
        aVar.a(b2, jVar.a(oVar2, eVar2, dVar2, jVar.wc, jVar.vc.getPriority(), i3, i4, this.zc.vc));
        return aVar;
    }

    private c a(com.bumptech.glide.request.target.o<TranscodeType> oVar, @Nullable e<TranscodeType> eVar, f fVar) {
        return a(oVar, eVar, (d) null, this.wc, fVar.getPriority(), fVar.fh(), fVar.eh(), fVar);
    }

    private c a(com.bumptech.glide.request.target.o<TranscodeType> oVar, e<TranscodeType> eVar, f fVar, d dVar, n<?, ? super TranscodeType> nVar, Priority priority, int i, int i2) {
        Context context2 = this.context;
        e eVar2 = this.Eb;
        Object obj = this.model;
        Class<TranscodeType> cls = this.uc;
        return SingleRequest.a(context2, eVar2, obj, cls, fVar, i, i2, priority, oVar, eVar, this.xc, dVar, eVar2.X(), nVar.Vf());
    }

    private boolean a(f fVar, c cVar) {
        return !fVar.nh() && cVar.isComplete();
    }

    private c b(com.bumptech.glide.request.target.o<TranscodeType> oVar, e<TranscodeType> eVar, @Nullable d dVar, n<?, ? super TranscodeType> nVar, Priority priority, int i, int i2, f fVar) {
        d dVar2 = dVar;
        Priority priority2 = priority;
        j<TranscodeType> jVar = this.yc;
        if (jVar != null) {
            if (!this.Dc) {
                n<?, ? super TranscodeType> nVar2 = jVar.Bc ? nVar : jVar.wc;
                Priority priority3 = this.yc.vc.oh() ? this.yc.vc.getPriority() : c(priority2);
                int fh = this.yc.vc.fh();
                int eh = this.yc.vc.eh();
                if (l.o(i, i2) && !this.yc.vc.th()) {
                    fh = fVar.fh();
                    eh = fVar.eh();
                }
                int i3 = fh;
                int i4 = eh;
                i iVar = new i(dVar2);
                c a2 = a(oVar, eVar, fVar, (d) iVar, nVar, priority, i, i2);
                this.Dc = true;
                j<TranscodeType> jVar2 = this.yc;
                i iVar2 = iVar;
                c a3 = jVar2.a(oVar, eVar, (d) iVar, nVar2, priority3, i3, i4, jVar2.vc);
                this.Dc = false;
                iVar2.a(a2, a3);
                return iVar2;
            }
            throw new IllegalStateException("You cannot use a request as both the main request and a thumbnail, consider using clone() on the request(s) passed to thumbnail()");
        } else if (this.Ac == null) {
            return a(oVar, eVar, fVar, dVar, nVar, priority, i, i2);
        } else {
            i iVar3 = new i(dVar2);
            e<TranscodeType> eVar2 = eVar;
            i iVar4 = iVar3;
            n<?, ? super TranscodeType> nVar3 = nVar;
            int i5 = i;
            int i6 = i2;
            iVar3.a(a(oVar, eVar2, fVar, (d) iVar4, nVar3, priority, i5, i6), a(oVar, eVar2, fVar.clone().g(this.Ac.floatValue()), (d) iVar4, nVar3, c(priority2), i5, i6));
            return iVar3;
        }
    }

    private <Y extends com.bumptech.glide.request.target.o<TranscodeType>> Y b(@NonNull Y y, @Nullable e<TranscodeType> eVar, @NonNull f fVar) {
        l.Ih();
        com.bumptech.glide.util.i.checkNotNull(y);
        if (this.Cc) {
            f Og = fVar.Og();
            c a2 = a(y, eVar, Og);
            c request = y.getRequest();
            if (!a2.a(request) || a(Og, request)) {
                this.da.d((com.bumptech.glide.request.target.o<?>) y);
                y.f(a2);
                this.da.a(y, a2);
                return y;
            }
            a2.recycle();
            com.bumptech.glide.util.i.checkNotNull(request);
            if (!request.isRunning()) {
                request.begin();
            }
            return y;
        }
        throw new IllegalArgumentException("You must call #load() before calling #into()");
    }

    @NonNull
    private Priority c(@NonNull Priority priority) {
        int i = i.tc[priority.ordinal()];
        if (i == 1) {
            return Priority.NORMAL;
        }
        if (i == 2) {
            return Priority.HIGH;
        }
        if (i == 3 || i == 4) {
            return Priority.IMMEDIATE;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("unknown priority: ");
        sb.append(this.vc.getPriority());
        throw new IllegalArgumentException(sb.toString());
    }

    @NonNull
    private j<TranscodeType> t(@Nullable Object obj) {
        this.model = obj;
        this.Cc = true;
        return this;
    }

    /* access modifiers changed from: protected */
    @CheckResult
    @NonNull
    public j<File> Hf() {
        return new j(File.class, this).b(Ec);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public f If() {
        f fVar = this.ta;
        f fVar2 = this.vc;
        return fVar == fVar2 ? fVar2.clone() : fVar2;
    }

    @NonNull
    public b<TranscodeType> Jf() {
        return j(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> a(@Nullable Drawable drawable) {
        t(drawable);
        return b(f.b(o.NONE));
    }

    @NonNull
    public j<TranscodeType> a(@Nullable j<TranscodeType> jVar) {
        this.zc = jVar;
        return this;
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> a(@NonNull n<?, ? super TranscodeType> nVar) {
        com.bumptech.glide.util.i.checkNotNull(nVar);
        this.wc = nVar;
        this.Bc = false;
        return this;
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> a(@Nullable e<TranscodeType> eVar) {
        this.xc = eVar;
        return this;
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> a(@Nullable File file) {
        t(file);
        return this;
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> a(@Nullable @RawRes @DrawableRes Integer num) {
        t(num);
        return b(f.h(com.bumptech.glide.e.a.obtain(this.context)));
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> a(@Nullable j<TranscodeType>... jVarArr) {
        j<TranscodeType> jVar = null;
        if (jVarArr == null || jVarArr.length == 0) {
            return b(null);
        }
        for (int length = jVarArr.length - 1; length >= 0; length--) {
            j<TranscodeType> jVar2 = jVarArr[length];
            if (jVar2 != null) {
                jVar = jVar == null ? jVar2 : jVar2.b(jVar);
            }
        }
        return b(jVar);
    }

    @NonNull
    public ViewTarget<ImageView, TranscodeType> a(@NonNull ImageView imageView) {
        l.Ih();
        com.bumptech.glide.util.i.checkNotNull(imageView);
        f fVar = this.vc;
        if (!fVar.sh() && fVar.qh() && imageView.getScaleType() != null) {
            switch (i.sc[imageView.getScaleType().ordinal()]) {
                case 1:
                    fVar = fVar.clone().wh();
                    break;
                case 2:
                    fVar = fVar.clone().xh();
                    break;
                case 3:
                case 4:
                case 5:
                    fVar = fVar.clone().zh();
                    break;
                case 6:
                    fVar = fVar.clone().xh();
                    break;
            }
        }
        ViewTarget<ImageView, TranscodeType> a2 = this.Eb.a(imageView, this.uc);
        b(a2, null, fVar);
        return a2;
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public <Y extends com.bumptech.glide.request.target.o<TranscodeType>> Y a(@NonNull Y y, @Nullable e<TranscodeType> eVar) {
        b(y, eVar, If());
        return y;
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> b(float f2) {
        if (f2 < 0.0f || f2 > 1.0f) {
            throw new IllegalArgumentException("sizeMultiplier must be between 0 and 1");
        }
        this.Ac = Float.valueOf(f2);
        return this;
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> b(@Nullable j<TranscodeType> jVar) {
        this.yc = jVar;
        return this;
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> b(@NonNull f fVar) {
        com.bumptech.glide.util.i.checkNotNull(fVar);
        this.vc = If().b(fVar);
        return this;
    }

    @Deprecated
    @CheckResult
    public j<TranscodeType> b(@Nullable URL url) {
        t(url);
        return this;
    }

    @Deprecated
    @CheckResult
    public <Y extends com.bumptech.glide.request.target.o<File>> Y b(@NonNull Y y) {
        return Hf().c(y);
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> c(@Nullable Uri uri) {
        t(uri);
        return this;
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> c(@Nullable byte[] bArr) {
        t(bArr);
        if (!this.vc.mh()) {
            this = b(f.b(o.NONE));
        }
        return !this.vc.ph() ? this.b(f.r(true)) : this;
    }

    @NonNull
    public <Y extends com.bumptech.glide.request.target.o<TranscodeType>> Y c(@NonNull Y y) {
        return a(y, null);
    }

    @CheckResult
    public j<TranscodeType> clone() {
        try {
            j<TranscodeType> jVar = (j) super.clone();
            jVar.vc = jVar.vc.clone();
            jVar.wc = jVar.wc.clone();
            return jVar;
        } catch (CloneNotSupportedException e2) {
            throw new RuntimeException(e2);
        }
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> g(@Nullable Bitmap bitmap) {
        t(bitmap);
        return b(f.b(o.NONE));
    }

    @Deprecated
    @CheckResult
    public b<File> g(int i, int i2) {
        return Hf().j(i, i2);
    }

    @Deprecated
    public b<TranscodeType> h(int i, int i2) {
        return j(i, i2);
    }

    @NonNull
    public com.bumptech.glide.request.target.o<TranscodeType> i(int i, int i2) {
        return c((Y) com.bumptech.glide.request.target.l.a(this.da, i, i2));
    }

    @NonNull
    public b<TranscodeType> j(int i, int i2) {
        RequestFutureTarget requestFutureTarget = new RequestFutureTarget(this.Eb.Y(), i, i2);
        if (l.Jh()) {
            this.Eb.Y().post(new h(this, requestFutureTarget));
        } else {
            a((Y) requestFutureTarget, (e<TranscodeType>) requestFutureTarget);
        }
        return requestFutureTarget;
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> load(@Nullable Object obj) {
        t(obj);
        return this;
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> load(@Nullable String str) {
        t(str);
        return this;
    }

    @NonNull
    public com.bumptech.glide.request.target.o<TranscodeType> preload() {
        return i(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }
}
