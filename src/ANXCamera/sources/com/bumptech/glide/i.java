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
import com.bumptech.glide.load.engine.g;
import com.bumptech.glide.manager.c;
import com.bumptech.glide.manager.d;
import com.bumptech.glide.manager.h;
import com.bumptech.glide.manager.m;
import com.bumptech.glide.manager.n;
import com.bumptech.glide.manager.o;
import com.bumptech.glide.request.f;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.util.k;
import java.io.File;
import java.net.URL;

/* compiled from: RequestManager */
public class i implements g<h<Drawable>>, com.bumptech.glide.manager.i {
    private static final f cL = f.p(Bitmap.class).eo();
    private static final f cM = f.p(com.bumptech.glide.load.resource.gif.b.class).eo();
    private static final f cv = f.a(g.gW).c(Priority.LOW).m(true);
    private final Handler bF;
    protected final c bj;
    final h cN;
    private final n cO;
    private final m cP;
    private final o cQ;
    private final Runnable cR;
    private final c cS;
    protected final Context context;
    private f cx;

    /* compiled from: RequestManager */
    private static class a extends ViewTarget<View, Object> {
        a(@NonNull View view) {
            super(view);
        }

        public void a(@NonNull Object obj, @Nullable com.bumptech.glide.request.a.f<? super Object> fVar) {
        }
    }

    /* compiled from: RequestManager */
    private static class b implements c.a {
        private final n cO;

        b(@NonNull n nVar) {
            this.cO = nVar;
        }

        public void d(boolean z) {
            if (z) {
                this.cO.dH();
            }
        }
    }

    public i(@NonNull c cVar, @NonNull h hVar, @NonNull m mVar, @NonNull Context context2) {
        this(cVar, hVar, mVar, new n(), cVar.N(), context2);
    }

    i(c cVar, h hVar, m mVar, n nVar, d dVar, Context context2) {
        this.cQ = new o();
        this.cR = new Runnable() {
            public void run() {
                i.this.cN.a(i.this);
            }
        };
        this.bF = new Handler(Looper.getMainLooper());
        this.bj = cVar;
        this.cN = hVar;
        this.cP = mVar;
        this.cO = nVar;
        this.context = context2;
        this.cS = dVar.a(context2.getApplicationContext(), new b(nVar));
        if (k.fr()) {
            this.bF.post(this.cR);
        } else {
            hVar.a(this);
        }
        hVar.a(this.cS);
        c(cVar.O().T());
        cVar.a(this);
    }

    private void d(@NonNull f fVar) {
        this.cx = this.cx.g(fVar);
    }

    private void e(@NonNull com.bumptech.glide.request.target.n<?> nVar) {
        if (!f(nVar) && !this.bj.a(nVar) && nVar.dS() != null) {
            com.bumptech.glide.request.c dS = nVar.dS();
            nVar.j((com.bumptech.glide.request.c) null);
            dS.clear();
        }
    }

    /* access modifiers changed from: package-private */
    public f T() {
        return this.cx;
    }

    /* access modifiers changed from: package-private */
    public void a(@NonNull com.bumptech.glide.request.target.n<?> nVar, @NonNull com.bumptech.glide.request.c cVar) {
        this.cQ.g(nVar);
        this.cO.a(cVar);
    }

    public void ad() {
        k.fo();
        this.cO.ad();
    }

    public void ae() {
        k.fo();
        this.cO.ae();
    }

    public void af() {
        k.fo();
        ad();
        for (i ad : this.cP.dz()) {
            ad.ad();
        }
    }

    public void ag() {
        k.fo();
        this.cO.ag();
    }

    public void ah() {
        k.fo();
        ag();
        for (i ag : this.cP.dz()) {
            ag.ag();
        }
    }

    @CheckResult
    @NonNull
    public h<Bitmap> ai() {
        return c(Bitmap.class).b(cL);
    }

    @CheckResult
    @NonNull
    public h<com.bumptech.glide.load.resource.gif.b> aj() {
        return c(com.bumptech.glide.load.resource.gif.b.class).b(cM);
    }

    @CheckResult
    @NonNull
    public h<Drawable> ak() {
        return c(Drawable.class);
    }

    @CheckResult
    @NonNull
    public h<File> al() {
        return c(File.class).b(cv);
    }

    @CheckResult
    @NonNull
    public h<File> am() {
        return c(File.class).b(f.i(true));
    }

    @CheckResult
    @NonNull
    /* renamed from: b */
    public h<Drawable> a(@Nullable Bitmap bitmap) {
        return ak().a(bitmap);
    }

    @CheckResult
    @NonNull
    /* renamed from: b */
    public h<Drawable> a(@Nullable Drawable drawable) {
        return ak().a(drawable);
    }

    @CheckResult
    @NonNull
    /* renamed from: b */
    public h<Drawable> a(@Nullable Uri uri) {
        return ak().a(uri);
    }

    @CheckResult
    @NonNull
    /* renamed from: b */
    public h<Drawable> a(@Nullable File file) {
        return ak().a(file);
    }

    @CheckResult
    @NonNull
    /* renamed from: b */
    public h<Drawable> a(@Nullable @RawRes @DrawableRes Integer num) {
        return ak().a(num);
    }

    @Deprecated
    @CheckResult
    /* renamed from: b */
    public h<Drawable> a(@Nullable URL url) {
        return ak().a(url);
    }

    @CheckResult
    @NonNull
    /* renamed from: b */
    public h<Drawable> a(@Nullable byte[] bArr) {
        return ak().a(bArr);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public <T> j<?, T> b(Class<T> cls) {
        return this.bj.O().b(cls);
    }

    public void b(@NonNull View view) {
        d((com.bumptech.glide.request.target.n<?>) new a(view));
    }

    @CheckResult
    @NonNull
    public <ResourceType> h<ResourceType> c(@NonNull Class<ResourceType> cls) {
        return new h<>(this.bj, this, cls, this.context);
    }

    /* access modifiers changed from: protected */
    public void c(@NonNull f fVar) {
        this.cx = fVar.clone().ep();
    }

    public void d(@Nullable final com.bumptech.glide.request.target.n<?> nVar) {
        if (nVar != null) {
            if (k.fq()) {
                e(nVar);
            } else {
                this.bF.post(new Runnable() {
                    public void run() {
                        i.this.d((com.bumptech.glide.request.target.n<?>) nVar);
                    }
                });
            }
        }
    }

    @NonNull
    public i e(@NonNull f fVar) {
        d(fVar);
        return this;
    }

    @NonNull
    public i f(@NonNull f fVar) {
        c(fVar);
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean f(@NonNull com.bumptech.glide.request.target.n<?> nVar) {
        com.bumptech.glide.request.c dS = nVar.dS();
        if (dS == null) {
            return true;
        }
        if (!this.cO.b(dS)) {
            return false;
        }
        this.cQ.h(nVar);
        nVar.j((com.bumptech.glide.request.c) null);
        return true;
    }

    public boolean isPaused() {
        k.fo();
        return this.cO.isPaused();
    }

    @CheckResult
    @NonNull
    /* renamed from: j */
    public h<Drawable> load(@Nullable Object obj) {
        return ak().load(obj);
    }

    @CheckResult
    @NonNull
    public h<File> l(@Nullable Object obj) {
        return al().load(obj);
    }

    @CheckResult
    @NonNull
    /* renamed from: n */
    public h<Drawable> m(@Nullable String str) {
        return ak().m(str);
    }

    public void onDestroy() {
        this.cQ.onDestroy();
        for (com.bumptech.glide.request.target.n<?> d : this.cQ.getAll()) {
            d(d);
        }
        this.cQ.clear();
        this.cO.dG();
        this.cN.b(this);
        this.cN.b(this.cS);
        this.bF.removeCallbacks(this.cR);
        this.bj.b(this);
    }

    public void onStart() {
        ag();
        this.cQ.onStart();
    }

    public void onStop() {
        ad();
        this.cQ.onStop();
    }

    public String toString() {
        return super.toString() + "{tracker=" + this.cO + ", treeNode=" + this.cP + "}";
    }
}
