package com.android.camera.module;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.os.CountDownTimer;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.support.annotation.MainThread;
import android.telephony.TelephonyManager;
import android.util.Range;
import android.view.Surface;
import com.android.camera.AutoLockManager;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.constant.AutoFocus;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.constant.UpdateConstant.UpdateType;
import com.android.camera.data.DataRepository;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.log.Log;
import com.android.camera.module.encoder.MediaAudioEncoder;
import com.android.camera.module.encoder.MediaEncoder;
import com.android.camera.module.encoder.MediaEncoder.MediaEncoderListener;
import com.android.camera.module.encoder.MediaMuxerWrapper;
import com.android.camera.module.encoder.MediaVideoEncoder;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.FocusManager2;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol.BackStack;
import com.android.camera.protocol.ModeProtocol.DualController;
import com.android.camera.protocol.ModeProtocol.FilterProtocol;
import com.android.camera.protocol.ModeProtocol.MainContentProtocol;
import com.android.camera.protocol.ModeProtocol.RecordState;
import com.android.camera.protocol.ModeProtocol.StickerProtocol;
import com.android.camera.protocol.ModeProtocol.TopAlert;
import com.android.camera.statistic.CameraStatUtil;
import com.android.camera.storage.Storage;
import com.android.camera.ui.PopupManager;
import com.android.camera.ui.V6CameraGLSurfaceView;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.gallery3d.ui.GLCanvasImpl;
import com.mi.config.b;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

public class FunModule extends VideoBase implements FilterProtocol, StickerProtocol {
    private static final int FRAME_RATE = 30;
    private static final long START_OFFSET_MS = 450;
    private V6CameraGLSurfaceView mCameraView;
    private CountDownTimer mCountDownTimer;
    private MediaMuxerWrapper mLastMuxer;
    private MediaAudioEncoder mMediaAudioEncoder;
    private final MediaEncoderListener mMediaEncoderListener = new MediaEncoderListener() {
        public void onPrepared(MediaEncoder mediaEncoder) {
            String str = VideoBase.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onPrepared: encoder=");
            sb.append(mediaEncoder);
            Log.v(str, sb.toString());
        }

        public void onStopped(MediaEncoder mediaEncoder, boolean z) {
            String str = VideoBase.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onStopped: encoder=");
            sb.append(mediaEncoder);
            Log.v(str, sb.toString());
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
            do {
                if (this.mPendingSaveTaskList.isEmpty()) {
                    break;
                }
                SaveVideoTask saveVideoTask = (SaveVideoTask) this.mPendingSaveTaskList.remove(0);
                String str = VideoBase.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("executeSaveTask: ");
                sb.append(saveVideoTask.videoPath);
                Log.d(str, sb.toString());
                this.mActivity.getImageSaver().addVideo(saveVideoTask.videoPath, saveVideoTask.contentValues, true);
            } while (!z);
            doLaterReleaseIfNeed();
        }
    }

    private boolean initializeRecorder() {
        String str;
        if (this.mCamera2Device == null) {
            Log.e(VideoBase.TAG, "initializeRecorder: null camera");
            return false;
        }
        Log.v(VideoBase.TAG, "initializeRecorder");
        closeVideoFileDescriptor();
        if (isCaptureIntent()) {
            parseIntent(this.mActivity.getIntent());
        }
        ParcelFileDescriptor parcelFileDescriptor = this.mVideoFileDescriptor;
        if (parcelFileDescriptor != null) {
            str = convertToFilePath(parcelFileDescriptor.getFileDescriptor());
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
            String str2 = VideoBase.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("rotation: ");
            sb.append(this.mOrientationCompensation);
            Log.d(str2, sb.toString());
            this.mMuxer.setOrientationHint(this.mOrientationCompensation);
            return true;
        } catch (IOException e2) {
            Log.e(VideoBase.TAG, "initializeRecorder: ", e2);
            return false;
        }
    }

    private boolean isEisOn() {
        return isBackCamera() && DataRepository.dataItemFeature().fb() && CameraSettings.isMovieSolidOn();
    }

    private boolean isSupportShortVideoBeautyBody() {
        return isBackCamera() && DataRepository.dataItemFeature().isSupportShortVideoBeautyBody();
    }

    private void onStartRecorderFail() {
        enableCameraControls(true);
        this.mAudioManager.abandonAudioFocus(null);
        restoreMusicSound();
        RecordState recordState = (RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
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
                setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio() * 0.6f);
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
        Log.v(VideoBase.TAG, "listen call state");
        updateRecordingTime();
        keepScreenOn();
        AutoLockManager.getInstance(this.mActivity).removeMessage();
        trackGeneralInfo(1, false);
    }

    private void releaseLastMediaRecorder() {
        String str = VideoBase.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("releaseLastMediaRecorder ");
        sb.append(this.mLastMuxer != null);
        Log.d(str, sb.toString());
        MediaMuxerWrapper mediaMuxerWrapper = this.mLastMuxer;
        if (mediaMuxerWrapper != null) {
            mediaMuxerWrapper.join();
            this.mLastMuxer = null;
        }
    }

    private void releaseMediaRecorder() {
        Log.v(VideoBase.TAG, "releaseMediaRecorder");
        MediaMuxerWrapper mediaMuxerWrapper = this.mMuxer;
        if (mediaMuxerWrapper != null) {
            this.mLastMuxer = mediaMuxerWrapper;
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
            Log.e(VideoBase.TAG, "fail to initialize recorder");
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
        String str = VideoBase.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("updateFilter: 0x");
        sb.append(Integer.toHexString(shaderEffect));
        Log.v(str, sb.toString());
        EffectController.getInstance().setEffect(shaderEffect);
    }

    private void updateFpsRange() {
        boolean isMTKPlatform = b.isMTKPlatform();
        Integer valueOf = Integer.valueOf(30);
        if (isMTKPlatform) {
            this.mCamera2Device.setVideoFpsRange(new Range(Integer.valueOf(5), valueOf));
            this.mCamera2Device.setFpsRange(new Range(Integer.valueOf(5), valueOf));
            return;
        }
        this.mCamera2Device.setVideoFpsRange(new Range(valueOf, valueOf));
        this.mCamera2Device.setFpsRange(new Range(valueOf, valueOf));
    }

    private void updatePictureAndPreviewSize() {
        this.mPreviewSize = Util.getOptimalPreviewSize(false, this.mBogusCameraId, this.mCameraCapabilities.getSupportedOutputSize(SurfaceTexture.class), (double) CameraSettings.getPreviewAspectRatio(16, 9));
        CameraSize cameraSize = this.mPreviewSize;
        updateCameraScreenNailSize(cameraSize.width, cameraSize.height);
        String str = VideoBase.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("previewSize: ");
        sb.append(this.mPreviewSize);
        Log.d(str, sb.toString());
    }

    private void updateUltraWideLDC() {
        this.mCamera2Device.setUltraWideLDC(shouldApplyUltraWideLDC());
    }

    private void updateVideoStabilization() {
        if (isDeviceAlive()) {
            if (!CameraSettings.isVideoBokehOn() || !isFrontCamera()) {
                if (isEisOn()) {
                    String str = VideoBase.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("videoStabilization: EIS isEISPreviewSupported = ");
                    sb.append(this.mCameraCapabilities.isEISPreviewSupported());
                    Log.d(str, sb.toString());
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
                return;
            }
            Log.d(VideoBase.TAG, "videoStabilization: disabled EIS and OIS when VIDEO_BOKEH is opened");
            this.mCamera2Device.setEnableEIS(false);
            this.mCamera2Device.setEnableOIS(false);
            this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
        }
    }

    public void consumePreference(@UpdateType int... iArr) {
        for (int i : iArr) {
            if (i == 1) {
                updatePictureAndPreviewSize();
            } else if (i == 2) {
                updateFilter();
            } else if (i == 3) {
                updateFocusArea();
            } else if (i == 5) {
                updateFace();
            } else if (i == 50) {
                continue;
            } else if (i == 58) {
                updateBackSoftLightPreference();
            } else if (i == 19) {
                updateFpsRange();
            } else if (i == 20) {
                continue;
            } else if (i == 24) {
                applyZoomRatio();
            } else if (i == 25) {
                focusCenter();
            } else if (i == 34) {
                continue;
            } else if (i == 35) {
                updateDeviceOrientation();
            } else if (!(i == 42 || i == 43 || i == 46)) {
                if (i == 47) {
                    updateUltraWideLDC();
                } else if (i == 54) {
                    continue;
                } else if (i != 55) {
                    switch (i) {
                        case 9:
                            updateAntiBanding(CameraSettings.getAntiBanding());
                            break;
                        case 10:
                            updateFlashPreference();
                            break;
                        case 11:
                            continue;
                        case 12:
                            setEvValue();
                            break;
                        case 13:
                            updateBeauty();
                            break;
                        case 14:
                            updateVideoFocusMode();
                            break;
                        default:
                            switch (i) {
                                case 29:
                                    updateExposureMeteringMode();
                                    break;
                                case 30:
                                    continue;
                                case 31:
                                    updateVideoStabilization();
                                    break;
                                default:
                                    String str = "no consumer for this updateType: ";
                                    if (!BaseModule.DEBUG) {
                                        String str2 = VideoBase.TAG;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(str);
                                        sb.append(i);
                                        Log.w(str2, sb.toString());
                                        break;
                                    } else {
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append(str);
                                        sb2.append(i);
                                        throw new RuntimeException(sb2.toString());
                                    }
                            }
                    }
                } else {
                    updateModuleRelated();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public int getOperatingMode() {
        int i = isEisOn() ? 32772 : (!CameraSettings.isVideoBokehOn() || !isFrontCamera()) ? this.mCameraCapabilities.isSupportVideoBeauty() ? CameraCapabilities.SESSION_OPERATION_MODE_VIDEO_BEAUTY : DataRepository.dataItemFeature().ob() ? CameraCapabilities.SESSION_OPERATION_MODE_MCTF : 0 : 32770;
        String str = VideoBase.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("getOperatingMode: ");
        sb.append(Integer.toHexString(i));
        Log.d(str, sb.toString());
        return i;
    }

    /* access modifiers changed from: protected */
    public boolean isCameraSwitchingDuringZoomingAllowed() {
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            int i = this.mModuleIndex;
            if (i == 161 && !CameraSettings.isMacroModeEnabled(i) && isBackCamera() && !this.mMediaRecorderRecording && !this.mMediaRecorderRecordingPaused) {
                return true;
            }
        }
        return false;
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
        this.mHandler.removeCallbacksAndMessages(null);
        if (!this.mActivity.isActivityPaused() && !CameraSettings.isStereoModeOn()) {
            PopupManager.getInstance(this.mActivity).notifyShowPopup(null, 1);
        }
    }

    public void onPreviewLayoutChanged(Rect rect) {
        String str = VideoBase.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onPreviewLayoutChanged: ");
        sb.append(rect);
        Log.v(str, sb.toString());
        this.mActivity.onLayoutChange(rect);
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setRenderSize(this.mActivity.getCameraScreenNail().getRenderWidth(), this.mActivity.getCameraScreenNail().getRenderHeight());
            this.mFocusManager.setPreviewSize(rect.width(), rect.height());
        }
    }

    public void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession) {
        super.onPreviewSessionSuccess(cameraCaptureSession);
        if (!isCreated()) {
            Log.w(VideoBase.TAG, "onPreviewSessionSuccess: module is not ready");
            return;
        }
        String str = VideoBase.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onPreviewSessionSuccess: ");
        sb.append(cameraCaptureSession);
        Log.d(str, sb.toString());
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
        String str = VideoBase.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onShutterButtonClick  isRecording=");
        sb.append(this.mMediaRecorderRecording);
        sb.append(" inStartingFocusRecording=");
        sb.append(this.mInStartingFocusRecording);
        Log.v(str, sb.toString());
        this.mInStartingFocusRecording = false;
        this.mLastBackPressedTime = 0;
        if (isIgnoreTouchEvent()) {
            Log.w(VideoBase.TAG, "onShutterButtonClick: ignore touch event");
        } else if (!isFrontCamera() || !this.mActivity.isScreenSlideOff()) {
            RecordState recordState = (RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (this.mMediaRecorderRecording) {
                stopVideoRecording(true, false);
            } else {
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
                } else {
                    Log.v(VideoBase.TAG, "wait for autoFocus");
                    this.mInStartingFocusRecording = true;
                }
            }
        }
    }

    public void onShutterButtonFocus(boolean z, int i) {
    }

    public void onSingleTapUp(int i, int i2, boolean z) {
        if (!this.mPaused && this.mCamera2Device != null && !hasCameraException() && this.mCamera2Device.isSessionReady() && !this.mSnapshotInProgress && isInTapableRect(i, i2)) {
            if (!isFrameAvailable()) {
                Log.w(VideoBase.TAG, "onSingleTapUp: frame not available");
            } else if (!isFrontCamera() || !this.mActivity.isScreenSlideOff()) {
                BackStack backStack = (BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
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
        String str2 = VideoBase.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onStickerChanged: ");
        sb.append(str);
        Log.v(str2, sb.toString());
        V6CameraGLSurfaceView v6CameraGLSurfaceView = this.mCameraView;
        if (v6CameraGLSurfaceView != null) {
            GLCanvasImpl gLCanvas = v6CameraGLSurfaceView.getGLCanvas();
            if (gLCanvas instanceof GLCanvasImpl) {
                gLCanvas.setSticker(str);
            }
        }
    }

    public void onStop() {
        super.onStop();
        EffectController.getInstance().setCurrentSticker(null);
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
        DualController dualController = (DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && !dualController.isSlideVisible()) {
            if ((i == 2 || i == 1) && !this.mMediaRecorderRecording && !this.mMediaRecorderRecordingPaused && !CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                dualController.setImmersiveModeEnabled(false);
            }
        }
    }

    public void onZoomingActionStart(int i) {
        TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && topAlert.isExtraMenuShowing()) {
            topAlert.hideExtraMenu();
        }
        DualController dualController = (DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController == null) {
            return;
        }
        if (i == 2 || i == 1) {
            dualController.setImmersiveModeEnabled(true);
        }
    }

    public void pausePreview() {
        Log.v(VideoBase.TAG, "pausePreview");
        this.mPreviewing = false;
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.pausePreview();
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.resetFocused();
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
        if (((this.mCameraCapabilities.getSensorOrientation() - Util.getDisplayRotation(this.mActivity)) + 360) % 180 == 0) {
            MainContentProtocol mainContentProtocol = this.mMainProtocol;
            CameraSize cameraSize = this.mPreviewSize;
            mainContentProtocol.setPreviewAspectRatio(((float) cameraSize.height) / ((float) cameraSize.width));
            return;
        }
        MainContentProtocol mainContentProtocol2 = this.mMainProtocol;
        CameraSize cameraSize2 = this.mPreviewSize;
        mainContentProtocol2.setPreviewAspectRatio(((float) cameraSize2.width) / ((float) cameraSize2.height));
    }

    public void resumePreview() {
        Log.v(VideoBase.TAG, "resumePreview");
        this.mPreviewing = true;
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.resumePreview();
        }
    }

    public void startPreview() {
        String str = VideoBase.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("startPreview: ");
        sb.append(this.mPreviewing);
        Log.d(str, sb.toString());
        if (isDeviceAlive()) {
            checkDisplayOrientation();
            CameraSize cameraSize = this.mPreviewSize;
            setVideoSize(cameraSize.width, cameraSize.height);
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
        Log.v(VideoBase.TAG, "startVideoRecording");
        this.mCurrentVideoUri = null;
        silenceSounds();
        if (!startRecorder()) {
            onStartRecorderFail();
            return;
        }
        RecordState recordState = (RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            recordState.onStart();
        }
        Log.v(VideoBase.TAG, "startVideoRecording process done");
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
                    setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio() * 0.6f);
                } else {
                    setMinZoomRatio(0.6f);
                    setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                }
            }
            this.mActivity.sendBroadcast(new Intent(VideoBase.STOP_VIDEO_RECORDING_ACTION));
            this.mTelephonyManager.listen(this.mPhoneStateListener, 0);
            Log.v(VideoBase.TAG, "listen none");
            animateHold();
            CountDownTimer countDownTimer = this.mCountDownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            RecordState recordState = (RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onFinish();
            }
            if (this.mCamera2Device != null) {
                CameraStatUtil.trackVideoRecorded(isFrontCamera(), getModuleIndex(), false, false, CameraSettings.isUltraWideConfigOpen(getModuleIndex()), CameraSettings.VIDEO_MODE_FUN, this.mQuality, this.mCamera2Device.getFlashMode(), 30, 0, this.mBeautyValues, uptimeMillis / 1000);
                CameraStatUtil.trackUltraWideFunTaken();
            }
            if (!z2) {
                String str = this.mVideoFocusMode;
                String str2 = AutoFocus.LEGACY_CONTINUOUS_VIDEO;
                if (!str2.equals(str)) {
                    this.mMainProtocol.clearFocusView(2);
                    setVideoFocusMode(str2, false);
                    updatePreferenceInWorkThread(14);
                }
            }
            this.mAudioManager.abandonAudioFocus(null);
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
            CountDownTimer countDownTimer = this.mCountDownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            AnonymousClass1 r1 = new CountDownTimer((long) this.mMaxVideoDurationInMs, 1000) {
                public void onFinish() {
                    FunModule.this.stopVideoRecording(true, false);
                }

                public void onTick(long j) {
                    String millisecondToTimeString = Util.millisecondToTimeString((j + 950) - FunModule.START_OFFSET_MS, false);
                    TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
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
