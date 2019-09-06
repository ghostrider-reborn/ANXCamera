package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools.Pool;
import com.bumptech.glide.load.a.e;
import com.bumptech.glide.load.g;
import com.bumptech.glide.util.i;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: LoadPath */
public class x<Data, ResourceType, Transcode> {
    private final List<? extends i<Data, ResourceType, Transcode>> Pf;
    private final Class<Data> dataClass;

    /* renamed from: if reason: not valid java name */
    private final Pool<List<Throwable>> f1if;
    private final String jf;

    public x(Class<Data> cls, Class<ResourceType> cls2, Class<Transcode> cls3, List<i<Data, ResourceType, Transcode>> list, Pool<List<Throwable>> pool) {
        this.dataClass = cls;
        this.f1if = pool;
        i.a(list);
        this.Pf = list;
        StringBuilder sb = new StringBuilder();
        sb.append("Failed LoadPath{");
        sb.append(cls.getSimpleName());
        String str = "->";
        sb.append(str);
        sb.append(cls2.getSimpleName());
        sb.append(str);
        sb.append(cls3.getSimpleName());
        sb.append("}");
        this.jf = sb.toString();
    }

    private A<Transcode> a(e<Data> eVar, @NonNull g gVar, int i, int i2, a<ResourceType> aVar, List<Throwable> list) throws GlideException {
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
        throw new GlideException(this.jf, (List<Throwable>) new ArrayList<Throwable>(list2));
    }

    public Class<Data> M() {
        return this.dataClass;
    }

    public A<Transcode> a(e<Data> eVar, @NonNull g gVar, int i, int i2, a<ResourceType> aVar) throws GlideException {
        Object acquire = this.f1if.acquire();
        i.checkNotNull(acquire);
        List list = (List) acquire;
        try {
            A<Transcode> a2 = a(eVar, gVar, i, i2, aVar, list);
            return a2;
        } finally {
            this.f1if.release(list);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LoadPath{decodePaths=");
        sb.append(Arrays.toString(this.Pf.toArray()));
        sb.append('}');
        return sb.toString();
    }
}
