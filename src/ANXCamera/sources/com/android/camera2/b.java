package com.android.camera2;

/* compiled from: lambda */
public final /* synthetic */ class b implements Runnable {
    private final /* synthetic */ MiCamera2 ub;
    private final /* synthetic */ boolean vb;

    public /* synthetic */ b(MiCamera2 miCamera2, boolean z) {
        this.ub = miCamera2;
        this.vb = z;
    }

    public final void run() {
        this.ub.n(this.vb);
    }
}
