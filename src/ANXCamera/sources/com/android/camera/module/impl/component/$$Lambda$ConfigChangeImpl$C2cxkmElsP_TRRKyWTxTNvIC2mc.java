package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* renamed from: com.android.camera.module.impl.component.-$$Lambda$ConfigChangeImpl$C2cxkmElsP_TRRKyWTxTNvIC2mc  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$ConfigChangeImpl$C2cxkmElsP_TRRKyWTxTNvIC2mc implements Consumer {
    public static final /* synthetic */ $$Lambda$ConfigChangeImpl$C2cxkmElsP_TRRKyWTxTNvIC2mc INSTANCE = new $$Lambda$ConfigChangeImpl$C2cxkmElsP_TRRKyWTxTNvIC2mc();

    private /* synthetic */ $$Lambda$ConfigChangeImpl$C2cxkmElsP_TRRKyWTxTNvIC2mc() {
    }

    public final void accept(Object obj) {
        ((BaseModule) obj).onSharedPreferenceChanged();
    }
}
