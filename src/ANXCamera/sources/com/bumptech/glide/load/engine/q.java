package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.d;
import com.bumptech.glide.load.model.m;
import java.io.File;
import java.util.List;

/* compiled from: ResourceCacheGenerator */
class q implements d.a<Object>, d {
    private final e<?> fH;
    private final d.a fI;
    private int fJ;
    private c fK;
    private List<m<File, ?>> fL;
    private int fM;
    private volatile m.a<?> fN;
    private File fO;
    private int hT = -1;
    private r hU;

    q(e<?> eVar, d.a aVar) {
        this.fH = eVar;
        this.fI = aVar;
    }

    private boolean aU() {
        return this.fM < this.fL.size();
    }

    public boolean aT() {
        List<c> bh = this.fH.bh();
        boolean z = false;
        if (bh.isEmpty()) {
            return false;
        }
        List<Class<?>> be = this.fH.be();
        if (be.isEmpty() && File.class.equals(this.fH.bc())) {
            return false;
        }
        while (true) {
            if (this.fL == null || !aU()) {
                this.hT++;
                if (this.hT >= be.size()) {
                    this.fJ++;
                    if (this.fJ >= bh.size()) {
                        return false;
                    }
                    this.hT = 0;
                }
                c cVar = bh.get(this.fJ);
                Class cls = be.get(this.hT);
                r rVar = new r(this.fH.M(), cVar, this.fH.bb(), this.fH.getWidth(), this.fH.getHeight(), this.fH.f(cls), cls, this.fH.ba());
                this.hU = rVar;
                this.fO = this.fH.aX().e(this.hU);
                if (this.fO != null) {
                    this.fK = cVar;
                    this.fL = this.fH.g(this.fO);
                    this.fM = 0;
                }
            } else {
                this.fN = null;
                while (!z && aU()) {
                    List<m<File, ?>> list = this.fL;
                    int i = this.fM;
                    this.fM = i + 1;
                    this.fN = list.get(i).b(this.fO, this.fH.getWidth(), this.fH.getHeight(), this.fH.ba());
                    if (this.fN != null && this.fH.d(this.fN.kZ.aK())) {
                        this.fN.kZ.a(this.fH.aZ(), this);
                        z = true;
                    }
                }
                return z;
            }
        }
    }

    public void b(@NonNull Exception exc) {
        this.fI.a(this.hU, exc, this.fN.kZ, DataSource.RESOURCE_DISK_CACHE);
    }

    public void cancel() {
        m.a<?> aVar = this.fN;
        if (aVar != null) {
            aVar.kZ.cancel();
        }
    }

    public void n(Object obj) {
        this.fI.a(this.fK, obj, this.fN.kZ, DataSource.RESOURCE_DISK_CACHE, this.hU);
    }
}
