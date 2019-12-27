package com.bumptech.glide.d;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.i;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ResourceEncoderRegistry */
public class f {
    private final List<a<?>> tk = new ArrayList();

    /* compiled from: ResourceEncoderRegistry */
    private static final class a<T> {
        private final Class<T> Be;
        final i<T> encoder;

        a(@NonNull Class<T> cls, @NonNull i<T> iVar) {
            this.Be = cls;
            this.encoder = iVar;
        }

        /* access modifiers changed from: package-private */
        public boolean g(@NonNull Class<?> cls) {
            return this.Be.isAssignableFrom(cls);
        }
    }

    public synchronized <Z> void a(@NonNull Class<Z> cls, @NonNull i<Z> iVar) {
        this.tk.add(new a(cls, iVar));
    }

    public synchronized <Z> void b(@NonNull Class<Z> cls, @NonNull i<Z> iVar) {
        this.tk.add(0, new a(cls, iVar));
    }

    @Nullable
    public synchronized <Z> i<Z> get(@NonNull Class<Z> cls) {
        int size = this.tk.size();
        for (int i = 0; i < size; i++) {
            a aVar = this.tk.get(i);
            if (aVar.g(cls)) {
                return aVar.encoder;
            }
        }
        return null;
    }
}
