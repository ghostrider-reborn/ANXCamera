package com.android.camera2.compat;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureRequest.Builder;
import android.hardware.camera2.CaptureRequest.Key;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.StreamConfiguration;
import com.android.camera2.vendortag.CameraCharacteristicsVendorTags;
import com.android.camera2.vendortag.CaptureRequestVendorTags;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTag;
import com.android.camera2.vendortag.VendorTagHelper;

@TargetApi(21)
class MiCameraCompatMtkImpl extends MiCameraCompatBaseImpl {
    MiCameraCompatMtkImpl() {
    }

    public void applyContrast(Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.CONTRAST_LEVEL, Integer.valueOf(i + 1));
    }

    public void applyCustomWB(Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.USE_CUSTOM_WB, Integer.valueOf(i));
    }

    public void applyExposureMeteringMode(Builder builder, int i) {
        if (i == 1) {
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.MTK_EXPOSURE_METERING_MODE, Byte.valueOf(0));
        } else if (i == 2) {
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.MTK_EXPOSURE_METERING_MODE, Byte.valueOf(1));
        } else if (i == 0) {
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.MTK_EXPOSURE_METERING_MODE, Byte.valueOf(2));
        }
    }

    public void applyExposureTime(Builder builder, long j) {
        if (j > 0) {
            builder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, Long.valueOf(j));
            builder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(0));
            return;
        }
        builder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, Long.valueOf(0));
        Key key = CaptureRequest.CONTROL_AE_MODE;
        builder.set(key, (Integer) builder.get(key));
    }

    public void applyFaceDetection(Builder builder, boolean z) {
        super.applyFaceDetection(builder, z);
        if (z) {
            builder.set(CaptureRequest.CONTROL_SCENE_MODE, Integer.valueOf(1));
            return;
        }
        Key key = CaptureRequest.CONTROL_SCENE_MODE;
        builder.set(key, (Integer) builder.get(key));
    }

    public void applyFrontMirror(Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.FRONT_MIRROR, Boolean.valueOf(z));
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.SANPSHOT_FLIP_MODE, Integer.valueOf(z ? 1 : 0));
    }

    public void applyHdrBracketMode(Builder builder, byte b2) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.HDR_BRACKET_MODE, Byte.valueOf(b2));
    }

    public void applyHighFpsVideoRecordingMode(Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.HFPSVR_MODE, Integer.valueOf(z ? 1 : 0));
    }

    public void applyISO(Builder builder, int i) {
        Integer valueOf = Integer.valueOf(0);
        if (i > 0) {
            builder.set(CaptureRequest.SENSOR_SENSITIVITY, Integer.valueOf(i));
            builder.set(CaptureRequest.CONTROL_AE_MODE, valueOf);
            return;
        }
        builder.set(CaptureRequest.SENSOR_SENSITIVITY, valueOf);
        Key key = CaptureRequest.CONTROL_AE_MODE;
        builder.set(key, (Integer) builder.get(key));
    }

    public void applyPostProcessCropRegion(Builder builder, Rect rect) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.POST_PROCESS_CROP_REGION, rect);
    }

    public void applyQuickPreview(Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_QUICK_PREVIEW, z ? CaptureRequestVendorTags.CONTROL_QUICK_PREVIEW_ON : CaptureRequestVendorTags.CONTROL_QUICK_PREVIEW_OFF);
    }

    public void applyRawReprocessHint(Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.HINT_FOR_RAW_REPROCESS, Boolean.valueOf(z));
    }

    public void applyRemosaicEnabled(Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_ENABLE_REMOSAIC, Boolean.valueOf(z));
    }

    public void applyRemosaicHint(Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_REMOSAIC_HINT, z ? CaptureRequestVendorTags.CONTROL_REMOSAIC_HINT_ON : CaptureRequestVendorTags.CONTROL_REMOSAIC_HINT_OFF);
    }

    public void applySaturation(Builder builder, int i) {
        int i2 = 0;
        switch (i) {
            case 1:
                i2 = 2;
                break;
            case 2:
                i2 = 4;
                break;
            case 3:
                i2 = 5;
                break;
            case 4:
                i2 = 6;
                break;
            case 5:
                i2 = 8;
                break;
            case 6:
                i2 = 10;
                break;
        }
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.SATURATION, Integer.valueOf(i2));
    }

    public void applySharpness(Builder builder, int i) {
        int i2 = 0;
        switch (i) {
            case 1:
                i2 = 1;
                break;
            case 2:
                i2 = 2;
                break;
            case 3:
                i2 = 3;
                break;
            case 4:
                i2 = 4;
                break;
            case 5:
                i2 = 5;
                break;
            case 6:
                i2 = 6;
                break;
        }
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.SHARPNESS_CONTROL, Integer.valueOf(i2));
    }

    public void applySlowMotionVideoRecordingMode(Builder builder, int[] iArr) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.SMVR_MODE, iArr);
    }

    public void applyVideoStreamState(Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.RECORDING_END_STREAM, Byte.valueOf(z ^ true ? (byte) 1 : 0));
    }

    public void applyZsd(Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.ZSL_CAPTURE_MODE, Byte.valueOf(z ? (byte) 1 : 0));
    }

    public void copyAiSceneFromCaptureResultToRequest(CaptureResult captureResult, Builder builder) {
        Integer num = (Integer) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.AI_SCENE_DETECTED);
        if (num != null) {
            VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_AI_SCENE_MODE, num);
        }
    }

    public void copyFpcDataFromCaptureResultToRequest(CaptureResult captureResult, Builder builder) {
        byte[] bArr = (byte[]) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.DISTORTION_FPC_DATA);
        if (bArr != null && bArr.length / 8 == 23) {
            VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_DISTORTION_FPC_DATA, bArr);
        }
    }

    public VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>> getDefaultSteamConfigurationsTag() {
        return CameraCharacteristicsVendorTags.SCALER_AVAILABLE_STREAM_CONFIGURATIONS;
    }
}
