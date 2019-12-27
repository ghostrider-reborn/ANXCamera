package com.android.camera.fragment.mimoji;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.android.camera.R;

public class BubbleEditMimojiPresenter {
    private static final int INVISIBLE_STATE = -1;
    public static final int RESET_STATE = -2;
    private static final String TAG = "BubbleEditMimojiPresenter";
    private static final int VISIBLE_STATE = 1;
    BubblePop bubblePop1;
    /* access modifiers changed from: private */
    public int downMove;
    /* access modifiers changed from: private */
    public boolean isAnimationingDele = false;
    /* access modifiers changed from: private */
    public boolean isAnimationingEdit = false;
    /* access modifiers changed from: private */
    public double leftMove;
    Context mContext;
    private int mHashCodeBubble = -1;
    public int[] mShowBubbleState = new int[3];
    public View mTargetView;
    /* access modifiers changed from: private */
    public double rightMove;
    /* access modifiers changed from: private */
    public int topMove;

    class BubblePop {
        public static final int DELETE_PROCESS = 102;
        public static final int EDIT_PROCESS = 101;
        public static final int HIDE_STATE = 104;
        public static final int SHOW_STATE = 103;
        private boolean hasAddView = false;
        /* access modifiers changed from: private */
        public RelativeLayout.LayoutParams layoutParamsDelete;
        /* access modifiers changed from: private */
        public RelativeLayout.LayoutParams layoutParamsEdit;
        Context mContext;
        private int mHashCode;
        ImageView mIvDeleteFisrt;
        ImageView mIvEditFirst;
        public int[] mLocationSelect = new int[3];
        private RelativeLayout mRootView;
        private int processState = 104;

        BubblePop(Context context, View.OnClickListener onClickListener, RelativeLayout relativeLayout) {
            this.mContext = context;
            this.mRootView = relativeLayout;
            this.mIvDeleteFisrt = new ImageView(this.mContext);
            this.mIvDeleteFisrt.setImageDrawable(this.mContext.getDrawable(R.drawable.mimoji_delete));
            this.mIvDeleteFisrt.setTag(102);
            this.mIvDeleteFisrt.setOnClickListener(onClickListener);
            this.mIvEditFirst = new ImageView(this.mContext);
            this.mIvEditFirst.setImageDrawable(this.mContext.getDrawable(R.drawable.mimoji_edit));
            this.mIvEditFirst.setTag(101);
            this.mIvEditFirst.setOnClickListener(onClickListener);
            int[] iArr = this.mLocationSelect;
            iArr[0] = -1;
            iArr[1] = -1;
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.mimoji_edit_bubble_width);
            int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.mimoji_edit_bubble_height);
            this.layoutParamsEdit = new RelativeLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize2);
            this.layoutParamsDelete = new RelativeLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize2);
        }

        public int getProcessState() {
            return this.processState;
        }

        public void hideBubbleAni() {
            if (this.mIvDeleteFisrt != null) {
                ImageView imageView = this.mIvEditFirst;
                if (imageView != null) {
                    this.processState = 104;
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(imageView, View.SCALE_X, new float[]{1.0f, 0.0f});
                    ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mIvEditFirst, View.SCALE_Y, new float[]{1.0f, 0.0f});
                    ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mIvEditFirst, View.ALPHA, new float[]{1.0f, 0.0f});
                    ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.mIvEditFirst, "translationX", new float[]{0.0f, (float) BubbleEditMimojiPresenter.this.rightMove});
                    ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(this.mIvEditFirst, "translationY", new float[]{0.0f, (float) BubbleEditMimojiPresenter.this.downMove});
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2, ofFloat3, ofFloat4, ofFloat5});
                    animatorSet.setDuration(120);
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            RelativeLayout.LayoutParams access$400 = BubblePop.this.layoutParamsEdit;
                            BubblePop bubblePop = BubblePop.this;
                            int[] iArr = bubblePop.mLocationSelect;
                            access$400.setMargins(iArr[0], iArr[1] + BubbleEditMimojiPresenter.this.downMove + BubbleEditMimojiPresenter.this.topMove, 0, 0);
                            BubblePop bubblePop2 = BubblePop.this;
                            bubblePop2.mIvEditFirst.setLayoutParams(bubblePop2.layoutParamsEdit);
                            BubblePop.this.mIvEditFirst.setVisibility(4);
                            boolean unused = BubbleEditMimojiPresenter.this.isAnimationingEdit = false;
                        }

                        public void onAnimationStart(Animator animator) {
                            super.onAnimationStart(animator);
                            RelativeLayout.LayoutParams access$400 = BubblePop.this.layoutParamsEdit;
                            BubblePop bubblePop = BubblePop.this;
                            access$400.leftMargin = (int) (((double) bubblePop.mLocationSelect[0]) + BubbleEditMimojiPresenter.this.leftMove);
                            RelativeLayout.LayoutParams access$4002 = BubblePop.this.layoutParamsEdit;
                            BubblePop bubblePop2 = BubblePop.this;
                            access$4002.topMargin = bubblePop2.mLocationSelect[1] + BubbleEditMimojiPresenter.this.topMove;
                            BubblePop bubblePop3 = BubblePop.this;
                            bubblePop3.mIvEditFirst.setLayoutParams(bubblePop3.layoutParamsEdit);
                            BubblePop.this.mIvEditFirst.setVisibility(0);
                            boolean unused = BubbleEditMimojiPresenter.this.isAnimationingEdit = true;
                        }
                    });
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    animatorSet2.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.mIvDeleteFisrt, View.SCALE_X, new float[]{1.0f, 0.0f}), ObjectAnimator.ofFloat(this.mIvDeleteFisrt, View.SCALE_Y, new float[]{1.0f, 0.0f}), ObjectAnimator.ofFloat(this.mIvDeleteFisrt, View.ALPHA, new float[]{1.0f, 0.0f}), ObjectAnimator.ofFloat(this.mIvDeleteFisrt, "translationX", new float[]{0.0f, (float) BubbleEditMimojiPresenter.this.leftMove}), ObjectAnimator.ofFloat(this.mIvDeleteFisrt, "translationY", new float[]{0.0f, (float) BubbleEditMimojiPresenter.this.downMove})});
                    animatorSet2.setDuration(120);
                    animatorSet2.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            RelativeLayout.LayoutParams access$600 = BubblePop.this.layoutParamsDelete;
                            BubblePop bubblePop = BubblePop.this;
                            int[] iArr = bubblePop.mLocationSelect;
                            access$600.setMargins(iArr[0], iArr[1] + BubbleEditMimojiPresenter.this.downMove + BubbleEditMimojiPresenter.this.topMove, 0, 0);
                            BubblePop.this.mIvDeleteFisrt.setVisibility(4);
                            BubblePop bubblePop2 = BubblePop.this;
                            bubblePop2.mIvDeleteFisrt.setLayoutParams(bubblePop2.layoutParamsDelete);
                            BubblePop bubblePop3 = BubblePop.this;
                            int[] iArr2 = bubblePop3.mLocationSelect;
                            iArr2[0] = -1;
                            iArr2[1] = -1;
                            iArr2[2] = -1;
                            boolean unused = BubbleEditMimojiPresenter.this.isAnimationingDele = false;
                        }

                        public void onAnimationStart(Animator animator) {
                            super.onAnimationStart(animator);
                            RelativeLayout.LayoutParams access$600 = BubblePop.this.layoutParamsDelete;
                            BubblePop bubblePop = BubblePop.this;
                            access$600.leftMargin = (int) (((double) bubblePop.mLocationSelect[0]) + BubbleEditMimojiPresenter.this.rightMove);
                            RelativeLayout.LayoutParams access$6002 = BubblePop.this.layoutParamsDelete;
                            BubblePop bubblePop2 = BubblePop.this;
                            access$6002.topMargin = bubblePop2.mLocationSelect[1] + BubbleEditMimojiPresenter.this.topMove;
                            BubblePop bubblePop3 = BubblePop.this;
                            bubblePop3.mIvDeleteFisrt.setLayoutParams(bubblePop3.layoutParamsDelete);
                            boolean unused = BubbleEditMimojiPresenter.this.isAnimationingDele = true;
                        }
                    });
                    animatorSet.start();
                    animatorSet2.start();
                }
            }
        }

        public void processBubbleAni(int i, int i2, int i3) {
            this.mHashCode = i3;
            if (!this.hasAddView) {
                this.mRootView.addView(this.mIvEditFirst);
                this.mRootView.addView(this.mIvDeleteFisrt);
                this.hasAddView = true;
            }
            if (!BubbleEditMimojiPresenter.this.isAnimationingEdit && !BubbleEditMimojiPresenter.this.isAnimationingDele) {
                if (this.mLocationSelect[2] > 0) {
                    hideBubbleAni();
                    return;
                }
                BubbleEditMimojiPresenter.this.setmHashCodeBubble(this.mHashCode);
                showBubbleAni(i, i2);
            }
        }

        public void showBubbleAni(int i, int i2) {
            final int i3 = i;
            final int i4 = i2;
            this.processState = 103;
            int[] iArr = this.mLocationSelect;
            iArr[0] = i3;
            iArr[1] = i4;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mIvEditFirst, View.SCALE_X, new float[]{0.0f, 1.0f});
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mIvEditFirst, View.SCALE_Y, new float[]{0.0f, 1.0f});
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mIvEditFirst, View.ALPHA, new float[]{0.0f, 1.0f});
            ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.mIvEditFirst, "translationX", new float[]{0.0f, (float) BubbleEditMimojiPresenter.this.leftMove});
            ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(this.mIvEditFirst, "translationY", new float[]{0.0f, (float) BubbleEditMimojiPresenter.this.topMove});
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2, ofFloat3, ofFloat4, ofFloat5});
            animatorSet.setDuration(200);
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    BubblePop.this.mIvEditFirst.setVisibility(0);
                    boolean unused = BubbleEditMimojiPresenter.this.isAnimationingEdit = false;
                }

                public void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                    BubblePop.this.layoutParamsEdit.topMargin = i4;
                    BubblePop.this.layoutParamsEdit.leftMargin = i3;
                    BubblePop bubblePop = BubblePop.this;
                    bubblePop.mIvEditFirst.setLayoutParams(bubblePop.layoutParamsEdit);
                    BubblePop.this.mIvEditFirst.setVisibility(0);
                    boolean unused = BubbleEditMimojiPresenter.this.isAnimationingEdit = true;
                }
            });
            AnimatorSet animatorSet2 = new AnimatorSet();
            ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(this.mIvDeleteFisrt, View.SCALE_X, new float[]{0.0f, 1.0f});
            ObjectAnimator objectAnimator = ofFloat6;
            animatorSet2.playTogether(new Animator[]{objectAnimator, ObjectAnimator.ofFloat(this.mIvDeleteFisrt, View.SCALE_Y, new float[]{0.0f, 1.0f}), ObjectAnimator.ofFloat(this.mIvDeleteFisrt, View.ALPHA, new float[]{0.0f, 1.0f}), ObjectAnimator.ofFloat(this.mIvDeleteFisrt, "translationX", new float[]{0.0f, (float) BubbleEditMimojiPresenter.this.rightMove}), ObjectAnimator.ofFloat(this.mIvDeleteFisrt, "translationY", new float[]{0.0f, (float) BubbleEditMimojiPresenter.this.topMove})});
            animatorSet2.setDuration(200);
            animatorSet2.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    BubblePop bubblePop = BubblePop.this;
                    bubblePop.mLocationSelect[2] = 1;
                    bubblePop.mIvDeleteFisrt.setVisibility(0);
                    boolean unused = BubbleEditMimojiPresenter.this.isAnimationingDele = false;
                }

                public void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                    BubblePop.this.layoutParamsDelete.topMargin = i4;
                    BubblePop.this.layoutParamsDelete.leftMargin = i3;
                    BubblePop bubblePop = BubblePop.this;
                    bubblePop.mIvDeleteFisrt.setLayoutParams(bubblePop.layoutParamsDelete);
                    BubblePop.this.mIvDeleteFisrt.setVisibility(0);
                    boolean unused = BubbleEditMimojiPresenter.this.isAnimationingDele = true;
                }
            });
            animatorSet.start();
            animatorSet2.start();
        }
    }

    BubbleEditMimojiPresenter(Context context, View.OnClickListener onClickListener, RelativeLayout relativeLayout) {
        this.mContext = context;
        this.bubblePop1 = new BubblePop(this.mContext, onClickListener, relativeLayout);
    }

    public void processBubbleAni(int i, int i2, View view) {
        if (-2 != i || -2 != i2) {
            this.mTargetView = view;
            this.mTargetView.getHeight();
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.mimoji_edit_bubble_width);
            this.rightMove = ((double) dimensionPixelSize) * 0.75d;
            this.leftMove = -this.rightMove;
            this.topMove = -dimensionPixelSize;
            this.downMove = dimensionPixelSize / 2;
            Log.i(TAG, "calculate vector leftMove:" + this.leftMove + " rightMove:" + this.rightMove + "  topMove:" + this.topMove + "  downMove:" + this.downMove);
            BubblePop bubblePop = this.bubblePop1;
            bubblePop.processBubbleAni(i, i2, bubblePop.hashCode());
        } else if (this.bubblePop1.getProcessState() == 103) {
            BubblePop bubblePop2 = this.bubblePop1;
            bubblePop2.processBubbleAni(i, i2, bubblePop2.hashCode());
        }
    }

    public void setmHashCodeBubble(int i) {
        this.mHashCodeBubble = i;
    }
}
