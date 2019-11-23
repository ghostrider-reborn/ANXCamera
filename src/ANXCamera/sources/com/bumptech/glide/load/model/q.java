package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.model.m;
import com.bumptech.glide.util.i;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: MultiModelLoaderFactory */
public class q {
    private static final c lh = new c();
    private static final m<Object, Object> li = new a();
    private final Pools.Pool<List<Throwable>> cu;
    private final List<b<?, ?>> lj;
    private final c lk;
    private final Set<b<?, ?>> ll;

    /* compiled from: MultiModelLoaderFactory */
    private static class a implements m<Object, Object> {
        a() {
        }

        @Nullable
        public m.a<Object> b(@NonNull Object obj, int i, int i2, @NonNull f fVar) {
            return null;
        }

        public boolean t(@NonNull Object obj) {
            return false;
        }
    }

    /* compiled from: MultiModelLoaderFactory */
    private static class b<Model, Data> {
        final Class<Data> dataClass;
        private final Class<Model> lm;
        final n<? extends Model, ? extends Data> ln;

        public b(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull n<? extends Model, ? extends Data> nVar) {
            this.lm = cls;
            this.dataClass = cls2;
            this.ln = nVar;
        }

        public boolean d(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
            return m(cls) && this.dataClass.isAssignableFrom(cls2);
        }

        public boolean m(@NonNull Class<?> cls) {
            return this.lm.isAssignableFrom(cls);
        }
    }

    /* compiled from: MultiModelLoaderFactory */
    static class c {
        c() {
        }

        @NonNull
        public <Model, Data> p<Model, Data> a(@NonNull List<m<Model, Data>> list, @NonNull Pools.Pool<List<Throwable>> pool) {
            return new p<>(list, pool);
        }
    }

    public q(@NonNull Pools.Pool<List<Throwable>> pool) {
        this(pool, lh);
    }

    @VisibleForTesting
    q(@NonNull Pools.Pool<List<Throwable>> pool, @NonNull c cVar) {
        this.lj = new ArrayList();
        this.ll = new HashSet();
        this.cu = pool;
        this.lk = cVar;
    }

    @NonNull
    private <Model, Data> n<Model, Data> a(@NonNull b<?, ?> bVar) {
        return bVar.ln;
    }

    private <Model, Data> void a(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull n<? extends Model, ? extends Data> nVar, boolean z) {
        this.lj.add(z ? this.lj.size() : 0, new b(cls, cls2, nVar));
    }

    @NonNull
    private <Model, Data> m<Model, Data> b(@NonNull b<?, ?> bVar) {
        return (m) i.checkNotNull(bVar.ln.a(this));
    }

    @NonNull
    private static <Model, Data> m<Model, Data> cM() {
        return li;
    }

    @NonNull
    public synchronized <Model, Data> m<Model, Data> b(@NonNull Class<Model> cls, @NonNull Class<Data> cls2) {
        try {
            ArrayList arrayList = new ArrayList();
            boolean z = false;
            for (b next : this.lj) {
                if (this.ll.contains(next)) {
                    z = true;
                } else if (next.d(cls, cls2)) {
                    this.ll.add(next);
                    arrayList.add(b(next));
                    this.ll.remove(next);
                }
            }
            if (arrayList.size() > 1) {
                return this.lk.a(arrayList, this.cu);
            } else if (arrayList.size() == 1) {
                return (m) arrayList.get(0);
            } else if (z) {
                return cM();
            } else {
                throw new Registry.NoModelLoaderAvailableException(cls, cls2);
            }
        } catch (Throwable th) {
            this.ll.clear();
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public synchronized <Model, Data> List<n<? extends Model, ? extends Data>> c(@NonNull Class<Model> cls, @NonNull Class<Data> cls2) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        Iterator<b<?, ?>> it = this.lj.iterator();
        while (it.hasNext()) {
            b next = it.next();
            if (next.d(cls, cls2)) {
                it.remove();
                arrayList.add(a(next));
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    public synchronized <Model, Data> void d(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull n<? extends Model, ? extends Data> nVar) {
        a(cls, cls2, nVar, true);
    }

    /* access modifiers changed from: package-private */
    public synchronized <Model, Data> void e(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull n<? extends Model, ? extends Data> nVar) {
        a(cls, cls2, nVar, false);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public synchronized <Model, Data> List<n<? extends Model, ? extends Data>> g(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull n<? extends Model, ? extends Data> nVar) {
        List<n<? extends Model, ? extends Data>> c2;
        c2 = c(cls, cls2);
        d(cls, cls2, nVar);
        return c2;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public synchronized List<Class<?>> i(@NonNull Class<?> cls) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (b next : this.lj) {
            if (!arrayList.contains(next.dataClass) && next.m(cls)) {
                arrayList.add(next.dataClass);
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public synchronized <Model> List<m<Model, ?>> l(@NonNull Class<Model> cls) {
        ArrayList arrayList;
        try {
            arrayList = new ArrayList();
            for (b next : this.lj) {
                if (!this.ll.contains(next)) {
                    if (next.m(cls)) {
                        this.ll.add(next);
                        arrayList.add(b(next));
                        this.ll.remove(next);
                    }
                }
            }
        } catch (Throwable th) {
            this.ll.clear();
            throw th;
        }
        return arrayList;
    }
}
