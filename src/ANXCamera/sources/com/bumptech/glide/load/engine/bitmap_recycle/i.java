package com.bumptech.glide.load.engine.bitmap_recycle;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/* compiled from: LruArrayPool */
public final class i implements b {
    private static final int DEFAULT_SIZE = 4194304;
    @VisibleForTesting
    static final int MAX_OVER_SIZE_MULTIPLE = 8;
    private static final int iq = 2;
    private final g<a, Object> ii;
    private final b ir;
    private final Map<Class<?>, NavigableMap<Integer, Integer>> iu;
    private final Map<Class<?>, a<?>> iv;
    private int iz;
    private final int maxSize;

    /* compiled from: LruArrayPool */
    private static final class a implements l {
        private final b iA;
        private Class<?> iB;
        int size;

        a(b bVar) {
            this.iA = bVar;
        }

        public void bO() {
            this.iA.a(this);
        }

        /* access modifiers changed from: package-private */
        public void d(int i, Class<?> cls) {
            this.size = i;
            this.iB = cls;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return this.size == aVar.size && this.iB == aVar.iB;
        }

        public int hashCode() {
            return (31 * this.size) + (this.iB != null ? this.iB.hashCode() : 0);
        }

        public String toString() {
            return "Key{size=" + this.size + "array=" + this.iB + '}';
        }
    }

    /* compiled from: LruArrayPool */
    private static final class b extends c<a> {
        b() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: bV */
        public a bQ() {
            return new a(this);
        }

        /* access modifiers changed from: package-private */
        public a e(int i, Class<?> cls) {
            a aVar = (a) bR();
            aVar.d(i, cls);
            return aVar;
        }
    }

    @VisibleForTesting
    public i() {
        this.ii = new g<>();
        this.ir = new b();
        this.iu = new HashMap();
        this.iv = new HashMap();
        this.maxSize = 4194304;
    }

    public i(int i) {
        this.ii = new g<>();
        this.ir = new b();
        this.iu = new HashMap();
        this.iv = new HashMap();
        this.maxSize = i;
    }

    @Nullable
    private <T> T a(a aVar) {
        return this.ii.b(aVar);
    }

    private <T> T a(a aVar, Class<T> cls) {
        a<T> h = h(cls);
        T a2 = a(aVar);
        if (a2 != null) {
            this.iz -= h.q(a2) * h.bM();
            c(h.q(a2), cls);
        }
        if (a2 != null) {
            return a2;
        }
        if (Log.isLoggable(h.getTag(), 2)) {
            Log.v(h.getTag(), "Allocated " + aVar.size + " bytes");
        }
        return h.newArray(aVar.size);
    }

    private boolean a(int i, Integer num) {
        return num != null && (bS() || num.intValue() <= 8 * i);
    }

    private boolean bS() {
        return this.iz == 0 || this.maxSize / this.iz >= 2;
    }

    private void bT() {
        w(this.maxSize);
    }

    private void c(int i, Class<?> cls) {
        NavigableMap<Integer, Integer> g = g(cls);
        Integer num = (Integer) g.get(Integer.valueOf(i));
        if (num == null) {
            throw new NullPointerException("Tried to decrement empty size, size: " + i + ", this: " + this);
        } else if (num.intValue() == 1) {
            g.remove(Integer.valueOf(i));
        } else {
            g.put(Integer.valueOf(i), Integer.valueOf(num.intValue() - 1));
        }
    }

    private NavigableMap<Integer, Integer> g(Class<?> cls) {
        NavigableMap<Integer, Integer> navigableMap = this.iu.get(cls);
        if (navigableMap != null) {
            return navigableMap;
        }
        TreeMap treeMap = new TreeMap();
        this.iu.put(cls, treeMap);
        return treeMap;
    }

    private <T> a<T> h(Class<T> cls) {
        a<T> aVar = this.iv.get(cls);
        if (aVar == null) {
            if (cls.equals(int[].class)) {
                aVar = new h();
            } else if (cls.equals(byte[].class)) {
                aVar = new f();
            } else {
                throw new IllegalArgumentException("No array pool found for: " + cls.getSimpleName());
            }
            this.iv.put(cls, aVar);
        }
        return aVar;
    }

    private <T> a<T> r(T t) {
        return h(t.getClass());
    }

    private boolean v(int i) {
        return i <= this.maxSize / 2;
    }

    private void w(int i) {
        while (this.iz > i) {
            Object removeLast = this.ii.removeLast();
            com.bumptech.glide.util.i.checkNotNull(removeLast);
            a r = r(removeLast);
            this.iz -= r.q(removeLast) * r.bM();
            c(r.q(removeLast), removeLast.getClass());
            if (Log.isLoggable(r.getTag(), 2)) {
                Log.v(r.getTag(), "evicted: " + r.q(removeLast));
            }
        }
    }

    public synchronized void P() {
        w(0);
    }

    public synchronized <T> T a(int i, Class<T> cls) {
        Integer ceilingKey;
        ceilingKey = g(cls).ceilingKey(Integer.valueOf(i));
        return a(a(i, ceilingKey) ? this.ir.e(ceilingKey.intValue(), cls) : this.ir.e(i, cls), cls);
    }

    @Deprecated
    public <T> void a(T t, Class<T> cls) {
        put(t);
    }

    public synchronized <T> T b(int i, Class<T> cls) {
        return a(this.ir.e(i, cls), cls);
    }

    /* access modifiers changed from: package-private */
    public int bU() {
        int i = 0;
        for (Class next : this.iu.keySet()) {
            for (Integer num : this.iu.get(next).keySet()) {
                i += num.intValue() * ((Integer) this.iu.get(next).get(num)).intValue() * h(next).bM();
            }
        }
        return i;
    }

    public synchronized <T> void put(T t) {
        Class<?> cls = t.getClass();
        a<?> h = h(cls);
        int q = h.q(t);
        int bM = h.bM() * q;
        if (v(bM)) {
            a e = this.ir.e(q, cls);
            this.ii.a(e, t);
            NavigableMap<Integer, Integer> g = g(cls);
            Integer num = (Integer) g.get(Integer.valueOf(e.size));
            Integer valueOf = Integer.valueOf(e.size);
            int i = 1;
            if (num != null) {
                i = 1 + num.intValue();
            }
            g.put(valueOf, Integer.valueOf(i));
            this.iz += bM;
            bT();
        }
    }

    public synchronized void trimMemory(int i) {
        if (i >= 40) {
            try {
                P();
            } catch (Throwable th) {
                throw th;
            }
        } else if (i >= 20 || i == 15) {
            w(this.maxSize / 2);
        }
    }
}
