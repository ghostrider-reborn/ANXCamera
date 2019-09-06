package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class e implements Consumer {
    public static final /* synthetic */ e INSTANCE = new e();

    private /* synthetic */ e() {
    }

    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(45);
    }
}
