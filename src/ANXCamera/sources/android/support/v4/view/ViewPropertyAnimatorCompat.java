package android.support.v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;

public final class ViewPropertyAnimatorCompat {
    static final int LISTENER_TAG_ID = 2113929216;
    private static final String TAG = "ViewAnimatorCompat";
    Runnable mEndAction = null;
    int mOldLayerType = -1;
    Runnable mStartAction = null;
    private WeakReference<View> mView;

    static class ViewPropertyAnimatorListenerApi14 implements ViewPropertyAnimatorListener {
        boolean mAnimEndCalled;
        ViewPropertyAnimatorCompat mVpa;

        ViewPropertyAnimatorListenerApi14(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
            this.mVpa = viewPropertyAnimatorCompat;
        }

        public void onAnimationCancel(View view) {
            Object tag = view.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
            ViewPropertyAnimatorListener viewPropertyAnimatorListener = tag instanceof ViewPropertyAnimatorListener ? (ViewPropertyAnimatorListener) tag : null;
            if (viewPropertyAnimatorListener != null) {
                viewPropertyAnimatorListener.onAnimationCancel(view);
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: android.support.v4.view.ViewPropertyAnimatorListener} */
        /* JADX WARNING: Multi-variable type inference failed */
        public void onAnimationEnd(View view) {
            int i = this.mVpa.mOldLayerType;
            ViewPropertyAnimatorListener viewPropertyAnimatorListener = null;
            if (i > -1) {
                view.setLayerType(i, (Paint) null);
                this.mVpa.mOldLayerType = -1;
            }
            if (Build.VERSION.SDK_INT >= 16 || !this.mAnimEndCalled) {
                ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mVpa;
                Runnable runnable = viewPropertyAnimatorCompat.mEndAction;
                if (runnable != null) {
                    viewPropertyAnimatorCompat.mEndAction = null;
                    runnable.run();
                }
                Object tag = view.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
                if (tag instanceof ViewPropertyAnimatorListener) {
                    viewPropertyAnimatorListener = tag;
                }
                if (viewPropertyAnimatorListener != null) {
                    viewPropertyAnimatorListener.onAnimationEnd(view);
                }
                this.mAnimEndCalled = true;
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.support.v4.view.ViewPropertyAnimatorListener} */
        /* JADX WARNING: Multi-variable type inference failed */
        public void onAnimationStart(View view) {
            this.mAnimEndCalled = false;
            ViewPropertyAnimatorListener viewPropertyAnimatorListener = null;
            if (this.mVpa.mOldLayerType > -1) {
                view.setLayerType(2, (Paint) null);
            }
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mVpa;
            Runnable runnable = viewPropertyAnimatorCompat.mStartAction;
            if (runnable != null) {
                viewPropertyAnimatorCompat.mStartAction = null;
                runnable.run();
            }
            Object tag = view.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
            if (tag instanceof ViewPropertyAnimatorListener) {
                viewPropertyAnimatorListener = tag;
            }
            if (viewPropertyAnimatorListener != null) {
                viewPropertyAnimatorListener.onAnimationStart(view);
            }
        }
    }

    ViewPropertyAnimatorCompat(View view) {
        this.mView = new WeakReference<>(view);
    }

    private void setListenerInternal(final View view, final ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        if (viewPropertyAnimatorListener != null) {
            view.animate().setListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animator) {
                    viewPropertyAnimatorListener.onAnimationCancel(view);
                }

                public void onAnimationEnd(Animator animator) {
                    viewPropertyAnimatorListener.onAnimationEnd(view);
                }

                public void onAnimationStart(Animator animator) {
                    viewPropertyAnimatorListener.onAnimationStart(view);
                }
            });
        } else {
            view.animate().setListener((Animator.AnimatorListener) null);
        }
    }

    public ViewPropertyAnimatorCompat alpha(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().alpha(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat alphaBy(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().alphaBy(f2);
        }
        return this;
    }

    public void cancel() {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().cancel();
        }
    }

    public long getDuration() {
        View view = (View) this.mView.get();
        if (view != null) {
            return view.animate().getDuration();
        }
        return 0;
    }

    public Interpolator getInterpolator() {
        View view = (View) this.mView.get();
        if (view == null || Build.VERSION.SDK_INT < 18) {
            return null;
        }
        return (Interpolator) view.animate().getInterpolator();
    }

    public long getStartDelay() {
        View view = (View) this.mView.get();
        if (view != null) {
            return view.animate().getStartDelay();
        }
        return 0;
    }

    public ViewPropertyAnimatorCompat rotation(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().rotation(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationBy(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().rotationBy(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationX(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().rotationX(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationXBy(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().rotationXBy(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationY(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().rotationY(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationYBy(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().rotationYBy(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleX(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().scaleX(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleXBy(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().scaleXBy(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleY(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().scaleY(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleYBy(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().scaleYBy(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setDuration(long j) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().setDuration(j);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setInterpolator(Interpolator interpolator) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().setInterpolator(interpolator);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setListener(ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        View view = (View) this.mView.get();
        if (view != null) {
            if (Build.VERSION.SDK_INT >= 16) {
                setListenerInternal(view, viewPropertyAnimatorListener);
            } else {
                view.setTag(LISTENER_TAG_ID, viewPropertyAnimatorListener);
                setListenerInternal(view, new ViewPropertyAnimatorListenerApi14(this));
            }
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setStartDelay(long j) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().setStartDelay(j);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setUpdateListener(final ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener) {
        final View view = (View) this.mView.get();
        if (view != null && Build.VERSION.SDK_INT >= 19) {
            AnonymousClass2 r1 = null;
            if (viewPropertyAnimatorUpdateListener != null) {
                r1 = new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        viewPropertyAnimatorUpdateListener.onAnimationUpdate(view);
                    }
                };
            }
            view.animate().setUpdateListener(r1);
        }
        return this;
    }

    public void start() {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().start();
        }
    }

    public ViewPropertyAnimatorCompat translationX(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().translationX(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationXBy(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().translationXBy(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationY(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().translationY(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationYBy(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().translationYBy(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationZ(float f2) {
        View view = (View) this.mView.get();
        if (view != null && Build.VERSION.SDK_INT >= 21) {
            view.animate().translationZ(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationZBy(float f2) {
        View view = (View) this.mView.get();
        if (view != null && Build.VERSION.SDK_INT >= 21) {
            view.animate().translationZBy(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat withEndAction(Runnable runnable) {
        View view = (View) this.mView.get();
        if (view != null) {
            if (Build.VERSION.SDK_INT >= 16) {
                view.animate().withEndAction(runnable);
            } else {
                setListenerInternal(view, new ViewPropertyAnimatorListenerApi14(this));
                this.mEndAction = runnable;
            }
        }
        return this;
    }

    public ViewPropertyAnimatorCompat withLayer() {
        View view = (View) this.mView.get();
        if (view != null) {
            if (Build.VERSION.SDK_INT >= 16) {
                view.animate().withLayer();
            } else {
                this.mOldLayerType = view.getLayerType();
                setListenerInternal(view, new ViewPropertyAnimatorListenerApi14(this));
            }
        }
        return this;
    }

    public ViewPropertyAnimatorCompat withStartAction(Runnable runnable) {
        View view = (View) this.mView.get();
        if (view != null) {
            if (Build.VERSION.SDK_INT >= 16) {
                view.animate().withStartAction(runnable);
            } else {
                setListenerInternal(view, new ViewPropertyAnimatorListenerApi14(this));
                this.mStartAction = runnable;
            }
        }
        return this;
    }

    public ViewPropertyAnimatorCompat x(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().x(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat xBy(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().xBy(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat y(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().y(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat yBy(float f2) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().yBy(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat z(float f2) {
        View view = (View) this.mView.get();
        if (view != null && Build.VERSION.SDK_INT >= 21) {
            view.animate().z(f2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat zBy(float f2) {
        View view = (View) this.mView.get();
        if (view != null && Build.VERSION.SDK_INT >= 21) {
            view.animate().zBy(f2);
        }
        return this;
    }
}
