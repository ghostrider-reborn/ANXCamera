package com.android.camera.fragment.vv;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

public class VVPreviewTransformer implements ViewPager.PageTransformer {
    private static final float MAX_ALPHA = 1.0f;
    private static final float MAX_SCALE = 1.0f;
    private static final float MIN_ALPHA = 1.0f;
    private static final float MIN_SCALE = 0.92f;
    private static final float alphaSlope = 0.0f;
    private static final float scaleSlope = 0.07999998f;

    public void transformPage(View view, float f) {
        if (f < -1.0f) {
            f = -1.0f;
        } else if (f > 1.0f) {
            f = 1.0f;
        }
        float f2 = f < 0.0f ? f + 1.0f : 1.0f - f;
        ViewCompat.setAlpha(view, 1.0f + (0.0f * f2));
        ViewCompat.setScaleX(view, MIN_SCALE + (f2 * scaleSlope));
    }
}
