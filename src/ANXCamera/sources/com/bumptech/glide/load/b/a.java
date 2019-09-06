package com.bumptech.glide.load.b;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.util.i;

/* compiled from: SimpleResource */
public class a<T> implements A<T> {
    protected final T data;

    public a(@NonNull T t) {
        i.checkNotNull(t);
        this.data = t;
    }

    @NonNull
    public final T get() {
        return this.data;
    }

    public final int getSize() {
        return 1;
    }

    public void recycle() {
    }

    @NonNull
    public Class<T> z() {
        return this.data.getClass();
    }
}
