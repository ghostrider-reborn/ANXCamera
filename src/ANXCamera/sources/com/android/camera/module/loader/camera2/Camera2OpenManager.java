package com.android.camera.module.loader.camera2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.support.annotation.NonNull;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.constant.ExceptionConstant;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.MiCamera2;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@SuppressLint({"MissingPermission"})
@TargetApi(21)
public class Camera2OpenManager {
    private static final long CAMERA_OPEN_OR_CLOSE_TIMEOUT = 5000;
    private static final int MANAGER_MSG_CLOSE_CAMERA_AFTER_CAPTURE = 5;
    private static final int MANAGER_MSG_CLOSE_FINISH = 3;
    private static final int MANAGER_MSG_FORCE_CLOSE_CAMERA = 2;
    private static final int MANAGER_MSG_OPEN_FINISH = 4;
    private static final int MANAGER_MSG_REQUEST_CAMERA = 1;
    private static final int MANAGER_STATE_IDLE = 1;
    private static final int MANAGER_STATE_WAITING_CLOSE = 2;
    private static final int MANAGER_STATE_WAITING_OPEN = 3;
    /* access modifiers changed from: private */
    public static final long POP_CAMERA_DELAY_CREATE_SESSION = SystemProperties.getLong("delay_create_session", 450);
    public static final int REASON_CLOSE = 0;
    public static final int REASON_DISCONNECTED = 1;
    /* access modifiers changed from: private */
    public static final String TAG = "Camera2OpenManager";
    private static Camera2OpenManager sInstance;
    /* access modifiers changed from: private */
    public Camera2Proxy mCamera2Device;
    /* access modifiers changed from: private */
    public Handler mCameraHandler;
    private Handler mCameraMainThreadHandler;
    private CameraManager mCameraManager;
    private final StateCallback mCameraOpenCallback = new StateCallback() {
        public void onClosed(@NonNull CameraDevice cameraDevice) {
            String access$300 = Camera2OpenManager.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("CameraOpenCallback: closed ");
            sb.append(cameraDevice.getId());
            Log.d(access$300, sb.toString());
            if (DataRepository.dataItemFeature().eb() && Integer.valueOf(cameraDevice.getId()).intValue() == 1 && Camera2OpenManager.this.mPendingCameraId.get() == -1) {
                CompatibilityUtils.takebackMotor();
            }
            Camera2OpenManager.this.mCameraHandler.sendEmptyMessage(3);
        }

        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            String access$300 = Camera2OpenManager.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("CameraOpenCallback: onDisconnected ");
            sb.append(cameraDevice.getId());
            Log.d(access$300, sb.toString());
            Camera2OpenManager.this.mCameraHandler.removeMessages(2);
            Camera2OpenManager.this.mCameraHandler.removeMessages(5);
            if (Camera2OpenManager.this.mCamera2Device != null) {
                Camera2OpenManager.this.mPendingCameraId.set(-1);
                Message obtainMessage = Camera2OpenManager.this.mCameraHandler.obtainMessage();
                obtainMessage.what = 2;
                obtainMessage.arg1 = 1;
                Camera2OpenManager.this.mCameraHandler.sendMessageAtFrontOfQueue(obtainMessage);
            }
        }

        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            StringBuilder sb = new StringBuilder();
            sb.append("onError: cameraId=");
            sb.append(cameraDevice.getId());
            sb.append(" error=");
            sb.append(i);
            String sb2 = sb.toString();
            String access$300 = Camera2OpenManager.TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("CameraOpenCallback: ");
            sb3.append(sb2);
            Log.e(access$300, sb3.toString());
            Camera2OpenManager.this.onCameraOpenFailed(ExceptionConstant.transFromCamera2Error(i), sb2);
        }

        public void onOpened(@NonNull CameraDevice cameraDevice) {
            int parseInt = Integer.parseInt(cameraDevice.getId());
            CameraCapabilities capabilities = Camera2DataContainer.getInstance().getCapabilities(parseInt);
            StringBuilder sb = new StringBuilder();
            sb.append("CameraOpenCallback: camera ");
            sb.append(parseInt);
            sb.append(" was opened successfully");
            String sb2 = sb.toString();
            if (capabilities != null) {
                Camera2OpenManager camera2OpenManager = Camera2OpenManager.this;
                MiCamera2 miCamera2 = new MiCamera2(cameraDevice, parseInt, capabilities, camera2OpenManager.getCameraHandler(), Camera2OpenManager.this.getCameraPreviewHandler(), Camera2OpenManager.this.getCameraMainThreadHandler());
                camera2OpenManager.mCamera2Device = miCamera2;
                Camera2DataContainer.getInstance().setCurrentOpenedCameraId(parseInt);
                if (DataRepository.dataItemFeature().eb()) {
                    long currentTimeMillis = System.currentTimeMillis() - Camera2OpenManager.this.mPopCameraTimeStamp.get();
                    String access$300 = Camera2OpenManager.TAG;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(sb2);
                    sb3.append(", current delay = ");
                    sb3.append(currentTimeMillis);
                    sb3.append(", should delay = ");
                    sb3.append(Camera2OpenManager.POP_CAMERA_DELAY_CREATE_SESSION);
                    Log.d(access$300, sb3.toString());
                    if (currentTimeMillis <= Camera2OpenManager.POP_CAMERA_DELAY_CREATE_SESSION) {
                        Log.d(Camera2OpenManager.TAG, "onOpened: sleep start...");
                        try {
                            Thread.sleep(Camera2OpenManager.POP_CAMERA_DELAY_CREATE_SESSION - currentTimeMillis);
                        } catch (InterruptedException unused) {
                        }
                        Log.d(Camera2OpenManager.TAG, "onOpened: sleep end...");
                    }
                    Camera2OpenManager.this.mPopCameraTimeStamp.set(-1);
                }
                Camera2OpenManager.this.mCameraHandler.sendEmptyMessage(4);
                return;
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append(sb2);
            sb4.append(", but corresponding CameraCapabilities is null");
            String sb5 = sb4.toString();
            Log.e(Camera2OpenManager.TAG, sb5);
            Camera2OpenManager.this.onCameraOpenFailed(231, sb5);
        }
    };
    private Handler mCameraPreviewHandler;
    private ObservableEmitter<Camera2Result> mCameraResultEmitter;
    private ConnectableObservable<Camera2Result> mCameraResultObservable;
    private AtomicInteger mCurrentModule = new AtomicInteger(160);
    private int mCurrentState = 1;
    private final Object mEmitterLock = new Object();
    /* access modifiers changed from: private */
    public AtomicInteger mPendingCameraId = new AtomicInteger(-1);
    /* access modifiers changed from: private */
    public AtomicLong mPopCameraTimeStamp = new AtomicLong(-1);

    @interface ManagerState {
    }

    private Camera2OpenManager() {
        initData();
    }

    private void abandonOpenObservableIfExists() {
        Log.d(TAG, "abandonOpenObservableIfExists: E");
        synchronized (this.mEmitterLock) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("abandonOpenObservableIfExists: start mCameraResultEmitter = ");
            sb.append(this.mCameraResultEmitter);
            Log.d(str, sb.toString());
            if (this.mCameraResultEmitter != null && !this.mCameraResultEmitter.isDisposed()) {
                this.mCameraResultEmitter.onNext(Camera2Result.create(3).setCameraError(225));
                this.mCameraResultEmitter.onComplete();
                this.mCameraResultEmitter = null;
            }
        }
        Log.d(TAG, "abandonOpenObservableIfExists: X");
    }

    private boolean attachInObservable(Observer<Camera2Result> observer) {
        boolean z;
        synchronized (this.mEmitterLock) {
            if (this.mCameraResultEmitter != null) {
                if (!this.mCameraResultEmitter.isDisposed()) {
                    this.mCameraResultObservable.subscribe(observer);
                    z = true;
                }
            }
            this.mCameraResultObservable = Observable.create(new a(this)).timeout(getCameraOpTimeout(), TimeUnit.MILLISECONDS).onErrorResumeNext((Function<? super Throwable, ? extends ObservableSource<? extends T>>) new b<Object,Object>(this)).observeOn(GlobalConstant.sCameraSetupScheduler).publish();
            this.mCameraResultObservable.subscribe(observer);
            this.mCameraResultObservable.connect();
            z = false;
        }
        return z;
    }

    private long getCameraOpTimeout() {
        return (this.mCamera2Device == null || !ModuleManager.isManualModule()) ? CAMERA_OPEN_OR_CLOSE_TIMEOUT : CAMERA_OPEN_OR_CLOSE_TIMEOUT + (CameraSettings.getExposureTime() / 1000000);
    }

    public static synchronized Camera2OpenManager getInstance() {
        Camera2OpenManager camera2OpenManager;
        synchronized (Camera2OpenManager.class) {
            if (sInstance == null) {
                sInstance = new Camera2OpenManager();
            }
            camera2OpenManager = sInstance;
        }
        return camera2OpenManager;
    }

    @ManagerState
    private int getManagerState() {
        return this.mCurrentState;
    }

    private void initData() {
        HandlerThread handlerThread = new HandlerThread("Camera Handler Thread");
        handlerThread.start();
        this.mCameraHandler = new Handler(handlerThread.getLooper()) {
            public void handleMessage(Message message) {
                Camera2OpenManager.this.onMessage(message);
            }
        };
        HandlerThread handlerThread2 = new HandlerThread("CameraPreviewHandlerThread");
        handlerThread2.start();
        this.mCameraPreviewHandler = new Handler(handlerThread2.getLooper());
        this.mCameraMainThreadHandler = new Handler(Looper.getMainLooper());
        this.mCameraManager = (CameraManager) CameraAppImpl.getAndroidContext().getSystemService("camera");
        Camera2DataContainer.getInstance(this.mCameraManager);
    }

    /* access modifiers changed from: private */
    public void onCameraOpenFailed(int i, String str) {
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onCameraOpenFailed: ");
        sb.append(i);
        sb.append(" msg:");
        sb.append(str);
        Log.e(str2, sb.toString());
        setManagerState(1);
        synchronized (this.mEmitterLock) {
            if (this.mCameraResultEmitter != null) {
                this.mCameraResultEmitter.onNext(Camera2Result.create(3).setCameraError(i));
                this.mCameraResultEmitter.onComplete();
            }
        }
    }

    private void onCameraOpenSuccess() {
        setManagerState(1);
        Log.d(TAG, "onCameraOpenSuccess: E");
        synchronized (this.mEmitterLock) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onCameraOpenSuccess: mCameraResultEmitter = ");
            sb.append(this.mCameraResultEmitter);
            Log.d(str, sb.toString());
            if (this.mCameraResultEmitter != null) {
                this.mCameraResultEmitter.onNext(Camera2Result.create(2));
                this.mCameraResultEmitter.onComplete();
            }
        }
        Log.d(TAG, "onCameraOpenSuccess: X");
    }

    /* access modifiers changed from: private */
    public void onMessage(Message message) {
        int i = message.what;
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    Log.e(TAG, "close finish");
                    if (DataRepository.dataItemFeature().eb() && this.mPopCameraTimeStamp.get() > 0 && this.mPendingCameraId.get() == -1) {
                        CompatibilityUtils.takebackMotor();
                    }
                    setManagerState(1);
                    this.mCameraHandler.sendEmptyMessage(1);
                    return;
                } else if (i == 4) {
                    Log.e(TAG, "open finish");
                    setManagerState(1);
                    this.mCameraHandler.sendEmptyMessage(1);
                    return;
                } else if (i != 5) {
                    return;
                }
            }
            if (this.mCamera2Device == null) {
                this.mCameraHandler.sendEmptyMessage(1);
            } else if (getManagerState() != 1) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("not idle, break on msg.what ");
                sb.append(message.what);
                sb.append(", mCurrentState ");
                sb.append(this.mCurrentState);
                Log.w(str, sb.toString());
            } else {
                setManagerState(2);
                int i2 = message.arg1;
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("force close start reason ");
                sb2.append(i2);
                Log.e(str2, sb2.toString());
                this.mCamera2Device.releasePreview(i2);
                this.mCamera2Device.resetConfigs();
                this.mCamera2Device.close();
                this.mCamera2Device = null;
            }
        } else {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.setCaptureBusyCallback(null);
            }
            String str3 = "close start";
            if (this.mPendingCameraId.get() >= 0) {
                Camera2Proxy camera2Proxy2 = this.mCamera2Device;
                if (camera2Proxy2 != null && camera2Proxy2.getId() == this.mPendingCameraId.get()) {
                    this.mCameraHandler.removeMessages(5);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Camera ");
                    sb3.append(this.mCamera2Device.getId());
                    sb3.append(" was opened successfully");
                    String sb4 = sb3.toString();
                    if (this.mCamera2Device.getCapabilities() == null) {
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(sb4);
                        sb5.append(", but corresponding CameraCapabilities is null");
                        String sb6 = sb5.toString();
                        Log.d(TAG, sb6);
                        onCameraOpenFailed(231, sb6);
                        return;
                    }
                    Log.d(TAG, sb4);
                    if (!DataRepository.dataItemFeature().bb()) {
                        this.mCamera2Device.cancelSession();
                    }
                    onCameraOpenSuccess();
                } else if (this.mCamera2Device != null) {
                    setManagerState(2);
                    Log.d(TAG, str3);
                    this.mCamera2Device.close();
                    this.mCamera2Device = null;
                } else if (getManagerState() == 1) {
                    try {
                        setManagerState(3);
                        Log.d(TAG, "open start");
                        this.mCameraManager.openCamera(String.valueOf(this.mPendingCameraId), this.mCameraOpenCallback, this.mCameraHandler);
                    } catch (CameraAccessException | IllegalArgumentException | SecurityException e2) {
                        e2.printStackTrace();
                        onCameraOpenFailed(230, e2.getClass().getSimpleName());
                        String str4 = TAG;
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append("openCamera: failed to open camera ");
                        sb7.append(this.mPendingCameraId.get());
                        Log.e(str4, sb7.toString(), e2);
                    }
                }
            } else if (this.mCamera2Device != null) {
                setManagerState(2);
                Log.e(TAG, str3);
                this.mCamera2Device.close();
                this.mCamera2Device = null;
            }
        }
    }

    public static void preload() {
        Log.i(TAG, "preload");
    }

    private void removeAllAppMessages() {
        this.mCameraHandler.removeMessages(1);
        this.mCameraHandler.removeMessages(2);
        this.mCameraHandler.removeMessages(3);
        this.mCameraHandler.removeMessages(4);
        this.mCameraHandler.removeMessages(5);
    }

    private void setManagerState(@ManagerState int i) {
        this.mCurrentState = i;
    }

    public /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        this.mCameraResultEmitter = observableEmitter;
    }

    public /* synthetic */ ObservableSource c(Throwable th) throws Exception {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Exception occurs in camera open or close: ");
        sb.append(th);
        Log.d(str, sb.toString());
        if (!this.mCameraHandler.getLooper().getQueue().isPolling()) {
            Log.d(TAG, "CameraHandlerThread is being stuck...");
        }
        return Observable.just(Camera2Result.create(3).setCameraError(236));
    }

    public /* synthetic */ void d(boolean z, boolean z2) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("release onCaptureCompleted: success:");
        sb.append(z2);
        sb.append(" immediate:");
        sb.append(z);
        Log.d(str, sb.toString());
        if (z) {
            Handler handler = this.mCameraHandler;
            handler.sendMessage(handler.obtainMessage(5));
        }
    }

    public Handler getCameraHandler() {
        return this.mCameraHandler;
    }

    public Handler getCameraMainThreadHandler() {
        return this.mCameraMainThreadHandler;
    }

    public Handler getCameraPreviewHandler() {
        return this.mCameraPreviewHandler;
    }

    public Camera2Proxy getCurrentCamera2Device() {
        return this.mCamera2Device;
    }

    public int getPendingCameraId() {
        return this.mPendingCameraId.get();
    }

    public void openCamera(int i, int i2, Observer<Camera2Result> observer, boolean z) {
        int actualOpenCameraId = Camera2DataContainer.getInstance().getActualOpenCameraId(i, i2);
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("openCamera: pendingOpenId = ");
        sb.append(actualOpenCameraId);
        sb.append(", mPendingCameraId = ");
        sb.append(this.mPendingCameraId.get());
        sb.append(", currentMode = ");
        sb.append(i2);
        sb.append(", mCurrentModule = ");
        sb.append(this.mCurrentModule.get());
        sb.append(", forceClose = ");
        sb.append(z);
        Log.d(str, sb.toString());
        if (DataRepository.dataItemFeature().eb()) {
            if (i == 1 && this.mPendingCameraId.get() != actualOpenCameraId) {
                boolean popupMotor = CompatibilityUtils.popupMotor();
                this.mPopCameraTimeStamp.set(System.currentTimeMillis());
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("openCamera: popupMotor = ");
                sb2.append(popupMotor);
                Log.d(str2, sb2.toString());
            } else if (i == 0) {
                this.mPopCameraTimeStamp.set(-1);
            }
        }
        if (!(this.mPendingCameraId.get() == actualOpenCameraId && this.mCurrentModule.get() == i2)) {
            removeAllAppMessages();
            this.mPendingCameraId.set(actualOpenCameraId);
            this.mCurrentModule.set(i2);
            abandonOpenObservableIfExists();
        }
        attachInObservable(observer);
        if (z) {
            this.mCameraHandler.sendEmptyMessage(2);
        } else {
            this.mCameraHandler.sendEmptyMessage(1);
        }
    }

    public void release(boolean z) {
        abandonOpenObservableIfExists();
        this.mPendingCameraId.set(-1);
        this.mCameraHandler.removeMessages(1);
        this.mCurrentModule.set(160);
        this.mCamera2Device.setCaptureBusyCallback(new c(this, z));
    }
}
