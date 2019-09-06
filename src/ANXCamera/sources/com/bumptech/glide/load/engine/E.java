package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.a.d.a;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.model.t;
import com.bumptech.glide.util.e;
import java.util.Collections;
import java.util.List;

/* compiled from: SourceGenerator */
class E implements f, a<Object>, f.a {
    private static final String TAG = "SourceGenerator";
    private int Zf;
    private C0097c _f;
    private Object ag;
    private C0098d bg;
    private final f.a cb;
    private final g<?> ue;
    private volatile t.a<?> ze;

    E(g<?> gVar, f.a aVar) {
        this.ue = gVar;
        this.cb = aVar;
    }

    private boolean ek() {
        return this.Zf < this.ue.fg().size();
    }

    /* JADX INFO: finally extract failed */
    private void u(Object obj) {
        String str = TAG;
        long Gh = e.Gh();
        try {
            com.bumptech.glide.load.a l = this.ue.l(obj);
            C0099e eVar = new C0099e(l, obj, this.ue.getOptions());
            this.bg = new C0098d(this.ze.we, this.ue.getSignature());
            this.ue.n().a(this.bg, eVar);
            if (Log.isLoggable(str, 2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Finished encoding source to cache, key: ");
                sb.append(this.bg);
                sb.append(", data: ");
                sb.append(obj);
                sb.append(", encoder: ");
                sb.append(l);
                sb.append(", duration: ");
                sb.append(e.g(Gh));
                Log.v(str, sb.toString());
            }
            this.ze.bi.cleanup();
            this._f = new C0097c(Collections.singletonList(this.ze.we), this.ue, this);
        } catch (Throwable th) {
            this.ze.bi.cleanup();
            throw th;
        }
    }

    public void K() {
        throw new UnsupportedOperationException();
    }

    public void a(c cVar, Exception exc, d<?> dVar, DataSource dataSource) {
        this.cb.a(cVar, exc, dVar, this.ze.bi.r());
    }

    public void a(c cVar, Object obj, d<?> dVar, DataSource dataSource, c cVar2) {
        this.cb.a(cVar, obj, dVar, this.ze.bi.r(), cVar);
    }

    public void b(@NonNull Exception exc) {
        this.cb.a(this.bg, exc, this.ze.bi, this.ze.bi.r());
    }

    public void b(Object obj) {
        o eg = this.ue.eg();
        if (obj == null || !eg.a(this.ze.bi.r())) {
            this.cb.a(this.ze.we, obj, this.ze.bi, this.ze.bi.r(), this.bg);
            return;
        }
        this.ag = obj;
        this.cb.K();
    }

    public void cancel() {
        t.a<?> aVar = this.ze;
        if (aVar != null) {
            aVar.bi.cancel();
        }
    }

    public boolean q() {
        Object obj = this.ag;
        if (obj != null) {
            this.ag = null;
            u(obj);
        }
        C0097c cVar = this._f;
        if (cVar != null && cVar.q()) {
            return true;
        }
        this._f = null;
        this.ze = null;
        boolean z = false;
        while (!z && ek()) {
            List fg = this.ue.fg();
            int i = this.Zf;
            this.Zf = i + 1;
            this.ze = (t.a) fg.get(i);
            if (this.ze != null && (this.ue.eg().a(this.ze.bi.r()) || this.ue.e(this.ze.bi.M()))) {
                this.ze.bi.a(this.ue.getPriority(), this);
                z = true;
            }
        }
        return z;
    }
}
