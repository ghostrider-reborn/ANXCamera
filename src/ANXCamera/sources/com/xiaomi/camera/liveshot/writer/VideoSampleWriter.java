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
    private static final String TAG = VideoSampleWriter.class.getSimpleName();
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
        long j2;
        boolean z;
        long j3;
        boolean z2;
        ByteBuffer byteBuffer;
        Log.d(TAG, "writeVideoSamples: E");
        long j4 = this.mVideoSnapshot.head;
        long j5 = this.mVideoSnapshot.tail;
        long j6 = this.mVideoSnapshot.time;
        int i = this.mVideoSnapshot.filterId;
        Log.d(TAG, "writeVideoSamples: head timestamp: " + this.mVideoSnapshot.head + ":" + j4);
        Log.d(TAG, "writeVideoSamples: snap timestamp: " + this.mVideoSnapshot.time + ":" + this.mVideoSnapshot.time);
        Log.d(TAG, "writeVideoSamples: tail timestamp: " + this.mVideoSnapshot.tail + ":" + j5);
        Log.d(TAG, "writeVideoSamples: cur filterId: " + this.mVideoSnapshot.filterId + ":" + i);
        long j7 = -1;
        boolean z3 = false;
        long j8 = 0;
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
                j2 = j5;
                sb.append("writeVideoSamples: livePhotoResult ");
                sb.append(livePhotoResult);
                Log.d(str, sb.toString());
                if ((bufferInfo.flags & 1) != 0 || z4) {
                    if (j6 - bufferInfo.presentationTimeUs >= MIN_DURATION && !z5) {
                        if (!z6) {
                            boolean isLivePhotoStable = Util.isLivePhotoStable(livePhotoResult, i);
                            if (!isLivePhotoStable) {
                                Log.d(TAG, "writeVideoSamples: drop not stable: " + bufferInfo.presentationTimeUs);
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
                                j5 = j2;
                                z5 = true;
                                z6 = true;
                            }
                        }
                        z3 = z;
                        j5 = j2;
                        z5 = false;
                    }
                    if (bufferInfo.presentationTimeUs - j6 > MIN_DURATION) {
                        z2 = true;
                        if (!Util.isLivePhotoStable(livePhotoResult, i)) {
                            String str2 = TAG;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("writeVideoSamples: drop not stable 2: ");
                            j = j6;
                            sb2.append(bufferInfo.presentationTimeUs);
                            Log.d(str2, sb2.toString());
                            z7 = true;
                        } else {
                            j = j6;
                        }
                    } else {
                        j = j6;
                        z2 = true;
                    }
                    if (bufferInfo.presentationTimeUs < j4 || j7 >= bufferInfo.presentationTimeUs - j8) {
                        byteBuffer = byteBuffer3;
                    } else {
                        if (!z4) {
                            j8 = bufferInfo.presentationTimeUs;
                            this.mVideoSnapshot.offset = j8 - this.mVideoSnapshot.head;
                            if (this.mVideoFirstKeyFrameArrivedNotifier != null) {
                                this.mVideoFirstKeyFrameArrivedNotifier.notify(Long.valueOf(this.mVideoSnapshot.offset));
                            }
                            Log.d(TAG, "writeVideoSamples: first video sample timestamp: " + j8);
                            z4 = z2;
                        }
                        if (bufferInfo.presentationTimeUs >= j2 || z7) {
                            Log.d(TAG, "writeVideoSamples: stop writing as reaching the ending timestamp");
                            bufferInfo.flags = 4;
                        }
                        bufferInfo.presentationTimeUs -= j8;
                        byteBuffer = byteBuffer3;
                        this.mMediaMuxer.writeSampleData(this.mVideoTrackId, byteBuffer, bufferInfo);
                        j7 = bufferInfo.presentationTimeUs;
                        Log.d(TAG, "writeVideoSamples: video sample timestamp: " + (bufferInfo.presentationTimeUs + j8));
                    }
                    z3 = byteBuffer.limit() == 0 || (bufferInfo.flags & 4) != 0;
                    j5 = j2;
                    j6 = j;
                } else {
                    Log.d(TAG, "writeVideoSamples: drop non-key frame sample timestamp: " + bufferInfo.presentationTimeUs);
                }
                j3 = j6;
                z3 = z;
                j5 = j2;
                j6 = j;
            } catch (InterruptedException e) {
                j2 = j5;
                j3 = j6;
                z = z3;
                Log.d(TAG, "writeVideoSamples: take: meet interrupted exception");
            }
        }
        this.mVideoSnapshot.time = Math.max(0, this.mVideoSnapshot.time - j8);
        Log.d(TAG, "writeVideoSamples: cover frame timestamp: " + this.mVideoSnapshot.time);
        Log.d(TAG, "writeVideoSamples: X: duration: " + j7);
        Log.d(TAG, "writeVideoSamples: X: offset: " + this.mVideoSnapshot.offset);
    }
}
