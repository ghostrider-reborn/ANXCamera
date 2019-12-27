package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.l;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* compiled from: RoundedCorners */
public final class w extends g {
    private static final String ID = "com.bumptech.glide.load.resource.bitmap.RoundedCorners";
    private static final byte[] ui = ID.getBytes(c.CHARSET);
    private final int vi;

    public w(int i) {
        i.a(i > 0, "roundingRadius must be greater than 0.");
        this.vi = i;
    }

    public boolean equals(Object obj) {
        return (obj instanceof w) && this.vi == ((w) obj).vi;
    }

    public int hashCode() {
        return l.n(ID.hashCode(), l.hashCode(this.vi));
    }

    /* access modifiers changed from: protected */
    public Bitmap transform(@NonNull d dVar, @NonNull Bitmap bitmap, int i, int i2) {
        return y.b(dVar, bitmap, this.vi);
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ui);
        messageDigest.update(ByteBuffer.allocate(4).putInt(this.vi).array());
    }
}
