package com.android.camera.fragment.top;

/* compiled from: lambda */
public final /* synthetic */ class i implements Runnable {
    private final /* synthetic */ FragmentTopConfig ub;
    private final /* synthetic */ FragmentTopAlert vb;
    private final /* synthetic */ boolean wb;

    public /* synthetic */ i(FragmentTopConfig fragmentTopConfig, FragmentTopAlert fragmentTopAlert, boolean z) {
        this.ub = fragmentTopConfig;
        this.vb = fragmentTopAlert;
        this.wb = z;
    }

    public final void run() {
        this.ub.a(this.vb, this.wb);
    }
}
