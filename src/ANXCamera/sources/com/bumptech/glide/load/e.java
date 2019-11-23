package com.bumptech.glide.load;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.util.i;
import java.security.MessageDigest;

/* compiled from: Option */
public final class e<T> {
    private static final a<Object> eK = new a<Object>() {
        public void a(@NonNull byte[] bArr, @NonNull Object obj, @NonNull MessageDigest messageDigest) {
        }
    };
    private final T defaultValue;
    private final a<T> eL;
    private volatile byte[] eM;
    private final String key;

    /* compiled from: Option */
    public interface a<T> {
        void a(@NonNull byte[] bArr, @NonNull T t, @NonNull MessageDigest messageDigest);
    }

    private e(@NonNull String str, @Nullable T t, @NonNull a<T> aVar) {
        this.key = i.I(str);
        this.defaultValue = t;
        this.eL = (a) i.checkNotNull(aVar);
    }

    @NonNull
    public static <T> e<T> a(@NonNull String str, @NonNull a<T> aVar) {
        return new e<>(str, (T) null, aVar);
    }

    @NonNull
    public static <T> e<T> a(@NonNull String str, @NonNull T t) {
        return new e<>(str, t, aJ());
    }

    @NonNull
    public static <T> e<T> a(@NonNull String str, @Nullable T t, @NonNull a<T> aVar) {
        return new e<>(str, t, aVar);
    }

    @NonNull
    private byte[] aI() {
        if (this.eM == null) {
            this.eM = this.key.getBytes(c.eI);
        }
        return this.eM;
    }

    @NonNull
    private static <T> a<T> aJ() {
        return eK;
    }

    @NonNull
    public static <T> e<T> r(@NonNull String str) {
        return new e<>(str, (T) null, aJ());
    }

    public void a(@NonNull T t, @NonNull MessageDigest messageDigest) {
        this.eL.a(aI(), t, messageDigest);
    }

    public boolean equals(Object obj) {
        if (obj instanceof e) {
            return this.key.equals(((e) obj).key);
        }
        return false;
    }

    @Nullable
    public T getDefaultValue() {
        return this.defaultValue;
    }

    public int hashCode() {
        return this.key.hashCode();
    }

    public String toString() {
        return "Option{key='" + this.key + '\'' + '}';
    }
}
