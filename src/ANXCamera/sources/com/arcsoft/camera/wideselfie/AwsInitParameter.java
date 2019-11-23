package com.arcsoft.camera.wideselfie;

public class AwsInitParameter {

    /* renamed from: a  reason: collision with root package name */
    private int f79a;

    /* renamed from: b  reason: collision with root package name */
    private int f80b;
    private int c;
    public float cameraViewAngleForHeight;
    public float cameraViewAngleForWidth;
    public int changeDirectionThumbThreshold;
    public boolean convertNV21;
    private int d;
    private int e;
    private int f;
    public int guideStableBarThumbHeight;
    public int guideStopBarThumbHeight;
    public int maxResultWidth;
    public int mode;
    public int progressBarThumbHeight;
    public float progressBarThumbHeightCropRatio;
    public float resultAngleForHeight;
    public float resultAngleForWidth;
    public int thumbnailHeight;
    public int thumbnailWidth;

    private AwsInitParameter() {
    }

    public static AwsInitParameter getDefaultInitParams(int i, int i2, int i3, int i4) {
        AwsInitParameter awsInitParameter = new AwsInitParameter();
        awsInitParameter.f79a = 0;
        awsInitParameter.mode = 64;
        awsInitParameter.cameraViewAngleForHeight = 42.9829f;
        awsInitParameter.cameraViewAngleForWidth = 55.3014f;
        awsInitParameter.resultAngleForWidth = 180.0f;
        awsInitParameter.resultAngleForHeight = 180.0f;
        awsInitParameter.changeDirectionThumbThreshold = 120;
        awsInitParameter.f80b = i3;
        awsInitParameter.c = i;
        awsInitParameter.d = i2;
        awsInitParameter.e = awsInitParameter.f80b;
        awsInitParameter.thumbnailWidth = awsInitParameter.c;
        awsInitParameter.thumbnailHeight = awsInitParameter.d;
        awsInitParameter.f = i4;
        awsInitParameter.guideStopBarThumbHeight = 0;
        awsInitParameter.maxResultWidth = 0;
        awsInitParameter.progressBarThumbHeight = 0;
        awsInitParameter.guideStableBarThumbHeight = 5;
        awsInitParameter.progressBarThumbHeightCropRatio = 0.0f;
        awsInitParameter.convertNV21 = false;
        return awsInitParameter;
    }

    public int getBufferSize() {
        return this.f79a;
    }

    public int getDeviceOrientation() {
        return this.f;
    }

    public int getFullImageHeight() {
        return this.d;
    }

    public int getFullImageWidth() {
        return this.c;
    }

    public int getSrcFormat() {
        return this.f80b;
    }

    public int getThumbForamt() {
        return this.e;
    }

    public int getThumbnailHeight() {
        return this.thumbnailHeight;
    }

    public int getThumbnailWidth() {
        return this.thumbnailWidth;
    }
}
