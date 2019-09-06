package com.bumptech.glide.load.b.d;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.resource.bitmap.f;
import com.bumptech.glide.load.resource.gif.b;

/* compiled from: DrawableBytesTranscoder */
public final class c implements e<Drawable, byte[]> {
    private final d Bb;
    private final e<Bitmap, byte[]> Zj;
    private final e<b, byte[]> _j;

    public c(@NonNull d dVar, @NonNull e<Bitmap, byte[]> eVar, @NonNull e<b, byte[]> eVar2) {
        this.Bb = dVar;
        this.Zj = eVar;
        this._j = eVar2;
    }

    @NonNull
    private static A<b> l(@NonNull A<Drawable> a2) {
        return a2;
    }

    @Nullable
    public A<byte[]> a(@NonNull A<Drawable> a2, @NonNull g gVar) {
        Drawable drawable = (Drawable) a2.get();
        if (drawable instanceof BitmapDrawable) {
            return this.Zj.a(f.a(((BitmapDrawable) drawable).getBitmap(), this.Bb), gVar);
        }
        if (drawable instanceof b) {
            return this._j.a(a2, gVar);
        }
        return null;
    }
}
