package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.j;
import com.bumptech.glide.util.i;
import java.security.MessageDigest;
import java.util.Map;

/* compiled from: EngineKey */
class s implements c {
    private final Class<?> Be;
    private final Map<Class<?>, j<?>> Ld;
    private int hashCode;
    private final int height;
    private final Object model;
    private final g options;
    private final c signature;
    private final Class<?> uc;
    private final int width;

    s(Object obj, c cVar, int i, int i2, Map<Class<?>, j<?>> map, Class<?> cls, Class<?> cls2, g gVar) {
        i.checkNotNull(obj);
        this.model = obj;
        i.a(cVar, "Signature must not be null");
        this.signature = cVar;
        this.width = i;
        this.height = i2;
        i.checkNotNull(map);
        this.Ld = map;
        i.a(cls, "Resource class must not be null");
        this.Be = cls;
        i.a(cls2, "Transcode class must not be null");
        this.uc = cls2;
        i.checkNotNull(gVar);
        this.options = gVar;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof s)) {
            return false;
        }
        s sVar = (s) obj;
        return this.model.equals(sVar.model) && this.signature.equals(sVar.signature) && this.height == sVar.height && this.width == sVar.width && this.Ld.equals(sVar.Ld) && this.Be.equals(sVar.Be) && this.uc.equals(sVar.uc) && this.options.equals(sVar.options);
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = this.model.hashCode();
            this.hashCode = (this.hashCode * 31) + this.signature.hashCode();
            this.hashCode = (this.hashCode * 31) + this.width;
            this.hashCode = (this.hashCode * 31) + this.height;
            this.hashCode = (this.hashCode * 31) + this.Ld.hashCode();
            this.hashCode = (this.hashCode * 31) + this.Be.hashCode();
            this.hashCode = (this.hashCode * 31) + this.uc.hashCode();
            this.hashCode = (this.hashCode * 31) + this.options.hashCode();
        }
        return this.hashCode;
    }

    public String toString() {
        return "EngineKey{model=" + this.model + ", width=" + this.width + ", height=" + this.height + ", resourceClass=" + this.Be + ", transcodeClass=" + this.uc + ", signature=" + this.signature + ", hashCode=" + this.hashCode + ", transformations=" + this.Ld + ", options=" + this.options + '}';
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        throw new UnsupportedOperationException();
    }
}
