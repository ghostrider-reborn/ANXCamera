package com.xiaomi.camera.liveshot;

import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.opengl.EGLContext;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.android.camera.CameraSettings;
import com.android.camera.Util;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.log.Log;
import com.xiaomi.camera.liveshot.encoder.CircularAudioEncoder;
import com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder.Snapshot;
import com.xiaomi.camera.liveshot.encoder.CircularVideoEncoder;
import com.xiaomi.camera.liveshot.util.BackgroundTaskScheduler;
import com.xiaomi.camera.liveshot.util.BackgroundTaskScheduler.CancellableTask;
import com.xiaomi.camera.liveshot.writer.AudioSampleWriter;
import com.xiaomi.camera.liveshot.writer.SampleWriter.StatusNotifier;
import com.xiaomi.camera.liveshot.writer.VideoSampleWriter;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CircularMediaRecorder {
    private static final int AUDIO_BIT_RATE = 64000;
    private static final int AUDIO_CHANNELS = 1;
    private static final int AUDIO_FORMAT = 2;
    private static final String AUDIO_MIME_TYPE = "audio/mp4a-latm";
    private static final int AUDIO_SAMPLE_RATE = 44100;
    private static final int BYTES_COPY_BUFFER_LENGTH = 2048;
    private static final long CAPTURE_DURATION_IN_MICROSECOND = 2000000;
    private static final boolean DEBUG = false;
    private static final int MOVIE_FILE_FORMAT = 0;
    private static final long PRE_CAPTURE_DURATION_IN_MICROSECOND = 1000000;
    /* access modifiers changed from: private */
    public static final boolean SAVE_MICRO_VIDEO_IN_SDCARD = Util.saveLiveShotMicroVideoInSdcard();
    /* access modifiers changed from: private */
    public static final String TAG = "CircularMediaRecorder";
    private static final int VIDEO_BIT_RATE = 35000000;
    private static final int VIDEO_FRAME_RATE = 30;
    private static final float VIDEO_I_FRAME_INTERVAL = 0.1f;
    private final CircularAudioEncoder mCircularAudioEncoder;
    private final CircularVideoEncoder mCircularVideoEncoder;
    private int mOrientationHint = 0;
    private final BackgroundTaskScheduler mSnapshotRequestScheduler;

    private static final class SnapshotRequest extends CancellableTask {
        private final Snapshot mAudioSnapshot;
        private final int mOrientationHint;
        private final ExecutorService mSampleWriterExecutor;
        private final Object mTag;
        private final VideoClipSavingCallback mVideoClipSavingCallback;
        private final Snapshot mVideoSnapshot;

        private static /* synthetic */ void $closeResource(Throwable th, AutoCloseable autoCloseable) {
            if (th != null) {
                try {
                    autoCloseable.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            } else {
                autoCloseable.close();
            }
        }

        private SnapshotRequest(Snapshot snapshot, Snapshot snapshot2, int i, Object obj, VideoClipSavingCallback videoClipSavingCallback) {
            if (snapshot2 == null && snapshot == null) {
                throw new IllegalStateException("At least one non-null snapshot should be provided");
            }
            this.mAudioSnapshot = snapshot;
            this.mVideoSnapshot = snapshot2;
            this.mOrientationHint = i;
            this.mTag = obj;
            this.mVideoClipSavingCallback = videoClipSavingCallback;
            this.mSampleWriterExecutor = Executors.newFixedThreadPool(2);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:22:0x002c, code lost:
            r3 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
            $closeResource(r2, r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0030, code lost:
            throw r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0033, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
            $closeResource(r4, r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0037, code lost:
            throw r2;
         */
        private static byte[] readFully(String str) {
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[2048];
                while (true) {
                    int read = bufferedInputStream.read(bArr);
                    if (read < 0) {
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        $closeResource(null, byteArrayOutputStream);
                        $closeResource(null, bufferedInputStream);
                        return byteArray;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
            } catch (IOException e2) {
                String access$100 = CircularMediaRecorder.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to load the mp4 file content into memory: ");
                sb.append(e2);
                Log.d(access$100, sb.toString());
                return new byte[0];
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:100:0x0213  */
        /* JADX WARNING: Removed duplicated region for block: B:71:0x016d A[Catch:{ all -> 0x01d1 }] */
        /* JADX WARNING: Removed duplicated region for block: B:73:0x0176 A[SYNTHETIC, Splitter:B:73:0x0176] */
        /* JADX WARNING: Removed duplicated region for block: B:79:0x0196  */
        /* JADX WARNING: Removed duplicated region for block: B:81:0x01ad A[ADDED_TO_REGION] */
        /* JADX WARNING: Removed duplicated region for block: B:90:0x01d4 A[SYNTHETIC, Splitter:B:90:0x01d4] */
        /* JADX WARNING: Removed duplicated region for block: B:96:0x01f4 A[ADDED_TO_REGION] */
        public void run() {
            MediaMuxer mediaMuxer;
            File file;
            StringBuilder sb;
            String str;
            StringBuilder sb2;
            String str2;
            Throwable e2;
            String str3 = "Failed to delete the temporary mp4 file: ";
            String str4 = "Failed to release the media muxer: ";
            String str5 = "Ignore deleting the temporary mp4 file: ";
            if (isCancelled()) {
                Log.d(CircularMediaRecorder.TAG, "Saving request is cancelled before executing");
                this.mSampleWriterExecutor.shutdown();
                VideoClipSavingCallback videoClipSavingCallback = this.mVideoClipSavingCallback;
                if (videoClipSavingCallback != null) {
                    videoClipSavingCallback.onVideoClipSavingCancelled(this.mTag);
                }
                return;
            }
            StatusNotifier statusNotifier = null;
            try {
                file = CircularMediaRecorder.SAVE_MICRO_VIDEO_IN_SDCARD ? new File(Environment.getExternalStorageDirectory(), "microvideo.mp4") : File.createTempFile("microvideo", ".mp4");
                try {
                    mediaMuxer = new MediaMuxer(file.getPath(), 0);
                } catch (Exception e3) {
                    Throwable th = e3;
                    mediaMuxer = null;
                    e2 = th;
                    try {
                        String access$100 = CircularMediaRecorder.TAG;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Failed to save the videoclip as an mp4 file: ");
                        sb3.append(e2);
                        Log.d(access$100, sb3.toString());
                        if (this.mVideoClipSavingCallback != null) {
                        }
                        if (mediaMuxer != null) {
                        }
                        if (CircularMediaRecorder.SAVE_MICRO_VIDEO_IN_SDCARD) {
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (mediaMuxer != null) {
                            try {
                                mediaMuxer.release();
                            } catch (Exception unused) {
                                String access$1002 = CircularMediaRecorder.TAG;
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append(str4);
                                sb4.append(mediaMuxer);
                                Log.d(access$1002, sb4.toString());
                            }
                        }
                        if (CircularMediaRecorder.SAVE_MICRO_VIDEO_IN_SDCARD) {
                            String access$1003 = CircularMediaRecorder.TAG;
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(str5);
                            sb5.append(file);
                            Log.d(access$1003, sb5.toString());
                        } else if (file != null && !file.delete()) {
                            String access$1004 = CircularMediaRecorder.TAG;
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(str3);
                            sb6.append(file);
                            Log.d(access$1004, sb6.toString());
                        }
                        this.mSampleWriterExecutor.shutdown();
                        throw th;
                    }
                } catch (Throwable th3) {
                    Throwable th4 = th3;
                    mediaMuxer = null;
                    th = th4;
                    if (mediaMuxer != null) {
                    }
                    if (CircularMediaRecorder.SAVE_MICRO_VIDEO_IN_SDCARD) {
                    }
                    this.mSampleWriterExecutor.shutdown();
                    throw th;
                }
                try {
                    mediaMuxer.setOrientationHint(this.mOrientationHint);
                    int addTrack = this.mVideoSnapshot != null ? mediaMuxer.addTrack(this.mVideoSnapshot.format) : -1;
                    int addTrack2 = this.mAudioSnapshot != null ? mediaMuxer.addTrack(this.mAudioSnapshot.format) : -1;
                    mediaMuxer.start();
                    ArrayList<Future> arrayList = new ArrayList<>(2);
                    if (!(this.mVideoSnapshot == null || addTrack == -1)) {
                        statusNotifier = new StatusNotifier(Long.valueOf(0));
                        arrayList.add(this.mSampleWriterExecutor.submit(new VideoSampleWriter(mediaMuxer, this.mVideoSnapshot, addTrack, statusNotifier)));
                    }
                    if (!(this.mAudioSnapshot == null || addTrack2 == -1)) {
                        arrayList.add(this.mSampleWriterExecutor.submit(new AudioSampleWriter(mediaMuxer, this.mAudioSnapshot, addTrack2, statusNotifier)));
                    }
                    for (Future future : arrayList) {
                        if (future != null) {
                            try {
                                future.get();
                            } catch (InterruptedException e4) {
                                String access$1005 = CircularMediaRecorder.TAG;
                                StringBuilder sb7 = new StringBuilder();
                                sb7.append("Writing is interrupted and the generated video may be corrupted: ");
                                sb7.append(e4);
                                Log.d(access$1005, sb7.toString());
                            }
                        }
                    }
                    mediaMuxer.stop();
                    if (this.mVideoClipSavingCallback != null) {
                        this.mVideoClipSavingCallback.onVideoClipSavingCompleted(this.mTag, readFully(file.getPath()), this.mVideoSnapshot == null ? -1 : this.mVideoSnapshot.time);
                    }
                    try {
                        mediaMuxer.release();
                    } catch (Exception unused2) {
                        String access$1006 = CircularMediaRecorder.TAG;
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append(str4);
                        sb8.append(mediaMuxer);
                        Log.d(access$1006, sb8.toString());
                    }
                    if (CircularMediaRecorder.SAVE_MICRO_VIDEO_IN_SDCARD) {
                        str2 = CircularMediaRecorder.TAG;
                        sb2 = new StringBuilder();
                        sb2.append(str5);
                        sb2.append(file);
                        Log.d(str2, sb2.toString());
                        this.mSampleWriterExecutor.shutdown();
                    }
                    if (file != null && !file.delete()) {
                        str = CircularMediaRecorder.TAG;
                        sb = new StringBuilder();
                        sb.append(str3);
                        sb.append(file);
                        Log.d(str, sb.toString());
                    }
                    this.mSampleWriterExecutor.shutdown();
                } catch (Exception e5) {
                    e2 = e5;
                    String access$1007 = CircularMediaRecorder.TAG;
                    StringBuilder sb32 = new StringBuilder();
                    sb32.append("Failed to save the videoclip as an mp4 file: ");
                    sb32.append(e2);
                    Log.d(access$1007, sb32.toString());
                    if (this.mVideoClipSavingCallback != null) {
                    }
                    if (mediaMuxer != null) {
                    }
                    if (CircularMediaRecorder.SAVE_MICRO_VIDEO_IN_SDCARD) {
                    }
                }
            } catch (Exception e6) {
                mediaMuxer = null;
                e2 = e6;
                file = null;
                String access$10072 = CircularMediaRecorder.TAG;
                StringBuilder sb322 = new StringBuilder();
                sb322.append("Failed to save the videoclip as an mp4 file: ");
                sb322.append(e2);
                Log.d(access$10072, sb322.toString());
                if (this.mVideoClipSavingCallback != null) {
                    this.mVideoClipSavingCallback.onVideoClipSavingException(this.mTag, e2);
                }
                if (mediaMuxer != null) {
                    try {
                        mediaMuxer.release();
                    } catch (Exception unused3) {
                        String access$1008 = CircularMediaRecorder.TAG;
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append(str4);
                        sb9.append(mediaMuxer);
                        Log.d(access$1008, sb9.toString());
                    }
                }
                if (CircularMediaRecorder.SAVE_MICRO_VIDEO_IN_SDCARD) {
                    str2 = CircularMediaRecorder.TAG;
                    sb2 = new StringBuilder();
                    sb2.append(str5);
                    sb2.append(file);
                    Log.d(str2, sb2.toString());
                    this.mSampleWriterExecutor.shutdown();
                }
                if (file != null && !file.delete()) {
                    str = CircularMediaRecorder.TAG;
                    sb = new StringBuilder();
                    sb.append(str3);
                    sb.append(file);
                    Log.d(str, sb.toString());
                }
                this.mSampleWriterExecutor.shutdown();
            } catch (Throwable th5) {
                mediaMuxer = null;
                th = th5;
                file = null;
                if (mediaMuxer != null) {
                }
                if (CircularMediaRecorder.SAVE_MICRO_VIDEO_IN_SDCARD) {
                }
                this.mSampleWriterExecutor.shutdown();
                throw th;
            }
        }
    }

    public interface VideoClipSavingCallback {
        @WorkerThread
        void onVideoClipSavingCancelled(@Nullable Object obj);

        @WorkerThread
        void onVideoClipSavingCompleted(@Nullable Object obj, @NonNull byte[] bArr, long j);

        @WorkerThread
        void onVideoClipSavingException(@Nullable Object obj, @NonNull Throwable th);
    }

    public CircularMediaRecorder(int i, int i2, EGLContext eGLContext, boolean z, Queue<LivePhotoResult> queue) {
        CircularVideoEncoder circularVideoEncoder = new CircularVideoEncoder(createVideoFormat(i, i2), eGLContext, CAPTURE_DURATION_IN_MICROSECOND, PRE_CAPTURE_DURATION_IN_MICROSECOND, queue);
        this.mCircularVideoEncoder = circularVideoEncoder;
        if (z) {
            CircularAudioEncoder circularAudioEncoder = new CircularAudioEncoder(createAudioFormat(AUDIO_SAMPLE_RATE, 1), CAPTURE_DURATION_IN_MICROSECOND, PRE_CAPTURE_DURATION_IN_MICROSECOND, null);
            this.mCircularAudioEncoder = circularAudioEncoder;
        } else {
            this.mCircularAudioEncoder = null;
        }
        this.mSnapshotRequestScheduler = new BackgroundTaskScheduler(Executors.newSingleThreadExecutor());
    }

    private static MediaFormat createAudioFormat(int i, int i2) {
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat(AUDIO_MIME_TYPE, i, i2);
        createAudioFormat.setInteger("aac-profile", 2);
        createAudioFormat.setInteger("bitrate", AUDIO_BIT_RATE);
        createAudioFormat.setInteger("channel-count", i2);
        createAudioFormat.setInteger("pcm-encoding", 2);
        return createAudioFormat;
    }

    private static MediaFormat createVideoFormat(int i, int i2) {
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat(isH265EncodingPreferred() ? "video/hevc" : "video/avc", i, i2);
        createVideoFormat.setInteger("color-format", 2130708361);
        createVideoFormat.setInteger("bitrate", VIDEO_BIT_RATE);
        createVideoFormat.setInteger("frame-rate", 30);
        createVideoFormat.setFloat("i-frame-interval", 0.1f);
        return createVideoFormat;
    }

    private static boolean isH265EncodingPreferred() {
        return CameraSettings.getVideoEncoder() == 5 && MediaCodecCapability.isH265EncodingSupported();
    }

    public void onSurfaceTextureUpdated(DrawExtTexAttribute drawExtTexAttribute) {
        CircularVideoEncoder circularVideoEncoder = this.mCircularVideoEncoder;
        if (circularVideoEncoder != null) {
            circularVideoEncoder.onSurfaceTextureUpdated(drawExtTexAttribute);
        }
    }

    public void release() {
        Log.d(TAG, "release(): E");
        this.mSnapshotRequestScheduler.shutdown();
        CircularVideoEncoder circularVideoEncoder = this.mCircularVideoEncoder;
        if (circularVideoEncoder != null) {
            circularVideoEncoder.release();
        }
        CircularAudioEncoder circularAudioEncoder = this.mCircularAudioEncoder;
        if (circularAudioEncoder != null) {
            circularAudioEncoder.release();
        }
        Log.d(TAG, "release(): X");
    }

    public void setFilterId(int i) {
        CircularVideoEncoder circularVideoEncoder = this.mCircularVideoEncoder;
        if (circularVideoEncoder != null) {
            circularVideoEncoder.setFilterId(i);
        }
    }

    public void setFpsReduction(float f2) {
        CircularVideoEncoder circularVideoEncoder = this.mCircularVideoEncoder;
        if (circularVideoEncoder != null) {
            circularVideoEncoder.setFpsReduction(f2);
        }
    }

    public void setOrientationHint(int i) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("setOrientationHint(): ");
        sb.append(i);
        Log.d(str, sb.toString());
        this.mOrientationHint = i;
    }

    public void snapshot(int i, VideoClipSavingCallback videoClipSavingCallback, Object obj, int i2) {
        CircularAudioEncoder circularAudioEncoder = this.mCircularAudioEncoder;
        Snapshot snapshot = circularAudioEncoder == null ? null : circularAudioEncoder.snapshot(i2);
        CircularVideoEncoder circularVideoEncoder = this.mCircularVideoEncoder;
        Snapshot snapshot2 = circularVideoEncoder == null ? null : circularVideoEncoder.snapshot(i2);
        if (i == -1) {
            i = this.mOrientationHint;
        }
        SnapshotRequest snapshotRequest = new SnapshotRequest(snapshot, snapshot2, i, obj, videoClipSavingCallback);
        this.mSnapshotRequestScheduler.execute(snapshotRequest);
    }

    public void start() {
        Log.d(TAG, "start(): E");
        CircularVideoEncoder circularVideoEncoder = this.mCircularVideoEncoder;
        if (circularVideoEncoder != null) {
            circularVideoEncoder.start();
        }
        CircularAudioEncoder circularAudioEncoder = this.mCircularAudioEncoder;
        if (circularAudioEncoder != null) {
            circularAudioEncoder.start();
        }
        Log.d(TAG, "start(): X");
    }

    public void stop() {
        Log.d(TAG, "stop(): E");
        this.mSnapshotRequestScheduler.abortRemainingTasks();
        CircularVideoEncoder circularVideoEncoder = this.mCircularVideoEncoder;
        if (circularVideoEncoder != null) {
            circularVideoEncoder.stop();
        }
        CircularAudioEncoder circularAudioEncoder = this.mCircularAudioEncoder;
        if (circularAudioEncoder != null) {
            circularAudioEncoder.stop();
        }
        Log.d(TAG, "stop(): X");
    }
}
