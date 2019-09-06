package com.bumptech.glide.b;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;

/* compiled from: GifDecoder */
public interface a {
    public static final int STATUS_FORMAT_ERROR = 1;
    public static final int STATUS_OK = 0;
    public static final int STATUS_OPEN_ERROR = 2;
    public static final int rr = 3;
    public static final int sr = 0;

    /* renamed from: com.bumptech.glide.b.a$a reason: collision with other inner class name */
    /* compiled from: GifDecoder */
    public interface C0004a {
        @NonNull
        Bitmap a(int i, int i2, @NonNull Config config);

        void a(@NonNull int[] iArr);

        void d(@NonNull Bitmap bitmap);

        void d(@NonNull byte[] bArr);

        @NonNull
        byte[] d(int i);

        @NonNull
        int[] f(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: GifDecoder */
    public @interface b {
    }

    int F();

    int I();

    int N();

    void a(@NonNull Config config);

    void a(@NonNull c cVar, @NonNull ByteBuffer byteBuffer);

    void a(@NonNull c cVar, @NonNull ByteBuffer byteBuffer, int i);

    void a(@NonNull c cVar, @NonNull byte[] bArr);

    void advance();

    void clear();

    @NonNull
    ByteBuffer getData();

    int getDelay(int i);

    int getFrameCount();

    int getHeight();

    @Deprecated
    int getLoopCount();

    @Nullable
    Bitmap getNextFrame();

    int getStatus();

    int getWidth();

    int read(@Nullable InputStream inputStream, int i);

    int read(@Nullable byte[] bArr);

    void s();

    int v();

    int x();
}
