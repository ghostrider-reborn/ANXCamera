package com.bef.effectsdk;

import android.media.MediaPlayer;
import android.support.annotation.Keep;
import android.util.Log;

@Keep
public class AudioPlayer {
    public static final String TAG = AudioPlayer.class.getSimpleName();
    private String mFilename;
    /* access modifiers changed from: private */
    public boolean mIsPrepared;
    /* access modifiers changed from: private */
    public MediaPlayer mMediaPlayer;
    /* access modifiers changed from: private */
    public long mNativePtr;

    @Keep
    public AudioPlayer() {
        this.mIsPrepared = false;
        this.mMediaPlayer = null;
        this.mIsPrepared = false;
        this.mFilename = null;
    }

    /* access modifiers changed from: private */
    @Keep
    public native void nativeOnCompletion(long j);

    /* access modifiers changed from: private */
    @Keep
    public native void nativeOnError(long j, int i, int i2);

    /* access modifiers changed from: private */
    @Keep
    public native void nativeOnInfo(long j, int i, int i2);

    /* access modifiers changed from: private */
    @Keep
    public native void nativeOnPrepared(long j);

    @Keep
    public float getCurrentPlayTime() {
        if (this.mMediaPlayer != null) {
            return ((float) this.mMediaPlayer.getCurrentPosition()) / 1000.0f;
        }
        Log.e(TAG, "MediaPlayer is null!");
        return 0.0f;
    }

    @Keep
    public float getTotalPlayTime() {
        if (this.mMediaPlayer != null) {
            return ((float) this.mMediaPlayer.getDuration()) / 1000.0f;
        }
        Log.e(TAG, "MediaPlayer is null!");
        return 0.0f;
    }

    @Keep
    public int init() {
        this.mIsPrepared = false;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.stop();
            this.mMediaPlayer.release();
        }
        this.mMediaPlayer = new MediaPlayer();
        this.mMediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
                String str = AudioPlayer.TAG;
                Log.i(str, "MediaPlayer onInfo: [what, extra] = [" + i + ", " + i2 + "]");
                AudioPlayer.this.nativeOnInfo(AudioPlayer.this.mNativePtr, i, i2);
                return false;
            }
        });
        this.mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                String str = AudioPlayer.TAG;
                Log.d(str, "MediaPlayer onError: [what, extra] = [" + i + ", " + i2 + "]");
                try {
                    AudioPlayer.this.mMediaPlayer.stop();
                    AudioPlayer.this.mMediaPlayer.release();
                } catch (Exception e) {
                    e.printStackTrace();
                    String str2 = AudioPlayer.TAG;
                    Log.e(str2, "MediaPlayer stop exception on error " + e.toString());
                }
                MediaPlayer unused = AudioPlayer.this.mMediaPlayer = null;
                AudioPlayer.this.nativeOnError(AudioPlayer.this.mNativePtr, i, i2);
                return false;
            }
        });
        this.mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                Log.i(AudioPlayer.TAG, "MediaPlayer onPrepared...");
                boolean unused = AudioPlayer.this.mIsPrepared = true;
                AudioPlayer.this.nativeOnPrepared(AudioPlayer.this.mNativePtr);
            }
        });
        this.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.i(AudioPlayer.TAG, "MediaPlayer onCompletion...");
                AudioPlayer.this.nativeOnCompletion(AudioPlayer.this.mNativePtr);
            }
        });
        return 0;
    }

    @Keep
    public boolean isPlaying() {
        if (this.mMediaPlayer == null) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else if (!this.mIsPrepared) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else {
            try {
                return this.mMediaPlayer.isPlaying();
            } catch (Exception e) {
                e.printStackTrace();
                String str = TAG;
                Log.e(str, "MediaPlayer isPlaying exception. " + e.toString());
                return false;
            }
        }
    }

    @Keep
    public boolean pause() {
        if (this.mMediaPlayer == null) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else if (!this.mIsPrepared) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else {
            this.mMediaPlayer.pause();
            return true;
        }
    }

    @Keep
    public int release() {
        if (this.mMediaPlayer == null) {
            return 0;
        }
        try {
            this.mMediaPlayer.stop();
            this.mMediaPlayer.release();
        } catch (Exception e) {
            e.printStackTrace();
            String str = TAG;
            Log.e(str, "MediaPlayer stop exception on release " + e.toString());
        }
        this.mMediaPlayer = null;
        return 0;
    }

    @Keep
    public boolean resume() {
        if (this.mMediaPlayer == null) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else if (!this.mIsPrepared) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else {
            this.mMediaPlayer.start();
            return true;
        }
    }

    @Keep
    public boolean seek(int i) {
        if (this.mMediaPlayer == null) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else if (!this.mIsPrepared) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else {
            try {
                this.mMediaPlayer.seekTo(i);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                String str = TAG;
                Log.e(str, "MediaPlayer seek exception. " + e.toString());
                return true;
            }
        }
    }

    @Keep
    public void setDataSource(String str) {
        if (this.mMediaPlayer == null) {
            init();
        }
        if (!str.equals(this.mFilename) || !this.mIsPrepared || !this.mMediaPlayer.isPlaying()) {
            try {
                this.mMediaPlayer.reset();
                this.mMediaPlayer.setDataSource(str);
            } catch (Exception e) {
                e.printStackTrace();
                String str2 = TAG;
                Log.e(str2, "MediaPlayer setDataSource exception. " + e.toString());
            }
            this.mFilename = str;
        }
    }

    @Keep
    public boolean setLoop(boolean z) {
        if (this.mMediaPlayer == null) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        }
        String str = TAG;
        Log.i(str, "set isLoop " + z);
        this.mMediaPlayer.setLooping(z);
        return true;
    }

    @Keep
    public void setNativePtr(long j) {
        this.mNativePtr = j;
    }

    @Keep
    public boolean setVolume(float f) {
        if (this.mMediaPlayer == null) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else if (!this.mIsPrepared) {
            Log.e(TAG, "MediaPlayer is null!");
            return false;
        } else {
            this.mMediaPlayer.setVolume(f, f);
            return true;
        }
    }

    @Keep
    public void startPlay() {
        if (this.mMediaPlayer != null) {
            try {
                if (!this.mIsPrepared) {
                    this.mMediaPlayer.prepare();
                    this.mIsPrepared = true;
                }
                this.mMediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
                String str = TAG;
                Log.e(str, "MediaPlayer setDataSource exception. " + e.toString());
            }
        }
    }

    @Keep
    public void stopPlay() {
        if (this.mMediaPlayer == null) {
            Log.e(TAG, "MediaPlayer is null!");
        } else if (this.mIsPrepared) {
            try {
                this.mMediaPlayer.stop();
                this.mMediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
                String str = TAG;
                Log.e(str, "MediaPlayer stop exception on stop " + e.toString());
            }
            this.mMediaPlayer = null;
            this.mIsPrepared = false;
        }
    }
}
