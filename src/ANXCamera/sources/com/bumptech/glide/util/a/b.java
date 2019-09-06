package com.bumptech.glide.util.a;

import android.support.annotation.NonNull;
import com.bumptech.glide.util.a.d.a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: FactoryPools */
class b implements a<List<T>> {
    b() {
    }

    @NonNull
    public List<T> create() {
        return new ArrayList();
    }
}
