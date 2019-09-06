package com.arcsoft.avatar.recoder;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.support.annotation.NonNull;
import com.arcsoft.avatar.gl.GLRender;
import com.arcsoft.avatar.util.CodecLog;
import com.arcsoft.avatar.util.NotifyMessage;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MediaManager implements RecordingListener {
    public static final int MUXER_AUDIO_ENCODER = 1;
    public static final int MUXER_VIDEO_AND_AUDIO_ENCODER = 2;
    public static final int MUXER_VIDEO_ENCODER = 1;

    /* renamed from: a reason: collision with root package name */
    private static final String f109a = "Arc_VideoEncoder";
    private static final int r = 2;
    /* access modifiers changed from: private */

    /* renamed from: b reason: collision with root package name */
    public int f110b;
    /* access modifiers changed from: private */

    /* renamed from: c reason: collision with root package name */
    public int f111c;

    /* renamed from: d reason: collision with root package name */
    private int f112d;

    /* renamed from: e reason: collision with root package name */
    private boolean f113e;

    /* renamed from: f reason: collision with root package name */
    private boolean f114f;
    private String g;
    private BaseEncoder h;
    private BaseEncoder i;
    private MuxerWrapper j;
    private boolean k;
    private GLRender l;
    private int m;
    private int n;
    private Object o;
    private RecordingListener p;
    private FrameQueue q;
    private int s;
    private int t;
    private int[] u;

    public class SaveThread extends Thread {

        /* renamed from: b reason: collision with root package name */
        private ByteBuffer f116b;

        public SaveThread(ByteBuffer byteBuffer) {
            this.f116b = byteBuffer;
        }

        public void run() {
            super.run();
            Bitmap createBitmap = Bitmap.createBitmap(MediaManager.this.f110b, MediaManager.this.f111c, Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(this.f116b);
            StringBuilder sb = new StringBuilder();
            sb.append("/sdcard/Pictures/_");
            sb.append(System.currentTimeMillis());
            sb.append(".png");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(sb.toString());
                createBitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
                createBitmap.recycle();
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }

    public MediaManager(@NonNull FileDescriptor fileDescriptor, int i2, int i3, int i4, boolean z, int i5, RecordingListener recordingListener) {
        this.p = recordingListener;
        this.f110b = i2;
        this.f111c = i3;
        if (90 == i4 || 270 == i4) {
            int i6 = this.f110b;
            int i7 = this.f111c;
            this.f110b = i6 ^ i7;
            int i8 = this.f110b;
            this.f111c = i7 ^ i8;
            this.f110b = i8 ^ this.f111c;
        }
        this.f112d = i4;
        this.f113e = z;
        this.m = 0;
        this.n = 0;
        this.j = new MuxerWrapper(fileDescriptor, i5, (RecordingListener) this);
        EGLDisplay eglGetCurrentDisplay = EGL14.eglGetCurrentDisplay();
        EGLSurface eglGetCurrentSurface = EGL14.eglGetCurrentSurface(12378);
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        EGL14.eglQuerySurface(eglGetCurrentDisplay, eglGetCurrentSurface, 12375, iArr, 0);
        EGL14.eglQuerySurface(eglGetCurrentDisplay, eglGetCurrentSurface, 12374, iArr2, 0);
        this.s = iArr[0];
        this.t = iArr2[0];
        this.o = new Object();
        StringBuilder sb = new StringBuilder();
        sb.append("MediaManager constructor mFrameWidth = ");
        sb.append(i2);
        sb.append(" ,mFrameHeight = ");
        sb.append(i3);
        CodecLog.d(f109a, sb.toString());
    }

    public MediaManager(@NonNull String str, int i2, int i3, int i4, boolean z, int i5, RecordingListener recordingListener) {
        this.p = recordingListener;
        this.f110b = i2;
        this.f111c = i3;
        if (90 == i4 || 270 == i4) {
            int i6 = this.f110b;
            int i7 = this.f111c;
            this.f110b = i6 ^ i7;
            int i8 = this.f110b;
            this.f111c = i7 ^ i8;
            this.f110b = i8 ^ this.f111c;
        }
        this.f112d = i4;
        this.f113e = z;
        this.m = 0;
        this.n = 0;
        this.j = new MuxerWrapper(str, i5, (RecordingListener) this);
        EGLDisplay eglGetCurrentDisplay = EGL14.eglGetCurrentDisplay();
        EGLSurface eglGetCurrentSurface = EGL14.eglGetCurrentSurface(12378);
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        EGL14.eglQuerySurface(eglGetCurrentDisplay, eglGetCurrentSurface, 12375, iArr, 0);
        EGL14.eglQuerySurface(eglGetCurrentDisplay, eglGetCurrentSurface, 12374, iArr2, 0);
        this.s = iArr[0];
        this.t = iArr2[0];
        this.o = new Object();
        StringBuilder sb = new StringBuilder();
        sb.append("MediaManager constructor mFrameWidth = ");
        sb.append(i2);
        sb.append(" ,mFrameHeight = ");
        sb.append(i3);
        CodecLog.d(f109a, sb.toString());
    }

    private void a() {
        int i2 = this.m;
        int i3 = this.n;
        if (i2 == i3) {
            this.f114f = true;
        } else if (i3 >= 3) {
            StringBuilder sb = new StringBuilder();
            sb.append("Init encoder count great than need. need=");
            sb.append(this.m);
            sb.append(" ,but got=");
            sb.append(this.n);
            throw new RuntimeException(sb.toString());
        }
    }

    private void b() {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(this.f110b * this.f111c * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        GLES20.glReadPixels(0, 0, this.f110b, this.f111c, 6408, 5121, allocateDirect);
        StringBuilder sb = new StringBuilder();
        sb.append("glReadPixels() glError = ");
        sb.append(GLES20.glGetError());
        CodecLog.d(f109a, sb.toString());
        new SaveThread(allocateDirect).start();
    }

    /* JADX INFO: finally extract failed */
    public void drawSurfaceWithTextureId(int i2) {
        boolean z;
        String str = "drawSurfaceWithTextureId meet error when get lock : ";
        boolean z2 = this.f114f;
        String str2 = f109a;
        if (!z2) {
            CodecLog.e(str2, "drawSurfaceWithTextureId()-> MediaManager has not been initialized.");
        } else if (i2 <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("textureId must >0 , your textureId=");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        } else if (this.l != null) {
            CodecLog.d(str2, "drawSurfaceWithTextureId()->A-");
            FrameItem frameItem = null;
            try {
                this.h.lock();
                if (this.q.isIsInited()) {
                    frameItem = this.q.getFrameForProducer();
                    if (frameItem != null) {
                        if (frameItem.mIsInited) {
                            z = true;
                        }
                    }
                    CodecLog.d(str2, "drawSurfaceWithTextureId()-> get a null frame item.");
                    this.h.unLock();
                    return;
                }
                z = false;
                this.h.unLock();
            } catch (Exception e2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(e2.getMessage());
                CodecLog.d(str2, sb2.toString());
                this.h.unLock();
                z = false;
            } catch (Throwable th) {
                this.h.unLock();
                throw th;
            }
            if (z) {
                if (this.u == null) {
                    this.u = new int[4];
                    GLES30.glGetIntegerv(2978, this.u, 0);
                }
                this.q.deleteSync(frameItem);
                frameItem.mFramebuffer.bind(false, false);
                GLES20.glViewport(0, 0, this.f110b, this.f111c);
                this.l.renderWithTextureId(i2);
                frameItem.f102a = GLES30.glFenceSync(37143, 0);
                frameItem.mFramebuffer.unBind(false, false);
                frameItem.mIsEmpty = false;
                int[] iArr = this.u;
                GLES20.glViewport(iArr[0], iArr[1], iArr[2], iArr[3]);
                StringBuilder sb3 = new StringBuilder();
                sb3.append("drawSurfaceWithTextureId()-> A glError = ");
                sb3.append(GLES20.glGetError());
                sb3.append(", fbo = ");
                sb3.append(frameItem);
                sb3.append(" ,textureId = ");
                sb3.append(frameItem.mFramebuffer.getTextureId());
                CodecLog.d(str2, sb3.toString());
                try {
                    this.h.lock();
                    this.q.addFrameForProducer();
                } catch (Exception e3) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str);
                    sb4.append(e3.getMessage());
                    CodecLog.d(str2, sb4.toString());
                } catch (Throwable th2) {
                    this.h.sinalCondition();
                    this.h.unLock();
                    throw th2;
                }
                this.h.sinalCondition();
                this.h.unLock();
                CodecLog.d(str2, "drawSurfaceWithTextureId()->C");
            }
        } else {
            throw new RuntimeException("Could not call drawSurfaceWithTextureId() in with a null GLRender.");
        }
    }

    public long getMuxerSizeRecorded() {
        MuxerWrapper muxerWrapper = this.j;
        if (muxerWrapper == null) {
            return 0;
        }
        return muxerWrapper.getSizeRecordFile();
    }

    public long getMuxerTimeElapsed() {
        MuxerWrapper muxerWrapper = this.j;
        if (muxerWrapper == null) {
            return 0;
        }
        return muxerWrapper.getTimeElapse();
    }

    public void initAudioEncoder() {
        this.i = new AudioEncoder(this.j, this.o, this);
        this.i.prepare(false);
        this.n++;
        a();
    }

    public void initVideoEncoder(String str) {
        String str2 = f109a;
        CodecLog.e(str2, "MediaManager initVideoEncoder in");
        VideoEncoder videoEncoder = new VideoEncoder(this.j, this.f110b, this.f111c, this.o, this, EGL14.EGL_NO_CONTEXT, 10000000, str);
        this.h = videoEncoder;
        this.h.prepare(false);
        this.f114f = true;
        this.n++;
        a();
        StringBuilder sb = new StringBuilder();
        sb.append("MediaManager initVideoEncoder out mInitedEncoderCount = ");
        sb.append(this.n);
        CodecLog.e(str2, sb.toString());
    }

    public void initVideoEncoderWithSharedContext(EGLContext eGLContext, int i2, boolean z, String str) {
        String str2 = f109a;
        CodecLog.d(str2, "MediaManager initVideoEncoderWithSharedContext in");
        VideoEncoder videoEncoder = new VideoEncoder(this.j, this.f110b, this.f111c, this.o, this, eGLContext, i2, str);
        this.h = videoEncoder;
        StringBuilder sb = new StringBuilder();
        sb.append("MediaManager initVideoEncoderWithSharedContext encoder type = ");
        sb.append(this.h.getEncoderType());
        CodecLog.d(str2, sb.toString());
        this.k = true;
        if (this.k) {
            if (this.h.getInputSurface() != null) {
                this.l = new GLRender(this.f110b, this.f111c, this.f112d, this.f113e);
                this.l.initRender(z);
            } else {
                CodecLog.e(str2, "initVideoEncoder()->getInputSurface null.");
                RecordingListener recordingListener = this.p;
                if (recordingListener != null) {
                    recordingListener.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_CONFIGURE, Integer.valueOf(0));
                }
            }
        }
        this.q = new FrameQueue();
        this.q.init(2, this.f110b, this.f111c, false);
        this.h.setFrameQueue(this.q);
        this.n++;
        a();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("MediaManager initVideoEncoderWithSharedContext out mInitedEncoderCount = ");
        sb2.append(this.n);
        CodecLog.e(str2, sb2.toString());
    }

    public void onRecordingListener(int i2, Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("onRecordingListener()->in msg = ");
        sb.append(i2);
        sb.append(" ,value = ");
        sb.append((Integer) obj);
        String sb2 = sb.toString();
        String str = f109a;
        CodecLog.d(str, sb2);
        int i3 = NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER;
        switch (i2) {
            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_AUDIO_CREATE /*545*/:
            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_AUDIO_CONFIGURE /*546*/:
            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_AUDIO_START /*547*/:
            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_AUDIO_STOP /*548*/:
            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_AUDIO_RELEASE /*549*/:
                i3 = 544;
                break;
            default:
                switch (i2) {
                    case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_CREATE /*561*/:
                    case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_CONFIGURE /*562*/:
                    case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_START /*563*/:
                    case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_STOP /*564*/:
                    case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_RELEASE /*565*/:
                        i3 = 560;
                        break;
                    default:
                        switch (i2) {
                            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_CREATE /*609*/:
                            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_ADD_TRACK /*610*/:
                            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_START /*611*/:
                            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_WRITE_SAMPLE_DATA /*612*/:
                            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_STOP /*613*/:
                            case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_RELEASE /*614*/:
                                break;
                            default:
                                switch (i2) {
                                    case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_AUDIO_RECORD_CREATE /*625*/:
                                    case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_AUDIO_RECORD_START_RECORDING /*626*/:
                                    case NotifyMessage.MSG_MEDIA_RECORDER_ERROR_AUDIO_RECORD_STOP /*627*/:
                                        i3 = 624;
                                        break;
                                    default:
                                        i3 = i2;
                                        break;
                                }
                        }
                }
        }
        RecordingListener recordingListener = this.p;
        if (recordingListener != null) {
            recordingListener.onRecordingListener(i3, obj);
        }
        CodecLog.d(str, "onRecordingListener()->out");
    }

    public int pauseRecording() {
        BaseEncoder baseEncoder = this.i;
        if (baseEncoder != null) {
            baseEncoder.pauseRecording();
        }
        BaseEncoder baseEncoder2 = this.h;
        if (baseEncoder2 != null) {
            baseEncoder2.pauseRecording();
        }
        return 0;
    }

    public int resumeRecording() {
        synchronized (this.o) {
            if (this.i != null) {
                this.i.resumeRecording();
            }
            if (this.h != null) {
                this.h.resumeRecording();
            }
            this.o.notifyAll();
        }
        return 0;
    }

    public void setEncoderCount(int i2) {
        MuxerWrapper muxerWrapper = this.j;
        if (muxerWrapper != null) {
            muxerWrapper.setEncoderCount(i2);
        }
        this.m = i2;
    }

    public void startRecording() {
        if (!this.f114f || this.j == null) {
            throw new RuntimeException("Unit Encoder or Muxer is null.");
        }
        BaseEncoder baseEncoder = this.h;
        String str = f109a;
        if (baseEncoder != null) {
            baseEncoder.startRecording();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("startRecording()-> VideoEncoder is null. maxEncoderCount=");
            sb.append(this.m);
            CodecLog.i(str, sb.toString());
        }
        BaseEncoder baseEncoder2 = this.i;
        if (baseEncoder2 != null) {
            baseEncoder2.startRecording();
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("startRecording()-> AudioEncoder is null. maxEncoderCount=");
        sb2.append(this.m);
        CodecLog.i(str, sb2.toString());
    }

    public void stopRecording() {
        synchronized (this.o) {
            this.o.notifyAll();
        }
        BaseEncoder baseEncoder = this.h;
        if (baseEncoder != null) {
            baseEncoder.stopRecording();
            this.h.release(true);
            this.h = null;
        }
        BaseEncoder baseEncoder2 = this.i;
        if (baseEncoder2 != null) {
            baseEncoder2.stopRecording();
            this.i.release(false);
            this.i = null;
        }
        GLRender gLRender = this.l;
        if (gLRender != null) {
            gLRender.unInitRender();
            this.l = null;
        }
        FrameQueue frameQueue = this.q;
        if (frameQueue != null) {
            frameQueue.unInit();
            this.q = null;
        }
        this.j = null;
        this.o = null;
        this.u = null;
    }
}
