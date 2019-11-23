package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;

@Deprecated
/* compiled from: VideoBitmapDecoder */
public class y extends VideoDecoder<ParcelFileDescriptor> {
    public y(Context context) {
        this(c.c(context).L());
    }

    public y(d dVar) {
        super(dVar, new VideoDecoder.b());
    }
}
