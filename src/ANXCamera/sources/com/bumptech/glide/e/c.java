package com.bumptech.glide.e;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* compiled from: MediaStoreSignature */
public class c implements com.bumptech.glide.load.c {
    private final long Wl;
    @NonNull
    private final String mimeType;
    private final int orientation;

    public c(@Nullable String str, long j, int i) {
        if (str == null) {
            str = "";
        }
        this.mimeType = str;
        this.Wl = j;
        this.orientation = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || c.class != obj.getClass()) {
            return false;
        }
        c cVar = (c) obj;
        return this.Wl == cVar.Wl && this.orientation == cVar.orientation && this.mimeType.equals(cVar.mimeType);
    }

    public int hashCode() {
        int hashCode = this.mimeType.hashCode() * 31;
        long j = this.Wl;
        return ((hashCode + ((int) (j ^ (j >>> 32)))) * 31) + this.orientation;
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ByteBuffer.allocate(12).putLong(this.Wl).putInt(this.orientation).array());
        messageDigest.update(this.mimeType.getBytes(com.bumptech.glide.load.c.CHARSET));
    }
}
