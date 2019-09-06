package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.util.f;

/* compiled from: ModelCache */
class s extends f<ModelKey<A>, B> {
    final /* synthetic */ ModelCache this$0;

    s(ModelCache modelCache, long j) {
        this.this$0 = modelCache;
        super(j);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void b(@NonNull ModelKey<A> modelKey, @Nullable B b2) {
        modelKey.release();
    }
}
