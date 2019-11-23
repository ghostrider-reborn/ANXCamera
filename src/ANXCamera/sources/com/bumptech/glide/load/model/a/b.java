package com.bumptech.glide.load.model.a;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.android.volley.DefaultRetryPolicy;
import com.bumptech.glide.load.a.j;
import com.bumptech.glide.load.e;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.g;
import com.bumptech.glide.load.model.m;
import com.bumptech.glide.load.model.n;
import com.bumptech.glide.load.model.q;
import java.io.InputStream;

/* compiled from: HttpGlideUrlLoader */
public class b implements m<g, InputStream> {
    public static final e<Integer> lw = e.a("com.bumptech.glide.load.model.stream.HttpGlideUrlLoader.Timeout", Integer.valueOf(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS));
    @Nullable
    private final ModelCache<g, g> lv;

    /* compiled from: HttpGlideUrlLoader */
    public static class a implements n<g, InputStream> {
        private final ModelCache<g, g> lv = new ModelCache<>(500);

        @NonNull
        public m<g, InputStream> a(q qVar) {
            return new b(this.lv);
        }

        public void cB() {
        }
    }

    public b() {
        this((ModelCache<g, g>) null);
    }

    public b(@Nullable ModelCache<g, g> modelCache) {
        this.lv = modelCache;
    }

    /* renamed from: a */
    public m.a<InputStream> b(@NonNull g gVar, int i, int i2, @NonNull f fVar) {
        if (this.lv != null) {
            g c = this.lv.c(gVar, 0, 0);
            if (c == null) {
                this.lv.a(gVar, 0, 0, gVar);
            } else {
                gVar = c;
            }
        }
        return new m.a<>(gVar, new j(gVar, ((Integer) fVar.a(lw)).intValue()));
    }

    /* renamed from: a */
    public boolean t(@NonNull g gVar) {
        return true;
    }
}
