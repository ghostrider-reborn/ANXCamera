package com.bumptech.glide.load.engine;

import com.bumptech.glide.util.a.d.a;

/* compiled from: Engine */
class p implements a<DecodeJob<?>> {
    final /* synthetic */ DecodeJobFactory this$0;

    p(DecodeJobFactory decodeJobFactory) {
        this.this$0 = decodeJobFactory;
    }

    public DecodeJob<?> create() {
        DecodeJobFactory decodeJobFactory = this.this$0;
        return new DecodeJob<>(decodeJobFactory.Ce, decodeJobFactory.pool);
    }
}
