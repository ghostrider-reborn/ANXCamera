package com.bumptech.glide.request.target;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.m;
import com.bumptech.glide.request.a.f;

/* compiled from: PreloadTarget */
public final class l<Z> extends m<Z> {
    private static final int Al = 1;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper(), new k());
    private final m da;

    private l(m mVar, int i, int i2) {
        super(i, i2);
        this.da = mVar;
    }

    public static <Z> l<Z> a(m mVar, int i, int i2) {
        return new l<>(mVar, i, i2);
    }

    public void a(@NonNull Z z, @Nullable f<? super Z> fVar) {
        HANDLER.obtainMessage(1, this).sendToTarget();
    }

    /* access modifiers changed from: 0000 */
    public void clear() {
        this.da.d((o<?>) this);
    }
}
