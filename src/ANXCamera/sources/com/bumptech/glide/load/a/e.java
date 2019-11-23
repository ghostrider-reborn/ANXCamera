package com.bumptech.glide.load.a;

import android.support.annotation.NonNull;
import java.io.IOException;

/* compiled from: DataRewinder */
public interface e<T> {

    /* compiled from: DataRewinder */
    public interface a<T> {
        @NonNull
        Class<T> aK();

        @NonNull
        e<T> o(@NonNull T t);
    }

    @NonNull
    T aN() throws IOException;

    void cleanup();
}
