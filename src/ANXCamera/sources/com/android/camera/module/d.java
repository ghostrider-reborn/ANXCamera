package com.android.camera.module;

import com.android.camera.protocol.ModeProtocol;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    private final /* synthetic */ ModeProtocol.TopAlert ub;

    public /* synthetic */ d(ModeProtocol.TopAlert topAlert) {
        this.ub = topAlert;
    }

    public final void run() {
        this.ub.hideAlert();
    }
}
