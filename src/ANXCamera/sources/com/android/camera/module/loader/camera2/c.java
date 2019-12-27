package com.android.camera.module.loader.camera2;

import com.android.camera2.Camera2Proxy;

/* compiled from: lambda */
public final /* synthetic */ class c implements Camera2Proxy.CaptureBusyCallback {
    private final /* synthetic */ Camera2OpenManager ub;
    private final /* synthetic */ boolean vb;

    public /* synthetic */ c(Camera2OpenManager camera2OpenManager, boolean z) {
        this.ub = camera2OpenManager;
        this.vb = z;
    }

    public final void onCaptureCompleted(boolean z) {
        this.ub.d(this.vb, z);
    }
}
