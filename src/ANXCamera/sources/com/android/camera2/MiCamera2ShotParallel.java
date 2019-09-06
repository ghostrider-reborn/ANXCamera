package com.android.camera2;

import android.graphics.Rect;
import android.hardware.camera2.CaptureResult;
import android.media.Image;
import android.util.Size;
import android.view.Surface;
import com.android.camera.CameraSize;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.parallel.AlgoConnector;
import com.xiaomi.camera.base.CameraDeviceUtil;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.GraphDescriptorBean;

public abstract class MiCamera2ShotParallel<T> extends MiCamera2Shot<T> {
    private static final String TAG = "ShotParallelBase";
    protected final Rect mActiveArraySize;
    CameraSize mCapturedImageSize;
    protected CaptureResult mPreviewCaptureResult;

    MiCamera2ShotParallel(MiCamera2 miCamera2) {
        super(miCamera2);
        this.mActiveArraySize = miCamera2.getCapabilities().getActiveArraySize();
    }

    private boolean hasDualCamera() {
        return this.mMiCamera.getId() == Camera2DataContainer.getInstance().getSATFrontCameraId() || this.mMiCamera.getId() == Camera2DataContainer.getInstance().getBokehFrontCameraId() || this.mMiCamera.getId() == Camera2DataContainer.getInstance().getSATCameraId() || this.mMiCamera.getId() == Camera2DataContainer.getInstance().getBokehCameraId() || this.mMiCamera.getId() == Camera2DataContainer.getInstance().getUltraWideBokehCameraId();
    }

    /* access modifiers changed from: 0000 */
    public void configParallelSession(Size size) {
        GraphDescriptorBean graphDescriptorBean;
        boolean isPortraitModule = ModuleManager.isPortraitModule();
        String str = TAG;
        if (isPortraitModule) {
            int i = hasDualCamera() ? 2 : 1;
            StringBuilder sb = new StringBuilder();
            sb.append("configParallelSession: inputStreamNum = ");
            sb.append(i);
            Log.d(str, sb.toString());
            graphDescriptorBean = new GraphDescriptorBean(32770, i, true, CameraDeviceUtil.getCameraCombinationMode(this.mMiCamera.getId()));
        } else {
            graphDescriptorBean = ModuleManager.isManualModule() ? new GraphDescriptorBean(32771, 1, true, CameraDeviceUtil.getCameraCombinationMode(this.mMiCamera.getId())) : ModuleManager.isUltraPixel() ? new GraphDescriptorBean(33011, 1, true, CameraDeviceUtil.getCameraCombinationMode(this.mMiCamera.getId())) : new GraphDescriptorBean(0, 1, true, CameraDeviceUtil.getCameraCombinationMode(this.mMiCamera.getId()));
        }
        int width = size.getWidth();
        int height = size.getHeight();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("configParallelSession: pictureSize = ");
        sb2.append(size);
        Log.d(str, sb2.toString());
        AlgoConnector.getInstance().getLocalBinder().configCaptureSession(new BufferFormat(width, height, 35, graphDescriptorBean));
        this.mCapturedImageSize = new CameraSize(size);
    }

    /* access modifiers changed from: 0000 */
    public void configParallelSession(Size size, int i) {
        GraphDescriptorBean graphDescriptorBean;
        boolean isPortraitModule = ModuleManager.isPortraitModule();
        String str = TAG;
        if (isPortraitModule) {
            int i2 = hasDualCamera() ? 2 : 1;
            StringBuilder sb = new StringBuilder();
            sb.append("configParallelSession: inputStreamNum = ");
            sb.append(i2);
            Log.d(str, sb.toString());
            graphDescriptorBean = new GraphDescriptorBean(32770, i2, true, i);
        } else {
            graphDescriptorBean = new GraphDescriptorBean(0, 1, true, i);
        }
        int width = size.getWidth();
        int height = size.getHeight();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("configParallelSession: pictureSize = ");
        sb2.append(size);
        Log.d(str, sb2.toString());
        AlgoConnector.getInstance().getLocalBinder().configCaptureSession(new BufferFormat(width, height, 35, graphDescriptorBean));
        this.mCapturedImageSize = new CameraSize(size);
    }

    /* access modifiers changed from: protected */
    public Surface getMainCaptureSurface() {
        return this.mMiCamera.getMainCaptureSurface();
    }

    /* access modifiers changed from: protected */
    public boolean isIn3OrMoreSatMode() {
        return this.mMiCamera.isIn3OrMoreSatMode();
    }

    /* access modifiers changed from: protected */
    public void notifyResultData(T t) {
    }

    /* access modifiers changed from: protected */
    public void onImageReceived(Image image, int i) {
    }
}
