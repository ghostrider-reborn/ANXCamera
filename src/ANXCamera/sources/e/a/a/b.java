package e.a.a;

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
    protected static int[] sampleRateSuggested = {44100, BaseModule.LENS_DIRTY_DETECT_HINT_DURATION, 11025, 16000, 22050};
    AudioDataProcessThread Bq;
    a Cq;
    boolean Dq = false;
    int Eq = 1;
    AudioRecord audio;
    int audioFormat = 2;
    int bufferSizeInBytes = 0;
    int channelConfig = -1;
    boolean isRecording = false;
    int sampleRateInHz = -1;

    /* compiled from: BufferedAudioRecorder */
    private class a implements Runnable {
        boolean Aq;
        private double speed;

        public a(double d2, boolean z) {
            this.speed = d2;
            this.Aq = z;
        }

        public void run() {
            boolean z;
            String str = b.TAG;
            b bVar = b.this;
            byte[] bArr = new byte[bVar.bufferSizeInBytes];
            bVar.Dq = false;
            a aVar = bVar.Cq;
            bVar.Bq = new AudioDataProcessThread(aVar, aVar);
            b.this.Bq.start();
            if (this.Aq) {
                b bVar2 = b.this;
                bVar2.Bq.startFeeding(bVar2.sampleRateInHz, this.speed);
            }
            try {
                if (b.this.audio != null) {
                    b.this.audio.startRecording();
                    if (b.this.audio == null || b.this.audio.getRecordingState() == 3) {
                        z = false;
                    } else {
                        b.this.Cq.recordStatus(false);
                        z = true;
                    }
                    boolean z2 = z;
                    int i = 0;
                    while (true) {
                        b bVar3 = b.this;
                        if (!bVar3.isRecording) {
                            break;
                        }
                        AudioRecord audioRecord = bVar3.audio;
                        if (audioRecord != null) {
                            i = audioRecord.read(bArr, 0, bVar3.bufferSizeInBytes);
                        }
                        if (-3 == i) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("bad audio buffer len ");
                            sb.append(i);
                            VELogUtil.e(str, sb.toString());
                        } else if (i > 0) {
                            try {
                                if (b.this.isRecording) {
                                    b.this.Cq.addPCMData(bArr, i);
                                }
                                if (b.this.Bq.isProcessing() && !b.this.Dq) {
                                    b.this.Bq.feed(bArr, i);
                                }
                            } catch (Exception unused) {
                            }
                        } else {
                            AudioRecord audioRecord2 = b.this.audio;
                            if (!(audioRecord2 == null || audioRecord2.getRecordingState() == 3 || z2)) {
                                b.this.Cq.recordStatus(false);
                                z2 = true;
                            }
                            Thread.sleep(50);
                        }
                    }
                }
            } catch (Exception e2) {
                try {
                    if (b.this.audio != null) {
                        b.this.audio.release();
                    }
                } catch (Exception unused2) {
                }
                b.this.audio = null;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("audio recording failed!");
                sb2.append(e2);
                VELogUtil.e(str, sb2.toString());
            }
        }
    }

    public b(a aVar) {
        this.Cq = aVar;
    }

    public int J(int i) {
        return 16 == i ? 1 : 2;
    }

    public void Nj() {
        synchronized (this) {
            this.Dq = true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        new java.lang.Thread(new e.a.a.b.a(r2, r3, r5)).start();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0042, code lost:
        java.lang.Runtime.getRuntime().gc();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        java.lang.Thread.sleep(100);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004e, code lost:
        java.lang.System.runFinalization();
        new java.lang.Thread(new e.a.a.b.a(r2, r3, r5)).start();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0019, code lost:
        return;
     */
    public void a(double d2, boolean z) {
        VELogUtil.i(TAG, "startRecording() called");
        synchronized (this) {
            if (this.isRecording) {
                VELogUtil.w(TAG, "recorder is started");
                if (z) {
                    a(d2);
                }
            } else {
                if (this.audio == null) {
                    init(this.Eq);
                    if (this.audio == null) {
                        VELogUtil.e(TAG, "recorder is null");
                        return;
                    }
                }
                this.isRecording = true;
            }
        }
    }

    public boolean a(double d2) {
        StringBuilder sb = new StringBuilder();
        sb.append("startFeeding() called with: speed = [");
        sb.append(d2);
        sb.append("]");
        String sb2 = sb.toString();
        String str = TAG;
        VELogUtil.i(str, sb2);
        if (this.isRecording) {
            AudioDataProcessThread audioDataProcessThread = this.Bq;
            if (audioDataProcessThread != null) {
                if (audioDataProcessThread.isProcessing()) {
                    VELogUtil.w(str, "startFeeding 失败，已经调用过一次了");
                    return false;
                }
                this.Dq = false;
                this.Bq.startFeeding(this.sampleRateInHz, d2);
                return true;
            }
        }
        VELogUtil.w(str, "startFeeding 录音未启动，将先启动startRecording");
        a(d2, true);
        return true;
    }

    public void discard() {
        AudioDataProcessThread audioDataProcessThread = this.Bq;
        if (audioDataProcessThread != null) {
            audioDataProcessThread.discard();
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        AudioRecord audioRecord = this.audio;
        if (audioRecord != null) {
            try {
                if (audioRecord.getState() != 0) {
                    this.audio.stop();
                }
                this.audio.release();
            } catch (Exception unused) {
            }
            this.audio = null;
        }
        super.finalize();
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x0168  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x017f  */
    public void init(int i) {
        int i2;
        String str;
        int i3;
        int i4;
        int i5;
        int[] iArr;
        int i6;
        int i7;
        String str2 = " ";
        this.Eq = i;
        AudioRecord audioRecord = this.audio;
        String str3 = TAG;
        if (audioRecord != null) {
            VELogUtil.e(str3, "second time audio init(), skip");
            return;
        }
        int i8 = -1;
        try {
            if (!(channelConfigOffset == -1 || sampleRateOffset == -1)) {
                this.channelConfig = channelConfigSuggested[channelConfigOffset];
                this.sampleRateInHz = sampleRateSuggested[sampleRateOffset];
                this.bufferSizeInBytes = AudioRecord.getMinBufferSize(this.sampleRateInHz, this.channelConfig, this.audioFormat);
                AudioRecord audioRecord2 = new AudioRecord(i, this.sampleRateInHz, this.channelConfig, this.audioFormat, this.bufferSizeInBytes);
                this.audio = audioRecord2;
            }
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("使用预设配置");
            sb.append(channelConfigOffset);
            sb.append(",");
            sb.append(sampleRateOffset);
            sb.append("实例化audio recorder失败，重新测试配置。");
            sb.append(e2);
            VELogUtil.e(str3, sb.toString());
            this.Cq.lackPermission();
        }
        int i9 = 1;
        if (this.audio == null) {
            channelConfigOffset = -1;
            int[] iArr2 = channelConfigSuggested;
            int length = iArr2.length;
            int i10 = 0;
            boolean z = false;
            while (true) {
                if (i10 >= length) {
                    break;
                }
                this.channelConfig = iArr2[i10];
                channelConfigOffset += i9;
                sampleRateOffset = i8;
                int[] iArr3 = sampleRateSuggested;
                int length2 = iArr3.length;
                int i11 = 0;
                while (true) {
                    if (i11 >= length2) {
                        str = str2;
                        i3 = i10;
                        i2 = i9;
                        break;
                    }
                    int i12 = iArr3[i11];
                    sampleRateOffset += i9;
                    try {
                        this.bufferSizeInBytes = AudioRecord.getMinBufferSize(i12, this.channelConfig, this.audioFormat);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("试用hz ");
                        sb2.append(i12);
                        sb2.append(str2);
                        sb2.append(this.channelConfig);
                        sb2.append(str2);
                        sb2.append(this.audioFormat);
                        VELogUtil.e(str3, sb2.toString());
                        if (this.bufferSizeInBytes > 0) {
                            this.sampleRateInHz = i12;
                            int i13 = this.sampleRateInHz;
                            int i14 = this.channelConfig;
                            int i15 = i13;
                            r3 = r3;
                            str = str2;
                            i7 = i12;
                            i4 = i11;
                            int i16 = i15;
                            i5 = length2;
                            int i17 = i14;
                            iArr = iArr3;
                            i3 = i10;
                            try {
                                AudioRecord audioRecord3 = new AudioRecord(i, i16, i17, this.audioFormat, this.bufferSizeInBytes);
                                this.audio = audioRecord3;
                                i2 = 1;
                                z = true;
                                break;
                            } catch (Exception e3) {
                                e = e3;
                                this.sampleRateInHz = 0;
                                this.audio = null;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("apply audio record sample rate ");
                                sb3.append(i7);
                                sb3.append(" failed: ");
                                sb3.append(e.getMessage());
                                VELogUtil.e(str3, sb3.toString());
                                i6 = 1;
                                sampleRateOffset++;
                                i11 = i4 + 1;
                                i9 = i6;
                                iArr3 = iArr;
                                i10 = i3;
                                length2 = i5;
                                str2 = str;
                            }
                        } else {
                            str = str2;
                            int i18 = i12;
                            i4 = i11;
                            i5 = length2;
                            iArr = iArr3;
                            i3 = i10;
                            sampleRateOffset++;
                            i6 = 1;
                            i11 = i4 + 1;
                            i9 = i6;
                            iArr3 = iArr;
                            i10 = i3;
                            length2 = i5;
                            str2 = str;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        str = str2;
                        i7 = i12;
                        i4 = i11;
                        i5 = length2;
                        iArr = iArr3;
                        i3 = i10;
                        this.sampleRateInHz = 0;
                        this.audio = null;
                        StringBuilder sb32 = new StringBuilder();
                        sb32.append("apply audio record sample rate ");
                        sb32.append(i7);
                        sb32.append(" failed: ");
                        sb32.append(e.getMessage());
                        VELogUtil.e(str3, sb32.toString());
                        i6 = 1;
                        sampleRateOffset++;
                        i11 = i4 + 1;
                        i9 = i6;
                        iArr3 = iArr;
                        i10 = i3;
                        length2 = i5;
                        str2 = str;
                    }
                }
                if (z) {
                    break;
                }
                i10 = i3 + 1;
                i9 = i2;
                str2 = str;
                i8 = -1;
            }
            if (this.sampleRateInHz > 0) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("!Init audio recorder failed, hz ");
                sb4.append(this.sampleRateInHz);
                VELogUtil.e(str3, sb4.toString());
                return;
            }
            if (this.channelConfig != 16) {
                i2 = 2;
            }
            this.Cq.initAudioConfig(this.sampleRateInHz, i2);
            StringBuilder sb5 = new StringBuilder();
            sb5.append("Init audio recorder succeed, apply audio record sample rate ");
            sb5.append(this.sampleRateInHz);
            sb5.append(" buffer ");
            sb5.append(this.bufferSizeInBytes);
            sb5.append(" state ");
            sb5.append(this.audio.getState());
            VELogUtil.e(str3, sb5.toString());
            return;
        }
        i2 = i9;
        if (this.sampleRateInHz > 0) {
        }
    }

    public synchronized boolean isProcessing() {
        return this.Bq != null && this.Bq.isProcessing();
    }

    public boolean stopFeeding() {
        String str = TAG;
        VELogUtil.i(str, "stopFeeding() called");
        if (!this.isRecording || this.audio != null) {
            if (this.isRecording) {
                AudioDataProcessThread audioDataProcessThread = this.Bq;
                if (audioDataProcessThread != null) {
                    if (!audioDataProcessThread.isProcessing()) {
                        VELogUtil.e(str, "stopFeeding 失败，请先startFeeding再stopFeeding");
                        return false;
                    }
                    this.Bq.stopFeeding();
                    return true;
                }
            }
            VELogUtil.e(str, "stopFeeding 失败，请先调用startRecording");
            return false;
        }
        VELogUtil.e(str, "stopFeeding: 状态异常，重置状态");
        this.isRecording = false;
        this.Dq = true;
        AudioDataProcessThread audioDataProcessThread2 = this.Bq;
        if (audioDataProcessThread2 != null) {
            audioDataProcessThread2.stop();
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0035, code lost:
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
            if (this.Bq != null) {
                this.Bq.stop();
            }
        }
    }

    public void unInit() {
        if (this.isRecording) {
            stopRecording();
        }
        AudioRecord audioRecord = this.audio;
        if (audioRecord != null) {
            try {
                if (audioRecord.getState() != 0) {
                    this.audio.stop();
                }
                this.audio.release();
            } catch (Exception unused) {
            }
            this.audio = null;
        }
        VELogUtil.i(TAG, "unInit()");
    }

    public void waitUtilAudioProcessDone() {
        AudioDataProcessThread audioDataProcessThread = this.Bq;
        if (audioDataProcessThread != null) {
            audioDataProcessThread.waitUtilAudioProcessDone();
        }
    }
}
