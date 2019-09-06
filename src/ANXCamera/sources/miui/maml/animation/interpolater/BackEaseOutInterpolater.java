package miui.maml.animation.interpolater;

import android.view.animation.Interpolator;

public class BackEaseOutInterpolater implements Interpolator {
    private final float mFactor;

    public BackEaseOutInterpolater() {
        this.mFactor = 1.70158f;
    }

    public BackEaseOutInterpolater(float f2) {
        this.mFactor = f2;
    }

    public float getInterpolation(float f2) {
        float f3 = f2 - 1.0f;
        float f4 = f3;
        float f5 = f3 * f4;
        float f6 = this.mFactor;
        return (f5 * (((f6 + 1.0f) * f4) + f6)) + 1.0f;
    }
}
