package com.ss.android.ttve.audio;

import android.media.AudioRecord;
import android.util.Log;
import com.android.camera.module.BaseModule;

public class TEBufferedAudioRecorder {
    private static final String TAG = "TEBufferedAudioRecorder";
    protected static int channelConfigOffset = -1;
    protected static int[] channelConfigSuggested = {12, 16, 1};
    protected static int sampleRateOffset = -1;
    protected static int[] sampleRateSuggested = {44100, BaseModule.LENS_DIRTY_DETECT_HINT_DURATION_8S, 11025, 16000, 22050};
    AudioRecord audio;
    TEAudioWriterInterface audioCaller;
    int audioFormat = 2;
    int bufferSizeInBytes = 0;
    int channelConfig = -1;
    boolean isRecording = false;
    int sampleRateInHz = -1;

    public TEBufferedAudioRecorder(TEAudioWriterInterface tEAudioWriterInterface) {
        this.audioCaller = tEAudioWriterInterface;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (this.audio != null) {
            try {
                if (this.audio.getState() != 0) {
                    this.audio.stop();
                }
                this.audio.release();
            } catch (Exception e) {
            }
            this.audio = null;
        }
        super.finalize();
    }

    public int getSampleRateInHz() {
        return this.sampleRateInHz;
    }

    public void init(int i) {
        if (this.audio != null) {
            Log.e(TAG, "second time audio init(), skip");
            return;
        }
        int i2 = -1;
        try {
            if (!(channelConfigOffset == -1 || sampleRateOffset == -1)) {
                this.channelConfig = channelConfigSuggested[channelConfigOffset];
                this.sampleRateInHz = sampleRateSuggested[sampleRateOffset];
                this.bufferSizeInBytes = AudioRecord.getMinBufferSize(this.sampleRateInHz, this.channelConfig, this.audioFormat);
                AudioRecord audioRecord = new AudioRecord(i, this.sampleRateInHz, this.channelConfig, this.audioFormat, this.bufferSizeInBytes);
                this.audio = audioRecord;
            }
        } catch (Exception e) {
            Log.e(TAG, "使用预设配置" + channelConfigOffset + "," + sampleRateOffset + "实例化audio recorder失败，重新测试配置。" + e);
        }
        if (this.audio == null) {
            channelConfigOffset = -1;
            int[] iArr = channelConfigSuggested;
            int length = iArr.length;
            int i3 = 0;
            boolean z = false;
            while (i3 < length) {
                this.channelConfig = iArr[i3];
                channelConfigOffset++;
                sampleRateOffset = i2;
                int[] iArr2 = sampleRateSuggested;
                int length2 = iArr2.length;
                int i4 = 0;
                while (true) {
                    if (i4 >= length2) {
                        break;
                    }
                    int i5 = iArr2[i4];
                    sampleRateOffset++;
                    try {
                        this.bufferSizeInBytes = AudioRecord.getMinBufferSize(i5, this.channelConfig, this.audioFormat);
                        Log.i(TAG, "试用hz " + i5 + " " + this.channelConfig + " " + this.audioFormat);
                        if (this.bufferSizeInBytes > 0) {
                            this.sampleRateInHz = i5;
                            int i6 = this.sampleRateInHz;
                            int i7 = this.channelConfig;
                            AudioRecord audioRecord2 = new AudioRecord(i, i6, i7, this.audioFormat, this.bufferSizeInBytes);
                            this.audio = audioRecord2;
                            z = true;
                            break;
                        }
                        sampleRateOffset++;
                        i4++;
                    } catch (Exception e2) {
                        this.sampleRateInHz = 0;
                        this.audio = null;
                        Log.e(TAG, "apply audio record sample rate " + i5 + " failed: " + e2.getMessage());
                        sampleRateOffset = sampleRateOffset + 1;
                    }
                }
                if (z) {
                    break;
                }
                i3++;
                i2 = -1;
            }
        }
        if (this.sampleRateInHz <= 0) {
            Log.e(TAG, "!Init audio recorder failed, hz " + this.sampleRateInHz);
            return;
        }
        Log.i(TAG, "Init audio recorder succeed, apply audio record sample rate " + this.sampleRateInHz + " buffer " + this.bufferSizeInBytes + " state " + this.audio.getState());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0015, code lost:
        r10 = r9.audioCaller.initWavFile(r10, r9.sampleRateInHz, 2, r11, r13, r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0022, code lost:
        if (r10 == 0) goto L_0x003b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0024, code lost:
        android.util.Log.e(TAG, "init wav file failed, ret = " + r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003a, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003b, code lost:
        new java.lang.Thread(new com.ss.android.ttve.audio.TEBufferedAudioRecorder.AnonymousClass1(r9)).start();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0048, code lost:
        return;
     */
    public void startRecording(String str, double d, int i, int i2) {
        Log.e(TAG, "audio startRecording");
        synchronized (this) {
            if (!this.isRecording) {
                if (this.audio != null) {
                    this.isRecording = true;
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0014, code lost:
        if (r3.audio.getState() == 0) goto L_0x001b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0016, code lost:
        r3.audio.stop();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001b, code lost:
        r3.audioCaller.closeWavFile();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0021, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0034, code lost:
        return false;
     */
    public boolean stopRecording() {
        synchronized (this) {
            if (this.isRecording) {
                if (this.audio != null) {
                    this.isRecording = false;
                }
            }
            Log.e(TAG, "未启动音频模块但调用stopRecording");
            if (this.audio != null) {
                this.audio.release();
            }
        }
    }

    public void unInit() {
        if (this.audio != null) {
            try {
                if (this.audio.getState() != 0) {
                    this.audio.stop();
                }
                this.audio.release();
            } catch (Exception e) {
            }
            this.audio = null;
        }
        if (this.audioCaller != null) {
            this.audioCaller.destroy();
        }
    }
}
