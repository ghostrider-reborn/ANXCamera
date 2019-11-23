package com.android.camera.module.loader;

import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.Face;
import com.android.camera.CameraSettings;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CaptureResultParser;
import io.reactivex.functions.Function;

public class FunctionParseAiScene implements Function<CaptureResult, Integer> {
    private static final String TAG = "FunctionParseAiScene";
    private CameraCapabilities mCameraCapabilities;
    private int mCurrentFaceScene;
    private int mLatestFaceScene;
    private int mModuleIndex;
    private int mParsedAiScene;
    private int mSameFaceSceneDetectedTimes;
    private final boolean mSupportMoonMode = DataRepository.dataItemFeature().hN();

    public FunctionParseAiScene(int i, CameraCapabilities cameraCapabilities) {
        this.mModuleIndex = i;
        this.mCameraCapabilities = cameraCapabilities;
    }

    private boolean faceSceneFiltering(int i) {
        if (this.mLatestFaceScene != i) {
            this.mLatestFaceScene = i;
            this.mSameFaceSceneDetectedTimes = 0;
        } else if (this.mSameFaceSceneDetectedTimes < 20) {
            this.mSameFaceSceneDetectedTimes++;
            if (20 == this.mSameFaceSceneDetectedTimes && this.mCurrentFaceScene != this.mLatestFaceScene) {
                this.mLatestFaceScene = this.mCurrentFaceScene;
                this.mCurrentFaceScene = this.mLatestFaceScene;
                return true;
            }
        }
        return false;
    }

    public Integer apply(CaptureResult captureResult) {
        int i;
        int hdrDetectedScene;
        Face[] faceArr = (Face[]) captureResult.get(CaptureResult.STATISTICS_FACES);
        if (this.mModuleIndex == 171 || CameraSettings.isFrontCamera() || faceArr == null || faceArr.length <= 0) {
            i = Integer.MIN_VALUE;
        } else {
            i = Integer.MIN_VALUE;
            for (Face bounds : faceArr) {
                if (bounds.getBounds().width() > 300) {
                    Log.c(TAG, "parseAiSceneResult: AI_SCENE_MODE_HUMAN  face.length = " + faceArr.length + ";face.width = " + bounds.getBounds().width() + ";hdrMode = " + hdrDetectedScene);
                    i = (hdrDetectedScene != 1 || this.mCameraCapabilities == null || this.mCameraCapabilities.getMiAlgoASDVersion() >= 2.0f) ? 25 : -1;
                }
            }
        }
        if (faceSceneFiltering(i)) {
            if (i == Integer.MIN_VALUE) {
                int asdDetectedModes = CaptureResultParser.getAsdDetectedModes(captureResult);
                if (!this.mSupportMoonMode && asdDetectedModes == 35) {
                    Log.w(TAG, "detected moon mode on unsupported device, set scene negative");
                    asdDetectedModes = 0;
                }
                if (asdDetectedModes < 0) {
                    Log.e(TAG, "parseAiSceneResult: parse a error result: " + asdDetectedModes);
                    this.mParsedAiScene = 0;
                } else {
                    this.mParsedAiScene = asdDetectedModes;
                }
            } else {
                this.mParsedAiScene = i;
            }
        }
        return Integer.valueOf(this.mParsedAiScene);
    }

    public void resetScene() {
        this.mLatestFaceScene = 0;
        this.mParsedAiScene = 0;
    }
}
