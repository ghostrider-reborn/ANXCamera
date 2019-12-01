package miui.view.animation;

import android.view.animation.Interpolator;

public class BackEaseInInterpolator implements Interpolator {
    private final float Xe;

    public BackEaseInInterpolator() {
        this(0.0f);
    }

    public BackEaseInInterpolator(float f) {
        this.Xe = f;
    }

    public float getInterpolation(float f) {
        float f2 = this.Xe == 0.0f ? 1.70158f : this.Xe;
        return f * f * (((1.0f + f2) * f) - f2);
    }
}
