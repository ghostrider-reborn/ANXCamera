package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.d;
import com.bumptech.glide.load.model.m;
import com.bumptech.glide.util.e;
import java.util.Collections;
import java.util.List;

/* compiled from: SourceGenerator */
class t implements d.a<Object>, d.a, d {
    private static final String TAG = "SourceGenerator";
    private final e<?> fH;
    private final d.a fI;
    private volatile m.a<?> fN;
    private int ia;
    private a ib;
    private Object ic;
    private b ie;

    t(e<?> eVar, d.a aVar) {
        this.fH = eVar;
        this.fI = aVar;
    }

    private boolean aU() {
        return this.ia < this.fH.bg().size();
    }

    /* JADX INFO: finally extract failed */
    private void p(Object obj) {
        long fn = e.fn();
        try {
            a<X> g = this.fH.g(obj);
            c cVar = new c(g, obj, this.fH.ba());
            this.ie = new b(this.fN.fK, this.fH.bb());
            this.fH.aX().a(this.ie, cVar);
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Finished encoding source to cache, key: " + this.ie + ", data: " + obj + ", encoder: " + g + ", duration: " + e.f(fn));
            }
            this.fN.kZ.cleanup();
            this.ib = new a(Collections.singletonList(this.fN.fK), this.fH, this);
        } catch (Throwable th) {
            this.fN.kZ.cleanup();
            throw th;
        }
    }

    public void a(c cVar, Exception exc, com.bumptech.glide.load.a.d<?> dVar, DataSource dataSource) {
        this.fI.a(cVar, exc, dVar, this.fN.kZ.aL());
    }

    public void a(c cVar, Object obj, com.bumptech.glide.load.a.d<?> dVar, DataSource dataSource, c cVar2) {
        this.fI.a(cVar, obj, dVar, this.fN.kZ.aL(), cVar);
    }

    public boolean aT() {
        if (this.ic != null) {
            Object obj = this.ic;
            this.ic = null;
            p(obj);
        }
        if (this.ib != null && this.ib.aT()) {
            return true;
        }
        this.ib = null;
        this.fN = null;
        boolean z = false;
        while (!z && aU()) {
            List<m.a<?>> bg = this.fH.bg();
            int i = this.ia;
            this.ia = i + 1;
            this.fN = bg.get(i);
            if (this.fN != null && (this.fH.aY().b(this.fN.kZ.aL()) || this.fH.d(this.fN.kZ.aK()))) {
                this.fN.kZ.a(this.fH.aZ(), this);
                z = true;
            }
        }
        return z;
    }

    public void aW() {
        throw new UnsupportedOperationException();
    }

    public void b(@NonNull Exception exc) {
        this.fI.a(this.ie, exc, this.fN.kZ, this.fN.kZ.aL());
    }

    public void cancel() {
        m.a<?> aVar = this.fN;
        if (aVar != null) {
            aVar.kZ.cancel();
        }
    }

    public void n(Object obj) {
        g aY = this.fH.aY();
        if (obj == null || !aY.b(this.fN.kZ.aL())) {
            this.fI.a(this.fN.fK, obj, this.fN.kZ, this.fN.kZ.aL(), this.ie);
            return;
        }
        this.ic = obj;
        this.fI.aW();
    }
}
