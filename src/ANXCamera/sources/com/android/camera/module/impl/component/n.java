package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class n implements Consumer {
    private final /* synthetic */ boolean ub;

    public /* synthetic */ n(boolean z) {
        this.ub = z;
    }

    public final void accept(Object obj) {
        ConfigChangeImpl.a(this.ub, (BaseModule) obj);
    }
}
