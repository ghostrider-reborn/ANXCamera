package com.xiaomi.camera.liveshot.writer;

import android.media.MediaCodec;
import android.media.MediaMuxer;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.xiaomi.camera.liveshot.LivePhotoResult;
import com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder;
import com.xiaomi.camera.liveshot.writer.SampleWriter;
import java.nio.ByteBuffer;

public class VideoSampleWriter extends SampleWriter {
    private static final boolean DEBUG = true;
    private static final long MIN_DURATION = 500000;
    private static final String TAG = "VideoSampleWriter";
    private final MediaMuxer mMediaMuxer;
    private final SampleWriter.StatusNotifier<Long> mVideoFirstKeyFrameArrivedNotifier;
    private final CircularMediaEncoder.Snapshot mVideoSnapshot;
    private final int mVideoTrackId;

    public VideoSampleWriter(MediaMuxer mediaMuxer, CircularMediaEncoder.Snapshot snapshot, int i, SampleWriter.StatusNotifier<Long> statusNotifier) {
        this.mMediaMuxer = mediaMuxer;
        this.mVideoSnapshot = snapshot;
        this.mVideoTrackId = i;
        this.mVideoFirstKeyFrameArrivedNotifier = statusNotifier;
    }

    /* access modifiers changed from: protected */
    public void writeSample() {
        long j;
        boolean z;
        boolean z2;
        ByteBuffer byteBuffer;
        Log.d(TAG, "writeVideoSamples: E");
        CircularMediaEncoder.Snapshot snapshot = this.mVideoSnapshot;
        long j2 = snapshot.head;
        long j3 = snapshot.tail;
        long j4 = snapshot.time;
        int i = snapshot.filterId;
        Log.d(TAG, "writeVideoSamples: head timestamp: " + this.mVideoSnapshot.head + ":" + j2);
        Log.d(TAG, "writeVideoSamples: snap timestamp: " + this.mVideoSnapshot.time + ":" + this.mVideoSnapshot.time);
        Log.d(TAG, "writeVideoSamples: tail timestamp: " + this.mVideoSnapshot.tail + ":" + j3);
        Log.d(TAG, "writeVideoSamples: cur filterId: " + this.mVideoSnapshot.filterId + ":" + i);
        long j5 = -1;
        boolean z3 = false;
        long j6 = 0;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        boolean z7 = false;
        while (true) {
            if (z3) {
                break;
            }
            Log.d(TAG, "writeVideoSamples: take: E");
            try {
                CircularMediaEncoder.Sample take = this.mVideoSnapshot.samples.take();
                Log.d(TAG, "writeVideoSamples: take: X");
                if (take == null) {
                    Log.e(TAG, "sample null return");
                    break;
                }
                ByteBuffer byteBuffer2 = take.data;
                MediaCodec.BufferInfo bufferInfo = take.info;
                LivePhotoResult livePhotoResult = take.livePhotoResult;
                z = z3;
                String str = TAG;
                ByteBuffer byteBuffer3 = byteBuffer2;
                StringBuilder sb = new StringBuilder();
                j = j3;
                sb.append("writeVideoSamples: livePhotoResult ");
                sb.append(livePhotoResult);
                Log.d(str, sb.toString());
                if ((bufferInfo.flags & 1) != 0 || z4) {
                    if (j4 - bufferInfo.presentationTimeUs >= MIN_DURATION && !z5) {
                        if (!z6) {
                            boolean isLivePhotoStable = Util.isLivePhotoStable(livePhotoResult, i);
                            if (!isLivePhotoStable) {
                                Log.d(TAG, "writeVideoSamples: drop not stable: " + bufferInfo.presentationTimeUs);
                                z3 = z;
                                j3 = j;
                            } else {
                                Log.d(TAG, "writeVideoSamples: drop first stable: " + bufferInfo.presentationTimeUs);
                                z6 = isLivePhotoStable;
                            }
                        } else if (!z5) {
                            z6 = Util.isLivePhotoStable(livePhotoResult, i);
                            if (!z6) {
                                Log.d(TAG, "writeVideoSamples: drop second stable: " + bufferInfo.presentationTimeUs);
                            } else {
                                Log.d(TAG, "writeVideoSamples: drop first and second stable: " + bufferInfo.presentationTimeUs);
                                z3 = z;
                                j3 = j;
                                z5 = true;
                                z6 = true;
                            }
                        }
                        z3 = z;
                        j3 = j;
                        z5 = false;
                    }
                    if (bufferInfo.presentationTimeUs - j4 > MIN_DURATION) {
                        z2 = true;
                        if (!Util.isLivePhotoStable(livePhotoResult, i)) {
                            Log.d(TAG, "writeVideoSamples: drop not stable 2: " + bufferInfo.presentationTimeUs);
                            z7 = true;
                        }
                    } else {
                        z2 = true;
                    }
                    long j7 = bufferInfo.presentationTimeUs;
                    if (j7 < j2 || j5 >= j7 - j6) {
                        byteBuffer = byteBuffer3;
                    } else {
                        if (!z4) {
                            CircularMediaEncoder.Snapshot snapshot2 = this.mVideoSnapshot;
                            snapshot2.offset = j7 - snapshot2.head;
                            SampleWriter.StatusNotifier<Long> statusNotifier = this.mVideoFirstKeyFrameArrivedNotifier;
                            if (statusNotifier != null) {
                                statusNotifier.notify(Long.valueOf(snapshot2.offset));
                            }
                            Log.d(TAG, "writeVideoSamples: first video sample timestamp: " + j7);
                            z4 = z2;
                        } else {
                            j7 = j6;
                        }
                        if (bufferInfo.presentationTimeUs >= j || z7) {
                            Log.d(TAG, "writeVideoSamples: stop writing as reaching the ending timestamp");
                            bufferInfo.flags = 4;
                        }
                        bufferInfo.presentationTimeUs -= j7;
                        ByteBuffer byteBuffer4 = byteBuffer3;
                        this.mMediaMuxer.writeSampleData(this.mVideoTrackId, byteBuffer4, bufferInfo);
                        long j8 = bufferInfo.presentationTimeUs;
                        String str2 = TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("writeVideoSamples: video sample timestamp: ");
                        long j9 = j8;
                        sb2.append(bufferInfo.presentationTimeUs + j7);
                        Log.d(str2, sb2.toString());
                        byteBuffer = byteBuffer4;
                        j6 = j7;
                        j5 = j9;
                    }
                    z3 = byteBuffer.limit() == 0 || (bufferInfo.flags & 4) != 0;
                    j3 = j;
                } else {
                    Log.d(TAG, "writeVideoSamples: drop non-key frame sample timestamp: " + bufferInfo.presentationTimeUs);
                    z3 = z;
                    j3 = j;
                }
            } catch (InterruptedException unused) {
                j = j3;
                z = z3;
                Log.d(TAG, "writeVideoSamples: take: meet interrupted exception");
            }
        }
        CircularMediaEncoder.Snapshot snapshot3 = this.mVideoSnapshot;
        snapshot3.time = Math.max(0, snapshot3.time - j6);
        Log.d(TAG, "writeVideoSamples: cover frame timestamp: " + this.mVideoSnapshot.time);
        Log.d(TAG, "writeVideoSamples: X: duration: " + j5);
        Log.d(TAG, "writeVideoSamples: X: offset: " + this.mVideoSnapshot.offset);
    }
}
