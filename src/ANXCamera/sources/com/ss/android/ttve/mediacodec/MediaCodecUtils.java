package com.ss.android.ttve.mediacodec;

import android.media.MediaCodecInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

class MediaCodecUtils {
    MediaCodecUtils() {
    }

    @Nullable
    @RequiresApi(api = 18)
    static MediaCodecInfo.CodecProfileLevel findBestMatchedProfile(@NonNull MediaCodecInfo.CodecCapabilities codecCapabilities, int i) {
        MediaCodecInfo.CodecProfileLevel codecProfileLevel = null;
        for (MediaCodecInfo.CodecProfileLevel codecProfileLevel2 : codecCapabilities.profileLevels) {
            if (codecProfileLevel2.profile == i) {
                return codecProfileLevel2;
            }
            if (codecProfileLevel == null || codecProfileLevel.profile < codecProfileLevel2.profile) {
                codecProfileLevel = codecProfileLevel2;
            }
        }
        return codecProfileLevel;
    }
}
