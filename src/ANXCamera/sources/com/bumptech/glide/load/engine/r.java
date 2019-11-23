package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.i;
import com.bumptech.glide.util.f;
import com.bumptech.glide.util.k;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* compiled from: ResourceCacheKey */
final class r implements c {
    private static final f<Class<?>, byte[]> hV = new f<>(50);
    private final b br;
    private final c fK;
    private final c fP;
    private final com.bumptech.glide.load.f fR;
    private final Class<?> hW;
    private final i<?> hX;
    private final int height;
    private final int width;

    r(b bVar, c cVar, c cVar2, int i, int i2, i<?> iVar, Class<?> cls, com.bumptech.glide.load.f fVar) {
        this.br = bVar;
        this.fK = cVar;
        this.fP = cVar2;
        this.width = i;
        this.height = i2;
        this.hX = iVar;
        this.hW = cls;
        this.fR = fVar;
    }

    private byte[] bL() {
        byte[] bArr = hV.get(this.hW);
        if (bArr != null) {
            return bArr;
        }
        byte[] bytes = this.hW.getName().getBytes(eI);
        hV.put(this.hW, bytes);
        return bytes;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof r)) {
            return false;
        }
        r rVar = (r) obj;
        return this.height == rVar.height && this.width == rVar.width && k.d(this.hX, rVar.hX) && this.hW.equals(rVar.hW) && this.fK.equals(rVar.fK) && this.fP.equals(rVar.fP) && this.fR.equals(rVar.fR);
    }

    public int hashCode() {
        int hashCode = (((((this.fK.hashCode() * 31) + this.fP.hashCode()) * 31) + this.width) * 31) + this.height;
        if (this.hX != null) {
            hashCode = (hashCode * 31) + this.hX.hashCode();
        }
        return (31 * ((hashCode * 31) + this.hW.hashCode())) + this.fR.hashCode();
    }

    public String toString() {
        return "ResourceCacheKey{sourceKey=" + this.fK + ", signature=" + this.fP + ", width=" + this.width + ", height=" + this.height + ", decodedResourceClass=" + this.hW + ", transformation='" + this.hX + '\'' + ", options=" + this.fR + '}';
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        byte[] bArr = (byte[]) this.br.b(8, byte[].class);
        ByteBuffer.wrap(bArr).putInt(this.width).putInt(this.height).array();
        this.fP.updateDiskCacheKey(messageDigest);
        this.fK.updateDiskCacheKey(messageDigest);
        messageDigest.update(bArr);
        if (this.hX != null) {
            this.hX.updateDiskCacheKey(messageDigest);
        }
        this.fR.updateDiskCacheKey(messageDigest);
        messageDigest.update(bL());
        this.br.put(bArr);
    }
}
