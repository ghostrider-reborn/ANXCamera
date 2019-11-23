package com.ss.android.vesdk;

import android.support.annotation.Keep;

@Keep
public class VEMusicSRTEffectParam {
    private String mEffectResourcePath;
    private int mFontFaceIndex;
    private String mFontTTFPath;
    public GetMusicCurrentPorgressInvoker mGetMusicProgressInvoker;
    private boolean mParamUpdated = false;
    private SRTData[] mSrtData;

    public interface GetMusicCurrentPorgressInvoker {
        float getMusicCurrentProgress();
    }

    public static class SRTData {
        public String mData;
        public int mEndTime;
        public int mIndex;
        public int mStartTime;

        private SRTData() {
        }

        public SRTData(String str, int i, int i2, int i3) {
            this.mData = str;
            this.mStartTime = i;
            this.mEndTime = i2;
            this.mIndex = i3;
        }
    }

    private VEMusicSRTEffectParam() {
    }

    public VEMusicSRTEffectParam(SRTData[] sRTDataArr, String str, String str2, int i, GetMusicCurrentPorgressInvoker getMusicCurrentPorgressInvoker) {
        this.mSrtData = sRTDataArr;
        this.mFontTTFPath = str2;
        this.mEffectResourcePath = str;
        this.mFontFaceIndex = i;
        this.mGetMusicProgressInvoker = getMusicCurrentPorgressInvoker;
        this.mParamUpdated = true;
    }

    private int[] convertDataToUnicode32(SRTData sRTData) {
        int codePointCount = sRTData.mData.codePointCount(0, sRTData.mData.length());
        int[] iArr = new int[(codePointCount + 3)];
        iArr[0] = sRTData.mIndex;
        iArr[1] = sRTData.mStartTime;
        iArr[2] = sRTData.mEndTime;
        for (int i = 0; i < codePointCount; i++) {
            iArr[i + 3] = sRTData.mData.codePointAt(i);
        }
        return iArr;
    }

    private boolean getParamUpdated() {
        if (!this.mParamUpdated) {
            return false;
        }
        this.mParamUpdated = false;
        return true;
    }

    public String getEffectResPath() {
        return this.mEffectResourcePath;
    }

    public int getFontFaceIndex() {
        return this.mFontFaceIndex;
    }

    public String getFontTTFPath() {
        return this.mFontTTFPath;
    }

    public float getMusicProgress() {
        if (this.mGetMusicProgressInvoker != null) {
            return this.mGetMusicProgressInvoker.getMusicCurrentProgress();
        }
        return 0.0f;
    }

    public int[][] getSrtData() {
        int[][] iArr = new int[this.mSrtData.length][];
        for (int i = 0; i < this.mSrtData.length; i++) {
            iArr[i] = convertDataToUnicode32(this.mSrtData[i]);
        }
        return iArr;
    }

    public void updateEffectResPath(SRTData[] sRTDataArr, String str, String str2, int i, GetMusicCurrentPorgressInvoker getMusicCurrentPorgressInvoker) {
        this.mSrtData = sRTDataArr;
        this.mFontTTFPath = str2;
        this.mEffectResourcePath = str;
        this.mFontFaceIndex = i;
        this.mGetMusicProgressInvoker = getMusicCurrentPorgressInvoker;
        this.mParamUpdated = true;
    }
}
