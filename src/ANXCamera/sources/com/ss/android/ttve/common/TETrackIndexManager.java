package com.ss.android.ttve.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class TETrackIndexManager {
    public static final int TRACK_TYPE_AUDIO = 1;
    public static final int TRACK_TYPE_VIDEO = 2;
    private List<Integer> mAudioTrackIndexList = new ArrayList();
    private int mFirstAudioIndex = -1;
    private int mFirstVideoIndex = -1;
    private List<Integer> mVideoTrackIndexList = new ArrayList();

    @Retention(RetentionPolicy.SOURCE)
    public @interface TETrackType {
    }

    public int addTrack(int i, int i2) {
        switch (i) {
            case 1:
                if (this.mFirstAudioIndex == -1) {
                    this.mFirstAudioIndex = i2;
                }
                if (this.mAudioTrackIndexList.size() > 0) {
                    i2 = this.mAudioTrackIndexList.get(this.mAudioTrackIndexList.size() - 1).intValue() + 1;
                }
                this.mAudioTrackIndexList.add(Integer.valueOf(i2));
                return i2;
            case 2:
                if (this.mFirstVideoIndex == -1) {
                    this.mFirstVideoIndex = i2;
                }
                if (this.mVideoTrackIndexList.size() > 0) {
                    i2 = this.mVideoTrackIndexList.get(this.mVideoTrackIndexList.size() - 1).intValue() + 1;
                }
                this.mVideoTrackIndexList.add(Integer.valueOf(i2));
                return i2;
            default:
                return i2;
        }
    }

    public int getNativeTrackIndex(int i, int i2) {
        int i3 = 0;
        switch (i) {
            case 1:
                if (i2 >= this.mFirstAudioIndex && this.mFirstAudioIndex != -1) {
                    while (i3 < this.mAudioTrackIndexList.size()) {
                        if (i2 == this.mAudioTrackIndexList.get(i3).intValue()) {
                            return i3 + this.mFirstAudioIndex;
                        }
                        i3++;
                    }
                    break;
                } else {
                    return i2;
                }
                break;
            case 2:
                if (i2 >= this.mFirstVideoIndex && this.mFirstVideoIndex != -1) {
                    while (i3 < this.mVideoTrackIndexList.size()) {
                        if (i2 == this.mVideoTrackIndexList.get(i3).intValue()) {
                            return i3 + this.mFirstVideoIndex;
                        }
                        i3++;
                    }
                    break;
                } else {
                    return i2;
                }
                break;
        }
        return i2;
    }

    public void removeTrack(int i, int i2) {
        switch (i) {
            case 1:
                this.mAudioTrackIndexList.remove(Integer.valueOf(i2));
                return;
            case 2:
                this.mVideoTrackIndexList.remove(Integer.valueOf(i2));
                return;
            default:
                return;
        }
    }
}
