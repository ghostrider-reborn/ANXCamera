package com.bumptech.glide.load.a;

import android.support.annotation.NonNull;
import java.io.IOException;

/* compiled from: DataRewinder */
public interface e<T> {

    /* compiled from: DataRewinder */
    public interface a<T> {
        @NonNull
        Class<T> M();

        @NonNull
        e<T> build(@NonNull T t);
    }

    @NonNull
    T C() throws IOException;

    void cleanup();
}
