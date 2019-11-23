package com.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.p;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.resource.bitmap.f;
import java.security.MessageDigest;

/* compiled from: GifDrawableTransformation */
public class e implements i<b> {
    private final i<Bitmap> lA;

    /* JADX WARNING: type inference failed for: r1v0, types: [java.lang.Object, com.bumptech.glide.load.i<android.graphics.Bitmap>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    public e(i<Bitmap> r1) {
        this.lA = (i) com.bumptech.glide.util.i.checkNotNull(r1);
    }

    public boolean equals(Object obj) {
        if (obj instanceof e) {
            return this.lA.equals(((e) obj).lA);
        }
        return false;
    }

    public int hashCode() {
        return this.lA.hashCode();
    }

    @NonNull
    public p<b> transform(@NonNull Context context, @NonNull p<b> pVar, int i, int i2) {
        b bVar = pVar.get();
        f fVar = new f(bVar.dj(), c.c(context).L());
        p<Bitmap> transform = this.lA.transform(context, fVar, i, i2);
        if (!fVar.equals(transform)) {
            fVar.recycle();
        }
        bVar.a(this.lA, transform.get());
        return pVar;
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.lA.updateDiskCacheKey(messageDigest);
    }
}
