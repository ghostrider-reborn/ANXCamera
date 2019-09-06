package com.android.camera.fragment;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    private final /* synthetic */ FragmentMainContent ub;
    private final /* synthetic */ boolean vb;

    public /* synthetic */ d(FragmentMainContent fragmentMainContent, boolean z) {
        this.ub = fragmentMainContent;
        this.vb = z;
    }

    public final void run() {
        this.ub.f(this.vb);
    }
}
