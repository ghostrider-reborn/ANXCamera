package com.android.camera.scene;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    private final /* synthetic */ AbASDResultParse ub;
    private final /* synthetic */ int vb;
    private final /* synthetic */ int wb;
    private final /* synthetic */ int zb;

    public /* synthetic */ a(AbASDResultParse abASDResultParse, int i, int i2, int i3) {
        this.ub = abASDResultParse;
        this.vb = i;
        this.wb = i2;
        this.zb = i3;
    }

    public final void run() {
        this.ub.a(this.vb, this.wb, this.zb);
    }
}
