package com.android.camera2;

import android.hardware.camera2.CaptureRequest;
import java.util.function.BiConsumer;

/* compiled from: lambda */
public final /* synthetic */ class a implements BiConsumer {
    private final /* synthetic */ CaptureRequest.Builder ub;

    public /* synthetic */ a(CaptureRequest.Builder builder) {
        this.ub = builder;
    }

    public final void accept(Object obj, Object obj2) {
        CaptureSessionConfigurations.a(this.ub, obj, obj2);
    }
}
