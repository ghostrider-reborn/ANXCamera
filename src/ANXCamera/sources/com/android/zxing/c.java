package com.android.zxing;

import io.reactivex.functions.Function;

/* compiled from: lambda */
public final /* synthetic */ class c implements Function {
    private final /* synthetic */ QrDecoder ub;

    public /* synthetic */ c(QrDecoder qrDecoder) {
        this.ub = qrDecoder;
    }

    public final Object apply(Object obj) {
        return this.ub.b((PreviewImage) obj);
    }
}
