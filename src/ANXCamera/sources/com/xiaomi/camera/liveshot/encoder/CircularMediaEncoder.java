package com.xiaomi.camera.liveshot.encoder;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.android.camera.effect.FilterInfo;
import com.android.camera.log.Log;
import com.ss.android.vesdk.VEEditor;
import com.xiaomi.camera.liveshot.LivePhotoResult;
import com.xiaomi.camera.liveshot.MediaCodecCapability;
import com.xiaomi.camera.liveshot.util.BackgroundWorker;
import com.xiaomi.camera.liveshot.util.HandlerHelper;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public abstract class CircularMediaEncoder extends MediaCodec.Callback {
    private static final boolean DEBUG = true;
    private static final int MSG_RELEASE_ENCODER = 2;
    private static final int MSG_START_ENCODING = 0;
    private static final int MSG_STOP_ENCODING = 1;
    /* access modifiers changed from: private */
    public static final String TAG = CircularMediaEncoder.class.getSimpleName();
    protected final MediaCodec.BufferInfo mBufferInfo;
    protected final long mBufferingDurationUs;
    protected final long mCaptureDurationUs;
    protected volatile long mCurrentPresentationTimeUs;
    protected CyclicBuffer mCyclicBuffer;
    protected final MediaFormat mDesiredMediaFormat;
    protected final BackgroundWorker mEncodingThread;
    protected final EventHandler mEventHandler;
    protected final HandlerHelper mHandlerHelper;
    protected volatile boolean mIsBuffering;
    protected volatile boolean mIsInitialized = false;
    private Queue<LivePhotoResult> mLivePhotoQueue;
    protected MediaCodec mMediaCodec;
    private volatile boolean mOutputFormatChanged;
    protected final long mPostCaptureDurationUs;
    protected final long mPreCaptureDurationUs;
    protected final List<Snapshot> mSnapshots;

    protected static final class CyclicBuffer {
        private static final boolean DEBUG = true;
        private static final String TAG = CyclicBuffer.class.getSimpleName();
        private final byte[] mDataBuffer;
        private final LivePhotoResult[] mLivePhotoResults;
        private int mMetaHead;
        private int mMetaTail;
        private final int[] mPacketFlags;
        private final int[] mPacketLength;
        private final long[] mPacketPtsUs;
        private final int[] mPacketStart;

        public CyclicBuffer(MediaFormat mediaFormat, long j) {
            if (mediaFormat != null) {
                String string = mediaFormat.getString("mime");
                if (string != null) {
                    int integer = mediaFormat.getInteger("bitrate");
                    int i = (int) ((((long) integer) * j) / 8000);
                    this.mDataBuffer = new byte[i];
                    int integer2 = (int) ((((double) i) / ((((double) integer) / (string.contains(VEEditor.MVConsts.TYPE_VIDEO) ? (double) mediaFormat.getInteger("frame-rate") : ((double) mediaFormat.getInteger("sample-rate")) / 1024.0d)) / 8.0d)) + 1.0d);
                    int i2 = integer2 * 2;
                    this.mPacketFlags = new int[i2];
                    this.mPacketPtsUs = new long[i2];
                    this.mPacketStart = new int[i2];
                    this.mPacketLength = new int[i2];
                    this.mLivePhotoResults = new LivePhotoResult[i2];
                    String str = TAG;
                    Log.d(str, "DesiredSpan = " + j + ", dataBufferSize = " + i + ", metaBufferCount = " + i2 + ", estimatedPacketCount = " + integer2);
                    return;
                }
                throw new IllegalArgumentException("The desired mimeType is not specified");
            }
            throw new IllegalArgumentException("The desired MediaFormat must not be null");
        }

        private boolean canAdd(int i) {
            int length = this.mDataBuffer.length;
            int length2 = this.mPacketStart.length;
            if (i > length) {
                throw new RuntimeException("Enormous packet: " + i + " vs. buffer " + length);
            } else if (this.mMetaHead == this.mMetaTail) {
                return true;
            } else {
                if ((this.mMetaHead + 1) % length2 == this.mMetaTail) {
                    String str = TAG;
                    Log.v(str, "Ran out of metadata (head=" + this.mMetaHead + " tail=" + this.mMetaTail + ")");
                    return false;
                }
                int headStart = getHeadStart();
                int i2 = this.mPacketStart[this.mMetaTail];
                int i3 = ((i2 + length) - headStart) % length;
                if (i > i3) {
                    String str2 = TAG;
                    Log.v(str2, "Ran out of data (tailStart=" + i2 + " headStart=" + headStart + " req=" + i + " free=" + i3 + ")");
                    return false;
                }
                String str3 = TAG;
                Log.v(str3, "Okay: size=" + i + " free=" + i3 + " metaFree=" + ((((this.mMetaTail + length2) - this.mMetaHead) % length2) - 1));
                return true;
            }
        }

        private int getHeadStart() {
            if (this.mMetaHead == this.mMetaTail) {
                return 0;
            }
            int length = this.mDataBuffer.length;
            int length2 = this.mPacketStart.length;
            int i = ((this.mMetaHead + length2) - 1) % length2;
            return ((this.mPacketStart[i] + this.mPacketLength[i]) + 1) % length;
        }

        private void removeTail() {
            if (this.mMetaHead != this.mMetaTail) {
                this.mMetaTail = (this.mMetaTail + 1) % this.mPacketStart.length;
                return;
            }
            throw new RuntimeException("Can't removeTail() in empty buffer");
        }

        public void add(ByteBuffer byteBuffer, int i, long j, LivePhotoResult livePhotoResult) {
            int limit = byteBuffer.limit() - byteBuffer.position();
            String str = TAG;
            Log.d(str, "Add size=" + limit + " flags=0x" + Integer.toHexString(i) + " pts=" + j);
            while (!canAdd(limit)) {
                Log.d(TAG, "Cached buffer removed from tail");
                removeTail();
            }
            int length = this.mDataBuffer.length;
            int length2 = this.mPacketStart.length;
            int headStart = getHeadStart();
            this.mPacketFlags[this.mMetaHead] = i;
            this.mPacketPtsUs[this.mMetaHead] = j;
            this.mPacketStart[this.mMetaHead] = headStart;
            this.mPacketLength[this.mMetaHead] = limit;
            this.mLivePhotoResults[this.mMetaHead] = livePhotoResult;
            if (headStart + limit < length) {
                byteBuffer.get(this.mDataBuffer, headStart, limit);
            } else {
                int i2 = length - headStart;
                String str2 = TAG;
                Log.v(str2, "Split, firstsize=" + i2 + " size=" + limit);
                byteBuffer.get(this.mDataBuffer, headStart, i2);
                byteBuffer.get(this.mDataBuffer, 0, limit - i2);
            }
            this.mMetaHead = (this.mMetaHead + 1) % length2;
        }

        public void clear() {
            Arrays.fill(this.mDataBuffer, (byte) 0);
            Arrays.fill(this.mPacketFlags, 0);
            Arrays.fill(this.mPacketPtsUs, 0);
            Arrays.fill(this.mPacketStart, 0);
            Arrays.fill(this.mPacketLength, 0);
            Arrays.fill(this.mLivePhotoResults, (Object) null);
            this.mMetaHead = 0;
            this.mMetaTail = 0;
        }

        public long computeTimeSpanUsec() {
            int length = this.mPacketStart.length;
            if (this.mMetaHead == this.mMetaTail) {
                return 0;
            }
            return this.mPacketPtsUs[((this.mMetaHead + length) - 1) % length] - this.mPacketPtsUs[this.mMetaTail];
        }

        public ByteBuffer getChunk(int i, MediaCodec.BufferInfo bufferInfo) {
            int length = this.mDataBuffer.length;
            int i2 = this.mPacketStart[i];
            int i3 = this.mPacketLength[i];
            bufferInfo.flags = this.mPacketFlags[i];
            bufferInfo.offset = i2;
            bufferInfo.presentationTimeUs = this.mPacketPtsUs[i];
            bufferInfo.size = i3;
            if (i2 + i3 <= length) {
                ByteBuffer allocateDirect = ByteBuffer.allocateDirect(i3);
                allocateDirect.put(this.mDataBuffer, i2, i3);
                bufferInfo.offset = 0;
                return allocateDirect;
            }
            ByteBuffer allocateDirect2 = ByteBuffer.allocateDirect(i3);
            int i4 = length - i2;
            allocateDirect2.put(this.mDataBuffer, this.mPacketStart[i], i4);
            allocateDirect2.put(this.mDataBuffer, 0, i3 - i4);
            bufferInfo.offset = 0;
            return allocateDirect2;
        }

        public int getFirstIndex() {
            int i = this.mMetaTail;
            if (i == this.mMetaHead) {
                return -1;
            }
            return i;
        }

        public LivePhotoResult getLivePhotoResult(int i) {
            return this.mLivePhotoResults[i];
        }

        public int getNextIndex(int i) {
            int length = (i + 1) % this.mPacketStart.length;
            if (length == this.mMetaHead) {
                return -1;
            }
            return length;
        }
    }

    protected class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    CircularMediaEncoder.this.doStart();
                    Message obtainMessage = ((Handler) message.obj).obtainMessage();
                    obtainMessage.obj = new Object();
                    obtainMessage.sendToTarget();
                    return;
                case 1:
                    CircularMediaEncoder.this.doStop();
                    Message obtainMessage2 = ((Handler) message.obj).obtainMessage();
                    obtainMessage2.obj = new Object();
                    obtainMessage2.sendToTarget();
                    return;
                case 2:
                    CircularMediaEncoder.this.doRelease();
                    Message obtainMessage3 = ((Handler) message.obj).obtainMessage();
                    obtainMessage3.obj = new Object();
                    obtainMessage3.sendToTarget();
                    return;
                default:
                    String access$000 = CircularMediaEncoder.TAG;
                    Log.w(access$000, "Unknown message " + message.what);
                    return;
            }
        }
    }

    public static final class Sample {
        public static final Sample EOS_SAMPLE_ENTRY;
        public final ByteBuffer data;
        public final MediaCodec.BufferInfo info;
        public final LivePhotoResult livePhotoResult;

        static {
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            bufferInfo.set(0, 0, 0, 4);
            EOS_SAMPLE_ENTRY = create(ByteBuffer.allocate(0), bufferInfo);
        }

        private Sample(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
            this(byteBuffer, bufferInfo, (LivePhotoResult) null);
        }

        private Sample(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo, LivePhotoResult livePhotoResult2) {
            this.data = byteBuffer;
            MediaCodec.BufferInfo bufferInfo2 = new MediaCodec.BufferInfo();
            bufferInfo2.set(bufferInfo.offset, bufferInfo.size, bufferInfo.presentationTimeUs, bufferInfo.flags);
            this.info = bufferInfo2;
            this.livePhotoResult = livePhotoResult2;
        }

        public static Sample create(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
            return new Sample(byteBuffer, bufferInfo);
        }

        public static Sample create(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo, LivePhotoResult livePhotoResult2) {
            return new Sample(byteBuffer, bufferInfo, livePhotoResult2);
        }
    }

    public static final class Snapshot {
        public int filterId;
        private boolean forceEos = false;
        public final MediaFormat format;
        public final long head;
        public long offset;
        public long position = -1;
        public volatile BlockingQueue<Sample> samples = new LinkedBlockingQueue();
        public final long tail;
        public long time;

        public Snapshot(long j, long j2, long j3, int i, MediaFormat mediaFormat) {
            this.head = j;
            this.tail = j2;
            this.time = j3;
            this.format = mediaFormat;
            this.filterId = i;
        }

        public void clear() {
            while (!this.samples.isEmpty()) {
                ((Sample) this.samples.poll()).data.clear();
            }
        }

        public boolean eos() {
            return this.forceEos || this.position >= this.tail;
        }

        public void put(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo, LivePhotoResult livePhotoResult) throws InterruptedException {
            if (!eos()) {
                this.samples.put(Sample.create(byteBuffer, bufferInfo, livePhotoResult));
                this.position = bufferInfo.presentationTimeUs;
                if (eos()) {
                    String str = this.format.getString("mime").split("/")[0];
                    Log.d("Snapshot", str + ": max range has reached: " + this.head + ":" + this.tail + ":" + this.position);
                    putEos();
                }
            }
        }

        public void putEos() throws InterruptedException {
            this.samples.put(Sample.EOS_SAMPLE_ENTRY);
            this.forceEos = true;
        }
    }

    public CircularMediaEncoder(MediaFormat mediaFormat, long j, long j2, Queue<LivePhotoResult> queue) {
        if (mediaFormat == null) {
            throw new IllegalArgumentException("The desired MediaFormat must not be null");
        } else if (j2 > j) {
            throw new IllegalArgumentException("The preCaptureDurationUs must not be greater than captureDurationUs");
        } else if (j <= 0 || j2 <= 0) {
            throw new IllegalArgumentException("Both captureDurationUs and preCaptureDurationUs must be positive integers");
        } else {
            String string = mediaFormat.getString("mime");
            if (string == null) {
                throw new IllegalArgumentException("The desired mimeType is not specified");
            } else if (MediaCodecCapability.isFormatSupported(mediaFormat, string)) {
                this.mLivePhotoQueue = queue;
                this.mDesiredMediaFormat = mediaFormat;
                this.mCaptureDurationUs = j;
                this.mPreCaptureDurationUs = j2;
                this.mPostCaptureDurationUs = j - j2;
                this.mBufferingDurationUs = j * 2;
                this.mCyclicBuffer = new CyclicBuffer(this.mDesiredMediaFormat, TimeUnit.MICROSECONDS.toMillis(this.mBufferingDurationUs));
                this.mBufferInfo = new MediaCodec.BufferInfo();
                this.mSnapshots = new ArrayList();
                this.mEncodingThread = new BackgroundWorker(string.contains(VEEditor.MVConsts.TYPE_VIDEO) ? "VideoEncodingThread" : "AudioEncodingThread");
                this.mEventHandler = new EventHandler(this.mEncodingThread.getLooper());
                this.mHandlerHelper = new HandlerHelper();
                this.mIsBuffering = false;
            } else {
                throw new IllegalArgumentException("The desired MediaFormat is not supported");
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doRelease() {
        if (this.mMediaCodec != null) {
            this.mMediaCodec.release();
            this.mMediaCodec = null;
        }
        if (this.mEncodingThread != null) {
            try {
                this.mEncodingThread.quit();
            } catch (InterruptedException e) {
                String str = TAG;
                Log.d(str, "Failed to stop encoding thread: " + e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doStart() {
        this.mOutputFormatChanged = false;
        if (this.mMediaCodec != null) {
            this.mMediaCodec.start();
        }
    }

    /* access modifiers changed from: protected */
    public void doStop() {
        if (this.mMediaCodec != null) {
            this.mMediaCodec.flush();
            this.mMediaCodec.stop();
            this.mMediaCodec.reset();
        }
    }

    /* access modifiers changed from: protected */
    public long getNextPresentationTimeUs(long j) {
        return j;
    }

    public void onError(MediaCodec mediaCodec, MediaCodec.CodecException codecException) {
        String str = TAG;
        Log.e(str, "MediaCodec Exception occurred: " + codecException);
    }

    public void onInputBufferAvailable(MediaCodec mediaCodec, int i) {
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x01d8  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01b5 A[SYNTHETIC] */
    public void onOutputBufferAvailable(MediaCodec mediaCodec, int i, MediaCodec.BufferInfo bufferInfo) {
        int i2;
        MediaCodec.BufferInfo bufferInfo2 = bufferInfo;
        ByteBuffer outputBuffer = mediaCodec.getOutputBuffer(i);
        String str = mediaCodec.getOutputFormat(i).getString("mime").split("/")[0];
        if (!(outputBuffer == null || bufferInfo2.size == 0 || !this.mIsBuffering)) {
            outputBuffer.position(bufferInfo2.offset);
            outputBuffer.limit(bufferInfo2.offset + bufferInfo2.size);
            bufferInfo2.presentationTimeUs = getNextPresentationTimeUs(bufferInfo2.presentationTimeUs);
            LivePhotoResult livePhotoResult = null;
            if (this.mLivePhotoQueue != null) {
                livePhotoResult = this.mLivePhotoQueue.poll();
            }
            LivePhotoResult livePhotoResult2 = livePhotoResult;
            String str2 = TAG;
            Log.d(str2, str + ": CyclicBuffer.add E " + bufferInfo2.presentationTimeUs);
            this.mCyclicBuffer.add(outputBuffer, bufferInfo2.flags, bufferInfo2.presentationTimeUs, livePhotoResult2);
            this.mCurrentPresentationTimeUs = bufferInfo2.presentationTimeUs;
            String str3 = TAG;
            Log.d(str3, str + ": CyclicBuffer.add X " + bufferInfo2.presentationTimeUs);
            ArrayList<Snapshot> arrayList = new ArrayList<>();
            synchronized (this.mSnapshots) {
                arrayList.addAll(this.mSnapshots);
            }
            for (Snapshot snapshot : arrayList) {
                int firstIndex = this.mCyclicBuffer.getFirstIndex();
                if (firstIndex >= 0) {
                    boolean z = true;
                    boolean z2 = snapshot.position == -1;
                    int i3 = firstIndex;
                    while (true) {
                        ByteBuffer chunk = this.mCyclicBuffer.getChunk(i3, this.mBufferInfo);
                        LivePhotoResult livePhotoResult3 = this.mCyclicBuffer.getLivePhotoResult(i3);
                        long j = this.mBufferInfo.presentationTimeUs;
                        boolean z3 = this.mBufferInfo.flags & z ? z : false;
                        if (z2) {
                            try {
                                if (j >= snapshot.head) {
                                    String str4 = TAG;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(str);
                                    sb.append(": Snapshot.put oldcache E ");
                                    i2 = i3;
                                    try {
                                        sb.append(snapshot.head);
                                        sb.append(":");
                                        sb.append(snapshot.tail);
                                        sb.append(":");
                                        sb.append(j);
                                        sb.append(":");
                                        sb.append(z3);
                                        Log.d(str4, sb.toString());
                                        snapshot.put(chunk, this.mBufferInfo, livePhotoResult3);
                                        String str5 = TAG;
                                        Log.d(str5, str + ": Snapshot.put oldcache X");
                                    } catch (InterruptedException e) {
                                        String str6 = TAG;
                                        Log.e(str6, str + ": Snapshot.put: meet interrupted exception");
                                        if (!snapshot.eos()) {
                                        }
                                    }
                                } else {
                                    i2 = i3;
                                }
                            } catch (InterruptedException e2) {
                                i2 = i3;
                                String str62 = TAG;
                                Log.e(str62, str + ": Snapshot.put: meet interrupted exception");
                                if (!snapshot.eos()) {
                                }
                            }
                        } else {
                            i2 = i3;
                            if (j > snapshot.position) {
                                String str7 = TAG;
                                Log.d(str7, str + ": Snapshot.put incoming E " + snapshot.head + ":" + snapshot.tail + ":" + j + ":" + z3);
                                snapshot.put(chunk, this.mBufferInfo, livePhotoResult3);
                                String str8 = TAG;
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(str);
                                sb2.append(": Snapshot.put incoming X");
                                Log.d(str8, sb2.toString());
                            }
                        }
                        if (!snapshot.eos()) {
                            i3 = this.mCyclicBuffer.getNextIndex(i2);
                            if (i3 < 0 || snapshot.eos()) {
                                break;
                            }
                            z = true;
                        } else {
                            synchronized (this.mSnapshots) {
                                String str9 = TAG;
                                Log.e(str9, str + ": Snapshot.put: removed from queue");
                                this.mSnapshots.remove(snapshot);
                            }
                            break;
                        }
                    }
                } else {
                    String str10 = TAG;
                    Log.w(str10, str + ": Unable to get the first index");
                    throw new IllegalStateException("Unable to get the first index");
                }
            }
        }
        mediaCodec.releaseOutputBuffer(i, false);
    }

    public void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat) {
        String str = TAG;
        Log.e(str, "MediaCodec Output Format Changed: " + mediaFormat);
        synchronized (this) {
            this.mOutputFormatChanged = true;
            notifyAll();
        }
    }

    public void release() {
        Log.d(TAG, "release");
        this.mHandlerHelper.sendMessageAndAwaitResponse(this.mEventHandler.obtainMessage(2));
        this.mHandlerHelper.release();
    }

    public Snapshot snapshot() {
        return snapshot(FilterInfo.FILTER_ID_NONE);
    }

    public Snapshot snapshot(int i) {
        if (this.mIsBuffering) {
            long j = this.mCurrentPresentationTimeUs + this.mPostCaptureDurationUs;
            long max = Math.max(0, j - this.mCaptureDurationUs);
            String str = TAG;
            Log.d(str, "Snapshot[" + max + ", " + this.mCurrentPresentationTimeUs + ", " + j + "]");
            Snapshot snapshot = new Snapshot(max, j, this.mCurrentPresentationTimeUs, i, this.mMediaCodec.getOutputFormat());
            synchronized (this.mSnapshots) {
                this.mSnapshots.add(snapshot);
            }
            return snapshot;
        }
        throw new IllegalStateException("MediaCodec has not been started yet");
    }

    public void start() {
        Log.d(TAG, "start");
        this.mHandlerHelper.sendMessageAndAwaitResponse(this.mEventHandler.obtainMessage(0));
    }

    public void stop() {
        int i;
        Log.d(TAG, "stop");
        synchronized (this) {
            i = 10;
            while (!this.mOutputFormatChanged && i > 0) {
                i--;
                Log.d(TAG, "waiting for MediaCodec getting stable before stop: E");
                try {
                    wait(200);
                } catch (InterruptedException e) {
                }
                Log.d(TAG, "waiting for MediaCodec getting stable before stop: X");
            }
        }
        if (i == 0) {
            Log.d(TAG, "waiting for MediaCodec getting stable before stop has timed out");
        }
        this.mHandlerHelper.sendMessageAndAwaitResponse(this.mEventHandler.obtainMessage(1));
    }
}
