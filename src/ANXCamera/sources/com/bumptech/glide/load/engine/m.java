package com.bumptech.glide.load.engine;

import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.load.c;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Jobs */
final class m {
    private final Map<c, EngineJob<?>> hO = new HashMap();
    private final Map<c, EngineJob<?>> hP = new HashMap();

    m() {
    }

    private Map<c, EngineJob<?>> g(boolean z) {
        return z ? this.hP : this.hO;
    }

    /* access modifiers changed from: package-private */
    public void a(c cVar, EngineJob<?> engineJob) {
        g(engineJob.bA()).put(cVar, engineJob);
    }

    /* access modifiers changed from: package-private */
    public void b(c cVar, EngineJob<?> engineJob) {
        Map<c, EngineJob<?>> g = g(engineJob.bA());
        if (engineJob.equals(g.get(cVar))) {
            g.remove(cVar);
        }
    }

    /* access modifiers changed from: package-private */
    public EngineJob<?> c(c cVar, boolean z) {
        return g(z).get(cVar);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public Map<c, EngineJob<?>> getAll() {
        return Collections.unmodifiableMap(this.hO);
    }
}
