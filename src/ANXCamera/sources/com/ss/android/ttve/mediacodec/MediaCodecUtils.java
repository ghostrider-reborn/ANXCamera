package com.ss.android.ttve.mediacodec;

import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecInfo.CodecProfileLevel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

class MediaCodecUtils {
    MediaCodecUtils() {
    }

    @Nullable
    @RequiresApi(api = 18)
    static CodecProfileLevel findBestMatchedProfile(@NonNull CodecCapabilities codecCapabilities, int i) {
        CodecProfileLevel[] codecProfileLevelArr;
        CodecProfileLevel codecProfileLevel = null;
        for (CodecProfileLevel codecProfileLevel2 : codecCapabilities.profileLevels) {
            int i2 = codecProfileLevel2.profile;
            if (i2 == i) {
                return codecProfileLevel2;
            }
            if (codecProfileLevel == null || codecProfileLevel.profile < i2) {
                codecProfileLevel = codecProfileLevel2;
            }
        }
        return codecProfileLevel;
    }
}
