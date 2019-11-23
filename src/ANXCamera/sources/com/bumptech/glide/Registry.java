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
import com.bumptech.glide.load.engine.p;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.h;
import com.bumptech.glide.load.model.m;
import com.bumptech.glide.load.model.n;
import com.bumptech.glide.load.model.o;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Registry {
    public static final String cd = "Gif";
    public static final String ce = "Bitmap";
    public static final String cf = "BitmapDrawable";
    private static final String cg = "legacy_prepend_all";
    private static final String ci = "legacy_append";
    private final o cj = new o(this.cu);
    private final a ck = new a();
    private final e cl = new e();
    private final f cm = new f();
    private final com.bumptech.glide.load.a.f cn = new com.bumptech.glide.load.a.f();
    private final com.bumptech.glide.load.resource.d.f cp = new com.bumptech.glide.load.resource.d.f();
    private final b cq = new b();
    private final d cr = new d();
    private final c ct = new c();
    private final Pools.Pool<List<Throwable>> cu = com.bumptech.glide.util.a.a.fs();

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
        b((List<String>) Arrays.asList(new String[]{cd, ce, cf}));
    }

    @NonNull
    private <Data, TResource, Transcode> List<com.bumptech.glide.load.engine.f<Data, TResource, Transcode>> b(@NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull Class<Transcode> cls3) {
        ArrayList arrayList = new ArrayList();
        for (Class next : this.cl.i(cls, cls2)) {
            for (Class next2 : this.cp.f(next, cls3)) {
                com.bumptech.glide.load.engine.f fVar = new com.bumptech.glide.load.engine.f(cls, next, next2, this.cl.h(cls, next), this.cp.e(next, next2), this.cu);
                arrayList.add(fVar);
            }
        }
        return arrayList;
    }

    @NonNull
    public List<ImageHeaderParser> X() {
        List<ImageHeaderParser> dL = this.cq.dL();
        if (!dL.isEmpty()) {
            return dL;
        }
        throw new NoImageHeaderParserException();
    }

    @NonNull
    public Registry a(@NonNull ImageHeaderParser imageHeaderParser) {
        this.cq.b(imageHeaderParser);
        return this;
    }

    @NonNull
    public Registry a(@NonNull e.a<?> aVar) {
        this.cn.b(aVar);
        return this;
    }

    @Deprecated
    @NonNull
    public <Data> Registry a(@NonNull Class<Data> cls, @NonNull com.bumptech.glide.load.a<Data> aVar) {
        return b(cls, aVar);
    }

    @Deprecated
    @NonNull
    public <TResource> Registry a(@NonNull Class<TResource> cls, @NonNull h<TResource> hVar) {
        return b(cls, hVar);
    }

    @NonNull
    public <Data, TResource> Registry a(@NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull g<Data, TResource> gVar) {
        a(ci, cls, cls2, gVar);
        return this;
    }

    @NonNull
    public <Model, Data> Registry a(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull n<Model, Data> nVar) {
        this.cj.d(cls, cls2, nVar);
        return this;
    }

    @NonNull
    public <TResource, Transcode> Registry a(@NonNull Class<TResource> cls, @NonNull Class<Transcode> cls2, @NonNull com.bumptech.glide.load.resource.d.e<TResource, Transcode> eVar) {
        this.cp.b(cls, cls2, eVar);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry a(@NonNull String str, @NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull g<Data, TResource> gVar) {
        this.cl.a(str, gVar, cls, cls2);
        return this;
    }

    @Nullable
    public <Data, TResource, Transcode> com.bumptech.glide.load.engine.n<Data, TResource, Transcode> a(@NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull Class<Transcode> cls3) {
        com.bumptech.glide.load.engine.n<Data, TResource, Transcode> d = this.ct.d(cls, cls2, cls3);
        if (this.ct.a(d)) {
            return null;
        }
        if (d == null) {
            List<com.bumptech.glide.load.engine.f<Data, TResource, Transcode>> b2 = b(cls, cls2, cls3);
            if (b2.isEmpty()) {
                d = null;
            } else {
                d = new com.bumptech.glide.load.engine.n<>(cls, cls2, cls3, b2, this.cu);
            }
            this.ct.a(cls, cls2, cls3, d);
        }
        return d;
    }

    public boolean a(@NonNull p<?> pVar) {
        return this.cm.o(pVar.bH()) != null;
    }

    @NonNull
    public <Data> Registry b(@NonNull Class<Data> cls, @NonNull com.bumptech.glide.load.a<Data> aVar) {
        this.ck.d(cls, aVar);
        return this;
    }

    @NonNull
    public <TResource> Registry b(@NonNull Class<TResource> cls, @NonNull h<TResource> hVar) {
        this.cm.d(cls, hVar);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry b(@NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull g<Data, TResource> gVar) {
        b(cg, cls, cls2, gVar);
        return this;
    }

    @NonNull
    public <Model, Data> Registry b(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull n<Model, Data> nVar) {
        this.cj.e(cls, cls2, nVar);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry b(@NonNull String str, @NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull g<Data, TResource> gVar) {
        this.cl.b(str, gVar, cls, cls2);
        return this;
    }

    @NonNull
    public final Registry b(@NonNull List<String> list) {
        ArrayList arrayList = new ArrayList(list);
        arrayList.add(0, cg);
        arrayList.add(ci);
        this.cl.e(arrayList);
        return this;
    }

    @NonNull
    public <X> h<X> b(@NonNull p<X> pVar) throws NoResultEncoderAvailableException {
        h<X> o = this.cm.o(pVar.bH());
        if (o != null) {
            return o;
        }
        throw new NoResultEncoderAvailableException(pVar.bH());
    }

    @NonNull
    public <Data> Registry c(@NonNull Class<Data> cls, @NonNull com.bumptech.glide.load.a<Data> aVar) {
        this.ck.e(cls, aVar);
        return this;
    }

    @NonNull
    public <TResource> Registry c(@NonNull Class<TResource> cls, @NonNull h<TResource> hVar) {
        this.cm.e(cls, hVar);
        return this;
    }

    @NonNull
    public <Model, Data> Registry c(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull n<? extends Model, ? extends Data> nVar) {
        this.cj.f(cls, cls2, nVar);
        return this;
    }

    @NonNull
    public <Model, TResource, Transcode> List<Class<?>> c(@NonNull Class<Model> cls, @NonNull Class<TResource> cls2, @NonNull Class<Transcode> cls3) {
        List<Class<?>> g = this.cr.g(cls, cls2);
        if (g == null) {
            g = new ArrayList<>();
            for (Class<?> i : this.cj.i((Class<?>) cls)) {
                for (Class next : this.cl.i(i, cls2)) {
                    if (!this.cp.f(next, cls3).isEmpty() && !g.contains(next)) {
                        g.add(next);
                    }
                }
            }
            this.cr.a(cls, cls2, Collections.unmodifiableList(g));
        }
        return g;
    }

    @NonNull
    public <X> com.bumptech.glide.load.a<X> g(@NonNull X x) throws NoSourceEncoderAvailableException {
        com.bumptech.glide.load.a<X> n = this.ck.n(x.getClass());
        if (n != null) {
            return n;
        }
        throw new NoSourceEncoderAvailableException(x.getClass());
    }

    @NonNull
    public <X> com.bumptech.glide.load.a.e<X> h(@NonNull X x) {
        return this.cn.o(x);
    }

    @NonNull
    public <Model> List<m<Model, ?>> i(@NonNull Model model) {
        List<m<Model, ?>> i = this.cj.i(model);
        if (!i.isEmpty()) {
            return i;
        }
        throw new NoModelLoaderAvailableException(model);
    }
}
