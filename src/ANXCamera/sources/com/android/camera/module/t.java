package com.android.camera.module;

import com.android.camera.data.observeable.RxData;
import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class t implements Consumer {
    private final /* synthetic */ LiveModuleSubVV ub;

    public /* synthetic */ t(LiveModuleSubVV liveModuleSubVV) {
        this.ub = liveModuleSubVV;
    }

    public final void accept(Object obj) {
        this.ub.b((RxData.DataWrap) obj);
    }
}
