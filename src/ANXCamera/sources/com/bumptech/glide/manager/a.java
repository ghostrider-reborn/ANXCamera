package com.bumptech.glide.manager;

import android.support.annotation.NonNull;
import com.bumptech.glide.util.k;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: ActivityFragmentLifecycle */
class a implements h {
    private final Set<i> nS = Collections.newSetFromMap(new WeakHashMap());
    private boolean nT;
    private boolean np;

    a() {
    }

    public void a(@NonNull i iVar) {
        this.nS.add(iVar);
        if (this.nT) {
            iVar.onDestroy();
        } else if (this.np) {
            iVar.onStart();
        } else {
            iVar.onStop();
        }
    }

    public void b(@NonNull i iVar) {
        this.nS.remove(iVar);
    }

    /* access modifiers changed from: package-private */
    public void onDestroy() {
        this.nT = true;
        for (T onDestroy : k.c(this.nS)) {
            onDestroy.onDestroy();
        }
    }

    /* access modifiers changed from: package-private */
    public void onStart() {
        this.np = true;
        for (T onStart : k.c(this.nS)) {
            onStart.onStart();
        }
    }

    /* access modifiers changed from: package-private */
    public void onStop() {
        this.np = false;
        for (T onStop : k.c(this.nS)) {
            onStop.onStop();
        }
    }
}
