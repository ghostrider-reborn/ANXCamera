package com.bumptech.glide.request.target;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import com.bumptech.glide.util.i;

/* compiled from: FixedSizeDrawable */
public class g extends Drawable {
    private final RectF Ga;
    private boolean Ha;
    private final RectF bounds;
    private final Matrix matrix;
    private a state;
    private Drawable wa;

    /* compiled from: FixedSizeDrawable */
    static final class a extends Drawable.ConstantState {
        final int height;
        private final Drawable.ConstantState wa;
        final int width;

        a(Drawable.ConstantState constantState, int i, int i2) {
            this.wa = constantState;
            this.width = i;
            this.height = i2;
        }

        a(a aVar) {
            this(aVar.wa, aVar.width, aVar.height);
        }

        public int getChangingConfigurations() {
            return 0;
        }

        @NonNull
        public Drawable newDrawable() {
            return new g(this, this.wa.newDrawable());
        }

        @NonNull
        public Drawable newDrawable(Resources resources) {
            return new g(this, this.wa.newDrawable(resources));
        }
    }

    public g(Drawable drawable, int i, int i2) {
        this(new a(drawable.getConstantState(), i, i2), drawable);
    }

    g(a aVar, Drawable drawable) {
        i.checkNotNull(aVar);
        this.state = aVar;
        i.checkNotNull(drawable);
        this.wa = drawable;
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        this.matrix = new Matrix();
        this.Ga = new RectF(0.0f, 0.0f, (float) drawable.getIntrinsicWidth(), (float) drawable.getIntrinsicHeight());
        this.bounds = new RectF();
    }

    private void Uj() {
        this.matrix.setRectToRect(this.Ga, this.bounds, Matrix.ScaleToFit.CENTER);
    }

    public void clearColorFilter() {
        this.wa.clearColorFilter();
    }

    public void draw(@NonNull Canvas canvas) {
        canvas.save();
        canvas.concat(this.matrix);
        this.wa.draw(canvas);
        canvas.restore();
    }

    @RequiresApi(19)
    public int getAlpha() {
        return this.wa.getAlpha();
    }

    public Drawable.Callback getCallback() {
        return this.wa.getCallback();
    }

    public int getChangingConfigurations() {
        return this.wa.getChangingConfigurations();
    }

    public Drawable.ConstantState getConstantState() {
        return this.state;
    }

    @NonNull
    public Drawable getCurrent() {
        return this.wa.getCurrent();
    }

    public int getIntrinsicHeight() {
        return this.state.height;
    }

    public int getIntrinsicWidth() {
        return this.state.width;
    }

    public int getMinimumHeight() {
        return this.wa.getMinimumHeight();
    }

    public int getMinimumWidth() {
        return this.wa.getMinimumWidth();
    }

    public int getOpacity() {
        return this.wa.getOpacity();
    }

    public boolean getPadding(@NonNull Rect rect) {
        return this.wa.getPadding(rect);
    }

    public void invalidateSelf() {
        super.invalidateSelf();
        this.wa.invalidateSelf();
    }

    @NonNull
    public Drawable mutate() {
        if (!this.Ha && super.mutate() == this) {
            this.wa = this.wa.mutate();
            this.state = new a(this.state);
            this.Ha = true;
        }
        return this;
    }

    public void scheduleSelf(@NonNull Runnable runnable, long j) {
        super.scheduleSelf(runnable, j);
        this.wa.scheduleSelf(runnable, j);
    }

    public void setAlpha(int i) {
        this.wa.setAlpha(i);
    }

    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        this.bounds.set((float) i, (float) i2, (float) i3, (float) i4);
        Uj();
    }

    public void setBounds(@NonNull Rect rect) {
        super.setBounds(rect);
        this.bounds.set(rect);
        Uj();
    }

    public void setChangingConfigurations(int i) {
        this.wa.setChangingConfigurations(i);
    }

    public void setColorFilter(int i, @NonNull PorterDuff.Mode mode) {
        this.wa.setColorFilter(i, mode);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.wa.setColorFilter(colorFilter);
    }

    @Deprecated
    public void setDither(boolean z) {
        this.wa.setDither(z);
    }

    public void setFilterBitmap(boolean z) {
        this.wa.setFilterBitmap(z);
    }

    public boolean setVisible(boolean z, boolean z2) {
        return this.wa.setVisible(z, z2);
    }

    public void unscheduleSelf(@NonNull Runnable runnable) {
        super.unscheduleSelf(runnable);
        this.wa.unscheduleSelf(runnable);
    }
}
