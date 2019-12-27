package com.bumptech.glide.load.b.d;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/* compiled from: TranscoderRegistry */
public class f {
    private final List<a<?, ?>> ck = new ArrayList();

    /* compiled from: TranscoderRegistry */
    private static final class a<Z, R> {
        private final Class<Z> ak;
        private final Class<R> bk;
        final e<Z, R> hf;

        a(@NonNull Class<Z> cls, @NonNull Class<R> cls2, @NonNull e<Z, R> eVar) {
            this.ak = cls;
            this.bk = cls2;
            this.hf = eVar;
        }

        public boolean c(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
            return this.ak.isAssignableFrom(cls) && cls2.isAssignableFrom(this.bk);
        }
    }

    public synchronized <Z, R> void a(@NonNull Class<Z> cls, @NonNull Class<R> cls2, @NonNull e<Z, R> eVar) {
        this.ck.add(new a(cls, cls2, eVar));
    }

    @NonNull
    public synchronized <Z, R> e<Z, R> d(@NonNull Class<Z> cls, @NonNull Class<R> cls2) {
        if (cls2.isAssignableFrom(cls)) {
            return g.get();
        }
        for (a next : this.ck) {
            if (next.c(cls, cls2)) {
                return next.hf;
            }
        }
        throw new IllegalArgumentException("No transcoder registered to transcode from " + cls + " to " + cls2);
    }

    @NonNull
    public synchronized <Z, R> List<Class<R>> e(@NonNull Class<Z> cls, @NonNull Class<R> cls2) {
        ArrayList arrayList = new ArrayList();
        if (cls2.isAssignableFrom(cls)) {
            arrayList.add(cls2);
            return arrayList;
        }
        for (a<?, ?> c2 : this.ck) {
            if (c2.c(cls, cls2)) {
                arrayList.add(cls2);
            }
        }
        return arrayList;
    }
}
