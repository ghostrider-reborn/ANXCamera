package com.bumptech.glide.manager;

import android.support.annotation.NonNull;
import com.bumptech.glide.request.target.n;
import com.bumptech.glide.util.k;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: TargetTracker */
public final class o implements i {
    private final Set<n<?>> ov = Collections.newSetFromMap(new WeakHashMap());

    public void clear() {
        this.ov.clear();
    }

    public void g(@NonNull n<?> nVar) {
        this.ov.add(nVar);
    }

    @NonNull
    public List<n<?>> getAll() {
        return k.c(this.ov);
    }

    public void h(@NonNull n<?> nVar) {
        this.ov.remove(nVar);
    }

    public void onDestroy() {
        for (T onDestroy : k.c(this.ov)) {
            onDestroy.onDestroy();
        }
    }

    public void onStart() {
        for (T onStart : k.c(this.ov)) {
            onStart.onStart();
        }
    }

    public void onStop() {
        for (T onStop : k.c(this.ov)) {
            onStop.onStop();
        }
    }
}
