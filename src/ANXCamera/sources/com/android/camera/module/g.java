package com.android.camera.module;

import com.android.camera.fragment.GoogleLensFragment;

/* compiled from: lambda */
public final /* synthetic */ class g implements GoogleLensFragment.OnClickListener {
    private final /* synthetic */ int Ab;
    private final /* synthetic */ Camera2Module ub;
    private final /* synthetic */ float vb;
    private final /* synthetic */ float wb;
    private final /* synthetic */ int zb;

    public /* synthetic */ g(Camera2Module camera2Module, float f2, float f3, int i, int i2) {
        this.ub = camera2Module;
        this.vb = f2;
        this.wb = f3;
        this.zb = i;
        this.Ab = i2;
    }

    public final void onOptionClick(int i) {
        this.ub.a(this.vb, this.wb, this.zb, this.Ab, i);
    }
}
