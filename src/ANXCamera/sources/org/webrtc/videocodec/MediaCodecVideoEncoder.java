package org.webrtc.videocodec;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodec.CryptoException;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

class MediaCodecVideoEncoder {
    public static final Boolean DEBUG = Boolean.valueOf(true);
    public static final int ENCODE = 0;
    public static final int EXIT = 2;
    public static final int PUSHBUFFER = 1;
    /* access modifiers changed from: private */
    public static String TAG;
    private static int encodeStarted = 0;
    private LinkedList<Integer> availableInputBufferIndices;
    private LinkedList<Integer> availableOutputBufferIndices;
    private LinkedList<BufferInfo> availableOutputBufferInfos;
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

    /* renamed from: org.webrtc.videocodec.MediaCodecVideoEncoder$4 reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$org$webrtc$videocodec$MediaCodecVideoEncoder$CodecName = new int[CodecName.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            $SwitchMap$org$webrtc$videocodec$MediaCodecVideoEncoder$CodecName[CodecName.HWH264.ordinal()] = 1;
            $SwitchMap$org$webrtc$videocodec$MediaCodecVideoEncoder$CodecName[CodecName.ON2_VP8.ordinal()] = 2;
            $SwitchMap$org$webrtc$videocodec$MediaCodecVideoEncoder$CodecName[CodecName.GOOGLE_VPX.ordinal()] = 3;
            try {
                $SwitchMap$org$webrtc$videocodec$MediaCodecVideoEncoder$CodecName[CodecName.EXYNOX_VP8.ordinal()] = 4;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

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
                int i = message.what;
                if (i != 0) {
                    if (i != 1) {
                        if (i == 2) {
                            if (MediaCodecVideoEncoder.DEBUG.booleanValue()) {
                                Log.d(MediaCodecVideoEncoder.TAG, "handleMessage EXIT.");
                            }
                        }
                    } else if (MediaCodecVideoEncoder.this.validCodec(MediaCodecVideoEncoder.this.codec)) {
                        MediaCodecVideoEncoder.this.PushPendingBuffers();
                        MediaCodecVideoEncoder.this.pushhandler.sendMessageDelayed(MediaCodecVideoEncoder.this.pushhandler.obtainMessage(1), 10);
                    }
                } else if (MediaCodecVideoEncoder.this.validCodec(MediaCodecVideoEncoder.this.codec)) {
                    MediaCodecVideoEncoder.this.encodePendingBuffers();
                    MediaCodecVideoEncoder.this.encodehandler.sendMessageDelayed(MediaCodecVideoEncoder.this.encodehandler.obtainMessage(0), 10);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
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
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0025, code lost:
        if (feedInputBuffer() == false) goto L_0x0028;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0028, code lost:
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
            AlertDialog create = new Builder(this.context).create();
            create.setTitle("WebRTC Error");
            create.setMessage(str);
            create.setButton(-1, "OK", new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            create.show();
        }
    }

    private Frame dequeueFrame() {
        Frame frame;
        synchronized (this.frameQueue) {
            this.frameCount--;
            frame = (Frame) this.frameQueue.removeFirst();
        }
        return frame;
    }

    private boolean drainOutputBuffer() {
        int i;
        int i2;
        String str;
        if (this.availableOutputBufferIndices.isEmpty()) {
            try {
                Thread.sleep(10);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return false;
        }
        int intValue = ((Integer) this.availableOutputBufferIndices.peekFirst()).intValue();
        BufferInfo bufferInfo = (BufferInfo) this.availableOutputBufferInfos.peekFirst();
        if (logInfoEnable()) {
            String str2 = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("drainOutputBuffer--enter-- got one frame:");
            sb.append(intValue);
            sb.append("info.presentationTimeUs:");
            sb.append(bufferInfo.presentationTimeUs);
            sb.append("info.size:");
            sb.append(bufferInfo.size);
            Log.d(str2, sb.toString());
        }
        int i3 = bufferInfo.flags;
        if ((i3 & 4) != 0) {
            check(false, "Saw output end of stream.");
            Log.d(TAG, "Saw output end of stream");
            return false;
        }
        if ((i3 & 2) != 0) {
            Log.d(TAG, "Got config bytes");
        }
        if ((bufferInfo.flags & 1) != 0) {
            Log.d(TAG, "Got Sync Frame");
            i = 1;
        } else {
            i = 0;
        }
        String str3 = (String) this.timeMap.get(String.valueOf(bufferInfo.presentationTimeUs));
        if (str3 == null) {
            String str4 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("drainOutputBuffer timestap error from decoder -index:");
            sb2.append(intValue);
            sb2.append("info.presentationTimeUs:");
            sb2.append(bufferInfo.presentationTimeUs);
            Log.e(str4, sb2.toString());
            if (this.native_context != 0) {
                Frame frame = new Frame(this.codecOutputBuffers[intValue], bufferInfo.size, bufferInfo.presentationTimeUs, -1, this.codecwidth, this.codecheight, i);
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
        long parseLong = Long.parseLong(str3);
        if (logInfoEnable()) {
            String str5 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("drainOutputBuffer before SendFrame:");
            sb3.append(intValue);
            sb3.append("info.presentationTimeUs:");
            sb3.append(bufferInfo.presentationTimeUs);
            sb3.append("frametype:");
            sb3.append(i);
            Log.d(str5, sb3.toString());
        }
        if (this.native_context != 0) {
            Frame frame2 = r1;
            str = str3;
            i2 = i;
            Frame frame3 = new Frame(this.codecOutputBuffers[intValue], bufferInfo.size, bufferInfo.presentationTimeUs, parseLong, this.codecwidth, this.codecheight, i);
            SendFrame(frame2, this.native_context);
        } else {
            str = str3;
            i2 = i;
        }
        if (logInfoEnable()) {
            String str6 = TAG;
            StringBuilder sb4 = new StringBuilder();
            sb4.append("drainOutputBuffer after SendFrame:");
            sb4.append(intValue);
            sb4.append("info.presentationTimeUs:");
            sb4.append(bufferInfo.presentationTimeUs);
            sb4.append("frametype:");
            sb4.append(i2);
            Log.d(str6, sb4.toString());
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
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0095, code lost:
        if (drainOutputBuffer() == false) goto L_0x0098;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0098, code lost:
        return;
     */
    public void encodePendingBuffers() {
        synchronized (this.codec) {
            if (validCodec(this.codec)) {
                BufferInfo bufferInfo = new BufferInfo();
                int dequeueOutputBuffer = this.codec.dequeueOutputBuffer(bufferInfo, (long) 0);
                if (logInfoEnable()) {
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("dequeueOutputBuffer :index:");
                    sb.append(dequeueOutputBuffer);
                    Log.d(str, sb.toString());
                }
                if (dequeueOutputBuffer >= 0) {
                    this.availableOutputBufferIndices.add(Integer.valueOf(dequeueOutputBuffer));
                    this.availableOutputBufferInfos.add(bufferInfo);
                } else if (dequeueOutputBuffer == -3) {
                    this.codecOutputBuffers = this.codec.getOutputBuffers();
                    String str2 = TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Output Buffer changed ");
                    sb2.append(this.codecOutputBuffers);
                    Log.d(str2, sb2.toString());
                } else if (dequeueOutputBuffer == -2) {
                    MediaFormat outputFormat = this.codec.getOutputFormat();
                    String str3 = TAG;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Media Format Changed ");
                    sb3.append(outputFormat);
                    Log.d(str3, sb3.toString());
                } else if (dequeueOutputBuffer == -1) {
                }
            }
        }
    }

    private boolean feedInputBuffer() {
        if (this.availableInputBufferIndices.isEmpty()) {
            try {
                Thread.sleep(10);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return false;
        } else if (!hasFrame()) {
            try {
                Thread.sleep(10);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            return false;
        } else {
            Frame dequeueFrame = dequeueFrame();
            ByteBuffer byteBuffer = dequeueFrame.buffer;
            int intValue = ((Integer) this.availableInputBufferIndices.pollFirst()).intValue();
            ByteBuffer byteBuffer2 = this.codecInputBuffers[intValue];
            check(byteBuffer2.capacity() >= byteBuffer.capacity(), "Buffer is too small to copy a frame.");
            byteBuffer.rewind();
            byteBuffer2.rewind();
            byteBuffer2.put(byteBuffer);
            try {
                if (logInfoEnable()) {
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("feedInputBuffer -index:");
                    sb.append(intValue);
                    sb.append("frame.timestampUs:");
                    sb.append(dequeueFrame.timestampUs);
                    Log.d(str, sb.toString());
                }
                synchronized (this.codec) {
                    if (!validCodec(this.codec)) {
                        return false;
                    }
                    this.codec.queueInputBuffer(intValue, 0, byteBuffer.capacity(), dequeueFrame.timestampUs, 0);
                }
            } catch (CryptoException e4) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("CryptoException w/ errorCode ");
                sb2.append(e4.getErrorCode());
                sb2.append(", '");
                sb2.append(e4.getMessage());
                sb2.append("'");
                check(false, sb2.toString());
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
            } catch (InterruptedException unused) {
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
        } else if (VERSION.SDK_INT < 19) {
            return false;
        } else {
            if (DEBUG.booleanValue()) {
                Log.d(TAG, "isPlatformSupported KK SDK");
            }
            return true;
        }
    }

    private boolean logInfoEnable() {
        if (!DEBUG.booleanValue()) {
            return false;
        }
        long j = this.frameNum;
        return j < 60 || 0 == j % 50;
    }

    private long mediaTimeToSystemTime(long j) {
        if (this.deltaTimeUs == -1) {
            this.deltaTimeUs = (System.currentTimeMillis() * 1000) - j;
        }
        return this.deltaTimeUs + j;
    }

    private void pushBuffer(byte[] bArr, int i, long j, long j2, int i2, int i3, int i4) {
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
                    sb.append(j);
                    sb.append("ntp_timestapUs:");
                    sb.append(j2);
                    sb.append("frameCount:");
                    sb.append(this.frameCount);
                    Log.d(str, sb.toString());
                } else {
                    long j3 = j;
                    long j4 = j2;
                }
                if (this.frameCount > 30) {
                    this.frameCount--;
                    Log.d(TAG, "pushBuffer drop one frame in frameQueue");
                    this.frameQueue.removeFirst();
                }
                LinkedList<Frame> linkedList = this.frameQueue;
                Frame frame = new Frame(allocate, i, j, j2, i2, i3, i4);
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
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("pushBuffer sleep, frameCount:");
                sb2.append(this.frameCount);
                Log.d(str2, sb2.toString());
                Thread.sleep(10);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
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
        StringBuilder sb = new StringBuilder();
        sb.append("Init---framerate:");
        sb.append(i3);
        sb.append(" bitrate:");
        sb.append(i4);
        Log.v("setCodecState", sb.toString());
        try {
            int i5 = AnonymousClass4.$SwitchMap$org$webrtc$videocodec$MediaCodecVideoEncoder$CodecName[codecName.ordinal()];
            if (i5 == 1) {
                this.format.setString("mime", "video/avc");
                this.codec = MediaCodec.createEncoderByType("video/avc");
            } else if (!(i5 == 2 || i5 == 3 || i5 == 4)) {
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
                this.codec.configure(this.format, null, null, 1);
                this.codec.start();
                this.codecInputBuffers = this.codec.getInputBuffers();
                this.codecOutputBuffers = this.codec.getOutputBuffers();
            }
            return true;
        } catch (Exception e2) {
            if (e2 instanceof IOException) {
                Log.e(TAG, "Failed to create MediaCodec for VP8.", e2);
                return false;
            }
            throw new RuntimeException(e2);
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
        } catch (IllegalStateException e2) {
            Log.e(TAG, "setRates failed", e2);
            return false;
        }
    }

    private boolean start(int i, int i2, int i3, int i4) {
        this.deltaTimeUs = -1;
        MediaCodec mediaCodec = this.codec;
        if (mediaCodec != null) {
            synchronized (mediaCodec) {
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
        EncodeHandler encodeHandler = this.pushhandler;
        encodeHandler.sendMessage(encodeHandler.obtainMessage(1));
        return true;
    }

    private void startEncodeLooperThread() {
        this.encodelooperThread = new Thread() {
            public void run() {
                Looper.prepare();
                MediaCodecVideoEncoder mediaCodecVideoEncoder = MediaCodecVideoEncoder.this;
                mediaCodecVideoEncoder.encodehandler = new EncodeHandler();
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
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void startPushLooperThread() {
        this.pushlooperThread = new Thread() {
            public void run() {
                Looper.prepare();
                MediaCodecVideoEncoder mediaCodecVideoEncoder = MediaCodecVideoEncoder.this;
                mediaCodecVideoEncoder.pushhandler = new EncodeHandler();
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
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean validCodec(MediaCodec mediaCodec) {
        if (mediaCodec != null) {
            return true;
        }
        if (DEBUG.booleanValue()) {
            Log.d(TAG, "validCodec codec is null.");
        }
        return false;
    }

    public native void ChangeResolution(int i, int i2, long j);

    public native void SendFrame(Frame frame, long j);

    public void dispose() {
        if (DEBUG.booleanValue()) {
            Log.d(TAG, "dispose");
        }
        MediaCodec mediaCodec = this.codec;
        if (mediaCodec != null) {
            synchronized (mediaCodec) {
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
