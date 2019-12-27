package com.bumptech.glide.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: LruCache */
public class f<T, Y> {
    private final Map<T, Y> cache = new LinkedHashMap(100, 0.75f, true);
    private long ig;
    private long maxSize;
    private final long mg;

    public f(long j) {
        this.mg = j;
        this.maxSize = j;
    }

    private void pk() {
        h(this.maxSize);
    }

    public void G() {
        h(0);
    }

    public synchronized long J() {
        return this.ig;
    }

    public synchronized void a(float f2) {
        if (f2 >= 0.0f) {
            this.maxSize = (long) Math.round(((float) this.mg) * f2);
            pk();
        } else {
            throw new IllegalArgumentException("Multiplier must be >= 0");
        }
    }

    /* access modifiers changed from: protected */
    public void b(@NonNull T t, @Nullable Y y) {
    }

    public synchronized boolean contains(@NonNull T t) {
        return this.cache.containsKey(t);
    }

    @Nullable
    public synchronized Y get(@NonNull T t) {
        return this.cache.get(t);
    }

    /* access modifiers changed from: protected */
    public synchronized int getCount() {
        return this.cache.size();
    }

    public synchronized long getMaxSize() {
        return this.maxSize;
    }

    /* access modifiers changed from: protected */
    public synchronized void h(long j) {
        while (this.ig > j) {
            Iterator<Map.Entry<T, Y>> it = this.cache.entrySet().iterator();
            Map.Entry next = it.next();
            Object value = next.getValue();
            this.ig -= (long) r(value);
            Object key = next.getKey();
            it.remove();
            b(key, value);
        }
    }

    @Nullable
    public synchronized Y put(@NonNull T t, @Nullable Y y) {
        long r = (long) r(y);
        if (r >= this.maxSize) {
            b(t, y);
            return null;
        }
        if (y != null) {
            this.ig += r;
        }
        Y put = this.cache.put(t, y);
        if (put != null) {
            this.ig -= (long) r(put);
            if (!put.equals(y)) {
                b(t, put);
            }
        }
        pk();
        return put;
    }

    /* access modifiers changed from: protected */
    public int r(@Nullable Y y) {
        return 1;
    }

    @Nullable
    public synchronized Y remove(@NonNull T t) {
        Y remove;
        remove = this.cache.remove(t);
        if (remove != null) {
            this.ig -= (long) r(remove);
        }
        return remove;
    }
}
