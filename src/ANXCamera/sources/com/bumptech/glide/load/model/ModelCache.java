package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.util.f;
import com.bumptech.glide.util.k;
import java.util.Queue;

public class ModelCache<A, B> {
    private static final int DEFAULT_SIZE = 250;
    private final f<ModelKey<A>, B> kV;

    @VisibleForTesting
    static final class ModelKey<A> {
        private static final Queue<ModelKey<?>> kX = k.ab(0);
        private A cz;
        private int height;
        private int width;

        private ModelKey() {
        }

        static <A> ModelKey<A> d(A a2, int i, int i2) {
            ModelKey<A> poll;
            synchronized (kX) {
                poll = kX.poll();
            }
            if (poll == null) {
                poll = new ModelKey<>();
            }
            poll.e(a2, i, i2);
            return poll;
        }

        private void e(A a2, int i, int i2) {
            this.cz = a2;
            this.width = i;
            this.height = i2;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ModelKey)) {
                return false;
            }
            ModelKey modelKey = (ModelKey) obj;
            return this.width == modelKey.width && this.height == modelKey.height && this.cz.equals(modelKey.cz);
        }

        public int hashCode() {
            return (31 * ((this.height * 31) + this.width)) + this.cz.hashCode();
        }

        public void release() {
            synchronized (kX) {
                kX.offer(this);
            }
        }
    }

    public ModelCache() {
        this(250);
    }

    public ModelCache(long j) {
        this.kV = new f<ModelKey<A>, B>(j) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void c(@NonNull ModelKey<A> modelKey, @Nullable B b2) {
                modelKey.release();
            }
        };
    }

    public void a(A a2, int i, int i2, B b2) {
        this.kV.put(ModelKey.d(a2, i, i2), b2);
    }

    @Nullable
    public B c(A a2, int i, int i2) {
        ModelKey d = ModelKey.d(a2, i, i2);
        B b2 = this.kV.get(d);
        d.release();
        return b2;
    }

    public void clear() {
        this.kV.P();
    }
}
