package com.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.android.camera.effect.EffectController;
import com.android.camera.log.Log;
import com.android.camera.ui.GradienterDrawer;

public class ReferenceLineDrawer extends View {
    private static final int BORDER = 1;
    public static final String TAG = ReferenceLineDrawer.class.getSimpleName();
    private boolean isGradienterEnabled;
    private boolean mBottomVisible = true;
    private int mColumnCount = 1;
    private GradienterDrawer.Direct mCurrentDirect = GradienterDrawer.Direct.NONE;
    private float mDeviceRotation = 0.0f;
    private int mFrameColor = 402653184;
    private Paint mFramePaint;
    private int mLineColor = 1795162111;
    private Paint mLinePaint;
    private int mRowCount = 1;
    private boolean mTopVisible = true;

    public ReferenceLineDrawer(Context context) {
        super(context);
    }

    public ReferenceLineDrawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ReferenceLineDrawer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void resetline(Canvas canvas) {
        Log.i(TAG, "resetline  rotationOffset : mDeviceRotation :" + this.mDeviceRotation);
        int width = getWidth() - 1;
        int height = getHeight() - 1;
        for (int i = 1; i < this.mColumnCount; i++) {
            if (this.isGradienterEnabled && this.mCurrentDirect == GradienterDrawer.Direct.LEFT && i == 2) {
                int i2 = i * width;
                canvas.drawRect(new Rect(i2 / this.mColumnCount, 1, (i2 / this.mColumnCount) + 3, (height / this.mRowCount) - 1), this.mFramePaint);
                canvas.drawRect(new Rect(i2 / this.mColumnCount, ((height / this.mRowCount) * (this.mRowCount - 1)) - 1, (i2 / this.mColumnCount) + 3, height - 1), this.mFramePaint);
            } else if (this.isGradienterEnabled && this.mCurrentDirect == GradienterDrawer.Direct.RIGHT && i == 1) {
                int i3 = i * width;
                canvas.drawRect(new Rect(i3 / this.mColumnCount, 1, (i3 / this.mColumnCount) + 3, (height / this.mRowCount) - 1), this.mFramePaint);
                canvas.drawRect(new Rect(i3 / this.mColumnCount, ((height / this.mRowCount) * (this.mRowCount - 1)) - 1, (i3 / this.mColumnCount) + 3, height - 1), this.mFramePaint);
            } else {
                int i4 = i * width;
                canvas.drawRect(new Rect(i4 / this.mColumnCount, 1, (i4 / this.mColumnCount) + 3, height - 1), this.mFramePaint);
            }
        }
        boolean z = !this.mBottomVisible;
        for (int i5 = 0; i5 <= this.mRowCount; i5++) {
            if (!(i5 == 0 || i5 == this.mRowCount) || ((i5 == 0 && this.mTopVisible) || (i5 == this.mRowCount && this.mBottomVisible))) {
                if (this.isGradienterEnabled && this.mCurrentDirect == GradienterDrawer.Direct.BOTTOM && i5 == 1) {
                    int i6 = i5 * height;
                    canvas.drawRect(new Rect(z, i6 / this.mRowCount, (width / this.mColumnCount) - z, (i6 / this.mRowCount) + 3), this.mFramePaint);
                    canvas.drawRect(new Rect(((width / this.mColumnCount) * (this.mColumnCount - 1)) - z, i6 / this.mRowCount, width - z, (i6 / this.mRowCount) + 3), this.mFramePaint);
                } else if (this.isGradienterEnabled && this.mCurrentDirect == GradienterDrawer.Direct.TOP && i5 == 2) {
                    int i7 = i5 * height;
                    canvas.drawRect(new Rect(z, i7 / this.mRowCount, (width / this.mColumnCount) - z, (i7 / this.mRowCount) + 3), this.mFramePaint);
                    canvas.drawRect(new Rect(((width / this.mColumnCount) * (this.mColumnCount - 1)) - z, i7 / this.mRowCount, width - z, (i7 / this.mRowCount) + 3), this.mFramePaint);
                } else {
                    int i8 = i5 * height;
                    canvas.drawRect(new Rect(z, i8 / this.mRowCount, width - z, (i8 / this.mRowCount) + 3), this.mFramePaint);
                }
            }
        }
        for (int i9 = 1; i9 < this.mColumnCount; i9++) {
            if (this.isGradienterEnabled && this.mCurrentDirect == GradienterDrawer.Direct.RIGHT && i9 == 1) {
                int i10 = i9 * width;
                canvas.drawRect(new Rect(i10 / this.mColumnCount, 1, (i10 / this.mColumnCount) + 2, (height / this.mRowCount) - 1), this.mLinePaint);
                canvas.drawRect(new Rect(i10 / this.mColumnCount, ((height / this.mRowCount) * (this.mRowCount - 1)) - 1, (i10 / this.mColumnCount) + 2, height - 1), this.mLinePaint);
            } else if (this.isGradienterEnabled && this.mCurrentDirect == GradienterDrawer.Direct.LEFT && i9 == 2) {
                int i11 = i9 * width;
                canvas.drawRect(new Rect(i11 / this.mColumnCount, 1, (i11 / this.mColumnCount) + 2, (height / this.mRowCount) - 1), this.mLinePaint);
                canvas.drawRect(new Rect(i11 / this.mColumnCount, ((height / this.mRowCount) * (this.mRowCount - 1)) - 1, (i11 / this.mColumnCount) + 2, height - 1), this.mLinePaint);
            } else {
                int i12 = i9 * width;
                canvas.drawRect(new Rect(i12 / this.mColumnCount, 1, (i12 / this.mColumnCount) + 2, height - 1), this.mLinePaint);
            }
        }
        for (int i13 = 0; i13 <= this.mRowCount; i13++) {
            if (!(i13 == 0 || i13 == this.mRowCount) || ((i13 == 0 && this.mTopVisible) || (i13 == this.mRowCount && this.mBottomVisible))) {
                if (this.isGradienterEnabled && this.mCurrentDirect == GradienterDrawer.Direct.BOTTOM && i13 == 1) {
                    int i14 = i13 * height;
                    canvas.drawRect(new Rect(z, i14 / this.mRowCount, (width / this.mColumnCount) - z, (i14 / this.mRowCount) + 2), this.mLinePaint);
                    canvas.drawRect(new Rect(((width / this.mColumnCount) * (this.mColumnCount - 1)) - z, i14 / this.mRowCount, width - z, (i14 / this.mRowCount) + 2), this.mLinePaint);
                } else if (this.isGradienterEnabled && this.mCurrentDirect == GradienterDrawer.Direct.TOP && i13 == 2) {
                    int i15 = i13 * height;
                    canvas.drawRect(new Rect(z, i15 / this.mRowCount, (width / this.mColumnCount) - z, (i15 / this.mRowCount) + 2), this.mLinePaint);
                    canvas.drawRect(new Rect(((width / this.mColumnCount) * (this.mColumnCount - 1)) - z, i15 / this.mRowCount, width - z, (i15 / this.mRowCount) + 2), this.mLinePaint);
                } else {
                    int i16 = i13 * height;
                    canvas.drawRect(new Rect(z, i16 / this.mRowCount, width - (z ? 1 : 0), (i16 / this.mRowCount) + 2), this.mLinePaint);
                }
            }
        }
    }

    private void updateView(Canvas canvas) {
        float f;
        GradienterDrawer.Direct direct;
        this.mDeviceRotation = EffectController.getInstance().getDeviceRotation();
        if (this.mDeviceRotation > 45.0f && this.mDeviceRotation < 135.0f) {
            direct = GradienterDrawer.Direct.RIGHT;
            f = this.mDeviceRotation - 90.0f;
        } else if (this.mDeviceRotation >= 135.0f && this.mDeviceRotation < 225.0f) {
            direct = GradienterDrawer.Direct.TOP;
            f = this.mDeviceRotation - 180.0f;
        } else if (this.mDeviceRotation <= 225.0f || this.mDeviceRotation >= 315.0f) {
            direct = GradienterDrawer.Direct.BOTTOM;
            f = this.mDeviceRotation > 300.0f ? this.mDeviceRotation - 360.0f : this.mDeviceRotation;
        } else {
            direct = GradienterDrawer.Direct.LEFT;
            f = this.mDeviceRotation - 270.0f;
        }
        if (direct != this.mCurrentDirect) {
            this.mCurrentDirect = direct;
        }
        resetline(canvas);
        String str = TAG;
        Log.i(str, "updateView  rotationOffset : " + f + ";  mDeviceRotation :" + this.mDeviceRotation);
        invalidate();
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
        updateView(canvas);
        super.onDraw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 0) {
            this.mCurrentDirect = GradienterDrawer.Direct.NONE;
        }
    }

    public void setBorderVisible(boolean z, boolean z2) {
        if (this.mTopVisible != z || this.mBottomVisible != z2) {
            this.mTopVisible = z;
            this.mBottomVisible = z2;
            invalidate();
        }
    }

    public void setGradienterEnabled(boolean z) {
        this.isGradienterEnabled = z;
        if (getVisibility() == 0) {
            this.mCurrentDirect = GradienterDrawer.Direct.NONE;
            invalidate();
        }
    }

    public void setLineColor(int i) {
        this.mLineColor = i;
    }
}
