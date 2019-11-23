package com.bumptech.glide.request;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

/* compiled from: ThumbnailRequestCoordinator */
public class h implements c, d {
    private boolean isRunning;
    @Nullable
    private final d oF;
    private c pX;
    private c pY;

    @VisibleForTesting
    h() {
        this((d) null);
    }

    public h(@Nullable d dVar) {
        this.oF = dVar;
    }

    private boolean dN() {
        return this.oF == null || this.oF.d(this);
    }

    private boolean dO() {
        return this.oF == null || this.oF.f(this);
    }

    private boolean dP() {
        return this.oF == null || this.oF.e(this);
    }

    private boolean dR() {
        return this.oF != null && this.oF.dQ();
    }

    public void a(c cVar, c cVar2) {
        this.pX = cVar;
        this.pY = cVar2;
    }

    public void begin() {
        this.isRunning = true;
        if (!this.pX.isComplete() && !this.pY.isRunning()) {
            this.pY.begin();
        }
        if (this.isRunning && !this.pX.isRunning()) {
            this.pX.begin();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x002d A[ORIG_RETURN, RETURN, SYNTHETIC] */
    public boolean c(c cVar) {
        if (!(cVar instanceof h)) {
            return false;
        }
        h hVar = (h) cVar;
        if (this.pX == null) {
            if (hVar.pX != null) {
                return false;
            }
        } else if (!this.pX.c(hVar.pX)) {
            return false;
        }
        if (this.pY == null) {
            return hVar.pY == null;
        }
        if (!this.pY.c(hVar.pY)) {
            return false;
        }
    }

    public void clear() {
        this.isRunning = false;
        this.pY.clear();
        this.pX.clear();
    }

    public boolean d(c cVar) {
        return dN() && (cVar.equals(this.pX) || !this.pX.dM());
    }

    public boolean dM() {
        return this.pX.dM() || this.pY.dM();
    }

    public boolean dQ() {
        return dR() || dM();
    }

    public boolean e(c cVar) {
        return dP() && cVar.equals(this.pX) && !dQ();
    }

    public boolean f(c cVar) {
        return dO() && cVar.equals(this.pX);
    }

    public void h(c cVar) {
        if (!cVar.equals(this.pY)) {
            if (this.oF != null) {
                this.oF.h(this);
            }
            if (!this.pY.isComplete()) {
                this.pY.clear();
            }
        }
    }

    public void i(c cVar) {
        if (cVar.equals(this.pX) && this.oF != null) {
            this.oF.i(this);
        }
    }

    public boolean isCancelled() {
        return this.pX.isCancelled();
    }

    public boolean isComplete() {
        return this.pX.isComplete() || this.pY.isComplete();
    }

    public boolean isFailed() {
        return this.pX.isFailed();
    }

    public boolean isPaused() {
        return this.pX.isPaused();
    }

    public boolean isRunning() {
        return this.pX.isRunning();
    }

    public void pause() {
        this.isRunning = false;
        this.pX.pause();
        this.pY.pause();
    }

    public void recycle() {
        this.pX.recycle();
        this.pY.recycle();
    }
}
