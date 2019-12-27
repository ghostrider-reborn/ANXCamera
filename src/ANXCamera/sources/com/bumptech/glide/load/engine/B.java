package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.f;
import com.bumptech.glide.load.model.t;
import java.io.File;
import java.util.List;

/* compiled from: ResourceCacheGenerator */
class B implements f, d.a<Object> {
    private File Ae;
    private int Sf = -1;
    private C Tf;
    private final f.a cb;
    private final g<?> ue;
    private int ve;
    private c we;
    private List<t<File, ?>> xe;
    private int ye;
    private volatile t.a<?> ze;

    B(g<?> gVar, f.a aVar) {
        this.ue = gVar;
        this.cb = aVar;
    }

    private boolean ek() {
        return this.ye < this.xe.size();
    }

    public void b(@NonNull Exception exc) {
        this.cb.a(this.Tf, exc, this.ze.bi, DataSource.RESOURCE_DISK_CACHE);
    }

    public void b(Object obj) {
        this.cb.a(this.we, obj, this.ze.bi, DataSource.RESOURCE_DISK_CACHE, this.Tf);
    }

    public void cancel() {
        t.a<?> aVar = this.ze;
        if (aVar != null) {
            aVar.bi.cancel();
        }
    }

    public boolean q() {
        List<c> dg = this.ue.dg();
        boolean z = false;
        if (dg.isEmpty()) {
            return false;
        }
        List<Class<?>> hg = this.ue.hg();
        if (hg.isEmpty() && File.class.equals(this.ue.ig())) {
            return false;
        }
        while (true) {
            if (this.xe == null || !ek()) {
                this.Sf++;
                if (this.Sf >= hg.size()) {
                    this.ve++;
                    if (this.ve >= dg.size()) {
                        return false;
                    }
                    this.Sf = 0;
                }
                c cVar = dg.get(this.ve);
                Class cls = hg.get(this.Sf);
                C c2 = new C(this.ue.V(), cVar, this.ue.getSignature(), this.ue.getWidth(), this.ue.getHeight(), this.ue.d(cls), cls, this.ue.getOptions());
                this.Tf = c2;
                this.Ae = this.ue.n().b(this.Tf);
                File file = this.Ae;
                if (file != null) {
                    this.we = cVar;
                    this.xe = this.ue.e(file);
                    this.ye = 0;
                }
            } else {
                this.ze = null;
                while (!z && ek()) {
                    List<t<File, ?>> list = this.xe;
                    int i = this.ye;
                    this.ye = i + 1;
                    this.ze = list.get(i).a(this.Ae, this.ue.getWidth(), this.ue.getHeight(), this.ue.getOptions());
                    if (this.ze != null && this.ue.e((Class<?>) this.ze.bi.M())) {
                        this.ze.bi.a(this.ue.getPriority(), this);
                        z = true;
                    }
                }
                return z;
            }
        }
    }
}
