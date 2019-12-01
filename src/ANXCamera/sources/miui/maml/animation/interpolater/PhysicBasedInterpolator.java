package miui.maml.animation.interpolater;

import android.view.animation.Interpolator;

public class PhysicBasedInterpolator implements Interpolator {
    private float c;
    private float c1;
    private float c2;
    private float k;
    private float m;
    private float mInitial;
    private float r;
    private float w;

    public PhysicBasedInterpolator() {
        this(0.9f, 0.3f);
    }

    public PhysicBasedInterpolator(float damping, float response) {
        this.mInitial = -1.0f;
        this.m = 1.0f;
        this.c1 = this.mInitial;
        this.k = (float) (Math.pow(6.283185307179586d / ((double) response), 2.0d) * ((double) this.m));
        this.c = (float) (((12.566370614359172d * ((double) damping)) * ((double) this.m)) / ((double) response));
        this.w = ((float) Math.sqrt((double) (((4.0f * this.m) * this.k) - (this.c * this.c)))) / (this.m * 2.0f);
        this.r = -((this.c / 2.0f) * this.m);
        this.c2 = (0.0f - (this.r * this.mInitial)) / this.w;
    }

    public float getInterpolation(float input) {
        return (float) ((Math.pow(2.718281828459045d, (double) (this.r * input)) * ((((double) this.c1) * Math.cos((double) (this.w * input))) + (((double) this.c2) * Math.sin((double) (this.w * input))))) + 1.0d);
    }
}
