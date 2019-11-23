package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* renamed from: com.android.camera.module.impl.component.-$$Lambda$ConfigChangeImpl$EN1G-iuB2wlLMsL6_MklXgSFfxk  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$ConfigChangeImpl$EN1GiuB2wlLMsL6_MklXgSFfxk implements Consumer {
    public static final /* synthetic */ $$Lambda$ConfigChangeImpl$EN1GiuB2wlLMsL6_MklXgSFfxk INSTANCE = new $$Lambda$ConfigChangeImpl$EN1GiuB2wlLMsL6_MklXgSFfxk();

    private /* synthetic */ $$Lambda$ConfigChangeImpl$EN1GiuB2wlLMsL6_MklXgSFfxk() {
    }

    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(29);
    }
}
