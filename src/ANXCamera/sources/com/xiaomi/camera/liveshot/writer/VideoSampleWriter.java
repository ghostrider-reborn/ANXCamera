package com.xiaomi.camera.liveshot.writer;

import android.media.MediaCodec.BufferInfo;
import android.media.MediaMuxer;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.xiaomi.camera.liveshot.LivePhotoResult;
import com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder.Sample;
import com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder.Snapshot;
import com.xiaomi.camera.liveshot.writer.SampleWriter.StatusNotifier;
import java.nio.ByteBuffer;

public class VideoSampleWriter extends SampleWriter {
    private static final boolean DEBUG = true;
    private static final long MIN_DURATION = 500000;
    private static final String TAG = "VideoSampleWriter";
    private final MediaMuxer mMediaMuxer;
    private final StatusNotifier<Long> mVideoFirstKeyFrameArrivedNotifier;
    private final Snapshot mVideoSnapshot;
    private final int mVideoTrackId;

    public VideoSampleWriter(MediaMuxer mediaMuxer, Snapshot snapshot, int i, StatusNotifier<Long> statusNotifier) {
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
        Snapshot snapshot = this.mVideoSnapshot;
        long j2 = snapshot.head;
        long j3 = snapshot.tail;
        long j4 = snapshot.time;
        int i = snapshot.filterId;
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("writeVideoSamples: head timestamp: ");
        sb.append(this.mVideoSnapshot.head);
        String str2 = ":";
        sb.append(str2);
        sb.append(j2);
        Log.d(str, sb.toString());
        String str3 = TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("writeVideoSamples: snap timestamp: ");
        sb2.append(this.mVideoSnapshot.time);
        sb2.append(str2);
        sb2.append(this.mVideoSnapshot.time);
        Log.d(str3, sb2.toString());
        String str4 = TAG;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("writeVideoSamples: tail timestamp: ");
        sb3.append(this.mVideoSnapshot.tail);
        sb3.append(str2);
        sb3.append(j3);
        Log.d(str4, sb3.toString());
        String str5 = TAG;
        StringBuilder sb4 = new StringBuilder();
        sb4.append("writeVideoSamples: cur filterId: ");
        sb4.append(this.mVideoSnapshot.filterId);
        sb4.append(str2);
        sb4.append(i);
        Log.d(str5, sb4.toString());
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
                Sample sample = (Sample) this.mVideoSnapshot.samples.take();
                Log.d(TAG, "writeVideoSamples: take: X");
                if (sample == null) {
                    Log.e(TAG, "sample null return");
                    break;
                }
                ByteBuffer byteBuffer2 = sample.data;
                BufferInfo bufferInfo = sample.info;
                LivePhotoResult livePhotoResult = sample.livePhotoResult;
                z = z3;
                String str6 = TAG;
                ByteBuffer byteBuffer3 = byteBuffer2;
                StringBuilder sb5 = new StringBuilder();
                j = j3;
                sb5.append("writeVideoSamples: livePhotoResult ");
                sb5.append(livePhotoResult);
                Log.d(str6, sb5.toString());
                if ((bufferInfo.flags & 1) != 0 || z4) {
                    if (j4 - bufferInfo.presentationTimeUs >= MIN_DURATION && !z5) {
                        if (!z6) {
                            boolean isLivePhotoStable = Util.isLivePhotoStable(livePhotoResult, i);
                            if (!isLivePhotoStable) {
                                String str7 = TAG;
                                StringBuilder sb6 = new StringBuilder();
                                sb6.append("writeVideoSamples: drop not stable: ");
                                sb6.append(bufferInfo.presentationTimeUs);
                                Log.d(str7, sb6.toString());
                                z3 = z;
                                j3 = j;
                            } else {
                                String str8 = TAG;
                                StringBuilder sb7 = new StringBuilder();
                                sb7.append("writeVideoSamples: drop first stable: ");
                                sb7.append(bufferInfo.presentationTimeUs);
                                Log.d(str8, sb7.toString());
                                z6 = isLivePhotoStable;
                            }
                        } else if (!z5) {
                            z6 = Util.isLivePhotoStable(livePhotoResult, i);
                            if (!z6) {
                                String str9 = TAG;
                                StringBuilder sb8 = new StringBuilder();
                                sb8.append("writeVideoSamples: drop second stable: ");
                                sb8.append(bufferInfo.presentationTimeUs);
                                Log.d(str9, sb8.toString());
                            } else {
                                String str10 = TAG;
                                StringBuilder sb9 = new StringBuilder();
                                sb9.append("writeVideoSamples: drop first and second stable: ");
                                sb9.append(bufferInfo.presentationTimeUs);
                                Log.d(str10, sb9.toString());
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
                            String str11 = TAG;
                            StringBuilder sb10 = new StringBuilder();
                            sb10.append("writeVideoSamples: drop not stable 2: ");
                            sb10.append(bufferInfo.presentationTimeUs);
                            Log.d(str11, sb10.toString());
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
                            Snapshot snapshot2 = this.mVideoSnapshot;
                            snapshot2.offset = j7 - snapshot2.head;
                            StatusNotifier<Long> statusNotifier = this.mVideoFirstKeyFrameArrivedNotifier;
                            if (statusNotifier != null) {
                                statusNotifier.notify(Long.valueOf(snapshot2.offset));
                            }
                            String str12 = TAG;
                            StringBuilder sb11 = new StringBuilder();
                            sb11.append("writeVideoSamples: first video sample timestamp: ");
                            sb11.append(j7);
                            Log.d(str12, sb11.toString());
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
                        String str13 = TAG;
                        StringBuilder sb12 = new StringBuilder();
                        sb12.append("writeVideoSamples: video sample timestamp: ");
                        long j9 = j8;
                        sb12.append(bufferInfo.presentationTimeUs + j7);
                        Log.d(str13, sb12.toString());
                        byteBuffer = byteBuffer4;
                        j6 = j7;
                        j5 = j9;
                    }
                    z3 = byteBuffer.limit() == 0 || (bufferInfo.flags & 4) != 0;
                    j3 = j;
                } else {
                    String str14 = TAG;
                    StringBuilder sb13 = new StringBuilder();
                    sb13.append("writeVideoSamples: drop non-key frame sample timestamp: ");
                    sb13.append(bufferInfo.presentationTimeUs);
                    Log.d(str14, sb13.toString());
                    z3 = z;
                    j3 = j;
                }
            } catch (InterruptedException unused) {
                j = j3;
                z = z3;
                Log.d(TAG, "writeVideoSamples: take: meet interrupted exception");
            }
        }
        Snapshot snapshot3 = this.mVideoSnapshot;
        snapshot3.time = Math.max(0, snapshot3.time - j6);
        String str15 = TAG;
        StringBuilder sb14 = new StringBuilder();
        sb14.append("writeVideoSamples: cover frame timestamp: ");
        sb14.append(this.mVideoSnapshot.time);
        Log.d(str15, sb14.toString());
        String str16 = TAG;
        StringBuilder sb15 = new StringBuilder();
        sb15.append("writeVideoSamples: X: duration: ");
        sb15.append(j5);
        Log.d(str16, sb15.toString());
        String str17 = TAG;
        StringBuilder sb16 = new StringBuilder();
        sb16.append("writeVideoSamples: X: offset: ");
        sb16.append(this.mVideoSnapshot.offset);
        Log.d(str17, sb16.toString());
    }
}
