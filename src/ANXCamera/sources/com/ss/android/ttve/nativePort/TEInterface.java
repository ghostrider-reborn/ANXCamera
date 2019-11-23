package com.ss.android.ttve.nativePort;

import android.graphics.Bitmap;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Surface;
import com.ss.android.vesdk.ROTATE_DEGREE;
import com.ss.android.vesdk.VEException;
import com.ss.android.vesdk.VEGestureType;
import com.ss.android.vesdk.VEMusicSRTEffectParam;
import com.ss.android.vesdk.VERect;
import com.ss.android.vesdk.VEResult;
import com.ss.android.vesdk.VEStickerAnimator;
import com.ss.android.vesdk.VETimelineParams;
import com.ss.android.vesdk.VEWaterMarkPosition;
import com.ss.android.vesdk.VEWatermarkParam;
import java.util.ArrayList;
import java.util.Arrays;

@Keep
public final class TEInterface extends TENativeServiceBase {
    private static final int OPTION_UPDATE_ANYTIME = 1;
    private static final int OPTION_UPDATE_BEFORE_PREPARE = 0;
    private static final String TAG = "TEInterface";
    private int mHostTrackIndex = -1;
    private long mNative = 0;

    static {
        TENativeLibsLoader.loadLibrary();
    }

    private TEInterface() {
    }

    public static TEInterface createEngine() {
        TEInterface tEInterface = new TEInterface();
        long nativeCreateEngine = tEInterface.nativeCreateEngine();
        if (nativeCreateEngine == 0) {
            return null;
        }
        tEInterface.mNative = nativeCreateEngine;
        return tEInterface;
    }

    private native int nativeAddAudioTrack(long j, String str, int i, int i2, int i3, int i4, boolean z);

    private native int nativeAddAudioTrack2(long j, String str, int i, int i2, int i3, int i4, boolean z, int i5, int i6);

    private native int nativeAddAudioTrackMV(long j, String str, int i, int i2, int i3, int i4, int i5, boolean z);

    private native int nativeAddExternalTrack(long j, String[] strArr, String[] strArr2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, double d, double d2, double d3, double d4, int i, int i2);

    private native int nativeAddExternalTrackMV(long j, String[] strArr, String[] strArr2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5, double d, double d2, double d3, double d4, int i, int i2);

    private native int[] nativeAddFilters(long j, int[] iArr, String[] strArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5);

    private native int nativeAddInfoSticker(long j, String str, String[] strArr);

    private native void nativeAddMetaData(long j, String str, String str2);

    private native int nativeAddVideoTrack(long j, String[] strArr, String[] strArr2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5, int i);

    private native int nativeAdjustFilterInOut(long j, int i, int i2, int i3);

    private native int nativeBegin2DBrush(long j);

    private native int nativeCancelGetImages(long j);

    private native void nativeClearDisplay(long j, int i);

    private native long nativeCreateEngine();

    private native int nativeCreateImageScene(long j, Bitmap[] bitmapArr, int[] iArr, int[] iArr2, String[] strArr, int[] iArr3, int[] iArr4, String[] strArr2, String[][] strArr3, float[] fArr, int i);

    private native int nativeCreateScene(long j, String str, String[] strArr, String[] strArr2, String[] strArr3, String[][] strArr4, int i);

    private native int nativeCreateScene2(long j, String[] strArr, int[] iArr, int[] iArr2, String[] strArr2, int[] iArr3, int[] iArr4, String[] strArr3, String[][] strArr4, float[] fArr, int[] iArr5, int i);

    private native int nativeCreateSceneMV(long j, String[] strArr, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, String[] strArr2, int[] iArr5, int[] iArr6, int[] iArr7, int[] iArr8, int[] iArr9, String[] strArr3, String[][] strArr4, float[] fArr, int i);

    private native int nativeCreateTimeline(long j);

    private native int nativeDeleteAudioTrack(long j, int i);

    private native int nativeDeleteExternalTrack(long j, int i);

    private native int nativeDestroyEngine(long j);

    private native int nativeEnd2DBrush(long j, String str);

    private native int nativeGet2DBrushStrokeCount(long j);

    private native int nativeGetCurPosition(long j);

    private native int nativeGetCurState(long j);

    private native int[] nativeGetDisplayDumpSize(long j);

    private native int nativeGetDisplayImage(long j, byte[] bArr, int i, int i2);

    private native int nativeGetDuration(long j);

    private native int nativeGetExternalTrackFilter(long j, int i);

    private native int nativeGetImages(long j, int[] iArr, int i, int i2);

    private native float[] nativeGetInfoStickerBoundingBox(long j, int i);

    private native int[] nativeGetInitResolution(long j);

    private native String nativeGetMetaData(long j, String str);

    private native float nativeGetTrackVolume(long j, int i, int i2, int i3);

    private native Object nativeInitMVResources(long j, String str, String[] strArr, String[] strArr2, boolean z);

    private native boolean nativeIsInfoStickerAnimatable(long j, int i);

    private native int nativePause(long j);

    private native int nativePauseInfoStickerAnimation(long j, boolean z);

    private native int nativePauseSync(long j);

    private native int nativePrepareEngine(long j, int i);

    private native int nativeProcessLongPressEvent(long j, float f, float f2);

    private native int nativeProcessPanEvent(long j, float f, float f2, float f3, float f4, float f5);

    private native int nativeProcessRotationEvent(long j, float f, float f2);

    private native int nativeProcessScaleEvent(long j, float f, float f2);

    private native int nativeProcessTouchDownEvent(long j, float f, float f2, int i);

    private native int nativeProcessTouchMoveEvent(long j, float f, float f2);

    private native int nativeProcessTouchUpEvent(long j, float f, float f2, int i);

    private native void nativeReleasePreviewSurface(long j);

    private native int nativeRemoveFilter(long j, int[] iArr);

    private native int nativeRemoveInfoSticker(long j, int i);

    private native int nativeRestore(long j, String str);

    private native String nativeSave(long j);

    private native int nativeSeek(long j, int i, int i2, int i3, int i4);

    private native int nativeSet2DBrushCanvasColor(long j, float f);

    private native int nativeSet2DBrushColor(long j, float f, float f2, float f3, float f4);

    private native int nativeSet2DBrushSize(long j, float f);

    private native void nativeSetBackGroundColor(long j, int i);

    private native int nativeSetClipAttr(long j, int i, int i2, int i3, String str, String str2);

    private native int nativeSetDestroyVersion(long j, boolean z);

    private native void nativeSetDisplayState(long j, float f, float f2, float f3, float f4, int i, int i2, int i3);

    private native int nativeSetDldThrVal(long j, int i);

    private native int nativeSetDleEnabled(long j, boolean z);

    private native void nativeSetEnableMultipleAudioFilter(long j, boolean z);

    private native void nativeSetEnableRemuxVideo(long j, boolean z);

    private native void nativeSetEncoderParallel(long j, boolean z);

    private native void nativeSetExpandLastFrame(long j, boolean z);

    private native int nativeSetFilterParam(long j, int i, String str, VEMusicSRTEffectParam vEMusicSRTEffectParam);

    private native int nativeSetFilterParam(long j, int i, String str, VEStickerAnimator vEStickerAnimator);

    private native int nativeSetFilterParam(long j, int i, String str, String str2);

    private native void nativeSetOption(long j, int i, String str, float f);

    private native void nativeSetOption(long j, int i, String str, long j2);

    private native void nativeSetOption(long j, int i, String str, String str2);

    private native void nativeSetOptionArray(long j, int i, String[] strArr, long[] jArr);

    private native void nativeSetPreviewFps(long j, int i);

    private native int nativeSetPreviewScaleMode(long j, int i);

    private native void nativeSetPreviewSurface(long j, Surface surface);

    private native void nativeSetSpeedRatio(long j, float f);

    private native void nativeSetSurfaceSize(long j, int i, int i2);

    private native int nativeSetTimeRange(long j, int i, int i2, int i3);

    private native boolean nativeSetTrackVolume(long j, int i, int i2, float f);

    private native void nativeSetViewPort(long j, int i, int i2, int i3, int i4);

    private native void nativeSetWaterMark(long j, ArrayList<String[]> arrayList, int i, int i2, int i3, int i4, int i5, long j2, int i6, VEWatermarkParam.VEWatermarkMask vEWatermarkMask);

    private native int nativeStart(long j);

    private native int nativeStop(long j);

    private native boolean nativeTestSerialization(long j);

    private native int nativeUndo2DBrush(long j);

    private native int nativeUpdateAudioTrack(long j, int i, int i2, int i3, int i4, int i5, boolean z);

    private native int nativeUpdateAudioTrack2(long j, int i, int i2, int i3, int i4, int i5, boolean z, int i6, int i7);

    private native int nativeUpdateScene(long j, String[] strArr, int[] iArr, int[] iArr2);

    private native int nativeUpdateSceneFileOrder(long j, int[] iArr);

    private native int nativeUpdateSceneTime(long j, boolean[] zArr, int[] iArr, int[] iArr2, int[] iArr3, double[] dArr);

    private native int nativeUpdateTrackClip(long j, int i, int i2, String[] strArr);

    private native int nativeUpdateTrackFilter(long j, int i, int i2, boolean z);

    public int addAudioTrack(String str, int i, int i2, int i3, int i4, boolean z) {
        if (this.mNative == 0) {
            return -1;
        }
        if (TextUtils.isEmpty(str)) {
            return -100;
        }
        return nativeAddAudioTrack(this.mNative, str, i, i2, i3, i4, z);
    }

    public int addAudioTrack(String str, int i, int i2, int i3, int i4, boolean z, int i5, int i6) {
        if (this.mNative == 0) {
            return -1;
        }
        if (TextUtils.isEmpty(str)) {
            return -100;
        }
        return nativeAddAudioTrack2(this.mNative, str, i, i2, i3, i4, z, i5, i6);
    }

    public int addAudioTrackForMV(String str, int i, int i2, int i3, int i4, int i5, boolean z) {
        if (this.mNative == 0) {
            return -1;
        }
        if (TextUtils.isEmpty(str)) {
            return -100;
        }
        return nativeAddAudioTrackMV(this.mNative, str, i, i2, i3, i4, i5, z);
    }

    public int addExternalTrack(String[] strArr, String[] strArr2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, double d, double d2, double d3, double d4, int i) {
        return nativeAddExternalTrack(this.mNative, strArr, strArr2, iArr, iArr2, iArr, iArr2, d, d2, d3, d4, 5, this.mHostTrackIndex);
    }

    public int[] addFilters(int[] iArr, String[] strArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5) {
        if (this.mNative == 0) {
            return new int[]{-1};
        }
        return nativeAddFilters(this.mNative, iArr, strArr, iArr2, iArr3, iArr4, iArr5);
    }

    public int addInfoSticker(String str, String[] strArr) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : this.mHostTrackIndex < 0 ? VEResult.TER_INVALID_STAT : nativeAddInfoSticker(this.mNative, str, strArr);
    }

    public void addMetaData(String str, String str2) {
        if (this.mNative != 0) {
            nativeAddMetaData(this.mNative, str, str2);
        }
    }

    public int addSticker(String[] strArr, String[] strArr2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, double d, double d2, double d3, double d4) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        if (this.mHostTrackIndex < 0) {
            return VEResult.TER_INVALID_STAT;
        }
        return nativeAddExternalTrack(this.mNative, strArr, strArr2, iArr, iArr2, iArr3, iArr4, d, d2, d3, d4, 0, this.mHostTrackIndex);
    }

    public int addVideoTrackForMV(String[] strArr, String[] strArr2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5) {
        if (this.mNative == 0) {
            return -1;
        }
        if (this.mHostTrackIndex < 0) {
            return VEResult.TER_INVALID_STAT;
        }
        return nativeAddVideoTrack(this.mNative, strArr, strArr2, iArr, iArr2, iArr3, iArr4, iArr5, this.mHostTrackIndex);
    }

    public int addWaterMark(String[] strArr, String[] strArr2, int[] iArr, int[] iArr2, double d, double d2, double d3, double d4) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        if (this.mHostTrackIndex < 0) {
            return VEResult.TER_INVALID_STAT;
        }
        return nativeAddExternalTrack(this.mNative, strArr, strArr2, iArr, iArr2, iArr, iArr2, d, d2, d3, d4, 5, this.mHostTrackIndex);
    }

    public int adjustFilterInOut(int i, int i2, int i3) {
        return nativeAdjustFilterInOut(this.mNative, i, i2, i3);
    }

    public int begin2DBrush() {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeBegin2DBrush(this.mNative);
    }

    public int cancelGetImages() {
        return nativeCancelGetImages(this.mNative);
    }

    public void clearDisplay(int i) {
        nativeClearDisplay(this.mNative, i);
    }

    public int createImageScene(Bitmap[] bitmapArr, int[] iArr, int[] iArr2, String[] strArr, int[] iArr3, int[] iArr4, String[] strArr2, String[][] strArr3, float[] fArr, int i) {
        float[] fArr2;
        Bitmap[] bitmapArr2;
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        if (fArr == null) {
            bitmapArr2 = bitmapArr;
            float[] fArr3 = new float[bitmapArr2.length];
            Arrays.fill(fArr3, 1.0f);
            fArr2 = fArr3;
        } else {
            bitmapArr2 = bitmapArr;
            fArr2 = fArr;
        }
        int nativeCreateImageScene = nativeCreateImageScene(this.mNative, bitmapArr2, iArr, iArr2, strArr, iArr3, iArr4, strArr2, strArr3, fArr2, i);
        if (nativeCreateImageScene < 0) {
            return nativeCreateImageScene;
        }
        this.mHostTrackIndex = nativeCreateImageScene;
        return 0;
    }

    public int createScene(String str, String[] strArr, String[] strArr2, String[] strArr3, String[][] strArr4, int i) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        int nativeCreateScene = nativeCreateScene(this.mNative, str, strArr, strArr2, strArr3, strArr4, i);
        if (nativeCreateScene < 0) {
            return nativeCreateScene;
        }
        this.mHostTrackIndex = nativeCreateScene;
        return 0;
    }

    public int createScene2(String[] strArr, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, String[] strArr2, int[] iArr5, int[] iArr6, int[] iArr7, int[] iArr8, int[] iArr9, String[] strArr3, String[][] strArr4, float[] fArr, int i) {
        float[] fArr2;
        String[] strArr5;
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        if (fArr == null) {
            strArr5 = strArr;
            float[] fArr3 = new float[strArr5.length];
            Arrays.fill(fArr3, 1.0f);
            fArr2 = fArr3;
        } else {
            strArr5 = strArr;
            fArr2 = fArr;
        }
        int nativeCreateSceneMV = nativeCreateSceneMV(this.mNative, strArr5, iArr, iArr2, iArr3, iArr4, strArr2, iArr5, iArr6, iArr7, iArr8, iArr9, strArr3, strArr4, fArr2, i);
        if (nativeCreateSceneMV < 0) {
            return nativeCreateSceneMV;
        }
        this.mHostTrackIndex = nativeCreateSceneMV;
        return 0;
    }

    public int createScene2(String[] strArr, int[] iArr, int[] iArr2, String[] strArr2, int[] iArr3, int[] iArr4, String[] strArr3, String[][] strArr4, float[] fArr, int i) {
        return createScene2(strArr, iArr, iArr2, strArr2, iArr3, iArr4, strArr3, strArr4, fArr, (int[]) null, i);
    }

    public int createScene2(String[] strArr, int[] iArr, int[] iArr2, String[] strArr2, int[] iArr3, int[] iArr4, String[] strArr3, String[][] strArr4, float[] fArr, int[] iArr5, int i) {
        float[] fArr2;
        String[] strArr5;
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        if (fArr == null) {
            strArr5 = strArr;
            float[] fArr3 = new float[strArr5.length];
            Arrays.fill(fArr3, 1.0f);
            fArr2 = fArr3;
        } else {
            strArr5 = strArr;
            fArr2 = fArr;
        }
        int nativeCreateScene2 = nativeCreateScene2(this.mNative, strArr5, iArr, iArr2, strArr2, iArr3, iArr4, strArr3, strArr4, fArr2, iArr5, i);
        if (nativeCreateScene2 < 0) {
            return nativeCreateScene2;
        }
        this.mHostTrackIndex = nativeCreateScene2;
        return 0;
    }

    public int createTimeline() {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeCreateTimeline(this.mNative);
    }

    public int deleteAudioTrack(int i) {
        if (this.mNative == 0) {
            return -1;
        }
        return nativeDeleteAudioTrack(this.mNative, i);
    }

    public int deleteSticker(int i) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        if (i < 0) {
            return -100;
        }
        return nativeDeleteExternalTrack(this.mNative, i);
    }

    public int deleteWatermark(int i) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        if (i < 0) {
            return -100;
        }
        return nativeDeleteExternalTrack(this.mNative, i);
    }

    public int destroyEngine() {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        int nativeDestroyEngine = nativeDestroyEngine(this.mNative);
        this.mNative = 0;
        return nativeDestroyEngine;
    }

    public void enableSimpleProcessor(boolean z) {
        setOption(0, "engine processor mode", z ? 1 : 0);
    }

    public int end2DBrush(@NonNull String str) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeEnd2DBrush(this.mNative, str);
    }

    public int get2DBrushStrokeCount() {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeGet2DBrushStrokeCount(this.mNative);
    }

    public int getCurPosition() {
        if (this.mNative == 0) {
            return -1;
        }
        return nativeGetCurPosition(this.mNative);
    }

    public int getCurState() {
        if (this.mNative == 0) {
            return -1;
        }
        return nativeGetCurState(this.mNative);
    }

    public int getDisplayImage(byte[] bArr, int i, int i2) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        return nativeGetDisplayImage(this.mNative, bArr, i, i2);
    }

    public VERect getDisplayRect() {
        if (this.mNative == 0) {
            return new VERect(0, 0, 0, 0);
        }
        int[] nativeGetDisplayDumpSize = nativeGetDisplayDumpSize(this.mNative);
        return new VERect(nativeGetDisplayDumpSize[0], nativeGetDisplayDumpSize[1], nativeGetDisplayDumpSize[2], nativeGetDisplayDumpSize[3]);
    }

    public int getDuration() {
        if (this.mNative == 0) {
            return -1;
        }
        return nativeGetDuration(this.mNative);
    }

    public int getImages(int[] iArr, int i, int i2) {
        return nativeGetImages(this.mNative, iArr, i, i2);
    }

    public float[] getInfoStickerBoundingBox(int i) throws VEException {
        if (this.mNative == 0) {
            throw new VEException(VEResult.TER_INVALID_HANDLER, "");
        } else if (this.mHostTrackIndex >= 0) {
            float[] nativeGetInfoStickerBoundingBox = nativeGetInfoStickerBoundingBox(this.mNative, i);
            if (nativeGetInfoStickerBoundingBox[0] == 0.0f) {
                float[] fArr = new float[4];
                System.arraycopy(nativeGetInfoStickerBoundingBox, 1, fArr, 0, 4);
                return fArr;
            }
            throw new VEException(-1, "native getInfoStickerBoundingBox failed: " + nativeGetInfoStickerBoundingBox[0]);
        } else {
            throw new VEException(-100, "");
        }
    }

    public int[] getInitResolution() {
        return this.mNative == 0 ? new int[]{-1, -1, -1, -1} : nativeGetInitResolution(this.mNative);
    }

    public String getMetaData(String str) {
        return this.mNative == 0 ? "" : nativeGetMetaData(this.mNative, str);
    }

    public long getNativeHandler() {
        return this.mNative;
    }

    public int getStickerFilterIndex(int i) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        if (i < 0) {
            return -100;
        }
        return nativeGetExternalTrackFilter(this.mNative, i);
    }

    public float getTrackVolume(int i, int i2, int i3) {
        if (this.mNative == 0) {
            return 0.0f;
        }
        return nativeGetTrackVolume(this.mNative, i, i2, i3);
    }

    public Object initMVResources(String str, String[] strArr, String[] strArr2, boolean z) {
        return nativeInitMVResources(this.mNative, str, strArr, strArr2, z);
    }

    public boolean isInfoStickerAnimatable(int i) {
        if (this.mNative != 0 && this.mHostTrackIndex >= 0) {
            return nativeIsInfoStickerAnimatable(this.mNative, i);
        }
        return false;
    }

    public native int nativeSetAudioOffset(long j, int i, int i2);

    public int pause() {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativePause(this.mNative);
    }

    public int pauseInfoStickerAnimation(boolean z) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : this.mHostTrackIndex < 0 ? VEResult.TER_INVALID_STAT : nativePauseInfoStickerAnimation(this.mNative, z);
    }

    public int pauseSync() {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativePauseSync(this.mNative);
    }

    public int prepareEngine(int i) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativePrepareEngine(this.mNative, i);
    }

    public int processLongPressEvent(float f, float f2) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeProcessLongPressEvent(this.mNative, f, f2);
    }

    public int processPanEvent(float f, float f2, float f3, float f4, float f5) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        return nativeProcessPanEvent(this.mNative, f, f2, f3, f4, f5);
    }

    public int processRotationEvent(float f, float f2) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeProcessRotationEvent(this.mNative, f, f2);
    }

    public int processScaleEvent(float f, float f2) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeProcessScaleEvent(this.mNative, f, f2);
    }

    public int processTouchDownEvent(float f, float f2, VEGestureType vEGestureType) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        return nativeProcessTouchDownEvent(this.mNative, f, f2, vEGestureType.ordinal());
    }

    public int processTouchMoveEvent(float f, float f2) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeProcessTouchMoveEvent(this.mNative, f, f2);
    }

    public int processTouchUpEvent(float f, float f2, VEGestureType vEGestureType) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        return nativeProcessTouchUpEvent(this.mNative, f, f2, vEGestureType.ordinal());
    }

    public void releasePreviewSurface() {
        if (this.mNative != 0) {
            nativeReleasePreviewSurface(this.mNative);
        }
    }

    public int removeFilter(int[] iArr) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeRemoveFilter(this.mNative, iArr);
    }

    public int removeInfoSticker(int i) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : this.mHostTrackIndex < 0 ? VEResult.TER_INVALID_STAT : nativeRemoveInfoSticker(this.mNative, i);
    }

    public int restore(String str) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeRestore(this.mNative, str);
    }

    @Nullable
    public String save() {
        if (this.mNative == 0) {
            return null;
        }
        String nativeSave = nativeSave(this.mNative);
        if (TextUtils.isEmpty(nativeSave)) {
            return null;
        }
        return nativeSave;
    }

    public int seek(int i, int i2, int i3, int i4) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        return nativeSeek(this.mNative, i, i2, i3, i4);
    }

    public int set2DBrushCanvasAlpha(float f) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeSet2DBrushCanvasColor(this.mNative, f);
    }

    public int set2DBrushColor(float f, float f2, float f3, float f4) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        return nativeSet2DBrushColor(this.mNative, f, f2, f3, f4);
    }

    public int set2DBrushSize(float f) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeSet2DBrushSize(this.mNative, f);
    }

    public int setAudioOffset(int i, int i2) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeSetAudioOffset(this.mNative, i, i2);
    }

    public void setBackGroundColor(int i) {
        if (this.mNative != 0) {
            nativeSetBackGroundColor(this.mNative, i);
        }
    }

    public int setClipAttr(int i, int i2, int i3, String str, String str2) {
        return nativeSetClipAttr(this.mNative, i, i2, i3, str, str2);
    }

    public void setCompileCommonEncodeOptions(int i, int i2) {
        setOption(0, "CompileBitrateMode", (long) i);
        setOption(0, "CompileEncodeProfile", (long) i2);
    }

    public void setCompileFps(int i) {
        setOption(0, "CompileFps", (long) i);
    }

    public void setCompileHardwareEncodeOptions(int i) {
        setOption(0, "CompileHardwareBitrate", (long) i);
    }

    public void setCompileSoftwareEncodeOptions(int i, long j, int i2, int i3) {
        setOption(0, "CompileSoftwareCrf", (long) i);
        setOption(0, "CompileSoftwareMaxrate", j);
        setOption(0, "CompileSoftwarePreset", (long) i2);
        setOption(0, "CompileSoftwareQp", (long) i3);
    }

    public void setCompileType(int i) {
        setOption(0, "CompileType", (long) i);
    }

    public void setCompileWatermark(VEWatermarkParam vEWatermarkParam) {
        setOption(0, "CompilePathWatermark", vEWatermarkParam.extFile);
        setOption(0, "CompilePathWavWatermark", "");
    }

    public void setCrop(int i, int i2, int i3, int i4) {
        setOption(1, new String[]{"engine crop x", "engine crop y", "engine crop width", "engine crop height"}, new long[]{(long) i, (long) i2, (long) i3, (long) i4});
    }

    public int setDestroyVersion(boolean z) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeSetDestroyVersion(this.mNative, z);
    }

    public void setDisplayState(float f, float f2, float f3, float f4, int i, int i2, boolean z) {
        if (this.mNative != 0) {
            nativeSetDisplayState(this.mNative, f, f2, f3, f4, i, i2, z ? 1 : 0);
        }
    }

    public int setDldThrVal(int i) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeSetDldThrVal(this.mNative, i);
    }

    public int setDleEnabled(boolean z) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeSetDleEnabled(this.mNative, z);
    }

    public void setEnableInterLeave(boolean z) {
        long j = 0;
        if (this.mNative != 0) {
            if (z) {
                j = 1;
            }
            setOption(0, "CompileInterleave", j);
        }
    }

    public void setEnableMultipleAudioFilter(boolean z) {
        if (this.mNative != 0) {
            nativeSetEnableMultipleAudioFilter(this.mNative, z);
        }
    }

    public void setEnableRemuxVideo(boolean z) {
        if (this.mNative != 0) {
            nativeSetEnableRemuxVideo(this.mNative, z);
        }
    }

    public void setEncGopSize(int i) {
        setOption(0, "video gop size", (long) i);
    }

    public void setEncoderParallel(boolean z) {
        if (this.mNative != 0) {
            nativeSetEncoderParallel(this.mNative, z);
        }
    }

    public void setEngineCompilePath(String str, String str2) {
        setOption(0, "CompilePath", str);
        setOption(0, "CompilePathWav", str2);
    }

    public void setExpandLastFrame(boolean z) {
        if (this.mNative != 0) {
            nativeSetExpandLastFrame(this.mNative, z);
        }
    }

    public int setFilterParam(int i, String str, VEMusicSRTEffectParam vEMusicSRTEffectParam) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        return nativeSetFilterParam(this.mNative, i, str, vEMusicSRTEffectParam);
    }

    public int setFilterParam(int i, String str, VEStickerAnimator vEStickerAnimator) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        return nativeSetFilterParam(this.mNative, i, str, vEStickerAnimator);
    }

    public int setFilterParam(int i, String str, String str2) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        return nativeSetFilterParam(this.mNative, i, str, str2);
    }

    public void setLooping(boolean z) {
        setOption(1, "engine loop play", z ? 1 : 0);
    }

    public void setMaxWidthHeight(int i, int i2) {
        if (i > 0) {
            setOption(0, "engine max video width", (long) i);
        }
        if (i2 > 0) {
            setOption(0, "engine max video height", (long) i2);
        }
    }

    public void setOption(int i, String str, float f) {
        if (this.mNative != 0) {
            nativeSetOption(this.mNative, i, str, f);
        }
    }

    public void setOption(int i, String str, long j) {
        if (this.mNative != 0) {
            nativeSetOption(this.mNative, i, str, j);
        }
    }

    public void setOption(int i, String str, String str2) {
        if (this.mNative != 0) {
            nativeSetOption(this.mNative, i, str, str2);
        }
    }

    public void setOption(int i, String[] strArr, long[] jArr) {
        if (this.mNative != 0) {
            nativeSetOptionArray(this.mNative, i, strArr, jArr);
        }
    }

    public void setPageMode(int i) {
        setOption(0, "engine page mode", (long) i);
    }

    public int setPreviewFps(int i) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        nativeSetPreviewFps(this.mNative, i);
        return 0;
    }

    public int setPreviewScaleMode(int i) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeSetPreviewScaleMode(this.mNative, i);
    }

    public void setPreviewSurface(Surface surface) {
        if (this.mNative != 0) {
            nativeSetPreviewSurface(this.mNative, surface);
        }
    }

    public void setResizer(int i, float f, float f2) {
        setOption(0, "filter mode", (long) i);
        setOption(0, "resizer offset x percent", f);
        setOption(0, "resizer offset y percent", f2);
    }

    public void setScaleMode(int i) {
        setOption(0, "filter mode", (long) i);
    }

    public void setSpeedRatio(float f) {
        if (this.mNative != 0) {
            nativeSetSpeedRatio(this.mNative, f);
        }
    }

    public void setSurfaceSize(int i, int i2) {
        if (this.mNative != 0) {
            nativeSetSurfaceSize(this.mNative, i, i2);
        }
    }

    public int setTimeRange(int i, int i2, int i3) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        return nativeSetTimeRange(this.mNative, i, i2, i3);
    }

    public boolean setTrackVolume(int i, int i2, float f) {
        if (this.mNative == 0) {
            return false;
        }
        return nativeSetTrackVolume(this.mNative, i, i2, f);
    }

    public void setUseHwEnc(boolean z) {
        setOption(0, "HardwareVideo", z ? 1 : 0);
    }

    public void setUseLargeMattingModel(boolean z) {
        setOption(0, "UseLargeMattingModel", z ? 1 : 0);
    }

    public void setUsrRotate(int i) {
        if (i == 0) {
            setOption(0, "usr rotate", 0);
        } else if (i == 90) {
            setOption(0, "usr rotate", 1);
        } else if (i == 180) {
            setOption(0, "usr rotate", 2);
        } else if (i != 270) {
            setOption(0, "usr rotate", 0);
        } else {
            setOption(0, "usr rotate", 3);
        }
    }

    public void setVideoCompileBitrate(int i, int i2) {
        setOption(0, "CompileBitrateMode", (long) i);
        setOption(0, "CompileBitrateValue", (long) i2);
    }

    public void setViewPort(int i, int i2, int i3, int i4) {
        if (this.mNative != 0) {
            nativeSetViewPort(this.mNative, i, i2, i3, i4);
        }
    }

    public void setWaterMark(ArrayList<String[]> arrayList, int i, int i2, int i3, int i4, int i5, long j, VEWaterMarkPosition vEWaterMarkPosition, VEWatermarkParam.VEWatermarkMask vEWatermarkMask) {
        if (this.mNative != 0) {
            nativeSetWaterMark(this.mNative, arrayList, i, i2, i3, i4, i5, j, vEWaterMarkPosition.ordinal(), vEWatermarkMask);
        }
    }

    public void setWidthHeight(int i, int i2) {
        if (i > 0) {
            setOption(0, "engine video width", (long) i);
        }
        if (i2 > 0) {
            setOption(0, "engine video height", (long) i2);
        }
    }

    public int start() {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeStart(this.mNative);
    }

    public int stop() {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeStop(this.mNative);
    }

    public native String stringFromJNI();

    public boolean testSerialization() {
        return nativeTestSerialization(this.mNative);
    }

    public int undo2DBrush() {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeUndo2DBrush(this.mNative);
    }

    public int updateAudioTrack(int i, int i2, int i3, int i4, int i5, boolean z) {
        if (this.mNative == 0) {
            return -1;
        }
        if (i < 0) {
            return -100;
        }
        return nativeUpdateAudioTrack(this.mNative, i, i2, i3, i4, i5, z);
    }

    public int updateAudioTrack(int i, int i2, int i3, int i4, int i5, boolean z, int i6, int i7) {
        if (this.mNative == 0) {
            return -1;
        }
        if (i < 0) {
            return -100;
        }
        return nativeUpdateAudioTrack2(this.mNative, i, i2, i3, i4, i5, z, i6, i7);
    }

    public void updateResolution(int i, int i2, int i3, int i4) {
        setOption(1, new String[]{"engine preivew width", "engine preivew height", "engine preivew width percent", "engine preivew height percent"}, new long[]{(long) i, (long) i2, (long) i3, (long) i4});
    }

    public int updateScene(String[] strArr, int[] iArr, int[] iArr2) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        int nativeUpdateScene = nativeUpdateScene(this.mNative, strArr, iArr, iArr2);
        if (nativeUpdateScene < 0) {
            return nativeUpdateScene;
        }
        this.mHostTrackIndex = nativeUpdateScene;
        return 0;
    }

    public int updateSceneFileOrder(VETimelineParams vETimelineParams) {
        return this.mNative == 0 ? VEResult.TER_INVALID_HANDLER : nativeUpdateSceneFileOrder(this.mNative, vETimelineParams.videoFileIndex);
    }

    public int updateSenceTime(VETimelineParams vETimelineParams) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        return nativeUpdateSceneTime(this.mNative, vETimelineParams.enable, vETimelineParams.vTrimIn, vETimelineParams.vTrimOut, ROTATE_DEGREE.toIntArray(vETimelineParams.rotate), vETimelineParams.speed);
    }

    public int updateTrackClips(int i, int i2, String[] strArr) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        return nativeUpdateTrackClip(this.mNative, i, i2, strArr);
    }

    public int updateTrackFilter(int i, int i2, boolean z) {
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        return nativeUpdateTrackFilter(this.mNative, i, i2, z);
    }
}
