package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools;
import com.bumptech.glide.load.a.e;
import com.bumptech.glide.load.engine.i;
import com.bumptech.glide.load.g;
import com.bumptech.glide.util.i;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: LoadPath */
public class x<Data, ResourceType, Transcode> {
    private final List<? extends i<Data, ResourceType, Transcode>> Pf;
    private final Class<Data> dataClass;

    /* renamed from: if  reason: not valid java name */
    private final Pools.Pool<List<Throwable>> f1if;
    private final String jf;

    public x(Class<Data> cls, Class<ResourceType> cls2, Class<Transcode> cls3, List<i<Data, ResourceType, Transcode>> list, Pools.Pool<List<Throwable>> pool) {
        this.dataClass = cls;
        this.f1if = pool;
        i.a(list);
        this.Pf = list;
        this.jf = "Failed LoadPath{" + cls.getSimpleName() + "->" + cls2.getSimpleName() + "->" + cls3.getSimpleName() + "}";
    }

    private A<Transcode> a(e<Data> eVar, @NonNull g gVar, int i, int i2, i.a<ResourceType> aVar, List<Throwable> list) throws GlideException {
        List<Throwable> list2 = list;
        int size = this.Pf.size();
        A<Transcode> a2 = null;
        for (int i3 = 0; i3 < size; i3++) {
            try {
                a2 = ((i) this.Pf.get(i3)).a(eVar, i, i2, gVar, aVar);
            } catch (GlideException e2) {
                list2.add(e2);
            }
            if (a2 != null) {
                break;
            }
        }
        if (a2 != null) {
            return a2;
        }
        throw new GlideException(this.jf, (List<Throwable>) new ArrayList(list2));
    }

    public Class<Data> M() {
        return this.dataClass;
    }

    public A<Transcode> a(e<Data> eVar, @NonNull g gVar, int i, int i2, i.a<ResourceType> aVar) throws GlideException {
        List<Throwable> acquire = this.f1if.acquire();
        com.bumptech.glide.util.i.checkNotNull(acquire);
        List list = acquire;
        try {
            A<Transcode> a2 = a(eVar, gVar, i, i2, aVar, list);
            return a2;
        } finally {
            this.f1if.release(list);
        }
    }

    public String toString() {
        return "LoadPath{decodePaths=" + Arrays.toString(this.Pf.toArray()) + '}';
    }
}
