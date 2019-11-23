package com.bumptech.glide.d;

import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pools;
import com.bumptech.glide.load.engine.f;
import com.bumptech.glide.load.engine.n;
import com.bumptech.glide.load.resource.d.g;
import com.bumptech.glide.util.h;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: LoadPathCache */
public class c {
    private static final n<?, ?, ?> oy;
    private final AtomicReference<h> oA = new AtomicReference<>();
    private final ArrayMap<h, n<?, ?, ?>> oz = new ArrayMap<>();

    static {
        f fVar = new f(Object.class, Object.class, Object.class, Collections.emptyList(), new g(), (Pools.Pool<List<Throwable>>) null);
        n nVar = new n(Object.class, Object.class, Object.class, Collections.singletonList(fVar), (Pools.Pool<List<Throwable>>) null);
        oy = nVar;
    }

    private h e(Class<?> cls, Class<?> cls2, Class<?> cls3) {
        h andSet = this.oA.getAndSet((Object) null);
        if (andSet == null) {
            andSet = new h();
        }
        andSet.f(cls, cls2, cls3);
        return andSet;
    }

    public void a(Class<?> cls, Class<?> cls2, Class<?> cls3, @Nullable n<?, ?, ?> nVar) {
        synchronized (this.oz) {
            ArrayMap<h, n<?, ?, ?>> arrayMap = this.oz;
            h hVar = new h(cls, cls2, cls3);
            if (nVar == null) {
                nVar = oy;
            }
            arrayMap.put(hVar, nVar);
        }
    }

    public boolean a(@Nullable n<?, ?, ?> nVar) {
        return oy.equals(nVar);
    }

    @Nullable
    public <Data, TResource, Transcode> n<Data, TResource, Transcode> d(Class<Data> cls, Class<TResource> cls2, Class<Transcode> cls3) {
        n<Data, TResource, Transcode> nVar;
        h e = e(cls, cls2, cls3);
        synchronized (this.oz) {
            nVar = this.oz.get(e);
        }
        this.oA.set(e);
        return nVar;
    }
}
