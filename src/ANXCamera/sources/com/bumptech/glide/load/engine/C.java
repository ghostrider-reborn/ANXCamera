package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.j;
import com.bumptech.glide.util.f;
import com.bumptech.glide.util.l;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* compiled from: ResourceCacheKey */
final class C implements c {
    private static final f<Class<?>, byte[]> Wf = new f<>(50);
    private final Class<?> Uf;
    private final j<?> Vf;
    private final int height;
    private final g options;
    private final b qa;
    private final c signature;
    private final c we;
    private final int width;

    C(b bVar, c cVar, c cVar2, int i, int i2, j<?> jVar, Class<?> cls, g gVar) {
        this.qa = bVar;
        this.we = cVar;
        this.signature = cVar2;
        this.width = i;
        this.height = i2;
        this.Vf = jVar;
        this.Uf = cls;
        this.options = gVar;
    }

    private byte[] nk() {
        byte[] bArr = Wf.get(this.Uf);
        if (bArr != null) {
            return bArr;
        }
        byte[] bytes = this.Uf.getName().getBytes(c.CHARSET);
        Wf.put(this.Uf, bytes);
        return bytes;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C)) {
            return false;
        }
        C c2 = (C) obj;
        return this.height == c2.height && this.width == c2.width && l.d((Object) this.Vf, (Object) c2.Vf) && this.Uf.equals(c2.Uf) && this.we.equals(c2.we) && this.signature.equals(c2.signature) && this.options.equals(c2.options);
    }

    public int hashCode() {
        int hashCode = (((((this.we.hashCode() * 31) + this.signature.hashCode()) * 31) + this.width) * 31) + this.height;
        j<?> jVar = this.Vf;
        if (jVar != null) {
            hashCode = (hashCode * 31) + jVar.hashCode();
        }
        return (((hashCode * 31) + this.Uf.hashCode()) * 31) + this.options.hashCode();
    }

    public String toString() {
        return "ResourceCacheKey{sourceKey=" + this.we + ", signature=" + this.signature + ", width=" + this.width + ", height=" + this.height + ", decodedResourceClass=" + this.Uf + ", transformation='" + this.Vf + '\'' + ", options=" + this.options + '}';
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        byte[] bArr = (byte[]) this.qa.b(8, byte[].class);
        ByteBuffer.wrap(bArr).putInt(this.width).putInt(this.height).array();
        this.signature.updateDiskCacheKey(messageDigest);
        this.we.updateDiskCacheKey(messageDigest);
        messageDigest.update(bArr);
        j<?> jVar = this.Vf;
        if (jVar != null) {
            jVar.updateDiskCacheKey(messageDigest);
        }
        this.options.updateDiskCacheKey(messageDigest);
        messageDigest.update(nk());
        this.qa.put(bArr);
    }
}
