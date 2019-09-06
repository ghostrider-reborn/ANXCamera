package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class b implements Consumer {
    private final /* synthetic */ int[] ub;

    public /* synthetic */ b(int[] iArr) {
        this.ub = iArr;
    }

    public final void accept(Object obj) {
        ConfigChangeImpl.a(this.ub, (BaseModule) obj);
    }
}
