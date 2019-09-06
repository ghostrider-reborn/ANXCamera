package com.bumptech.glide.util.a;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SimplePool;
import android.support.v4.util.Pools.SynchronizedPool;
import android.util.Log;
import java.util.List;

/* compiled from: FactoryPools */
public final class d {
    private static final int DEFAULT_POOL_SIZE = 20;
    private static final String TAG = "FactoryPools";
    private static final C0014d<Object> gm = new a();

    /* compiled from: FactoryPools */
    public interface a<T> {
        T create();
    }

    /* compiled from: FactoryPools */
    private static final class b<T> implements Pool<T> {
        private final a<T> factory;
        private final C0014d<T> fm;
        private final Pool<T> pool;

        b(@NonNull Pool<T> pool2, @NonNull a<T> aVar, @NonNull C0014d<T> dVar) {
            this.pool = pool2;
            this.factory = aVar;
            this.fm = dVar;
        }

        public T acquire() {
            T acquire = this.pool.acquire();
            if (acquire == null) {
                acquire = this.factory.create();
                String str = d.TAG;
                if (Log.isLoggable(str, 2)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Created new ");
                    sb.append(acquire.getClass());
                    Log.v(str, sb.toString());
                }
            }
            if (acquire instanceof c) {
                ((c) acquire).getVerifier().u(false);
            }
            return acquire;
        }

        public boolean release(@NonNull T t) {
            if (t instanceof c) {
                ((c) t).getVerifier().u(true);
            }
            this.fm.reset(t);
            return this.pool.release(t);
        }
    }

    /* compiled from: FactoryPools */
    public interface c {
        @NonNull
        g getVerifier();
    }

    /* renamed from: com.bumptech.glide.util.a.d$d reason: collision with other inner class name */
    /* compiled from: FactoryPools */
    public interface C0014d<T> {
        void reset(@NonNull T t);
    }

    private d() {
    }

    @NonNull
    public static <T> Pool<List<T>> I(int i) {
        return a(new SynchronizedPool(i), new b(), new c());
    }

    @NonNull
    public static <T> Pool<List<T>> Lh() {
        return I(20);
    }

    @NonNull
    public static <T extends c> Pool<T> a(int i, @NonNull a<T> aVar) {
        return a((Pool<T>) new SimplePool<T>(i), aVar);
    }

    @NonNull
    private static <T extends c> Pool<T> a(@NonNull Pool<T> pool, @NonNull a<T> aVar) {
        return a(pool, aVar, el());
    }

    @NonNull
    private static <T> Pool<T> a(@NonNull Pool<T> pool, @NonNull a<T> aVar, @NonNull C0014d<T> dVar) {
        return new b(pool, aVar, dVar);
    }

    @NonNull
    public static <T extends c> Pool<T> b(int i, @NonNull a<T> aVar) {
        return a((Pool<T>) new SynchronizedPool<T>(i), aVar);
    }

    @NonNull
    private static <T> C0014d<T> el() {
        return gm;
    }
}
