package com.bumptech.glide.request.a;

import android.view.View;

/* compiled from: ViewPropertyTransition */
public class j<R> implements f<R> {
    private final a animator;

    /* compiled from: ViewPropertyTransition */
    public interface a {
        void animate(View view);
    }

    public j(a aVar) {
        this.animator = aVar;
    }

    public boolean a(R r, com.bumptech.glide.request.a.f.a aVar) {
        if (aVar.getView() != null) {
            this.animator.animate(aVar.getView());
        }
        return false;
    }
}
