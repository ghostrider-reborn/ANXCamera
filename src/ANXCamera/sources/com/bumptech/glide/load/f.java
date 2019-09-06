package com.bumptech.glide.load;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.util.i;
import java.security.MessageDigest;

/* compiled from: Option */
public final class f<T> {
    private static final a<Object> Od = new e();
    private final a<T> Md;
    private volatile byte[] Nd;
    private final T defaultValue;
    private final String key;

    /* compiled from: Option */
    public interface a<T> {
        void a(@NonNull byte[] bArr, @NonNull T t, @NonNull MessageDigest messageDigest);
    }

    private f(@NonNull String str, @Nullable T t, @NonNull a<T> aVar) {
        i.u(str);
        this.key = str;
        this.defaultValue = t;
        i.checkNotNull(aVar);
        this.Md = aVar;
    }

    @NonNull
    public static <T> f<T> a(@NonNull String str, @NonNull a<T> aVar) {
        return new f<>(str, null, aVar);
    }

    @NonNull
    public static <T> f<T> a(@NonNull String str, @NonNull T t) {
        return new f<>(str, t, ak());
    }

    @NonNull
    public static <T> f<T> a(@NonNull String str, @Nullable T t, @NonNull a<T> aVar) {
        return new f<>(str, t, aVar);
    }

    @NonNull
    private static <T> a<T> ak() {
        return Od;
    }

    @NonNull
    private byte[] bk() {
        if (this.Nd == null) {
            this.Nd = this.key.getBytes(c.CHARSET);
        }
        return this.Nd;
    }

    @NonNull
    public static <T> f<T> q(@NonNull String str) {
        return new f<>(str, null, ak());
    }

    public void a(@NonNull T t, @NonNull MessageDigest messageDigest) {
        this.Md.a(bk(), t, messageDigest);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof f)) {
            return false;
        }
        return this.key.equals(((f) obj).key);
    }

    @Nullable
    public T getDefaultValue() {
        return this.defaultValue;
    }

    public int hashCode() {
        return this.key.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Option{key='");
        sb.append(this.key);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
