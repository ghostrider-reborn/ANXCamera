package com.android.camera.module.encoder;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.ParcelFileDescriptor;
import com.android.camera.FileCompat;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.storage.Storage;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MediaMuxerWrapper {
    private static final String TAG = MediaMuxerWrapper.class.getSimpleName();
    private MediaEncoder mAudioEncoder;
    private int mEncoderCount;
    private boolean mIsStarted;
    private MediaMuxer mMediaMuxer;
    private int mStartedCount;
    private MediaEncoder mVideoEncoder;

    public MediaMuxerWrapper(String str) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor;
        Exception e;
        if (!Storage.isUseDocumentMode()) {
            this.mMediaMuxer = new MediaMuxer(str, 0);
        } else {
            try {
                parcelFileDescriptor = FileCompat.getParcelFileDescriptor(str, true);
                try {
                    this.mMediaMuxer = new MediaMuxer(parcelFileDescriptor.getFileDescriptor(), 0);
                } catch (Exception e2) {
                    e = e2;
                    try {
                        Log.w(TAG, "open file failed, path = " + str, e);
                        Util.closeSafely(parcelFileDescriptor);
                        this.mEncoderCount = 0;
                        this.mStartedCount = 0;
                        this.mIsStarted = false;
                    } catch (Throwable th) {
                        th = th;
                    }
                }
            } catch (Exception e3) {
                Exception exc = e3;
                parcelFileDescriptor = null;
                e = exc;
                Log.w(TAG, "open file failed, path = " + str, e);
                Util.closeSafely(parcelFileDescriptor);
                this.mEncoderCount = 0;
                this.mStartedCount = 0;
                this.mIsStarted = false;
            } catch (Throwable th2) {
                th = th2;
                parcelFileDescriptor = null;
                Util.closeSafely(parcelFileDescriptor);
                throw th;
            }
            Util.closeSafely(parcelFileDescriptor);
        }
        this.mEncoderCount = 0;
        this.mStartedCount = 0;
        this.mIsStarted = false;
    }

    /* access modifiers changed from: package-private */
    public void addEncoder(MediaEncoder mediaEncoder) {
        if (mediaEncoder instanceof MediaVideoEncoder) {
            if (this.mVideoEncoder == null) {
                this.mVideoEncoder = mediaEncoder;
            } else {
                throw new IllegalArgumentException("video encoder already added!");
            }
        } else if (!(mediaEncoder instanceof MediaAudioEncoder)) {
            throw new IllegalArgumentException("unsupported encoder!");
        } else if (this.mAudioEncoder == null) {
            this.mAudioEncoder = mediaEncoder;
        } else {
            throw new IllegalArgumentException("audio encoder already added!");
        }
        int i = 0;
        int i2 = this.mVideoEncoder != null ? 1 : 0;
        if (this.mAudioEncoder != null) {
            i = 1;
        }
        this.mEncoderCount = i2 + i;
    }

    /* access modifiers changed from: package-private */
    public synchronized int addTrack(MediaFormat mediaFormat) {
        int addTrack;
        if (!this.mIsStarted) {
            addTrack = this.mMediaMuxer.addTrack(mediaFormat);
            String str = TAG;
            Log.v(str, "addTrack: trackNum=" + this.mEncoderCount + " trackIndex=" + addTrack + " format=" + mediaFormat);
        } else {
            throw new IllegalStateException("muxer already started");
        }
        return addTrack;
    }

    public synchronized boolean isStarted() {
        return this.mIsStarted;
    }

    public void join() {
        Log.d(TAG, "join>>>");
        if (this.mAudioEncoder != null) {
            this.mAudioEncoder.join();
            this.mAudioEncoder = null;
        }
        if (this.mVideoEncoder != null) {
            this.mVideoEncoder.join();
            this.mVideoEncoder = null;
        }
        Log.d(TAG, "join<<<");
    }

    public void prepare() throws IOException {
        if (this.mVideoEncoder != null) {
            this.mVideoEncoder.prepare();
        }
        if (this.mAudioEncoder != null) {
            this.mAudioEncoder.prepare();
        }
    }

    public void setLocation(float f, float f2) {
        this.mMediaMuxer.setLocation(f, f2);
    }

    public void setOrientationHint(int i) {
        this.mMediaMuxer.setOrientationHint(i);
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean start() {
        String str = TAG;
        Log.d(str, "start: startedCount=" + this.mStartedCount);
        this.mStartedCount = this.mStartedCount + 1;
        if (this.mEncoderCount > 0 && this.mStartedCount == this.mEncoderCount) {
            this.mMediaMuxer.start();
            this.mIsStarted = true;
            notifyAll();
            Log.d(TAG, "MediaMuxer started");
        }
        return this.mIsStarted;
    }

    public boolean startRecording(long j) {
        String str = TAG;
        Log.d(str, "startRecording: offset=" + j);
        boolean z = this.mVideoEncoder == null || this.mVideoEncoder.startRecording(j);
        return this.mAudioEncoder != null ? this.mAudioEncoder.startRecording(j) && z : z;
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean stop() {
        boolean z;
        Log.d(TAG, "stop: startedCount=" + this.mStartedCount);
        z = true;
        this.mStartedCount = this.mStartedCount - 1;
        if (this.mEncoderCount > 0 && this.mStartedCount <= 0) {
            this.mMediaMuxer.stop();
            this.mMediaMuxer.release();
            this.mIsStarted = false;
            Log.v(TAG, "MediaMuxer stopped");
        }
        if (this.mStartedCount > 0) {
            z = false;
        }
        return z;
    }

    public void stopRecording() {
        Log.d(TAG, "stopRecording>>>");
        if (this.mAudioEncoder != null) {
            this.mAudioEncoder.stopRecording();
        }
        if (this.mVideoEncoder != null) {
            this.mVideoEncoder.stopRecording();
        }
        Log.d(TAG, "stopRecording<<<");
    }

    /* access modifiers changed from: package-private */
    public synchronized void writeSampleData(int i, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (this.mStartedCount > 0) {
            this.mMediaMuxer.writeSampleData(i, byteBuffer, bufferInfo);
        }
    }
}
