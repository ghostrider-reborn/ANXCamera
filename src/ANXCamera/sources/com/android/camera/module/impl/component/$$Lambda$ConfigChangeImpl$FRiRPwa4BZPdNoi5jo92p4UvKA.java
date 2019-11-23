package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* renamed from: com.android.camera.module.impl.component.-$$Lambda$ConfigChangeImpl$FRi-RPwa4BZPdNoi5jo92p4UvKA  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$ConfigChangeImpl$FRiRPwa4BZPdNoi5jo92p4UvKA implements Consumer {
    public static final /* synthetic */ $$Lambda$ConfigChangeImpl$FRiRPwa4BZPdNoi5jo92p4UvKA INSTANCE = new $$Lambda$ConfigChangeImpl$FRiRPwa4BZPdNoi5jo92p4UvKA();

    private /* synthetic */ $$Lambda$ConfigChangeImpl$FRiRPwa4BZPdNoi5jo92p4UvKA() {
    }

    public final void accept(Object obj) {
        ((BaseModule) obj).onSharedPreferenceChanged();
    }
}
