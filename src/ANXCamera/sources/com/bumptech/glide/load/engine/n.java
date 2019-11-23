package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools;
import com.bumptech.glide.load.a.e;
import com.bumptech.glide.load.engine.f;
import com.bumptech.glide.load.f;
import com.bumptech.glide.util.i;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: LoadPath */
public class n<Data, ResourceType, Transcode> {
    private final Class<Data> dataClass;
    private final Pools.Pool<List<Throwable>> gS;
    private final String gT;
    private final List<? extends f<Data, ResourceType, Transcode>> hQ;

    public n(Class<Data> cls, Class<ResourceType> cls2, Class<Transcode> cls3, List<f<Data, ResourceType, Transcode>> list, Pools.Pool<List<Throwable>> pool) {
        this.dataClass = cls;
        this.gS = pool;
        this.hQ = (List) i.b(list);
        this.gT = "Failed LoadPath{" + cls.getSimpleName() + "->" + cls2.getSimpleName() + "->" + cls3.getSimpleName() + "}";
    }

    private p<Transcode> a(e<Data> eVar, @NonNull f fVar, int i, int i2, f.a<ResourceType> aVar, List<Throwable> list) throws GlideException {
        List<Throwable> list2 = list;
        int size = this.hQ.size();
        p<Transcode> pVar = null;
        for (int i3 = 0; i3 < size; i3++) {
            try {
                pVar = ((f) this.hQ.get(i3)).a(eVar, i, i2, fVar, aVar);
            } catch (GlideException e) {
                list2.add(e);
            }
            if (pVar != null) {
                break;
            }
        }
        if (pVar != null) {
            return pVar;
        }
        throw new GlideException(this.gT, (List<Throwable>) new ArrayList(list2));
    }

    public p<Transcode> a(e<Data> eVar, @NonNull com.bumptech.glide.load.f fVar, int i, int i2, f.a<ResourceType> aVar) throws GlideException {
        List list = (List) i.checkNotNull(this.gS.acquire());
        try {
            p<Transcode> a2 = a(eVar, fVar, i, i2, aVar, list);
            return a2;
        } finally {
            this.gS.release(list);
        }
    }

    public Class<Data> aK() {
        return this.dataClass;
    }

    public String toString() {
        return "LoadPath{decodePaths=" + Arrays.toString(this.hQ.toArray()) + '}';
    }
}
