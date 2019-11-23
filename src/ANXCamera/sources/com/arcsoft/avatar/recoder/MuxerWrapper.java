package com.arcsoft.avatar.recoder;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.arcsoft.avatar.util.CodecLog;
import com.arcsoft.avatar.util.NotifyMessage;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MuxerWrapper {

    /* renamed from: a  reason: collision with root package name */
    private static final String f40a = "Arc_MuxerWrapper";
    private static final String g = "video";
    private static final String h = ".mp4";

    /* renamed from: b  reason: collision with root package name */
    private int f41b;
    private volatile int c;
    private volatile boolean d;
    private MediaMuxer e;
    private String f;
    private long i;
    private long j;
    private RecordingListener k;

    public MuxerWrapper(@Nullable FileDescriptor fileDescriptor, int i2, RecordingListener recordingListener) {
        this.f = "";
        this.i = 0;
        this.j = 0;
        this.k = null;
        this.k = recordingListener;
        this.f41b = 0;
        this.c = 0;
        this.d = false;
        try {
            this.e = new MediaMuxer(fileDescriptor, 0);
            this.e.setOrientationHint(i2);
            CodecLog.d(f40a, "MuxerWrapper()-> screenOrientation=" + i2);
        } catch (IOException e2) {
            CodecLog.e(f40a, "MuxerWrapper()-> create MediaMuxer failed.");
            e2.printStackTrace();
            this.e = null;
            if (this.k != null) {
                this.k.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_CREATE, 0);
            }
        }
    }

    public MuxerWrapper(@Nullable String str, int i2, int i3, RecordingListener recordingListener) {
        this(str, i3, recordingListener);
    }

    public MuxerWrapper(@Nullable String str, int i2, RecordingListener recordingListener) {
        this.f = "";
        this.i = 0;
        this.j = 0;
        this.k = null;
        this.k = recordingListener;
        this.f = str;
        this.f41b = 0;
        this.c = 0;
        this.d = false;
        CodecLog.d(f40a, "MuxerWrapper()-> video name=" + this.f);
        try {
            this.e = new MediaMuxer(this.f, 0);
            this.e.setOrientationHint(i2);
            CodecLog.d(f40a, "MuxerWrapper()-> screenOrientation=" + i2);
        } catch (IOException e2) {
            CodecLog.e(f40a, "MuxerWrapper()-> create MediaMuxer failed.");
            e2.printStackTrace();
            this.e = null;
            if (this.k != null) {
                this.k.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_CREATE, 0);
            }
        }
    }

    private void a() {
        File file = new File(this.f);
        if (!file.exists()) {
            File file2 = new File(file.getParent());
            if (!file2.exists()) {
                file2.mkdirs();
                return;
            }
            return;
        }
        file.delete();
    }

    public synchronized int addTrack(@NonNull MediaFormat mediaFormat) {
        int i2;
        if (this.e == null) {
            CodecLog.e(f40a, "writeSampleData()-> mMuxer must be created , but it's null until now.");
            return -1;
        }
        try {
            i2 = this.e.addTrack(mediaFormat);
        } catch (Exception e2) {
            if (this.k != null) {
                this.k.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_ADD_TRACK, 0);
            }
            e2.printStackTrace();
            i2 = 0;
        }
        return i2;
    }

    public long getSizeRecordFile() {
        if (TextUtils.isEmpty(this.f)) {
            return 0;
        }
        File file = new File(this.f);
        if (!file.exists() || !file.isFile()) {
            return 0;
        }
        return file.length();
    }

    public long getTimeElapse() {
        return this.j - this.i;
    }

    public boolean isStarted() {
        return this.d;
    }

    public void setCurrentTime(long j2) {
        this.j = j2;
    }

    public void setEncoderCount(int i2) {
        if (i2 <= 0 || i2 > 2) {
            throw new RuntimeException("The encoder count must between 1 and 2.");
        }
        this.f41b = i2;
    }

    public void setStartTime(long j2) {
        this.i = j2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0053, code lost:
        return;
     */
    public synchronized void startMuxer() {
        if (this.e == null) {
            CodecLog.e(f40a, "startMuxer()-> mMuxer must be created , but it's null until now");
            return;
        }
        this.c++;
        if (this.c == this.f41b) {
            try {
                CodecLog.d(f40a, "startMuxer()-> Muxerstart");
                this.e.start();
            } catch (Exception e2) {
                CodecLog.e(f40a, "startMuxer()-> Muxer start failed");
                if (this.k != null) {
                    this.k.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_START, 0);
                }
                e2.printStackTrace();
            }
            this.d = true;
            notifyAll();
            CodecLog.d(f40a, "startMuxer()-> mMuxer is started");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a3, code lost:
        return;
     */
    public synchronized void stopMuxer() {
        if (this.e != null) {
            this.c--;
            CodecLog.d(f40a, "stopMuxer()-> mEncoderCount=" + this.c + " ,maxCount=" + this.f41b);
            if (this.c == 0) {
                try {
                    this.e.stop();
                } catch (Exception e2) {
                    CodecLog.e(f40a, "stopMuxer()-> muxer.stop() error=" + e2.getMessage());
                    if (this.k != null) {
                        this.k.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_STOP, 0);
                    }
                }
                try {
                    this.e.release();
                } catch (Exception e3) {
                    CodecLog.e(f40a, "stopMuxer()-> muxer.release() error=" + e3.getMessage());
                    if (this.k != null) {
                        this.k.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_RELEASE, 0);
                    }
                }
                this.e = null;
                CodecLog.d(f40a, "stopMuxer()-> Muxer is released.");
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003a, code lost:
        return;
     */
    public synchronized void writeSampleData(int i2, @NonNull ByteBuffer byteBuffer, @NonNull MediaCodec.BufferInfo bufferInfo) {
        if (this.e == null) {
            CodecLog.e(f40a, "writeSampleData()-> mMuxer must be created , but it's null until now.");
            return;
        }
        try {
            this.e.writeSampleData(i2, byteBuffer, bufferInfo);
            CodecLog.d(f40a, "writeSampleData()-> writeSampleData done");
        } catch (Exception e2) {
            CodecLog.e(f40a, "writeSampleData()-> writeSampleData failed");
            e2.printStackTrace();
            if (this.k != null) {
                this.k.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_WRITE_SAMPLE_DATA, 0);
            }
        }
    }
}
