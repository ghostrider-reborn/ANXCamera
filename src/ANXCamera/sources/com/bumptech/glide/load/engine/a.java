package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.d;
import com.bumptech.glide.load.model.m;
import java.io.File;
import java.util.List;

/* compiled from: DataCacheGenerator */
class a implements d.a<Object>, d {
    private final List<c> fG;
    private final e<?> fH;
    private final d.a fI;
    private int fJ;
    private c fK;
    private List<m<File, ?>> fL;
    private int fM;
    private volatile m.a<?> fN;
    private File fO;

    a(e<?> eVar, d.a aVar) {
        this(eVar.bh(), eVar, aVar);
    }

    a(List<c> list, e<?> eVar, d.a aVar) {
        this.fJ = -1;
        this.fG = list;
        this.fH = eVar;
        this.fI = aVar;
    }

    private boolean aU() {
        return this.fM < this.fL.size();
    }

    public boolean aT() {
        while (true) {
            boolean z = false;
            if (this.fL == null || !aU()) {
                this.fJ++;
                if (this.fJ >= this.fG.size()) {
                    return false;
                }
                c cVar = this.fG.get(this.fJ);
                this.fO = this.fH.aX().e(new b(cVar, this.fH.bb()));
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
        this.fI.a(this.fK, exc, this.fN.kZ, DataSource.DATA_DISK_CACHE);
    }

    public void cancel() {
        m.a<?> aVar = this.fN;
        if (aVar != null) {
            aVar.kZ.cancel();
        }
    }

    public void n(Object obj) {
        this.fI.a(this.fK, obj, this.fN.kZ, DataSource.DATA_DISK_CACHE, this.fK);
    }
}
