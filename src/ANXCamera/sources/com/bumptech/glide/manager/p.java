package com.bumptech.glide.manager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.request.c;
import com.bumptech.glide.util.l;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: RequestTracker */
public class p {
    private static final String TAG = "RequestTracker";
    private final List<c> qk = new ArrayList();
    private final Set<c> requests = Collections.newSetFromMap(new WeakHashMap());
    private boolean rk;

    private boolean a(@Nullable c cVar, boolean z) {
        boolean z2 = true;
        if (cVar == null) {
            return true;
        }
        boolean remove = this.requests.remove(cVar);
        if (!this.qk.remove(cVar) && !remove) {
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

    public void Ig() {
        for (c a2 : l.b(this.requests)) {
            a(a2, false);
        }
        this.qk.clear();
    }

    public void Jg() {
        for (c cVar : l.b(this.requests)) {
            if (!cVar.isComplete() && !cVar.isCancelled()) {
                cVar.pause();
                if (!this.rk) {
                    cVar.begin();
                } else {
                    this.qk.add(cVar);
                }
            }
        }
    }

    public void Pf() {
        this.rk = true;
        for (c cVar : l.b(this.requests)) {
            if (cVar.isRunning() || cVar.isComplete()) {
                cVar.pause();
                this.qk.add(cVar);
            }
        }
    }

    public void Qf() {
        this.rk = true;
        for (c cVar : l.b(this.requests)) {
            if (cVar.isRunning()) {
                cVar.pause();
                this.qk.add(cVar);
            }
        }
    }

    public void Sf() {
        this.rk = false;
        for (c cVar : l.b(this.requests)) {
            if (!cVar.isComplete() && !cVar.isCancelled() && !cVar.isRunning()) {
                cVar.begin();
            }
        }
        this.qk.clear();
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public void addRequest(c cVar) {
        this.requests.add(cVar);
    }

    public boolean h(@Nullable c cVar) {
        return a(cVar, true);
    }

    public void i(@NonNull c cVar) {
        this.requests.add(cVar);
        if (!this.rk) {
            cVar.begin();
            return;
        }
        String str = TAG;
        if (Log.isLoggable(str, 2)) {
            Log.v(str, "Paused, delaying request");
        }
        this.qk.add(cVar);
    }

    public boolean isPaused() {
        return this.rk;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("{numRequests=");
        sb.append(this.requests.size());
        sb.append(", isPaused=");
        sb.append(this.rk);
        sb.append("}");
        return sb.toString();
    }
}
