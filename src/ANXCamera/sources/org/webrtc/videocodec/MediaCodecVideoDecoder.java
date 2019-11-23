package org.webrtc.videocodec;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

class MediaCodecVideoDecoder {
    public static final Boolean DEBUG = true;
    public static final int DECODE = 0;
    public static final int EXIT = 2;
    public static final int PUSHBUFFER = 1;
    /* access modifiers changed from: private */
    public static String TAG;
    private static boolean decodeStarted = false;
    private LinkedList<Integer> availableInputBufferIndices;
    private LinkedList<Integer> availableOutputBufferIndices;
    private LinkedList<MediaCodec.BufferInfo> availableOutputBufferInfos;
    /* access modifiers changed from: private */
    public MediaCodec codec = null;
    private ByteBuffer[] codecInputBuffers;
    private ByteBuffer[] codecOutputBuffers;
    private int codecheight;
    private int codecwidth;
    private Context context;
    /* access modifiers changed from: private */
    public DecodeHandler decodehandler;
    private Thread decodelooperThread;
    private long deltaTimeUs;
    private MediaFormat format;
    int frameCount = 0;
    long frameNum = 0;
    private LinkedList<Frame> frameQueue;
    private long native_context;
    /* access modifiers changed from: private */
    public DecodeHandler pushhandler;
    private Thread pushlooperThread;
    private SurfaceView surfaceView;
    /* access modifiers changed from: private */
    public Map<String, String> timeMap;

    private enum CodecName {
        HWH264,
        ON2_VP8,
        GOOGLE_VPX,
        EXYNOX_VP8
    }

    class DecodeHandler extends Handler {
        DecodeHandler() {
        }

        public void handleMessage(Message message) {
            try {
                switch (message.what) {
                    case 0:
                        if (MediaCodecVideoDecoder.this.validCodec(MediaCodecVideoDecoder.this.codec)) {
                            MediaCodecVideoDecoder.this.decodePendingBuffers();
                            MediaCodecVideoDecoder.this.decodehandler.sendMessageDelayed(MediaCodecVideoDecoder.this.decodehandler.obtainMessage(0), 5);
                            return;
                        }
                        return;
                    case 1:
                        if (MediaCodecVideoDecoder.this.validCodec(MediaCodecVideoDecoder.this.codec)) {
                            MediaCodecVideoDecoder.this.PushPendingBuffers();
                            MediaCodecVideoDecoder.this.pushhandler.sendMessageDelayed(MediaCodecVideoDecoder.this.pushhandler.obtainMessage(1), 5);
                            return;
                        }
                        return;
                    case 2:
                        if (MediaCodecVideoDecoder.DEBUG.booleanValue()) {
                            Log.d(MediaCodecVideoDecoder.TAG, "handleMessage EXIT.");
                            return;
                        }
                        return;
                    default:
                        return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Frame {
        public ByteBuffer buffer;
        public int buflen;
        public int frameType;
        public int height;
        public long ntp_timestapUs;
        public long timestampUs;
        public int width;

        Frame(ByteBuffer byteBuffer, int i, long j, long j2, int i2, int i3, int i4) {
            this.buffer = byteBuffer;
            this.buflen = i;
            this.timestampUs = j;
            this.ntp_timestapUs = j2;
            this.width = i2;
            this.height = i3;
            this.frameType = i4;
            MediaCodecVideoDecoder.this.timeMap.put(String.valueOf(j), String.valueOf(j2));
        }
    }

    public MediaCodecVideoDecoder(int i, long j) {
        TAG = "HW-Decoder";
        this.native_context = j;
        this.frameQueue = new LinkedList<>();
        this.availableInputBufferIndices = new LinkedList<>();
        this.availableOutputBufferIndices = new LinkedList<>();
        this.availableOutputBufferInfos = new LinkedList<>();
        this.timeMap = new HashMap();
        decodeStarted = false;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0028, code lost:
        if (feedInputBuffer() == false) goto L_0x002b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002b, code lost:
        return;
     */
    public void PushPendingBuffers() {
        synchronized (this.codec) {
            if (validCodec(this.codec)) {
                int dequeueInputBuffer = this.codec.dequeueInputBuffer((long) 10);
                if (dequeueInputBuffer != -1) {
                    this.availableInputBufferIndices.add(Integer.valueOf(dequeueInputBuffer));
                }
            }
        }
    }

    private void check(boolean z, String str) {
        if (!z) {
            Log.e("WEBRTC-CHECK", str);
            AlertDialog create = new AlertDialog.Builder(this.context).create();
            create.setTitle("WebRTC Error");
            create.setMessage(str);
            create.setButton(-1, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            create.show();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0083, code lost:
        if (drainOutputBuffer() == false) goto L_0x0086;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0086, code lost:
        return;
     */
    public void decodePendingBuffers() {
        synchronized (this.codec) {
            if (validCodec(this.codec)) {
                MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                int dequeueOutputBuffer = this.codec.dequeueOutputBuffer(bufferInfo, (long) 0);
                if (logInfoEnable()) {
                    String str = TAG;
                    Log.d(str, "decodePendingBuffers DECODE. index:" + dequeueOutputBuffer);
                }
                if (dequeueOutputBuffer >= 0) {
                    this.availableOutputBufferIndices.add(Integer.valueOf(dequeueOutputBuffer));
                    this.availableOutputBufferInfos.add(bufferInfo);
                }
                if (dequeueOutputBuffer == -3) {
                    this.codecOutputBuffers = this.codec.getOutputBuffers();
                } else if (dequeueOutputBuffer == -2) {
                    MediaFormat outputFormat = this.codec.getOutputFormat();
                    this.codecwidth = outputFormat.getInteger("width");
                    this.codecheight = outputFormat.getInteger("height");
                    if (this.native_context != 0) {
                        ChangeResolution(this.codecwidth, this.codecheight, this.native_context);
                    }
                }
            }
        }
    }

    private Frame dequeueFrame() {
        Frame removeFirst;
        synchronized (this.frameQueue) {
            this.frameCount--;
            removeFirst = this.frameQueue.removeFirst();
        }
        return removeFirst;
    }

    private boolean drainOutputBuffer() {
        if (this.availableOutputBufferIndices.isEmpty()) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        int intValue = this.availableOutputBufferIndices.peekFirst().intValue();
        MediaCodec.BufferInfo peekFirst = this.availableOutputBufferInfos.peekFirst();
        if ((peekFirst.flags & 4) != 0) {
            check(false, "Saw output end of stream.");
            return false;
        }
        if (logInfoEnable()) {
            String str = TAG;
            Log.d(str, "drainOutputBuffer got one frame:" + intValue + "info.presentationTimeUs:" + peekFirst.presentationTimeUs);
        }
        String str2 = this.timeMap.get(String.valueOf(peekFirst.presentationTimeUs));
        if (str2 == null) {
            String str3 = TAG;
            Log.e(str3, "drainOutputBuffer timestap error from decoder -index:" + intValue + "info.presentationTimeUs:" + peekFirst.presentationTimeUs);
            if (this.native_context != 0) {
                Frame frame = new Frame(this.codecOutputBuffers[intValue], peekFirst.size, peekFirst.presentationTimeUs, -1, this.codecwidth, this.codecheight, -1);
                DeliverFrame(frame, this.native_context);
            }
            synchronized (this.codec) {
                if (!validCodec(this.codec)) {
                    return false;
                }
                this.codec.releaseOutputBuffer(intValue, false);
                this.availableOutputBufferIndices.removeFirst();
                this.availableOutputBufferInfos.removeFirst();
                return false;
            }
        }
        long parseLong = Long.parseLong(str2);
        if (logInfoEnable()) {
            String str4 = TAG;
            Log.d(str4, "drainOutputBuffer before DeliverFrame:" + intValue + "info.presentationTimeUs:" + peekFirst.presentationTimeUs);
        }
        if (this.native_context != 0) {
            Frame frame2 = r1;
            Frame frame3 = new Frame(this.codecOutputBuffers[intValue], peekFirst.size, peekFirst.presentationTimeUs, parseLong, this.codecwidth, this.codecheight, -1);
            DeliverFrame(frame2, this.native_context);
        }
        if (logInfoEnable()) {
            String str5 = TAG;
            Log.d(str5, "drainOutputBuffer after DeliverFrame:" + intValue + "info.presentationTimeUs:" + peekFirst.presentationTimeUs);
        }
        this.timeMap.remove(str2);
        synchronized (this.codec) {
            if (!validCodec(this.codec)) {
                return false;
            }
            this.codec.releaseOutputBuffer(intValue, false);
            this.availableOutputBufferIndices.removeFirst();
            this.availableOutputBufferInfos.removeFirst();
            return true;
        }
    }

    private boolean feedInputBuffer() {
        if (this.availableInputBufferIndices.isEmpty()) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        } else if (!hasFrame()) {
            try {
                Thread.sleep(10);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return false;
        } else {
            Frame dequeueFrame = dequeueFrame();
            ByteBuffer byteBuffer = dequeueFrame.buffer;
            int intValue = this.availableInputBufferIndices.pollFirst().intValue();
            ByteBuffer byteBuffer2 = this.codecInputBuffers[intValue];
            check(byteBuffer2.capacity() >= byteBuffer.capacity(), "Buffer is too small to copy a frame.");
            byteBuffer.rewind();
            byteBuffer2.rewind();
            byteBuffer2.put(byteBuffer);
            try {
                if (logInfoEnable()) {
                    String str = TAG;
                    Log.d(str, "feedInputBuffer -index:" + intValue + "frame.timestampUs:" + dequeueFrame.timestampUs);
                }
                synchronized (this.codec) {
                    if (!validCodec(this.codec)) {
                        return false;
                    }
                    this.codec.queueInputBuffer(intValue, 0, byteBuffer.capacity(), dequeueFrame.timestampUs, 0);
                }
            } catch (MediaCodec.CryptoException e3) {
                check(false, "CryptoException w/ errorCode " + e3.getErrorCode() + ", '" + e3.getMessage() + "'");
            }
        }
        return true;
    }

    private void flush() {
        this.availableInputBufferIndices.clear();
        this.availableOutputBufferIndices.clear();
        this.availableOutputBufferInfos.clear();
        this.timeMap.clear();
        this.codec.flush();
    }

    private boolean hasFrame() {
        synchronized (this.frameQueue) {
            if (!this.frameQueue.isEmpty()) {
                return true;
            }
            if (logInfoEnable()) {
                Log.d(TAG, "hasFrame frameQueue is empty");
            }
            try {
                this.frameQueue.wait();
                return false;
            } catch (InterruptedException e) {
                Log.w(TAG, "frameQueue.wait exception");
                return false;
            }
        }
    }

    private boolean logInfoEnable() {
        if (DEBUG.booleanValue()) {
            return this.frameNum < 60 || 0 == this.frameNum % 50;
        }
        return false;
    }

    private long mediaTimeToSystemTime(long j) {
        if (this.deltaTimeUs == -1) {
            this.deltaTimeUs = (System.currentTimeMillis() * 1000) - j;
        }
        return this.deltaTimeUs + j;
    }

    private void pushBuffer(ByteBuffer byteBuffer, int i, long j, long j2, int i2, int i3, int i4) {
        try {
            synchronized (this.frameQueue) {
                LinkedList<Frame> linkedList = this.frameQueue;
                Frame frame = new Frame(byteBuffer, i, j, j2, i2, i3, i4);
                linkedList.add(frame);
                if (logInfoEnable()) {
                    Log.d(TAG, "pushBuffer timestampUs:" + j + "ntp_timestapUs:" + j2 + "frameCount:" + this.frameCount);
                }
                this.frameNum++;
                if (this.frameNum > 1000) {
                    this.frameNum = 0;
                }
                this.frameCount++;
                this.frameQueue.notifyAll();
                if (!decodeStarted) {
                    decodeStarted = true;
                    this.decodehandler.sendMessage(this.decodehandler.obtainMessage(0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean setCodecState(int i, int i2, CodecName codecName) {
        this.format = new MediaFormat();
        this.codecwidth = i;
        this.codecheight = i2;
        this.format.setInteger("width", i);
        this.format.setInteger("height", i2);
        try {
            switch (codecName) {
                case HWH264:
                    this.format.setString("mime", "video/avc");
                    this.codec = MediaCodec.createDecoderByType("video/avc");
                    break;
                case ON2_VP8:
                    this.format.setString("mime", "video/x-vnd.on2.vp8");
                    this.codec = MediaCodec.createDecoderByType("video/x-vnd.on2.vp8");
                    break;
                case GOOGLE_VPX:
                    this.codec = MediaCodec.createByCodecName("OMX.google.vpx.decoder");
                    break;
                case EXYNOX_VP8:
                    this.codec = MediaCodec.createByCodecName("OMX.Exynos.VP8.Decoder");
                    break;
                default:
                    return false;
            }
            if (Build.MODEL.equalsIgnoreCase("MiTV2")) {
                this.format.setInteger("forceDecode", 1);
            }
            this.format.setInteger("color-format", 19);
            synchronized (this.codec) {
                this.codec.configure(this.format, (Surface) null, (MediaCrypto) null, 0);
                this.codec.start();
                if (DEBUG.booleanValue()) {
                    String str = TAG;
                    Log.d(str, "setCodecState after codec start -width:" + i + " height:" + i2);
                }
                this.codecInputBuffers = this.codec.getInputBuffers();
                this.codecOutputBuffers = this.codec.getOutputBuffers();
            }
            return true;
        } catch (Exception e) {
            if (e instanceof IOException) {
                Log.e(TAG, "Failed to create MediaCodec for VP8.", e);
                return false;
            }
            throw new RuntimeException(e);
        }
    }

    private boolean start(int i, int i2) {
        this.deltaTimeUs = -1;
        if (this.codec != null) {
            synchronized (this.codec) {
                if (this.codec != null) {
                    this.codec.stop();
                    this.codec.release();
                    this.codec = null;
                }
            }
        }
        if (!setCodecState(i, i2, CodecName.HWH264)) {
            return false;
        }
        startPushLooperThread();
        startDecodeLooperThread();
        this.pushhandler.sendMessage(this.pushhandler.obtainMessage(1));
        return true;
    }

    private void startDecodeLooperThread() {
        this.decodelooperThread = new Thread() {
            public void run() {
                Looper.prepare();
                DecodeHandler unused = MediaCodecVideoDecoder.this.decodehandler = new DecodeHandler();
                if (MediaCodecVideoDecoder.DEBUG.booleanValue()) {
                    Log.d(MediaCodecVideoDecoder.TAG, "startDecodeLooperThread Decoder-HW");
                }
                synchronized (MediaCodecVideoDecoder.this) {
                    MediaCodecVideoDecoder.this.notify();
                }
                Looper.loop();
            }
        };
        this.decodelooperThread.start();
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void startPushLooperThread() {
        this.pushlooperThread = new Thread() {
            public void run() {
                Looper.prepare();
                DecodeHandler unused = MediaCodecVideoDecoder.this.pushhandler = new DecodeHandler();
                if (MediaCodecVideoDecoder.DEBUG.booleanValue()) {
                    Log.d(MediaCodecVideoDecoder.TAG, "startPushLooperThread HW-Decoder");
                }
                synchronized (MediaCodecVideoDecoder.this) {
                    MediaCodecVideoDecoder.this.notify();
                }
                Looper.loop();
            }
        };
        this.pushlooperThread.start();
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean validCodec(MediaCodec mediaCodec) {
        if (mediaCodec != null) {
            return true;
        }
        if (!DEBUG.booleanValue()) {
            return false;
        }
        Log.d(TAG, "validCodec codec is null.");
        return false;
    }

    public native void ChangeResolution(int i, int i2, long j);

    public native void DeliverFrame(Frame frame, long j);

    public void dispose() {
        if (DEBUG.booleanValue()) {
            Log.d(TAG, "dispose");
        }
        if (this.codec != null) {
            synchronized (this.codec) {
                if (this.codec != null) {
                    this.codec.stop();
                    this.codec.release();
                    this.codec = null;
                }
                decodeStarted = false;
                this.native_context = 0;
                this.timeMap.clear();
            }
        }
    }

    public SurfaceView getView() {
        return this.surfaceView;
    }
}
