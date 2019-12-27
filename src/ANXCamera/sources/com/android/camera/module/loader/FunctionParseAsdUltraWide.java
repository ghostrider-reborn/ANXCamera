package com.android.camera.module.loader;

import android.hardware.camera2.CaptureResult;
import com.android.camera.CameraSettings;
import com.android.camera.data.DataRepository;
import com.android.camera.module.Camera2Module;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CaptureResultParser;
import io.reactivex.functions.Function;
import java.lang.ref.WeakReference;

public class FunctionParseAsdUltraWide implements Function<CaptureResult, CaptureResult> {
    private static final int[] ULTRA_WIDE_RECOM = {2, 1};
    public static final int ULTRA_WIDE_SIDEFACE_TYPE = 2;
    public static final int ULTRA_WIDE_TOWER_BUILDING_TYPE = 1;
    private boolean mEnable;
    private boolean mIsOpenUltraWide;
    private WeakReference<Camera2Proxy.UltraWideCheckCallback> mUltrawidecheckcallback;

    public FunctionParseAsdUltraWide(int i, Camera2Proxy.UltraWideCheckCallback ultraWideCheckCallback) {
        this.mEnable = DataRepository.dataItemFeature().isSupportUltraWide() && (i == 163 || i == 165) && !CameraSettings.isUltraWideConfigOpen(i) && !CameraSettings.isUltraPixelOn() && !CameraSettings.isMacroModeEnabled(i);
        if (this.mEnable) {
            this.mUltrawidecheckcallback = new WeakReference<>(ultraWideCheckCallback);
        }
    }

    private boolean isOpenUltraWide(int i) {
        int i2 = 0;
        boolean z = false;
        while (true) {
            int[] iArr = ULTRA_WIDE_RECOM;
            if (i2 >= iArr.length) {
                return z;
            }
            z = iArr[i2] == i;
            if (z) {
                return z;
            }
            i2++;
        }
    }

    public CaptureResult apply(CaptureResult captureResult) throws Exception {
        if (!this.mEnable) {
            return captureResult;
        }
        Camera2Proxy.UltraWideCheckCallback ultraWideCheckCallback = (Camera2Proxy.UltraWideCheckCallback) this.mUltrawidecheckcallback.get();
        if (ultraWideCheckCallback == null || !ultraWideCheckCallback.isUltraWideDetectStarted()) {
            return captureResult;
        }
        int ultraWideDetectedResult = CaptureResultParser.getUltraWideDetectedResult(captureResult);
        boolean isOpenUltraWide = isOpenUltraWide(ultraWideDetectedResult);
        if (((Camera2Module) ultraWideCheckCallback).isZoomRatioBetweenUltraAndWide()) {
            isOpenUltraWide = false;
        }
        if (this.mIsOpenUltraWide == isOpenUltraWide) {
            return captureResult;
        }
        this.mIsOpenUltraWide = isOpenUltraWide;
        ultraWideCheckCallback.onUltraWideChanged(isOpenUltraWide, ultraWideDetectedResult);
        return captureResult;
    }
}
