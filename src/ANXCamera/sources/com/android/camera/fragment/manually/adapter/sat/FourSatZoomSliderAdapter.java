package com.android.camera.fragment.manually.adapter.sat;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.text.Layout.Alignment;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.TextAppearanceSpan;
import android.util.Spline;
import com.android.camera.ActivityBase;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.data.ComponentData;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.ui.HorizontalSlideView;

public class FourSatZoomSliderAdapter extends AbstractZoomSliderAdapter {
    public static final int ENTRY_COUNT_10X_TO_MAX = 38;
    public static final int ENTRY_COUNT_1X_TO_5X = 11;
    public static final int ENTRY_COUNT_5X_TO_10X = 11;
    public static final int ENTRY_COUNT_MIN_TO_1X = 5;
    public static final int ENTRY_COUNT_TOTAL = 62;
    public static final int ENTRY_INDEX_10X = 24;
    public static final int ENTRY_INDEX_1X = 4;
    public static final int ENTRY_INDEX_5X = 14;
    public static final int ENTRY_INDEX_MAX = 61;
    public static final int ENTRY_INDEX_MIN = 0;
    private static final float[] RANGE_10X_TO_MAX_ENTRY_INDEX_X = {24.0f, 26.0f, 34.0f, 39.0f, 47.0f, 51.0f, 58.0f};
    private static final float[] RANGE_10X_TO_MAX_ZOOM_RATIO_Y = {12.0f, 15.0f, 25.0f, 30.0f, 37.0f, 45.0f, 50.0f};
    private static final float[] RANGE_1X_TO_5X_ENTRY_INDEX_X = {4.0f, 14.0f};
    private static final float[] RANGE_1X_TO_5X_ZOOM_RATIO_Y = {1.0f, 5.0f};
    private static final float[] RANGE_5X_TO_10X_ENTRY_INDEX_X = {14.0f, 24.0f};
    private static final float[] RANGE_5X_TO_10X_ZOOM_RATIO_Y = {5.0f, 10.0f};
    private static final float[] RANGE_MIN_TO_1X_ENTRY_INDEX_X = {0.0f, 1.0f, 2.0f, 3.0f, 4.0f};
    private static final float[] RANGE_MIN_TO_1X_ZOOM_RATIO_Y = {0.6f, 0.7f, 0.8f, 0.9f, 1.0f};
    private static final String TAG = "TriSatZoomSliderAdapter";
    private static final int[] sTextActivatedColorState = {16843518};
    private static final int[] sTextDefaultColorState = {0};
    private ComponentData mComponentData;
    private int mCurrentMode;
    private String mCurrentValue;
    private int mCurrentValueIndex;
    private TextAppearanceSpan mDigitsTextStyle;
    private int mDotColorActivated;
    private int mDotRadius;
    private boolean mEnable;
    private StaticLayout[] mEntryLayouts;
    private int mLineColorDefault;
    private int mLineDotYGap;
    private float mLineHalfHeight;
    private int mLineLineGap;
    private int mLineTextGap;
    private int mLineWidth;
    private ManuallyListener mManuallyListener;
    private Paint mPaint;
    private Spline mRange10XToMaxEntryToZoomRatioSpline;
    private Spline mRange10XToMaxZoomRatioToEntrySpline;
    private Spline mRange1XTo5XEntryToZoomRatioSpline = Spline.createLinearSpline(RANGE_1X_TO_5X_ENTRY_INDEX_X, RANGE_1X_TO_5X_ZOOM_RATIO_Y);
    private Spline mRange1XTo5XZoomRatioToEntrySpline = Spline.createLinearSpline(RANGE_1X_TO_5X_ZOOM_RATIO_Y, RANGE_1X_TO_5X_ENTRY_INDEX_X);
    private Spline mRange5XTo10XEntryToZoomRatioSpline = Spline.createLinearSpline(RANGE_5X_TO_10X_ENTRY_INDEX_X, RANGE_5X_TO_10X_ZOOM_RATIO_Y);
    private Spline mRange5XTo10XZoomRatioToEntrySpline = Spline.createLinearSpline(RANGE_5X_TO_10X_ZOOM_RATIO_Y, RANGE_5X_TO_10X_ENTRY_INDEX_X);
    private Spline mRangeMinTo1XEntryToZoomRatioSpline = Spline.createLinearSpline(RANGE_MIN_TO_1X_ENTRY_INDEX_X, RANGE_MIN_TO_1X_ZOOM_RATIO_Y);
    private Spline mRangeMinTo1XZoomRatioToEntrySpline = Spline.createLinearSpline(RANGE_MIN_TO_1X_ZOOM_RATIO_Y, RANGE_MIN_TO_1X_ENTRY_INDEX_X);
    private ColorStateList mTextColor;
    private TextPaint mTextPaint;
    private int mTextSize;
    private TextAppearanceSpan mXTextStyle;
    private final float mZoomRatioMax;
    private final float mZoomRatioMin;

    public FourSatZoomSliderAdapter(Context context, ComponentData componentData, int i, ManuallyListener manuallyListener) {
        this.mComponentData = componentData;
        this.mCurrentMode = i;
        this.mManuallyListener = manuallyListener;
        this.mCurrentValue = componentData.getComponentValue(this.mCurrentMode);
        BaseModule baseModule = (BaseModule) ((ActivityBase) context).getCurrentModule();
        this.mZoomRatioMax = baseModule.getMaxZoomRatio();
        this.mZoomRatioMin = baseModule.getMinZoomRatio();
        StringBuilder sb = new StringBuilder();
        sb.append("ZOOM RATIO RANGE [");
        sb.append(this.mZoomRatioMin);
        sb.append(", ");
        sb.append(this.mZoomRatioMax);
        sb.append("]");
        Log.d(TAG, sb.toString());
        float[] entryX = getEntryX();
        float[] zoomRatioY = getZoomRatioY(this.mZoomRatioMax);
        this.mRange10XToMaxEntryToZoomRatioSpline = Spline.createMonotoneCubicSpline(entryX, zoomRatioY);
        this.mRange10XToMaxZoomRatioToEntrySpline = Spline.createMonotoneCubicSpline(zoomRatioY, entryX);
        initStyle(context);
        CharSequence[] charSequenceArr = {createZoomRatioLabel(0.6f), createZoomRatioLabel(1.0f), createZoomRatioLabel(5.0f), createZoomRatioLabel(10.0f), createZoomRatioLabel(this.mZoomRatioMax)};
        this.mEntryLayouts = new StaticLayout[charSequenceArr.length];
        for (int i2 = 0; i2 < charSequenceArr.length; i2++) {
            StaticLayout[] staticLayoutArr = this.mEntryLayouts;
            StaticLayout staticLayout = new StaticLayout(charSequenceArr[i2], this.mTextPaint, Util.sWindowWidth, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            staticLayoutArr[i2] = staticLayout;
        }
    }

    private CharSequence createZoomRatioLabel(float f2) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        float decimal = HybridZoomingSystem.toDecimal(f2);
        int i = (int) decimal;
        if (((int) ((10.0f * decimal) - ((float) (i * 10)))) == 0) {
            Util.appendInApi26(spannableStringBuilder, String.valueOf(i), this.mDigitsTextStyle, 33);
        } else {
            Util.appendInApi26(spannableStringBuilder, String.valueOf(decimal), this.mDigitsTextStyle, 33);
        }
        Util.appendInApi26(spannableStringBuilder, "X", this.mXTextStyle, 33);
        return spannableStringBuilder;
    }

    private void drawText(int i, Canvas canvas) {
        float lineAscent = (float) (this.mEntryLayouts[i].getLineAscent(0) - this.mEntryLayouts[i].getLineDescent(0));
        canvas.save();
        canvas.translate(0.0f, lineAscent / 2.0f);
        this.mEntryLayouts[i].draw(canvas);
        canvas.restore();
    }

    private static boolean drawTextForItemAt(int i) {
        return i == 0 || i == 4 || i == 14 || i == 24 || i == 61;
    }

    private static float[] getEntryX() {
        float[] fArr = RANGE_10X_TO_MAX_ENTRY_INDEX_X;
        float f2 = (float) 24;
        int i = (int) ((fArr[fArr.length - 1] - f2) + 1.0f);
        float[] fArr2 = new float[fArr.length];
        for (int i2 = 0; i2 < fArr.length; i2++) {
            if (fArr[i2] <= f2) {
                fArr2[i2] = fArr[i2];
            } else {
                fArr2[i2] = (((fArr[i2] - f2) / ((float) (i - 1))) * 37.0f) + f2;
            }
        }
        return fArr2;
    }

    private static float[] getZoomRatioY(float f2) {
        float[] fArr = RANGE_10X_TO_MAX_ZOOM_RATIO_Y;
        int i = (int) fArr[fArr.length - 1];
        float[] fArr2 = new float[fArr.length];
        for (int i2 = 0; i2 < fArr.length; i2++) {
            if (fArr[i2] <= 10.0f) {
                fArr2[i2] = fArr[i2];
            } else {
                fArr2[i2] = (((fArr[i2] - 10.0f) / (((float) i) - 10.0f)) * (f2 - 10.0f)) + 10.0f;
            }
        }
        return fArr2;
    }

    private static int indexToSection(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 4) {
            return 1;
        }
        if (i == 14) {
            return 2;
        }
        if (i == 24) {
            return 3;
        }
        return i == 61 ? 4 : -1;
    }

    private void initStyle(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.style.SingeTextItemTextStyle, new int[]{16842901, 16842904});
        this.mTextSize = obtainStyledAttributes.getDimensionPixelSize(obtainStyledAttributes.getIndex(0), this.mTextSize);
        this.mTextColor = obtainStyledAttributes.getColorStateList(obtainStyledAttributes.getIndex(1));
        obtainStyledAttributes.recycle();
        Resources resources = context.getResources();
        this.mLineHalfHeight = ((float) resources.getDimensionPixelSize(R.dimen.focus_line_height)) / 2.0f;
        this.mLineWidth = resources.getDimensionPixelSize(R.dimen.focus_line_width);
        this.mDotRadius = resources.getDimensionPixelSize(R.dimen.zoom_popup_dot_radius);
        this.mLineLineGap = resources.getDimensionPixelSize(R.dimen.focus_line_line_gap);
        this.mLineTextGap = resources.getDimensionPixelSize(R.dimen.focus_line_text_gap);
        this.mLineDotYGap = resources.getDimensionPixelSize(R.dimen.zoom_popup_line_dot_y_gap);
        this.mLineColorDefault = resources.getColor(R.color.zoom_popup_line_color_default);
        this.mDotColorActivated = resources.getColor(R.color.zoom_popup_dot_color_activated);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeWidth((float) this.mLineWidth);
        this.mPaint.setTextSize((float) this.mTextSize);
        this.mPaint.setTextAlign(Align.LEFT);
        this.mTextPaint = new TextPaint(this.mPaint);
        this.mDigitsTextStyle = new TextAppearanceSpan(context, R.style.ZoomPopupDigitsTextStyle);
        this.mXTextStyle = new TextAppearanceSpan(context, R.style.ZoomPopupXTextStyle);
    }

    public void draw(int i, Canvas canvas, boolean z) {
        if (drawTextForItemAt(i)) {
            this.mTextPaint.drawableState = z ? sTextActivatedColorState : sTextDefaultColorState;
            drawText(indexToSection(i), canvas);
            return;
        }
        this.mPaint.setColor(z ? this.mTextColor.getColorForState(sTextActivatedColorState, 0) : this.mLineColorDefault);
        float f2 = this.mLineHalfHeight;
        canvas.drawLine(0.0f, -f2, 0.0f, f2, this.mPaint);
    }

    public Align getAlign(int i) {
        return drawTextForItemAt(i) ? Align.LEFT : Align.CENTER;
    }

    public int getCount() {
        return 62;
    }

    public boolean isEnable() {
        return this.mEnable;
    }

    public float mapPositionToZoomRatio(float f2) {
        return (0.0f > f2 || f2 >= 4.0f) ? (4.0f > f2 || f2 >= 14.0f) ? (14.0f > f2 || f2 >= 24.0f) ? this.mRange10XToMaxEntryToZoomRatioSpline.interpolate(f2) : this.mRange5XTo10XEntryToZoomRatioSpline.interpolate(f2) : this.mRange1XTo5XEntryToZoomRatioSpline.interpolate(f2) : this.mRangeMinTo1XEntryToZoomRatioSpline.interpolate(f2);
    }

    public float mapZoomRatioToPosition(float f2) {
        return (0.6f > f2 || f2 >= 1.0f) ? (1.0f > f2 || f2 >= 5.0f) ? (5.0f > f2 || f2 >= 10.0f) ? this.mRange10XToMaxZoomRatioToEntrySpline.interpolate(f2) : this.mRange5XTo10XZoomRatioToEntrySpline.interpolate(f2) : this.mRange1XTo5XZoomRatioToEntrySpline.interpolate(f2) : this.mRangeMinTo1XZoomRatioToEntrySpline.interpolate(f2);
    }

    public float measureGap(int i) {
        return (float) (drawTextForItemAt(i) ? this.mLineTextGap : this.mLineLineGap);
    }

    public float measureWidth(int i) {
        return drawTextForItemAt(i) ? this.mEntryLayouts[indexToSection(i)].getLineWidth(0) : (float) this.mLineWidth;
    }

    public void onPositionSelect(HorizontalSlideView horizontalSlideView, int i, float f2) {
        if (this.mEnable) {
            String valueOf = String.valueOf(mapPositionToZoomRatio(f2 * ((float) (getCount() - 1))));
            if (!valueOf.equals(this.mCurrentValue)) {
                this.mComponentData.setComponentValue(this.mCurrentMode, valueOf);
                ManuallyListener manuallyListener = this.mManuallyListener;
                if (manuallyListener != null) {
                    manuallyListener.onManuallyDataChanged(this.mComponentData, this.mCurrentValue, valueOf, false);
                }
                this.mCurrentValue = valueOf;
                this.mCurrentValueIndex = i;
            }
        }
    }

    public void setEnable(boolean z) {
        this.mEnable = z;
    }
}
