package com.bumptech.glide.load.engine;

import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.load.c;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Jobs */
final class w {
    private final Map<c, EngineJob<?>> Of = new HashMap();
    private final Map<c, EngineJob<?>> of = new HashMap();

    w() {
    }

    private Map<c, EngineJob<?>> x(boolean z) {
        return z ? this.Of : this.of;
    }

    /* access modifiers changed from: package-private */
    public EngineJob<?> a(c cVar, boolean z) {
        return x(z).get(cVar);
    }

    /* access modifiers changed from: package-private */
    public void a(c cVar, EngineJob<?> engineJob) {
        x(engineJob.sg()).put(cVar, engineJob);
    }

    /* access modifiers changed from: package-private */
    public void b(c cVar, EngineJob<?> engineJob) {
        Map<c, EngineJob<?>> x = x(engineJob.sg());
        if (engineJob.equals(x.get(cVar))) {
            x.remove(cVar);
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public Map<c, EngineJob<?>> getAll() {
        return Collections.unmodifiableMap(this.of);
    }
}
