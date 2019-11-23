package com.bumptech.glide.d;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/* compiled from: EncoderRegistry */
public class a {
    private final List<C0004a<?>> ox = new ArrayList();

    /* renamed from: com.bumptech.glide.d.a$a  reason: collision with other inner class name */
    /* compiled from: EncoderRegistry */
    private static final class C0004a<T> {
        private final Class<T> dataClass;
        final com.bumptech.glide.load.a<T> fQ;

        C0004a(@NonNull Class<T> cls, @NonNull com.bumptech.glide.load.a<T> aVar) {
            this.dataClass = cls;
            this.fQ = aVar;
        }

        /* access modifiers changed from: package-private */
        public boolean m(@NonNull Class<?> cls) {
            return this.dataClass.isAssignableFrom(cls);
        }
    }

    public synchronized <T> void d(@NonNull Class<T> cls, @NonNull com.bumptech.glide.load.a<T> aVar) {
        this.ox.add(new C0004a(cls, aVar));
    }

    public synchronized <T> void e(@NonNull Class<T> cls, @NonNull com.bumptech.glide.load.a<T> aVar) {
        this.ox.add(0, new C0004a(cls, aVar));
    }

    @Nullable
    public synchronized <T> com.bumptech.glide.load.a<T> n(@NonNull Class<T> cls) {
        for (C0004a next : this.ox) {
            if (next.m(cls)) {
                return next.fQ;
            }
        }
        return null;
    }
}
