package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.annotation.NonNull;

/* compiled from: BitmapPool */
public interface d {
    void G();

    void a(float f2);

    void a(Bitmap bitmap);

    @NonNull
    Bitmap c(int i, int i2, Config config);

    @NonNull
    Bitmap d(int i, int i2, Config config);

    long getMaxSize();

    void trimMemory(int i);
}
