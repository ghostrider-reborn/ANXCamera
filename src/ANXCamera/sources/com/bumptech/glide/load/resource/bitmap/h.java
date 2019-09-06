package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.n;
import com.bumptech.glide.request.a.b;
import com.bumptech.glide.request.a.c;
import com.bumptech.glide.request.a.c.a;
import com.bumptech.glide.request.a.g;

/* compiled from: BitmapTransitionOptions */
public final class h extends n<h, Bitmap> {
    @NonNull
    public static h Yf() {
        return new h().Xf();
    }

    @NonNull
    public static h b(@NonNull a aVar) {
        return new h().a(aVar);
    }

    @NonNull
    public static h b(@NonNull c cVar) {
        return new h().a(cVar);
    }

    @NonNull
    public static h b(@NonNull g<Bitmap> gVar) {
        return (h) new h().a(gVar);
    }

    @NonNull
    public static h d(@NonNull g<Drawable> gVar) {
        return new h().c(gVar);
    }

    @NonNull
    public static h n(int i) {
        return new h().m(i);
    }

    @NonNull
    public h Xf() {
        return a(new a());
    }

    @NonNull
    public h a(@NonNull a aVar) {
        return c(aVar.build());
    }

    @NonNull
    public h a(@NonNull c cVar) {
        return c(cVar);
    }

    @NonNull
    public h c(@NonNull g<Drawable> gVar) {
        return (h) a((g<? super TranscodeType>) new b<Object>(gVar));
    }

    @NonNull
    public h m(int i) {
        return a(new a(i));
    }
}
