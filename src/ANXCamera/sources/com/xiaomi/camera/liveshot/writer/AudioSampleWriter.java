package com.xiaomi.camera.liveshot.writer;

import android.media.MediaCodec;
import android.media.MediaMuxer;
import com.android.camera.log.Log;
import com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder;
import com.xiaomi.camera.liveshot.writer.SampleWriter;
import java.nio.ByteBuffer;

public class AudioSampleWriter extends SampleWriter {
    private static final boolean DEBUG = true;
    private static final String TAG = AudioSampleWriter.class.getSimpleName();
    private final CircularMediaEncoder.Snapshot mAudioSnapshot;
    private final int mAudioTrackId;
    private final MediaMuxer mMediaMuxer;
    private final SampleWriter.StatusNotifier<Long> mVideoFirstKeyFrameArrivedNotifier;

    public AudioSampleWriter(MediaMuxer mediaMuxer, CircularMediaEncoder.Snapshot snapshot, int i, SampleWriter.StatusNotifier<Long> statusNotifier) {
        this.mMediaMuxer = mediaMuxer;
        this.mAudioSnapshot = snapshot;
        this.mAudioTrackId = i;
        this.mVideoFirstKeyFrameArrivedNotifier = statusNotifier;
    }

    /* access modifiers changed from: protected */
    public void writeSample() {
        long j;
        Log.d(TAG, "writeAudioSamples: E");
        long longValue = this.mVideoFirstKeyFrameArrivedNotifier != null ? this.mVideoFirstKeyFrameArrivedNotifier.getStatus().longValue() : 0;
        long j2 = this.mAudioSnapshot.head;
        if (longValue < 0) {
            longValue = 0;
        }
        long j3 = j2 + longValue;
        long j4 = this.mAudioSnapshot.tail;
        Log.d(TAG, "writeAudioSamples: head timestamp: " + this.mAudioSnapshot.head + ":" + j3);
        Log.d(TAG, "writeAudioSamples: tail timestamp: " + this.mAudioSnapshot.tail + ":" + j4);
        long j5 = -1;
        long j6 = 0;
        boolean z = false;
        boolean z2 = false;
        while (!z) {
            Log.d(TAG, "writeAudioSamples: take: E");
            try {
                CircularMediaEncoder.Sample take = this.mAudioSnapshot.samples.take();
                Log.d(TAG, "writeAudioSamples: take: X");
                ByteBuffer byteBuffer = take.data;
                MediaCodec.BufferInfo bufferInfo = take.info;
                if (bufferInfo.presentationTimeUs < j3 || j5 >= bufferInfo.presentationTimeUs - j6) {
                    j = j4;
                } else {
                    if (!z2) {
                        j6 = bufferInfo.presentationTimeUs;
                        this.mAudioSnapshot.offset = j6 - this.mAudioSnapshot.head;
                        Log.d(TAG, "writeAudioSamples: first audio sample timestamp: " + j6);
                        z2 = true;
                    }
                    if (bufferInfo.presentationTimeUs >= j4) {
                        Log.d(TAG, "writeAudioSamples: stop writing as reaching the ending timestamp");
                        bufferInfo.flags = 4;
                    }
                    bufferInfo.presentationTimeUs -= j6;
                    this.mMediaMuxer.writeSampleData(this.mAudioTrackId, byteBuffer, bufferInfo);
                    j5 = bufferInfo.presentationTimeUs;
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("writeAudioSamples: audio sample timestamp: ");
                    j = j4;
                    sb.append(bufferInfo.presentationTimeUs + j6);
                    Log.d(str, sb.toString());
                    z2 = z2;
                }
                if (byteBuffer.limit() == 0 || (bufferInfo.flags & 4) != 0) {
                    z = true;
                    j4 = j;
                } else {
                    z = false;
                    j4 = j;
                }
            } catch (InterruptedException e) {
                j = j4;
                Log.d(TAG, "writeAudioSamples: take: meet interrupted exception");
            }
        }
        Log.d(TAG, "writeAudioSamples: X: duration: " + j5);
        Log.d(TAG, "writeAudioSamples: X: offset: " + this.mAudioSnapshot.offset);
    }
}
