package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.c;
import java.security.MessageDigest;

/* compiled from: DataCacheKey */
final class b implements c {
    private final c fK;
    private final c fP;

    b(c cVar, c cVar2) {
        this.fK = cVar;
        this.fP = cVar2;
    }

    /* access modifiers changed from: package-private */
    public c aV() {
        return this.fK;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return this.fK.equals(bVar.fK) && this.fP.equals(bVar.fP);
    }

    public int hashCode() {
        return (31 * this.fK.hashCode()) + this.fP.hashCode();
    }

    public String toString() {
        return "DataCacheKey{sourceKey=" + this.fK + ", signature=" + this.fP + '}';
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.fK.updateDiskCacheKey(messageDigest);
        this.fP.updateDiskCacheKey(messageDigest);
    }
}
