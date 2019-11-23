package org.webrtc.videocodec;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Bundle;
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

class MediaCodecVideoEncoder {
    public static final Boolean DEBUG = true;
    public static final int ENCODE = 0;
    public static final int EXIT = 2;
    public static final int PUSHBUFFER = 1;
    /* access modifiers changed from: private */
    public static String TAG;
    private static int encodeStarted = 0;
    private LinkedList<Integer> availableInputBufferIndices;
    private LinkedList<Integer> availableOutputBufferIndices;
    private LinkedList<MediaCodec.BufferInfo> availableOutputBufferInfos;
    private int bitRate;
    /* access modifiers changed from: private */
    public MediaCodec codec = null;
    private ByteBuffer[] codecInputBuffers;
    private ByteBuffer[] codecOutputBuffers;
    private int codecheight;
    private int codecwidth;
    private Context context;
    private long deltaTimeUs;
    /* access modifiers changed from: private */
    public EncodeHandler encodehandler;
    private Thread encodelooperThread;
    private MediaFormat format;
    private int fps;
    int frameCount = 0;
    long frameNum = 0;
    private LinkedList<Frame> frameQueue;
    private long native_context;
    /* access modifiers changed from: private */
    public EncodeHandler pushhandler;
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

    class EncodeHandler extends Handler {
        EncodeHandler() {
        }

        public void handleMessage(Message message) {
            try {
                switch (message.what) {
                    case 0:
                        if (MediaCodecVideoEncoder.this.validCodec(MediaCodecVideoEncoder.this.codec)) {
                            MediaCodecVideoEncoder.this.encodePendingBuffers();
                            MediaCodecVideoEncoder.this.encodehandler.sendMessageDelayed(MediaCodecVideoEncoder.this.encodehandler.obtainMessage(0), 10);
                            return;
                        }
                        return;
                    case 1:
                        if (MediaCodecVideoEncoder.this.validCodec(MediaCodecVideoEncoder.this.codec)) {
                            MediaCodecVideoEncoder.this.PushPendingBuffers();
                            MediaCodecVideoEncoder.this.pushhandler.sendMessageDelayed(MediaCodecVideoEncoder.this.pushhandler.obtainMessage(1), 10);
                            return;
                        }
                        return;
                    case 2:
                        if (MediaCodecVideoEncoder.DEBUG.booleanValue()) {
                            Log.d(MediaCodecVideoEncoder.TAG, "handleMessage EXIT.");
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
            MediaCodecVideoEncoder.this.timeMap.put(String.valueOf(j), String.valueOf(j2));
        }
    }

    public MediaCodecVideoEncoder(int i, long j) {
        TAG = "HW-Encoder";
        this.native_context = j;
        this.frameQueue = new LinkedList<>();
        this.availableInputBufferIndices = new LinkedList<>();
        this.availableOutputBufferIndices = new LinkedList<>();
        this.availableOutputBufferInfos = new LinkedList<>();
        this.timeMap = new HashMap();
        encodeStarted = 0;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0026, code lost:
        if (feedInputBuffer() == false) goto L_0x0029;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0029, code lost:
        return;
     */
    public void PushPendingBuffers() {
        synchronized (this.codec) {
            if (validCodec(this.codec)) {
                int dequeueInputBuffer = this.codec.dequeueInputBuffer((long) 0);
                if (dequeueInputBuffer >= 0) {
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

    private Frame dequeueFrame() {
        Frame removeFirst;
        synchronized (this.frameQueue) {
            this.frameCount--;
            removeFirst = this.frameQueue.removeFirst();
        }
        return removeFirst;
    }

    private boolean drainOutputBuffer() {
        int i;
        int i2;
        String str;
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
        if (logInfoEnable()) {
            Log.d(TAG, "drainOutputBuffer--enter-- got one frame:" + intValue + "info.presentationTimeUs:" + peekFirst.presentationTimeUs + "info.size:" + peekFirst.size);
        }
        if ((peekFirst.flags & 4) != 0) {
            check(false, "Saw output end of stream.");
            Log.d(TAG, "Saw output end of stream");
            return false;
        }
        if ((peekFirst.flags & 2) != 0) {
            Log.d(TAG, "Got config bytes");
        }
        if ((peekFirst.flags & 1) != 0) {
            Log.d(TAG, "Got Sync Frame");
            i = 1;
        } else {
            i = 0;
        }
        String str2 = this.timeMap.get(String.valueOf(peekFirst.presentationTimeUs));
        if (str2 == null) {
            Log.e(TAG, "drainOutputBuffer timestap error from decoder -index:" + intValue + "info.presentationTimeUs:" + peekFirst.presentationTimeUs);
            if (this.native_context != 0) {
                Frame frame = new Frame(this.codecOutputBuffers[intValue], peekFirst.size, peekFirst.presentationTimeUs, -1, this.codecwidth, this.codecheight, i);
                SendFrame(frame, this.native_context);
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
            Log.d(TAG, "drainOutputBuffer before SendFrame:" + intValue + "info.presentationTimeUs:" + peekFirst.presentationTimeUs + "frametype:" + i);
        }
        if (this.native_context != 0) {
            Frame frame2 = r1;
            str = str2;
            i2 = i;
            Frame frame3 = new Frame(this.codecOutputBuffers[intValue], peekFirst.size, peekFirst.presentationTimeUs, parseLong, this.codecwidth, this.codecheight, i);
            SendFrame(frame2, this.native_context);
        } else {
            str = str2;
            i2 = i;
        }
        if (logInfoEnable()) {
            Log.d(TAG, "drainOutputBuffer after SendFrame:" + intValue + "info.presentationTimeUs:" + peekFirst.presentationTimeUs + "frametype:" + i2);
        }
        this.timeMap.remove(str);
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

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0097, code lost:
        if (drainOutputBuffer() == false) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x009a, code lost:
        return;
     */
    public void encodePendingBuffers() {
        synchronized (this.codec) {
            if (validCodec(this.codec)) {
                MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                int dequeueOutputBuffer = this.codec.dequeueOutputBuffer(bufferInfo, (long) 0);
                if (logInfoEnable()) {
                    String str = TAG;
                    Log.d(str, "dequeueOutputBuffer :index:" + dequeueOutputBuffer);
                }
                if (dequeueOutputBuffer >= 0) {
                    this.availableOutputBufferIndices.add(Integer.valueOf(dequeueOutputBuffer));
                    this.availableOutputBufferInfos.add(bufferInfo);
                } else if (dequeueOutputBuffer == -3) {
                    this.codecOutputBuffers = this.codec.getOutputBuffers();
                    String str2 = TAG;
                    Log.d(str2, "Output Buffer changed " + this.codecOutputBuffers);
                } else if (dequeueOutputBuffer == -2) {
                    MediaFormat outputFormat = this.codec.getOutputFormat();
                    String str3 = TAG;
                    Log.d(str3, "Media Format Changed " + outputFormat);
                } else if (dequeueOutputBuffer == -1) {
                }
            }
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
            try {
                this.frameQueue.wait();
                return false;
            } catch (InterruptedException e) {
                Log.w(TAG, "frameQueue.wait exception");
                return false;
            }
        }
    }

    private static boolean isPlatformSupported() {
        if (Build.MODEL.equals("MiTV2")) {
            if (DEBUG.booleanValue()) {
                Log.d(TAG, "isPlatformSupported MiTV2 model");
            }
            return true;
        } else if (Build.VERSION.SDK_INT < 19) {
            return false;
        } else {
            if (DEBUG.booleanValue()) {
                Log.d(TAG, "isPlatformSupported KK SDK");
            }
            return true;
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

    private void pushBuffer(byte[] bArr, int i, long j, long j2, int i2, int i3, int i4) {
        long j3;
        long j4;
        byte[] bArr2 = bArr;
        try {
            synchronized (this.frameQueue) {
                ByteBuffer allocate = ByteBuffer.allocate(((i2 * i3) * 3) / 2);
                allocate.put(bArr2);
                if (logInfoEnable()) {
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("len:");
                    sb.append(bArr2.length);
                    sb.append(" pushBuffer timestampUs:");
                    j4 = j;
                    sb.append(j4);
                    sb.append("ntp_timestapUs:");
                    j3 = j2;
                    sb.append(j3);
                    sb.append("frameCount:");
                    sb.append(this.frameCount);
                    Log.d(str, sb.toString());
                } else {
                    j4 = j;
                    j3 = j2;
                }
                if (this.frameCount > 30) {
                    this.frameCount--;
                    Log.d(TAG, "pushBuffer drop one frame in frameQueue");
                    this.frameQueue.removeFirst();
                }
                LinkedList<Frame> linkedList = this.frameQueue;
                Frame frame = new Frame(allocate, i, j4, j3, i2, i3, i4);
                linkedList.add(frame);
                this.frameCount++;
                this.frameNum++;
                if (this.frameNum > 1000) {
                    this.frameNum = 0;
                }
                this.frameQueue.notifyAll();
                if (encodeStarted == 0) {
                    this.encodehandler.sendMessage(this.encodehandler.obtainMessage(0));
                    encodeStarted++;
                }
            }
            if (this.frameCount > 10) {
                Log.d(TAG, "pushBuffer sleep, frameCount:" + this.frameCount);
                Thread.sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestKeyFrame() {
        if (isPlatformSupported()) {
            Log.d(TAG, "Sync frame request");
            Bundle bundle = new Bundle();
            bundle.putInt("request-sync", 0);
            this.codec.setParameters(bundle);
        }
    }

    private boolean setCodecState(int i, int i2, CodecName codecName, int i3, int i4) {
        this.codecwidth = i;
        this.codecheight = i2;
        this.bitRate = i4;
        this.fps = i3;
        this.format = new MediaFormat();
        this.format.setInteger("width", i);
        this.format.setInteger("height", i2);
        Log.v("setCodecState", "Init---framerate:" + i3 + " bitrate:" + i4);
        try {
            switch (codecName) {
                case HWH264:
                    this.format.setString("mime", "video/avc");
                    this.codec = MediaCodec.createEncoderByType("video/avc");
                    break;
                case ON2_VP8:
                    break;
                case GOOGLE_VPX:
                    break;
                case EXYNOX_VP8:
                    break;
                default:
                    return false;
            }
            this.format.setInteger("bitrate", i4 * 1000);
            this.format.setInteger("frame-rate", i3);
            this.format.setInteger("color-format", 19);
            if (!isPlatformSupported()) {
                this.format.setInteger("i-frame-interval", 2);
            } else {
                this.format.setInteger("i-frame-interval", 450);
            }
            synchronized (this.codec) {
                this.codec.configure(this.format, (Surface) null, (MediaCrypto) null, 1);
                this.codec.start();
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

    private boolean setRates(int i, int i2) {
        if (!isPlatformSupported()) {
            return false;
        }
        if (i != this.bitRate && i > 0) {
            this.bitRate = i;
        } else if (DEBUG.booleanValue()) {
            Log.d(TAG, "no need to set bitRate");
        }
        if (i2 != this.fps && i2 > 0 && i2 <= 30) {
            this.fps = i2;
        } else if (DEBUG.booleanValue()) {
            Log.d(TAG, "no need to set fps");
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("video-bitrate", i * 1000);
            this.codec.setParameters(bundle);
            return true;
        } catch (IllegalStateException e) {
            Log.e(TAG, "setRates failed", e);
            return false;
        }
    }

    private boolean start(int i, int i2, int i3, int i4) {
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
        if (!setCodecState(i, i2, CodecName.HWH264, i3, i4)) {
            return false;
        }
        startPushLooperThread();
        startEncodeLooperThread();
        this.pushhandler.sendMessage(this.pushhandler.obtainMessage(1));
        return true;
    }

    private void startEncodeLooperThread() {
        this.encodelooperThread = new Thread() {
            public void run() {
                Looper.prepare();
                EncodeHandler unused = MediaCodecVideoEncoder.this.encodehandler = new EncodeHandler();
                if (MediaCodecVideoEncoder.DEBUG.booleanValue()) {
                    Log.d(MediaCodecVideoEncoder.TAG, "startEncodeLooperThread Encoder-HW");
                }
                synchronized (MediaCodecVideoEncoder.this) {
                    MediaCodecVideoEncoder.this.notify();
                }
                Looper.loop();
            }
        };
        this.encodelooperThread.start();
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
                EncodeHandler unused = MediaCodecVideoEncoder.this.pushhandler = new EncodeHandler();
                if (MediaCodecVideoEncoder.DEBUG.booleanValue()) {
                    Log.d(MediaCodecVideoEncoder.TAG, "startPushLooperThread Encoder-HW");
                }
                synchronized (MediaCodecVideoEncoder.this) {
                    MediaCodecVideoEncoder.this.notify();
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

    public native void SendFrame(Frame frame, long j);

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
                encodeStarted = 0;
                this.native_context = 0;
                this.timeMap.clear();
            }
        }
    }

    public SurfaceView getView() {
        return this.surfaceView;
    }
}
