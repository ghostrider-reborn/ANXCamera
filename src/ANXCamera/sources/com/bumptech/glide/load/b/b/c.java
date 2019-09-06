package com.bumptech.glide.load.b.b;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.n;
import com.bumptech.glide.request.a.c.a;
import com.bumptech.glide.request.a.g;

/* compiled from: DrawableTransitionOptions */
public final class c extends n<c, Drawable> {
    @NonNull
    public static c Yf() {
        return new c().Xf();
    }

    @NonNull
    public static c b(@NonNull a aVar) {
        return new c().a(aVar);
    }

    @NonNull
    public static c b(@NonNull com.bumptech.glide.request.a.c cVar) {
        return new c().a(cVar);
    }

    @NonNull
    public static c b(@NonNull g<Drawable> gVar) {
        return (c) new c().a(gVar);
    }

    @NonNull
    public static c n(int i) {
        return new c().m(i);
    }

    @NonNull
    public c Xf() {
        return a(new a());
    }

    @NonNull
    public c a(@NonNull a aVar) {
        return a(aVar.build());
    }

    @NonNull
    public c a(@NonNull com.bumptech.glide.request.a.c cVar) {
        return (c) a((g<? super TranscodeType>) cVar);
    }

    @NonNull
    public c m(int i) {
        return a(new a(i));
    }
}
