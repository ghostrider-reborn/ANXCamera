package com.android.camera2;

import com.android.camera2.Camera2Proxy.ScreenLightCallback;

/* compiled from: lambda */
public final /* synthetic */ class c implements Runnable {
    private final /* synthetic */ ScreenLightCallback ub;

    public /* synthetic */ c(ScreenLightCallback screenLightCallback) {
        this.ub = screenLightCallback;
    }

    public final void run() {
        this.ub.stopScreenLight();
    }
}
