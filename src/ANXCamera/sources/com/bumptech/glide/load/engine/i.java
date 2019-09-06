package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools.Pool;
import android.util.Log;
import com.bumptech.glide.load.b.d.e;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.h;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: DecodePath */
public class i<DataType, ResourceType, Transcode> {
    private static final String TAG = "DecodePath";
    private final Class<DataType> dataClass;
    private final List<? extends h<DataType, ResourceType>> gf;
    private final e<ResourceType, Transcode> hf;

    /* renamed from: if reason: not valid java name */
    private final Pool<List<Throwable>> f0if;
    private final String jf;

    /* compiled from: DecodePath */
    interface a<ResourceType> {
        @NonNull
        A<ResourceType> a(@NonNull A<ResourceType> a2);
    }

    public i(Class<DataType> cls, Class<ResourceType> cls2, Class<Transcode> cls3, List<? extends h<DataType, ResourceType>> list, e<ResourceType, Transcode> eVar, Pool<List<Throwable>> pool) {
        this.dataClass = cls;
        this.gf = list;
        this.hf = eVar;
        this.f0if = pool;
        StringBuilder sb = new StringBuilder();
        sb.append("Failed DecodePath{");
        sb.append(cls.getSimpleName());
        String str = "->";
        sb.append(str);
        sb.append(cls2.getSimpleName());
        sb.append(str);
        sb.append(cls3.getSimpleName());
        sb.append("}");
        this.jf = sb.toString();
    }

    @NonNull
    private A<ResourceType> a(com.bumptech.glide.load.a.e<DataType> eVar, int i, int i2, @NonNull g gVar) throws GlideException {
        Object acquire = this.f0if.acquire();
        com.bumptech.glide.util.i.checkNotNull(acquire);
        List list = (List) acquire;
        try {
            A<ResourceType> a2 = a(eVar, i, i2, gVar, list);
            return a2;
        } finally {
            this.f0if.release(list);
        }
    }

    @NonNull
    private A<ResourceType> a(com.bumptech.glide.load.a.e<DataType> eVar, int i, int i2, @NonNull g gVar, List<Throwable> list) throws GlideException {
        int size = this.gf.size();
        A<ResourceType> a2 = null;
        for (int i3 = 0; i3 < size; i3++) {
            h hVar = (h) this.gf.get(i3);
            try {
                if (hVar.a(eVar.C(), gVar)) {
                    a2 = hVar.b(eVar.C(), i, i2, gVar);
                }
            } catch (IOException | OutOfMemoryError | RuntimeException e2) {
                String str = TAG;
                if (Log.isLoggable(str, 2)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failed to decode data for ");
                    sb.append(hVar);
                    Log.v(str, sb.toString(), e2);
                }
                list.add(e2);
            }
            if (a2 != null) {
                break;
            }
        }
        if (a2 != null) {
            return a2;
        }
        throw new GlideException(this.jf, (List<Throwable>) new ArrayList<Throwable>(list));
    }

    public A<Transcode> a(com.bumptech.glide.load.a.e<DataType> eVar, int i, int i2, @NonNull g gVar, a<ResourceType> aVar) throws GlideException {
        return this.hf.a(aVar.a(a(eVar, i, i2, gVar)), gVar);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DecodePath{ dataClass=");
        sb.append(this.dataClass);
        sb.append(", decoders=");
        sb.append(this.gf);
        sb.append(", transcoder=");
        sb.append(this.hf);
        sb.append('}');
        return sb.toString();
    }
}
