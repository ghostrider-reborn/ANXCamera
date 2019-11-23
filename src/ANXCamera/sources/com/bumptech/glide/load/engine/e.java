package com.bumptech.glide.load.engine;

import com.bumptech.glide.Priority;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.DecodeJob;
import com.bumptech.glide.load.engine.a.a;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.h;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.m;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: DecodeHelper */
final class e<Transcode> {
    private com.bumptech.glide.e bp;
    private Class<Transcode> cw;
    private Object cz;
    private final List<c> fG = new ArrayList();
    private c fP;
    private f fR;
    private final List<m.a<?>> fS = new ArrayList();
    private Class<?> fT;
    private DecodeJob.d fU;
    private Map<Class<?>, i<?>> fV;
    private boolean fW;
    private boolean fX;
    private Priority fY;
    private g fZ;
    private boolean ga;
    private boolean gb;
    private int height;
    private int width;

    e() {
    }

    /* access modifiers changed from: package-private */
    public b M() {
        return this.bp.M();
    }

    /* access modifiers changed from: package-private */
    public <R> void a(com.bumptech.glide.e eVar, Object obj, c cVar, int i, int i2, g gVar, Class<?> cls, Class<R> cls2, Priority priority, f fVar, Map<Class<?>, i<?>> map, boolean z, boolean z2, DecodeJob.d dVar) {
        this.bp = eVar;
        this.cz = obj;
        this.fP = cVar;
        this.width = i;
        this.height = i2;
        this.fZ = gVar;
        this.fT = cls;
        this.fU = dVar;
        this.cw = cls2;
        this.fY = priority;
        this.fR = fVar;
        this.fV = map;
        this.ga = z;
        this.gb = z2;
    }

    /* access modifiers changed from: package-private */
    public boolean a(p<?> pVar) {
        return this.bp.S().a(pVar);
    }

    /* access modifiers changed from: package-private */
    public a aX() {
        return this.fU.aX();
    }

    /* access modifiers changed from: package-private */
    public g aY() {
        return this.fZ;
    }

    /* access modifiers changed from: package-private */
    public Priority aZ() {
        return this.fY;
    }

    /* access modifiers changed from: package-private */
    public <Z> h<Z> b(p<Z> pVar) {
        return this.bp.S().b(pVar);
    }

    /* access modifiers changed from: package-private */
    public f ba() {
        return this.fR;
    }

    /* access modifiers changed from: package-private */
    public c bb() {
        return this.fP;
    }

    /* access modifiers changed from: package-private */
    public Class<?> bc() {
        return this.cw;
    }

    /* access modifiers changed from: package-private */
    public Class<?> bd() {
        return this.cz.getClass();
    }

    /* access modifiers changed from: package-private */
    public List<Class<?>> be() {
        return this.bp.S().c(this.cz.getClass(), this.fT, this.cw);
    }

    /* access modifiers changed from: package-private */
    public boolean bf() {
        return this.gb;
    }

    /* access modifiers changed from: package-private */
    public List<m.a<?>> bg() {
        if (!this.fW) {
            this.fW = true;
            this.fS.clear();
            List i = this.bp.S().i(this.cz);
            int size = i.size();
            for (int i2 = 0; i2 < size; i2++) {
                m.a b2 = ((m) i.get(i2)).b(this.cz, this.width, this.height, this.fR);
                if (b2 != null) {
                    this.fS.add(b2);
                }
            }
        }
        return this.fS;
    }

    /* access modifiers changed from: package-private */
    public List<c> bh() {
        if (!this.fX) {
            this.fX = true;
            this.fG.clear();
            List<m.a<?>> bg = bg();
            int size = bg.size();
            for (int i = 0; i < size; i++) {
                m.a aVar = bg.get(i);
                if (!this.fG.contains(aVar.fK)) {
                    this.fG.add(aVar.fK);
                }
                for (int i2 = 0; i2 < aVar.kY.size(); i2++) {
                    if (!this.fG.contains(aVar.kY.get(i2))) {
                        this.fG.add(aVar.kY.get(i2));
                    }
                }
            }
        }
        return this.fG;
    }

    /* access modifiers changed from: package-private */
    public boolean c(c cVar) {
        List<m.a<?>> bg = bg();
        int size = bg.size();
        for (int i = 0; i < size; i++) {
            if (bg.get(i).fK.equals(cVar)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.bp = null;
        this.cz = null;
        this.fP = null;
        this.fT = null;
        this.cw = null;
        this.fR = null;
        this.fY = null;
        this.fV = null;
        this.fZ = null;
        this.fS.clear();
        this.fW = false;
        this.fG.clear();
        this.fX = false;
    }

    /* access modifiers changed from: package-private */
    public boolean d(Class<?> cls) {
        return e(cls) != null;
    }

    /* access modifiers changed from: package-private */
    public <Data> n<Data, ?, Transcode> e(Class<Data> cls) {
        return this.bp.S().a(cls, this.fT, this.cw);
    }

    /* access modifiers changed from: package-private */
    public <Z> i<Z> f(Class<Z> cls) {
        i<Z> iVar = this.fV.get(cls);
        if (iVar == null) {
            Iterator<Map.Entry<Class<?>, i<?>>> it = this.fV.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry next = it.next();
                if (((Class) next.getKey()).isAssignableFrom(cls)) {
                    iVar = (i) next.getValue();
                    break;
                }
            }
        }
        if (iVar != null) {
            return iVar;
        }
        if (!this.fV.isEmpty() || !this.ga) {
            return com.bumptech.glide.load.resource.b.cP();
        }
        throw new IllegalArgumentException("Missing transformation for " + cls + ". If you wish to ignore unknown resource types, use the optional transformation methods.");
    }

    /* access modifiers changed from: package-private */
    public <X> com.bumptech.glide.load.a<X> g(X x) throws Registry.NoSourceEncoderAvailableException {
        return this.bp.S().g(x);
    }

    /* access modifiers changed from: package-private */
    public List<m<File, ?>> g(File file) throws Registry.NoModelLoaderAvailableException {
        return this.bp.S().i(file);
    }

    /* access modifiers changed from: package-private */
    public int getHeight() {
        return this.height;
    }

    /* access modifiers changed from: package-private */
    public int getWidth() {
        return this.width;
    }
}
