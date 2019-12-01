package com.android.camera.module;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.SensorEvent;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.MiuiSettings;
import android.text.TextUtils;
import android.util.Range;
import android.util.Size;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import com.android.camera.BasePreferenceActivity;
import com.android.camera.Camera;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraPreferenceActivity;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.Exif;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.LocalParallelService;
import com.android.camera.LocationManager;
import com.android.camera.PictureSizeManager;
import com.android.camera.R;
import com.android.camera.SensorStateManager;
import com.android.camera.Thumbnail;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.constant.AutoFocus;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.constant.CameraScene;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigBokeh;
import com.android.camera.data.data.config.ComponentConfigFlash;
import com.android.camera.data.data.config.ComponentConfigHdr;
import com.android.camera.data.data.config.ComponentRunningUltraPixel;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.data.data.runing.ComponentRunningTiltValue;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FaceAnalyzeInfo;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.effect.renders.DeviceWatermarkParam;
import com.android.camera.fragment.GoogleLensFragment;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.fragment.top.FragmentTopAlert;
import com.android.camera.fragment.top.FragmentTopConfig;
import com.android.camera.lib.compatibility.related.vibrator.ViberatorContext;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.CameraClickObservableImpl;
import com.android.camera.module.loader.FunctionParseAiScene;
import com.android.camera.module.loader.FunctionParseAsdFace;
import com.android.camera.module.loader.FunctionParseAsdHdr;
import com.android.camera.module.loader.FunctionParseAsdLivePhoto;
import com.android.camera.module.loader.FunctionParseAsdScene;
import com.android.camera.module.loader.FunctionParseAsdUltraWide;
import com.android.camera.module.loader.FunctionParseBeautyBodySlimCount;
import com.android.camera.module.loader.FunctionParseSuperNight;
import com.android.camera.module.loader.PredicateFilterAiScene;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.FocusManager2;
import com.android.camera.module.loader.camera2.FocusTask;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera.preferences.CameraSettingPreferences;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.scene.FunctionMiAlgoASDEngine;
import com.android.camera.scene.MiAlgoAsdSceneProfile;
import com.android.camera.statistic.CameraStat;
import com.android.camera.statistic.CameraStatUtil;
import com.android.camera.statistic.ScenarioTrackUtil;
import com.android.camera.storage.ImageSaver;
import com.android.camera.storage.Storage;
import com.android.camera.ui.ObjectView;
import com.android.camera.ui.RotateTextToast;
import com.android.camera.ui.zoom.ZoomingAction;
import com.android.camera.watermark.WaterMarkData;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CameraConfigs;
import com.android.camera2.CameraHardwareFace;
import com.android.camera2.CaptureResultParser;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import com.android.gallery3d.exif.ExifInterface;
import com.android.lens.LensAgent;
import com.android.zxing.PreviewDecodeManager;
import com.google.android.apps.photos.api.PhotosOemApi;
import com.google.lens.sdk.LensApi;
import com.mi.config.b;
import com.xiaomi.camera.base.CameraDeviceUtil;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.BaseBoostFramework;
import com.xiaomi.camera.core.MtkBoost;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.ParallelTaskDataParameter;
import com.xiaomi.camera.core.PictureInfo;
import com.xiaomi.camera.liveshot.CircularMediaRecorder;
import com.xiaomi.camera.liveshot.LivePhotoResult;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.GraphDescriptorBean;
import com.xiaomi.protocol.ISessionStatusCallBackListener;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@TargetApi(21)
public class Camera2Module extends BaseModule implements FocusManager2.Listener, ModeProtocol.CameraAction, ModeProtocol.CameraModuleSpecial, ModeProtocol.FilterProtocol, ModeProtocol.TopConfigProtocol, ObjectView.ObjectViewListener, Camera2Proxy.BeautyBodySlimCountCallback, Camera2Proxy.CameraMetaDataCallback, Camera2Proxy.CameraPreviewCallback, Camera2Proxy.FaceDetectionCallback, Camera2Proxy.FocusCallback, Camera2Proxy.HdrCheckerCallback, Camera2Proxy.LivePhotoResultCallback, Camera2Proxy.PictureCallback, Camera2Proxy.ScreenLightCallback, Camera2Proxy.SuperNightCallback, Camera2Proxy.UltraWideCheckCallback {
    private static final int BURST_SHOOTING_DELAY = 0;
    private static final long CAPTURE_DURATION_THRESHOLD = 12000;
    private static final float MOON_AF_DISTANCE = 0.5f;
    private static final int REQUEST_CROP = 1000;
    /* access modifiers changed from: private */
    public static final String TAG = Camera2Module.class.getSimpleName();
    private static boolean mIsBeautyFrontOn = false;
    private static final String sTempCropFilename = "crop-temp";
    /* access modifiers changed from: private */
    public float[] curGyroscope;
    private volatile boolean isDetectedInHdr;
    /* access modifiers changed from: private */
    public volatile boolean isResetFromMutex = false;
    private boolean isSilhouette;
    /* access modifiers changed from: private */
    public float[] lastGyroscope;
    private boolean m3ALocked;
    private int mAFEndLogTimes;
    private Disposable mAiSceneDisposable;
    private boolean mAiSceneEnabled;
    /* access modifiers changed from: private */
    public FlowableEmitter<CaptureResult> mAiSceneFlowableEmitter;
    /* access modifiers changed from: private */
    public String mAlgorithmName;
    private float[] mApertures;
    private MarshalQueryableASDScene.ASDScene[] mAsdScenes;
    private BeautyValues mBeautyValues;
    private boolean mBlockQuickShot = (!CameraSettings.isCameraQuickShotEnable());
    private Intent mBroadcastIntent;
    /* access modifiers changed from: private */
    public Disposable mBurstDisposable;
    /* access modifiers changed from: private */
    public ObservableEmitter mBurstEmitter;
    private long mBurstNextDelayTime = 0;
    /* access modifiers changed from: private */
    public long mBurstStartTime;
    private ModeProtocol.CameraClickObservable.ClickObserver mCameraClickObserverAction = new ModeProtocol.CameraClickObservable.ClickObserver() {
        public void action() {
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.directlyHideTips();
            }
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.alertAiDetectTipHint(8, 0, 0);
            }
        }

        public int getObserver() {
            return 161;
        }
    };
    private final Object mCameraDeviceLock = new Object();
    private long mCaptureStartTime;
    private String mCaptureWaterMarkStr;
    private CircularMediaRecorder mCircularMediaRecorder = null;
    private final Object mCircularMediaRecorderStateLock = new Object();
    private boolean mConfigRawStream;
    /* access modifiers changed from: private */
    public Disposable mCountdownDisposable;
    private BaseBoostFramework mCpuBoost;
    private String mCropValue;
    private int mCurrentAiScene;
    private int mCurrentAsdScene = -1;
    private int mCurrentDetectedScene;
    /* access modifiers changed from: private */
    public boolean mEnableParallelSession;
    private boolean mEnableShot2Gallery;
    private boolean mEnabledPreviewThumbnail;
    /* access modifiers changed from: private */
    public boolean mEnteringMoonMode;
    protected boolean mFaceDetected;
    private boolean mFaceDetectionEnabled;
    private boolean mFaceDetectionStarted;
    private FaceAnalyzeInfo mFaceInfo;
    private int mFilterId;
    private float[] mFocalLengths;
    /* access modifiers changed from: private */
    public FocusManager2 mFocusManager;
    private FunctionParseAiScene mFunctionParseAiScene;
    private boolean mHasAiSceneFilterEffect;
    private boolean mHdrCheckEnabled;
    private boolean mIsAiConflict;
    private boolean mIsBeautyBodySlimOn;
    private boolean mIsCurrentLensEnabled;
    private boolean mIsFaceConflict;
    private boolean mIsGenderAgeOn;
    private volatile boolean mIsGoogleLensAvailable;
    private boolean mIsGradienterOn;
    /* access modifiers changed from: private */
    public boolean mIsImageCaptureIntent;
    private boolean mIsLensServiceBound = false;
    private boolean mIsMagicMirrorOn;
    private boolean mIsMicrophoneEnabled = true;
    /* access modifiers changed from: private */
    public boolean mIsMoonMode;
    private boolean mIsPortraitLightingOn;
    private boolean mIsSaveCaptureImage;
    /* access modifiers changed from: private */
    public int mIsShowLyingDirectHintStatus = -1;
    private boolean mIsUltraWideConflict;
    /* access modifiers changed from: private */
    public int mJpegRotation;
    private boolean mKeepBitmapTexture;
    private long mLastAsdSceneShowTime = 0;
    private long mLastCaptureTime;
    private long mLastChangeSceneTime = 0;
    private String mLastFlashMode;
    private LensApi mLensApi;
    /* access modifiers changed from: private */
    public int mLensStatus = 1;
    private Queue<LivePhotoResult> mLivePhotoQueue = new LinkedBlockingQueue(120);
    private boolean mLiveShotEnabled;
    /* access modifiers changed from: private */
    public Location mLocation;
    private boolean mLongPressedAutoFocus;
    private Disposable mMetaDataDisposable;
    /* access modifiers changed from: private */
    public FlowableEmitter<CaptureResult> mMetaDataFlowableEmitter;
    private boolean mMotionDetected;
    /* access modifiers changed from: private */
    public boolean mMultiSnapStatus = false;
    /* access modifiers changed from: private */
    public boolean mMultiSnapStopRequest = false;
    private boolean mNeedAutoFocus;
    /* access modifiers changed from: private */
    public long mOnResumeTime;
    private int mOperatingMode;
    private boolean mPendingMultiCapture;
    private boolean mQuickCapture;
    private boolean mQuickShotAnimateEnable = false;
    /* access modifiers changed from: private */
    public int mReceivedJpegCallbackNum = 0;
    private Uri mSaveUri;
    private String mSceneMode;
    private SensorStateManager.SensorStateListener mSensorStateListener = new SensorStateManager.SensorStateListener() {
        private ModeProtocol.TopAlert mTopAlert;

        public boolean isWorking() {
            return Camera2Module.this.isAlive() && Camera2Module.this.getCameraState() != 0;
        }

        public void notifyDevicePostureChanged() {
        }

        public void onDeviceBecomeStable() {
        }

        public void onDeviceBeginMoving() {
            if (!Camera2Module.this.mPaused && CameraSettings.isEdgePhotoEnable()) {
                Camera2Module.this.mActivity.getEdgeShutterView().onDeviceMoving();
            }
        }

        public void onDeviceKeepMoving(double d) {
            if (!Camera2Module.this.mPaused && Camera2Module.this.mFocusManager != null && !Camera2Module.this.mMultiSnapStatus && !Camera2Module.this.is3ALocked() && !Camera2Module.this.mMainProtocol.isEvAdjusted(true) && !Camera2Module.this.mIsMoonMode) {
                Camera2Module.this.mFocusManager.onDeviceKeepMoving(d);
            }
        }

        public void onDeviceKeepStable() {
        }

        public void onDeviceLieChanged(boolean z) {
            if (!Camera2Module.this.mPaused && Camera2Module.this.mIsShowLyingDirectHintStatus != Camera2Module.this.mOrientationCompensation + (z ? 1 : 0)) {
                int unused = Camera2Module.this.mIsShowLyingDirectHintStatus = Camera2Module.this.mOrientationCompensation + z;
                Camera2Module.this.mHandler.removeMessages(58);
                if (this.mTopAlert == null) {
                    this.mTopAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                }
                if (this.mTopAlert != null ? this.mTopAlert.isContainAlertRecommendTip(R.string.dirty_tip_toast, R.string.pic_flaw_blink_one, R.string.pic_flaw_blink_more, R.string.pic_flaw_cover) : false) {
                    z = false;
                }
                if (z) {
                    Camera2Module.this.mHandler.sendMessageDelayed(Camera2Module.this.mHandler.obtainMessage(58, 1, Camera2Module.this.mOrientationCompensation), 400);
                    Camera2Module.this.mHandler.sendMessageDelayed(Camera2Module.this.mHandler.obtainMessage(58, 0, Camera2Module.this.mOrientationCompensation), 5000);
                    return;
                }
                Camera2Module.this.mHandler.sendMessageDelayed(Camera2Module.this.mHandler.obtainMessage(58, 0, Camera2Module.this.mOrientationCompensation), 500);
            }
        }

        public void onDeviceOrientationChanged(float f, boolean z) {
            Camera2Module.this.mDeviceRotation = !z ? f : (float) Camera2Module.this.mOrientation;
            if (Camera2Module.this.getCameraState() != 3 || CameraSettings.isGradienterOn()) {
                EffectController.getInstance().setDeviceRotation(z, Util.getShootRotation(Camera2Module.this.mActivity, Camera2Module.this.mDeviceRotation));
            }
            Camera2Module.this.mHandler.removeMessages(33);
            if (!Camera2Module.this.mPaused && !z && f != -1.0f) {
                int roundOrientation = Util.roundOrientation(Math.round(f), Camera2Module.this.mOrientation);
                Camera2Module.this.mHandler.obtainMessage(33, roundOrientation, (Util.getDisplayRotation(Camera2Module.this.mActivity) + roundOrientation) % MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT).sendToTarget();
            }
        }

        public void onDeviceRotationChanged(float[] fArr) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == 4) {
                float[] unused = Camera2Module.this.lastGyroscope = Camera2Module.this.curGyroscope;
                float[] unused2 = Camera2Module.this.curGyroscope = sensorEvent.values;
            }
        }
    };
    private LocalParallelService.ServiceStatusListener mServiceStatusListener;
    private ISessionStatusCallBackListener mSessionStatusCallbackListener = new ISessionStatusCallBackListener.Stub() {
        public void onSessionStatusFlawResultData(int i, final int i2) throws RemoteException {
            String access$300 = Camera2Module.TAG;
            Log.d(access$300, "resultId:" + i + ",flawResult:" + i2);
            if (1.0f == Camera2Module.this.getZoomRatio() && !CameraSettings.isMacroModeEnabled(Camera2Module.this.getModuleIndex())) {
                ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                if (bottomPopupTips == null || !bottomPopupTips.isQRTipVisible()) {
                    final FragmentTopConfig fragmentTopConfig = (FragmentTopConfig) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    if (fragmentTopConfig != null && fragmentTopConfig.isCurrentRecommendTipText(R.string.super_night_hint)) {
                        return;
                    }
                    if (Camera2Module.this.mCamera2Device == null || !Camera2Module.this.mCamera2Device.isCaptureBusy(true)) {
                        AndroidSchedulers.mainThread().scheduleDirect(new Runnable() {
                            public void run() {
                                switch (i2) {
                                    case 1:
                                        if (fragmentTopConfig != null) {
                                            fragmentTopConfig.alertAiDetectTipHint(0, R.string.pic_flaw_cover, 3000);
                                            return;
                                        }
                                        return;
                                    case 2:
                                        if (fragmentTopConfig != null) {
                                            fragmentTopConfig.alertAiDetectTipHint(0, R.string.pic_flaw_blink_one, 3000);
                                            return;
                                        }
                                        return;
                                    case 3:
                                        if (fragmentTopConfig != null) {
                                            fragmentTopConfig.alertAiDetectTipHint(0, R.string.pic_flaw_blink_more, 3000);
                                            return;
                                        }
                                        return;
                                    default:
                                        return;
                                }
                            }
                        });
                    }
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public int mShootOrientation;
    /* access modifiers changed from: private */
    public float mShootRotation;
    private boolean mShouldDoMFNR;
    /* access modifiers changed from: private */
    public boolean mShowSuperNightHint;
    private long mShutterCallbackTime;
    private long mShutterLag;
    private Disposable mSuperNightDisposable;
    private Consumer<Integer> mSuperNightEventConsumer;
    /* access modifiers changed from: private */
    public int mTotalJpegCallbackNum = 1;
    private volatile boolean mUltraWideAELocked;
    /* access modifiers changed from: private */
    public boolean mUpdateImageTitle = false;
    private CameraSize mVideoSize;
    private boolean mVolumeLongPress = false;
    /* access modifiers changed from: private */
    public volatile boolean mWaitingSnapshot;
    /* access modifiers changed from: private */
    public boolean mWaitingSuperNightResult;

    private static class AsdSceneConsumer implements Consumer<Integer> {
        private WeakReference<BaseModule> mModule;

        public AsdSceneConsumer(BaseModule baseModule) {
            this.mModule = new WeakReference<>(baseModule);
        }

        public void accept(Integer num) throws Exception {
            if (this.mModule != null && this.mModule.get() != null) {
                BaseModule baseModule = (BaseModule) this.mModule.get();
                if (baseModule instanceof Camera2Module) {
                    ((Camera2Module) baseModule).consumeAsdSceneResult(num.intValue());
                }
            }
        }
    }

    private final class JpegQuickPictureCallback extends Camera2Proxy.PictureCallbackWrapper {
        String mBurstShotTitle;
        boolean mDropped;
        Location mLocation;
        String mPressDownTitle;
        int mSavedJpegCallbackNum;

        public JpegQuickPictureCallback(Location location) {
            this.mLocation = location;
        }

        private String getBurstShotTitle() {
            String str;
            if (Camera2Module.this.mUpdateImageTitle && this.mBurstShotTitle != null && this.mSavedJpegCallbackNum == 1) {
                this.mPressDownTitle = this.mBurstShotTitle;
                this.mBurstShotTitle = null;
            }
            if (this.mBurstShotTitle == null) {
                long currentTimeMillis = System.currentTimeMillis();
                this.mBurstShotTitle = Util.createJpegName(currentTimeMillis);
                if (this.mBurstShotTitle.length() != 19) {
                    this.mBurstShotTitle = Util.createJpegName(currentTimeMillis + 1000);
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(this.mBurstShotTitle);
            if (Camera2Module.this.mMutexModePicker.isUbiFocus()) {
                str = Storage.UBIFOCUS_SUFFIX + (this.mSavedJpegCallbackNum - 1);
            } else {
                str = "_BURST" + this.mSavedJpegCallbackNum;
            }
            sb.append(str);
            return sb.toString();
        }

        public void onPictureTaken(byte[] bArr) {
            int height;
            int width;
            if (!Camera2Module.this.mPaused && bArr != null && Camera2Module.this.mReceivedJpegCallbackNum < Camera2Module.this.mTotalJpegCallbackNum && Camera2Module.this.mMultiSnapStatus) {
                if (this.mSavedJpegCallbackNum == 1 && !Camera2Module.this.mMultiSnapStopRequest && !Camera2Module.this.mMutexModePicker.isUbiFocus()) {
                    Camera2Module.this.mActivity.getImageSaver().updateImage(getBurstShotTitle(), this.mPressDownTitle);
                }
                boolean z = false;
                if (!Storage.isLowStorageAtLastPoint()) {
                    Camera2Module.access$804(Camera2Module.this);
                    if (!Camera2Module.this.mActivity.getImageSaver().isSaveQueueFull()) {
                        this.mSavedJpegCallbackNum++;
                        if (!Camera2Module.this.mMutexModePicker.isUbiFocus()) {
                            Camera2Module.this.playCameraSound(4);
                        }
                        ViberatorContext.getInstance(Camera2Module.this.getActivity().getApplicationContext()).performBurstCapture();
                        Camera2Module.this.mBurstEmitter.onNext(Integer.valueOf(this.mSavedJpegCallbackNum));
                        boolean z2 = Camera2Module.this.mMutexModePicker.isUbiFocus() && Camera2Module.this.mReceivedJpegCallbackNum <= Camera2Module.this.mTotalJpegCallbackNum;
                        int orientation = z2 ? 0 : Exif.getOrientation(bArr);
                        if ((Camera2Module.this.mJpegRotation + orientation) % 180 == 0) {
                            height = Camera2Module.this.mPictureSize.getWidth();
                            width = Camera2Module.this.mPictureSize.getHeight();
                        } else {
                            height = Camera2Module.this.mPictureSize.getHeight();
                            width = Camera2Module.this.mPictureSize.getWidth();
                        }
                        int i = height;
                        int i2 = width;
                        String burstShotTitle = getBurstShotTitle();
                        boolean z3 = Camera2Module.this.mMutexModePicker.isUbiFocus() && Camera2Module.this.mReceivedJpegCallbackNum == Camera2Module.this.mTotalJpegCallbackNum - 1;
                        if (!Camera2Module.this.mMutexModePicker.isUbiFocus() || Camera2Module.this.mReceivedJpegCallbackNum != Camera2Module.this.mTotalJpegCallbackNum) {
                            Camera2Module.this.mActivity.getImageSaver().addImage(bArr, (Camera2Module.this.mReceivedJpegCallbackNum != 1 || Camera2Module.this.mMultiSnapStopRequest) && (Camera2Module.this.mReceivedJpegCallbackNum == Camera2Module.this.mTotalJpegCallbackNum || Camera2Module.this.mMultiSnapStopRequest || this.mDropped), burstShotTitle, (String) null, System.currentTimeMillis(), (Uri) null, this.mLocation, i, i2, (ExifInterface) null, orientation, z2, z3, true, false, false, (String) null, Camera2Module.this.getPictureInfo(), -1);
                            this.mDropped = false;
                        }
                    } else {
                        Log.e(Camera2Module.TAG, "CaptureBurst queue full and drop " + Camera2Module.this.mReceivedJpegCallbackNum);
                        this.mDropped = true;
                        if (Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum) {
                            Camera2Module.this.mActivity.getThumbnailUpdater().getLastThumbnailUncached();
                        }
                    }
                    if (Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum || Camera2Module.this.mMultiSnapStopRequest || this.mDropped) {
                        Camera2Module.this.stopMultiSnap();
                    }
                } else if (!Camera2Module.this.mMutexModePicker.isUbiFocus() && Camera2Module.this.mMultiSnapStatus) {
                    Camera2Module.this.trackGeneralInfo(this.mSavedJpegCallbackNum, true);
                    CameraStat.PictureTakenParameter pictureTakenParameter = new CameraStat.PictureTakenParameter();
                    pictureTakenParameter.takenNum = this.mSavedJpegCallbackNum;
                    pictureTakenParameter.burst = true;
                    if (this.mLocation != null) {
                        z = true;
                    }
                    pictureTakenParameter.location = z;
                    pictureTakenParameter.aiSceneName = Camera2Module.this.getCurrentAiSceneName();
                    pictureTakenParameter.isEnteringMoon = Camera2Module.this.mEnteringMoonMode;
                    pictureTakenParameter.isSelectMoonMode = Camera2Module.this.mIsMoonMode;
                    pictureTakenParameter.isSuperNightInCaptureMode = Camera2Module.this.mShowSuperNightHint;
                    Camera2Module.this.trackPictureTaken(pictureTakenParameter);
                    Camera2Module.this.stopMultiSnap();
                }
            }
        }

        public void onPictureTakenFinished(boolean z) {
            Camera2Module.this.stopMultiSnap();
            Camera2Module.this.mBurstEmitter.onComplete();
        }
    }

    private final class JpegRepeatingCaptureCallback extends Camera2Proxy.PictureCallbackWrapper {
        String mBurstShotTitle;
        private boolean mDropped;
        private WeakReference<Camera2Module> mModule;
        ParallelTaskDataParameter mParallelParameter = null;
        String mPressDownTitle;

        public JpegRepeatingCaptureCallback(Camera2Module camera2Module) {
            this.mModule = new WeakReference<>(camera2Module);
        }

        private String getBurstShotTitle() {
            String str;
            if (Camera2Module.this.mUpdateImageTitle && this.mBurstShotTitle != null && Camera2Module.this.mReceivedJpegCallbackNum == 1) {
                this.mPressDownTitle = this.mBurstShotTitle;
                this.mBurstShotTitle = null;
            }
            if (this.mBurstShotTitle == null) {
                long currentTimeMillis = System.currentTimeMillis();
                this.mBurstShotTitle = Util.createJpegName(currentTimeMillis);
                if (this.mBurstShotTitle.length() != 19) {
                    this.mBurstShotTitle = Util.createJpegName(currentTimeMillis + 1000);
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(this.mBurstShotTitle);
            if (Camera2Module.this.mMutexModePicker.isUbiFocus()) {
                str = Storage.UBIFOCUS_SUFFIX + (Camera2Module.this.mReceivedJpegCallbackNum - 1);
            } else {
                str = "_BURST" + Camera2Module.this.mReceivedJpegCallbackNum;
            }
            sb.append(str);
            return sb.toString();
        }

        private boolean tryCheckNeedStop() {
            boolean z = false;
            if (!Storage.isLowStorageAtLastPoint()) {
                return false;
            }
            if (!Camera2Module.this.mMutexModePicker.isUbiFocus() && Camera2Module.this.mMultiSnapStatus) {
                Camera2Module.this.trackGeneralInfo(Camera2Module.this.mReceivedJpegCallbackNum, true);
                CameraStat.PictureTakenParameter pictureTakenParameter = new CameraStat.PictureTakenParameter();
                pictureTakenParameter.takenNum = Camera2Module.this.mReceivedJpegCallbackNum;
                pictureTakenParameter.burst = true;
                if (Camera2Module.this.mLocation != null) {
                    z = true;
                }
                pictureTakenParameter.location = z;
                pictureTakenParameter.aiSceneName = Camera2Module.this.getCurrentAiSceneName();
                pictureTakenParameter.isEnteringMoon = Camera2Module.this.mEnteringMoonMode;
                pictureTakenParameter.isSelectMoonMode = Camera2Module.this.mIsMoonMode;
                pictureTakenParameter.isSuperNightInCaptureMode = Camera2Module.this.mShowSuperNightHint;
                Camera2Module.this.trackPictureTaken(pictureTakenParameter);
                Camera2Module.this.stopMultiSnap();
            }
            return true;
        }

        /* JADX WARNING: Removed duplicated region for block: B:39:0x00fa  */
        /* JADX WARNING: Removed duplicated region for block: B:40:0x00fc  */
        /* JADX WARNING: Removed duplicated region for block: B:43:0x012a  */
        /* JADX WARNING: Removed duplicated region for block: B:44:0x0136  */
        /* JADX WARNING: Removed duplicated region for block: B:47:0x0141  */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x0146  */
        public ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, CameraSize cameraSize, boolean z) {
            ArrayList arrayList;
            boolean z2 = true;
            if (!Camera2Module.this.mEnableParallelSession || Camera2Module.this.mPaused || Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum || !Camera2Module.this.mMultiSnapStatus) {
                Log.d(Camera2Module.TAG, "onCaptureStart: revNum = " + Camera2Module.this.mReceivedJpegCallbackNum + " paused = " + Camera2Module.this.mPaused + " status = " + Camera2Module.this.mMultiSnapStatus);
                parallelTaskData.setAbandoned(true);
                return parallelTaskData;
            }
            if (Camera2Module.this.mReceivedJpegCallbackNum == 1 && !Camera2Module.this.mMultiSnapStopRequest) {
                if (!Camera2Module.this.is3ALocked()) {
                    Camera2Module.this.mFocusManager.onShutter();
                }
                if (!Camera2Module.this.mMutexModePicker.isUbiFocus()) {
                    Camera2Module.this.mActivity.getImageSaver().updateImage(getBurstShotTitle(), this.mPressDownTitle);
                }
            }
            if (tryCheckNeedStop()) {
                Log.d(Camera2Module.TAG, "onCaptureStart: need stop multi capture, return null");
                return null;
            }
            if (this.mParallelParameter == null) {
                if (CameraSettings.isAgeGenderAndMagicMirrorWaterOpen()) {
                    List<WaterMarkData> faceWaterMarkInfos = ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).getFaceWaterMarkInfos();
                    if (faceWaterMarkInfos != null && !faceWaterMarkInfos.isEmpty()) {
                        arrayList = new ArrayList(faceWaterMarkInfos);
                        if (Camera2Module.this.mOutPutSize == null || !Camera2Module.this.mOutPutSize.equals(cameraSize)) {
                            Camera2Module.this.mOutPutSize = cameraSize;
                        }
                        this.mParallelParameter = new ParallelTaskDataParameter.Builder(Camera2Module.this.mPreviewSize.toSizeObject(), cameraSize.toSizeObject(), Camera2Module.this.mOutPutSize.toSizeObject()).setHasDualWaterMark(CameraSettings.isDualCameraWaterMarkOpen()).setMirror(Camera2Module.this.isFrontMirror()).setLightingPattern(CameraSettings.getPortraitLightingPattern()).setFilterId(EffectController.getInstance().getEffectForSaving(false)).setOrientation(-1 != Camera2Module.this.mOrientation ? 0 : Camera2Module.this.mOrientation).setJpegRotation(Camera2Module.this.mJpegRotation).setShootRotation(Camera2Module.this.mShootRotation).setShootOrientation(Camera2Module.this.mShootOrientation).setLocation(Camera2Module.this.mLocation == null ? new Location(Camera2Module.this.mLocation) : null).setTimeWaterMarkString(!CameraSettings.isTimeWaterMarkOpen() ? Util.getTimeWatermark() : null).setFaceWaterMarkList(arrayList).setAgeGenderAndMagicMirrorWater(CameraSettings.isAgeGenderAndMagicMirrorWaterOpen()).setFrontCamera(Camera2Module.this.isFrontCamera()).setBokehFrontCamera(Camera2Module.this.isBokehFrontCamera()).setAlgorithmName(Camera2Module.this.mAlgorithmName).setPictureInfo(Camera2Module.this.getPictureInfo()).setSuffix(Camera2Module.this.getSuffix()).setTiltShiftMode(Camera2Module.getTiltShiftMode()).setSaveGroupshotPrimitive(false).setDeviceWatermarkParam(Camera2Module.this.getDeviceWaterMarkParam()).setJpegQuality(BaseModule.getJpegQuality(true)).build();
                    }
                }
                arrayList = null;
                Camera2Module.this.mOutPutSize = cameraSize;
                this.mParallelParameter = new ParallelTaskDataParameter.Builder(Camera2Module.this.mPreviewSize.toSizeObject(), cameraSize.toSizeObject(), Camera2Module.this.mOutPutSize.toSizeObject()).setHasDualWaterMark(CameraSettings.isDualCameraWaterMarkOpen()).setMirror(Camera2Module.this.isFrontMirror()).setLightingPattern(CameraSettings.getPortraitLightingPattern()).setFilterId(EffectController.getInstance().getEffectForSaving(false)).setOrientation(-1 != Camera2Module.this.mOrientation ? 0 : Camera2Module.this.mOrientation).setJpegRotation(Camera2Module.this.mJpegRotation).setShootRotation(Camera2Module.this.mShootRotation).setShootOrientation(Camera2Module.this.mShootOrientation).setLocation(Camera2Module.this.mLocation == null ? new Location(Camera2Module.this.mLocation) : null).setTimeWaterMarkString(!CameraSettings.isTimeWaterMarkOpen() ? Util.getTimeWatermark() : null).setFaceWaterMarkList(arrayList).setAgeGenderAndMagicMirrorWater(CameraSettings.isAgeGenderAndMagicMirrorWaterOpen()).setFrontCamera(Camera2Module.this.isFrontCamera()).setBokehFrontCamera(Camera2Module.this.isBokehFrontCamera()).setAlgorithmName(Camera2Module.this.mAlgorithmName).setPictureInfo(Camera2Module.this.getPictureInfo()).setSuffix(Camera2Module.this.getSuffix()).setTiltShiftMode(Camera2Module.getTiltShiftMode()).setSaveGroupshotPrimitive(false).setDeviceWatermarkParam(Camera2Module.this.getDeviceWaterMarkParam()).setJpegQuality(BaseModule.getJpegQuality(true)).build();
            }
            parallelTaskData.fillParameter(this.mParallelParameter);
            if (!Camera2Module.this.mActivity.getImageSaver().isSaveQueueFull()) {
                Camera2Module.access$804(Camera2Module.this);
                if (!Camera2Module.this.mMutexModePicker.isUbiFocus()) {
                    Camera2Module.this.playCameraSound(4);
                }
                ViberatorContext.getInstance(Camera2Module.this.getActivity().getApplicationContext()).performBurstCapture();
                Log.d(Camera2Module.TAG, "onCaptureStart: revNum = " + Camera2Module.this.mReceivedJpegCallbackNum);
                Camera2Module.this.mBurstEmitter.onNext(Integer.valueOf(Camera2Module.this.mReceivedJpegCallbackNum));
                if (!Camera2Module.this.mMutexModePicker.isUbiFocus() && Camera2Module.this.mReceivedJpegCallbackNum <= Camera2Module.this.mTotalJpegCallbackNum) {
                    String generateFilepath4Jpeg = Storage.generateFilepath4Jpeg(getBurstShotTitle());
                    Log.d(Camera2Module.TAG, "onCaptureStart: savePath = " + generateFilepath4Jpeg);
                    parallelTaskData.setSavePath(generateFilepath4Jpeg);
                    if (Camera2Module.this.mReceivedJpegCallbackNum != Camera2Module.this.mTotalJpegCallbackNum && !Camera2Module.this.mMultiSnapStopRequest && !this.mDropped) {
                        z2 = false;
                    }
                    parallelTaskData.setNeedThumbnail(z2);
                    Camera2Module.this.beginParallelProcess(parallelTaskData, false);
                    this.mDropped = false;
                    if (Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum || Camera2Module.this.mMultiSnapStopRequest || this.mDropped) {
                        Camera2Module.this.stopMultiSnap();
                    }
                    return parallelTaskData;
                }
            } else {
                Log.e(Camera2Module.TAG, "onCaptureStart: queue full and drop " + Camera2Module.this.mReceivedJpegCallbackNum);
                this.mDropped = true;
                if (Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum) {
                    Camera2Module.this.mActivity.getThumbnailUpdater().getLastThumbnailUncached();
                }
            }
            parallelTaskData = null;
            Camera2Module.this.stopMultiSnap();
            return parallelTaskData;
        }

        public void onPictureTakenFinished(boolean z) {
            if (this.mModule.get() != null) {
                ((Camera2Module) this.mModule.get()).onBurstPictureTakenFinished(z);
            } else {
                Log.e(Camera2Module.TAG, "callback onShotFinished null");
            }
        }
    }

    private static class MainHandler extends Handler {
        private WeakReference<Camera2Module> mModule;

        public MainHandler(Camera2Module camera2Module, Looper looper) {
            super(looper);
            this.mModule = new WeakReference<>(camera2Module);
        }

        public void handleMessage(Message message) {
            Camera2Module camera2Module = (Camera2Module) this.mModule.get();
            if (camera2Module != null) {
                if (!camera2Module.isCreated()) {
                    removeCallbacksAndMessages((Object) null);
                } else if (camera2Module.getActivity() != null) {
                    boolean z = false;
                    switch (message.what) {
                        case 2:
                            camera2Module.getWindow().clearFlags(128);
                            return;
                        case 4:
                            camera2Module.checkActivityOrientation();
                            if (SystemClock.uptimeMillis() - camera2Module.mOnResumeTime < 5000) {
                                sendEmptyMessageDelayed(4, 100);
                                return;
                            }
                            return;
                        case 9:
                            camera2Module.mMainProtocol.initializeFocusView(camera2Module);
                            camera2Module.mMainProtocol.setObjectViewListener(camera2Module);
                            return;
                        case 10:
                            if (!camera2Module.mActivity.isActivityPaused()) {
                                camera2Module.mOpenCameraFail = true;
                                camera2Module.onCameraException();
                                return;
                            }
                            return;
                        case 11:
                            return;
                        case 17:
                            removeMessages(17);
                            removeMessages(2);
                            camera2Module.getWindow().addFlags(128);
                            sendEmptyMessageDelayed(2, (long) camera2Module.getScreenDelay());
                            return;
                        case 31:
                            camera2Module.setOrientationParameter();
                            return;
                        case 33:
                            camera2Module.setOrientation(message.arg1, message.arg2);
                            return;
                        case 35:
                            boolean z2 = message.arg1 > 0;
                            if (message.arg2 > 0) {
                                z = true;
                            }
                            camera2Module.handleUpdateFaceView(z2, z);
                            return;
                        case 44:
                            camera2Module.restartModule();
                            return;
                        case 45:
                            camera2Module.setActivity((Camera) null);
                            return;
                        case 48:
                            camera2Module.setCameraState(1);
                            return;
                        case 49:
                            if (camera2Module.isAlive()) {
                                camera2Module.stopMultiSnap();
                                camera2Module.mBurstEmitter.onComplete();
                                return;
                            }
                            return;
                        case 50:
                            Log.w(Camera2Module.TAG, "Oops, capture timeout later release timeout!");
                            camera2Module.onPictureTakenFinished(false);
                            return;
                        case 51:
                            if (!camera2Module.mActivity.isActivityPaused()) {
                                camera2Module.mOpenCameraFail = true;
                                camera2Module.onCameraException();
                                return;
                            }
                            return;
                        case 52:
                            camera2Module.onShutterButtonClick(camera2Module.getTriggerMode());
                            return;
                        case 56:
                            if (camera2Module.mMainProtocol != null && camera2Module.mMainProtocol.isFaceExists(1) && camera2Module.mMainProtocol.isFocusViewVisible() && camera2Module.mCamera2Device != null && 4 == camera2Module.mCamera2Device.getFocusMode()) {
                                camera2Module.mMainProtocol.clearFocusView(7);
                                return;
                            }
                            return;
                        case 57:
                            PreviewDecodeManager.getInstance().reset();
                            return;
                        case 58:
                            ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                            if (configChanges != null) {
                                configChanges.configRotationChange(message.arg1, (360 - (message.arg2 >= 0 ? message.arg2 % MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT : (message.arg2 % MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT) + MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT)) % MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT);
                                return;
                            }
                            return;
                        case 60:
                            Log.d(Camera2Module.TAG, "fallback timeout");
                            camera2Module.mIsSatFallback = false;
                            camera2Module.mFallbackProcessed = false;
                            camera2Module.mLastSatFallbackRequestId = -1;
                            if (camera2Module.mWaitingSnapshot && camera2Module.getCameraState() == 1) {
                                boolean unused = camera2Module.mWaitingSnapshot = false;
                                sendEmptyMessage(52);
                                return;
                            }
                            return;
                        default:
                            throw new RuntimeException("no consumer for this message: " + message.what);
                    }
                }
            }
        }
    }

    private static class SuperNightEventConsumer implements Consumer<Integer> {
        private final WeakReference<Camera2Module> mCamera2ModuleRef;

        private SuperNightEventConsumer(Camera2Module camera2Module) {
            this.mCamera2ModuleRef = new WeakReference<>(camera2Module);
        }

        public void accept(Integer num) throws Exception {
            Camera2Module camera2Module = (Camera2Module) this.mCamera2ModuleRef.get();
            if (camera2Module != null && camera2Module.isAlive()) {
                int intValue = num.intValue();
                if (intValue == 300) {
                    Log.d(Camera2Module.TAG, "SuperNight: show capture instruction hint");
                    ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                    if (bottomPopupTips != null) {
                        bottomPopupTips.showTips(11, (int) R.string.super_night_toast, 4);
                    }
                } else if (intValue == 2000) {
                    Log.d(Camera2Module.TAG, "SuperNight: trigger shutter animation, sound and post saving");
                    boolean unused = camera2Module.mWaitingSuperNightResult = true;
                    camera2Module.animateCapture();
                    camera2Module.playCameraSound(0);
                    ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
                    if (recordState != null) {
                        recordState.onPostSavingStart();
                    }
                }
            }
        }
    }

    static /* synthetic */ int access$804(Camera2Module camera2Module) {
        int i = camera2Module.mReceivedJpegCallbackNum + 1;
        camera2Module.mReceivedJpegCallbackNum = i;
        return i;
    }

    /* access modifiers changed from: private */
    public void animateCapture() {
        if (!this.mIsImageCaptureIntent) {
            this.mActivity.getCameraScreenNail().animateCapture(getCameraRotation());
        }
    }

    private void applyBacklightEffect() {
        trackAISceneChanged(this.mModuleIndex, 23);
        setAiSceneEffect(23);
        updateHDR("normal");
        this.mCamera2Device.setASDScene(23);
        resetEvValue();
    }

    /* access modifiers changed from: private */
    public void beginParallelProcess(ParallelTaskData parallelTaskData, boolean z) {
        String str = TAG;
        Log.i(str, "algo begin: " + parallelTaskData.getSavePath() + " | " + Thread.currentThread().getName());
        if (this.mServiceStatusListener == null) {
            this.mServiceStatusListener = new LocalParallelService.ServiceStatusListener() {
                public void onImagePostProcessEnd(ParallelTaskData parallelTaskData) {
                    if (parallelTaskData != null && parallelTaskData.isJpegDataReady() && Camera2Module.this.mIsImageCaptureIntent) {
                        Camera2Module.this.onPictureTakenFinished(true);
                    }
                }

                public void onImagePostProcessStart(ParallelTaskData parallelTaskData) {
                    if (4 != parallelTaskData.getAlgoType()) {
                        if (!Camera2Module.this.mIsImageCaptureIntent) {
                            Camera2Module.this.onPictureTakenFinished(true);
                        }
                        PerformanceTracker.trackPictureCapture(1);
                        if (Camera2Module.this.mCamera2Device != null) {
                            Camera2Module.this.mCamera2Device.onParallelImagePostProcStart();
                        } else if (Camera2Module.this.mActivity != null) {
                            Camera2Module.this.mActivity.removeShotAfterPictureTaken();
                        }
                    }
                }
            };
            AlgoConnector.getInstance().setServiceStatusListener(this.mServiceStatusListener);
        }
    }

    private long calculateTimeout(int i) {
        if (i == 167) {
            return (Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))) / 1000000) + CAPTURE_DURATION_THRESHOLD;
        }
        if (i == 173 || CameraSettings.isSuperNightOn()) {
            return 24000;
        }
        return CAPTURE_DURATION_THRESHOLD;
    }

    private void checkMoreFrameCaptureLockAFAE(boolean z) {
        if (this.mCamera2Device == null) {
            Log.w(TAG, "mCamera2Device == null, return");
        } else if (DataRepository.dataItemFeature().hM()) {
            if ((!ModuleManager.isSuperNightScene() && !this.mShowSuperNightHint && !this.mMutexModePicker.isHdr()) || is3ALocked()) {
                return;
            }
            if (z) {
                Log.d(TAG, "more frame capture lock af/ae");
                this.mCamera2Device.setFocusMode(1);
                this.mCamera2Device.lockExposure(true, true);
                return;
            }
            Log.d(TAG, "more frame capture unlock af/ae");
            updateFocusMode();
            this.mCamera2Device.unlockExposure();
            resumePreview();
        }
    }

    private void checkSatFallback(CaptureResult captureResult) {
        boolean isSatFallbackDetected = CaptureResultParser.isSatFallbackDetected(captureResult);
        if (this.mLastSatFallbackRequestId == -1 && isSatFallbackDetected && !this.mWaitingSnapshot) {
            int sendSatFallbackRequest = this.mCamera2Device.sendSatFallbackRequest();
            String str = TAG;
            Log.d(str, "checkSatFallback: lastFallbackRequestId = " + sendSatFallbackRequest);
            if (sendSatFallbackRequest >= 0) {
                this.mIsSatFallback = true;
                this.mFallbackProcessed = false;
                this.mLastSatFallbackRequestId = sendSatFallbackRequest;
                this.mHandler.removeMessages(60);
                this.mHandler.sendEmptyMessageDelayed(60, 1500);
            }
        } else if (this.mLastSatFallbackRequestId >= 0) {
            if (this.mLastSatFallbackRequestId <= captureResult.getSequenceId()) {
                this.mFallbackProcessed = true;
            }
            String str2 = TAG;
            Log.d(str2, "checkSatFallback: fallbackDetected = " + isSatFallbackDetected + " requestId = " + captureResult.getSequenceId() + "|" + captureResult.getFrameNumber());
            if (this.mFallbackProcessed && !isSatFallbackDetected) {
                this.mIsSatFallback = false;
                this.mFallbackProcessed = false;
                this.mLastSatFallbackRequestId = -1;
                this.mHandler.removeMessages(60);
                if (this.mWaitingSnapshot && getCameraState() == 1) {
                    this.mWaitingSnapshot = false;
                    this.mHandler.sendEmptyMessage(52);
                }
            }
        }
    }

    private boolean checkShutterCondition() {
        if (isBlockSnap() || isIgnoreTouchEvent()) {
            String str = TAG;
            Log.w(str, "checkShutterCondition: blockSnap=" + isBlockSnap() + " ignoreTouchEvent=" + isIgnoreTouchEvent());
            return false;
        } else if (Storage.isLowStorageAtLastPoint()) {
            Log.w(TAG, "checkShutterCondition: low storage");
            return false;
        } else if (isFrontCamera() && this.mActivity.isScreenSlideOff()) {
            Log.w(TAG, "checkShutterCondition: screen is slide off");
            return false;
        } else if (!this.mIsSatFallback || !shouldCheckSatFallbackState()) {
            this.mWaitingSnapshot = false;
            ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
            if (backStack != null) {
                backStack.handleBackStackFromShutter();
            }
            return true;
        } else {
            this.mWaitingSnapshot = true;
            Log.w(TAG, "checkShutterCondition: sat fallback");
            return false;
        }
    }

    private void configParallelSession() {
        GraphDescriptorBean graphDescriptorBean;
        if (isPortraitMode()) {
            int i = (isDualFrontCamera() || isDualCamera() || isBokehUltraWideBackCamera()) ? 2 : 1;
            String str = TAG;
            Log.d(str, "configParallelSession: inputStreamNum = " + i);
            graphDescriptorBean = new GraphDescriptorBean(32770, i, true, CameraDeviceUtil.getCameraCombinationMode(this.mActualCameraId));
        } else if (this.mModuleIndex == 167) {
            graphDescriptorBean = new GraphDescriptorBean(32771, 1, true, CameraDeviceUtil.getCameraCombinationMode(this.mActualCameraId));
        } else if (this.mModuleIndex == 175) {
            graphDescriptorBean = new GraphDescriptorBean(33011, 1, true, CameraDeviceUtil.getCameraCombinationMode(this.mActualCameraId));
        } else {
            int cameraCombinationMode = CameraDeviceUtil.getCameraCombinationMode(this.mActualCameraId);
            if (cameraCombinationMode == 0) {
                cameraCombinationMode = 513;
            }
            graphDescriptorBean = new GraphDescriptorBean(0, 1, true, cameraCombinationMode);
        }
        String str2 = TAG;
        Log.d(str2, "configParallelSession: pictureSize = " + this.mPictureSize);
        String str3 = TAG;
        Log.d(str3, "configParallelSession: outputSize = " + this.mOutPutSize);
        BufferFormat bufferFormat = new BufferFormat(this.mPictureSize.width, this.mPictureSize.height, 35, graphDescriptorBean);
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder(true);
        localBinder.configCaptureSession(bufferFormat);
        localBinder.setImageSaver(this.mActivity.getImageSaver());
        localBinder.setJpegOutputSize(this.mOutPutSize.width, this.mOutPutSize.height);
        localBinder.setCallerIdentity(this.mActivity.hashCode());
        localBinder.setSRRequireReprocess(DataRepository.dataItemFeature().isSRRequireReprocess());
    }

    /* access modifiers changed from: private */
    public void consumeAiSceneResult(int i, boolean z) {
        if (this.mAiSceneEnabled) {
            realConsumeAiSceneResult(i, z);
            if (!(this.mCurrentAiScene == -1 || this.mCurrentAiScene == 23 || this.mCurrentAiScene == 24 || this.mCurrentAiScene == 35)) {
                this.mCamera2Device.setASDScene(0);
            }
            resumePreviewInWorkThread();
        }
    }

    /* access modifiers changed from: private */
    public void consumeAsdSceneResult(int i) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLastAsdSceneShowTime > 500 && this.mCurrentAsdScene != i && !isDoingAction() && isAlive() && !this.mActivity.isActivityPaused()) {
            exitAsdScene(this.mCurrentAsdScene);
            enterAsdScene(i);
            this.mCurrentAsdScene = i;
            this.mLastAsdSceneShowTime = currentTimeMillis;
        }
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:57:0x011b=Splitter:B:57:0x011b, B:52:0x010c=Splitter:B:52:0x010c} */
    private void doAttach() {
        if (!this.mPaused) {
            byte[] storedJpegData = this.mActivity.getImageSaver().getStoredJpegData();
            if (this.mIsSaveCaptureImage) {
                this.mActivity.getImageSaver().saveStoredData();
            }
            OutputStream outputStream = null;
            if (this.mCropValue != null) {
                try {
                    File fileStreamPath = this.mActivity.getFileStreamPath(sTempCropFilename);
                    fileStreamPath.delete();
                    FileOutputStream openFileOutput = this.mActivity.openFileOutput(sTempCropFilename, 0);
                    try {
                        openFileOutput.write(storedJpegData);
                        openFileOutput.close();
                        Uri fromFile = Uri.fromFile(fileStreamPath);
                        Util.closeSilently((Closeable) null);
                        Bundle bundle = new Bundle();
                        if (ComponentRunningTiltValue.TILT_CIRCLE.equals(this.mCropValue)) {
                            bundle.putString("circleCrop", "true");
                        }
                        if (this.mSaveUri != null) {
                            bundle.putParcelable("output", this.mSaveUri);
                        } else {
                            bundle.putBoolean("return-data", true);
                        }
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setData(fromFile);
                        intent.putExtras(bundle);
                        this.mActivity.startActivityForResult(intent, 1000);
                    } catch (FileNotFoundException e) {
                        outputStream = openFileOutput;
                    } catch (IOException e2) {
                        outputStream = openFileOutput;
                        try {
                            this.mActivity.setResult(0);
                            this.mActivity.finish();
                            Util.closeSilently(outputStream);
                            return;
                        } catch (Throwable th) {
                            th = th;
                            Util.closeSilently(outputStream);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        outputStream = openFileOutput;
                        Util.closeSilently(outputStream);
                        throw th;
                    }
                } catch (FileNotFoundException e3) {
                    this.mActivity.setResult(0);
                    this.mActivity.finish();
                    Util.closeSilently(outputStream);
                    return;
                } catch (IOException e4) {
                    this.mActivity.setResult(0);
                    this.mActivity.finish();
                    Util.closeSilently(outputStream);
                    return;
                }
            } else if (this.mSaveUri != null) {
                try {
                    OutputStream openOutputStream = CameraAppImpl.getAndroidContext().getContentResolver().openOutputStream(this.mSaveUri);
                    try {
                        openOutputStream.write(storedJpegData);
                        openOutputStream.close();
                        this.mActivity.setResult(-1);
                        this.mActivity.finish();
                        Util.closeSilently(openOutputStream);
                    } catch (Exception e5) {
                        e = e5;
                        outputStream = openOutputStream;
                        try {
                            Log.e(TAG, "Exception when doAttach: ", e);
                            this.mActivity.finish();
                            Util.closeSilently(outputStream);
                            this.mActivity.getImageSaver().releaseStoredJpegData();
                        } catch (Throwable th3) {
                            th = th3;
                            this.mActivity.finish();
                            Util.closeSilently(outputStream);
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        outputStream = openOutputStream;
                        this.mActivity.finish();
                        Util.closeSilently(outputStream);
                        throw th;
                    }
                } catch (Exception e6) {
                    e = e6;
                    Log.e(TAG, "Exception when doAttach: ", e);
                    this.mActivity.finish();
                    Util.closeSilently(outputStream);
                    this.mActivity.getImageSaver().releaseStoredJpegData();
                }
            } else {
                this.mActivity.setResult(-1, new Intent("inline-data").putExtra(PhotosOemApi.PATH_SPECIAL_TYPE_DATA, Util.rotate(Util.makeBitmap(storedJpegData, 51200), Exif.getOrientation(storedJpegData))));
                this.mActivity.finish();
            }
            this.mActivity.getImageSaver().releaseStoredJpegData();
        }
    }

    private void doLaterReleaseIfNeed() {
        if (this.mActivity == null) {
            Log.w(TAG, "doLaterReleaseIfNeed: mActivity is null...");
            return;
        }
        if (this.mHandler != null) {
            this.mHandler.removeMessages(50);
        }
        if (this.mActivity.isActivityPaused()) {
            boolean z = this.mCamera2Device != null && this.mCamera2Device.isSessionReady();
            if (z) {
                Log.d(TAG, "doLaterRelease");
            } else {
                Log.d(TAG, "doLaterRelease but session is closed");
            }
            this.mActivity.releaseAll(true, z);
        } else if (isDeparted()) {
            Log.w(TAG, "doLaterReleaseIfNeed: isDeparted...");
        } else {
            this.mHandler.post(new Runnable() {
                public final void run() {
                    Camera2Module.this.handlePendingScreenSlide();
                }
            });
            if (isTextureExpired()) {
                Log.d(TAG, "doLaterReleaseIfNeed: surfaceTexture expired, restartModule");
                this.mHandler.post(new Runnable() {
                    public final void run() {
                        Camera2Module.this.restartModule();
                    }
                });
            }
        }
    }

    private boolean enableFrontMFNR() {
        if (b.isMTKPlatform()) {
            return b.km() && DataRepository.dataItemFeature().he();
        }
        if (!b.km()) {
            return false;
        }
        if (this.mOperatingMode != 32773) {
            if (!DataRepository.dataItemFeature().he()) {
                return false;
            }
            if (this.mOperatingMode != 36865 && (!"tucana".equals(b.rp) || this.mOperatingMode != 36867)) {
                return false;
            }
        }
        return true;
    }

    private boolean enablePreviewAsThumbnail() {
        if (!isAlive() || CameraSettings.isUltraPixelOn() || this.mModuleIndex == 175) {
            return false;
        }
        if (this.mEnableParallelSession) {
            return true;
        }
        if (this.mIsPortraitLightingOn) {
            return false;
        }
        if (!CameraSettings.isLiveShotOn() && !CameraSettings.isPortraitModeBackOn()) {
            return this.mModuleIndex != 167 && this.mModuleIndex != 173 && !CameraSettings.isSuperNightOn() && !CameraSettings.showGenderAge() && !CameraSettings.isMagicMirrorOn() && !CameraSettings.isTiltShiftOn() && this.mCamera2Device != null && this.mCamera2Device.isNeedPreviewThumbnail();
        }
        return true;
    }

    private void enterAsdScene(int i) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (i == 0) {
            topAlert.alertFlash(0, "1", false);
        } else if (i != 9) {
            switch (i) {
                case 4:
                    if (!this.m3ALocked) {
                        updateTipMessage(6, R.string.portrait_mode_too_close_hint, 4);
                        return;
                    }
                    return;
                case 5:
                    if (!this.m3ALocked) {
                        updateTipMessage(6, R.string.portrait_mode_too_far_hint, 4);
                        return;
                    }
                    return;
                case 6:
                    if (!this.m3ALocked) {
                        updateTipMessage(6, R.string.portrait_mode_lowlight_hint, 4);
                        return;
                    }
                    return;
                case 7:
                    if (!this.m3ALocked) {
                        setPortraitSuccessHintVisible(0);
                        return;
                    }
                    return;
                default:
                    return;
            }
        } else {
            String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
            if ("3".equals(componentValue)) {
                topAlert.alertFlash(0, "1", false);
                updatePreferenceInWorkThread(10);
            } else if (ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO.equals(componentValue)) {
                topAlert.alertFlash(0, "1", false);
                Log.d(TAG, "enterAsdScene(): turn off HDR as FLASH has higher priority than HDR");
                onHdrSceneChanged(false);
                updatePreferenceInWorkThread(10);
            }
        }
    }

    private void exitAsdScene(int i) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (i == 0) {
            String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
            if (!"1".equals(componentValue) && !ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_ON.equals(componentValue) && !"2".equals(componentValue) && !"5".equals(componentValue)) {
                topAlert.alertFlash(8, "1", false);
            }
        } else if (i != 9) {
            switch (i) {
                case 4:
                case 5:
                case 6:
                    if (!this.m3ALocked) {
                        hideTipMessage(0);
                        return;
                    }
                    return;
                case 7:
                    if (!this.m3ALocked) {
                        setPortraitSuccessHintVisible(8);
                        return;
                    }
                    return;
                default:
                    return;
            }
        } else {
            String componentValue2 = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
            if ("3".equals(componentValue2) || ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO.equals(componentValue2)) {
                topAlert.alertFlash(8, "1", false);
            }
            updatePreferenceInWorkThread(10);
        }
    }

    /* access modifiers changed from: private */
    public void finishSuperNightState(boolean z) {
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            if (z) {
                animateCapture();
                playCameraSound(0);
                recordState.onPostSavingStart();
            }
            recordState.onPostSavingFinish();
        }
    }

    private String getCalibrationDataFileName(int i) {
        return isFrontCamera() ? "front_dual_camera_caldata.bin" : i == Camera2DataContainer.getInstance().getUltraWideBokehCameraId() ? "back_dual_camera_caldata_wu.bin" : "back_dual_camera_caldata.bin";
    }

    private int getCountDownTimes(int i) {
        int timerDurationSeconds = this.mBroadcastIntent != null ? CameraIntentManager.getInstance(this.mBroadcastIntent).getTimerDurationSeconds() : this.mActivity.getCameraIntentManager().getTimerDurationSeconds();
        if (timerDurationSeconds != -1) {
            if (this.mBroadcastIntent != null) {
                this.mBroadcastIntent.removeExtra(CameraIntentManager.CameraExtras.TIMER_DURATION_SECONDS);
            } else {
                this.mActivity.getIntent().removeExtra(CameraIntentManager.CameraExtras.TIMER_DURATION_SECONDS);
            }
            if (timerDurationSeconds != 0) {
                return timerDurationSeconds != 5 ? 3 : 5;
            }
            return 0;
        } else if (i != 100 || !CameraSettings.isHandGestureOpen()) {
            return CameraSettings.getCountDownTimes();
        } else {
            int countDownTimes = CameraSettings.getCountDownTimes();
            if (countDownTimes != 0) {
                return countDownTimes;
            }
            return 3;
        }
    }

    /* access modifiers changed from: private */
    public String getCurrentAiSceneName() {
        int i = this.mCurrentAiScene;
        if (this.mModuleIndex != 163 && this.mModuleIndex != 167) {
            return null;
        }
        if (!CameraSettings.getAiSceneOpen(this.mModuleIndex)) {
            return "off";
        }
        if (i == -1) {
            i = this.isSilhouette ? 24 : 23;
        }
        TypedArray obtainTypedArray = getResources().obtainTypedArray(R.array.ai_scene_names);
        String string = (i < 0 || i >= obtainTypedArray.length()) ? "未知" : obtainTypedArray.getString(i);
        obtainTypedArray.recycle();
        return string;
    }

    /* access modifiers changed from: private */
    public DeviceWatermarkParam getDeviceWaterMarkParam() {
        float f;
        float f2;
        float f3;
        float resourceFloat;
        float resourceFloat2;
        float resourceFloat3;
        boolean isDualCameraWaterMarkOpen = CameraSettings.isDualCameraWaterMarkOpen();
        boolean isFrontCameraWaterMarkOpen = CameraSettings.isFrontCameraWaterMarkOpen();
        if (isDualCameraWaterMarkOpen) {
            resourceFloat = CameraSettings.getResourceFloat(R.dimen.dualcamera_watermark_size_ratio, 0.0f);
            resourceFloat2 = CameraSettings.getResourceFloat(R.dimen.dualcamera_watermark_padding_x_ratio, 0.0f);
            resourceFloat3 = CameraSettings.getResourceFloat(R.dimen.dualcamera_watermark_padding_y_ratio, 0.0f);
        } else if (isFrontCameraWaterMarkOpen) {
            resourceFloat = CameraSettings.getResourceFloat(R.dimen.global_frontcamera_watermark_size_ratio, 0.0f);
            if (!Util.isGlobalVersion() || resourceFloat == 0.0f) {
                resourceFloat = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_size_ratio, 0.0f);
            }
            resourceFloat2 = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_padding_x_ratio, 0.0f);
            resourceFloat3 = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_padding_y_ratio, 0.0f);
        } else {
            f3 = 0.0f;
            f2 = 0.0f;
            f = 0.0f;
            DeviceWatermarkParam deviceWatermarkParam = new DeviceWatermarkParam(isDualCameraWaterMarkOpen, isFrontCameraWaterMarkOpen, CameraSettings.isUltraPixelRearOn(), CameraSettings.isMiMovieOpen(this.mModuleIndex), CameraSettings.getDualCameraWaterMarkFilePathVendor(), f3, f2, f);
            return deviceWatermarkParam;
        }
        f = resourceFloat3;
        f3 = resourceFloat;
        f2 = resourceFloat2;
        DeviceWatermarkParam deviceWatermarkParam2 = new DeviceWatermarkParam(isDualCameraWaterMarkOpen, isFrontCameraWaterMarkOpen, CameraSettings.isUltraPixelRearOn(), CameraSettings.isMiMovieOpen(this.mModuleIndex), CameraSettings.getDualCameraWaterMarkFilePathVendor(), f3, f2, f);
        return deviceWatermarkParam2;
    }

    private String getManualValue(String str, String str2) {
        return ModuleManager.isManualModule() ? CameraSettingPreferences.instance().getString(str, str2) : str2;
    }

    /* access modifiers changed from: private */
    public PictureInfo getPictureInfo() {
        PictureInfo opMode = new PictureInfo().setFrontMirror(isFrontMirror()).setSensorType(isFrontCamera()).setBokehFrontCamera(isBokehFrontCamera()).setHdrType(DataRepository.dataItemConfig().getComponentHdr().getComponentValue(this.mModuleIndex)).setOpMode(getOperatingMode());
        opMode.setAiEnabled(this.mAiSceneEnabled);
        opMode.setAiType(this.mCurrentAiScene);
        if (this.mModuleIndex == 166) {
            opMode.setPanorama(true);
        } else if (this.mModuleIndex == 167) {
            opMode.setProfession(true);
        }
        opMode.setShotBurst(this.mMultiSnapStatus);
        opMode.setFilter(CameraSettings.getShaderEffect());
        CameraSettings.getCameraLensType(this.mModuleIndex);
        if (isFrontCamera()) {
            opMode.setLensType(PictureInfo.SENSOR_TYPE_FRONT);
        } else {
            int actualCameraId = getActualCameraId();
            if (actualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                opMode.setLensType(actualCameraId + PictureInfo.SENSOR_TYPE_REAR_ULTRA);
            } else if (actualCameraId == 22) {
                opMode.setLensType(actualCameraId + PictureInfo.SENSOR_TYPE_REAR_MACRO);
            } else if (actualCameraId == 20) {
                opMode.setLensType(actualCameraId + PictureInfo.SENSOR_TYPE_REAR_TELE);
            } else if (actualCameraId == 23) {
                opMode.setLensType(actualCameraId + PictureInfo.SENSOR_TYPE_REAR_TELE4x);
            } else if (actualCameraId == Camera2DataContainer.getInstance().getMainBackCameraId()) {
                opMode.setLensType(actualCameraId + PictureInfo.SENSOR_TYPE_REAR_WIDE);
            } else if (actualCameraId == Camera2DataContainer.getInstance().getSATCameraId()) {
                opMode.setLensType(String.valueOf(actualCameraId) + "_" + PictureInfo.SENSOR_TYPE_REAR);
            }
        }
        if (this.mFocalLengths != null && this.mFocalLengths.length > 0) {
            opMode.setLensfocal(this.mFocalLengths[0]);
        }
        if (this.mApertures != null && this.mApertures.length > 0) {
            opMode.setLensApertues(this.mApertures[0]);
        }
        String faceInfoString = DebugInfoUtil.getFaceInfoString(this.mMainProtocol.getFaces());
        if (!TextUtils.isEmpty(faceInfoString)) {
            opMode.setFaceRoi(faceInfoString);
        }
        opMode.setOperateMode(this.mOperatingMode);
        opMode.setZoomMulti(getZoomRatio());
        if (this.mCamera2Device != null) {
            opMode.setEvValue(this.mCamera2Device.getExposureCompensation());
            MeteringRectangle[] aFRegions = this.mCamera2Device.getCameraConfigs().getAFRegions();
            if (aFRegions != null && aFRegions.length > 0) {
                opMode.setTouchRoi(aFRegions[0]);
            }
        }
        if (this.mBeautyValues != null && !BeautyConstant.LEVEL_CLOSE.equals(CameraSettings.getFaceBeautifyLevel()) && DataRepository.dataItemRunning().getComponentRunningShine().getBeautyVersion() == 2) {
            opMode.setBeautyLevel(this.mBeautyValues.mBeautyLevel);
        }
        if (this.mFaceDetectionEnabled && this.mFaceInfo != null) {
            opMode.setGender(this.mFaceInfo.mGender);
            opMode.setBaby(this.mFaceInfo.mAge);
        }
        if (this.mModuleIndex == 173) {
            opMode.setNightScene(1);
        }
        opMode.end();
        return opMode;
    }

    private CameraSize getPictureSize(int i, int i2, CameraSize cameraSize) {
        CameraSize cameraSize2;
        CameraCapabilities capabilities = Camera2DataContainer.getInstance().getCapabilities(i);
        if (capabilities != null) {
            capabilities.setOperatingMode(this.mOperatingMode);
            List<CameraSize> supportedOutputSize = capabilities.getSupportedOutputSize(i2);
            if (cameraSize != null) {
                ArrayList arrayList = new ArrayList(0);
                for (int i3 = 0; i3 < supportedOutputSize.size(); i3++) {
                    CameraSize cameraSize3 = supportedOutputSize.get(i3);
                    if (cameraSize3.compareTo(cameraSize) <= 0) {
                        arrayList.add(cameraSize3);
                    }
                }
                supportedOutputSize = arrayList;
            }
            String str = TAG;
            Log.d(str, "getPictureSize: matchSizes = " + supportedOutputSize);
            cameraSize2 = PictureSizeManager.getBestPictureSize(supportedOutputSize);
        } else {
            cameraSize2 = null;
        }
        String str2 = TAG;
        Log.d(str2, "getPictureSize: cameraId = " + i + " size = " + cameraSize2);
        return cameraSize2;
    }

    private String getPrefix() {
        return isLivePhotoStarted() ? Storage.LIVE_SHOT_PREFIX : "";
    }

    private String getRequestFlashMode() {
        if (isSupportSceneMode()) {
            String flashModeByScene = CameraSettings.getFlashModeByScene(this.mSceneMode);
            if (!TextUtils.isEmpty(flashModeByScene)) {
                return flashModeByScene;
            }
        }
        if (!this.mMutexModePicker.isSupportedFlashOn() && !this.mMutexModePicker.isSupportedTorch()) {
            return "0";
        }
        if (ModuleManager.isManualModule() && !CameraSettings.isFlashSupportedInManualMode()) {
            return ComponentConfigFlash.FLASH_VALUE_MANUAL_OFF;
        }
        String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
        if (this.mCurrentAsdScene == 9) {
            if (componentValue.equals("3")) {
                return "2";
            }
            if (componentValue.equals(ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO)) {
                return ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_ON;
            }
        }
        return componentValue;
    }

    private CameraSize getSatPictureSize() {
        int satMasterCameraId = this.mCamera2Device.getSatMasterCameraId();
        switch (satMasterCameraId) {
            case 1:
                return this.mUltraWidePictureSize;
            case 2:
                return this.mWidePictureSize;
            case 3:
                return this.mTelePictureSize;
            case 4:
                return this.mUltraTelePictureSize;
            default:
                String str = TAG;
                Log.e(str, "getSatPictureSize: invalid satMasterCameraId " + satMasterCameraId);
                return this.mWidePictureSize;
        }
    }

    /* access modifiers changed from: private */
    public String getSuffix() {
        return !this.mMutexModePicker.isNormal() ? this.mMutexModePicker.getSuffix() : "";
    }

    /* access modifiers changed from: private */
    public static String getTiltShiftMode() {
        if (CameraSettings.isTiltShiftOn()) {
            return DataRepository.dataItemRunning().getComponentRunningTiltValue().getComponentValue(160);
        }
        return null;
    }

    private boolean handleSuperNightResultIfNeed() {
        if (this.mSuperNightDisposable == null) {
            return false;
        }
        if (!this.mSuperNightDisposable.isDisposed()) {
            this.mSuperNightDisposable.dispose();
        }
        this.mSuperNightDisposable = null;
        boolean z = !this.mWaitingSuperNightResult;
        this.mWaitingSuperNightResult = false;
        if (z) {
            Log.d(TAG, "SuperNight: force trigger shutter animation, sound and post saving");
        }
        stopCpuBoost();
        if (currentIsMainThread()) {
            finishSuperNightState(z);
        } else {
            AndroidSchedulers.mainThread().scheduleDirect(new Runnable(z) {
                private final /* synthetic */ boolean f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    Camera2Module.this.finishSuperNightState(this.f$1);
                }
            });
        }
        return true;
    }

    private void handleSuperNightResultInCaptureMode() {
        if (this.mShowSuperNightHint) {
            this.mHandler.post(new Runnable() {
                public final void run() {
                    Camera2Module.lambda$handleSuperNightResultInCaptureMode$7(Camera2Module.this);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void handleUpdateFaceView(boolean z, boolean z2) {
        boolean isFrontCamera = isFrontCamera();
        if (!z) {
            this.mMainProtocol.updateFaceView(z, z2, isFrontCamera, false, -1);
        } else if ((this.mFaceDetectionStarted || isFaceBeautyMode()) && 1 != this.mCamera2Device.getFocusMode()) {
            this.mMainProtocol.updateFaceView(z, true, isFrontCamera, true, this.mCameraDisplayOrientation);
        }
    }

    private void hidePostCaptureAlert() {
        enableCameraControls(true);
        if (this.mCamera2Device.isSessionReady()) {
            resumePreview();
        } else {
            startPreview();
        }
        this.mMainProtocol.setEffectViewVisible(true);
        ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).delegateEvent(6);
    }

    private void hideSceneSelector() {
        this.mHandler.post($$Lambda$Camera2Module$qHFMpOoCCTSO6YFl_47nU6Q0rA.INSTANCE);
    }

    private void initAiSceneParser() {
        this.mFunctionParseAiScene = new FunctionParseAiScene(this.mModuleIndex, getCameraCapabilities());
        this.mAiSceneDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
            public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                FlowableEmitter unused = Camera2Module.this.mAiSceneFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).observeOn(GlobalConstant.sCameraSetupScheduler).map(this.mFunctionParseAiScene).filter(new PredicateFilterAiScene(this)).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
            public void accept(Integer num) {
                Camera2Module.this.consumeAiSceneResult(num.intValue(), false);
            }
        });
    }

    private void initFlashAutoStateForTrack(boolean z) {
        this.mFlashAutoModeState = null;
        String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
        if (!componentValue.equals("3") && !componentValue.equals(ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO)) {
            return;
        }
        if (this.mCurrentAsdScene == 9 || z) {
            this.mFlashAutoModeState = CameraStatUtil.AUTO_ON;
        } else {
            this.mFlashAutoModeState = CameraStatUtil.AUTO_OFF;
        }
    }

    private void initMetaParser() {
        this.mMetaDataDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
            public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                FlowableEmitter unused = Camera2Module.this.mMetaDataFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).observeOn(GlobalConstant.sCameraSetupScheduler).map(new FunctionParseAsdFace(this, isFrontCamera())).map(new FunctionParseAsdHdr(this)).map(new FunctionParseAsdUltraWide(this.mModuleIndex, this)).map(new FunctionParseAsdLivePhoto(this)).map(new FunctionParseSuperNight(this)).map(new FunctionMiAlgoASDEngine(this)).sample(500, TimeUnit.MILLISECONDS).observeOn(GlobalConstant.sCameraSetupScheduler).map(new FunctionParseAsdScene(this)).observeOn(AndroidSchedulers.mainThread()).onTerminateDetach().subscribe(new AsdSceneConsumer(this));
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

    /* access modifiers changed from: private */
    public boolean is3ALocked() {
        return this.m3ALocked;
    }

    private boolean isCannotGotoGallery() {
        return this.mPaused || this.isZooming || isKeptBitmapTexture() || this.mMultiSnapStatus || getCameraState() == 0 || isQueueFull() || isInCountDown();
    }

    private boolean isCurrentRawDomainBasedSuperNight() {
        return this.mModuleIndex == 173 && DataRepository.dataItemFeature().hK();
    }

    private boolean isEnableQcfaForAlgoUp() {
        if (!this.mCameraCapabilities.isSupportedQcfa() || !this.mEnableParallelSession) {
            return false;
        }
        if (b.isMTKPlatform()) {
            return CameraSettings.isUltraPixelOn();
        }
        if (isInQCFAMode()) {
            return true;
        }
        return CameraSettings.isUltraPixelOn() && DataRepository.dataItemFeature().hS();
    }

    private boolean isFaceBeautyOn(BeautyValues beautyValues) {
        if (beautyValues == null) {
            return false;
        }
        return beautyValues.isFaceBeautyOn();
    }

    /* access modifiers changed from: private */
    public boolean isFrontMirror() {
        if (!isFrontCamera()) {
            return false;
        }
        if (CameraSettings.isLiveShotOn()) {
            return true;
        }
        return CameraSettings.isFrontMirror();
    }

    private boolean isImageSaverFull() {
        ImageSaver imageSaver = this.mActivity.getImageSaver();
        if (imageSaver == null) {
            Log.w(TAG, "isParallelQueueFull: ImageSaver is null");
            return false;
        } else if (!imageSaver.isSaveQueueFull()) {
            return false;
        } else {
            Log.d(TAG, "isParallelQueueFull: ImageSaver queue is full");
            return true;
        }
    }

    private boolean isIn3OrMoreSatMode() {
        String str = TAG;
        Log.d(str, "isIn3OrMoreSatMode: opMode=0x" + Integer.toHexString(this.mOperatingMode) + " isIn3OrMore=" + HybridZoomingSystem.IS_3_OR_MORE_SAT);
        return 36866 == this.mOperatingMode && HybridZoomingSystem.IS_3_OR_MORE_SAT;
    }

    private boolean isInCountDown() {
        return this.mCountdownDisposable != null && !this.mCountdownDisposable.isDisposed();
    }

    private boolean isInMultiSurfaceSatMode() {
        return this.mCamera2Device.isInMultiSurfaceSatMode();
    }

    private boolean isInQCFAMode() {
        return (getModuleIndex() == 163 || getModuleIndex() == 165) && this.mCameraCapabilities.isSupportedQcfa() && isFrontCamera();
    }

    private boolean isLaunchedByMainIntent() {
        String str;
        if (this.mActivity != null) {
            Intent intent = this.mActivity.getIntent();
            if (intent != null) {
                str = intent.getAction();
                return "android.intent.action.MAIN".equals(str);
            }
        }
        str = null;
        return "android.intent.action.MAIN".equals(str);
    }

    private static boolean isLiveShotAvailable(int i) {
        return i == 0 || i == 5 || i == 8;
    }

    private boolean isParallelQueueFull() {
        boolean z = false;
        if (!this.mEnableParallelSession || this.mActivity.getImageSaver() == null) {
            return false;
        }
        if (isImageSaverFull()) {
            return true;
        }
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
        if (localBinder != null) {
            z = localBinder.needWaitProcess();
        } else {
            Log.w(TAG, "isParallelQueueFull: NOTICE: CHECK WHY BINDER IS NULL!");
        }
        String str = TAG;
        Log.w(str, "isParallelQueueFull: isNeedWaitProcess:" + z);
        return z;
    }

    private boolean isPortraitSuccessHintShowing() {
        return ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).isPortraitHintVisible();
    }

    private boolean isPreviewThumbnailWhenFlash() {
        if (!this.mUseLegacyFlashMode) {
            return true;
        }
        return !"3".equals(this.mLastFlashMode) && !"1".equals(this.mLastFlashMode);
    }

    private boolean isQueueFull() {
        return this.mEnableParallelSession ? isParallelQueueFull() : isImageSaverFull();
    }

    private boolean isSensorRawStreamRequired() {
        int i = this.mModuleIndex;
        if (i == 167) {
            return DataRepository.dataItemConfig().getComponentConfigRaw().isSwitchOn(167);
        }
        if (i != 173) {
            return false;
        }
        return DataRepository.dataItemFeature().hK();
    }

    private boolean isTriggerQcfaModeChange(boolean z, boolean z2) {
        if (!this.mCameraCapabilities.isSupportedQcfa()) {
            return false;
        }
        if ((this.mModuleIndex == 171 && isBokehFrontCamera()) || DataRepository.dataItemFeature().gW() >= 0) {
            return false;
        }
        if (z) {
            if (!mIsBeautyFrontOn) {
                if (this.mOperatingMode == 32775) {
                    return true;
                }
                DataRepository.dataItemConfig().getComponentHdr().getComponentValue(this.mModuleIndex);
            }
        } else if (z2) {
        }
        return false;
    }

    private boolean isUseSwMfnr() {
        if (CameraSettings.isGroupShotOn()) {
            Log.d(TAG, "GroupShot is on");
            return false;
        } else if (!DataRepository.dataItemFeature().fI() && (isUltraWideBackCamera() || isZoomRatioBetweenUltraAndWide())) {
            Log.d(TAG, "SwMfnr force off for ultra wide camera");
            return false;
        } else if (!CameraSettings.isMfnrSatEnable()) {
            Log.d(TAG, "Mfnr not enabled");
            return false;
        } else if (!DataRepository.dataItemFeature().fH()) {
            Log.d(TAG, "SwMfnr is not supported");
            return false;
        } else if (!this.mMutexModePicker.isNormal()) {
            Log.d(TAG, "Mutex mode is not normal");
            return false;
        } else if (DataRepository.dataItemFeature().fI() && this.mModuleIndex != 167 && this.mModuleIndex != 173 && !CameraSettings.isSuperNightOn()) {
            Log.d(TAG, "For the devices does not have hardware MFNR, use software MFNR");
            return true;
        } else if (!isFrontCamera() || isDualFrontCamera()) {
            return false;
        } else {
            if (this.mOperatingMode == 32773 && b.km()) {
                return true;
            }
            if (this.mOperatingMode != 32773 || b.km()) {
                return DataRepository.dataItemFeature().isSupportUltraWide() || b.sA || b.sJ;
            }
            return false;
        }
    }

    private void keepScreenOnAwhile() {
        this.mHandler.sendEmptyMessageDelayed(17, 1000);
    }

    public static /* synthetic */ void lambda$handleSuperNightResultInCaptureMode$7(Camera2Module camera2Module) {
        ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).alertAiDetectTipHint(8, R.string.super_night_hint, -1);
        camera2Module.mShowSuperNightHint = false;
    }

    static /* synthetic */ void lambda$hideSceneSelector$2() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertAiSceneSelector(8);
        }
    }

    public static /* synthetic */ void lambda$onFaceDetected$4(Camera2Module camera2Module, CameraHardwareFace[] cameraHardwareFaceArr) {
        if (cameraHardwareFaceArr.length > 0) {
            if (!camera2Module.mIsFaceConflict) {
                camera2Module.mIsFaceConflict = true;
                camera2Module.showOrHideChip(false);
            }
        } else if (camera2Module.mIsFaceConflict) {
            camera2Module.mIsFaceConflict = false;
            camera2Module.showOrHideChip(true);
        }
    }

    static /* synthetic */ void lambda$onHdrSceneChanged$3(boolean z) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertHDR(z ? 0 : 8, false, false);
        }
    }

    public static /* synthetic */ void lambda$onLongPress$8(Camera2Module camera2Module, float f, float f2, int i, int i2, int i3) {
        String str = TAG;
        Log.d(str, "onOptionClick: which = " + i3);
        CameraStatUtil.trackGoogleLensPickerValue(i3 == 0);
        switch (i3) {
            case 0:
                DataRepository.dataItemGlobal().editor().putBoolean(CameraSettings.KEY_GOOGLE_LENS_OOBE, true).apply();
                DataRepository.dataItemGlobal().editor().putString(CameraSettings.KEY_LONG_PRESS_VIEWFINDER, camera2Module.getString(R.string.pref_camera_long_press_viewfinder_default)).apply();
                LensAgent.getInstance().onFocusChange(2, f / ((float) Util.sWindowWidth), f2 / ((float) Util.sWindowHeight));
                return;
            case 1:
                DataRepository.dataItemGlobal().editor().putString(CameraSettings.KEY_LONG_PRESS_VIEWFINDER, camera2Module.getString(R.string.pref_camera_long_press_viewfinder_lock_ae_af)).apply();
                DataRepository.dataItemGlobal().editor().putBoolean(CameraSettings.KEY_EN_FIRST_CHOICE_LOCK_AE_AF_TOAST, true).apply();
                camera2Module.onSingleTapUp(i, i2, true);
                if (camera2Module.m3ALockSupported) {
                    camera2Module.lockAEAF();
                }
                camera2Module.mMainProtocol.performHapticFeedback(0);
                return;
            default:
                return;
        }
    }

    public static /* synthetic */ void lambda$onPause$11(Camera2Module camera2Module) {
        if (camera2Module.mLensApi != null && camera2Module.mIsLensServiceBound) {
            Log.d(TAG, "Unbind Lens service: E");
            camera2Module.mIsLensServiceBound = false;
            try {
                camera2Module.mLensApi.onPause();
            } catch (Exception e) {
                String str = TAG;
                Log.d(str, "Unknown error when pause LensAPI->" + e.getMessage());
            }
            Log.d(TAG, "Unbind Lens service: X");
        }
    }

    public static /* synthetic */ void lambda$onResume$9(Camera2Module camera2Module) {
        if (camera2Module.mLensApi != null && !camera2Module.mIsLensServiceBound && !camera2Module.mActivity.isActivityPaused()) {
            Log.d(TAG, "Bind Lens service: E");
            camera2Module.mLensApi.onResume();
            camera2Module.mIsLensServiceBound = true;
            Log.d(TAG, "Bind Lens service: X");
        }
    }

    public static /* synthetic */ void lambda$onUltraWideChanged$15(Camera2Module camera2Module, boolean z, int i) {
        String str = TAG;
        Log.d(str, "isOpenUltraWide: " + z + "; type: " + i);
        if (camera2Module.mIsGoogleLensAvailable) {
            camera2Module.mIsUltraWideConflict = z;
            camera2Module.showOrHideChip(!z);
        }
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            boolean isZoomRatioBetweenUltraAndWide = camera2Module.isZoomRatioBetweenUltraAndWide();
            int i2 = R.string.ultra_wide_recommend_tip_hint_sat;
            if (isZoomRatioBetweenUltraAndWide && (bottomPopupTips.containTips(R.string.ultra_wide_recommend_tip_hint) || bottomPopupTips.containTips(R.string.ultra_wide_recommend_tip_hint_sat))) {
                bottomPopupTips.directlyHideTips();
            } else if (z) {
                ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
                if (!(dualController != null && dualController.isSlideVisible()) && !MiAlgoAsdSceneProfile.isAlreadyTip() && camera2Module.getZoomRatio() <= 1.0f && 171 != camera2Module.getModuleIndex()) {
                    if (i == 2) {
                        camera2Module.trackAISceneChanged(camera2Module.mModuleIndex, 36);
                    }
                    MiAlgoAsdSceneProfile.setTipEnable(MiAlgoAsdSceneProfile.ULTRA_WIDE, true);
                    if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                        i2 = R.string.ultra_wide_recommend_tip_hint;
                    }
                    bottomPopupTips.showTips(14, i2, 7);
                }
            } else if (bottomPopupTips.containTips(R.string.ultra_wide_recommend_tip_hint) || bottomPopupTips.containTips(R.string.ultra_wide_recommend_tip_hint_sat)) {
                bottomPopupTips.directlyHideTips();
            }
        }
    }

    public static /* synthetic */ void lambda$registerProtocol$0(Camera2Module camera2Module) {
        camera2Module.mIsFaceConflict = false;
        camera2Module.mIsAiConflict = false;
        camera2Module.mIsUltraWideConflict = false;
        camera2Module.showOrHideChip(true);
    }

    public static /* synthetic */ void lambda$startNormalCapture$5(Camera2Module camera2Module, boolean z) {
        String str = TAG;
        Log.d(str, "onCaptureCompleted and enable shot = " + z);
        if (DataRepository.dataItemFeature().hm()) {
            if (!camera2Module.isKeptBitmapTexture() && !camera2Module.mMultiSnapStatus && z) {
                camera2Module.setCameraState(1);
                camera2Module.enableCameraControls(true);
            }
            if (camera2Module.mCamera2Device != null) {
                camera2Module.mCamera2Device.unRegisterCaptureCallback();
            }
        }
    }

    public static /* synthetic */ void lambda$unRegisterProtocol$1(Camera2Module camera2Module) {
        camera2Module.showOrHideChip(false);
        camera2Module.mIsFaceConflict = false;
        camera2Module.mIsUltraWideConflict = false;
        camera2Module.mIsAiConflict = false;
    }

    private void lockAEAF() {
        Log.d(TAG, "lockAEAF");
        if (this.mFocusManager != null) {
            this.mFocusManager.setAeAwbLock(true);
        }
        this.m3ALocked = true;
    }

    private boolean needQuickShot() {
        boolean z = false;
        if (this.mBlockQuickShot || this.mIsImageCaptureIntent || !CameraSettings.isCameraQuickShotEnable()) {
            return false;
        }
        if (enablePreviewAsThumbnail() && ((this.mModuleIndex == 163 || this.mModuleIndex == 165) && getZoomRatio() == 1.0f && !isFrontCamera() && !CameraSettings.isUltraWideConfigOpen(this.mModuleIndex) && !CameraSettings.isMacroModeEnabled(this.mModuleIndex) && !this.mCamera2Device.isNeedFlashOn() && !CameraSettings.isUltraPixelOn() && !CameraSettings.isLiveShotOn() && (this.mBeautyValues == null || !this.mBeautyValues.isFaceBeautyOn()))) {
            z = true;
        }
        String str = TAG;
        Log.d(str, "needQuickShot bRet:" + z);
        return z;
    }

    private boolean needShowThumbProgressImmediately() {
        return Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))) > 250000000 && this.mModuleIndex != 173;
    }

    /* access modifiers changed from: private */
    public void onBurstPictureTakenFinished(boolean z) {
        stopMultiSnap();
        if (this.mBurstEmitter != null) {
            this.mBurstEmitter.onComplete();
        }
        onPictureTakenFinished(z);
        PerformanceTracker.trackPictureCapture(1);
    }

    private void onShutter(boolean z) {
        if (getCameraState() == 0) {
            Log.d(TAG, "onShutter: preview stopped");
            return;
        }
        this.mShutterCallbackTime = System.currentTimeMillis();
        this.mShutterLag = this.mShutterCallbackTime - this.mCaptureStartTime;
        String str = TAG;
        Log.v(str, "mShutterLag = " + this.mShutterLag + "ms");
        updateEnablePreviewThumbnail(z);
        if (this.mEnabledPreviewThumbnail) {
            this.mActivity.getCameraScreenNail().requestReadPixels();
        } else if (this.mReceivedJpegCallbackNum == 0 && this.mModuleIndex != 173) {
            updateThumbProgress(false);
            animateCapture();
            playCameraSound(0);
        }
    }

    private void parseIntent() {
        CameraIntentManager cameraIntentManager = this.mActivity.getCameraIntentManager();
        this.mIsImageCaptureIntent = cameraIntentManager.isImageCaptureIntent();
        if (this.mIsImageCaptureIntent) {
            this.mSaveUri = cameraIntentManager.getExtraSavedUri();
            this.mCropValue = cameraIntentManager.getExtraCropValue();
            this.mIsSaveCaptureImage = cameraIntentManager.getExtraShouldSaveCapture().booleanValue();
            this.mQuickCapture = cameraIntentManager.isQuickCapture().booleanValue();
        }
    }

    private void prepareEffectProcessor(int i) {
    }

    private void prepareMultiCapture() {
        Log.d(TAG, "prepareMultiCapture");
        this.mMultiSnapStatus = true;
        this.mMultiSnapStopRequest = false;
        Util.clearMemoryLimit();
        prepareNormalCapture();
        CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
        this.mTotalJpegCallbackNum = CameraCapabilities.getBurstShootCount();
        this.mHandler.removeMessages(49);
        if (!is3ALocked()) {
            this.mFocusManager.onShutter();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x0128  */
    private void prepareNormalCapture() {
        boolean z;
        Log.d(TAG, "prepareNormalCapture");
        initFlashAutoStateForTrack(this.mCamera2Device.isNeedFlashOn());
        this.mEnabledPreviewThumbnail = false;
        this.mTotalJpegCallbackNum = 1;
        this.mReceivedJpegCallbackNum = 0;
        this.mCaptureStartTime = System.currentTimeMillis();
        ScenarioTrackUtil.trackCaptureTimeStart(isFrontCamera(), this.mModuleIndex);
        this.mLastCaptureTime = this.mCaptureStartTime;
        setCameraState(3);
        this.mJpegRotation = Util.getJpegRotation(this.mBogusCameraId, this.mOrientation);
        Log.d(TAG, "prepareNormalCapture: mOrientation = " + this.mOrientation + ", mJpegRotation = " + this.mJpegRotation);
        this.mCamera2Device.setJpegRotation(this.mJpegRotation);
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        this.mCamera2Device.setGpsLocation(currentLocation);
        this.mLocation = currentLocation;
        updateFrontMirror();
        updateBeauty();
        updateSRAndMFNR();
        updateShotDetermine();
        updateCaptureTriggerFlow();
        this.mCamera2Device.setShotSavePath(Storage.generateFilepath4Jpeg(getPrefix() + Util.createJpegName(System.currentTimeMillis()) + getSuffix()), !this.mMultiSnapStatus && (this.mEnableParallelSession || this.mEnableShot2Gallery));
        if (this.mModuleIndex == 163 || this.mModuleIndex == 165 || this.mModuleIndex == 171 || this.mModuleIndex == 175) {
            boolean z2 = CameraSettings.isCameraQuickShotEnable() || CameraSettings.isCameraQuickShotAnimateEnable();
            boolean hm = DataRepository.dataItemFeature().hm();
            if (z2 || hm) {
                z = true;
                this.mCamera2Device.setNeedSequence(z);
                if (enablePreviewAsThumbnail() || this.mMutexModePicker.isHdr()) {
                    this.mQuickShotAnimateEnable = false;
                } else {
                    this.mQuickShotAnimateEnable = CameraSettings.isCameraQuickShotAnimateEnable();
                }
                setWaterMark();
                setPictureOrientation();
                updateJpegQuality();
                updateAlgorithmName();
                if (needShowThumbProgressImmediately()) {
                    updateThumbProgress(false);
                }
                prepareSuperNight();
                prepareSuperNightInCaptureMode();
                checkMoreFrameCaptureLockAFAE(true);
            }
        }
        z = false;
        this.mCamera2Device.setNeedSequence(z);
        if (enablePreviewAsThumbnail()) {
        }
        this.mQuickShotAnimateEnable = false;
        setWaterMark();
        setPictureOrientation();
        updateJpegQuality();
        updateAlgorithmName();
        if (needShowThumbProgressImmediately()) {
        }
        prepareSuperNight();
        prepareSuperNightInCaptureMode();
        checkMoreFrameCaptureLockAFAE(true);
    }

    private void prepareSuperNight() {
        if (this.mModuleIndex == 173) {
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onPrepare();
                recordState.onStart();
            }
            startCpuBoost();
            if (this.mSuperNightEventConsumer == null) {
                this.mSuperNightEventConsumer = new SuperNightEventConsumer();
            }
            this.mSuperNightDisposable = Observable.just(300, 2000).flatMap(new Function<Integer, ObservableSource<Integer>>() {
                public ObservableSource<Integer> apply(Integer num) throws Exception {
                    return Observable.just(num).delaySubscription((long) num.intValue(), TimeUnit.MILLISECONDS);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(this.mSuperNightEventConsumer);
        }
    }

    private void prepareSuperNightInCaptureMode() {
        if (CameraSettings.isSuperNightOn()) {
            String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
            if (this.mMainProtocol.isFaceExists(1) && componentValue == "3" && DataRepository.dataItemFeature().ie()) {
                this.mCamera2Device.setForceBackSoftLight(true);
            }
            this.mShowSuperNightHint = true;
            ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).alertAiDetectTipHint(0, R.string.super_night_hint, -1);
        }
    }

    private void previewWhenSessionSuccess() {
        setCameraState(1);
        this.mFaceDetectionEnabled = false;
        updatePreferenceInWorkThread(UpdateConstant.CAMERA_TYPES_ON_PREVIEW_SUCCESS);
        if (ModuleManager.isManualModule()) {
            updatePreferenceInWorkThread(UpdateConstant.CAMERA_TYPES_MANUALLY);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:94:0x01ed  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x01f7  */
    private void realConsumeAiSceneResult(int i, boolean z) {
        boolean z2 = false;
        if (i == 36) {
            i = 0;
        }
        if (this.mCurrentAiScene == i) {
            if (i == 0) {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null && topAlert.getCurrentAiSceneLevel() == i) {
                    return;
                }
            } else {
                return;
            }
        }
        if (!isDoingAction() && isAlive() && !this.mActivity.isActivityPaused()) {
            if (!z || !this.isResetFromMutex) {
                if (!z) {
                    this.isResetFromMutex = false;
                }
                Log.d(TAG, "consumeAiSceneResult: " + i + "; isReset: " + z);
                ModeProtocol.TopAlert topAlert2 = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                if (!isFrontCamera() && !b.sU) {
                    this.mCamera2Device.setCameraAI30(i == 25);
                }
                if (this.mIsGoogleLensAvailable) {
                    if (!LensAgent.isConflictAiScene(this.mCurrentAiScene) && LensAgent.isConflictAiScene(i)) {
                        this.mIsAiConflict = true;
                        showOrHideChip(false);
                    } else if (LensAgent.isConflictAiScene(this.mCurrentAiScene) && !LensAgent.isConflictAiScene(i)) {
                        this.mIsAiConflict = false;
                        showOrHideChip(true);
                    }
                }
                closeMoonMode(i, 8);
                closeBacklightTip(i, 8);
                if (i != -1) {
                    if (i == 1) {
                        int parseInt = Integer.parseInt(CameraSettings.getSharpness());
                        if (parseInt < 6) {
                            parseInt++;
                        }
                        this.mCurrentAiScene = i;
                        configChanges.restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_AI);
                        this.mCamera2Device.setSharpness(parseInt);
                    } else if (i == 10) {
                        configChanges.closeMutexElement(SupportedConfigFactory.CLOSE_BY_AI, 193);
                        setFlashMode("0");
                        updateMfnr(true);
                        updateOIS();
                    } else if (i == 15 || i == 19) {
                        int parseInt2 = Integer.parseInt(CameraSettings.getSharpness());
                        if (parseInt2 < 6) {
                            parseInt2++;
                        }
                        this.mCamera2Device.setSharpness(parseInt2);
                        this.mCurrentAiScene = i;
                        configChanges.restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_AI);
                    } else {
                        if (i != 37) {
                            switch (i) {
                                case 3:
                                    if (DataRepository.dataItemFeature().ie() && isBackCamera() && !MiAlgoAsdSceneProfile.isAlreadyTip()) {
                                        MiAlgoAsdSceneProfile.setTipEnable(MiAlgoAsdSceneProfile.AI_SCENE_MODE_FOOD, true);
                                        updateTipMessage(20, R.string.recommend_soft_light_tip, 2);
                                        break;
                                    }
                                case 4:
                                    this.mCamera2Device.setContrast(Integer.parseInt(CameraSettings.getContrast()));
                                    this.mCurrentAiScene = i;
                                    configChanges.restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_AI);
                                    updateSuperResolution();
                                    break;
                                default:
                                    switch (i) {
                                        case 7:
                                        case 8:
                                            this.mCurrentAiScene = i;
                                            configChanges.restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_AI);
                                            break;
                                        default:
                                            switch (i) {
                                                case 25:
                                                    trackAISceneChanged(this.mModuleIndex, 25);
                                                    topAlert2.setAiSceneImageLevel(25);
                                                    topAlert2.alertAiSceneSelector(8);
                                                    setAiSceneEffect(25);
                                                    this.mCurrentAiScene = i;
                                                    updateHDRPreference();
                                                    configChanges.restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_AI);
                                                    this.mCamera2Device.setASD(false);
                                                    resumePreviewInWorkThread();
                                                    return;
                                                case 26:
                                                case 27:
                                                case 28:
                                                case 29:
                                                case 30:
                                                case 31:
                                                    if (!DataRepository.dataItemFeature().fD()) {
                                                        configChanges.restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_AI);
                                                        updatePreferenceInWorkThread(UpdateConstant.AI_SCENE_CONFIG);
                                                        i = 0;
                                                        break;
                                                    } else {
                                                        this.mCurrentAiScene = i;
                                                        configChanges.restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_AI);
                                                        break;
                                                    }
                                                default:
                                                    switch (i) {
                                                        case 34:
                                                            break;
                                                        case 35:
                                                            if (showMoonMode(false)) {
                                                                topAlert2.setAiSceneImageLevel(i);
                                                                this.mCamera2Device.setASD(true);
                                                                trackAISceneChanged(this.mModuleIndex, i);
                                                                this.mCurrentAiScene = i;
                                                                return;
                                                            }
                                                            break;
                                                        default:
                                                            updateHDRPreference();
                                                            configChanges.restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_AI);
                                                            updatePreferenceInWorkThread(UpdateConstant.AI_SCENE_CONFIG);
                                                            break;
                                                    }
                                            }
                                    }
                            }
                        }
                        this.mCurrentAiScene = i;
                        trackAISceneChanged(this.mModuleIndex, i);
                        topAlert2.setAiSceneImageLevel(i);
                        if (z2) {
                            setAiSceneEffect(i);
                        }
                        this.mCamera2Device.setASD(true);
                        if (!z) {
                            this.mCurrentAiScene = i;
                        }
                        updateBeauty();
                        resumePreviewInWorkThread();
                        return;
                    }
                    z2 = true;
                    trackAISceneChanged(this.mModuleIndex, i);
                    topAlert2.setAiSceneImageLevel(i);
                    if (z2) {
                    }
                    this.mCamera2Device.setASD(true);
                    if (!z) {
                    }
                    updateBeauty();
                    resumePreviewInWorkThread();
                    return;
                }
                showBacklightTip();
                topAlert2.setAiSceneImageLevel(23);
                this.mCurrentAiScene = i;
            }
        }
    }

    private void releaseEffectProcessor() {
    }

    private void resetAiSceneInHdrOrFlashOn() {
        if (this.mAiSceneEnabled && !this.isResetFromMutex && this.mCurrentAiScene != 0) {
            if (this.mCurrentAiScene == -1 || this.mCurrentAiScene == 10) {
                this.mHandler.post(new Runnable() {
                    public void run() {
                        Camera2Module.this.consumeAiSceneResult(0, true);
                        boolean unused = Camera2Module.this.isResetFromMutex = true;
                    }
                });
            }
        }
    }

    private void resetAsdSceneInHdrOrFlashChange() {
        if (b.kk() && isFrontCamera() && this.mCurrentAsdScene != -1 && this.mCurrentAsdScene == 9) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    Camera2Module.this.consumeAsdSceneResult(-1);
                }
            });
        }
    }

    private void resetScreenOn() {
        if (this.mHandler != null) {
            this.mHandler.removeMessages(17);
            this.mHandler.removeMessages(2);
        }
    }

    private void resumePreviewInWorkThread() {
        updatePreferenceInWorkThread(new int[0]);
    }

    private void setAiSceneEffect(int i) {
        String str = TAG;
        Log.d(str, "setAiSceneEffect: " + i);
        if (!DataRepository.dataItemFeature().fY() || !CameraSettings.isBackCamera() || i != 25) {
            if (CameraSettings.isFrontCamera() || isPortraitMode()) {
                if (i != 0) {
                    Log.d(TAG, "setAiSceneEffect: front camera or portrait mode nonsupport!");
                    return;
                } else if (CameraSettings.getPortraitLightingPattern() != 0) {
                    Log.d(TAG, "setAiSceneEffect: scene = 0 but portrait lighting is on...");
                    return;
                }
            }
            ArrayList<FilterInfo> filterInfo = EffectController.getInstance().getFilterInfo(5);
            if (i < 0 || i > filterInfo.size()) {
                Log.e(TAG, "setAiSceneEffect: scene unknown!");
                return;
            }
            int shaderEffect = CameraSettings.getShaderEffect();
            if (FilterInfo.getCategory(shaderEffect) == 5 || shaderEffect == FilterInfo.FILTER_ID_NONE) {
                int id = filterInfo.get(i).getId();
                EffectController.getInstance().setAiSceneEffect(id);
                this.mHasAiSceneFilterEffect = id != FilterInfo.FILTER_ID_NONE;
                if (EffectController.getInstance().hasEffect()) {
                    prepareEffectProcessor(id);
                    return;
                }
                return;
            }
            return;
        }
        Log.d(TAG, "supportAi30: AI 3.0 back camera in HUMAN SCENE not apply filter!");
    }

    private void setEffectFilter(int i) {
        String str = TAG;
        Log.d(str, "setEffectFilter: " + i);
        EffectController.getInstance().setEffect(i);
        this.mFilterId = i;
        if (this.mCircularMediaRecorder != null) {
            this.mCircularMediaRecorder.setFilterId(i);
        }
        if (EffectController.getInstance().hasEffect()) {
            prepareEffectProcessor(i);
        }
    }

    private void setLightingEffect() {
        int shaderEffect = CameraSettings.getShaderEffect();
        if (FilterInfo.getCategory(shaderEffect) == 5 || shaderEffect == FilterInfo.FILTER_ID_NONE) {
            int portraitLightingPattern = CameraSettings.getPortraitLightingPattern();
            String str = TAG;
            Log.d(str, "setLightingEffect: " + portraitLightingPattern);
            int id = EffectController.getInstance().getFilterInfo(6).get(portraitLightingPattern).getId();
            EffectController.getInstance().setLightingEffect(id);
            if (EffectController.getInstance().hasEffect()) {
                prepareEffectProcessor(id);
            }
        }
    }

    /* access modifiers changed from: private */
    public void setOrientation(int i, int i2) {
        if (i != -1) {
            this.mOrientation = i;
            EffectController.getInstance().setOrientation(Util.getShootOrientation(this.mActivity, this.mOrientation));
            checkActivityOrientation();
            if (this.mOrientationCompensation != i2) {
                this.mOrientationCompensation = i2;
                setOrientationParameter();
            }
        }
    }

    /* access modifiers changed from: private */
    public void setOrientationParameter() {
        if (!isDeparted()) {
            if (!(this.mCamera2Device == null || this.mOrientation == -1)) {
                if (!isFrameAvailable() || getCameraState() != 1) {
                    GlobalConstant.sCameraSetupScheduler.scheduleDirect(new Runnable() {
                        public final void run() {
                            Camera2Module.this.mCamera2Device.setDeviceOrientation(Camera2Module.this.mOrientation);
                        }
                    });
                } else {
                    updatePreferenceInWorkThread(35);
                }
            }
            if (this.mCircularMediaRecorder != null) {
                this.mCircularMediaRecorder.setOrientationHint(this.mOrientationCompensation);
            }
        }
    }

    private void setPictureOrientation() {
        this.mShootRotation = this.mActivity.getSensorStateManager().isDeviceLying() ? (float) this.mOrientation : this.mDeviceRotation;
        this.mShootOrientation = this.mOrientation == -1 ? 0 : this.mOrientation;
    }

    private void setPortraitSuccessHintVisible(int i) {
        ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).setPortraitHintVisible(i);
    }

    private boolean setSceneMode(String str) {
        int parseInt = Util.parseInt(str, -1);
        this.mCamera2Device.setSceneMode(parseInt);
        if (!Util.isSupported(parseInt, this.mCameraCapabilities.getSupportedSceneModes())) {
            return false;
        }
        String str2 = TAG;
        Log.d(str2, "sceneMode=" + str);
        return true;
    }

    private void setVideoSize(int i, int i2) {
        if (this.mCameraDisplayOrientation % 180 == 0) {
            this.mVideoSize = new CameraSize(i, i2);
        } else {
            this.mVideoSize = new CameraSize(i2, i);
        }
    }

    private void setWaterMark() {
        if (this.mMultiSnapStatus || DataRepository.dataItemFeature().fQ() || ((!this.mEnableParallelSession && (this.mModuleIndex == 165 || CameraSettings.getShaderEffect() != FilterInfo.FILTER_ID_NONE || this.mHasAiSceneFilterEffect || CameraSettings.isTiltShiftOn())) || (this.mEnableParallelSession && (!DataRepository.dataItemFeature().fS() || this.mModuleIndex == 171)))) {
            this.mCamera2Device.setDualCamWaterMarkEnable(false);
            this.mCamera2Device.setTimeWaterMarkEnable(false);
            return;
        }
        if (CameraSettings.isDualCameraWaterMarkOpen()) {
            this.mCamera2Device.setDualCamWaterMarkEnable(true);
        } else if (CameraSettings.isFrontCameraWaterMarkOpen()) {
            this.mCamera2Device.setDualCamWaterMarkEnable(true);
        } else {
            this.mCamera2Device.setDualCamWaterMarkEnable(false);
        }
        if (CameraSettings.isTimeWaterMarkOpen()) {
            this.mCamera2Device.setTimeWaterMarkEnable(true);
            this.mCaptureWaterMarkStr = Util.getTimeWatermark();
            this.mCamera2Device.setTimeWatermarkValue(this.mCaptureWaterMarkStr);
            return;
        }
        this.mCaptureWaterMarkStr = null;
        this.mCamera2Device.setTimeWaterMarkEnable(false);
    }

    private boolean shouldApplyNormalWideLDC() {
        if (CameraSettings.shouldNormalWideLDCBeVisibleInMode(this.mModuleIndex) && !CameraSettings.isMacroModeEnabled(this.mModuleIndex) && this.mActualCameraId != Camera2DataContainer.getInstance().getUltraWideCameraId() && !CameraSettings.isUltraPixelOn() && !isZoomRatioBetweenUltraAndWide()) {
            return CameraSettings.isNormalWideLDCEnabled();
        }
        return false;
    }

    private boolean shouldApplyUltraWideLDC() {
        if (!CameraSettings.shouldUltraWideLDCBeVisibleInMode(this.mModuleIndex)) {
            return false;
        }
        if (CameraSettings.isMacroModeEnabled(this.mModuleIndex) && !DataRepository.dataItemFeature().hH()) {
            return true;
        }
        if (this.mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return CameraSettings.isUltraWideLDCEnabled();
        }
        if (isZoomRatioBetweenUltraAndWide()) {
            return CameraSettings.isUltraWideLDCEnabled();
        }
        return false;
    }

    private boolean shouldChangeAiScene(int i) {
        if (isDoingAction() || !isAlive() || this.mCurrentDetectedScene == i || System.currentTimeMillis() - this.mLastChangeSceneTime <= 300) {
            return false;
        }
        this.mCurrentDetectedScene = i;
        this.mLastChangeSceneTime = System.currentTimeMillis();
        return true;
    }

    private boolean shouldDoMultiFrameCapture() {
        boolean z = false;
        if (this.mIsMoonMode && !DataRepository.dataItemFeature().iz()) {
            Log.d(TAG, "shouldDoMultiFrameCapture: return false in moon mode");
            return false;
        } else if (this.mCamera2Device == null || this.mCameraCapabilities == null || !this.mCamera2Device.useLegacyFlashStrategy() || !this.mCamera2Device.isNeedFlashOn() || !this.mCameraCapabilities.isFlashSupported()) {
            if (this.mMutexModePicker.isHdr() || this.mShouldDoMFNR || this.mMutexModePicker.isSuperResolution() || CameraSettings.isGroupShotOn()) {
                z = true;
            }
            String str = TAG;
            Log.d(str, "shouldDoMultiFrameCapture: " + z);
            return z;
        } else {
            Log.d(TAG, "shouldDoMultiFrameCapture: return false in case of flash");
            return false;
        }
    }

    private boolean showMoonMode(boolean z) {
        if (CameraSettings.isUltraWideConfigOpen(getModuleIndex()) || CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
            return false;
        }
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert == null) {
            return false;
        }
        this.mEnteringMoonMode = true;
        topAlert.alertMoonModeSelector(0, z);
        if (!z) {
            updateMoonNight();
        } else {
            updateMoon(true);
        }
        String str = TAG;
        Log.d(str, "(moon_mode) show moon mode,button check status:" + z);
        return true;
    }

    private void showPostCaptureAlert() {
        enableCameraControls(false);
        this.mFocusManager.removeMessages();
        stopFaceDetection(true);
        pausePreview();
        this.mMainProtocol.setEffectViewVisible(false);
        ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).delegateEvent(6);
        resetMetaDataManager();
    }

    private void startCount(final int i, int i2) {
        if (checkShutterCondition()) {
            setTriggerMode(i2);
            tryRemoveCountDownMessage();
            String str = TAG;
            Log.d(str, "startCount: " + i);
            Observable.interval(1, TimeUnit.SECONDS).take((long) i).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
                public void onComplete() {
                    Camera2Module.this.tryRemoveCountDownMessage();
                    Camera2Module.this.onShutterButtonFocus(true, 3);
                    Camera2Module.this.startNormalCapture(Camera2Module.this.getTriggerMode());
                    Camera2Module.this.onShutterButtonFocus(false, 0);
                    ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    if (topAlert != null) {
                        topAlert.reInitAlert(true);
                    }
                }

                public void onError(Throwable th) {
                }

                public void onNext(Long l) {
                    int intValue = i - (l.intValue() + 1);
                    if (intValue > 0) {
                        Camera2Module.this.playCameraSound(5);
                        Camera2Module.this.mMainProtocol.showDelayNumber(intValue);
                    }
                }

                public void onSubscribe(Disposable disposable) {
                    Disposable unused = Camera2Module.this.mCountdownDisposable = disposable;
                    Camera2Module.this.playCameraSound(7);
                    ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    if (topAlert != null) {
                        AndroidSchedulers.mainThread().scheduleDirect(new Runnable() {
                            public final void run() {
                                ModeProtocol.TopAlert.this.hideAlert();
                            }
                        }, 120, TimeUnit.MILLISECONDS);
                    }
                    Camera2Module.this.mMainProtocol.clearFocusView(7);
                    Camera2Module.this.mMainProtocol.showDelayNumber(i);
                }
            });
        }
    }

    private void startCpuBoost() {
        if (this.mCpuBoost == null && b.isMTKPlatform()) {
            this.mCpuBoost = new MtkBoost();
        }
        if (this.mCpuBoost != null) {
            this.mCpuBoost.startBoost();
        }
    }

    /* access modifiers changed from: private */
    public void startLensActivity() {
        if (b.gQ() || !Util.isGlobalVersion()) {
            Log.d(TAG, "start ai lens");
            try {
                Intent intent = new Intent();
                intent.setAction("android.media.action.XIAOAI_CONTROL");
                intent.setPackage(CameraSettings.AI_LENS_PACKAGE);
                intent.putExtra("preview_width", this.mPreviewSize.width);
                intent.putExtra("preview_height", this.mPreviewSize.height);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
            } catch (Exception e) {
                Log.e(TAG, "onClick: occur a exception", e);
            }
        } else if (this.mLensApi != null && this.mLensStatus == 0) {
            this.mLensApi.launchLensActivity(this.mActivity, 0);
        }
    }

    private void startLiveShotAnimation() {
        synchronized (this.mCircularMediaRecorderStateLock) {
            if (!(this.mCircularMediaRecorder == null || this.mHandler == null)) {
                this.mHandler.post(new Runnable() {
                    public void run() {
                        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                        if (topAlert != null) {
                            topAlert.startLiveShotAnimation();
                        }
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void startNormalCapture(int i) {
        String str = TAG;
        Log.d(str, "startNormalCapture mode -> " + i);
        prepareNormalCapture();
        if (!CameraSettings.isGroupShotOn() || isParallelSessionEnable()) {
            this.mHandler.sendEmptyMessageDelayed(50, calculateTimeout(this.mModuleIndex));
            this.mCamera2Device.setQuickShotAnimation(this.mQuickShotAnimateEnable);
            if (DataRepository.dataItemFeature().in()) {
                if (getModuleIndex() == 163 && getZoomRatio() == 1.0f) {
                    this.mCamera2Device.setFlawDetectEnable(true);
                } else {
                    this.mCamera2Device.setFlawDetectEnable(false);
                }
            }
            this.mCamera2Device.takePicture(this, this.mActivity.getImageSaver());
            if (!needQuickShot() || i == 90) {
                this.mBlockQuickShot = true;
            } else {
                Log.d(TAG, "startNormalCapture force set CameraStateConstant.IDLE");
                setCameraState(1);
                enableCameraControls(true);
            }
            if (isFrontCamera() && DataRepository.dataItemFeature().hm() && 32775 != this.mOperatingMode) {
                this.mCamera2Device.registerCaptureCallback(new Camera2Proxy.CaptureCallback() {
                    public final void onCaptureCompleted(boolean z) {
                        Camera2Module.lambda$startNormalCapture$5(Camera2Module.this, z);
                    }
                });
                return;
            }
            return;
        }
        this.mCamera2Device.captureGroupShotPictures(this, this.mActivity.getImageSaver(), this.mTotalJpegCallbackNum, this.mActivity);
        this.mBlockQuickShot = true;
    }

    private void stopCpuBoost() {
        if (this.mCpuBoost != null && b.isMTKPlatform()) {
            this.mCpuBoost.stopBoost();
            this.mCpuBoost = null;
        }
    }

    /* access modifiers changed from: private */
    public void stopMultiSnap() {
        Log.d(TAG, "stopMultiSnap: start");
        this.mHandler.removeMessages(49);
        if (this.mMultiSnapStatus) {
            this.mLastCaptureTime = System.currentTimeMillis();
            if (this.mCamera2Device != null && this.mMultiSnapStatus) {
                this.mCamera2Device.captureAbortBurst();
                this.mMultiSnapStatus = false;
            }
            boolean z = true;
            int i = !this.mMutexModePicker.isUbiFocus() ? this.mReceivedJpegCallbackNum : 1;
            boolean z2 = !this.mMutexModePicker.isUbiFocus();
            trackGeneralInfo(i, z2);
            CameraStat.PictureTakenParameter pictureTakenParameter = new CameraStat.PictureTakenParameter();
            pictureTakenParameter.takenNum = i;
            pictureTakenParameter.burst = z2;
            if (this.mLocation == null) {
                z = false;
            }
            pictureTakenParameter.location = z;
            pictureTakenParameter.aiSceneName = getCurrentAiSceneName();
            pictureTakenParameter.isEnteringMoon = this.mEnteringMoonMode;
            pictureTakenParameter.isSelectMoonMode = this.mIsMoonMode;
            pictureTakenParameter.isSuperNightInCaptureMode = this.mShowSuperNightHint;
            trackPictureTaken(pictureTakenParameter);
            animateCapture();
            this.mUpdateImageTitle = false;
            this.mHandler.sendEmptyMessageDelayed(48, 800);
        }
    }

    private void trackAISceneChanged(int i, int i2) {
        CameraStatUtil.trackAISceneChanged(i, i2, getResources());
    }

    private void trackBeautyInfo(int i, boolean z, BeautyValues beautyValues) {
        CameraStatUtil.trackBeautyInfo(i, z ? CameraStat.VALUE_FRONT_CAMERA : CameraStat.VALUE_BACK_CAMERA, beautyValues);
    }

    private void trackManualInfo(int i) {
        CameraStatUtil.trackPictureTakenInManual(i, getManualValue(CameraSettings.KEY_WHITE_BALANCE, getString(R.string.pref_camera_whitebalance_default)), getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default)), getManualValue(CameraSettings.KEY_QC_ISO, getString(R.string.pref_camera_iso_default)), this.mModuleIndex);
        CameraStatUtil.trackUltraWideManualTaken(this.mModuleIndex);
    }

    private void unlockAEAF() {
        Log.d(TAG, "unlockAEAF");
        this.m3ALocked = false;
        if (this.mAeLockSupported) {
            this.mCamera2Device.unlockExposure();
            if (!DataRepository.dataItemFeature().hG() && this.mUltraWideAELocked) {
                String focusMode = CameraSettings.getFocusMode();
                String str = TAG;
                Log.d(str, "unlockAEAF: focusMode = " + focusMode);
                setFocusMode(focusMode);
                this.mUltraWideAELocked = false;
            }
        }
        if (this.mFocusManager != null) {
            this.mFocusManager.setAeAwbLock(false);
        }
    }

    private void unregisterSensor() {
        if (CameraSettings.isGradienterOn()) {
            this.mActivity.getSensorStateManager().setGradienterEnabled(false);
        }
        this.mActivity.getSensorStateManager().setLieIndicatorEnabled(false);
        this.mIsShowLyingDirectHintStatus = -1;
        this.mHandler.removeMessages(58);
    }

    private void updateASDDirtyDetect() {
        this.mCamera2Device.setAsdDirtyEnable(CameraSettings.isLensDirtyDetectEnabled() && DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_LENS_DIRTY_TIP, getResources().getBoolean(R.bool.pref_lens_dirty_tip_default)) && CameraSettings.shouldShowLensDirtyDetectHint());
    }

    private void updateAiScene() {
        if (this.mFunctionParseAiScene != null) {
            this.mFunctionParseAiScene.resetScene();
        }
        this.mCurrentAiScene = 0;
        this.mAiSceneEnabled = CameraSettings.getAiSceneOpen(this.mModuleIndex);
        if (isFrontCamera() && this.mActivity.isScreenSlideOff()) {
            this.mAiSceneEnabled = false;
        }
        this.mCamera2Device.setASD(this.mAiSceneEnabled);
        if ((isFrontCamera() && ModuleManager.isCapture()) || !this.mAiSceneEnabled) {
            this.mCamera2Device.setCameraAI30(this.mAiSceneEnabled);
        }
        setAiSceneEffect(0);
        this.mCamera2Device.setASDScene(0);
        if (this.mAiSceneEnabled) {
            this.mCamera2Device.setASDPeriod(300);
            return;
        }
        hideSceneSelector();
        updateHDRPreference();
        updateFlashPreference();
        updateBeauty();
    }

    private void updateAlgorithmName() {
        this.mAlgorithmName = !b.ks() ? this.mCamera2Device.isBokehEnabled() ? DataRepository.dataItemFeature().gP() > 0 ? Util.ALGORITHM_NAME_SOFT_PORTRAIT_ENCRYPTED : Util.ALGORITHM_NAME_SOFT_PORTRAIT : isPortraitMode() ? Util.ALGORITHM_NAME_PORTRAIT : this.mMutexModePicker.getAlgorithmName() : null;
    }

    private void updateBeauty() {
        if (this.mModuleIndex == 163 || this.mModuleIndex == 165 || this.mModuleIndex == 171) {
            if (this.mBeautyValues == null) {
                this.mBeautyValues = new BeautyValues();
            }
            CameraSettings.initBeautyValues(this.mBeautyValues, this.mModuleIndex);
            if (!DataRepository.dataItemConfig().getComponentConfigBeauty().isClosed(this.mModuleIndex) && this.mCurrentAiScene == 25 && !isFaceBeautyOn(this.mBeautyValues)) {
                ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
                if (componentRunningShine.supportBeautyLevel()) {
                    this.mBeautyValues.mBeautyLevel = BeautyConstant.LEVEL_LOW;
                } else if (componentRunningShine.supportSmoothLevel()) {
                    this.mBeautyValues.mBeautySkinSmooth = 10;
                }
                Log.d(TAG, String.format(Locale.ENGLISH, "Human scene mode detected, auto set beauty level from %s to %s", new Object[]{BeautyConstant.LEVEL_CLOSE, this.mBeautyValues.mBeautyLevel}));
            }
            String str = TAG;
            Log.d(str, "updateBeauty(): " + this.mBeautyValues);
            this.mCamera2Device.setBeautyValues(this.mBeautyValues);
            this.mIsBeautyBodySlimOn = this.mBeautyValues.isBeautyBodyOn();
            updateFaceAgeAnalyze();
        }
    }

    private void updateBokeh() {
        ComponentConfigBokeh componentBokeh = DataRepository.dataItemConfig().getComponentBokeh();
        if (!ModuleManager.isPortraitModule() && !"on".equals(componentBokeh.getComponentValue(this.mModuleIndex))) {
            this.mCamera2Device.setMiBokeh(false);
            this.mCamera2Device.setRearBokehEnable(false);
        } else if (isSingleCamera()) {
            this.mCamera2Device.setMiBokeh(true);
            this.mCamera2Device.setRearBokehEnable(false);
        } else {
            this.mCamera2Device.setMiBokeh(false);
            this.mCamera2Device.setRearBokehEnable(true);
        }
    }

    private void updateCaptureTriggerFlow() {
    }

    private void updateContrast() {
        this.mCamera2Device.setContrast(Integer.parseInt(getString(R.string.pref_camera_contrast_default)));
    }

    private void updateDecodePreview() {
        if (this.mIsGoogleLensAvailable || scanQRCodeEnabled() || b.gX()) {
            String str = TAG;
            Log.d(str, "updateDecodePreview: PreviewDecodeManager AlgorithmPreviewSize = " + this.mCamera2Device.getAlgorithmPreviewSize());
            this.mCamera2Device.startPreviewCallback(PreviewDecodeManager.getInstance().getPreviewCallback());
            PreviewDecodeManager.getInstance().startDecode();
        }
    }

    private void updateDeviceOrientation() {
        this.mCamera2Device.setDeviceOrientation(this.mOrientation);
    }

    private void updateEnablePreviewThumbnail(boolean z) {
        if (this.mModuleIndex == 167 && DataRepository.dataItemRunning().isSwitchOn("pref_camera_peak_key")) {
            this.mEnabledPreviewThumbnail = false;
        } else if ((this.mModuleIndex == 165 || this.mModuleIndex == 163) && DataRepository.dataItemRunning().isSwitchOn("pref_camera_tilt_shift_mode")) {
            this.mEnabledPreviewThumbnail = false;
        } else if (CameraSettings.isMiMovieOpen(this.mModuleIndex)) {
            this.mEnabledPreviewThumbnail = false;
        } else if (!isPreviewThumbnailWhenFlash()) {
            this.mEnabledPreviewThumbnail = false;
        } else if (this.mIsImageCaptureIntent) {
            this.mEnabledPreviewThumbnail = false;
        } else if (this.mEnableParallelSession || this.mEnableShot2Gallery || z || (this.mReceivedJpegCallbackNum == 0 && enablePreviewAsThumbnail())) {
            this.mEnabledPreviewThumbnail = true;
        }
    }

    private void updateExposureTime() {
        this.mCamera2Device.setExposureTime(Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))));
        updateManualEvAdjust();
    }

    private void updateEyeLight() {
        if (isFrontCamera() && DataRepository.dataItemFeature().gq()) {
            this.mCamera2Device.setEyeLight(Integer.parseInt(CameraSettings.getEyeLightType()));
        }
    }

    private void updateFNumber() {
        if (DataRepository.dataItemFeature().isSupportBokehAdjust()) {
            this.mCamera2Device.setFNumber(CameraSettings.readFNumber());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x006b  */
    private void updateFace() {
        boolean z;
        boolean z2;
        if (this.mMultiSnapStatus || this.mMutexModePicker.isUbiFocus()) {
            z2 = false;
        } else if (CameraSettings.isMagicMirrorOn() || CameraSettings.isPortraitModeBackOn() || CameraSettings.isGroupShotOn() || CameraSettings.showGenderAge()) {
            z2 = true;
            z = true;
            if (this.mMainProtocol != null) {
                this.mMainProtocol.setSkipDrawFace(!z2 || !z);
            }
            if (z2) {
                if (!this.mFaceDetectionEnabled) {
                    this.mFaceDetectionEnabled = true;
                    startFaceDetection();
                    return;
                }
                return;
            } else if (this.mFaceDetectionEnabled) {
                stopFaceDetection(true);
                this.mFaceDetectionEnabled = false;
                return;
            } else {
                return;
            }
        } else {
            z2 = DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_FACE_DETECTION, getResources().getBoolean(R.bool.pref_camera_facedetection_default));
            if (CameraSettings.isTiltShiftOn()) {
                z = false;
                if (this.mMainProtocol != null) {
                }
                if (z2) {
                }
            }
        }
        z = true;
        if (this.mMainProtocol != null) {
        }
        if (z2) {
        }
    }

    private void updateFaceAgeAnalyze() {
        this.mIsGenderAgeOn = DataRepository.dataItemRunning().isSwitchOn("pref_camera_show_gender_age_key");
        this.mCamera2Device.setFaceAgeAnalyze(this.mIsGenderAgeOn ? true : this.mBeautyValues != null ? isFaceBeautyOn(this.mBeautyValues) : false);
        if (EffectController.getInstance().hasEffect()) {
            prepareEffectProcessor(FilterInfo.FILTER_ID_NONE);
        }
    }

    private void updateFaceScore() {
        this.mIsMagicMirrorOn = DataRepository.dataItemRunning().isSwitchOn("pref_camera_magic_mirror_key");
        this.mCamera2Device.setFaceScore(this.mIsMagicMirrorOn);
        if (EffectController.getInstance().hasEffect()) {
            prepareEffectProcessor(FilterInfo.FILTER_ID_NONE);
        }
    }

    private void updateFilter() {
        setEffectFilter(CameraSettings.getShaderEffect());
    }

    private void updateFocusArea() {
        if (this.mActivity != null && !this.mActivity.isActivityPaused() && isAlive()) {
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

    private void updateFocusMode() {
        String str;
        if (this.mIsMoonMode) {
            this.mFocusManager.removeMessages();
            str = this.mFocusManager.setFocusMode("manual");
        } else {
            str = this.mFocusManager.setFocusMode(CameraSettings.getFocusMode());
        }
        setFocusMode(str);
        if (CameraSettings.isFocusModeSwitching() && isBackCamera()) {
            CameraSettings.setFocusModeSwitching(false);
            this.mFocusManager.resetFocusStateIfNeeded();
        }
        if (str.equals("manual")) {
            float minimumFocusDistance = (this.mCameraCapabilities.getMinimumFocusDistance() * ((float) CameraSettings.getFocusPosition())) / 1000.0f;
            if (this.mIsMoonMode) {
                minimumFocusDistance = 0.5f;
            }
            this.mCamera2Device.setFocusDistance(minimumFocusDistance);
        }
    }

    private void updateFpsRange() {
        Range[] supportedFpsRange = this.mCameraCapabilities.getSupportedFpsRange();
        Range range = supportedFpsRange[0];
        for (Range range2 : supportedFpsRange) {
            if (((Integer) range.getUpper()).intValue() < ((Integer) range2.getUpper()).intValue() || (range.getUpper() == range2.getUpper() && ((Integer) range.getLower()).intValue() < ((Integer) range2.getLower()).intValue())) {
                range = range2;
            }
        }
        if (b.sF && CameraSettings.isPortraitModeBackOn()) {
            this.mCamera2Device.setFpsRange(new Range(30, 30));
        }
    }

    private void updateFrontMirror() {
        this.mCamera2Device.setFrontMirror(isFrontMirror());
    }

    private void updateHDR(String str) {
        if ("auto".equals(str)) {
            this.isDetectedInHdr = false;
        }
        int mutexHdrMode = getMutexHdrMode(str);
        stopObjectTracking(true);
        if (mutexHdrMode != 0) {
            this.mMutexModePicker.setMutexMode(mutexHdrMode);
        } else if (this.mMutexModePicker.isHdr()) {
            resetMutexModeManually();
        }
        if (isFrontCamera() && isTriggerQcfaModeChange(false, true)) {
            this.mHandler.sendEmptyMessage(44);
        }
    }

    private void updateISO() {
        String string = getString(R.string.pref_camera_iso_default);
        String manualValue = getManualValue(CameraSettings.KEY_QC_ISO, string);
        if (manualValue == null || manualValue.equals(string)) {
            this.mCamera2Device.setISO(0);
        } else {
            this.mCamera2Device.setISO(Math.min(Util.parseInt(manualValue, 0), this.mCameraCapabilities.getMaxIso()));
        }
        updateManualEvAdjust();
    }

    private void updateJpegQuality() {
        int jpegQuality = getJpegQuality(this.mMultiSnapStatus);
        if (DataRepository.dataItemRunning().getComponentUltraPixel().isRear108MPSwitchOn()) {
            jpegQuality = Util.clamp(jpegQuality, 0, 90);
        }
        this.mCamera2Device.setJpegQuality(jpegQuality);
    }

    private void updateJpegThumbnailSize() {
        CameraSize jpegThumbnailSize = getJpegThumbnailSize();
        this.mCamera2Device.setJpegThumbnailSize(jpegThumbnailSize);
        String str = TAG;
        Log.d(str, "thumbnailSize=" + jpegThumbnailSize);
    }

    private void updateLiveShot() {
        if (DataRepository.dataItemFeature().gz() && this.mModuleIndex == 163) {
            if (CameraSettings.isLiveShotOn()) {
                startLiveShot();
            } else {
                stopLiveShot(false);
            }
        }
    }

    private void updateMacroMode() {
        this.mCamera2Device.setMacroMode(CameraSettings.isMacroModeEnabled(this.mModuleIndex));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0042, code lost:
        if (isUltraWideBackCamera() != false) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x005e, code lost:
        if (java.lang.Long.parseLong(getManualValue(com.android.camera.CameraSettings.KEY_QC_EXPOSURETIME, getString(com.android.camera.R.string.pref_camera_exposuretime_default))) < 250000000) goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006b, code lost:
        if (isDualCamera() == false) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008b, code lost:
        if (enableFrontMFNR() == false) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ad, code lost:
        if (isZoomRatioBetweenUltraAndWide() == false) goto L_0x00b1;
     */
    private void updateMfnr(boolean z) {
        boolean z2 = false;
        if (!isUseSwMfnr() && z) {
            if (this.mModuleIndex == 167) {
                boolean hu = DataRepository.dataItemFeature().hu();
                DataRepository.dataItemRunning().getComponentUltraPixel().isRear108MPSwitchOn();
                if (!isSensorRawStreamRequired()) {
                    if (hu) {
                        if (!isSingleCamera()) {
                            if (!isStandaloneMacroCamera()) {
                            }
                        }
                        if (this.mCamera2Device != null) {
                        }
                    }
                }
            } else {
                if (b.sE) {
                }
                if (this.mMutexModePicker.isNormal()) {
                    if (!CameraSettings.isGroupShotOn()) {
                        if (isFrontCamera()) {
                        }
                        if (!DataRepository.dataItemFeature().fF()) {
                            if (getZoomRatio() != 1.0f) {
                                if (!isUltraWideBackCamera()) {
                                }
                            }
                        }
                    }
                }
            }
            z2 = true;
        }
        if (this.mCamera2Device != null) {
            Log.d(TAG, "setMfnr to " + z2);
            this.mCamera2Device.setMfnr(z2);
        }
    }

    private void updateMiMovie() {
        this.mCamera2Device.setMiMovie(CameraSettings.isMiMovieOpen(this.mModuleIndex));
    }

    private void updateMute() {
    }

    private void updateNormalWideLDC() {
        this.mCamera2Device.setNormalWideLDC(shouldApplyNormalWideLDC());
    }

    private void updateOIS() {
        boolean z = isDualCamera() && !this.mCameraCapabilities.isTeleOISSupported() && getZoomRatio() > 1.0f;
        boolean isPortraitModeBackOn = CameraSettings.isPortraitModeBackOn();
        boolean z2 = this.mModuleIndex == 167 && Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))) > 1000000000;
        if (z || isPortraitModeBackOn || z2) {
            this.mCamera2Device.setEnableOIS(false);
        } else {
            this.mCamera2Device.setEnableOIS(true);
        }
    }

    private void updateOutputSize(CameraSize cameraSize) {
        if (165 == this.mModuleIndex) {
            int i = cameraSize.width > cameraSize.height ? cameraSize.height : cameraSize.width;
            this.mOutPutSize = new CameraSize(i, i);
            return;
        }
        this.mOutPutSize = cameraSize;
    }

    private void updatePictureAndPreviewSize() {
        int i = this.mEnableParallelSession ? 35 : 256;
        int[] sATSubCameraIds = this.mCamera2Device.getSATSubCameraIds();
        boolean z = sATSubCameraIds != null;
        if (z) {
            String str = TAG;
            Log.d(str, "[SAT] camera list: " + Arrays.toString(sATSubCameraIds));
            int length = sATSubCameraIds.length;
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = sATSubCameraIds[i2];
                if (i3 == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                    if (this.mUltraCameraCapabilities == null) {
                        this.mUltraCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getUltraWideCameraId());
                    }
                    if (this.mUltraCameraCapabilities != null) {
                        this.mUltraCameraCapabilities.setOperatingMode(this.mOperatingMode);
                        this.mUltraWidePictureSize = getBestPictureSize(this.mUltraCameraCapabilities.getSupportedOutputSize(i));
                    }
                } else if (i3 == Camera2DataContainer.getInstance().getMainBackCameraId()) {
                    if (this.mWideCameraCapabilities == null) {
                        this.mWideCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getMainBackCameraId());
                    }
                    if (this.mWideCameraCapabilities != null) {
                        this.mWideCameraCapabilities.setOperatingMode(this.mOperatingMode);
                        this.mWidePictureSize = getBestPictureSize(this.mWideCameraCapabilities.getSupportedOutputSize(i));
                    }
                } else if (i3 == Camera2DataContainer.getInstance().getAuxCameraId()) {
                    if (this.mTeleCameraCapabilities == null) {
                        this.mTeleCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getAuxCameraId());
                    }
                    if (this.mTeleCameraCapabilities != null) {
                        this.mTeleCameraCapabilities.setOperatingMode(this.mOperatingMode);
                        this.mTelePictureSize = getBestPictureSize(this.mTeleCameraCapabilities.getSupportedOutputSize(i));
                    }
                } else if (i3 == Camera2DataContainer.getInstance().getUltraTeleCameraId()) {
                    if (this.mUltraTeleCameraCapabilities == null) {
                        this.mUltraTeleCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getUltraTeleCameraId());
                    }
                    if (this.mUltraTeleCameraCapabilities != null) {
                        this.mUltraTeleCameraCapabilities.setOperatingMode(this.mOperatingMode);
                        this.mUltraTelePictureSize = getBestPictureSize(this.mUltraTeleCameraCapabilities.getSupportedOutputSize(i));
                    }
                    this.mCamera2Device.setUltraTelePictureSize(this.mUltraTelePictureSize);
                }
            }
            if (DataRepository.dataItemFeature().hW()) {
                Log.d(TAG, String.format(Locale.ENGLISH, "ultraWideSize: %s, wideSize: %s, teleSize: %s, ultraTeleSize:%s", new Object[]{this.mUltraWidePictureSize, this.mWidePictureSize, this.mTelePictureSize, this.mUltraTelePictureSize}));
            } else {
                Log.d(TAG, String.format(Locale.ENGLISH, "ultraWideSize: %s, wideSize: %s, teleSize: %s", new Object[]{this.mUltraWidePictureSize, this.mWidePictureSize, this.mTelePictureSize}));
            }
            this.mCamera2Device.setUltraWidePictureSize(this.mUltraWidePictureSize);
            this.mCamera2Device.setWidePictureSize(this.mWidePictureSize);
            this.mCamera2Device.setTelePictureSize(this.mTelePictureSize);
            this.mPictureSize = getSatPictureSize();
        } else {
            int ir = (!isUltraTeleCamera() || this.mModuleIndex != 167) ? 0 : DataRepository.dataItemFeature().ir();
            if (isSensorRawStreamRequired()) {
                List<CameraSize> supportedOutputSize = this.mCameraCapabilities.getSupportedOutputSize(32);
                if (this.mModuleIndex != 167) {
                    this.mSensorRawImageSize = getBestPictureSize(supportedOutputSize);
                } else if (supportedOutputSize == null || supportedOutputSize.size() == 0) {
                    Log.w(TAG, "The supported raw size list return from hal is null!");
                } else if (ir == 0) {
                    this.mSensorRawImageSize = getBestPictureSize(supportedOutputSize, 1.3333333f);
                } else {
                    PictureSizeManager.initializeLimitWidth(supportedOutputSize, ir, this.mModuleIndex, this.mBogusCameraId);
                    this.mSensorRawImageSize = PictureSizeManager.getBestPictureSize(1.3333333f);
                }
                String str2 = TAG;
                Log.d(str2, "The best sensor raw image size: " + this.mSensorRawImageSize);
            }
            if (!this.mEnableParallelSession || !isPortraitMode()) {
                List<CameraSize> supportedOutputSize2 = this.mCameraCapabilities.getSupportedOutputSize(i);
                CameraSize bestPictureSize = getBestPictureSize(supportedOutputSize2);
                if (b.sM && getOperatingMode() == 36867) {
                    bestPictureSize = new CameraSize(bestPictureSize.width / 2, bestPictureSize.height / 2);
                }
                if (b.isMTKPlatform() && isFrontCamera()) {
                    bestPictureSize = new CameraSize(bestPictureSize.width / 2, bestPictureSize.height / 2);
                }
                if (ir != 0) {
                    PictureSizeManager.initializeLimitWidth(supportedOutputSize2, ir, this.mModuleIndex, this.mBogusCameraId);
                    bestPictureSize = PictureSizeManager.getBestPictureSize();
                }
                this.mPictureSize = bestPictureSize;
            } else {
                updatePortraitPictureSize(i);
            }
        }
        List<CameraSize> supportedOutputSize3 = this.mCameraCapabilities.getSupportedOutputSize(SurfaceTexture.class);
        double previewAspectRatio = (double) CameraSettings.getPreviewAspectRatio(this.mPictureSize.width, this.mPictureSize.height);
        this.mPreviewSize = Util.getOptimalPreviewSize(false, this.mBogusCameraId, supportedOutputSize3, previewAspectRatio);
        this.mCamera2Device.setPreviewSize(this.mPreviewSize);
        if (this.mIsGoogleLensAvailable) {
            this.mCamera2Device.setAlgorithmPreviewSize(Util.getAlgorithmPreviewSize(supportedOutputSize3, previewAspectRatio, this.mPreviewSize));
        } else {
            this.mCamera2Device.setAlgorithmPreviewSize(this.mPreviewSize);
        }
        this.mCamera2Device.setAlgorithmPreviewFormat(35);
        if (this.mEnableParallelSession) {
            List<CameraSize> supportedOutputSize4 = this.mCameraCapabilities.getSupportedOutputSize(256);
            if (this.mModuleIndex == 165) {
                int i4 = this.mPictureSize.width > this.mPictureSize.height ? this.mPictureSize.height : this.mPictureSize.width;
                this.mOutPutSize = PictureSizeManager.getBestSquareSize(supportedOutputSize4, i4);
                if (this.mOutPutSize.isEmpty()) {
                    Log.w(TAG, String.format(Locale.ENGLISH, "size %dx%d is not supported!", new Object[]{Integer.valueOf(i4), Integer.valueOf(i4)}));
                    this.mOutPutSize = new CameraSize(i4, i4);
                }
            } else if (z) {
                this.mOutPutSize = this.mPictureSize;
            } else {
                this.mOutPutSize = PictureSizeManager.getBestPictureSize(supportedOutputSize4);
            }
            String str3 = TAG;
            Log.d(str3, "output picture size: " + this.mOutPutSize);
        }
        Log.d(TAG, String.format(Locale.ENGLISH, "updateSize: picture size is: %s, preview size is: %s, sensor raw image size is: %s", new Object[]{this.mPictureSize, this.mPreviewSize, this.mSensorRawImageSize}));
        updateCameraScreenNailSize(this.mPreviewSize.width, this.mPreviewSize.height);
        checkDisplayOrientation();
        setVideoSize(this.mPreviewSize.width, this.mPreviewSize.height);
    }

    private void updatePortraitLighting() {
        String valueOf = String.valueOf(CameraSettings.getPortraitLightingPattern());
        this.mIsPortraitLightingOn = !valueOf.equals("0");
        this.mCamera2Device.setPortraitLighting(Integer.parseInt(valueOf));
        setLightingEffect();
    }

    private void updatePortraitPictureSize(int i) {
        int i2;
        boolean z;
        boolean z2;
        if (!isFrontCamera()) {
            z2 = DataRepository.dataItemFeature().im();
            z = DataRepository.dataItemRunning().isSwitchOn("pref_ultra_wide_bokeh_enabled");
            i2 = z ? Camera2DataContainer.getInstance().getUltraWideCameraId() : b.kG() ? this.mCamera2Device.getBokehAuxCameraId() : Camera2DataContainer.getInstance().getAuxCameraId();
        } else if (isDualFrontCamera()) {
            i2 = Camera2DataContainer.getInstance().getAuxFrontCameraId();
            z2 = true;
            z = false;
        } else {
            i2 = -1;
            z2 = false;
            z = false;
        }
        Log.d(TAG, "BS = " + z2 + " UW = " + z + " id = " + i2);
        PictureSizeManager.initializeLimitWidth(this.mCameraCapabilities.getSupportedOutputSize(i), DataRepository.dataItemFeature().r(isBackCamera()), this.mModuleIndex, this.mBogusCameraId);
        CameraSize bestPictureSize = PictureSizeManager.getBestPictureSize();
        if (b.sM && getOperatingMode() == 36867) {
            bestPictureSize = new CameraSize(bestPictureSize.width / 2, bestPictureSize.height / 2);
        }
        if (b.isMTKPlatform() && isFrontCamera()) {
            bestPictureSize = new CameraSize(bestPictureSize.width / 2, bestPictureSize.height / 2);
        }
        CameraSize cameraSize = null;
        if (-1 == i2) {
            this.mPictureSize = bestPictureSize;
            this.mSubPictureSize = null;
        } else {
            if (z2) {
                cameraSize = bestPictureSize;
            }
            CameraSize pictureSize = getPictureSize(i2, i, cameraSize);
            if (z || z2) {
                this.mPictureSize = bestPictureSize;
                this.mSubPictureSize = pictureSize;
            } else {
                this.mPictureSize = pictureSize;
                this.mSubPictureSize = bestPictureSize;
            }
        }
        Log.d(TAG, String.format(Locale.ENGLISH, "mainSize = %s subSize = %s", new Object[]{this.mPictureSize, this.mSubPictureSize}));
    }

    private void updateSRAndMFNR() {
        if (b.rp.equals("tucana") && !this.mMutexModePicker.isHdr()) {
            if (isIn3OrMoreSatMode() && getZoomRatio() > 1.0f && this.mCamera2Device.getSatMasterCameraId() == 2) {
                this.mMutexModePicker.resetMutexMode();
            } else {
                updateSuperResolution();
            }
        }
    }

    private void updateSaturation() {
        this.mCamera2Device.setSaturation(Integer.parseInt(getString(R.string.pref_camera_saturation_default)));
    }

    private void updateScene() {
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        if (this.mMutexModePicker.isSceneHdr()) {
            this.mSceneMode = CameraScene.HDR;
        } else if (!dataItemRunning.isSwitchOn("pref_camera_scenemode_setting_key")) {
            this.mSceneMode = "-1";
        } else {
            this.mSceneMode = dataItemRunning.getComponentRunningSceneValue().getComponentValue(this.mModuleIndex);
        }
        String str = TAG;
        Log.d(str, "sceneMode=" + this.mSceneMode + " mutexMode=" + this.mMutexModePicker.getMutexMode());
        if (!setSceneMode(this.mSceneMode)) {
            this.mSceneMode = "-1";
        }
        this.mHandler.post(new Runnable() {
            public void run() {
                Camera2Module.this.updateSceneModeUI();
            }
        });
        if ("0".equals(this.mSceneMode) || "-1".equals(this.mSceneMode)) {
            this.mFocusManager.overrideFocusMode((String) null);
        } else {
            this.mFocusManager.overrideFocusMode(AutoFocus.LEGACY_CONTINUOUS_PICTURE);
        }
    }

    /* access modifiers changed from: private */
    public void updateSceneModeUI() {
        boolean isSwitchOn = DataRepository.dataItemRunning().isSwitchOn("pref_camera_scenemode_setting_key");
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (isSwitchOn) {
            DataRepository.dataItemConfig().getComponentHdr().setComponentValue(163, "off");
            String flashModeByScene = CameraSettings.getFlashModeByScene(this.mSceneMode);
            if (topAlert != null) {
                topAlert.disableMenuItem(false, 194);
                if (flashModeByScene != null) {
                    topAlert.disableMenuItem(false, 193);
                } else {
                    topAlert.enableMenuItem(false, 193);
                }
                topAlert.hideExtraMenu();
            }
        } else if (topAlert != null) {
            topAlert.enableMenuItem(false, 193, 194);
        }
        if (topAlert != null) {
            topAlert.updateConfigItem(193, 194);
        }
        updatePreferenceInWorkThread(11, 10);
    }

    private void updateSharpness() {
        this.mCamera2Device.setSharpness(Integer.parseInt(getString(R.string.pref_camera_sharpness_default)));
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0065  */
    private void updateShotDetermine() {
        boolean z;
        int i;
        int i2 = 1;
        if (this.mModuleIndex == 171) {
            if (!isBackCamera()) {
                z = DataRepository.dataItemFeature().fK();
            } else if (b.kl() || DataRepository.dataItemFeature().fJ()) {
                z = true;
            }
            this.mEnableParallelSession = isParallelSessionEnable();
            if (this.mIsImageCaptureIntent) {
                this.mEnableShot2Gallery = !this.mEnableParallelSession && DataRepository.dataItemFeature().gS() && enablePreviewAsThumbnail() && !CameraSettings.isLiveShotOn();
                int i3 = this.mModuleIndex;
                if (!(i3 == 163 || i3 == 165 || i3 == 167)) {
                    if (i3 != 171) {
                        if (i3 == 173) {
                            i2 = isCurrentRawDomainBasedSuperNight() ? 10 : 0;
                        } else if (i3 != 175) {
                            this.mEnableParallelSession = false;
                            return;
                        }
                    } else if (!this.mEnableParallelSession) {
                        i2 = z ? 2 : 0;
                    } else if (shouldDoMultiFrameCapture()) {
                        i2 = 8;
                    } else {
                        if (isDualFrontCamera() || isDualCamera() || isBokehUltraWideBackCamera()) {
                            if (z) {
                                i = 6;
                            }
                        } else if (z) {
                            i = 7;
                        }
                        i2 = 5;
                    }
                    Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                    this.mCamera2Device.setShotType(i2);
                    this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                }
                if (this.mEnableParallelSession) {
                    i2 = shouldDoMultiFrameCapture() ? 8 : 5;
                } else if (!this.mConfigRawStream || this.mModuleIndex != 167) {
                    i2 = 0;
                }
                Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                this.mCamera2Device.setShotType(i2);
                this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
            } else if (this.mEnableParallelSession) {
                i2 = -5;
                if (isDualFrontCamera() || isDualCamera() || isBokehUltraWideBackCamera()) {
                    if (z) {
                        i = -7;
                    }
                    Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                    this.mCamera2Device.setShotType(i2);
                    this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                }
                if (z) {
                    i = -6;
                }
                Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                this.mCamera2Device.setShotType(i2);
                this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
            } else {
                i = z ? -3 : -2;
            }
            i2 = i;
            Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
            this.mCamera2Device.setShotType(i2);
            this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
        }
        z = false;
        this.mEnableParallelSession = isParallelSessionEnable();
        if (this.mIsImageCaptureIntent) {
        }
        i2 = i;
        Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
        this.mCamera2Device.setShotType(i2);
        this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
    }

    private void updateSuperNight() {
        this.mCamera2Device.setSuperNight(this.mModuleIndex == 173 || CameraSettings.isSuperNightOn());
    }

    private void updateSuperResolution() {
        if (isFrontCamera() || this.mModuleIndex == 173) {
            return;
        }
        if (isUltraWideBackCamera()) {
            Log.d(TAG, "SR force off for ultra wide camera");
        } else if (CameraSettings.isSREnable()) {
            if (ModuleManager.isManualModule()) {
                boolean hu = DataRepository.dataItemFeature().hu();
                DataRepository.dataItemRunning().getComponentUltraPixel().isRear108MPSwitchOn();
                if (!isSensorRawStreamRequired() && hu && ((isUltraTeleCamera() || isAuxCamera()) && this.mCamera2Device != null && Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))) < 250000000)) {
                    this.mCamera2Device.setSuperResolution(true);
                } else if (this.mCamera2Device != null) {
                    this.mCamera2Device.setSuperResolution(false);
                }
            } else if (getZoomRatio() <= 1.0f) {
                if (DataRepository.dataItemRunning().isSwitchOn("pref_camera_super_resolution_key")) {
                    return;
                }
                if (this.mMutexModePicker.isSuperResolution()) {
                    this.mMutexModePicker.resetMutexMode();
                } else if (this.mCamera2Device != null) {
                    this.mCamera2Device.setSuperResolution(false);
                }
            } else if (CameraSettings.isGroupShotOn()) {
                if (this.mMutexModePicker.isSuperResolution()) {
                    this.mMutexModePicker.resetMutexMode();
                }
            } else if (this.mMutexModePicker.isNormal()) {
                this.mMutexModePicker.setMutexMode(10);
            }
        }
    }

    private void updateSwMfnr() {
        boolean isUseSwMfnr = isUseSwMfnr();
        String str = TAG;
        Log.d(str, "setSwMfnr to " + isUseSwMfnr);
        this.mCamera2Device.setSwMfnr(isUseSwMfnr);
    }

    private void updateThumbProgress(boolean z) {
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        if (actionProcessing != null) {
            actionProcessing.updateLoading(z);
        }
    }

    private void updateUltraPixelPortrait() {
        this.mCamera2Device.setUltraPixelPortrait(CameraSettings.isUltraPixelPortraitFrontOn());
    }

    private void updateUltraWideLDC() {
        this.mCamera2Device.setUltraWideLDC(shouldApplyUltraWideLDC());
    }

    private void updateWhiteBalance() {
        setAWBMode(getManualValue(CameraSettings.KEY_WHITE_BALANCE, "1"));
    }

    private void updateZsl() {
        this.mCamera2Device.setEnableZsl(isZslPreferred());
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

    public void closeBacklightTip(int i, int i2) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (this.mCurrentAiScene == -1 && this.mCurrentAiScene != i) {
            topAlert.alertAiSceneSelector(i2);
            this.mCamera2Device.setASD(true);
        }
    }

    public void closeCamera() {
        Log.d(TAG, "closeCamera: E");
        setCameraState(0);
        synchronized (this.mCameraDeviceLock) {
            if (this.mCamera2Device != null) {
                if (this.mMultiSnapStatus) {
                    this.mCamera2Device.captureAbortBurst();
                    this.mMultiSnapStatus = false;
                }
                if (this.mBurstDisposable != null) {
                    this.mBurstDisposable.dispose();
                }
                if (this.mMetaDataFlowableEmitter != null) {
                    this.mMetaDataFlowableEmitter.onComplete();
                }
                if (this.mMetaDataDisposable != null) {
                    this.mMetaDataDisposable.dispose();
                }
                if (this.mAiSceneFlowableEmitter != null) {
                    this.mAiSceneFlowableEmitter.onComplete();
                }
                if (this.mAiSceneDisposable != null) {
                    this.mAiSceneDisposable.dispose();
                }
                if (this.mSuperNightDisposable != null) {
                    this.mSuperNightDisposable.dispose();
                }
                this.mCamera2Device.setScreenLightCallback((Camera2Proxy.ScreenLightCallback) null);
                this.mCamera2Device.setMetaDataCallback((Camera2Proxy.CameraMetaDataCallback) null);
                this.mCamera2Device.setErrorCallback((Camera2Proxy.CameraErrorCallback) null);
                this.mCamera2Device.releaseCameraPreviewCallback((Camera2Proxy.CameraPreviewCallback) null);
                this.mCamera2Device.setFocusCallback((Camera2Proxy.FocusCallback) null);
                this.mCamera2Device.setASD(false);
                if (scanQRCodeEnabled() || b.gX() || this.mIsGoogleLensAvailable) {
                    this.mCamera2Device.stopPreviewCallback(true);
                }
                if (this.mFaceDetectionStarted) {
                    this.mFaceDetectionStarted = false;
                }
                this.m3ALocked = false;
                this.mCamera2Device = null;
            }
        }
        if (this.mFocusManager != null) {
            this.mFocusManager.setAeAwbLock(false);
            this.mFocusManager.destroy();
        }
        if (scanQRCodeEnabled() || b.gX() || this.mIsGoogleLensAvailable) {
            PreviewDecodeManager.getInstance().quit();
        }
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
        if (localBinder != null) {
            localBinder.setOnSessionStatusCallBackListener((ISessionStatusCallBackListener) null);
        }
        stopCpuBoost();
        Log.d(TAG, "closeCamera: X");
    }

    public void closeMoonMode(int i, int i2) {
        if (this.mEnteringMoonMode) {
            if ((this.mCurrentAiScene == 10 || this.mCurrentAiScene == 35) && i != this.mCurrentAiScene) {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    topAlert.alertMoonModeSelector(i2, false);
                    if (i2 != 0) {
                        this.mEnteringMoonMode = false;
                    }
                    if (8 == i2) {
                        Log.d(TAG, "(moon_mode) close moon mode");
                    }
                }
                updateMoon(false);
                if (this.mMutexModePicker.isSuperResolution() && !DataRepository.dataItemFeature().iz()) {
                    this.mCamera2Device.setSuperResolution(true);
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
                case 4:
                    updateScene();
                    break;
                case 5:
                    updateFace();
                    break;
                case 6:
                    updateWhiteBalance();
                    break;
                case 7:
                    updateJpegQuality();
                    break;
                case 8:
                    updateJpegThumbnailSize();
                    break;
                case 9:
                    updateAntiBanding(CameraSettings.getAntiBanding());
                    break;
                case 10:
                    updateFlashPreference();
                    break;
                case 11:
                    updateHDRPreference();
                    break;
                case 12:
                    setEvValue();
                    break;
                case 13:
                    updateBeauty();
                    updateEyeLight();
                    break;
                case 14:
                    updateFocusMode();
                    break;
                case 15:
                    updateISO();
                    break;
                case 16:
                    updateExposureTime();
                    break;
                case 19:
                    updateFpsRange();
                    break;
                case 20:
                    updateOIS();
                    break;
                case 21:
                    updateMute();
                    break;
                case 22:
                    updateZsl();
                    break;
                case 23:
                    updateDecodePreview();
                    break;
                case 24:
                    applyZoomRatio();
                    break;
                case 25:
                    focusCenter();
                    break;
                case 26:
                    updateContrast();
                    break;
                case 27:
                    updateSaturation();
                    break;
                case 28:
                    updateSharpness();
                    break;
                case 29:
                    updateExposureMeteringMode();
                    break;
                case 30:
                    updateSuperResolution();
                    break;
                case 34:
                    updateMfnr(CameraSettings.isMfnrSatEnable());
                    break;
                case 35:
                    updateDeviceOrientation();
                    break;
                case 36:
                    updateAiScene();
                    break;
                case 37:
                    updateBokeh();
                    break;
                case 38:
                    updateFaceAgeAnalyze();
                    break;
                case 39:
                    updateFaceScore();
                    break;
                case 40:
                    updateFrontMirror();
                    break;
                case 42:
                    updateSwMfnr();
                    break;
                case 43:
                    updatePortraitLighting();
                    break;
                case 44:
                    updateShotDetermine();
                    break;
                case 45:
                    updateEyeLight();
                    break;
                case 46:
                    updateNormalWideLDC();
                    break;
                case 47:
                    updateUltraWideLDC();
                    break;
                case 48:
                    updateFNumber();
                    break;
                case 49:
                    updateLiveShot();
                    break;
                case 50:
                    break;
                case 52:
                    updateMacroMode();
                    break;
                case 55:
                    updateModuleRelated();
                    break;
                case 56:
                    updateSuperNight();
                    break;
                case 57:
                    updateUltraPixelPortrait();
                    break;
                case 58:
                    updateBackSoftLightPreference();
                    break;
                case 59:
                    updateOnTripMode();
                    break;
                case 60:
                    updateMiMovie();
                    break;
                case 61:
                    updateASDDirtyDetect();
                    break;
                default:
                    throw new RuntimeException("no consumer for this updateType: " + i);
            }
        }
    }

    public void enterMutexMode(int i) {
        if (this.mCamera2Device == null) {
            Log.d(TAG, "enterMutexMode error, mCamera2Device is null");
            return;
        }
        if (i == 1) {
            this.mCamera2Device.setHDR(true);
        } else if (i == 3) {
            this.mCamera2Device.setHHT(true);
        } else if (i == 10) {
            this.mCamera2Device.setSuperResolution(true);
        }
        updateMfnr(CameraSettings.isMfnrSatEnable());
        updateSwMfnr();
    }

    public void exitMutexMode(int i) {
        if (i == 1) {
            this.mCamera2Device.setHDR(false);
            updateSuperResolution();
        } else if (i == 3) {
            this.mCamera2Device.setHHT(false);
        } else if (i == 10) {
            this.mCamera2Device.setSuperResolution(false);
        }
        updateMfnr(CameraSettings.isMfnrSatEnable());
        updateSwMfnr();
    }

    /* access modifiers changed from: protected */
    public void focusCenter() {
    }

    /* access modifiers changed from: protected */
    public CameraSize getBestPictureSize(List<CameraSize> list) {
        PictureSizeManager.initialize(list, getMaxPictureSize(), this.mModuleIndex, this.mBogusCameraId);
        return PictureSizeManager.getBestPictureSize();
    }

    /* access modifiers changed from: protected */
    public CameraSize getBestPictureSize(List<CameraSize> list, float f) {
        PictureSizeManager.initialize(list, getMaxPictureSize(), this.mModuleIndex, this.mBogusCameraId);
        return PictureSizeManager.getBestPictureSize(f);
    }

    public CircularMediaRecorder getCircularMediaRecorder() {
        CircularMediaRecorder circularMediaRecorder;
        synchronized (this.mCircularMediaRecorderStateLock) {
            circularMediaRecorder = this.mCircularMediaRecorder;
        }
        return circularMediaRecorder;
    }

    public int getCurrentAiScene() {
        return this.mCurrentAiScene;
    }

    public String getDebugInfo() {
        String str;
        StringBuilder sb = new StringBuilder();
        int moduleIndex = getModuleIndex();
        CameraCapabilities cameraCapabilities = getCameraCapabilities();
        if (cameraCapabilities != null) {
            CameraCharacteristics cameraCharacteristics = cameraCapabilities.getCameraCharacteristics();
            if (cameraCharacteristics != null) {
                float[] fArr = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
                float[] fArr2 = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES);
                if (fArr != null && fArr.length > 0) {
                    sb.append("LensFocal:" + fArr[0] + " ");
                }
                if (fArr2 != null && fArr2.length > 0) {
                    sb.append("LensApertues:" + fArr2[0] + " ");
                }
            }
        }
        if (moduleIndex == 167) {
            sb.append("SceneProfession:true");
        }
        sb.append("ZoomMultiple:" + getZoomRatio() + " ");
        Camera2Proxy cameraDevice = getCameraDevice();
        if (cameraDevice != null) {
            CameraConfigs cameraConfigs = cameraDevice.getCameraConfigs();
            if (cameraConfigs != null) {
                MeteringRectangle[] aFRegions = cameraConfigs.getAFRegions();
                if (aFRegions != null && aFRegions.length > 0) {
                    MeteringRectangle meteringRectangle = aFRegions[0];
                    if (meteringRectangle == null) {
                        str = "0";
                    } else {
                        int x = meteringRectangle.getX();
                        int y = meteringRectangle.getY();
                        str = "[" + x + "," + y + "," + (meteringRectangle.getWidth() + x) + "," + (meteringRectangle.getHeight() + y) + "]";
                    }
                    sb.append("afRoi:" + str + " ");
                }
            }
        }
        if (!TextUtils.isEmpty(DebugInfoUtil.getFaceInfoString(this.mMainProtocol.getFaces()))) {
            sb.append("FaceRoi:" + r1 + " ");
        }
        sb.append("FilterId:" + CameraSettings.getShaderEffect() + " ");
        sb.append("AIScene:" + getCurrentAiScene() + " ");
        return sb.toString();
    }

    public int getFilterId() {
        return this.mFilterId;
    }

    /* access modifiers changed from: protected */
    public int getMaxPictureSize() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getMutexHdrMode(String str) {
        if ("normal".equals(str)) {
            return 1;
        }
        return (!b.ja() || !"live".equals(str)) ? 0 : 2;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0137, code lost:
        if (r0 != 175) goto L_0x013a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0158, code lost:
        if (com.android.camera.CameraSettings.isUltraPixelOn() != false) goto L_0x015d;
     */
    public int getOperatingMode() {
        int i;
        if (isParallelSessionEnable()) {
            boolean isInQCFAMode = isInQCFAMode();
            int i2 = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_NORMAL;
            if (isInQCFAMode) {
                Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_QCFA");
                i2 = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_QCFA;
            } else if (167 == getModuleIndex()) {
                if (CameraSettings.isUltraPixelOn()) {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_MANUAL_ULTRA_PIXEL");
                    i2 = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_MANUAL_ULTRA_PIXEL;
                } else {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_MANUAL");
                    i2 = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_MANUAL;
                }
            } else if (171 == getModuleIndex()) {
                if (!isFrontCamera() || isDualFrontCamera()) {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_DUAL_BOKEH");
                    i2 = 36864;
                } else {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_SINGLE_BOKEH");
                    i2 = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_SINGLE_BOKEH;
                }
            } else if (CameraSettings.isUltraPixelOn()) {
                Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_HD");
                i2 = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_HD;
            } else if (isUltraWideBackCamera()) {
                Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_NORMAL");
            } else if (CameraSettings.isSupportedOpticalZoom()) {
                Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_SAT");
                i2 = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_SAT;
            } else {
                Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_NORMAL");
            }
            this.mOperatingMode = i2;
            return i2;
        }
        int i3 = 33011;
        if (isFrontCamera()) {
            mIsBeautyFrontOn = true;
            if (!isPortraitMode() || !DataRepository.dataItemFeature().fK()) {
                if (!isPortraitMode() || !isBokehFrontCamera()) {
                    if (!this.mCameraCapabilities.isSupportedQcfa() || mIsBeautyFrontOn || !"off".equals(DataRepository.dataItemConfig().getComponentHdr().getComponentValue(this.mModuleIndex)) || DataRepository.dataItemFeature().gW() >= 0) {
                        i = 32773;
                        i3 = i;
                        if (this.mModuleIndex == 163 && CameraSettings.isUltraPixelOn()) {
                            i3 = 32775;
                        }
                    } else {
                        i3 = 32775;
                        i3 = 32775;
                    }
                }
            } else if (!isBokehFrontCamera()) {
                i = 33009;
                i3 = i;
                i3 = 32775;
            }
            i3 = 32770;
            i3 = 32775;
        } else {
            int moduleIndex = getModuleIndex();
            if (moduleIndex != 163) {
                if (moduleIndex == 167) {
                    i3 = CameraSettings.isUltraPixelOn() ? CameraCapabilities.SESSION_OPERATION_MODE_PROFESSIONAL_ULTRA_PIXEL_PHOTOGRAPHY : 32771;
                } else if (moduleIndex == 171) {
                    i3 = 32770;
                } else if (moduleIndex == 173) {
                    i3 = CameraCapabilities.SESSION_OPERATION_MODE_SUPER_NIGHT;
                }
            }
            i3 = 32769;
        }
        this.mOperatingMode = i3;
        Log.d(TAG, "getOperatingMode: " + String.format("operatingMode = 0x%x", new Object[]{Integer.valueOf(i3)}));
        return i3;
    }

    public void initializeCapabilities() {
        super.initializeCapabilities();
        this.mContinuousFocusSupported = Util.isSupported(4, this.mCameraCapabilities.getSupportedFocusModes());
        this.mMaxFaceCount = this.mCameraCapabilities.getMaxFaceCount();
    }

    /* access modifiers changed from: protected */
    public boolean isAutoRestartInNonZSL() {
        return false;
    }

    public boolean isBeautyBodySlimCountDetectStarted() {
        return this.mIsBeautyBodySlimOn;
    }

    public boolean isBlockSnap() {
        return this.mPaused || this.isZooming || isKeptBitmapTexture() || this.mMultiSnapStatus || getCameraState() == 0 || getCameraState() == 3 || (this.mCamera2Device != null && this.mCamera2Device.isCaptureBusy(this.mMutexModePicker.isHdr())) || isQueueFull() || isInCountDown();
    }

    /* access modifiers changed from: protected */
    public boolean isCameraSwitchingDuringZoomingAllowed() {
        return CameraSettings.isSupportedOpticalZoom() ? super.isCameraSwitchingDuringZoomingAllowed() : HybridZoomingSystem.IS_3_OR_MORE_SAT && !CameraSettings.isMacroModeEnabled(this.mModuleIndex) && isBackCamera();
    }

    /* access modifiers changed from: protected */
    public boolean isDetectedHHT() {
        return false;
    }

    public boolean isDoingAction() {
        if (this.mPaused || this.isZooming || isKeptBitmapTexture() || this.mMultiSnapStatus || getCameraState() == 0 || getCameraState() == 3) {
            return true;
        }
        return (this.mCamera2Device != null && this.mCamera2Device.isCaptureBusy(true)) || isQueueFull() || isInCountDown();
    }

    /* access modifiers changed from: protected */
    public boolean isFaceBeautyMode() {
        return false;
    }

    public boolean isFaceDetectStarted() {
        return this.mFaceDetectionStarted;
    }

    public boolean isGyroStable() {
        return Util.isGyroscopeStable(this.curGyroscope, this.lastGyroscope);
    }

    public boolean isHdrSceneDetectionStarted() {
        return this.mHdrCheckEnabled;
    }

    public boolean isKeptBitmapTexture() {
        return this.mKeepBitmapTexture;
    }

    public boolean isLivePhotoStarted() {
        return this.mLiveShotEnabled;
    }

    public boolean isMeteringAreaOnly() {
        if (this.mCamera2Device == null) {
            return false;
        }
        int focusMode = this.mCamera2Device.getFocusMode();
        return (!this.mFocusAreaSupported && this.mMeteringAreaSupported && !this.mFocusOrAELockSupported) || 5 == focusMode || focusMode == 0;
    }

    public boolean isNeedMute() {
        return CameraSettings.isLiveShotOn();
    }

    /* access modifiers changed from: protected */
    public boolean isParallelSessionEnable() {
        boolean z = false;
        boolean z2 = CameraSettings.isCameraParallelProcessEnable() && getModuleIndex() != 173 && (!isUltraWideBackCamera() || b.isMTKPlatform()) && (b.isMTKPlatform() || !this.mIsImageCaptureIntent || b.sQ);
        if (b.isMTKPlatform()) {
            return z2;
        }
        if (!isUltraWideBackCamera() && getModuleIndex() != 167 && !isStandaloneMacroCamera() && (!DataRepository.dataItemFeature().hu() || getModuleIndex() != 175)) {
            z = true;
        }
        return z2 & z;
    }

    /* access modifiers changed from: protected */
    public boolean isRepeatingRequestInProgress() {
        return this.mMultiSnapStatus && 3 == getCameraState();
    }

    public boolean isSelectingCapturedResult() {
        if (!this.mIsImageCaptureIntent) {
            return false;
        }
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        return baseDelegate != null && baseDelegate.getActiveFragment(R.id.bottom_action) == 4083;
    }

    public boolean isShot2GalleryOrEnableParallel() {
        return this.mEnableShot2Gallery || this.mEnableParallelSession;
    }

    public boolean isShowAeAfLockIndicator() {
        return this.m3ALocked;
    }

    public boolean isShowCaptureButton() {
        return !this.mMutexModePicker.isBurstShoot() && isSupportFocusShoot();
    }

    public boolean isSupportFocusShoot() {
        return DataRepository.dataItemGlobal().isGlobalSwitchOn("pref_camera_focus_shoot_key");
    }

    /* access modifiers changed from: protected */
    public boolean isSupportSceneMode() {
        return false;
    }

    public boolean isSupportSuperNight() {
        if (!DataRepository.dataItemFeature().il() || (b.sU && !Util.sSuperNightDefaultModeEnable)) {
            return false;
        }
        return (163 == getModuleIndex() || 165 == getModuleIndex()) && isBackCamera() && 1.0f == CameraSettings.readZoom();
    }

    public boolean isUltraWideDetectStarted() {
        return true;
    }

    public boolean isUnInterruptable() {
        this.mUnInterruptableReason = null;
        if (isKeptBitmapTexture()) {
            this.mUnInterruptableReason = "bitmap cover";
        } else if (getCameraState() == 3) {
            this.mUnInterruptableReason = "snapshot";
        }
        return this.mUnInterruptableReason != null;
    }

    public boolean isUseFaceInfo() {
        return this.mIsGenderAgeOn || this.mIsMagicMirrorOn;
    }

    /* access modifiers changed from: protected */
    public boolean isZoomEnabled() {
        return getCameraState() != 3 && !this.mMutexModePicker.isUbiFocus() && !CameraSettings.isStereoModeOn() && !CameraSettings.isPortraitModeBackOn() && !isFrontCamera() && this.mCamera2Device != null && !this.mCamera2Device.isCaptureBusy(true) && !CameraSettings.isUltraPixelOn();
    }

    /* access modifiers changed from: protected */
    public boolean isZslPreferred() {
        if (b.isMTKPlatform()) {
            if (this.mModuleIndex == 163 || this.mModuleIndex == 171) {
                return true;
            }
        } else if (this.mModuleIndex != 167) {
            return true;
        }
        return false;
    }

    public boolean multiCapture() {
        if (isDoingAction() || !this.mPendingMultiCapture) {
            return false;
        }
        this.mPendingMultiCapture = false;
        this.mActivity.getScreenHint().updateHint();
        if (Storage.isLowStorageAtLastPoint()) {
            String str = TAG;
            Log.i(str, "Not enough space or storage not ready. remaining=" + Storage.getLeftSpace());
            return false;
        } else if (this.mActivity.getImageSaver().isBusy()) {
            Log.d(TAG, "ImageSaver is busy, wait for a moment!");
            RotateTextToast.getInstance(this.mActivity).show(R.string.toast_saving, 0);
            return false;
        } else if (this.mIsMoonMode) {
            return false;
        } else {
            ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).closeMutexElement(SupportedConfigFactory.CLOSE_BY_BURST_SHOOT, 193, 194, 196, 239, 201, 206);
            ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
            if (backStack != null) {
                backStack.handleBackStackFromShutter();
            }
            prepareMultiCapture();
            Observable.create(new ObservableOnSubscribe<Integer>() {
                public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                    ObservableEmitter unused = Camera2Module.this.mBurstEmitter = observableEmitter;
                }
            }).observeOn(AndroidSchedulers.mainThread()).map(new Function<Integer, Integer>() {
                public Integer apply(Integer num) throws Exception {
                    ModeProtocol.SnapShotIndicator snapShotIndicator = (ModeProtocol.SnapShotIndicator) ModeCoordinatorImpl.getInstance().getAttachProtocol(184);
                    if (snapShotIndicator != null) {
                        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                        snapShotIndicator.setSnapNumVisible(!(miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()), false);
                        snapShotIndicator.setSnapNumValue(num.intValue());
                    }
                    return num;
                }
            }).subscribe(new Observer<Integer>() {
                public void onComplete() {
                    ((ModeProtocol.SnapShotIndicator) ModeCoordinatorImpl.getInstance().getAttachProtocol(184)).setSnapNumVisible(false, true);
                    ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_BURST_SHOOT);
                }

                public void onError(Throwable th) {
                }

                public void onNext(Integer num) {
                }

                public void onSubscribe(Disposable disposable) {
                    long unused = Camera2Module.this.mBurstStartTime = System.currentTimeMillis();
                    Disposable unused2 = Camera2Module.this.mBurstDisposable = disposable;
                }
            });
            this.mBurstNextDelayTime = 0;
            if (isParallelSessionEnable()) {
                this.mCamera2Device.setShotType(9);
                this.mCamera2Device.captureBurstPictures(this.mTotalJpegCallbackNum, new JpegRepeatingCaptureCallback(this), this.mActivity.getImageSaver());
            } else {
                this.mCamera2Device.setShotType(3);
                this.mCamera2Device.setAWBLock(true);
                this.mCamera2Device.captureBurstPictures(this.mTotalJpegCallbackNum, new JpegQuickPictureCallback(LocationManager.instance().getCurrentLocation()), this.mActivity.getImageSaver());
            }
            return true;
        }
    }

    public void notifyFocusAreaUpdate() {
        updatePreferenceTrampoline(3);
    }

    public boolean onBackPressed() {
        if (!isFrameAvailable()) {
            return false;
        }
        tryRemoveCountDownMessage();
        if (this.mMultiSnapStatus) {
            onShutterButtonLongClickCancel(false);
            return true;
        }
        if (getCameraState() == 3) {
            long currentTimeMillis = System.currentTimeMillis();
            if (this.mModuleIndex == 173) {
                if (currentTimeMillis - this.mLastBackPressedTime > 3000) {
                    this.mLastBackPressedTime = currentTimeMillis;
                    ToastUtils.showToast((Context) this.mActivity, (int) R.string.capture_back_pressed_hint);
                    return true;
                }
            } else if (currentTimeMillis - this.mLastCaptureTime < CAPTURE_DURATION_THRESHOLD) {
                return true;
            }
        }
        return super.onBackPressed();
    }

    public void onBeautyBodySlimCountChange(final boolean z) {
        this.mHandler.post(new Runnable() {
            public void run() {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    if (z) {
                        topAlert.alertAiDetectTipHint(0, R.string.beauty_body_slim_count_tip, FunctionParseBeautyBodySlimCount.TIP_TIME);
                    } else {
                        topAlert.alertAiDetectTipHint(8, R.string.beauty_body_slim_count_tip, 0);
                    }
                }
            }
        });
    }

    public void onBeautyParameterChanged() {
        if (b.km()) {
            updatePreferenceInWorkThread(13, 34, 42);
            return;
        }
        updatePreferenceInWorkThread(13);
    }

    public void onBroadcastReceived(Context context, Intent intent) {
        if (intent != null && isAlive()) {
            if (CameraIntentManager.ACTION_VOICE_CONTROL.equals(intent.getAction())) {
                Log.d(TAG, "on Receive voice control broadcast action intent");
                String voiceControlAction = CameraIntentManager.getInstance(intent).getVoiceControlAction();
                this.mBroadcastIntent = intent;
                char c = 65535;
                if (voiceControlAction.hashCode() == 1270567718 && voiceControlAction.equals("CAPTURE")) {
                    c = 0;
                }
                if (c == 0) {
                    onShutterButtonClick(getTriggerMode());
                    this.mBroadcastIntent = null;
                }
                CameraIntentManager.removeInstance(intent);
            }
            super.onBroadcastReceived(context, intent);
        }
    }

    /* access modifiers changed from: protected */
    public void onCameraOpened() {
        super.onCameraOpened();
        initializeFocusManager();
        updatePreferenceTrampoline(UpdateConstant.CAMERA_TYPES_INIT);
        if (this.mEnableParallelSession && isPortraitMode()) {
            Util.saveCameraCalibrationToFile(this.mCameraCapabilities.getCameraCalibrationData(), getCalibrationDataFileName(this.mActualCameraId));
        }
        if (!isKeptBitmapTexture()) {
            startPreview();
        }
        initMetaParser();
        if (DataRepository.dataItemFeature().fM()) {
            initAiSceneParser();
        }
        this.mOnResumeTime = SystemClock.uptimeMillis();
        this.mHandler.sendEmptyMessage(4);
        this.mHandler.sendEmptyMessage(31);
    }

    /* access modifiers changed from: protected */
    public void onCapabilityChanged(CameraCapabilities cameraCapabilities) {
        super.onCapabilityChanged(cameraCapabilities);
        this.mUltraWideAELocked = false;
        if (this.mFocusManager != null) {
            this.mFocusManager.setCharacteristics(cameraCapabilities);
        }
        if (this.mCamera2Device != null) {
            this.mCamera2Device.onCapabilityChanged(cameraCapabilities);
        }
    }

    public void onCaptureShutter(boolean z) {
        String str = TAG;
        Log.d(str, "onCaptureShutter: cameraState = " + getCameraState() + ", isParallel = " + this.mEnableParallelSession);
        onShutter(z);
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x00ba  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0106  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0129  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x012b  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0151  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0153  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0177  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x021c  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x0264  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x026d  */
    public ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, CameraSize cameraSize, boolean z) {
        ArrayList arrayList;
        ImageSaver imageSaver;
        if (isDeparted()) {
            Log.w(TAG, "onCaptureStart: departed");
            parallelTaskData.setAbandoned(true);
            return parallelTaskData;
        }
        int parallelType = parallelTaskData.getParallelType();
        boolean z2 = CameraSettings.isLiveShotOn() && isLiveShotAvailable(parallelType);
        if (z2) {
            startLiveShotAnimation();
        }
        if (!z || (CameraSettings.isGroupShotOn() && !this.mEnableParallelSession)) {
            if (!CameraSettings.isSupportedZslShutter()) {
                updateEnablePreviewThumbnail(z);
                if (this.mEnabledPreviewThumbnail) {
                    CameraSettings.setPlayToneOnCaptureStart(false);
                }
            }
            if (CameraSettings.isUltraPixelOn() && !this.mEnableParallelSession) {
                CameraSettings.setPlayToneOnCaptureStart(false);
            } else if (!this.mEnabledPreviewThumbnail) {
                onShutter(z);
                CameraSettings.setPlayToneOnCaptureStart(true);
            }
        }
        String str = null;
        if (CameraSettings.isAgeGenderAndMagicMirrorWaterOpen()) {
            List<WaterMarkData> faceWaterMarkInfos = ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).getFaceWaterMarkInfos();
            if (faceWaterMarkInfos != null && !faceWaterMarkInfos.isEmpty()) {
                arrayList = new ArrayList(faceWaterMarkInfos);
                Log.d(TAG, "onCaptureStart: inputSize = " + cameraSize);
                if ((isIn3OrMoreSatMode() || isInMultiSurfaceSatMode()) && !cameraSize.equals(this.mPictureSize)) {
                    this.mPictureSize = cameraSize;
                    updateOutputSize(cameraSize);
                }
                Size sizeObject = this.mOutPutSize != null ? cameraSize.toSizeObject() : this.mOutPutSize.toSizeObject();
                Log.d(TAG, "onCaptureStart: outputSize = " + sizeObject);
                CameraCharacteristics cameraCharacteristics = this.mCameraCapabilities.getCameraCharacteristics();
                this.mFocalLengths = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
                this.mApertures = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES);
                ParallelTaskDataParameter.Builder builder = new ParallelTaskDataParameter.Builder(this.mPreviewSize.toSizeObject(), cameraSize.toSizeObject(), sizeObject);
                if (parallelType == 1) {
                    builder.setRawSize(this.mSensorRawImageSize.width, this.mSensorRawImageSize.height);
                }
                ParallelTaskDataParameter.Builder location = builder.setHasDualWaterMark(CameraSettings.isDualCameraWaterMarkOpen()).setVendorWaterMark(this.mCamera2Device.getCameraConfigs().getWaterMarkAppliedList().size() <= 0).setMirror(isFrontMirror()).setLightingPattern(CameraSettings.getPortraitLightingPattern()).setFilterId(EffectController.getInstance().getEffectForSaving(false)).setOrientation(-1 != this.mOrientation ? 0 : this.mOrientation).setJpegRotation(this.mJpegRotation).setShootRotation(this.mShootRotation).setShootOrientation(this.mShootOrientation).setLocation(this.mLocation);
                if (CameraSettings.isTimeWaterMarkOpen()) {
                    str = Util.getTimeWatermark();
                }
                parallelTaskData.fillParameter(location.setTimeWaterMarkString(str).setFaceWaterMarkList(arrayList).setAgeGenderAndMagicMirrorWater(CameraSettings.isAgeGenderAndMagicMirrorWaterOpen()).setFrontCamera(isFrontCamera()).setBokehFrontCamera(isBokehFrontCamera()).setAlgorithmName(this.mAlgorithmName).setPictureInfo(getPictureInfo()).setSuffix(getSuffix()).setTiltShiftMode(getTiltShiftMode()).setSaveGroupshotPrimitive(CameraSettings.isSaveGroushotPrimitiveOn()).setDeviceWatermarkParam(getDeviceWaterMarkParam()).setJpegQuality(getJpegQuality(false)).setPrefix(getPrefix()).setMoonMode(this.mIsMoonMode).build());
                parallelTaskData.setNeedThumbnail(z && !this.mEnabledPreviewThumbnail);
                parallelTaskData.setCurrentModuleIndex(this.mModuleIndex);
                parallelTaskData.setAdaptiveSnapshotSize(this.mCameraCapabilities == null && this.mCameraCapabilities.isAdaptiveSnapshotSizeInSatModeSupported());
                parallelTaskData.setLiveShotTask(false);
                if (z2 && this.mActivity != null) {
                    imageSaver = this.mActivity.getImageSaver();
                    if (imageSaver != null) {
                        synchronized (this.mCircularMediaRecorderStateLock) {
                            if (this.mCircularMediaRecorder != null) {
                                parallelTaskData.setLiveShotTask(true);
                                this.mCircularMediaRecorder.snapshot(this.mOrientationCompensation, imageSaver, parallelTaskData, this.mFilterId);
                            }
                        }
                    }
                }
                Log.d(TAG, "onCaptureStart: isParallel = " + this.mEnableParallelSession + ", shotType = " + parallelTaskData.getParallelType() + ", isLiveShot = " + z2);
                if (this.mEnableParallelSession) {
                    beginParallelProcess(parallelTaskData, true);
                }
                if (CameraSettings.isHandGestureOpen()) {
                    Log.d(TAG, "send msg: reset hand gesture");
                    this.mHandler.removeMessages(57);
                    this.mHandler.sendEmptyMessageDelayed(57, 0);
                }
                return parallelTaskData;
            }
        }
        arrayList = null;
        Log.d(TAG, "onCaptureStart: inputSize = " + cameraSize);
        this.mPictureSize = cameraSize;
        updateOutputSize(cameraSize);
        if (this.mOutPutSize != null) {
        }
        Log.d(TAG, "onCaptureStart: outputSize = " + sizeObject);
        CameraCharacteristics cameraCharacteristics2 = this.mCameraCapabilities.getCameraCharacteristics();
        this.mFocalLengths = (float[]) cameraCharacteristics2.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
        this.mApertures = (float[]) cameraCharacteristics2.get(CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES);
        ParallelTaskDataParameter.Builder builder2 = new ParallelTaskDataParameter.Builder(this.mPreviewSize.toSizeObject(), cameraSize.toSizeObject(), sizeObject);
        if (parallelType == 1) {
        }
        ParallelTaskDataParameter.Builder location2 = builder2.setHasDualWaterMark(CameraSettings.isDualCameraWaterMarkOpen()).setVendorWaterMark(this.mCamera2Device.getCameraConfigs().getWaterMarkAppliedList().size() <= 0).setMirror(isFrontMirror()).setLightingPattern(CameraSettings.getPortraitLightingPattern()).setFilterId(EffectController.getInstance().getEffectForSaving(false)).setOrientation(-1 != this.mOrientation ? 0 : this.mOrientation).setJpegRotation(this.mJpegRotation).setShootRotation(this.mShootRotation).setShootOrientation(this.mShootOrientation).setLocation(this.mLocation);
        if (CameraSettings.isTimeWaterMarkOpen()) {
        }
        parallelTaskData.fillParameter(location2.setTimeWaterMarkString(str).setFaceWaterMarkList(arrayList).setAgeGenderAndMagicMirrorWater(CameraSettings.isAgeGenderAndMagicMirrorWaterOpen()).setFrontCamera(isFrontCamera()).setBokehFrontCamera(isBokehFrontCamera()).setAlgorithmName(this.mAlgorithmName).setPictureInfo(getPictureInfo()).setSuffix(getSuffix()).setTiltShiftMode(getTiltShiftMode()).setSaveGroupshotPrimitive(CameraSettings.isSaveGroushotPrimitiveOn()).setDeviceWatermarkParam(getDeviceWaterMarkParam()).setJpegQuality(getJpegQuality(false)).setPrefix(getPrefix()).setMoonMode(this.mIsMoonMode).build());
        parallelTaskData.setNeedThumbnail(z && !this.mEnabledPreviewThumbnail);
        parallelTaskData.setCurrentModuleIndex(this.mModuleIndex);
        parallelTaskData.setAdaptiveSnapshotSize(this.mCameraCapabilities == null && this.mCameraCapabilities.isAdaptiveSnapshotSizeInSatModeSupported());
        parallelTaskData.setLiveShotTask(false);
        imageSaver = this.mActivity.getImageSaver();
        if (imageSaver != null) {
        }
        Log.d(TAG, "onCaptureStart: isParallel = " + this.mEnableParallelSession + ", shotType = " + parallelTaskData.getParallelType() + ", isLiveShot = " + z2);
        if (this.mEnableParallelSession) {
        }
        if (CameraSettings.isHandGestureOpen()) {
        }
        return parallelTaskData;
    }

    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        parseIntent();
        this.mHandler = new MainHandler(this, this.mActivity.getMainLooper());
        this.mActivity.getSensorStateManager().setSensorStateListener(this.mSensorStateListener);
        this.mIsGoogleLensAvailable = 163 == getModuleIndex() && !this.mIsImageCaptureIntent && isBackCamera() && CameraSettings.isAvailableGoogleLens();
        onCameraOpened();
        if (Util.isGlobalVersion() && !b.gQ()) {
            this.mLensApi = new LensApi(this.mActivity);
            this.mLensApi.checkLensAvailability(new LensApi.LensAvailabilityCallback() {
                public void onAvailabilityStatusFetched(int i) {
                    int unused = Camera2Module.this.mLensStatus = i;
                    CameraSettings.setGoogleLensAvailability(Camera2Module.this.mLensStatus == 0);
                }
            });
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mHandler != null) {
            this.mHandler.sendEmptyMessage(45);
        }
    }

    public void onFaceDetected(CameraHardwareFace[] cameraHardwareFaceArr, FaceAnalyzeInfo faceAnalyzeInfo) {
        if (isAlive() && this.mActivity.getCameraScreenNail().getFrameAvailableFlag() && cameraHardwareFaceArr != null) {
            if (b.kD()) {
                boolean z = cameraHardwareFaceArr.length > 0;
                if (z != this.mFaceDetected && isFrontCamera() && (this.mModuleIndex == 163 || this.mModuleIndex == 165 || this.mModuleIndex == 171)) {
                    this.mCamera2Device.resumePreview();
                }
                this.mFaceDetected = z;
            }
            this.mFaceInfo = faceAnalyzeInfo;
            if (!b.jc() || cameraHardwareFaceArr.length <= 0 || cameraHardwareFaceArr[0].faceType != 64206) {
                if (this.mIsGoogleLensAvailable && this.mActivity != null) {
                    this.mActivity.runOnUiThread(new Runnable(cameraHardwareFaceArr) {
                        private final /* synthetic */ CameraHardwareFace[] f$1;

                        {
                            this.f$1 = r2;
                        }

                        public final void run() {
                            Camera2Module.lambda$onFaceDetected$4(Camera2Module.this, this.f$1);
                        }
                    });
                }
                if (this.mMainProtocol.setFaces(1, cameraHardwareFaceArr, getActiveArraySize(), getDeviceBasedZoomRatio())) {
                    if (this.mIsPortraitLightingOn) {
                        this.mMainProtocol.lightingDetectFace(cameraHardwareFaceArr, false);
                    }
                    if (!this.mMainProtocol.isFaceExists(1) || !this.mMainProtocol.isFocusViewVisible() || this.mFocusManager == null || this.mFocusManager.isFromTouch()) {
                        this.mHandler.removeMessages(56);
                    } else if (!this.mHandler.hasMessages(56)) {
                        this.mHandler.sendEmptyMessage(56);
                    }
                }
            } else if (this.mObjectTrackingStarted) {
                this.mMainProtocol.setFaces(3, cameraHardwareFaceArr, getActiveArraySize(), getDeviceBasedZoomRatio());
            }
        }
    }

    public void onFilterChanged(int i, int i2) {
        String str = TAG;
        Log.d(str, "onFilterChanged: category = " + i + ", newIndex = " + i2);
        updatePreferenceTrampoline(2);
        this.mMainProtocol.updateEffectViewVisible();
    }

    public void onFocusStateChanged(FocusTask focusTask) {
        if (isFrameAvailable() && !isDeparted()) {
            switch (focusTask.getFocusTrigger()) {
                case 1:
                    Log.v(TAG, String.format(Locale.ENGLISH, "FocusTime=%1$dms focused=%2$b", new Object[]{Long.valueOf(focusTask.getElapsedTime()), Boolean.valueOf(focusTask.isSuccess())}));
                    if (!this.mFocusManager.isFocusingSnapOnFinish() && getCameraState() != 3) {
                        setCameraState(1);
                    }
                    this.mFocusManager.onFocusResult(focusTask);
                    this.mActivity.getSensorStateManager().reset();
                    if (focusTask.isSuccess() && this.m3ALocked) {
                        if (!DataRepository.dataItemFeature().hG() && isZoomRatioBetweenUltraAndWide() && this.mUltraCameraCapabilities != null) {
                            boolean isAFRegionSupported = this.mUltraCameraCapabilities.isAFRegionSupported();
                            Log.d(TAG, "onFocusStateChanged: isUltraFocusAreaSupported = " + isAFRegionSupported);
                            if (!isAFRegionSupported) {
                                this.mCamera2Device.setFocusMode(0);
                                this.mCamera2Device.setFocusDistance(0.0f);
                                this.mUltraWideAELocked = true;
                            }
                        }
                        this.mCamera2Device.lockExposure(true);
                        return;
                    }
                    return;
                case 2:
                case 3:
                    String str = null;
                    if (focusTask.isFocusing()) {
                        str = "onAutoFocusMoving start";
                        this.mAFEndLogTimes = 0;
                    } else if (this.mAFEndLogTimes == 0) {
                        str = "onAutoFocusMoving end. result=" + focusTask.isSuccess();
                        this.mAFEndLogTimes++;
                    }
                    if (Util.sIsDumpLog && str != null) {
                        Log.v(TAG, str);
                    }
                    if (getCameraState() != 3 || focusTask.getFocusTrigger() == 3) {
                        if (!this.m3ALocked) {
                            this.mFocusManager.onFocusResult(focusTask);
                            return;
                        }
                        return;
                    } else if (focusTask.isSuccess()) {
                        this.mFocusManager.onFocusResult(focusTask);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public void onGradienterSwitched(boolean z) {
        this.mIsGradienterOn = z;
        this.mActivity.getSensorStateManager().setGradienterEnabled(z);
        updatePreferenceTrampoline(2, 5);
    }

    public void onHanGestureSwitched(boolean z) {
        if (z) {
            PreviewDecodeManager.getInstance().init(this.mBogusCameraId, 1);
            PreviewDecodeManager.getInstance().startDecode();
            return;
        }
        PreviewDecodeManager.getInstance().stopDecode(1);
    }

    public void onHdrMotionDetectionResult(boolean z) {
        if (this.mCameraCapabilities != null && this.mCameraCapabilities.isMotionDetectionSupported() && this.mMotionDetected != z) {
            this.mMotionDetected = z;
            updateHDRPreference();
        }
    }

    public void onHdrSceneChanged(boolean z) {
        if (this.isDetectedInHdr != z && !this.mPaused) {
            ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
            if (!componentHdr.isEmpty() && "auto".equals(componentHdr.getComponentValue(this.mModuleIndex))) {
                if (z) {
                    if (getZoomRatio() <= 1.0f && this.mCurrentAiScene != -1) {
                        if (this.mCamera2Device != null && this.mCamera2Device.isNeedFlashOn()) {
                            return;
                        }
                    } else {
                        return;
                    }
                }
                this.mHandler.post(new Runnable(z) {
                    private final /* synthetic */ boolean f$0;

                    {
                        this.f$0 = r1;
                    }

                    public final void run() {
                        Camera2Module.lambda$onHdrSceneChanged$3(this.f$0);
                    }
                });
                if (z) {
                    if (this.mMutexModePicker.isNormal()) {
                        this.mMutexModePicker.setMutexMode(1);
                    }
                } else if (this.mMutexModePicker.isMorphoHdr()) {
                    this.mMutexModePicker.resetMutexMode();
                }
                this.isDetectedInHdr = z;
                String str = TAG;
                Log.d(str, "onHdrSceneChanged: " + z);
            }
        }
    }

    public void onHostStopAndNotifyActionStop() {
        super.onHostStopAndNotifyActionStop();
        if (this.mMultiSnapStatus) {
            onBurstPictureTakenFinished(true);
        }
        if (this.mFocusManager != null) {
            this.mFocusManager.setAeAwbLock(false);
            this.mFocusManager.destroy();
        }
        checkMoreFrameCaptureLockAFAE(false);
        if (handleSuperNightResultIfNeed()) {
            doLaterReleaseIfNeed();
        }
    }

    /* access modifiers changed from: protected */
    public boolean onInterceptZoomingEvent(float f, float f2, int i) {
        if (f2 < 1.0f) {
            onUltraWideChanged(false, -1);
        }
        if (DataRepository.dataItemFeature().hG() && !this.mIsMoonMode) {
            if (this.m3ALocked) {
                unlockAEAF();
                if (this.mFocusManager != null) {
                    this.mFocusManager.cancelFocus();
                }
                ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                if (bottomPopupTips != null) {
                    bottomPopupTips.directlyHideTips();
                }
            } else if (!(this.mCamera2Device == null || 4 == this.mCamera2Device.getFocusMode())) {
                Log.d(TAG, "onInterceptZoomingEvent: should cancel focus.");
                if (this.mFocusManager != null) {
                    this.mFocusManager.cancelFocus();
                }
            }
        }
        return super.onInterceptZoomingEvent(f, f2, i);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean z = false;
        if (!isFrameAvailable()) {
            return false;
        }
        switch (i) {
            case 24:
            case 25:
            case 87:
            case 88:
                if (i == 24 || i == 88) {
                    z = true;
                }
                if (handleVolumeKeyEvent(z, true, keyEvent.getRepeatCount(), keyEvent.getDevice().isExternal())) {
                    return true;
                }
                break;
            case 27:
            case 66:
                if (keyEvent.getRepeatCount() == 0) {
                    if (!Util.isFingerPrintKeyEvent(keyEvent)) {
                        performKeyClicked(40, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                    } else if (CameraSettings.isFingerprintCaptureEnable()) {
                        performKeyClicked(30, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                    }
                }
                return true;
            case 80:
                if (keyEvent.getRepeatCount() == 0) {
                    onShutterButtonFocus(true, 1);
                }
                return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 4 && ((ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)).handleBackStackFromKeyBack()) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    public void onLivePhotoResultCallback(LivePhotoResult livePhotoResult) {
        this.mLivePhotoQueue.offer(livePhotoResult);
    }

    public void onLongPress(float f, float f2) {
        int i = (int) f;
        int i2 = (int) f2;
        if (isInTapableRect(i, i2)) {
            if (!this.mIsCurrentLensEnabled || !this.mIsGoogleLensAvailable || !CameraSettings.isAvailableLongPressGoogleLens()) {
                onSingleTapUp(i, i2, true);
                if (this.m3ALockSupported && this.mCamera2Device.getFocusMode() != AutoFocus.convertToFocusMode("manual")) {
                    lockAEAF();
                }
                this.mMainProtocol.performHapticFeedback(0);
            } else if (DataRepository.dataItemGlobal().getString(CameraSettings.KEY_LONG_PRESS_VIEWFINDER, (String) null) == null) {
                CameraStatUtil.trackGoogleLensPicker();
                FragmentManager fragmentManager = this.mActivity.getFragmentManager();
                $$Lambda$Camera2Module$IyAHJeItOvbXf2FkenUGJSeZEsw r0 = new GoogleLensFragment.OnClickListener(f, f2, i, i2) {
                    private final /* synthetic */ float f$1;
                    private final /* synthetic */ float f$2;
                    private final /* synthetic */ int f$3;
                    private final /* synthetic */ int f$4;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r4;
                        this.f$4 = r5;
                    }

                    public final void onOptionClick(int i) {
                        Camera2Module.lambda$onLongPress$8(Camera2Module.this, this.f$1, this.f$2, this.f$3, this.f$4, i);
                    }
                };
                GoogleLensFragment.showOptions(fragmentManager, r0);
            } else {
                CameraStatUtil.trackGoogleLensTouchAndHold();
                LensAgent.getInstance().onFocusChange(0, f / ((float) Util.sWindowWidth), f2 / ((float) Util.sWindowHeight));
            }
        }
    }

    public void onObjectStable() {
    }

    public void onOrientationChanged(int i, int i2, int i3) {
        if (!this.mIsGradienterOn || this.mActivity.getSensorStateManager().isDeviceLying()) {
            setOrientation(i, i2);
        }
    }

    public void onPause() {
        super.onPause();
        stopLiveShot(true);
        if (this.mFocusManager != null) {
            this.mFocusManager.removeMessages();
        }
        this.mWaitingSnapshot = false;
        unregisterSensor();
        tryRemoveCountDownMessage();
        this.mActivity.getSensorStateManager().reset();
        resetScreenOn();
        closeCamera();
        setAiSceneEffect(0);
        releaseEffectProcessor();
        if (Util.isGlobalVersion() && !b.gQ()) {
            this.mActivity.runOnUiThread(new Runnable() {
                public final void run() {
                    Camera2Module.lambda$onPause$11(Camera2Module.this);
                }
            });
        }
        if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages((Object) null);
        }
    }

    public void onPictureTaken(byte[] bArr) {
    }

    public void onPictureTakenFinished(boolean z) {
        Log.d(TAG, "onPictureTakenFinished: succeed = " + z);
        if (z) {
            trackGeneralInfo(1, false);
            CameraStat.PictureTakenParameter pictureTakenParameter = new CameraStat.PictureTakenParameter();
            pictureTakenParameter.takenNum = 1;
            pictureTakenParameter.burst = false;
            pictureTakenParameter.location = this.mLocation != null;
            pictureTakenParameter.aiSceneName = getCurrentAiSceneName();
            pictureTakenParameter.isEnteringMoon = this.mEnteringMoonMode;
            pictureTakenParameter.isSelectMoonMode = this.mIsMoonMode;
            pictureTakenParameter.isSuperNightInCaptureMode = this.mShowSuperNightHint;
            trackPictureTaken(pictureTakenParameter);
            CameraStatUtil.trackTakePictureCost(System.currentTimeMillis() - this.mCaptureStartTime, isFrontCamera(), this.mModuleIndex);
            if (this.mModuleIndex == 171 && DataRepository.dataItemFeature().isSupportBokehAdjust()) {
                CameraStatUtil.trackBokehTaken();
            }
            ScenarioTrackUtil.trackCaptureTimeEnd();
            Log.d(TAG, "mCaptureStartTime(from onShutterButtonClick start to jpegCallback finished) = " + r2 + "ms");
            if (this.mIsImageCaptureIntent) {
                if (this.mQuickCapture) {
                    doAttach();
                } else if (isAlive()) {
                    this.mKeepBitmapTexture = true;
                    showPostCaptureAlert();
                }
            } else if (this.mLongPressedAutoFocus) {
                this.mLongPressedAutoFocus = false;
                this.mFocusManager.cancelLongPressedAutoFocus();
            }
        }
        this.mReceivedJpegCallbackNum++;
        if (!isKeptBitmapTexture() && !this.mMultiSnapStatus && this.mBlockQuickShot && (!CameraSettings.isGroupShotOn() || (CameraSettings.isGroupShotOn() && z))) {
            setCameraState(1);
            enableCameraControls(true);
            this.mBlockQuickShot = false;
        }
        this.mHandler.removeMessages(50);
        handleSuperNightResultIfNeed();
        checkMoreFrameCaptureLockAFAE(false);
        handleSuperNightResultInCaptureMode();
        PreviewDecodeManager.getInstance().resetScanResult();
        doLaterReleaseIfNeed();
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
            boolean z = false;
            if (DataRepository.dataItemFeature().he() || !isFrontCamera()) {
                this.mShouldDoMFNR = false;
            } else if (b.kF()) {
                this.mShouldDoMFNR = true;
            } else {
                Integer num = (Integer) captureResult.get(CaptureResult.SENSOR_SENSITIVITY);
                Log.c(TAG, "onPreviewMetaDataUpdate: iso = " + num);
                if (num != null && num.intValue() >= 800) {
                    z = true;
                }
                this.mShouldDoMFNR = z;
            }
            if (shouldCheckSatFallbackState()) {
                checkSatFallback(captureResult);
            }
            if (this.mMetaDataFlowableEmitter != null) {
                this.mMetaDataFlowableEmitter.onNext(captureResult);
            }
            if (this.mAiSceneFlowableEmitter != null && this.mAiSceneEnabled && this.mCamera2Device != null) {
                this.mAiSceneFlowableEmitter.onNext(captureResult);
            }
        }
    }

    public void onPreviewPixelsRead(byte[] bArr, int i, int i2) {
        animateCapture();
        playCameraSound(0);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        createBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bArr));
        boolean z = true;
        boolean z2 = isFrontCamera() && !isFrontMirror();
        synchronized (this.mCameraDeviceLock) {
            if (isAlive()) {
                if (isDeviceAlive()) {
                    if ((this.mEnableParallelSession || this.mEnableShot2Gallery) && !this.mIsImageCaptureIntent) {
                        Bitmap cropBitmap = Util.cropBitmap(createBitmap, this.mShootRotation, z2, (float) this.mOrientation, this.mModuleIndex == 165);
                        if (cropBitmap == null) {
                            Log.w(TAG, "onPreviewPixelsRead: bitmap is null!");
                            return;
                        }
                        byte[] bitmapData = Util.getBitmapData(cropBitmap, 87);
                        if (bitmapData == null) {
                            Log.w(TAG, "onPreviewPixelsRead: jpegData is null!");
                            return;
                        }
                        Log.d(TAG, "onPreviewPixelsRead: isParallel = " + this.mEnableParallelSession + ", mEnableShot2Gallery = " + this.mEnableShot2Gallery + ", data = " + bitmapData);
                        ParallelTaskData parallelTaskData = new ParallelTaskData(this.mActualCameraId, System.currentTimeMillis(), -1, this.mCamera2Device.getParallelShotSavePath());
                        if (!this.mEnableParallelSession) {
                            if (!this.mEnableShot2Gallery) {
                                z = false;
                            }
                        }
                        parallelTaskData.setNeedThumbnail(z);
                        parallelTaskData.fillJpegData(bitmapData, 0);
                        parallelTaskData.fillParameter(new ParallelTaskDataParameter.Builder(new Size(i, i2), new Size(i, i2), new Size(i, i2)).setOrientation(this.mOrientation).build());
                        this.mActivity.getImageSaver().onParallelProcessFinish(parallelTaskData, (CaptureResult) null, (CameraCharacteristics) null);
                        return;
                    }
                    int i3 = this.mShootOrientation - this.mDisplayRotation;
                    if (isFrontCamera() && b.kc() && i3 % 180 == 0) {
                        i3 = 0;
                    }
                    Thumbnail createThumbnail = Thumbnail.createThumbnail((Uri) null, createBitmap, i3, z2);
                    createThumbnail.startWaitingForUri();
                    this.mActivity.getThumbnailUpdater().setThumbnail(createThumbnail, true, true);
                    this.mCamera2Device.onPreviewThumbnailReceived(createThumbnail);
                    return;
                }
            }
            Log.d(TAG, "onPreviewPixelsRead: module is dead");
        }
    }

    public void onPreviewSessionClosed(CameraCaptureSession cameraCaptureSession) {
        Log.d(TAG, "onPreviewSessionClosed: ");
        setCameraState(0);
    }

    public void onPreviewSessionFailed(CameraCaptureSession cameraCaptureSession) {
        if (!isTextureExpired() || !retryOnceIfCameraError(this.mHandler)) {
            this.mHandler.sendEmptyMessage(51);
        } else {
            Log.d(TAG, "sessionFailed due to surfaceTexture expired, retry");
        }
    }

    public void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession) {
        String str = TAG;
        Log.d(str, "onPreviewSessionSuccess: " + Thread.currentThread().getName() + " " + this);
        if (cameraCaptureSession == null) {
            String str2 = TAG;
            Log.d(str2, "onPreviewSessionSuccess null session." + Util.getCallers(1));
        } else if (!isAlive()) {
            String str3 = TAG;
            Log.d(str3, "onPreviewSessionSuccess module not alive." + Util.getCallers(1));
        } else {
            if (!isKeptBitmapTexture()) {
                this.mHandler.sendEmptyMessage(9);
            }
            if (this.mEnableParallelSession) {
                configParallelSession();
            }
            previewWhenSessionSuccess();
            if (this.mActivity.getCameraIntentManager().checkCallerLegality() && !this.mActivity.isActivityPaused()) {
                if (!this.mActivity.getCameraIntentManager().isOpenOnly(this.mActivity)) {
                    this.mActivity.getIntent().removeExtra(CameraIntentManager.CameraExtras.CAMERA_OPEN_ONLY);
                    this.mHandler.sendEmptyMessageDelayed(52, 1000);
                    return;
                }
                this.mActivity.getIntent().removeExtra(CameraIntentManager.CameraExtras.TIMER_DURATION_SECONDS);
            }
        }
    }

    public void onPreviewSizeChanged(int i, int i2) {
        if (this.mFocusManager != null) {
            this.mFocusManager.setPreviewSize(i, i2);
        }
    }

    public void onResume() {
        super.onResume();
        this.mHandler.removeMessages(50);
        if (!isSelectingCapturedResult()) {
            this.mKeepBitmapTexture = false;
            this.mActivity.getCameraScreenNail().releaseBitmapIfNeeded();
        }
        keepScreenOnAwhile();
        if (Util.isGlobalVersion() && !b.gQ()) {
            this.mActivity.runOnUiThread(new Runnable() {
                public final void run() {
                    Camera2Module.lambda$onResume$9(Camera2Module.this);
                }
            });
        }
    }

    public void onReviewCancelClicked() {
        this.mKeepBitmapTexture = false;
        if (isSelectingCapturedResult()) {
            this.mActivity.getCameraScreenNail().releaseBitmapIfNeeded();
            hidePostCaptureAlert();
            return;
        }
        this.mActivity.setResult(0, new Intent());
        this.mActivity.finish();
    }

    public void onReviewDoneClicked() {
        doAttach();
    }

    public void onShutterButtonClick(int i) {
        if (i == 100) {
            this.mActivity.onUserInteraction();
        }
        int countDownTimes = getCountDownTimes(i);
        if (countDownTimes > 0) {
            startCount(countDownTimes, i);
            return;
        }
        CameraStat.PictureTakenParameter pictureTakenParameter = new CameraStat.PictureTakenParameter();
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            pictureTakenParameter.isASDBacklitTip = topAlert.isShowBacklightSelector();
        }
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            pictureTakenParameter.isASDPortraitTip = bottomPopupTips.containTips(R.string.recommend_portrait);
        }
        trackTouchShutterButtom(pictureTakenParameter);
        if (checkShutterCondition()) {
            setTriggerMode(i);
            String str = TAG;
            Log.d(str, "onShutterButtonClick " + String.valueOf(getCameraState()));
            this.mFocusManager.prepareCapture(this.mNeedAutoFocus, 2);
            this.mFocusManager.doSnap();
            if (this.mFocusManager.isFocusingSnapOnFinish()) {
                enableCameraControls(false);
            }
        }
    }

    public void onShutterButtonFocus(boolean z, int i) {
    }

    public boolean onShutterButtonLongClick() {
        if (isDoingAction()) {
            Log.d(TAG, "onShutterButtonLongClick: doing action");
            return false;
        } else if (this.mIsImageCaptureIntent) {
            return false;
        } else {
            if (this.mIsSatFallback && shouldCheckSatFallbackState()) {
                Log.d(TAG, "onShutterButtonLongClick: sat fallback");
                return false;
            } else if (!CameraSettings.isBurstShootingEnable() || !ModuleManager.isCameraModule() || this.mIsImageCaptureIntent || CameraSettings.isGroupShotOn() || CameraSettings.isGradienterOn() || CameraSettings.isTiltShiftOn() || DataRepository.dataItemRunning().isSwitchOn("pref_camera_hand_night_key") || DataRepository.dataItemRunning().isSwitchOn("pref_camera_scenemode_setting_key") || CameraSettings.isStereoModeOn() || CameraSettings.isPortraitModeBackOn() || !isBackCamera() || this.mMultiSnapStatus || this.mHandler.hasMessages(24) || this.mPendingMultiCapture || isUltraWideBackCamera() || CameraSettings.isUltraPixelOn() || isStandaloneMacroCamera()) {
                this.mLongPressedAutoFocus = true;
                this.mMainProtocol.setFocusViewType(false);
                unlockAEAF();
                this.mFocusManager.requestAutoFocus();
                this.mActivity.getScreenHint().updateHint();
                return false;
            } else {
                if (b.jo()) {
                    this.mUpdateImageTitle = true;
                }
                this.mPendingMultiCapture = true;
                this.mFocusManager.doMultiSnap(true);
                return true;
            }
        }
    }

    public void onShutterButtonLongClickCancel(boolean z) {
        Log.d(TAG, "onShutterButtonLongClickCancel: start");
        this.mPendingMultiCapture = false;
        if (this.mMultiSnapStatus) {
            this.mHandler.sendEmptyMessageDelayed(49, FragmentTopAlert.HINT_DELAY_TIME);
        }
        this.mMultiSnapStopRequest = true;
        if (!this.mLongPressedAutoFocus) {
            return;
        }
        if (z) {
            onShutterButtonClick(10);
            return;
        }
        this.mLongPressedAutoFocus = false;
        this.mFocusManager.cancelLongPressedAutoFocus();
    }

    public void onSingleTapUp(int i, int i2, boolean z) {
        Log.v(TAG, "onSingleTapUp mPaused: " + this.mPaused + "; mCamera2Device: " + this.mCamera2Device + "; isInCountDown: " + isInCountDown() + "; getCameraState: " + getCameraState() + "; mMultiSnapStatus: " + this.mMultiSnapStatus + "; Camera2Module: " + this);
        if (!this.mPaused && this.mCamera2Device != null && !hasCameraException() && this.mCamera2Device.isSessionReady() && this.mCamera2Device.isPreviewReady() && isInTapableRect(i, i2) && getCameraState() != 3 && getCameraState() != 4 && getCameraState() != 0 && !isInCountDown() && !this.mMultiSnapStatus) {
            if (this.mIsMoonMode) {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                boolean z2 = false;
                boolean z3 = miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow();
                if (topAlert != null && topAlert.isExtraMenuShowing()) {
                    z2 = true;
                }
                if (!z3 && !z2) {
                    return;
                }
            }
            if (isFrameAvailable()) {
                if (!isFrontCamera() || !this.mActivity.isScreenSlideOff()) {
                    ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
                    if (backStack != null && !backStack.handleBackStackFromTapDown(i, i2)) {
                        tryRemoveCountDownMessage();
                        if ((this.mFocusAreaSupported || this.mMeteringAreaSupported) && !this.mMutexModePicker.isUbiFocus()) {
                            if (this.mObjectTrackingStarted) {
                                stopObjectTracking(true);
                            }
                            this.mMainProtocol.setFocusViewType(true);
                            Point point = new Point(i, i2);
                            mapTapCoordinate(point);
                            unlockAEAF();
                            setCameraState(2);
                            this.mFocusManager.onSingleTapUp(point.x, point.y, z);
                            if (!this.mFocusAreaSupported && this.mMeteringAreaSupported) {
                                this.mActivity.getSensorStateManager().reset();
                            }
                            CameraClickObservableImpl cameraClickObservableImpl = (CameraClickObservableImpl) ModeCoordinatorImpl.getInstance().getAttachProtocol(227);
                            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                            if (!z && cameraClickObservableImpl != null) {
                                cameraClickObservableImpl.subscribe(165);
                            }
                        }
                    }
                }
            }
        }
    }

    public void onStop() {
        super.onStop();
        if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages((Object) null);
        }
    }

    public void onSuperNightChanged(boolean z) {
        if (z != CameraSettings.isSuperNightOn()) {
            CameraSettings.setSuperNightOn(z);
            if (z) {
                this.mCamera2Device.setSuperResolution(false);
            } else if (this.mMutexModePicker.isSuperResolution()) {
                this.mCamera2Device.setSuperResolution(true);
            }
            this.mMainProtocol.setEvAdjustable(!z);
            updateHDRPreference();
            updateSuperNight();
            resumePreviewInWorkThread();
        }
    }

    public void onSurfaceTextureReleased() {
        Log.d(TAG, "onSurfaceTextureReleased: no further preview frame will be available");
    }

    public void onSurfaceTextureUpdated(DrawExtTexAttribute drawExtTexAttribute) {
        if (this.mCircularMediaRecorder != null) {
            this.mCircularMediaRecorder.onSurfaceTextureUpdated(drawExtTexAttribute);
        }
        if (this.mCamera2Device != null) {
            this.mCamera2Device.onPreviewComing();
        }
    }

    public void onThumbnailClicked(View view) {
        if (this.mEnableParallelSession || this.mEnableShot2Gallery) {
            if (isCannotGotoGallery()) {
                Log.d(TAG, "onThumbnailClicked: CannotGotoGallery...");
                return;
            }
        } else if (isDoingAction()) {
            Log.d(TAG, "onThumbnailClicked: DoingAction..");
            return;
        }
        if (this.mActivity.getThumbnailUpdater().getThumbnail() != null) {
            this.mActivity.gotoGallery();
        }
    }

    public void onTiltShiftSwitched(boolean z) {
        if (z) {
            resetEvValue();
        }
        this.mMainProtocol.initEffectCropView();
        updatePreferenceTrampoline(2, 5);
        this.mMainProtocol.updateEffectViewVisible();
        this.mMainProtocol.setEvAdjustable(!z);
    }

    public void onUltraWideChanged(boolean z, int i) {
        this.mHandler.post(new Runnable(z, i) {
            private final /* synthetic */ boolean f$1;
            private final /* synthetic */ int f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                Camera2Module.lambda$onUltraWideChanged$15(Camera2Module.this, this.f$1, this.f$2);
            }
        });
    }

    public void onUserInteraction() {
        super.onUserInteraction();
        if (!isDoingAction()) {
            keepScreenOnAwhile();
        }
    }

    public boolean onWaitingFocusFinished() {
        if (isBlockSnap() || !isAlive()) {
            return false;
        }
        startNormalCapture(getTriggerMode());
        return true;
    }

    public void onZoomingActionEnd(int i) {
        String str = TAG;
        Log.d(str, "onZoomingActionEnd(): " + ZoomingAction.toString(i));
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && !dualController.isSlideVisible()) {
            if (i == 1 || i == 2) {
                dualController.setImmersiveModeEnabled(false);
            }
        }
    }

    public void onZoomingActionStart(int i) {
        String str = TAG;
        Log.d(str, "onZoomingActionStart(): " + ZoomingAction.toString(i));
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && topAlert.isExtraMenuShowing()) {
            topAlert.hideExtraMenu();
        }
        if (!isZoomEnabled() && CameraSettings.isUltraPixelOn()) {
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.showTips(15, ComponentRunningUltraPixel.getNoSupportZoomTip(), 1);
            }
        }
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && (i == 1 || i == 2)) {
            dualController.setImmersiveModeEnabled(true);
        }
        ModeProtocol.CameraClickObservable cameraClickObservable = (ModeProtocol.CameraClickObservable) ModeCoordinatorImpl.getInstance().getAttachProtocol(227);
        if (cameraClickObservable != null) {
            cameraClickObservable.subscribe(168);
        }
    }

    /* access modifiers changed from: protected */
    public void openSettingActivity() {
        Intent intent = new Intent();
        intent.setClass(this.mActivity, CameraPreferenceActivity.class);
        intent.putExtra(BasePreferenceActivity.FROM_WHERE, this.mModuleIndex);
        intent.putExtra(CameraPreferenceActivity.IS_IMAGE_CAPTURE_INTENT, this.mIsImageCaptureIntent);
        intent.putExtra(":miui:starting_window_label", getResources().getString(R.string.pref_camera_settings_category));
        if (this.mActivity.startFromKeyguard()) {
            intent.putExtra(CameraIntentManager.EXTRA_START_WHEN_LOCKED, true);
        }
        this.mActivity.startActivity(intent);
        this.mActivity.setJumpFlag(2);
        CameraStatUtil.trackGotoSettings(this.mModuleIndex);
    }

    public void pausePreview() {
        Log.v(TAG, "pausePreview");
        if (this.mCamera2Device != null) {
            this.mCamera2Device.pausePreview();
        }
        setCameraState(0);
    }

    /* access modifiers changed from: protected */
    public void performKeyClicked(int i, String str, int i2, boolean z) {
        if (!this.mPaused && getCameraState() != 0) {
            if (!isDoingAction()) {
                restoreBottom();
            }
            if (i2 == 0) {
                if (z) {
                    onShutterButtonFocus(true, 1);
                    if (str.equals(getString(R.string.pref_camera_volumekey_function_entryvalue_timer))) {
                        startCount(2, 20);
                    } else {
                        onShutterButtonClick(i);
                    }
                } else {
                    onShutterButtonFocus(false, 0);
                    if (this.mVolumeLongPress) {
                        this.mVolumeLongPress = false;
                        onShutterButtonLongClickCancel(false);
                    }
                }
            } else if (z && !this.mVolumeLongPress) {
                this.mVolumeLongPress = onShutterButtonLongClick();
                if (!this.mVolumeLongPress && this.mLongPressedAutoFocus) {
                    this.mVolumeLongPress = true;
                }
            }
        }
    }

    public void playFocusSound(int i) {
        playCameraSound(i);
    }

    public void reShowMoon() {
        if (this.mEnteringMoonMode) {
            showMoonMode(this.mIsMoonMode);
        }
    }

    public void registerProtocol() {
        super.registerProtocol();
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(165, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(193, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(195, this);
        getActivity().getImplFactory().initAdditional(getActivity(), 164, 174, 199, 227);
        if (getModuleIndex() == 163) {
            ModeProtocol.CameraClickObservable cameraClickObservable = (ModeProtocol.CameraClickObservable) ModeCoordinatorImpl.getInstance().getAttachProtocol(227);
            if (cameraClickObservable != null) {
                cameraClickObservable.addObservable(new int[]{R.string.recommend_portrait, R.string.recommend_super_night, R.string.lens_dirty_detected_title_back, R.string.recommend_macro_mode}, this.mCameraClickObserverAction, 161, 162, 166, 163, 164, 165, 167, 168);
            }
        }
        boolean z = false;
        if (getModuleIndex() == 173) {
            getActivity().getImplFactory().initAdditional(getActivity(), 212);
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("registerProtocol: mIsGoogleLensAvailable = ");
        sb.append(this.mIsGoogleLensAvailable);
        sb.append(", activity is null ? ");
        if (this.mActivity == null) {
            z = true;
        }
        sb.append(z);
        Log.d(str, sb.toString());
        if (this.mIsGoogleLensAvailable && this.mActivity != null) {
            this.mActivity.runOnUiThread(new Runnable() {
                public final void run() {
                    Camera2Module.lambda$registerProtocol$0(Camera2Module.this);
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void resetMetaDataManager() {
        CameraSettings.isSupportedMetadata();
    }

    public void resumePreview() {
        Log.v(TAG, "resumePreview");
        previewWhenSessionSuccess();
        this.mBlockQuickShot = !CameraSettings.isCameraQuickShotEnable();
    }

    public boolean scanQRCodeEnabled() {
        return CameraSettings.isScanQRCode(this.mActivity) && this.mModuleIndex == 163 && !this.mIsImageCaptureIntent && CameraSettings.isBackCamera() && !this.mMultiSnapStatus && !CameraSettings.isStereoModeOn() && !CameraSettings.isPortraitModeBackOn() && (!DataRepository.dataItemFeature().hf() || !CameraSettings.isUltraPixelOn()) && !CameraSettings.isUltraWideConfigOpen(this.mModuleIndex) && !CameraSettings.isMacroModeEnabled(this.mModuleIndex);
    }

    /* access modifiers changed from: protected */
    public void sendOpenFailMessage() {
        this.mHandler.sendEmptyMessage(10);
    }

    public void setAsdScenes(MarshalQueryableASDScene.ASDScene[] aSDSceneArr) {
        this.mAsdScenes = aSDSceneArr;
    }

    public void setFrameAvailable(boolean z) {
        super.setFrameAvailable(z);
        if (z && this.mActivity != null && CameraSettings.isCameraSoundOpen()) {
            this.mActivity.loadCameraSound(1);
            this.mActivity.loadCameraSound(0);
            this.mActivity.loadCameraSound(4);
            this.mActivity.loadCameraSound(5);
            this.mActivity.loadCameraSound(7);
        }
        if (z && this.mCamera2Device != null) {
            this.mCamera2Device.releaseFakeSurfaceIfNeed();
        }
        if (z && isBackCamera()) {
            if ((this.mModuleIndex == 165 || this.mModuleIndex == 163) && CameraSettings.isCameraLyingHintOn()) {
                this.mHandler.post(new Runnable() {
                    public final void run() {
                        Camera2Module.this.mActivity.getSensorStateManager().setLieIndicatorEnabled(true);
                    }
                });
            }
        }
    }

    public boolean shouldCaptureDirectly() {
        return this.mUseLegacyFlashMode && this.mCamera2Device != null && this.mCamera2Device.isNeedFlashOn();
    }

    /* access modifiers changed from: protected */
    public boolean shouldCheckSatFallbackState() {
        return isIn3OrMoreSatMode() && DataRepository.dataItemFeature().shouldCheckSatFallbackState();
    }

    public boolean shouldReleaseLater() {
        return !this.mIsImageCaptureIntent && (getCameraState() == 3 || (this.mEnableShot2Gallery && this.mHandler != null && this.mHandler.hasMessages(50))) && ((this.mHandler == null || (!this.mHandler.hasMessages(48) && !this.mHandler.hasMessages(49))) && !this.mFocusManager.isFocusing() && (this.mModuleIndex != 167 || getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default)).equals(getString(R.string.pref_camera_exposuretime_default))));
    }

    public void showBacklightTip() {
        if (!CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).closeMutexElement(SupportedConfigFactory.CLOSE_BY_AI, 193);
            topAlert.alertHDR(8, false, false);
            topAlert.alertAiSceneSelector(0);
            applyBacklightEffect();
            this.mCamera2Device.setASD(false);
            resumePreviewInWorkThread();
        }
    }

    public void showOrHideChip(boolean z) {
        if (this.mIsGoogleLensAvailable) {
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            boolean z2 = false;
            if (z) {
                boolean z3 = bottomPopupTips != null && bottomPopupTips.isTipShowing();
                ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
                boolean z4 = dualController != null && dualController.isSlideVisible();
                ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
                boolean z5 = makeupProtocol != null && makeupProtocol.isSeekBarVisible();
                ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                boolean z6 = !this.mIsAiConflict && !this.mIsFaceConflict && !this.mIsUltraWideConflict && !this.mIsMoonMode && !z3 && !z4 && !z5 && !(miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow());
                Log.d(TAG, "pre showOrHideChip: isTipsShow = " + z3 + ", isZoomSlideVisible = " + z4 + ", isSeekBarVisible = " + z5 + ", isMakeupVisible = " + r5 + ", mIsAiConflict = " + this.mIsAiConflict + ", mIsUltraWideConflict = " + this.mIsUltraWideConflict + ", mIsMoonMode = " + this.mIsMoonMode + ", mIsFaceConflict = " + this.mIsFaceConflict + ", final isShow = " + z6 + ", mIsCurrentLensEnabled = " + this.mIsCurrentLensEnabled);
                z = z6;
            }
            if (this.mIsCurrentLensEnabled != z) {
                this.mIsCurrentLensEnabled = z;
                Log.d(TAG, "showOrHideChip: show = " + z + ", isChipsEnabled = " + CameraSettings.isAvailableChipsGoogleLens());
                LensAgent instance = LensAgent.getInstance();
                if (z && CameraSettings.isAvailableChipsGoogleLens()) {
                    z2 = true;
                }
                instance.showOrHideChip(z2);
                if (bottomPopupTips != null) {
                    bottomPopupTips.reConfigQrCodeTip();
                }
            }
        }
    }

    public void showQRCodeResult() {
        if (!this.mPaused) {
            String scanResult = PreviewDecodeManager.getInstance().getScanResult();
            if (scanResult == null || scanResult.isEmpty()) {
                Log.e(TAG, "showQRCodeResult: get a null result!");
                return;
            }
            Camera camera = this.mActivity;
            camera.dismissKeyguard();
            Intent intent = new Intent(Util.QRCODE_RECEIVER_ACTION);
            intent.addFlags(32);
            intent.setPackage("com.xiaomi.scanner");
            intent.putExtra("result", scanResult);
            camera.sendBroadcast(intent);
            camera.setJumpFlag(3);
            PreviewDecodeManager.getInstance().resetScanResult();
        }
    }

    public void startAiLens() {
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                Camera2Module.this.startLensActivity();
            }
        }, 300);
    }

    public void startFaceDetection() {
        if (this.mFaceDetectionEnabled && !this.mFaceDetectionStarted && this.mActivity != null && !this.mActivity.isActivityPaused() && isAlive() && this.mMaxFaceCount > 0 && this.mCamera2Device != null) {
            this.mFaceDetectionStarted = true;
            this.mMainProtocol.setActiveIndicator(1);
            this.mCamera2Device.startFaceDetection();
            updateFaceView(true, true);
        }
    }

    public void startFocus() {
        if (!isDeviceAlive() || !isFrameAvailable()) {
            return;
        }
        if (this.mFocusOrAELockSupported) {
            this.mCamera2Device.startFocus(FocusTask.create(1), this.mModuleIndex);
        } else {
            this.mCamera2Device.resumePreview();
        }
    }

    public void startLiveShot() {
        synchronized (this.mCircularMediaRecorderStateLock) {
            if (this.mCircularMediaRecorder == null) {
                CircularMediaRecorder circularMediaRecorder = new CircularMediaRecorder(this.mVideoSize.width, this.mVideoSize.height, getActivity().getGLView().getEGLContext14(), this.mIsMicrophoneEnabled, this.mLivePhotoQueue);
                this.mCircularMediaRecorder = circularMediaRecorder;
            }
            this.mLiveShotEnabled = true;
            this.mCircularMediaRecorder.setOrientationHint(this.mOrientationCompensation);
            this.mCircularMediaRecorder.start();
        }
        this.mActivity.getSensorStateManager().setGyroscopeEnabled(true);
    }

    public void startObjectTracking() {
    }

    public void startPreview() {
        if (this.mCamera2Device != null) {
            this.mCamera2Device.setFocusCallback(this);
            this.mCamera2Device.setMetaDataCallback(this);
            this.mCamera2Device.setScreenLightCallback(this);
            this.mCamera2Device.setErrorCallback(this.mErrorCallback);
            Log.d(TAG, "startPreview: set PictureSize with " + this.mPictureSize);
            this.mCamera2Device.setPictureSize(this.mPictureSize);
            if (isSensorRawStreamRequired() && this.mSensorRawImageSize != null) {
                Log.d(TAG, "startPreview: set SensorRawImageSize with " + this.mSensorRawImageSize);
                this.mCamera2Device.setSensorRawImageSize(this.mSensorRawImageSize, isCurrentRawDomainBasedSuperNight());
            }
            if (this.mEnableParallelSession && isPortraitMode()) {
                Log.d(TAG, "startPreview: set SubPictureSize with " + this.mSubPictureSize);
                this.mCamera2Device.setSubPictureSize(this.mSubPictureSize);
            }
            boolean isEnableQcfaForAlgoUp = isEnableQcfaForAlgoUp();
            Log.d(TAG, "[QCFA] startPreview: set qcfa enable " + isEnableQcfaForAlgoUp);
            this.mCamera2Device.setQcfaEnable(isEnableQcfaForAlgoUp);
            boolean scanQRCodeEnabled = scanQRCodeEnabled();
            boolean supportHandGesture = DataRepository.dataItemRunning().supportHandGesture();
            boolean z = true;
            boolean z2 = this.mIsGoogleLensAvailable || scanQRCodeEnabled || supportHandGesture;
            if (this.mIsGoogleLensAvailable) {
                PreviewDecodeManager.getInstance().init(this.mActualCameraId, 2);
            }
            if (scanQRCodeEnabled) {
                PreviewDecodeManager.getInstance().init(this.mBogusCameraId, 0);
            }
            if (supportHandGesture) {
                PreviewDecodeManager.getInstance().init(this.mBogusCameraId, 1);
            }
            SurfaceTexture surfaceTexture = this.mActivity.getCameraScreenNail().getSurfaceTexture();
            Log.d(TAG, "startPreview: surfaceTexture = " + surfaceTexture);
            if (surfaceTexture != null) {
                this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            }
            Surface surface = surfaceTexture != null ? new Surface(surfaceTexture) : null;
            if (!isSensorRawStreamRequired() || this.mSensorRawImageSize == null) {
                z = false;
            }
            this.mConfigRawStream = z;
            int operatingMode = getOperatingMode();
            if (CameraSettings.isMacro2Sat() && 36866 == operatingMode && DataRepository.dataItemFeature().iB()) {
                operatingMode |= CameraSettings.getLensIndex() << 8;
                Log.d(TAG, "getOperatingMode = " + operatingMode);
                Log.d(TAG, "Index = " + r1);
            }
            int i = operatingMode;
            if (CameraSettings.isMacro2Sat()) {
                CameraSettings.setMacro2Sat(false);
            }
            this.mCamera2Device.startPreviewSession(surface, z2, this.mConfigRawStream, isCurrentRawDomainBasedSuperNight(), i, this.mEnableParallelSession, this);
        }
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
        if (localBinder != null && CameraSettings.isPictureFlawCheckOn()) {
            localBinder.setOnSessionStatusCallBackListener(this.mSessionStatusCallbackListener);
        }
    }

    public void startScreenLight(final int i, final int i2) {
        if (!this.mPaused) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
                    if (fullScreenProtocol != null) {
                        fullScreenProtocol.setScreenLightColor(i);
                        if (fullScreenProtocol.showScreenLight() && Camera2Module.this.mActivity != null) {
                            Camera2Module.this.mCamera2Device.setAELock(true);
                            Camera2Module.this.mActivity.setWindowBrightness(i2);
                        }
                    }
                }
            });
        }
    }

    public void stopFaceDetection(boolean z) {
        if (this.mFaceDetectionEnabled && this.mFaceDetectionStarted) {
            if (!b.isMTKPlatform() || !(getCameraState() == 3 || getCameraState() == 0)) {
                this.mCamera2Device.stopFaceDetection();
            }
            this.mFaceDetectionStarted = false;
            this.mMainProtocol.setActiveIndicator(2);
            updateFaceView(false, z);
        }
    }

    public void stopLiveShot(boolean z) {
        synchronized (this.mCircularMediaRecorderStateLock) {
            if (this.mCircularMediaRecorder != null) {
                this.mCircularMediaRecorder.stop();
                if (z) {
                    this.mCircularMediaRecorder.release();
                    this.mCircularMediaRecorder = null;
                }
            }
            this.mLiveShotEnabled = false;
        }
        this.mActivity.getSensorStateManager().setGyroscopeEnabled(false);
        this.mLivePhotoQueue.clear();
    }

    public void stopObjectTracking(boolean z) {
    }

    public void stopScreenLight() {
        this.mHandler.post(new Runnable() {
            public void run() {
                Camera2Module.this.mCamera2Device.setAELock(false);
                if (Camera2Module.this.mActivity != null) {
                    Camera2Module.this.mActivity.restoreWindowBrightness();
                }
                ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
                String access$300 = Camera2Module.TAG;
                Log.d(access$300, "stopScreenLight: protocol = " + fullScreenProtocol + ", mHandler = " + Camera2Module.this.mHandler);
                if (fullScreenProtocol != null) {
                    fullScreenProtocol.hideScreenLight();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void trackModeCustomInfo(int i) {
        if (this.mModuleIndex == 167) {
            trackManualInfo(i);
        } else if (this.mModuleIndex == 163) {
            if (isFaceBeautyOn(this.mBeautyValues)) {
                trackBeautyInfo(i, isFrontCamera(), new BeautyValues(this.mBeautyValues));
            }
            CameraStatUtil.trackUltraWidePictureTaken();
            CameraStatUtil.trackLyingDirectPictureTaken(this.mIsShowLyingDirectHintStatus);
        } else if (this.mModuleIndex == 165) {
            CameraStatUtil.trackUltraWidePictureTaken();
            CameraStatUtil.trackLyingDirectPictureTaken(this.mIsShowLyingDirectHintStatus);
        }
    }

    public void tryRemoveCountDownMessage() {
        if (this.mCountdownDisposable != null && !this.mCountdownDisposable.isDisposed()) {
            this.mCountdownDisposable.dispose();
            this.mCountdownDisposable = null;
            this.mHandler.post(new Runnable() {
                public void run() {
                    Log.d(Camera2Module.TAG, "run: hide delay number in main thread");
                    Camera2Module.this.mMainProtocol.hideDelayNumber();
                }
            });
        }
    }

    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("unRegisterProtocol: mIsGoogleLensAvailable = ");
        sb.append(this.mIsGoogleLensAvailable);
        sb.append(", activity is null ? ");
        sb.append(this.mActivity == null);
        Log.d(str, sb.toString());
        if (this.mIsGoogleLensAvailable && this.mActivity != null) {
            this.mActivity.runOnUiThread(new Runnable() {
                public final void run() {
                    Camera2Module.lambda$unRegisterProtocol$1(Camera2Module.this);
                }
            });
        }
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(165, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(193, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(195, this);
        getActivity().getImplFactory().detachAdditional();
    }

    public void updateBacklight() {
        if (!isDoingAction() && isAlive()) {
            this.isSilhouette = false;
            applyBacklightEffect();
            resumePreviewInWorkThread();
        }
    }

    /* access modifiers changed from: protected */
    public void updateFaceView(boolean z, boolean z2) {
        if (this.mHandler.hasMessages(35)) {
            this.mHandler.removeMessages(35);
        }
        this.mHandler.obtainMessage(35, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
    }

    public void updateFlashPreference() {
        String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
        String requestFlashMode = getRequestFlashMode();
        if (Util.parseInt(requestFlashMode, 0) != 0) {
            resetAiSceneInHdrOrFlashOn();
        }
        setFlashMode(requestFlashMode);
        if (!TextUtils.equals(componentValue, this.mLastFlashMode) && (Util.parseInt(componentValue, 0) == 103 || Util.parseInt(componentValue, 0) == 0)) {
            resetAsdSceneInHdrOrFlashChange();
        }
        this.mLastFlashMode = componentValue;
        stopObjectTracking(true);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert == null) {
            return;
        }
        if (requestFlashMode.equals(ComponentConfigFlash.FLASH_VALUE_MANUAL_OFF)) {
            topAlert.disableMenuItem(false, 193);
            return;
        }
        topAlert.enableMenuItem(false, 193);
    }

    public void updateHDRPreference() {
        ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
        if (!componentHdr.isEmpty()) {
            String componentValue = componentHdr.getComponentValue(this.mModuleIndex);
            if ((getZoomRatio() != 1.0f || this.mMotionDetected) && this.mMutexModePicker.isHdr() && "auto".equals(componentValue)) {
                onHdrSceneChanged(false);
            }
            if (this.mIsMoonMode || this.mMotionDetected || CameraSettings.isSuperNightOn()) {
                updateHDR("off");
            } else {
                updateHDR(componentValue);
            }
            if ((!"off".equals(componentValue) || this.mAiSceneEnabled) && ((getZoomRatio() <= 1.0f || "normal".equals(componentValue)) && !this.mIsMoonMode && ((!DataRepository.dataItemFeature().ii() || !isStandaloneMacroCamera() || !"auto".equals(componentValue)) && !CameraSettings.isSuperNightOn()))) {
                resetAiSceneInHdrOrFlashOn();
                resetAsdSceneInHdrOrFlashChange();
                if ("auto".equals(componentValue)) {
                    this.mHdrCheckEnabled = true;
                } else {
                    this.mHdrCheckEnabled = false;
                }
                this.mCamera2Device.setHDRCheckerEnable(true);
                return;
            }
            this.mCamera2Device.setHDRCheckerEnable(false);
            this.mHdrCheckEnabled = false;
        }
    }

    public void updateManualEvAdjust() {
        if (this.mModuleIndex == 167) {
            String manualValue = getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default));
            String manualValue2 = getManualValue(CameraSettings.KEY_QC_ISO, getString(R.string.pref_camera_iso_default));
            String str = TAG;
            Log.d(str, "MODE_MANUAL: exposureTime = " + manualValue + "iso = " + manualValue2);
            boolean equals = b.kt() ? getString(R.string.pref_camera_exposuretime_default).equals(manualValue) : getString(R.string.pref_camera_iso_default).equals(manualValue2) || getString(R.string.pref_camera_exposuretime_default).equals(manualValue);
            if (this.mHandler != null) {
                this.mHandler.post(new Runnable(equals) {
                    private final /* synthetic */ boolean f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        Camera2Module.this.mMainProtocol.setEvAdjustable(this.f$1);
                    }
                });
            }
            if (1 == this.mCamera2Device.getFocusMode() && this.m3ALocked) {
                if (this.mActivity != null) {
                    this.mActivity.runOnUiThread(new Runnable() {
                        public final void run() {
                            Camera2Module.this.mFocusManager.cancelFocus();
                        }
                    });
                }
                unlockAEAF();
            }
        }
    }

    public void updateMoon(boolean z) {
        if (z) {
            this.mIsMoonMode = true;
            if (!DataRepository.dataItemFeature().iz()) {
                this.mCamera2Device.setSuperResolution(false);
            }
            updateFocusMode();
            updateHDRPreference();
            this.mCurrentAiScene = 35;
            this.mCamera2Device.setASDScene(35);
            resumePreviewInWorkThread();
            if (this.mZoomSupported) {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(Math.max(20.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                String str = TAG;
                Log.d(str, "updateMoon(): Override zoom ratio range to: [" + getMinZoomRatio() + "," + getMaxZoomRatio() + "]");
            }
            ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            if (mainContentProtocol != null) {
                mainContentProtocol.clearFocusView(1);
            }
        } else if (this.mIsMoonMode) {
            this.mIsMoonMode = false;
            setFocusMode(this.mFocusManager.setFocusMode(CameraSettings.getFocusMode()));
            updateHDRPreference();
            this.mCamera2Device.setASDScene(-35);
            initializeZoomRangeFromCapabilities();
            String str2 = TAG;
            Log.d(str2, "updateMoon(): Restore zoom ratio range to: [" + getMinZoomRatio() + "," + getMaxZoomRatio() + "]");
            if (getZoomRatio() > getMaxZoomRatio()) {
                onZoomingActionUpdate(getMaxZoomRatio(), -1);
            }
        }
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null) {
            dualController.hideSlideView();
        }
    }

    public void updateMoonNight() {
        this.mIsMoonMode = false;
        closeMoonMode(10, 0);
        ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).closeMutexElement(SupportedConfigFactory.CLOSE_BY_AI, 193);
        setFlashMode("0");
        updateMfnr(true);
        updateOIS();
        setAiSceneEffect(10);
        this.mCurrentAiScene = 10;
        resumePreviewInWorkThread();
    }

    public void updateOnTripMode() {
        if (this.mCamera2Device != null && this.mAsdScenes != null) {
            this.mCamera2Device.setOnTripodModeStatus(this.mAsdScenes);
        }
    }

    public void updatePreviewSurface() {
        if (this.mMainProtocol != null) {
            this.mMainProtocol.initEffectCropView();
        }
        checkDisplayOrientation();
        if (this.mActivity != null) {
            if (this.mPreviewSize != null) {
                updateCameraScreenNailSize(this.mPreviewSize.width, this.mPreviewSize.height);
            }
            if (this.mCamera2Device != null) {
                SurfaceTexture surfaceTexture = this.mActivity.getCameraScreenNail().getSurfaceTexture();
                String str = TAG;
                Log.d(str, "updatePreviewSurface: surfaceTexture = " + surfaceTexture);
                if (surfaceTexture != null) {
                    this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
                }
                this.mCamera2Device.updateDeferPreviewSession(new Surface(surfaceTexture));
            }
        }
    }

    public void updateSATZooming(boolean z) {
        if (DataRepository.dataItemFeature().ij() && HybridZoomingSystem.IS_3_OR_MORE_SAT && this.mCamera2Device != null) {
            this.mCamera2Device.setSatIsZooming(z);
            resumePreviewInWorkThread();
        }
    }

    public void updateSilhouette() {
        if (!isDoingAction() && isAlive()) {
            this.isSilhouette = true;
            trackAISceneChanged(this.mModuleIndex, 24);
            setAiSceneEffect(24);
            updateHDR("off");
            this.mCamera2Device.setASDScene(24);
            resumePreviewInWorkThread();
        }
    }
}
