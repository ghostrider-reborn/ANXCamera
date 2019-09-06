package com.android.camera.fragment.top;

import com.android.camera.statistic.CameraStatUtil;

/* compiled from: lambda */
public final /* synthetic */ class e implements Runnable {
    public static final /* synthetic */ e INSTANCE = new e();

    private /* synthetic */ e() {
    }

    public final void run() {
        CameraStatUtil.trackLyingDirectShow(0);
    }
}
