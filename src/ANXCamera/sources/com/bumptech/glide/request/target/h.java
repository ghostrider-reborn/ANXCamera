package com.bumptech.glide.request.target;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.request.a.f;
import com.bumptech.glide.request.a.f.a;

/* compiled from: ImageViewTarget */
public abstract class h<Z> extends ViewTarget<ImageView, Z> implements a {
    @Nullable
    private Animatable Hl;

    public h(ImageView imageView) {
        super(imageView);
    }

    @Deprecated
    public h(ImageView imageView, boolean z) {
        super(imageView, z);
    }

    private void x(@Nullable Z z) {
        if (z instanceof Animatable) {
            this.Hl = (Animatable) z;
            this.Hl.start();
            return;
        }
        this.Hl = null;
    }

    private void y(@Nullable Z z) {
        o(z);
        x(z);
    }

    public void a(@NonNull Z z, @Nullable f<? super Z> fVar) {
        if (fVar == null || !fVar.a(z, this)) {
            y(z);
        } else {
            x(z);
        }
    }

    public void b(@Nullable Drawable drawable) {
        super.b(drawable);
        Animatable animatable = this.Hl;
        if (animatable != null) {
            animatable.stop();
        }
        y(null);
        setDrawable(drawable);
    }

    public void c(@Nullable Drawable drawable) {
        super.c(drawable);
        y(null);
        setDrawable(drawable);
    }

    public void d(@Nullable Drawable drawable) {
        super.d(drawable);
        y(null);
        setDrawable(drawable);
    }

    @Nullable
    public Drawable getCurrentDrawable() {
        return ((ImageView) this.view).getDrawable();
    }

    /* access modifiers changed from: protected */
    public abstract void o(@Nullable Z z);

    public void onStart() {
        Animatable animatable = this.Hl;
        if (animatable != null) {
            animatable.start();
        }
    }

    public void onStop() {
        Animatable animatable = this.Hl;
        if (animatable != null) {
            animatable.stop();
        }
    }

    public void setDrawable(Drawable drawable) {
        ((ImageView) this.view).setImageDrawable(drawable);
    }
}
