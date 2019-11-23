package com.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.ui.BaseHorizontalZoomView;

public class HorizontalZoomView extends BaseHorizontalZoomView {
    private static final String TAG = "HorizontalZoomView";
    private static final int TOUCH_SCROLL_THRESHOLD = 10;
    private static final int TOUCH_STATE_CLICK = 1;
    private static final int TOUCH_STATE_IDLE = 0;
    private static final int TOUCH_STATE_SCROLL = 2;
    private boolean isOnlyDrawLine;
    private BaseHorizontalZoomView.HorizontalDrawAdapter mDrawAdapter;
    private float mDrawEndX = -1.0f;
    private float mDrawStartX = -1.0f;
    private boolean mEnableCallBack = true;
    private float mFirstItemWidtch;
    protected float mItemGap;
    private float mItemWidthNormal;
    private float mLastItemWidtch;
    private int mMargin;
    private BaseHorizontalZoomView.OnPositionSelectListener mOnPositionZoomSelectListener;
    private float mSelectPointX;
    protected float mTotalWidth;
    private float mTouchStartTime;
    private float mTouchStartX;
    private float mTouchStartY;
    private int mTouchState = 0;
    private float mTouchX;

    public HorizontalZoomView(Context context) {
        super(context);
        init(context);
    }

    public HorizontalZoomView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public HorizontalZoomView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private float getFirstOrLastWidth(boolean z) {
        if (Util.isLayoutRTL(getContext())) {
            z = !z;
        }
        return (z ? this.mFirstItemWidtch : this.mLastItemWidtch) - (this.mItemWidthNormal / 2.0f);
    }

    private float getItemWidth(int i) {
        return this.mDrawAdapter.measureWidth(i);
    }

    private void setSelectPointXToEffectiveRang(float f) {
        this.mSelectPointX = Util.clamp(f, this.mDrawStartX + getFirstOrLastWidth(true), this.mDrawEndX - getFirstOrLastWidth(false));
    }

    private boolean startScrollIfNeeded(MotionEvent motionEvent) {
        int y = (int) motionEvent.getY();
        float x = (float) ((int) motionEvent.getX());
        if (x >= this.mTouchStartX - 10.0f && x <= this.mTouchStartX + 10.0f) {
            float f = (float) y;
            if (f >= this.mTouchStartY - 10.0f && f <= this.mTouchStartY + 10.0f) {
                return false;
            }
        }
        this.mTouchState = 2;
        return true;
    }

    /* access modifiers changed from: protected */
    public void init(Context context) {
        this.mMargin = context.getResources().getDimensionPixelSize(R.dimen.zoom_popup_padding_left);
        this.mDrawStartX = (float) this.mMargin;
        this.mDrawEndX = (float) (Util.sWindowWidth - this.mMargin);
        this.mTotalWidth = (float) (Util.sWindowWidth - (2 * this.mMargin));
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00ce A[ADDED_TO_REGION] */
    public void onDraw(Canvas canvas) {
        float f;
        Canvas canvas2 = canvas;
        setSelectPointXToEffectiveRang(this.mSelectPointX);
        float f2 = 2.0f;
        float height = (((float) getHeight()) * 2.0f) / 5.0f;
        boolean z = true;
        if (this.mDrawAdapter != null) {
            boolean isLayoutRTL = Util.isLayoutRTL(getContext());
            int count = isLayoutRTL ? this.mDrawAdapter.getCount() - 1 : 0;
            int count2 = isLayoutRTL ? 0 : this.mDrawAdapter.getCount() - 1;
            int i = isLayoutRTL ? -1 : 1;
            boolean z2 = true;
            float f3 = this.mDrawStartX;
            int i2 = 0;
            int i3 = -1;
            while (i2 < this.mDrawAdapter.getCount()) {
                int i4 = (i2 * i) + count;
                float itemWidth = getItemWidth(i4);
                float firstOrLastWidth = i2 == 0 ? (f3 + getFirstOrLastWidth(z)) - this.mSelectPointX : ((itemWidth / f2) + f3) - this.mSelectPointX;
                if (z2) {
                    if (this.mSelectPointX <= this.mDrawStartX + getFirstOrLastWidth(z)) {
                        i3 = count;
                    } else if (this.mSelectPointX >= this.mDrawEndX - getFirstOrLastWidth(false)) {
                        i3 = count2;
                    } else if (Math.abs(firstOrLastWidth) <= this.mItemWidthNormal / 2.0f) {
                        i3 = i4;
                    } else if (this.isOnlyDrawLine) {
                        f = 2.0f;
                        if (Math.abs(firstOrLastWidth) <= (this.mItemGap / 2.0f) + (this.mItemWidthNormal / 2.0f)) {
                            i3 = i4;
                            z2 = false;
                        }
                        canvas.save();
                        canvas2.translate(0.0f, height);
                        this.mDrawAdapter.draw(f3, i4, canvas2, z2 && i3 == i4);
                        canvas.restore();
                        f3 += itemWidth + this.mItemGap;
                        i2++;
                        f2 = f;
                        z = true;
                    }
                    f = 2.0f;
                    z2 = false;
                    canvas.save();
                    canvas2.translate(0.0f, height);
                    this.mDrawAdapter.draw(f3, i4, canvas2, z2 && i3 == i4);
                    canvas.restore();
                    f3 += itemWidth + this.mItemGap;
                    i2++;
                    f2 = f;
                    z = true;
                }
                f = 2.0f;
                canvas.save();
                canvas2.translate(0.0f, height);
                this.mDrawAdapter.draw(f3, i4, canvas2, z2 && i3 == i4);
                canvas.restore();
                f3 += itemWidth + this.mItemGap;
                i2++;
                f2 = f;
                z = true;
            }
            canvas.save();
            canvas2.translate(0.0f, height);
            if (z2) {
                this.mDrawAdapter.draw(this.mSelectPointX, -1, canvas2, z2);
            }
            canvas.restore();
            if (this.mOnPositionZoomSelectListener != null && this.mEnableCallBack) {
                float firstOrLastWidth2 = ((this.mSelectPointX - getFirstOrLastWidth(true)) - ((float) this.mMargin)) / ((this.mTotalWidth - getFirstOrLastWidth(true)) - getFirstOrLastWidth(false));
                if (isLayoutRTL) {
                    firstOrLastWidth2 = 1.0f - firstOrLastWidth2;
                }
                this.mOnPositionZoomSelectListener.onPositionSelect(this, i3, firstOrLastWidth2);
            }
        }
        this.isOnlyDrawLine = false;
        this.mEnableCallBack = true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                this.mTouchStartTime = (float) System.currentTimeMillis();
                this.mTouchStartX = motionEvent.getX();
                this.mTouchStartY = motionEvent.getY();
                if (this.mTouchState != 2) {
                    this.mTouchState = 1;
                    break;
                }
                break;
            case 1:
            case 3:
                if (((float) System.currentTimeMillis()) - this.mTouchStartTime <= ((float) ViewConfiguration.getLongPressTimeout())) {
                    if (this.mTouchState == 2) {
                        this.mSelectPointX += motionEvent.getX() - this.mTouchX;
                    } else {
                        this.mSelectPointX = motionEvent.getX();
                    }
                    this.mTouchState = 0;
                    this.isOnlyDrawLine = true;
                    invalidate();
                    break;
                } else {
                    this.mTouchState = 0;
                    return true;
                }
            case 2:
                if (this.mTouchState == 1) {
                    startScrollIfNeeded(motionEvent);
                }
                if (this.mTouchState == 2) {
                    this.mSelectPointX += motionEvent.getX() - this.mTouchX;
                    invalidate();
                    break;
                }
                break;
        }
        this.mTouchX = motionEvent.getX();
        return true;
    }

    public void setDrawAdapter(BaseHorizontalZoomView.HorizontalDrawAdapter horizontalDrawAdapter) {
        this.mDrawAdapter = horizontalDrawAdapter;
        float f = 0.0f;
        for (int i = 0; i < this.mDrawAdapter.getCount(); i++) {
            if (i == 0) {
                this.mFirstItemWidtch = getItemWidth(i);
            } else if (i == this.mDrawAdapter.getCount() - 1) {
                this.mLastItemWidtch = getItemWidth(i);
            } else if (this.mItemWidthNormal == 0.0f) {
                this.mItemWidthNormal = getItemWidth(i);
            }
            f += getItemWidth(i);
        }
        this.mItemGap = (this.mTotalWidth - f) / ((float) (this.mDrawAdapter.getCount() - 1));
        invalidate();
    }

    public void setEvent(MotionEvent motionEvent) {
        this.mTouchState = 2;
        onTouchEvent(motionEvent);
    }

    public void setJustifyEnabled(boolean z) {
    }

    public void setOnPositionSelectListener(BaseHorizontalZoomView.OnPositionSelectListener onPositionSelectListener) {
        this.mOnPositionZoomSelectListener = onPositionSelectListener;
    }

    public void setSelection(float f, boolean z) {
        if (Util.isLayoutRTL(getContext()) && this.mDrawAdapter != null) {
            f = 1.0f - f;
        }
        this.mSelectPointX = (((this.mTotalWidth - getFirstOrLastWidth(true)) - getFirstOrLastWidth(false)) * f) + getFirstOrLastWidth(true) + ((float) this.mMargin);
        this.mEnableCallBack = !z;
        this.isOnlyDrawLine = z;
        Log.d(TAG, "setSelection   " + this.mSelectPointX + "  " + f);
        invalidate();
    }
}
