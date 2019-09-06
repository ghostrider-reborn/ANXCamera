package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.j;
import java.security.MessageDigest;

/* compiled from: DrawableTransformation */
public class r implements j<Drawable> {
    private final boolean ij;
    private final j<Bitmap> wa;

    public r(j<Bitmap> jVar, boolean z) {
        this.wa = jVar;
        this.ij = z;
    }

    private A<Drawable> a(Context context, A<Bitmap> a2) {
        return u.a(context.getResources(), a2);
    }

    public j<BitmapDrawable> Eg() {
        return this;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof r)) {
            return false;
        }
        return this.wa.equals(((r) obj).wa);
    }

    public int hashCode() {
        return this.wa.hashCode();
    }

    @NonNull
    public A<Drawable> transform(@NonNull Context context, @NonNull A<Drawable> a2, int i, int i2) {
        Drawable drawable = (Drawable) a2.get();
        A a3 = q.a(c.get(context).Cf(), drawable, i, i2);
        if (a3 != null) {
            A transform = this.wa.transform(context, a3, i, i2);
            if (!transform.equals(a3)) {
                return a(context, transform);
            }
            transform.recycle();
            return a2;
        } else if (!this.ij) {
            return a2;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to convert ");
            sb.append(drawable);
            sb.append(" to a Bitmap");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.wa.updateDiskCacheKey(messageDigest);
    }
}
