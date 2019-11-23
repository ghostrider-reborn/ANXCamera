package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* renamed from: com.android.camera.module.impl.component.-$$Lambda$ConfigChangeImpl$dwnyNY8Bd5Qy3HBC0mcPWR3H1Us  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$ConfigChangeImpl$dwnyNY8Bd5Qy3HBC0mcPWR3H1Us implements Consumer {
    public static final /* synthetic */ $$Lambda$ConfigChangeImpl$dwnyNY8Bd5Qy3HBC0mcPWR3H1Us INSTANCE = new $$Lambda$ConfigChangeImpl$dwnyNY8Bd5Qy3HBC0mcPWR3H1Us();

    private /* synthetic */ $$Lambda$ConfigChangeImpl$dwnyNY8Bd5Qy3HBC0mcPWR3H1Us() {
    }

    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(10);
    }
}
