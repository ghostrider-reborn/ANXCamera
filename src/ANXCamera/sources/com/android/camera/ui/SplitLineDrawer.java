package com.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class SplitLineDrawer extends View {
    private static final int BORDER = 1;
    private boolean mBottomVisible = true;
    private int mColumnCount = 1;
    private int mFrameColor = 402653184;
    private Paint mFramePaint;
    private int mLineColor = 1795162111;
    private Paint mLinePaint;
    private int mRowCount = 1;
    private boolean mTopVisible = true;

    public SplitLineDrawer(Context context) {
        super(context);
    }

    public SplitLineDrawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SplitLineDrawer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void initialize(int i, int i2) {
        this.mColumnCount = Math.max(i2, 1);
        this.mRowCount = Math.max(i, 1);
        this.mLinePaint = new Paint();
        this.mFramePaint = new Paint();
        this.mLinePaint.setStrokeWidth(1.0f);
        this.mFramePaint.setStrokeWidth(1.0f);
        this.mLinePaint.setStyle(Paint.Style.FILL);
        this.mFramePaint.setStyle(Paint.Style.STROKE);
        this.mLinePaint.setColor(this.mLineColor);
        this.mFramePaint.setColor(this.mFrameColor);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int width = getWidth() - 1;
        int height = getHeight() - 1;
        int i = 1;
        while (true) {
            int i2 = this.mColumnCount;
            if (i >= i2) {
                break;
            }
            int i3 = i * width;
            canvas.drawRect(new Rect(i3 / i2, 1, (i3 / i2) + 3, height - 1), this.mFramePaint);
            i++;
        }
        boolean z = !this.mBottomVisible;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            int i6 = this.mRowCount;
            if (i5 > i6) {
                break;
            }
            if (!(i5 == 0 || i5 == i6) || ((i5 == 0 && this.mTopVisible) || (i5 == this.mRowCount && this.mBottomVisible))) {
                int i7 = i5 * height;
                int i8 = this.mRowCount;
                canvas.drawRect(new Rect(z, i7 / i8, width - z, (i7 / i8) + 3), this.mFramePaint);
            }
            i5++;
        }
        int i9 = 1;
        while (true) {
            int i10 = this.mColumnCount;
            if (i9 >= i10) {
                break;
            }
            int i11 = i9 * width;
            canvas.drawRect(new Rect(i11 / i10, 1, (i11 / i10) + 2, height - 1), this.mLinePaint);
            i9++;
        }
        while (true) {
            int i12 = this.mRowCount;
            if (i4 <= i12) {
                if (!(i4 == 0 || i4 == i12) || ((i4 == 0 && this.mTopVisible) || (i4 == this.mRowCount && this.mBottomVisible))) {
                    int i13 = i4 * height;
                    int i14 = this.mRowCount;
                    canvas.drawRect(new Rect(z, i13 / i14, width - (z ? 1 : 0), (i13 / i14) + 2), this.mLinePaint);
                }
                i4++;
            } else {
                super.onDraw(canvas);
                return;
            }
        }
    }

    public void setBorderVisible(boolean z, boolean z2) {
        if (this.mTopVisible != z || this.mBottomVisible != z2) {
            this.mTopVisible = z;
            this.mBottomVisible = z2;
            invalidate();
        }
    }

    public void setLineColor(int i) {
        this.mLineColor = i;
    }
}
