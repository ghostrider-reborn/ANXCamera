package com.arcsoft.supernight;

import android.graphics.Rect;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.Face;
import android.media.Image;
import android.os.Environment;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

public class SuperNightProcess implements ProgressCallback {
    public static final int ARC_SN_CAMERA_MODE_UNKNOWN = -1;
    public static final int ARC_SN_CAMERA_MODE_XIAOMI_G80_GW1 = 1793;
    public static final int ARC_SN_CAMERA_MODE_XIAOMI_G90_GW1 = 1793;
    public static final int ARC_SN_CAMERA_MODE_XIAOMI_SDM855_12MB_IMX586 = 1792;
    public static final int ARC_SN_CAMERA_STATE_HAND = 2;
    public static final int ARC_SN_CAMERA_STATE_UNKNOWN = 0;
    public static final int ARC_SN_MAX_INPUT_IMAGE_NUM = 20;
    public static final int ARC_SN_SCENEMODE_INDOOR = 1;
    public static final int ARC_SN_SCENEMODE_LOWLIGHT = 3;
    public static final int ARC_SN_SCENEMODE_OUTDOOR = 2;
    public static final int ARC_SN_SCENEMODE_PORTRAIT = 4;
    public static final int ARC_SN_SCENEMODE_UNKNOW = 0;
    public static final int ASVL_PAF_NV12 = 2049;
    public static final int ASVL_PAF_NV21 = 2050;
    public static final int ASVL_PAF_RAW10_BGGR_10B = 3332;
    public static final int ASVL_PAF_RAW10_BGGR_16B = 3340;
    public static final int ASVL_PAF_RAW10_GBRG_10B = 3331;
    public static final int ASVL_PAF_RAW10_GBRG_16B = 3339;
    public static final int ASVL_PAF_RAW10_GRAY_10B = 3585;
    public static final int ASVL_PAF_RAW10_GRAY_16B = 3713;
    public static final int ASVL_PAF_RAW10_GRBG_10B = 3330;
    public static final int ASVL_PAF_RAW10_GRBG_16B = 3338;
    public static final int ASVL_PAF_RAW10_RGGB_10B = 3329;
    public static final int ASVL_PAF_RAW10_RGGB_16B = 3337;
    public static final int ASVL_PAF_RAW12_BGGR_12B = 3336;
    public static final int ASVL_PAF_RAW12_BGGR_16B = 3348;
    public static final int ASVL_PAF_RAW12_GBRG_12B = 3335;
    public static final int ASVL_PAF_RAW12_GBRG_16B = 3347;
    public static final int ASVL_PAF_RAW12_GRAY_12B = 3601;
    public static final int ASVL_PAF_RAW12_GRAY_16B = 3729;
    public static final int ASVL_PAF_RAW12_GRBG_12B = 3334;
    public static final int ASVL_PAF_RAW12_GRBG_16B = 3346;
    public static final int ASVL_PAF_RAW12_RGGB_12B = 3333;
    public static final int ASVL_PAF_RAW12_RGGB_16B = 3345;
    public static final int ASVL_PAF_RAW14_BGGR_14B = 3384;
    public static final int ASVL_PAF_RAW14_BGGR_16B = 3396;
    public static final int ASVL_PAF_RAW14_GBRG_14B = 3383;
    public static final int ASVL_PAF_RAW14_GBRG_16B = 3395;
    public static final int ASVL_PAF_RAW14_GRAY_14B = 3617;
    public static final int ASVL_PAF_RAW14_GRAY_16B = 3745;
    public static final int ASVL_PAF_RAW14_GRBG_14B = 3382;
    public static final int ASVL_PAF_RAW14_GRBG_16B = 3394;
    public static final int ASVL_PAF_RAW14_RGGB_14B = 3381;
    public static final int ASVL_PAF_RAW14_RGGB_16B = 3393;
    public static final int ASVL_PAF_RAW16_BGGR_16B = 3364;
    public static final int ASVL_PAF_RAW16_GBRG_16B = 3363;
    public static final int ASVL_PAF_RAW16_GRAY_16B = 3633;
    public static final int ASVL_PAF_RAW16_GRBG_16B = 3362;
    public static final int ASVL_PAF_RAW16_RGGB_16B = 3361;
    private static final String DEBUG_FILE = (Environment.getExternalStorageDirectory().toString() + "/DCIM/arc_super_night/dump_file.txt");
    private static final String DUMP_KEY = "dumpSNImage";
    private static final String HINT_FOR_RAW_REPROCESS_KEY = "com.mediatek.control.capture.hintForRawReprocess";
    private static final String LOG_KEY = "debugSNLog";
    private static final String SUPPERNIGHT_ADRCGAIN_KEY = "com.mediatek.suppernightfeature.fadrcgain";
    private static final String SUPPERNIGHT_BLACKLEVEL_KEY = "com.mediatek.suppernightfeature.blacklevel";
    private static final String SUPPERNIGHT_BRIGHTLEVEL_KEY = "com.mediatek.suppernightfeature.brightlevel";
    private static final String SUPPERNIGHT_EXPINDEX_KEY = "com.mediatek.suppernightfeature.expindex";
    private static final String SUPPERNIGHT_ISPGAIN_KEY = "com.mediatek.suppernightfeature.fispgain";
    private static final String SUPPERNIGHT_LUXINDEX_KEY = "com.mediatek.suppernightfeature.luxindex";
    private static final String SUPPERNIGHT_SENSORGAIN_KEY = "com.mediatek.suppernightfeature.fsensorgain";
    private static final String SUPPERNIGHT_SHUTTER_KEY = "com.mediatek.suppernightfeature.fshutter";
    private static final String SUPPERNIGHT_TOTALGAIN_KEY = "com.mediatek.suppernightfeature.ftotalgain";
    private static final String SUPPERNIGHT_WBGAIN_KEY = "com.mediatek.suppernightfeature.fwbgain";
    private static final String TAG = "SuperNightProcess";
    private CaptureResult.Key<int[]> ADRC_GAIN_RESULT_KEY = null;
    private CaptureResult.Key<int[]> BLACK_LEVEL_RESULT_KEY = null;
    private CaptureResult.Key<int[]> BRIGHT_LEVEL_RESULT_KEY = null;
    private CaptureResult.Key<int[]> EXP_INDEX_RESULT_KEY = null;
    private CaptureResult.Key<int[]> ISP_GAIN_RESULT_KEY = null;
    private CaptureResult.Key<int[]> LUX_INDEX_RESULT_KEY = null;
    private CaptureResult.Key<int[]> SENSOR_GAIN_RESULT_KEY = null;
    private CaptureResult.Key<long[]> SHUTTER_RESULT_KEY = null;
    private CaptureResult.Key<int[]> TOTAL_GAIN_RESULT_KEY = null;
    private CaptureResult.Key<float[]> WB_GAIN_RESULT_KEY = null;
    private Rect mArrayRect = null;
    private CountDownLatch mCountDownLatch;
    private boolean mDumpFile = false;
    private boolean mEnableAdrcGain = true;
    private boolean mEnableBlackLevel = true;
    private boolean mEnableWbGain = true;
    private int mFaceOrientation = 90;
    private volatile boolean mInit = false;
    private volatile boolean mIsCancel = false;
    private TotalCaptureResult mMetdata;
    private SuperNightJni mSuperNightJni = new SuperNightJni();

    public class FaceInfo {
        public int faceNum;
        public int faceOrientation;
        public Rect[] faceRects;

        public FaceInfo() {
        }
    }

    public class InputInfo {
        public int cameraState;
        public int curIndex;
        public int imgNum;
        public int[] inputFd = new int[20];
        public RawImage[] inputImages = new RawImage[20];
        public float[] inputImagesEV = new float[20];

        public InputInfo() {
        }
    }

    public class Param {
        public int curveBrightness;
        public int curveContrast;
        public int curveHighlight;
        public int curveMid;
        public int curveShadow;
        public int edgeSharpIntensity;
        public int noiseLength;
        public int sharpIntensity;

        public Param() {
        }
    }

    public class RawInfo {
        public int[] blackLevel = new int[4];
        public int[] brightLevel = new int[4];
        public int[] evList = new int[20];
        public int expIndex;
        public float fAdrcGain;
        public float fISPGain;
        public float fSensorGain;
        public float fShutter;
        public float fTotalGain;
        public float[] fWbGain = new float[4];
        public int luxIndex;
        public int rawType;

        public RawInfo() {
            for (int i = 0; i < 4; i++) {
                this.fWbGain[i] = 1.0f;
            }
            this.fAdrcGain = 1.0f;
        }
    }

    public SuperNightProcess(Rect rect) {
        readDebugFileValue();
        this.mSuperNightJni.setDumpImageFile(this.mDumpFile);
        this.mArrayRect = rect;
        LOG.d(TAG, "dumpFile = " + this.mDumpFile + " debugLog = " + LOG.DEBUG);
        StringBuilder sb = new StringBuilder();
        sb.append("mArrayRect = ");
        sb.append(this.mArrayRect);
        LOG.d(TAG, sb.toString());
        if (this.mArrayRect != null) {
            LOG.d(TAG, "mArrayRect = " + this.mArrayRect.toString());
        }
        LOG.d("Version", "--8/10--");
    }

    private void conversionCropRect(Rect rect, int i, int i2) {
        if (rect != null && this.mArrayRect != null && i > 0 && i2 > 0) {
            float width = ((float) this.mArrayRect.width()) / ((float) i);
            float height = ((float) this.mArrayRect.height()) / ((float) i2);
            LOG.d(TAG, "fMultipleW = " + width + ", fMultipleH = " + height);
            rect.left = (int) (((float) rect.left) * width);
            rect.top = (int) (((float) rect.top) * height);
            rect.right = this.mArrayRect.right - ((int) (((float) (i - rect.right)) * width));
            rect.bottom = this.mArrayRect.bottom - ((int) (((float) (i2 - rect.bottom)) * height));
            if (rect.left % 2 != 0) {
                rect.left++;
            }
            if (rect.top % 2 != 0) {
                rect.top++;
            }
            if (rect.right % 2 != 0) {
                rect.right--;
            }
            if (rect.bottom % 2 != 0) {
                rect.bottom--;
            }
            LOG.d(TAG, "conversionCropRect -> cropRect = " + rect.toString());
        }
    }

    private FaceInfo getFaceInfo(TotalCaptureResult totalCaptureResult, int i, int i2) {
        if (totalCaptureResult == null || this.mArrayRect == null || i <= 0 || i2 <= 0) {
            return null;
        }
        Face[] faceArr = (Face[]) totalCaptureResult.get(CaptureResult.STATISTICS_FACES);
        if (faceArr != null) {
            LOG.d(TAG, "face length = " + faceArr.length);
        }
        if (faceArr == null || faceArr.length <= 0) {
            return null;
        }
        float width = ((float) this.mArrayRect.width()) / ((float) i);
        float height = ((float) this.mArrayRect.height()) / ((float) i2);
        LOG.d(TAG, "fMultipleW = " + width + ", fMultipleH = " + height);
        FaceInfo faceInfo = new FaceInfo();
        faceInfo.faceRects = new Rect[faceArr.length];
        faceInfo.faceNum = faceArr.length;
        faceInfo.faceOrientation = this.mFaceOrientation;
        for (int i3 = 0; i3 < faceArr.length; i3++) {
            faceInfo.faceRects[i3] = new Rect(faceArr[i3].getBounds());
            faceInfo.faceRects[i3].left = (int) (((float) faceInfo.faceRects[i3].left) / width);
            faceInfo.faceRects[i3].top = (int) (((float) faceInfo.faceRects[i3].top) / height);
            faceInfo.faceRects[i3].right = (int) (((float) faceInfo.faceRects[i3].right) / width);
            faceInfo.faceRects[i3].bottom = (int) (((float) faceInfo.faceRects[i3].bottom) / height);
            LOG.d(TAG, "conversionFaceRect -> faceRect = " + faceInfo.faceRects[i3].toString());
        }
        return faceInfo;
    }

    private RawImage getRawImage(Image image, int i) {
        RawImage rawImage = new RawImage();
        int format = image.getFormat();
        rawImage.mWidth = image.getWidth();
        rawImage.mHeight = image.getHeight();
        Image.Plane[] planes = image.getPlanes();
        ByteBuffer buffer = planes[0].getBuffer();
        buffer.rewind();
        rawImage.mPitch1 = 0;
        rawImage.mPlane1 = null;
        if (format == 35) {
            rawImage.mPixelArrayFormat = 2050;
            ByteBuffer buffer2 = planes[2].getBuffer();
            buffer2.rewind();
            rawImage.mPitch1 = planes[2].getRowStride();
            rawImage.mPlane1 = buffer2;
        } else if (format == 32) {
            rawImage.mPixelArrayFormat = i;
        }
        rawImage.mPitch0 = planes[0].getRowStride();
        rawImage.mPitch2 = 0;
        rawImage.mPitch3 = 0;
        rawImage.mPlane0 = buffer;
        rawImage.mPlane2 = null;
        rawImage.mPlane3 = null;
        return rawImage;
    }

    private void getVendorTagValue(TotalCaptureResult totalCaptureResult, RawInfo rawInfo) {
        if (this.BRIGHT_LEVEL_RESULT_KEY != null) {
            int[] iArr = (int[]) totalCaptureResult.get(this.BRIGHT_LEVEL_RESULT_KEY);
            if (iArr != null && iArr.length > 0) {
                if (this.mEnableBlackLevel) {
                    for (int i = 0; i < rawInfo.brightLevel.length; i++) {
                        rawInfo.brightLevel[i] = iArr[0] / 1024;
                        LOG.d(TAG, "brightLevel[" + i + "] = " + rawInfo.brightLevel[i]);
                    }
                }
                for (int i2 = 0; i2 < iArr.length; i2++) {
                    LOG.d("vendorTag", "bright[" + i2 + "] = " + iArr[i2]);
                }
            }
        }
        if (this.BLACK_LEVEL_RESULT_KEY != null) {
            int[] iArr2 = (int[]) totalCaptureResult.get(this.BLACK_LEVEL_RESULT_KEY);
            if (iArr2 != null && iArr2.length > 0) {
                if (this.mEnableBlackLevel) {
                    for (int i3 = 0; i3 < rawInfo.blackLevel.length; i3++) {
                        rawInfo.blackLevel[i3] = iArr2[0] / 1024;
                        LOG.d(TAG, "blackLevel[" + i3 + "] = " + rawInfo.blackLevel[i3]);
                    }
                }
                for (int i4 = 0; i4 < iArr2.length; i4++) {
                    LOG.d("vendorTag", "black[" + i4 + "] = " + iArr2[i4]);
                }
            }
        }
        if (this.WB_GAIN_RESULT_KEY != null) {
            float[] fArr = (float[]) totalCaptureResult.get(this.WB_GAIN_RESULT_KEY);
            LOG.d("vendorTag", "awbGain = " + fArr);
            if (fArr != null && fArr.length > 0) {
                if (this.mEnableWbGain) {
                    rawInfo.fWbGain[0] = fArr[0];
                    rawInfo.fWbGain[1] = fArr[1];
                    rawInfo.fWbGain[2] = fArr[2];
                    rawInfo.fWbGain[3] = fArr[3];
                }
                for (int i5 = 0; i5 < fArr.length; i5++) {
                    LOG.d("vendorTag", "wbGain[" + i5 + "] = " + fArr[i5]);
                }
            }
        }
        if (this.SHUTTER_RESULT_KEY != null) {
            long[] jArr = (long[]) totalCaptureResult.get(this.SHUTTER_RESULT_KEY);
            if (jArr != null && jArr.length > 0) {
                rawInfo.fShutter = (float) jArr[0];
                LOG.d(TAG, "fShutter = " + rawInfo.fShutter);
                for (int i6 = 0; i6 < jArr.length; i6++) {
                    LOG.d("vendorTag", "shutter[" + i6 + "] = " + jArr[i6]);
                }
            }
        }
        if (this.SENSOR_GAIN_RESULT_KEY != null) {
            int[] iArr3 = (int[]) totalCaptureResult.get(this.SENSOR_GAIN_RESULT_KEY);
            if (iArr3 != null && iArr3.length > 0) {
                rawInfo.fSensorGain = ((float) iArr3[0]) / 1024.0f;
                LOG.d(TAG, "fSensorGain = " + rawInfo.fSensorGain);
                for (int i7 = 0; i7 < iArr3.length; i7++) {
                    LOG.d("vendorTag", "sensorGain[" + i7 + "] = " + iArr3[i7]);
                }
            }
        }
        if (this.ISP_GAIN_RESULT_KEY != null) {
            int[] iArr4 = (int[]) totalCaptureResult.get(this.ISP_GAIN_RESULT_KEY);
            if (iArr4 != null && iArr4.length > 0) {
                rawInfo.fISPGain = ((float) iArr4[0]) / 1024.0f;
                LOG.d(TAG, "fISPGain = " + rawInfo.fISPGain);
                for (int i8 = 0; i8 < iArr4.length; i8++) {
                    LOG.d("vendorTag", "ispGain[" + i8 + "] = " + iArr4[i8]);
                }
            }
        }
        if (this.LUX_INDEX_RESULT_KEY != null) {
            int[] iArr5 = (int[]) totalCaptureResult.get(this.LUX_INDEX_RESULT_KEY);
            if (iArr5 != null && iArr5.length > 0) {
                rawInfo.luxIndex = iArr5[0] / 10000;
                LOG.d(TAG, "luxIndex = " + rawInfo.luxIndex);
                for (int i9 = 0; i9 < iArr5.length; i9++) {
                    LOG.d("vendorTag", "luxIndex[" + i9 + "] = " + iArr5[i9]);
                }
            }
        }
        if (this.EXP_INDEX_RESULT_KEY != null) {
            int[] iArr6 = (int[]) totalCaptureResult.get(this.EXP_INDEX_RESULT_KEY);
            if (iArr6 != null && iArr6.length > 0) {
                rawInfo.expIndex = iArr6[0];
                LOG.d(TAG, "expIndex = " + rawInfo.expIndex);
                for (int i10 = 0; i10 < iArr6.length; i10++) {
                    LOG.d("vendorTag", "expIndex[" + i10 + "] = " + iArr6[i10]);
                }
            }
        }
        if (this.ADRC_GAIN_RESULT_KEY != null) {
            int[] iArr7 = (int[]) totalCaptureResult.get(this.ADRC_GAIN_RESULT_KEY);
            if (iArr7 != null && iArr7.length > 0) {
                if (this.mEnableAdrcGain) {
                    rawInfo.fAdrcGain = ((float) iArr7[0]) / 1000.0f;
                    LOG.d(TAG, "fAdrcGain = " + rawInfo.fAdrcGain);
                }
                for (int i11 = 0; i11 < iArr7.length; i11++) {
                    LOG.d("vendorTag", "adrcGain[" + i11 + "] = " + iArr7[i11]);
                }
            }
        }
        if (this.TOTAL_GAIN_RESULT_KEY != null) {
            int[] iArr8 = (int[]) totalCaptureResult.get(this.TOTAL_GAIN_RESULT_KEY);
            if (iArr8 != null && iArr8.length > 0) {
                rawInfo.fTotalGain = ((float) iArr8[0]) / 100.0f;
                LOG.d(TAG, "fTotalGain = " + rawInfo.fTotalGain);
                for (int i12 = 0; i12 < iArr8.length; i12++) {
                    LOG.d("vendorTag", "totalGain[" + i12 + "] = " + iArr8[i12]);
                }
            }
        }
    }

    private void setupSomcVendorTag(TotalCaptureResult totalCaptureResult) {
        LOG.d(TAG, "setupSomcVendorTag");
        if (this.BRIGHT_LEVEL_RESULT_KEY == null || this.BLACK_LEVEL_RESULT_KEY == null || this.WB_GAIN_RESULT_KEY == null || this.SHUTTER_RESULT_KEY == null || this.SENSOR_GAIN_RESULT_KEY == null || this.ISP_GAIN_RESULT_KEY == null || this.LUX_INDEX_RESULT_KEY == null || this.ADRC_GAIN_RESULT_KEY == null || this.TOTAL_GAIN_RESULT_KEY == null || this.EXP_INDEX_RESULT_KEY == null) {
            for (CaptureResult.Key<int[]> key : totalCaptureResult.getKeys()) {
                if (SUPPERNIGHT_BRIGHTLEVEL_KEY.equals(key.getName())) {
                    LOG.d(TAG, "BRIGHT_LEVEL_RESULT_KEY");
                    this.BRIGHT_LEVEL_RESULT_KEY = key;
                }
                if (SUPPERNIGHT_BLACKLEVEL_KEY.equals(key.getName())) {
                    LOG.d(TAG, "BLACK_LEVEL_RESULT_KEY");
                    this.BLACK_LEVEL_RESULT_KEY = key;
                }
                if (SUPPERNIGHT_WBGAIN_KEY.equals(key.getName())) {
                    LOG.d(TAG, "WB_GAIN_RESULT_KEY");
                    this.WB_GAIN_RESULT_KEY = key;
                }
                if (SUPPERNIGHT_SHUTTER_KEY.equals(key.getName())) {
                    LOG.d(TAG, "SHUTTER_RESULT_KEY");
                    this.SHUTTER_RESULT_KEY = key;
                }
                if (SUPPERNIGHT_SENSORGAIN_KEY.equals(key.getName())) {
                    LOG.d(TAG, "SENSOR_GAIN_RESULT_KEY");
                    this.SENSOR_GAIN_RESULT_KEY = key;
                }
                if (SUPPERNIGHT_ISPGAIN_KEY.equals(key.getName())) {
                    LOG.d(TAG, "ISP_GAIN_RESULT_KEY");
                    this.ISP_GAIN_RESULT_KEY = key;
                }
                if (SUPPERNIGHT_LUXINDEX_KEY.equals(key.getName())) {
                    LOG.d(TAG, "LUX_INDEX_RESULT_KEY");
                    this.LUX_INDEX_RESULT_KEY = key;
                }
                if (SUPPERNIGHT_EXPINDEX_KEY.equals(key.getName())) {
                    LOG.d(TAG, "EXP_INDEX_RESULT_KEY");
                    this.EXP_INDEX_RESULT_KEY = key;
                }
                if (SUPPERNIGHT_ADRCGAIN_KEY.equals(key.getName())) {
                    LOG.d(TAG, "ADRC_GAIN_RESULT_KEY");
                    this.ADRC_GAIN_RESULT_KEY = key;
                }
                if (SUPPERNIGHT_TOTALGAIN_KEY.equals(key.getName())) {
                    LOG.d(TAG, "TOTAL_GAIN_RESULT_KEY");
                    this.TOTAL_GAIN_RESULT_KEY = key;
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0193, code lost:
        if (r7.mCountDownLatch != null) goto L_0x0195;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0195, code lost:
        com.arcsoft.supernight.LOG.d(TAG, "mCountDownLatch.countDown() 0");
        r7.mCountDownLatch.countDown();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01c2, code lost:
        if (r7.mCountDownLatch != null) goto L_0x0195;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01c5, code lost:
        return r8;
     */
    public int addAllInputInfo(ArrayList<Image> arrayList, ArrayList<TotalCaptureResult> arrayList2, int i, Image image, Rect rect) {
        ArrayList<Image> arrayList3 = arrayList;
        ArrayList<TotalCaptureResult> arrayList4 = arrayList2;
        int i2 = i;
        Image image2 = image;
        LOG.d(TAG, "-- addAllInputInfoEx --");
        int i3 = -1;
        if (arrayList3 == null || arrayList.size() <= 0 || arrayList4 == null || arrayList2.size() <= 0 || arrayList.size() != arrayList2.size() || this.mSuperNightJni == null || image2 == null || rect == null) {
            LOG.d(TAG, "addAllInputInfo - > error invalid param");
            if (this.mCountDownLatch == null) {
                return -1;
            }
            LOG.d(TAG, "mCountDownLatch.countDown() 1");
            this.mCountDownLatch.countDown();
            return -1;
        }
        LOG.d(TAG, " imageList size =  " + arrayList.size());
        int size = arrayList.size();
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i4 < arrayList.size()) {
            try {
                if (this.mIsCancel) {
                    try {
                        LOG.d(TAG, "is cancel 0");
                        if (this.mCountDownLatch != null) {
                            LOG.d(TAG, "mCountDownLatch.countDown() 0");
                            this.mCountDownLatch.countDown();
                        }
                        return i3;
                    } catch (IllegalStateException e) {
                        e = e;
                        try {
                            LOG.d("Error", "e-> " + e.toString());
                        } catch (Throwable th) {
                            if (this.mCountDownLatch != null) {
                                LOG.d(TAG, "mCountDownLatch.countDown() 0");
                                this.mCountDownLatch.countDown();
                            }
                            throw th;
                        }
                    }
                } else {
                    Image image3 = arrayList3.get(i4);
                    TotalCaptureResult totalCaptureResult = arrayList4.get(i4);
                    if (image3 != null) {
                        if (totalCaptureResult != null) {
                            LOG.d("vendorTag", " ------ " + i4);
                            setupSomcVendorTag(totalCaptureResult);
                            InputInfo inputInfo = new InputInfo();
                            inputInfo.curIndex = i4;
                            inputInfo.imgNum = size;
                            inputInfo.cameraState = 2;
                            int width = image3.getWidth();
                            int height = image3.getHeight();
                            RawInfo rawInfo = new RawInfo();
                            rawInfo.rawType = 0;
                            getVendorTagValue(totalCaptureResult, rawInfo);
                            Integer num = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION);
                            if (num != null) {
                                rawInfo.evList[0] = num.intValue();
                                inputInfo.inputImagesEV[0] = (float) num.intValue();
                            }
                            inputInfo.inputImages[0] = getRawImage(image3, i2);
                            this.mSuperNightJni.addOneInputInfo(rawInfo, inputInfo);
                            i4++;
                            i5 = width;
                            i6 = height;
                            i3 = -1;
                        }
                    }
                    LOG.d(TAG, "TotalCaptureResult - > error invalid param");
                    if (this.mCountDownLatch == null) {
                        return -1;
                    }
                    LOG.d(TAG, "mCountDownLatch.countDown() 0");
                    this.mCountDownLatch.countDown();
                    return -1;
                }
            } catch (IllegalStateException e2) {
                e = e2;
                i3 = -1;
                LOG.d("Error", "e-> " + e.toString());
            }
        }
        FaceInfo faceInfo = null;
        Iterator<TotalCaptureResult> it = arrayList2.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            TotalCaptureResult next = it.next();
            Integer num2 = (Integer) next.get(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION);
            if (num2 != null && num2.intValue() == 0) {
                faceInfo = getFaceInfo(next, i5, i6);
                break;
            }
        }
        if (this.mIsCancel) {
            LOG.d(TAG, "is cancel 1");
            if (this.mCountDownLatch == null) {
                return -1;
            }
            LOG.d(TAG, "mCountDownLatch.countDown() 0");
            this.mCountDownLatch.countDown();
            return -1;
        }
        InputInfo inputInfo2 = new InputInfo();
        inputInfo2.curIndex = 0;
        inputInfo2.imgNum = size;
        inputInfo2.cameraState = 2;
        inputInfo2.inputImages[0] = getRawImage(image2, i2);
        i3 = this.mSuperNightJni.process(faceInfo, inputInfo2, 3, rect, this);
        LOG.d(TAG, "cropRect0 = " + rect.toString());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0184, code lost:
        if (r7.mCountDownLatch != null) goto L_0x0186;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0186, code lost:
        com.arcsoft.supernight.LOG.d(TAG, "mCountDownLatch.countDown() 2");
        r7.mCountDownLatch.countDown();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01b5, code lost:
        if (r7.mCountDownLatch != null) goto L_0x0186;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01b8, code lost:
        return r1;
     */
    public int addAllInputInfoEx(ArrayList<Image> arrayList, ArrayList<TotalCaptureResult> arrayList2, int i, Rect rect) {
        int i2;
        ArrayList<Image> arrayList3 = arrayList;
        ArrayList<TotalCaptureResult> arrayList4 = arrayList2;
        if (arrayList3 == null || arrayList.size() <= 0 || arrayList4 == null || arrayList2.size() <= 0 || arrayList.size() != arrayList2.size() || this.mSuperNightJni == null || rect == null) {
            LOG.d(TAG, "addAllInputInfo - > error invalid param");
            if (this.mCountDownLatch != null) {
                LOG.d(TAG, "mCountDownLatch.countDown() 3");
                this.mCountDownLatch.countDown();
            }
            return -1;
        }
        InputInfo inputInfo = new InputInfo();
        LOG.d(TAG, " imageList size =  " + arrayList.size());
        int size = arrayList.size();
        InputInfo inputInfo2 = inputInfo;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i3 < arrayList.size()) {
            try {
                if (this.mIsCancel) {
                    if (this.mCountDownLatch != null) {
                        LOG.d(TAG, "mCountDownLatch.countDown() 2");
                        this.mCountDownLatch.countDown();
                    }
                    return -1;
                }
                Image image = arrayList3.get(i3);
                TotalCaptureResult totalCaptureResult = arrayList4.get(i3);
                LOG.d(TAG, "Image format - > " + image.getFormat());
                if (image != null) {
                    if (totalCaptureResult != null) {
                        LOG.d("vendorTag", " ------ " + i3);
                        setupSomcVendorTag(totalCaptureResult);
                        InputInfo inputInfo3 = new InputInfo();
                        inputInfo3.curIndex = i3;
                        inputInfo3.imgNum = size;
                        inputInfo3.cameraState = 2;
                        int width = image.getWidth();
                        int height = image.getHeight();
                        RawInfo rawInfo = new RawInfo();
                        rawInfo.rawType = 0;
                        getVendorTagValue(totalCaptureResult, rawInfo);
                        Integer num = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION);
                        if (num != null) {
                            rawInfo.evList[0] = num.intValue();
                            inputInfo3.inputImagesEV[0] = (float) num.intValue();
                        }
                        inputInfo3.inputImages[0] = getRawImage(image, i);
                        this.mSuperNightJni.addOneInputInfo(rawInfo, inputInfo3);
                        if (i3 == 0) {
                            inputInfo2 = inputInfo3;
                        }
                        i3++;
                        i4 = width;
                        i5 = height;
                    }
                }
                LOG.d(TAG, "TotalCaptureResult - > error invalid param");
                if (this.mCountDownLatch != null) {
                    LOG.d(TAG, "mCountDownLatch.countDown() 2");
                    this.mCountDownLatch.countDown();
                }
                return -1;
            } catch (IllegalStateException e) {
                e = e;
                i2 = -1;
                try {
                    LOG.d("Error", "e-> " + e.toString());
                } catch (Throwable th) {
                    if (this.mCountDownLatch != null) {
                        LOG.d(TAG, "mCountDownLatch.countDown() 2");
                        this.mCountDownLatch.countDown();
                    }
                    throw th;
                }
            }
        }
        FaceInfo faceInfo = null;
        Iterator<TotalCaptureResult> it = arrayList2.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            TotalCaptureResult next = it.next();
            Integer num2 = (Integer) next.get(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION);
            if (num2 != null && num2.intValue() == 0) {
                faceInfo = getFaceInfo(next, i4, i5);
                break;
            }
        }
        FaceInfo faceInfo2 = faceInfo;
        if (this.mIsCancel) {
            if (this.mCountDownLatch != null) {
                LOG.d(TAG, "mCountDownLatch.countDown() 2");
                this.mCountDownLatch.countDown();
            }
            return -1;
        }
        i2 = this.mSuperNightJni.process(faceInfo2, inputInfo2, 3, rect, this);
        try {
            LOG.d(TAG, "cropRect = " + rect.toString());
        } catch (IllegalStateException e2) {
            e = e2;
            LOG.d("Error", "e-> " + e.toString());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:71:0x01e3, code lost:
        if (r7.mCountDownLatch != null) goto L_0x01e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01e5, code lost:
        com.arcsoft.supernight.LOG.d(TAG, "mCountDownLatch.countDown() 0");
        r7.mCountDownLatch.countDown();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0212, code lost:
        if (r7.mCountDownLatch != null) goto L_0x01e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0215, code lost:
        return r8;
     */
    public int addAllInputInfo_Fd(ArrayList<Image> arrayList, ArrayList<TotalCaptureResult> arrayList2, ArrayList<Integer> arrayList3, int i, Image image, int i2, Rect rect) {
        ArrayList<Image> arrayList4 = arrayList;
        ArrayList<TotalCaptureResult> arrayList5 = arrayList2;
        ArrayList<Integer> arrayList6 = arrayList3;
        int i3 = i;
        Image image2 = image;
        LOG.d(TAG, "-- addAllInputInfo by fd --");
        int i4 = -1;
        if (arrayList4 == null || arrayList.size() <= 0 || arrayList5 == null || arrayList2.size() <= 0 || arrayList6 == null || arrayList3.size() <= 0 || arrayList.size() != arrayList2.size() || arrayList.size() != arrayList3.size() || this.mSuperNightJni == null || image2 == null || rect == null) {
            LOG.d(TAG, "addAllInputInfo - > error invalid param");
            if (this.mCountDownLatch == null) {
                return -1;
            }
            LOG.d(TAG, "mCountDownLatch.countDown() 1");
            this.mCountDownLatch.countDown();
            return -1;
        }
        LOG.d(TAG, " imageList size =  " + arrayList.size());
        int size = arrayList.size();
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i6 < arrayList.size()) {
            try {
                if (this.mIsCancel) {
                    try {
                        LOG.d(TAG, "is cancel 0");
                        if (this.mCountDownLatch != null) {
                            LOG.d(TAG, "mCountDownLatch.countDown() 0");
                            this.mCountDownLatch.countDown();
                        }
                        return i4;
                    } catch (IllegalStateException e) {
                        e = e;
                        try {
                            LOG.d("Error", "e-> " + e.toString());
                        } catch (Throwable th) {
                            if (this.mCountDownLatch != null) {
                                LOG.d(TAG, "mCountDownLatch.countDown() 0");
                                this.mCountDownLatch.countDown();
                            }
                            throw th;
                        }
                    }
                } else {
                    Image image3 = arrayList4.get(i6);
                    TotalCaptureResult totalCaptureResult = arrayList5.get(i6);
                    if (image3 != null) {
                        if (totalCaptureResult != null) {
                            LOG.d("vendorTag", " ------ " + i6);
                            setupSomcVendorTag(totalCaptureResult);
                            InputInfo inputInfo = new InputInfo();
                            inputInfo.curIndex = i6;
                            inputInfo.imgNum = size;
                            inputInfo.cameraState = 2;
                            Integer num = arrayList6.get(i6);
                            if (num != null) {
                                inputInfo.inputFd[i5] = num.intValue();
                                LOG.d(TAG, "input fd[" + i6 + "] = " + inputInfo.inputFd[i5]);
                            }
                            int width = image3.getWidth();
                            int height = image3.getHeight();
                            RawInfo rawInfo = new RawInfo();
                            rawInfo.rawType = i5;
                            getVendorTagValue(totalCaptureResult, rawInfo);
                            Integer num2 = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION);
                            if (num2 != null) {
                                rawInfo.evList[0] = num2.intValue();
                                inputInfo.inputImagesEV[0] = (float) num2.intValue();
                            }
                            inputInfo.inputImages[0] = getRawImage(image3, i3);
                            this.mSuperNightJni.addOneInputInfo(rawInfo, inputInfo);
                            i6++;
                            i7 = width;
                            i8 = height;
                            i5 = 0;
                            i4 = -1;
                        }
                    }
                    LOG.d(TAG, "TotalCaptureResult - > error invalid param");
                    if (this.mCountDownLatch == null) {
                        return -1;
                    }
                    LOG.d(TAG, "mCountDownLatch.countDown() 0");
                    this.mCountDownLatch.countDown();
                    return -1;
                }
            } catch (IllegalStateException e2) {
                e = e2;
                i4 = -1;
                LOG.d("Error", "e-> " + e.toString());
            }
        }
        FaceInfo faceInfo = null;
        Iterator<TotalCaptureResult> it = arrayList2.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            TotalCaptureResult next = it.next();
            Integer num3 = (Integer) next.get(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION);
            if (num3 != null && num3.intValue() == 0) {
                faceInfo = getFaceInfo(next, i7, i8);
                break;
            }
        }
        FaceInfo faceInfo2 = faceInfo;
        if (this.mIsCancel) {
            LOG.d(TAG, "is cancel 1");
            if (this.mCountDownLatch == null) {
                return -1;
            }
            LOG.d(TAG, "mCountDownLatch.countDown() 0");
            this.mCountDownLatch.countDown();
            return -1;
        }
        InputInfo inputInfo2 = new InputInfo();
        inputInfo2.curIndex = 0;
        inputInfo2.imgNum = size;
        inputInfo2.cameraState = 2;
        inputInfo2.inputImages[0] = getRawImage(image2, i3);
        inputInfo2.inputFd[0] = i2;
        i4 = this.mSuperNightJni.process(faceInfo2, inputInfo2, 3, rect, this);
        LOG.d(TAG, "cropRect0 = " + rect.toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x007f A[DONT_GENERATE] */
    public int addOneInputInfo(Image image, TotalCaptureResult totalCaptureResult, int i, int i2, int i3) {
        String str;
        int i4 = -1;
        if (image == null || totalCaptureResult == null || this.mSuperNightJni == null || i < 0) {
            LOG.d(TAG, "addOneInputInfo - > error invalid param");
            return -1;
        }
        try {
            setupSomcVendorTag(totalCaptureResult);
            InputInfo inputInfo = new InputInfo();
            inputInfo.curIndex = i;
            inputInfo.imgNum = i3;
            inputInfo.cameraState = 2;
            RawInfo rawInfo = new RawInfo();
            rawInfo.rawType = 0;
            getVendorTagValue(totalCaptureResult, rawInfo);
            Integer num = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION);
            if (num != null) {
                rawInfo.evList[0] = num.intValue();
                inputInfo.inputImagesEV[0] = (float) num.intValue();
            }
            inputInfo.inputImages[0] = getRawImage(image, i2);
            int addOneInputInfo = this.mSuperNightJni.addOneInputInfo(rawInfo, inputInfo);
            if (num != null) {
                try {
                    if (num.intValue() == 0 && this.mMetdata == null) {
                        this.mMetdata = totalCaptureResult;
                    }
                } catch (IllegalStateException e) {
                    i4 = addOneInputInfo;
                    e = e;
                    try {
                        e.printStackTrace();
                        return i4;
                    } finally {
                        if (this.mCountDownLatch != null) {
                            str = "mCountDownLatch.countDown() 5";
                            LOG.d(TAG, str);
                            this.mCountDownLatch.countDown();
                        }
                    }
                }
            }
            if (this.mCountDownLatch == null) {
                return addOneInputInfo;
            }
            LOG.d(TAG, "mCountDownLatch.countDown() 5");
            this.mCountDownLatch.countDown();
            return addOneInputInfo;
        } catch (IllegalStateException e2) {
            e = e2;
            e.printStackTrace();
            return i4;
        }
    }

    public void cancelSuperNight() {
        if (this.mSuperNightJni != null) {
            this.mIsCancel = true;
            this.mSuperNightJni.cancelSuperNight();
            if (!this.mInit) {
                LOG.d(TAG, "mInit is false ,cancelSuperNight return!!!");
                return;
            }
            this.mCountDownLatch = new CountDownLatch(1);
            try {
                this.mCountDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int init(int i, int i2, int i3, int i4) {
        if (this.mSuperNightJni == null) {
            return -1;
        }
        this.mSuperNightJni.init(i, i2, i3, i4);
        int preProcess = this.mSuperNightJni.preProcess();
        this.mIsCancel = false;
        this.mInit = true;
        LOG.d(TAG, "preprocess = " + preProcess);
        return preProcess;
    }

    public void onProgress(int i, int i2) {
        LOG.d(TAG, "progress = " + i + " status = " + i2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0052, code lost:
        com.arcsoft.supernight.LOG.d(TAG, "mCountDownLatch.countDown() 4");
        r10.mCountDownLatch.countDown();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006a, code lost:
        if (r10.mCountDownLatch != null) goto L_0x0052;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006d, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0050, code lost:
        if (r10.mCountDownLatch != null) goto L_0x0052;
     */
    public int process(Image image, int i, Rect rect, int i2) {
        int i3;
        if (image == null || rect == null) {
            return -1;
        }
        try {
            FaceInfo faceInfo = getFaceInfo(this.mMetdata, image.getWidth(), image.getHeight());
            InputInfo inputInfo = new InputInfo();
            inputInfo.curIndex = 0;
            inputInfo.imgNum = i2;
            inputInfo.cameraState = 2;
            inputInfo.inputImages[0] = getRawImage(image, i);
            i3 = this.mSuperNightJni.process(faceInfo, inputInfo, 3, rect, this);
            try {
                LOG.d(TAG, "cropRect = " + rect.toString());
            } catch (IllegalStateException e) {
                e = e;
                try {
                    e.printStackTrace();
                } catch (Throwable th) {
                    if (this.mCountDownLatch != null) {
                        LOG.d(TAG, "mCountDownLatch.countDown() 4");
                        this.mCountDownLatch.countDown();
                    }
                    throw th;
                }
            }
        } catch (IllegalStateException e2) {
            e = e2;
            i3 = -1;
            e.printStackTrace();
        }
    }

    public void readDebugFileValue() {
        String substring;
        String substring2;
        File file = new File(DEBUG_FILE);
        if (file.exists() || file.isFile()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                int i = 0;
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    LOG.d(TAG, "dump file line = " + readLine);
                    if (!TextUtils.isEmpty(readLine)) {
                        if (i >= 2) {
                            break;
                        }
                        boolean z = true;
                        if (readLine.contains(DUMP_KEY)) {
                            String trim = readLine.trim();
                            LOG.d(TAG, "dump file value =" + substring2);
                            if (Integer.parseInt(substring2) != 1) {
                                z = false;
                            }
                            this.mDumpFile = z;
                            i++;
                        } else if (readLine.contains(LOG_KEY)) {
                            String trim2 = readLine.trim();
                            LOG.d(TAG, "debug log value =" + substring);
                            if (Integer.parseInt(substring) != 1) {
                                z = false;
                            }
                            LOG.DEBUG = z;
                            TimeConsumingUtil.DEBUG = LOG.DEBUG;
                            i++;
                        }
                    }
                }
                bufferedReader.close();
                inputStreamReader.close();
                fileInputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (NumberFormatException e3) {
                e3.printStackTrace();
            }
        } else {
            LOG.d(TAG, "dump file return false 0");
        }
    }

    public void setEnableAdrcGain(boolean z) {
        this.mEnableAdrcGain = z;
    }

    public void setEnableBlackLevel(boolean z) {
        this.mEnableBlackLevel = z;
    }

    public void setEnableWbGain(boolean z) {
        this.mEnableWbGain = z;
    }

    public void setFaceOrientation(int i) {
        this.mFaceOrientation = i;
    }

    public int unInit() {
        if (this.mSuperNightJni == null) {
            return -1;
        }
        int postProcess = this.mSuperNightJni.postProcess();
        LOG.d(TAG, "postProcess = " + postProcess);
        int unInit = this.mSuperNightJni.unInit();
        LOG.d(TAG, "unInit = " + unInit);
        this.mMetdata = null;
        if (this.mCountDownLatch != null && this.mCountDownLatch.getCount() > 0) {
            LOG.d(TAG, "mCountDownLatch.countDown() 6");
            this.mCountDownLatch.countDown();
        }
        this.mInit = false;
        return unInit;
    }
}
