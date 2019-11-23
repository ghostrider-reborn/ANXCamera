package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* renamed from: com.android.camera.module.impl.component.-$$Lambda$ConfigChangeImpl$uU5NlSthpXgrK7CLXW2ujx2dUfM  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$ConfigChangeImpl$uU5NlSthpXgrK7CLXW2ujx2dUfM implements Consumer {
    public static final /* synthetic */ $$Lambda$ConfigChangeImpl$uU5NlSthpXgrK7CLXW2ujx2dUfM INSTANCE = new $$Lambda$ConfigChangeImpl$uU5NlSthpXgrK7CLXW2ujx2dUfM();

    private /* synthetic */ $$Lambda$ConfigChangeImpl$uU5NlSthpXgrK7CLXW2ujx2dUfM() {
    }

    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(11, 10);
    }
}
