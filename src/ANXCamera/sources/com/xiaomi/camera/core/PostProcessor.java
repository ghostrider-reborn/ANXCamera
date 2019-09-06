package com.xiaomi.camera.core;

import android.content.Context;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.view.Surface;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.storage.ImageSaver;
import com.mi.config.b;
import com.ss.android.ttve.common.TEDefine;
import com.xiaomi.camera.base.ImageUtil;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.CaptureData.CaptureDataBean;
import com.xiaomi.camera.core.ImageProcessor.ImageProcessorStatusCallback;
import com.xiaomi.camera.core.ParallelDataZipper.DataListener;
import com.xiaomi.camera.imagecodec.ImagePool;
import com.xiaomi.camera.imagecodec.ImagePool.ImageFormat;
import com.xiaomi.camera.imagecodec.ReprocessData;
import com.xiaomi.camera.imagecodec.ReprocessData.OnDataAvailableListener;
import com.xiaomi.camera.imagecodec.ReprocessorFactory;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.MiCameraAlgo;
import com.xiaomi.engine.ResultData;
import com.xiaomi.engine.TaskSession;
import com.xiaomi.engine.TaskSession.SessionStatusCallback;
import com.xiaomi.protocol.ICustomCaptureResult;
import com.xiaomi.protocol.IImageReaderParameterSets;
import com.xiaomi.protocol.ISessionStatusCallBackListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import miui.os.Build;

public class PostProcessor {
    private static final int MSG_RESULT_FLAW = 0;
    /* access modifiers changed from: private */
    public static final String TAG = "PostProcessor";
    /* access modifiers changed from: private */
    public CaptureDataListener mCaptureDataListener = new CaptureDataListener() {
        public void onCaptureDataAvailable(@NonNull CaptureData captureData) {
            long captureTimestamp = captureData.getCaptureTimestamp();
            int algoType = captureData.getAlgoType();
            if (algoType == 2 || algoType == 5) {
                CaptureDataBean multiFrameProcessResult = captureData.getMultiFrameProcessResult();
                if (multiFrameProcessResult != null) {
                    ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(captureTimestamp));
                    long timeStamp = multiFrameProcessResult.getResult().getTimeStamp();
                    String access$100 = PostProcessor.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("[1] onCaptureDataAvailable: timestamp: ");
                    sb.append(captureTimestamp);
                    sb.append(" | ");
                    sb.append(timeStamp);
                    Log.d(access$100, sb.toString());
                    if (timeStamp != captureTimestamp) {
                        parallelTaskData.setTimestamp(timeStamp);
                        PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(captureTimestamp));
                        PostProcessor.this.mParallelTaskHashMap.put(Long.valueOf(timeStamp), parallelTaskData);
                    }
                    if (algoType == 5 && parallelTaskData.getDataParameter().isSaveGroupshotPrimitive()) {
                        for (int i = 0; i < captureData.getCaptureDataBeanList().size(); i++) {
                            ParallelTaskData cloneTaskData = parallelTaskData.cloneTaskData(i);
                            long timeStamp2 = ((CaptureDataBean) captureData.getCaptureDataBeanList().get(i)).getResult().getTimeStamp();
                            if (timeStamp2 == timeStamp) {
                                timeStamp2++;
                            }
                            cloneTaskData.setTimestamp(timeStamp2);
                            PostProcessor.this.mParallelTaskHashMap.put(Long.valueOf(timeStamp2), cloneTaskData);
                            String access$1002 = PostProcessor.TAG;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("[1] onCaptureDataAvailable: add ");
                            sb2.append(timeStamp2);
                            Log.d(access$1002, sb2.toString());
                        }
                    }
                    captureData.getCaptureDataBeanList().add(multiFrameProcessResult);
                } else {
                    throw new RuntimeException("No multi-frame process result!");
                }
            }
            if (algoType == 4) {
                if (Build.IS_DEBUGGABLE) {
                    Log.d(PostProcessor.TAG, "onCaptureDataAvailable: start process multi-shot image...");
                }
                CaptureDataBean captureDataBean = (CaptureDataBean) captureData.getCaptureDataBeanList().get(0);
                ICustomCaptureResult result = captureDataBean.getResult();
                Image mainImage = captureDataBean.getMainImage();
                ParallelTaskData parallelTaskData2 = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(captureTimestamp));
                if (parallelTaskData2 != null) {
                    parallelTaskData2.setCaptureResult(result);
                    PostProcessor.this.mImageProcessorStatusCb.onImageProcessStart(mainImage.getTimestamp());
                    PostProcessor.this.mImageProcessorStatusCb.onImageProcessed(mainImage, 0, false);
                } else {
                    String access$1003 = PostProcessor.TAG;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("[1] onCaptureDataAvailable: no captureResult ");
                    sb3.append(captureTimestamp);
                    Log.e(access$1003, sb3.toString());
                }
                mainImage.close();
                onOriginalImageClosed(mainImage);
            } else {
                List<CaptureDataBean> captureDataBeanList = captureData.getCaptureDataBeanList();
                if (captureDataBeanList != null && !captureDataBeanList.isEmpty()) {
                    for (CaptureDataBean captureDataBean2 : captureDataBeanList) {
                        if (captureDataBean2.isFirstResult()) {
                            long timeStamp3 = captureDataBean2.getResult().getTimeStamp();
                            ParallelTaskData parallelTaskData3 = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(timeStamp3));
                            if (parallelTaskData3 != null) {
                                parallelTaskData3.setCaptureResult(captureDataBean2.getResult());
                            } else {
                                String access$1004 = PostProcessor.TAG;
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("[1] onCaptureDataAvailable: no task data with timestamp ");
                                sb4.append(timeStamp3);
                                Log.e(access$1004, sb4.toString(), new RuntimeException());
                                Image mainImage2 = captureDataBean2.getMainImage();
                                mainImage2.close();
                                onOriginalImageClosed(mainImage2);
                                ImagePool.getInstance().releaseImage(mainImage2);
                                Image subImage = captureDataBean2.getSubImage();
                                if (subImage != null) {
                                    subImage.close();
                                    onOriginalImageClosed(subImage);
                                    ImagePool.getInstance().releaseImage(subImage);
                                }
                            }
                        }
                    }
                    ImageProcessor imageProcessor = captureData.getImageProcessor();
                    if (imageProcessor != PostProcessor.this.mImageProcessor) {
                        Log.w(PostProcessor.TAG, "[1] onCaptureDataAvailable: image processor switched");
                    }
                    imageProcessor.dispatchTask(captureDataBeanList);
                } else if (!Build.IS_DEBUGGABLE) {
                    Log.e(PostProcessor.TAG, "[1] onCaptureDataAvailable: There are no result to process!");
                } else {
                    throw new RuntimeException("There are no result to process!");
                }
            }
        }

        public void onOriginalImageClosed(Image image) {
            if (PostProcessor.this.mImageMemoryManager != null && image != null) {
                String access$100 = PostProcessor.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("onOriginalImageClosed: ");
                sb.append(image);
                Log.d(access$100, sb.toString());
                PostProcessor.this.mImageMemoryManager.releaseAnImage(image);
            }
        }
    };
    private CaptureStatusListener mCaptureStatusListener = new CaptureStatusListener();
    /* access modifiers changed from: private */
    public ImageMemoryManager mImageMemoryManager;
    /* access modifiers changed from: private */
    public ImageProcessor mImageProcessor;
    /* access modifiers changed from: private */
    public ImageProcessorStatusCallback mImageProcessorStatusCb = new ImageProcessorStatusCallback() {
        public ParallelTaskData getParallelTaskData(long j) {
            return (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(j));
        }

        /* JADX WARNING: Incorrect type for immutable var: ssa=android.media.Image, code=java.lang.Object, for r3v0, types: [android.media.Image, java.lang.Object] */
        public void onImageProcessFailed(Object obj, String str) {
            String access$100 = PostProcessor.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onImageProcessFailed: image=");
            if (obj == null) {
                obj = TEDefine.FACE_BEAUTY_NULL;
            }
            sb.append(obj);
            sb.append(" reason=");
            sb.append(str);
            Log.d(access$100, sb.toString());
        }

        public void onImageProcessStart(long j) {
            if (PostProcessor.this.mPostProcessStatusCallback != null) {
                String access$100 = PostProcessor.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("onImageProcessStart: get parallelTaskData: ");
                sb.append(j);
                Log.d(access$100, sb.toString());
                PostProcessor.this.mPostProcessStatusCallback.onImagePostProcessStart((ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(j)));
            }
        }

        public void onImageProcessed(Image image, int i, boolean z) {
            long timestamp = image.getTimestamp();
            StringBuilder sb = new StringBuilder();
            sb.append(timestamp);
            sb.append(File.separator);
            sb.append(i);
            String sb2 = sb.toString();
            String access$100 = PostProcessor.TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("[2] onImageProcessed: ");
            sb3.append(image);
            sb3.append(" | ");
            sb3.append(sb2);
            Log.d(access$100, sb3.toString());
            if (i == 2) {
                PerformanceTracker.trackJpegReprocess(i, 0);
                PostProcessor.this.mJpegEncoderListener.onJpegAvailable(ImageUtil.getFirstPlane(image), sb2);
                return;
            }
            ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(timestamp));
            if (parallelTaskData != null) {
                ICustomCaptureResult captureResult = parallelTaskData.getCaptureResult();
                String access$1002 = PostProcessor.TAG;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("[2] onImageProcessed: captureResult = ");
                sb4.append(captureResult.getResults());
                Log.d(access$1002, sb4.toString());
                ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
                ReprocessData reprocessData = new ReprocessData(image, sb2, captureResult, dataParameter.isFrontCamera(), dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), PostProcessor.this.mJpegEncoderListener);
                reprocessData.setFrontMirror(dataParameter.isMirror());
                reprocessData.setJpegQuality(dataParameter.getJpegQuality());
                reprocessData.setImageFromPool(z);
                PerformanceTracker.trackJpegReprocess(i, 0);
                try {
                    ReprocessorFactory.getReprocessor().submit(reprocessData);
                } catch (Exception unused) {
                    PostProcessor.this.mJpegEncoderListener.onError("nativeDetachImage failed for image!!!", sb2);
                }
            } else {
                StringBuilder sb5 = new StringBuilder();
                sb5.append("no parallelTaskData with timestamp ");
                sb5.append(timestamp);
                throw new RuntimeException(sb5.toString());
            }
        }

        public void onOriginalImageClosed(Image image) {
            PostProcessor.this.mCaptureDataListener.onOriginalImageClosed(image);
            String access$100 = PostProcessor.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onOriginalImageClosed: ");
            sb.append(image);
            Log.d(access$100, sb.toString());
        }
    };
    private List<ImageReader> mImageReaderList = new ArrayList();
    /* access modifiers changed from: private */
    public ImageSaver mImageSaver;
    /* access modifiers changed from: private */
    public OnDataAvailableListener mJpegEncoderListener = new OnDataAvailableListener() {
        public void onError(String str, String str2) {
            String[] split = str2.split(File.separator);
            long parseLong = Long.parseLong(split[0]);
            int parseInt = Integer.parseInt(split[1]);
            String access$100 = PostProcessor.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("[3] onError: ");
            sb.append(parseLong);
            String str3 = " | ";
            sb.append(str3);
            sb.append(parseInt);
            sb.append(str3);
            sb.append(str);
            Log.e(access$100, sb.toString());
            PerformanceTracker.trackJpegReprocess(parseInt, 1);
            ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(parseLong));
            if (parallelTaskData != null) {
                parallelTaskData.releaseImageData();
                PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(parseLong));
                String access$1002 = PostProcessor.TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("[3] onError: remove task ");
                sb2.append(parseLong);
                sb2.append(str3);
                sb2.append(parseInt);
                Log.e(access$1002, sb2.toString());
            }
            PostProcessor.this.tryToCloseSession();
        }

        public void onJpegAvailable(byte[] bArr, String str) {
            String[] split = str.split(File.separator);
            long parseLong = Long.parseLong(split[0]);
            int parseInt = Integer.parseInt(split[1]);
            String access$100 = PostProcessor.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("[3] onJpegAvailable: ");
            sb.append(parseLong);
            sb.append(" | ");
            sb.append(parseInt);
            Log.d(access$100, sb.toString());
            PerformanceTracker.trackJpegReprocess(parseInt, 1);
            ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(parseLong));
            if (parallelTaskData != null) {
                parallelTaskData.fillJpegData(bArr, parseInt);
                if (parallelTaskData.isJpegDataReady()) {
                    String access$1002 = PostProcessor.TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("[3] onJpegAvailable: save image start. dataLen=");
                    sb2.append(bArr.length);
                    Log.d(access$1002, sb2.toString());
                    if (PostProcessor.this.mImageSaver.onParallelProcessFinish(parallelTaskData, null, null)) {
                        parallelTaskData.releaseImageData();
                    }
                    if (PostProcessor.this.isNeedCallBackFinished(parallelTaskData) && PostProcessor.this.mPostProcessStatusCallback != null) {
                        PostProcessor.this.mPostProcessStatusCallback.onImagePostProcessEnd(parallelTaskData);
                    }
                    PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(parseLong));
                    String access$1003 = PostProcessor.TAG;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("[3] onJpegAvailable: parallelTaskHashMap remove ");
                    sb3.append(parseLong);
                    Log.d(access$1003, sb3.toString());
                } else {
                    Log.d(PostProcessor.TAG, "[3] onJpegAvailable: jpeg data isn't ready, save action has been ignored.");
                }
            } else {
                String access$1004 = PostProcessor.TAG;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("[3] onJpegAvailable: null task data. timestamp=");
                sb4.append(parseLong);
                Log.w(access$1004, sb4.toString());
            }
            PostProcessor.this.tryToStopBoost();
            PostProcessor.this.tryToCloseSession();
        }

        public void onYuvAvailable(Image image, String str) {
        }
    };
    private int mMaxParallelRequestNumber = 10;
    private MtkBoost mMtkBoost;
    /* access modifiers changed from: private */
    public ConcurrentHashMap<Long, ParallelTaskData> mParallelTaskHashMap = new ConcurrentHashMap<>();
    /* access modifiers changed from: private */
    public PostProcessStatusCallback mPostProcessStatusCallback;
    private SessionStatusCallback mSessionStatusCb = new SessionStatusCallback() {
        public void onSessionCallback(int i, String str, Object obj) {
            if (i != 0) {
                String access$100 = PostProcessor.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown result type ");
                sb.append(i);
                Log.e(access$100, sb.toString());
                return;
            }
            ResultData resultData = (ResultData) obj;
            if (!(PostProcessor.this.mSessionStatusCbListener == null || resultData == null)) {
                try {
                    PostProcessor.this.mSessionStatusCbListener.onSessionStatusFlawResultData(resultData.getResultId(), resultData.getFlawResult());
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            }
            String access$1002 = PostProcessor.TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("onSessionCallback FLAW getResultId: ");
            sb2.append(resultData.getResultId());
            sb2.append(", getFlawResult: ");
            sb2.append(resultData.getFlawResult());
            Log.d(access$1002, sb2.toString());
        }
    };
    /* access modifiers changed from: private */
    public ISessionStatusCallBackListener mSessionStatusCbListener;
    private boolean mShouldDestroyWhenTasksFinished = false;
    private List<Surface> mSurfaceList = new ArrayList();
    private int mToken = -1;
    private Handler mWorkerHandler;
    private HandlerThread mWorkerThread = new HandlerThread("CallbackHandleThread");
    /* access modifiers changed from: private */
    public DataListener mZipperResultListener = new DataListener() {
        public void onParallelDataAbandoned(CaptureData captureData) {
            if (captureData != null) {
                String access$100 = PostProcessor.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("onParallelDataAbandoned: ");
                sb.append(captureData);
                Log.d(access$100, sb.toString());
                PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(captureData.getCaptureTimestamp()));
                for (CaptureDataBean captureDataBean : captureData.getCaptureDataBeanList()) {
                    if (captureDataBean != null) {
                        Image mainImage = captureDataBean.getMainImage();
                        String access$1002 = PostProcessor.TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("onParallelDataAbandoned: mainImage = ");
                        sb2.append(mainImage);
                        Log.d(access$1002, sb2.toString());
                        if (mainImage != null) {
                            mainImage.close();
                            PostProcessor.this.mCaptureDataListener.onOriginalImageClosed(mainImage);
                        }
                        Image subImage = captureDataBean.getSubImage();
                        String access$1003 = PostProcessor.TAG;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("onParallelDataAbandoned: subImage = ");
                        sb3.append(subImage);
                        Log.d(access$1003, sb3.toString());
                        if (subImage != null) {
                            subImage.close();
                            PostProcessor.this.mCaptureDataListener.onOriginalImageClosed(subImage);
                        }
                    }
                }
            }
        }

        public void onParallelDataAvailable(@NonNull CaptureData captureData) {
            String access$100 = PostProcessor.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("[z] onParallelDataAvailable: ");
            sb.append(captureData.getCaptureTimestamp());
            Log.d(access$100, sb.toString());
            if (Build.IS_DEBUGGABLE) {
                for (CaptureDataBean captureDataBean : captureData.getCaptureDataBeanList()) {
                    Log.d(PostProcessor.TAG, "[z] onParallelDataAvailable: ------------------------");
                    String access$1002 = PostProcessor.TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("[z] Result timestamp: ");
                    sb2.append(captureDataBean.getResult().getTimeStamp());
                    Log.d(access$1002, sb2.toString());
                }
            }
            int algoType = captureData.getAlgoType();
            String access$1003 = PostProcessor.TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("[z] onParallelDataAvailable: algoType = ");
            sb3.append(algoType);
            Log.d(access$1003, sb3.toString());
            if (algoType != 4) {
                ImageFormat imageQueueKey = ImagePool.getInstance().toImageQueueKey(((CaptureDataBean) captureData.getCaptureDataBeanList().get(0)).getMainImage());
                if (ImagePool.getInstance().isImageQueueFull(imageQueueKey, 4)) {
                    Log.w(PostProcessor.TAG, "[z] wait image pool>>");
                    ImagePool.getInstance().waitIfImageQueueFull(imageQueueKey, 4, 0);
                    Log.w(PostProcessor.TAG, "[z] wait image pool<<");
                }
                PostProcessor.this.mImageProcessorStatusCb.onImageProcessStart(captureData.getCaptureTimestamp());
            }
            if (algoType == 2) {
                captureData.setMultiFrameProcessListener(PostProcessor.this.mCaptureDataListener);
                ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(captureData.getCaptureTimestamp()));
                if (parallelTaskData != null) {
                    captureData.setCapturedByFrontCamera(parallelTaskData.getDataParameter().isFrontCamera());
                }
                MultiFrameProcessor.getInstance().processData(captureData);
            } else if (algoType == 5) {
                captureData.setMultiFrameProcessListener(PostProcessor.this.mCaptureDataListener);
                ParallelTaskData parallelTaskData2 = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(captureData.getCaptureTimestamp()));
                if (parallelTaskData2 != null && parallelTaskData2.getDataParameter().isSaveGroupshotPrimitive()) {
                    captureData.setSaveInputImage(true);
                }
                MultiFrameProcessor.getInstance().processData(captureData);
            } else {
                PostProcessor.this.mCaptureDataListener.onCaptureDataAvailable(captureData);
            }
        }
    };

    public class CaptureStatusListener {
        public CaptureStatusListener() {
        }

        public void onCaptureCompleted(ICustomCaptureResult iCustomCaptureResult, boolean z) {
            String access$100 = PostProcessor.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("[0] onCaptureCompleted: timestamp = ");
            sb.append(iCustomCaptureResult.getTimeStamp());
            Log.d(access$100, sb.toString());
            ParallelDataZipper.getInstance().join(iCustomCaptureResult, z);
        }

        public void onCaptureFailed(long j, int i) {
            String access$100 = PostProcessor.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("[0] onCaptureFailed: reason = ");
            sb.append(i);
            sb.append(" timestamp = ");
            sb.append(j);
            Log.w(access$100, sb.toString());
            PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(j));
        }

        public void onCaptureStarted(@NonNull ParallelTaskData parallelTaskData) {
            PostProcessor.this.startMtkBoost();
            long timestamp = parallelTaskData.getTimestamp();
            String access$100 = PostProcessor.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("[0] onCaptureStarted: timestamp = ");
            sb.append(timestamp);
            Log.d(access$100, sb.toString());
            if (!parallelTaskData.isAbandoned()) {
                PostProcessor.this.mParallelTaskHashMap.put(Long.valueOf(timestamp), parallelTaskData);
            }
            CaptureData captureData = new CaptureData(parallelTaskData.getAlgoType(), PostProcessor.this.mImageProcessor instanceof DualCameraProcessor ? 2 : 1, parallelTaskData.getBurstNum(), timestamp, parallelTaskData.isAbandoned(), PostProcessor.this.mImageProcessor);
            captureData.setDataListener(PostProcessor.this.mZipperResultListener);
            ParallelDataZipper.getInstance().startTask(captureData);
        }
    }

    private static class ImageAvailableListener implements OnImageAvailableListener {
        private int mImageFlag;
        private ImageMemoryManager mMemoryManager;

        ImageAvailableListener(int i, ImageMemoryManager imageMemoryManager) {
            this.mImageFlag = i;
            this.mMemoryManager = imageMemoryManager;
        }

        public void onImageAvailable(ImageReader imageReader) {
            if (imageReader == null) {
                Log.e(PostProcessor.TAG, "[0] onImageAvailable: null imageReader!");
                return;
            }
            this.mMemoryManager.waitImageCloseIfNeeded();
            Image acquireNextImage = imageReader.acquireNextImage();
            String access$100 = PostProcessor.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("[0] onImageAvailable: timestamp = ");
            sb.append(acquireNextImage.getTimestamp());
            Log.d(access$100, sb.toString());
            this.mMemoryManager.holdAnImage(imageReader.hashCode(), acquireNextImage);
            if (Util.isDumpImageEnabled()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("dump_0_");
                sb2.append(acquireNextImage.getTimestamp());
                ImageUtil.dumpYuvImage(acquireNextImage, sb2.toString());
            }
            ParallelDataZipper.getInstance().join(acquireNextImage, this.mImageFlag);
        }
    }

    public interface PostProcessStatusCallback {
        void onImagePostProcessEnd(ParallelTaskData parallelTaskData);

        void onImagePostProcessStart(ParallelTaskData parallelTaskData);

        void onPostProcessorClosed(PostProcessor postProcessor);
    }

    public PostProcessor(Context context, PostProcessStatusCallback postProcessStatusCallback) {
        this.mWorkerThread.start();
        this.mWorkerHandler = new Handler(this.mWorkerThread.getLooper());
        this.mPostProcessStatusCallback = postProcessStatusCallback;
        init();
    }

    private int getMinHoldImageNum(List<IImageReaderParameterSets> list) {
        int i = 0;
        if (list == null || list.isEmpty()) {
            Log.e(TAG, "getMinHoldImageNum: empty param");
            return 0;
        }
        for (IImageReaderParameterSets iImageReaderParameterSets : list) {
            if (i == 0 || iImageReaderParameterSets.maxImages < i) {
                i = iImageReaderParameterSets.maxImages;
            }
        }
        return i;
    }

    /* access modifiers changed from: private */
    public boolean isNeedCallBackFinished(ParallelTaskData parallelTaskData) {
        boolean z = false;
        if (parallelTaskData == null) {
            return false;
        }
        if (parallelTaskData.getParallelType() == -7 || parallelTaskData.getParallelType() == -5 || parallelTaskData.getParallelType() == -6) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: private */
    public void startMtkBoost() {
        if (b.isMTKPlatform()) {
            if (this.mMtkBoost == null) {
                this.mMtkBoost = new MtkBoost();
            }
            this.mMtkBoost.startBoost();
        }
    }

    private void stopMtkBoost() {
        if (b.isMTKPlatform()) {
            MtkBoost mtkBoost = this.mMtkBoost;
            if (mtkBoost != null) {
                mtkBoost.stopBoost();
            }
        }
    }

    /* access modifiers changed from: private */
    public void tryToCloseSession() {
        if (!this.mParallelTaskHashMap.isEmpty() || !this.mShouldDestroyWhenTasksFinished) {
            Log.d(TAG, "tryToCloseSession: ignore");
            return;
        }
        Log.d(TAG, "tryToCloseSession: start");
        finish();
        deInit();
        PostProcessStatusCallback postProcessStatusCallback = this.mPostProcessStatusCallback;
        if (postProcessStatusCallback != null) {
            postProcessStatusCallback.onPostProcessorClosed(this);
        }
        stopMtkBoost();
    }

    /* access modifiers changed from: private */
    public void tryToStopBoost() {
        Log.d(TAG, "tryToStopBoost");
        if (isIdle()) {
            Log.d(TAG, "stopBoost");
            stopMtkBoost();
        }
    }

    public void configCaptureSession(BufferFormat bufferFormat) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("configCaptureSession: ");
        sb.append(bufferFormat);
        Log.d(str, sb.toString());
        boolean z = bufferFormat.getGraphDescriptor().getOperationModeID() == 32770;
        ImageProcessor imageProcessor = this.mImageProcessor;
        if (imageProcessor != null) {
            imageProcessor.stopWorkWhenIdle();
        }
        if (bufferFormat.getGraphDescriptor().getStreamNumber() == 2) {
            this.mImageProcessor = new DualCameraProcessor(this.mImageProcessorStatusCb, z, bufferFormat);
        } else {
            this.mImageProcessor = new SingleCameraProcessor(this.mImageProcessorStatusCb, z, bufferFormat);
        }
        this.mImageProcessor.startWork();
        TaskSession createSessionByOutputConfigurations = MiCameraAlgo.createSessionByOutputConfigurations(bufferFormat, this.mImageProcessor.configOutputConfigurations(bufferFormat), this.mSessionStatusCb);
        ImageProcessor imageProcessor2 = this.mImageProcessor;
        if (imageProcessor2 != null) {
            imageProcessor2.setTaskSession(createSessionByOutputConfigurations);
        }
    }

    public List<Surface> configHALOutputSurface(@NonNull List<IImageReaderParameterSets> list) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("configHALOutputSurface: paramsNum=");
        sb.append(list.size());
        Log.d(str, sb.toString());
        if (!this.mImageReaderList.isEmpty()) {
            this.mImageReaderList.clear();
        }
        if (!this.mSurfaceList.isEmpty()) {
            this.mSurfaceList.clear();
        }
        int minHoldImageNum = getMinHoldImageNum(list);
        this.mImageMemoryManager = new ImageMemoryManager(minHoldImageNum);
        String str2 = TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("configHALOutputSurface: holdNum=");
        sb2.append(minHoldImageNum);
        Log.d(str2, sb2.toString());
        for (IImageReaderParameterSets iImageReaderParameterSets : list) {
            ImageReader newInstance = ImageReader.newInstance(iImageReaderParameterSets.width, iImageReaderParameterSets.height, iImageReaderParameterSets.format, iImageReaderParameterSets.maxImages);
            newInstance.setOnImageAvailableListener(new ImageAvailableListener(iImageReaderParameterSets.targetCamera, this.mImageMemoryManager), this.mWorkerHandler);
            this.mSurfaceList.add(newInstance.getSurface());
            this.mImageReaderList.add(newInstance);
        }
        return this.mSurfaceList;
    }

    public void deInit() {
        if (!this.mImageReaderList.isEmpty()) {
            for (ImageReader close : this.mImageReaderList) {
                close.close();
            }
            this.mImageReaderList.clear();
        }
        if (!this.mSurfaceList.isEmpty()) {
            this.mSurfaceList.clear();
        }
    }

    public void destroyWhenTasksFinished() {
        this.mShouldDestroyWhenTasksFinished = true;
        tryToCloseSession();
    }

    public void finish() {
        ImageProcessor imageProcessor = this.mImageProcessor;
        if (imageProcessor != null) {
            imageProcessor.stopWork();
            this.mImageProcessor = null;
        }
        HandlerThread handlerThread = this.mWorkerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            try {
                this.mWorkerThread.join();
                this.mWorkerThread = null;
                this.mWorkerHandler = null;
            } catch (InterruptedException e2) {
                Log.w(TAG, "finish: failed!", e2);
            }
        }
    }

    public CaptureStatusListener getCaptureStatusListener() {
        return this.mCaptureStatusListener;
    }

    public List<Surface> getSurfaceList() {
        return this.mSurfaceList;
    }

    public int getToken() {
        return this.mToken;
    }

    public void init() {
    }

    public boolean isIdle() {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("isIdle ");
        sb.append(this.mParallelTaskHashMap.size());
        Log.d(str, sb.toString());
        return this.mParallelTaskHashMap.isEmpty();
    }

    public boolean isStopping() {
        return this.mShouldDestroyWhenTasksFinished;
    }

    public boolean needWaitAlgorithmEngine() {
        ImageProcessor imageProcessor = this.mImageProcessor;
        boolean z = imageProcessor != null && imageProcessor.getProcessingRequestNumber() >= this.mMaxParallelRequestNumber;
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("needWaitAlgorithmEngine: return ");
        sb.append(z);
        Log.c(str, sb.toString());
        return z;
    }

    public boolean needWaitImageClose() {
        ImageMemoryManager imageMemoryManager = this.mImageMemoryManager;
        boolean z = imageMemoryManager != null && imageMemoryManager.needWaitImageClose();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("needWaitImageClose: return ");
        sb.append(z);
        Log.c(str, sb.toString());
        return z;
    }

    public void setImageSaver(ImageSaver imageSaver) {
        this.mImageSaver = imageSaver;
    }

    public void setMaxParallelRequestNumber(int i) {
        if (i > 0) {
            this.mMaxParallelRequestNumber = i;
        }
    }

    public void setOnSessionStatusCallBackListener(ISessionStatusCallBackListener iSessionStatusCallBackListener) {
        this.mSessionStatusCbListener = iSessionStatusCallBackListener;
    }

    public void setToken(int i) {
        this.mToken = i;
    }
}
