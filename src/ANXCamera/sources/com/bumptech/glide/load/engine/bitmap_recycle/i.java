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
    private static final int jg = 2;
    private final b cg;
    private final g<a, Object> dg;
    private final Map<Class<?>, NavigableMap<Integer, Integer>> gg;
    private final Map<Class<?>, a<?>> hg;
    private int ig;
    private final int maxSize;

    /* compiled from: LruArrayPool */
    private static final class a implements l {
        private Class<?> fg;
        private final b pool;
        int size;

        a(b bVar) {
            this.pool = bVar;
        }

        /* access modifiers changed from: package-private */
        public void c(int i, Class<?> cls) {
            this.size = i;
            this.fg = cls;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return this.size == aVar.size && this.fg == aVar.fg;
        }

        public int hashCode() {
            int i = this.size * 31;
            Class<?> cls = this.fg;
            return i + (cls != null ? cls.hashCode() : 0);
        }

        public String toString() {
            return "Key{size=" + this.size + "array=" + this.fg + '}';
        }

        public void u() {
            this.pool.a(this);
        }
    }

    /* compiled from: LruArrayPool */
    private static final class b extends c<a> {
        b() {
        }

        /* access modifiers changed from: package-private */
        public a a(int i, Class<?> cls) {
            a aVar = (a) get();
            aVar.c(i, cls);
            return aVar;
        }

        /* access modifiers changed from: protected */
        public a create() {
            return new a(this);
        }
    }

    @VisibleForTesting
    public i() {
        this.dg = new g<>();
        this.cg = new b();
        this.gg = new HashMap();
        this.hg = new HashMap();
        this.maxSize = 4194304;
    }

    public i(int i) {
        this.dg = new g<>();
        this.cg = new b();
        this.gg = new HashMap();
        this.hg = new HashMap();
        this.maxSize = i;
    }

    private void P(int i) {
        while (this.ig > i) {
            Object removeLast = this.dg.removeLast();
            com.bumptech.glide.util.i.checkNotNull(removeLast);
            a v = v(removeLast);
            this.ig -= v.a(removeLast) * v.E();
            d(v.a(removeLast), removeLast.getClass());
            if (Log.isLoggable(v.getTag(), 2)) {
                Log.v(v.getTag(), "evicted: " + v.a(removeLast));
            }
        }
    }

    private boolean Q(int i) {
        return i <= this.maxSize / 2;
    }

    @Nullable
    private <T> T a(a aVar) {
        return this.dg.b(aVar);
    }

    private <T> T a(a aVar, Class<T> cls) {
        a<T> m = m(cls);
        T a2 = a(aVar);
        if (a2 != null) {
            this.ig -= m.a(a2) * m.E();
            d(m.a(a2), cls);
        }
        if (a2 != null) {
            return a2;
        }
        if (Log.isLoggable(m.getTag(), 2)) {
            Log.v(m.getTag(), "Allocated " + aVar.size + " bytes");
        }
        return m.newArray(aVar.size);
    }

    private boolean a(int i, Integer num) {
        return num != null && (qk() || num.intValue() <= i * 8);
    }

    private void d(int i, Class<?> cls) {
        NavigableMap<Integer, Integer> n = n(cls);
        Integer num = (Integer) n.get(Integer.valueOf(i));
        if (num == null) {
            throw new NullPointerException("Tried to decrement empty size, size: " + i + ", this: " + this);
        } else if (num.intValue() == 1) {
            n.remove(Integer.valueOf(i));
        } else {
            n.put(Integer.valueOf(i), Integer.valueOf(num.intValue() - 1));
        }
    }

    private <T> a<T> m(Class<T> cls) {
        a<T> aVar = this.hg.get(cls);
        if (aVar == null) {
            if (cls.equals(int[].class)) {
                aVar = new h();
            } else if (cls.equals(byte[].class)) {
                aVar = new f();
            } else {
                throw new IllegalArgumentException("No array pool found for: " + cls.getSimpleName());
            }
            this.hg.put(cls, aVar);
        }
        return aVar;
    }

    private NavigableMap<Integer, Integer> n(Class<?> cls) {
        NavigableMap<Integer, Integer> navigableMap = this.gg.get(cls);
        if (navigableMap != null) {
            return navigableMap;
        }
        TreeMap treeMap = new TreeMap();
        this.gg.put(cls, treeMap);
        return treeMap;
    }

    private void pk() {
        P(this.maxSize);
    }

    private boolean qk() {
        int i = this.ig;
        return i == 0 || this.maxSize / i >= 2;
    }

    private <T> a<T> v(T t) {
        return m(t.getClass());
    }

    public synchronized void G() {
        P(0);
    }

    /* access modifiers changed from: package-private */
    public int J() {
        int i = 0;
        for (Class next : this.gg.keySet()) {
            for (Integer num : this.gg.get(next).keySet()) {
                i += num.intValue() * ((Integer) this.gg.get(next).get(num)).intValue() * m(next).E();
            }
        }
        return i;
    }

    public synchronized <T> T a(int i, Class<T> cls) {
        Integer ceilingKey;
        ceilingKey = n(cls).ceilingKey(Integer.valueOf(i));
        return a(a(i, ceilingKey) ? this.cg.a(ceilingKey.intValue(), cls) : this.cg.a(i, cls), cls);
    }

    @Deprecated
    public <T> void a(T t, Class<T> cls) {
        put(t);
    }

    public synchronized <T> T b(int i, Class<T> cls) {
        return a(this.cg.a(i, cls), cls);
    }

    public synchronized <T> void put(T t) {
        Class<?> cls = t.getClass();
        a<?> m = m(cls);
        int a2 = m.a(t);
        int E = m.E() * a2;
        if (Q(E)) {
            a a3 = this.cg.a(a2, cls);
            this.dg.a(a3, t);
            NavigableMap<Integer, Integer> n = n(cls);
            Integer num = (Integer) n.get(Integer.valueOf(a3.size));
            Integer valueOf = Integer.valueOf(a3.size);
            int i = 1;
            if (num != null) {
                i = 1 + num.intValue();
            }
            n.put(valueOf, Integer.valueOf(i));
            this.ig += E;
            pk();
        }
    }

    public synchronized void trimMemory(int i) {
        if (i >= 40) {
            try {
                G();
            } catch (Throwable th) {
                throw th;
            }
        } else if (i >= 20 || i == 15) {
            P(this.maxSize / 2);
        }
    }
}
