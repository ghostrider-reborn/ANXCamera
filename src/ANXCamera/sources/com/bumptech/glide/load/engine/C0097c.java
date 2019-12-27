package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.f;
import com.bumptech.glide.load.model.t;
import java.io.File;
import java.util.List;

/* renamed from: com.bumptech.glide.load.engine.c  reason: case insensitive filesystem */
/* compiled from: DataCacheGenerator */
class C0097c implements f, d.a<Object> {
    private File Ae;
    private final f.a cb;
    private final List<c> te;
    private final g<?> ue;
    private int ve;
    private c we;
    private List<t<File, ?>> xe;
    private int ye;
    private volatile t.a<?> ze;

    C0097c(g<?> gVar, f.a aVar) {
        this(gVar.dg(), gVar, aVar);
    }

    C0097c(List<c> list, g<?> gVar, f.a aVar) {
        this.ve = -1;
        this.te = list;
        this.ue = gVar;
        this.cb = aVar;
    }

    private boolean ek() {
        return this.ye < this.xe.size();
    }

    public void b(@NonNull Exception exc) {
        this.cb.a(this.we, exc, this.ze.bi, DataSource.DATA_DISK_CACHE);
    }

    public void b(Object obj) {
        this.cb.a(this.we, obj, this.ze.bi, DataSource.DATA_DISK_CACHE, this.we);
    }

    public void cancel() {
        t.a<?> aVar = this.ze;
        if (aVar != null) {
            aVar.bi.cancel();
        }
    }

    public boolean q() {
        while (true) {
            boolean z = false;
            if (this.xe == null || !ek()) {
                this.ve++;
                if (this.ve >= this.te.size()) {
                    return false;
                }
                c cVar = this.te.get(this.ve);
                this.Ae = this.ue.n().b(new C0098d(cVar, this.ue.getSignature()));
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
