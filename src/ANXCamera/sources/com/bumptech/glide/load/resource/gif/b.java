package com.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.view.Gravity;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.resource.gif.GifFrameLoader;
import java.nio.ByteBuffer;

/* compiled from: GifDrawable */
public class b extends Drawable implements Animatable, GifFrameLoader.a {
    private static final int GRAVITY = 119;
    public static final int nm = -1;
    public static final int nn = 0;
    private boolean hJ;
    private boolean isRunning;
    private int loopCount;
    private final a no;
    private boolean np;
    private boolean nq;
    private int nr;
    private boolean ns;
    private Rect nt;
    private Paint paint;

    /* compiled from: GifDrawable */
    static final class a extends Drawable.ConstantState {
        @VisibleForTesting
        final GifFrameLoader frameLoader;

        a(GifFrameLoader gifFrameLoader) {
            this.frameLoader = gifFrameLoader;
        }

        public int getChangingConfigurations() {
            return 0;
        }

        @NonNull
        public Drawable newDrawable() {
            return new b(this);
        }

        @NonNull
        public Drawable newDrawable(Resources resources) {
            return newDrawable();
        }
    }

    @Deprecated
    public b(Context context, com.bumptech.glide.b.a aVar, d dVar, i<Bitmap> iVar, int i, int i2, Bitmap bitmap) {
        this(context, aVar, iVar, i, i2, bitmap);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    public b(Context context, com.bumptech.glide.b.a aVar, i<Bitmap> iVar, int i, int i2, Bitmap bitmap) {
        this(new a(r1));
        GifFrameLoader gifFrameLoader = new GifFrameLoader(c.c(context), aVar, i, i2, iVar, bitmap);
    }

    @VisibleForTesting
    b(GifFrameLoader gifFrameLoader, Paint paint2) {
        this(new a(gifFrameLoader));
        this.paint = paint2;
    }

    b(a aVar) {
        this.nq = true;
        this.nr = -1;
        this.no = (a) com.bumptech.glide.util.i.checkNotNull(aVar);
    }

    private void dm() {
        this.loopCount = 0;
    }

    /* renamed from: do  reason: not valid java name */
    private void m1do() {
        com.bumptech.glide.util.i.a(!this.hJ, "You cannot start a recycled Drawable. Ensure thatyou clear any references to the Drawable when clearing the corresponding request.");
        if (this.no.frameLoader.getFrameCount() == 1) {
            invalidateSelf();
        } else if (!this.isRunning) {
            this.isRunning = true;
            this.no.frameLoader.a(this);
            invalidateSelf();
        }
    }

    private Rect dp() {
        if (this.nt == null) {
            this.nt = new Rect();
        }
        return this.nt;
    }

    private Drawable.Callback dq() {
        Drawable.Callback callback = getCallback();
        while (callback instanceof Drawable) {
            callback = ((Drawable) callback).getCallback();
        }
        return callback;
    }

    private Paint getPaint() {
        if (this.paint == null) {
            this.paint = new Paint(2);
        }
        return this.paint;
    }

    private void stopRunning() {
        this.isRunning = false;
        this.no.frameLoader.b(this);
    }

    public void a(i<Bitmap> iVar, Bitmap bitmap) {
        this.no.frameLoader.a(iVar, bitmap);
    }

    public Bitmap dj() {
        return this.no.frameLoader.dj();
    }

    public i<Bitmap> dk() {
        return this.no.frameLoader.dk();
    }

    public int dl() {
        return this.no.frameLoader.getCurrentIndex();
    }

    public void dn() {
        com.bumptech.glide.util.i.a(!this.isRunning, "You cannot restart a currently running animation.");
        this.no.frameLoader.dv();
        start();
    }

    public void dr() {
        if (dq() == null) {
            stop();
            invalidateSelf();
            return;
        }
        invalidateSelf();
        if (dl() == getFrameCount() - 1) {
            this.loopCount++;
        }
        if (this.nr != -1 && this.loopCount >= this.nr) {
            stop();
        }
    }

    public void draw(@NonNull Canvas canvas) {
        if (!this.hJ) {
            if (this.ns) {
                Gravity.apply(119, getIntrinsicWidth(), getIntrinsicHeight(), getBounds(), dp());
                this.ns = false;
            }
            canvas.drawBitmap(this.no.frameLoader.ds(), (Rect) null, dp(), getPaint());
        }
    }

    public ByteBuffer getBuffer() {
        return this.no.frameLoader.getBuffer();
    }

    public Drawable.ConstantState getConstantState() {
        return this.no;
    }

    public int getFrameCount() {
        return this.no.frameLoader.getFrameCount();
    }

    public int getIntrinsicHeight() {
        return this.no.frameLoader.getHeight();
    }

    public int getIntrinsicWidth() {
        return this.no.frameLoader.getWidth();
    }

    public int getOpacity() {
        return -2;
    }

    public int getSize() {
        return this.no.frameLoader.getSize();
    }

    /* access modifiers changed from: package-private */
    public void h(boolean z) {
        this.isRunning = z;
    }

    /* access modifiers changed from: package-private */
    public boolean isRecycled() {
        return this.hJ;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.ns = true;
    }

    public void recycle() {
        this.hJ = true;
        this.no.frameLoader.clear();
    }

    public void setAlpha(int i) {
        getPaint().setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        getPaint().setColorFilter(colorFilter);
    }

    public void setLoopCount(int i) {
        if (i <= 0 && i != -1 && i != 0) {
            throw new IllegalArgumentException("Loop count must be greater than 0, or equal to GlideDrawable.LOOP_FOREVER, or equal to GlideDrawable.LOOP_INTRINSIC");
        } else if (i == 0) {
            int loopCount2 = this.no.frameLoader.getLoopCount();
            if (loopCount2 == 0) {
                loopCount2 = -1;
            }
            this.nr = loopCount2;
        } else {
            this.nr = i;
        }
    }

    public boolean setVisible(boolean z, boolean z2) {
        com.bumptech.glide.util.i.a(!this.hJ, "Cannot change the visibility of a recycled resource. Ensure that you unset the Drawable from your View before changing the View's visibility.");
        this.nq = z;
        if (!z) {
            stopRunning();
        } else if (this.np) {
            m1do();
        }
        return super.setVisible(z, z2);
    }

    public void start() {
        this.np = true;
        dm();
        if (this.nq) {
            m1do();
        }
    }

    public void stop() {
        this.np = false;
        stopRunning();
    }
}
