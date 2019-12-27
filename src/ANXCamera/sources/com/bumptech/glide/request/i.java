package com.bumptech.glide.request;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

/* compiled from: ThumbnailRequestCoordinator */
public class i implements d, c {
    private boolean isRunning;
    @Nullable
    private final d parent;
    private c thumb;
    private c ul;

    @VisibleForTesting
    i() {
        this((d) null);
    }

    public i(@Nullable d dVar) {
        this.parent = dVar;
    }

    private boolean Lk() {
        d dVar = this.parent;
        return dVar == null || dVar.g(this);
    }

    private boolean Mk() {
        d dVar = this.parent;
        return dVar == null || dVar.c(this);
    }

    private boolean Nk() {
        d dVar = this.parent;
        return dVar == null || dVar.d(this);
    }

    private boolean Ok() {
        d dVar = this.parent;
        return dVar != null && dVar.k();
    }

    public void a(c cVar, c cVar2) {
        this.ul = cVar;
        this.thumb = cVar2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0029 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    public boolean a(c cVar) {
        if (!(cVar instanceof i)) {
            return false;
        }
        i iVar = (i) cVar;
        c cVar2 = this.ul;
        if (cVar2 == null) {
            if (iVar.ul != null) {
                return false;
            }
        } else if (!cVar2.a(iVar.ul)) {
            return false;
        }
        c cVar3 = this.thumb;
        if (cVar3 == null) {
            return iVar.thumb == null;
        }
        if (!cVar3.a(iVar.thumb)) {
            return false;
        }
    }

    public void b(c cVar) {
        if (!cVar.equals(this.thumb)) {
            d dVar = this.parent;
            if (dVar != null) {
                dVar.b(this);
            }
            if (!this.thumb.isComplete()) {
                this.thumb.clear();
            }
        }
    }

    public void begin() {
        this.isRunning = true;
        if (!this.ul.isComplete() && !this.thumb.isRunning()) {
            this.thumb.begin();
        }
        if (this.isRunning && !this.ul.isRunning()) {
            this.ul.begin();
        }
    }

    public boolean c(c cVar) {
        return Mk() && cVar.equals(this.ul) && !k();
    }

    public void clear() {
        this.isRunning = false;
        this.thumb.clear();
        this.ul.clear();
    }

    public boolean d(c cVar) {
        return Nk() && (cVar.equals(this.ul) || !this.ul.l());
    }

    public void e(c cVar) {
        if (cVar.equals(this.ul)) {
            d dVar = this.parent;
            if (dVar != null) {
                dVar.e(this);
            }
        }
    }

    public boolean g(c cVar) {
        return Lk() && cVar.equals(this.ul);
    }

    public boolean isCancelled() {
        return this.ul.isCancelled();
    }

    public boolean isComplete() {
        return this.ul.isComplete() || this.thumb.isComplete();
    }

    public boolean isFailed() {
        return this.ul.isFailed();
    }

    public boolean isPaused() {
        return this.ul.isPaused();
    }

    public boolean isRunning() {
        return this.ul.isRunning();
    }

    public boolean k() {
        return Ok() || l();
    }

    public boolean l() {
        return this.ul.l() || this.thumb.l();
    }

    public void pause() {
        this.isRunning = false;
        this.ul.pause();
        this.thumb.pause();
    }

    public void recycle() {
        this.ul.recycle();
        this.thumb.recycle();
    }
}
