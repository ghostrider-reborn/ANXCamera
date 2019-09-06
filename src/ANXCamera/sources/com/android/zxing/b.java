package com.android.zxing;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class b implements Consumer {
    private final /* synthetic */ HandGestureDecoder ub;

    public /* synthetic */ b(HandGestureDecoder handGestureDecoder) {
        this.ub = handGestureDecoder;
    }

    public final void accept(Object obj) {
        this.ub.b((Integer) obj);
    }
}
