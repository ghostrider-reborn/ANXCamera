package com.android.camera.module;

/* compiled from: lambda */
public final /* synthetic */ class x implements Runnable {
    private final /* synthetic */ Camera2Module ub;

    public /* synthetic */ x(Camera2Module camera2Module) {
        this.ub = camera2Module;
    }

    public final void run() {
        this.ub.handlePendingScreenSlide();
    }
}
