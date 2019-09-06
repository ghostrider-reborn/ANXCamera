package com.android.camera.module;

import com.android.camera.protocol.ModeProtocol.TopAlert;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    private final /* synthetic */ TopAlert ub;

    public /* synthetic */ d(TopAlert topAlert) {
        this.ub = topAlert;
    }

    public final void run() {
        this.ub.hideAlert();
    }
}
