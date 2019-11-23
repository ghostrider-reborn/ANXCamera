package com.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.android.camera.R;
import com.android.camera.effect.EffectController;
import com.android.camera.log.Log;

public class GradienterDrawer extends RelativeLayout {
    public static final int COLOR_NORMAL = -1711276033;
    public static final int COLOR_SELECTED = -13840129;
    public static final String TAG = GradienterDrawer.class.getSimpleName();
    private boolean isReferenceLineEnabled;
    private boolean isSquareModule;
    private Direct mCurrentDirect = Direct.NONE;
    private float mDeviceRotation = 0.0f;
    private View mLineLeftView;
    private int mLineLongColor = 1795162111;
    private int mLineLongWidth = 1;
    private View mLineRightView;
    private int mLineShortColor = COLOR_SELECTED;
    private View mLineShortView;
    private int mLineShortWidth = 6;
    private int mParentHeighth = 0;
    private int mParentWidth = 0;
    private LinearLayout mRootView;

    enum Direct {
        NONE,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public GradienterDrawer(Context context) {
        super(context);
        init(context);
    }

    public GradienterDrawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public GradienterDrawer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        if (getChildCount() > 0) {
            removeAllViews();
        }
        this.mRootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.v6_preview_gradienter, this, false);
        addView(this.mRootView, new RelativeLayout.LayoutParams(-1, -1));
        this.mLineShortView = this.mRootView.findViewById(R.id.view_line_short);
        this.mLineLeftView = this.mRootView.findViewById(R.id.view_line_left);
        this.mLineRightView = this.mRootView.findViewById(R.id.view_line_right);
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    private void resetMargin() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mLineShortView.getLayoutParams();
        if (this.isReferenceLineEnabled) {
            resetParams(layoutParams);
            setViewVisible(this.mLineRightView, 8);
            setViewVisible(this.mLineLeftView, 8);
            switch (this.mCurrentDirect) {
                case TOP:
                    int i = !this.isSquareModule ? this.mParentHeighth / 3 : (this.mParentWidth / 3) + ((this.mParentHeighth - this.mParentWidth) / 2);
                    int i2 = this.mParentWidth / 3;
                    this.mRootView.setOrientation(0);
                    this.mRootView.setGravity(80);
                    layoutParams.height = this.mLineShortWidth;
                    layoutParams.width = -1;
                    layoutParams.bottomMargin = i - (this.mLineShortWidth / 2);
                    layoutParams.leftMargin = i2;
                    layoutParams.rightMargin = i2;
                    break;
                case BOTTOM:
                    int i3 = !this.isSquareModule ? this.mParentHeighth / 3 : (this.mParentWidth / 3) + ((this.mParentHeighth - this.mParentWidth) / 2);
                    int i4 = this.mParentWidth / 3;
                    this.mRootView.setOrientation(0);
                    this.mRootView.setGravity(48);
                    layoutParams.height = this.mLineShortWidth;
                    layoutParams.width = -1;
                    layoutParams.topMargin = i3 - (this.mLineShortWidth / 2);
                    layoutParams.leftMargin = i4;
                    layoutParams.rightMargin = i4;
                    break;
                case RIGHT:
                    int i5 = this.mParentWidth / 3;
                    int i6 = !this.isSquareModule ? this.mParentHeighth / 3 : (this.mParentWidth / 3) + ((this.mParentHeighth - this.mParentWidth) / 2);
                    this.mRootView.setOrientation(1);
                    this.mRootView.setGravity(3);
                    layoutParams.width = this.mLineShortWidth;
                    layoutParams.height = -1;
                    layoutParams.leftMargin = i5 - (this.mLineShortWidth / 2);
                    layoutParams.topMargin = i6;
                    layoutParams.bottomMargin = i6;
                    break;
                case LEFT:
                    int i7 = this.mParentWidth / 3;
                    int i8 = !this.isSquareModule ? this.mParentHeighth / 3 : (this.mParentWidth / 3) + ((this.mParentHeighth - this.mParentWidth) / 2);
                    this.mRootView.setOrientation(1);
                    this.mRootView.setGravity(5);
                    layoutParams.width = this.mLineShortWidth;
                    layoutParams.height = -1;
                    layoutParams.rightMargin = i7 - (this.mLineShortWidth / 2);
                    layoutParams.topMargin = i8;
                    layoutParams.bottomMargin = i8;
                    break;
            }
            this.mLineShortView.setLayoutParams(layoutParams);
            return;
        }
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mLineLeftView.getLayoutParams();
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mLineRightView.getLayoutParams();
        resetParams(layoutParams, layoutParams2, layoutParams3);
        setViewVisible(this.mLineRightView, 0);
        setViewVisible(this.mLineLeftView, 0);
        switch (this.mCurrentDirect) {
            case TOP:
            case BOTTOM:
                int i9 = this.mParentHeighth / 2;
                this.mRootView.setOrientation(0);
                this.mRootView.setGravity(48);
                layoutParams.height = this.mLineShortWidth;
                layoutParams2.height = this.mLineLongWidth;
                layoutParams3.height = this.mLineLongWidth;
                layoutParams.width = -1;
                layoutParams2.width = -1;
                layoutParams3.width = -1;
                layoutParams.topMargin = i9 - (this.mLineShortWidth / 2);
                layoutParams2.topMargin = i9 - (this.mLineLongWidth / 2);
                layoutParams3.topMargin = i9 - (this.mLineLongWidth / 2);
                break;
            case RIGHT:
            case LEFT:
                int i10 = this.mParentWidth / 2;
                this.mRootView.setOrientation(1);
                this.mRootView.setGravity(5);
                layoutParams.width = this.mLineShortWidth;
                layoutParams2.width = this.mLineLongWidth;
                layoutParams3.width = this.mLineLongWidth;
                if (!this.isSquareModule) {
                    layoutParams.height = -1;
                    layoutParams2.height = -1;
                    layoutParams3.height = -1;
                } else {
                    layoutParams.height = this.mParentWidth / 3;
                    layoutParams2.height = (this.mParentHeighth - (this.mParentWidth / 3)) / 2;
                    layoutParams3.height = (this.mParentHeighth - (this.mParentWidth / 3)) / 2;
                }
                layoutParams.rightMargin = i10 - (this.mLineShortWidth / 2);
                layoutParams2.rightMargin = i10 - (this.mLineLongWidth / 2);
                layoutParams3.rightMargin = i10 - (this.mLineLongWidth / 2);
                break;
        }
        this.mLineShortView.setLayoutParams(layoutParams);
        this.mLineLeftView.setLayoutParams(layoutParams2);
        this.mLineRightView.setLayoutParams(layoutParams3);
    }

    private void resetParams(LinearLayout.LayoutParams... layoutParamsArr) {
        for (LinearLayout.LayoutParams layoutParams : layoutParamsArr) {
            layoutParams.rightMargin = 0;
            layoutParams.leftMargin = 0;
            layoutParams.topMargin = 0;
            layoutParams.bottomMargin = 0;
        }
    }

    private void setViewVisible(View view, int i) {
        if (view.getVisibility() != i) {
            view.setVisibility(i);
        }
    }

    private void updateView() {
        float f;
        Direct direct;
        this.mDeviceRotation = EffectController.getInstance().getDeviceRotation();
        if (this.mDeviceRotation > 45.0f && this.mDeviceRotation < 135.0f) {
            direct = Direct.RIGHT;
            f = this.mDeviceRotation - 90.0f;
        } else if (this.mDeviceRotation >= 135.0f && this.mDeviceRotation < 225.0f) {
            direct = Direct.TOP;
            f = this.mDeviceRotation - 180.0f;
        } else if (this.mDeviceRotation <= 225.0f || this.mDeviceRotation >= 315.0f) {
            direct = Direct.BOTTOM;
            if (this.mDeviceRotation == -1.0f) {
                this.mDeviceRotation = -5.0f;
            }
            f = this.mDeviceRotation > 300.0f ? this.mDeviceRotation - 360.0f : this.mDeviceRotation;
        } else {
            direct = Direct.LEFT;
            f = this.mDeviceRotation - 270.0f;
        }
        if (Math.abs(f) <= 3.0f) {
            f = 0.0f;
        }
        setViewVisible(this.mLineShortView, 0);
        if (direct != this.mCurrentDirect) {
            setViewVisible(this.mLineShortView, 4);
            this.mCurrentDirect = direct;
            resetMargin();
        }
        if (f == 0.0f) {
            setLineShortColor(COLOR_SELECTED);
        } else {
            setLineShortColor(-1711276033);
        }
        this.mLineShortView.setRotation(-f);
        Log.i(TAG, "updateView  rotationOffset : " + f + ";  mDeviceRotation :" + this.mDeviceRotation);
        invalidateView();
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        updateView();
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 0) {
            this.mCurrentDirect = Direct.NONE;
        }
    }

    public void setConfigInfo(int i, int i2, boolean z) {
        this.mParentWidth = i;
        this.mParentHeighth = i2;
        this.isSquareModule = z;
        this.mCurrentDirect = Direct.NONE;
        updateView();
    }

    public void setLineShortColor(int i) {
        if (this.mLineShortColor != i) {
            this.mLineShortColor = i;
            this.mLineShortView.setBackgroundColor(i);
        }
    }

    public void setReferenceLineEnabled(boolean z) {
        this.mCurrentDirect = Direct.NONE;
        this.isReferenceLineEnabled = z;
    }

    public void setlineWidth(int i, int i2) {
        this.mLineLongWidth = i;
        this.mLineShortWidth = i2;
        this.mCurrentDirect = Direct.NONE;
    }
}
