package org.webrtc.hwvideocodec;

import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import android.util.Log;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

/* compiled from: MediaCodecVideoDecoder */
class H264Decoder {
    private static final String AVC_MIME_TYPE = "video/avc";
    private static final int COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m = 2141391876;
    private static final int DEQUEUE_TIMEOUT = 100000;
    private static final String HEVC_MIME_TYPE = "video/hevc";
    private static final String TAG = "H264Decoder";
    private static final int[] supportedColorList = {19, 21, 2141391872, COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m};
    private static final String[] supportedHwCodecPrefixes = {"OMX.qcom.", "OMX.Nvidia.", "OMX.IMG.TOPAZ", "OMX.Exynos", "OMX.MTK", "OMX.hantro", "OMX.Intel", "OMX.IMG.MSVDX"};
    private int colorFormat;
    int counter = 0;
    private int cropBottom;
    private int cropLeft;
    private int cropRight;
    private int cropTop;
    private int dequedBufferIndex = -1;
    private boolean drop_frame;
    int frame_rate = 30;
    private int height;
    private ByteBuffer[] inputBuffers;
    boolean last_drop = false;
    private MediaCodec mediaCodec;
    private long nativeContext;
    long next_want_time = 0;
    private ByteBuffer[] outputBuffers;
    private int outputColorFormat;
    private Thread outputThread;
    long pre_time = 0;
    private Queue<Integer> queue = new LinkedList();
    /* access modifiers changed from: private */
    public volatile boolean running;
    private int sliceHeight;
    private int stride;
    int sum_time = 0;
    long timeStamp;
    private int width;

    /* compiled from: MediaCodecVideoDecoder */
    private static class DecoderProperties {
        public final String codecName;
        public final int colorFormat;

        DecoderProperties(String str, int i) {
            this.codecName = str;
            this.colorFormat = i;
        }
    }

    private void averageFrameRate(long j) {
        try {
            if (this.drop_frame) {
                if (this.pre_time != 0) {
                    int i = (int) (j - this.pre_time);
                    this.queue.offer(Integer.valueOf(i));
                    this.sum_time += i;
                    if (this.queue.size() > 25) {
                        this.sum_time -= ((Integer) this.queue.poll()).intValue();
                    }
                    this.frame_rate = (this.queue.size() * 1000) / this.sum_time;
                }
                this.pre_time = j;
            }
        } catch (Exception e2) {
            Log.e(TAG, "find exception at averageFrameRate:", e2);
        }
    }

    private Thread createOutputThread() {
        return new Thread("Mediacodec_outputThread") {
            public void run() {
                while (H264Decoder.this.running) {
                    H264Decoder.this.deliverDecodedFrame();
                }
            }
        };
    }

    private int dequeueInputBuffer() {
        try {
            return this.mediaCodec.dequeueInputBuffer(100000);
        } catch (Exception e2) {
            Log.e(TAG, "find exception at dequeueIntputBuffer:", e2);
            return -2;
        }
    }

    private int dequeueOutputBuffer() {
        String str = "slice-height";
        String str2 = "stride";
        String str3 = "color-format";
        String str4 = "crop-right";
        String str5 = "crop-left";
        String str6 = "crop-bottom";
        String str7 = "crop-top";
        String str8 = TAG;
        int i = -2;
        try {
            BufferInfo bufferInfo = new BufferInfo();
            int dequeueOutputBuffer = this.mediaCodec.dequeueOutputBuffer(bufferInfo, 100000);
            this.timeStamp = bufferInfo.presentationTimeUs;
            while (true) {
                if (dequeueOutputBuffer != -3) {
                    if (dequeueOutputBuffer != i) {
                        if ((bufferInfo.flags & 4) != 0) {
                            return -3;
                        }
                        return dequeueOutputBuffer;
                    }
                }
                if (dequeueOutputBuffer == -3) {
                    this.outputBuffers = this.mediaCodec.getOutputBuffers();
                } else if (dequeueOutputBuffer == i) {
                    MediaFormat outputFormat = this.mediaCodec.getOutputFormat();
                    StringBuilder sb = new StringBuilder();
                    sb.append("Format changed: ");
                    sb.append(outputFormat.toString());
                    Log.d(str8, sb.toString());
                    this.width = outputFormat.getInteger("width");
                    this.height = outputFormat.getInteger("height");
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("new width: ");
                    sb2.append(this.width);
                    sb2.append("new height:");
                    sb2.append(this.height);
                    Log.d(str8, sb2.toString());
                    if (outputFormat.containsKey(str7)) {
                        this.cropTop = outputFormat.getInteger(str7);
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Crop-top:");
                        sb3.append(this.cropTop);
                        Log.d(str8, sb3.toString());
                    }
                    if (outputFormat.containsKey(str6)) {
                        this.cropBottom = outputFormat.getInteger(str6);
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("Crop-bottom:");
                        sb4.append(this.cropBottom);
                        Log.d(str8, sb4.toString());
                    }
                    if (outputFormat.containsKey(str5)) {
                        this.cropLeft = outputFormat.getInteger(str5);
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("Crop-left:");
                        sb5.append(this.cropLeft);
                        Log.d(str8, sb5.toString());
                    }
                    if (outputFormat.containsKey(str4)) {
                        this.cropRight = outputFormat.getInteger(str4);
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append("Crop-right:");
                        sb6.append(this.cropRight);
                        Log.d(str8, sb6.toString());
                    }
                    if (outputFormat.containsKey(str3)) {
                        int integer = outputFormat.getInteger(str3);
                        this.colorFormat = integer;
                        this.outputColorFormat = integer;
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append("Color: 0x");
                        sb7.append(Integer.toHexString(this.colorFormat));
                        Log.d(str8, sb7.toString());
                        int[] iArr = supportedColorList;
                        int length = iArr.length;
                        boolean z = false;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= length) {
                                break;
                            }
                            if (this.colorFormat == iArr[i2]) {
                                z = true;
                                break;
                            }
                            i2++;
                        }
                        if (!z) {
                            Log.e(str8, "Non supported color format");
                            return -2;
                        }
                    }
                    if (outputFormat.containsKey(str2)) {
                        this.stride = outputFormat.getInteger(str2);
                    }
                    if (outputFormat.containsKey(str)) {
                        this.sliceHeight = outputFormat.getInteger(str);
                    }
                    StringBuilder sb8 = new StringBuilder();
                    sb8.append("Frame stride and slice height: ");
                    sb8.append(this.stride);
                    sb8.append(" x ");
                    sb8.append(this.sliceHeight);
                    Log.i(str8, sb8.toString());
                    this.stride = Math.max(this.width, this.stride);
                    this.sliceHeight = Math.max(this.height, this.sliceHeight);
                }
                int dequeueOutputBuffer2 = this.mediaCodec.dequeueOutputBuffer(bufferInfo, 100000);
                this.timeStamp = bufferInfo.presentationTimeUs;
                dequeueOutputBuffer = dequeueOutputBuffer2;
                i = -2;
            }
        } catch (Exception e2) {
            StringBuilder sb9 = new StringBuilder();
            sb9.append("find exception at dequeueOutputBuffer:");
            sb9.append(e2);
            Log.e(str8, sb9.toString());
            return -2;
        }
    }

    private boolean dropFrame(int i, long j) {
        try {
            int max = 1000 / Math.max(Math.min(i, 120), 8);
            boolean z = j - this.next_want_time > 0 && this.next_want_time != 0;
            if (this.next_want_time == 0) {
                this.next_want_time = ((long) max) + j;
            } else {
                this.next_want_time += (long) max;
            }
            if (Math.abs(j - this.next_want_time) > ((long) (max * 3))) {
                this.next_want_time = j + ((long) max);
            }
            return z;
        } catch (Exception e2) {
            Log.e(TAG, "find exception at averageFrameRate:", e2);
            return false;
        }
    }

    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    private static DecoderProperties findHwDecoder(String str) {
        DecoderProperties decoderProperties;
        String str2;
        int[] iArr;
        String[] strArr;
        int[] iArr2;
        String str3 = str;
        String str4 = TAG;
        ? r2 = 0;
        try {
            if (VERSION.SDK_INT < 19) {
                Log.i(str4, "sdk version too low");
                return null;
            }
            int i = 0;
            DecoderProperties decoderProperties2 = r2;
            while (i < MediaCodecList.getCodecCount()) {
                MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
                if (!codecInfoAt.isEncoder()) {
                    String[] supportedTypes = codecInfoAt.getSupportedTypes();
                    int length = supportedTypes.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            str2 = decoderProperties2;
                            break;
                        }
                        String str5 = supportedTypes[i2];
                        if (str5.equals(str3)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("mimeType is ");
                            sb.append(str5);
                            Log.i(str4, sb.toString());
                            String name = codecInfoAt.getName();
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("name is  ");
                            sb2.append(name);
                            Log.i(str4, sb2.toString());
                            str2 = name;
                            break;
                        }
                        i2++;
                    }
                    if (str2 != 0) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Found candidate decoder ");
                        sb3.append(str2);
                        Log.d(str4, sb3.toString());
                        CodecCapabilities capabilitiesForType = codecInfoAt.getCapabilitiesForType(str3);
                        for (int i3 : capabilitiesForType.colorFormats) {
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("   Color: 0x");
                            sb4.append(Integer.toHexString(i3));
                            Log.d(str4, sb4.toString());
                        }
                        for (String str6 : supportedHwCodecPrefixes) {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(" hwCodecPrefix :");
                            sb5.append(str6);
                            Log.i(str4, sb5.toString());
                            if (str2.startsWith(str6)) {
                                for (int i4 : supportedColorList) {
                                    int[] iArr3 = capabilitiesForType.colorFormats;
                                    int length2 = iArr3.length;
                                    int i5 = 0;
                                    while (i5 < length2) {
                                        try {
                                            int i6 = iArr3[i5];
                                            if (i6 == i4) {
                                                StringBuilder sb6 = new StringBuilder();
                                                sb6.append("Found target decoder ");
                                                sb6.append(str2);
                                                sb6.append(". Color: 0x");
                                                sb6.append(Integer.toHexString(i6));
                                                Log.d(str4, sb6.toString());
                                                return new DecoderProperties(str2, i6);
                                            }
                                            i5++;
                                        } catch (Exception e2) {
                                            e = e2;
                                            decoderProperties = 0;
                                            Log.e(str4, "find exception at findHwDecoder:", e);
                                            return decoderProperties;
                                        }
                                    }
                                }
                                continue;
                            }
                        }
                        continue;
                    }
                }
                i++;
                decoderProperties2 = 0;
            }
            return decoderProperties2;
        } catch (Exception e3) {
            e = e3;
            decoderProperties = r2;
            Log.e(str4, "find exception at findHwDecoder:", e);
            return decoderProperties;
        }
    }

    private static boolean isPlatformSupported() {
        return findHwDecoder(AVC_MIME_TYPE) != null;
    }

    private boolean queueInputBuffer(int i, int i2, long j) {
        try {
            this.inputBuffers[i].position(0);
            this.inputBuffers[i].limit(i2);
            this.mediaCodec.queueInputBuffer(i, 0, i2, j, 0);
            return true;
        } catch (Exception e2) {
            Log.e(TAG, "find exception at queueInputBuffer:", e2);
            return false;
        }
    }

    private boolean releaseOutputBuffer(int i) {
        try {
            this.mediaCodec.releaseOutputBuffer(i, false);
            return true;
        } catch (Exception e2) {
            Log.e(TAG, "find exception at releaseOutputBuffer:", e2);
            return false;
        }
    }

    public native void DeliverFrame(ByteBuffer byteBuffer, long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j2, int i8);

    public boolean decodeFrameInputStream(H264I420Frame h264I420Frame) {
        String str = TAG;
        boolean z = false;
        try {
            int dequeueInputBuffer = dequeueInputBuffer();
            if (dequeueInputBuffer >= 0) {
                ByteBuffer byteBuffer = this.inputBuffers[dequeueInputBuffer];
                byteBuffer.clear();
                byteBuffer.put(h264I420Frame.buffer);
                this.counter++;
                z = queueInputBuffer(dequeueInputBuffer, h264I420Frame.size, h264I420Frame.timeStamp);
            } else {
                Log.i(str, " get inputBuffer error, maybe discard a frame");
            }
            return z;
        } catch (Exception e2) {
            Log.e(str, "find exception at decodeFrameInputStream:", e2);
            return false;
        }
    }

    public void decodeFramePushPicture(boolean z) {
        String str;
        String str2 = TAG;
        if (z) {
            try {
                Log.i(str2, "flush decoder output queue");
            } catch (Exception e2) {
                e = e2;
                str = str2;
                Log.e(str, "find exception at decodeFramePushPicture:", e);
                return;
            }
        }
        int dequeueOutputBuffer = dequeueOutputBuffer();
        boolean z2 = dequeueOutputBuffer >= 0;
        if (z) {
            z2 = dequeueOutputBuffer == -3;
        }
        int i = 300;
        while (z2 && i > 0) {
            if (dequeueOutputBuffer >= 0) {
                this.counter--;
                if (this.nativeContext != 0) {
                    str = str2;
                    try {
                        DeliverFrame(this.outputBuffers[dequeueOutputBuffer], this.nativeContext, this.width, this.height, this.stride, this.cropTop, this.cropBottom, this.cropLeft, this.cropRight, this.timeStamp, this.outputColorFormat);
                    } catch (Exception e3) {
                        e = e3;
                        Log.e(str, "find exception at decodeFramePushPicture:", e);
                        return;
                    }
                } else {
                    str = str2;
                }
                releaseOutputBuffer(dequeueOutputBuffer);
            } else {
                str = str2;
                try {
                    Thread.sleep(10);
                    i -= 10;
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
            dequeueOutputBuffer = dequeueOutputBuffer();
            boolean z3 = dequeueOutputBuffer >= 0;
            if (z) {
                z3 = dequeueOutputBuffer != -3;
                str2 = str;
            } else {
                str2 = str;
            }
        }
    }

    public void deliverDecodedFrame() {
        String str;
        int i;
        String str2 = TAG;
        try {
            int dequeueOutputBuffer = dequeueOutputBuffer();
            if (dequeueOutputBuffer >= 0) {
                if (this.nativeContext != 0) {
                    averageFrameRate(System.currentTimeMillis());
                    if (!this.drop_frame || this.last_drop || !dropFrame(this.frame_rate, System.currentTimeMillis())) {
                        str = str2;
                        i = dequeueOutputBuffer;
                        try {
                            DeliverFrame(this.outputBuffers[dequeueOutputBuffer], this.nativeContext, this.width, this.height, this.stride, this.cropTop, this.cropBottom, this.cropLeft, this.cropRight, this.timeStamp, this.outputColorFormat);
                            this.last_drop = false;
                            releaseOutputBuffer(i);
                        } catch (Exception e2) {
                            e = e2;
                            StringBuilder sb = new StringBuilder();
                            sb.append("find exception at deliverOutPutsTimer:");
                            sb.append(e);
                            Log.e(str, sb.toString());
                        }
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("drop this frame! frame rate: ");
                        sb2.append(this.frame_rate);
                        Log.i(str2, sb2.toString());
                        this.last_drop = true;
                    }
                }
                String str3 = str2;
                i = dequeueOutputBuffer;
                releaseOutputBuffer(i);
            }
        } catch (Exception e3) {
            e = e3;
            str = str2;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("find exception at deliverOutPutsTimer:");
            sb3.append(e);
            Log.e(str, sb3.toString());
        }
    }

    public boolean flush() {
        decodeFramePushPicture(true);
        return true;
    }

    public boolean initDecoder(int i, int i2, int i3, int i4, boolean z, long j) throws IOException {
        String str = "color-format";
        String str2 = TAG;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("decoder init with:");
            sb.append(i2);
            sb.append(" height:");
            sb.append(i3);
            sb.append(" getWidth:");
            sb.append(" context:");
            sb.append(j);
            sb.append(" frameRate:");
            sb.append(i4);
            Log.i(str2, sb.toString());
            String str3 = i == 0 ? AVC_MIME_TYPE : i == 1 ? HEVC_MIME_TYPE : null;
            DecoderProperties findHwDecoder = findHwDecoder(str3);
            if (findHwDecoder == null) {
                return false;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Java initDecode: ");
            sb2.append(i2);
            sb2.append(" x ");
            sb2.append(i3);
            sb2.append(" drop_frame ");
            sb2.append(z);
            sb2.append(". Color: 0x");
            sb2.append(Integer.toHexString(findHwDecoder.colorFormat));
            Log.d(str2, sb2.toString());
            this.width = i2;
            this.height = i3;
            this.drop_frame = z;
            this.stride = i2;
            this.sliceHeight = i3;
            this.cropTop = 0;
            this.cropBottom = i3 - 1;
            this.cropLeft = 0;
            this.cropRight = i2 - 1;
            this.dequedBufferIndex = -1;
            this.nativeContext = j;
            this.timeStamp = 0;
            MediaFormat createVideoFormat = MediaFormat.createVideoFormat(str3, i2, i3);
            if (i4 == 0) {
                i4 = 60;
            }
            createVideoFormat.setFloat("frame-rate", (float) i4);
            this.mediaCodec = MediaCodec.createByCodecName(findHwDecoder.codecName);
            if (this.mediaCodec == null) {
                Log.i("hevc decoder", "decoder init error null");
                return false;
            }
            this.mediaCodec.configure(createVideoFormat, null, null, 0);
            this.mediaCodec.start();
            this.colorFormat = findHwDecoder.colorFormat;
            this.outputBuffers = this.mediaCodec.getOutputBuffers();
            this.inputBuffers = this.mediaCodec.getInputBuffers();
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Input buffers: ");
            sb3.append(this.inputBuffers.length);
            sb3.append(". Output buffers: ");
            sb3.append(this.outputBuffers.length);
            Log.d(str2, sb3.toString());
            MediaFormat outputFormat = this.mediaCodec.getOutputFormat();
            if (outputFormat.containsKey(str)) {
                this.outputColorFormat = outputFormat.getInteger(str);
            }
            this.running = true;
            this.outputThread = createOutputThread();
            this.outputThread.start();
            Log.i(str2, "decoder init done");
            return true;
        } catch (Exception e2) {
            Log.e(str2, "find exception at initDecode :", e2);
            return false;
        }
    }

    public void release() {
        String str = TAG;
        try {
            Log.i(str, "decoder release begin");
            this.running = false;
            this.mediaCodec.stop();
            this.mediaCodec.release();
            this.mediaCodec = null;
            Log.i(str, "decoder release done");
        } catch (Exception e2) {
            Log.e(str, "find exception at release:", e2);
        }
    }
}
