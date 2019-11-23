package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.i;
import java.security.MessageDigest;

/* compiled from: DrawableTransformation */
public class p implements i<Drawable> {
    private final i<Bitmap> lA;
    private final boolean my;

    public p(i<Bitmap> iVar, boolean z) {
        this.lA = iVar;
        this.my = z;
    }

    private com.bumptech.glide.load.engine.p<Drawable> a(Context context, com.bumptech.glide.load.engine.p<Bitmap> pVar) {
        return s.a(context.getResources(), pVar);
    }

    public i<BitmapDrawable> cX() {
        return this;
    }

    public boolean equals(Object obj) {
        if (obj instanceof p) {
            return this.lA.equals(((p) obj).lA);
        }
        return false;
    }

    public int hashCode() {
        return this.lA.hashCode();
    }

    @NonNull
    public com.bumptech.glide.load.engine.p<Drawable> transform(@NonNull Context context, @NonNull com.bumptech.glide.load.engine.p<Drawable> pVar, int i, int i2) {
        d L = c.c(context).L();
        Drawable drawable = pVar.get();
        com.bumptech.glide.load.engine.p<Bitmap> a2 = o.a(L, drawable, i, i2);
        if (a2 != null) {
            com.bumptech.glide.load.engine.p<Bitmap> transform = this.lA.transform(context, a2, i, i2);
            if (!transform.equals(a2)) {
                return a(context, transform);
            }
            transform.recycle();
            return pVar;
        } else if (!this.my) {
            return pVar;
        } else {
            throw new IllegalArgumentException("Unable to convert " + drawable + " to a Bitmap");
        }
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.lA.updateDiskCacheKey(messageDigest);
    }
}
