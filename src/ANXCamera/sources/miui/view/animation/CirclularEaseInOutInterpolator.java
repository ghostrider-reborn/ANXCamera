package miui.view.animation;

import android.view.animation.Interpolator;

public class CirclularEaseInOutInterpolator implements Interpolator {
    public float getInterpolation(float f2) {
        float f3 = f2 * 2.0f;
        if (f3 < 1.0f) {
            return ((float) (Math.sqrt((double) (1.0f - (f3 * f3))) - 1.0d)) * -0.5f;
        }
        float f4 = f3 - 2.0f;
        return ((float) (Math.sqrt((double) (1.0f - (f4 * f4))) + 1.0d)) * 0.5f;
    }
}
