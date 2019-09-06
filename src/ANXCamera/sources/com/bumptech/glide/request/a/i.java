package com.bumptech.glide.request.a;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.request.a.j.a;

/* compiled from: ViewPropertyAnimationFactory */
public class i<R> implements g<R> {
    private j<R> animation;
    private final a animator;

    public i(a aVar) {
        this.animator = aVar;
    }

    public f<R> a(DataSource dataSource, boolean z) {
        if (dataSource == DataSource.MEMORY_CACHE || !z) {
            return e.get();
        }
        if (this.animation == null) {
            this.animation = new j<>(this.animator);
        }
        return this.animation;
    }
}
