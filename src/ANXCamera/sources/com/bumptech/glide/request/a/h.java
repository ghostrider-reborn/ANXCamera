package com.bumptech.glide.request.a;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.bumptech.glide.load.DataSource;

/* compiled from: ViewAnimationFactory */
public class h<R> implements g<R> {
    private final a Tl;
    private f<R> transition;

    /* compiled from: ViewAnimationFactory */
    private static class a implements a {
        private final Animation animation;

        a(Animation animation2) {
            this.animation = animation2;
        }

        public Animation g(Context context) {
            return this.animation;
        }
    }

    /* compiled from: ViewAnimationFactory */
    private static class b implements a {
        private final int Sl;

        b(int i) {
            this.Sl = i;
        }

        public Animation g(Context context) {
            return AnimationUtils.loadAnimation(context, this.Sl);
        }
    }

    public h(int i) {
        this((a) new b(i));
    }

    public h(Animation animation) {
        this((a) new a(animation));
    }

    h(a aVar) {
        this.Tl = aVar;
    }

    public f<R> a(DataSource dataSource, boolean z) {
        if (dataSource == DataSource.MEMORY_CACHE || !z) {
            return e.get();
        }
        if (this.transition == null) {
            this.transition = new k(this.Tl);
        }
        return this.transition;
    }
}
