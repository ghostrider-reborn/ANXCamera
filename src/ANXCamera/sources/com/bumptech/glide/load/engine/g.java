package com.bumptech.glide.load.engine;

import com.bumptech.glide.Priority;
import com.bumptech.glide.Registry;
import com.bumptech.glide.e;
import com.bumptech.glide.load.a;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.DecodeJob;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.j;
import com.bumptech.glide.load.model.t;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: DecodeHelper */
final class g<Transcode> {
    private Class<?> Be;
    private DecodeJob.d Ce;
    private boolean De;
    private e Eb;
    private boolean Ee;
    private o Fe;
    private boolean Ge;
    private boolean He;
    private Map<Class<?>, j<?>> Ld;
    private int height;
    private Object model;
    private com.bumptech.glide.load.g options;
    private Priority priority;
    private c signature;
    private final List<c> te = new ArrayList();
    private Class<Transcode> uc;
    private int width;
    private final List<t.a<?>> ze = new ArrayList();

    g() {
    }

    /* access modifiers changed from: package-private */
    public b V() {
        return this.Eb.V();
    }

    /* access modifiers changed from: package-private */
    public <R> void a(e eVar, Object obj, c cVar, int i, int i2, o oVar, Class<?> cls, Class<R> cls2, Priority priority2, com.bumptech.glide.load.g gVar, Map<Class<?>, j<?>> map, boolean z, boolean z2, DecodeJob.d dVar) {
        this.Eb = eVar;
        this.model = obj;
        this.signature = cVar;
        this.width = i;
        this.height = i2;
        this.Fe = oVar;
        this.Be = cls;
        this.Ce = dVar;
        this.uc = cls2;
        this.priority = priority2;
        this.options = gVar;
        this.Ld = map;
        this.Ge = z;
        this.He = z2;
    }

    /* access modifiers changed from: package-private */
    public <Data> x<Data, ?, Transcode> c(Class<Data> cls) {
        return this.Eb.getRegistry().a(cls, this.Be, this.uc);
    }

    /* access modifiers changed from: package-private */
    public <Z> i<Z> c(A<Z> a2) {
        return this.Eb.getRegistry().c(a2);
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.Eb = null;
        this.model = null;
        this.signature = null;
        this.Be = null;
        this.uc = null;
        this.options = null;
        this.priority = null;
        this.Ld = null;
        this.Fe = null;
        this.ze.clear();
        this.De = false;
        this.te.clear();
        this.Ee = false;
    }

    /* access modifiers changed from: package-private */
    public <Z> j<Z> d(Class<Z> cls) {
        j<Z> jVar = this.Ld.get(cls);
        if (jVar == null) {
            Iterator<Map.Entry<Class<?>, j<?>>> it = this.Ld.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry next = it.next();
                if (((Class) next.getKey()).isAssignableFrom(cls)) {
                    jVar = (j) next.getValue();
                    break;
                }
            }
        }
        if (jVar != null) {
            return jVar;
        }
        if (!this.Ld.isEmpty() || !this.Ge) {
            return com.bumptech.glide.load.b.b.get();
        }
        throw new IllegalArgumentException("Missing transformation for " + cls + ". If you wish to ignore unknown resource types, use the optional transformation methods.");
    }

    /* access modifiers changed from: package-private */
    public boolean d(A<?> a2) {
        return this.Eb.getRegistry().d(a2);
    }

    /* access modifiers changed from: package-private */
    public List<c> dg() {
        if (!this.Ee) {
            this.Ee = true;
            this.te.clear();
            List<t.a<?>> fg = fg();
            int size = fg.size();
            for (int i = 0; i < size; i++) {
                t.a aVar = fg.get(i);
                if (!this.te.contains(aVar.we)) {
                    this.te.add(aVar.we);
                }
                for (int i2 = 0; i2 < aVar.ai.size(); i2++) {
                    if (!this.te.contains(aVar.ai.get(i2))) {
                        this.te.add(aVar.ai.get(i2));
                    }
                }
            }
        }
        return this.te;
    }

    /* access modifiers changed from: package-private */
    public List<t<File, ?>> e(File file) throws Registry.NoModelLoaderAvailableException {
        return this.Eb.getRegistry().j(file);
    }

    /* access modifiers changed from: package-private */
    public boolean e(c cVar) {
        List<t.a<?>> fg = fg();
        int size = fg.size();
        for (int i = 0; i < size; i++) {
            if (fg.get(i).we.equals(cVar)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean e(Class<?> cls) {
        return c(cls) != null;
    }

    /* access modifiers changed from: package-private */
    public o eg() {
        return this.Fe;
    }

    /* access modifiers changed from: package-private */
    public List<t.a<?>> fg() {
        if (!this.De) {
            this.De = true;
            this.ze.clear();
            List j = this.Eb.getRegistry().j(this.model);
            int size = j.size();
            for (int i = 0; i < size; i++) {
                t.a a2 = ((t) j.get(i)).a(this.model, this.width, this.height, this.options);
                if (a2 != null) {
                    this.ze.add(a2);
                }
            }
        }
        return this.ze;
    }

    /* access modifiers changed from: package-private */
    public int getHeight() {
        return this.height;
    }

    /* access modifiers changed from: package-private */
    public com.bumptech.glide.load.g getOptions() {
        return this.options;
    }

    /* access modifiers changed from: package-private */
    public Priority getPriority() {
        return this.priority;
    }

    /* access modifiers changed from: package-private */
    public c getSignature() {
        return this.signature;
    }

    /* access modifiers changed from: package-private */
    public int getWidth() {
        return this.width;
    }

    /* access modifiers changed from: package-private */
    public Class<?> gg() {
        return this.model.getClass();
    }

    /* access modifiers changed from: package-private */
    public List<Class<?>> hg() {
        return this.Eb.getRegistry().b(this.model.getClass(), this.Be, this.uc);
    }

    /* access modifiers changed from: package-private */
    public Class<?> ig() {
        return this.uc;
    }

    /* access modifiers changed from: package-private */
    public boolean jg() {
        return this.He;
    }

    /* access modifiers changed from: package-private */
    public <X> a<X> l(X x) throws Registry.NoSourceEncoderAvailableException {
        return this.Eb.getRegistry().l(x);
    }

    /* access modifiers changed from: package-private */
    public com.bumptech.glide.load.engine.a.a n() {
        return this.Ce.n();
    }
}
