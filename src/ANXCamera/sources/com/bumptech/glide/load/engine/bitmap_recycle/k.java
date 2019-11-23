package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

/* compiled from: LruPoolStrategy */
interface k {
    @Nullable
    Bitmap b(int i, int i2, Bitmap.Config config);

    @Nullable
    Bitmap bN();

    String c(int i, int i2, Bitmap.Config config);

    void d(Bitmap bitmap);

    String e(Bitmap bitmap);

    int f(Bitmap bitmap);
}
