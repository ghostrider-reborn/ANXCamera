package com.android.zxing;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class d implements Consumer {
    private final /* synthetic */ QrDecoder ub;

    public /* synthetic */ d(QrDecoder qrDecoder) {
        this.ub = qrDecoder;
    }

    public final void accept(Object obj) {
        this.ub.m((String) obj);
    }
}
