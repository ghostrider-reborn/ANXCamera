package com.bumptech.glide.d;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.bumptech.glide.util.h;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ModelToResourceClassCache */
public class d {
    private final AtomicReference<h> wk = new AtomicReference<>();
    private final ArrayMap<h, List<Class<?>>> xk = new ArrayMap<>();

    public void a(@NonNull Class<?> cls, @NonNull Class<?> cls2, @NonNull List<Class<?>> list) {
        synchronized (this.xk) {
            this.xk.put(new h(cls, cls2), list);
        }
    }

    public void clear() {
        synchronized (this.xk) {
            this.xk.clear();
        }
    }

    @Nullable
    public List<Class<?>> d(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
        List<Class<?>> list;
        h hVar = (h) this.wk.getAndSet(null);
        if (hVar == null) {
            hVar = new h(cls, cls2);
        } else {
            hVar.h(cls, cls2);
        }
        synchronized (this.xk) {
            list = (List) this.xk.get(hVar);
        }
        this.wk.set(hVar);
        return list;
    }
}
