package com.android.camera.fragment.vv;

import com.android.camera.data.observeable.RxData;
import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class a implements Consumer {
    private final /* synthetic */ FragmentVVProcess ub;

    public /* synthetic */ a(FragmentVVProcess fragmentVVProcess) {
        this.ub = fragmentVVProcess;
    }

    public final void accept(Object obj) {
        this.ub.a((RxData.DataWrap) obj);
    }
}
