package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.p;
import com.bumptech.glide.load.i;
import java.security.MessageDigest;

@Deprecated
/* compiled from: BitmapDrawableTransformation */
public class d implements i<BitmapDrawable> {
    private final i<Drawable> lA;

    public d(i<Bitmap> iVar) {
        this.lA = (i) com.bumptech.glide.util.i.checkNotNull(new p(iVar, false));
    }

    private static p<BitmapDrawable> j(p<Drawable> pVar) {
        if (pVar.get() instanceof BitmapDrawable) {
            return pVar;
        }
        throw new IllegalArgumentException("Wrapped transformation unexpectedly returned a non BitmapDrawable resource: " + pVar.get());
    }

    private static p<Drawable> k(p<BitmapDrawable> pVar) {
        return pVar;
    }

    public boolean equals(Object obj) {
        if (obj instanceof d) {
            return this.lA.equals(((d) obj).lA);
        }
        return false;
    }

    public int hashCode() {
        return this.lA.hashCode();
    }

    @NonNull
    public p<BitmapDrawable> transform(@NonNull Context context, @NonNull p<BitmapDrawable> pVar, int i, int i2) {
        return j(this.lA.transform(context, k(pVar), i, i2));
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.lA.updateDiskCacheKey(messageDigest);
    }
}
