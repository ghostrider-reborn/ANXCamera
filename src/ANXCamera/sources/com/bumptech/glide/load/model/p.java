package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pools;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.model.m;
import com.bumptech.glide.util.i;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: MultiModelLoader */
class p<Model, Data> implements m<Model, Data> {
    private final List<m<Model, Data>> fL;
    private final Pools.Pool<List<Throwable>> le;

    /* compiled from: MultiModelLoader */
    static class a<Data> implements d.a<Data>, d<Data> {
        private final Pools.Pool<List<Throwable>> cu;
        private int currentIndex = 0;
        @Nullable
        private List<Throwable> exceptions;
        private Priority fY;
        private final List<d<Data>> lf;
        private d.a<? super Data> lg;

        a(@NonNull List<d<Data>> list, @NonNull Pools.Pool<List<Throwable>> pool) {
            this.cu = pool;
            i.b(list);
            this.lf = list;
        }

        private void cL() {
            if (this.currentIndex < this.lf.size() - 1) {
                this.currentIndex++;
                a(this.fY, this.lg);
                return;
            }
            i.checkNotNull(this.exceptions);
            this.lg.b(new GlideException("Fetch failed", (List<Throwable>) new ArrayList(this.exceptions)));
        }

        public void a(@NonNull Priority priority, @NonNull d.a<? super Data> aVar) {
            this.fY = priority;
            this.lg = aVar;
            this.exceptions = this.cu.acquire();
            this.lf.get(this.currentIndex).a(priority, this);
        }

        @NonNull
        public Class<Data> aK() {
            return this.lf.get(0).aK();
        }

        @NonNull
        public DataSource aL() {
            return this.lf.get(0).aL();
        }

        public void b(@NonNull Exception exc) {
            ((List) i.checkNotNull(this.exceptions)).add(exc);
            cL();
        }

        public void cancel() {
            for (d<Data> cancel : this.lf) {
                cancel.cancel();
            }
        }

        public void cleanup() {
            if (this.exceptions != null) {
                this.cu.release(this.exceptions);
            }
            this.exceptions = null;
            for (d<Data> cleanup : this.lf) {
                cleanup.cleanup();
            }
        }

        public void n(@Nullable Data data) {
            if (data != null) {
                this.lg.n(data);
            } else {
                cL();
            }
        }
    }

    p(@NonNull List<m<Model, Data>> list, @NonNull Pools.Pool<List<Throwable>> pool) {
        this.fL = list;
        this.le = pool;
    }

    public m.a<Data> b(@NonNull Model model, int i, int i2, @NonNull f fVar) {
        int size = this.fL.size();
        ArrayList arrayList = new ArrayList(size);
        c cVar = null;
        for (int i3 = 0; i3 < size; i3++) {
            m mVar = this.fL.get(i3);
            if (mVar.t(model)) {
                m.a b2 = mVar.b(model, i, i2, fVar);
                if (b2 != null) {
                    cVar = b2.fK;
                    arrayList.add(b2.kZ);
                }
            }
        }
        if (arrayList.isEmpty() || cVar == null) {
            return null;
        }
        return new m.a<>(cVar, new a(arrayList, this.le));
    }

    public boolean t(@NonNull Model model) {
        for (m<Model, Data> t : this.fL) {
            if (t.t(model)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "MultiModelLoader{modelLoaders=" + Arrays.toString(this.fL.toArray()) + '}';
    }
}
