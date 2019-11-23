package com.ss.android.vesdk;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntRange;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.v4.os.EnvironmentCompat;
import android.text.TextUtils;
import android.util.Log;
import com.ss.android.vesdk.runtime.VERuntime;
import com.ss.android.vesdk.runtime.cloudconfig.PerformanceConfig;
import com.ss.android.vesdk.runtime.cloudconfig.VECloudConfig;
import com.ss.android.vesdk.settings.VEVideoCompileEncodeSettings;
import com.ss.android.vesdk.settings.VEVideoEncodeProfile;
import org.json.JSONException;
import org.json.JSONObject;

@Keep
public class VEVideoEncodeSettings implements Parcelable {
    public static final Parcelable.Creator<VEVideoEncodeSettings> CREATOR = new Parcelable.Creator<VEVideoEncodeSettings>() {
        public VEVideoEncodeSettings createFromParcel(Parcel parcel) {
            return new VEVideoEncodeSettings(parcel);
        }

        public VEVideoEncodeSettings[] newArray(int i) {
            return new VEVideoEncodeSettings[i];
        }
    };
    private static final String TAG = "VEVideoEncodeSettings";
    public static final int USAGE_COMPILE = 2;
    public static final int USAGE_IMPORT = 3;
    public static final int USAGE_RECORD = 1;
    /* access modifiers changed from: private */
    public ENCODE_BITRATE_MODE bitrateMode;
    /* access modifiers changed from: private */
    public int bps;
    public COMPILE_TYPE compileType;
    private boolean enableInterLeave;
    /* access modifiers changed from: private */
    public boolean enableRemuxVideo;
    /* access modifiers changed from: private */
    public int encodeProfile;
    /* access modifiers changed from: private */
    public int encodeStandard;
    /* access modifiers changed from: private */
    public String externalSettingsJsonStr;
    /* access modifiers changed from: private */
    public int fps;
    /* access modifiers changed from: private */
    public int gopSize;
    /* access modifiers changed from: private */
    public boolean hasBFrame;
    /* access modifiers changed from: private */
    public VEVideoCompileEncodeSettings mVideoCompileEncodeSetting;
    /* access modifiers changed from: private */
    public VEVideoCompileEncodeSettings mVideoWatermarkCompileEncodeSetting;
    /* access modifiers changed from: private */
    public VEWatermarkParam mWatermarkParam;
    /* access modifiers changed from: private */
    public VESize outputSize;
    /* access modifiers changed from: private */
    public int resizeMode;
    /* access modifiers changed from: private */
    public float resizeX;
    /* access modifiers changed from: private */
    public float resizeY;
    /* access modifiers changed from: private */
    public int rotate;
    /* access modifiers changed from: private */
    public float speed;
    /* access modifiers changed from: private */
    public int swCRF;
    /* access modifiers changed from: private */
    public long swMaxrate;
    /* access modifiers changed from: private */
    public int swPreset;
    /* access modifiers changed from: private */
    public int swQP;
    /* access modifiers changed from: private */
    public boolean useHWEncoder;

    public static class Builder {
        private VEVideoEncodeSettings exportVideoEncodeSettings;
        private int mUsage;

        public Builder(@IntRange(from = 1, to = 3) int i) {
            this.mUsage = i;
            this.exportVideoEncodeSettings = new VEVideoEncodeSettings();
        }

        public Builder(@IntRange(from = 1, to = 3) int i, @NonNull VEVideoEncodeSettings vEVideoEncodeSettings) {
            this.mUsage = i;
            this.exportVideoEncodeSettings = vEVideoEncodeSettings;
        }

        private ENCODE_BITRATE_MODE getCompileHardwareBitrateModeFromCloud() {
            return ENCODE_BITRATE_MODE.ENCODE_BITRATE_ABR;
        }

        private ENCODE_BITRATE_MODE getImportHardwareBitrateModeFromCloud() {
            return ENCODE_BITRATE_MODE.ENCODE_BITRATE_ABR;
        }

        private ENCODE_BITRATE_MODE getRecordHardwareBitrateModeFromCloud() {
            return ENCODE_BITRATE_MODE.ENCODE_BITRATE_ABR;
        }

        private void overrideWithCloudConfigForCompile() {
            VECloudConfig vECloudConfig = PerformanceConfig.sVECloudConfig;
            boolean z = true;
            if (vECloudConfig.mCompileEncodeMode != 1) {
                z = false;
            }
            boolean unused = this.exportVideoEncodeSettings.useHWEncoder = z;
            int unused2 = this.exportVideoEncodeSettings.swCRF = vECloudConfig.mCompileEncodeSWCRF;
            int unused3 = this.exportVideoEncodeSettings.swPreset = vECloudConfig.mCompileEncodeSWCRFPreset;
            ENCODE_BITRATE_MODE unused4 = this.exportVideoEncodeSettings.bitrateMode = z ? getCompileHardwareBitrateModeFromCloud() : ENCODE_BITRATE_MODE.fromInteger(vECloudConfig.mCompileSWBitrateMode);
            int unused5 = this.exportVideoEncodeSettings.bps = z ? vECloudConfig.mCompileEncodeHWBPS : this.exportVideoEncodeSettings.bps;
            int unused6 = this.exportVideoEncodeSettings.gopSize = z ? this.exportVideoEncodeSettings.gopSize : vECloudConfig.mCompileEncodeSWGOP;
            int unused7 = this.exportVideoEncodeSettings.encodeProfile = z ? vECloudConfig.mCompileHwProfile : this.exportVideoEncodeSettings.encodeProfile;
            long unused8 = this.exportVideoEncodeSettings.swMaxrate = z ? this.exportVideoEncodeSettings.swMaxrate : (long) vECloudConfig.mCompileEncodeSWMaxrate;
            int unused9 = this.exportVideoEncodeSettings.swQP = z ? this.exportVideoEncodeSettings.swQP : vECloudConfig.mCompileVideoSWQP;
        }

        private void overrideWithCloudConfigForImport() {
            VECloudConfig vECloudConfig = PerformanceConfig.sVECloudConfig;
            boolean z = true;
            if (vECloudConfig.mImportEncodeMode != 1) {
                z = false;
            }
            boolean unused = this.exportVideoEncodeSettings.useHWEncoder = z;
            int unused2 = this.exportVideoEncodeSettings.swCRF = vECloudConfig.mImportSWEncodeCRF;
            int unused3 = this.exportVideoEncodeSettings.swPreset = vECloudConfig.mImportVideoSWPreset;
            ENCODE_BITRATE_MODE unused4 = this.exportVideoEncodeSettings.bitrateMode = z ? getImportHardwareBitrateModeFromCloud() : ENCODE_BITRATE_MODE.fromInteger(vECloudConfig.mImportSWBitrateMode);
            int unused5 = this.exportVideoEncodeSettings.bps = z ? vECloudConfig.mImportHWEncodeBPS : this.exportVideoEncodeSettings.bps;
            int unused6 = this.exportVideoEncodeSettings.gopSize = z ? this.exportVideoEncodeSettings.gopSize : vECloudConfig.mImportVideoSWGop;
            int unused7 = this.exportVideoEncodeSettings.encodeProfile = z ? vECloudConfig.mImportHwProfile : this.exportVideoEncodeSettings.encodeProfile;
            long unused8 = this.exportVideoEncodeSettings.swMaxrate = z ? this.exportVideoEncodeSettings.swMaxrate : (long) vECloudConfig.mImportVideoSWMaxrate;
            int unused9 = this.exportVideoEncodeSettings.swQP = z ? this.exportVideoEncodeSettings.swQP : vECloudConfig.mImportVideoSWQP;
        }

        private void overrideWithCloudConfigForRecord() {
            VECloudConfig vECloudConfig = PerformanceConfig.sVECloudConfig;
            boolean z = true;
            if (vECloudConfig.mRecordEncodeMode != 1) {
                z = false;
            }
            boolean unused = this.exportVideoEncodeSettings.useHWEncoder = z;
            VESize unused2 = this.exportVideoEncodeSettings.outputSize = new VESize(vECloudConfig.mRecordResolutionWidth, vECloudConfig.mRecordResolutionHeight);
            int unused3 = this.exportVideoEncodeSettings.swCRF = vECloudConfig.mRecordSWEncodeCRF;
            int unused4 = this.exportVideoEncodeSettings.swPreset = vECloudConfig.mRecordVideoSWPreset;
            ENCODE_BITRATE_MODE unused5 = this.exportVideoEncodeSettings.bitrateMode = z ? getRecordHardwareBitrateModeFromCloud() : ENCODE_BITRATE_MODE.fromInteger(vECloudConfig.mRecordSWBitrateMode);
            int unused6 = this.exportVideoEncodeSettings.bps = z ? vECloudConfig.mRecordHWEncodeBPS : this.exportVideoEncodeSettings.bps;
            int unused7 = this.exportVideoEncodeSettings.gopSize = z ? this.exportVideoEncodeSettings.gopSize : vECloudConfig.mRecordVideoSWGop;
            int unused8 = this.exportVideoEncodeSettings.encodeProfile = z ? vECloudConfig.mRecordHwProfile : this.exportVideoEncodeSettings.encodeProfile;
            long unused9 = this.exportVideoEncodeSettings.swMaxrate = z ? this.exportVideoEncodeSettings.swMaxrate : (long) vECloudConfig.mRecordVideoSWMaxrate;
            int unused10 = this.exportVideoEncodeSettings.swQP = z ? this.exportVideoEncodeSettings.swQP : vECloudConfig.mRecordVideoSWQP;
        }

        private void overrideWithUserConfig() {
            VEVideoCompileEncodeSettings vEVideoCompileEncodeSettings = new VEVideoCompileEncodeSettings();
            vEVideoCompileEncodeSettings.useHWEncoder = this.exportVideoEncodeSettings.useHWEncoder;
            if (this.exportVideoEncodeSettings.useHWEncoder) {
                vEVideoCompileEncodeSettings.mHWEncodeSetting.mBitrate = (long) this.exportVideoEncodeSettings.bps;
                vEVideoCompileEncodeSettings.mHWEncodeSetting.mProfile = this.exportVideoEncodeSettings.encodeProfile;
                vEVideoCompileEncodeSettings.mHWEncodeSetting.mGop = this.exportVideoEncodeSettings.gopSize;
            } else {
                vEVideoCompileEncodeSettings.mSWEncodeSetting.mBitrateMode = this.exportVideoEncodeSettings.bitrateMode.ordinal();
                vEVideoCompileEncodeSettings.mSWEncodeSetting.mBps = this.exportVideoEncodeSettings.bps;
                vEVideoCompileEncodeSettings.mSWEncodeSetting.mCrf = this.exportVideoEncodeSettings.swCRF;
                vEVideoCompileEncodeSettings.mSWEncodeSetting.mMaxRate = this.exportVideoEncodeSettings.swMaxrate;
                vEVideoCompileEncodeSettings.mSWEncodeSetting.mPreset = this.exportVideoEncodeSettings.swPreset;
                vEVideoCompileEncodeSettings.mSWEncodeSetting.mProfile = this.exportVideoEncodeSettings.encodeProfile;
                vEVideoCompileEncodeSettings.mSWEncodeSetting.mGop = this.exportVideoEncodeSettings.gopSize;
            }
            VEVideoCompileEncodeSettings unused = this.exportVideoEncodeSettings.mVideoCompileEncodeSetting = vEVideoCompileEncodeSettings;
            VEVideoCompileEncodeSettings unused2 = this.exportVideoEncodeSettings.mVideoWatermarkCompileEncodeSetting = vEVideoCompileEncodeSettings;
        }

        private void parseExternalSettingsJsonStr(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                VEVideoCompileEncodeSettings unused = this.exportVideoEncodeSettings.mVideoCompileEncodeSetting = parseJsonToSetting(jSONObject.getJSONObject("compile"));
                VEVideoCompileEncodeSettings unused2 = this.exportVideoEncodeSettings.mVideoWatermarkCompileEncodeSetting = parseJsonToSetting(jSONObject.getJSONObject("watermark_compile"));
            } catch (JSONException e) {
                e.printStackTrace();
                VELogUtil.e(VEVideoEncodeSettings.TAG, "external json str parse error : " + e.getLocalizedMessage());
            }
        }

        private VEVideoCompileEncodeSettings parseJsonToSetting(JSONObject jSONObject) {
            VEVideoCompileEncodeSettings vEVideoCompileEncodeSettings = new VEVideoCompileEncodeSettings();
            try {
                String string = jSONObject.getString("encode_mode");
                if (!EnvironmentCompat.MEDIA_UNKNOWN.equals(string) || this.mUsage != 2) {
                    vEVideoCompileEncodeSettings.useHWEncoder = "hw".equals(string);
                } else {
                    vEVideoCompileEncodeSettings.useHWEncoder = this.exportVideoEncodeSettings.useHWEncoder;
                }
                vEVideoCompileEncodeSettings.mHWEncodeSetting.mBitrate = (this.mUsage == 2 && jSONObject.getJSONObject("hw").getInt("bitrate") == -1) ? this.exportVideoEncodeSettings.useHWEncoder ? (long) this.exportVideoEncodeSettings.bps : vEVideoCompileEncodeSettings.mHWEncodeSetting.mBitrate : (long) jSONObject.getJSONObject("hw").getInt("bitrate");
                vEVideoCompileEncodeSettings.mHWEncodeSetting.mProfile = (this.mUsage != 2 || !EnvironmentCompat.MEDIA_UNKNOWN.equals(jSONObject.getJSONObject("hw").getString("profile"))) ? VEVideoEncodeProfile.valueOfString(jSONObject.getJSONObject("hw").getString("profile")).ordinal() : this.exportVideoEncodeSettings.useHWEncoder ? this.exportVideoEncodeSettings.encodeProfile : vEVideoCompileEncodeSettings.mHWEncodeSetting.mProfile;
                vEVideoCompileEncodeSettings.mHWEncodeSetting.mGop = (this.mUsage == 2 && jSONObject.getJSONObject("hw").getInt("gop") == -1) ? this.exportVideoEncodeSettings.useHWEncoder ? this.exportVideoEncodeSettings.gopSize : vEVideoCompileEncodeSettings.mHWEncodeSetting.mGop : jSONObject.getJSONObject("hw").getInt("gop");
                vEVideoCompileEncodeSettings.mSWEncodeSetting.mBitrateMode = ENCODE_BITRATE_MODE.ENCODE_BITRATE_CRF.ordinal();
                vEVideoCompileEncodeSettings.mSWEncodeSetting.mCrf = (this.mUsage == 2 && jSONObject.getJSONObject("sw").getInt("crf") == -1) ? this.exportVideoEncodeSettings.useHWEncoder ? vEVideoCompileEncodeSettings.mSWEncodeSetting.mCrf : this.exportVideoEncodeSettings.swCRF : jSONObject.getJSONObject("sw").getInt("crf");
                vEVideoCompileEncodeSettings.mSWEncodeSetting.mMaxRate = (this.mUsage == 2 && jSONObject.getJSONObject("sw").getInt("maxrate") == -1) ? this.exportVideoEncodeSettings.useHWEncoder ? vEVideoCompileEncodeSettings.mSWEncodeSetting.mMaxRate : this.exportVideoEncodeSettings.swMaxrate : (long) jSONObject.getJSONObject("sw").getInt("maxrate");
                vEVideoCompileEncodeSettings.mSWEncodeSetting.mPreset = (this.mUsage == 2 && jSONObject.getJSONObject("sw").getInt("preset") == -1) ? this.exportVideoEncodeSettings.useHWEncoder ? vEVideoCompileEncodeSettings.mSWEncodeSetting.mPreset : this.exportVideoEncodeSettings.swPreset : jSONObject.getJSONObject("sw").getInt("preset");
                vEVideoCompileEncodeSettings.mSWEncodeSetting.mProfile = (this.mUsage != 2 || !EnvironmentCompat.MEDIA_UNKNOWN.equals(jSONObject.getJSONObject("sw").getString("profile"))) ? VEVideoEncodeProfile.valueOfString(jSONObject.getJSONObject("sw").getString("profile")).ordinal() : this.exportVideoEncodeSettings.useHWEncoder ? vEVideoCompileEncodeSettings.mSWEncodeSetting.mProfile : this.exportVideoEncodeSettings.encodeProfile;
                vEVideoCompileEncodeSettings.mSWEncodeSetting.mGop = (this.mUsage == 2 && jSONObject.getJSONObject("sw").getInt("gop") == -1) ? this.exportVideoEncodeSettings.useHWEncoder ? vEVideoCompileEncodeSettings.mSWEncodeSetting.mGop : this.exportVideoEncodeSettings.gopSize : jSONObject.getJSONObject("sw").getInt("gop");
            } catch (JSONException e) {
                e.printStackTrace();
                VELogUtil.e(VEVideoEncodeSettings.TAG, "parseJsonToSetting : external json str parse error : " + e.getLocalizedMessage());
            }
            return vEVideoCompileEncodeSettings;
        }

        public VEVideoEncodeSettings build() {
            if (!TextUtils.isEmpty(this.exportVideoEncodeSettings.externalSettingsJsonStr)) {
                parseExternalSettingsJsonStr(this.exportVideoEncodeSettings.externalSettingsJsonStr);
            } else {
                overrideWithUserConfig();
            }
            return this.exportVideoEncodeSettings;
        }

        public Builder overrideWithCloudConfig() {
            if (!VERuntime.getInstance().isUseCloudConfig()) {
                Log.e(VEVideoEncodeSettings.TAG, "UseCloudConfig is disabled. VEVideoEncodeSettings.overrideWithCloudConfig will do nothing!");
                return this;
            } else if (PerformanceConfig.sVECloudConfig == null) {
                Log.e(getClass().getSimpleName(), "Override with Cloud Configs failed. CloudConfig == null");
                return this;
            } else {
                switch (this.mUsage) {
                    case 1:
                        overrideWithCloudConfigForRecord();
                        break;
                    case 2:
                        overrideWithCloudConfigForCompile();
                        break;
                    case 3:
                        overrideWithCloudConfigForImport();
                        break;
                    default:
                        throw new IllegalStateException("CompileTime BUG, Unexpected usage = " + this.mUsage);
                }
                return this;
            }
        }

        public Builder setBps(int i) {
            ENCODE_BITRATE_MODE unused = this.exportVideoEncodeSettings.bitrateMode = ENCODE_BITRATE_MODE.ENCODE_BITRATE_ABR;
            int unused2 = this.exportVideoEncodeSettings.bps = i;
            return this;
        }

        public Builder setCompileType(@NonNull COMPILE_TYPE compile_type) {
            this.exportVideoEncodeSettings.compileType = compile_type;
            return this;
        }

        public Builder setEnableRemuxVideo(boolean z) {
            boolean unused = this.exportVideoEncodeSettings.enableRemuxVideo = z;
            return this;
        }

        public Builder setEncodePreset(@NonNull ENCODE_PRESET encode_preset) {
            int unused = this.exportVideoEncodeSettings.swPreset = encode_preset.ordinal();
            return this;
        }

        public Builder setEncodeProfile(@NonNull ENCODE_PROFILE encode_profile) {
            int unused = this.exportVideoEncodeSettings.encodeProfile = encode_profile.ordinal();
            return this;
        }

        public Builder setEncodeStandard(ENCODE_STANDARD encode_standard) {
            int unused = this.exportVideoEncodeSettings.encodeStandard = encode_standard.ordinal();
            return this;
        }

        public Builder setExternalSettings(String str) {
            String unused = this.exportVideoEncodeSettings.externalSettingsJsonStr = str;
            return this;
        }

        public Builder setFps(int i) {
            int unused = this.exportVideoEncodeSettings.fps = i;
            return this;
        }

        public Builder setGopSize(int i) {
            int unused = this.exportVideoEncodeSettings.gopSize = i;
            return this;
        }

        public Builder setHasBFrame(boolean z) {
            boolean unused = this.exportVideoEncodeSettings.hasBFrame = z;
            return this;
        }

        public Builder setHwEnc(boolean z) {
            boolean unused = this.exportVideoEncodeSettings.useHWEncoder = z;
            return this;
        }

        public Builder setQP(int i) {
            ENCODE_BITRATE_MODE unused = this.exportVideoEncodeSettings.bitrateMode = ENCODE_BITRATE_MODE.ENCODE_BITRATE_QP;
            int unused2 = this.exportVideoEncodeSettings.swQP = i;
            return this;
        }

        public Builder setResizeMode(int i) {
            int unused = this.exportVideoEncodeSettings.resizeMode = i;
            return this;
        }

        public Builder setResizeX(float f) {
            float unused = this.exportVideoEncodeSettings.resizeX = f;
            return this;
        }

        public Builder setResizeY(float f) {
            float unused = this.exportVideoEncodeSettings.resizeY = f;
            return this;
        }

        public Builder setRotate(int i) {
            int unused = this.exportVideoEncodeSettings.rotate = i;
            return this;
        }

        public Builder setSWCRF(int i) {
            ENCODE_BITRATE_MODE unused = this.exportVideoEncodeSettings.bitrateMode = ENCODE_BITRATE_MODE.ENCODE_BITRATE_CRF;
            int unused2 = this.exportVideoEncodeSettings.swCRF = i;
            return this;
        }

        public Builder setSpeed(float f) {
            float unused = this.exportVideoEncodeSettings.speed = f;
            return this;
        }

        public Builder setSwMaxrate(long j) {
            long unused = this.exportVideoEncodeSettings.swMaxrate = j;
            return this;
        }

        public Builder setVideoBitrate(ENCODE_BITRATE_MODE encode_bitrate_mode, int i) {
            ENCODE_BITRATE_MODE unused = this.exportVideoEncodeSettings.bitrateMode = encode_bitrate_mode;
            switch (encode_bitrate_mode) {
                case ENCODE_BITRATE_ABR:
                    int unused2 = this.exportVideoEncodeSettings.bps = i;
                    break;
                case ENCODE_BITRATE_CRF:
                    int unused3 = this.exportVideoEncodeSettings.swCRF = i;
                    break;
                case ENCODE_BITRATE_QP:
                    int unused4 = this.exportVideoEncodeSettings.swQP = i;
                    break;
                case ENCODE_BITRATE_VBR:
                    int unused5 = this.exportVideoEncodeSettings.bps = i;
                    break;
                default:
                    throw new IllegalStateException("CompileTime BUG. Unhandled enum value " + encode_bitrate_mode.toString());
            }
            return this;
        }

        public Builder setVideoBitrateMode(ENCODE_BITRATE_MODE encode_bitrate_mode) {
            ENCODE_BITRATE_MODE unused = this.exportVideoEncodeSettings.bitrateMode = encode_bitrate_mode;
            return this;
        }

        public Builder setVideoRes(int i, int i2) {
            this.exportVideoEncodeSettings.outputSize.width = i;
            this.exportVideoEncodeSettings.outputSize.height = i2;
            return this;
        }

        public Builder setWatermarkParam(VEWatermarkParam vEWatermarkParam) {
            VEWatermarkParam unused = this.exportVideoEncodeSettings.mWatermarkParam = vEWatermarkParam;
            return this;
        }
    }

    public enum COMPILE_TYPE implements Parcelable {
        COMPILE_TYPE_MP4,
        COMPILE_TYPE_GIF,
        COMPILE_TYPE_HIGH_GIF;
        
        public static final Parcelable.Creator<COMPILE_TYPE> CREATOR = null;

        static {
            CREATOR = new Parcelable.Creator<COMPILE_TYPE>() {
                public COMPILE_TYPE createFromParcel(Parcel parcel) {
                    return COMPILE_TYPE.values()[parcel.readInt()];
                }

                public COMPILE_TYPE[] newArray(int i) {
                    return new COMPILE_TYPE[i];
                }
            };
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(ordinal());
        }
    }

    public enum ENCODE_BITRATE_MODE implements Parcelable {
        ENCODE_BITRATE_ABR,
        ENCODE_BITRATE_CRF,
        ENCODE_BITRATE_QP,
        ENCODE_BITRATE_VBR;
        
        public static final Parcelable.Creator<ENCODE_BITRATE_MODE> CREATOR = null;
        private static final ENCODE_BITRATE_MODE[] values = null;

        static {
            values = values();
            CREATOR = new Parcelable.Creator<ENCODE_BITRATE_MODE>() {
                public ENCODE_BITRATE_MODE createFromParcel(Parcel parcel) {
                    return ENCODE_BITRATE_MODE.values()[parcel.readInt()];
                }

                public ENCODE_BITRATE_MODE[] newArray(int i) {
                    return new ENCODE_BITRATE_MODE[i];
                }
            };
        }

        public static ENCODE_BITRATE_MODE fromInteger(int i) {
            return values[i];
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(ordinal());
        }
    }

    public enum ENCODE_PRESET implements Parcelable {
        ENCODE_LEVEL_ULTRAFAST,
        ENCODE_LEVEL_SUPERFAST,
        ENCODE_LEVEL_VERYFAST,
        ENCODE_LEVEL_FASTER,
        ENCODE_LEVEL_FAST,
        ENCODE_LEVEL_MEDIUM,
        ENCODE_LEVEL_SLOW,
        ENCODE_LEVEL_SLOWER,
        ENCODE_LEVEL_VERYSLOW,
        ENCODE_LEVEL_PLACEBO;
        
        public static final Parcelable.Creator<ENCODE_PRESET> CREATOR = null;

        static {
            CREATOR = new Parcelable.Creator<ENCODE_PRESET>() {
                public ENCODE_PRESET createFromParcel(Parcel parcel) {
                    return ENCODE_PRESET.values()[parcel.readInt()];
                }

                public ENCODE_PRESET[] newArray(int i) {
                    return new ENCODE_PRESET[i];
                }
            };
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(ordinal());
        }
    }

    public enum ENCODE_PROFILE implements Parcelable {
        ENCODE_PROFILE_UNKNOWN,
        ENCODE_PROFILE_BASELINE,
        ENCODE_PROFILE_MAIN,
        ENCODE_PROFILE_HIGH;
        
        public static final Parcelable.Creator<ENCODE_PROFILE> CREATOR = null;

        static {
            CREATOR = new Parcelable.Creator<ENCODE_PROFILE>() {
                public ENCODE_PROFILE createFromParcel(Parcel parcel) {
                    return ENCODE_PROFILE.values()[parcel.readInt()];
                }

                public ENCODE_PROFILE[] newArray(int i) {
                    return new ENCODE_PROFILE[i];
                }
            };
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(ordinal());
        }
    }

    public enum ENCODE_STANDARD implements Parcelable {
        ENCODE_STANDARD_H264,
        ENCODE_STANDARD_H265,
        ENCODE_STANDARD_MPEG4;
        
        public static final Parcelable.Creator<ENCODE_STANDARD> CREATOR = null;

        static {
            CREATOR = new Parcelable.Creator<ENCODE_STANDARD>() {
                public ENCODE_STANDARD createFromParcel(Parcel parcel) {
                    return ENCODE_STANDARD.values()[parcel.readInt()];
                }

                public ENCODE_STANDARD[] newArray(int i) {
                    return new ENCODE_STANDARD[i];
                }
            };
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(ordinal());
        }
    }

    private VEVideoEncodeSettings() {
        this.rotate = 0;
        this.resizeMode = 2;
        this.resizeX = 0.0f;
        this.resizeY = 0.0f;
        this.speed = 1.0f;
        this.outputSize = new VESize(576, 1024);
        this.externalSettingsJsonStr = null;
        this.bitrateMode = ENCODE_BITRATE_MODE.ENCODE_BITRATE_ABR;
        this.bps = 4194304;
        this.swCRF = 15;
        this.swQP = 15;
        this.fps = -1;
        this.gopSize = 60;
        this.swPreset = ENCODE_PRESET.ENCODE_LEVEL_ULTRAFAST.ordinal();
        this.encodeStandard = ENCODE_STANDARD.ENCODE_STANDARD_H264.ordinal();
        this.encodeProfile = ENCODE_PROFILE.ENCODE_PROFILE_UNKNOWN.ordinal();
        this.swMaxrate = 15000000;
        this.hasBFrame = false;
        this.outputSize.width = 576;
        this.outputSize.height = 1024;
        this.fps = -1;
        this.bps = 4194304;
        this.useHWEncoder = true;
        this.compileType = COMPILE_TYPE.COMPILE_TYPE_MP4;
    }

    protected VEVideoEncodeSettings(Parcel parcel) {
        boolean z = false;
        this.rotate = 0;
        this.resizeMode = 2;
        this.resizeX = 0.0f;
        this.resizeY = 0.0f;
        this.speed = 1.0f;
        this.outputSize = new VESize(576, 1024);
        this.externalSettingsJsonStr = null;
        this.bitrateMode = ENCODE_BITRATE_MODE.ENCODE_BITRATE_ABR;
        this.bps = 4194304;
        this.swCRF = 15;
        this.swQP = 15;
        this.fps = -1;
        this.gopSize = 60;
        this.swPreset = ENCODE_PRESET.ENCODE_LEVEL_ULTRAFAST.ordinal();
        this.encodeStandard = ENCODE_STANDARD.ENCODE_STANDARD_H264.ordinal();
        this.encodeProfile = ENCODE_PROFILE.ENCODE_PROFILE_UNKNOWN.ordinal();
        this.swMaxrate = 15000000;
        this.hasBFrame = false;
        this.compileType = (COMPILE_TYPE) parcel.readParcelable(COMPILE_TYPE.class.getClassLoader());
        this.rotate = parcel.readInt();
        this.resizeMode = parcel.readInt();
        this.resizeX = parcel.readFloat();
        this.resizeY = parcel.readFloat();
        this.speed = parcel.readFloat();
        this.outputSize = (VESize) parcel.readParcelable(VESize.class.getClassLoader());
        this.bitrateMode = (ENCODE_BITRATE_MODE) parcel.readParcelable(ENCODE_BITRATE_MODE.class.getClassLoader());
        this.bps = parcel.readInt();
        this.fps = parcel.readInt();
        this.swCRF = parcel.readInt();
        this.swQP = parcel.readInt();
        this.gopSize = parcel.readInt();
        this.swPreset = parcel.readInt();
        this.encodeStandard = parcel.readInt();
        this.encodeProfile = parcel.readInt();
        this.useHWEncoder = parcel.readByte() != 0;
        this.enableRemuxVideo = parcel.readByte() != 0;
        this.enableInterLeave = parcel.readByte() != 0;
        this.hasBFrame = parcel.readByte() != 0 ? true : z;
        this.swMaxrate = parcel.readLong();
        this.mWatermarkParam = (VEWatermarkParam) parcel.readParcelable(VEWatermarkParam.class.getClassLoader());
        this.mVideoWatermarkCompileEncodeSetting = (VEVideoCompileEncodeSettings) parcel.readParcelable(VEVideoCompileEncodeSettings.class.getClassLoader());
        this.mVideoCompileEncodeSetting = (VEVideoCompileEncodeSettings) parcel.readParcelable(VEVideoCompileEncodeSettings.class.getClassLoader());
        this.externalSettingsJsonStr = parcel.readString();
    }

    @Deprecated
    public VEVideoEncodeSettings(VESize vESize, boolean z) {
        this.rotate = 0;
        this.resizeMode = 2;
        this.resizeX = 0.0f;
        this.resizeY = 0.0f;
        this.speed = 1.0f;
        this.outputSize = new VESize(576, 1024);
        this.externalSettingsJsonStr = null;
        this.bitrateMode = ENCODE_BITRATE_MODE.ENCODE_BITRATE_ABR;
        this.bps = 4194304;
        this.swCRF = 15;
        this.swQP = 15;
        this.fps = -1;
        this.gopSize = 60;
        this.swPreset = ENCODE_PRESET.ENCODE_LEVEL_ULTRAFAST.ordinal();
        this.encodeStandard = ENCODE_STANDARD.ENCODE_STANDARD_H264.ordinal();
        this.encodeProfile = ENCODE_PROFILE.ENCODE_PROFILE_UNKNOWN.ordinal();
        this.swMaxrate = 15000000;
        this.hasBFrame = false;
        this.outputSize = vESize;
        this.useHWEncoder = z;
        this.compileType = COMPILE_TYPE.COMPILE_TYPE_MP4;
    }

    @Deprecated
    public VEVideoEncodeSettings(VESize vESize, boolean z, int i, int i2) {
        this.rotate = 0;
        this.resizeMode = 2;
        this.resizeX = 0.0f;
        this.resizeY = 0.0f;
        this.speed = 1.0f;
        this.outputSize = new VESize(576, 1024);
        this.externalSettingsJsonStr = null;
        this.bitrateMode = ENCODE_BITRATE_MODE.ENCODE_BITRATE_ABR;
        this.bps = 4194304;
        this.swCRF = 15;
        this.swQP = 15;
        this.fps = -1;
        this.gopSize = 60;
        this.swPreset = ENCODE_PRESET.ENCODE_LEVEL_ULTRAFAST.ordinal();
        this.encodeStandard = ENCODE_STANDARD.ENCODE_STANDARD_H264.ordinal();
        this.encodeProfile = ENCODE_PROFILE.ENCODE_PROFILE_UNKNOWN.ordinal();
        this.swMaxrate = 15000000;
        this.hasBFrame = false;
        this.outputSize = vESize;
        this.useHWEncoder = z;
        this.fps = i;
        this.gopSize = i2;
        this.compileType = COMPILE_TYPE.COMPILE_TYPE_MP4;
    }

    @Deprecated
    public VEVideoEncodeSettings(VESize vESize, boolean z, int i, int i2, int i3, int i4, boolean z2) {
        this.rotate = 0;
        this.resizeMode = 2;
        this.resizeX = 0.0f;
        this.resizeY = 0.0f;
        this.speed = 1.0f;
        this.outputSize = new VESize(576, 1024);
        this.externalSettingsJsonStr = null;
        this.bitrateMode = ENCODE_BITRATE_MODE.ENCODE_BITRATE_ABR;
        this.bps = 4194304;
        this.swCRF = 15;
        this.swQP = 15;
        this.fps = -1;
        this.gopSize = 60;
        this.swPreset = ENCODE_PRESET.ENCODE_LEVEL_ULTRAFAST.ordinal();
        this.encodeStandard = ENCODE_STANDARD.ENCODE_STANDARD_H264.ordinal();
        this.encodeProfile = ENCODE_PROFILE.ENCODE_PROFILE_UNKNOWN.ordinal();
        this.swMaxrate = 15000000;
        this.hasBFrame = false;
        this.outputSize = vESize;
        this.useHWEncoder = z;
        this.fps = i;
        this.gopSize = i2;
        this.bps = i3;
        this.swPreset = i4;
        this.hasBFrame = z2;
        this.compileType = COMPILE_TYPE.COMPILE_TYPE_MP4;
    }

    public int describeContents() {
        return 0;
    }

    public ENCODE_BITRATE_MODE getBitrateMode() {
        return this.bitrateMode;
    }

    public int getBitrateValue() {
        switch (this.bitrateMode) {
            case ENCODE_BITRATE_ABR:
                return getBps();
            case ENCODE_BITRATE_CRF:
                return getSwCRF();
            case ENCODE_BITRATE_QP:
                return getSwQP();
            case ENCODE_BITRATE_VBR:
                return getBps();
            default:
                throw new IllegalStateException("CompileTime BUG by SDK. Unhandled ENCODE_BITRATE_MODE enum value.");
        }
    }

    public int getBps() {
        return this.bps;
    }

    public COMPILE_TYPE getCompileType() {
        return this.compileType;
    }

    public int getEncodeProfile() {
        return this.encodeProfile;
    }

    public int getFps() {
        return this.fps;
    }

    public int getGopSize() {
        return this.gopSize;
    }

    public int getResizeMode() {
        return this.resizeMode;
    }

    public float getResizeX() {
        return this.resizeX;
    }

    public float getResizeY() {
        return this.resizeY;
    }

    public int getRotate() {
        return this.rotate;
    }

    public float getSpeed() {
        return this.speed;
    }

    public int getSwCRF() {
        return this.swCRF;
    }

    public long getSwMaxRate() {
        return this.swMaxrate;
    }

    public int getSwPreset() {
        return this.swPreset;
    }

    public int getSwQP() {
        return this.swQP;
    }

    public VEVideoCompileEncodeSettings getVideoCompileEncodeSetting() {
        return this.mVideoCompileEncodeSetting;
    }

    public VESize getVideoRes() {
        return this.outputSize;
    }

    public VEVideoCompileEncodeSettings getWatermarkCompileEncodeSetting() {
        return this.mVideoWatermarkCompileEncodeSetting;
    }

    public VEWatermarkParam getWatermarkParam() {
        return this.mWatermarkParam;
    }

    public boolean isEnableInterLeave() {
        return this.enableInterLeave;
    }

    public boolean isEnableRemuxVideo() {
        return this.enableRemuxVideo;
    }

    public boolean isHwEnc() {
        return this.useHWEncoder;
    }

    @Deprecated
    public void setBps(int i) {
        this.bitrateMode = ENCODE_BITRATE_MODE.ENCODE_BITRATE_VBR;
        this.bps = i;
    }

    public void setCompileType(COMPILE_TYPE compile_type) {
        this.compileType = compile_type;
    }

    public void setEnableInterLeave(boolean z) {
        this.enableInterLeave = z;
    }

    public void setEnableRemuxVideo(boolean z) {
        this.enableRemuxVideo = z;
    }

    public void setEncodeProfile(ENCODE_PROFILE encode_profile) {
        this.encodeProfile = encode_profile.ordinal();
    }

    public void setFps(int i) {
        this.fps = i;
    }

    public void setGopSize(int i) {
        this.gopSize = i;
    }

    public void setHwEnc(boolean z) {
        this.useHWEncoder = z;
    }

    public void setQP(int i) {
        this.bitrateMode = ENCODE_BITRATE_MODE.ENCODE_BITRATE_QP;
        this.swQP = i;
    }

    public void setResizeMode(int i) {
        this.resizeMode = i;
    }

    public void setResizeX(float f) {
        this.resizeX = f;
    }

    public void setResizeY(float f) {
        this.resizeY = f;
    }

    public void setRotate(int i) {
        this.rotate = i;
    }

    public void setSWCRF(int i) {
        this.bitrateMode = ENCODE_BITRATE_MODE.ENCODE_BITRATE_CRF;
        this.swCRF = i;
    }

    public void setSpeed(float f) {
        this.speed = f;
    }

    public void setSwPreset(ENCODE_PRESET encode_preset) {
        this.swPreset = encode_preset.ordinal();
    }

    public void setVideoBitrate(ENCODE_BITRATE_MODE encode_bitrate_mode, int i) {
        this.bitrateMode = encode_bitrate_mode;
        switch (this.bitrateMode) {
            case ENCODE_BITRATE_ABR:
                this.bps = i;
                return;
            case ENCODE_BITRATE_CRF:
                this.swCRF = i;
                return;
            case ENCODE_BITRATE_QP:
                this.swQP = i;
                return;
            case ENCODE_BITRATE_VBR:
                this.bps = i;
                return;
            default:
                throw new IllegalStateException("CompileTime BUG by sdk. Unhandled bitrateMode");
        }
    }

    public void setVideoRes(int i, int i2) {
        this.outputSize.width = i;
        this.outputSize.height = i2;
    }

    public void setWatermark(VEWatermarkParam vEWatermarkParam) {
        this.mWatermarkParam = vEWatermarkParam;
    }

    public String toString() {
        return "VEVideoEncodeSettings{compileType=" + this.compileType + ", rotate=" + this.rotate + ", resizeMode=" + this.resizeMode + ", resizeX=" + this.resizeX + ", resizeY=" + this.resizeY + ", speed=" + this.speed + ", outputSize=" + this.outputSize + ", bitrateMode=" + this.bitrateMode + ", bps=" + this.bps + ", swCRF=" + this.swCRF + ", swQP=" + this.swQP + ", fps=" + this.fps + ", gopSize=" + this.gopSize + ", swPreset=" + this.swPreset + ", encodeStandard=" + this.encodeStandard + ", encodeProfile=" + this.encodeProfile + ", swMaxrate=" + this.swMaxrate + ", useHWEncoder=" + this.useHWEncoder + ", enableRemuxVideo=" + this.enableRemuxVideo + ", enableInterLeave=" + this.enableInterLeave + ", hasBFrame=" + this.hasBFrame + ", mWatermarkParam=" + this.mWatermarkParam + ", mVideoWatermarkCompileEncodeSetting=" + this.mVideoWatermarkCompileEncodeSetting + ", mVideoCompileEncodeSetting=" + this.mVideoCompileEncodeSetting + '}';
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.compileType, i);
        parcel.writeInt(this.rotate);
        parcel.writeInt(this.resizeMode);
        parcel.writeFloat(this.resizeX);
        parcel.writeFloat(this.resizeY);
        parcel.writeFloat(this.speed);
        parcel.writeParcelable(this.outputSize, i);
        parcel.writeParcelable(this.bitrateMode, i);
        parcel.writeInt(this.bps);
        parcel.writeInt(this.fps);
        parcel.writeInt(this.swCRF);
        parcel.writeInt(this.swQP);
        parcel.writeInt(this.gopSize);
        parcel.writeInt(this.swPreset);
        parcel.writeInt(this.encodeStandard);
        parcel.writeInt(this.encodeProfile);
        parcel.writeByte(this.useHWEncoder ? (byte) 1 : 0);
        parcel.writeByte(this.enableRemuxVideo ? (byte) 1 : 0);
        parcel.writeByte(this.enableInterLeave ? (byte) 1 : 0);
        parcel.writeByte(this.hasBFrame ? (byte) 1 : 0);
        parcel.writeLong(this.swMaxrate);
        parcel.writeParcelable(this.mWatermarkParam, i);
        parcel.writeParcelable(this.mVideoWatermarkCompileEncodeSetting, i);
        parcel.writeParcelable(this.mVideoCompileEncodeSetting, i);
        parcel.writeString(this.externalSettingsJsonStr);
    }
}
