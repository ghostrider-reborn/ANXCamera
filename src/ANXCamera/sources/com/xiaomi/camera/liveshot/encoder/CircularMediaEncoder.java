package com.xiaomi.camera.liveshot.encoder;

import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodec.Callback;
import android.media.MediaCodec.CodecException;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.android.camera.effect.FilterInfo;
import com.android.camera.log.Log;
import com.ss.android.vesdk.VEEditor.MVConsts;
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

public abstract class CircularMediaEncoder extends Callback {
    private static final boolean DEBUG = true;
    private static final int MSG_RELEASE_ENCODER = 2;
    private static final int MSG_START_ENCODING = 0;
    private static final int MSG_STOP_ENCODING = 1;
    /* access modifiers changed from: private */
    public static final String TAG = "CircularMediaEncoder";
    protected final BufferInfo mBufferInfo;
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
        private static final String TAG = "CyclicBuffer";
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
                    int integer2 = (int) ((((double) i) / ((((double) integer) / (string.contains(MVConsts.TYPE_VIDEO) ? (double) mediaFormat.getInteger("frame-rate") : ((double) mediaFormat.getInteger("sample-rate")) / 1024.0d)) / 8.0d)) + 1.0d);
                    int i2 = integer2 * 2;
                    this.mPacketFlags = new int[i2];
                    this.mPacketPtsUs = new long[i2];
                    this.mPacketStart = new int[i2];
                    this.mPacketLength = new int[i2];
                    this.mLivePhotoResults = new LivePhotoResult[i2];
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("DesiredSpan = ");
                    sb.append(j);
                    sb.append(", dataBufferSize = ");
                    sb.append(i);
                    sb.append(", metaBufferCount = ");
                    sb.append(i2);
                    sb.append(", estimatedPacketCount = ");
                    sb.append(integer2);
                    Log.d(str, sb.toString());
                    return;
                }
                throw new IllegalArgumentException("The desired mimeType is not specified");
            }
            throw new IllegalArgumentException("The desired MediaFormat must not be null");
        }

        private boolean canAdd(int i) {
            int length = this.mDataBuffer.length;
            int length2 = this.mPacketStart.length;
            if (i <= length) {
                int i2 = this.mMetaHead;
                int i3 = this.mMetaTail;
                if (i2 == i3) {
                    return true;
                }
                String str = ")";
                if ((i2 + 1) % length2 == i3) {
                    String str2 = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Ran out of metadata (head=");
                    sb.append(this.mMetaHead);
                    sb.append(" tail=");
                    sb.append(this.mMetaTail);
                    sb.append(str);
                    Log.v(str2, sb.toString());
                    return false;
                }
                int headStart = getHeadStart();
                int i4 = this.mPacketStart[this.mMetaTail];
                int i5 = ((i4 + length) - headStart) % length;
                String str3 = " free=";
                if (i > i5) {
                    String str4 = TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Ran out of data (tailStart=");
                    sb2.append(i4);
                    sb2.append(" headStart=");
                    sb2.append(headStart);
                    sb2.append(" req=");
                    sb2.append(i);
                    sb2.append(str3);
                    sb2.append(i5);
                    sb2.append(str);
                    Log.v(str4, sb2.toString());
                    return false;
                }
                String str5 = TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Okay: size=");
                sb3.append(i);
                sb3.append(str3);
                sb3.append(i5);
                sb3.append(" metaFree=");
                sb3.append((((this.mMetaTail + length2) - this.mMetaHead) % length2) - 1);
                Log.v(str5, sb3.toString());
                return true;
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Enormous packet: ");
            sb4.append(i);
            sb4.append(" vs. buffer ");
            sb4.append(length);
            throw new RuntimeException(sb4.toString());
        }

        private int getHeadStart() {
            int i = this.mMetaHead;
            if (i == this.mMetaTail) {
                return 0;
            }
            int length = this.mDataBuffer.length;
            int[] iArr = this.mPacketStart;
            int length2 = iArr.length;
            int i2 = ((i + length2) - 1) % length2;
            return ((iArr[i2] + this.mPacketLength[i2]) + 1) % length;
        }

        private void removeTail() {
            int i = this.mMetaHead;
            int i2 = this.mMetaTail;
            if (i != i2) {
                this.mMetaTail = (i2 + 1) % this.mPacketStart.length;
                return;
            }
            throw new RuntimeException("Can't removeTail() in empty buffer");
        }

        public void add(ByteBuffer byteBuffer, int i, long j, LivePhotoResult livePhotoResult) {
            int limit = byteBuffer.limit() - byteBuffer.position();
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Add size=");
            sb.append(limit);
            sb.append(" flags=0x");
            sb.append(Integer.toHexString(i));
            sb.append(" pts=");
            sb.append(j);
            Log.d(str, sb.toString());
            while (!canAdd(limit)) {
                Log.d(TAG, "Cached buffer removed from tail");
                removeTail();
            }
            int length = this.mDataBuffer.length;
            int length2 = this.mPacketStart.length;
            int headStart = getHeadStart();
            int[] iArr = this.mPacketFlags;
            int i2 = this.mMetaHead;
            iArr[i2] = i;
            this.mPacketPtsUs[i2] = j;
            this.mPacketStart[i2] = headStart;
            this.mPacketLength[i2] = limit;
            this.mLivePhotoResults[i2] = livePhotoResult;
            if (headStart + limit < length) {
                byteBuffer.get(this.mDataBuffer, headStart, limit);
            } else {
                int i3 = length - headStart;
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Split, firstsize=");
                sb2.append(i3);
                sb2.append(" size=");
                sb2.append(limit);
                Log.v(str2, sb2.toString());
                byteBuffer.get(this.mDataBuffer, headStart, i3);
                byteBuffer.get(this.mDataBuffer, 0, limit - i3);
            }
            this.mMetaHead = (this.mMetaHead + 1) % length2;
        }

        public void clear() {
            Arrays.fill(this.mDataBuffer, 0);
            Arrays.fill(this.mPacketFlags, 0);
            Arrays.fill(this.mPacketPtsUs, 0);
            Arrays.fill(this.mPacketStart, 0);
            Arrays.fill(this.mPacketLength, 0);
            Arrays.fill(this.mLivePhotoResults, null);
            this.mMetaHead = 0;
            this.mMetaTail = 0;
        }

        public long computeTimeSpanUsec() {
            int length = this.mPacketStart.length;
            int i = this.mMetaHead;
            int i2 = this.mMetaTail;
            if (i == i2) {
                return 0;
            }
            int i3 = ((i + length) - 1) % length;
            long[] jArr = this.mPacketPtsUs;
            return jArr[i3] - jArr[i2];
        }

        public ByteBuffer getChunk(int i, BufferInfo bufferInfo) {
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
            int i = message.what;
            if (i == 0) {
                CircularMediaEncoder.this.doStart();
                Message obtainMessage = ((Handler) message.obj).obtainMessage();
                obtainMessage.obj = new Object();
                obtainMessage.sendToTarget();
            } else if (i == 1) {
                CircularMediaEncoder.this.doStop();
                Message obtainMessage2 = ((Handler) message.obj).obtainMessage();
                obtainMessage2.obj = new Object();
                obtainMessage2.sendToTarget();
            } else if (i != 2) {
                String access$000 = CircularMediaEncoder.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown message ");
                sb.append(message.what);
                Log.w(access$000, sb.toString());
            } else {
                CircularMediaEncoder.this.doRelease();
                Message obtainMessage3 = ((Handler) message.obj).obtainMessage();
                obtainMessage3.obj = new Object();
                obtainMessage3.sendToTarget();
            }
        }
    }

    public static final class Sample {
        public static final Sample EOS_SAMPLE_ENTRY;
        public final ByteBuffer data;
        public final BufferInfo info;
        public final LivePhotoResult livePhotoResult;

        static {
            BufferInfo bufferInfo = new BufferInfo();
            bufferInfo.set(0, 0, 0, 4);
            EOS_SAMPLE_ENTRY = create(ByteBuffer.allocate(0), bufferInfo);
        }

        private Sample(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
            this(byteBuffer, bufferInfo, null);
        }

        private Sample(ByteBuffer byteBuffer, BufferInfo bufferInfo, LivePhotoResult livePhotoResult2) {
            this.data = byteBuffer;
            BufferInfo bufferInfo2 = new BufferInfo();
            bufferInfo2.set(bufferInfo.offset, bufferInfo.size, bufferInfo.presentationTimeUs, bufferInfo.flags);
            this.info = bufferInfo2;
            this.livePhotoResult = livePhotoResult2;
        }

        public static Sample create(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
            return new Sample(byteBuffer, bufferInfo);
        }

        public static Sample create(ByteBuffer byteBuffer, BufferInfo bufferInfo, LivePhotoResult livePhotoResult2) {
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

        public void put(ByteBuffer byteBuffer, BufferInfo bufferInfo, LivePhotoResult livePhotoResult) throws InterruptedException {
            if (!eos()) {
                this.samples.put(Sample.create(byteBuffer, bufferInfo, livePhotoResult));
                this.position = bufferInfo.presentationTimeUs;
                if (eos()) {
                    String str = this.format.getString("mime").split("/")[0];
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(": max range has reached: ");
                    sb.append(this.head);
                    String str2 = ":";
                    sb.append(str2);
                    sb.append(this.tail);
                    sb.append(str2);
                    sb.append(this.position);
                    Log.d("Snapshot", sb.toString());
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
                this.mBufferInfo = new BufferInfo();
                this.mSnapshots = new ArrayList();
                this.mEncodingThread = new BackgroundWorker(string.contains(MVConsts.TYPE_VIDEO) ? "VideoEncodingThread" : "AudioEncodingThread");
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
        MediaCodec mediaCodec = this.mMediaCodec;
        if (mediaCodec != null) {
            mediaCodec.release();
            this.mMediaCodec = null;
        }
        BackgroundWorker backgroundWorker = this.mEncodingThread;
        if (backgroundWorker != null) {
            try {
                backgroundWorker.quit();
            } catch (InterruptedException e2) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to stop encoding thread: ");
                sb.append(e2);
                Log.d(str, sb.toString());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doStart() {
        this.mOutputFormatChanged = false;
        MediaCodec mediaCodec = this.mMediaCodec;
        if (mediaCodec != null) {
            mediaCodec.start();
        }
    }

    /* access modifiers changed from: protected */
    public void doStop() {
        MediaCodec mediaCodec = this.mMediaCodec;
        if (mediaCodec != null) {
            mediaCodec.flush();
            this.mMediaCodec.stop();
            this.mMediaCodec.reset();
        }
    }

    /* access modifiers changed from: protected */
    public long getNextPresentationTimeUs(long j) {
        return j;
    }

    public void onError(MediaCodec mediaCodec, CodecException codecException) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("MediaCodec Exception occurred: ");
        sb.append(codecException);
        Log.e(str, sb.toString());
    }

    public void onInputBufferAvailable(MediaCodec mediaCodec, int i) {
    }

    /* JADX WARNING: Removed duplicated region for block: B:59:0x01d4  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x01b1 A[SYNTHETIC] */
    public void onOutputBufferAvailable(MediaCodec mediaCodec, int i, BufferInfo bufferInfo) {
        String str;
        String str2;
        StringBuilder sb;
        BufferInfo bufferInfo2 = bufferInfo;
        ByteBuffer outputBuffer = mediaCodec.getOutputBuffer(i);
        boolean z = false;
        String str3 = mediaCodec.getOutputFormat(i).getString("mime").split("/")[0];
        if (!(outputBuffer == null || bufferInfo2.size == 0 || !this.mIsBuffering)) {
            outputBuffer.position(bufferInfo2.offset);
            outputBuffer.limit(bufferInfo2.offset + bufferInfo2.size);
            bufferInfo2.presentationTimeUs = getNextPresentationTimeUs(bufferInfo2.presentationTimeUs);
            LivePhotoResult livePhotoResult = null;
            Queue<LivePhotoResult> queue = this.mLivePhotoQueue;
            if (queue != null) {
                livePhotoResult = (LivePhotoResult) queue.poll();
            }
            LivePhotoResult livePhotoResult2 = livePhotoResult;
            String str4 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str3);
            sb2.append(": CyclicBuffer.add E ");
            sb2.append(bufferInfo2.presentationTimeUs);
            Log.d(str4, sb2.toString());
            this.mCyclicBuffer.add(outputBuffer, bufferInfo2.flags, bufferInfo2.presentationTimeUs, livePhotoResult2);
            this.mCurrentPresentationTimeUs = bufferInfo2.presentationTimeUs;
            String str5 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str3);
            sb3.append(": CyclicBuffer.add X ");
            sb3.append(bufferInfo2.presentationTimeUs);
            Log.d(str5, sb3.toString());
            ArrayList<Snapshot> arrayList = new ArrayList<>();
            synchronized (this.mSnapshots) {
                arrayList.addAll(this.mSnapshots);
            }
            for (Snapshot snapshot : arrayList) {
                int firstIndex = this.mCyclicBuffer.getFirstIndex();
                if (firstIndex >= 0) {
                    boolean z2 = true;
                    boolean z3 = snapshot.position == -1 ? true : z;
                    while (true) {
                        ByteBuffer chunk = this.mCyclicBuffer.getChunk(firstIndex, this.mBufferInfo);
                        LivePhotoResult livePhotoResult3 = this.mCyclicBuffer.getLivePhotoResult(firstIndex);
                        BufferInfo bufferInfo3 = this.mBufferInfo;
                        long j = bufferInfo3.presentationTimeUs;
                        boolean z4 = bufferInfo3.flags & z2 ? z2 : z;
                        if (z3) {
                            try {
                                if (j >= snapshot.head) {
                                    String str6 = TAG;
                                    StringBuilder sb4 = new StringBuilder();
                                    sb4.append(str3);
                                    sb4.append(": Snapshot.put oldcache E ");
                                    String str7 = str3;
                                    try {
                                        sb4.append(snapshot.head);
                                        sb4.append(":");
                                        sb4.append(snapshot.tail);
                                        sb4.append(":");
                                        sb4.append(j);
                                        sb4.append(":");
                                        sb4.append(z4);
                                        Log.d(str6, sb4.toString());
                                        snapshot.put(chunk, this.mBufferInfo, livePhotoResult3);
                                        str2 = TAG;
                                        sb = new StringBuilder();
                                        str = str7;
                                    } catch (InterruptedException unused) {
                                        str = str7;
                                        String str8 = TAG;
                                        StringBuilder sb5 = new StringBuilder();
                                        sb5.append(str);
                                        sb5.append(": Snapshot.put: meet interrupted exception");
                                        Log.e(str8, sb5.toString());
                                        if (snapshot.eos()) {
                                        }
                                        str3 = str;
                                        z = false;
                                    }
                                    try {
                                        sb.append(str);
                                        sb.append(": Snapshot.put oldcache X");
                                        Log.d(str2, sb.toString());
                                    } catch (InterruptedException unused2) {
                                        String str82 = TAG;
                                        StringBuilder sb52 = new StringBuilder();
                                        sb52.append(str);
                                        sb52.append(": Snapshot.put: meet interrupted exception");
                                        Log.e(str82, sb52.toString());
                                        if (snapshot.eos()) {
                                        }
                                        str3 = str;
                                        z = false;
                                    }
                                } else {
                                    str = str3;
                                }
                            } catch (InterruptedException unused3) {
                                str = str3;
                                String str822 = TAG;
                                StringBuilder sb522 = new StringBuilder();
                                sb522.append(str);
                                sb522.append(": Snapshot.put: meet interrupted exception");
                                Log.e(str822, sb522.toString());
                                if (snapshot.eos()) {
                                }
                                str3 = str;
                                z = false;
                            }
                        } else {
                            str = str3;
                            if (j > snapshot.position) {
                                String str9 = TAG;
                                StringBuilder sb6 = new StringBuilder();
                                sb6.append(str);
                                sb6.append(": Snapshot.put incoming E ");
                                ByteBuffer byteBuffer = chunk;
                                sb6.append(snapshot.head);
                                sb6.append(":");
                                sb6.append(snapshot.tail);
                                sb6.append(":");
                                sb6.append(j);
                                sb6.append(":");
                                sb6.append(z4);
                                Log.d(str9, sb6.toString());
                                snapshot.put(byteBuffer, this.mBufferInfo, livePhotoResult3);
                                String str10 = TAG;
                                StringBuilder sb7 = new StringBuilder();
                                sb7.append(str);
                                sb7.append(": Snapshot.put incoming X");
                                Log.d(str10, sb7.toString());
                            }
                        }
                        if (snapshot.eos()) {
                            firstIndex = this.mCyclicBuffer.getNextIndex(firstIndex);
                            if (firstIndex < 0 || snapshot.eos()) {
                                break;
                            }
                            str3 = str;
                            z2 = true;
                            z = false;
                        } else {
                            synchronized (this.mSnapshots) {
                                String str11 = TAG;
                                StringBuilder sb8 = new StringBuilder();
                                sb8.append(str);
                                sb8.append(": Snapshot.put: removed from queue");
                                Log.e(str11, sb8.toString());
                                this.mSnapshots.remove(snapshot);
                            }
                            break;
                        }
                    }
                    str3 = str;
                    z = false;
                } else {
                    String str12 = str3;
                    String str13 = TAG;
                    StringBuilder sb9 = new StringBuilder();
                    sb9.append(str12);
                    sb9.append(": Unable to get the first index");
                    Log.w(str13, sb9.toString());
                    throw new IllegalStateException("Unable to get the first index");
                }
            }
        }
        mediaCodec.releaseOutputBuffer(i, z);
    }

    public void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("MediaCodec Output Format Changed: ");
        sb.append(mediaFormat);
        Log.e(str, sb.toString());
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
            StringBuilder sb = new StringBuilder();
            sb.append("Snapshot[");
            sb.append(max);
            sb.append(", ");
            sb.append(this.mCurrentPresentationTimeUs);
            sb.append(", ");
            sb.append(j);
            sb.append("]");
            Log.d(str, sb.toString());
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

    /* JADX WARNING: Can't wrap try/catch for region: R(8:7|8|9|10|11|12|4|3) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x001e */
    public void stop() {
        int i;
        Log.d(TAG, "stop");
        synchronized (this) {
            i = 10;
            while (!this.mOutputFormatChanged && i > 0) {
                i--;
                Log.d(TAG, "waiting for MediaCodec getting stable before stop: E");
                wait(200);
                Log.d(TAG, "waiting for MediaCodec getting stable before stop: X");
            }
        }
        if (i == 0) {
            Log.d(TAG, "waiting for MediaCodec getting stable before stop has timed out");
        }
        this.mHandlerHelper.sendMessageAndAwaitResponse(this.mEventHandler.obtainMessage(1));
    }
}
