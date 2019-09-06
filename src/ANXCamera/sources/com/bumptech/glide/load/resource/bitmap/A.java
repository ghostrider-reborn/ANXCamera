package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.bitmap_recycle.d;

@Deprecated
/* compiled from: VideoBitmapDecoder */
public class A extends VideoDecoder<ParcelFileDescriptor> {
    public A(Context context) {
        this(c.get(context).Cf());
    }

    public A(d dVar) {
        super(dVar, new b());
    }
}
