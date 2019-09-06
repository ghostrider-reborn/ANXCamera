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
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.view.Gravity;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.j;
import com.bumptech.glide.util.i;
import java.nio.ByteBuffer;

/* compiled from: GifDrawable */
public class b extends Drawable implements com.bumptech.glide.load.resource.gif.GifFrameLoader.a, Animatable {
    public static final int Ea = -1;
    public static final int Fa = 0;
    private static final int GRAVITY = 119;
    private boolean Aa;
    private int Ba;
    private boolean Ca;
    private Rect Da;
    private boolean isRunning;
    private int loopCount;
    private Paint paint;
    private final a state;
    private boolean ya;
    private boolean za;

    /* compiled from: GifDrawable */
    static final class a extends ConstantState {
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
    public b(Context context, com.bumptech.glide.b.a aVar, d dVar, j<Bitmap> jVar, int i, int i2, Bitmap bitmap) {
        this(context, aVar, jVar, i, i2, bitmap);
    }

    public b(Context context, com.bumptech.glide.b.a aVar, j<Bitmap> jVar, int i, int i2, Bitmap bitmap) {
        GifFrameLoader gifFrameLoader = new GifFrameLoader(c.get(context), aVar, i, i2, jVar, bitmap);
        this(new a(gifFrameLoader));
    }

    @VisibleForTesting
    b(GifFrameLoader gifFrameLoader, Paint paint2) {
        this(new a(gifFrameLoader));
        this.paint = paint2;
    }

    b(a aVar) {
        this.Aa = true;
        this.Ba = -1;
        i.checkNotNull(aVar);
        this.state = aVar;
    }

    private Callback Qj() {
        Callback callback = getCallback();
        while (callback instanceof Drawable) {
            callback = ((Drawable) callback).getCallback();
        }
        return callback;
    }

    private Rect Rj() {
        if (this.Da == null) {
            this.Da = new Rect();
        }
        return this.Da;
    }

    private void Sj() {
        this.loopCount = 0;
    }

    private void Tj() {
        i.a(!this.za, "You cannot start a recycled Drawable. Ensure thatyou clear any references to the Drawable when clearing the corresponding request.");
        if (this.state.frameLoader.getFrameCount() == 1) {
            invalidateSelf();
        } else if (!this.isRunning) {
            this.isRunning = true;
            this.state.frameLoader.a(this);
            invalidateSelf();
        }
    }

    private Paint getPaint() {
        if (this.paint == null) {
            this.paint = new Paint(2);
        }
        return this.paint;
    }

    private void stopRunning() {
        this.isRunning = false;
        this.state.frameLoader.b(this);
    }

    public Bitmap Z() {
        return this.state.frameLoader.Z();
    }

    public void a(j<Bitmap> jVar, Bitmap bitmap) {
        this.state.frameLoader.a(jVar, bitmap);
    }

    public int aa() {
        return this.state.frameLoader.getCurrentIndex();
    }

    public j<Bitmap> ba() {
        return this.state.frameLoader.ba();
    }

    public void ca() {
        i.a(!this.isRunning, "You cannot restart a currently running animation.");
        this.state.frameLoader.Hg();
        start();
    }

    public void draw(@NonNull Canvas canvas) {
        if (!this.za) {
            if (this.Ca) {
                Gravity.apply(119, getIntrinsicWidth(), getIntrinsicHeight(), getBounds(), Rj());
                this.Ca = false;
            }
            canvas.drawBitmap(this.state.frameLoader.Gg(), null, Rj(), getPaint());
        }
    }

    /* access modifiers changed from: 0000 */
    public void e(boolean z) {
        this.isRunning = z;
    }

    public ByteBuffer getBuffer() {
        return this.state.frameLoader.getBuffer();
    }

    public ConstantState getConstantState() {
        return this.state;
    }

    public int getFrameCount() {
        return this.state.frameLoader.getFrameCount();
    }

    public int getIntrinsicHeight() {
        return this.state.frameLoader.getHeight();
    }

    public int getIntrinsicWidth() {
        return this.state.frameLoader.getWidth();
    }

    public int getOpacity() {
        return -2;
    }

    public int getSize() {
        return this.state.frameLoader.getSize();
    }

    /* access modifiers changed from: 0000 */
    public boolean isRecycled() {
        return this.za;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void m() {
        if (Qj() == null) {
            stop();
            invalidateSelf();
            return;
        }
        invalidateSelf();
        if (aa() == getFrameCount() - 1) {
            this.loopCount++;
        }
        int i = this.Ba;
        if (i != -1 && this.loopCount >= i) {
            stop();
        }
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.Ca = true;
    }

    public void recycle() {
        this.za = true;
        this.state.frameLoader.clear();
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
            int loopCount2 = this.state.frameLoader.getLoopCount();
            if (loopCount2 == 0) {
                loopCount2 = -1;
            }
            this.Ba = loopCount2;
        } else {
            this.Ba = i;
        }
    }

    public boolean setVisible(boolean z, boolean z2) {
        i.a(!this.za, "Cannot change the visibility of a recycled resource. Ensure that you unset the Drawable from your View before changing the View's visibility.");
        this.Aa = z;
        if (!z) {
            stopRunning();
        } else if (this.ya) {
            Tj();
        }
        return super.setVisible(z, z2);
    }

    public void start() {
        this.ya = true;
        Sj();
        if (this.Aa) {
            Tj();
        }
    }

    public void stop() {
        this.ya = false;
        stopRunning();
    }
}
