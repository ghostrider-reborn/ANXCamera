package com.xiaomi.camera.core;

import android.location.Location;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Size;
import com.android.camera.effect.renders.DeviceWatermarkParam;
import com.android.camera.watermark.WaterMarkData;
import java.util.List;

public class ParallelTaskDataParameter {
    /* access modifiers changed from: private */
    public boolean mAgeGenderAndMagicMirrorWater;
    /* access modifiers changed from: private */
    public String mAlgorithmName;
    /* access modifiers changed from: private */
    public boolean mBokehFrontCamera;
    /* access modifiers changed from: private */
    public DeviceWatermarkParam mDeviceWatermarkParam;
    /* access modifiers changed from: private */
    public List<WaterMarkData> mFaceWaterMarkList;
    /* access modifiers changed from: private */
    public int mFilterId;
    /* access modifiers changed from: private */
    public boolean mHasDualWaterMark;
    /* access modifiers changed from: private */
    public boolean mIsFrontCamera;
    /* access modifiers changed from: private */
    public boolean mIsMoonMode;
    /* access modifiers changed from: private */
    public int mJpegQuality;
    /* access modifiers changed from: private */
    public int mJpegRotation;
    /* access modifiers changed from: private */
    public int mLightingPattern;
    /* access modifiers changed from: private */
    public Location mLocation;
    /* access modifiers changed from: private */
    public boolean mMirror;
    /* access modifiers changed from: private */
    public int mOrientation;
    private Size mOutputSize;
    /* access modifiers changed from: private */
    public PictureInfo mPictureInfo;
    private Size mPictureSize;
    /* access modifiers changed from: private */
    public String mPrefix;
    private Size mPreviewSize;
    /* access modifiers changed from: private */
    public Size mRawSize;
    /* access modifiers changed from: private */
    public boolean mSaveGroupshotPrimitive;
    /* access modifiers changed from: private */
    public int mShootOrientation;
    /* access modifiers changed from: private */
    public float mShootRotation;
    /* access modifiers changed from: private */
    public String mSuffix;
    /* access modifiers changed from: private */
    public String mTiltShiftMode;
    /* access modifiers changed from: private */
    public String mTimeWaterMarkString;
    /* access modifiers changed from: private */
    public boolean mVendorWaterMark;

    public static final class Builder {
        private final ParallelTaskDataParameter mParameter;

        public Builder(Size size, Size size2, Size size3) {
            this.mParameter = new ParallelTaskDataParameter(size, size2, size3);
        }

        public Builder(ParallelTaskDataParameter parallelTaskDataParameter) {
            this.mParameter = new ParallelTaskDataParameter();
        }

        @NonNull
        public ParallelTaskDataParameter build() {
            return this.mParameter;
        }

        public Builder setAgeGenderAndMagicMirrorWater(boolean z) {
            boolean unused = this.mParameter.mAgeGenderAndMagicMirrorWater = z;
            return this;
        }

        public Builder setAlgorithmName(String str) {
            String unused = this.mParameter.mAlgorithmName = str;
            return this;
        }

        public Builder setBokehFrontCamera(boolean z) {
            boolean unused = this.mParameter.mBokehFrontCamera = z;
            return this;
        }

        public Builder setDeviceWatermarkParam(DeviceWatermarkParam deviceWatermarkParam) {
            DeviceWatermarkParam unused = this.mParameter.mDeviceWatermarkParam = deviceWatermarkParam;
            return this;
        }

        public Builder setFaceWaterMarkList(List<WaterMarkData> list) {
            List unused = this.mParameter.mFaceWaterMarkList = list;
            return this;
        }

        public Builder setFilterId(int i) {
            int unused = this.mParameter.mFilterId = i;
            return this;
        }

        public Builder setFrontCamera(boolean z) {
            boolean unused = this.mParameter.mIsFrontCamera = z;
            return this;
        }

        public Builder setHasDualWaterMark(boolean z) {
            boolean unused = this.mParameter.mHasDualWaterMark = z;
            return this;
        }

        public Builder setJpegQuality(int i) {
            int unused = this.mParameter.mJpegQuality = i;
            return this;
        }

        public Builder setJpegRotation(int i) {
            int unused = this.mParameter.mJpegRotation = i;
            return this;
        }

        public Builder setLightingPattern(int i) {
            int unused = this.mParameter.mLightingPattern = i;
            return this;
        }

        public Builder setLocation(Location location) {
            Location unused = this.mParameter.mLocation = location;
            return this;
        }

        public Builder setMirror(boolean z) {
            boolean unused = this.mParameter.mMirror = z;
            return this;
        }

        public Builder setMoonMode(boolean z) {
            boolean unused = this.mParameter.mIsMoonMode = z;
            return this;
        }

        public Builder setOrientation(int i) {
            int unused = this.mParameter.mOrientation = i;
            return this;
        }

        public Builder setPictureInfo(PictureInfo pictureInfo) {
            PictureInfo unused = this.mParameter.mPictureInfo = pictureInfo;
            return this;
        }

        public Builder setPrefix(String str) {
            String unused = this.mParameter.mPrefix = str;
            return this;
        }

        public Builder setRawSize(int i, int i2) {
            Size unused = this.mParameter.mRawSize = new Size(i, i2);
            return this;
        }

        public Builder setSaveGroupshotPrimitive(boolean z) {
            boolean unused = this.mParameter.mSaveGroupshotPrimitive = z;
            return this;
        }

        public Builder setShootOrientation(int i) {
            int unused = this.mParameter.mShootOrientation = i;
            return this;
        }

        public Builder setShootRotation(float f) {
            float unused = this.mParameter.mShootRotation = f;
            return this;
        }

        public Builder setSuffix(String str) {
            String unused = this.mParameter.mSuffix = str;
            return this;
        }

        public Builder setTiltShiftMode(String str) {
            String unused = this.mParameter.mTiltShiftMode = str;
            return this;
        }

        public Builder setTimeWaterMarkString(String str) {
            String unused = this.mParameter.mTimeWaterMarkString = str;
            return this;
        }

        public Builder setVendorWaterMark(boolean z) {
            boolean unused = this.mParameter.mVendorWaterMark = z;
            return this;
        }
    }

    private ParallelTaskDataParameter(Size size, Size size2, Size size3) {
        this.mPreviewSize = size;
        this.mPictureSize = size2;
        this.mOutputSize = size3;
    }

    private ParallelTaskDataParameter(ParallelTaskDataParameter parallelTaskDataParameter) {
        this.mHasDualWaterMark = parallelTaskDataParameter.mHasDualWaterMark;
        this.mMirror = parallelTaskDataParameter.mMirror;
        this.mLightingPattern = parallelTaskDataParameter.mLightingPattern;
        if (parallelTaskDataParameter.mPreviewSize != null) {
            this.mPreviewSize = new Size(parallelTaskDataParameter.mPreviewSize.getWidth(), parallelTaskDataParameter.mPreviewSize.getHeight());
        }
        if (parallelTaskDataParameter.mPictureSize != null) {
            this.mPictureSize = new Size(parallelTaskDataParameter.mPictureSize.getWidth(), parallelTaskDataParameter.mPictureSize.getHeight());
        }
        if (parallelTaskDataParameter.mRawSize != null) {
            this.mRawSize = new Size(parallelTaskDataParameter.mRawSize.getWidth(), parallelTaskDataParameter.mRawSize.getHeight());
        }
        this.mFilterId = parallelTaskDataParameter.mFilterId;
        this.mOrientation = parallelTaskDataParameter.mOrientation;
        this.mJpegRotation = parallelTaskDataParameter.mJpegRotation;
        this.mShootRotation = parallelTaskDataParameter.mShootRotation;
        this.mShootOrientation = parallelTaskDataParameter.mShootOrientation;
        if (parallelTaskDataParameter.mLocation != null) {
            this.mLocation = new Location(parallelTaskDataParameter.mLocation);
        }
        this.mTimeWaterMarkString = parallelTaskDataParameter.mTimeWaterMarkString;
        this.mFaceWaterMarkList = parallelTaskDataParameter.mFaceWaterMarkList;
        this.mAgeGenderAndMagicMirrorWater = parallelTaskDataParameter.mAgeGenderAndMagicMirrorWater;
        this.mIsFrontCamera = parallelTaskDataParameter.mIsFrontCamera;
        if (parallelTaskDataParameter.mOutputSize != null) {
            this.mOutputSize = new Size(parallelTaskDataParameter.mOutputSize.getWidth(), parallelTaskDataParameter.mOutputSize.getHeight());
        }
        this.mBokehFrontCamera = parallelTaskDataParameter.mBokehFrontCamera;
        this.mAlgorithmName = parallelTaskDataParameter.mAlgorithmName;
        this.mPictureInfo = parallelTaskDataParameter.mPictureInfo;
        this.mSuffix = parallelTaskDataParameter.mSuffix;
        this.mTiltShiftMode = parallelTaskDataParameter.mTiltShiftMode;
        this.mDeviceWatermarkParam = parallelTaskDataParameter.mDeviceWatermarkParam;
        this.mJpegQuality = parallelTaskDataParameter.mJpegQuality;
    }

    public String getAlgorithmName() {
        return this.mAlgorithmName;
    }

    public DeviceWatermarkParam getDeviceWatermarkParam() {
        return this.mDeviceWatermarkParam;
    }

    public List<WaterMarkData> getFaceWaterMarkList() {
        return this.mFaceWaterMarkList;
    }

    public int getFilterId() {
        return this.mFilterId;
    }

    public int getJpegQuality() {
        return this.mJpegQuality;
    }

    public int getJpegRotation() {
        return this.mJpegRotation;
    }

    public int getLightingPattern() {
        return this.mLightingPattern;
    }

    public Location getLocation() {
        return this.mLocation;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public Size getOutputSize() {
        return this.mOutputSize;
    }

    public PictureInfo getPictureInfo() {
        return this.mPictureInfo;
    }

    public Size getPictureSize() {
        return this.mPictureSize;
    }

    public String getPrefix() {
        return this.mPrefix;
    }

    public Size getPreviewSize() {
        return this.mPreviewSize;
    }

    public Size getRawSize() {
        return this.mRawSize;
    }

    public int getShootOrientation() {
        return this.mShootOrientation;
    }

    public float getShootRotation() {
        return this.mShootRotation;
    }

    public String getSuffix() {
        return this.mSuffix;
    }

    public String getTiltShiftMode() {
        return this.mTiltShiftMode;
    }

    public String getTimeWaterMarkString() {
        return this.mTimeWaterMarkString;
    }

    public boolean getVendorWaterMark() {
        return this.mVendorWaterMark;
    }

    public boolean isAgeGenderAndMagicMirrorWater() {
        return this.mAgeGenderAndMagicMirrorWater;
    }

    public boolean isBokehFrontCamera() {
        return this.mBokehFrontCamera;
    }

    public boolean isFrontCamera() {
        return this.mIsFrontCamera;
    }

    public boolean isHasDualWaterMark() {
        return this.mHasDualWaterMark;
    }

    public boolean isHasFrontWaterMark() {
        return this.mDeviceWatermarkParam != null && this.mDeviceWatermarkParam.isFrontWatermarkEnable();
    }

    public boolean isHasWaterMark() {
        return isHasDualWaterMark() || isHasFrontWaterMark() || !TextUtils.isEmpty(this.mTimeWaterMarkString);
    }

    public boolean isMirror() {
        return this.mMirror;
    }

    public boolean isMoonMode() {
        return this.mIsMoonMode;
    }

    public boolean isSaveGroupshotPrimitive() {
        return this.mSaveGroupshotPrimitive;
    }

    public boolean isUtralPixelWaterMark() {
        return this.mDeviceWatermarkParam != null && this.mDeviceWatermarkParam.isUltraWatermarkEnable();
    }

    public void updateOutputSize(int i, int i2) {
        this.mOutputSize = Size.parseSize(String.format("%d*%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
    }
}
