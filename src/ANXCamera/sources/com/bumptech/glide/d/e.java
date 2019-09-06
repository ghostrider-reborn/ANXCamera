package com.bumptech.glide.d;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.h;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: ResourceDecoderRegistry */
public class e {
    private final Map<String, List<a<?, ?>>> gf = new HashMap();
    private final List<String> yk = new ArrayList();

    /* compiled from: ResourceDecoderRegistry */
    private static class a<T, R> {
        final Class<R> Be;
        private final Class<T> dataClass;
        final h<T, R> decoder;

        public a(@NonNull Class<T> cls, @NonNull Class<R> cls2, h<T, R> hVar) {
            this.dataClass = cls;
            this.Be = cls2;
            this.decoder = hVar;
        }

        public boolean c(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
            return this.dataClass.isAssignableFrom(cls) && cls2.isAssignableFrom(this.Be);
        }
    }

    @NonNull
    private synchronized List<a<?, ?>> D(@NonNull String str) {
        List<a<?, ?>> list;
        if (!this.yk.contains(str)) {
            this.yk.add(str);
        }
        list = (List) this.gf.get(str);
        if (list == null) {
            list = new ArrayList<>();
            this.gf.put(str, list);
        }
        return list;
    }

    public synchronized <T, R> void a(@NonNull String str, @NonNull h<T, R> hVar, @NonNull Class<T> cls, @NonNull Class<R> cls2) {
        D(str).add(new a(cls, cls2, hVar));
    }

    public synchronized <T, R> void b(@NonNull String str, @NonNull h<T, R> hVar, @NonNull Class<T> cls, @NonNull Class<R> cls2) {
        D(str).add(0, new a(cls, cls2, hVar));
    }

    public synchronized void c(@NonNull List<String> list) {
        ArrayList<String> arrayList = new ArrayList<>(this.yk);
        this.yk.clear();
        this.yk.addAll(list);
        for (String str : arrayList) {
            if (!list.contains(str)) {
                this.yk.add(str);
            }
        }
    }

    @NonNull
    public synchronized <T, R> List<h<T, R>> f(@NonNull Class<T> cls, @NonNull Class<R> cls2) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (String str : this.yk) {
            List<a> list = (List) this.gf.get(str);
            if (list != null) {
                for (a aVar : list) {
                    if (aVar.c(cls, cls2)) {
                        arrayList.add(aVar.decoder);
                    }
                }
            }
        }
        return arrayList;
    }

    @NonNull
    public synchronized <T, R> List<Class<R>> g(@NonNull Class<T> cls, @NonNull Class<R> cls2) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (String str : this.yk) {
            List<a> list = (List) this.gf.get(str);
            if (list != null) {
                for (a aVar : list) {
                    if (aVar.c(cls, cls2) && !arrayList.contains(aVar.Be)) {
                        arrayList.add(aVar.Be);
                    }
                }
            }
        }
        return arrayList;
    }
}
