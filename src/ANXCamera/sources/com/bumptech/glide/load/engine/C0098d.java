package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.c;
import java.security.MessageDigest;

/* renamed from: com.bumptech.glide.load.engine.d  reason: case insensitive filesystem */
/* compiled from: DataCacheKey */
final class C0098d implements c {
    private final c signature;
    private final c we;

    C0098d(c cVar, c cVar2) {
        this.we = cVar;
        this.signature = cVar2;
    }

    /* access modifiers changed from: package-private */
    public c cg() {
        return this.we;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C0098d)) {
            return false;
        }
        C0098d dVar = (C0098d) obj;
        return this.we.equals(dVar.we) && this.signature.equals(dVar.signature);
    }

    public int hashCode() {
        return (this.we.hashCode() * 31) + this.signature.hashCode();
    }

    public String toString() {
        return "DataCacheKey{sourceKey=" + this.we + ", signature=" + this.signature + '}';
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.we.updateDiskCacheKey(messageDigest);
        this.signature.updateDiskCacheKey(messageDigest);
    }
}
