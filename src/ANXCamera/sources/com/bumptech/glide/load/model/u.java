package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.model.m;

/* compiled from: UnitModelLoader */
public class u<Model> implements m<Model, Model> {
    private static final u<?> lp = new u<>();

    /* compiled from: UnitModelLoader */
    public static class a<Model> implements n<Model, Model> {
        private static final a<?> lq = new a<>();

        public static <T> a<T> cO() {
            return lq;
        }

        @NonNull
        public m<Model, Model> a(q qVar) {
            return u.cN();
        }

        public void cB() {
        }
    }

    /* compiled from: UnitModelLoader */
    private static class b<Model> implements d<Model> {
        private final Model resource;

        b(Model model) {
            this.resource = model;
        }

        public void a(@NonNull Priority priority, @NonNull d.a<? super Model> aVar) {
            aVar.n(this.resource);
        }

        @NonNull
        public Class<Model> aK() {
            return this.resource.getClass();
        }

        @NonNull
        public DataSource aL() {
            return DataSource.LOCAL;
        }

        public void cancel() {
        }

        public void cleanup() {
        }
    }

    public static <T> u<T> cN() {
        return lp;
    }

    public m.a<Model> b(@NonNull Model model, int i, int i2, @NonNull f fVar) {
        return new m.a<>(new com.bumptech.glide.e.d(model), new b(model));
    }

    public boolean t(@NonNull Model model) {
        return true;
    }
}
