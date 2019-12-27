package com.bumptech.glide;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pools;
import com.bumptech.glide.d.a;
import com.bumptech.glide.d.b;
import com.bumptech.glide.d.c;
import com.bumptech.glide.d.d;
import com.bumptech.glide.d.e;
import com.bumptech.glide.d.f;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.a.e;
import com.bumptech.glide.load.a.g;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.i;
import com.bumptech.glide.load.engine.x;
import com.bumptech.glide.load.h;
import com.bumptech.glide.load.model.t;
import com.bumptech.glide.load.model.u;
import com.bumptech.glide.load.model.v;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Registry {
    public static final String mc = "Gif";
    public static final String nc = "Bitmap";
    public static final String oc = "BitmapDrawable";
    private static final String qc = "legacy_prepend_all";
    private static final String rc = "legacy_append";
    private final v bc = new v(this.lc);
    private final a dc = new a();
    private final e ec = new e();
    private final f fc = new f();
    private final g gc = new g();
    private final com.bumptech.glide.load.b.d.f hc = new com.bumptech.glide.load.b.d.f();
    private final b ic = new b();
    private final d jc = new d();
    private final c kc = new c();
    private final Pools.Pool<List<Throwable>> lc = com.bumptech.glide.util.a.d.Lh();

    public static class MissingComponentException extends RuntimeException {
        public MissingComponentException(@NonNull String str) {
            super(str);
        }
    }

    public static final class NoImageHeaderParserException extends MissingComponentException {
        public NoImageHeaderParserException() {
            super("Failed to find image header parser.");
        }
    }

    public static class NoModelLoaderAvailableException extends MissingComponentException {
        public NoModelLoaderAvailableException(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
            super("Failed to find any ModelLoaders for model: " + cls + " and data: " + cls2);
        }

        public NoModelLoaderAvailableException(@NonNull Object obj) {
            super("Failed to find any ModelLoaders for model: " + obj);
        }
    }

    public static class NoResultEncoderAvailableException extends MissingComponentException {
        public NoResultEncoderAvailableException(@NonNull Class<?> cls) {
            super("Failed to find result encoder for resource class: " + cls + ", you may need to consider registering a new Encoder for the requested type or DiskCacheStrategy.DATA/DiskCacheStrategy.NONE if caching your transformed resource is unnecessary.");
        }
    }

    public static class NoSourceEncoderAvailableException extends MissingComponentException {
        public NoSourceEncoderAvailableException(@NonNull Class<?> cls) {
            super("Failed to find source encoder for data class: " + cls);
        }
    }

    public Registry() {
        b(Arrays.asList(new String[]{mc, nc, oc}));
    }

    @NonNull
    private <Data, TResource, Transcode> List<i<Data, TResource, Transcode>> e(@NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull Class<Transcode> cls3) {
        ArrayList arrayList = new ArrayList();
        for (Class next : this.ec.g(cls, cls2)) {
            for (Class next2 : this.hc.e(next, cls3)) {
                i iVar = new i(cls, next, next2, this.ec.f(cls, next), this.hc.d(next, next2), this.lc);
                arrayList.add(iVar);
            }
        }
        return arrayList;
    }

    @NonNull
    public List<ImageHeaderParser> Gf() {
        List<ImageHeaderParser> Ng = this.ic.Ng();
        if (!Ng.isEmpty()) {
            return Ng;
        }
        throw new NoImageHeaderParserException();
    }

    @NonNull
    public Registry a(@NonNull ImageHeaderParser imageHeaderParser) {
        this.ic.b(imageHeaderParser);
        return this;
    }

    @NonNull
    public Registry a(@NonNull e.a<?> aVar) {
        this.gc.a(aVar);
        return this;
    }

    @NonNull
    public <Data> Registry a(@NonNull Class<Data> cls, @NonNull com.bumptech.glide.load.a<Data> aVar) {
        this.dc.a(cls, aVar);
        return this;
    }

    @NonNull
    public <TResource> Registry a(@NonNull Class<TResource> cls, @NonNull com.bumptech.glide.load.i<TResource> iVar) {
        this.fc.a(cls, iVar);
        return this;
    }

    @NonNull
    public <TResource, Transcode> Registry a(@NonNull Class<TResource> cls, @NonNull Class<Transcode> cls2, @NonNull com.bumptech.glide.load.b.d.e<TResource, Transcode> eVar) {
        this.hc.a(cls, cls2, eVar);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry a(@NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull h<Data, TResource> hVar) {
        a(rc, cls, cls2, hVar);
        return this;
    }

    @NonNull
    public <Model, Data> Registry a(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull u<Model, Data> uVar) {
        this.bc.a(cls, cls2, uVar);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry a(@NonNull String str, @NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull h<Data, TResource> hVar) {
        this.ec.a(str, hVar, cls, cls2);
        return this;
    }

    @Nullable
    public <Data, TResource, Transcode> x<Data, TResource, Transcode> a(@NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull Class<Transcode> cls3) {
        x<Data, TResource, Transcode> c2 = this.kc.c(cls, cls2, cls3);
        if (this.kc.a(c2)) {
            return null;
        }
        if (c2 == null) {
            List<i<Data, TResource, Transcode>> e2 = e(cls, cls2, cls3);
            if (e2.isEmpty()) {
                c2 = null;
            } else {
                c2 = new x<>(cls, cls2, cls3, e2, this.lc);
            }
            this.kc.a(cls, cls2, cls3, c2);
        }
        return c2;
    }

    @NonNull
    public <Data> Registry b(@NonNull Class<Data> cls, @NonNull com.bumptech.glide.load.a<Data> aVar) {
        this.dc.b(cls, aVar);
        return this;
    }

    @NonNull
    public <TResource> Registry b(@NonNull Class<TResource> cls, @NonNull com.bumptech.glide.load.i<TResource> iVar) {
        this.fc.b(cls, iVar);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry b(@NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull h<Data, TResource> hVar) {
        b(qc, cls, cls2, hVar);
        return this;
    }

    @NonNull
    public <Model, Data> Registry b(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull u<Model, Data> uVar) {
        this.bc.b(cls, cls2, uVar);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry b(@NonNull String str, @NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull h<Data, TResource> hVar) {
        this.ec.b(str, hVar, cls, cls2);
        return this;
    }

    @NonNull
    public final Registry b(@NonNull List<String> list) {
        ArrayList arrayList = new ArrayList(list);
        arrayList.add(0, qc);
        arrayList.add(rc);
        this.ec.c(arrayList);
        return this;
    }

    @NonNull
    public <Model, TResource, Transcode> List<Class<?>> b(@NonNull Class<Model> cls, @NonNull Class<TResource> cls2, @NonNull Class<Transcode> cls3) {
        List<Class<?>> d2 = this.jc.d(cls, cls2);
        if (d2 == null) {
            d2 = new ArrayList<>();
            for (Class<?> g : this.bc.f((Class<?>) cls)) {
                for (Class next : this.ec.g(g, cls2)) {
                    if (!this.hc.e(next, cls3).isEmpty() && !d2.contains(next)) {
                        d2.add(next);
                    }
                }
            }
            this.jc.a(cls, cls2, Collections.unmodifiableList(d2));
        }
        return d2;
    }

    @Deprecated
    @NonNull
    public <Data> Registry c(@NonNull Class<Data> cls, @NonNull com.bumptech.glide.load.a<Data> aVar) {
        return a(cls, aVar);
    }

    @Deprecated
    @NonNull
    public <TResource> Registry c(@NonNull Class<TResource> cls, @NonNull com.bumptech.glide.load.i<TResource> iVar) {
        return a(cls, iVar);
    }

    @NonNull
    public <Model, Data> Registry c(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull u<? extends Model, ? extends Data> uVar) {
        this.bc.c(cls, cls2, uVar);
        return this;
    }

    @NonNull
    public <X> com.bumptech.glide.load.i<X> c(@NonNull A<X> a2) throws NoResultEncoderAvailableException {
        com.bumptech.glide.load.i<X> iVar = this.fc.get(a2.z());
        if (iVar != null) {
            return iVar;
        }
        throw new NoResultEncoderAvailableException(a2.z());
    }

    public boolean d(@NonNull A<?> a2) {
        return this.fc.get(a2.z()) != null;
    }

    @NonNull
    public <Model> List<t<Model, ?>> j(@NonNull Model model) {
        List<t<Model, ?>> j = this.bc.j(model);
        if (!j.isEmpty()) {
            return j;
        }
        throw new NoModelLoaderAvailableException(model);
    }

    @NonNull
    public <X> com.bumptech.glide.load.a.e<X> k(@NonNull X x) {
        return this.gc.build(x);
    }

    @NonNull
    public <X> com.bumptech.glide.load.a<X> l(@NonNull X x) throws NoSourceEncoderAvailableException {
        com.bumptech.glide.load.a<X> i = this.dc.i(x.getClass());
        if (i != null) {
            return i;
        }
        throw new NoSourceEncoderAvailableException(x.getClass());
    }
}
