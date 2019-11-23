package com.arcsoft.avatar.recoder;

import android.graphics.Bitmap;
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

    /* renamed from: a  reason: collision with root package name */
    private static final String f36a = "Arc_VideoEncoder";
    private static final int r = 2;
    /* access modifiers changed from: private */

    /* renamed from: b  reason: collision with root package name */
    public int f37b;
    /* access modifiers changed from: private */
    public int c;
    private int d;
    private boolean e;
    private boolean f;
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

        /* renamed from: b  reason: collision with root package name */
        private ByteBuffer f39b;

        public SaveThread(ByteBuffer byteBuffer) {
            this.f39b = byteBuffer;
        }

        public void run() {
            super.run();
            Bitmap createBitmap = Bitmap.createBitmap(MediaManager.this.f37b, MediaManager.this.c, Bitmap.Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(this.f39b);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream("/sdcard/Pictures/_" + System.currentTimeMillis() + ".png");
                createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
                createBitmap.recycle();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public MediaManager(@NonNull FileDescriptor fileDescriptor, int i2, int i3, int i4, boolean z, int i5, RecordingListener recordingListener) {
        this.p = recordingListener;
        this.f37b = i2;
        this.c = i3;
        if (90 == i4 || 270 == i4) {
            this.f37b ^= this.c;
            this.c = this.f37b ^ this.c;
            this.f37b ^= this.c;
        }
        this.d = i4;
        this.e = z;
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
        CodecLog.d(f36a, "MediaManager constructor mFrameWidth = " + i2 + " ,mFrameHeight = " + i3);
    }

    public MediaManager(@NonNull String str, int i2, int i3, int i4, boolean z, int i5, RecordingListener recordingListener) {
        this.p = recordingListener;
        this.f37b = i2;
        this.c = i3;
        if (90 == i4 || 270 == i4) {
            this.f37b ^= this.c;
            this.c = this.f37b ^ this.c;
            this.f37b ^= this.c;
        }
        this.d = i4;
        this.e = z;
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
        CodecLog.d(f36a, "MediaManager constructor mFrameWidth = " + i2 + " ,mFrameHeight = " + i3);
    }

    private void a() {
        if (this.m == this.n) {
            this.f = true;
        } else if (this.n >= 3) {
            throw new RuntimeException("Init encoder count great than need. need=" + this.m + " ,but got=" + this.n);
        }
    }

    private void b() {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(this.f37b * this.c * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        GLES20.glReadPixels(0, 0, this.f37b, this.c, 6408, 5121, allocateDirect);
        CodecLog.d(f36a, "glReadPixels() glError = " + GLES20.glGetError());
        new SaveThread(allocateDirect).start();
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x007d A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x007e  */
    public void drawSurfaceWithTextureId(int i2) {
        boolean z;
        if (!this.f) {
            CodecLog.e(f36a, "drawSurfaceWithTextureId()-> MediaManager has not been initialized.");
        } else if (i2 <= 0) {
            throw new IllegalArgumentException("textureId must >0 , your textureId=" + i2);
        } else if (this.l != null) {
            CodecLog.d(f36a, "drawSurfaceWithTextureId()->A-");
            FrameItem frameItem = null;
            try {
                this.h.lock();
                if (this.q.isIsInited()) {
                    FrameItem frameForProducer = this.q.getFrameForProducer();
                    if (frameForProducer != null) {
                        try {
                            if (frameForProducer.mIsInited) {
                                frameItem = frameForProducer;
                                z = true;
                            }
                        } catch (Exception e2) {
                            FrameItem frameItem2 = frameForProducer;
                            e = e2;
                            frameItem = frameItem2;
                            try {
                                CodecLog.d(f36a, "drawSurfaceWithTextureId meet error when get lock : " + e.getMessage());
                                this.h.unLock();
                                z = false;
                                if (z) {
                                }
                            } catch (Throwable th) {
                                this.h.unLock();
                                throw th;
                            }
                        }
                    }
                    CodecLog.d(f36a, "drawSurfaceWithTextureId()-> get a null frame item.");
                    this.h.unLock();
                    return;
                }
                z = false;
                this.h.unLock();
            } catch (Exception e3) {
                e = e3;
                CodecLog.d(f36a, "drawSurfaceWithTextureId meet error when get lock : " + e.getMessage());
                this.h.unLock();
                z = false;
                if (z) {
                }
            }
            if (z) {
                if (this.u == null) {
                    this.u = new int[4];
                    GLES30.glGetIntegerv(2978, this.u, 0);
                }
                this.q.deleteSync(frameItem);
                frameItem.mFramebuffer.bind(false, false);
                GLES20.glViewport(0, 0, this.f37b, this.c);
                this.l.renderWithTextureId(i2);
                frameItem.f33a = GLES30.glFenceSync(37143, 0);
                frameItem.mFramebuffer.unBind(false, false);
                frameItem.mIsEmpty = false;
                GLES20.glViewport(this.u[0], this.u[1], this.u[2], this.u[3]);
                CodecLog.d(f36a, "drawSurfaceWithTextureId()-> A glError = " + GLES20.glGetError() + ", fbo = " + frameItem + " ,textureId = " + frameItem.mFramebuffer.getTextureId());
                try {
                    this.h.lock();
                    this.q.addFrameForProducer();
                } catch (Exception e4) {
                    CodecLog.d(f36a, "drawSurfaceWithTextureId meet error when get lock : " + e4.getMessage());
                } catch (Throwable th2) {
                    this.h.sinalCondition();
                    this.h.unLock();
                    throw th2;
                }
                this.h.sinalCondition();
                this.h.unLock();
                CodecLog.d(f36a, "drawSurfaceWithTextureId()->C");
            }
        } else {
            throw new RuntimeException("Could not call drawSurfaceWithTextureId() in with a null GLRender.");
        }
    }

    public long getMuxerSizeRecorded() {
        if (this.j == null) {
            return 0;
        }
        return this.j.getSizeRecordFile();
    }

    public long getMuxerTimeElapsed() {
        if (this.j == null) {
            return 0;
        }
        return this.j.getTimeElapse();
    }

    public void initAudioEncoder() {
        this.i = new AudioEncoder(this.j, this.o, this);
        this.i.prepare(false);
        this.n++;
        a();
    }

    public void initVideoEncoder(String str) {
        CodecLog.e(f36a, "MediaManager initVideoEncoder in");
        VideoEncoder videoEncoder = new VideoEncoder(this.j, this.f37b, this.c, this.o, this, EGL14.EGL_NO_CONTEXT, 10000000, str);
        this.h = videoEncoder;
        this.h.prepare(false);
        this.f = true;
        this.n++;
        a();
        CodecLog.e(f36a, "MediaManager initVideoEncoder out mInitedEncoderCount = " + this.n);
    }

    public void initVideoEncoderWithSharedContext(EGLContext eGLContext, int i2, boolean z, String str) {
        CodecLog.d(f36a, "MediaManager initVideoEncoderWithSharedContext in");
        VideoEncoder videoEncoder = new VideoEncoder(this.j, this.f37b, this.c, this.o, this, eGLContext, i2, str);
        this.h = videoEncoder;
        CodecLog.d(f36a, "MediaManager initVideoEncoderWithSharedContext encoder type = " + this.h.getEncoderType());
        this.k = true;
        if (this.k) {
            if (this.h.getInputSurface() != null) {
                this.l = new GLRender(this.f37b, this.c, this.d, this.e);
                this.l.initRender(z);
            } else {
                CodecLog.e(f36a, "initVideoEncoder()->getInputSurface null.");
                if (this.p != null) {
                    this.p.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_ENCODER_VIDEO_CONFIGURE, 0);
                }
            }
        }
        this.q = new FrameQueue();
        this.q.init(2, this.f37b, this.c, false);
        this.h.setFrameQueue(this.q);
        this.n++;
        a();
        CodecLog.e(f36a, "MediaManager initVideoEncoderWithSharedContext out mInitedEncoderCount = " + this.n);
    }

    public void onRecordingListener(int i2, Object obj) {
        CodecLog.d(f36a, "onRecordingListener()->in msg = " + i2 + " ,value = " + ((Integer) obj));
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
        if (this.p != null) {
            this.p.onRecordingListener(i3, obj);
        }
        CodecLog.d(f36a, "onRecordingListener()->out");
    }

    public int pauseRecording() {
        if (this.i != null) {
            this.i.pauseRecording();
        }
        if (this.h == null) {
            return 0;
        }
        this.h.pauseRecording();
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
        if (this.j != null) {
            this.j.setEncoderCount(i2);
        }
        this.m = i2;
    }

    public void startRecording() {
        if (!this.f || this.j == null) {
            throw new RuntimeException("Unit Encoder or Muxer is null.");
        }
        if (this.h != null) {
            this.h.startRecording();
        } else {
            CodecLog.i(f36a, "startRecording()-> VideoEncoder is null. maxEncoderCount=" + this.m);
        }
        if (this.i != null) {
            this.i.startRecording();
            return;
        }
        CodecLog.i(f36a, "startRecording()-> AudioEncoder is null. maxEncoderCount=" + this.m);
    }

    public void stopRecording() {
        synchronized (this.o) {
            this.o.notifyAll();
        }
        if (this.h != null) {
            this.h.stopRecording();
            this.h.release(true);
            this.h = null;
        }
        if (this.i != null) {
            this.i.stopRecording();
            this.i.release(false);
            this.i = null;
        }
        if (this.l != null) {
            this.l.unInitRender();
            this.l = null;
        }
        if (this.q != null) {
            this.q.unInit();
            this.q = null;
        }
        this.j = null;
        this.o = null;
        this.u = null;
    }
}
