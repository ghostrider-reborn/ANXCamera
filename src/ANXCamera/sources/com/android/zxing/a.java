package com.android.zxing;

import io.reactivex.functions.Function;

/* compiled from: lambda */
public final /* synthetic */ class a implements Function {
    private final /* synthetic */ HandGestureDecoder ub;

    public /* synthetic */ a(HandGestureDecoder handGestureDecoder) {
        this.ub = handGestureDecoder;
    }

    public final Object apply(Object obj) {
        return this.ub.a((PreviewImage) obj);
    }
}
