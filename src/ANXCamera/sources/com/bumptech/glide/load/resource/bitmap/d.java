package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.j;
import com.bumptech.glide.util.i;
import java.security.MessageDigest;

@Deprecated
/* compiled from: BitmapDrawableTransformation */
public class d implements j<BitmapDrawable> {
    private final j<Drawable> wa;

    public d(j<Bitmap> jVar) {
        r rVar = new r(jVar, false);
        i.checkNotNull(rVar);
        this.wa = rVar;
    }

    private static A<BitmapDrawable> j(A<Drawable> a2) {
        if (a2.get() instanceof BitmapDrawable) {
            return a2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Wrapped transformation unexpectedly returned a non BitmapDrawable resource: ");
        sb.append(a2.get());
        throw new IllegalArgumentException(sb.toString());
    }

    private static A<Drawable> k(A<BitmapDrawable> a2) {
        return a2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof d)) {
            return false;
        }
        return this.wa.equals(((d) obj).wa);
    }

    public int hashCode() {
        return this.wa.hashCode();
    }

    @NonNull
    public A<BitmapDrawable> transform(@NonNull Context context, @NonNull A<BitmapDrawable> a2, int i, int i2) {
        A<BitmapDrawable> transform = this.wa.transform(context, a2, i, i2);
        j(transform);
        return transform;
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.wa.updateDiskCacheKey(messageDigest);
    }
}
