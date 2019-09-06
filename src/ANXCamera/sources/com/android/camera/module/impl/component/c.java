package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class c implements Consumer {
    public static final /* synthetic */ c INSTANCE = new c();

    private /* synthetic */ c() {
    }

    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(10);
    }
}
