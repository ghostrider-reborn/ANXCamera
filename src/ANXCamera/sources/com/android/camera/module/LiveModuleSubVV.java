package com.android.camera.module;

import android.app.AlertDialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.SensorEvent;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureResult;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Range;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import com.android.camera.BasePreferenceActivity;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraPreferenceActivity;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.LocationManager;
import com.android.camera.R;
import com.android.camera.RotateDialogController;
import com.android.camera.SensorStateManager;
import com.android.camera.SurfaceTextureScreenNail;
import com.android.camera.Util;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.observeable.RxData;
import com.android.camera.data.observeable.VMProcessing;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FaceAnalyzeInfo;
import com.android.camera.fragment.CtaNoticeFragment;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.fragment.mimoji.FragmentMimoji;
import com.android.camera.fragment.vv.VVItem;
import com.android.camera.log.Log;
import com.android.camera.module.loader.FunctionParseAsdFace;
import com.android.camera.module.loader.FunctionParseAsdScene;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.FocusManager2;
import com.android.camera.module.loader.camera2.FocusTask;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtil;
import com.android.camera.storage.Storage;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CameraHardwareFace;
import com.mi.config.b;
import com.xiaomi.camera.core.ParallelTaskData;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LiveModuleSubVV extends BaseModule implements LifecycleOwner, FocusManager2.Listener, ModeProtocol.CameraAction, Camera2Proxy.CameraPreviewCallback, Camera2Proxy.FaceDetectionCallback, Camera2Proxy.FocusCallback, Camera2Proxy.PictureCallback {
    private static final int BEAUTY_SWITCH = 8;
    private static final int FILTER_SWITCH = 2;
    private static final int FRAME_RATE = 30;
    private static final int STICKER_SWITCH = 4;
    /* access modifiers changed from: private */
    public static final String TAG = LiveModuleSubVV.class.getSimpleName();
    private boolean m3ALocked;
    private String mBaseFileName;
    protected BeautyValues mBeautyValues;
    private String mCaptureWaterMarkStr;
    private CtaNoticeFragment mCtaNoticeFragment;
    private AlertDialog mDialog;
    protected boolean mFaceDetectionEnabled;
    protected boolean mFaceDetectionStarted;
    /* access modifiers changed from: private */
    public FocusManager2 mFocusManager;
    private boolean mIsPreviewing = false;
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    private ModeProtocol.LiveConfigVV mLiveConfigChanges;
    private int mMessageId;
    private Disposable mMetaDataDisposable;
    /* access modifiers changed from: private */
    public FlowableEmitter<CaptureResult> mMetaDataFlowableEmitter;
    private int mOldOriginVolumeStream;
    /* access modifiers changed from: private */
    public long mOnResumeTime;
    protected final PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        public void onCallStateChanged(int i, String str) {
            if (i == 2 && LiveModuleSubVV.this.isRecording()) {
                Log.w(LiveModuleSubVV.TAG, "CALL_STATE_OFFHOOK");
                LiveModuleSubVV.this.onStop();
            }
            super.onCallStateChanged(i, str);
        }
    };
    private int mQuality = 5;
    private boolean mSaved;
    protected SensorStateManager.SensorStateListener mSensorStateListener = new SensorStateManager.SensorStateListener() {
        public boolean isWorking() {
            return LiveModuleSubVV.this.isAlive() && LiveModuleSubVV.this.getCameraState() != 0;
        }

        public void notifyDevicePostureChanged() {
            LiveModuleSubVV.this.mActivity.getEdgeShutterView().onDevicePostureChanged();
        }

        public void onDeviceBecomeStable() {
            Log.v(LiveModuleSubVV.TAG, "onDeviceBecomeStable");
        }

        public void onDeviceBeginMoving() {
        }

        public void onDeviceKeepMoving(double d) {
            if (!LiveModuleSubVV.this.mMainProtocol.isEvAdjusted(true) && !LiveModuleSubVV.this.mPaused && Util.isTimeout(System.currentTimeMillis(), LiveModuleSubVV.this.mTouchFocusStartingTime, 3000) && !LiveModuleSubVV.this.is3ALocked() && LiveModuleSubVV.this.mFocusManager != null && LiveModuleSubVV.this.mFocusManager.isNeedCancelAutoFocus() && !LiveModuleSubVV.this.isRecording()) {
                LiveModuleSubVV.this.mFocusManager.onDeviceKeepMoving(d);
            }
        }

        public void onDeviceKeepStable() {
        }

        public void onDeviceLieChanged(boolean z) {
        }

        public void onDeviceOrientationChanged(float f, boolean z) {
        }

        public void onDeviceRotationChanged(float[] fArr) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
        }
    };
    protected boolean mShowFace = false;
    protected TelephonyManager mTelephonyManager;
    private int mTitleId;
    /* access modifiers changed from: private */
    public long mTouchFocusStartingTime;
    private VMProcessing mVmProcessing;

    private class LiveAsdConsumer implements Consumer<Integer> {
        private LiveAsdConsumer() {
        }

        public void accept(Integer num) throws Exception {
            LiveModuleSubVV.this.consumeAsdSceneResult(num.intValue());
        }
    }

    private class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 2) {
                LiveModuleSubVV.this.getWindow().clearFlags(128);
            } else if (i == 4) {
                LiveModuleSubVV.this.checkActivityOrientation();
                if (SystemClock.uptimeMillis() - LiveModuleSubVV.this.mOnResumeTime < 5000) {
                    LiveModuleSubVV.this.mHandler.sendEmptyMessageDelayed(4, 100);
                }
            } else if (i == 9) {
                LiveModuleSubVV.this.mMainProtocol.initializeFocusView(LiveModuleSubVV.this);
            } else if (i == 17) {
                LiveModuleSubVV.this.mHandler.removeMessages(17);
                LiveModuleSubVV.this.mHandler.removeMessages(2);
                LiveModuleSubVV.this.getWindow().addFlags(128);
                LiveModuleSubVV.this.mHandler.sendEmptyMessageDelayed(2, (long) LiveModuleSubVV.this.getScreenDelay());
            } else if (i != 31) {
                boolean z = true;
                if (i == 35) {
                    LiveModuleSubVV liveModuleSubVV = LiveModuleSubVV.this;
                    boolean z2 = message.arg1 > 0;
                    if (message.arg2 <= 0) {
                        z = false;
                    }
                    liveModuleSubVV.handleUpdateFaceView(z2, z);
                } else if (i == 51 && !LiveModuleSubVV.this.mActivity.isActivityPaused()) {
                    LiveModuleSubVV.this.mOpenCameraFail = true;
                    LiveModuleSubVV.this.onCameraException();
                }
            } else {
                LiveModuleSubVV.this.setOrientationParameter();
            }
        }
    }

    /* access modifiers changed from: private */
    public void consumeAsdSceneResult(int i) {
    }

    private String createName(long j, int i) {
        if (i > 0) {
            return this.mBaseFileName;
        }
        this.mBaseFileName = new SimpleDateFormat(getString(R.string.video_file_name_format), Locale.ENGLISH).format(new Date(j));
        return this.mBaseFileName;
    }

    private void doLaterReleaseIfNeed() {
        if (this.mActivity != null && this.mActivity.isActivityPaused()) {
            this.mActivity.pause();
            this.mActivity.releaseAll(true, true);
        }
    }

    private ContentValues genContentValues(int i, int i2, boolean z) {
        String str;
        String createName = createName(System.currentTimeMillis(), i2);
        if (i2 > 0) {
            String format = String.format(Locale.ENGLISH, "_%d", new Object[]{Integer.valueOf(i2)});
            createName = createName + format;
        }
        String str2 = createName + Util.convertOutputFormatToFileExt(i);
        String convertOutputFormatToMimeType = Util.convertOutputFormatToMimeType(i);
        if (z) {
            str = Storage.CAMERA_TEMP_DIRECTORY + '/' + str2;
            Util.createFile(new File(Storage.CAMERA_TEMP_DIRECTORY + File.separator + Storage.AVOID_SCAN_FILE_NAME));
        } else {
            str = Storage.DIRECTORY + '/' + str2;
        }
        Log.v(TAG, "genContentValues: path=" + str);
        ContentValues contentValues = new ContentValues(8);
        contentValues.put("title", createName);
        contentValues.put("_display_name", str2);
        contentValues.put("mime_type", convertOutputFormatToMimeType);
        contentValues.put("_data", str);
        contentValues.put("resolution", Integer.toString(this.mPreviewSize.width) + "x" + Integer.toString(this.mPreviewSize.height));
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        if (!(currentLocation == null || (currentLocation.getLatitude() == 0.0d && currentLocation.getLongitude() == 0.0d))) {
            contentValues.put("latitude", Double.valueOf(currentLocation.getLatitude()));
            contentValues.put("longitude", Double.valueOf(currentLocation.getLongitude()));
        }
        return contentValues;
    }

    /* access modifiers changed from: private */
    public void handleUpdateFaceView(boolean z, boolean z2) {
        boolean isFrontCamera = isFrontCamera();
        if (!z) {
            this.mMainProtocol.updateFaceView(z, z2, isFrontCamera, false, -1);
        } else if (this.mFaceDetectionStarted && 1 != this.mCamera2Device.getFocusMode()) {
            this.mMainProtocol.updateFaceView(z, true, isFrontCamera, true, this.mCameraDisplayOrientation);
        }
    }

    private int initLiveConfig() {
        this.mLiveConfigChanges = (ModeProtocol.LiveConfigVV) ModeCoordinatorImpl.getInstance().getAttachProtocol(228);
        if (this.mLiveConfigChanges == null) {
            getActivity().getImplFactory().initModulePersistent(getActivity(), 228);
            this.mLiveConfigChanges = (ModeProtocol.LiveConfigVV) ModeCoordinatorImpl.getInstance().getAttachProtocol(228);
            this.mLiveConfigChanges.prepare();
        }
        this.mLiveConfigChanges.initResource();
        return this.mLiveConfigChanges.getAuthResult();
    }

    private void initMetaParser() {
        this.mMetaDataDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
            public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                FlowableEmitter unused = LiveModuleSubVV.this.mMetaDataFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).map(new FunctionParseAsdFace(this, isFrontCamera())).map(new FunctionParseAsdScene(this)).subscribe(new LiveAsdConsumer());
    }

    private void initializeFocusManager() {
        this.mFocusManager = new FocusManager2(this.mCameraCapabilities, this, isFrontCamera(), this.mActivity.getMainLooper());
        Rect renderRect = this.mActivity.getCameraScreenNail() != null ? this.mActivity.getCameraScreenNail().getRenderRect() : null;
        if (renderRect == null || renderRect.width() <= 0) {
            this.mFocusManager.setRenderSize(Util.sWindowWidth, Util.sWindowHeight);
            this.mFocusManager.setPreviewSize(Util.sWindowWidth, Util.sWindowHeight);
            return;
        }
        this.mFocusManager.setRenderSize(this.mActivity.getCameraScreenNail().getRenderWidth(), this.mActivity.getCameraScreenNail().getRenderHeight());
        this.mFocusManager.setPreviewSize(renderRect.width(), renderRect.height());
    }

    private boolean isEisOn() {
        return isBackCamera() && CameraSettings.isMovieSolidOn() && this.mCameraCapabilities.isEISPreviewSupported();
    }

    private boolean isSaving() {
        return this.mVmProcessing != null && this.mVmProcessing.getCurrentState() == 7;
    }

    static /* synthetic */ void lambda$onDestroy$1() {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol != null) {
            mainContentProtocol.setCenterHint(8, (String) null, (String) null, 0);
        }
    }

    /* access modifiers changed from: private */
    public void onProcessingSateChanged(int i) {
        if (i == 7) {
            pausePreview();
        }
    }

    private void previewWhenSessionSuccess() {
        setCameraState(1);
        updatePreferenceInWorkThread(UpdateConstant.FUN_TYPES_ON_PREVIEW_SUCCESS);
    }

    private void setOrientation(int i, int i2) {
        if (i != -1) {
            this.mOrientation = i;
            checkActivityOrientation();
            if (this.mOrientationCompensation != i2) {
                this.mOrientationCompensation = i2;
                setOrientationParameter();
            }
        }
    }

    /* access modifiers changed from: private */
    public void setOrientationParameter() {
        if (!isDeparted() && this.mCamera2Device != null && this.mOrientation != -1) {
            if (!isFrameAvailable() || getCameraState() != 1) {
                GlobalConstant.sCameraSetupScheduler.scheduleDirect(new Runnable() {
                    public final void run() {
                        LiveModuleSubVV.this.mCamera2Device.setDeviceOrientation(LiveModuleSubVV.this.mOrientation);
                    }
                });
                return;
            }
            updatePreferenceInWorkThread(35);
        }
    }

    private boolean shouldApplyUltraWideLDC() {
        if (CameraSettings.shouldUltraWideVideoLDCBeVisibleInMode(this.mModuleIndex) && this.mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return CameraSettings.isUltraWideVideoLDCEnabled();
        }
        return false;
    }

    private void showAuthError() {
        this.mHandler.post(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(LiveModuleSubVV.this.mActivity);
                builder.setTitle(R.string.live_error_title);
                builder.setMessage(R.string.live_error_message);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.live_error_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LiveModuleSubVV.this.mActivity.startActivity(new Intent("android.settings.DATE_SETTINGS"));
                    }
                });
                builder.setNegativeButton(R.string.snap_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });
    }

    private void showPreview() {
        this.mSaved = false;
        ((ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230)).onCombinePrepare(genContentValues(2, 0, false));
        this.mIsPreviewing = true;
    }

    @MainThread
    private void startVideoRecording() {
        keepScreenOn();
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        recordState.onPrepare();
        if (this.mLiveConfigChanges != null) {
            this.mLiveConfigChanges.startRecordingNewFragment();
            this.mOldOriginVolumeStream = getActivity().getVolumeControlStream();
            getActivity().setVolumeControlStream(3);
        }
        recordState.onStart();
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
            if (isUltraWideBackCamera()) {
                setMinZoomRatio(0.6f);
                setMaxZoomRatio(0.6f * this.mCameraCapabilities.getMaxZoomRatio());
            } else {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
            }
        }
        this.mTelephonyManager.listen(this.mPhoneStateListener, 32);
        Log.v(TAG, "listen call state");
        trackGeneralInfo(1, false);
    }

    private void trackLiveRecordingParams() {
    }

    private void trackLiveVideoParams() {
        this.mLiveConfigChanges.trackVideoParams();
    }

    private void updateBeauty() {
    }

    private void updateDeviceOrientation() {
        this.mCamera2Device.setDeviceOrientation(this.mOrientation);
    }

    private void updateFaceView(boolean z, boolean z2) {
        if (this.mHandler.hasMessages(35)) {
            this.mHandler.removeMessages(35);
        }
        this.mHandler.obtainMessage(35, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
    }

    private void updateFilter() {
        int shaderEffect = CameraSettings.getShaderEffect();
        String str = TAG;
        Log.v(str, "updateFilter: 0x" + Integer.toHexString(shaderEffect));
        EffectController.getInstance().setEffect(shaderEffect);
    }

    private void updateFocusMode() {
        setFocusMode(this.mFocusManager.setFocusMode(CameraSettings.getFocusMode()));
    }

    private void updateFpsRange() {
        this.mCamera2Device.setFpsRange(new Range(30, 30));
    }

    private void updateLiveRelated() {
        if (this.mAlgorithmPreviewSize != null) {
            this.mCamera2Device.startPreviewCallback(this.mLiveConfigChanges);
        }
    }

    private void updatePictureAndPreviewSize() {
        List<CameraSize> supportedOutputSize = this.mCameraCapabilities.getSupportedOutputSize(SurfaceTexture.class);
        float previewRatio = this.mLiveConfigChanges.getPreviewRatio();
        Util.getOptimalPreviewSize(false, this.mBogusCameraId, supportedOutputSize, (double) previewRatio);
        this.mPreviewSize = new CameraSize(3840, 2160);
        String str = TAG;
        Log.d(str, "previewSize: " + this.mPreviewSize.toString());
        this.mPictureSize = null;
        CameraSize optimalVideoSnapshotPictureSize = Util.getOptimalVideoSnapshotPictureSize(supportedOutputSize, (double) CameraSettings.getPreviewAspectRatio(previewRatio), Util.sWindowHeight, Util.sWindowWidth);
        String str2 = TAG;
        Log.d(str2, "displaySize: " + optimalVideoSnapshotPictureSize.toString());
        if (this.mAlgorithmPreviewSize != null) {
            String str3 = TAG;
            Log.d(str3, "AlgorithmPreviewSize: " + this.mAlgorithmPreviewSize.toString());
            this.mCamera2Device.setAlgorithmPreviewSize(this.mAlgorithmPreviewSize);
            this.mCamera2Device.setAlgorithmPreviewFormat(35);
            this.mCamera2Device.setPreviewMaxImages(1);
        }
        updateCameraScreenNailSize(optimalVideoSnapshotPictureSize.width, optimalVideoSnapshotPictureSize.height);
    }

    private void updateUltraWideLDC() {
        this.mCamera2Device.setUltraWideLDC(shouldApplyUltraWideLDC());
    }

    private void updateVideoStabilization() {
        if (isDeviceAlive()) {
            if (isEisOn()) {
                Log.d(TAG, "videoStabilization: EIS");
                this.mCamera2Device.setEnableEIS(true);
                this.mCamera2Device.setEnableOIS(false);
                if (!this.mCameraCapabilities.isEISPreviewSupported()) {
                    this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(true);
                    return;
                }
                return;
            }
            Log.d(TAG, "videoStabilization: OIS");
            this.mCamera2Device.setEnableEIS(false);
            this.mCamera2Device.setEnableOIS(true);
            this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
        }
    }

    public void cancelFocus(boolean z) {
        if (isAlive() && isFrameAvailable() && getCameraState() != 0) {
            if (this.mCamera2Device != null) {
                if (z) {
                    this.mCamera2Device.setFocusMode(4);
                }
                this.mCamera2Device.cancelFocus(this.mModuleIndex);
            }
            if (getCameraState() != 3) {
                setCameraState(1);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkCallingState() {
        if (Storage.isLowStorageAtLastPoint()) {
            this.mActivity.getScreenHint().updateHint();
            return false;
        } else if (2 != this.mTelephonyManager.getCallState()) {
            return true;
        } else {
            showConfirmMessage(R.string.confirm_recording_fail_title, R.string.confirm_recording_fail_calling_alert);
            return false;
        }
    }

    public void checkDisplayOrientation() {
        if (isCreated()) {
            super.checkDisplayOrientation();
            if (this.mMainProtocol != null) {
                this.mMainProtocol.setCameraDisplayOrientation(this.mCameraDisplayOrientation);
            }
            if (this.mFocusManager != null) {
                this.mFocusManager.setDisplayOrientation(this.mCameraDisplayOrientation);
            }
        }
    }

    public void closeCamera() {
        if (this.mMetaDataFlowableEmitter != null) {
            this.mMetaDataFlowableEmitter.onComplete();
        }
        if (this.mMetaDataDisposable != null) {
            this.mMetaDataDisposable.dispose();
        }
        if (this.mActivity != null) {
            this.mActivity.getCameraScreenNail().setExternalFrameProcessor((SurfaceTextureScreenNail.ExternalFrameProcessor) null);
        }
        if (this.mCamera2Device != null) {
            this.mCamera2Device.releaseCameraPreviewCallback((Camera2Proxy.CameraPreviewCallback) null);
            this.mCamera2Device.setFocusCallback((Camera2Proxy.FocusCallback) null);
            this.mCamera2Device.setErrorCallback((Camera2Proxy.CameraErrorCallback) null);
            this.mCamera2Device.stopPreviewCallback(true);
            this.mCamera2Device = null;
        }
        if (this.mFocusManager != null) {
            this.mFocusManager.setAeAwbLock(false);
            this.mFocusManager.destroy();
        }
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    /* access modifiers changed from: protected */
    public void consumePreference(int... iArr) {
        for (int i : iArr) {
            switch (i) {
                case 1:
                    updatePictureAndPreviewSize();
                    break;
                case 2:
                    updateFilter();
                    break;
                case 3:
                    updateFocusArea();
                    break;
                case 5:
                    updateFace();
                    break;
                case 9:
                    updateAntiBanding(CameraSettings.getAntiBanding());
                    break;
                case 10:
                    updateFlashPreference();
                    break;
                case 11:
                case 20:
                case 30:
                case 34:
                case 42:
                case 43:
                case 46:
                case 50:
                    break;
                case 12:
                    setEvValue();
                    break;
                case 13:
                    updateBeauty();
                    break;
                case 14:
                    updateFocusMode();
                    break;
                case 19:
                    updateFpsRange();
                    break;
                case 24:
                    applyZoomRatio();
                    break;
                case 25:
                    focusCenter();
                    break;
                case 29:
                    updateExposureMeteringMode();
                    break;
                case 31:
                    updateVideoStabilization();
                    break;
                case 35:
                    updateDeviceOrientation();
                    break;
                case 47:
                    updateUltraWideLDC();
                    break;
                case 54:
                    updateLiveRelated();
                    break;
                case 55:
                    updateModuleRelated();
                    break;
                case 58:
                    updateBackSoftLightPreference();
                    break;
                default:
                    throw new RuntimeException("no consumer for this updateType: " + i);
            }
        }
    }

    public void doReverse() {
        if (this.mLiveConfigChanges != null && !this.mLiveConfigChanges.isRecording()) {
            this.mLiveConfigChanges.deleteLastFragment();
        }
    }

    /* access modifiers changed from: protected */
    public boolean enableFaceDetection() {
        return DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_FACE_DETECTION, getResources().getBoolean(R.bool.pref_camera_facedetection_default));
    }

    @NonNull
    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    /* access modifiers changed from: protected */
    public int getOperatingMode() {
        return CameraCapabilities.SESSION_OPERATION_MODE_VV;
    }

    public void initializeCapabilities() {
        super.initializeCapabilities();
        this.mContinuousFocusSupported = Util.isSupported(4, this.mCameraCapabilities.getSupportedFocusModes());
        this.mMaxFaceCount = this.mCameraCapabilities.getMaxFaceCount();
    }

    /* access modifiers changed from: protected */
    public boolean is3ALocked() {
        return this.m3ALocked;
    }

    /* access modifiers changed from: protected */
    public boolean isAEAFLockSupported() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isCameraSwitchingDuringZoomingAllowed() {
        return HybridZoomingSystem.IS_3_OR_MORE_SAT && this.mModuleIndex == 174 && !CameraSettings.isMacroModeEnabled(this.mModuleIndex) && isBackCamera() && this.mLiveConfigChanges != null && !this.mLiveConfigChanges.isRecording();
    }

    public boolean isDoingAction() {
        ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
        return isRecording() || getCameraState() == 3 || (fullScreenProtocol != null && fullScreenProtocol.isLiveRecordPreviewShown());
    }

    public boolean isFaceDetectStarted() {
        return this.mFaceDetectionStarted;
    }

    public boolean isNeedMute() {
        return isRecording();
    }

    public boolean isRecording() {
        return this.mLiveConfigChanges != null && this.mLiveConfigChanges.isRecording();
    }

    public boolean isSelectingCapturedResult() {
        return false;
    }

    public boolean isShowCaptureButton() {
        return isSupportFocusShoot();
    }

    public boolean isSupportFocusShoot() {
        if (this.mModuleIndex == 177) {
            return DataRepository.dataItemGlobal().isGlobalSwitchOn("pref_camera_focus_shoot_key");
        }
        return false;
    }

    public boolean isUnInterruptable() {
        return false;
    }

    public boolean isUseFaceInfo() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isZoomEnabled() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean judgeTapableRectByUiStyle() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void keepScreenOn() {
        this.mHandler.removeMessages(17);
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(52);
        getWindow().addFlags(128);
    }

    /* access modifiers changed from: protected */
    public void keepScreenOnAwhile() {
        this.mHandler.sendEmptyMessageDelayed(17, 1000);
    }

    /* access modifiers changed from: protected */
    public void lockAEAF() {
        Log.d(TAG, "lockAEAF");
        if (this.mAeLockSupported) {
            this.mCamera2Device.setAELock(true);
        }
        this.mFocusManager.setAeAwbLock(true);
        this.m3ALocked = true;
    }

    public boolean multiCapture() {
        return false;
    }

    public void notifyError() {
        if (currentIsMainThread() && isRecording()) {
            stopVideoRecording(true, true);
        }
        super.notifyError();
    }

    public void notifyFocusAreaUpdate() {
        updatePreferenceTrampoline(3);
    }

    public boolean onBackPressed() {
        ModeProtocol.LiveVVProcess liveVVProcess = (ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230);
        if (liveVVProcess == null) {
            return super.onBackPressed();
        }
        if (this.mVmProcessing == null || this.mVmProcessing.getCurrentState() == 7) {
            return true;
        }
        liveVVProcess.showExitConfirm();
        return true;
    }

    public void onBeautyParameterChanged() {
        updatePreferenceTrampoline(13);
    }

    /* access modifiers changed from: protected */
    public void onCameraOpened() {
        super.onCameraOpened();
        switch (initLiveConfig()) {
            case 2:
            case 3:
            case 4:
                showAuthError();
                return;
            default:
                initializeFocusManager();
                updatePreferenceTrampoline(UpdateConstant.FUN_TYPES_INIT);
                startPreview();
                if (DataRepository.dataItemGlobal().isFirstShowCTAConCollect() && this.mModuleIndex == 174) {
                    this.mCtaNoticeFragment = CtaNoticeFragment.showCta(getActivity().getFragmentManager(), false, (CtaNoticeFragment.OnCtaNoticeClickListener) null, 1);
                }
                this.mVmProcessing = (VMProcessing) DataRepository.dataItemObservable().get(VMProcessing.class);
                this.mVmProcessing.startObservable(this, new Consumer() {
                    public final void accept(Object obj) {
                        LiveModuleSubVV.this.onProcessingSateChanged(((Integer) ((RxData.DataWrap) obj).get()).intValue());
                    }
                });
                this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
                this.mOnResumeTime = SystemClock.uptimeMillis();
                this.mHandler.sendEmptyMessage(4);
                this.mHandler.sendEmptyMessage(31);
                initMetaParser();
                return;
        }
    }

    public void onCaptureShutter(boolean z) {
    }

    public ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, CameraSize cameraSize, boolean z) {
        return null;
    }

    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        this.mActivity.getSensorStateManager().setSensorStateListener(this.mSensorStateListener);
        this.mHandler = new MainHandler(this.mActivity.getMainLooper());
        this.mTelephonyManager = (TelephonyManager) this.mActivity.getSystemService("phone");
        onCameraOpened();
        this.mHandler.sendEmptyMessage(4);
        this.mHandler.sendEmptyMessage(31);
        this.mActivity.getSensorStateManager().setRotationVectorEnabled(true);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mCtaNoticeFragment != null) {
            this.mCtaNoticeFragment.dismiss();
        }
        this.mHandler.post($$Lambda$LiveModuleSubVV$wCf2nUHzzLv4V4XMIEsfVO42fs.INSTANCE);
        if (this.mHandler != null) {
            this.mHandler.sendEmptyMessage(45);
        }
        this.mActivity.getSensorStateManager().setRotationVectorEnabled(false);
        this.mActivity.getSensorStateManager().setTTARSensorEnabled(false);
    }

    public void onFaceDetected(CameraHardwareFace[] cameraHardwareFaceArr, FaceAnalyzeInfo faceAnalyzeInfo) {
        if (!isCreated() || cameraHardwareFaceArr == null) {
            return;
        }
        if (!b.jc() || cameraHardwareFaceArr.length <= 0 || cameraHardwareFaceArr[0].faceType != 64206) {
            if (this.mMainProtocol.setFaces(1, cameraHardwareFaceArr, getActiveArraySize(), getDeviceBasedZoomRatio())) {
            }
        } else if (this.mObjectTrackingStarted) {
            this.mMainProtocol.setFaces(3, cameraHardwareFaceArr, getActiveArraySize(), getDeviceBasedZoomRatio());
        }
    }

    public void onFocusStateChanged(FocusTask focusTask) {
        if (isCreated() && !isDeparted()) {
            switch (focusTask.getFocusTrigger()) {
                case 1:
                    Log.v(TAG, String.format(Locale.ENGLISH, "FocusTime=%1$dms focused=%2$b", new Object[]{Long.valueOf(focusTask.getElapsedTime()), Boolean.valueOf(focusTask.isSuccess())}));
                    if (!this.mFocusManager.isFocusingSnapOnFinish() && getCameraState() != 3) {
                        setCameraState(1);
                    }
                    this.mFocusManager.onFocusResult(focusTask);
                    this.mActivity.getSensorStateManager().reset();
                    if (focusTask.isSuccess() && this.m3ALocked) {
                        this.mCamera2Device.lockExposure(true);
                        return;
                    }
                    return;
                case 2:
                case 3:
                    String str = null;
                    if (focusTask.isFocusing()) {
                        str = "onAutoFocusMoving start";
                    }
                    if (Util.sIsDumpLog && str != null) {
                        Log.v(TAG, str);
                    }
                    if ((getCameraState() != 3 || focusTask.getFocusTrigger() == 3) && !this.m3ALocked) {
                        this.mFocusManager.onFocusResult(focusTask);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void onHostStopAndNotifyActionStop() {
        if (isRecording()) {
            stopVideoRecording(true, true);
        } else if (isSaving()) {
            return;
        }
        doLaterReleaseIfNeed();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this.mPaused) {
            return true;
        }
        boolean z = false;
        if (!isFrameAvailable()) {
            return false;
        }
        switch (i) {
            case 24:
            case 25:
            case 87:
            case 88:
                if (!this.mIsPreviewing) {
                    if (i == 24 || i == 88) {
                        z = true;
                    }
                    if (handleVolumeKeyEvent(z, true, keyEvent.getRepeatCount(), keyEvent.getDevice().isExternal())) {
                        return true;
                    }
                }
                break;
            case 27:
            case 66:
                if (keyEvent.getRepeatCount() == 0) {
                    if (this.mIsPreviewing) {
                        ((ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196)).startLiveRecordSaving();
                    } else if (!Util.isFingerPrintKeyEvent(keyEvent)) {
                        performKeyClicked(40, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                    } else if (CameraSettings.isFingerprintCaptureEnable()) {
                        performKeyClicked(30, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                    }
                    return true;
                }
                break;
            case 700:
                if (isRecording() && !isPostProcessing()) {
                    if (isBackCamera()) {
                        stopVideoRecording(true, false);
                        break;
                    } else {
                        return false;
                    }
                }
            case 701:
                if (isRecording() && !isPostProcessing()) {
                    if (isFrontCamera()) {
                        stopVideoRecording(true, false);
                        break;
                    } else {
                        return false;
                    }
                }
        }
        return super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4) {
            if (i == 27 || i == 66) {
                return true;
            }
        } else if (((ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)).handleBackStackFromKeyBack()) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    public void onNewUriArrived(final Uri uri, final String str) {
        super.onNewUriArrived(uri, str);
        if (isAlive()) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    ModeProtocol.LiveVVProcess liveVVProcess = (ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230);
                    if (liveVVProcess != null) {
                        ContentValues saveContentValues = liveVVProcess.getSaveContentValues();
                        if (saveContentValues != null) {
                            String asString = saveContentValues.getAsString("title");
                            String access$300 = LiveModuleSubVV.TAG;
                            Log.d(access$300, "newUri: " + str + " | " + asString);
                            if (asString.equals(str)) {
                                liveVVProcess.onLiveSaveToLocalFinished(uri);
                            }
                        }
                    }
                }
            });
        }
    }

    public void onOrientationChanged(int i, int i2, int i3) {
        setOrientation(i, i2);
        if (this.mLiveConfigChanges != null) {
            this.mLiveConfigChanges.onOrientationChanged(i, i2, i3);
        }
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        if (this.mFocusManager != null) {
            this.mFocusManager.removeMessages();
        }
        if (getActivity().startFromKeyguard()) {
            DataRepository.dataItemLive().getMimojiStatusManager().setCurrentMimojiState(FragmentMimoji.CLOSE_STATE);
            DataRepository.dataItemLive().getMimojiStatusManager().setMimojiPannelState(false);
            DataRepository.dataItemLive().getMimojiStatusManager().reset();
        }
        tryRemoveCountDownMessage();
        this.mActivity.getSensorStateManager().reset();
        resetScreenOn();
        closeCamera();
        if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages((Object) null);
        }
    }

    public void onPauseButtonClick() {
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (this.mLiveConfigChanges.isRecording()) {
            this.mLiveConfigChanges.onRecordingNewFragmentFinished();
            if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
                updateZoomRatioToggleButtonState(false);
                setMinZoomRatio(0.6f);
                setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
            }
            if (recordState != null) {
                recordState.onPause();
                return;
            }
            return;
        }
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
            updateZoomRatioToggleButtonState(true);
            if (isUltraWideBackCamera()) {
                setMinZoomRatio(0.6f);
                setMaxZoomRatio(0.6f * this.mCameraCapabilities.getMaxZoomRatio());
            } else {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
            }
        }
        trackLiveRecordingParams();
        this.mLiveConfigChanges.startRecordingNextFragment();
        if (recordState != null) {
            recordState.onResume();
        }
    }

    public void onPictureTaken(byte[] bArr) {
    }

    public void onPictureTakenFinished(boolean z) {
    }

    public boolean onPictureTakenImageConsumed(Image image) {
        return false;
    }

    public void onPreviewLayoutChanged(Rect rect) {
        this.mActivity.onLayoutChange(rect);
        if (this.mFocusManager != null && this.mActivity.getCameraScreenNail() != null) {
            this.mFocusManager.setRenderSize(this.mActivity.getCameraScreenNail().getRenderWidth(), this.mActivity.getCameraScreenNail().getRenderHeight());
            this.mFocusManager.setPreviewSize(rect.width(), rect.height());
        }
    }

    public void onPreviewMetaDataUpdate(CaptureResult captureResult) {
        if (captureResult != null) {
            super.onPreviewMetaDataUpdate(captureResult);
            if (this.mMetaDataFlowableEmitter != null) {
                this.mMetaDataFlowableEmitter.onNext(captureResult);
            }
        }
    }

    public void onPreviewSessionClosed(CameraCaptureSession cameraCaptureSession) {
    }

    public void onPreviewSessionFailed(CameraCaptureSession cameraCaptureSession) {
        if (!isTextureExpired() || !retryOnceIfCameraError(this.mHandler)) {
            this.mHandler.sendEmptyMessage(51);
        } else {
            Log.d(TAG, "sessionFailed due to surfaceTexture expired, retry");
        }
    }

    public void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession) {
        if (cameraCaptureSession != null && isAlive()) {
            this.mHandler.sendEmptyMessage(9);
            previewWhenSessionSuccess();
        }
    }

    public void onPreviewSizeChanged(int i, int i2) {
        if (this.mFocusManager != null) {
            this.mFocusManager.setPreviewSize(i, i2);
        }
    }

    public void onResume() {
        super.onResume();
        keepScreenOnAwhile();
    }

    public void onReviewCancelClicked() {
        getActivity().setVolumeControlStream(this.mOldOriginVolumeStream);
        ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onFinish();
        this.mIsPreviewing = false;
        ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).configLiveVV((VVItem) null, false, false);
        doLaterReleaseIfNeed();
    }

    public void onReviewDoneClicked() {
        getActivity().setVolumeControlStream(this.mOldOriginVolumeStream);
        ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onFinish();
        this.mIsPreviewing = false;
        startSaveToLocal();
        ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).configLiveVV((VVItem) null, false, true);
        doLaterReleaseIfNeed();
    }

    public void onShutterButtonClick(int i) {
        switch (this.mLiveConfigChanges != null ? this.mLiveConfigChanges.getNextRecordStep() : 1) {
            case 2:
                if (!checkCallingState()) {
                    Log.d(TAG, "ignore in calling state");
                    ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
                    recordState.onPrepare();
                    recordState.onFailed();
                    return;
                }
                startVideoRecording();
                ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
                if (backStack != null) {
                    backStack.handleBackStackFromShutter();
                    return;
                }
                return;
            case 3:
                stopVideoRecording(true, false);
                return;
            default:
                return;
        }
    }

    public void onShutterButtonFocus(boolean z, int i) {
    }

    public boolean onShutterButtonLongClick() {
        return false;
    }

    public void onShutterButtonLongClickCancel(boolean z) {
    }

    public void onSingleTapUp(int i, int i2, boolean z) {
        if (!this.mPaused && this.mCamera2Device != null && this.mCamera2Device.isSessionReady() && isInTapableRect(i, i2) && getCameraState() != 3 && getCameraState() != 0) {
            if (!isFrameAvailable()) {
                Log.w(TAG, "onSingleTapUp: frame not available");
            } else if ((!isFrontCamera() || !this.mActivity.isScreenSlideOff()) && !((ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)).handleBackStackFromTapDown(i, i2)) {
                this.mMainProtocol.setFocusViewType(true);
                this.mTouchFocusStartingTime = System.currentTimeMillis();
                Point point = new Point(i, i2);
                mapTapCoordinate(point);
                unlockAEAF();
                this.mFocusManager.onSingleTapUp(point.x, point.y, z);
            }
        }
    }

    public void onStop() {
        super.onStop();
        if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages((Object) null);
        }
    }

    public void onThumbnailClicked(View view) {
        if (!isDoingAction() && this.mActivity.getThumbnailUpdater().getThumbnail() != null) {
            this.mActivity.gotoGallery();
        }
    }

    public boolean onWaitingFocusFinished() {
        return !isBlockSnap() && isAlive();
    }

    public void onZoomingActionEnd(int i) {
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && !dualController.isSlideVisible()) {
            if (i != 2 && i != 1) {
                return;
            }
            if ((this.mLiveConfigChanges == null || (!this.mLiveConfigChanges.isRecording() && !this.mLiveConfigChanges.isRecordingPaused())) && !CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                dualController.setImmersiveModeEnabled(false);
            }
        }
    }

    public void onZoomingActionStart(int i) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && topAlert.isExtraMenuShowing()) {
            topAlert.hideExtraMenu();
        }
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController == null) {
            return;
        }
        if (i == 2 || i == 1) {
            dualController.setImmersiveModeEnabled(true);
        }
    }

    /* access modifiers changed from: protected */
    public void openSettingActivity() {
        Intent intent = new Intent();
        intent.setClass(this.mActivity, CameraPreferenceActivity.class);
        intent.putExtra(BasePreferenceActivity.FROM_WHERE, this.mModuleIndex);
        intent.putExtra(":miui:starting_window_label", getResources().getString(R.string.pref_camera_settings_category));
        if (this.mActivity.startFromKeyguard()) {
            intent.putExtra(CameraIntentManager.EXTRA_START_WHEN_LOCKED, true);
        }
        this.mActivity.startActivity(intent);
        this.mActivity.setJumpFlag(2);
        CameraStatUtil.trackGotoSettings(this.mModuleIndex);
    }

    public void pausePreview() {
        if (this.mCamera2Device.getFlashMode() == 2) {
            this.mCamera2Device.forceTurnFlashOffAndPausePreview();
        } else {
            this.mCamera2Device.pausePreview();
        }
        setCameraState(0);
    }

    /* access modifiers changed from: protected */
    public void performKeyClicked(int i, String str, int i2, boolean z) {
        if (i2 == 0 && z) {
            if (isIgnoreTouchEvent()) {
                Log.w(TAG, "ignore volume key");
            } else {
                onShutterButtonClick(i);
            }
        }
    }

    public void playFocusSound(int i) {
        playCameraSound(i);
    }

    public void registerProtocol() {
        super.registerProtocol();
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(167, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(167, this);
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            getActivity().getImplFactory().initAdditional(getActivity(), 164, 174, 199, 212);
        } else {
            getActivity().getImplFactory().initAdditional(getActivity(), 164, 199, 212);
        }
    }

    /* access modifiers changed from: protected */
    public void resetScreenOn() {
        this.mHandler.removeMessages(17);
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(52);
    }

    public void resumePreview() {
        previewWhenSessionSuccess();
    }

    public boolean shouldCaptureDirectly() {
        return false;
    }

    public boolean shouldReleaseLater() {
        return isRecording() || isSaving();
    }

    /* access modifiers changed from: protected */
    public void showConfirmMessage(int i, int i2) {
        if (this.mDialog != null && this.mDialog.isShowing()) {
            if (this.mTitleId != i && this.mMessageId != i2) {
                this.mDialog.dismiss();
            } else {
                return;
            }
        }
        this.mTitleId = i;
        this.mMessageId = i2;
        this.mDialog = RotateDialogController.showSystemAlertDialog(this.mActivity, this.mActivity.getString(i), this.mActivity.getString(i2), this.mActivity.getString(17039370), (Runnable) null, (String) null, (Runnable) null);
    }

    public void startFaceDetection() {
        if (this.mFaceDetectionEnabled && !this.mFaceDetectionStarted && isAlive() && this.mMaxFaceCount > 0 && this.mCamera2Device != null) {
            this.mFaceDetectionStarted = true;
            this.mCamera2Device.startFaceDetection();
            updateFaceView(true, true);
        }
    }

    public void startFocus() {
        if (isDeviceAlive() && isFrameAvailable()) {
            Log.v(TAG, "startFocus");
            if (this.mFocusOrAELockSupported) {
                this.mCamera2Device.startFocus(FocusTask.create(1), this.mModuleIndex);
            } else {
                this.mCamera2Device.resumePreview();
            }
        }
    }

    public void startPreview() {
        if (isDeviceAlive()) {
            this.mCamera2Device.setFocusCallback(this);
            this.mCamera2Device.setMetaDataCallback(this);
            this.mCamera2Device.setErrorCallback(this.mErrorCallback);
            this.mCamera2Device.setPreviewSize(this.mPreviewSize);
            this.mQuality = Util.convertSizeToQuality(this.mPreviewSize);
            this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            Log.d(TAG, "LiveModule, startPreview");
            checkDisplayOrientation();
            new Surface(this.mActivity.getCameraScreenNail().getSurfaceTexture());
            boolean z = this.mAlgorithmPreviewSize != null;
            this.mLiveConfigChanges.initPreview(this.mPreviewSize.width, this.mPreviewSize.height, this.mBogusCameraId, this.mActivity.getCameraScreenNail());
            Surface surface = new Surface(this.mLiveConfigChanges.getInputSurfaceTexture());
            isEisOn();
            if (!isSelectingCapturedResult()) {
                this.mCamera2Device.startPreviewSession(surface, z, false, false, getOperatingMode(), false, this);
            }
        }
    }

    public void startSaveToLocal() {
        if (!this.mSaved) {
            ContentValues saveContentValues = ((ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230)).getSaveContentValues();
            if (saveContentValues != null) {
                this.mSaved = true;
                saveContentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
                getActivity().getImageSaver().addVideo(saveContentValues.getAsString("_data"), saveContentValues, true);
            }
        }
    }

    public void stopFaceDetection(boolean z) {
        if (this.mFaceDetectionEnabled && this.mFaceDetectionStarted) {
            this.mFaceDetectionStarted = false;
            if (this.mCamera2Device != null) {
                this.mCamera2Device.stopFaceDetection();
            }
            this.mMainProtocol.setActiveIndicator(2);
            updateFaceView(false, z);
        }
    }

    public void stopObjectTracking(boolean z) {
    }

    public void stopVideoRecording(boolean z, boolean z2) {
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (isAlive() && recordState != null) {
            keepScreenOnAwhile();
            recordState.onPause();
            this.mLiveConfigChanges.onRecordingNewFragmentFinished();
            if (this.mLiveConfigChanges.canFinishRecording()) {
                trackLiveVideoParams();
                showPreview();
                recordState.onFinish();
            }
        }
    }

    public void unRegisterModulePersistProtocol() {
        super.unRegisterModulePersistProtocol();
        Log.d(TAG, "unRegisterModulePersistProtocol");
        getActivity().getImplFactory().detachModulePersistent();
    }

    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(167, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(167, this);
        getActivity().getImplFactory().detachAdditional();
    }

    /* access modifiers changed from: protected */
    public void unlockAEAF() {
        Log.d(TAG, "unlockAEAF");
        this.m3ALocked = false;
        if (this.mAeLockSupported) {
            this.mCamera2Device.setAELock(false);
        }
        this.mFocusManager.setAeAwbLock(false);
    }

    /* access modifiers changed from: protected */
    public void updateFace() {
        boolean enableFaceDetection = enableFaceDetection();
        if (this.mMainProtocol != null) {
            this.mMainProtocol.setSkipDrawFace(!enableFaceDetection || !this.mShowFace);
        }
        if (enableFaceDetection) {
            if (!this.mFaceDetectionEnabled) {
                this.mFaceDetectionEnabled = true;
                startFaceDetection();
            }
        } else if (this.mFaceDetectionEnabled) {
            stopFaceDetection(true);
            this.mFaceDetectionEnabled = false;
        }
    }

    public void updateFlashPreference() {
        setFlashMode(DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex));
    }

    /* access modifiers changed from: protected */
    public void updateFocusArea() {
        if (this.mCamera2Device == null) {
            Log.e(TAG, "updateFocusArea: null camera device");
            return;
        }
        Rect cropRegion = getCropRegion();
        Rect activeArraySize = getActiveArraySize();
        this.mActivity.getSensorStateManager().setFocusSensorEnabled(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize) != null);
        this.mCamera2Device.setAERegions(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize));
        if (this.mFocusAreaSupported) {
            this.mCamera2Device.setAFRegions(this.mFocusManager.getFocusAreas(cropRegion, activeArraySize));
        } else {
            this.mCamera2Device.resumePreview();
        }
    }
}
