package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class g implements Consumer {
    public static final /* synthetic */ g INSTANCE = new g();

    private /* synthetic */ g() {
    }

    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(29);
    }
}
