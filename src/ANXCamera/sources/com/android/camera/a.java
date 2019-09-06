package com.android.camera;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    private final /* synthetic */ Camera ub;
    private final /* synthetic */ boolean vb;
    private final /* synthetic */ boolean wb;

    public /* synthetic */ a(Camera camera, boolean z, boolean z2) {
        this.ub = camera;
        this.vb = z;
        this.wb = z2;
    }

    public final void run() {
        this.ub.b(this.vb, this.wb);
    }
}
