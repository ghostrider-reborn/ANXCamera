package com.bumptech.glide.manager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.request.c;
import com.bumptech.glide.util.k;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: RequestTracker */
public class n {
    private static final String TAG = "RequestTracker";
    private final Set<c> oo = Collections.newSetFromMap(new WeakHashMap());
    private final List<c> oq = new ArrayList();
    private boolean or;

    private boolean a(@Nullable c cVar, boolean z) {
        boolean z2 = true;
        if (cVar == null) {
            return true;
        }
        boolean remove = this.oo.remove(cVar);
        if (!this.oq.remove(cVar) && !remove) {
            z2 = false;
        }
        if (z2) {
            cVar.clear();
            if (z) {
                cVar.recycle();
            }
        }
        return z2;
    }

    public void a(@NonNull c cVar) {
        this.oo.add(cVar);
        if (!this.or) {
            cVar.begin();
            return;
        }
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "Paused, delaying request");
        }
        this.oq.add(cVar);
    }

    public void ad() {
        this.or = true;
        for (T t : k.c(this.oo)) {
            if (t.isRunning()) {
                t.pause();
                this.oq.add(t);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void addRequest(c cVar) {
        this.oo.add(cVar);
    }

    public void ae() {
        this.or = true;
        for (T t : k.c(this.oo)) {
            if (t.isRunning() || t.isComplete()) {
                t.pause();
                this.oq.add(t);
            }
        }
    }

    public void ag() {
        this.or = false;
        for (T t : k.c(this.oo)) {
            if (!t.isComplete() && !t.isCancelled() && !t.isRunning()) {
                t.begin();
            }
        }
        this.oq.clear();
    }

    public boolean b(@Nullable c cVar) {
        return a(cVar, true);
    }

    public void dG() {
        for (T a2 : k.c(this.oo)) {
            a(a2, false);
        }
        this.oq.clear();
    }

    public void dH() {
        for (T t : k.c(this.oo)) {
            if (!t.isComplete() && !t.isCancelled()) {
                t.pause();
                if (!this.or) {
                    t.begin();
                } else {
                    this.oq.add(t);
                }
            }
        }
    }

    public boolean isPaused() {
        return this.or;
    }

    public String toString() {
        return super.toString() + "{numRequests=" + this.oo.size() + ", isPaused=" + this.or + "}";
    }
}
