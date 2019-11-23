package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.k;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* compiled from: RoundedCorners */
public final class u extends g {
    private static final String ID = "com.bumptech.glide.load.resource.bitmap.RoundedCorners";
    private static final byte[] lE = ID.getBytes(eI);
    private final int mJ;

    public u(int i) {
        i.a(i > 0, "roundingRadius must be greater than 0.");
        this.mJ = i;
    }

    public boolean equals(Object obj) {
        return (obj instanceof u) && this.mJ == ((u) obj).mJ;
    }

    public int hashCode() {
        return k.u(ID.hashCode(), k.hashCode(this.mJ));
    }

    /* access modifiers changed from: protected */
    public Bitmap transform(@NonNull d dVar, @NonNull Bitmap bitmap, int i, int i2) {
        return w.b(dVar, bitmap, this.mJ);
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(lE);
        messageDigest.update(ByteBuffer.allocate(4).putInt(this.mJ).array());
    }
}
