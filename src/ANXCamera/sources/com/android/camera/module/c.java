package com.android.camera.module;

import com.android.camera2.Camera2Proxy.CaptureCallback;

/* compiled from: lambda */
public final /* synthetic */ class c implements CaptureCallback {
    private final /* synthetic */ Camera2Module ub;

    public /* synthetic */ c(Camera2Module camera2Module) {
        this.ub = camera2Module;
    }

    public final void onCaptureCompleted(boolean z) {
        this.ub.l(z);
    }
}
