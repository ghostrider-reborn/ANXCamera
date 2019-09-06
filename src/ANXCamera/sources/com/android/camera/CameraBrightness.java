package com.android.camera;

import android.app.Activity;
import android.hardware.display.DisplayManager;
import android.miui.R;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Spline;
import android.view.WindowManager.LayoutParams;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.internal.R.array;
import com.android.internal.R.bool;
import com.android.internal.R.integer;
import com.mi.config.b;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import miui.reflect.Field;
import miui.reflect.NoSuchFieldException;
import miui.util.ReflectionUtils;

public class CameraBrightness implements CameraBrightnessCallback {
    private static final float SCREEN_AUTO_BRIGHTNESS_RATIO = 0.5f;
    private static final String TAG = "CameraBrightness";
    private int mBrightness = -1;
    private AsyncTask<Void, Void, Integer> mCameraBrightnessTask;
    private Activity mCurrentActivity;
    private DisplayManager mDisplayManager;
    private boolean mFirstFocusChanged;
    private boolean mPaused;
    private float mScreenAutoBrightnessRatio = 0.0f;
    private boolean mUseDefaultValue;

    private static class CameraBrightnessTask extends AsyncTask<Void, Void, Integer> {
        private final float ADJUST_RATIO_BASE = 0.1f;
        private final float ADJUST_RATIO_RANGE = 0.3f;
        private final boolean DEBUG = false;
        private WeakReference<Activity> mActivityWeakReference;
        private int mBrightnessMode = -1;
        private WeakReference<CameraBrightnessCallback> mCallbackWeakReference;
        private boolean mPaused;
        private Spline mPositiveScreenManualBrightnessSpline;
        private float mScreenAutoBrightnessRatioInner;
        private Spline mScreenManualBrightnessSpline;
        private boolean mUseDefaultValue;

        public CameraBrightnessTask(Activity activity, CameraBrightnessCallback cameraBrightnessCallback, boolean z, boolean z2, float f2) {
            this.mActivityWeakReference = new WeakReference<>(activity);
            this.mCallbackWeakReference = new WeakReference<>(cameraBrightnessCallback);
            this.mPaused = z2;
            this.mUseDefaultValue = z;
            this.mScreenAutoBrightnessRatioInner = f2;
        }

        private Spline createManualBrightnessSpline(int[] iArr, int[] iArr2) {
            try {
                int length = iArr.length;
                float[] fArr = new float[length];
                float[] fArr2 = new float[length];
                for (int i = 0; i < length; i++) {
                    fArr[i] = (float) iArr[i];
                    fArr2[i] = (float) iArr2[i];
                }
                return new MonotoneCubicSpline(fArr, fArr2);
            } catch (IllegalArgumentException e2) {
                Log.e(CameraBrightness.TAG, "Could not create manual-brightness spline.", e2);
                return null;
            }
        }

        private void createSpline() {
            if ((this.mScreenManualBrightnessSpline == null || this.mPositiveScreenManualBrightnessSpline == null) && getAndroidBoolRes("config_manual_spline_available", true)) {
                int[] androidArrayRes = getAndroidArrayRes("config_manualBrightnessRemapIn");
                int[] androidArrayRes2 = getAndroidArrayRes("config_manualBrightnessRemapOut");
                this.mScreenManualBrightnessSpline = createManualBrightnessSpline(androidArrayRes2, androidArrayRes);
                this.mPositiveScreenManualBrightnessSpline = createManualBrightnessSpline(androidArrayRes, androidArrayRes2);
                if (this.mScreenManualBrightnessSpline == null || this.mPositiveScreenManualBrightnessSpline == null) {
                    Log.e(CameraBrightness.TAG, "Error to create manual brightness spline");
                }
            }
        }

        private String execCommand(String str) {
            String str2 = CameraBrightness.TAG;
            long currentTimeMillis = System.currentTimeMillis();
            String str3 = "";
            try {
                Process exec = Runtime.getRuntime().exec(str);
                if (exec.waitFor() != 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("exit value = ");
                    sb.append(exec.exitValue());
                    Log.e(str2, sb.toString());
                    return str3;
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    stringBuffer.append(readLine);
                }
                bufferedReader.close();
                str3 = stringBuffer.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append("execCommand lcd value=");
                sb2.append(str3);
                sb2.append(" cost=");
                sb2.append(System.currentTimeMillis() - currentTimeMillis);
                Log.v(str2, sb2.toString());
                return str3;
            } catch (InterruptedException e2) {
                Log.e(str2, "execCommand InterruptedException");
                e2.printStackTrace();
            } catch (IOException e3) {
                Log.e(str2, "execCommand IOException");
                e3.printStackTrace();
            }
        }

        private int[] getAndroidArrayRes(String str) {
            String str2 = Field.INT_SIGNATURE_PRIMITIVE;
            String str3 = CameraBrightness.TAG;
            try {
                return CameraAppImpl.getAndroidContext().getResources().getIntArray(Field.of(array.class, str, str2).getInt(null));
            } catch (NoSuchFieldException e2) {
                Log.e(str3, e2.getMessage());
                try {
                    return CameraAppImpl.getAndroidContext().getResources().getIntArray(GeneralUtils.miuiResArrayField(str, str2).getInt(null));
                } catch (NoSuchFieldException e3) {
                    Log.e(str3, e3.getMessage());
                    return new int[]{0, 255};
                } catch (IllegalArgumentException e4) {
                    Log.e(str3, e4.getMessage());
                    return new int[]{0, 255};
                }
            } catch (IllegalArgumentException e5) {
                Log.e(str3, e5.getMessage());
                return CameraAppImpl.getAndroidContext().getResources().getIntArray(GeneralUtils.miuiResArrayField(str, str2).getInt(null));
            }
        }

        private boolean getAndroidBoolRes(String str, boolean z) {
            String str2 = Field.INT_SIGNATURE_PRIMITIVE;
            String str3 = CameraBrightness.TAG;
            try {
                return CameraAppImpl.getAndroidContext().getResources().getBoolean(Field.of(bool.class, str, str2).getInt(null));
            } catch (NoSuchFieldException e2) {
                Log.e(str3, e2.getMessage());
                try {
                    return CameraAppImpl.getAndroidContext().getResources().getBoolean(GeneralUtils.miuiResBoolField(str, str2).getInt(null));
                } catch (NoSuchFieldException e3) {
                    Log.e(str3, e3.getMessage());
                    return z;
                } catch (IllegalArgumentException e4) {
                    Log.e(str3, e4.getMessage());
                    return z;
                }
            } catch (IllegalArgumentException e5) {
                Log.e(str3, e5.getMessage());
                return CameraAppImpl.getAndroidContext().getResources().getBoolean(GeneralUtils.miuiResBoolField(str, str2).getInt(null));
            }
        }

        private int getAndroidIntResource(String str) {
            String str2 = CameraBrightness.TAG;
            try {
                return CameraAppImpl.getAndroidContext().getResources().getInteger(Field.of(integer.class, str, Field.INT_SIGNATURE_PRIMITIVE).getInt(null));
            } catch (NoSuchFieldException e2) {
                Log.e(str2, e2.getMessage());
                return 0;
            } catch (IllegalArgumentException e3) {
                Log.e(str2, e3.getMessage());
                return 0;
            }
        }

        @Nullable
        private Integer getBrightIsAndroidP(LayoutParams layoutParams, Activity activity) {
            try {
                this.mBrightnessMode = System.getInt(activity.getContentResolver(), "screen_brightness_mode");
            } catch (SettingNotFoundException unused) {
            }
            CameraBrightnessCallback cameraBrightnessCallback = (CameraBrightnessCallback) this.mCallbackWeakReference.get();
            String str = CameraBrightness.TAG;
            if (cameraBrightnessCallback != null && this.mBrightnessMode == 1) {
                if (!this.mUseDefaultValue && !this.mPaused && this.mScreenAutoBrightnessRatioInner == 0.0f) {
                    Log.d(str, "adjustBrightnessInAutoMode(0.5)");
                    cameraBrightnessCallback.adjustBrightnessInAutoMode(0.5f);
                } else if (this.mScreenAutoBrightnessRatioInner == 0.5f) {
                    Log.d(str, "adjustBrightnessInAutoMode(0)");
                    cameraBrightnessCallback.adjustBrightnessInAutoMode(0.0f);
                }
                return null;
            } else if (this.mBrightnessMode != 0) {
                return null;
            } else {
                if (this.mUseDefaultValue || this.mPaused) {
                    return Integer.valueOf(-1);
                }
                try {
                    int i = ReflectionUtils.findField(PowerManager.class, "BRIGHTNESS_ON").getInt(PowerManager.class);
                    int i2 = System.getInt(activity.getContentResolver(), "screen_brightness");
                    StringBuilder sb = new StringBuilder();
                    sb.append("android P -> current back light -> ");
                    sb.append(i2);
                    Log.d(str, sb.toString());
                    float enLargeBrightness = (float) toEnLargeBrightness((int) Math.ceil((double) (((float) (i2 * 256)) / ((float) (i + 1)))));
                    float f2 = layoutParams.screenBrightness;
                    if (f2 <= 0.0f || enLargeBrightness != ((float) ((int) (f2 * 255.0f)))) {
                        return Integer.valueOf((int) enLargeBrightness);
                    }
                    Log.v(str, "android P -> doInBackground brightness unchanged");
                    return null;
                } catch (Exception e2) {
                    Log.e(str, e2.toString());
                    return null;
                }
            }
        }

        @Nullable
        private Integer getBrightNotAndroidP(LayoutParams layoutParams) {
            int currentBackLight = getCurrentBackLight();
            StringBuilder sb = new StringBuilder();
            sb.append("current back light -> ");
            sb.append(currentBackLight);
            String sb2 = sb.toString();
            String str = CameraBrightness.TAG;
            Log.d(str, sb2);
            if (currentBackLight <= 0) {
                return null;
            }
            createSpline();
            float f2 = layoutParams.screenBrightness;
            if (f2 > 0.0f) {
                float f3 = f2 * 255.0f;
                Spline spline = this.mPositiveScreenManualBrightnessSpline;
                if (Math.abs((spline != null ? Math.round(spline.interpolate(f3)) : Math.round(f3)) - currentBackLight) <= 1) {
                    Log.v(str, "doInBackground brightness unchanged");
                    return null;
                }
            }
            Spline spline2 = this.mScreenManualBrightnessSpline;
            if (spline2 != null) {
                currentBackLight = (int) spline2.interpolate((float) currentBackLight);
            }
            return Integer.valueOf(toEnLargeBrightness(currentBackLight));
        }

        private int getCurrentBackLight() {
            String str;
            String str2 = null;
            int i = 0;
            while (true) {
                String str3 = "0";
                boolean equals = str3.equals(str2);
                str = CameraBrightness.TAG;
                if ((equals || str2 == null) && i < 3 && !isCancelled()) {
                    str2 = execCommand("cat sys/class/backlight/panel0-backlight/brightness");
                    if (str3.equals(str2) || str2 == null) {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e2) {
                            Log.e(str, e2.getMessage());
                        }
                        i++;
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("getCurrentBackLight currentSetting=");
            sb.append(str2);
            Log.v(str, sb.toString());
            if (!TextUtils.isEmpty(str2)) {
                int androidIntResource = getAndroidIntResource("config_backlightBits");
                if (androidIntResource <= 0) {
                    androidIntResource = getMiuiIntResource("config_backlightBit");
                }
                int parseFloat = (int) Float.parseFloat(str2);
                if (androidIntResource > 8) {
                    int i2 = androidIntResource - 8;
                    int i3 = 1;
                    if (parseFloat >= (1 << i2)) {
                        i3 = parseFloat >> i2;
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("getCurrentBackLight convert to ");
                    sb2.append(i3);
                    Log.v(str, sb2.toString());
                    return i3;
                }
            }
            return -1;
        }

        private int getMiuiIntResource(String str) {
            String str2 = CameraBrightness.TAG;
            try {
                return CameraAppImpl.getAndroidContext().getResources().getInteger(Field.of(R.integer.class, str, Field.INT_SIGNATURE_PRIMITIVE).getInt(null));
            } catch (NoSuchFieldException e2) {
                Log.e(str2, e2.getMessage());
                return 0;
            } catch (IllegalArgumentException e3) {
                Log.e(str2, e3.getMessage());
                return 0;
            }
        }

        private int toEnLargeBrightness(int i) {
            return Integer.valueOf(Util.clamp((int) (((double) i) * (((double) (((((float) Util.clamp(i, 0, 185)) / 185.0f) * 0.3f) + 0.1f)) + 1.0d)), 0, 255)).intValue();
        }

        private void updateBrightness(int i) {
            Activity activity = (Activity) this.mActivityWeakReference.get();
            if (activity != null) {
                LayoutParams attributes = activity.getWindow().getAttributes();
                if (this.mUseDefaultValue || this.mPaused) {
                    attributes.screenBrightness = -1.0f;
                } else {
                    attributes.screenBrightness = ((float) i) / 255.0f;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("updateBrightness setting=");
                sb.append(i);
                sb.append(" useDefaultValue=");
                sb.append(this.mUseDefaultValue);
                sb.append(" screenBrightness=");
                sb.append(attributes.screenBrightness);
                Log.v(CameraBrightness.TAG, sb.toString());
                activity.getWindow().setAttributes(attributes);
                CameraBrightnessCallback cameraBrightnessCallback = (CameraBrightnessCallback) this.mCallbackWeakReference.get();
                if (cameraBrightnessCallback != null) {
                    cameraBrightnessCallback.setBrightness(i);
                }
            }
        }

        /* access modifiers changed from: protected */
        public Integer doInBackground(Void... voidArr) {
            StringBuilder sb = new StringBuilder();
            sb.append("doInBackground useDefaultValue=");
            sb.append(this.mUseDefaultValue);
            sb.append(" paused=");
            sb.append(this.mPaused);
            Log.v(CameraBrightness.TAG, sb.toString());
            Activity activity = (Activity) this.mActivityWeakReference.get();
            LayoutParams attributes = activity.getWindow().getAttributes();
            if (VERSION.SDK_INT >= 28) {
                return getBrightIsAndroidP(attributes, activity);
            }
            if (activity != null) {
                return getBrightNotAndroidP(attributes);
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Integer num) {
            if (!isCancelled() && num != null) {
                updateBrightness(num.intValue());
            }
        }
    }

    public static class MonotoneCubicSpline extends Spline {
        private float[] mM;
        private float[] mX;
        private float[] mY;

        public MonotoneCubicSpline(float[] fArr, float[] fArr2) {
            if (fArr == null || fArr2 == null || fArr.length != fArr2.length || fArr.length < 2) {
                throw new IllegalArgumentException("There must be at least two control points and the arrays must be of equal length.");
            }
            int length = fArr.length;
            int i = length - 1;
            float[] fArr3 = new float[i];
            float[] fArr4 = new float[length];
            int i2 = 0;
            while (i2 < i) {
                int i3 = i2 + 1;
                float f2 = fArr[i3] - fArr[i2];
                if (f2 > 0.0f) {
                    fArr3[i2] = (fArr2[i3] - fArr2[i2]) / f2;
                    i2 = i3;
                } else {
                    throw new IllegalArgumentException("The control points must all have strictly increasing X values.");
                }
            }
            fArr4[0] = fArr3[0];
            for (int i4 = 1; i4 < i; i4++) {
                fArr4[i4] = (fArr3[i4 - 1] + fArr3[i4]) * 0.5f;
            }
            fArr4[i] = fArr3[length - 2];
            for (int i5 = 0; i5 < i; i5++) {
                if (fArr3[i5] == 0.0f) {
                    fArr4[i5] = 0.0f;
                    fArr4[i5 + 1] = 0.0f;
                } else {
                    float f3 = fArr4[i5] / fArr3[i5];
                    int i6 = i5 + 1;
                    float f4 = fArr4[i6] / fArr3[i5];
                    if (f3 < 0.0f || f4 < 0.0f) {
                        throw new IllegalArgumentException("The control points must have monotonic Y values.");
                    }
                    float hypot = (float) Math.hypot((double) f3, (double) f4);
                    if (hypot > 9.0f) {
                        float f5 = 3.0f / hypot;
                        fArr4[i5] = f3 * f5 * fArr3[i5];
                        fArr4[i6] = f5 * f4 * fArr3[i5];
                    }
                }
            }
            this.mX = fArr;
            this.mY = fArr2;
            this.mM = fArr4;
        }

        public float interpolate(float f2) {
            int length = this.mX.length;
            if (Float.isNaN(f2)) {
                return f2;
            }
            float[] fArr = this.mX;
            int i = 0;
            if (f2 <= fArr[0]) {
                return this.mY[0];
            }
            int i2 = length - 1;
            if (f2 >= fArr[i2]) {
                return this.mY[i2];
            }
            while (true) {
                float[] fArr2 = this.mX;
                int i3 = i + 1;
                if (f2 < fArr2[i3]) {
                    float f3 = fArr2[i3] - fArr2[i];
                    float f4 = (f2 - fArr2[i]) / f3;
                    float[] fArr3 = this.mY;
                    float f5 = 2.0f * f4;
                    float f6 = fArr3[i] * (f5 + 1.0f);
                    float[] fArr4 = this.mM;
                    float f7 = f6 + (fArr4[i] * f3 * f4);
                    float f8 = 1.0f - f4;
                    return (f7 * f8 * f8) + (((fArr3[i3] * (3.0f - f5)) + (f3 * fArr4[i3] * (f4 - 1.0f))) * f4 * f4);
                } else if (f2 == fArr2[i3]) {
                    return this.mY[i3];
                } else {
                    i = i3;
                }
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            int length = this.mX.length;
            sb.append("MonotoneCubicSpline{[");
            for (int i = 0; i < length; i++) {
                String str = ", ";
                if (i != 0) {
                    sb.append(str);
                }
                sb.append("(");
                sb.append(this.mX[i]);
                sb.append(str);
                sb.append(this.mY[i]);
                sb.append(": ");
                sb.append(this.mM[i]);
                sb.append(")");
            }
            sb.append("]}");
            return sb.toString();
        }
    }

    public CameraBrightness(Activity activity) {
        this.mCurrentActivity = activity;
        this.mUseDefaultValue = true;
        this.mFirstFocusChanged = false;
        this.mDisplayManager = (DisplayManager) CameraAppImpl.getAndroidContext().getSystemService(DisplayManager.class);
    }

    private void adjustBrightness() {
        if (this.mCurrentActivity != null && b.Nh()) {
            cancelLastTask();
            CameraBrightnessTask cameraBrightnessTask = new CameraBrightnessTask(this.mCurrentActivity, this, this.mUseDefaultValue, this.mPaused, this.mScreenAutoBrightnessRatio);
            this.mCameraBrightnessTask = cameraBrightnessTask.execute(new Void[0]);
        }
    }

    private void cancelLastTask() {
        AsyncTask<Void, Void, Integer> asyncTask = this.mCameraBrightnessTask;
        if (asyncTask != null) {
            asyncTask.cancel(true);
            this.mCameraBrightnessTask = null;
        }
    }

    public void adjustBrightnessInAutoMode(float f2) {
        try {
            CompatibilityUtils.setTemporaryAutoBrightnessAdjustment(this.mDisplayManager, f2);
            this.mScreenAutoBrightnessRatio = f2;
        } catch (SecurityException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Meet exception when adjustBrightnessInAutoMode(): ");
            sb.append(e2);
            Log.e(TAG, sb.toString());
        }
    }

    public int getCurrentBrightness() {
        return this.mBrightness;
    }

    public float getCurrentBrightnessAuto() {
        return this.mScreenAutoBrightnessRatio;
    }

    public int getCurrentBrightnessManual() {
        return this.mBrightness;
    }

    public void onPause() {
        this.mFirstFocusChanged = false;
        this.mPaused = true;
        cancelLastTask();
    }

    public void onResume() {
        this.mUseDefaultValue = this.mCurrentActivity instanceof BasePreferenceActivity;
        this.mPaused = false;
        Log.v(TAG, "onResume adjustBrightness");
        adjustBrightness();
    }

    public void onWindowFocusChanged(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("onWindowFocusChanged hasFocus=");
        sb.append(z);
        sb.append(" mFirstFocusChanged=");
        sb.append(this.mFirstFocusChanged);
        Log.v(TAG, sb.toString());
        boolean z2 = true;
        if (this.mFirstFocusChanged || !z) {
            if (!(this.mCurrentActivity instanceof BasePreferenceActivity) && z) {
                z2 = false;
            }
            this.mUseDefaultValue = z2;
            adjustBrightness();
            return;
        }
        this.mFirstFocusChanged = true;
    }

    public void setBrightness(int i) {
        this.mBrightness = i;
    }
}
