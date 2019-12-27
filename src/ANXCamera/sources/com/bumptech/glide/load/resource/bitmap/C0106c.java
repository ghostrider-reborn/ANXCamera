package com.bumptech.glide.load.resource.bitmap;

import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.b.b.b;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.engine.v;
import com.bumptech.glide.util.l;

/* renamed from: com.bumptech.glide.load.resource.bitmap.c  reason: case insensitive filesystem */
/* compiled from: BitmapDrawableResource */
public class C0106c extends b<BitmapDrawable> implements v {
    private final d Bb;

    public C0106c(BitmapDrawable bitmapDrawable, d dVar) {
        super(bitmapDrawable);
        this.Bb = dVar;
    }

    public int getSize() {
        return l.j(((BitmapDrawable) this.drawable).getBitmap());
    }

    public void initialize() {
        ((BitmapDrawable) this.drawable).getBitmap().prepareToDraw();
    }

    public void recycle() {
        this.Bb.a(((BitmapDrawable) this.drawable).getBitmap());
    }

    @NonNull
    public Class<BitmapDrawable> z() {
        return BitmapDrawable.class;
    }
}
