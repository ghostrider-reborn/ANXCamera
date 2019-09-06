package com.xiaomi.conferencemanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.android.camera.module.BaseModule;
import com.xiaomi.conferencemanager.ConferenceManager.DynamicViewPolicyT;
import com.xiaomi.conferencemanager.ConferenceManager.EngineErrorTypeT;
import com.xiaomi.conferencemanager.ConferenceManager.VideoContentTypeT;
import com.xiaomi.conferencemanager.Model.ConnectionData;
import com.xiaomi.conferencemanager.Model.MonitorData;
import com.xiaomi.conferencemanager.callback.ConferenceCallback;
import com.xiaomi.conferencemanager.callback.ConferenceCallback.ParticipantVolume;
import com.xiaomi.utils.Logger;

public class ConferenceEngine {
    private static final String TAG = "ConferenceEngine";
    private static ConferenceEngine instance;
    private long address;
    private ConferenceCallback cc;

    public static class InVideoStat {
        public int mBitrate;
        public String mCodecName;
        public int mDistinctNacksRequested;
        public int mDistinctNacksTransmitted;
        public int mFecSuccessRate;
        public int mFrameRate;
        public int mFramesDecoded;
        public int mFramesDisplay;
        public int mHeight;
        public int mNacksSent;
        public int mPacketsLost;
        public int mPacketsReceived;
        public byte mPayloadType;
        public int mRTT;
        public int mRecvBandwidth;
        public int mRecvBitRateApp;
        public int mRecvBitRateAudio;
        public int mRecvBitRateTotal;
        public int mRecvBitRateVideo;
        public int mWidth;

        public InVideoStat(String str, byte b2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18) {
            this.mCodecName = str;
            this.mPayloadType = b2;
            this.mBitrate = i;
            this.mPacketsReceived = i2;
            this.mPacketsLost = i3;
            this.mFrameRate = i4;
            this.mFramesDecoded = i5;
            this.mFramesDisplay = i6;
            this.mWidth = i7;
            this.mHeight = i8;
            this.mRTT = i9;
            this.mFecSuccessRate = i10;
            this.mNacksSent = i11;
            this.mDistinctNacksRequested = i12;
            this.mDistinctNacksTransmitted = i13;
            this.mRecvBitRateVideo = i14;
            this.mRecvBitRateAudio = i15;
            this.mRecvBitRateApp = i16;
            this.mRecvBitRateTotal = i17;
            this.mRecvBandwidth = i18;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\nVideo Recv{\nmBitrate = ");
            sb.append(this.mBitrate / BaseModule.LENS_DIRTY_DETECT_HINT_DURATION);
            sb.append("\nmPacketsReceived = ");
            sb.append(this.mPacketsReceived);
            sb.append("\nmPacketsLost = ");
            sb.append(this.mPacketsLost);
            sb.append("\nmFrameRate = ");
            sb.append(this.mFrameRate);
            sb.append("\nmFramesDecoded = ");
            sb.append(this.mFramesDecoded);
            sb.append("\nmFramesDisplay = ");
            sb.append(this.mFramesDisplay);
            sb.append("\nmWidth = ");
            sb.append(this.mWidth);
            sb.append(", mHeight = ");
            sb.append(this.mHeight);
            sb.append("\nmFecSuccessRate = ");
            sb.append(this.mFecSuccessRate);
            sb.append("\nmNacksSent = ");
            sb.append(this.mNacksSent);
            sb.append("\nNacksRequested = ");
            sb.append(this.mDistinctNacksRequested);
            sb.append("\nNacksTransmitted = ");
            sb.append(this.mDistinctNacksTransmitted);
            sb.append("\nmRecBW = ");
            sb.append(this.mRecvBandwidth);
            sb.append("\nmRecVideoBW = ");
            sb.append(this.mRecvBitRateVideo);
            sb.append("\nmRecAudioBW = ");
            sb.append(this.mRecvBitRateAudio);
            sb.append("\nmRecTotalBW = ");
            sb.append(this.mRecvBitRateTotal);
            sb.append("}\n");
            return sb.toString();
        }
    }

    public static class OutVideoStat {
        public int mBitrate;
        public int mCaptureFrameRate;
        public String mCodecName;
        public int mDistinctNacksRcvd;
        public int mDistinctPacketsRetransmitted;
        public int mEncodeFrameRate;
        public int mHeight;
        public int mIFramesSent;
        public int mNacksRcvd;
        public byte mPayloadType;
        public int mRTT;
        public int mSendBandwidth;
        public int mSendBitRateApp;
        public int mSendBitRateAudio;
        public int mSendBitRateTotal;
        public int mSendBitRateVideo;
        public int mWidth;

        public OutVideoStat(String str, byte b2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15) {
            this.mCodecName = str;
            this.mPayloadType = b2;
            this.mBitrate = i;
            this.mCaptureFrameRate = i2;
            this.mEncodeFrameRate = i3;
            this.mIFramesSent = i4;
            this.mWidth = i5;
            this.mHeight = i6;
            this.mRTT = i7;
            this.mNacksRcvd = i8;
            this.mDistinctNacksRcvd = i9;
            this.mDistinctPacketsRetransmitted = i10;
            this.mSendBitRateVideo = i11;
            this.mSendBitRateAudio = i12;
            this.mSendBitRateApp = i13;
            this.mSendBitRateTotal = i14;
            this.mSendBandwidth = i15;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\nVideo Send{\nmBitrate = ");
            sb.append(this.mBitrate / BaseModule.LENS_DIRTY_DETECT_HINT_DURATION);
            sb.append("\nmCaptureFrameRate = ");
            sb.append(this.mCaptureFrameRate);
            sb.append("\nmEncodeFrameRate = ");
            sb.append(this.mEncodeFrameRate);
            sb.append("\nmIFramesSent = ");
            sb.append(this.mIFramesSent);
            sb.append("\nmWidth = ");
            sb.append(this.mWidth);
            sb.append(", mHeight = ");
            sb.append(this.mHeight);
            sb.append("\nmRTT = ");
            sb.append(this.mRTT);
            sb.append(", mNacksRcvd = ");
            sb.append(this.mNacksRcvd);
            sb.append("\nmDistinctNacksRcvd = ");
            sb.append(this.mDistinctNacksRcvd);
            sb.append("\nPacketsRetransmitted = ");
            sb.append(this.mDistinctPacketsRetransmitted);
            sb.append("\nmSendBandwidth = ");
            sb.append(this.mSendBandwidth);
            sb.append("\nmSendBitRateVideo = ");
            sb.append(this.mSendBitRateVideo);
            sb.append("\nmSendBitRateAudio = ");
            sb.append(this.mSendBitRateAudio);
            sb.append("\nmSendBitRateTotal = ");
            sb.append(this.mSendBitRateTotal);
            sb.append("}");
            return sb.toString();
        }
    }

    public static native boolean WriteSettings(String str, String str2);

    public static native String getEngineVersion();

    public static native int getEngineVersionInt();

    public static ConferenceEngine getInstance() {
        if (instance == null) {
            synchronized (ConferenceEngine.class) {
                if (instance == null) {
                    instance = new ConferenceEngine();
                }
            }
        }
        return instance;
    }

    public native void AutoStartCamera(boolean z);

    public native void AutoStartMicrophone(boolean z);

    public native void AutoStartSpeaker(boolean z);

    public native long Construct(Context context, long j, String str, byte[] bArr, String str2);

    public native void Dispose();

    public native boolean Join(String str, String str2, boolean z, boolean z2, boolean z3, String str3, String str4, String str5, int i, boolean z4, String str6, String str7);

    public native void Leave();

    public native void MuteMicrophone(boolean z);

    public native boolean MuteVideo(boolean z);

    public native boolean SetGslbConfig(byte[] bArr);

    public native void SetSecure(boolean z);

    public native boolean StartVideo(boolean z);

    public native void acceptCall();

    public native long addVideoStream(int i, int i2, int i3);

    public native void declineCall();

    public native void enableCameraRotation(boolean z);

    public native void enableMonitorTraffic(boolean z);

    public native int getAECType();

    public long getAddress() {
        return this.address;
    }

    public EngineErrorTypeT getEngineError(int i) {
        EngineErrorTypeT[] values;
        EngineErrorTypeT engineErrorTypeT = EngineErrorTypeT.ENGINE_UNKNOWN_ERROR;
        for (EngineErrorTypeT engineErrorTypeT2 : EngineErrorTypeT.values()) {
            if (engineErrorTypeT2.ordinal() == i) {
                engineErrorTypeT = engineErrorTypeT2;
            }
        }
        return engineErrorTypeT;
    }

    public native String getIceStat();

    public native InVideoStat getInVideoStat();

    public native MonitorData getMonitorData();

    public String getNetworkEnv(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return "No network";
        }
        if (activeNetworkInfo.getType() == 1) {
            return "WIFI";
        }
        String str = "Unknown";
        if (activeNetworkInfo.getType() != 0) {
            return str;
        }
        switch (activeNetworkInfo.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return "2G";
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return "3G";
            case 13:
                return "4G";
            default:
                return str;
        }
    }

    public native OutVideoStat getOutVideoStat();

    public native int getRunHorseTime();

    public native int getUserPlayoutVolume(String str);

    public native int getVoiceMode();

    public boolean initialize(Context context, long j, String str, byte[] bArr, ConferenceCallback conferenceCallback, String str2) {
        this.cc = conferenceCallback;
        if (bArr != null) {
            StringBuilder sb = new StringBuilder();
            String str3 = "Java gslb_config_str.length. ";
            sb.append(str3);
            sb.append(String.valueOf(bArr.length));
            Logger.LogI(sb.toString());
            String str4 = new String(bArr);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str3);
            sb2.append(str4);
            Logger.LogI(sb2.toString());
        }
        this.address = Construct(context, j, str, bArr, str2);
        return this.address != 0;
    }

    public native boolean isInConference();

    public native void localParticipantSetDynamicViewPolicy(DynamicViewPolicyT dynamicViewPolicyT);

    public native void muteUserAudio(String str, boolean z);

    public void onError(String str, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("Java onError: ");
        sb.append(i);
        sb.append(" ");
        sb.append(getEngineError(i));
        Logger.LogI(sb.toString());
        this.cc.onError(str, getEngineError(i));
    }

    public void onGetConnnectionData(ConnectionData connectionData) {
        Logger.LogI("Java onGetConnectionData.");
    }

    public void onGetFirstAudioSample() {
        this.cc.onGetFirstAudioSample();
    }

    public void onGetFirstVideoSample(String str) {
        this.cc.onGetFirstVideoSample(str);
    }

    public void onGetSpeekerDetect(String[] strArr) {
        this.cc.onGetSpeekerDetect(strArr);
    }

    public void onLocalUserJoined() {
        Logger.LogI("Java onLocalUserJoined.");
        this.cc.onLocalUserJoined();
    }

    public void onLocalUserLeaved(int i) {
        Logger.LogI("Java onLocalUserLeaved.");
        this.cc.onLocalUserLeaved(getEngineError(i));
    }

    public void onParticipantsVolumeChanged(String[] strArr, int[] iArr) {
        ParticipantVolume[] participantVolumeArr = new ParticipantVolume[strArr.length];
        for (int i = 0; i < participantVolumeArr.length; i++) {
            ParticipantVolume participantVolume = new ParticipantVolume();
            participantVolume.mUid = strArr[i];
            participantVolume.mVolume = iArr[i];
            participantVolumeArr[i] = participantVolume;
        }
        this.cc.onParticipantsVolumeChanged(participantVolumeArr);
    }

    public void onRemoteAudioStreamCreated(String str) {
        this.cc.onRemoteAudioStreamCreated(str);
    }

    public void onRemoteAudioStreamRemoved(String str) {
        this.cc.onRemoteAudioStreamRemoved(str);
    }

    public void onRemoteSourceAdded(String str, long j) {
        this.cc.onRemoteSourceAdded(str, j);
    }

    public void onRemoteSourceRemoved(String str, long j) {
        this.cc.onRemoteSourceRemoved(str, j);
    }

    public void onRemoteUserJoined(String str) {
        this.cc.onRemoteUserJoined(str);
    }

    public void onRemoteUserLeaved(String str) {
        this.cc.onRemoteUserLeaved(str);
    }

    public void onRemoteVidResize(String str, int i, int i2) {
        this.cc.onRemoteVidResize(str, i, i2);
    }

    public void onRemoteVidStreamCreated(String str, String str2) {
        this.cc.onRemoteVidStreamCreated(str, str2);
    }

    public void onRemoteVidStreamRemoved(String str, String str2) {
        this.cc.onRemoteVidStreamRemoved(str, str2);
    }

    public void onSpeakingStatusChanged(String str, boolean z) {
        this.cc.onSpeakingStatusChanged(str, z);
    }

    public void onStartCamera() {
        this.cc.onStartCamera();
    }

    public void onStopCamera() {
        this.cc.onStopCamera();
    }

    public native void pushVideoFrameJni(int i, int i2, byte[] bArr, int i3, int i4, int i5, long j, long j2);

    public native void removeVideoStream(long j);

    public native void setAudioType(int i);

    public native void setCallResolutionMode(int i, int i2);

    public native void setEncoderMaxBitRate(int i);

    public native void setLocalNetWork(String str, String str2);

    public native void setMirrorCamera(boolean z);

    public native void setPlayoutVolume(int i);

    public native void setPowerStatus(int i, boolean z);

    public native void setRemoteNetWork(String str, String str2);

    public native boolean setScreenFps(int i);

    public native boolean setScreenResolution(int i, int i2);

    public native void setUserPlayoutVolume(String str, double d2);

    public native void startSpeakingMonitor();

    public native void startVolumeMonitor(int i);

    public native void stopSpeakingMonitor();

    public native void stopVolumeMonitor();

    public native boolean switchVideoContent(VideoContentTypeT videoContentTypeT);

    public void uninitialize() {
        Dispose();
        this.cc = null;
    }
}
