package miui.maml.animation.interpolater;

import android.view.animation.Interpolator;

public class BackEaseInInterpolater implements Interpolator {
    private final float mFactor;

    public BackEaseInInterpolater() {
        this.mFactor = 1.70158f;
    }

    public BackEaseInInterpolater(float f2) {
        this.mFactor = f2;
    }

    public float getInterpolation(float f2) {
        float f3 = f2 * f2;
        float f4 = this.mFactor;
        return f3 * (((1.0f + f4) * f2) - f4);
    }
}
