package com.a.a;

import android.media.MediaPlayer;
import java.io.IOException;

/* compiled from: AudioPlayer */
public class a {
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private String mPath;

    public a(String str) {
        this.mPath = str;
    }

    public boolean F() {
        try {
            this.mMediaPlayer.setDataSource(this.mPath);
            this.mMediaPlayer.setAudioStreamType(3);
            this.mMediaPlayer.prepare();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void destroy() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
    }

    public boolean isPlaying() {
        return this.mMediaPlayer.isPlaying();
    }

    public void pause() {
        this.mMediaPlayer.pause();
    }

    public void play() {
        this.mMediaPlayer.start();
    }

    public void resume() {
        this.mMediaPlayer.start();
    }

    public void setLoop(boolean z) {
        this.mMediaPlayer.setLooping(z);
    }

    public void stop() {
        this.mMediaPlayer.stop();
    }
}
