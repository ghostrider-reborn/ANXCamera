package com.bumptech.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CheckResult;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.view.View;
import com.bumptech.glide.load.engine.o;
import com.bumptech.glide.manager.c;
import com.bumptech.glide.manager.d;
import com.bumptech.glide.manager.i;
import com.bumptech.glide.manager.j;
import com.bumptech.glide.manager.p;
import com.bumptech.glide.manager.q;
import com.bumptech.glide.request.f;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.util.l;
import java.io.File;
import java.net.URL;

/* compiled from: RequestManager */
public class m implements j, g<j<Drawable>> {
    private static final f Ec = f.b(o.DATA).a(Priority.LOW).q(true);
    private static final f Kc = f.k(Bitmap.class).lock();
    private static final f Lc = f.k(com.bumptech.glide.load.resource.gif.b.class).lock();
    private final p Fc;
    private final com.bumptech.glide.manager.o Gc;
    private final q Hc;
    private final Runnable Ic;
    private final c Jc;
    protected final c Kb;
    final i aa;
    protected final Context context;
    private final Handler pa;
    private f vc;

    /* compiled from: RequestManager */
    private static class a extends ViewTarget<View, Object> {
        a(@NonNull View view) {
            super(view);
        }

        public void a(@NonNull Object obj, @Nullable com.bumptech.glide.request.a.f<? super Object> fVar) {
        }
    }

    /* compiled from: RequestManager */
    private static class b implements com.bumptech.glide.manager.c.a {
        private final p Fc;

        b(@NonNull p pVar) {
            this.Fc = pVar;
        }

        public void d(boolean z) {
            if (z) {
                this.Fc.Jg();
            }
        }
    }

    public m(@NonNull c cVar, @NonNull i iVar, @NonNull com.bumptech.glide.manager.o oVar, @NonNull Context context2) {
        this(cVar, iVar, oVar, new p(), cVar.Df(), context2);
    }

    m(c cVar, i iVar, com.bumptech.glide.manager.o oVar, p pVar, d dVar, Context context2) {
        this.Hc = new q();
        this.Ic = new k(this);
        this.pa = new Handler(Looper.getMainLooper());
        this.Kb = cVar;
        this.aa = iVar;
        this.Gc = oVar;
        this.Fc = pVar;
        this.context = context2;
        this.Jc = dVar.a(context2.getApplicationContext(), new b(pVar));
        if (l.Jh()) {
            this.pa.post(this.Ic);
        } else {
            iVar.b(this);
        }
        iVar.b(this.Jc);
        d(cVar.Ef().W());
        cVar.b(this);
    }

    private void e(@NonNull f fVar) {
        this.vc = this.vc.b(fVar);
    }

    private void g(@NonNull com.bumptech.glide.request.target.o<?> oVar) {
        if (!e(oVar) && !this.Kb.a(oVar) && oVar.getRequest() != null) {
            com.bumptech.glide.request.c request = oVar.getRequest();
            oVar.f(null);
            request.clear();
        }
    }

    @CheckResult
    @NonNull
    public j<Bitmap> Kf() {
        return b(Bitmap.class).b(Kc);
    }

    @CheckResult
    @NonNull
    public j<Drawable> Lf() {
        return b(Drawable.class);
    }

    @CheckResult
    @NonNull
    public j<File> Mf() {
        return b(File.class).b(f.r(true));
    }

    @CheckResult
    @NonNull
    public j<com.bumptech.glide.load.resource.gif.b> Nf() {
        return b(com.bumptech.glide.load.resource.gif.b.class).b(Lc);
    }

    @CheckResult
    @NonNull
    public j<File> Of() {
        return b(File.class).b(Ec);
    }

    public void Pf() {
        l.Ih();
        this.Fc.Pf();
    }

    public void Qf() {
        l.Ih();
        this.Fc.Qf();
    }

    public void Rf() {
        l.Ih();
        Qf();
        for (m Qf : this.Gc.H()) {
            Qf.Qf();
        }
    }

    public void Sf() {
        l.Ih();
        this.Fc.Sf();
    }

    public void Tf() {
        l.Ih();
        Sf();
        for (m Sf : this.Gc.H()) {
            Sf.Sf();
        }
    }

    /* access modifiers changed from: 0000 */
    public f W() {
        return this.vc;
    }

    @CheckResult
    @NonNull
    public j<Drawable> a(@Nullable Drawable drawable) {
        return Lf().a(drawable);
    }

    @CheckResult
    @NonNull
    public j<Drawable> a(@Nullable File file) {
        return Lf().a(file);
    }

    @CheckResult
    @NonNull
    public j<Drawable> a(@Nullable @RawRes @DrawableRes Integer num) {
        return Lf().a(num);
    }

    @NonNull
    public m a(@NonNull f fVar) {
        d(fVar);
        return this;
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public <T> n<?, T> a(Class<T> cls) {
        return this.Kb.Ef().a(cls);
    }

    /* access modifiers changed from: 0000 */
    public void a(@NonNull com.bumptech.glide.request.target.o<?> oVar, @NonNull com.bumptech.glide.request.c cVar) {
        this.Hc.f(oVar);
        this.Fc.i(cVar);
    }

    @CheckResult
    @NonNull
    public <ResourceType> j<ResourceType> b(@NonNull Class<ResourceType> cls) {
        return new j<>(this.Kb, this, cls, this.context);
    }

    @Deprecated
    @CheckResult
    public j<Drawable> b(@Nullable URL url) {
        return Lf().b(url);
    }

    @CheckResult
    @NonNull
    public j<Drawable> c(@Nullable Uri uri) {
        return Lf().c(uri);
    }

    @CheckResult
    @NonNull
    public j<Drawable> c(@Nullable byte[] bArr) {
        return Lf().c(bArr);
    }

    @NonNull
    public m c(@NonNull f fVar) {
        e(fVar);
        return this;
    }

    /* access modifiers changed from: protected */
    public void d(@NonNull f fVar) {
        this.vc = fVar.clone().Og();
    }

    public void d(@Nullable com.bumptech.glide.request.target.o<?> oVar) {
        if (oVar != null) {
            if (l.Kh()) {
                g(oVar);
            } else {
                this.pa.post(new l(this, oVar));
            }
        }
    }

    public void e(@NonNull View view) {
        d((com.bumptech.glide.request.target.o<?>) new a<Object>(view));
    }

    /* access modifiers changed from: 0000 */
    public boolean e(@NonNull com.bumptech.glide.request.target.o<?> oVar) {
        com.bumptech.glide.request.c request = oVar.getRequest();
        if (request == null) {
            return true;
        }
        if (!this.Fc.h(request)) {
            return false;
        }
        this.Hc.e(oVar);
        oVar.f(null);
        return true;
    }

    @CheckResult
    @NonNull
    public j<Drawable> g(@Nullable Bitmap bitmap) {
        return Lf().g(bitmap);
    }

    public boolean isPaused() {
        l.Ih();
        return this.Fc.isPaused();
    }

    @CheckResult
    @NonNull
    public j<Drawable> load(@Nullable Object obj) {
        return Lf().load(obj);
    }

    @CheckResult
    @NonNull
    public j<Drawable> load(@Nullable String str) {
        return Lf().load(str);
    }

    @CheckResult
    @NonNull
    public j<File> m(@Nullable Object obj) {
        return Of().load(obj);
    }

    public void onDestroy() {
        this.Hc.onDestroy();
        for (com.bumptech.glide.request.target.o d2 : this.Hc.getAll()) {
            d(d2);
        }
        this.Hc.clear();
        this.Fc.Ig();
        this.aa.a(this);
        this.aa.a(this.Jc);
        this.pa.removeCallbacks(this.Ic);
        this.Kb.c(this);
    }

    public void onStart() {
        Sf();
        this.Hc.onStart();
    }

    public void onStop() {
        Qf();
        this.Hc.onStop();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("{tracker=");
        sb.append(this.Fc);
        sb.append(", treeNode=");
        sb.append(this.Gc);
        sb.append("}");
        return sb.toString();
    }
}
