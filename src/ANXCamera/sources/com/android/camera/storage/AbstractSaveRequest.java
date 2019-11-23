package com.android.camera.storage;

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
import com.mi.config.b;
import com.xiaomi.camera.base.Constants;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.ParallelTaskDataParameter;
import com.xiaomi.camera.core.PictureInfo;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

public abstract class AbstractSaveRequest implements SaveRequest {
    private static final String TAG = "AbstractSaveRequest";
    protected long date;
    public int height;
    protected ParallelTaskData mParallelTaskData;
    private WeakReference<SaverCallback> mSaverCallbackRef;
    public int orientation;
    public int width;

    private DrawJPEGAttribute getDrawJPEGAttribute(byte[] bArr, int i, int i2, int i3, boolean z, int i4, int i5, Location location, String str, int i6, int i7, float f, String str2, boolean z2, boolean z3, String str3, List<WaterMarkData> list, boolean z4, PictureInfo pictureInfo, int i8, int i9) {
        int i10 = i4;
        int i11 = i5;
        Location location2 = location;
        DrawJPEGAttribute drawJPEGAttribute = new DrawJPEGAttribute(bArr, z, i10 > i11 ? Math.max(i, i2) : Math.min(i, i2), i11 > i10 ? Math.max(i, i2) : Math.min(i, i2), i10, i11, i3, EffectController.getInstance().copyEffectRectAttribute(), location2 == null ? null : new Location(location2), str, System.currentTimeMillis(), i6, i7, f, pictureInfo.isFrontMirror(), str2, z2, pictureInfo, list, CameraSettings.isDualCameraWaterMarkOpen() || CameraSettings.isFrontCameraWaterMarkOpen(), z3, CameraSettings.isTimeWaterMarkOpen() ? str3 : null, z4, i8, i9);
        return drawJPEGAttribute;
    }

    private SaverCallback getSaverCallback() {
        if (this.mSaverCallbackRef != null) {
            return (SaverCallback) this.mSaverCallbackRef.get();
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
                DrawJPEGAttribute drawJPEGAttribute = getDrawJPEGAttribute(jpegImageData, dataParameter.getPreviewSize().getWidth(), dataParameter.getPreviewSize().getHeight(), filterId, parallelTaskData.isNeedThumbnail(), i2, i, dataParameter.getLocation(), createJpegName, dataParameter.getShootOrientation(), jpegRotation, dataParameter.getShootRotation(), dataParameter.getAlgorithmName(), dataParameter.isHasWaterMark(), dataParameter.isUtralPixelWaterMark(), dataParameter.getTimeWaterMarkString(), dataParameter.getFaceWaterMarkList(), false, dataParameter.getPictureInfo(), parallelTaskData.getCurrentModuleIndex(), parallelTaskData.getPreviewThumbnailHash());
                saverCallback.processorJpegSync(false, drawJPEGAttribute);
                jpegImageData = drawJPEGAttribute.mData;
            } else {
                parallelTaskDataParameter = dataParameter;
                Log.d(TAG, "parserMimojiCaptureTask(): saverCallback is null");
            }
        } else {
            parallelTaskDataParameter = dataParameter;
        }
        ParallelTaskDataParameter parallelTaskDataParameter2 = parallelTaskDataParameter;
        reFillSaveRequest(jpegImageData, parallelTaskData.isNeedThumbnail(), createJpegName, (String) null, parallelTaskData.getDateTakenTime(), (Uri) null, parallelTaskDataParameter2.getLocation(), width2, height2, (ExifInterface) null, jpegRotation, false, false, true, false, false, Util.ALGORITHM_NAME_MIMOJI_CAPTURE, parallelTaskDataParameter2.getPictureInfo(), parallelTaskData.getPreviewThumbnailHash());
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00a3  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x01a6  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x01af  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x01eb  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x020f  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0218  */
    private void parserNormalDualTask(ParallelTaskData parallelTaskData) {
        int i;
        int i2;
        String str;
        int[] iArr;
        byte[] bArr;
        byte[] bArr2;
        byte[] bArr3;
        ParallelTaskDataParameter parallelTaskDataParameter;
        DrawJPEGAttribute drawJPEGAttribute;
        ParallelTaskDataParameter parallelTaskDataParameter2;
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
                str = Util.createJpegName(System.currentTimeMillis()) + dataParameter.getSuffix();
            }
            String str2 = str;
            byte[] bArr4 = null;
            if (!z) {
                SaverCallback saverCallback = getSaverCallback();
                if (saverCallback != null) {
                    SaverCallback saverCallback2 = saverCallback;
                    int i3 = filterId;
                    ParallelTaskDataParameter parallelTaskDataParameter3 = dataParameter;
                    DrawJPEGAttribute drawJPEGAttribute2 = getDrawJPEGAttribute(jpegImageData, dataParameter.getPreviewSize().getWidth(), dataParameter.getPreviewSize().getHeight(), filterId, parallelTaskData.isNeedThumbnail(), i2, i, dataParameter.getLocation(), str2, dataParameter.getShootOrientation(), orientation2, dataParameter.getShootRotation(), dataParameter.getAlgorithmName(), dataParameter.isHasWaterMark(), false, dataParameter.getTimeWaterMarkString(), dataParameter.getFaceWaterMarkList(), false, dataParameter.getPictureInfo(), parallelTaskData.getCurrentModuleIndex(), -1);
                    if (isDepthMapData) {
                        parallelTaskDataParameter2 = parallelTaskDataParameter3;
                        drawJPEGAttribute = getDrawJPEGAttribute(portraitRawData, parallelTaskDataParameter2.getPreviewSize().getWidth(), parallelTaskDataParameter2.getPreviewSize().getHeight(), i3, parallelTaskData.isNeedThumbnail(), i2, i, parallelTaskDataParameter2.getLocation(), str2, parallelTaskDataParameter2.getShootOrientation(), orientation2, parallelTaskDataParameter2.getShootRotation(), parallelTaskDataParameter2.getAlgorithmName(), false, false, parallelTaskDataParameter2.getTimeWaterMarkString(), parallelTaskDataParameter2.getFaceWaterMarkList(), true, parallelTaskDataParameter2.getPictureInfo(), parallelTaskData.getCurrentModuleIndex(), -1);
                    } else {
                        parallelTaskDataParameter2 = parallelTaskDataParameter3;
                        drawJPEGAttribute = null;
                    }
                    saverCallback2.processorJpegSync(false, drawJPEGAttribute2, drawJPEGAttribute);
                    byte[] bArr5 = drawJPEGAttribute2.mData;
                    byte[] bArr6 = drawJPEGAttribute2.mDataOfTheRegionUnderWatermarks;
                    int[] iArr2 = drawJPEGAttribute2.mCoordinatesOfTheRegionUnderWatermarks;
                    if (b.rp.equals("tucana")) {
                        iArr2 = null;
                    } else {
                        bArr4 = bArr6;
                    }
                    if (isDepthMapData) {
                        portraitRawData = drawJPEGAttribute.mData;
                    }
                    iArr = iArr2;
                    parallelTaskDataParameter = parallelTaskDataParameter2;
                    bArr3 = bArr5;
                    bArr2 = portraitRawData;
                    bArr = bArr4;
                    byte[] composeDepthMapPicture = isDepthMapData ? Util.composeDepthMapPicture(bArr3, portraitDepthData, bArr2, bArr, iArr, parallelTaskDataParameter.isHasDualWaterMark(), parallelTaskDataParameter.isHasFrontWaterMark(), parallelTaskDataParameter.getLightingPattern(), parallelTaskDataParameter.getTimeWaterMarkString(), parallelTaskDataParameter.getOutputSize().getWidth(), parallelTaskDataParameter.getOutputSize().getHeight(), parallelTaskDataParameter.isMirror(), parallelTaskDataParameter.isBokehFrontCamera(), parallelTaskDataParameter.getJpegRotation(), parallelTaskDataParameter.getDeviceWatermarkParam(), parallelTaskDataParameter.getPictureInfo()) : Util.composeMainSubPicture(bArr3, bArr, iArr);
                    Log.d(TAG, "insertNormalDualTask: isShot2Gallery = " + parallelTaskData.isShot2Gallery());
                    if (parallelTaskData.isShot2Gallery()) {
                        parallelTaskData.refillJpegData(composeDepthMapPicture);
                        parserParallelDualTask(parallelTaskData);
                        return;
                    }
                    ParallelTaskData parallelTaskData2 = parallelTaskData;
                    reFillSaveRequest(composeDepthMapPicture, parallelTaskData.isNeedThumbnail(), str2, (String) null, parallelTaskData.getDateTakenTime(), (Uri) null, parallelTaskDataParameter.getLocation(), i2, i, (ExifInterface) null, orientation2, false, false, true, false, false, parallelTaskDataParameter.getAlgorithmName(), parallelTaskDataParameter.getPictureInfo(), -1);
                    return;
                }
                parallelTaskDataParameter = dataParameter;
                Log.d(TAG, "parserNormalDualTask(): saverCallback is null");
            } else {
                parallelTaskDataParameter = dataParameter;
            }
            bArr3 = jpegImageData;
            bArr2 = portraitRawData;
            bArr = null;
            iArr = null;
            if (isDepthMapData) {
            }
            Log.d(TAG, "insertNormalDualTask: isShot2Gallery = " + parallelTaskData.isShot2Gallery());
            if (parallelTaskData.isShot2Gallery()) {
            }
        }
        i2 = width2;
        i = height2;
        if (!parallelTaskData.isShot2Gallery()) {
        }
        String str22 = str;
        byte[] bArr42 = null;
        if (!z) {
        }
        bArr3 = jpegImageData;
        bArr2 = portraitRawData;
        bArr = null;
        iArr = null;
        if (isDepthMapData) {
        }
        Log.d(TAG, "insertNormalDualTask: isShot2Gallery = " + parallelTaskData.isShot2Gallery());
        if (parallelTaskData.isShot2Gallery()) {
        }
    }

    private void parserParallelBurstTask(ParallelTaskData parallelTaskData) {
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        Log.d(TAG, "insertParallelBurstTask: path=" + parallelTaskData.getSavePath());
        byte[] populateExif = populateExif(parallelTaskData.getJpegImageData(), parallelTaskData);
        byte[] dataOfTheRegionUnderWatermarks = parallelTaskData.getDataOfTheRegionUnderWatermarks();
        int[] coordinatesOfTheRegionUnderWatermarks = parallelTaskData.getCoordinatesOfTheRegionUnderWatermarks();
        int width2 = dataParameter.getPictureSize().getWidth();
        int height2 = dataParameter.getPictureSize().getHeight();
        int orientation2 = Exif.getOrientation(populateExif);
        int jpegRotation = dataParameter.getJpegRotation();
        Log.d(TAG, String.format(Locale.ENGLISH, "insertParallelBurstTask: %d x %d, %d : %d", new Object[]{Integer.valueOf(width2), Integer.valueOf(height2), Integer.valueOf(jpegRotation), Integer.valueOf(orientation2)}));
        if ((jpegRotation + orientation2) % 180 != 0) {
            int i = height2;
            height2 = width2;
            width2 = i;
        }
        Log.d(TAG, "insertParallelBurstTask: result = " + width2 + "x" + height2);
        String fileTitleFromPath = Util.getFileTitleFromPath(parallelTaskData.getSavePath());
        StringBuilder sb = new StringBuilder();
        sb.append("insertParallelBurstTask: ");
        sb.append(fileTitleFromPath);
        Log.d(TAG, sb.toString());
        boolean isNeedThumbnail = parallelTaskData.isNeedThumbnail();
        byte[] composeMainSubPicture = Util.composeMainSubPicture(populateExif, dataOfTheRegionUnderWatermarks, coordinatesOfTheRegionUnderWatermarks);
        boolean isNeedThumbnail2 = parallelTaskData.isNeedThumbnail();
        long dateTakenTime = parallelTaskData.getDateTakenTime();
        Location location = dataParameter.getLocation();
        String algorithmName = dataParameter.getAlgorithmName();
        PictureInfo pictureInfo = dataParameter.getPictureInfo();
        reFillSaveRequest(composeMainSubPicture, isNeedThumbnail2, fileTitleFromPath, (String) null, dateTakenTime, (Uri) null, location, width2, height2, (ExifInterface) null, orientation2, false, false, isNeedThumbnail, false, true, algorithmName, pictureInfo, -1);
    }

    private void parserParallelDualTask(ParallelTaskData parallelTaskData) {
        byte[] bArr;
        int i;
        ParallelTaskData parallelTaskData2 = parallelTaskData;
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        Log.d(TAG, "addParallel: path=" + parallelTaskData.getSavePath());
        byte[] populateExif = populateExif(parallelTaskData.getJpegImageData(), parallelTaskData2);
        byte[] dataOfTheRegionUnderWatermarks = parallelTaskData.getDataOfTheRegionUnderWatermarks();
        int[] coordinatesOfTheRegionUnderWatermarks = parallelTaskData.getCoordinatesOfTheRegionUnderWatermarks();
        if ((6 == parallelTaskData.getParallelType() || 8 == parallelTaskData.getParallelType() || 7 == parallelTaskData.getParallelType() || -6 == parallelTaskData.getParallelType() || -7 == parallelTaskData.getParallelType()) && ArcsoftDepthMap.isDepthMapData(parallelTaskData.getPortraitDepthData())) {
            i = -7;
            bArr = Util.composeDepthMapPicture(populateExif, parallelTaskData.getPortraitDepthData(), parallelTaskData.getPortraitRawData(), dataOfTheRegionUnderWatermarks, coordinatesOfTheRegionUnderWatermarks, dataParameter.isHasDualWaterMark(), dataParameter.isHasFrontWaterMark(), dataParameter.getLightingPattern(), dataParameter.getTimeWaterMarkString(), dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), dataParameter.isMirror(), dataParameter.isBokehFrontCamera(), dataParameter.getJpegRotation(), dataParameter.getDeviceWatermarkParam(), dataParameter.getPictureInfo());
        } else {
            i = -7;
            if (parallelTaskData.isLiveShotTask()) {
                bArr = Util.composeLiveShotPicture(populateExif, dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), parallelTaskData.getMicroVideoData(), parallelTaskData.getCoverFrameTimestamp(), dataParameter.isHasDualWaterMark(), dataParameter.isHasFrontWaterMark(), dataParameter.getTimeWaterMarkString(), dataParameter.getJpegRotation(), dataParameter.getDeviceWatermarkParam(), dataOfTheRegionUnderWatermarks, coordinatesOfTheRegionUnderWatermarks);
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
        reFillSaveRequest(bArr, parallelTaskData.getTimestamp(), parallelTaskData.getDateTakenTime(), dataParameter.getLocation(), dataParameter.getJpegRotation(), parallelTaskData.getSavePath(), dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), parallelTaskData.isNeedThumbnail(), dataParameter.getAlgorithmName(), dataParameter.getPictureInfo());
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
        reFillSaveRequest(jpegImageData, parallelTaskData.isNeedThumbnail(), parallelTaskData.getSavePath(), parallelTaskData.getDateTakenTime(), location, i3, i2, i, true, true, str, pictureInfo);
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x014d  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0178  */
    private void parserSingleTask(ParallelTaskData parallelTaskData) {
        int i;
        int i2;
        String str;
        ParallelTaskDataParameter parallelTaskDataParameter;
        int i3;
        String str2;
        int i4;
        int i5;
        byte[] bArr;
        int[] iArr;
        String str3;
        ParallelTaskDataParameter parallelTaskDataParameter2;
        ParallelTaskData parallelTaskData2 = parallelTaskData;
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
            str = Util.createJpegName(System.currentTimeMillis()) + dataParameter.getSuffix();
        }
        String str4 = str;
        if (z) {
            SaverCallback saverCallback = getSaverCallback();
            if (saverCallback != null) {
                str2 = str4;
                i3 = orientation2;
                parallelTaskDataParameter = dataParameter;
                DrawJPEGAttribute drawJPEGAttribute = getDrawJPEGAttribute(jpegImageData, dataParameter.getPreviewSize().getWidth(), dataParameter.getPreviewSize().getHeight(), filterId, parallelTaskData.isNeedThumbnail(), i2, i, dataParameter.getLocation(), str4, dataParameter.getShootOrientation(), i3, dataParameter.getShootRotation(), dataParameter.getAlgorithmName(), dataParameter.isHasWaterMark(), dataParameter.isUtralPixelWaterMark(), dataParameter.getTimeWaterMarkString(), dataParameter.getFaceWaterMarkList(), false, dataParameter.getPictureInfo(), parallelTaskData.getCurrentModuleIndex(), parallelTaskData.getPreviewThumbnailHash());
                saverCallback.processorJpegSync(false, drawJPEGAttribute);
                jpegImageData = drawJPEGAttribute.mData;
                int i6 = drawJPEGAttribute.mWidth;
                int i7 = drawJPEGAttribute.mHeight;
                bArr = drawJPEGAttribute.mDataOfTheRegionUnderWatermarks;
                iArr = drawJPEGAttribute.mCoordinatesOfTheRegionUnderWatermarks;
                if (b.rp.equals("tucana")) {
                    iArr = null;
                    bArr = null;
                }
                i5 = i6;
                i4 = i7;
                if (parallelTaskData.isLiveShotTask()) {
                    byte[] composeMainSubPicture = Util.composeMainSubPicture(jpegImageData, bArr, iArr);
                    if (composeMainSubPicture == null || composeMainSubPicture.length < jpegImageData.length) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Failed to compose main sub photos: ");
                        str3 = str2;
                        sb.append(str3);
                        Log.e(TAG, sb.toString());
                    } else {
                        jpegImageData = composeMainSubPicture;
                        str3 = str2;
                    }
                    parallelTaskDataParameter2 = parallelTaskDataParameter;
                } else {
                    str3 = str2;
                    parallelTaskDataParameter2 = parallelTaskDataParameter;
                    byte[] composeLiveShotPicture = Util.composeLiveShotPicture(jpegImageData, width2, height2, parallelTaskData.getMicroVideoData(), parallelTaskData.getCoverFrameTimestamp(), parallelTaskDataParameter2.isHasDualWaterMark(), parallelTaskDataParameter2.isHasFrontWaterMark(), parallelTaskDataParameter2.getTimeWaterMarkString(), parallelTaskDataParameter2.getJpegRotation(), parallelTaskDataParameter2.getDeviceWatermarkParam(), bArr, iArr);
                    if (composeLiveShotPicture == null || composeLiveShotPicture.length < jpegImageData.length) {
                        Log.e(TAG, "Failed to compose LiveShot photo: " + str3);
                    } else {
                        str3 = parallelTaskDataParameter2.getPrefix() + str3;
                        jpegImageData = composeLiveShotPicture;
                    }
                }
                if (parallelTaskData.getParallelType() != -2 || parallelTaskData.getParallelType() == -3) {
                    this.width = i5;
                    this.height = i4;
                    this.orientation = i3;
                    parallelTaskData.refillJpegData(jpegImageData);
                }
                Log.d(TAG, "insertSingleTask: isShot2Gallery = " + parallelTaskData.isShot2Gallery());
                if (parallelTaskData.isShot2Gallery()) {
                    parallelTaskData.refillJpegData(jpegImageData);
                    parallelTaskData.getDataParameter().updateOutputSize(i5, i4);
                    parserParallelDualTask(parallelTaskData);
                    return;
                }
                ParallelTaskData parallelTaskData3 = parallelTaskData;
                reFillSaveRequest(jpegImageData, parallelTaskData.isNeedThumbnail(), str3, (String) null, parallelTaskData.getDateTakenTime(), (Uri) null, parallelTaskDataParameter2.getLocation(), i5, i4, (ExifInterface) null, i3, false, false, true, false, false, parallelTaskDataParameter2.getAlgorithmName(), parallelTaskDataParameter2.getPictureInfo(), parallelTaskData.getPreviewThumbnailHash());
                return;
            }
            str2 = str4;
            i3 = orientation2;
            parallelTaskDataParameter = dataParameter;
            Log.d(TAG, "parserSingleTask(): saverCallback is null");
        } else {
            str2 = str4;
            i3 = orientation2;
            parallelTaskDataParameter = dataParameter;
        }
        i5 = i2;
        i4 = i;
        iArr = null;
        bArr = null;
        if (parallelTaskData.isLiveShotTask()) {
        }
        if (parallelTaskData.getParallelType() != -2) {
        }
        this.width = i5;
        this.height = i4;
        this.orientation = i3;
        parallelTaskData.refillJpegData(jpegImageData);
    }

    private static byte[] populateExif(byte[] bArr, ParallelTaskData parallelTaskData) {
        if (parallelTaskData == null || parallelTaskData.getCaptureResult() == null) {
            return bArr;
        }
        return Util.appendCaptureResultToExif(bArr, parallelTaskData.getDataParameter().getPictureSize().getWidth(), parallelTaskData.getDataParameter().getPictureSize().getHeight(), parallelTaskData.getDataParameter().getJpegRotation(), parallelTaskData.getDateTakenTime(), parallelTaskData.getDataParameter().getLocation(), parallelTaskData.getCaptureResult().getResults());
    }

    /* access modifiers changed from: protected */
    public int calculateMemoryUsed(ParallelTaskData parallelTaskData) {
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
        if (this.mParallelTaskData == null) {
            Log.v(TAG, "mParallelTaskData is null, ignore");
            return;
        }
        switch (this.mParallelTaskData.getParallelType()) {
            case Constants.ShotType.INTENT_PARALLEL_DUAL_SHOT /*-7*/:
            case Constants.ShotType.INTENT_PARALLEL_SINGLE_PORTRAIT /*-6*/:
            case Constants.ShotType.INTENT_PARALLEL_SINGLE_SHOT /*-5*/:
            case 5:
            case 6:
            case 7:
            case 8:
                parserParallelDualTask(this.mParallelTaskData);
                return;
            case -4:
                parserMimojiCaptureTask(this.mParallelTaskData);
                return;
            case -3:
            case -2:
            case 0:
            case 1:
            case 10:
                parserSingleTask(this.mParallelTaskData);
                return;
            case -1:
                parserPreviewShotTask(this.mParallelTaskData);
                return;
            case 2:
                parserNormalDualTask(this.mParallelTaskData);
                return;
            case 9:
                parserParallelBurstTask(this.mParallelTaskData);
                return;
            default:
                throw new RuntimeException("Unknown shot type: " + this.mParallelTaskData.getParallelType());
        }
    }

    /* access modifiers changed from: protected */
    public void reFillSaveRequest(byte[] bArr, long j, long j2, Location location, int i, String str, int i2, int i3, boolean z, String str2, PictureInfo pictureInfo) {
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
