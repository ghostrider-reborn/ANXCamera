package com.ss.android.medialib;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.opengl.GLES20;
import android.os.Build;
import android.util.Pair;
import android.view.Surface;
import com.android.camera.constant.DurationConstant;
import com.ss.android.medialib.common.TextureDrawer;
import com.ss.android.medialib.log.VEMonitorKeys;
import com.ss.android.ttve.monitor.TEMonitor;
import com.ss.android.ttve.monitor.TEMonitorNewKeys;
import com.ss.android.vesdk.VELogUtil;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.Queue;

@TargetApi(18)
public class AVCEncoder {
    private static final String[] BITRATE_MODES = {"BITRATE_MODE_CQ", "BITRATE_MODE_VBR", "BITRATE_MODE_CBR"};
    private static final boolean DEBUG = true;
    private static final String TAG = "AVCEncoder";
    private static int TIMEOUT_USEC = 5000;
    static AVCEncoderInterface mEncoderCaller = null;
    private byte[] codec_config;
    BufferedOutputStream fileWriter = null;
    ByteBuffer[] inputBuffers;
    MediaCodec.BufferInfo mBufferInfo = null;
    private String mCodecName = "video/avc";
    private int mColorFormat;
    private int mDrawCount = 0;
    private int mEncodeCount = 0;
    private boolean mFlag = false;
    int mFrameRate = 30;
    private int mHeight;
    private boolean mIsError = false;
    private MediaCodec mMediaCodec = null;
    private MediaCodecInfo mMediaCodecInfo = null;
    private Queue<Pair<Integer, Integer>> mPTSQueue = new LinkedList();
    private int mProfile = 1;
    private Surface mSurface;
    private TextureDrawer mTextureDrawer;
    private int mWidth;
    ByteBuffer[] outputBuffers;
    int status = 0;

    private interface Status {
        public static final int INITED = 1;
        public static final int STARTED = 2;
        public static final int STOPPED = 3;
        public static final int UNSET = 0;
    }

    static {
        new Thread(new Runnable() {
            public void run() {
                synchronized (AVCEncoder.class) {
                    MediaCodecList.getCodecCount();
                }
            }
        }).start();
    }

    private MediaCodecInfo getMediaCodecInfo() {
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                String name = codecInfoAt.getName();
                if (!name.startsWith("OMX.google.") && !name.startsWith("OMX.Nvidia.") && !name.equals("OMX.TI.DUCATI1.VIDEO.H264E")) {
                    String[] supportedTypes = codecInfoAt.getSupportedTypes();
                    for (String equalsIgnoreCase : supportedTypes) {
                        if (equalsIgnoreCase.equalsIgnoreCase(this.mCodecName)) {
                            return codecInfoAt;
                        }
                    }
                    continue;
                }
            }
        }
        return null;
    }

    @TargetApi(21)
    private MediaCodecInfo getMediaCodecInfo21() {
        MediaCodecInfo[] codecInfos = new MediaCodecList(1).getCodecInfos();
        if (codecInfos == null || codecInfos.length == 0) {
            return null;
        }
        for (MediaCodecInfo mediaCodecInfo : codecInfos) {
            if (mediaCodecInfo != null && mediaCodecInfo.isEncoder()) {
                String name = mediaCodecInfo.getName();
                if (!name.startsWith("OMX.google.") && !name.startsWith("OMX.Nvidia.") && !name.equals("OMX.TI.DUCATI1.VIDEO.H264E")) {
                    String[] supportedTypes = mediaCodecInfo.getSupportedTypes();
                    for (String equalsIgnoreCase : supportedTypes) {
                        if (equalsIgnoreCase.equalsIgnoreCase(this.mCodecName)) {
                            return mediaCodecInfo;
                        }
                    }
                    continue;
                }
            }
        }
        return null;
    }

    private int getOneColorFormat() {
        int[] colorFormats = getColorFormats();
        if (colorFormats == null) {
            return -1;
        }
        for (int i = 0; i < colorFormats.length; i++) {
            if (colorFormats[i] == 2130708361) {
                VELogUtil.i(TAG, "====== mColorFormat support COLOR_FormatSurface ======");
                return colorFormats[i];
            }
        }
        return -1;
    }

    public static void setDrainWaitTimeout(int i) {
        TIMEOUT_USEC = i;
    }

    private void testCode(boolean z) {
        ByteBuffer order = ByteBuffer.allocateDirect(this.mWidth * this.mHeight * 4).order(ByteOrder.nativeOrder());
        GLES20.glReadPixels(0, 0, this.mWidth, this.mHeight, 6408, 5121, order);
        try {
            if (this.fileWriter == null) {
                this.fileWriter = new BufferedOutputStream(new FileOutputStream("/storage/emulated/0/xzw/rgbaBig.rgba"));
            }
            this.fileWriter.write(order.array());
            try {
                if (this.fileWriter != null) {
                    this.fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            if (this.fileWriter != null) {
                this.fileWriter.close();
            }
        } catch (Throwable th) {
            try {
                if (this.fileWriter != null) {
                    this.fileWriter.close();
                }
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            throw th;
        }
        Bitmap createBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
        createBitmap.copyPixelsFromBuffer(order);
        if (z) {
            saveBitmap(createBitmap, "/sdcard/aweme/picture/record_e.jpeg");
        } else {
            saveBitmap(createBitmap, "/sdcard/aweme/picture/record_s.jpeg");
        }
        createBitmap.recycle();
    }

    public synchronized void createEncoder() {
        if (this.status == 0) {
            this.mColorFormat = getOneColorFormat();
            if (this.mColorFormat >= 0) {
                try {
                    this.mMediaCodec = MediaCodec.createEncoderByType(this.mCodecName);
                    MediaCodecInfo codecInfo = this.mMediaCodec.getCodecInfo();
                    if (!codecInfo.getName().startsWith("OMX.google.")) {
                        for (String str : codecInfo.getSupportedTypes()) {
                            VELogUtil.i(TAG, "CodecNames: " + str);
                        }
                        this.status = 1;
                    }
                } catch (IOException e) {
                    VELogUtil.e(TAG, "createEncoderByTyp: " + e.getMessage());
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:59:0x014c, code lost:
        return 0;
     */
    public int encode(int i, int i2, int i3, boolean z) {
        int i4;
        int i5 = i;
        boolean z2 = z;
        VELogUtil.d(TAG, "encodeTexture::texID: " + i5 + " pts: " + i4 + " duration:" + i3 + "  isEndStream = " + z2);
        synchronized (this) {
            if (this.status == 2) {
                if (this.mMediaCodec != null) {
                    if (i5 > 0) {
                        if (i4 >= 0) {
                            if (this.mTextureDrawer == null && !initEGLCtx()) {
                                return -1;
                            }
                            this.mPTSQueue.offer(Pair.create(Integer.valueOf(i2), Integer.valueOf(i3)));
                            GLES20.glViewport(0, 0, this.mWidth, this.mHeight);
                            VELogUtil.d(TAG, "encode: width = " + this.mWidth + " height = " + this.mHeight);
                            this.mTextureDrawer.drawTexture(i5);
                            GLES20.glFinish();
                            this.mDrawCount = this.mDrawCount + 1;
                            mEncoderCaller.onSwapGlBuffers();
                            if (this.mFlag) {
                                testCode(z2);
                                this.mFlag = false;
                            }
                            if (z2) {
                                try {
                                    this.mMediaCodec.signalEndOfInputStream();
                                } catch (Throwable th) {
                                    this.mIsError = true;
                                    return -2;
                                }
                            }
                            int i6 = 0;
                            while (true) {
                                i6++;
                                try {
                                    if (Build.VERSION.SDK_INT < 21) {
                                        this.outputBuffers = this.mMediaCodec.getOutputBuffers();
                                    }
                                    int dequeueOutputBuffer = this.mMediaCodec.dequeueOutputBuffer(this.mBufferInfo, (long) TIMEOUT_USEC);
                                    VELogUtil.d(TAG, "outputBufferIndex = " + dequeueOutputBuffer);
                                    VELogUtil.d(TAG, "mBufferInfo.flags = " + this.mBufferInfo.flags);
                                    if (dequeueOutputBuffer == -1) {
                                        if (z2 && TIMEOUT_USEC < 5000) {
                                            TIMEOUT_USEC = DurationConstant.DURATION_VIDEO_RECORDING_CIRCLE;
                                        }
                                        if (!z2 || this.mDrawCount == this.mEncodeCount || i6 >= 10) {
                                            break;
                                        }
                                    } else if (dequeueOutputBuffer == -3) {
                                        this.outputBuffers = this.mMediaCodec.getOutputBuffers();
                                    } else if (dequeueOutputBuffer == -2) {
                                        VELogUtil.d(TAG, "encode: output format change!");
                                    } else if (dequeueOutputBuffer < 0) {
                                        VELogUtil.d(TAG, "encode: error.");
                                        break;
                                    } else {
                                        ByteBuffer outputBuffer = Build.VERSION.SDK_INT >= 21 ? this.mMediaCodec.getOutputBuffer(dequeueOutputBuffer) : this.outputBuffers[dequeueOutputBuffer];
                                        outputBuffer.position(this.mBufferInfo.offset);
                                        outputBuffer.limit(this.mBufferInfo.offset + this.mBufferInfo.size);
                                        if ((this.mBufferInfo.flags & 2) != 0) {
                                            VELogUtil.d(TAG, "mEncoderCaller.onSetCodecConfig");
                                            if (mEncoderCaller != null) {
                                                mEncoderCaller.onSetCodecConfig(outputBuffer);
                                            }
                                            this.mBufferInfo.size = 0;
                                        } else {
                                            int i7 = (this.mBufferInfo.flags & 1) != 0 ? 1 : 0;
                                            VELogUtil.d(TAG, "mEncoderCaller.onWriteFile");
                                            if (mEncoderCaller != null) {
                                                VELogUtil.d(TAG, "encode: pts queue size = " + this.mPTSQueue.size());
                                                if (this.mPTSQueue.size() > 0) {
                                                    this.mEncodeCount++;
                                                    Pair poll = this.mPTSQueue.poll();
                                                    long j = 0;
                                                    if (this.mBufferInfo.presentationTimeUs > 0) {
                                                        j = this.mBufferInfo.presentationTimeUs;
                                                    }
                                                    mEncoderCaller.onWriteFile(outputBuffer, j / 1000, (long) ((Integer) poll.first).intValue(), ((Integer) poll.second).intValue(), i7);
                                                } else {
                                                    VELogUtil.w(TAG, "encode: no available pts!!!");
                                                }
                                            } else {
                                                VELogUtil.d(TAG, "encode: no output.");
                                            }
                                        }
                                        this.mMediaCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                                        i6 = 0;
                                    }
                                } catch (Throwable th2) {
                                    this.mIsError = true;
                                    return -3;
                                }
                            }
                            if (z2) {
                                releaseEGLCtx();
                            }
                        }
                    }
                    VELogUtil.e(TAG, "encode: invalidate params: texID = " + i5 + ", pts = " + i4);
                    return -1;
                }
            }
            VELogUtil.w(TAG, "encode: codec is not ready.");
            return -1;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:64:0x01b2, code lost:
        return 0;
     */
    public int encode(byte[] bArr, int i, boolean z) {
        byte[] bArr2 = bArr;
        int i2 = i;
        boolean z2 = z;
        synchronized (this) {
            if (this.status == 2) {
                if (this.mMediaCodec != null) {
                    VELogUtil.d(TAG, "encodeBuffer pts: " + i2 + "  isEndStream = " + z2);
                    if (Build.VERSION.SDK_INT >= 21) {
                        int dequeueInputBuffer = this.mMediaCodec.dequeueInputBuffer(-1);
                        if (dequeueInputBuffer >= 0) {
                            ByteBuffer inputBuffer = this.mMediaCodec.getInputBuffer(dequeueInputBuffer);
                            inputBuffer.clear();
                            inputBuffer.put(bArr2, 0, bArr2.length);
                            this.mMediaCodec.queueInputBuffer(dequeueInputBuffer, 0, bArr2.length, (long) i2, z2 ? 4 : 0);
                        }
                        VELogUtil.d(TAG, "outputBufferIndex = " + r0);
                        VELogUtil.d(TAG, "mBufferInfo.flags = " + this.mBufferInfo.flags);
                        for (int dequeueOutputBuffer = this.mMediaCodec.dequeueOutputBuffer(this.mBufferInfo, (long) TIMEOUT_USEC); dequeueOutputBuffer >= 0; dequeueOutputBuffer = this.mMediaCodec.dequeueOutputBuffer(this.mBufferInfo, 0)) {
                            ByteBuffer outputBuffer = this.mMediaCodec.getOutputBuffer(dequeueOutputBuffer);
                            outputBuffer.position(this.mBufferInfo.offset);
                            outputBuffer.limit(this.mBufferInfo.offset + this.mBufferInfo.size);
                            if ((this.mBufferInfo.flags & 2) != 0) {
                                VELogUtil.d(TAG, "mEncoderCaller.onSetCodecConfig");
                                if (mEncoderCaller != null) {
                                    mEncoderCaller.onSetCodecConfig(outputBuffer);
                                }
                                this.mBufferInfo.size = 0;
                            } else {
                                int i3 = (int) this.mBufferInfo.presentationTimeUs;
                                int i4 = (this.mBufferInfo.flags & 1) != 0 ? 1 : 0;
                                VELogUtil.d(TAG, "mEncoderCaller.onWriteFile");
                                if (mEncoderCaller != null) {
                                    mEncoderCaller.onWriteFile(outputBuffer, i3, 0, i4);
                                }
                            }
                            this.mMediaCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                        }
                    } else {
                        int dequeueInputBuffer2 = this.mMediaCodec.dequeueInputBuffer(-1);
                        if (dequeueInputBuffer2 >= 0) {
                            ByteBuffer byteBuffer = this.inputBuffers[dequeueInputBuffer2];
                            byteBuffer.clear();
                            byteBuffer.put(bArr2, 0, bArr2.length);
                            this.mMediaCodec.queueInputBuffer(dequeueInputBuffer2, 0, bArr2.length, (long) i2, z2 ? 4 : 0);
                        }
                        for (int dequeueOutputBuffer2 = this.mMediaCodec.dequeueOutputBuffer(this.mBufferInfo, (long) TIMEOUT_USEC); dequeueOutputBuffer2 >= 0; dequeueOutputBuffer2 = this.mMediaCodec.dequeueOutputBuffer(this.mBufferInfo, 0)) {
                            if (dequeueOutputBuffer2 == -3) {
                                this.outputBuffers = this.mMediaCodec.getOutputBuffers();
                            } else if (dequeueOutputBuffer2 != -2) {
                                ByteBuffer byteBuffer2 = this.outputBuffers[dequeueOutputBuffer2];
                                byteBuffer2.position(this.mBufferInfo.offset);
                                byteBuffer2.limit(this.mBufferInfo.offset + this.mBufferInfo.size);
                                if ((this.mBufferInfo.flags & 2) != 0) {
                                    VELogUtil.d(TAG, "mEncoderCaller.onSetCodecConfig");
                                    if (mEncoderCaller != null) {
                                        mEncoderCaller.onSetCodecConfig(byteBuffer2);
                                    }
                                    this.mBufferInfo.size = 0;
                                } else {
                                    int i5 = (int) this.mBufferInfo.presentationTimeUs;
                                    int i6 = (this.mBufferInfo.flags & 1) != 0 ? 1 : 0;
                                    VELogUtil.d(TAG, "mEncoderCaller.onWriteFile");
                                    if (mEncoderCaller != null) {
                                        mEncoderCaller.onWriteFile(byteBuffer2, i5, 0, i6);
                                    }
                                }
                                this.mMediaCodec.releaseOutputBuffer(dequeueOutputBuffer2, false);
                            }
                        }
                    }
                }
            }
            return -1;
        }
    }

    public int[] getColorFormats() {
        VELogUtil.i(TAG, "start == ");
        this.mMediaCodecInfo = Build.VERSION.SDK_INT >= 21 ? getMediaCodecInfo21() : getMediaCodecInfo();
        VELogUtil.i(TAG, "end == ");
        if (this.mMediaCodecInfo == null) {
            return null;
        }
        VELogUtil.i(TAG, "mMediaCodecInfo name = " + this.mMediaCodecInfo.getName());
        MediaCodecInfo.CodecCapabilities capabilitiesForType = this.mMediaCodecInfo.getCapabilitiesForType(this.mCodecName);
        int length = capabilitiesForType.colorFormats.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = capabilitiesForType.colorFormats[i];
        }
        return iArr;
    }

    public int getProfile() {
        return this.mProfile;
    }

    public Surface initAVCEncoder(int i, int i2, int i3, int i4) {
        return initAVCEncoder(i, i2, i3, 1, 8, i4, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x011e, code lost:
        if (r13.profile < 8) goto L_0x0121;
     */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0127 A[Catch:{ Exception -> 0x0332 }, LOOP:0: B:24:0x00cb->B:41:0x0127, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0126 A[SYNTHETIC] */
    public Surface initAVCEncoder(int i, int i2, int i3, int i4, int i5, int i6, boolean z) {
        int i7;
        int i8 = i;
        int i9 = i2;
        int i10 = i3;
        int i11 = i4;
        int i12 = i5;
        if (!z || Build.VERSION.SDK_INT < 18) {
            return null;
        }
        this.mDrawCount = 0;
        this.mEncodeCount = 0;
        VELogUtil.i(TAG, "initAVCEncoder == enter");
        if (i11 < 0 || i11 > 2) {
            VELogUtil.i(TAG, "Do not support bitrate mode " + i11 + ", set VBR mode");
            i11 = 1;
        }
        if (i12 < 1 || i12 > 64) {
            VELogUtil.i(TAG, "Do not support profile " + i12 + ", use baseline");
            i12 = 1;
        }
        VELogUtil.i(TAG, "width + " + i8 + "\theight = " + i9 + "\tbitrate = " + i10 + "\tuseTextureInput = " + r6);
        if (i8 <= 0 || i9 <= 0) {
            return null;
        }
        this.mWidth = i8;
        this.mHeight = i9;
        synchronized (this) {
            try {
                createEncoder();
                if (mEncoderCaller != null) {
                    mEncoderCaller.setColorFormat(this.mColorFormat);
                }
                MediaFormat createVideoFormat = MediaFormat.createVideoFormat(this.mCodecName, i8, i9);
                MediaCodecInfo.CodecCapabilities capabilitiesForType = this.mMediaCodec.getCodecInfo().getCapabilitiesForType(this.mCodecName);
                MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr = capabilitiesForType.profileLevels;
                int length = codecProfileLevelArr.length;
                MediaCodecInfo.CodecProfileLevel codecProfileLevel = null;
                int i13 = 0;
                while (true) {
                    if (i13 >= length) {
                        break;
                    }
                    MediaCodecInfo.CodecProfileLevel codecProfileLevel2 = codecProfileLevelArr[i13];
                    VELogUtil.i(TAG, "Profile = " + codecProfileLevel2.profile + ", Level = " + codecProfileLevel2.level);
                    if (codecProfileLevel2.profile == 1) {
                        VELogUtil.i(TAG, "Support Baseline Profile!");
                    } else if (codecProfileLevel2.profile == 2) {
                        VELogUtil.i(TAG, "Support Main Profile!");
                        if (codecProfileLevel.profile < 2) {
                        }
                        if (codecProfileLevel2.profile == i12) {
                            break;
                        }
                        i13++;
                    } else {
                        if (codecProfileLevel2.profile == 8) {
                            VELogUtil.i(TAG, "Support High Profile!");
                        }
                        if (codecProfileLevel2.profile == i12) {
                        }
                    }
                    codecProfileLevel = codecProfileLevel2;
                    if (codecProfileLevel2.profile == i12) {
                    }
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    MediaCodecInfo.EncoderCapabilities encoderCapabilities = capabilitiesForType.getEncoderCapabilities();
                    for (int i14 = 0; i14 < 3; i14++) {
                        VELogUtil.d(TAG, BITRATE_MODES[i14] + ": " + encoderCapabilities.isBitrateModeSupported(i14));
                    }
                }
                if (Build.VERSION.SDK_INT < 24 || codecProfileLevel == null) {
                    VELogUtil.w(TAG, "Do not support profile " + i12 + ", use baseline");
                    TEMonitor.perfLong(0, TEMonitorNewKeys.TE_MEDIACODEC_PROFILE, 1);
                    TEMonitor.perfLong(VEMonitorKeys.IESMMTRACKER_KEY_RECORD_MEDIACODEC_PROFILE, 1);
                    i7 = i10;
                } else {
                    VELogUtil.i(TAG, "Set Profile: " + codecProfileLevel.profile + ", Level = " + codecProfileLevel.level);
                    this.mProfile = codecProfileLevel.profile;
                    createVideoFormat.setInteger("profile", codecProfileLevel.profile);
                    createVideoFormat.setInteger("level", codecProfileLevel.level);
                    int i15 = codecProfileLevel.profile;
                    if (i15 == 2) {
                        VELogUtil.i(TAG, "Set Main Profile");
                        i7 = (int) (((float) i10) * 0.85f);
                    } else if (i15 != 8) {
                        i7 = i10;
                    } else {
                        VELogUtil.i(TAG, "Set High Profile");
                        i7 = (int) (((float) i10) * 0.75f);
                    }
                    TEMonitor.perfLong(0, TEMonitorNewKeys.TE_MEDIACODEC_PROFILE, (long) codecProfileLevel.profile);
                    TEMonitor.perfLong(VEMonitorKeys.IESMMTRACKER_KEY_RECORD_MEDIACODEC_PROFILE, (long) codecProfileLevel.profile);
                }
                int i16 = 12000000;
                if (i7 <= 12000000) {
                    i16 = i7;
                }
                VELogUtil.i(TAG, "bitrate = " + ((((float) i16) * 1.0f) / 1000000.0f) + "Mb/s");
                StringBuilder sb = new StringBuilder();
                sb.append("speed = ");
                sb.append(i6);
                VELogUtil.i(TAG, sb.toString());
                createVideoFormat.setInteger("bitrate", i16);
                if (Build.VERSION.SDK_INT >= 21) {
                    MediaCodecInfo.EncoderCapabilities encoderCapabilities2 = capabilitiesForType.getEncoderCapabilities();
                    for (int i17 = 0; i17 < 3; i17++) {
                        VELogUtil.i(TAG, BITRATE_MODES[i17] + ": " + encoderCapabilities2.isBitrateModeSupported(i17));
                    }
                    createVideoFormat.setInteger("bitrate-mode", i11);
                    VELogUtil.i(TAG, "Bitrate mode = " + i11);
                    TEMonitor.perfLong(0, TEMonitorNewKeys.TE_RECORD_MEDIACODEC_RATE_CONTROL, (long) i11);
                    createVideoFormat.setInteger("max-bitrate", i10);
                    VELogUtil.i(TAG, "Encoder ComplexityRange: " + encoderCapabilities2.getComplexityRange().toString());
                }
                createVideoFormat.setInteger("color-format", this.mColorFormat);
                createVideoFormat.setInteger("frame-rate", this.mFrameRate);
                createVideoFormat.setInteger("i-frame-interval", 1);
                TEMonitor.perfLong(0, TEMonitorNewKeys.TE_RECORD_VIDEO_ENCODE_GOP, (long) (this.mFrameRate * 1));
                VELogUtil.i(TAG, "initAVCEncoder: format = " + createVideoFormat);
                this.mMediaCodec.configure(createVideoFormat, (Surface) null, (MediaCrypto) null, 1);
                this.mSurface = this.mMediaCodec.createInputSurface();
                this.mMediaCodec.start();
                this.status = 2;
                if (Build.VERSION.SDK_INT < 21) {
                    this.inputBuffers = this.mMediaCodec.getInputBuffers();
                    this.outputBuffers = this.mMediaCodec.getOutputBuffers();
                }
                this.mBufferInfo = new MediaCodec.BufferInfo();
                if (this.mSurface == null) {
                    return null;
                }
                VELogUtil.i(TAG, "initAVCEncoder == exit");
                return this.mSurface;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public Surface initAVCEncoder(int i, int i2, int i3, int i4, boolean z) {
        return initAVCEncoder(i, i2, i3, 1, 1, i4, z);
    }

    public boolean initEGLCtx() {
        if (this.mSurface == null) {
            VELogUtil.e(TAG, "initEGLCtx: MediaCodec should initialized ahead.");
            return false;
        }
        this.mTextureDrawer = TextureDrawer.create();
        this.mTextureDrawer.setRotation(0.0f);
        this.mTextureDrawer.setFlipScale(1.0f, -1.0f);
        return true;
    }

    public boolean isError() {
        return this.mIsError;
    }

    public void releaseEGLCtx() {
        if (this.mTextureDrawer != null) {
            this.mTextureDrawer.release();
            this.mTextureDrawer = null;
        }
    }

    public synchronized void releaseEncoder() {
        long currentTimeMillis = System.currentTimeMillis();
        try {
            this.mMediaCodec.release();
        } catch (Exception e) {
        }
        this.mMediaCodec = null;
        this.status = 0;
        VELogUtil.i(TAG, "time cost: " + (System.currentTimeMillis() - currentTimeMillis));
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0078 A[SYNTHETIC, Splitter:B:30:0x0078] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0084 A[SYNTHETIC, Splitter:B:35:0x0084] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0092 A[SYNTHETIC, Splitter:B:41:0x0092] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x009e A[SYNTHETIC, Splitter:B:46:0x009e] */
    /* JADX WARNING: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    public void saveBitmap(Bitmap bitmap, String str) {
        FileOutputStream fileOutputStream;
        BufferedOutputStream bufferedOutputStream;
        VELogUtil.i(TAG, "saving Bitmap : " + str);
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(str);
            try {
                bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            } catch (IOException e) {
                e = e;
                try {
                    VELogUtil.e(TAG, "Err when saving bitmap...");
                    e.printStackTrace();
                    if (bufferedOutputStream2 != null) {
                        try {
                            bufferedOutputStream2.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (fileOutputStream == null) {
                        try {
                            fileOutputStream.close();
                            return;
                        } catch (IOException e3) {
                            e3.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (bufferedOutputStream2 != null) {
                    }
                    if (fileOutputStream != null) {
                    }
                    throw th;
                }
            }
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream);
                bufferedOutputStream.flush();
                try {
                    bufferedOutputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                try {
                    fileOutputStream.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
                VELogUtil.i(TAG, "Bitmap " + str + " saved!");
            } catch (IOException e6) {
                e = e6;
                bufferedOutputStream2 = bufferedOutputStream;
            } catch (Throwable th2) {
                th = th2;
                bufferedOutputStream2 = bufferedOutputStream;
                if (bufferedOutputStream2 != null) {
                }
                if (fileOutputStream != null) {
                }
                throw th;
            }
        } catch (IOException e7) {
            e = e7;
            fileOutputStream = null;
            VELogUtil.e(TAG, "Err when saving bitmap...");
            e.printStackTrace();
            if (bufferedOutputStream2 != null) {
            }
            if (fileOutputStream == null) {
            }
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
            if (bufferedOutputStream2 != null) {
                try {
                    bufferedOutputStream2.close();
                } catch (IOException e8) {
                    e8.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e9) {
                    e9.printStackTrace();
                }
            }
            throw th;
        }
    }

    @TargetApi(21)
    public MediaFormat setBitrateMode(MediaFormat mediaFormat) {
        mediaFormat.setInteger("bitrate-mode", 0);
        return mediaFormat;
    }

    public void setEncoderCaller(AVCEncoderInterface aVCEncoderInterface) {
        mEncoderCaller = aVCEncoderInterface;
    }

    public void setFrameRate(int i) {
        this.mFrameRate = i;
    }

    public void uninitAVCEncoder() {
        VELogUtil.i(TAG, "uninitAVCEncoder == enter");
        synchronized (this) {
            if (this.status != 0) {
                if (this.mMediaCodec != null) {
                    if (this.status == 2) {
                        try {
                            this.mMediaCodec.stop();
                        } catch (Exception e) {
                            VELogUtil.e(TAG, "MediaCodec Exception");
                        }
                    }
                    this.status = 3;
                    if (this.mSurface != null) {
                        this.mSurface.release();
                    }
                    releaseEncoder();
                    VELogUtil.i(TAG, "uninitAVCEncoder == exit");
                }
            }
        }
    }
}
