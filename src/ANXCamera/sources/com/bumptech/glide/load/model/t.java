package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.g;
import com.bumptech.glide.util.i;
import java.util.Collections;
import java.util.List;

/* compiled from: ModelLoader */
public interface t<Model, Data> {

    /* compiled from: ModelLoader */
    public static class a<Data> {
        public final List<c> ai;
        public final d<Data> bi;
        public final c we;

        public a(@NonNull c cVar, @NonNull d<Data> dVar) {
            this(cVar, Collections.emptyList(), dVar);
        }

        public a(@NonNull c cVar, @NonNull List<c> list, @NonNull d<Data> dVar) {
            i.checkNotNull(cVar);
            this.we = cVar;
            i.checkNotNull(list);
            this.ai = list;
            i.checkNotNull(dVar);
            this.bi = dVar;
        }
    }

    @Nullable
    a<Data> a(@NonNull Model model, int i, int i2, @NonNull g gVar);

    boolean c(@NonNull Model model);
}
