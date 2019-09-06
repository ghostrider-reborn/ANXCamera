package com.xiaomi.conferencemanager.callback;

import com.xiaomi.conferencemanager.ConferenceManager.EngineErrorTypeT;

public interface ConferenceCallback {

    public static class ParticipantVolume {
        public String mUid;
        public int mVolume;
    }

    void onError(String str, EngineErrorTypeT engineErrorTypeT);

    void onGetFirstAudioSample();

    void onGetFirstVideoSample(String str);

    void onGetSpeekerDetect(String[] strArr);

    void onLoad(boolean z);

    void onLocalUserJoined();

    void onLocalUserLeaved(EngineErrorTypeT engineErrorTypeT);

    void onParticipantsVolumeChanged(ParticipantVolume[] participantVolumeArr);

    void onRemoteAudioStreamCreated(String str);

    void onRemoteAudioStreamRemoved(String str);

    void onRemoteSourceAdded(String str, long j);

    void onRemoteSourceRemoved(String str, long j);

    void onRemoteUserJoined(String str);

    void onRemoteUserLeaved(String str);

    void onRemoteVidResize(String str, int i, int i2);

    void onRemoteVidStreamCreated(String str, String str2);

    void onRemoteVidStreamRemoved(String str, String str2);

    void onSpeakingStatusChanged(String str, boolean z);

    void onStartCamera();

    void onStopCamera();
}
