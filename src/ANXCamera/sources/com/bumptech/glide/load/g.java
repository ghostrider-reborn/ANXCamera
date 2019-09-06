package com.bumptech.glide.load;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.bumptech.glide.util.CachedHashCodeArrayMap;
import java.security.MessageDigest;

/* compiled from: Options */
public final class g implements c {
    private final ArrayMap<f<?>, Object> values = new CachedHashCodeArrayMap();

    private static <T> void a(@NonNull f<T> fVar, @NonNull Object obj, @NonNull MessageDigest messageDigest) {
        fVar.a(obj, messageDigest);
    }

    @NonNull
    public <T> g a(@NonNull f<T> fVar, @NonNull T t) {
        this.values.put(fVar, t);
        return this;
    }

    @Nullable
    public <T> T a(@NonNull f<T> fVar) {
        return this.values.containsKey(fVar) ? this.values.get(fVar) : fVar.getDefaultValue();
    }

    public void b(@NonNull g gVar) {
        this.values.putAll(gVar.values);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof g)) {
            return false;
        }
        return this.values.equals(((g) obj).values);
    }

    public int hashCode() {
        return this.values.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Options{values=");
        sb.append(this.values);
        sb.append('}');
        return sb.toString();
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        for (int i = 0; i < this.values.size(); i++) {
            a((f) this.values.keyAt(i), this.values.valueAt(i), messageDigest);
        }
    }
}
