package com.android.camera.fragment.manually.adapter;

import com.android.camera.ui.BaseHorizontalZoomView;
import com.mi.config.b;

public abstract class AbstractZoomSliderAdapter extends BaseHorizontalZoomView.HorizontalDrawAdapter implements BaseHorizontalZoomView.OnPositionSelectListener {
    protected static int getRealZoomRatioTele() {
        return b.sE ? 17 : 20;
    }

    public abstract boolean isEnable();

    public abstract float mapPositionToZoomRatio(float f);

    public abstract float mapZoomRatioToPosition(float f);

    public abstract void setEnable(boolean z);
}
