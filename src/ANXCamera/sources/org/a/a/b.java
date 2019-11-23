package org.a.a;

import android.media.AudioRecord;
import android.util.Log;
import com.android.camera.module.BaseModule;
import com.ss.android.medialib.audio.AudioDataProcessThread;
import com.ss.android.medialib.common.LogUtil;
import com.ss.android.vesdk.VELogUtil;

/* compiled from: BufferedAudioRecorder */
public class b {
    private static final String TAG = "BufferedAudioRecorder";
    protected static int channelConfigOffset = -1;
    protected static int[] channelConfigSuggested = {12, 16, 1};
    protected static int sampleRateOffset = -1;
    protected static int[] sampleRateSuggested = {44100, BaseModule.LENS_DIRTY_DETECT_HINT_DURATION_8S, 11025, 16000, 22050};
    AudioRecord audio;
    int audioFormat = 2;
    int bufferSizeInBytes = 0;
    int channelConfig = -1;
    boolean isRecording = false;
    int sampleRateInHz = -1;
    AudioDataProcessThread yt;
    a yu;
    boolean yv = false;
    int yw = 1;

    /* compiled from: BufferedAudioRecorder */
    private class a implements Runnable {
        private double speed;
        boolean yx;

        public a(double d, boolean z) {
            this.speed = d;
            this.yx = z;
        }

        public void run() {
            boolean z;
            byte[] bArr = new byte[b.this.bufferSizeInBytes];
            b.this.yv = false;
            b.this.yt = new AudioDataProcessThread(b.this.yu, b.this.yu);
            b.this.yt.start();
            if (this.yx) {
                b.this.yt.startFeeding(b.this.sampleRateInHz, this.speed);
            }
            try {
                if (b.this.audio != null) {
                    b.this.audio.startRecording();
                    if (b.this.audio == null || b.this.audio.getRecordingState() == 3) {
                        z = false;
                    } else {
                        b.this.yu.recordStatus(false);
                        z = true;
                    }
                    boolean z2 = z;
                    int i = 0;
                    while (b.this.isRecording) {
                        if (b.this.audio != null) {
                            i = b.this.audio.read(bArr, 0, b.this.bufferSizeInBytes);
                        }
                        if (-3 == i) {
                            VELogUtil.e(b.TAG, "bad audio buffer len " + i);
                        } else if (i > 0) {
                            try {
                                if (b.this.isRecording) {
                                    b.this.yu.addPCMData(bArr, i);
                                }
                                if (b.this.yt.isProcessing() && !b.this.yv) {
                                    b.this.yt.feed(bArr, i);
                                }
                            } catch (Exception e) {
                            }
                        } else {
                            if (!(b.this.audio == null || b.this.audio.getRecordingState() == 3 || z2)) {
                                b.this.yu.recordStatus(false);
                                z2 = true;
                            }
                            try {
                                Thread.sleep(50);
                            } catch (Exception e2) {
                            }
                        }
                    }
                }
            } catch (Exception e3) {
                try {
                    if (b.this.audio != null) {
                        b.this.audio.release();
                    }
                } catch (Exception e4) {
                }
                b.this.audio = null;
                VELogUtil.e(b.TAG, "audio recording failed!" + e3);
            }
        }
    }

    public b(a aVar) {
        this.yu = aVar;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0019, code lost:
        return;
     */
    public void a(double d, boolean z) {
        VELogUtil.i(TAG, "startRecording() called");
        synchronized (this) {
            if (this.isRecording) {
                VELogUtil.w(TAG, "recorder is started");
                if (z) {
                    d(d);
                }
            } else {
                if (this.audio == null) {
                    init(this.yw);
                    if (this.audio == null) {
                        VELogUtil.e(TAG, "recorder is null");
                        return;
                    }
                }
                this.isRecording = true;
                try {
                    new Thread(new a(d, z)).start();
                } catch (OutOfMemoryError e) {
                    Runtime.getRuntime().gc();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e2) {
                    }
                    System.runFinalization();
                    new Thread(new a(d, z)).start();
                }
            }
        }
    }

    public int ad(int i) {
        return 16 == i ? 1 : 2;
    }

    public boolean d(double d) {
        VELogUtil.i(TAG, "startFeeding() called with: speed = [" + d + "]");
        if (!this.isRecording || this.yt == null) {
            VELogUtil.w(TAG, "startFeeding 录音未启动，将先启动startRecording");
            a(d, true);
            return true;
        } else if (this.yt.isProcessing()) {
            VELogUtil.w(TAG, "startFeeding 失败，已经调用过一次了");
            return false;
        } else {
            this.yv = false;
            this.yt.startFeeding(this.sampleRateInHz, d);
            return true;
        }
    }

    public void discard() {
        if (this.yt != null) {
            this.yt.discard();
        }
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

    public void init(int i) {
        int i2;
        int i3;
        int[] iArr;
        int i4;
        int i5 = i;
        this.yw = i5;
        if (this.audio != null) {
            VELogUtil.e(TAG, "second time audio init(), skip");
            return;
        }
        int i6 = -1;
        try {
            if (!(channelConfigOffset == -1 || sampleRateOffset == -1)) {
                this.channelConfig = channelConfigSuggested[channelConfigOffset];
                this.sampleRateInHz = sampleRateSuggested[sampleRateOffset];
                this.bufferSizeInBytes = AudioRecord.getMinBufferSize(this.sampleRateInHz, this.channelConfig, this.audioFormat);
                AudioRecord audioRecord = new AudioRecord(i5, this.sampleRateInHz, this.channelConfig, this.audioFormat, this.bufferSizeInBytes);
                this.audio = audioRecord;
            }
        } catch (Exception e) {
            VELogUtil.e(TAG, "使用预设配置" + channelConfigOffset + "," + sampleRateOffset + "实例化audio recorder失败，重新测试配置。" + e);
            this.yu.lackPermission();
        }
        int i7 = 1;
        if (this.audio == null) {
            channelConfigOffset = -1;
            int[] iArr2 = channelConfigSuggested;
            int length = iArr2.length;
            int i8 = 0;
            boolean z = false;
            while (i8 < length) {
                this.channelConfig = iArr2[i8];
                channelConfigOffset++;
                sampleRateOffset = i6;
                int[] iArr3 = sampleRateSuggested;
                int length2 = iArr3.length;
                int i9 = 0;
                while (true) {
                    if (i9 >= length2) {
                        break;
                    }
                    int i10 = iArr3[i9];
                    sampleRateOffset++;
                    try {
                        this.bufferSizeInBytes = AudioRecord.getMinBufferSize(i10, this.channelConfig, this.audioFormat);
                        VELogUtil.e(TAG, "试用hz " + i10 + " " + this.channelConfig + " " + this.audioFormat);
                        if (this.bufferSizeInBytes > 0) {
                            this.sampleRateInHz = i10;
                            int i11 = this.sampleRateInHz;
                            int i12 = this.channelConfig;
                            r2 = r2;
                            i4 = i10;
                            i2 = i9;
                            int i13 = i12;
                            i3 = length2;
                            iArr = iArr3;
                            try {
                                AudioRecord audioRecord2 = new AudioRecord(i5, i11, i13, this.audioFormat, this.bufferSizeInBytes);
                                this.audio = audioRecord2;
                                z = true;
                                break;
                            } catch (Exception e2) {
                                e = e2;
                                this.sampleRateInHz = 0;
                                this.audio = null;
                                VELogUtil.e(TAG, "apply audio record sample rate " + i4 + " failed: " + e.getMessage());
                                sampleRateOffset = sampleRateOffset + 1;
                                i9 = i2 + 1;
                                iArr3 = iArr;
                                length2 = i3;
                            }
                        } else {
                            int i14 = i10;
                            i2 = i9;
                            i3 = length2;
                            iArr = iArr3;
                            sampleRateOffset++;
                            i9 = i2 + 1;
                            iArr3 = iArr;
                            length2 = i3;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        i4 = i10;
                        i2 = i9;
                        i3 = length2;
                        iArr = iArr3;
                        this.sampleRateInHz = 0;
                        this.audio = null;
                        VELogUtil.e(TAG, "apply audio record sample rate " + i4 + " failed: " + e.getMessage());
                        sampleRateOffset = sampleRateOffset + 1;
                        i9 = i2 + 1;
                        iArr3 = iArr;
                        length2 = i3;
                    }
                }
                if (z) {
                    break;
                }
                i8++;
                i6 = -1;
            }
        }
        if (this.sampleRateInHz <= 0) {
            VELogUtil.e(TAG, "!Init audio recorder failed, hz " + this.sampleRateInHz);
            return;
        }
        if (this.channelConfig != 16) {
            i7 = 2;
        }
        this.yu.initAudioConfig(this.sampleRateInHz, i7);
        VELogUtil.e(TAG, "Init audio recorder succeed, apply audio record sample rate " + this.sampleRateInHz + " buffer " + this.bufferSizeInBytes + " state " + this.audio.getState());
    }

    public synchronized boolean isProcessing() {
        return this.yt != null && this.yt.isProcessing();
    }

    public void kI() {
        synchronized (this) {
            this.yv = true;
        }
    }

    public boolean kJ() {
        VELogUtil.i(TAG, "stopFeeding() called");
        if (this.isRecording && this.audio == null) {
            VELogUtil.e(TAG, "stopFeeding: 状态异常，重置状态");
            this.isRecording = false;
            this.yv = true;
            if (this.yt != null) {
                this.yt.stop();
            }
            return false;
        } else if (!this.isRecording || this.yt == null) {
            VELogUtil.e(TAG, "stopFeeding 失败，请先调用startRecording");
            return false;
        } else if (!this.yt.isProcessing()) {
            VELogUtil.e(TAG, "stopFeeding 失败，请先startFeeding再stopFeeding");
            return false;
        } else {
            this.yt.stopFeeding();
            return true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0034, code lost:
        return true;
     */
    public boolean stopRecording() {
        synchronized (this) {
            Log.d(TAG, "stopRecording() called");
            if (!this.isRecording) {
                return false;
            }
            this.isRecording = false;
            if (this.audio == null) {
                LogUtil.e(TAG, "未启动音频模块但调用stopRecording");
            } else if (this.audio.getState() != 0) {
                this.audio.stop();
            }
            if (this.yt != null) {
                this.yt.stop();
            }
        }
    }

    public void unInit() {
        if (this.isRecording) {
            stopRecording();
        }
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
        VELogUtil.i(TAG, "unInit()");
    }

    public void waitUtilAudioProcessDone() {
        if (this.yt != null) {
            this.yt.waitUtilAudioProcessDone();
        }
    }
}
