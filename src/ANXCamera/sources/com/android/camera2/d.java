package com.android.camera2;

import android.media.ImageReader;

/* compiled from: lambda */
public final /* synthetic */ class d implements ImageReader.OnImageAvailableListener {
    private final /* synthetic */ MiCamera2 ub;

    public /* synthetic */ d(MiCamera2 miCamera2) {
        this.ub = miCamera2;
    }

    public final void onImageAvailable(ImageReader imageReader) {
        this.ub.a(imageReader);
    }
}
