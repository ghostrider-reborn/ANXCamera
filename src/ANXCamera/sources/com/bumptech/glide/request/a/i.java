package com.bumptech.glide.request.a;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.request.a.j;

/* compiled from: ViewPropertyAnimationFactory */
public class i<R> implements g<R> {
    private final j.a qJ;
    private j<R> qK;

    public i(j.a aVar) {
        this.qJ = aVar;
    }

    public f<R> a(DataSource dataSource, boolean z) {
        if (dataSource == DataSource.MEMORY_CACHE || !z) {
            return e.fj();
        }
        if (this.qK == null) {
            this.qK = new j<>(this.qJ);
        }
        return this.qK;
    }
}
