package com.android.camera;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import com.android.camera.log.Log;

public class SensorStateManager {
    private static final int ACCELEROMETER = 8;
    private static final double ACCELEROMETER_GAP_TOLERANCE = 0.8999999761581421d;
    private static final int ACCELEROMETER_THRESHOLD = 16;
    /* access modifiers changed from: private */
    public static final int CAPTURE_POSTURE_DEGREE = SystemProperties.getInt("capture_degree", 45);
    private static final long EVENT_PROCESS_INTERVAL = 100000000;
    private static final long EVENT_TIME_OUT = 1000000000;
    private static final int GAME_ROTATION = 64;
    private static final int GRAVITY = 32;
    private static final int GYROSCOPE = 2;
    private static final double GYROSCOPE_FOCUS_THRESHOLD = 1.0471975511965976d;
    private static final double GYROSCOPE_IGNORE_THRESHOLD = 0.05000000074505806d;
    /* access modifiers changed from: private */
    public static final double GYROSCOPE_MOVING_THRESHOLD = ((double) (((float) SystemProperties.getInt("camera_moving_threshold", 15)) / 10.0f));
    private static final double GYROSCOPE_STABLE_THRESHOLD = ((double) (((float) SystemProperties.getInt("camera_stable_threshold", 9)) / 10.0f));
    public static final int LEFT_CAPTURE_POSTURE = 1;
    private static final int LINEAR_ACCELEROMETER = 1;
    private static final int LYING_HYSTERESIS = 5;
    private static final int MAX_LYING_BOUND = 153;
    private static final int MIN_LYING_BOUND = 26;
    private static final int MSG_DEVICE_BECOME_STABLE = 1;
    private static final int MSG_UPDATE = 2;
    private static final float NS2S = 1.0E-9f;
    private static final int ORIENTATION = 4;
    public static final int ORIENTATION_UNKNOWN = -1;
    public static final int PORTRAIT_CAPTURE_POSTURE = 0;
    public static final int RIGHT_CAPTURE_POSTURE = 2;
    private static final int ROTATION_VECTOR = 16;
    public static final int SENSOR_ALL = 127;
    private static final String TAG = "SensorStateManager";
    private final Sensor mAccelerometerSensor;
    private SensorEventListener mAccelerometerSensorEventListenerImpl;
    /* access modifiers changed from: private */
    public long mAccelerometerTimeStamp;
    /* access modifiers changed from: private */
    public double[] mAngleSpeed;
    /* access modifiers changed from: private */
    public int mAngleSpeedIndex;
    /* access modifiers changed from: private */
    public double mAngleTotal = 0.0d;
    private int mCapturePosture = 0;
    private boolean mEdgeTouchEnabled;
    private boolean mFocusSensorEnabled;
    private final Sensor mGameRotationSensor;
    private SensorEventListener mGameRotationSensorListener;
    /* access modifiers changed from: private */
    public boolean mGradienterEnabled;
    private final Sensor mGravitySensor;
    private SensorEventListener mGravitySensorListener;
    private final Sensor mGyroscope;
    private boolean mGyroscopeEnabled;
    private SensorEventListener mGyroscopeListener;
    /* access modifiers changed from: private */
    public long mGyroscopeTimeStamp;
    private Handler mHandler;
    /* access modifiers changed from: private */
    public boolean mIsLyingForGradienter = false;
    /* access modifiers changed from: private */
    public boolean mIsLyingForLie = false;
    /* access modifiers changed from: private */
    public boolean mLieFlagEnabled;
    private SensorEventListener mLinearAccelerationListener;
    private final Sensor mLinearAccelerometer;
    /* access modifiers changed from: private */
    public float mOrientation = -1.0f;
    private final Sensor mOrientationSensor;
    private SensorEventListener mOrientationSensorEventListener;
    private int mRate;
    private SensorEventListener mRoatationSensorListener;
    private final Sensor mRotationVecotrSensor;
    private boolean mRotationVectorFlagEnabled;
    private HandlerThread mSensorListenerThread;
    private final SensorManager mSensorManager;
    private int mSensorRegister;
    private SensorStateListener mSensorStateListener;
    private boolean mTTARFlagEnabled;
    private Handler mThreadHandler;

    private class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            int i = message.what;
            boolean z = true;
            if (i == 1) {
                SensorStateManager.this.deviceBecomeStable();
            } else if (i == 2) {
                SensorStateManager sensorStateManager = SensorStateManager.this;
                int i2 = message.arg1;
                if (message.arg2 != 1) {
                    z = false;
                }
                sensorStateManager.update(i2, z);
            }
        }
    }

    class OrientationSensorEventListenerImpl implements SensorEventListener {
        OrientationSensorEventListenerImpl() {
        }

        public void onAccuracyChanged(Sensor sensor, int i) {
            StringBuilder sb = new StringBuilder();
            sb.append("onAccuracyChanged accuracy=");
            sb.append(i);
            Log.v(SensorStateManager.TAG, sb.toString());
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            SensorStateListener access$000 = SensorStateManager.this.getSensorStateListener();
            if (access$000 != null) {
                float[] fArr = sensorEvent.values;
                int i = 1;
                float f2 = fArr[1];
                float f3 = fArr[2];
                float abs = Math.abs(f2);
                float abs2 = Math.abs(f3);
                boolean access$900 = SensorStateManager.this.mLieFlagEnabled;
                String str = SensorStateManager.TAG;
                int i2 = 5;
                if (access$900) {
                    int i3 = SensorStateManager.this.mIsLyingForLie ? 5 : 0;
                    int i4 = i3 + 10;
                    int i5 = 170 - i3;
                    float f4 = (float) i4;
                    boolean z = (abs <= f4 || abs >= ((float) i5)) && (abs2 <= f4 || abs2 >= ((float) i5));
                    if (z != SensorStateManager.this.mIsLyingForLie) {
                        SensorStateManager.this.mIsLyingForLie = z;
                        StringBuilder sb = new StringBuilder();
                        sb.append("SensorEventListenerImpl TYPE_ORIENTATION mIsLyingForLie=");
                        sb.append(SensorStateManager.this.mIsLyingForLie);
                        Log.v(str, sb.toString());
                        access$000.onDeviceLieChanged(SensorStateManager.this.mIsLyingForLie);
                    }
                }
                if (SensorStateManager.this.mGradienterEnabled) {
                    if (!SensorStateManager.this.mIsLyingForGradienter) {
                        i2 = 0;
                    }
                    int i6 = i2 + 26;
                    int i7 = 153 - i2;
                    float f5 = (float) i6;
                    boolean z2 = (abs <= f5 || abs >= ((float) i7)) && (abs2 <= f5 || abs2 >= ((float) i7));
                    if (z2 != SensorStateManager.this.mIsLyingForGradienter) {
                        SensorStateManager.this.mIsLyingForGradienter = z2;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("SensorEventListenerImpl TYPE_ORIENTATION mIsLyingForGradienter=");
                        sb2.append(SensorStateManager.this.mIsLyingForGradienter);
                        sb2.append("mOrientation=");
                        sb2.append(SensorStateManager.this.mOrientation);
                        Log.v(str, sb2.toString());
                        access$000.onDeviceOrientationChanged(SensorStateManager.this.mOrientation, SensorStateManager.this.mIsLyingForGradienter);
                    }
                }
                if (Math.abs(abs2 - 90.0f) < ((float) SensorStateManager.CAPTURE_POSTURE_DEGREE)) {
                    SensorStateManager sensorStateManager = SensorStateManager.this;
                    if (f3 >= 0.0f) {
                        i = 2;
                    }
                    sensorStateManager.changeCapturePosture(i);
                } else {
                    SensorStateManager.this.changeCapturePosture(0);
                }
            }
        }
    }

    public interface SensorStateListener {
        boolean isWorking();

        void notifyDevicePostureChanged();

        void onDeviceBecomeStable();

        void onDeviceBeginMoving();

        void onDeviceKeepMoving(double d2);

        void onDeviceKeepStable();

        void onDeviceLieChanged(boolean z);

        void onDeviceOrientationChanged(float f2, boolean z);

        void onDeviceRotationChanged(float[] fArr);

        void onSensorChanged(SensorEvent sensorEvent);
    }

    public SensorStateManager(Context context, Looper looper) {
        double d2 = GYROSCOPE_STABLE_THRESHOLD;
        this.mAngleSpeed = new double[]{d2, d2, d2, d2, d2};
        this.mAngleSpeedIndex = -1;
        this.mAccelerometerTimeStamp = 0;
        this.mGyroscopeTimeStamp = 0;
        this.mGyroscopeListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            public void onSensorChanged(SensorEvent sensorEvent) {
                SensorStateListener access$000 = SensorStateManager.this.getSensorStateListener();
                if (access$000 != null && access$000.isWorking()) {
                    long abs = Math.abs(sensorEvent.timestamp - SensorStateManager.this.mGyroscopeTimeStamp);
                    if (abs >= SensorStateManager.EVENT_PROCESS_INTERVAL) {
                        if (SensorStateManager.this.mGyroscopeTimeStamp == 0 || abs > SensorStateManager.EVENT_TIME_OUT) {
                            SensorStateManager.this.mGyroscopeTimeStamp = sensorEvent.timestamp;
                        } else {
                            float f2 = ((float) abs) * SensorStateManager.NS2S;
                            float[] fArr = sensorEvent.values;
                            double sqrt = Math.sqrt((double) ((fArr[0] * fArr[0]) + (fArr[1] * fArr[1]) + (fArr[2] * fArr[2])));
                            SensorStateManager.this.mGyroscopeTimeStamp = sensorEvent.timestamp;
                            if (SensorStateManager.GYROSCOPE_MOVING_THRESHOLD < sqrt) {
                                SensorStateManager.this.deviceBeginMoving();
                            }
                            SensorStateManager sensorStateManager = SensorStateManager.this;
                            sensorStateManager.mAngleSpeedIndex = SensorStateManager.access$404(sensorStateManager) % SensorStateManager.this.mAngleSpeed.length;
                            SensorStateManager.this.mAngleSpeed[SensorStateManager.this.mAngleSpeedIndex] = sqrt;
                            if (sqrt >= SensorStateManager.GYROSCOPE_IGNORE_THRESHOLD) {
                                SensorStateManager.access$618(SensorStateManager.this, sqrt * ((double) f2));
                                if (SensorStateManager.this.mAngleTotal > SensorStateManager.GYROSCOPE_FOCUS_THRESHOLD) {
                                    SensorStateManager.this.mAngleTotal = 0.0d;
                                    SensorStateManager.this.deviceKeepMoving(10000.0d);
                                }
                                if (access$000.isWorking()) {
                                    access$000.onSensorChanged(sensorEvent);
                                }
                            }
                        }
                    }
                }
            }
        };
        this.mLinearAccelerationListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            public void onSensorChanged(SensorEvent sensorEvent) {
                SensorStateListener access$000 = SensorStateManager.this.getSensorStateListener();
                if (access$000 != null && access$000.isWorking()) {
                    long abs = Math.abs(sensorEvent.timestamp - SensorStateManager.this.mAccelerometerTimeStamp);
                    if (abs >= SensorStateManager.EVENT_PROCESS_INTERVAL) {
                        if (SensorStateManager.this.mAccelerometerTimeStamp == 0 || abs > SensorStateManager.EVENT_TIME_OUT) {
                            SensorStateManager.this.mAccelerometerTimeStamp = sensorEvent.timestamp;
                        } else {
                            float[] fArr = sensorEvent.values;
                            double sqrt = Math.sqrt((double) ((fArr[0] * fArr[0]) + (fArr[1] * fArr[1]) + (fArr[2] * fArr[2])));
                            SensorStateManager.this.mAccelerometerTimeStamp = sensorEvent.timestamp;
                            if (sqrt > SensorStateManager.ACCELEROMETER_GAP_TOLERANCE) {
                                SensorStateManager.this.deviceKeepMoving(sqrt);
                            }
                        }
                    }
                }
            }
        };
        this.mAccelerometerSensorEventListenerImpl = new SensorEventListener() {
            private static final float CLEAR_FILTER_THRESHOLD = 3.0f;
            private static final int _DATA_X = 0;
            private static final int _DATA_Y = 1;
            private static final int _DATA_Z = 2;
            private static final float finalAlpha = 0.7f;
            private static final float firstAlpha = 0.8f;
            private float[] finalFilter = new float[3];
            private float[] firstFilter = new float[3];

            private void clearFilter() {
                int i = 0;
                while (true) {
                    float[] fArr = this.firstFilter;
                    if (i < fArr.length) {
                        fArr[i] = 0.0f;
                        this.finalFilter[i] = 0.0f;
                        i++;
                    } else {
                        return;
                    }
                }
            }

            public void onAccuracyChanged(Sensor sensor, int i) {
                StringBuilder sb = new StringBuilder();
                sb.append("onAccuracyChanged accuracy=");
                sb.append(i);
                Log.v(SensorStateManager.TAG, sb.toString());
            }

            public void onSensorChanged(SensorEvent sensorEvent) {
                SensorStateListener access$000 = SensorStateManager.this.getSensorStateListener();
                if (access$000 != null) {
                    float[] fArr = this.firstFilter;
                    float f2 = fArr[0] * firstAlpha;
                    float[] fArr2 = sensorEvent.values;
                    fArr[0] = f2 + (fArr2[0] * 0.19999999f);
                    fArr[1] = (fArr[1] * firstAlpha) + (fArr2[1] * 0.19999999f);
                    fArr[2] = (fArr[2] * firstAlpha) + (fArr2[2] * 0.19999999f);
                    float[] fArr3 = this.finalFilter;
                    fArr3[0] = (fArr3[0] * 0.7f) + (fArr[0] * 0.3f);
                    fArr3[1] = (fArr3[1] * 0.7f) + (fArr[1] * 0.3f);
                    fArr3[2] = (fArr3[2] * 0.7f) + (fArr[2] * 0.3f);
                    StringBuilder sb = new StringBuilder();
                    sb.append("finalFilter=");
                    sb.append(this.finalFilter[0]);
                    String str = " ";
                    sb.append(str);
                    sb.append(this.finalFilter[1]);
                    sb.append(str);
                    sb.append(this.finalFilter[2]);
                    sb.append(" event.values=");
                    sb.append(sensorEvent.values[0]);
                    sb.append(str);
                    sb.append(sensorEvent.values[1]);
                    sb.append(str);
                    sb.append(sensorEvent.values[2]);
                    String sb2 = sb.toString();
                    String str2 = SensorStateManager.TAG;
                    Log.v(str2, sb2);
                    float f3 = -1.0f;
                    float[] fArr4 = this.finalFilter;
                    float f4 = -fArr4[0];
                    float f5 = -fArr4[1];
                    float f6 = -fArr4[2];
                    if (((f4 * f4) + (f5 * f5)) * 4.0f >= f6 * f6) {
                        f3 = SensorStateManager.this.normalizeDegree((float) (90 - Math.round(((float) Math.atan2((double) (-f5), (double) f4)) * 57.29578f)));
                    }
                    if (f3 != SensorStateManager.this.mOrientation) {
                        if (Math.abs(SensorStateManager.this.mOrientation - f3) > 3.0f) {
                            clearFilter();
                        }
                        SensorStateManager.this.mOrientation = f3;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("SensorEventListenerImpl TYPE_ACCELEROMETER mOrientation=");
                        sb3.append(SensorStateManager.this.mOrientation);
                        sb3.append(" mIsLyingForGradienter=");
                        sb3.append(SensorStateManager.this.mIsLyingForGradienter);
                        Log.v(str2, sb3.toString());
                        access$000.onDeviceOrientationChanged(SensorStateManager.this.mOrientation, SensorStateManager.this.mIsLyingForGradienter);
                    }
                    if (access$000.isWorking()) {
                        access$000.onSensorChanged(sensorEvent);
                    }
                }
            }
        };
        this.mRoatationSensorListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            public void onSensorChanged(SensorEvent sensorEvent) {
                SensorStateListener access$000 = SensorStateManager.this.getSensorStateListener();
                if (access$000 != null && access$000.isWorking()) {
                    float[] fArr = sensorEvent.values;
                    if (fArr != null && fArr.length >= 4) {
                        access$000.onDeviceRotationChanged(new float[]{fArr[0], fArr[1], fArr[2], fArr[3]});
                    }
                }
            }
        };
        this.mGravitySensorListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            public void onSensorChanged(SensorEvent sensorEvent) {
                SensorStateListener access$000 = SensorStateManager.this.getSensorStateListener();
                if (access$000 != null && access$000.isWorking()) {
                    access$000.onSensorChanged(sensorEvent);
                }
            }
        };
        this.mGameRotationSensorListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            public void onSensorChanged(SensorEvent sensorEvent) {
                SensorStateListener access$000 = SensorStateManager.this.getSensorStateListener();
                if (access$000 != null && access$000.isWorking()) {
                    access$000.onSensorChanged(sensorEvent);
                }
            }
        };
        this.mSensorManager = (SensorManager) context.getApplicationContext().getSystemService("sensor");
        this.mLinearAccelerometer = this.mSensorManager.getDefaultSensor(10);
        this.mGyroscope = this.mSensorManager.getDefaultSensor(4);
        this.mOrientationSensor = this.mSensorManager.getDefaultSensor(3);
        this.mAccelerometerSensor = this.mSensorManager.getDefaultSensor(1);
        this.mRotationVecotrSensor = this.mSensorManager.getDefaultSensor(11);
        this.mGravitySensor = this.mSensorManager.getDefaultSensor(9);
        this.mGameRotationSensor = this.mSensorManager.getDefaultSensor(15);
        this.mHandler = new MainHandler(looper);
        this.mRate = 30000;
        if (canDetectOrientation()) {
            this.mOrientationSensorEventListener = new OrientationSensorEventListenerImpl();
        }
        this.mSensorListenerThread = new HandlerThread("SensorListenerThread");
        this.mSensorListenerThread.start();
    }

    static /* synthetic */ int access$404(SensorStateManager sensorStateManager) {
        int i = sensorStateManager.mAngleSpeedIndex + 1;
        sensorStateManager.mAngleSpeedIndex = i;
        return i;
    }

    static /* synthetic */ double access$618(SensorStateManager sensorStateManager, double d2) {
        double d3 = sensorStateManager.mAngleTotal + d2;
        sensorStateManager.mAngleTotal = d3;
        return d3;
    }

    /* access modifiers changed from: private */
    public void changeCapturePosture(int i) {
        if (this.mCapturePosture != i) {
            this.mCapturePosture = i;
            SensorStateListener sensorStateListener = getSensorStateListener();
            if (sensorStateListener != null) {
                sensorStateListener.notifyDevicePostureChanged();
            }
        }
    }

    /* access modifiers changed from: private */
    public void deviceBecomeStable() {
        if (this.mFocusSensorEnabled) {
            SensorStateListener sensorStateListener = getSensorStateListener();
            if (sensorStateListener != null) {
                sensorStateListener.onDeviceBecomeStable();
            }
        }
    }

    /* access modifiers changed from: private */
    public void deviceBeginMoving() {
        SensorStateListener sensorStateListener = getSensorStateListener();
        if (sensorStateListener != null) {
            sensorStateListener.onDeviceBeginMoving();
        }
    }

    /* access modifiers changed from: private */
    public void deviceKeepMoving(double d2) {
        if (this.mFocusSensorEnabled) {
            SensorStateListener sensorStateListener = getSensorStateListener();
            if (sensorStateListener != null) {
                sensorStateListener.onDeviceKeepMoving(d2);
            }
        }
    }

    private void deviceKeepStable() {
        SensorStateListener sensorStateListener = getSensorStateListener();
        if (sensorStateListener != null) {
            sensorStateListener.onDeviceKeepStable();
        }
    }

    private int filterSensor(int i) {
        if (this.mEdgeTouchEnabled) {
            i = i & -3 & -5;
        }
        if (this.mLieFlagEnabled) {
            i &= -5;
        }
        if (this.mFocusSensorEnabled) {
            i = i & -2 & -3;
        }
        if (this.mGradienterEnabled) {
            i = i & -9 & -5;
        }
        if (this.mGyroscopeEnabled) {
            i &= -3;
        }
        return this.mTTARFlagEnabled ? i & -9 & -33 & -3 & -65 : i;
    }

    /* access modifiers changed from: private */
    public synchronized SensorStateListener getSensorStateListener() {
        return this.mSensorStateListener;
    }

    private boolean isContains(int i, int i2) {
        return (i & i2) == i2;
    }

    private boolean isPartialContains(int i, int i2) {
        return (i & i2) != 0;
    }

    /* access modifiers changed from: private */
    public float normalizeDegree(float f2) {
        while (f2 >= 360.0f) {
            f2 -= 360.0f;
        }
        while (f2 < 0.0f) {
            f2 += 360.0f;
        }
        return f2;
    }

    /* access modifiers changed from: private */
    public void update(int i, boolean z) {
        if (!z && isPartialContains(this.mSensorRegister, i)) {
            unregister(i);
        } else if (z && !isContains(this.mSensorRegister, i)) {
            register(i);
        }
    }

    public boolean canDetectOrientation() {
        return this.mOrientationSensor != null;
    }

    public int getCapturePosture() {
        return this.mCapturePosture;
    }

    public boolean isDeviceLying() {
        return this.mIsLyingForGradienter;
    }

    public boolean isStable() {
        int i = this.mAngleSpeedIndex;
        String str = " threshold=";
        String str2 = TAG;
        boolean z = true;
        if (i >= 0) {
            double d2 = 0.0d;
            for (double d3 : this.mAngleSpeed) {
                d2 += d3;
            }
            double[] dArr = this.mAngleSpeed;
            double length = d2 / ((double) dArr.length);
            double d4 = dArr[Util.clamp(this.mAngleSpeedIndex, 0, dArr.length - 1)];
            StringBuilder sb = new StringBuilder();
            sb.append("isStable mAngleSpeed=");
            sb.append(length);
            sb.append(" lastSpeed=");
            sb.append(d4);
            sb.append(str);
            sb.append(GYROSCOPE_STABLE_THRESHOLD);
            Log.v(str2, sb.toString());
            double d5 = GYROSCOPE_STABLE_THRESHOLD;
            if (length > d5 || d4 > d5) {
                z = false;
            }
            return z;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("isStable return true for mAngleSpeedIndex=");
        sb2.append(this.mAngleSpeedIndex);
        sb2.append(str);
        sb2.append(GYROSCOPE_STABLE_THRESHOLD);
        Log.v(str2, sb2.toString());
        return true;
    }

    public void onDestroy() {
        this.mSensorListenerThread.quit();
    }

    public void register() {
        int i = this.mFocusSensorEnabled ? 3 : 0;
        if (this.mEdgeTouchEnabled) {
            i = i | 2 | 4;
        }
        if (this.mGradienterEnabled) {
            i = i | 8 | 4;
        }
        if (this.mLieFlagEnabled) {
            i |= 4;
        }
        if (this.mRotationVectorFlagEnabled) {
            i |= 16;
        }
        if (this.mTTARFlagEnabled) {
            i = i | 8 | 2 | 64 | 32;
        }
        if (this.mGyroscopeEnabled) {
            i |= 2;
        }
        register(i);
    }

    public void register(int i) {
        if (!isContains(this.mSensorRegister, i)) {
            if (this.mThreadHandler == null && isPartialContains(i, 12)) {
                this.mThreadHandler = new Handler(this.mSensorListenerThread.getLooper());
            }
            if (this.mFocusSensorEnabled) {
                i = i | 1 | 2;
                this.mHandler.removeMessages(2);
            }
            if (isContains(i, 2) && !isContains(this.mSensorRegister, 2)) {
                this.mSensorManager.registerListener(this.mGyroscopeListener, this.mGyroscope, 2);
                this.mSensorRegister |= 2;
            }
            if (isContains(i, 1) && !isContains(this.mSensorRegister, 1)) {
                this.mSensorManager.registerListener(this.mLinearAccelerationListener, this.mLinearAccelerometer, 2);
                this.mSensorRegister = 1 | this.mSensorRegister;
            }
            if (canDetectOrientation() && isContains(i, 4) && !isContains(this.mSensorRegister, 4)) {
                HandlerThread handlerThread = this.mSensorListenerThread;
                if (handlerThread != null && handlerThread.isAlive()) {
                    this.mSensorManager.registerListener(this.mOrientationSensorEventListener, this.mOrientationSensor, this.mRate, this.mThreadHandler);
                    this.mSensorRegister = 4 | this.mSensorRegister;
                }
            }
            if (isContains(i, 8) && !isContains(this.mSensorRegister, 8)) {
                HandlerThread handlerThread2 = this.mSensorListenerThread;
                if (handlerThread2 != null && handlerThread2.isAlive()) {
                    this.mSensorManager.registerListener(this.mAccelerometerSensorEventListenerImpl, this.mAccelerometerSensor, this.mRate, this.mThreadHandler);
                    this.mSensorRegister = 8 | this.mSensorRegister;
                }
            }
            if (isContains(i, 16) && !isContains(this.mSensorRegister, 16)) {
                HandlerThread handlerThread3 = this.mSensorListenerThread;
                if (handlerThread3 != null && handlerThread3.isAlive()) {
                    this.mSensorManager.registerListener(this.mRoatationSensorListener, this.mRotationVecotrSensor, this.mRate, this.mThreadHandler);
                    this.mSensorRegister = 16 | this.mSensorRegister;
                }
            }
            if (isContains(i, 32) && !isContains(this.mSensorRegister, 32)) {
                HandlerThread handlerThread4 = this.mSensorListenerThread;
                if (handlerThread4 != null && handlerThread4.isAlive()) {
                    this.mSensorManager.registerListener(this.mGravitySensorListener, this.mGravitySensor, this.mRate, this.mThreadHandler);
                    this.mSensorRegister = 32 | this.mSensorRegister;
                }
            }
            if (isContains(i, 64) && !isContains(this.mSensorRegister, 64)) {
                HandlerThread handlerThread5 = this.mSensorListenerThread;
                if (handlerThread5 != null && handlerThread5.isAlive()) {
                    this.mSensorManager.registerListener(this.mGameRotationSensorListener, this.mGameRotationSensor, this.mRate, this.mThreadHandler);
                    this.mSensorRegister |= 64;
                }
            }
        }
    }

    public void reset() {
        this.mHandler.removeMessages(1);
        this.mAngleTotal = 0.0d;
    }

    public void setEdgeTouchEnabled(boolean z) {
        int i;
        if (this.mEdgeTouchEnabled != z) {
            if (z) {
                i = filterSensor(6);
                this.mEdgeTouchEnabled = z;
            } else {
                this.mEdgeTouchEnabled = z;
                i = filterSensor(6);
            }
            update(i, this.mEdgeTouchEnabled);
        }
    }

    public void setFocusSensorEnabled(boolean z) {
        int i;
        if (this.mFocusSensorEnabled != z) {
            this.mHandler.removeMessages(2);
            if (z) {
                i = filterSensor(3);
                this.mFocusSensorEnabled = z;
            } else {
                this.mFocusSensorEnabled = z;
                i = filterSensor(3);
            }
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(2, i, z ? 1 : 0), 1000);
        }
    }

    public void setGradienterEnabled(boolean z) {
        int i;
        if (this.mGradienterEnabled != z) {
            if (z) {
                i = filterSensor(12);
                this.mGradienterEnabled = z;
            } else {
                this.mGradienterEnabled = z;
                i = filterSensor(12);
            }
            update(i, this.mGradienterEnabled);
        }
    }

    public void setGyroscopeEnabled(boolean z) {
        int i;
        if (this.mGyroscopeEnabled != z) {
            if (z) {
                i = filterSensor(2);
                this.mGyroscopeEnabled = z;
            } else {
                this.mGyroscopeEnabled = z;
                i = filterSensor(2);
            }
            update(i, this.mGyroscopeEnabled);
        }
    }

    public void setLieIndicatorEnabled(boolean z) {
        int i;
        if (canDetectOrientation() && this.mLieFlagEnabled != z) {
            if (z) {
                i = filterSensor(4);
                this.mLieFlagEnabled = z;
            } else {
                this.mLieFlagEnabled = z;
                i = filterSensor(4);
            }
            update(i, this.mLieFlagEnabled);
        }
    }

    public void setRotationVectorEnabled(boolean z) {
        if (this.mRotationVectorFlagEnabled != z) {
            this.mRotationVectorFlagEnabled = z;
            update(16, this.mRotationVectorFlagEnabled);
        }
    }

    public synchronized void setSensorStateListener(SensorStateListener sensorStateListener) {
        this.mSensorStateListener = sensorStateListener;
    }

    public void setTTARSensorEnabled(boolean z) {
        int i;
        if (this.mTTARFlagEnabled != z) {
            if (z) {
                i = filterSensor(106);
                this.mTTARFlagEnabled = z;
            } else {
                this.mTTARFlagEnabled = z;
                i = filterSensor(106);
            }
            update(i, this.mTTARFlagEnabled);
        }
    }

    public void unregister(int i) {
        if (this.mSensorRegister != 0) {
            if (!this.mFocusSensorEnabled || i == 127) {
                if (!this.mFocusSensorEnabled && this.mHandler.hasMessages(2)) {
                    i |= 1;
                    if (!this.mEdgeTouchEnabled) {
                        i |= 2;
                    }
                }
                reset();
                this.mHandler.removeMessages(2);
            }
            if (isContains(i, 2) && isContains(this.mSensorRegister, 2)) {
                this.mSensorManager.unregisterListener(this.mGyroscopeListener);
                this.mSensorRegister &= -3;
            }
            if (isContains(i, 1) && isContains(this.mSensorRegister, 1)) {
                this.mSensorManager.unregisterListener(this.mLinearAccelerationListener);
                this.mSensorRegister &= -2;
            }
            if (isContains(i, 4) && isContains(this.mSensorRegister, 4)) {
                this.mSensorManager.unregisterListener(this.mOrientationSensorEventListener);
                this.mSensorRegister &= -5;
                this.mIsLyingForGradienter = false;
                this.mIsLyingForLie = false;
                changeCapturePosture(0);
            }
            if (isContains(i, 8) && isContains(this.mSensorRegister, 8)) {
                this.mSensorManager.unregisterListener(this.mAccelerometerSensorEventListenerImpl);
                this.mSensorRegister &= -9;
            }
            if (isContains(i, 16) && isContains(this.mSensorRegister, 16)) {
                this.mSensorManager.unregisterListener(this.mRoatationSensorListener);
                this.mSensorRegister &= -17;
            }
            if (isContains(i, 32) && isContains(this.mSensorRegister, 32)) {
                this.mSensorManager.unregisterListener(this.mGravitySensorListener);
                this.mSensorRegister &= -33;
            }
            if (isContains(i, 64) && isContains(this.mSensorRegister, 64)) {
                this.mSensorManager.unregisterListener(this.mGameRotationSensorListener);
                this.mSensorRegister &= -65;
            }
        }
    }
}
