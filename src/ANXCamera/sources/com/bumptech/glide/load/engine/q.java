package com.bumptech.glide.load.engine;

import com.bumptech.glide.util.a.d.a;

/* compiled from: Engine */
class q implements a<EngineJob<?>> {
    final /* synthetic */ EngineJobFactory this$0;

    q(EngineJobFactory engineJobFactory) {
        this.this$0 = engineJobFactory;
    }

    public EngineJob<?> create() {
        EngineJobFactory engineJobFactory = this.this$0;
        EngineJob engineJob = new EngineJob(engineJobFactory.Nb, engineJobFactory.Mb, engineJobFactory.lf, engineJobFactory.Rb, engineJobFactory.listener, engineJobFactory.pool);
        return engineJob;
    }
}
