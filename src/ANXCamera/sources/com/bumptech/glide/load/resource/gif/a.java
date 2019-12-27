package com.bumptech.glide.load.resource.gif;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.b.a;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.bitmap_recycle.d;

/* compiled from: GifBitmapProvider */
public final class a implements a.C0004a {
    private final d Bb;
    @Nullable
    private final b qa;

    public a(d dVar) {
        this(dVar, (b) null);
    }

    public a(d dVar, @Nullable b bVar) {
        this.Bb = dVar;
        this.qa = bVar;
    }

    @NonNull
    public Bitmap a(int i, int i2, @NonNull Bitmap.Config config) {
        return this.Bb.c(i, i2, config);
    }

    public void a(@NonNull int[] iArr) {
        b bVar = this.qa;
        if (bVar != null) {
            bVar.put(iArr);
        }
    }

    public void d(@NonNull Bitmap bitmap) {
        this.Bb.a(bitmap);
    }

    public void d(@NonNull byte[] bArr) {
        b bVar = this.qa;
        if (bVar != null) {
            bVar.put(bArr);
        }
    }

    @NonNull
    public byte[] d(int i) {
        b bVar = this.qa;
        return bVar == null ? new byte[i] : (byte[]) bVar.a(i, byte[].class);
    }

    @NonNull
    public int[] f(int i) {
        b bVar = this.qa;
        return bVar == null ? new int[i] : (int[]) bVar.a(i, int[].class);
    }
}
