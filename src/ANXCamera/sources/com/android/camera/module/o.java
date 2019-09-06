package com.android.camera.module;

import com.android.camera2.CameraHardwareFace;

/* compiled from: lambda */
public final /* synthetic */ class o implements Runnable {
    private final /* synthetic */ Camera2Module ub;
    private final /* synthetic */ CameraHardwareFace[] vb;

    public /* synthetic */ o(Camera2Module camera2Module, CameraHardwareFace[] cameraHardwareFaceArr) {
        this.ub = camera2Module;
        this.vb = cameraHardwareFaceArr;
    }

    public final void run() {
        this.ub.a(this.vb);
    }
}
