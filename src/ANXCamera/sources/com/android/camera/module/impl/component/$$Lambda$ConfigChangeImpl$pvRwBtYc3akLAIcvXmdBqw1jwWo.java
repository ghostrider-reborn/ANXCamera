package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* renamed from: com.android.camera.module.impl.component.-$$Lambda$ConfigChangeImpl$pvRwBtYc3akLAIcvXmdBqw1jwWo  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$ConfigChangeImpl$pvRwBtYc3akLAIcvXmdBqw1jwWo implements Consumer {
    public static final /* synthetic */ $$Lambda$ConfigChangeImpl$pvRwBtYc3akLAIcvXmdBqw1jwWo INSTANCE = new $$Lambda$ConfigChangeImpl$pvRwBtYc3akLAIcvXmdBqw1jwWo();

    private /* synthetic */ $$Lambda$ConfigChangeImpl$pvRwBtYc3akLAIcvXmdBqw1jwWo() {
    }

    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(43);
    }
}
