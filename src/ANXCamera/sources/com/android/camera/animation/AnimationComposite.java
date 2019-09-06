package com.android.camera.animation;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.provider.MiuiSettings.ScreenEffect;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.android.camera.animation.AnimationDelegate.AnimationResource;
import com.android.camera.data.DataRepository;
import com.android.camera.module.loader.StartControl;
import com.ss.android.vesdk.VEResult;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class AnimationComposite implements Consumer<Integer> {
    private static final String TAG = "AnimationComposite";
    private AtomicBoolean mActive = new AtomicBoolean(true);
    private Disposable mAfterFrameArrivedDisposable = Observable.create(new ObservableOnSubscribe<Integer>() {
        public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
            AnimationComposite.this.mAfterFrameArrivedEmitter = observableEmitter;
        }
    }).observeOn(AndroidSchedulers.mainThread()).subscribe((Consumer<? super T>) this);
    /* access modifiers changed from: private */
    public ObservableEmitter<Integer> mAfterFrameArrivedEmitter;
    private Disposable mAnimationDisposable;
    /* access modifiers changed from: private */
    public int mCurrentDegree = 0;
    private int mOrientation = -1;
    private SparseArray<AnimationResource> mResourceSparseArray = new SparseArray<>();
    private ValueAnimator mRotationAnimator;
    private int mStartDegree = 0;
    private int mTargetDegree = 0;

    public void accept(@NonNull Integer num) throws Exception {
        DataRepository.dataItemGlobal().setRetriedIfCameraError(false);
        for (int i = 0; i < this.mResourceSparseArray.size(); i++) {
            AnimationResource animationResource = (AnimationResource) this.mResourceSparseArray.valueAt(i);
            if (animationResource.canProvide()) {
                if (!this.mActive.get()) {
                    Log.e(TAG, "not active, skip notifyAfterFrameAvailable");
                    return;
                }
                if (!animationResource.isEnableClick()) {
                    animationResource.setClickEnable(true);
                }
                animationResource.notifyAfterFrameAvailable(num.intValue());
            }
        }
    }

    public void destroy() {
        SparseArray<AnimationResource> sparseArray = this.mResourceSparseArray;
        if (sparseArray != null) {
            sparseArray.clear();
            this.mResourceSparseArray = null;
        }
        Disposable disposable = this.mAfterFrameArrivedDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mAfterFrameArrivedEmitter.onComplete();
            this.mAfterFrameArrivedDisposable.dispose();
            this.mAfterFrameArrivedDisposable = null;
        }
    }

    public Disposable dispose(@Nullable Completable completable, @Nullable Action action, StartControl startControl) {
        ArrayList arrayList = new ArrayList();
        if (completable != null) {
            arrayList.add(completable);
        }
        int i = startControl.mTargetMode;
        int i2 = startControl.mResetType;
        int i3 = startControl.mViewConfigType;
        if (i3 != -1) {
            int i4 = 0;
            if (i3 == 1) {
                while (i4 < this.mResourceSparseArray.size()) {
                    AnimationResource animationResource = (AnimationResource) this.mResourceSparseArray.valueAt(i4);
                    if (animationResource.canProvide()) {
                        animationResource.provideAnimateElement(i, null, i2);
                    }
                    i4++;
                }
            } else if (i3 == 2) {
                while (i4 < this.mResourceSparseArray.size()) {
                    AnimationResource animationResource2 = (AnimationResource) this.mResourceSparseArray.valueAt(i4);
                    if (animationResource2.canProvide()) {
                        animationResource2.provideAnimateElement(i, arrayList, i2);
                    }
                    i4++;
                }
            } else if (i3 == 3) {
                while (i4 < this.mResourceSparseArray.size()) {
                    AnimationResource animationResource3 = (AnimationResource) this.mResourceSparseArray.valueAt(i4);
                    if (animationResource3.canProvide() && animationResource3.needViewClear()) {
                        animationResource3.provideAnimateElement(i, null, i2);
                    }
                    i4++;
                }
            }
        }
        Disposable disposable = this.mAnimationDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mAnimationDisposable.dispose();
        }
        if (action != null) {
            this.mAnimationDisposable = Completable.merge((Iterable<? extends CompletableSource>) arrayList).subscribe(action);
        } else {
            this.mAnimationDisposable = Completable.merge((Iterable<? extends CompletableSource>) arrayList).subscribe();
        }
        return this.mAnimationDisposable;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0090, code lost:
        if (r2 == 0) goto L_0x009f;
     */
    public void disposeRotation(int i) {
        int i2;
        int i3;
        int i4 = ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT;
        int i5 = i >= 0 ? i % ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT : (i % ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT) + ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT;
        int i6 = this.mOrientation;
        if (i6 != i5) {
            boolean z = i6 != -1;
            int i7 = i5 - this.mOrientation;
            if (i7 < 0) {
                i7 += ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT;
            }
            if (i7 > 180) {
                i7 += VEResult.TER_EGL_BAD_MATCH;
            }
            boolean z2 = i7 <= 0;
            this.mOrientation = i5;
            if (this.mOrientation != 0 || this.mCurrentDegree != 0) {
                this.mTargetDegree = (360 - i5) % ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT;
                final ArrayList<View> arrayList = new ArrayList<>();
                for (int i8 = 0; i8 < this.mResourceSparseArray.size(); i8++) {
                    AnimationResource animationResource = (AnimationResource) this.mResourceSparseArray.valueAt(i8);
                    if (animationResource.canProvide()) {
                        animationResource.provideRotateItem(arrayList, this.mTargetDegree);
                    }
                }
                if (!z) {
                    this.mCurrentDegree = this.mTargetDegree;
                    for (View rotation : arrayList) {
                        ViewCompat.setRotation(rotation, (float) this.mTargetDegree);
                    }
                    return;
                }
                ValueAnimator valueAnimator = this.mRotationAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                this.mStartDegree = this.mCurrentDegree;
                if (z2) {
                    i2 = this.mStartDegree;
                    if (i2 == 360) {
                        i2 = 0;
                    }
                    i3 = this.mTargetDegree;
                } else {
                    int i9 = this.mStartDegree;
                    if (i9 == 0) {
                        i9 = 360;
                    }
                    i3 = this.mTargetDegree;
                    if (i3 == 360) {
                        i4 = 0;
                        this.mRotationAnimator = ValueAnimator.ofInt(new int[]{i2, i4});
                        this.mRotationAnimator.setInterpolator(new LinearInterpolator());
                        this.mRotationAnimator.removeAllUpdateListeners();
                        this.mRotationAnimator.addUpdateListener(new AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                AnimationComposite.this.mCurrentDegree = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                                for (View rotation : arrayList) {
                                    ViewCompat.setRotation(rotation, (float) AnimationComposite.this.mCurrentDegree);
                                }
                            }
                        });
                        this.mRotationAnimator.start();
                    }
                }
                i4 = i3;
                this.mRotationAnimator = ValueAnimator.ofInt(new int[]{i2, i4});
                this.mRotationAnimator.setInterpolator(new LinearInterpolator());
                this.mRotationAnimator.removeAllUpdateListeners();
                this.mRotationAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        AnimationComposite.this.mCurrentDegree = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        for (View rotation : arrayList) {
                            ViewCompat.setRotation(rotation, (float) AnimationComposite.this.mCurrentDegree);
                        }
                    }
                });
                this.mRotationAnimator.start();
            }
        }
    }

    public void notifyAfterFirstFrameArrived(int i) {
        Disposable disposable = this.mAfterFrameArrivedDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mAfterFrameArrivedEmitter.onNext(Integer.valueOf(i));
        }
    }

    public void notifyDataChanged(int i, int i2) {
        for (int i3 = 0; i3 < this.mResourceSparseArray.size(); i3++) {
            AnimationResource animationResource = (AnimationResource) this.mResourceSparseArray.valueAt(i3);
            if (animationResource.canProvide()) {
                animationResource.notifyDataChanged(i, i2);
            }
        }
    }

    public void onStart() {
        this.mActive.set(true);
    }

    public void onStop() {
        this.mActive.set(false);
    }

    public void put(int i, AnimationResource animationResource) {
        this.mResourceSparseArray.put(i, animationResource);
    }

    public void remove(int i) {
        this.mResourceSparseArray.remove(i);
    }

    public void setClickEnable(boolean z) {
        for (int i = 0; i < this.mResourceSparseArray.size(); i++) {
            AnimationResource animationResource = (AnimationResource) this.mResourceSparseArray.valueAt(i);
            if (animationResource.canProvide() && animationResource.isEnableClick() != z) {
                animationResource.setClickEnable(z);
            }
        }
    }
}
