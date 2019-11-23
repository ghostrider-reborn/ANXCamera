package com.bumptech.glide.util.a;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* compiled from: FactoryPools */
public final class a {
    private static final int DEFAULT_POOL_SIZE = 20;
    private static final String TAG = "FactoryPools";
    private static final d<Object> rh = new d<Object>() {
        public void reset(@NonNull Object obj) {
        }
    };

    /* renamed from: com.bumptech.glide.util.a.a$a  reason: collision with other inner class name */
    /* compiled from: FactoryPools */
    public interface C0013a<T> {
        T create();
    }

    /* compiled from: FactoryPools */
    private static final class b<T> implements Pools.Pool<T> {
        private final Pools.Pool<T> gf;
        private final C0013a<T> ri;
        private final d<T> rj;

        b(@NonNull Pools.Pool<T> pool, @NonNull C0013a<T> aVar, @NonNull d<T> dVar) {
            this.gf = pool;
            this.ri = aVar;
            this.rj = dVar;
        }

        public T acquire() {
            T acquire = this.gf.acquire();
            if (acquire == null) {
                acquire = this.ri.create();
                if (Log.isLoggable(a.TAG, 2)) {
                    Log.v(a.TAG, "Created new " + acquire.getClass());
                }
            }
            if (acquire instanceof c) {
                ((c) acquire).br().o(false);
            }
            return acquire;
        }

        public boolean release(@NonNull T t) {
            if (t instanceof c) {
                ((c) t).br().o(true);
            }
            this.rj.reset(t);
            return this.gf.release(t);
        }
    }

    /* compiled from: FactoryPools */
    public interface c {
        @NonNull
        c br();
    }

    /* compiled from: FactoryPools */
    public interface d<T> {
        void reset(@NonNull T t);
    }

    private a() {
    }

    @NonNull
    public static <T extends c> Pools.Pool<T> a(int i, @NonNull C0013a<T> aVar) {
        return a(new Pools.SimplePool(i), aVar);
    }

    @NonNull
    private static <T extends c> Pools.Pool<T> a(@NonNull Pools.Pool<T> pool, @NonNull C0013a<T> aVar) {
        return a(pool, aVar, ft());
    }

    @NonNull
    private static <T> Pools.Pool<T> a(@NonNull Pools.Pool<T> pool, @NonNull C0013a<T> aVar, @NonNull d<T> dVar) {
        return new b(pool, aVar, dVar);
    }

    @NonNull
    public static <T> Pools.Pool<List<T>> ac(int i) {
        return a(new Pools.SynchronizedPool(i), new C0013a<List<T>>() {
            @NonNull
            /* renamed from: fu */
            public List<T> create() {
                return new ArrayList();
            }
        }, new d<List<T>>() {
            /* renamed from: f */
            public void reset(@NonNull List<T> list) {
                list.clear();
            }
        });
    }

    @NonNull
    public static <T extends c> Pools.Pool<T> b(int i, @NonNull C0013a<T> aVar) {
        return a(new Pools.SynchronizedPool(i), aVar);
    }

    @NonNull
    public static <T> Pools.Pool<List<T>> fs() {
        return ac(20);
    }

    @NonNull
    private static <T> d<T> ft() {
        return rh;
    }
}
