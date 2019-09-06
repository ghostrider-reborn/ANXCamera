package org.webrtc.hwvideocodec;

import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import java.nio.ByteBuffer;

/* compiled from: MediaCodecVideoEncoder */
class H264Encoder {
    private static final String AVC_MIME_TYPE = "video/avc";
    private static final String HEVC_MIME_TYPE = "video/hevc";
    private static final String TAG = "H264Encoder";
    private static final int VIDEO_ControlRateConstant = 2;
    private static boolean isQcomPlatform;
    private static final int[] supportedColorList = {21, 19};
    private static final String[] supportedHwCodecPrefixes = {"OMX.qcom.", "OMX.Nvidia.", "OMX.IMG.TOPAZ", "OMX.Exynos", "OMX.MTK", "OMX.hantro", "OMX.Intel", "OMX.ARM"};
    private boolean Constructed = false;
    int counter = 0;
    private int dequedBufferIndex = -1;
    int frameCounter = 0;
    int m_height;
    byte[] m_info = null;
    int m_width;
    private MediaCodec mediaCodec;
    private long nativeContext = 0;
    private int supportColorFormat = 0;

    /* compiled from: MediaCodecVideoEncoder */
    private static class EncoderProperties {
        public final String codecName;
        public final int colorFormat;

        EncoderProperties(String str, int i) {
            this.codecName = str;
            this.colorFormat = i;
        }
    }

    H264Encoder() {
    }

    public static int byteArrayToInt(byte[] bArr) {
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            i += (bArr[i2] & 255) << ((3 - i2) * 8);
        }
        return i;
    }

    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    private static EncoderProperties findHwEncoder(String str) {
        String str2;
        String str3;
        String[] supportedTypes;
        String str4 = str;
        String str5 = TAG;
        ? r2 = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("sdk version is: ");
            sb.append(VERSION.SDK_INT);
            Log.i(str5, sb.toString());
            if (VERSION.SDK_INT < 16) {
                return null;
            }
            int i = 0;
            int i2 = 0;
            while (true) {
                str2 = "codec name: ";
                if (i2 >= MediaCodecList.getCodecCount()) {
                    break;
                }
                MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i2);
                if (codecInfoAt.isEncoder()) {
                    for (String str6 : codecInfoAt.getSupportedTypes()) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str2);
                        sb2.append(str6);
                        sb2.append(" company:");
                        sb2.append(codecInfoAt.getName());
                        Log.i(str5, sb2.toString());
                    }
                }
                i2++;
            }
            int i3 = 0;
            EncoderProperties encoderProperties = r2;
            while (i3 < MediaCodecList.getCodecCount()) {
                MediaCodecInfo codecInfoAt2 = MediaCodecList.getCodecInfoAt(i3);
                if (codecInfoAt2.isEncoder()) {
                    String[] supportedTypes2 = codecInfoAt2.getSupportedTypes();
                    int length = supportedTypes2.length;
                    int i4 = i;
                    while (true) {
                        if (i4 >= length) {
                            str3 = encoderProperties;
                            break;
                        }
                        String str7 = supportedTypes2[i4];
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(str2);
                        sb3.append(str7);
                        Log.i(str5, sb3.toString());
                        if (str7.equals(str4)) {
                            str3 = codecInfoAt2.getName();
                            break;
                        }
                        i4++;
                    }
                    if (str3 != 0) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("Found candidate encoder ");
                        sb4.append(str3);
                        Log.i(str5, sb4.toString());
                        CodecCapabilities capabilitiesForType = codecInfoAt2.getCapabilitiesForType(str4);
                        int[] iArr = capabilitiesForType.colorFormats;
                        int length2 = iArr.length;
                        for (int i5 = i; i5 < length2; i5++) {
                            int i6 = iArr[i5];
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append("   Color: 0x");
                            sb5.append(Integer.toHexString(i6));
                            Log.i(str5, sb5.toString());
                        }
                        isQcomPlatform = str3.startsWith(supportedHwCodecPrefixes[i]);
                        String[] strArr = supportedHwCodecPrefixes;
                        int length3 = strArr.length;
                        int i7 = i;
                        while (i7 < length3) {
                            if (str3.startsWith(strArr[i7])) {
                                int[] iArr2 = supportedColorList;
                                int length4 = iArr2.length;
                                int i8 = i;
                                while (i8 < length4) {
                                    int i9 = iArr2[i8];
                                    int[] iArr3 = capabilitiesForType.colorFormats;
                                    int length5 = iArr3.length;
                                    int i10 = 0;
                                    while (i10 < length5) {
                                        int i11 = iArr3[i10];
                                        if (i11 == i9) {
                                            StringBuilder sb6 = new StringBuilder();
                                            sb6.append("Found target encoder ");
                                            sb6.append(str3);
                                            sb6.append(". Color: 0x");
                                            sb6.append(Integer.toHexString(i11));
                                            Log.i(str5, sb6.toString());
                                            return new EncoderProperties(str3, i11);
                                        }
                                        i10++;
                                        String str8 = str;
                                    }
                                    i8++;
                                    String str9 = str;
                                }
                                continue;
                            }
                            i7++;
                            String str10 = str;
                            i = 0;
                        }
                        continue;
                    }
                }
                i3++;
                str4 = str;
                i = 0;
                encoderProperties = 0;
            }
            return encoderProperties;
        } catch (Exception e2) {
            Log.e(str5, "find exception at findHwEncoder:", e2);
            return null;
        }
    }

    public static void intToByteArray(int i, byte[] bArr) {
        bArr[0] = (byte) ((i >> 24) & 255);
        bArr[1] = (byte) ((i >> 16) & 255);
        bArr[2] = (byte) ((i >> 8) & 255);
        bArr[3] = (byte) (i & 255);
    }

    private static boolean isPlatformSupported() {
        return findHwEncoder(AVC_MIME_TYPE) != null;
    }

    public native void SendFrame(byte[] bArr, long j, long j2, boolean z);

    public boolean encodeFrameInput(H264I420Frame h264I420Frame, boolean z) {
        String str = TAG;
        if (z) {
            try {
                Log.i(str, "force a key frame");
            } catch (Exception e2) {
                Log.e(str, "find exception at encodeFrameInput encoder:", e2);
                return false;
            }
        }
        int dequeueInputBuffer = this.mediaCodec.dequeueInputBuffer(-1);
        if (z && VERSION.SDK_INT >= 19) {
            Bundle bundle = new Bundle();
            bundle.putInt("request-sync", 0);
            this.mediaCodec.setParameters(bundle);
        }
        this.counter++;
        if (dequeueInputBuffer < 0) {
            return false;
        }
        ByteBuffer[] inputBuffers = this.mediaCodec.getInputBuffers();
        this.frameCounter++;
        ByteBuffer byteBuffer = inputBuffers[dequeueInputBuffer];
        byteBuffer.clear();
        byteBuffer.put(h264I420Frame.buffer);
        this.mediaCodec.queueInputBuffer(dequeueInputBuffer, 0, h264I420Frame.size, h264I420Frame.timeStamp * 1000, 0);
        encodeFrameOutput(false);
        return true;
    }

    public void encodeFrameOutput(boolean z) {
        String str = TAG;
        try {
            BufferInfo bufferInfo = new BufferInfo();
            if (z) {
                Log.i(str, "flush output queue");
            }
            int dequeueOutputBuffer = this.mediaCodec.dequeueOutputBuffer(bufferInfo, 0);
            boolean z2 = dequeueOutputBuffer >= 0;
            if (z) {
                z2 = (bufferInfo.flags & 4) == 0;
            }
            int i = 300;
            while (z2 && i > 0) {
                if (dequeueOutputBuffer >= 0) {
                    int i2 = bufferInfo.size;
                    long j = bufferInfo.presentationTimeUs / 1000;
                    ByteBuffer[] outputBuffers = this.mediaCodec.getOutputBuffers();
                    byte[] bArr = new byte[i2];
                    this.frameCounter--;
                    outputBuffers[dequeueOutputBuffer].get(bArr);
                    byte b2 = bArr[4] & 31;
                    boolean z3 = b2 >= 5 && b2 <= 8;
                    if (z3) {
                        Log.i(str, "h264 add frame header  cdr flag");
                    }
                    SendFrame(bArr, this.nativeContext, j, z3);
                    this.mediaCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                } else {
                    try {
                        Thread.sleep(10);
                        i -= 10;
                    } catch (Exception e2) {
                        Log.e(str, "find exception at ThreadSleep:", e2);
                    }
                }
                dequeueOutputBuffer = this.mediaCodec.dequeueOutputBuffer(bufferInfo, 0);
                boolean z4 = dequeueOutputBuffer >= 0;
                if (z) {
                    z4 = (bufferInfo.flags & 4) == 0;
                }
            }
        } catch (Exception e3) {
            Log.e(str, "find exception at encodeFrameOutput:", e3);
        }
    }

    public boolean flush() {
        encodeFrameOutput(true);
        return true;
    }

    public boolean initEncoder(int i, int i2, int i3, int i4, int i5, long j, boolean z) {
        String str = TAG;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("H264 encoder creat width");
            sb.append(i2);
            sb.append("height:");
            sb.append(i3);
            sb.append("framerate:");
            sb.append(i4);
            sb.append("bitrate:");
            sb.append(i5);
            sb.append("this:");
            sb.append(this);
            Log.i(str, sb.toString());
            String str2 = i == 0 ? AVC_MIME_TYPE : i == 1 ? HEVC_MIME_TYPE : null;
            this.m_width = i2;
            this.m_height = i3;
            this.nativeContext = j;
            this.m_info = null;
            this.dequedBufferIndex = -1;
            EncoderProperties findHwEncoder = findHwEncoder(str2);
            if (findHwEncoder == null) {
                Log.i(str, "Can not find HW AVC encoder");
                return false;
            }
            this.mediaCodec = MediaCodec.createByCodecName(findHwEncoder.codecName);
            if (this.mediaCodec == null) {
                Log.i(str, "creatByCodecName failed");
                return false;
            }
            MediaFormat createVideoFormat = MediaFormat.createVideoFormat(str2, i2, i3);
            createVideoFormat.setInteger("bitrate", i5);
            createVideoFormat.setInteger("frame-rate", i4);
            if (!z) {
                createVideoFormat.setInteger("bitrate-mode", 2);
            }
            this.supportColorFormat = findHwEncoder.colorFormat;
            createVideoFormat.setInteger("color-format", findHwEncoder.colorFormat);
            createVideoFormat.setInteger("i-frame-interval", 2);
            this.mediaCodec.configure(createVideoFormat, null, null, 1);
            this.mediaCodec.start();
            this.Constructed = true;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("avc encoder creat done, isSemiPlanar:");
            sb2.append(findHwEncoder.colorFormat == 21);
            Log.i(str, sb2.toString());
            return true;
        } catch (Exception e2) {
            Log.e(str, "find exception at initEncoder:", e2);
            return false;
        }
    }

    public boolean isSemiPlanarSupport() {
        return this.supportColorFormat == 21;
    }

    public void release() {
        String str = TAG;
        try {
            this.Constructed = false;
            Log.i(str, "avc encoder release begin");
            this.mediaCodec.stop();
            this.mediaCodec.release();
            this.mediaCodec = null;
            this.m_info = null;
            Log.i(str, "avc encoder release done");
        } catch (Exception e2) {
            Log.e(str, "find exception at release encoder:", e2);
        }
    }

    public void reset() {
        String str = TAG;
        try {
            if (this.Constructed) {
                this.mediaCodec.flush();
                Log.i(str, "avc encoder reset done");
            }
        } catch (Exception e2) {
            Log.e(str, "find exception at reset encoder:", e2);
        }
    }

    public void setBitrate(int i) {
        String str = TAG;
        try {
            if (this.Constructed && VERSION.SDK_INT >= 19) {
                StringBuilder sb = new StringBuilder();
                sb.append("setRates: ");
                sb.append(i);
                sb.append(" kbps ");
                Log.i(str, sb.toString());
                Bundle bundle = new Bundle();
                bundle.putInt("video-bitrate", i);
                this.mediaCodec.setParameters(bundle);
            }
        } catch (Exception e2) {
            Log.e(str, "find exception at setBitrate encoder:", e2);
        }
    }
}
