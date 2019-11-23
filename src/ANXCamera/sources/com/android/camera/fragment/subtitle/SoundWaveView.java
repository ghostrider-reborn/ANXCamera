package com.android.camera.fragment.subtitle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import com.android.camera.R;

public class SoundWaveView extends View {
    private static final int COUNT_LINE = 5;
    private static final int DEFAULT_DEGREES = 0;
    private static final int INVALIDATE_SOUND_VIEW_MSG = 100;
    private float LINE_SPACE;
    private float LINE_WIDTH;
    /* access modifiers changed from: private */
    public float[] curHeights;
    /* access modifiers changed from: private */
    @SuppressLint({"HandlerLeak"})
    public Handler handler;
    private float[] intervalHeights;
    /* access modifiers changed from: private */
    public boolean isAnimationProcessing;
    private boolean[] isElongation;
    private int mDegrees;
    private float[] mMaxLineHeights;
    /* access modifiers changed from: private */
    public float[] mMinLineHeights;
    private Paint mPaint;
    private RectF mRect;

    public SoundWaveView(Context context) {
        this(context, (AttributeSet) null, -1);
    }

    public SoundWaveView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SoundWaveView);
        this.mDegrees = obtainStyledAttributes.getInt(0, 0);
        obtainStyledAttributes.recycle();
    }

    public SoundWaveView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isAnimationProcessing = false;
        this.mMaxLineHeights = new float[]{22.0f, 16.0f, 24.0f, 16.0f, 22.0f};
        this.mMinLineHeights = new float[]{8.0f, 14.0f, 8.0f, 14.0f, 8.0f};
        this.intervalHeights = new float[]{0.7f, 0.1f, 0.8f, 0.1f, 0.7f};
        this.curHeights = new float[]{8.0f, 16.0f, 24.0f, 16.0f, 8.0f};
        this.isElongation = new boolean[]{true, true, true, true, true};
        this.handler = new Handler() {
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what != 100) {
                    return;
                }
                if (SoundWaveView.this.isAnimationProcessing || SoundWaveView.this.curHeights[0] > SoundWaveView.this.mMinLineHeights[0]) {
                    SoundWaveView.this.invalidate();
                    SoundWaveView.this.handler.sendEmptyMessageDelayed(100, 30);
                    return;
                }
                SoundWaveView.this.handler.removeCallbacksAndMessages((Object) null);
            }
        };
        init();
    }

    private void init() {
        this.LINE_WIDTH = 1.0f;
        this.LINE_SPACE = 8.0f;
        this.mPaint = new Paint(1);
        this.mPaint.setColor(-1);
        this.mPaint.setStrokeWidth(this.LINE_WIDTH);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mRect = new RectF();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float f = this.LINE_WIDTH + 5.0f + this.LINE_SPACE;
        if (this.mRect == null) {
            this.mRect = new RectF();
        }
        for (int i = 0; i < 5; i++) {
            if (this.mDegrees == 90 || this.mDegrees == 270) {
                float width = ((float) getWidth()) / 2.0f;
                float height = (((((float) getHeight()) - (f * 5.0f)) - this.LINE_SPACE) / 2.0f) + (((float) i) * f) + f;
                this.mRect.left = width - this.curHeights[i];
                this.mRect.top = height;
                this.mRect.right = width + this.curHeights[i];
                this.mRect.bottom = height + 5.0f;
                canvas.drawRoundRect(this.mRect, 15.0f, 15.0f, this.mPaint);
            } else {
                float height2 = ((float) getHeight()) / 2.0f;
                float width2 = (((((float) getWidth()) - (f * 5.0f)) - this.LINE_SPACE) / 2.0f) + (((float) i) * f) + f;
                this.mRect.left = width2;
                this.mRect.top = this.curHeights[i] + height2;
                this.mRect.right = width2 + 5.0f;
                this.mRect.bottom = height2 - this.curHeights[i];
                canvas.drawRoundRect(this.mRect, 15.0f, 15.0f, this.mPaint);
            }
            if (this.isElongation[i]) {
                float[] fArr = this.curHeights;
                fArr[i] = fArr[i] + this.intervalHeights[i];
                if (this.curHeights[i] >= this.mMaxLineHeights[i]) {
                    this.isElongation[i] = false;
                    this.curHeights[i] = this.mMaxLineHeights[i];
                    if (i == 0) {
                        int i2 = i + 1;
                        this.isElongation[i2] = true;
                        int i3 = i + 2;
                        this.isElongation[i3] = true;
                        this.curHeights[i3] = this.mMinLineHeights[i2];
                        this.curHeights[i3] = this.mMinLineHeights[i3];
                    }
                }
            } else {
                float[] fArr2 = this.curHeights;
                fArr2[i] = fArr2[i] - this.intervalHeights[i];
                if (this.curHeights[i] <= this.mMinLineHeights[i]) {
                    this.curHeights[i] = this.mMinLineHeights[i];
                    this.isElongation[i] = true;
                    if (i == 0) {
                        int i4 = i + 1;
                        this.isElongation[i4] = false;
                        int i5 = i + 2;
                        this.isElongation[i5] = false;
                        this.curHeights[i4] = this.mMaxLineHeights[i4];
                        this.curHeights[i5] = this.mMaxLineHeights[i5];
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    public void resetAnimation() {
        this.isAnimationProcessing = false;
    }

    public void startAnimation() {
        if (!this.isAnimationProcessing) {
            this.isAnimationProcessing = true;
            if (this.curHeights[0] <= this.mMinLineHeights[0]) {
                this.handler.removeCallbacksAndMessages((Object) null);
                this.handler.sendEmptyMessage(100);
            }
        }
    }
}
