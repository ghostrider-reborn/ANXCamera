package com.android.camera.fragment.vv;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class VVPreviewTransformer implements PageTransformer {
    private static final float MAX_ALPHA = 1.0f;
    private static final float MAX_SCALE = 1.0f;
    private static final float MIN_ALPHA = 1.0f;
    private static final float MIN_SCALE = 0.95f;
    private static final float alphaSlope = 0.0f;
    private static final float scaleSlope = 0.050000012f;

    public void transformPage(View view, float f2) {
        float f3 = -1.0f;
        if (f2 >= -1.0f) {
            f3 = f2 > 1.0f ? 1.0f : f2;
        }
        float f4 = f3 < 0.0f ? f3 + 1.0f : 1.0f - f3;
        ViewCompat.setAlpha(view, (0.0f * f4) + 1.0f);
        ViewCompat.setScaleX(view, (f4 * scaleSlope) + MIN_SCALE);
    }
}
