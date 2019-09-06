package com.xiaomi.camera.core;

import com.android.camera.log.Log;
import com.xiaomi.camera.base.Constants.ShotType;
import com.xiaomi.camera.core.ParallelTaskDataParameter.Builder;
import com.xiaomi.protocol.ICustomCaptureResult;

public class ParallelTaskData {
    private static final String GROUPSHOT_ORIGINAL_SUFFIX = "_ORG";
    private static final String TAG = "ParallelTaskData";
    private int currentModuleIndex = -1;
    private boolean isAbandoned = false;
    private boolean isAdaptiveSnapshotSize;
    private boolean isLiveShotTask;
    private boolean isNeedThumbnail;
    private boolean isPictureFilled;
    private int mAlgoType;
    private int mBurstNum;
    private int mCameraId;
    private ICustomCaptureResult mCaptureResult;
    private int[] mCoordinatesOfTheRegionUnderWatermarks;
    private long mCoverFrameTimestamp;
    private byte[] mDataOfTheRegionUnderWatermarks;
    private ParallelTaskDataParameter mDataParameter;
    private boolean mIsShot2Gallery;
    private byte[] mJpegImageData;
    private int mParallelType;
    private byte[] mPortraitDepthData;
    private byte[] mPortraitRawData;
    private byte[] mRawImageData;
    private String mSavePath;
    private long mTimestamp;
    private byte[] mVideoRawData = null;
    private int previewThumbnailHash;

    public ParallelTaskData(int i, long j, int i2, String str) {
        this.mCameraId = i;
        this.mTimestamp = j;
        this.mParallelType = i2;
        this.mSavePath = str;
    }

    public ParallelTaskData(ParallelTaskData parallelTaskData) {
        this.mParallelType = parallelTaskData.mParallelType;
        this.mIsShot2Gallery = parallelTaskData.mIsShot2Gallery;
        this.mTimestamp = parallelTaskData.mTimestamp;
        this.mCaptureResult = parallelTaskData.mCaptureResult;
        this.mJpegImageData = parallelTaskData.mJpegImageData;
        this.mRawImageData = parallelTaskData.mRawImageData;
        this.mPortraitRawData = parallelTaskData.mPortraitRawData;
        this.mPortraitDepthData = parallelTaskData.mPortraitDepthData;
        this.mSavePath = parallelTaskData.mSavePath;
        this.mDataParameter = parallelTaskData.mDataParameter;
        this.isNeedThumbnail = parallelTaskData.isNeedThumbnail;
        this.mVideoRawData = parallelTaskData.mVideoRawData;
        this.mCoverFrameTimestamp = parallelTaskData.mCoverFrameTimestamp;
        this.isLiveShotTask = parallelTaskData.isLiveShotTask;
        this.isPictureFilled = parallelTaskData.isPictureFilled;
        this.mDataOfTheRegionUnderWatermarks = parallelTaskData.mDataOfTheRegionUnderWatermarks;
        this.mCoordinatesOfTheRegionUnderWatermarks = parallelTaskData.mCoordinatesOfTheRegionUnderWatermarks;
        this.mCameraId = parallelTaskData.mCameraId;
    }

    public void checkThread() {
    }

    public ParallelTaskData cloneTaskData(int i) {
        String str;
        ParallelTaskData parallelTaskData = new ParallelTaskData(this);
        String savePath = getSavePath();
        String str2 = GROUPSHOT_ORIGINAL_SUFFIX;
        if (i > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("_");
            sb.append(i);
            str2 = sb.toString();
        }
        int lastIndexOf = savePath.lastIndexOf(".");
        if (lastIndexOf > 0) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(savePath.substring(0, lastIndexOf));
            sb2.append(str2);
            sb2.append(savePath.substring(lastIndexOf));
            str = sb2.toString();
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(savePath);
            sb3.append(str2);
            str = sb3.toString();
        }
        String str3 = TAG;
        StringBuilder sb4 = new StringBuilder();
        sb4.append("[1] cloneTaskData: path=");
        sb4.append(str);
        Log.d(str3, sb4.toString());
        parallelTaskData.setSavePath(str);
        parallelTaskData.setNeedThumbnail(false);
        Builder builder = new Builder(getDataParameter());
        builder.setHasDualWaterMark(false);
        builder.setTimeWaterMarkString(null);
        builder.setSaveGroupshotPrimitive(false);
        parallelTaskData.fillParameter(builder.build());
        return parallelTaskData;
    }

    public synchronized void fillJpegData(byte[] bArr, int i) {
        checkThread();
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        if (this.mRawImageData == null) {
                            this.mRawImageData = bArr;
                        } else {
                            throw new RuntimeException("algo fillJpegData: raw already set");
                        }
                    }
                } else if (this.mPortraitDepthData == null) {
                    this.mPortraitDepthData = new byte[bArr.length];
                    System.arraycopy(bArr, 0, this.mPortraitDepthData, 0, bArr.length);
                } else {
                    throw new RuntimeException("algo fillJpegData: depth already set");
                }
            } else if (this.mPortraitRawData == null) {
                this.mPortraitRawData = bArr;
            } else {
                throw new RuntimeException("algo fillJpegData: portrait raw already set");
            }
        } else if (this.mJpegImageData == null) {
            this.isPictureFilled = true;
            this.mJpegImageData = bArr;
        } else {
            throw new RuntimeException("algo fillJpegData: jpeg already set");
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("fillJpegData: jpegData=");
        sb.append(bArr);
        sb.append("; imageType=");
        sb.append(i);
        Log.d(str, sb.toString());
    }

    public void fillParameter(ParallelTaskDataParameter parallelTaskDataParameter) {
        this.mDataParameter = parallelTaskDataParameter;
    }

    public synchronized void fillVideoData(byte[] bArr, long j) {
        checkThread();
        if (this.mVideoRawData == null) {
            this.mVideoRawData = bArr;
            this.mCoverFrameTimestamp = j;
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("fillVideoData: video = ");
            sb.append(bArr);
            sb.append(", timestamp = ");
            sb.append(j);
            Log.d(str, sb.toString());
        } else {
            throw new IllegalStateException("algo fillVideoData: microvideo already set");
        }
    }

    public int getAlgoType() {
        return this.mAlgoType;
    }

    public int getBurstNum() {
        return this.mBurstNum;
    }

    public int getCameraId() {
        return this.mCameraId;
    }

    public ICustomCaptureResult getCaptureResult() {
        return this.mCaptureResult;
    }

    public int[] getCoordinatesOfTheRegionUnderWatermarks() {
        return this.mCoordinatesOfTheRegionUnderWatermarks;
    }

    public synchronized long getCoverFrameTimestamp() {
        return this.mCoverFrameTimestamp;
    }

    public int getCurrentModuleIndex() {
        return this.currentModuleIndex;
    }

    public byte[] getDataOfTheRegionUnderWatermarks() {
        return this.mDataOfTheRegionUnderWatermarks;
    }

    public ParallelTaskDataParameter getDataParameter() {
        return this.mDataParameter;
    }

    public byte[] getJpegImageData() {
        return this.mJpegImageData;
    }

    public synchronized byte[] getMicroVideoData() {
        return this.mVideoRawData;
    }

    public int getParallelType() {
        return this.mParallelType;
    }

    public byte[] getPortraitDepthData() {
        return this.mPortraitDepthData;
    }

    public byte[] getPortraitRawData() {
        return this.mPortraitRawData;
    }

    public int getPreviewThumbnailHash() {
        return this.previewThumbnailHash;
    }

    public byte[] getRawImageData() {
        return this.mRawImageData;
    }

    public String getSavePath() {
        return this.mSavePath;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public boolean isAbandoned() {
        return this.isAbandoned;
    }

    public boolean isAdaptiveSnapshotSize() {
        return this.isAdaptiveSnapshotSize;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000f, code lost:
        if (r4.mRawImageData != null) goto L_0x0011;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0011, code lost:
        r2 = true;
     */
    public synchronized boolean isJpegDataReady() {
        boolean z;
        z = false;
        switch (this.mParallelType) {
            case ShotType.INTENT_PARALLEL_DUAL_SHOT /*-7*/:
            case -3:
            case 2:
            case 6:
            case 7:
                if (!(this.mJpegImageData == null || this.mPortraitRawData == null || this.mPortraitDepthData == null)) {
                }
            case ShotType.INTENT_PARALLEL_SINGLE_PORTRAIT /*-6*/:
            case ShotType.INTENT_PARALLEL_SINGLE_SHOT /*-5*/:
            case -2:
            case -1:
            case 0:
            case 5:
            case 8:
            case 9:
            case 10:
                if (this.mJpegImageData != null) {
                }
                break;
            case 1:
                if (this.mJpegImageData != null) {
                    break;
                }
                break;
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("isJpegDataReady: object = ");
        sb.append(this);
        sb.append("; mParallelType = ");
        sb.append(this.mParallelType);
        sb.append("; mJpegImageData = ");
        sb.append(this.mJpegImageData);
        sb.append("; mRawImageData = ");
        sb.append(this.mRawImageData);
        sb.append("; mPortraitRawData = ");
        sb.append(this.mPortraitRawData);
        sb.append("; mPortraitDepthData = ");
        sb.append(this.mPortraitDepthData);
        sb.append("; mVideoRawData = ");
        sb.append(this.mVideoRawData);
        sb.append("; result = ");
        sb.append(z);
        Log.d(str, sb.toString());
        return z;
    }

    public synchronized boolean isLiveShotTask() {
        return this.isLiveShotTask;
    }

    public boolean isNeedThumbnail() {
        return this.isNeedThumbnail;
    }

    public synchronized boolean isPictureFilled() {
        return this.isPictureFilled;
    }

    public boolean isShot2Gallery() {
        return this.mIsShot2Gallery;
    }

    public void refillJpegData(byte[] bArr) {
        this.mJpegImageData = bArr;
        this.isPictureFilled = true;
    }

    public void releaseImageData() {
        this.mVideoRawData = null;
        this.mJpegImageData = null;
        this.mRawImageData = null;
        this.mPortraitRawData = null;
        this.mPortraitDepthData = null;
        this.isPictureFilled = false;
        this.mDataOfTheRegionUnderWatermarks = null;
        this.mCoordinatesOfTheRegionUnderWatermarks = null;
    }

    public void setAbandoned(boolean z) {
        this.isAbandoned = z;
    }

    public void setAdaptiveSnapshotSize(boolean z) {
        this.isAdaptiveSnapshotSize = z;
    }

    public void setAlgoType(int i) {
        this.mAlgoType = i;
    }

    public void setBurstNum(int i) {
        this.mBurstNum = i;
    }

    public void setCaptureResult(ICustomCaptureResult iCustomCaptureResult) {
        this.mCaptureResult = iCustomCaptureResult;
    }

    public void setCoordinatesOfTheRegionUnderWatermarks(int[] iArr) {
        this.mCoordinatesOfTheRegionUnderWatermarks = iArr;
    }

    public void setCurrentModuleIndex(int i) {
        this.currentModuleIndex = i;
    }

    public void setDataOfTheRegionUnderWatermarks(byte[] bArr) {
        this.mDataOfTheRegionUnderWatermarks = bArr;
    }

    public synchronized void setLiveShotTask(boolean z) {
        this.isLiveShotTask = z;
    }

    public void setNeedThumbnail(boolean z) {
        this.isNeedThumbnail = z;
    }

    public synchronized void setPictureFilled(boolean z) {
        this.isPictureFilled = z;
    }

    public void setPreviewThumbnailHash(int i) {
        this.previewThumbnailHash = i;
    }

    public void setSavePath(String str) {
        this.mSavePath = str;
    }

    public boolean setShot2Gallery(boolean z) {
        if (this.mIsShot2Gallery == z) {
            return false;
        }
        this.mIsShot2Gallery = z;
        return true;
    }

    public void setTimestamp(long j) {
        this.mTimestamp = j;
    }
}
