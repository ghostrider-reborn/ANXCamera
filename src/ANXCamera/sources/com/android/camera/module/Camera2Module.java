package com.android.camera.module;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.SensorEvent;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureResult;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.MiuiSettings.ScreenEffect;
import android.text.TextUtils;
import android.util.Range;
import android.util.Size;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import com.android.camera.Camera;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraIntentManager.CameraExtras;
import com.android.camera.CameraPreferenceActivity;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.Exif;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.LocalParallelService.LocalBinder;
import com.android.camera.LocalParallelService.ServiceStatusListener;
import com.android.camera.LocationManager;
import com.android.camera.PictureSizeManager;
import com.android.camera.R;
import com.android.camera.SensorStateManager.SensorStateListener;
import com.android.camera.Thumbnail;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.constant.AutoFocus;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.constant.CameraScene;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.constant.UpdateConstant.UpdateType;
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
import com.android.camera.module.loader.camera2.FocusManager2.Listener;
import com.android.camera.module.loader.camera2.FocusTask;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera.preferences.CameraSettingPreferences;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol.ActionProcessing;
import com.android.camera.protocol.ModeProtocol.BackStack;
import com.android.camera.protocol.ModeProtocol.BaseDelegate;
import com.android.camera.protocol.ModeProtocol.BottomPopupTips;
import com.android.camera.protocol.ModeProtocol.CameraAction;
import com.android.camera.protocol.ModeProtocol.CameraClickObservable;
import com.android.camera.protocol.ModeProtocol.CameraClickObservable.ClickObserver;
import com.android.camera.protocol.ModeProtocol.CameraModuleSpecial;
import com.android.camera.protocol.ModeProtocol.ConfigChanges;
import com.android.camera.protocol.ModeProtocol.DualController;
import com.android.camera.protocol.ModeProtocol.FilterProtocol;
import com.android.camera.protocol.ModeProtocol.FullScreenProtocol;
import com.android.camera.protocol.ModeProtocol.MainContentProtocol;
import com.android.camera.protocol.ModeProtocol.MakeupProtocol;
import com.android.camera.protocol.ModeProtocol.MiBeautyProtocol;
import com.android.camera.protocol.ModeProtocol.RecordState;
import com.android.camera.protocol.ModeProtocol.SnapShotIndicator;
import com.android.camera.protocol.ModeProtocol.TopAlert;
import com.android.camera.protocol.ModeProtocol.TopConfigProtocol;
import com.android.camera.scene.FunctionMiAlgoASDEngine;
import com.android.camera.scene.MiAlgoAsdSceneProfile;
import com.android.camera.statistic.CameraStat;
import com.android.camera.statistic.CameraStatUtil;
import com.android.camera.statistic.ScenarioTrackUtil;
import com.android.camera.storage.ImageSaver;
import com.android.camera.storage.Storage;
import com.android.camera.ui.ObjectView.ObjectViewListener;
import com.android.camera.ui.RotateTextToast;
import com.android.camera.ui.zoom.ZoomingAction;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.Camera2Proxy.BeautyBodySlimCountCallback;
import com.android.camera2.Camera2Proxy.CameraMetaDataCallback;
import com.android.camera2.Camera2Proxy.CameraPreviewCallback;
import com.android.camera2.Camera2Proxy.FaceDetectionCallback;
import com.android.camera2.Camera2Proxy.FocusCallback;
import com.android.camera2.Camera2Proxy.HdrCheckerCallback;
import com.android.camera2.Camera2Proxy.LivePhotoResultCallback;
import com.android.camera2.Camera2Proxy.PictureCallback;
import com.android.camera2.Camera2Proxy.PictureCallbackWrapper;
import com.android.camera2.Camera2Proxy.ScreenLightCallback;
import com.android.camera2.Camera2Proxy.SuperNightCallback;
import com.android.camera2.Camera2Proxy.UltraWideCheckCallback;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CameraHardwareFace;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene.ASDScene;
import com.android.lens.LensAgent;
import com.android.zxing.PreviewDecodeManager;
import com.google.android.apps.photos.api.PhotosOemApi;
import com.google.lens.sdk.LensApi;
import com.google.lens.sdk.LensApi.LensAvailabilityCallback;
import com.mi.config.b;
import com.xiaomi.camera.base.CameraDeviceUtil;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.BaseBoostFramework;
import com.xiaomi.camera.core.MtkBoost;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.ParallelTaskDataParameter;
import com.xiaomi.camera.core.ParallelTaskDataParameter.Builder;
import com.xiaomi.camera.core.PictureInfo;
import com.xiaomi.camera.liveshot.CircularMediaRecorder;
import com.xiaomi.camera.liveshot.LivePhotoResult;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.GraphDescriptorBean;
import com.xiaomi.protocol.ISessionStatusCallBackListener;
import com.xiaomi.protocol.ISessionStatusCallBackListener.Stub;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@TargetApi(21)
public class Camera2Module extends BaseModule implements Listener, ObjectViewListener, CameraMetaDataCallback, CameraAction, FilterProtocol, TopConfigProtocol, CameraModuleSpecial, CameraPreviewCallback, HdrCheckerCallback, ScreenLightCallback, PictureCallback, FaceDetectionCallback, FocusCallback, UltraWideCheckCallback, BeautyBodySlimCountCallback, SuperNightCallback, LivePhotoResultCallback {
    private static final int BURST_SHOOTING_DELAY = 0;
    private static final long CAPTURE_DURATION_THRESHOLD = 12000;
    private static final float MOON_AF_DISTANCE = 0.5f;
    private static final int REQUEST_CROP = 1000;
    /* access modifiers changed from: private */
    public static final String TAG = "Camera2Module";
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
    private ASDScene[] mAsdScenes;
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
    private ClickObserver mCameraClickObserverAction = new ClickObserver() {
        public void action() {
            BottomPopupTips bottomPopupTips = (BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.directlyHideTips();
            }
            TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
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
    /* access modifiers changed from: private */
    public boolean mIsGradienterOn;
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
    private SensorStateListener mSensorStateListener = new SensorStateListener() {
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

        public void onDeviceKeepMoving(double d2) {
            if (!Camera2Module.this.mPaused && Camera2Module.this.mFocusManager != null && !Camera2Module.this.mMultiSnapStatus && !Camera2Module.this.is3ALocked() && !Camera2Module.this.mMainProtocol.isEvAdjusted(true) && !Camera2Module.this.mIsMoonMode) {
                Camera2Module.this.mFocusManager.onDeviceKeepMoving(d2);
            }
        }

        public void onDeviceKeepStable() {
        }

        public void onDeviceLieChanged(boolean z) {
            if (!Camera2Module.this.mPaused) {
                int access$4100 = Camera2Module.this.mIsShowLyingDirectHintStatus;
                Camera2Module camera2Module = Camera2Module.this;
                int i = camera2Module.mOrientationCompensation;
                if (access$4100 != (z ? 1 : 0) + i) {
                    camera2Module.mIsShowLyingDirectHintStatus = i + z;
                    Camera2Module.this.mHandler.removeMessages(58);
                    if (z) {
                        Camera2Module camera2Module2 = Camera2Module.this;
                        Handler handler = camera2Module2.mHandler;
                        handler.sendMessageDelayed(handler.obtainMessage(58, 1, camera2Module2.mOrientationCompensation), 400);
                        Camera2Module camera2Module3 = Camera2Module.this;
                        Handler handler2 = camera2Module3.mHandler;
                        handler2.sendMessageDelayed(handler2.obtainMessage(58, 0, camera2Module3.mOrientationCompensation), 5000);
                        return;
                    }
                    Camera2Module camera2Module4 = Camera2Module.this;
                    Handler handler3 = camera2Module4.mHandler;
                    handler3.sendMessageDelayed(handler3.obtainMessage(58, 0, camera2Module4.mOrientationCompensation), 500);
                }
            }
        }

        public void onDeviceOrientationChanged(float f2, boolean z) {
            Camera2Module camera2Module = Camera2Module.this;
            camera2Module.mDeviceRotation = !z ? f2 : (float) camera2Module.mOrientation;
            if (Camera2Module.this.getCameraState() != 3) {
                EffectController instance = EffectController.getInstance();
                Camera2Module camera2Module2 = Camera2Module.this;
                instance.setDeviceRotation(z, Util.getShootRotation(camera2Module2.mActivity, camera2Module2.mDeviceRotation));
            }
            Camera2Module.this.mHandler.removeMessages(33);
            if (!Camera2Module.this.mPaused && !z && f2 != -1.0f) {
                int roundOrientation = Util.roundOrientation(Math.round(f2), Camera2Module.this.mOrientation);
                Camera2Module.this.mHandler.obtainMessage(33, roundOrientation, (Util.getDisplayRotation(Camera2Module.this.mActivity) + roundOrientation) % ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT).sendToTarget();
            }
        }

        public void onDeviceRotationChanged(float[] fArr) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == 4) {
                Camera2Module camera2Module = Camera2Module.this;
                camera2Module.lastGyroscope = camera2Module.curGyroscope;
                Camera2Module.this.curGyroscope = sensorEvent.values;
            }
        }
    };
    private ServiceStatusListener mServiceStatusListener;
    private ISessionStatusCallBackListener mSessionStatusCallbackListener = new Stub() {
        public void onSessionStatusFlawResultData(int i, final int i2) throws RemoteException {
            String access$300 = Camera2Module.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("resultId:");
            sb.append(i);
            sb.append(",flawResult:");
            sb.append(i2);
            Log.d(access$300, sb.toString());
            if (1.0f == Camera2Module.this.getZoomRatio() && !CameraSettings.isMacroModeEnabled(Camera2Module.this.getModuleIndex())) {
                BottomPopupTips bottomPopupTips = (BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                if (bottomPopupTips == null || !bottomPopupTips.isQRTipVisible()) {
                    final FragmentTopConfig fragmentTopConfig = (FragmentTopConfig) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    if (fragmentTopConfig == null || !fragmentTopConfig.isCurrentRecommendTipText(R.string.super_night_hint)) {
                        Camera2Proxy camera2Proxy = Camera2Module.this.mCamera2Device;
                        if (camera2Proxy == null || !camera2Proxy.isCaptureBusy(true)) {
                            AndroidSchedulers.mainThread().scheduleDirect(new Runnable() {
                                public void run() {
                                    int i = i2;
                                    if (i == 0) {
                                        return;
                                    }
                                    if (i == 1) {
                                        FragmentTopConfig fragmentTopConfig = fragmentTopConfig;
                                        if (fragmentTopConfig != null) {
                                            fragmentTopConfig.alertAiDetectTipHint(0, R.string.pic_flaw_cover, FragmentTopAlert.HINT_DELAY_TIME);
                                        }
                                    } else if (i == 2) {
                                        FragmentTopConfig fragmentTopConfig2 = fragmentTopConfig;
                                        if (fragmentTopConfig2 != null) {
                                            fragmentTopConfig2.alertAiDetectTipHint(0, R.string.pic_flaw_blink_one, FragmentTopAlert.HINT_DELAY_TIME);
                                        }
                                    } else if (i == 3) {
                                        FragmentTopConfig fragmentTopConfig3 = fragmentTopConfig;
                                        if (fragmentTopConfig3 != null) {
                                            fragmentTopConfig3.alertAiDetectTipHint(0, R.string.pic_flaw_blink_more, FragmentTopAlert.HINT_DELAY_TIME);
                                        }
                                    }
                                }
                            });
                        }
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
    private boolean mShowSuperNightHint;
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
    public boolean mWaitingSuperNightResult;

    private static class AsdSceneConsumer implements Consumer<Integer> {
        private WeakReference<BaseModule> mModule;

        public AsdSceneConsumer(BaseModule baseModule) {
            this.mModule = new WeakReference<>(baseModule);
        }

        public void accept(Integer num) throws Exception {
            WeakReference<BaseModule> weakReference = this.mModule;
            if (weakReference != null && weakReference.get() != null) {
                BaseModule baseModule = (BaseModule) this.mModule.get();
                if (baseModule instanceof Camera2Module) {
                    ((Camera2Module) baseModule).consumeAsdSceneResult(num.intValue());
                }
            }
        }
    }

    private final class JpegQuickPictureCallback extends PictureCallbackWrapper {
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
            if (Camera2Module.this.mUpdateImageTitle) {
                String str2 = this.mBurstShotTitle;
                if (str2 != null && this.mSavedJpegCallbackNum == 1) {
                    this.mPressDownTitle = str2;
                    this.mBurstShotTitle = null;
                }
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
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Storage.UBIFOCUS_SUFFIX);
                sb2.append(this.mSavedJpegCallbackNum - 1);
                str = sb2.toString();
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("_BURST");
                sb3.append(this.mSavedJpegCallbackNum);
                str = sb3.toString();
            }
            sb.append(str);
            return sb.toString();
        }

        public void onPictureTaken(byte[] bArr) {
            int i;
            int i2;
            if (!Camera2Module.this.mPaused && bArr != null && Camera2Module.this.mReceivedJpegCallbackNum < Camera2Module.this.mTotalJpegCallbackNum && Camera2Module.this.mMultiSnapStatus) {
                if (this.mSavedJpegCallbackNum == 1 && !Camera2Module.this.mMultiSnapStopRequest && !Camera2Module.this.mMutexModePicker.isUbiFocus()) {
                    Camera2Module.this.mActivity.getImageSaver().updateImage(getBurstShotTitle(), this.mPressDownTitle);
                }
                if (Storage.isLowStorageAtLastPoint()) {
                    if (!Camera2Module.this.mMutexModePicker.isUbiFocus() && Camera2Module.this.mMultiSnapStatus) {
                        Camera2Module.this.trackGeneralInfo(this.mSavedJpegCallbackNum, true);
                        Camera2Module.this.trackPictureTaken(this.mSavedJpegCallbackNum, true, this.mLocation != null, Camera2Module.this.getCurrentAiSceneName(), Camera2Module.this.mEnteringMoonMode, Camera2Module.this.mIsMoonMode);
                        Camera2Module.this.stopMultiSnap();
                    }
                    return;
                }
                Camera2Module.access$804(Camera2Module.this);
                if (!Camera2Module.this.mActivity.getImageSaver().isSaveQueueFull()) {
                    this.mSavedJpegCallbackNum++;
                    if (!Camera2Module.this.mMutexModePicker.isUbiFocus()) {
                        Camera2Module.this.playCameraSound(4);
                    }
                    ViberatorContext.getInstance(Camera2Module.this.getActivity().getApplicationContext()).performBurstCapture();
                    Camera2Module.this.mBurstEmitter.onNext(Integer.valueOf(this.mSavedJpegCallbackNum));
                    boolean z = Camera2Module.this.mMutexModePicker.isUbiFocus() && Camera2Module.this.mReceivedJpegCallbackNum <= Camera2Module.this.mTotalJpegCallbackNum;
                    int orientation = z ? 0 : Exif.getOrientation(bArr);
                    if ((Camera2Module.this.mJpegRotation + orientation) % 180 == 0) {
                        i2 = Camera2Module.this.mPictureSize.getWidth();
                        i = Camera2Module.this.mPictureSize.getHeight();
                    } else {
                        i2 = Camera2Module.this.mPictureSize.getHeight();
                        i = Camera2Module.this.mPictureSize.getWidth();
                    }
                    int i3 = i2;
                    int i4 = i;
                    String burstShotTitle = getBurstShotTitle();
                    boolean z2 = Camera2Module.this.mMutexModePicker.isUbiFocus() && Camera2Module.this.mReceivedJpegCallbackNum == Camera2Module.this.mTotalJpegCallbackNum - 1;
                    if (!Camera2Module.this.mMutexModePicker.isUbiFocus() || Camera2Module.this.mReceivedJpegCallbackNum != Camera2Module.this.mTotalJpegCallbackNum) {
                        Camera2Module.this.mActivity.getImageSaver().addImage(bArr, (Camera2Module.this.mReceivedJpegCallbackNum != 1 || Camera2Module.this.mMultiSnapStopRequest) && (Camera2Module.this.mReceivedJpegCallbackNum == Camera2Module.this.mTotalJpegCallbackNum || Camera2Module.this.mMultiSnapStopRequest || this.mDropped), burstShotTitle, null, System.currentTimeMillis(), null, this.mLocation, i3, i4, null, orientation, z, z2, true, false, false, null, Camera2Module.this.getPictureInfo(), -1);
                        this.mDropped = false;
                    }
                } else {
                    String access$300 = Camera2Module.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("CaptureBurst queue full and drop ");
                    sb.append(Camera2Module.this.mReceivedJpegCallbackNum);
                    Log.e(access$300, sb.toString());
                    this.mDropped = true;
                    if (Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum) {
                        Camera2Module.this.mActivity.getThumbnailUpdater().getLastThumbnailUncached();
                    }
                }
                if (Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum || Camera2Module.this.mMultiSnapStopRequest || this.mDropped) {
                    Camera2Module.this.stopMultiSnap();
                }
            }
        }

        public void onPictureTakenFinished(boolean z) {
            Camera2Module.this.stopMultiSnap();
            Camera2Module.this.mBurstEmitter.onComplete();
        }
    }

    private final class JpegRepeatingCaptureCallback extends PictureCallbackWrapper {
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
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Storage.UBIFOCUS_SUFFIX);
                sb2.append(Camera2Module.this.mReceivedJpegCallbackNum - 1);
                str = sb2.toString();
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("_BURST");
                sb3.append(Camera2Module.this.mReceivedJpegCallbackNum);
                str = sb3.toString();
            }
            sb.append(str);
            return sb.toString();
        }

        private boolean tryCheckNeedStop() {
            if (!Storage.isLowStorageAtLastPoint()) {
                return false;
            }
            if (!Camera2Module.this.mMutexModePicker.isUbiFocus() && Camera2Module.this.mMultiSnapStatus) {
                Camera2Module camera2Module = Camera2Module.this;
                camera2Module.trackGeneralInfo(camera2Module.mReceivedJpegCallbackNum, true);
                Camera2Module camera2Module2 = Camera2Module.this;
                camera2Module2.trackPictureTaken(camera2Module2.mReceivedJpegCallbackNum, true, Camera2Module.this.mLocation != null, Camera2Module.this.getCurrentAiSceneName(), Camera2Module.this.mEnteringMoonMode, Camera2Module.this.mIsMoonMode);
                Camera2Module.this.stopMultiSnap();
            }
            return true;
        }

        /* JADX WARNING: Removed duplicated region for block: B:34:0x00a6  */
        /* JADX WARNING: Removed duplicated region for block: B:37:0x00ee  */
        /* JADX WARNING: Removed duplicated region for block: B:46:0x012d  */
        /* JADX WARNING: Removed duplicated region for block: B:47:0x0139  */
        /* JADX WARNING: Removed duplicated region for block: B:50:0x0144  */
        /* JADX WARNING: Removed duplicated region for block: B:51:0x0149  */
        public ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, CameraSize cameraSize, boolean z) {
            List list;
            Camera2Module camera2Module;
            int i;
            String str = "onCaptureStart: revNum = ";
            boolean z2 = true;
            if (!Camera2Module.this.mEnableParallelSession || Camera2Module.this.mPaused || Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum || !Camera2Module.this.mMultiSnapStatus) {
                String access$300 = Camera2Module.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(Camera2Module.this.mReceivedJpegCallbackNum);
                sb.append(" paused = ");
                sb.append(Camera2Module.this.mPaused);
                sb.append(" status = ");
                sb.append(Camera2Module.this.mMultiSnapStatus);
                Log.d(access$300, sb.toString());
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
                    List faceWaterMarkInfos = ((MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).getFaceWaterMarkInfos();
                    if (faceWaterMarkInfos != null && !faceWaterMarkInfos.isEmpty()) {
                        list = new ArrayList(faceWaterMarkInfos);
                        camera2Module = Camera2Module.this;
                        if (camera2Module.mOutPutSize == null) {
                            camera2Module.mOutPutSize = cameraSize;
                        }
                        Builder filterId = new Builder(Camera2Module.this.mPreviewSize.toSizeObject(), cameraSize.toSizeObject(), Camera2Module.this.mOutPutSize.toSizeObject()).setHasDualWaterMark(CameraSettings.isDualCameraWaterMarkOpen()).setMirror(Camera2Module.this.isFrontMirror()).setLightingPattern(CameraSettings.getPortraitLightingPattern()).setFilterId(EffectController.getInstance().getEffectForSaving(false));
                        i = Camera2Module.this.mOrientation;
                        if (-1 == i) {
                            i = 0;
                        }
                        this.mParallelParameter = filterId.setOrientation(i).setJpegRotation(Camera2Module.this.mJpegRotation).setShootRotation((CameraSettings.isGradienterOn() || Camera2Module.this.mShootRotation != -1.0f) ? Camera2Module.this.mShootRotation : 0.0f).setShootOrientation(Camera2Module.this.mShootOrientation).setLocation(Camera2Module.this.mLocation == null ? new Location(Camera2Module.this.mLocation) : null).setTimeWaterMarkString(!CameraSettings.isTimeWaterMarkOpen() ? Util.getTimeWatermark() : null).setFaceWaterMarkList(list).setAgeGenderAndMagicMirrorWater(CameraSettings.isAgeGenderAndMagicMirrorWaterOpen()).setFrontCamera(Camera2Module.this.isFrontCamera()).setBokehFrontCamera(Camera2Module.this.isBokehFrontCamera()).setAlgorithmName(Camera2Module.this.mAlgorithmName).setPictureInfo(Camera2Module.this.getPictureInfo()).setSuffix(Camera2Module.this.getSuffix()).setGradienterOn(Camera2Module.this.mIsGradienterOn).setTiltShiftMode(Camera2Module.getTiltShiftMode()).setSaveGroupshotPrimitive(false).setDeviceWatermarkParam(Camera2Module.this.getDeviceWaterMarkParam()).setJpegQuality(BaseModule.getJpegQuality(true)).build();
                    }
                }
                list = null;
                camera2Module = Camera2Module.this;
                if (camera2Module.mOutPutSize == null) {
                }
                Builder filterId2 = new Builder(Camera2Module.this.mPreviewSize.toSizeObject(), cameraSize.toSizeObject(), Camera2Module.this.mOutPutSize.toSizeObject()).setHasDualWaterMark(CameraSettings.isDualCameraWaterMarkOpen()).setMirror(Camera2Module.this.isFrontMirror()).setLightingPattern(CameraSettings.getPortraitLightingPattern()).setFilterId(EffectController.getInstance().getEffectForSaving(false));
                i = Camera2Module.this.mOrientation;
                if (-1 == i) {
                }
                this.mParallelParameter = filterId2.setOrientation(i).setJpegRotation(Camera2Module.this.mJpegRotation).setShootRotation((CameraSettings.isGradienterOn() || Camera2Module.this.mShootRotation != -1.0f) ? Camera2Module.this.mShootRotation : 0.0f).setShootOrientation(Camera2Module.this.mShootOrientation).setLocation(Camera2Module.this.mLocation == null ? new Location(Camera2Module.this.mLocation) : null).setTimeWaterMarkString(!CameraSettings.isTimeWaterMarkOpen() ? Util.getTimeWatermark() : null).setFaceWaterMarkList(list).setAgeGenderAndMagicMirrorWater(CameraSettings.isAgeGenderAndMagicMirrorWaterOpen()).setFrontCamera(Camera2Module.this.isFrontCamera()).setBokehFrontCamera(Camera2Module.this.isBokehFrontCamera()).setAlgorithmName(Camera2Module.this.mAlgorithmName).setPictureInfo(Camera2Module.this.getPictureInfo()).setSuffix(Camera2Module.this.getSuffix()).setGradienterOn(Camera2Module.this.mIsGradienterOn).setTiltShiftMode(Camera2Module.getTiltShiftMode()).setSaveGroupshotPrimitive(false).setDeviceWatermarkParam(Camera2Module.this.getDeviceWaterMarkParam()).setJpegQuality(BaseModule.getJpegQuality(true)).build();
            }
            parallelTaskData.fillParameter(this.mParallelParameter);
            if (!Camera2Module.this.mActivity.getImageSaver().isSaveQueueFull()) {
                Camera2Module.access$804(Camera2Module.this);
                if (!Camera2Module.this.mMutexModePicker.isUbiFocus()) {
                    Camera2Module.this.playCameraSound(4);
                }
                ViberatorContext.getInstance(Camera2Module.this.getActivity().getApplicationContext()).performBurstCapture();
                String access$3002 = Camera2Module.TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(Camera2Module.this.mReceivedJpegCallbackNum);
                Log.d(access$3002, sb2.toString());
                Camera2Module.this.mBurstEmitter.onNext(Integer.valueOf(Camera2Module.this.mReceivedJpegCallbackNum));
                if (!Camera2Module.this.mMutexModePicker.isUbiFocus() && Camera2Module.this.mReceivedJpegCallbackNum <= Camera2Module.this.mTotalJpegCallbackNum) {
                    String generateFilepath4Jpeg = Storage.generateFilepath4Jpeg(getBurstShotTitle());
                    String access$3003 = Camera2Module.TAG;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("onCaptureStart: savePath = ");
                    sb3.append(generateFilepath4Jpeg);
                    Log.d(access$3003, sb3.toString());
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
                String access$3004 = Camera2Module.TAG;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("onCaptureStart: queue full and drop ");
                sb4.append(Camera2Module.this.mReceivedJpegCallbackNum);
                Log.e(access$3004, sb4.toString());
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
                    removeCallbacksAndMessages(null);
                } else if (camera2Module.getActivity() != null) {
                    int i = message.what;
                    if (i == 2) {
                        camera2Module.getWindow().clearFlags(128);
                    } else if (i == 4) {
                        camera2Module.checkActivityOrientation();
                        if (SystemClock.uptimeMillis() - camera2Module.mOnResumeTime < 5000) {
                            sendEmptyMessageDelayed(4, 100);
                        }
                    } else if (i == 17) {
                        removeMessages(17);
                        removeMessages(2);
                        camera2Module.getWindow().addFlags(128);
                        sendEmptyMessageDelayed(2, (long) camera2Module.getScreenDelay());
                    } else if (i == 31) {
                        camera2Module.setOrientationParameter();
                    } else if (i != 33) {
                        boolean z = false;
                        if (i == 35) {
                            boolean z2 = message.arg1 > 0;
                            if (message.arg2 > 0) {
                                z = true;
                            }
                            camera2Module.handleUpdateFaceView(z2, z);
                        } else if (i == 44) {
                            camera2Module.restartModule();
                        } else if (i != 45) {
                            switch (i) {
                                case 9:
                                    camera2Module.mMainProtocol.initializeFocusView(camera2Module);
                                    camera2Module.mMainProtocol.setObjectViewListener(camera2Module);
                                    break;
                                case 10:
                                    if (!camera2Module.mActivity.isActivityPaused()) {
                                        camera2Module.mOpenCameraFail = true;
                                        camera2Module.onCameraException();
                                        break;
                                    }
                                    break;
                                case 11:
                                    break;
                                default:
                                    switch (i) {
                                        case 48:
                                            camera2Module.setCameraState(1);
                                            break;
                                        case 49:
                                            if (camera2Module.isAlive()) {
                                                camera2Module.stopMultiSnap();
                                                camera2Module.mBurstEmitter.onComplete();
                                                break;
                                            } else {
                                                return;
                                            }
                                        case 50:
                                            Log.w(Camera2Module.TAG, "Oops, capture timeout later release timeout!");
                                            camera2Module.onPictureTakenFinished(false);
                                            break;
                                        case 51:
                                            if (!camera2Module.mActivity.isActivityPaused()) {
                                                camera2Module.mOpenCameraFail = true;
                                                camera2Module.onCameraException();
                                                break;
                                            }
                                            break;
                                        case 52:
                                            camera2Module.onShutterButtonClick(camera2Module.getTriggerMode());
                                            break;
                                        default:
                                            switch (i) {
                                                case 56:
                                                    MainContentProtocol mainContentProtocol = camera2Module.mMainProtocol;
                                                    if (mainContentProtocol != null && mainContentProtocol.isFaceExists(1) && camera2Module.mMainProtocol.isFocusViewVisible()) {
                                                        Camera2Proxy camera2Proxy = camera2Module.mCamera2Device;
                                                        if (camera2Proxy != null && 4 == camera2Proxy.getFocusMode()) {
                                                            camera2Module.mMainProtocol.clearFocusView(7);
                                                            break;
                                                        }
                                                    }
                                                case 57:
                                                    PreviewDecodeManager.getInstance().reset();
                                                    break;
                                                case 58:
                                                    ConfigChanges configChanges = (ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                                                    if (configChanges != null) {
                                                        int i2 = message.arg2;
                                                        configChanges.configRotationChange(message.arg1, (360 - (i2 >= 0 ? i2 % ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT : (i2 % ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT) + ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT)) % ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT);
                                                        break;
                                                    }
                                                    break;
                                                default:
                                                    StringBuilder sb = new StringBuilder();
                                                    sb.append("no consumer for this message: ");
                                                    sb.append(message.what);
                                                    throw new RuntimeException(sb.toString());
                                            }
                                            break;
                                    }
                            }
                        } else {
                            camera2Module.setActivity(null);
                        }
                    } else {
                        camera2Module.setOrientation(message.arg1, message.arg2);
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
                    BottomPopupTips bottomPopupTips = (BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                    if (bottomPopupTips != null) {
                        bottomPopupTips.showTips(11, (int) R.string.super_night_toast, 4);
                    }
                } else if (intValue == 2000) {
                    Log.d(Camera2Module.TAG, "SuperNight: trigger shutter animation, sound and post saving");
                    camera2Module.mWaitingSuperNightResult = true;
                    camera2Module.animateCapture();
                    camera2Module.playCameraSound(0);
                    RecordState recordState = (RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
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
        StringBuilder sb = new StringBuilder();
        sb.append("algo begin: ");
        sb.append(parallelTaskData.getSavePath());
        sb.append(" | ");
        sb.append(Thread.currentThread().getName());
        Log.i(str, sb.toString());
        if (this.mServiceStatusListener == null) {
            this.mServiceStatusListener = new ServiceStatusListener() {
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
                        Camera2Module camera2Module = Camera2Module.this;
                        Camera2Proxy camera2Proxy = camera2Module.mCamera2Device;
                        if (camera2Proxy != null) {
                            camera2Proxy.onParallelImagePostProcStart();
                            return;
                        }
                        Camera camera = camera2Module.mActivity;
                        if (camera != null) {
                            camera.removeShotAfterPictureTaken();
                        }
                    }
                }
            };
            AlgoConnector.getInstance().setServiceStatusListener(this.mServiceStatusListener);
        }
    }

    private long calculateTimeout(int i) {
        long j = CAPTURE_DURATION_THRESHOLD;
        if (i == 167) {
            return (Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))) / 1000000) + CAPTURE_DURATION_THRESHOLD;
        }
        if (i == 173 || CameraSettings.isSuperNightOn()) {
            j = 24000;
        }
        return j;
    }

    private void checkMoreFrameCaptureLockAFAE(boolean z) {
        if (DataRepository.dataItemFeature().Sc()) {
            if ((ModuleManager.isSuperNightScene() || CameraSettings.isSuperNightOn() || this.mMutexModePicker.isHdr()) && !is3ALocked()) {
                if (z) {
                    Log.d(TAG, "more frame capture lock af/ae");
                    this.mCamera2Device.setFocusMode(1);
                    this.mCamera2Device.lockExposure(true, true);
                } else {
                    Log.d(TAG, "more frame capture unlock af/ae");
                    updateFocusMode();
                    this.mCamera2Device.unlockExposure();
                    resumePreview();
                }
            }
        }
    }

    private boolean checkShutterCondition() {
        if (isBlockSnap() || isIgnoreTouchEvent()) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("checkShutterCondition: blockSnap=");
            sb.append(isBlockSnap());
            sb.append(" ignoreTouchEvent=");
            sb.append(isIgnoreTouchEvent());
            Log.w(str, sb.toString());
            return false;
        } else if (Storage.isLowStorageAtLastPoint()) {
            Log.w(TAG, "checkShutterCondition: low storage");
            return false;
        } else if (!isFrontCamera() || !this.mActivity.isScreenSlideOff()) {
            BackStack backStack = (BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
            if (backStack != null) {
                backStack.handleBackStackFromShutter();
            }
            return true;
        } else {
            Log.w(TAG, "checkShutterCondition: screen is slide off");
            return false;
        }
    }

    private void configParallelSession() {
        GraphDescriptorBean graphDescriptorBean;
        if (isPortraitMode()) {
            int i = (isDualFrontCamera() || isDualCamera() || isBokehUltraWideBackCamera()) ? 2 : 1;
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("configParallelSession: inputStreamNum = ");
            sb.append(i);
            Log.d(str, sb.toString());
            graphDescriptorBean = new GraphDescriptorBean(32770, i, true, CameraDeviceUtil.getCameraCombinationMode(this.mActualCameraId));
        } else {
            int i2 = this.mModuleIndex;
            if (i2 == 167) {
                graphDescriptorBean = new GraphDescriptorBean(32771, 1, true, CameraDeviceUtil.getCameraCombinationMode(this.mActualCameraId));
            } else if (i2 == 175) {
                graphDescriptorBean = new GraphDescriptorBean(33011, 1, true, CameraDeviceUtil.getCameraCombinationMode(this.mActualCameraId));
            } else {
                int cameraCombinationMode = CameraDeviceUtil.getCameraCombinationMode(this.mActualCameraId);
                if (cameraCombinationMode == 0) {
                    cameraCombinationMode = 513;
                }
                graphDescriptorBean = new GraphDescriptorBean(0, 1, true, cameraCombinationMode);
            }
        }
        String str2 = TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("configParallelSession: pictureSize = ");
        sb2.append(this.mPictureSize);
        Log.d(str2, sb2.toString());
        String str3 = TAG;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("configParallelSession: outputSize = ");
        sb3.append(this.mOutPutSize);
        Log.d(str3, sb3.toString());
        CameraSize cameraSize = this.mPictureSize;
        BufferFormat bufferFormat = new BufferFormat(cameraSize.width, cameraSize.height, 35, graphDescriptorBean);
        LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder(true);
        localBinder.configCaptureSession(bufferFormat);
        localBinder.setImageSaver(this.mActivity.getImageSaver());
        CameraSize cameraSize2 = this.mOutPutSize;
        localBinder.setJpegOutputSize(cameraSize2.width, cameraSize2.height);
        localBinder.setCallerIdentity(this.mActivity.hashCode());
    }

    /* access modifiers changed from: private */
    public void consumeAiSceneResult(int i, boolean z) {
        if (this.mAiSceneEnabled) {
            realConsumeAiSceneResult(i, z);
            int i2 = this.mCurrentAiScene;
            if (!(i2 == -1 || i2 == 23 || i2 == 24 || i2 == 35)) {
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

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r3v1, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r3v2, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r3v3, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r0v3, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r3v7 */
    /* JADX WARNING: type inference failed for: r3v14, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r3v15, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r3v16, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r3v17 */
    /* JADX WARNING: type inference failed for: r3v18 */
    /* JADX WARNING: type inference failed for: r3v19 */
    /* JADX WARNING: type inference failed for: r3v20 */
    /* JADX WARNING: type inference failed for: r3v21 */
    /* JADX WARNING: type inference failed for: r3v22 */
    /* JADX WARNING: type inference failed for: r3v23 */
    /* JADX WARNING: type inference failed for: r3v24 */
    /* JADX WARNING: type inference failed for: r3v25 */
    /* JADX WARNING: type inference failed for: r3v26 */
    /* JADX WARNING: type inference failed for: r3v27 */
    /* JADX WARNING: type inference failed for: r3v28 */
    /* JADX WARNING: type inference failed for: r3v29 */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00ee, code lost:
        r6 = th;
        r3 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00f0, code lost:
        r3 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        r6.mActivity.setResult(0);
        r6.mActivity.finish();
        r3 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00fa, code lost:
        com.android.camera.Util.closeSilently(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00fd, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00fe, code lost:
        r3 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        r6.mActivity.setResult(0);
        r6.mActivity.finish();
        r3 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0108, code lost:
        com.android.camera.Util.closeSilently(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x010b, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:44:0x00f0, B:48:0x00fe] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:44:0x00f0 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:48:0x00fe */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v4
  assigns: []
  uses: []
  mth insns count: 115
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:44:0x00f0=Splitter:B:44:0x00f0, B:48:0x00fe=Splitter:B:48:0x00fe} */
    /* JADX WARNING: Unknown variable types count: 11 */
    private void doAttach() {
        ? r3;
        ? r32;
        String str = sTempCropFilename;
        if (!this.mPaused) {
            byte[] storedJpegData = this.mActivity.getImageSaver().getStoredJpegData();
            if (this.mIsSaveCaptureImage) {
                this.mActivity.getImageSaver().saveStoredData();
            }
            ? r33 = 0;
            if (this.mCropValue != null) {
                ? r34 = r33;
                ? r35 = r33;
                File fileStreamPath = this.mActivity.getFileStreamPath(str);
                fileStreamPath.delete();
                ? openFileOutput = this.mActivity.openFileOutput(str, 0);
                try {
                    openFileOutput.write(storedJpegData);
                    openFileOutput.close();
                    r34 = r33;
                    r35 = r33;
                    Uri fromFile = Uri.fromFile(fileStreamPath);
                    Util.closeSilently(null);
                    Bundle bundle = new Bundle();
                    if (ComponentRunningTiltValue.TILT_CIRCLE.equals(this.mCropValue)) {
                        bundle.putString("circleCrop", "true");
                    }
                    Uri uri = this.mSaveUri;
                    if (uri != null) {
                        bundle.putParcelable("output", uri);
                    } else {
                        bundle.putBoolean("return-data", true);
                    }
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setData(fromFile);
                    intent.putExtras(bundle);
                    this.mActivity.startActivityForResult(intent, 1000);
                } catch (FileNotFoundException unused) {
                    r34 = openFileOutput;
                } catch (IOException unused2) {
                    r35 = openFileOutput;
                } catch (Throwable th) {
                    th = th;
                    ? r36 = openFileOutput;
                    Util.closeSilently(r36);
                    throw th;
                }
            } else if (this.mSaveUri != null) {
                try {
                    r32 = r33;
                    ? openOutputStream = CameraAppImpl.getAndroidContext().getContentResolver().openOutputStream(this.mSaveUri);
                    openOutputStream.write(storedJpegData);
                    openOutputStream.close();
                    this.mActivity.setResult(-1);
                    r32 = openOutputStream;
                    r3 = openOutputStream;
                } catch (Exception e2) {
                    Log.e(TAG, "Exception when doAttach: ", e2);
                    r3 = r32;
                } catch (Throwable th2) {
                    this.mActivity.finish();
                    Util.closeSilently(r32);
                    throw th2;
                }
                this.mActivity.finish();
                Util.closeSilently(r3);
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
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeMessages(50);
        }
        if (this.mActivity.isActivityPaused()) {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            boolean z = camera2Proxy != null && camera2Proxy.isSessionReady();
            if (z) {
                Log.d(TAG, "doLaterRelease");
            } else {
                Log.d(TAG, "doLaterRelease but session is closed");
            }
            this.mActivity.releaseAll(true, z);
        } else if (isDeparted()) {
            Log.w(TAG, "doLaterReleaseIfNeed: isDeparted...");
        } else {
            this.mHandler.post(new x(this));
            if (isTextureExpired()) {
                Log.d(TAG, "doLaterReleaseIfNeed: surfaceTexture expired, restartModule");
                this.mHandler.post(new y(this));
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0048, code lost:
        if (r4.mOperatingMode == 36867) goto L_0x004c;
     */
    private boolean enableFrontMFNR() {
        boolean z = true;
        if (b.isMTKPlatform()) {
            if (!b.Ji() || !DataRepository.dataItemFeature().gb()) {
                z = false;
            }
            return z;
        }
        if (b.Ji()) {
            if (this.mOperatingMode != 32773) {
                if (DataRepository.dataItemFeature().gb()) {
                    if (this.mOperatingMode != 36865) {
                        if ("tucana".equals(b.km)) {
                        }
                    }
                }
            }
            return z;
        }
        z = false;
        return z;
    }

    private boolean enablePreviewAsThumbnail() {
        boolean z = false;
        if (!isAlive() || CameraSettings.isUltraPixelOn() || this.mModuleIndex == 175) {
            return false;
        }
        if (this.mEnableParallelSession) {
            return true;
        }
        if (this.mIsPortraitLightingOn) {
            return false;
        }
        if (CameraSettings.isLiveShotOn()) {
            return true;
        }
        if (CameraSettings.isGradienterOn()) {
            return DataRepository.dataItemFeature()._c();
        }
        if (CameraSettings.isPortraitModeBackOn()) {
            return true;
        }
        int i = this.mModuleIndex;
        if (i == 167) {
            return false;
        }
        if (i != 173 && !CameraSettings.isSuperNightOn() && !CameraSettings.showGenderAge() && !CameraSettings.isMagicMirrorOn() && !CameraSettings.isTiltShiftOn()) {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null && camera2Proxy.isNeedPreviewThumbnail()) {
                z = true;
            }
        }
        return z;
    }

    private void enterAsdScene(int i) {
        TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        String str = "1";
        if (i == 0) {
            topAlert.alertFlash(0, str, false);
        } else if (i == 9) {
            String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
            if ("3".equals(componentValue)) {
                topAlert.alertFlash(0, str, false);
                updatePreferenceInWorkThread(10);
            } else if (ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO.equals(componentValue)) {
                topAlert.alertFlash(0, str, false);
                Log.d(TAG, "enterAsdScene(): turn off HDR as FLASH has higher priority than HDR");
                onHdrSceneChanged(false);
                updatePreferenceInWorkThread(10);
            }
        } else if (i != 4) {
            if (i != 5) {
                if (i != 6) {
                    if (i == 7 && !this.m3ALocked) {
                        setPortraitSuccessHintVisible(0);
                    }
                } else if (!this.m3ALocked) {
                    updateTipMessage(6, R.string.portrait_mode_lowlight_hint, 4);
                }
            } else if (!this.m3ALocked) {
                updateTipMessage(6, R.string.portrait_mode_too_far_hint, 4);
            }
        } else if (!this.m3ALocked) {
            updateTipMessage(6, R.string.portrait_mode_too_close_hint, 4);
        }
    }

    private void exitAsdScene(int i) {
        TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        String str = "1";
        if (i == 0) {
            String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
            if (!str.equals(componentValue) && !ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_ON.equals(componentValue) && !"2".equals(componentValue)) {
                topAlert.alertFlash(8, str, false);
            }
        } else if (i == 9) {
            String componentValue2 = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
            if ("3".equals(componentValue2) || ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO.equals(componentValue2)) {
                topAlert.alertFlash(8, str, false);
            }
            updatePreferenceInWorkThread(10);
        } else if (i == 4 || i == 5 || i == 6) {
            if (!this.m3ALocked) {
                hideTipMessage(0);
            }
        } else if (i == 7 && !this.m3ALocked) {
            setPortraitSuccessHintVisible(8);
        }
    }

    private String getCalibrationDataFileName(int i) {
        return isFrontCamera() ? "front_dual_camera_caldata.bin" : i == Camera2DataContainer.getInstance().getUltraWideBokehCameraId() ? "back_dual_camera_caldata_wu.bin" : "back_dual_camera_caldata.bin";
    }

    private int getCountDownTimes(int i) {
        Intent intent = this.mBroadcastIntent;
        int timerDurationSeconds = intent != null ? CameraIntentManager.getInstance(intent).getTimerDurationSeconds() : this.mActivity.getCameraIntentManager().getTimerDurationSeconds();
        if (timerDurationSeconds != -1) {
            Intent intent2 = this.mBroadcastIntent;
            String str = CameraExtras.TIMER_DURATION_SECONDS;
            if (intent2 != null) {
                intent2.removeExtra(str);
            } else {
                this.mActivity.getIntent().removeExtra(str);
            }
            if (timerDurationSeconds != 0) {
                return timerDurationSeconds != 5 ? 3 : 5;
            }
            return 0;
        } else if (i != 100 || !CameraSettings.isHandGestureOpen()) {
            return CameraSettings.getCountDownTimes();
        } else {
            int countDownTimes = CameraSettings.getCountDownTimes();
            if (countDownTimes == 0) {
                countDownTimes = 3;
            }
            return countDownTimes;
        }
    }

    /* access modifiers changed from: private */
    public String getCurrentAiSceneName() {
        int i = this.mCurrentAiScene;
        int i2 = this.mModuleIndex;
        if (i2 != 163 && i2 != 167) {
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
        float f2;
        float f3;
        float f4;
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
            resourceFloat = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_size_ratio, 0.0f);
            resourceFloat2 = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_padding_x_ratio, 0.0f);
            resourceFloat3 = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_padding_y_ratio, 0.0f);
        } else {
            f4 = 0.0f;
            f3 = 0.0f;
            f2 = 0.0f;
            DeviceWatermarkParam deviceWatermarkParam = new DeviceWatermarkParam(isDualCameraWaterMarkOpen, isFrontCameraWaterMarkOpen, CameraSettings.isUltraPixelRearOn(), CameraSettings.getDualCameraWaterMarkFilePathVendor(), f4, f3, f2);
            return deviceWatermarkParam;
        }
        f2 = resourceFloat3;
        f4 = resourceFloat;
        f3 = resourceFloat2;
        DeviceWatermarkParam deviceWatermarkParam2 = new DeviceWatermarkParam(isDualCameraWaterMarkOpen, isFrontCameraWaterMarkOpen, CameraSettings.isUltraPixelRearOn(), CameraSettings.getDualCameraWaterMarkFilePathVendor(), f4, f3, f2);
        return deviceWatermarkParam2;
    }

    private String getManualValue(String str, String str2) {
        return ModuleManager.isManualModule() ? CameraSettingPreferences.instance().getString(str, str2) : str2;
    }

    /* access modifiers changed from: private */
    public PictureInfo getPictureInfo() {
        PictureInfo opMode = new PictureInfo().setFrontMirror(isFrontMirror()).setSensorType(isFrontCamera()).setBokehFrontCamera(isBokehFrontCamera()).setHdrType(DataRepository.dataItemConfig().getComponentHdr().getComponentValue(this.mModuleIndex)).setOpMode(getOperatingMode());
        if (isPortraitMode()) {
            opMode.setAiEnabled(this.mAiSceneEnabled);
            opMode.setAiType(this.mCurrentAiScene);
            opMode.setAIScene(this.mCurrentAiScene);
        }
        if (this.mBeautyValues != null) {
            if (!BeautyConstant.LEVEL_CLOSE.equals(CameraSettings.getFaceBeautifyLevel()) && DataRepository.dataItemRunning().getComponentRunningShine().getBeautyVersion() == 2) {
                opMode.setBeautyLevel(this.mBeautyValues.mBeautyLevel);
            }
        }
        if (this.mFaceDetectionEnabled) {
            FaceAnalyzeInfo faceAnalyzeInfo = this.mFaceInfo;
            if (faceAnalyzeInfo != null) {
                opMode.setGender(faceAnalyzeInfo.mGender);
                opMode.setBaby(this.mFaceInfo.mAge);
            }
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
            List supportedOutputSize = capabilities.getSupportedOutputSize(i2);
            if (cameraSize != null) {
                List arrayList = new ArrayList(0);
                for (int i3 = 0; i3 < supportedOutputSize.size(); i3++) {
                    CameraSize cameraSize3 = (CameraSize) supportedOutputSize.get(i3);
                    if (cameraSize3.compareTo(cameraSize) <= 0) {
                        arrayList.add(cameraSize3);
                    }
                }
                supportedOutputSize = arrayList;
            }
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("getPictureSize: matchSizes = ");
            sb.append(supportedOutputSize);
            Log.d(str, sb.toString());
            cameraSize2 = PictureSizeManager.getBestPictureSize(supportedOutputSize);
        } else {
            cameraSize2 = null;
        }
        String str2 = TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("getPictureSize: cameraId = ");
        sb2.append(i);
        sb2.append(" size = ");
        sb2.append(cameraSize2);
        Log.d(str2, sb2.toString());
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
        if (satMasterCameraId == 1) {
            return this.mUltraWidePictureSize;
        }
        if (satMasterCameraId == 2) {
            return this.mWidePictureSize;
        }
        if (satMasterCameraId == 3) {
            return this.mTelePictureSize;
        }
        if (satMasterCameraId == 4) {
            return this.mUltraTelePictureSize;
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("getSatPictureSize: invalid satMasterCameraId ");
        sb.append(satMasterCameraId);
        Log.e(str, sb.toString());
        return this.mWidePictureSize;
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
        Disposable disposable = this.mSuperNightDisposable;
        if (disposable == null) {
            return false;
        }
        if (!disposable.isDisposed()) {
            this.mSuperNightDisposable.dispose();
        }
        this.mSuperNightDisposable = null;
        boolean z = !this.mWaitingSuperNightResult;
        this.mWaitingSuperNightResult = false;
        if (z) {
            Log.d(TAG, "SuperNight: force trigger shutter animation, sound and post saving");
        }
        stopCpuBoost();
        AndroidSchedulers.mainThread().scheduleDirect(new p(this, z));
        return true;
    }

    private void handleSuperNightResultInCaptureMode() {
        if (this.mShowSuperNightHint) {
            this.mHandler.post(new j(this));
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
        ((BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).delegateEvent(6);
    }

    private void hideSceneSelector() {
        this.mHandler.post(q.INSTANCE);
    }

    private void initAiSceneParser() {
        this.mFunctionParseAiScene = new FunctionParseAiScene(this.mModuleIndex, getCameraCapabilities());
        this.mAiSceneDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
            public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                Camera2Module.this.mAiSceneFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).observeOn(GlobalConstant.sCameraSetupScheduler).map(this.mFunctionParseAiScene).filter(new PredicateFilterAiScene(this)).observeOn(AndroidSchedulers.mainThread()).subscribe((Consumer<? super T>) new Consumer<Integer>() {
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
                Camera2Module.this.mMetaDataFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).observeOn(GlobalConstant.sCameraSetupScheduler).map(new FunctionParseAsdFace(this, isFrontCamera())).map(new FunctionParseAsdHdr(this)).map(new FunctionParseAsdUltraWide(this.mModuleIndex, this)).map(new FunctionParseAsdLivePhoto(this)).map(new FunctionParseSuperNight(this)).map(new FunctionMiAlgoASDEngine(this)).sample(500, TimeUnit.MILLISECONDS).observeOn(GlobalConstant.sCameraSetupScheduler).map(new FunctionParseAsdScene(this)).observeOn(AndroidSchedulers.mainThread()).onTerminateDetach().subscribe((Consumer<? super T>) new AsdSceneConsumer<Object>(this));
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
        return this.mModuleIndex == 173 && DataRepository.dataItemFeature().Zc();
    }

    private boolean isEnableQcfaForAlgoUp() {
        if (!this.mCameraCapabilities.isSupportedQcfa() || !this.mEnableParallelSession) {
            return false;
        }
        if (b.isMTKPlatform()) {
            return CameraSettings.isUltraPixelOn();
        }
        boolean z = true;
        if (isInQCFAMode()) {
            return true;
        }
        if (!CameraSettings.isUltraPixelOn() || !DataRepository.dataItemFeature().ed()) {
            z = false;
        }
        return z;
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
        StringBuilder sb = new StringBuilder();
        sb.append("isIn3OrMoreSatMode: opMode=0x");
        sb.append(Integer.toHexString(this.mOperatingMode));
        sb.append(" isIn3OrMore=");
        sb.append(HybridZoomingSystem.IS_3_OR_MORE_SAT);
        Log.d(str, sb.toString());
        return 36866 == this.mOperatingMode && HybridZoomingSystem.IS_3_OR_MORE_SAT;
    }

    private boolean isInCountDown() {
        Disposable disposable = this.mCountdownDisposable;
        return disposable != null && !disposable.isDisposed();
    }

    private boolean isInQCFAMode() {
        boolean z = false;
        if (getModuleIndex() != 163 && getModuleIndex() != 165) {
            return false;
        }
        if (this.mCameraCapabilities.isSupportedQcfa() && isFrontCamera()) {
            z = true;
        }
        return z;
    }

    private boolean isLaunchedByMainIntent() {
        String str;
        Camera camera = this.mActivity;
        if (camera != null) {
            Intent intent = camera.getIntent();
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
        LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
        if (localBinder != null) {
            z = localBinder.needWaitProcess();
        } else {
            Log.w(TAG, "isParallelQueueFull: NOTICE: CHECK WHY BINDER IS NULL!");
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("isParallelQueueFull: isNeedWaitProcess:");
        sb.append(z);
        Log.w(str, sb.toString());
        return z;
    }

    private boolean isPortraitSuccessHintShowing() {
        return ((BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).isPortraitHintVisible();
    }

    private boolean isPreviewThumbnailWhenFlash() {
        if (!this.mUseLegacyFlashMode) {
            return true;
        }
        if (!"3".equals(this.mLastFlashMode)) {
            if (!"1".equals(this.mLastFlashMode)) {
                return true;
            }
        }
        return false;
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
        return DataRepository.dataItemFeature().Zc();
    }

    private boolean isTriggerQcfaModeChange(boolean z, boolean z2) {
        if (!this.mCameraCapabilities.isSupportedQcfa()) {
            return false;
        }
        if ((this.mModuleIndex != 171 || !isBokehFrontCamera()) && DataRepository.dataItemFeature().Da() <= 0 && z && !mIsBeautyFrontOn) {
            if (this.mOperatingMode == 32775) {
                return true;
            }
            DataRepository.dataItemConfig().getComponentHdr().getComponentValue(this.mModuleIndex);
        }
        return false;
    }

    private boolean isUseSwMfnr() {
        if (CameraSettings.isGroupShotOn()) {
            Log.d(TAG, "GroupShot is on");
            return false;
        } else if (!DataRepository.dataItemFeature().kb() && (isUltraWideBackCamera() || isZoomRatioBetweenUltraAndWide())) {
            Log.d(TAG, "SwMfnr force off for ultra wide camera");
            return false;
        } else if (!CameraSettings.isMfnrSatEnable()) {
            Log.d(TAG, "Mfnr not enabled");
            return false;
        } else if (!DataRepository.dataItemFeature().dd()) {
            Log.d(TAG, "SwMfnr is not supported");
            return false;
        } else if (!this.mMutexModePicker.isNormal()) {
            Log.d(TAG, "Mutex mode is not normal");
            return false;
        } else {
            if (DataRepository.dataItemFeature().kb()) {
                int i = this.mModuleIndex;
                if (!(i == 167 || i == 173 || CameraSettings.isSuperNightOn())) {
                    Log.d(TAG, "For the devices does not have hardware MFNR, use software MFNR");
                    return true;
                }
            }
            if (!isFrontCamera() || isDualFrontCamera()) {
                return false;
            }
            if (this.mOperatingMode == 32773 && b.Ji()) {
                return true;
            }
            if (this.mOperatingMode != 32773 || b.Ji()) {
                return DataRepository.dataItemFeature().isSupportUltraWide() || b.wn || b.Fn;
            }
            return false;
        }
    }

    static /* synthetic */ void k(boolean z) {
        TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertHDR(z ? 0 : 8, false, false);
        }
    }

    private void keepScreenOnAwhile() {
        this.mHandler.sendEmptyMessageDelayed(17, 1000);
    }

    private void lockAEAF() {
        Log.d(TAG, "lockAEAF");
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(true);
        }
        this.m3ALocked = true;
    }

    private boolean needQuickShot() {
        boolean z = false;
        if (!this.mBlockQuickShot && !this.mIsImageCaptureIntent && CameraSettings.isCameraQuickShotEnable()) {
            if (enablePreviewAsThumbnail()) {
                int i = this.mModuleIndex;
                if ((i == 163 || i == 165) && getZoomRatio() == 1.0f && !isFrontCamera() && !CameraSettings.isUltraWideConfigOpen(this.mModuleIndex) && !CameraSettings.isMacroModeEnabled(this.mModuleIndex) && !this.mCamera2Device.isNeedFlashOn() && !CameraSettings.isUltraPixelOn() && !CameraSettings.isLiveShotOn()) {
                    BeautyValues beautyValues = this.mBeautyValues;
                    if (beautyValues == null || !beautyValues.isFaceBeautyOn()) {
                        z = true;
                    }
                }
            }
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("needQuickShot bRet:");
            sb.append(z);
            Log.d(str, sb.toString());
        }
        return z;
    }

    private boolean needShowThumbProgressImmediately() {
        return Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))) > 250000000 && this.mModuleIndex != 173;
    }

    /* access modifiers changed from: private */
    public void onBurstPictureTakenFinished(boolean z) {
        stopMultiSnap();
        ObservableEmitter observableEmitter = this.mBurstEmitter;
        if (observableEmitter != null) {
            observableEmitter.onComplete();
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
        StringBuilder sb = new StringBuilder();
        sb.append("mShutterLag = ");
        sb.append(this.mShutterLag);
        sb.append("ms");
        Log.v(str, sb.toString());
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

    static /* synthetic */ void pd() {
        TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertAiSceneSelector(8);
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
        this.mTotalJpegCallbackNum = CameraCapabilities.getBurstShootCount();
        this.mHandler.removeMessages(49);
        if (!is3ALocked()) {
            this.mFocusManager.onShutter();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x0118  */
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
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("prepareNormalCapture: mOrientation = ");
        sb.append(this.mOrientation);
        sb.append(", mJpegRotation = ");
        sb.append(this.mJpegRotation);
        Log.d(str, sb.toString());
        this.mCamera2Device.setJpegRotation(this.mJpegRotation);
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        this.mCamera2Device.setGpsLocation(currentLocation);
        this.mLocation = currentLocation;
        updateFrontMirror();
        updateBeauty();
        updateShotDetermine();
        updateCaptureTriggerFlow();
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getPrefix());
        sb2.append(Util.createJpegName(System.currentTimeMillis()));
        sb2.append(getSuffix());
        camera2Proxy.setShotSavePath(Storage.generateFilepath4Jpeg(sb2.toString()), !this.mMultiSnapStatus && (this.mEnableParallelSession || this.mEnableShot2Gallery));
        int i = this.mModuleIndex;
        if (i == 163 || i == 165 || i == 171 || i == 175) {
            boolean z2 = CameraSettings.isCameraQuickShotEnable() || CameraSettings.isCameraQuickShotAnimateEnable();
            boolean bc = DataRepository.dataItemFeature().bc();
            if (z2 || bc) {
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
            RecordState recordState = (RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onPrepare();
                recordState.onStart();
            }
            startCpuBoost();
            if (this.mSuperNightEventConsumer == null) {
                this.mSuperNightEventConsumer = new SuperNightEventConsumer();
            }
            this.mSuperNightDisposable = Observable.just(Integer.valueOf(300), Integer.valueOf(2000)).flatMap(new Function<Integer, ObservableSource<Integer>>() {
                public ObservableSource<Integer> apply(Integer num) throws Exception {
                    return Observable.just(num).delaySubscription((long) num.intValue(), TimeUnit.MILLISECONDS);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(this.mSuperNightEventConsumer);
        }
    }

    private void prepareSuperNightInCaptureMode() {
        if (CameraSettings.isSuperNightOn()) {
            String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
            if (this.mMainProtocol.isFaceExists(1) && componentValue == "3" && DataRepository.dataItemFeature().Jb()) {
                this.mCamera2Device.setForceBackSoftLight(true);
            }
            this.mShowSuperNightHint = true;
            ((TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).alertAiDetectTipHint(0, R.string.super_night_hint, -1);
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

    /* JADX WARNING: Removed duplicated region for block: B:100:0x01db  */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x01e5  */
    private void realConsumeAiSceneResult(int i, boolean z) {
        boolean z2 = false;
        if (i == 36) {
            i = 0;
        }
        if (this.mCurrentAiScene == i) {
            if (i == 0) {
                TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null && topAlert.getCurrentAiSceneLevel() == i) {
                    return;
                }
            } else {
                return;
            }
        }
        if (!isDoingAction() && isAlive() && !this.mActivity.isActivityPaused() && (!z || !this.isResetFromMutex)) {
            if (!z) {
                this.isResetFromMutex = false;
            }
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("consumeAiSceneResult: ");
            sb.append(i);
            sb.append("; isReset: ");
            sb.append(z);
            Log.d(str, sb.toString());
            TopAlert topAlert2 = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            ConfigChanges configChanges = (ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
            if (!isFrontCamera()) {
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
                String str2 = SupportedConfigFactory.CLOSE_BY_AI;
                if (i == 1) {
                    int parseInt = Integer.parseInt(CameraSettings.getSharpness());
                    if (parseInt < 6) {
                        parseInt++;
                    }
                    this.mCurrentAiScene = i;
                    configChanges.restoreAllMutexElement(str2);
                    this.mCamera2Device.setSharpness(parseInt);
                } else if (i == 10) {
                    configChanges.closeMutexElement(str2, 193);
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
                    configChanges.restoreAllMutexElement(str2);
                } else {
                    if (i != 37) {
                        if (i != 3) {
                            if (i == 4) {
                                this.mCamera2Device.setContrast(Integer.parseInt(CameraSettings.getContrast()));
                                this.mCurrentAiScene = i;
                                configChanges.restoreAllMutexElement(str2);
                                updateSuperResolution();
                            } else if (i == 7 || i == 8) {
                                this.mCurrentAiScene = i;
                                configChanges.restoreAllMutexElement(str2);
                            } else if (i != 34) {
                                if (i != 35) {
                                    switch (i) {
                                        case 25:
                                            trackAISceneChanged(this.mModuleIndex, 25);
                                            topAlert2.setAiSceneImageLevel(25);
                                            topAlert2.alertAiSceneSelector(8);
                                            setAiSceneEffect(25);
                                            this.mCurrentAiScene = i;
                                            updateHDRPreference();
                                            configChanges.restoreAllMutexElement(str2);
                                            this.mCamera2Device.setASD(false);
                                            resumePreviewInWorkThread();
                                            return;
                                        case 26:
                                        case 27:
                                        case 28:
                                        case 29:
                                        case 30:
                                        case 31:
                                            if (!DataRepository.dataItemFeature().Pc()) {
                                                configChanges.restoreAllMutexElement(str2);
                                                updatePreferenceInWorkThread(UpdateConstant.AI_SCENE_CONFIG);
                                                i = 0;
                                                break;
                                            } else {
                                                this.mCurrentAiScene = i;
                                                configChanges.restoreAllMutexElement(str2);
                                                break;
                                            }
                                        default:
                                            updateHDRPreference();
                                            configChanges.restoreAllMutexElement(str2);
                                            updatePreferenceInWorkThread(UpdateConstant.AI_SCENE_CONFIG);
                                            break;
                                    }
                                } else {
                                    if (showMoonMode(false)) {
                                        topAlert2.setAiSceneImageLevel(i);
                                        this.mCamera2Device.setASD(true);
                                        trackAISceneChanged(this.mModuleIndex, i);
                                        this.mCurrentAiScene = i;
                                        return;
                                    }
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
                            }
                        } else if (DataRepository.dataItemFeature().Jb() && isBackCamera() && !MiAlgoAsdSceneProfile.isAlreadyTip()) {
                            MiAlgoAsdSceneProfile.setTipEnable(MiAlgoAsdSceneProfile.AI_SCENE_MODE_FOOD, true);
                            updateTipMessage(20, R.string.recommend_soft_light_tip, 2);
                        }
                    }
                    this.mCurrentAiScene = i;
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

    private void releaseEffectProcessor() {
    }

    private void resetAiSceneInHdrOrFlashOn() {
        if (this.mAiSceneEnabled && !this.isResetFromMutex) {
            int i = this.mCurrentAiScene;
            if (i != 0) {
                if (i == -1 || i == 10) {
                    this.mHandler.post(new Runnable() {
                        public void run() {
                            Camera2Module.this.consumeAiSceneResult(0, true);
                            Camera2Module.this.isResetFromMutex = true;
                        }
                    });
                }
            }
        }
    }

    private void resetAsdSceneInHdrOrFlashChange() {
        if (b.Si() && isFrontCamera()) {
            int i = this.mCurrentAsdScene;
            if (i != -1 && i == 9) {
                this.mHandler.post(new Runnable() {
                    public void run() {
                        Camera2Module.this.consumeAsdSceneResult(-1);
                    }
                });
            }
        }
    }

    private void resetScreenOn() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeMessages(17);
            this.mHandler.removeMessages(2);
        }
    }

    private void resumePreviewInWorkThread() {
        updatePreferenceInWorkThread(new int[0]);
    }

    private void setAiSceneEffect(int i) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("setAiSceneEffect: ");
        sb.append(i);
        Log.d(str, sb.toString());
        if (!DataRepository.dataItemFeature().Ic() || !CameraSettings.isBackCamera() || i != 25) {
            if (CameraSettings.isFrontCamera() || isPortraitMode()) {
                if (i != 0) {
                    Log.d(TAG, "setAiSceneEffect: front camera or portrait mode nonsupport!");
                    return;
                } else if (CameraSettings.getPortraitLightingPattern() != 0) {
                    Log.d(TAG, "setAiSceneEffect: scene = 0 but portrait lighting is on...");
                    return;
                }
            }
            ArrayList filterInfo = EffectController.getInstance().getFilterInfo(5);
            if (i < 0 || i > filterInfo.size()) {
                Log.e(TAG, "setAiSceneEffect: scene unknown!");
                return;
            }
            int shaderEffect = CameraSettings.getShaderEffect();
            if (FilterInfo.getCategory(shaderEffect) == 5 || shaderEffect == FilterInfo.FILTER_ID_NONE) {
                int id = ((FilterInfo) filterInfo.get(i)).getId();
                EffectController.getInstance().setAiSceneEffect(id);
                this.mHasAiSceneFilterEffect = id != FilterInfo.FILTER_ID_NONE;
                if (EffectController.getInstance().hasEffect()) {
                    prepareEffectProcessor(id);
                }
                return;
            }
            return;
        }
        Log.d(TAG, "supportAi30: AI 3.0 back camera in HUMAN SCENE not apply filter!");
    }

    private void setEffectFilter(int i) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("setEffectFilter: ");
        sb.append(i);
        Log.d(str, sb.toString());
        EffectController.getInstance().setEffect(i);
        this.mFilterId = i;
        CircularMediaRecorder circularMediaRecorder = this.mCircularMediaRecorder;
        if (circularMediaRecorder != null) {
            circularMediaRecorder.setFilterId(i);
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
            StringBuilder sb = new StringBuilder();
            sb.append("setLightingEffect: ");
            sb.append(portraitLightingPattern);
            Log.d(str, sb.toString());
            int id = ((FilterInfo) EffectController.getInstance().getFilterInfo(6).get(portraitLightingPattern)).getId();
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
                    GlobalConstant.sCameraSetupScheduler.scheduleDirect(new b(this));
                } else {
                    updatePreferenceInWorkThread(35);
                }
            }
            CircularMediaRecorder circularMediaRecorder = this.mCircularMediaRecorder;
            if (circularMediaRecorder != null) {
                circularMediaRecorder.setOrientationHint(this.mOrientationCompensation);
            }
        }
    }

    private void setPictureOrientation() {
        this.mShootRotation = this.mActivity.getSensorStateManager().isDeviceLying() ? (float) this.mOrientation : this.mDeviceRotation;
        int i = this.mOrientation;
        if (i == -1) {
            i = 0;
        }
        this.mShootOrientation = i;
    }

    private void setPortraitSuccessHintVisible(int i) {
        ((BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).setPortraitHintVisible(i);
    }

    private boolean setSceneMode(String str) {
        int parseInt = Util.parseInt(str, -1);
        this.mCamera2Device.setSceneMode(parseInt);
        if (!Util.isSupported(parseInt, this.mCameraCapabilities.getSupportedSceneModes())) {
            return false;
        }
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("sceneMode=");
        sb.append(str);
        Log.d(str2, sb.toString());
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
        if (this.mMultiSnapStatus || this.mModuleIndex == 165 || CameraSettings.isGradienterOn() || CameraSettings.getShaderEffect() != FilterInfo.FILTER_ID_NONE || this.mHasAiSceneFilterEffect || CameraSettings.isTiltShiftOn() || DataRepository.dataItemFeature().Uc()) {
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
        } else {
            this.mCaptureWaterMarkStr = null;
            this.mCamera2Device.setTimeWaterMarkEnable(false);
        }
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
        if (CameraSettings.isMacroModeEnabled(this.mModuleIndex) && !DataRepository.dataItemFeature().ad()) {
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
        if (!this.mIsMoonMode || DataRepository.dataItemFeature().ac()) {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy == null || this.mCameraCapabilities == null || !camera2Proxy.useLegacyFlashStrategy() || !this.mCamera2Device.isNeedFlashOn() || !this.mCameraCapabilities.isFlashSupported()) {
                if (this.mMutexModePicker.isHdr() || this.mShouldDoMFNR || this.mMutexModePicker.isSuperResolution() || CameraSettings.isGroupShotOn()) {
                    z = true;
                }
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("shouldDoMultiFrameCapture: ");
                sb.append(z);
                Log.d(str, sb.toString());
                return z;
            }
            Log.d(TAG, "shouldDoMultiFrameCapture: return false in case of flash");
            return false;
        }
        Log.d(TAG, "shouldDoMultiFrameCapture: return false in moon mode");
        return false;
    }

    private boolean showMoonMode(boolean z) {
        if (!CameraSettings.isUltraWideConfigOpen(getModuleIndex()) && !CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
            TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                this.mEnteringMoonMode = true;
                topAlert.alertMoonModeSelector(0, z);
                if (!z) {
                    updateMoonNight();
                } else {
                    updateMoon(true);
                }
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("(moon_mode) show moon mode,button check status:");
                sb.append(z);
                Log.d(str, sb.toString());
                return true;
            }
        }
        return false;
    }

    private void showPostCaptureAlert() {
        enableCameraControls(false);
        this.mFocusManager.removeMessages();
        stopFaceDetection(true);
        pausePreview();
        this.mMainProtocol.setEffectViewVisible(false);
        ((BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).delegateEvent(6);
        resetMetaDataManager();
    }

    private void startCount(final int i, int i2) {
        if (checkShutterCondition()) {
            setTriggerMode(i2);
            tryRemoveCountDownMessage();
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("startCount: ");
            sb.append(i);
            Log.d(str, sb.toString());
            Observable.interval(1, TimeUnit.SECONDS).take((long) i).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new Observer<Long>() {
                public void onComplete() {
                    Camera2Module.this.tryRemoveCountDownMessage();
                    Camera2Module.this.onShutterButtonFocus(true, 3);
                    Camera2Module camera2Module = Camera2Module.this;
                    camera2Module.startNormalCapture(camera2Module.getTriggerMode());
                    Camera2Module.this.onShutterButtonFocus(false, 0);
                    TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
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
                    Camera2Module.this.mCountdownDisposable = disposable;
                    Camera2Module.this.playCameraSound(7);
                    TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    if (topAlert != null) {
                        AndroidSchedulers.mainThread().scheduleDirect(new d(topAlert), 120, TimeUnit.MILLISECONDS);
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
        BaseBoostFramework baseBoostFramework = this.mCpuBoost;
        if (baseBoostFramework != null) {
            baseBoostFramework.startBoost();
        }
    }

    /* access modifiers changed from: private */
    public void startLensActivity() {
        if (b.Tc() || !Util.isGlobalVersion()) {
            Log.d(TAG, "start ai lens");
            try {
                Intent intent = new Intent();
                intent.setAction("android.media.action.XIAOAI_CONTROL");
                intent.setPackage(CameraSettings.AI_LENS_PACKAGE);
                intent.putExtra("preview_width", this.mPreviewSize.width);
                intent.putExtra("preview_height", this.mPreviewSize.height);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
            } catch (Exception e2) {
                Log.e(TAG, "onClick: occur a exception", e2);
            }
        } else {
            LensApi lensApi = this.mLensApi;
            if (lensApi != null && this.mLensStatus == 0) {
                lensApi.launchLensActivity(this.mActivity, 0);
            }
        }
    }

    private void startLiveShotAnimation() {
        synchronized (this.mCircularMediaRecorderStateLock) {
            if (!(this.mCircularMediaRecorder == null || this.mHandler == null)) {
                this.mHandler.post(new Runnable() {
                    public void run() {
                        TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
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
        StringBuilder sb = new StringBuilder();
        sb.append("startNormalCapture mode -> ");
        sb.append(i);
        Log.d(str, sb.toString());
        prepareNormalCapture();
        if (!CameraSettings.isGroupShotOn() || isParallelSessionEnable()) {
            this.mHandler.sendEmptyMessageDelayed(50, calculateTimeout(this.mModuleIndex));
            this.mCamera2Device.setQuickShotAnimation(this.mQuickShotAnimateEnable);
            if (DataRepository.dataItemFeature().Zb()) {
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
            if (isFrontCamera() && DataRepository.dataItemFeature().bc() && 32775 != this.mOperatingMode) {
                this.mCamera2Device.registerCaptureCallback(new c(this));
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
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null && this.mMultiSnapStatus) {
                camera2Proxy.captureAbortBurst();
                this.mMultiSnapStatus = false;
            }
            int i = !this.mMutexModePicker.isUbiFocus() ? this.mReceivedJpegCallbackNum : 1;
            boolean z = !this.mMutexModePicker.isUbiFocus();
            trackGeneralInfo(i, z);
            trackPictureTaken(i, z, this.mLocation != null, getCurrentAiSceneName(), this.mEnteringMoonMode, this.mIsMoonMode);
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
            if (!DataRepository.dataItemFeature().gd() && this.mUltraWideAELocked) {
                String focusMode = CameraSettings.getFocusMode();
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("unlockAEAF: focusMode = ");
                sb.append(focusMode);
                Log.d(str, sb.toString());
                setFocusMode(focusMode);
                this.mUltraWideAELocked = false;
            }
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(false);
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

    private void updateAiScene() {
        FunctionParseAiScene functionParseAiScene = this.mFunctionParseAiScene;
        if (functionParseAiScene != null) {
            functionParseAiScene.resetScene();
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
        String str = !b.Oh() ? this.mCamera2Device.isBokehEnabled() ? DataRepository.dataItemFeature().Ba() > 0 ? Util.ALGORITHM_NAME_SOFT_PORTRAIT_ENCRYPTED : Util.ALGORITHM_NAME_SOFT_PORTRAIT : isPortraitMode() ? Util.ALGORITHM_NAME_PORTRAIT : this.mMutexModePicker.getAlgorithmName() : null;
        this.mAlgorithmName = str;
    }

    private void updateBeauty() {
        int i = this.mModuleIndex;
        if (i == 163 || i == 165 || i == 171) {
            if (this.mBeautyValues == null) {
                this.mBeautyValues = new BeautyValues();
            }
            CameraSettings.initBeautyValues(this.mBeautyValues, this.mModuleIndex);
            if (!DataRepository.dataItemConfig().getComponentConfigBeauty().isClosed() && this.mCurrentAiScene == 25 && !isFaceBeautyOn(this.mBeautyValues)) {
                ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
                if (componentRunningShine.supportBeautyLevel()) {
                    this.mBeautyValues.mBeautyLevel = BeautyConstant.LEVEL_LOW;
                } else if (componentRunningShine.supportSmoothLevel()) {
                    this.mBeautyValues.mBeautySkinSmooth = 10;
                }
                Log.d(TAG, String.format(Locale.ENGLISH, "Human scene mode detected, auto set beauty level from %s to %s", new Object[]{BeautyConstant.LEVEL_CLOSE, this.mBeautyValues.mBeautyLevel}));
            }
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("updateBeauty(): ");
            sb.append(this.mBeautyValues);
            Log.d(str, sb.toString());
            this.mCamera2Device.setBeautyValues(this.mBeautyValues);
            this.mIsBeautyBodySlimOn = this.mBeautyValues.isBeautyBodyOn();
            updateFaceAgeAnalyze();
        }
    }

    private void updateBokeh() {
        ComponentConfigBokeh componentBokeh = DataRepository.dataItemConfig().getComponentBokeh();
        if (!ModuleManager.isPortraitModule()) {
            if (!"on".equals(componentBokeh.getComponentValue(this.mModuleIndex))) {
                this.mCamera2Device.setMiBokeh(false);
                this.mCamera2Device.setRearBokehEnable(false);
                return;
            }
        }
        if (isSingleCamera()) {
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
        if (this.mIsGoogleLensAvailable || scanQRCodeEnabled() || b.Ob()) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("updateDecodePreview: PreviewDecodeManager AlgorithmPreviewSize = ");
            sb.append(this.mCamera2Device.getAlgorithmPreviewSize());
            Log.d(str, sb.toString());
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
        } else if (!isPreviewThumbnailWhenFlash()) {
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
        if (isFrontCamera() && DataRepository.dataItemFeature().Mc()) {
            this.mCamera2Device.setEyeLight(Integer.parseInt(CameraSettings.getEyeLightType()));
        }
    }

    private void updateFNumber() {
        if (DataRepository.dataItemFeature().isSupportBokehAdjust()) {
            this.mCamera2Device.setFNumber(CameraSettings.readFNumber());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x006b  */
    private void updateFace() {
        boolean z;
        boolean z2;
        MainContentProtocol mainContentProtocol;
        if (this.mMultiSnapStatus || this.mMutexModePicker.isUbiFocus()) {
            z2 = false;
        } else if (CameraSettings.isMagicMirrorOn() || CameraSettings.isPortraitModeBackOn() || CameraSettings.isGroupShotOn() || CameraSettings.showGenderAge()) {
            z2 = true;
            z = true;
            mainContentProtocol = this.mMainProtocol;
            if (mainContentProtocol != null) {
                mainContentProtocol.setSkipDrawFace(!z2 || !z);
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
            if (CameraSettings.isGradienterOn() || CameraSettings.isTiltShiftOn()) {
                z = false;
                mainContentProtocol = this.mMainProtocol;
                if (mainContentProtocol != null) {
                }
                if (z2) {
                }
            }
        }
        z = true;
        mainContentProtocol = this.mMainProtocol;
        if (mainContentProtocol != null) {
        }
        if (z2) {
        }
    }

    private void updateFaceAgeAnalyze() {
        boolean z;
        this.mIsGenderAgeOn = DataRepository.dataItemRunning().isSwitchOn("pref_camera_show_gender_age_key");
        if (this.mIsGenderAgeOn) {
            z = true;
        } else {
            BeautyValues beautyValues = this.mBeautyValues;
            z = beautyValues != null ? isFaceBeautyOn(beautyValues) : false;
        }
        this.mCamera2Device.setFaceAgeAnalyze(z);
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
        Camera camera = this.mActivity;
        if (camera != null && !camera.isActivityPaused() && isAlive()) {
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
        String str = "manual";
        String focusMode = this.mIsMoonMode ? this.mFocusManager.setFocusMode(str) : this.mFocusManager.setFocusMode(CameraSettings.getFocusMode());
        setFocusMode(focusMode);
        if (CameraSettings.isFocusModeSwitching() && isBackCamera()) {
            CameraSettings.setFocusModeSwitching(false);
            this.mFocusManager.resetFocusStateIfNeeded();
        }
        if (focusMode.equals(str)) {
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
        if (b.Bn && CameraSettings.isPortraitModeBackOn()) {
            this.mCamera2Device.setFpsRange(new Range(Integer.valueOf(30), Integer.valueOf(30)));
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
        this.mCamera2Device.setJpegQuality(BaseModule.getJpegQuality(this.mMultiSnapStatus));
    }

    private void updateJpegThumbnailSize() {
        CameraSize jpegThumbnailSize = getJpegThumbnailSize();
        this.mCamera2Device.setJpegThumbnailSize(jpegThumbnailSize);
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("thumbnailSize=");
        sb.append(jpegThumbnailSize);
        Log.d(str, sb.toString());
    }

    private void updateLiveShot() {
        if (DataRepository.dataItemFeature().Sb() && this.mModuleIndex == 163) {
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

    private void updateMfnr(boolean z) {
        boolean z2 = false;
        if (!isUseSwMfnr() && z && this.mModuleIndex != 167 && ((!b.An || isDualCamera()) && this.mMutexModePicker.isNormal() && !CameraSettings.isGroupShotOn() && ((!isFrontCamera() || enableFrontMFNR()) && (DataRepository.dataItemFeature().kd() || getZoomRatio() == 1.0f || isUltraWideBackCamera() || isZoomRatioBetweenUltraAndWide())))) {
            z2 = true;
        }
        if (this.mCamera2Device != null) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("setMfnr to ");
            sb.append(z2);
            Log.d(str, sb.toString());
            this.mCamera2Device.setMfnr(z2);
        }
    }

    private void updateMute() {
    }

    private void updateNormalWideLDC() {
        this.mCamera2Device.setNormalWideLDC(shouldApplyNormalWideLDC());
    }

    private void updateOIS() {
        boolean z;
        boolean z2 = isDualCamera() && !this.mCameraCapabilities.isTeleOISSupported() && getZoomRatio() > 1.0f;
        boolean isPortraitModeBackOn = CameraSettings.isPortraitModeBackOn();
        if (this.mModuleIndex == 167) {
            if (Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))) > 1000000000) {
                z = true;
                if (!z2 || isPortraitModeBackOn || z) {
                    this.mCamera2Device.setEnableOIS(false);
                } else {
                    this.mCamera2Device.setEnableOIS(true);
                    return;
                }
            }
        }
        z = false;
        if (!z2) {
        }
        this.mCamera2Device.setEnableOIS(false);
    }

    private void updateOutputSize(CameraSize cameraSize) {
        if (165 == this.mModuleIndex) {
            int i = cameraSize.width;
            int i2 = cameraSize.height;
            if (i <= i2) {
                i2 = i;
            }
            this.mOutPutSize = new CameraSize(i2, i2);
            return;
        }
        this.mOutPutSize = cameraSize;
    }

    private void updatePictureAndPreviewSize() {
        int i = this.mEnableParallelSession ? 35 : 256;
        boolean isIn3OrMoreSatMode = isIn3OrMoreSatMode();
        if (isIn3OrMoreSatMode) {
            this.mUltraWidePictureSize = getBestPictureSize(this.mUltraCameraCapabilities.getSupportedOutputSize(i));
            if (this.mWideCameraCapabilities == null) {
                this.mWideCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getMainBackCameraId());
                this.mWideCameraCapabilities.setOperatingMode(this.mOperatingMode);
            }
            this.mWidePictureSize = getBestPictureSize(this.mWideCameraCapabilities.getSupportedOutputSize(i));
            if (this.mTeleCameraCapabilities == null) {
                this.mTeleCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getAuxCameraId());
                this.mTeleCameraCapabilities.setOperatingMode(this.mOperatingMode);
            }
            this.mTelePictureSize = getBestPictureSize(this.mTeleCameraCapabilities.getSupportedOutputSize(i));
            if (DataRepository.dataItemFeature().ec()) {
                if (this.mUltraTeleCameraCapabilities == null) {
                    this.mUltraTeleCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getStandaloneCameraId());
                    this.mUltraTeleCameraCapabilities.setOperatingMode(this.mOperatingMode);
                }
                this.mUltraTelePictureSize = getBestPictureSize(this.mUltraTeleCameraCapabilities.getSupportedOutputSize(i));
                this.mCamera2Device.setUltraTelePictureSize(this.mUltraTelePictureSize);
                Log.d(TAG, String.format(Locale.ENGLISH, "ultraWideSize: %s, wideSize: %s, teleSize: %s, ultraTeleSize:%s", new Object[]{this.mUltraWidePictureSize, this.mWidePictureSize, this.mTelePictureSize, this.mUltraTelePictureSize}));
            } else {
                Log.d(TAG, String.format(Locale.ENGLISH, "ultraWideSize: %s, wideSize: %s, teleSize: %s", new Object[]{this.mUltraWidePictureSize, this.mWidePictureSize, this.mTelePictureSize}));
            }
            this.mCamera2Device.setUltraWidePictureSize(this.mUltraWidePictureSize);
            this.mCamera2Device.setWidePictureSize(this.mWidePictureSize);
            this.mCamera2Device.setTelePictureSize(this.mTelePictureSize);
            this.mPictureSize = getSatPictureSize();
        } else {
            if (isSensorRawStreamRequired()) {
                List supportedOutputSize = this.mCameraCapabilities.getSupportedOutputSize(32);
                if (this.mModuleIndex != 167) {
                    this.mSensorRawImageSize = getBestPictureSize(supportedOutputSize);
                } else if (supportedOutputSize == null || supportedOutputSize.size() == 0) {
                    Log.w(TAG, "The supported raw size list return from hal is null!");
                } else {
                    this.mSensorRawImageSize = getBestPictureSize(supportedOutputSize, 1.3333333f);
                }
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("The best sensor raw image size: ");
                sb.append(this.mSensorRawImageSize);
                Log.d(str, sb.toString());
            }
            if (!this.mEnableParallelSession || !isPortraitMode()) {
                CameraSize bestPictureSize = getBestPictureSize(this.mCameraCapabilities.getSupportedOutputSize(i));
                if (b.In && getOperatingMode() == 36867) {
                    bestPictureSize = new CameraSize(bestPictureSize.width / 2, bestPictureSize.height / 2);
                }
                if (b.isMTKPlatform() && isFrontCamera()) {
                    bestPictureSize = new CameraSize(bestPictureSize.width / 2, bestPictureSize.height / 2);
                }
                this.mPictureSize = bestPictureSize;
            } else {
                updatePortraitPictureSize(i);
            }
        }
        List supportedOutputSize2 = this.mCameraCapabilities.getSupportedOutputSize(SurfaceTexture.class);
        CameraSize cameraSize = this.mPictureSize;
        double previewAspectRatio = (double) CameraSettings.getPreviewAspectRatio(cameraSize.width, cameraSize.height);
        this.mPreviewSize = Util.getOptimalPreviewSize(false, this.mBogusCameraId, supportedOutputSize2, previewAspectRatio);
        this.mCamera2Device.setPreviewSize(this.mPreviewSize);
        if (this.mIsGoogleLensAvailable) {
            this.mCamera2Device.setAlgorithmPreviewSize(Util.getAlgorithmPreviewSize(supportedOutputSize2, previewAspectRatio, this.mPreviewSize));
        } else {
            this.mCamera2Device.setAlgorithmPreviewSize(this.mPreviewSize);
        }
        this.mCamera2Device.setAlgorithmPreviewFormat(35);
        if (this.mEnableParallelSession) {
            List supportedOutputSize3 = this.mCameraCapabilities.getSupportedOutputSize(256);
            if (this.mModuleIndex == 165) {
                this.mOutPutSize = PictureSizeManager.getBestSquareSize(supportedOutputSize3);
                if (this.mOutPutSize.getWidth() == 0) {
                    CameraSize cameraSize2 = this.mPictureSize;
                    int i2 = cameraSize2.width;
                    int i3 = cameraSize2.height;
                    if (i2 <= i3) {
                        i3 = i2;
                    }
                    throw new RuntimeException(String.format(Locale.ENGLISH, "size %dx%d is not supported!", new Object[]{Integer.valueOf(i3), Integer.valueOf(i3)}));
                }
            } else if (isIn3OrMoreSatMode) {
                this.mOutPutSize = this.mPictureSize;
            } else {
                this.mOutPutSize = PictureSizeManager.getBestPictureSize(supportedOutputSize3);
            }
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("output picture size: ");
            sb2.append(this.mOutPutSize);
            Log.d(str2, sb2.toString());
        }
        Log.d(TAG, String.format(Locale.ENGLISH, "updateSize: picture size is: %s, preview size is: %s, sensor raw image size is: %s", new Object[]{this.mPictureSize, this.mPreviewSize, this.mSensorRawImageSize}));
        CameraSize cameraSize3 = this.mPreviewSize;
        updateCameraScreenNailSize(cameraSize3.width, cameraSize3.height);
        checkDisplayOrientation();
        CameraSize cameraSize4 = this.mPreviewSize;
        setVideoSize(cameraSize4.width, cameraSize4.height);
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
            z2 = DataRepository.dataItemFeature().Va();
            z = DataRepository.dataItemRunning().isSwitchOn("pref_ultra_wide_bokeh_enabled");
            i2 = z ? Camera2DataContainer.getInstance().getUltraWideCameraId() : Camera2DataContainer.getInstance().getAuxCameraId();
        } else if (isDualFrontCamera()) {
            i2 = Camera2DataContainer.getInstance().getAuxFrontCameraId();
            z2 = true;
            z = false;
        } else {
            i2 = -1;
            z2 = false;
            z = false;
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("BS = ");
        sb.append(z2);
        sb.append(" UW = ");
        sb.append(z);
        sb.append(" id = ");
        sb.append(i2);
        Log.d(str, sb.toString());
        PictureSizeManager.initializeLimitWidth(this.mCameraCapabilities.getSupportedOutputSize(i), DataRepository.dataItemFeature().h(isBackCamera()), this.mModuleIndex, this.mBogusCameraId);
        CameraSize bestPictureSize = PictureSizeManager.getBestPictureSize();
        if (b.In && getOperatingMode() == 36867) {
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

    private void updateSaturation() {
        this.mCamera2Device.setSaturation(Integer.parseInt(getString(R.string.pref_camera_saturation_default)));
    }

    private void updateScene() {
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        String str = "-1";
        if (this.mMutexModePicker.isSceneHdr()) {
            this.mSceneMode = CameraScene.HDR;
        } else if (!dataItemRunning.isSwitchOn("pref_camera_scenemode_setting_key")) {
            this.mSceneMode = str;
        } else {
            this.mSceneMode = dataItemRunning.getComponentRunningSceneValue().getComponentValue(this.mModuleIndex);
        }
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("sceneMode=");
        sb.append(this.mSceneMode);
        sb.append(" mutexMode=");
        sb.append(this.mMutexModePicker.getMutexMode());
        Log.d(str2, sb.toString());
        if (!setSceneMode(this.mSceneMode)) {
            this.mSceneMode = str;
        }
        this.mHandler.post(new Runnable() {
            public void run() {
                Camera2Module.this.updateSceneModeUI();
            }
        });
        if ("0".equals(this.mSceneMode) || str.equals(this.mSceneMode)) {
            this.mFocusManager.overrideFocusMode(null);
        } else {
            this.mFocusManager.overrideFocusMode(AutoFocus.LEGACY_CONTINUOUS_PICTURE);
        }
    }

    /* access modifiers changed from: private */
    public void updateSceneModeUI() {
        TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (DataRepository.dataItemRunning().isSwitchOn("pref_camera_scenemode_setting_key")) {
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

    /* JADX WARNING: Code restructure failed: missing block: B:75:0x00d3, code lost:
        if (r9.mModuleIndex == 167) goto L_0x00df;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x00dc, code lost:
        if (shouldDoMultiFrameCapture() != false) goto L_0x00a6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x005e  */
    private void updateShotDetermine() {
        boolean z;
        int i;
        int i2 = 1;
        if (this.mModuleIndex == 171) {
            if (!isBackCamera()) {
                z = DataRepository.dataItemFeature().Yc();
            } else if (b.Hi() || DataRepository.dataItemFeature().Xc()) {
                z = true;
            }
            this.mEnableParallelSession = isParallelSessionEnable();
            if (this.mIsImageCaptureIntent) {
                this.mEnableShot2Gallery = !this.mEnableParallelSession && DataRepository.dataItemFeature()._c() && enablePreviewAsThumbnail();
                int i3 = this.mModuleIndex;
                if (!(i3 == 163 || i3 == 165 || i3 == 167)) {
                    if (i3 != 171) {
                        if (i3 == 173) {
                            if (isCurrentRawDomainBasedSuperNight()) {
                                i = 10;
                            }
                            i2 = 0;
                            String str = TAG;
                            StringBuilder sb = new StringBuilder();
                            sb.append("enableParallel=");
                            sb.append(this.mEnableParallelSession);
                            sb.append(" mEnableShot2Gallery=");
                            sb.append(this.mEnableShot2Gallery);
                            sb.append(" shotType=");
                            sb.append(i2);
                            Log.d(str, sb.toString());
                            this.mCamera2Device.setShotType(i2);
                            this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                        } else if (i3 != 175) {
                            this.mEnableParallelSession = false;
                            return;
                        }
                    } else if (this.mEnableParallelSession) {
                        if (!shouldDoMultiFrameCapture()) {
                            if (isDualFrontCamera() || isDualCamera() || isBokehUltraWideBackCamera()) {
                                if (z) {
                                    i = 6;
                                }
                                i2 = 5;
                                String str2 = TAG;
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("enableParallel=");
                                sb2.append(this.mEnableParallelSession);
                                sb2.append(" mEnableShot2Gallery=");
                                sb2.append(this.mEnableShot2Gallery);
                                sb2.append(" shotType=");
                                sb2.append(i2);
                                Log.d(str2, sb2.toString());
                                this.mCamera2Device.setShotType(i2);
                                this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                            }
                            if (z) {
                                i = 7;
                            }
                            i2 = 5;
                            String str22 = TAG;
                            StringBuilder sb22 = new StringBuilder();
                            sb22.append("enableParallel=");
                            sb22.append(this.mEnableParallelSession);
                            sb22.append(" mEnableShot2Gallery=");
                            sb22.append(this.mEnableShot2Gallery);
                            sb22.append(" shotType=");
                            sb22.append(i2);
                            Log.d(str22, sb22.toString());
                            this.mCamera2Device.setShotType(i2);
                            this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                        }
                        i2 = 8;
                        String str222 = TAG;
                        StringBuilder sb222 = new StringBuilder();
                        sb222.append("enableParallel=");
                        sb222.append(this.mEnableParallelSession);
                        sb222.append(" mEnableShot2Gallery=");
                        sb222.append(this.mEnableShot2Gallery);
                        sb222.append(" shotType=");
                        sb222.append(i2);
                        Log.d(str222, sb222.toString());
                        this.mCamera2Device.setShotType(i2);
                        this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                    } else {
                        if (z) {
                            i = 2;
                        }
                        i2 = 0;
                        String str2222 = TAG;
                        StringBuilder sb2222 = new StringBuilder();
                        sb2222.append("enableParallel=");
                        sb2222.append(this.mEnableParallelSession);
                        sb2222.append(" mEnableShot2Gallery=");
                        sb2222.append(this.mEnableShot2Gallery);
                        sb2222.append(" shotType=");
                        sb2222.append(i2);
                        Log.d(str2222, sb2222.toString());
                        this.mCamera2Device.setShotType(i2);
                        this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                    }
                }
                if (!this.mEnableParallelSession) {
                    if (this.mConfigRawStream) {
                    }
                    i2 = 0;
                    String str22222 = TAG;
                    StringBuilder sb22222 = new StringBuilder();
                    sb22222.append("enableParallel=");
                    sb22222.append(this.mEnableParallelSession);
                    sb22222.append(" mEnableShot2Gallery=");
                    sb22222.append(this.mEnableShot2Gallery);
                    sb22222.append(" shotType=");
                    sb22222.append(i2);
                    Log.d(str22222, sb22222.toString());
                    this.mCamera2Device.setShotType(i2);
                    this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                }
            } else if (this.mEnableParallelSession) {
                i2 = -5;
                if (isDualFrontCamera() || isDualCamera() || isBokehUltraWideBackCamera()) {
                    if (z) {
                        i = -7;
                    }
                    String str222222 = TAG;
                    StringBuilder sb222222 = new StringBuilder();
                    sb222222.append("enableParallel=");
                    sb222222.append(this.mEnableParallelSession);
                    sb222222.append(" mEnableShot2Gallery=");
                    sb222222.append(this.mEnableShot2Gallery);
                    sb222222.append(" shotType=");
                    sb222222.append(i2);
                    Log.d(str222222, sb222222.toString());
                    this.mCamera2Device.setShotType(i2);
                    this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                }
                if (z) {
                    i = -6;
                }
                String str2222222 = TAG;
                StringBuilder sb2222222 = new StringBuilder();
                sb2222222.append("enableParallel=");
                sb2222222.append(this.mEnableParallelSession);
                sb2222222.append(" mEnableShot2Gallery=");
                sb2222222.append(this.mEnableShot2Gallery);
                sb2222222.append(" shotType=");
                sb2222222.append(i2);
                Log.d(str2222222, sb2222222.toString());
                this.mCamera2Device.setShotType(i2);
                this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
            } else {
                i = z ? -3 : -2;
            }
            i2 = i;
            String str22222222 = TAG;
            StringBuilder sb22222222 = new StringBuilder();
            sb22222222.append("enableParallel=");
            sb22222222.append(this.mEnableParallelSession);
            sb22222222.append(" mEnableShot2Gallery=");
            sb22222222.append(this.mEnableShot2Gallery);
            sb22222222.append(" shotType=");
            sb22222222.append(i2);
            Log.d(str22222222, sb22222222.toString());
            this.mCamera2Device.setShotType(i2);
            this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
        }
        z = false;
        this.mEnableParallelSession = isParallelSessionEnable();
        if (this.mIsImageCaptureIntent) {
        }
        i2 = i;
        String str222222222 = TAG;
        StringBuilder sb222222222 = new StringBuilder();
        sb222222222.append("enableParallel=");
        sb222222222.append(this.mEnableParallelSession);
        sb222222222.append(" mEnableShot2Gallery=");
        sb222222222.append(this.mEnableShot2Gallery);
        sb222222222.append(" shotType=");
        sb222222222.append(i2);
        Log.d(str222222222, sb222222222.toString());
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
                if (DataRepository.dataItemRunning().getComponentUltraPixel().isRearTESTPSwitchOn()) {
                    Camera2Proxy camera2Proxy = this.mCamera2Device;
                    if (camera2Proxy != null) {
                        camera2Proxy.setSuperResolution(true);
                    }
                } else {
                    Camera2Proxy camera2Proxy2 = this.mCamera2Device;
                    if (camera2Proxy2 != null) {
                        camera2Proxy2.setSuperResolution(false);
                    }
                }
                return;
            }
            if (getZoomRatio() <= 1.0f) {
                if (!DataRepository.dataItemRunning().isSwitchOn("pref_camera_super_resolution_key")) {
                    if (this.mMutexModePicker.isSuperResolution()) {
                        this.mMutexModePicker.resetMutexMode();
                    } else {
                        Camera2Proxy camera2Proxy3 = this.mCamera2Device;
                        if (camera2Proxy3 != null) {
                            camera2Proxy3.setSuperResolution(false);
                        }
                    }
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
        StringBuilder sb = new StringBuilder();
        sb.append("setSwMfnr to ");
        sb.append(isUseSwMfnr);
        Log.d(str, sb.toString());
        this.mCamera2Device.setSwMfnr(isUseSwMfnr);
    }

    private void updateThumbProgress(boolean z) {
        ActionProcessing actionProcessing = (ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
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

    public /* synthetic */ void a(float f2, float f3, int i, int i2, int i3) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onOptionClick: which = ");
        sb.append(i3);
        Log.d(str, sb.toString());
        CameraStatUtil.trackGoogleLensPickerValue(i3 == 0);
        String str2 = CameraSettings.KEY_LONG_PRESS_VIEWFINDER;
        if (i3 == 0) {
            DataRepository.dataItemGlobal().editor().putBoolean(CameraSettings.KEY_GOOGLE_LENS_OOBE, true).apply();
            DataRepository.dataItemGlobal().editor().putString(str2, getString(R.string.pref_camera_long_press_viewfinder_default)).apply();
            LensAgent.getInstance().onFocusChange(2, f2 / ((float) Util.sWindowWidth), f3 / ((float) Util.sWindowHeight));
        } else if (i3 == 1) {
            DataRepository.dataItemGlobal().editor().putString(str2, getString(R.string.pref_camera_long_press_viewfinder_lock_ae_af)).apply();
            DataRepository.dataItemGlobal().editor().putBoolean(CameraSettings.KEY_EN_FIRST_CHOICE_LOCK_AE_AF_TOAST, true).apply();
            onSingleTapUp(i, i2, true);
            if (this.m3ALockSupported) {
                lockAEAF();
            }
            this.mMainProtocol.performHapticFeedback(0);
        }
    }

    public /* synthetic */ void a(CameraHardwareFace[] cameraHardwareFaceArr) {
        if (cameraHardwareFaceArr.length > 0) {
            if (!this.mIsFaceConflict) {
                this.mIsFaceConflict = true;
                showOrHideChip(false);
            }
        } else if (this.mIsFaceConflict) {
            this.mIsFaceConflict = false;
            showOrHideChip(true);
        }
    }

    public /* synthetic */ void c(boolean z, int i) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("isOpenUltraWide: ");
        sb.append(z);
        sb.append("; type: ");
        sb.append(i);
        Log.d(str, sb.toString());
        if (this.mIsGoogleLensAvailable) {
            this.mIsUltraWideConflict = z;
            showOrHideChip(!z);
        }
        BottomPopupTips bottomPopupTips = (BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            int i2 = 2131690408;
            if (!isZoomRatioBetweenUltraAndWide() || (!bottomPopupTips.containTips(R.string.ultra_wide_recommend_tip_hint) && !bottomPopupTips.containTips(R.string.ultra_wide_recommend_tip_hint_sat))) {
                if (z) {
                    if (!MiAlgoAsdSceneProfile.isAlreadyTip() && 171 != getModuleIndex()) {
                        if (i == 2) {
                            trackAISceneChanged(this.mModuleIndex, 36);
                        }
                        MiAlgoAsdSceneProfile.setTipEnable(MiAlgoAsdSceneProfile.ULTRA_WIDE, true);
                        if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                            i2 = R.string.ultra_wide_recommend_tip_hint;
                        }
                        bottomPopupTips.showTips(14, i2, 7);
                    } else {
                        return;
                    }
                } else if (bottomPopupTips.containTips(R.string.ultra_wide_recommend_tip_hint) || bottomPopupTips.containTips(R.string.ultra_wide_recommend_tip_hint_sat)) {
                    bottomPopupTips.directlyHideTips();
                }
                return;
            }
            bottomPopupTips.directlyHideTips();
        }
    }

    public void cancelFocus(boolean z) {
        if (isAlive() && isFrameAvailable() && getCameraState() != 0) {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                if (z) {
                    camera2Proxy.setFocusMode(4);
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
            MainContentProtocol mainContentProtocol = this.mMainProtocol;
            if (mainContentProtocol != null) {
                mainContentProtocol.setCameraDisplayOrientation(this.mCameraDisplayOrientation);
            }
            FocusManager2 focusManager2 = this.mFocusManager;
            if (focusManager2 != null) {
                focusManager2.setDisplayOrientation(this.mCameraDisplayOrientation);
            }
        }
    }

    public void closeBacklightTip(int i, int i2) {
        TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        int i3 = this.mCurrentAiScene;
        if (i3 == -1 && i3 != i) {
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
                this.mCamera2Device.setScreenLightCallback(null);
                this.mCamera2Device.setMetaDataCallback(null);
                this.mCamera2Device.setErrorCallback(null);
                this.mCamera2Device.releaseCameraPreviewCallback(null);
                this.mCamera2Device.setFocusCallback(null);
                this.mCamera2Device.setASD(false);
                if (scanQRCodeEnabled() || b.Ob() || this.mIsGoogleLensAvailable) {
                    this.mCamera2Device.stopPreviewCallback(true);
                }
                if (this.mFaceDetectionStarted) {
                    this.mFaceDetectionStarted = false;
                }
                this.m3ALocked = false;
                this.mCamera2Device = null;
            }
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(false);
            this.mFocusManager.destroy();
        }
        if (scanQRCodeEnabled() || b.Ob() || this.mIsGoogleLensAvailable) {
            PreviewDecodeManager.getInstance().quit();
        }
        LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
        if (localBinder != null) {
            localBinder.setOnSessionStatusCallBackListener(null);
        }
        stopCpuBoost();
        Log.d(TAG, "closeCamera: X");
    }

    public void closeMoonMode(int i, int i2) {
        if (this.mEnteringMoonMode) {
            int i3 = this.mCurrentAiScene;
            if ((i3 == 10 || i3 == 35) && i != this.mCurrentAiScene) {
                TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
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
                if (this.mMutexModePicker.isSuperResolution() && !DataRepository.dataItemFeature().ac()) {
                    this.mCamera2Device.setSuperResolution(true);
                }
            }
        }
    }

    public void consumePreference(@UpdateType int... iArr) {
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
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append("no consumer for this updateType: ");
                    sb.append(i);
                    throw new RuntimeException(sb.toString());
            }
        }
    }

    public void enterMutexMode(int i) {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy == null) {
            Log.d(TAG, "enterMutexMode error, mCamera2Device is null");
            return;
        }
        if (i == 1) {
            camera2Proxy.setHDR(true);
        } else if (i == 3) {
            camera2Proxy.setHHT(true);
        } else if (i == 10) {
            camera2Proxy.setSuperResolution(true);
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
    public CameraSize getBestPictureSize(List<CameraSize> list, float f2) {
        PictureSizeManager.initialize(list, getMaxPictureSize(), this.mModuleIndex, this.mBogusCameraId);
        return PictureSizeManager.getBestPictureSize(f2);
    }

    public CircularMediaRecorder getCircularMediaRecorder() {
        CircularMediaRecorder circularMediaRecorder;
        synchronized (this.mCircularMediaRecorderStateLock) {
            circularMediaRecorder = this.mCircularMediaRecorder;
        }
        return circularMediaRecorder;
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
        return (!b.Yi() || !"live".equals(str)) ? 0 : 2;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x012f, code lost:
        if (r0 != 175) goto L_0x0131;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0133, code lost:
        r3 = 33011;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x014d, code lost:
        if (com.android.camera.CameraSettings.isUltraPixelOn() != false) goto L_0x0133;
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
            } else {
                String str = "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_NORMAL";
                if (isUltraWideBackCamera()) {
                    Log.d(TAG, str);
                } else if (CameraSettings.isSupportedOpticalZoom()) {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_SAT");
                    i2 = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_SAT;
                } else {
                    Log.d(TAG, str);
                }
            }
            this.mOperatingMode = i2;
            return i2;
        }
        int i3 = 32775;
        if (isFrontCamera()) {
            mIsBeautyFrontOn = true;
            if (!isPortraitMode() || !DataRepository.dataItemFeature().Yc()) {
                if (!isPortraitMode() || !isBokehFrontCamera()) {
                    if (this.mCameraCapabilities.isSupportedQcfa() && !mIsBeautyFrontOn) {
                        if ("off".equals(DataRepository.dataItemConfig().getComponentHdr().getComponentValue(this.mModuleIndex)) && DataRepository.dataItemFeature().Da() <= 0) {
                            i = 32775;
                            if (this.mModuleIndex != 163 || !CameraSettings.isUltraPixelOn()) {
                                i3 = i;
                            }
                        }
                    }
                    i = 32773;
                    i3 = i;
                }
            } else if (!isBokehFrontCamera()) {
                i = 33009;
                i3 = i;
            }
            i = 32770;
            i3 = i;
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
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("getOperatingMode: ");
        sb.append(String.format("operatingMode = 0x%x", new Object[]{Integer.valueOf(i3)}));
        Log.d(str2, sb.toString());
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
        if (!this.mPaused && !this.isZooming && !isKeptBitmapTexture() && !this.mMultiSnapStatus && getCameraState() != 0 && getCameraState() != 3) {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if ((camera2Proxy == null || !camera2Proxy.isCaptureBusy(this.mMutexModePicker.isHdr())) && !isQueueFull() && !isInCountDown()) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isCameraSwitchingDuringZoomingAllowed() {
        if (CameraSettings.isSupportedOpticalZoom()) {
            return super.isCameraSwitchingDuringZoomingAllowed();
        }
        return HybridZoomingSystem.IS_3_OR_MORE_SAT && !CameraSettings.isMacroModeEnabled(this.mModuleIndex) && isBackCamera();
    }

    /* access modifiers changed from: protected */
    public boolean isDetectedHHT() {
        return false;
    }

    public boolean isDoingAction() {
        if (this.mPaused || this.isZooming || isKeptBitmapTexture() || this.mMultiSnapStatus || getCameraState() == 0 || getCameraState() == 3) {
            return true;
        }
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        return (camera2Proxy != null && camera2Proxy.isCaptureBusy(true)) || isQueueFull() || isInCountDown();
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
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        boolean z = false;
        if (camera2Proxy == null) {
            return false;
        }
        int focusMode = camera2Proxy.getFocusMode();
        if ((!this.mFocusAreaSupported && this.mMeteringAreaSupported && !this.mFocusOrAELockSupported) || 5 == focusMode || focusMode == 0) {
            z = true;
        }
        return z;
    }

    public boolean isNeedMute() {
        return CameraSettings.isLiveShotOn();
    }

    /* access modifiers changed from: protected */
    public boolean isParallelSessionEnable() {
        boolean z = true;
        boolean z2 = CameraSettings.isCameraParallelProcessEnable() && getModuleIndex() != 173 && (!isUltraWideBackCamera() || b.isMTKPlatform()) && (b.isMTKPlatform() || !this.mIsImageCaptureIntent || b.Kn);
        if (b.isMTKPlatform()) {
            return z2;
        }
        if (isUltraWideBackCamera() || getModuleIndex() == 167 || isStandaloneMacroCamera()) {
            z = false;
        }
        return z2 & z;
    }

    public boolean isSelectingCapturedResult() {
        boolean z = false;
        if (!this.mIsImageCaptureIntent) {
            return false;
        }
        BaseDelegate baseDelegate = (BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null && baseDelegate.getActiveFragment(R.id.bottom_action) == 4083) {
            z = true;
        }
        return z;
    }

    public boolean isShot2Gallery() {
        return this.mEnableShot2Gallery;
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
        if (!DataRepository.dataItemFeature().ic()) {
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
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0031, code lost:
        if (com.android.camera.CameraSettings.isUltraPixelOn() == false) goto L_0x0035;
     */
    public boolean isZoomEnabled() {
        boolean z = true;
        if (getCameraState() != 3 && !this.mMutexModePicker.isUbiFocus() && !CameraSettings.isStereoModeOn() && !CameraSettings.isPortraitModeBackOn() && !isFrontCamera()) {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                if (!camera2Proxy.isCaptureBusy(true)) {
                }
            }
        }
        z = false;
        DualController dualController = (DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (!z || dualController == null || !dualController.isZoomVisible() || !dualController.isSlideVisible()) {
            return z;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isZslPreferred() {
        if (b.isMTKPlatform()) {
            int i = this.mModuleIndex;
            if (i == 163 || i == 171) {
                return true;
            }
        } else if (this.mModuleIndex != 167) {
            return true;
        }
        return false;
    }

    public /* synthetic */ void j(boolean z) {
        RecordState recordState = (RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            if (z) {
                animateCapture();
                playCameraSound(0);
                recordState.onPostSavingStart();
            }
            recordState.onPostSavingFinish();
        }
    }

    public /* synthetic */ void l(boolean z) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onCaptureCompleted and enable shot = ");
        sb.append(z);
        Log.d(str, sb.toString());
        if (DataRepository.dataItemFeature().bc()) {
            if (!isKeptBitmapTexture() && !this.mMultiSnapStatus && z) {
                setCameraState(1);
                enableCameraControls(true);
            }
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.unRegisterCaptureCallback();
            }
        }
    }

    public /* synthetic */ void m(boolean z) {
        this.mMainProtocol.setEvAdjustable(z);
    }

    public boolean multiCapture() {
        if (isDoingAction() || !this.mPendingMultiCapture) {
            return false;
        }
        this.mPendingMultiCapture = false;
        this.mActivity.getScreenHint().updateHint();
        if (Storage.isLowStorageAtLastPoint()) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Not enough space or storage not ready. remaining=");
            sb.append(Storage.getLeftSpace());
            Log.i(str, sb.toString());
            return false;
        } else if (this.mActivity.getImageSaver().isBusy()) {
            Log.d(TAG, "ImageSaver is busy, wait for a moment!");
            RotateTextToast.getInstance(this.mActivity).show(R.string.toast_saving, 0);
            return false;
        } else if (this.mIsMoonMode) {
            return false;
        } else {
            ((ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).closeMutexElement(SupportedConfigFactory.CLOSE_BY_BURST_SHOOT, 193, 194, 196, 239, 201, 206);
            BackStack backStack = (BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
            if (backStack != null) {
                backStack.handleBackStackFromShutter();
            }
            prepareMultiCapture();
            Observable.create(new ObservableOnSubscribe<Integer>() {
                public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                    Camera2Module.this.mBurstEmitter = observableEmitter;
                }
            }).observeOn(AndroidSchedulers.mainThread()).map(new Function<Integer, Integer>() {
                public Integer apply(Integer num) throws Exception {
                    SnapShotIndicator snapShotIndicator = (SnapShotIndicator) ModeCoordinatorImpl.getInstance().getAttachProtocol(184);
                    if (snapShotIndicator != null) {
                        MiBeautyProtocol miBeautyProtocol = (MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                        snapShotIndicator.setSnapNumVisible(!(miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()), false);
                        snapShotIndicator.setSnapNumValue(num.intValue());
                    }
                    return num;
                }
            }).subscribe((Observer<? super T>) new Observer<Integer>() {
                public void onComplete() {
                    ((SnapShotIndicator) ModeCoordinatorImpl.getInstance().getAttachProtocol(184)).setSnapNumVisible(false, true);
                    ((ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_BURST_SHOOT);
                }

                public void onError(Throwable th) {
                }

                public void onNext(Integer num) {
                }

                public void onSubscribe(Disposable disposable) {
                    Camera2Module.this.mBurstStartTime = System.currentTimeMillis();
                    Camera2Module.this.mBurstDisposable = disposable;
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

    public /* synthetic */ void od() {
        ((TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).alertAiDetectTipHint(8, R.string.super_night_hint, -1);
        this.mShowSuperNightHint = false;
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
                TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
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
        if (b.Ji()) {
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
                char c2 = 65535;
                if (voiceControlAction.hashCode() == 1270567718 && voiceControlAction.equals("CAPTURE")) {
                    c2 = 0;
                }
                if (c2 == 0) {
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
        if (DataRepository.dataItemFeature().Ab()) {
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
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setCharacteristics(cameraCapabilities);
        }
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.onCapabilityChanged(cameraCapabilities);
        }
    }

    public void onCaptureShutter(boolean z) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onCaptureShutter: cameraState = ");
        sb.append(getCameraState());
        sb.append(", isParallel = ");
        sb.append(this.mEnableParallelSession);
        Log.d(str, sb.toString());
        onShutter(z);
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x00b2  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00b7  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00e2  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0114  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0147  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x01dc  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x022e  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0237  */
    public ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, CameraSize cameraSize, boolean z) {
        List list;
        int i;
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
            List faceWaterMarkInfos = ((MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).getFaceWaterMarkInfos();
            if (faceWaterMarkInfos != null && !faceWaterMarkInfos.isEmpty()) {
                list = new ArrayList(faceWaterMarkInfos);
                String str2 = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("onCaptureStart: inputSize = ");
                sb.append(cameraSize);
                Log.d(str2, sb.toString());
                if (isIn3OrMoreSatMode() && !cameraSize.equals(this.mPictureSize)) {
                    this.mPictureSize = cameraSize;
                    updateOutputSize(cameraSize);
                }
                CameraSize cameraSize2 = this.mOutPutSize;
                Size sizeObject = cameraSize2 != null ? cameraSize.toSizeObject() : cameraSize2.toSizeObject();
                String str3 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("onCaptureStart: outputSize = ");
                sb2.append(sizeObject);
                Log.d(str3, sb2.toString());
                Builder builder = new Builder(this.mPreviewSize.toSizeObject(), cameraSize.toSizeObject(), sizeObject);
                if (parallelType == 1) {
                    CameraSize cameraSize3 = this.mSensorRawImageSize;
                    builder.setRawSize(cameraSize3.width, cameraSize3.height);
                }
                Builder filterId = builder.setHasDualWaterMark(CameraSettings.isDualCameraWaterMarkOpen()).setMirror(isFrontMirror()).setLightingPattern(CameraSettings.getPortraitLightingPattern()).setFilterId(EffectController.getInstance().getEffectForSaving(false));
                i = this.mOrientation;
                if (-1 == i) {
                    i = 0;
                }
                Builder location = filterId.setOrientation(i).setJpegRotation(this.mJpegRotation).setShootRotation((CameraSettings.isGradienterOn() || this.mShootRotation != -1.0f) ? this.mShootRotation : 0.0f).setShootOrientation(this.mShootOrientation).setLocation(this.mLocation);
                if (CameraSettings.isTimeWaterMarkOpen()) {
                    str = Util.getTimeWatermark();
                }
                parallelTaskData.fillParameter(location.setTimeWaterMarkString(str).setFaceWaterMarkList(list).setAgeGenderAndMagicMirrorWater(CameraSettings.isAgeGenderAndMagicMirrorWaterOpen()).setFrontCamera(isFrontCamera()).setBokehFrontCamera(isBokehFrontCamera()).setAlgorithmName(this.mAlgorithmName).setPictureInfo(getPictureInfo()).setSuffix(getSuffix()).setGradienterOn(this.mIsGradienterOn).setTiltShiftMode(getTiltShiftMode()).setSaveGroupshotPrimitive(CameraSettings.isSaveGroushotPrimitiveOn()).setDeviceWatermarkParam(getDeviceWaterMarkParam()).setJpegQuality(BaseModule.getJpegQuality(false)).setPrefix(getPrefix()).build());
                parallelTaskData.setNeedThumbnail(z && !this.mEnabledPreviewThumbnail);
                parallelTaskData.setCurrentModuleIndex(this.mModuleIndex);
                CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
                parallelTaskData.setAdaptiveSnapshotSize(cameraCapabilities == null && cameraCapabilities.isAdaptiveSnapshotSizeInSatModeSupported());
                parallelTaskData.setLiveShotTask(false);
                if (z2) {
                    Camera camera = this.mActivity;
                    if (camera != null) {
                        ImageSaver imageSaver = camera.getImageSaver();
                        if (imageSaver != null) {
                            synchronized (this.mCircularMediaRecorderStateLock) {
                                if (this.mCircularMediaRecorder != null) {
                                    parallelTaskData.setLiveShotTask(true);
                                    this.mCircularMediaRecorder.snapshot(this.mOrientationCompensation, imageSaver, parallelTaskData, this.mFilterId);
                                }
                            }
                        }
                    }
                }
                String str4 = TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("onCaptureStart: isParallel = ");
                sb3.append(this.mEnableParallelSession);
                sb3.append(", shotType = ");
                sb3.append(parallelTaskData.getParallelType());
                sb3.append(", isLiveShot = ");
                sb3.append(z2);
                Log.d(str4, sb3.toString());
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
        list = null;
        String str22 = TAG;
        StringBuilder sb4 = new StringBuilder();
        sb4.append("onCaptureStart: inputSize = ");
        sb4.append(cameraSize);
        Log.d(str22, sb4.toString());
        this.mPictureSize = cameraSize;
        updateOutputSize(cameraSize);
        CameraSize cameraSize22 = this.mOutPutSize;
        if (cameraSize22 != null) {
        }
        String str32 = TAG;
        StringBuilder sb22 = new StringBuilder();
        sb22.append("onCaptureStart: outputSize = ");
        sb22.append(sizeObject);
        Log.d(str32, sb22.toString());
        Builder builder2 = new Builder(this.mPreviewSize.toSizeObject(), cameraSize.toSizeObject(), sizeObject);
        if (parallelType == 1) {
        }
        Builder filterId2 = builder2.setHasDualWaterMark(CameraSettings.isDualCameraWaterMarkOpen()).setMirror(isFrontMirror()).setLightingPattern(CameraSettings.getPortraitLightingPattern()).setFilterId(EffectController.getInstance().getEffectForSaving(false));
        i = this.mOrientation;
        if (-1 == i) {
        }
        Builder location2 = filterId2.setOrientation(i).setJpegRotation(this.mJpegRotation).setShootRotation((CameraSettings.isGradienterOn() || this.mShootRotation != -1.0f) ? this.mShootRotation : 0.0f).setShootOrientation(this.mShootOrientation).setLocation(this.mLocation);
        if (CameraSettings.isTimeWaterMarkOpen()) {
        }
        parallelTaskData.fillParameter(location2.setTimeWaterMarkString(str).setFaceWaterMarkList(list).setAgeGenderAndMagicMirrorWater(CameraSettings.isAgeGenderAndMagicMirrorWaterOpen()).setFrontCamera(isFrontCamera()).setBokehFrontCamera(isBokehFrontCamera()).setAlgorithmName(this.mAlgorithmName).setPictureInfo(getPictureInfo()).setSuffix(getSuffix()).setGradienterOn(this.mIsGradienterOn).setTiltShiftMode(getTiltShiftMode()).setSaveGroupshotPrimitive(CameraSettings.isSaveGroushotPrimitiveOn()).setDeviceWatermarkParam(getDeviceWaterMarkParam()).setJpegQuality(BaseModule.getJpegQuality(false)).setPrefix(getPrefix()).build());
        parallelTaskData.setNeedThumbnail(z && !this.mEnabledPreviewThumbnail);
        parallelTaskData.setCurrentModuleIndex(this.mModuleIndex);
        CameraCapabilities cameraCapabilities2 = this.mCameraCapabilities;
        parallelTaskData.setAdaptiveSnapshotSize(cameraCapabilities2 == null && cameraCapabilities2.isAdaptiveSnapshotSizeInSatModeSupported());
        parallelTaskData.setLiveShotTask(false);
        if (z2) {
        }
        String str42 = TAG;
        StringBuilder sb32 = new StringBuilder();
        sb32.append("onCaptureStart: isParallel = ");
        sb32.append(this.mEnableParallelSession);
        sb32.append(", shotType = ");
        sb32.append(parallelTaskData.getParallelType());
        sb32.append(", isLiveShot = ");
        sb32.append(z2);
        Log.d(str42, sb32.toString());
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
        if (Util.isGlobalVersion() && !b.Tc()) {
            this.mLensApi = new LensApi(this.mActivity);
            this.mLensApi.checkLensAvailability(new LensAvailabilityCallback() {
                public void onAvailabilityStatusFetched(int i) {
                    Camera2Module.this.mLensStatus = i;
                    CameraSettings.setGoogleLensAvailability(Camera2Module.this.mLensStatus == 0);
                }
            });
        }
    }

    public void onDestroy() {
        super.onDestroy();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendEmptyMessage(45);
        }
    }

    public void onFaceDetected(CameraHardwareFace[] cameraHardwareFaceArr, FaceAnalyzeInfo faceAnalyzeInfo) {
        if (isAlive() && this.mActivity.getCameraScreenNail().getFrameAvailableFlag() && cameraHardwareFaceArr != null) {
            if (b.hi()) {
                boolean z = cameraHardwareFaceArr.length > 0;
                if (z != this.mFaceDetected && isFrontCamera()) {
                    int i = this.mModuleIndex;
                    if (i == 163 || i == 165 || i == 171) {
                        this.mCamera2Device.resumePreview();
                    }
                }
                this.mFaceDetected = z;
            }
            this.mFaceInfo = faceAnalyzeInfo;
            if (!b.pj() || cameraHardwareFaceArr.length <= 0 || cameraHardwareFaceArr[0].faceType != 64206) {
                if (this.mIsGoogleLensAvailable) {
                    Camera camera = this.mActivity;
                    if (camera != null) {
                        camera.runOnUiThread(new o(this, cameraHardwareFaceArr));
                    }
                }
                if (this.mMainProtocol.setFaces(1, cameraHardwareFaceArr, getActiveArraySize(), getDeviceBasedZoomRatio())) {
                    if (this.mIsPortraitLightingOn) {
                        this.mMainProtocol.lightingDetectFace(cameraHardwareFaceArr, false);
                    }
                    if (this.mMainProtocol.isFaceExists(1) && this.mMainProtocol.isFocusViewVisible()) {
                        FocusManager2 focusManager2 = this.mFocusManager;
                        if (focusManager2 != null && !focusManager2.isFromTouch()) {
                            if (!this.mHandler.hasMessages(56)) {
                                this.mHandler.sendEmptyMessage(56);
                            }
                        }
                    }
                    this.mHandler.removeMessages(56);
                }
                return;
            }
            if (this.mObjectTrackingStarted) {
                this.mMainProtocol.setFaces(3, cameraHardwareFaceArr, getActiveArraySize(), getDeviceBasedZoomRatio());
            }
        }
    }

    public void onFilterChanged(int i, int i2) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onFilterChanged: category = ");
        sb.append(i);
        sb.append(", newIndex = ");
        sb.append(i2);
        Log.d(str, sb.toString());
        updatePreferenceTrampoline(2);
        this.mMainProtocol.updateEffectViewVisible();
    }

    public void onFocusStateChanged(FocusTask focusTask) {
        if (isFrameAvailable() && !isDeparted()) {
            int focusTrigger = focusTask.getFocusTrigger();
            if (focusTrigger == 1) {
                Log.v(TAG, String.format(Locale.ENGLISH, "FocusTime=%1$dms focused=%2$b", new Object[]{Long.valueOf(focusTask.getElapsedTime()), Boolean.valueOf(focusTask.isSuccess())}));
                if (!this.mFocusManager.isFocusingSnapOnFinish() && getCameraState() != 3) {
                    setCameraState(1);
                }
                this.mFocusManager.onFocusResult(focusTask);
                this.mActivity.getSensorStateManager().reset();
                if (focusTask.isSuccess() && this.m3ALocked) {
                    if (!DataRepository.dataItemFeature().gd() && isZoomRatioBetweenUltraAndWide()) {
                        CameraCapabilities cameraCapabilities = this.mUltraCameraCapabilities;
                        if (cameraCapabilities != null) {
                            boolean isAFRegionSupported = cameraCapabilities.isAFRegionSupported();
                            String str = TAG;
                            StringBuilder sb = new StringBuilder();
                            sb.append("onFocusStateChanged: isUltraFocusAreaSupported = ");
                            sb.append(isAFRegionSupported);
                            Log.d(str, sb.toString());
                            if (!isAFRegionSupported) {
                                this.mCamera2Device.setFocusMode(0);
                                this.mCamera2Device.setFocusDistance(0.0f);
                                this.mUltraWideAELocked = true;
                            }
                        }
                    }
                    this.mCamera2Device.lockExposure(true);
                }
            } else if (focusTrigger == 2 || focusTrigger == 3) {
                String str2 = null;
                if (focusTask.isFocusing()) {
                    this.mAFEndLogTimes = 0;
                    str2 = "onAutoFocusMoving start";
                } else if (this.mAFEndLogTimes == 0) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("onAutoFocusMoving end. result=");
                    sb2.append(focusTask.isSuccess());
                    str2 = sb2.toString();
                    this.mAFEndLogTimes++;
                }
                if (Util.sIsDumpLog && str2 != null) {
                    Log.v(TAG, str2);
                }
                if (getCameraState() != 3 || focusTask.getFocusTrigger() == 3) {
                    if (!this.m3ALocked) {
                        this.mFocusManager.onFocusResult(focusTask);
                    }
                } else if (focusTask.isSuccess()) {
                    this.mFocusManager.onFocusResult(focusTask);
                }
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
        CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
        if (cameraCapabilities != null && cameraCapabilities.isMotionDetectionSupported() && this.mMotionDetected != z) {
            this.mMotionDetected = z;
            updateHDRPreference();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0041, code lost:
        if (r0.isNeedFlashOn() != false) goto L_0x0043;
     */
    public void onHdrSceneChanged(boolean z) {
        if (this.isDetectedInHdr != z && !this.mPaused) {
            ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
            if (!componentHdr.isEmpty()) {
                if ("auto".equals(componentHdr.getComponentValue(this.mModuleIndex))) {
                    if (z) {
                        if (getZoomRatio() <= 1.0f && this.mCurrentAiScene != -1) {
                            Camera2Proxy camera2Proxy = this.mCamera2Device;
                            if (camera2Proxy != null) {
                            }
                        }
                        return;
                    }
                    this.mHandler.post(new e(z));
                    if (z) {
                        if (this.mMutexModePicker.isNormal()) {
                            this.mMutexModePicker.setMutexMode(1);
                        }
                    } else if (this.mMutexModePicker.isMorphoHdr()) {
                        this.mMutexModePicker.resetMutexMode();
                    }
                    this.isDetectedInHdr = z;
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("onHdrSceneChanged: ");
                    sb.append(z);
                    Log.d(str, sb.toString());
                }
            }
        }
    }

    public void onHostStopAndNotifyActionStop() {
        super.onHostStopAndNotifyActionStop();
        if (this.mMultiSnapStatus) {
            onBurstPictureTakenFinished(true);
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(false);
            this.mFocusManager.destroy();
        }
        checkMoreFrameCaptureLockAFAE(false);
        if (handleSuperNightResultIfNeed()) {
            doLaterReleaseIfNeed();
        }
    }

    /* access modifiers changed from: protected */
    public boolean onInterceptZoomingEvent(float f2, float f3, int i) {
        if (f3 < 1.0f) {
            onUltraWideChanged(false, -1);
        }
        if (DataRepository.dataItemFeature().gd()) {
            if (this.m3ALocked) {
                unlockAEAF();
                FocusManager2 focusManager2 = this.mFocusManager;
                if (focusManager2 != null) {
                    focusManager2.cancelFocus();
                }
                BottomPopupTips bottomPopupTips = (BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                if (bottomPopupTips != null) {
                    bottomPopupTips.directlyHideTips();
                }
            } else {
                Camera2Proxy camera2Proxy = this.mCamera2Device;
                if (!(camera2Proxy == null || 4 == camera2Proxy.getFocusMode())) {
                    Log.d(TAG, "onInterceptZoomingEvent: should cancel focus.");
                    FocusManager2 focusManager22 = this.mFocusManager;
                    if (focusManager22 != null) {
                        focusManager22.cancelFocus();
                    }
                }
            }
        }
        return super.onInterceptZoomingEvent(f2, f3, i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0023, code lost:
        if (r6 != 88) goto L_0x0079;
     */
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean z = false;
        if (!isFrameAvailable()) {
            return false;
        }
        if (!(i == 24 || i == 25)) {
            if (i == 27 || i == 66) {
                if (keyEvent.getRepeatCount() == 0) {
                    if (!Util.isFingerPrintKeyEvent(keyEvent)) {
                        performKeyClicked(40, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                    } else if (CameraSettings.isFingerprintCaptureEnable()) {
                        performKeyClicked(30, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                    }
                }
                return true;
            } else if (i == 80) {
                if (keyEvent.getRepeatCount() == 0) {
                    onShutterButtonFocus(true, 1);
                }
                return true;
            } else if (i != 87) {
            }
        }
        if (i == 24 || i == 88) {
            z = true;
        }
        if (handleVolumeKeyEvent(z, true, keyEvent.getRepeatCount(), keyEvent.getDevice().isExternal())) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 4 && ((BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)).handleBackStackFromKeyBack()) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    public void onLivePhotoResultCallback(LivePhotoResult livePhotoResult) {
        this.mLivePhotoQueue.offer(livePhotoResult);
    }

    public void onLongPress(float f2, float f3) {
        int i = (int) f2;
        int i2 = (int) f3;
        if (isInTapableRect(i, i2)) {
            if (!this.mIsCurrentLensEnabled || !this.mIsGoogleLensAvailable || !CameraSettings.isAvailableLongPressGoogleLens()) {
                onSingleTapUp(i, i2, true);
                if (this.m3ALockSupported && this.mCamera2Device.getFocusMode() != AutoFocus.convertToFocusMode("manual")) {
                    lockAEAF();
                }
                this.mMainProtocol.performHapticFeedback(0);
                return;
            }
            if (DataRepository.dataItemGlobal().getString(CameraSettings.KEY_LONG_PRESS_VIEWFINDER, null) == null) {
                CameraStatUtil.trackGoogleLensPicker();
                FragmentManager fragmentManager = this.mActivity.getFragmentManager();
                g gVar = new g(this, f2, f3, i, i2);
                GoogleLensFragment.showOptions(fragmentManager, gVar);
            } else {
                CameraStatUtil.trackGoogleLensTouchAndHold();
                LensAgent.getInstance().onFocusChange(0, f2 / ((float) Util.sWindowWidth), f3 / ((float) Util.sWindowHeight));
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
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.removeMessages();
        }
        unregisterSensor();
        tryRemoveCountDownMessage();
        this.mActivity.getSensorStateManager().reset();
        resetScreenOn();
        closeCamera();
        setAiSceneEffect(0);
        releaseEffectProcessor();
        if (Util.isGlobalVersion() && !b.Tc()) {
            this.mActivity.runOnUiThread(new h(this));
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    public void onPictureTaken(byte[] bArr) {
    }

    public void onPictureTakenFinished(boolean z) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onPictureTakenFinished: succeed = ");
        sb.append(z);
        Log.d(str, sb.toString());
        if (z) {
            trackGeneralInfo(1, false);
            trackPictureTaken(1, false, this.mLocation != null, getCurrentAiSceneName(), this.mEnteringMoonMode, this.mIsMoonMode);
            long currentTimeMillis = System.currentTimeMillis() - this.mCaptureStartTime;
            CameraStatUtil.trackTakePictureCost(currentTimeMillis, isFrontCamera(), this.mModuleIndex);
            if (this.mModuleIndex == 171 && DataRepository.dataItemFeature().isSupportBokehAdjust()) {
                CameraStatUtil.trackBokehTaken();
            }
            ScenarioTrackUtil.trackCaptureTimeEnd();
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("mCaptureStartTime(from onShutterButtonClick start to jpegCallback finished) = ");
            sb2.append(currentTimeMillis);
            sb2.append("ms");
            Log.d(str2, sb2.toString());
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
        handleSuperNightResultInCaptureMode();
        checkMoreFrameCaptureLockAFAE(false);
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
            if (DataRepository.dataItemFeature().gb() || !isFrontCamera()) {
                this.mShouldDoMFNR = false;
            } else if (b.ei()) {
                this.mShouldDoMFNR = true;
            } else {
                Integer num = (Integer) captureResult.get(CaptureResult.SENSOR_SENSITIVITY);
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("onPreviewMetaDataUpdate: iso = ");
                sb.append(num);
                Log.c(str, sb.toString());
                if (num != null && num.intValue() >= 800) {
                    z = true;
                }
                this.mShouldDoMFNR = z;
            }
            FlowableEmitter<CaptureResult> flowableEmitter = this.mMetaDataFlowableEmitter;
            if (flowableEmitter != null) {
                flowableEmitter.onNext(captureResult);
            }
            FlowableEmitter<CaptureResult> flowableEmitter2 = this.mAiSceneFlowableEmitter;
            if (!(flowableEmitter2 == null || !this.mAiSceneEnabled || this.mCamera2Device == null)) {
                flowableEmitter2.onNext(captureResult);
            }
        }
    }

    public void onPreviewPixelsRead(byte[] bArr, int i, int i2) {
        int i3 = i;
        int i4 = i2;
        animateCapture();
        playCameraSound(0);
        Bitmap createBitmap = Bitmap.createBitmap(i3, i4, Config.ARGB_8888);
        createBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bArr));
        boolean z = true;
        boolean z2 = isFrontCamera() && !isFrontMirror();
        synchronized (this.mCameraDeviceLock) {
            if (isAlive()) {
                if (isDeviceAlive()) {
                    if (this.mEnableParallelSession || this.mEnableShot2Gallery) {
                        Bitmap cropBitmap = Util.cropBitmap(createBitmap, this.mIsGradienterOn, this.mShootRotation, z2, (float) this.mOrientation, this.mModuleIndex == 165);
                        if (cropBitmap == null) {
                            Log.w(TAG, "onPreviewPixelsRead: bitmap is null!");
                            return;
                        }
                        byte[] bitmapData = Util.getBitmapData(cropBitmap, 87);
                        if (bitmapData == null) {
                            Log.w(TAG, "onPreviewPixelsRead: jpegData is null!");
                            return;
                        }
                        String str = TAG;
                        StringBuilder sb = new StringBuilder();
                        sb.append("onPreviewPixelsRead: isParallel = ");
                        sb.append(this.mEnableParallelSession);
                        sb.append(", mEnableShot2Gallery = ");
                        sb.append(this.mEnableShot2Gallery);
                        sb.append(", data = ");
                        sb.append(bitmapData);
                        Log.d(str, sb.toString());
                        ParallelTaskData parallelTaskData = new ParallelTaskData(this.mActualCameraId, System.currentTimeMillis(), -1, this.mCamera2Device.getParallelShotSavePath());
                        if (!this.mEnableParallelSession) {
                            if (!this.mEnableShot2Gallery) {
                                z = false;
                            }
                        }
                        parallelTaskData.setNeedThumbnail(z);
                        parallelTaskData.fillJpegData(bitmapData, 0);
                        parallelTaskData.fillParameter(new Builder(new Size(i3, i4), new Size(i3, i4), new Size(i3, i4)).setOrientation(this.mOrientation).build());
                        this.mActivity.getImageSaver().onParallelProcessFinish(parallelTaskData, null, null);
                        return;
                    }
                    int i5 = this.mShootOrientation - this.mDisplayRotation;
                    if (isFrontCamera() && b.di() && i5 % 180 == 0) {
                        i5 = 0;
                    }
                    Thumbnail createThumbnail = Thumbnail.createThumbnail(null, createBitmap, i5, z2);
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
        StringBuilder sb = new StringBuilder();
        sb.append("onPreviewSessionSuccess: ");
        sb.append(Thread.currentThread().getName());
        sb.append(" ");
        sb.append(this);
        Log.d(str, sb.toString());
        if (cameraCaptureSession == null) {
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("onPreviewSessionSuccess null session.");
            sb2.append(Util.getCallers(1));
            Log.d(str2, sb2.toString());
        } else if (!isAlive()) {
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("onPreviewSessionSuccess module not alive.");
            sb3.append(Util.getCallers(1));
            Log.d(str3, sb3.toString());
        } else {
            if (!isKeptBitmapTexture()) {
                this.mHandler.sendEmptyMessage(9);
            }
            if (this.mEnableParallelSession) {
                configParallelSession();
            }
            previewWhenSessionSuccess();
            if (this.mActivity.getCameraIntentManager().checkCallerLegality()) {
                if (!this.mActivity.getCameraIntentManager().isOpenOnly()) {
                    this.mActivity.getIntent().removeExtra(CameraExtras.CAMERA_OPEN_ONLY);
                    this.mHandler.sendEmptyMessageDelayed(52, 1000);
                } else {
                    this.mActivity.getIntent().removeExtra(CameraExtras.TIMER_DURATION_SECONDS);
                }
            }
        }
    }

    public void onPreviewSizeChanged(int i, int i2) {
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setPreviewSize(i, i2);
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
        if (Util.isGlobalVersion() && !b.Tc()) {
            this.mActivity.runOnUiThread(new i(this));
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
        } else if (checkShutterCondition()) {
            setTriggerMode(i);
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onShutterButtonClick ");
            sb.append(String.valueOf(getCameraState()));
            Log.d(str, sb.toString());
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
        if (isDoingAction() || this.mIsImageCaptureIntent) {
            return false;
        }
        if (!CameraSettings.isBurstShootingEnable() || !ModuleManager.isCameraModule() || this.mIsImageCaptureIntent || CameraSettings.isGroupShotOn() || CameraSettings.isGradienterOn() || CameraSettings.isTiltShiftOn() || DataRepository.dataItemRunning().isSwitchOn("pref_camera_hand_night_key") || DataRepository.dataItemRunning().isSwitchOn("pref_camera_scenemode_setting_key") || CameraSettings.isStereoModeOn() || CameraSettings.isPortraitModeBackOn() || !isBackCamera() || this.mMultiSnapStatus || this.mHandler.hasMessages(24) || this.mPendingMultiCapture || isUltraWideBackCamera() || CameraSettings.isUltraPixelOn() || isStandaloneMacroCamera()) {
            this.mLongPressedAutoFocus = true;
            this.mMainProtocol.setFocusViewType(false);
            unlockAEAF();
            this.mFocusManager.requestAutoFocus();
            this.mActivity.getScreenHint().updateHint();
            return false;
        }
        if (b.gj()) {
            this.mUpdateImageTitle = true;
        }
        this.mPendingMultiCapture = true;
        this.mFocusManager.doMultiSnap(true);
        return true;
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
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onSingleTapUp mPaused: ");
        sb.append(this.mPaused);
        sb.append("; mCamera2Device: ");
        sb.append(this.mCamera2Device);
        sb.append("; isInCountDown: ");
        sb.append(isInCountDown());
        sb.append("; getCameraState: ");
        sb.append(getCameraState());
        sb.append("; mMultiSnapStatus: ");
        sb.append(this.mMultiSnapStatus);
        sb.append("; Camera2Module: ");
        sb.append(this);
        Log.v(str, sb.toString());
        if (!this.mPaused && this.mCamera2Device != null && !hasCameraException() && this.mCamera2Device.isSessionReady() && this.mCamera2Device.isPreviewReady() && isInTapableRect(i, i2) && getCameraState() != 3 && getCameraState() != 4 && getCameraState() != 0 && !isInCountDown() && !this.mMultiSnapStatus) {
            if (this.mIsMoonMode) {
                TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                MiBeautyProtocol miBeautyProtocol = (MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
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
                    BackStack backStack = (BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
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
                            BottomPopupTips bottomPopupTips = (BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
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
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
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
        CircularMediaRecorder circularMediaRecorder = this.mCircularMediaRecorder;
        if (circularMediaRecorder != null) {
            circularMediaRecorder.onSurfaceTextureUpdated(drawExtTexAttribute);
        }
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.onPreviewComing();
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
        this.mHandler.post(new l(this, z, i));
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
        StringBuilder sb = new StringBuilder();
        sb.append("onZoomingActionEnd(): ");
        sb.append(ZoomingAction.toString(i));
        Log.d(str, sb.toString());
        DualController dualController = (DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && !dualController.isSlideVisible()) {
            if (i == 1 || i == 2) {
                dualController.setImmersiveModeEnabled(false);
            }
        }
    }

    public void onZoomingActionStart(int i) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onZoomingActionStart(): ");
        sb.append(ZoomingAction.toString(i));
        Log.d(str, sb.toString());
        TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && topAlert.isExtraMenuShowing()) {
            topAlert.hideExtraMenu();
        }
        if (!isZoomEnabled() && CameraSettings.isUltraPixelOn()) {
            BottomPopupTips bottomPopupTips = (BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.showTips(15, ComponentRunningUltraPixel.getNoSupportZoomTip(), 1);
            }
        }
        DualController dualController = (DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && (i == 1 || i == 2)) {
            dualController.setImmersiveModeEnabled(true);
        }
        CameraClickObservable cameraClickObservable = (CameraClickObservable) ModeCoordinatorImpl.getInstance().getAttachProtocol(227);
        if (cameraClickObservable != null) {
            cameraClickObservable.subscribe(168);
        }
    }

    /* access modifiers changed from: protected */
    public void openSettingActivity() {
        Intent intent = new Intent();
        intent.setClass(this.mActivity, CameraPreferenceActivity.class);
        intent.putExtra(CameraPreferenceActivity.FROM_WHERE, this.mModuleIndex);
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
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.pausePreview();
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

    public /* synthetic */ void qd() {
        if (this.mLensApi != null && this.mIsLensServiceBound) {
            Log.d(TAG, "Unbind Lens service: E");
            this.mIsLensServiceBound = false;
            try {
                this.mLensApi.onPause();
            } catch (Exception e2) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown error when pause LensAPI->");
                sb.append(e2.getMessage());
                Log.d(str, sb.toString());
            }
            Log.d(TAG, "Unbind Lens service: X");
        }
    }

    public /* synthetic */ void rd() {
        if (this.mLensApi != null && !this.mIsLensServiceBound && !this.mActivity.isActivityPaused()) {
            Log.d(TAG, "Bind Lens service: E");
            this.mLensApi.onResume();
            this.mIsLensServiceBound = true;
            Log.d(TAG, "Bind Lens service: X");
        }
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
            CameraClickObservable cameraClickObservable = (CameraClickObservable) ModeCoordinatorImpl.getInstance().getAttachProtocol(227);
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
        if (this.mIsGoogleLensAvailable) {
            Camera camera = this.mActivity;
            if (camera != null) {
                camera.runOnUiThread(new a(this));
            }
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
        return CameraSettings.isScanQRCode(this.mActivity) && this.mModuleIndex == 163 && !this.mIsImageCaptureIntent && CameraSettings.isBackCamera() && !this.mMultiSnapStatus && !CameraSettings.isStereoModeOn() && !CameraSettings.isPortraitModeBackOn() && (!DataRepository.dataItemFeature().qb() || !CameraSettings.isUltraPixelOn()) && !CameraSettings.isUltraWideConfigOpen(this.mModuleIndex) && !CameraSettings.isMacroModeEnabled(this.mModuleIndex);
    }

    public /* synthetic */ void sd() {
        this.mIsFaceConflict = false;
        this.mIsAiConflict = false;
        this.mIsUltraWideConflict = false;
        showOrHideChip(true);
    }

    /* access modifiers changed from: protected */
    public void sendOpenFailMessage() {
        this.mHandler.sendEmptyMessage(10);
    }

    public void setAsdScenes(ASDScene[] aSDSceneArr) {
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
        if (z) {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.releaseFakeSurfaceIfNeed();
            }
        }
        if (z && isBackCamera()) {
            int i = this.mModuleIndex;
            if ((i == 165 || i == 163) && CameraSettings.isCameraLyingHintOn()) {
                this.mHandler.post(new n(this));
            }
        }
    }

    public boolean shouldCaptureDirectly() {
        if (this.mUseLegacyFlashMode) {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null && camera2Proxy.isNeedFlashOn()) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0054, code lost:
        if (getManualValue(com.android.camera.CameraSettings.KEY_QC_EXPOSURETIME, getString(com.android.camera.R.string.pref_camera_exposuretime_default)).equals(getString(com.android.camera.R.string.pref_camera_exposuretime_default)) != false) goto L_0x0056;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0019, code lost:
        if (r0.hasMessages(50) != false) goto L_0x001b;
     */
    public boolean shouldReleaseLater() {
        if (!this.mIsImageCaptureIntent) {
            if (getCameraState() != 3) {
                if (this.mEnableShot2Gallery) {
                    Handler handler = this.mHandler;
                    if (handler != null) {
                    }
                }
            }
            Handler handler2 = this.mHandler;
            if ((handler2 == null || (!handler2.hasMessages(48) && !this.mHandler.hasMessages(49))) && !this.mFocusManager.isFocusing()) {
                if (this.mModuleIndex == 167) {
                }
                return true;
            }
        }
        return false;
    }

    public void showBacklightTip() {
        if (!CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
            TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            ((ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).closeMutexElement(SupportedConfigFactory.CLOSE_BY_AI, 193);
            topAlert.alertHDR(8, false, false);
            topAlert.alertAiSceneSelector(0);
            applyBacklightEffect();
            this.mCamera2Device.setASD(false);
            resumePreviewInWorkThread();
        }
    }

    public void showOrHideChip(boolean z) {
        if (this.mIsGoogleLensAvailable) {
            BottomPopupTips bottomPopupTips = (BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            boolean z2 = true;
            if (z) {
                boolean z3 = bottomPopupTips != null && bottomPopupTips.isTipShowing();
                DualController dualController = (DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
                boolean z4 = dualController != null && dualController.isSlideVisible();
                MakeupProtocol makeupProtocol = (MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
                boolean z5 = makeupProtocol != null && makeupProtocol.isSeekBarVisible();
                MiBeautyProtocol miBeautyProtocol = (MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                boolean z6 = miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow();
                boolean z7 = !this.mIsAiConflict && !this.mIsFaceConflict && !this.mIsUltraWideConflict && !this.mIsMoonMode && !z3 && !z4 && !z5 && !z6;
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("pre showOrHideChip: isTipsShow = ");
                sb.append(z3);
                sb.append(", isZoomSlideVisible = ");
                sb.append(z4);
                sb.append(", isSeekBarVisible = ");
                sb.append(z5);
                sb.append(", isMakeupVisible = ");
                sb.append(z6);
                sb.append(", mIsAiConflict = ");
                sb.append(this.mIsAiConflict);
                sb.append(", mIsUltraWideConflict = ");
                sb.append(this.mIsUltraWideConflict);
                sb.append(", mIsMoonMode = ");
                sb.append(this.mIsMoonMode);
                sb.append(", mIsFaceConflict = ");
                sb.append(this.mIsFaceConflict);
                sb.append(", final isShow = ");
                sb.append(z7);
                sb.append(", mIsCurrentLensEnabled = ");
                sb.append(this.mIsCurrentLensEnabled);
                Log.d(str, sb.toString());
                z = z7;
            }
            if (this.mIsCurrentLensEnabled != z) {
                this.mIsCurrentLensEnabled = z;
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("showOrHideChip: show = ");
                sb2.append(z);
                sb2.append(", isChipsEnabled = ");
                sb2.append(CameraSettings.isAvailableChipsGoogleLens());
                Log.d(str2, sb2.toString());
                LensAgent instance = LensAgent.getInstance();
                if (!z || !CameraSettings.isAvailableChipsGoogleLens()) {
                    z2 = false;
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
        if (this.mFaceDetectionEnabled && !this.mFaceDetectionStarted) {
            Camera camera = this.mActivity;
            if (camera != null && !camera.isActivityPaused() && isAlive() && this.mMaxFaceCount > 0 && this.mCamera2Device != null) {
                this.mFaceDetectionStarted = true;
                this.mMainProtocol.setActiveIndicator(1);
                this.mCamera2Device.startFaceDetection();
                updateFaceView(true, true);
            }
        }
    }

    public void startFocus() {
        if (isDeviceAlive() && isFrameAvailable()) {
            if (this.mFocusOrAELockSupported) {
                this.mCamera2Device.startFocus(FocusTask.create(1), this.mModuleIndex);
            } else {
                this.mCamera2Device.resumePreview();
            }
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
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setFocusCallback(this);
            this.mCamera2Device.setMetaDataCallback(this);
            this.mCamera2Device.setScreenLightCallback(this);
            this.mCamera2Device.setErrorCallback(this.mErrorCallback);
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("startPreview: set PictureSize with ");
            sb.append(this.mPictureSize);
            Log.d(str, sb.toString());
            this.mCamera2Device.setPictureSize(this.mPictureSize);
            if (isSensorRawStreamRequired() && this.mSensorRawImageSize != null) {
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("startPreview: set SensorRawImageSize with ");
                sb2.append(this.mSensorRawImageSize);
                Log.d(str2, sb2.toString());
                this.mCamera2Device.setSensorRawImageSize(this.mSensorRawImageSize, isCurrentRawDomainBasedSuperNight());
            }
            if (this.mEnableParallelSession && isPortraitMode()) {
                String str3 = TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("startPreview: set SubPictureSize with ");
                sb3.append(this.mSubPictureSize);
                Log.d(str3, sb3.toString());
                this.mCamera2Device.setSubPictureSize(this.mSubPictureSize);
            }
            boolean isEnableQcfaForAlgoUp = isEnableQcfaForAlgoUp();
            String str4 = TAG;
            StringBuilder sb4 = new StringBuilder();
            sb4.append("[QCFA] startPreview: set qcfa enable ");
            sb4.append(isEnableQcfaForAlgoUp);
            Log.d(str4, sb4.toString());
            this.mCamera2Device.setQcfaEnable(isEnableQcfaForAlgoUp);
            boolean scanQRCodeEnabled = scanQRCodeEnabled();
            boolean supportHandGesture = DataRepository.dataItemRunning().supportHandGesture();
            boolean z = false;
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
            String str5 = TAG;
            StringBuilder sb5 = new StringBuilder();
            sb5.append("startPreview: surfaceTexture = ");
            sb5.append(surfaceTexture);
            Log.d(str5, sb5.toString());
            if (surfaceTexture != null) {
                this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            }
            Surface surface = surfaceTexture != null ? new Surface(surfaceTexture) : null;
            if (isSensorRawStreamRequired() && this.mSensorRawImageSize != null) {
                z = true;
            }
            this.mConfigRawStream = z;
            this.mCamera2Device.startPreviewSession(surface, z2, this.mConfigRawStream, isCurrentRawDomainBasedSuperNight(), getOperatingMode(), this.mEnableParallelSession, this);
        }
        LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
        if (localBinder != null && CameraSettings.isPictureFlawCheckOn()) {
            localBinder.setOnSessionStatusCallBackListener(this.mSessionStatusCallbackListener);
        }
    }

    public void startScreenLight(final int i, final int i2) {
        if (!this.mPaused) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    FullScreenProtocol fullScreenProtocol = (FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
                    if (fullScreenProtocol != null) {
                        fullScreenProtocol.setScreenLightColor(i);
                        if (fullScreenProtocol.showScreenLight()) {
                            Camera2Module camera2Module = Camera2Module.this;
                            if (camera2Module.mActivity != null) {
                                camera2Module.mCamera2Device.setAELock(true);
                                Camera2Module.this.mActivity.setWindowBrightness(i2);
                            }
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
                Camera camera = Camera2Module.this.mActivity;
                if (camera != null) {
                    camera.restoreWindowBrightness();
                }
                FullScreenProtocol fullScreenProtocol = (FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
                String access$300 = Camera2Module.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("stopScreenLight: protocol = ");
                sb.append(fullScreenProtocol);
                sb.append(", mHandler = ");
                sb.append(Camera2Module.this.mHandler);
                Log.d(access$300, sb.toString());
                if (fullScreenProtocol != null) {
                    fullScreenProtocol.hideScreenLight();
                }
            }
        });
    }

    public /* synthetic */ void td() {
        this.mActivity.getSensorStateManager().setLieIndicatorEnabled(true);
    }

    /* access modifiers changed from: protected */
    public void trackModeCustomInfo(int i) {
        int i2 = this.mModuleIndex;
        if (i2 == 167) {
            trackManualInfo(i);
        } else if (i2 == 163) {
            if (isFaceBeautyOn(this.mBeautyValues)) {
                trackBeautyInfo(i, isFrontCamera(), new BeautyValues(this.mBeautyValues));
            }
            CameraStatUtil.trackUltraWidePictureTaken();
            CameraStatUtil.trackLyingDirectPictureTaken(this.mIsShowLyingDirectHintStatus);
        } else if (i2 == 165) {
            CameraStatUtil.trackUltraWidePictureTaken();
            CameraStatUtil.trackLyingDirectPictureTaken(this.mIsShowLyingDirectHintStatus);
        }
    }

    public void tryRemoveCountDownMessage() {
        Disposable disposable = this.mCountdownDisposable;
        if (disposable != null && !disposable.isDisposed()) {
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

    public /* synthetic */ void ud() {
        this.mCamera2Device.setDeviceOrientation(this.mOrientation);
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
        if (this.mIsGoogleLensAvailable) {
            Camera camera = this.mActivity;
            if (camera != null) {
                camera.runOnUiThread(new k(this));
            }
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
        TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
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
            String str = "auto";
            if ((getZoomRatio() != 1.0f || this.mMotionDetected) && this.mMutexModePicker.isHdr() && str.equals(componentValue)) {
                onHdrSceneChanged(false);
            }
            String str2 = "off";
            if (this.mIsMoonMode || this.mMotionDetected) {
                updateHDR(str2);
            } else {
                updateHDR(componentValue);
            }
            if ((!str2.equals(componentValue) || this.mAiSceneEnabled) && ((getZoomRatio() <= 1.0f || "normal".equals(componentValue)) && !this.mIsMoonMode && (!DataRepository.dataItemFeature().mb() || !isStandaloneMacroCamera() || !str.equals(componentValue)))) {
                resetAiSceneInHdrOrFlashOn();
                resetAsdSceneInHdrOrFlashChange();
                if (str.equals(componentValue)) {
                    this.mHdrCheckEnabled = true;
                } else {
                    this.mHdrCheckEnabled = false;
                }
                this.mCamera2Device.setHDRCheckerEnable(true);
            } else {
                this.mCamera2Device.setHDRCheckerEnable(false);
                this.mHdrCheckEnabled = false;
            }
        }
    }

    public void updateManualEvAdjust() {
        if (this.mModuleIndex == 167) {
            String manualValue = getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default));
            String manualValue2 = getManualValue(CameraSettings.KEY_QC_ISO, getString(R.string.pref_camera_iso_default));
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("MODE_MANUAL: exposureTime = ");
            sb.append(manualValue);
            sb.append("iso = ");
            sb.append(manualValue2);
            Log.d(str, sb.toString());
            boolean z = b.Ij() ? getString(R.string.pref_camera_exposuretime_default).equals(manualValue) : getString(R.string.pref_camera_iso_default).equals(manualValue2) || getString(R.string.pref_camera_exposuretime_default).equals(manualValue);
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new m(this, z));
            }
            if (1 == this.mCamera2Device.getFocusMode() && this.m3ALocked) {
                Camera camera = this.mActivity;
                if (camera != null) {
                    camera.runOnUiThread(new f(this));
                }
                unlockAEAF();
            }
        }
    }

    public void updateMoon(boolean z) {
        String str = "]";
        String str2 = ",";
        if (z) {
            this.mIsMoonMode = true;
            if (!DataRepository.dataItemFeature().ac()) {
                this.mCamera2Device.setSuperResolution(false);
            }
            updateFocusMode();
            updateHDRPreference();
            this.mCurrentAiScene = 35;
            this.mCamera2Device.setASDScene(35);
            resumePreviewInWorkThread();
            if (this.mZoomSupported) {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(20.0f);
                String str3 = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("updateMoon(): Override zoom ratio range to: [");
                sb.append(getMinZoomRatio());
                sb.append(str2);
                sb.append(getMaxZoomRatio());
                sb.append(str);
                Log.d(str3, sb.toString());
            }
            MainContentProtocol mainContentProtocol = (MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            if (mainContentProtocol != null) {
                mainContentProtocol.clearFocusView(1);
            }
        } else if (this.mIsMoonMode) {
            this.mIsMoonMode = false;
            setFocusMode(this.mFocusManager.setFocusMode(CameraSettings.getFocusMode()));
            updateHDRPreference();
            this.mCamera2Device.setASDScene(-35);
            initializeZoomRangeFromCapabilities();
            String str4 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("updateMoon(): Restore zoom ratio range to: [");
            sb2.append(getMinZoomRatio());
            sb2.append(str2);
            sb2.append(getMaxZoomRatio());
            sb2.append(str);
            Log.d(str4, sb2.toString());
            if (getZoomRatio() > getMaxZoomRatio()) {
                onZoomingActionUpdate(getMaxZoomRatio(), -1);
            }
        }
        DualController dualController = (DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null) {
            dualController.hideSlideView();
        }
    }

    public void updateMoonNight() {
        this.mIsMoonMode = false;
        closeMoonMode(10, 0);
        ((ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).closeMutexElement(SupportedConfigFactory.CLOSE_BY_AI, 193);
        setFlashMode("0");
        updateMfnr(true);
        updateOIS();
        setAiSceneEffect(10);
        this.mCurrentAiScene = 10;
        resumePreviewInWorkThread();
    }

    public void updateOnTripMode() {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            ASDScene[] aSDSceneArr = this.mAsdScenes;
            if (aSDSceneArr != null) {
                camera2Proxy.setOnTripodModeStatus(aSDSceneArr);
            }
        }
    }

    public void updatePreviewSurface() {
        MainContentProtocol mainContentProtocol = this.mMainProtocol;
        if (mainContentProtocol != null) {
            mainContentProtocol.initEffectCropView();
        }
        checkDisplayOrientation();
        if (this.mActivity != null) {
            CameraSize cameraSize = this.mPreviewSize;
            if (cameraSize != null) {
                updateCameraScreenNailSize(cameraSize.width, cameraSize.height);
            }
            if (this.mCamera2Device != null) {
                SurfaceTexture surfaceTexture = this.mActivity.getCameraScreenNail().getSurfaceTexture();
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("updatePreviewSurface: surfaceTexture = ");
                sb.append(surfaceTexture);
                Log.d(str, sb.toString());
                if (surfaceTexture != null) {
                    this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
                }
                this.mCamera2Device.updateDeferPreviewSession(new Surface(surfaceTexture));
            }
        }
    }

    public void updateSATZooming(boolean z) {
        if (DataRepository.dataItemFeature().vb() && HybridZoomingSystem.IS_3_OR_MORE_SAT) {
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

    public /* synthetic */ void vd() {
        showOrHideChip(false);
        this.mIsFaceConflict = false;
        this.mIsUltraWideConflict = false;
        this.mIsAiConflict = false;
    }

    public /* synthetic */ void wd() {
        this.mFocusManager.cancelFocus();
    }
}
