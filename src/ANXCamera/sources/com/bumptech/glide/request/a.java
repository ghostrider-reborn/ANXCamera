package com.bumptech.glide.request;

import android.support.annotation.Nullable;

/* compiled from: ErrorRequestCoordinator */
public final class a implements c, d {
    @Nullable
    private final d oF;
    private c oG;
    private c oH;

    public a(@Nullable d dVar) {
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

    private boolean g(c cVar) {
        return cVar.equals(this.oG) || (this.oG.isFailed() && cVar.equals(this.oH));
    }

    public void a(c cVar, c cVar2) {
        this.oG = cVar;
        this.oH = cVar2;
    }

    public void begin() {
        if (!this.oG.isRunning()) {
            this.oG.begin();
        }
    }

    public boolean c(c cVar) {
        if (!(cVar instanceof a)) {
            return false;
        }
        a aVar = (a) cVar;
        return this.oG.c(aVar.oG) && this.oH.c(aVar.oH);
    }

    public void clear() {
        this.oG.clear();
        if (this.oH.isRunning()) {
            this.oH.clear();
        }
    }

    public boolean d(c cVar) {
        return dN() && g(cVar);
    }

    public boolean dM() {
        return (this.oG.isFailed() ? this.oH : this.oG).dM();
    }

    public boolean dQ() {
        return dR() || dM();
    }

    public boolean e(c cVar) {
        return dP() && g(cVar);
    }

    public boolean f(c cVar) {
        return dO() && g(cVar);
    }

    public void h(c cVar) {
        if (this.oF != null) {
            this.oF.h(this);
        }
    }

    public void i(c cVar) {
        if (!cVar.equals(this.oH)) {
            if (!this.oH.isRunning()) {
                this.oH.begin();
            }
        } else if (this.oF != null) {
            this.oF.i(this);
        }
    }

    public boolean isCancelled() {
        return (this.oG.isFailed() ? this.oH : this.oG).isCancelled();
    }

    public boolean isComplete() {
        return (this.oG.isFailed() ? this.oH : this.oG).isComplete();
    }

    public boolean isFailed() {
        return this.oG.isFailed() && this.oH.isFailed();
    }

    public boolean isPaused() {
        return (this.oG.isFailed() ? this.oH : this.oG).isPaused();
    }

    public boolean isRunning() {
        return (this.oG.isFailed() ? this.oH : this.oG).isRunning();
    }

    public void pause() {
        if (!this.oG.isFailed()) {
            this.oG.pause();
        }
        if (this.oH.isRunning()) {
            this.oH.pause();
        }
    }

    public void recycle() {
        this.oG.recycle();
        this.oH.recycle();
    }
}
