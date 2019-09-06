package com.android.camera.storage;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.location.Location;
import android.net.Uri;
import com.android.camera.CameraSettings;
import com.android.camera.Exif;
import com.android.camera.Util;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.draw_mode.DrawJPEGAttribute;
import com.android.camera.log.Log;
import com.android.camera.watermark.WaterMarkData;
import com.android.camera2.ArcsoftDepthMap;
import com.android.gallery3d.exif.ExifInterface;
import com.xiaomi.camera.base.Constants.ShotType;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.ParallelTaskDataParameter;
import com.xiaomi.camera.core.PictureInfo;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

public abstract class AbstractSaveRequest implements SaveRequest {
    private static final String TAG = "AbstractSaveRequest";
    public int height;
    protected ParallelTaskData mParallelTaskData;
    private WeakReference<SaverCallback> mSaverCallbackRef;
    public int orientation;
    public int width;

    private DrawJPEGAttribute getDrawJPEGAttribute(byte[] bArr, int i, int i2, int i3, boolean z, int i4, int i5, Location location, String str, int i6, int i7, float f2, String str2, boolean z2, boolean z3, String str3, List<WaterMarkData> list, boolean z4, PictureInfo pictureInfo, int i8, int i9) {
        int i10 = i4;
        int i11 = i5;
        Location location2 = location;
        DrawJPEGAttribute drawJPEGAttribute = new DrawJPEGAttribute(bArr, z, i10 > i11 ? Math.max(i, i2) : Math.min(i, i2), i11 > i10 ? Math.max(i, i2) : Math.min(i, i2), i4, i5, i3, EffectController.getInstance().copyEffectRectAttribute(), location2 == null ? null : new Location(location2), str, System.currentTimeMillis(), i6, i7, f2, pictureInfo.isFrontMirror(), str2, z2, pictureInfo, list, CameraSettings.isDualCameraWaterMarkOpen() || CameraSettings.isFrontCameraWaterMarkOpen(), z3, CameraSettings.isTimeWaterMarkOpen() ? str3 : null, z4, i8, i9);
        return drawJPEGAttribute;
    }

    private SaverCallback getSaverCallback() {
        WeakReference<SaverCallback> weakReference = this.mSaverCallbackRef;
        if (weakReference != null) {
            return (SaverCallback) weakReference.get();
        }
        return null;
    }

    private void parserMimojiCaptureTask(ParallelTaskData parallelTaskData) {
        int i;
        int i2;
        ParallelTaskDataParameter parallelTaskDataParameter;
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        byte[] jpegImageData = parallelTaskData.getJpegImageData();
        int width2 = dataParameter.getPictureSize().getWidth();
        int height2 = dataParameter.getPictureSize().getHeight();
        int jpegRotation = dataParameter.getJpegRotation();
        int filterId = dataParameter.getFilterId();
        ExifInterface exif = ExifInterface.getExif(jpegImageData);
        int orientation2 = ExifInterface.getOrientation(exif);
        boolean z = EffectController.getInstance().hasEffect() || filterId != FilterInfo.FILTER_ID_NONE;
        String createJpegName = Util.createJpegName(System.currentTimeMillis());
        if (parallelTaskData.isAdaptiveSnapshotSize()) {
            int imageWidth = ExifInterface.getImageWidth(exif);
            i = ExifInterface.getImageHeight(exif);
            i2 = imageWidth;
        } else if ((jpegRotation + orientation2) % 180 == 0) {
            i2 = width2;
            i = height2;
        } else {
            i = width2;
            i2 = height2;
        }
        if (z || dataParameter.isHasWaterMark()) {
            SaverCallback saverCallback = getSaverCallback();
            if (saverCallback != null) {
                parallelTaskDataParameter = dataParameter;
                SaverCallback saverCallback2 = saverCallback;
                DrawJPEGAttribute drawJPEGAttribute = getDrawJPEGAttribute(jpegImageData, dataParameter.getPreviewSize().getWidth(), dataParameter.getPreviewSize().getHeight(), filterId, parallelTaskData.isNeedThumbnail(), i2, i, dataParameter.getLocation(), createJpegName, dataParameter.getShootOrientation(), jpegRotation, dataParameter.getShootRotation(), dataParameter.getAlgorithmName(), dataParameter.isHasWaterMark(), dataParameter.isUtralPixelWaterMark(), dataParameter.getTimeWaterMarkString(), dataParameter.getFaceWaterMarkList(), false, dataParameter.getPictureInfo(), parallelTaskData.getCurrentModuleIndex(), parallelTaskData.getPreviewThumbnailHash());
                saverCallback2.processorJpegSync(false, drawJPEGAttribute);
                jpegImageData = drawJPEGAttribute.mData;
            } else {
                parallelTaskDataParameter = dataParameter;
                Log.d(TAG, "parserMimojiCaptureTask(): saverCallback is null");
            }
        } else {
            parallelTaskDataParameter = dataParameter;
        }
        reFillSaveRequest(jpegImageData, parallelTaskData.isNeedThumbnail(), createJpegName, null, System.currentTimeMillis(), null, parallelTaskDataParameter.getLocation(), width2, height2, null, jpegRotation, false, false, true, false, false, Util.ALGORITHM_NAME_MIMOJI_CAPTURE, parallelTaskDataParameter.getPictureInfo(), parallelTaskData.getPreviewThumbnailHash());
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0173  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x017c  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x01b1  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x01d4  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x01dd  */
    private void parserNormalDualTask(ParallelTaskData parallelTaskData) {
        int i;
        int i2;
        String str;
        String str2;
        int[] iArr;
        byte[] bArr;
        byte[] bArr2;
        byte[] bArr3;
        String str3;
        byte[] bArr4;
        boolean isDepthMapData = ArcsoftDepthMap.isDepthMapData(parallelTaskData.getPortraitDepthData());
        byte[] jpegImageData = parallelTaskData.getJpegImageData();
        byte[] portraitRawData = parallelTaskData.getPortraitRawData();
        byte[] portraitDepthData = parallelTaskData.getPortraitDepthData();
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        int filterId = dataParameter.getFilterId();
        boolean z = EffectController.getInstance().hasEffect() || filterId != FilterInfo.FILTER_ID_NONE;
        int width2 = dataParameter.getPictureSize().getWidth();
        int height2 = dataParameter.getPictureSize().getHeight();
        ExifInterface exif = ExifInterface.getExif(jpegImageData);
        int orientation2 = ExifInterface.getOrientation(exif);
        int jpegRotation = dataParameter.getJpegRotation();
        if (parallelTaskData.isAdaptiveSnapshotSize()) {
            width2 = ExifInterface.getImageWidth(exif);
            height2 = ExifInterface.getImageHeight(exif);
        } else if ((jpegRotation + orientation2) % 180 != 0) {
            i = width2;
            i2 = height2;
            if (!parallelTaskData.isShot2Gallery()) {
                str = Util.getFileTitleFromPath(parallelTaskData.getSavePath());
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(Util.createJpegName(System.currentTimeMillis()));
                sb.append(dataParameter.getSuffix());
                str = sb.toString();
            }
            String str4 = str;
            String str5 = TAG;
            DrawJPEGAttribute drawJPEGAttribute = null;
            if (!z) {
                SaverCallback saverCallback = getSaverCallback();
                if (saverCallback != null) {
                    SaverCallback saverCallback2 = saverCallback;
                    String str6 = str5;
                    int i3 = filterId;
                    DrawJPEGAttribute drawJPEGAttribute2 = getDrawJPEGAttribute(jpegImageData, dataParameter.getPreviewSize().getWidth(), dataParameter.getPreviewSize().getHeight(), filterId, parallelTaskData.isNeedThumbnail(), i2, i, dataParameter.getLocation(), str4, dataParameter.getShootOrientation(), orientation2, dataParameter.getShootRotation(), dataParameter.getAlgorithmName(), dataParameter.isHasWaterMark(), false, dataParameter.getTimeWaterMarkString(), dataParameter.getFaceWaterMarkList(), false, dataParameter.getPictureInfo(), parallelTaskData.getCurrentModuleIndex(), -1);
                    if (isDepthMapData) {
                        drawJPEGAttribute = getDrawJPEGAttribute(portraitRawData, dataParameter.getPreviewSize().getWidth(), dataParameter.getPreviewSize().getHeight(), i3, parallelTaskData.isNeedThumbnail(), i2, i, dataParameter.getLocation(), str4, dataParameter.getShootOrientation(), orientation2, dataParameter.getShootRotation(), dataParameter.getAlgorithmName(), false, false, dataParameter.getTimeWaterMarkString(), dataParameter.getFaceWaterMarkList(), true, dataParameter.getPictureInfo(), parallelTaskData.getCurrentModuleIndex(), -1);
                    }
                    DrawJPEGAttribute drawJPEGAttribute3 = drawJPEGAttribute;
                    saverCallback2.processorJpegSync(false, drawJPEGAttribute2, drawJPEGAttribute3);
                    bArr3 = drawJPEGAttribute2.mData;
                    byte[] bArr5 = drawJPEGAttribute2.mDataOfTheRegionUnderWatermarks;
                    int[] iArr2 = drawJPEGAttribute2.mCoordinatesOfTheRegionUnderWatermarks;
                    if (isDepthMapData) {
                        portraitRawData = drawJPEGAttribute3.mData;
                    }
                    iArr = iArr2;
                    bArr = bArr5;
                    bArr2 = portraitRawData;
                    str2 = str6;
                    if (isDepthMapData) {
                        str3 = str2;
                        bArr4 = Util.composeDepthMapPicture(bArr3, portraitDepthData, bArr2, bArr, iArr, dataParameter.isHasDualWaterMark(), dataParameter.isHasFrontWaterMark(), dataParameter.getLightingPattern(), dataParameter.getTimeWaterMarkString(), dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), dataParameter.isMirror(), dataParameter.isBokehFrontCamera(), dataParameter.getPictureInfo());
                    } else {
                        str3 = str2;
                        bArr4 = Util.composeMainSubPicture(bArr3, bArr, iArr);
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("insertNormalDualTask: isShot2Gallery = ");
                    sb2.append(parallelTaskData.isShot2Gallery());
                    Log.d(str3, sb2.toString());
                    if (parallelTaskData.isShot2Gallery()) {
                        parallelTaskData.refillJpegData(bArr4);
                        parserParallelDualTask(parallelTaskData);
                        return;
                    }
                    ParallelTaskData parallelTaskData2 = parallelTaskData;
                    reFillSaveRequest(bArr4, parallelTaskData.isNeedThumbnail(), str4, null, System.currentTimeMillis(), null, dataParameter.getLocation(), i2, i, null, orientation2, false, false, true, false, false, dataParameter.getAlgorithmName(), dataParameter.getPictureInfo(), -1);
                    return;
                }
                str2 = str5;
                Log.d(str2, "parserNormalDualTask(): saverCallback is null");
            } else {
                str2 = str5;
            }
            bArr3 = jpegImageData;
            bArr = null;
            iArr = null;
            bArr2 = portraitRawData;
            if (isDepthMapData) {
            }
            StringBuilder sb22 = new StringBuilder();
            sb22.append("insertNormalDualTask: isShot2Gallery = ");
            sb22.append(parallelTaskData.isShot2Gallery());
            Log.d(str3, sb22.toString());
            if (parallelTaskData.isShot2Gallery()) {
            }
        }
        i2 = width2;
        i = height2;
        if (!parallelTaskData.isShot2Gallery()) {
        }
        String str42 = str;
        String str52 = TAG;
        DrawJPEGAttribute drawJPEGAttribute4 = null;
        if (!z) {
        }
        bArr3 = jpegImageData;
        bArr = null;
        iArr = null;
        bArr2 = portraitRawData;
        if (isDepthMapData) {
        }
        StringBuilder sb222 = new StringBuilder();
        sb222.append("insertNormalDualTask: isShot2Gallery = ");
        sb222.append(parallelTaskData.isShot2Gallery());
        Log.d(str3, sb222.toString());
        if (parallelTaskData.isShot2Gallery()) {
        }
    }

    private void parserParallelBurstTask(ParallelTaskData parallelTaskData) {
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        StringBuilder sb = new StringBuilder();
        sb.append("insertParallelBurstTask: path=");
        sb.append(parallelTaskData.getSavePath());
        String sb2 = sb.toString();
        String str = TAG;
        Log.d(str, sb2);
        byte[] populateExif = populateExif(parallelTaskData.getJpegImageData(), parallelTaskData);
        byte[] dataOfTheRegionUnderWatermarks = parallelTaskData.getDataOfTheRegionUnderWatermarks();
        int[] coordinatesOfTheRegionUnderWatermarks = parallelTaskData.getCoordinatesOfTheRegionUnderWatermarks();
        int width2 = dataParameter.getPictureSize().getWidth();
        int height2 = dataParameter.getPictureSize().getHeight();
        int orientation2 = Exif.getOrientation(populateExif);
        int jpegRotation = dataParameter.getJpegRotation();
        Log.d(str, String.format(Locale.ENGLISH, "insertParallelBurstTask: %d x %d, %d : %d", new Object[]{Integer.valueOf(width2), Integer.valueOf(height2), Integer.valueOf(jpegRotation), Integer.valueOf(orientation2)}));
        if ((jpegRotation + orientation2) % 180 != 0) {
            int i = height2;
            height2 = width2;
            width2 = i;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("insertParallelBurstTask: result = ");
        sb3.append(width2);
        sb3.append("x");
        sb3.append(height2);
        Log.d(str, sb3.toString());
        String fileTitleFromPath = Util.getFileTitleFromPath(parallelTaskData.getSavePath());
        String str2 = fileTitleFromPath;
        StringBuilder sb4 = new StringBuilder();
        sb4.append("insertParallelBurstTask: ");
        sb4.append(fileTitleFromPath);
        Log.d(str, sb4.toString());
        int i2 = width2;
        int i3 = height2;
        reFillSaveRequest(Util.composeMainSubPicture(populateExif, dataOfTheRegionUnderWatermarks, coordinatesOfTheRegionUnderWatermarks), parallelTaskData.isNeedThumbnail(), str2, null, System.currentTimeMillis(), null, dataParameter.getLocation(), i2, i3, null, orientation2, false, false, parallelTaskData.isNeedThumbnail(), false, true, dataParameter.getAlgorithmName(), dataParameter.getPictureInfo(), -1);
    }

    private void parserParallelDualTask(ParallelTaskData parallelTaskData) {
        byte[] bArr;
        int i;
        ParallelTaskData parallelTaskData2 = parallelTaskData;
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        StringBuilder sb = new StringBuilder();
        sb.append("addParallel: path=");
        sb.append(parallelTaskData.getSavePath());
        Log.d(TAG, sb.toString());
        byte[] populateExif = populateExif(parallelTaskData.getJpegImageData(), parallelTaskData2);
        byte[] dataOfTheRegionUnderWatermarks = parallelTaskData.getDataOfTheRegionUnderWatermarks();
        int[] coordinatesOfTheRegionUnderWatermarks = parallelTaskData.getCoordinatesOfTheRegionUnderWatermarks();
        if ((6 == parallelTaskData.getParallelType() || 8 == parallelTaskData.getParallelType() || 7 == parallelTaskData.getParallelType() || -6 == parallelTaskData.getParallelType() || -7 == parallelTaskData.getParallelType()) && ArcsoftDepthMap.isDepthMapData(parallelTaskData.getPortraitDepthData())) {
            i = -7;
            bArr = Util.composeDepthMapPicture(populateExif, parallelTaskData.getPortraitDepthData(), parallelTaskData.getPortraitRawData(), dataOfTheRegionUnderWatermarks, coordinatesOfTheRegionUnderWatermarks, dataParameter.isHasDualWaterMark(), dataParameter.isHasFrontWaterMark(), dataParameter.getLightingPattern(), dataParameter.getTimeWaterMarkString(), dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), dataParameter.isMirror(), dataParameter.isBokehFrontCamera(), dataParameter.getPictureInfo());
        } else {
            i = -7;
            if (parallelTaskData.isLiveShotTask()) {
                bArr = Util.composeLiveShotPicture(populateExif, dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), parallelTaskData.getMicroVideoData(), parallelTaskData.getCoverFrameTimestamp(), dataParameter.isHasDualWaterMark(), dataParameter.isHasFrontWaterMark(), dataParameter.getTimeWaterMarkString(), dataOfTheRegionUnderWatermarks, coordinatesOfTheRegionUnderWatermarks);
            } else {
                bArr = Util.composeMainSubPicture(populateExif, dataOfTheRegionUnderWatermarks, coordinatesOfTheRegionUnderWatermarks);
            }
        }
        if (parallelTaskData.getParallelType() == i || parallelTaskData.getParallelType() == -6 || parallelTaskData.getParallelType() == -5) {
            ExifInterface exif = ExifInterface.getExif(bArr);
            this.orientation = ExifInterface.getOrientation(exif);
            this.width = ExifInterface.getImageWidth(exif);
            parallelTaskData2.refillJpegData(bArr);
            return;
        }
        reFillSaveRequest(bArr, parallelTaskData.getTimestamp(), dataParameter.getLocation(), dataParameter.getJpegRotation(), parallelTaskData.getSavePath(), dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), parallelTaskData.isNeedThumbnail(), dataParameter.getAlgorithmName(), dataParameter.getPictureInfo());
    }

    private void parserPreviewShotTask(ParallelTaskData parallelTaskData) {
        PictureInfo pictureInfo;
        String str;
        int i;
        int i2;
        int i3;
        Location location;
        byte[] jpegImageData = parallelTaskData.getJpegImageData();
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        if (dataParameter != null) {
            int width2 = dataParameter.getOutputSize().getWidth();
            int height2 = dataParameter.getOutputSize().getHeight();
            Location location2 = dataParameter.getLocation();
            String algorithmName = dataParameter.getAlgorithmName();
            PictureInfo pictureInfo2 = dataParameter.getPictureInfo();
            i = dataParameter.getOrientation();
            i2 = height2;
            str = algorithmName;
            pictureInfo = pictureInfo2;
            i3 = width2;
            location = location2;
        } else {
            location = null;
            str = null;
            pictureInfo = null;
            i3 = 0;
            i2 = 0;
            i = 0;
        }
        PerformanceTracker.trackImageSaver(jpegImageData, 0);
        Util.getFileTitleFromPath(parallelTaskData.getSavePath());
        reFillSaveRequest(jpegImageData, parallelTaskData.isNeedThumbnail(), parallelTaskData.getSavePath(), System.currentTimeMillis(), location, i3, i2, i, true, true, str, pictureInfo);
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x012e  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0153  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x01b0  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0221  */
    private void parserSingleTask(ParallelTaskData parallelTaskData) {
        int i;
        int i2;
        String str;
        int i3;
        String str2;
        int i4;
        int i5;
        String str3;
        int[] iArr;
        int i6;
        int i7;
        AbstractSaveRequest abstractSaveRequest;
        ParallelTaskData parallelTaskData2;
        String str4;
        byte[] composeLiveShotPicture;
        ParallelTaskData parallelTaskData3 = parallelTaskData;
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        int filterId = dataParameter.getFilterId();
        boolean z = EffectController.getInstance().hasEffect() || filterId != FilterInfo.FILTER_ID_NONE;
        byte[] jpegImageData = parallelTaskData.getJpegImageData();
        int width2 = dataParameter.getPictureSize().getWidth();
        int height2 = dataParameter.getPictureSize().getHeight();
        ExifInterface exif = ExifInterface.getExif(jpegImageData);
        int orientation2 = ExifInterface.getOrientation(exif);
        int jpegRotation = dataParameter.getJpegRotation();
        if (parallelTaskData.isAdaptiveSnapshotSize()) {
            int imageWidth = ExifInterface.getImageWidth(exif);
            i = ExifInterface.getImageHeight(exif);
            i2 = imageWidth;
        } else if ((jpegRotation + orientation2) % 180 == 0) {
            i2 = width2;
            i = height2;
        } else {
            i = width2;
            i2 = height2;
        }
        if (parallelTaskData.isShot2Gallery()) {
            str = Util.getFileTitleFromPath(parallelTaskData.getSavePath());
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(Util.createJpegName(System.currentTimeMillis()));
            sb.append(dataParameter.getSuffix());
            str = sb.toString();
        }
        String str5 = str;
        byte[] bArr = null;
        String str6 = TAG;
        if (z) {
            SaverCallback saverCallback = getSaverCallback();
            if (saverCallback != null) {
                String str7 = str6;
                str2 = str5;
                i3 = orientation2;
                SaverCallback saverCallback2 = saverCallback;
                DrawJPEGAttribute drawJPEGAttribute = getDrawJPEGAttribute(jpegImageData, dataParameter.getPreviewSize().getWidth(), dataParameter.getPreviewSize().getHeight(), filterId, parallelTaskData.isNeedThumbnail(), i2, i, dataParameter.getLocation(), str2, dataParameter.getShootOrientation(), i3, dataParameter.getShootRotation(), dataParameter.getAlgorithmName(), dataParameter.isHasWaterMark(), dataParameter.isUtralPixelWaterMark(), dataParameter.getTimeWaterMarkString(), dataParameter.getFaceWaterMarkList(), false, dataParameter.getPictureInfo(), parallelTaskData.getCurrentModuleIndex(), parallelTaskData.getPreviewThumbnailHash());
                saverCallback2.processorJpegSync(false, drawJPEGAttribute);
                jpegImageData = drawJPEGAttribute.mData;
                int i8 = drawJPEGAttribute.mWidth;
                int i9 = drawJPEGAttribute.mHeight;
                byte[] bArr2 = drawJPEGAttribute.mDataOfTheRegionUnderWatermarks;
                iArr = drawJPEGAttribute.mCoordinatesOfTheRegionUnderWatermarks;
                i5 = i8;
                i4 = i9;
                bArr = bArr2;
                str3 = str7;
                if (parallelTaskData.isLiveShotTask()) {
                    composeLiveShotPicture = Util.composeMainSubPicture(jpegImageData, bArr, iArr);
                    if (composeLiveShotPicture == null || composeLiveShotPicture.length < jpegImageData.length) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Failed to compose main sub photos: ");
                        str4 = str2;
                        sb2.append(str4);
                        Log.e(str3, sb2.toString());
                        str2 = str4;
                        if (parallelTaskData.getParallelType() != -2) {
                            parallelTaskData2 = parallelTaskData;
                            i7 = i5;
                            i6 = i4;
                            abstractSaveRequest = this;
                        } else if (parallelTaskData.getParallelType() == -3) {
                            abstractSaveRequest = this;
                            parallelTaskData2 = parallelTaskData;
                            i7 = i5;
                            i6 = i4;
                        } else {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("insertSingleTask: isShot2Gallery = ");
                            sb3.append(parallelTaskData.isShot2Gallery());
                            Log.d(str3, sb3.toString());
                            if (parallelTaskData.isShot2Gallery()) {
                                parallelTaskData.refillJpegData(jpegImageData);
                                parallelTaskData.getDataParameter().updateOutputSize(i5, i4);
                                parserParallelDualTask(parallelTaskData);
                                return;
                            }
                            ParallelTaskData parallelTaskData4 = parallelTaskData;
                            reFillSaveRequest(jpegImageData, parallelTaskData.isNeedThumbnail(), str2, null, System.currentTimeMillis(), null, dataParameter.getLocation(), i5, i4, null, i3, false, false, true, false, false, dataParameter.getAlgorithmName(), dataParameter.getPictureInfo(), parallelTaskData.getPreviewThumbnailHash());
                            return;
                        }
                        abstractSaveRequest.width = i7;
                        abstractSaveRequest.height = i6;
                        abstractSaveRequest.orientation = i3;
                        parallelTaskData2.refillJpegData(jpegImageData);
                    }
                } else {
                    str4 = str2;
                    composeLiveShotPicture = Util.composeLiveShotPicture(jpegImageData, width2, height2, parallelTaskData.getMicroVideoData(), parallelTaskData.getCoverFrameTimestamp(), dataParameter.isHasDualWaterMark(), dataParameter.isHasFrontWaterMark(), dataParameter.getTimeWaterMarkString(), bArr, iArr);
                    if (composeLiveShotPicture == null || composeLiveShotPicture.length < jpegImageData.length) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("Failed to compose LiveShot photo: ");
                        sb4.append(str4);
                        Log.e(str3, sb4.toString());
                        str2 = str4;
                        if (parallelTaskData.getParallelType() != -2) {
                        }
                        abstractSaveRequest.width = i7;
                        abstractSaveRequest.height = i6;
                        abstractSaveRequest.orientation = i3;
                        parallelTaskData2.refillJpegData(jpegImageData);
                    }
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(dataParameter.getPrefix());
                    sb5.append(str4);
                    str2 = sb5.toString();
                }
                jpegImageData = composeLiveShotPicture;
                if (parallelTaskData.getParallelType() != -2) {
                }
                abstractSaveRequest.width = i7;
                abstractSaveRequest.height = i6;
                abstractSaveRequest.orientation = i3;
                parallelTaskData2.refillJpegData(jpegImageData);
            }
            str2 = str5;
            i3 = orientation2;
            str3 = str6;
            Log.d(str3, "parserSingleTask(): saverCallback is null");
        } else {
            str3 = str6;
            str2 = str5;
            i3 = orientation2;
        }
        iArr = null;
        i5 = i2;
        i4 = i;
        if (parallelTaskData.isLiveShotTask()) {
        }
        jpegImageData = composeLiveShotPicture;
        if (parallelTaskData.getParallelType() != -2) {
        }
        abstractSaveRequest.width = i7;
        abstractSaveRequest.height = i6;
        abstractSaveRequest.orientation = i3;
        parallelTaskData2.refillJpegData(jpegImageData);
    }

    private static byte[] populateExif(byte[] bArr, ParallelTaskData parallelTaskData) {
        if (parallelTaskData == null || parallelTaskData.getCaptureResult() == null) {
            return bArr;
        }
        return Util.appendCaptureResultToExif(bArr, parallelTaskData.getDataParameter().getPictureSize().getWidth(), parallelTaskData.getDataParameter().getPictureSize().getHeight(), parallelTaskData.getDataParameter().getJpegRotation(), System.currentTimeMillis(), parallelTaskData.getDataParameter().getLocation(), (CameraMetadataNative) parallelTaskData.getCaptureResult().getResults());
    }

    /* access modifiers changed from: protected */
    public int caculateMemoryUsed(ParallelTaskData parallelTaskData) {
        int i = 0;
        if (parallelTaskData == null) {
            return 0;
        }
        byte[] jpegImageData = parallelTaskData.getJpegImageData();
        int length = jpegImageData == null ? 0 : jpegImageData.length;
        byte[] portraitRawData = parallelTaskData.getPortraitRawData();
        int length2 = length + (portraitRawData == null ? 0 : portraitRawData.length);
        byte[] portraitDepthData = parallelTaskData.getPortraitDepthData();
        if (portraitDepthData != null) {
            i = portraitDepthData.length;
        }
        return i + length2;
    }

    /* access modifiers changed from: protected */
    public void parserParallelTaskData() {
        ParallelTaskData parallelTaskData = this.mParallelTaskData;
        if (parallelTaskData == null) {
            Log.v(TAG, "mParallelTaskData is null, ignore");
            return;
        }
        switch (parallelTaskData.getParallelType()) {
            case ShotType.INTENT_PARALLEL_DUAL_SHOT /*-7*/:
            case ShotType.INTENT_PARALLEL_SINGLE_PORTRAIT /*-6*/:
            case ShotType.INTENT_PARALLEL_SINGLE_SHOT /*-5*/:
            case 5:
            case 6:
            case 7:
            case 8:
                parserParallelDualTask(this.mParallelTaskData);
                break;
            case -4:
                parserMimojiCaptureTask(this.mParallelTaskData);
                break;
            case -3:
            case -2:
            case 0:
            case 1:
            case 10:
                parserSingleTask(this.mParallelTaskData);
                break;
            case -1:
                parserPreviewShotTask(this.mParallelTaskData);
                break;
            case 2:
                parserNormalDualTask(this.mParallelTaskData);
                break;
            case 9:
                parserParallelBurstTask(this.mParallelTaskData);
                break;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown shot type: ");
                sb.append(this.mParallelTaskData.getParallelType());
                throw new RuntimeException(sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void reFillSaveRequest(byte[] bArr, long j, Location location, int i, String str, int i2, int i3, boolean z, String str2, PictureInfo pictureInfo) {
    }

    /* access modifiers changed from: protected */
    public void reFillSaveRequest(byte[] bArr, boolean z, String str, long j, Location location, int i, int i2, int i3, boolean z2, boolean z3, String str2, PictureInfo pictureInfo) {
    }

    /* access modifiers changed from: protected */
    public void reFillSaveRequest(byte[] bArr, boolean z, String str, String str2, long j, Uri uri, Location location, int i, int i2, ExifInterface exifInterface, int i3, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String str3, PictureInfo pictureInfo, int i4) {
    }

    public void setParallelTaskData(ParallelTaskData parallelTaskData) {
        this.mParallelTaskData = parallelTaskData;
    }

    public void setSaverCallback(SaverCallback saverCallback) {
        this.mSaverCallbackRef = new WeakReference<>(saverCallback);
    }
}
