package com.android.camera.module;

/* compiled from: lambda */
public final /* synthetic */ class l implements Runnable {
    private final /* synthetic */ Camera2Module ub;
    private final /* synthetic */ boolean vb;
    private final /* synthetic */ int wb;

    public /* synthetic */ l(Camera2Module camera2Module, boolean z, int i) {
        this.ub = camera2Module;
        this.vb = z;
        this.wb = i;
    }

    public final void run() {
        this.ub.c(this.vb, this.wb);
    }
}
