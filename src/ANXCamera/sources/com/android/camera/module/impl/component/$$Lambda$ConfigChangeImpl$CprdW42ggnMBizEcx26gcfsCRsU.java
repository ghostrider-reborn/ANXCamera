package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* renamed from: com.android.camera.module.impl.component.-$$Lambda$ConfigChangeImpl$CprdW42ggnMBizEcx26gcfsCRsU  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$ConfigChangeImpl$CprdW42ggnMBizEcx26gcfsCRsU implements Consumer {
    public static final /* synthetic */ $$Lambda$ConfigChangeImpl$CprdW42ggnMBizEcx26gcfsCRsU INSTANCE = new $$Lambda$ConfigChangeImpl$CprdW42ggnMBizEcx26gcfsCRsU();

    private /* synthetic */ $$Lambda$ConfigChangeImpl$CprdW42ggnMBizEcx26gcfsCRsU() {
    }

    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(45);
    }
}
