package com.android.camera2;

import android.annotation.TargetApi;
import android.hardware.camera2.CaptureResult;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTagHelper;
import com.android.camera2.vendortag.struct.AECFrameControl;
import com.android.camera2.vendortag.struct.AFFrameControl;
import com.android.camera2.vendortag.struct.AWBFrameControl;

@TargetApi(21)
public class CaptureResultParser {
    private static final float AECGAIN_THRESHOLD = 2.0f;
    private static final String TAG = CaptureResultParser.class.getSimpleName();

    public static AECFrameControl getAECFrameControl(CaptureResult captureResult) {
        return (AECFrameControl) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.AEC_FRAME_CONTROL);
    }

    public static AFFrameControl getAFFrameControl(CaptureResult captureResult) {
        return (AFFrameControl) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.AF_FRAME_CONTROL);
    }

    public static AWBFrameControl getAWBFrameControl(CaptureResult captureResult) {
        return (AWBFrameControl) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.AWB_FRAME_CONTROL);
    }

    public static float getAecLux(CaptureResult captureResult) {
        Float f = (Float) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.AEC_LUX);
        if (f == null) {
            return 0.0f;
        }
        return f.floatValue();
    }

    public static int getAsdDetectedModes(CaptureResult captureResult) {
        Integer num = (Integer) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.AI_SCENE_DETECTED);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static int getBeautyBodySlimCountResult(CaptureResult captureResult) {
        Integer num = (Integer) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.BEAUTY_BODY_SLIM_COUNT);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static byte[] getExifValues(CaptureResult captureResult) {
        return (byte[]) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.EXIF_INFO_VALUES);
    }

    public static boolean getFastZoomResult(CaptureResult captureResult) {
        Byte b2 = (Byte) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.FAST_ZOOM_RESULT);
        String str = TAG;
        Log.d(str, "FAST_ZOOM_RESULT = " + b2);
        return b2 != null && b2.byteValue() == 1;
    }

    public static byte[] getHdrCheckerValues(CaptureResult captureResult) {
        return (byte[]) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.HDR_CHECKTER_EV_VALUES);
    }

    public static int getHdrDetectedScene(CaptureResult captureResult) {
        Byte b2 = (Byte) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.AI_HDR_DETECTED);
        if (b2 != null) {
            return b2.byteValue();
        }
        return 0;
    }

    public static byte[] getSatDbgInfo(CaptureResult captureResult) {
        return (byte[]) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.SAT_DBG_INFO);
    }

    public static int getSatMasterCameraId(CaptureResult captureResult) {
        Integer num;
        if (captureResult != null) {
            num = (Integer) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.SAT_MATER_CAMERA_ID);
            String str = TAG;
            Log.d(str, "getSatMasterCameraId: " + num);
        } else {
            num = null;
        }
        if (num == null) {
            Log.w(TAG, "getSatMasterCameraId: not found");
            num = 2;
        }
        return num.intValue();
    }

    public static int getUltraWideDetectedResult(CaptureResult captureResult) {
        Integer num = (Integer) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.ULTRA_WIDE_RECOMMENDED_RESULT);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static boolean isASDEnable(CaptureResult captureResult) {
        Byte b2 = (Byte) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.AI_SCENE_ENABLE);
        if (b2 == null) {
            return false;
        }
        String str = TAG;
        Log.d(str, "isASDEnable: " + b2);
        return b2.byteValue() == 1;
    }

    public static boolean isHdrMotionDetected(CaptureResult captureResult) {
        Byte b2 = (Byte) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.HDR_MOTION_DETECTED);
        return (b2 == null || b2.byteValue() == 0) ? false : true;
    }

    public static boolean isLensDirtyDetected(CaptureResult captureResult) {
        Integer num = (Integer) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.LENS_DIRTY_DETECTED);
        return num != null && num.intValue() == 1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0062 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    public static boolean isQuadCfaRunning(CaptureResult captureResult) {
        float f;
        boolean hn = DataRepository.dataItemFeature().hn();
        String str = TAG;
        Log.d(str, "isQuadCfaRunning: support=" + hn);
        if (hn) {
            AECFrameControl aECFrameControl = (AECFrameControl) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.AEC_FRAME_CONTROL);
            if (!(aECFrameControl == null || aECFrameControl.getAecExposureDatas() == null || aECFrameControl.getAecExposureDatas().length <= 0)) {
                f = aECFrameControl.getAecExposureDatas()[0].getLinearGain();
                String str2 = TAG;
                Log.d(str2, "isQuadCfaRunning: gain=" + f);
                return f >= 2.0f;
            }
        }
        f = 3.0f;
        String str22 = TAG;
        Log.d(str22, "isQuadCfaRunning: gain=" + f);
        if (f >= 2.0f) {
        }
    }

    public static boolean isRemosaicDetected(CaptureResult captureResult) {
        Boolean bool = (Boolean) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.REMOSAIC_DETECTED);
        String str = TAG;
        Log.d(str, "isRemosaicDetected: " + bool);
        return bool != null && bool.booleanValue();
    }

    public static boolean isSREnable(CaptureResult captureResult) {
        Boolean bool = (Boolean) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.IS_SR_ENABLE);
        return bool != null && bool.booleanValue();
    }

    public static boolean isSatFallbackDetected(CaptureResult captureResult) {
        if (captureResult == null) {
            return false;
        }
        Boolean bool = (Boolean) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.SAT_FALLBACK_DETECTED);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }
}
