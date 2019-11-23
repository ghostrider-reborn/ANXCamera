package com.bumptech.glide.load.resource.b;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.j;
import com.bumptech.glide.request.a.c;
import com.bumptech.glide.request.a.g;

/* compiled from: DrawableTransitionOptions */
public final class c extends j<c, Drawable> {
    @NonNull
    public static c J(int i) {
        return new c().K(i);
    }

    @NonNull
    public static c c(@NonNull c.a aVar) {
        return new c().d(aVar);
    }

    @NonNull
    public static c c(@NonNull com.bumptech.glide.request.a.c cVar) {
        return new c().d(cVar);
    }

    @NonNull
    public static c dh() {
        return new c().di();
    }

    @NonNull
    public static c f(@NonNull g<Drawable> gVar) {
        return (c) new c().b(gVar);
    }

    @NonNull
    public c K(int i) {
        return d(new c.a(i));
    }

    @NonNull
    public c d(@NonNull c.a aVar) {
        return d(aVar.fh());
    }

    @NonNull
    public c d(@NonNull com.bumptech.glide.request.a.c cVar) {
        return (c) b(cVar);
    }

    @NonNull
    public c di() {
        return d(new c.a());
    }
}
