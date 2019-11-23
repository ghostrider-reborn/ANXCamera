package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.engine.l;
import com.bumptech.glide.load.engine.p;
import com.bumptech.glide.util.i;

/* compiled from: LazyBitmapDrawableResource */
public final class s implements l, p<BitmapDrawable> {
    private final p<Bitmap> mG;
    private final Resources resources;

    private s(@NonNull Resources resources2, @NonNull p<Bitmap> pVar) {
        this.resources = (Resources) i.checkNotNull(resources2);
        this.mG = (p) i.checkNotNull(pVar);
    }

    @Nullable
    public static p<BitmapDrawable> a(@NonNull Resources resources2, @Nullable p<Bitmap> pVar) {
        if (pVar == null) {
            return null;
        }
        return new s(resources2, pVar);
    }

    @Deprecated
    public static s a(Context context, Bitmap bitmap) {
        return (s) a(context.getResources(), (p<Bitmap>) f.a(bitmap, c.c(context).L()));
    }

    @Deprecated
    public static s a(Resources resources2, d dVar, Bitmap bitmap) {
        return (s) a(resources2, (p<Bitmap>) f.a(bitmap, dVar));
    }

    @NonNull
    public Class<BitmapDrawable> bH() {
        return BitmapDrawable.class;
    }

    @NonNull
    /* renamed from: da */
    public BitmapDrawable get() {
        return new BitmapDrawable(this.resources, this.mG.get());
    }

    public int getSize() {
        return this.mG.getSize();
    }

    public void initialize() {
        if (this.mG instanceof l) {
            ((l) this.mG).initialize();
        }
    }

    public void recycle() {
        this.mG.recycle();
    }
}
