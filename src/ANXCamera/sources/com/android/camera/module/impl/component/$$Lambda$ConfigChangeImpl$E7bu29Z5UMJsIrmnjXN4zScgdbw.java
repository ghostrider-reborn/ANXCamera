package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* renamed from: com.android.camera.module.impl.component.-$$Lambda$ConfigChangeImpl$E7bu29Z5UMJsIrmnjXN4zScgdbw  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$ConfigChangeImpl$E7bu29Z5UMJsIrmnjXN4zScgdbw implements Consumer {
    public static final /* synthetic */ $$Lambda$ConfigChangeImpl$E7bu29Z5UMJsIrmnjXN4zScgdbw INSTANCE = new $$Lambda$ConfigChangeImpl$E7bu29Z5UMJsIrmnjXN4zScgdbw();

    private /* synthetic */ $$Lambda$ConfigChangeImpl$E7bu29Z5UMJsIrmnjXN4zScgdbw() {
    }

    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(11, 37);
    }
}
