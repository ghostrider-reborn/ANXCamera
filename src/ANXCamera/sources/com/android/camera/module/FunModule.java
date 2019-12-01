package com.android.camera.module;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.provider.MiuiSettings;
import android.support.annotation.MainThread;
import android.telephony.TelephonyManager;
import android.util.Range;
import android.view.Surface;
import android.view.View;
import com.android.camera.AutoLockManager;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.constant.AutoFocus;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.log.Log;
import com.android.camera.module.encoder.MediaAudioEncoder;
import com.android.camera.module.encoder.MediaEncoder;
import com.android.camera.module.encoder.MediaMuxerWrapper;
import com.android.camera.module.encoder.MediaVideoEncoder;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtil;
import com.android.camera.storage.Storage;
import com.android.camera.ui.PopupManager;
import com.android.camera.ui.V6CameraGLSurfaceView;
import com.android.camera2.CameraCapabilities;
import com.android.gallery3d.ui.GLCanvasImpl;
import com.mi.config.b;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

public class FunModule extends VideoBase implements ModeProtocol.FilterProtocol, ModeProtocol.StickerProtocol {
    private static final int FRAME_RATE = 30;
    private static final long START_OFFSET_MS = 450;
    private V6CameraGLSurfaceView mCameraView;
    private CountDownTimer mCountDownTimer;
    private MediaMuxerWrapper mLastMuxer;
    private MediaAudioEncoder mMediaAudioEncoder;
    private final MediaEncoder.MediaEncoderListener mMediaEncoderListener = new MediaEncoder.MediaEncoderListener() {
        public void onPrepared(MediaEncoder mediaEncoder) {
            String str = VideoBase.TAG;
            Log.v(str, "onPrepared: encoder=" + mediaEncoder);
        }

        public void onStopped(MediaEncoder mediaEncoder, boolean z) {
            String str = VideoBase.TAG;
            Log.v(str, "onStopped: encoder=" + mediaEncoder);
            if (z) {
                FunModule.this.executeSaveTask(true);
            }
        }
    };
    private MediaVideoEncoder mMediaVideoEncoder;
    private MediaMuxerWrapper mMuxer;
    private ArrayList<SaveVideoTask> mPendingSaveTaskList = new ArrayList<>();
    private int mQuality;
    private long mRequestStartTime;

    private final class SaveVideoTask {
        public ContentValues contentValues;
        public String videoPath;

        public SaveVideoTask(String str, ContentValues contentValues2) {
            this.videoPath = str;
            this.contentValues = contentValues2;
        }
    }

    public FunModule() {
        super(FunModule.class.getSimpleName());
        this.mOutputFormat = 2;
    }

    private void addSaveTask(String str, ContentValues contentValues) {
        contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        SaveVideoTask saveVideoTask = new SaveVideoTask(str, contentValues);
        synchronized (this) {
            this.mPendingSaveTaskList.add(saveVideoTask);
        }
    }

    private String convertToFilePath(FileDescriptor fileDescriptor) {
        return null;
    }

    /* access modifiers changed from: private */
    public void executeSaveTask(boolean z) {
        synchronized (this) {
            while (true) {
                if (this.mPendingSaveTaskList.isEmpty()) {
                    break;
                }
                SaveVideoTask remove = this.mPendingSaveTaskList.remove(0);
                String str = TAG;
                Log.d(str, "executeSaveTask: " + remove.videoPath);
                this.mActivity.getImageSaver().addVideo(remove.videoPath, remove.contentValues, true);
                if (z) {
                    break;
                }
            }
            doLaterReleaseIfNeed();
        }
    }

    private boolean initializeRecorder() {
        String str;
        if (this.mCamera2Device == null) {
            Log.e(TAG, "initializeRecorder: null camera");
            return false;
        }
        Log.v(TAG, "initializeRecorder");
        closeVideoFileDescriptor();
        if (isCaptureIntent()) {
            parseIntent(this.mActivity.getIntent());
        }
        if (this.mVideoFileDescriptor != null) {
            str = convertToFilePath(this.mVideoFileDescriptor.getFileDescriptor());
        } else {
            this.mCurrentVideoValues = genContentValues(this.mOutputFormat, -1);
            this.mCurrentVideoFilename = this.mCurrentVideoValues.getAsString("_data");
            str = this.mCurrentVideoFilename;
        }
        this.mOrientationCompensationAtRecordStart = this.mOrientationCompensation;
        try {
            releaseLastMediaRecorder();
            this.mMuxer = new MediaMuxerWrapper(str);
            MediaVideoEncoder mediaVideoEncoder = new MediaVideoEncoder(getActivity().getGLView().getEGLContext14(), this.mMuxer, this.mMediaEncoderListener, this.mVideoSize.width, this.mVideoSize.height);
            this.mMediaVideoEncoder = mediaVideoEncoder;
            this.mMediaAudioEncoder = new MediaAudioEncoder(this.mMuxer, this.mMediaEncoderListener);
            this.mMuxer.prepare();
            String str2 = TAG;
            Log.d(str2, "rotation: " + this.mOrientationCompensation);
            this.mMuxer.setOrientationHint(this.mOrientationCompensation);
            return true;
        } catch (IOException e) {
            Log.e(TAG, "initializeRecorder: ", e);
            return false;
        }
    }

    private boolean isEisOn() {
        return isBackCamera() && DataRepository.dataItemFeature().hD() && CameraSettings.isMovieSolidOn();
    }

    private boolean isSupportShortVideoBeautyBody() {
        return isBackCamera() && DataRepository.dataItemFeature().isSupportShortVideoBeautyBody();
    }

    private void onStartRecorderFail() {
        enableCameraControls(true);
        this.mAudioManager.abandonAudioFocus((AudioManager.OnAudioFocusChangeListener) null);
        restoreMusicSound();
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            recordState.onFailed();
        }
    }

    private void onStartRecorderSucceed() {
        enableCameraControls(true);
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
            updateZoomRatioToggleButtonState(true);
            if (isUltraWideBackCamera()) {
                setMinZoomRatio(0.6f);
                setMaxZoomRatio(0.6f * this.mCameraCapabilities.getMaxZoomRatio());
            } else if (isAuxCamera()) {
                setMinZoomRatio(2.0f);
                setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
            } else {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
            }
        }
        this.mActivity.sendBroadcast(new Intent(VideoBase.START_VIDEO_RECORDING_ACTION));
        this.mMediaRecorderRecording = true;
        this.mRecordingStartTime = SystemClock.uptimeMillis();
        this.mTelephonyManager.listen(this.mPhoneStateListener, 32);
        Log.v(TAG, "listen call state");
        updateRecordingTime();
        keepScreenOn();
        AutoLockManager.getInstance(this.mActivity).removeMessage();
        trackGeneralInfo(1, false);
    }

    private void releaseLastMediaRecorder() {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("releaseLastMediaRecorder ");
        sb.append(this.mLastMuxer != null);
        Log.d(str, sb.toString());
        if (this.mLastMuxer != null) {
            this.mLastMuxer.join();
            this.mLastMuxer = null;
        }
    }

    private void releaseMediaRecorder() {
        Log.v(TAG, "releaseMediaRecorder");
        if (this.mMuxer != null) {
            this.mLastMuxer = this.mMuxer;
            cleanupEmptyFile();
        }
    }

    private void releaseResources() {
        closeCamera();
        releaseMediaRecorder();
        releaseLastMediaRecorder();
    }

    private void setVideoSize(int i, int i2) {
        if (this.mCameraDisplayOrientation % 180 == 0) {
            this.mVideoSize = new CameraSize(i, i2);
        } else {
            this.mVideoSize = new CameraSize(i2, i);
        }
    }

    private boolean shouldApplyUltraWideLDC() {
        if (CameraSettings.shouldUltraWideVideoLDCBeVisibleInMode(this.mModuleIndex) && this.mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return CameraSettings.isUltraWideVideoLDCEnabled();
        }
        return false;
    }

    private boolean startRecorder() {
        if (!initializeRecorder()) {
            Log.e(TAG, "fail to initialize recorder");
            return false;
        }
        long currentTimeMillis = START_OFFSET_MS - (System.currentTimeMillis() - this.mRequestStartTime);
        if (currentTimeMillis < 0) {
            currentTimeMillis = 0;
        }
        boolean startRecording = this.mMuxer.startRecording(currentTimeMillis);
        if (!startRecording) {
            this.mMuxer.stopRecording();
            showConfirmMessage(R.string.confirm_recording_fail_title, R.string.confirm_recording_fail_recorder_busy_alert);
            releaseMediaRecorder();
        }
        return startRecording;
    }

    private void updateFilter() {
        int shaderEffect = CameraSettings.getShaderEffect();
        String str = TAG;
        Log.v(str, "updateFilter: 0x" + Integer.toHexString(shaderEffect));
        EffectController.getInstance().setEffect(shaderEffect);
    }

    private void updateFpsRange() {
        if (b.isMTKPlatform()) {
            this.mCamera2Device.setVideoFpsRange(new Range(5, 30));
            this.mCamera2Device.setFpsRange(new Range(5, 30));
            return;
        }
        this.mCamera2Device.setVideoFpsRange(new Range(30, 30));
        this.mCamera2Device.setFpsRange(new Range(30, 30));
    }

    private void updatePictureAndPreviewSize() {
        this.mPreviewSize = Util.getOptimalPreviewSize(false, this.mBogusCameraId, this.mCameraCapabilities.getSupportedOutputSize(SurfaceTexture.class), (double) CameraSettings.getPreviewAspectRatio(16, 9));
        updateCameraScreenNailSize(this.mPreviewSize.width, this.mPreviewSize.height);
        String str = TAG;
        Log.d(str, "previewSize: " + this.mPreviewSize);
    }

    private void updateUltraWideLDC() {
        this.mCamera2Device.setUltraWideLDC(shouldApplyUltraWideLDC());
    }

    private void updateVideoStabilization() {
        if (isDeviceAlive()) {
            if (CameraSettings.isVideoBokehOn() && isFrontCamera()) {
                Log.d(TAG, "videoStabilization: disabled EIS and OIS when VIDEO_BOKEH is opened");
                this.mCamera2Device.setEnableEIS(false);
                this.mCamera2Device.setEnableOIS(false);
                this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
            } else if (isEisOn()) {
                String str = TAG;
                Log.d(str, "videoStabilization: EIS isEISPreviewSupported = " + this.mCameraCapabilities.isEISPreviewSupported());
                this.mCamera2Device.setEnableEIS(true);
                this.mCamera2Device.setEnableOIS(false);
                if (!this.mCameraCapabilities.isEISPreviewSupported()) {
                    this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(true);
                }
            } else {
                this.mCamera2Device.setEnableEIS(false);
                this.mCamera2Device.setEnableOIS(true);
                this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
                if (b.isMTKPlatform() && this.mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                    this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(true);
                }
            }
        }
    }

    public void consumePreference(@UpdateConstant.UpdateType int... iArr) {
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
                case 54:
                    break;
                case 12:
                    setEvValue();
                    break;
                case 13:
                    updateBeauty();
                    break;
                case 14:
                    updateVideoFocusMode();
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
                case 55:
                    updateModuleRelated();
                    break;
                case 58:
                    updateBackSoftLightPreference();
                    break;
                default:
                    if (!DEBUG) {
                        Log.w(TAG, "no consumer for this updateType: " + i);
                        break;
                    } else {
                        throw new RuntimeException("no consumer for this updateType: " + i);
                    }
            }
        }
    }

    /* access modifiers changed from: protected */
    public int getOperatingMode() {
        int i = isEisOn() ? 32772 : (!CameraSettings.isVideoBokehOn() || !isFrontCamera()) ? this.mCameraCapabilities.isSupportVideoBeauty() ? CameraCapabilities.SESSION_OPERATION_MODE_VIDEO_BEAUTY : DataRepository.dataItemFeature().fX() ? CameraCapabilities.SESSION_OPERATION_MODE_MCTF : 0 : 32770;
        String str = TAG;
        Log.d(str, "getOperatingMode: " + Integer.toHexString(i));
        return i;
    }

    /* access modifiers changed from: protected */
    public boolean isCameraSwitchingDuringZoomingAllowed() {
        return HybridZoomingSystem.IS_3_OR_MORE_SAT && this.mModuleIndex == 161 && !CameraSettings.isMacroModeEnabled(this.mModuleIndex) && isBackCamera() && !this.mMediaRecorderRecording && !this.mMediaRecorderRecordingPaused;
    }

    public boolean isNeedHapticFeedback() {
        return !this.mMediaRecorderRecording;
    }

    public boolean isNeedMute() {
        return this.mMediaRecorderRecording;
    }

    public void onBeautyParameterChanged() {
        if (isSupportShortVideoBeautyBody() || this.mCameraCapabilities.isSupportVideoBeauty()) {
            updatePreferenceInWorkThread(13);
            return;
        }
        super.onBeautyParameterChanged();
    }

    /* access modifiers changed from: protected */
    public void onCameraOpened() {
        super.onCameraOpened();
        readVideoPreferences();
        initializeFocusManager();
        updatePreferenceTrampoline(UpdateConstant.FUN_TYPES_INIT);
        startPreview();
    }

    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        changeConflictPreference();
        setCaptureIntent(this.mActivity.getCameraIntentManager().isVideoCaptureIntent());
        this.mActivity.getSensorStateManager().setSensorStateListener(this.mSensorStateListener);
        this.mCameraView = this.mActivity.getGLView();
        enableCameraControls(false);
        this.mTelephonyManager = (TelephonyManager) this.mActivity.getSystemService("phone");
        this.mVideoFocusMode = AutoFocus.LEGACY_CONTINUOUS_VIDEO;
        onCameraOpened();
    }

    public void onDestroy() {
        super.onDestroy();
        this.mHandler.sendEmptyMessage(45);
    }

    public void onFilterChanged(int i, int i2) {
        updatePreferenceTrampoline(2);
        this.mMainProtocol.updateEffectViewVisible();
    }

    public void onPause() {
        super.onPause();
        waitStereoSwitchThread();
        releaseResources();
        updateStereoSettings(false);
        closeVideoFileDescriptor();
        this.mActivity.getSensorStateManager().reset();
        stopFaceDetection(true);
        resetScreenOn();
        this.mHandler.removeCallbacksAndMessages((Object) null);
        if (!this.mActivity.isActivityPaused() && !CameraSettings.isStereoModeOn()) {
            PopupManager.getInstance(this.mActivity).notifyShowPopup((View) null, 1);
        }
    }

    public void onPreviewLayoutChanged(Rect rect) {
        String str = TAG;
        Log.v(str, "onPreviewLayoutChanged: " + rect);
        this.mActivity.onLayoutChange(rect);
        if (this.mFocusManager != null) {
            this.mFocusManager.setRenderSize(this.mActivity.getCameraScreenNail().getRenderWidth(), this.mActivity.getCameraScreenNail().getRenderHeight());
            this.mFocusManager.setPreviewSize(rect.width(), rect.height());
        }
    }

    public void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession) {
        super.onPreviewSessionSuccess(cameraCaptureSession);
        if (!isCreated()) {
            Log.w(TAG, "onPreviewSessionSuccess: module is not ready");
            return;
        }
        String str = TAG;
        Log.d(str, "onPreviewSessionSuccess: " + cameraCaptureSession);
        this.mFaceDetectionEnabled = false;
        updatePreferenceInWorkThread(UpdateConstant.FUN_TYPES_ON_PREVIEW_SUCCESS);
    }

    /* access modifiers changed from: protected */
    @MainThread
    public void onPreviewStart() {
        if (this.mPreviewing) {
            this.mMainProtocol.initializeFocusView(this);
            onShutterButtonFocus(true, 3);
        }
    }

    public void onSharedPreferenceChanged() {
        if (!this.mPaused && this.mCamera2Device != null) {
            readVideoPreferences();
        }
    }

    public void onShutterButtonClick(int i) {
        String str = TAG;
        Log.v(str, "onShutterButtonClick  isRecording=" + this.mMediaRecorderRecording + " inStartingFocusRecording=" + this.mInStartingFocusRecording);
        this.mInStartingFocusRecording = false;
        this.mLastBackPressedTime = 0;
        if (isIgnoreTouchEvent()) {
            Log.w(TAG, "onShutterButtonClick: ignore touch event");
        } else if (!isFrontCamera() || !this.mActivity.isScreenSlideOff()) {
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (this.mMediaRecorderRecording) {
                stopVideoRecording(true, false);
                return;
            }
            recordState.onPrepare();
            if (!checkCallingState()) {
                recordState.onFailed();
                return;
            }
            this.mActivity.getScreenHint().updateHint();
            if (Storage.isLowStorageAtLastPoint()) {
                recordState.onFailed();
                return;
            }
            setTriggerMode(i);
            enableCameraControls(false);
            playCameraSound(2);
            this.mRequestStartTime = System.currentTimeMillis();
            if (this.mFocusManager.canRecord()) {
                startVideoRecording();
                return;
            }
            Log.v(TAG, "wait for autoFocus");
            this.mInStartingFocusRecording = true;
        }
    }

    public void onShutterButtonFocus(boolean z, int i) {
    }

    public void onSingleTapUp(int i, int i2, boolean z) {
        if (!this.mPaused && this.mCamera2Device != null && !hasCameraException() && this.mCamera2Device.isSessionReady() && !this.mSnapshotInProgress && isInTapableRect(i, i2)) {
            if (!isFrameAvailable()) {
                Log.w(TAG, "onSingleTapUp: frame not available");
            } else if (!isFrontCamera() || !this.mActivity.isScreenSlideOff()) {
                ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
                if (backStack != null && !backStack.handleBackStackFromTapDown(i, i2)) {
                    this.mMainProtocol.setFocusViewType(true);
                    this.mTouchFocusStartingTime = System.currentTimeMillis();
                    Point point = new Point(i, i2);
                    mapTapCoordinate(point);
                    unlockAEAF();
                    this.mFocusManager.onSingleTapUp(point.x, point.y, z);
                }
            }
        }
    }

    public void onStickerChanged(String str) {
        String str2 = TAG;
        Log.v(str2, "onStickerChanged: " + str);
        if (this.mCameraView != null) {
            GLCanvasImpl gLCanvas = this.mCameraView.getGLCanvas();
            if (gLCanvas instanceof GLCanvasImpl) {
                gLCanvas.setSticker(str);
            }
        }
    }

    public void onStop() {
        super.onStop();
        EffectController.getInstance().setCurrentSticker((String) null);
    }

    public void onSurfaceTextureUpdated(DrawExtTexAttribute drawExtTexAttribute) {
        MediaVideoEncoder mediaVideoEncoder;
        boolean z;
        synchronized (this) {
            mediaVideoEncoder = this.mMediaVideoEncoder;
            z = this.mMediaRecorderRecording;
        }
        if (mediaVideoEncoder != null && z) {
            mediaVideoEncoder.frameAvailableSoon(drawExtTexAttribute);
        }
    }

    public void onZoomingActionEnd(int i) {
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && !dualController.isSlideVisible()) {
            if ((i == 2 || i == 1) && !this.mMediaRecorderRecording && !this.mMediaRecorderRecordingPaused && !CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
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

    public void pausePreview() {
        Log.v(TAG, "pausePreview");
        this.mPreviewing = false;
        if (this.mCamera2Device != null) {
            this.mCamera2Device.pausePreview();
        }
        if (this.mFocusManager != null) {
            this.mFocusManager.resetFocused();
        }
    }

    /* access modifiers changed from: protected */
    public void readVideoPreferences() {
        this.mMaxVideoDurationInMs = 15450;
    }

    public void registerProtocol() {
        super.registerProtocol();
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(167, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(165, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(178, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(169, this);
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            getActivity().getImplFactory().initAdditional(getActivity(), 164, 174, 199, 212);
        } else {
            getActivity().getImplFactory().initAdditional(getActivity(), 164, 199, 212);
        }
    }

    /* access modifiers changed from: protected */
    public void resizeForPreviewAspectRatio() {
        if (((this.mCameraCapabilities.getSensorOrientation() - Util.getDisplayRotation(this.mActivity)) + MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT) % 180 == 0) {
            this.mMainProtocol.setPreviewAspectRatio(((float) this.mPreviewSize.height) / ((float) this.mPreviewSize.width));
        } else {
            this.mMainProtocol.setPreviewAspectRatio(((float) this.mPreviewSize.width) / ((float) this.mPreviewSize.height));
        }
    }

    public void resumePreview() {
        Log.v(TAG, "resumePreview");
        this.mPreviewing = true;
        if (this.mCamera2Device != null) {
            this.mCamera2Device.resumePreview();
        }
    }

    public void startPreview() {
        String str = TAG;
        Log.d(str, "startPreview: " + this.mPreviewing);
        if (isDeviceAlive()) {
            checkDisplayOrientation();
            setVideoSize(this.mPreviewSize.width, this.mPreviewSize.height);
            this.mQuality = Util.convertSizeToQuality(this.mPreviewSize);
            this.mCamera2Device.setPictureSize(this.mPreviewSize);
            this.mCamera2Device.setErrorCallback(this.mErrorCallback);
            this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            this.mCamera2Device.startPreviewSession(new Surface(this.mActivity.getCameraScreenNail().getSurfaceTexture()), false, false, false, getOperatingMode(), false, this);
            this.mFocusManager.resetFocused();
            if (this.mAELockOnlySupported) {
                this.mCamera2Device.setFocusCallback(this);
            }
            this.mPreviewing = true;
        }
    }

    /* access modifiers changed from: protected */
    public void startVideoRecording() {
        Log.v(TAG, "startVideoRecording");
        this.mCurrentVideoUri = null;
        silenceSounds();
        if (!startRecorder()) {
            onStartRecorderFail();
            return;
        }
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            recordState.onStart();
        }
        Log.v(TAG, "startVideoRecording process done");
        onStartRecorderSucceed();
    }

    public void stopVideoRecording(boolean z, boolean z2) {
        if (this.mMediaRecorderRecording) {
            if (is3ALocked()) {
                unlockAEAF();
            }
            this.mMediaRecorderRecording = false;
            long uptimeMillis = SystemClock.uptimeMillis() - this.mRecordingStartTime;
            this.mMuxer.stopRecording();
            if (!this.mPaused) {
                playCameraSound(3);
            }
            releaseMediaRecorder();
            boolean z3 = this.mCurrentVideoFilename == null;
            if (!z3 && uptimeMillis < 1000) {
                deleteVideoFile(this.mCurrentVideoFilename);
                z3 = true;
            }
            if (!z3) {
                addSaveTask(this.mCurrentVideoFilename, this.mCurrentVideoValues);
            }
            if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
                updateZoomRatioToggleButtonState(false);
                if (isUltraWideBackCamera()) {
                    setMinZoomRatio(0.6f);
                    setMaxZoomRatio(0.6f * this.mCameraCapabilities.getMaxZoomRatio());
                } else {
                    setMinZoomRatio(0.6f);
                    setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                }
            }
            this.mActivity.sendBroadcast(new Intent(VideoBase.STOP_VIDEO_RECORDING_ACTION));
            this.mTelephonyManager.listen(this.mPhoneStateListener, 0);
            Log.v(TAG, "listen none");
            animateHold();
            if (this.mCountDownTimer != null) {
                this.mCountDownTimer.cancel();
            }
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onFinish();
            }
            if (this.mCamera2Device != null) {
                CameraStatUtil.trackVideoRecorded(isFrontCamera(), getModuleIndex(), false, false, CameraSettings.isUltraWideConfigOpen(getModuleIndex()), CameraSettings.VIDEO_MODE_FUN, this.mQuality, this.mCamera2Device.getFlashMode(), 30, 0, this.mBeautyValues, uptimeMillis / 1000);
                CameraStatUtil.trackUltraWideFunTaken();
            }
            if (!z2 && !AutoFocus.LEGACY_CONTINUOUS_VIDEO.equals(this.mVideoFocusMode)) {
                this.mMainProtocol.clearFocusView(2);
                setVideoFocusMode(AutoFocus.LEGACY_CONTINUOUS_VIDEO, false);
                updatePreferenceInWorkThread(14);
            }
            this.mAudioManager.abandonAudioFocus((AudioManager.OnAudioFocusChangeListener) null);
            restoreMusicSound();
            keepScreenOnAwhile();
            AutoLockManager.getInstance(this.mActivity).hibernateDelayed();
        }
    }

    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(167, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(165, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(178, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(199, this);
        getActivity().getImplFactory().detachAdditional();
    }

    /* access modifiers changed from: protected */
    public void updateRecordingTime() {
        super.updateRecordingTime();
        if (this.mMediaRecorderRecording) {
            if (this.mCountDownTimer != null) {
                this.mCountDownTimer.cancel();
            }
            AnonymousClass1 r1 = new CountDownTimer((long) this.mMaxVideoDurationInMs, 1000) {
                public void onFinish() {
                    FunModule.this.stopVideoRecording(true, false);
                }

                public void onTick(long j) {
                    String millisecondToTimeString = Util.millisecondToTimeString((j + 950) - FunModule.START_OFFSET_MS, false);
                    ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    if (topAlert != null) {
                        topAlert.updateRecordingTime(millisecondToTimeString);
                    }
                }
            };
            this.mCountDownTimer = r1;
            this.mCountDownTimer.start();
        }
    }
}
