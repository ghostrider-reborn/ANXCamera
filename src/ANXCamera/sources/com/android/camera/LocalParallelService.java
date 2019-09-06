package com.android.camera;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.view.Surface;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.storage.ImageSaver;
import com.mi.config.b;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.PostProcessor;
import com.xiaomi.camera.core.PostProcessor.PostProcessStatusCallback;
import com.xiaomi.camera.imagecodec.ReprocessorFactory;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.MiCameraAlgo;
import com.xiaomi.protocol.ICustomCaptureResult;
import com.xiaomi.protocol.IImageReaderParameterSets;
import com.xiaomi.protocol.ISessionStatusCallBackListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocalParallelService extends Service {
    /* access modifiers changed from: private */
    public static final String TAG = "LocalParallelService";
    /* access modifiers changed from: private */
    public LocalBinder mLocalBinder;
    /* access modifiers changed from: private */
    public int mMaxParallelRequestNumber;
    /* access modifiers changed from: private */
    public PostProcessStatusCallback mPostProcessStatusCallback = new PostProcessStatusCallback() {
        public void onImagePostProcessEnd(ParallelTaskData parallelTaskData) {
            if (LocalParallelService.this.mServiceStatusListenerRef != null && LocalParallelService.this.mServiceStatusListenerRef.get() != null) {
                ((ServiceStatusListener) LocalParallelService.this.mServiceStatusListenerRef.get()).onImagePostProcessEnd(parallelTaskData);
            }
        }

        public void onImagePostProcessStart(ParallelTaskData parallelTaskData) {
            if (LocalParallelService.this.mServiceStatusListenerRef != null && LocalParallelService.this.mServiceStatusListenerRef.get() != null) {
                ((ServiceStatusListener) LocalParallelService.this.mServiceStatusListenerRef.get()).onImagePostProcessStart(parallelTaskData);
            }
        }

        public void onPostProcessorClosed(PostProcessor postProcessor) {
            if (LocalParallelService.this.mLocalBinder != null) {
                LocalParallelService.this.mLocalBinder.onPostProcessorClosed(postProcessor);
            }
        }
    };
    /* access modifiers changed from: private */
    public WeakReference<ServiceStatusListener> mServiceStatusListenerRef;

    public class LocalBinder extends Binder {
        private List<PostProcessor> mAlivePostProcessor = new ArrayList();
        private BufferFormat mCurrentBufferFormat;
        private List<IImageReaderParameterSets> mCurrentParams;
        private PostProcessor mCurrentPostProcessor;

        LocalBinder() {
            ReprocessorFactory.setPreferredReprocessorType(LocalParallelService.this);
            if (b.Cn) {
                ReprocessorFactory.getReprocessor().setVirtualCameraIds("5", "6");
            }
            ReprocessorFactory.getReprocessor().init(LocalParallelService.this);
            MiCameraAlgo.init(LocalParallelService.this);
        }

        private void initCurrentPostProcessor(@NonNull List<IImageReaderParameterSets> list) {
            this.mCurrentParams = list;
            LocalParallelService localParallelService = LocalParallelService.this;
            this.mCurrentPostProcessor = new PostProcessor(localParallelService, localParallelService.mPostProcessStatusCallback);
            this.mCurrentPostProcessor.setMaxParallelRequestNumber(LocalParallelService.this.mMaxParallelRequestNumber);
            String access$400 = LocalParallelService.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("initCurrentPostProcessor: create a new PostProcessor: ");
            sb.append(this.mCurrentPostProcessor);
            sb.append(" | maxParallelRequestNumber: ");
            sb.append(LocalParallelService.this.mMaxParallelRequestNumber);
            Log.d(access$400, sb.toString());
            this.mCurrentBufferFormat = null;
            this.mAlivePostProcessor.add(this.mCurrentPostProcessor);
        }

        private boolean isSetsEquals(List<IImageReaderParameterSets> list, List<IImageReaderParameterSets> list2) {
            boolean z = false;
            if (!(list == null || list2 == null)) {
                if (list.size() != list2.size()) {
                    return false;
                }
                int i = 0;
                for (IImageReaderParameterSets iImageReaderParameterSets : list) {
                    Iterator it = list2.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (iImageReaderParameterSets.equals((IImageReaderParameterSets) it.next())) {
                                i++;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                if (list.size() == i) {
                    z = true;
                }
            }
            return z;
        }

        /* access modifiers changed from: private */
        public void onPostProcessorClosed(PostProcessor postProcessor) {
            this.mAlivePostProcessor.remove(postProcessor);
        }

        public List<Surface> configCaptureOutputBuffer(@NonNull List<IImageReaderParameterSets> list) throws RemoteException {
            if (!list.isEmpty()) {
                if (isSetsEquals(list, this.mCurrentParams)) {
                    PostProcessor postProcessor = this.mCurrentPostProcessor;
                    if (postProcessor != null && !postProcessor.isStopping() && !this.mCurrentPostProcessor.getSurfaceList().isEmpty()) {
                        Log.d(LocalParallelService.TAG, "configCaptureOutputBuffer: sets is not changed, return the old.");
                        return new ArrayList(this.mCurrentPostProcessor.getSurfaceList());
                    }
                }
                PostProcessor postProcessor2 = this.mCurrentPostProcessor;
                if (postProcessor2 != null && !postProcessor2.isStopping()) {
                    this.mCurrentPostProcessor.destroyWhenTasksFinished();
                }
                initCurrentPostProcessor(list);
                return this.mCurrentPostProcessor.configHALOutputSurface(list);
            }
            throw new RemoteException("List is empty");
        }

        public void configCaptureSession(@NonNull BufferFormat bufferFormat) {
            if (!bufferFormat.equals(this.mCurrentBufferFormat)) {
                this.mCurrentBufferFormat = bufferFormat;
                long currentTimeMillis = System.currentTimeMillis();
                this.mCurrentPostProcessor.configCaptureSession(this.mCurrentBufferFormat);
                String access$400 = LocalParallelService.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("configCaptureSession: cost: ");
                sb.append(System.currentTimeMillis() - currentTimeMillis);
                Log.d(access$400, sb.toString());
                return;
            }
            Log.d(LocalParallelService.TAG, "configCaptureSession: bufferFormat keeps unchanged");
        }

        public void configMaxParallelRequestNumber(int i) {
            LocalParallelService.this.mMaxParallelRequestNumber = i;
            PostProcessor postProcessor = this.mCurrentPostProcessor;
            if (postProcessor != null) {
                postProcessor.setMaxParallelRequestNumber(LocalParallelService.this.mMaxParallelRequestNumber);
            }
        }

        public boolean isIdle() {
            for (PostProcessor isIdle : this.mAlivePostProcessor) {
                if (!isIdle.isIdle()) {
                    return false;
                }
            }
            return true;
        }

        public boolean needWaitProcess() {
            return this.mCurrentPostProcessor.needWaitImageClose() || this.mCurrentPostProcessor.needWaitAlgorithmEngine();
        }

        public void onCaptureCompleted(ICustomCaptureResult iCustomCaptureResult, boolean z) {
            this.mCurrentPostProcessor.getCaptureStatusListener().onCaptureCompleted(iCustomCaptureResult, z);
        }

        public void onCaptureFailed(long j, int i) {
            this.mCurrentPostProcessor.getCaptureStatusListener().onCaptureFailed(j, i);
        }

        public void onCaptureStarted(ParallelTaskData parallelTaskData) {
            this.mCurrentPostProcessor.getCaptureStatusListener().onCaptureStarted(parallelTaskData);
        }

        public void onServiceDestroy() {
            ReprocessorFactory.getReprocessor().deInit();
            MiCameraAlgo.deInit();
        }

        public void setCallerIdentity(int i) {
            PostProcessor postProcessor = this.mCurrentPostProcessor;
            if (postProcessor != null) {
                postProcessor.setToken(i);
            }
        }

        public void setImageSaver(ImageSaver imageSaver) {
            this.mCurrentPostProcessor.setImageSaver(imageSaver);
        }

        public void setJpegOutputSize(int i, int i2) {
            ReprocessorFactory.getReprocessor().setJpegOutputSize(i, i2);
        }

        public void setOnPictureTakenListener(ServiceStatusListener serviceStatusListener) {
            LocalParallelService.this.mServiceStatusListenerRef = new WeakReference(serviceStatusListener);
        }

        public void setOnSessionStatusCallBackListener(ISessionStatusCallBackListener iSessionStatusCallBackListener) {
            PostProcessor postProcessor = this.mCurrentPostProcessor;
            if (postProcessor != null) {
                postProcessor.setOnSessionStatusCallBackListener(iSessionStatusCallBackListener);
            }
        }

        public void stopPostProcessor(int i) {
            PostProcessor postProcessor = this.mCurrentPostProcessor;
            if (postProcessor != null && !postProcessor.isStopping()) {
                String access$400 = LocalParallelService.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("stopPostProcessor: ");
                sb.append(this.mCurrentPostProcessor);
                Log.d(access$400, sb.toString());
                int token = this.mCurrentPostProcessor.getToken();
                if (token == -1 || i == token) {
                    this.mCurrentPostProcessor.destroyWhenTasksFinished();
                }
            }
        }
    }

    public interface ServiceStatusListener {
        void onImagePostProcessEnd(ParallelTaskData parallelTaskData);

        void onImagePostProcessStart(ParallelTaskData parallelTaskData);
    }

    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: start");
        return this.mLocalBinder;
    }

    public void onCreate() {
        Log.d(TAG, "onCreate: start");
        if (!DataRepository.dataItemFeature().Jc()) {
            Log.d(TAG, "This device does not support Algo up, do nothing.");
            stopSelf();
            return;
        }
        this.mLocalBinder = new LocalBinder();
        super.onCreate();
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy: start");
        LocalBinder localBinder = this.mLocalBinder;
        if (localBinder != null) {
            localBinder.onServiceDestroy();
            this.mLocalBinder = null;
        }
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        Log.d(TAG, "onStartCommand: start");
        return super.onStartCommand(intent, i, i2);
    }

    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: start");
        return super.onUnbind(intent);
    }
}
