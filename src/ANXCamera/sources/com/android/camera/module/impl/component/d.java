package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class d implements Consumer {
    private final /* synthetic */ ConfigChangeImpl ub;

    public /* synthetic */ d(ConfigChangeImpl configChangeImpl) {
        this.ub = configChangeImpl;
    }

    public final void accept(Object obj) {
        this.ub.h((BaseModule) obj);
    }
}
