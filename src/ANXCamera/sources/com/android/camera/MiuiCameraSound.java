package com.android.camera;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import com.android.camera.log.Log;
import com.mi.config.b;
import com.ss.android.vesdk.VEEditor;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;

public class MiuiCameraSound implements Consumer<PlayConfig> {
    public static final int AUDIO_CAPTURE = 7;
    public static final int FAST_BURST = 4;
    public static final int FOCUS_COMPLETE = 1;
    public static final int KNOBS_SCROLL = 6;
    private static final int NUM_MEDIA_SOUND_STREAMS = 1;
    public static final int SHUTTER_CLICK = 0;
    public static final int SHUTTER_DELAY = 5;
    private static final String[] SOUND_FILES = {"camera_click_v9.mp3", "camera_focus_v9.mp3", "video_record_start_v9.ogg", "video_record_end_v9.ogg", "camera_fast_burst_v9.ogg", "sound_shuter_delay_bee.ogg", "/system/media/audio/ui/NumberPickerValueChange.ogg", "audio_capture.ogg"};
    private static final int SOUND_NOT_LOADED = -1;
    public static final int START_VIDEO_RECORDING = 2;
    public static final int STOP_VIDEO_RECORDING = 3;
    private static final String TAG = "MiuiCameraSound";
    private final AssetManager mAssetManager;
    private final AudioManager mAudioManager;
    private Disposable mDisposable;
    /* access modifiers changed from: private */
    public FlowableEmitter<PlayConfig> mFlowableEmitter;
    private boolean mForceSound;
    private long mLastPlayTime;
    private SoundPool.OnLoadCompleteListener mLoadCompleteListener;
    /* access modifiers changed from: private */
    public int mSoundIdToPlay;
    private int[] mSoundIds;
    private SoundPool mSoundPool;

    public static class PlayConfig {
        public int soundId;
        public float volume = 1.0f;
    }

    public MiuiCameraSound(Context context) {
        this(context, false);
    }

    public MiuiCameraSound(Context context, boolean z) {
        this.mLastPlayTime = 0;
        this.mLoadCompleteListener = new SoundPool.OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundPool, int i, int i2) {
                if (i2 != 0) {
                    Log.e(MiuiCameraSound.TAG, "Unable to load sound for playback (status: " + i2 + ")");
                } else if (MiuiCameraSound.this.mSoundIdToPlay == i) {
                    soundPool.play(i, 1.0f, 1.0f, 0, 0, 1.0f);
                    int unused = MiuiCameraSound.this.mSoundIdToPlay = -1;
                }
            }
        };
        this.mAudioManager = (AudioManager) context.getSystemService(VEEditor.MVConsts.TYPE_AUDIO);
        this.mAssetManager = context.getAssets();
        this.mForceSound = z;
        SoundPool.Builder builder = new SoundPool.Builder();
        int i = 1;
        builder.setMaxStreams(1);
        builder.setAudioAttributes(new AudioAttributes.Builder().setInternalLegacyStreamType((!b.iI() || this.mForceSound) ? 7 : i).build());
        this.mSoundPool = builder.build();
        this.mSoundPool.setOnLoadCompleteListener(this.mLoadCompleteListener);
        this.mSoundIds = new int[SOUND_FILES.length];
        for (int i2 = 0; i2 < this.mSoundIds.length; i2++) {
            this.mSoundIds[i2] = -1;
        }
        this.mSoundIdToPlay = -1;
        this.mDisposable = Flowable.create(new FlowableOnSubscribe<PlayConfig>() {
            public void subscribe(FlowableEmitter<PlayConfig> flowableEmitter) throws Exception {
                FlowableEmitter unused = MiuiCameraSound.this.mFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).observeOn(Schedulers.io()).onBackpressureDrop(new Consumer<PlayConfig>() {
            public void accept(@NonNull PlayConfig playConfig) throws Exception {
                Log.e(MiuiCameraSound.TAG, "play sound too fast: " + playConfig.soundId);
            }
        }).subscribe(this);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0038 A[SYNTHETIC, Splitter:B:19:0x0038] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x004c A[SYNTHETIC, Splitter:B:25:0x004c] */
    private int loadFromAsset(int i) {
        AssetFileDescriptor assetFileDescriptor = null;
        try {
            AssetFileDescriptor openFd = this.mAssetManager.openFd(SOUND_FILES[i]);
            try {
                int load = this.mSoundPool.load(openFd, 1);
                if (openFd == null) {
                    return load;
                }
                try {
                    openFd.close();
                    return load;
                } catch (IOException e) {
                    Log.e(TAG, "IOException occurs when closing Camera Sound AssetFileDescriptor.");
                    e.printStackTrace();
                    return load;
                }
            } catch (IOException e2) {
                IOException iOException = e2;
                assetFileDescriptor = openFd;
                e = iOException;
                try {
                    e.printStackTrace();
                    if (assetFileDescriptor != null) {
                    }
                    return -1;
                } catch (Throwable th) {
                    th = th;
                    if (assetFileDescriptor != null) {
                        try {
                            assetFileDescriptor.close();
                        } catch (IOException e3) {
                            Log.e(TAG, "IOException occurs when closing Camera Sound AssetFileDescriptor.");
                            e3.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                Throwable th3 = th2;
                assetFileDescriptor = openFd;
                th = th3;
                if (assetFileDescriptor != null) {
                }
                throw th;
            }
        } catch (IOException e4) {
            e = e4;
            e.printStackTrace();
            if (assetFileDescriptor != null) {
                try {
                    assetFileDescriptor.close();
                } catch (IOException e5) {
                    Log.e(TAG, "IOException occurs when closing Camera Sound AssetFileDescriptor.");
                    e5.printStackTrace();
                }
            }
            return -1;
        }
    }

    private synchronized void play(int i, float f, int i2) {
        if (i >= 0) {
            if (i < SOUND_FILES.length) {
                if (this.mSoundIds[i] == -1) {
                    if (!SOUND_FILES[i].startsWith("/")) {
                        this.mSoundIdToPlay = loadFromAsset(i);
                    } else {
                        this.mSoundIdToPlay = this.mSoundPool.load(SOUND_FILES[i], 1);
                    }
                    this.mSoundIds[i] = this.mSoundIdToPlay;
                } else {
                    this.mSoundPool.play(this.mSoundIds[i], f, f, 0, i2 - 1, 1.0f);
                    this.mLastPlayTime = System.currentTimeMillis();
                }
            }
        }
        throw new RuntimeException("Unknown sound requested: " + i);
    }

    private void playSound(int i, float f, int i2) {
        if (!b.iI() || this.mForceSound || this.mAudioManager.getRingerMode() == 2) {
            play(i, f, i2);
        }
    }

    public void accept(@NonNull PlayConfig playConfig) throws Exception {
        playSound(playConfig.soundId, playConfig.volume, 1);
    }

    public long getLastSoundPlayTime() {
        return this.mLastPlayTime;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0041, code lost:
        return;
     */
    public synchronized void load(int i) {
        if (i >= 0) {
            if (i < SOUND_FILES.length) {
                if (this.mSoundPool == null) {
                    Log.d(TAG, "mSoundPool has not been init, skip this time");
                    return;
                } else if (this.mSoundIds[i] == -1) {
                    if (!SOUND_FILES[i].startsWith("/")) {
                        this.mSoundIds[i] = loadFromAsset(i);
                    } else {
                        this.mSoundIds[i] = this.mSoundPool.load(SOUND_FILES[i], 1);
                    }
                }
            }
        }
        throw new RuntimeException("Unknown sound requested: " + i);
    }

    public void playSound(int i) {
        playSound(i, 1.0f);
    }

    public void playSound(int i, float f) {
        if (!this.mFlowableEmitter.isCancelled()) {
            PlayConfig playConfig = new PlayConfig();
            playConfig.soundId = i;
            playConfig.volume = f;
            this.mFlowableEmitter.onNext(playConfig);
        }
    }

    public void release() {
        if (this.mDisposable != null && !this.mDisposable.isDisposed()) {
            this.mDisposable.dispose();
        }
        if (this.mSoundPool != null) {
            this.mSoundPool.release();
            this.mSoundPool = null;
        }
    }
}
