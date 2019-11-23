package com.arcsoft.avatar.recoder;

import android.media.MediaCodec;
import android.os.Bundle;
import android.view.Surface;
import com.arcsoft.avatar.util.CodecLog;
import com.arcsoft.avatar.util.NotifyMessage;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public abstract class BaseEncoder {
    private static final String t = "Arc_BaseEncoder";
    private static final int u = 1000000;
    private static final int v = 1000000;
    private static final int w = 50564;
    private static final long x = 20000;
    private long A = 0;
    private long B = 0;
    private long C = 0;
    private long D = 0;

    /* renamed from: a  reason: collision with root package name */
    protected boolean f31a;

    /* renamed from: b  reason: collision with root package name */
    protected boolean f32b;
    protected boolean c;
    protected boolean d;
    protected volatile boolean e;
    protected Object f;
    protected volatile long g;
    protected MuxerWrapper h;
    protected MediaCodec i;
    protected int j;
    protected int k;
    protected boolean l;
    protected long m;
    protected Queue<Long> n;
    protected RecordingListener o;
    protected long p = 500;
    protected Lock q;
    protected Condition r;
    protected FrameQueue s;
    private long y = 0;
    private long z = 0;

    public BaseEncoder(MuxerWrapper muxerWrapper, Object obj, RecordingListener recordingListener) {
        this.o = recordingListener;
        this.h = muxerWrapper;
        this.c = false;
        this.f32b = false;
        this.f31a = true;
        this.e = false;
        this.d = false;
        this.k = -1;
        this.j = -1;
        this.f = obj;
        this.g = 0;
        this.l = false;
        this.m = 0;
        this.n = new LinkedList();
        CodecLog.d(t, "BaseEncoder constructor out");
    }

    /* access modifiers changed from: protected */
    public long a() {
        long nanoTime = System.nanoTime();
        long j2 = this.g;
        if (this.n.size() != 0) {
            j2 = this.n.poll().longValue();
        }
        long j3 = (nanoTime - j2) / 1000;
        CodecLog.d(t, "getPTSUs TotalPauseTime=" + (this.g / 1000));
        CodecLog.d(t, "getPTSUs preTime=" + this.z + " ,currentTime=" + (nanoTime / 1000) + " , result=" + j3);
        if (j3 < this.z) {
            long j4 = this.z - j3;
            return j4 < x ? (this.z + x) - j4 : this.z + x;
        } else if (0 == this.z) {
            return j3;
        } else {
            long j5 = j3 - this.z;
            return j5 < x ? (j3 + x) - j5 : j3;
        }
    }

    public void drain() {
        if (this.i == null) {
            CodecLog.e(t, "drain()->Encoder is not ready.");
            return;
        }
        String name = Thread.currentThread().getName();
        CodecLog.d(t, "drain()->Encoder one frame. threadName in=" + Thread.currentThread().getName());
        if (this.h == null) {
            CodecLog.e(t, "drain()->Muxer is not ready.");
            return;
        }
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        int i2 = 0;
        while (true) {
            if (!this.f32b) {
                break;
            }
            int dequeueOutputBuffer = this.i.dequeueOutputBuffer(bufferInfo, this.p);
            if (-1 == dequeueOutputBuffer) {
                if (i2 >= 2) {
                    CodecLog.d(t, "drain()->Encoded frame is preparing, wait time out.");
                    break;
                }
                i2++;
                CodecLog.d(t, "drain()->Encoded frame is preparing, wait ... waitCount = " + i2);
            } else if (-2 == dequeueOutputBuffer) {
                if (!this.c) {
                    this.j = this.h.addTrack(this.i.getOutputFormat());
                    this.c = true;
                    if (!this.h.isStarted()) {
                        this.h.startMuxer();
                        CodecLog.d(t, "Muxer started: threadName =" + Thread.currentThread().getName());
                        synchronized (this.h) {
                            while (!this.h.isStarted() && !this.d) {
                                try {
                                    this.h.wait(100);
                                } catch (InterruptedException e2) {
                                    CodecLog.e(t, "drain()->Wait for muxer started, but be interrupted : " + e2.getMessage());
                                    this.c = false;
                                }
                            }
                            long a2 = a();
                            this.D = a2;
                            this.h.setStartTime(a2);
                            CodecLog.i(t, "Muxer start time =" + a2);
                        }
                    } else {
                        continue;
                    }
                } else {
                    CodecLog.e(t, "drain()->Encoder format change twice.");
                    throw new RuntimeException("Format only allow change once, but Encoder meet twice!");
                }
            } else if (dequeueOutputBuffer < 0) {
                CodecLog.i(t, "drain()->Encoder meet bufferStatus =" + dequeueOutputBuffer);
            } else {
                ByteBuffer outputBuffer = this.i.getOutputBuffer(dequeueOutputBuffer);
                if ((2 & bufferInfo.flags) != 0) {
                    bufferInfo.size = 0;
                    CodecLog.i(t, "drain()->Encoder meet bufferStatus : BUFFER_FLAG_CODEC_CONFIG ");
                }
                if (!this.c) {
                    CodecLog.e(t, "drain()->Encoder muxer has not started ");
                }
                if (bufferInfo.size != 0) {
                    outputBuffer.position(bufferInfo.offset);
                    outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
                    CodecLog.d(t, "drain()->Encoder one frame. threadName=" + Thread.currentThread().getName() + " timestamp original buffer info =" + bufferInfo.presentationTimeUs);
                    bufferInfo.presentationTimeUs = a();
                    CodecLog.d(t, "time_diff _" + name + "= " + (bufferInfo.presentationTimeUs - this.y));
                    this.y = bufferInfo.presentationTimeUs;
                    if (bufferInfo.presentationTimeUs - this.A >= 1000000) {
                        this.A = bufferInfo.presentationTimeUs;
                        Bundle bundle = new Bundle();
                        bundle.putInt("request-sync", 1);
                        this.i.setParameters(bundle);
                    }
                    this.h.writeSampleData(this.j, outputBuffer, bufferInfo);
                    this.z = bufferInfo.presentationTimeUs;
                    this.h.setCurrentTime(bufferInfo.presentationTimeUs);
                    CodecLog.d(t, "drain()->Encoder one frame. threadName=" + Thread.currentThread().getName() + " timestamp=" + bufferInfo.presentationTimeUs);
                }
                this.i.releaseOutputBuffer(dequeueOutputBuffer, false);
                if ((4 & bufferInfo.flags) != 0) {
                    if (!this.f31a) {
                        CodecLog.e(t, "drain()->Encoder meet unexpected end of stream.");
                    } else {
                        CodecLog.d(t, "drain()->Encoder meet end of stream.");
                    }
                    this.f32b = false;
                }
            }
        }
        CodecLog.d(t, "drain()->Encoder one frame. threadName out=" + Thread.currentThread().getName());
    }

    public void encode(ByteBuffer byteBuffer, long j2) {
        if (this.i == null) {
            CodecLog.e(t, "encode()->Encoder is not ready.");
            return;
        }
        CodecLog.d(t, "encode()->Encoder one frame. threadName in=" + Thread.currentThread().getName());
        int i2 = 0;
        if (this.f32b) {
            while (this.f32b && !this.f31a) {
                int dequeueInputBuffer = this.i.dequeueInputBuffer(500);
                if (-1 == dequeueInputBuffer) {
                    if (i2 < 3) {
                        i2++;
                        CodecLog.d(t, "encode()->Encoder is busy, wait ... waitCount = " + i2);
                    } else {
                        CodecLog.d(t, "encode()->Encoder is busy, wait time out.");
                        return;
                    }
                } else if (dequeueInputBuffer >= 0) {
                    ByteBuffer inputBuffer = this.i.getInputBuffer(dequeueInputBuffer);
                    if (byteBuffer == null) {
                        this.f31a = true;
                        this.i.queueInputBuffer(dequeueInputBuffer, 0, 0, j2, 4);
                        CodecLog.d(t, "encode()->Encoder meets end of stream.");
                        return;
                    }
                    inputBuffer.clear();
                    inputBuffer.put(byteBuffer);
                    inputBuffer.flip();
                    this.i.queueInputBuffer(dequeueInputBuffer, 0, inputBuffer.remaining(), j2, 0);
                    CodecLog.d(t, "encode()->Encoder is fed a new frame.");
                    return;
                }
            }
        }
    }

    public String getEncoderType() {
        return this.i != null ? this.i.getName().toLowerCase().contains("google") ? "Software Encoder" : "Hardware Encoder" : "No Encoder";
    }

    public FrameQueue getFrameQueue() {
        return null;
    }

    public Surface getInputSurface() {
        return null;
    }

    public void lock() {
        if (this.q != null) {
            this.q.lock();
        }
    }

    public abstract void notifyNewFrameAvailable();

    public void pauseRecording() {
        this.e = true;
        CodecLog.d(t, "Log_mIsRequestPause_Vaule_pauseRecording ->mIsRequestPause=" + this.e);
    }

    public abstract void prepare(boolean z2);

    public void release(boolean z2) {
        if (this.i != null) {
            try {
                this.i.stop();
            } catch (Exception e2) {
                e2.printStackTrace();
                if (z2) {
                    if (this.o != null) {
                        this.o.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_STOP, 0);
                    }
                } else if (this.o != null) {
                    this.o.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_AUDIO_STOP, 0);
                }
            }
            try {
                this.i.release();
            } catch (Exception e3) {
                e3.printStackTrace();
                if (z2) {
                    if (this.o != null) {
                        this.o.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_RELEASE, 0);
                    }
                } else if (this.o != null) {
                    this.o.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_AUDIO_RELEASE, 0);
                }
            }
            this.i = null;
        }
        if (this.h != null) {
            this.h.stopMuxer();
            this.h = null;
        }
        this.f32b = false;
        this.e = false;
        this.d = false;
        this.f31a = true;
        this.f = null;
        this.q = null;
        this.r = null;
    }

    public void resumeRecording() {
        this.e = false;
        CodecLog.d(t, "Log_mIsRequestPause_Vaule_resumeRecording ->mIsRequestPause=" + this.e);
    }

    public void setFrameQueue(FrameQueue frameQueue) {
        this.s = frameQueue;
    }

    public void sinalCondition() {
        if (this.r != null) {
            this.r.signalAll();
        }
    }

    public void startRecording() {
        if (this.f32b) {
            CodecLog.i(t, "startRecording()-> encoder is started, you can not start it again");
            return;
        }
        this.f32b = true;
        this.d = false;
        this.f31a = false;
        CodecLog.d(t, "startRecording()-> encoder is started.");
    }

    public void stopRecording() {
        if (this.d) {
            CodecLog.i(t, "stopRecording()-> stop encoder request command is received,you can not send stop command again.");
        } else {
            this.d = true;
        }
    }

    public void unLock() {
        if (this.q != null) {
            this.q.unlock();
        }
    }
}
