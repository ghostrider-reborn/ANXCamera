package miui.maml.animation.interpolater;

import android.view.animation.Interpolator;

public class BounceEaseOutInterpolater implements Interpolator {
    public float getInterpolation(float t) {
        return getInterpolationImp(t);
    }

    public static float getInterpolationImp(float t) {
        if (((double) t) < 0.36363636363636365d) {
            return 7.5625f * t * t;
        }
        if (((double) t) < 0.7272727272727273d) {
            float t2 = (float) (((double) t) - 0.5454545454545454d);
            return (7.5625f * t2 * t2) + 0.75f;
        } else if (((double) t) < 0.9090909090909091d) {
            float t3 = (float) (((double) t) - 0.8181818181818182d);
            return (7.5625f * t3 * t3) + 0.9375f;
        } else {
            float t4 = (float) (((double) t) - 0.9545454545454546d);
            return (7.5625f * t4 * t4) + 0.984375f;
        }
    }
}
