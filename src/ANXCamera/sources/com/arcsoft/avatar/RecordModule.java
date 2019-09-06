package com.arcsoft.avatar;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.support.annotation.NonNull;
import com.arcsoft.avatar.AvatarConfig.ASAvatarProcessInfo;
import com.arcsoft.avatar.gl.GLFramebuffer;
import com.arcsoft.avatar.gl.GLRenderEngine;
import com.arcsoft.avatar.gl.TextureHelper;
import com.arcsoft.avatar.recoder.MediaManager;
import com.arcsoft.avatar.recoder.RecordingListener;
import com.arcsoft.avatar.util.ASVLOFFSCREEN;
import com.arcsoft.avatar.util.LOG;
import com.arcsoft.avatar.util.d;
import com.ss.android.ttve.common.TEDefine;
import java.io.FileDescriptor;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RecordModule {

    /* renamed from: a reason: collision with root package name */
    private static final String f41a = "RecordModule";

    /* renamed from: b reason: collision with root package name */
    private static final int f42b = 1;

    /* renamed from: c reason: collision with root package name */
    private static final int f43c = 512000;
    private boolean A;
    private int B = 270;
    private long C = 0;
    private final int D = 1000000;
    private long E = 0;
    private long F = 0;
    private AvatarEngine G;
    private MediaManager H;
    private volatile boolean I;
    private volatile boolean J;
    private boolean K;
    private RecordingListener L = null;
    private ASAvatarProcessInfo M = null;
    private MediaResultCallback N = null;
    private boolean O = false;
    private boolean[] P = new boolean[3];
    private int Q = 0;
    private Boolean R;
    private Bitmap S;
    private ASVLOFFSCREEN T;
    private volatile boolean U = true;
    private volatile boolean V = false;

    /* renamed from: d reason: collision with root package name */
    private Context f44d;

    /* renamed from: e reason: collision with root package name */
    private Lock f45e = new ReentrantLock();

    /* renamed from: f reason: collision with root package name */
    private Lock f46f = new ReentrantLock();
    private volatile boolean g = false;
    private volatile boolean h = false;
    private volatile boolean i = false;
    private volatile boolean j = false;
    private boolean k;
    private EGLDisplay l;
    private EGLContext m;
    private EGLSurface n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private Queue<ASVLOFFSCREEN> u;
    private GLRenderEngine v;
    private GLRenderEngine w;
    private GLFramebuffer x;
    private TextureHelper y;
    private boolean z;

    public interface MediaResultCallback {
        void onCaptureResult(ByteBuffer byteBuffer);

        void onVideoResult();
    }

    public RecordModule(Context context, MediaResultCallback mediaResultCallback) {
        this.f44d = context;
        this.N = mediaResultCallback;
    }

    private long a(long j2) {
        if (j2 <= 0) {
            return 0;
        }
        long j3 = j2 - 512000;
        if (j3 <= 0) {
            j3 = 1;
        }
        return j3;
    }

    private void a() {
        this.m = EGL14.eglGetCurrentContext();
    }

    private void a(int i2) {
        if (i2 <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("recordingIfNeed textureId = ");
            sb.append(i2);
            LOG.d(f41a, sb.toString());
            return;
        }
        if (!this.I && this.H != null && this.K) {
            String str = "video_test_log";
            LOG.d(str, "-- recordingIfNeed 1--");
            this.H.drawSurfaceWithTextureId(i2);
            b();
            LOG.d(str, "-- recordingIfNeed 2--");
        }
    }

    private void a(int i2, int i3, int i4, boolean z2) {
        if ((!this.J || !z2 || i3 != i4) && this.J && i2 > 0) {
            String str = "video_test_log";
            LOG.d(str, "-- captureIfNeed 1--");
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(this.q * this.r * 4);
            allocateDirect.order(ByteOrder.nativeOrder());
            LOG.d(str, "-- captureIfNeed 2--");
            GLES20.glReadPixels(this.s, this.t, this.q, this.r, 6408, 5121, allocateDirect);
            this.J = false;
            LOG.d(str, "-- captureIfNeed 3--");
            MediaResultCallback mediaResultCallback = this.N;
            if (mediaResultCallback != null) {
                mediaResultCallback.onCaptureResult(allocateDirect);
            }
        }
    }

    private void a(ASVLOFFSCREEN asvloffscreen) {
        String str = "Test_Arc";
        StringBuilder sb = new StringBuilder();
        sb.append("putPreviewInQueue  mPreviewQueue.size() = ");
        sb.append(this.u.size());
        String str2 = "CKK";
        LOG.d(str2, sb.toString());
        if (this.u.size() >= 1) {
            LOG.d(str2, "mPreviewQueue.poll()");
            this.u.poll();
        }
        try {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("mPreviewQueue.offer w = ");
            sb2.append(asvloffscreen.getWidth());
            sb2.append(" h = ");
            sb2.append(asvloffscreen.getHeight());
            sb2.append(" st = ");
            sb2.append(asvloffscreen.getRowStride());
            LOG.d(str2, sb2.toString());
            this.u.offer(asvloffscreen);
        } catch (ClassCastException e2) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("putPreviewInQueue  error1 = ");
            sb3.append(e2.toString());
            LOG.d(str, sb3.toString());
            e2.printStackTrace();
        } catch (NullPointerException e3) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("putPreviewInQueue  error2 = ");
            sb4.append(e3.toString());
            LOG.d(str, sb4.toString());
            e3.printStackTrace();
        } catch (IllegalArgumentException e4) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("putPreviewInQueue  error3 = ");
            sb5.append(e4.toString());
            LOG.d(str, sb5.toString());
            e4.printStackTrace();
        }
    }

    private void a(boolean z2) {
        int i2 = 0;
        int i3 = z2 ? this.s : 0;
        if (z2) {
            i2 = this.t;
        }
        GLES20.glViewport(i3, i2, this.q, this.r);
    }

    private void b() {
        long muxerTimeElapsed = this.H.getMuxerTimeElapsed();
        long muxerSizeRecorded = this.H.getMuxerSizeRecorded();
        long a2 = a(this.C);
        long j2 = muxerTimeElapsed / 1000000;
        if (j2 > this.E) {
            this.E = j2;
            RecordingListener recordingListener = this.L;
            if (recordingListener != null) {
                recordingListener.onRecordingListener(258, Long.valueOf(muxerTimeElapsed));
                this.L.onRecordingListener(260, Long.valueOf(muxerSizeRecorded));
            }
        }
        long j3 = this.F;
        String str = "video_test_log";
        if (j3 > 0 && muxerTimeElapsed > j3) {
            StringBuilder sb = new StringBuilder();
            sb.append("mMaxRecordingDuration = ");
            sb.append(this.F);
            sb.append(" ,timeElapsed = ");
            sb.append(muxerTimeElapsed);
            LOG.d(str, sb.toString());
            LOG.d(str, "-- controlRecordingProcess1 stopRecording--");
            stopRecording();
            RecordingListener recordingListener2 = this.L;
            if (recordingListener2 != null) {
                recordingListener2.onRecordingListener(257, Long.valueOf(muxerTimeElapsed));
            }
        }
        if (a2 > 0 && muxerSizeRecorded > a2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("adjuestedMaxSizle = ");
            sb2.append(a2);
            sb2.append(" ,sizeFile = ");
            sb2.append(muxerSizeRecorded);
            LOG.d(str, sb2.toString());
            LOG.d(str, "-- controlRecordingProcess2 stopRecording--");
            stopRecording();
            RecordingListener recordingListener3 = this.L;
            if (recordingListener3 != null) {
                recordingListener3.onRecordingListener(259, Long.valueOf(muxerSizeRecorded));
            }
        }
    }

    private boolean c() {
        ASAvatarProcessInfo aSAvatarProcessInfo = this.M;
        String str = "CheckOutLine";
        if (aSAvatarProcessInfo == null) {
            LOG.d(str, TEDefine.FACE_BEAUTY_NULL);
            this.O = true;
        } else if (aSAvatarProcessInfo.shelterIsNull()) {
            LOG.d(str, "shelterFlags == null");
            this.O = true;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("faceCount = ");
            sb.append(this.M.getFaceCount());
            LOG.d(str, sb.toString());
            if (this.M.getFaceCount() <= 0) {
                this.O = true;
            } else {
                boolean checkFaceBlocking = this.M.checkFaceBlocking();
                if (this.Q > 2) {
                    this.Q = 0;
                }
                boolean[] zArr = this.P;
                int i2 = this.Q;
                zArr[i2] = checkFaceBlocking;
                this.Q = i2 + 1;
                if (!zArr[0] || !zArr[1] || !zArr[2]) {
                    boolean[] zArr2 = this.P;
                    if (!zArr2[0] && !zArr2[1] && !zArr2[2]) {
                        this.O = false;
                    }
                } else {
                    this.O = true;
                }
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("--- > mBlockingFaces <---");
        sb2.append(this.O);
        LOG.d(str, sb2.toString());
        return this.O;
    }

    public void capture() {
        try {
            this.f45e.lock();
            if (this.g) {
                this.f45e.unlock();
                this.J = true;
            }
        } finally {
            this.f45e.unlock();
        }
    }

    public void changeHumanTemplate(String str, String str2) {
        String str3 = "test_log_c";
        LOG.d(str3, "-- changeHumanTemplate in--");
        StringBuilder sb = new StringBuilder();
        sb.append("-- templatePath = ");
        sb.append(str);
        LOG.d(str3, sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("-- configPath = ");
        sb2.append(str2);
        LOG.d(str3, sb2.toString());
        this.V = true;
        this.G.setTemplatePath(str);
        this.G.loadConfig(str2);
        this.V = false;
        LOG.d(str3, "-- changeHumanTemplate out--");
    }

    public AvatarEngine getAvatarEngine() {
        return this.G;
    }

    public void init(int i2, int i3, int i4, AvatarEngine avatarEngine, boolean z2) {
        this.g = false;
        this.h = false;
        this.i = false;
        this.j = false;
        this.z = false;
        this.K = false;
        this.I = false;
        this.o = i3;
        this.p = i4;
        this.G = avatarEngine;
        this.u = new LinkedList();
        this.u.clear();
        this.A = z2;
        this.B = i2;
        this.k = false;
        this.l = EGL14.EGL_NO_DISPLAY;
        this.m = EGL14.EGL_NO_CONTEXT;
        this.n = EGL14.EGL_NO_SURFACE;
        this.M = new ASAvatarProcessInfo();
        this.g = true;
    }

    public void pause() {
        this.T = null;
    }

    public void pauseRecording() {
        try {
            this.f45e.lock();
            if (this.g) {
                this.f45e.unlock();
                MediaManager mediaManager = this.H;
                if (mediaManager != null && this.K) {
                    this.I = true;
                    mediaManager.pauseRecording();
                }
            }
        } finally {
            this.f45e.unlock();
        }
    }

    public void reset() {
        this.U = true;
    }

    public void resumeRecording() {
        try {
            this.f45e.lock();
            if (this.g) {
                this.f45e.unlock();
                if (this.H != null && this.K && this.I) {
                    this.H.resumeRecording();
                    this.I = false;
                }
            }
        } finally {
            this.f45e.unlock();
        }
    }

    public void setAvatarEngine(AvatarEngine avatarEngine) {
        this.G = avatarEngine;
    }

    public void setDrawScope(int i2, int i3, int i4, int i5) {
        this.s = i2;
        this.t = i3;
        this.q = i4;
        this.r = i5;
    }

    public void setMirror(boolean z2) {
        this.A = z2;
    }

    public void setPreviewSize(int i2, int i3) {
        this.o = i2;
        this.p = i3;
        this.U = true;
    }

    public void setmImageOrientation(int i2) {
        this.B = i2;
    }

    public boolean startProcess(@NonNull ASVLOFFSCREEN asvloffscreen, int i2, boolean z2) {
        String str = "avatarProcessWithInfo";
        String str2 = "performance";
        String str3 = "buildNV21SingleBuffer";
        String str4 = "lock -> process unlock";
        try {
            this.f45e.lock();
            boolean z3 = this.g;
            String str5 = f41a;
            if (!z3) {
                LOG.d(str5, "startProcess_1() failed, engine is not inited. ");
                return true;
            }
            this.f45e.unlock();
            if (asvloffscreen == null) {
                return true;
            }
            try {
                this.i = true;
                LOG.d(str5, "lock -> process lock");
                this.f46f.lock();
                d.a(str3);
                this.T = asvloffscreen;
                d.a(str2, str3);
                this.f46f.unlock();
                LOG.d(str5, str4);
                this.h = true;
                if (!z2) {
                    this.i = false;
                    return true;
                }
                d.a(str);
                this.G.avatarProcessWithInfoEx(asvloffscreen, 90, this.A, i2, this.M, false);
                d.a(str2, str);
                this.U = c();
                this.i = false;
                return this.U;
            } catch (Exception e2) {
                try {
                    e2.printStackTrace();
                } catch (Throwable th) {
                    this.i = false;
                    throw th;
                }
            } catch (Throwable th2) {
                this.f46f.unlock();
                LOG.d(str5, str4);
                throw th2;
            }
        } finally {
            this.f45e.unlock();
        }
    }

    public boolean startProcess(@NonNull byte[] bArr, int i2, int i3, int i4, boolean z2) {
        byte[] bArr2 = bArr;
        int i5 = i3;
        int i6 = i4;
        String str = "avatarProcessWithInfo";
        String str2 = "lock -> process unlock";
        try {
            this.f45e.lock();
            boolean z3 = this.g;
            String str3 = f41a;
            if (!z3) {
                LOG.d(str3, "startProcess_1() failed, engine is not inited. ");
                return true;
            }
            this.f45e.unlock();
            if (bArr2 == null || bArr2.length <= 0 || i5 <= 0 || i6 <= 0) {
                return true;
            }
            try {
                this.i = true;
                LOG.d(str3, "lock -> process lock");
                this.f46f.lock();
                this.T = new ASVLOFFSCREEN(bArr, i5, i5, i6);
                this.f46f.unlock();
                LOG.d(str3, str2);
                this.h = true;
                if (!z2) {
                    this.i = false;
                    return true;
                }
                d.a(str);
                this.G.avatarProcessWithInfo(bArr, i3, i4, i3, 90, this.A, i2, this.M);
                d.a("performance", str);
                this.U = c();
                this.i = false;
                return this.U;
            } catch (Exception e2) {
                try {
                    e2.printStackTrace();
                } catch (Throwable th) {
                    this.i = false;
                    throw th;
                }
            } catch (Throwable th2) {
                this.f46f.unlock();
                LOG.d(str3, str2);
                throw th2;
            }
        } finally {
            this.f45e.unlock();
        }
    }

    public void startRecording(@NonNull FileDescriptor fileDescriptor, RecordingListener recordingListener, int i2, @NonNull int i3, @NonNull int i4, int i5, String str) {
        int i6 = i2;
        String str2 = "video_test_log";
        LOG.d(str2, "-- startRecording 1--");
        try {
            this.f45e.lock();
            if (!this.g) {
                LOG.d(f41a, "startRecording-> StickerApi is not init.");
                return;
            }
            this.f45e.unlock();
            if (i3 != 0 && i4 != 0 && fileDescriptor != null) {
                if (this.H != null) {
                    throw new RuntimeException("Recording has been started already.");
                } else if (i6 == 0 || 90 == i6 || 180 == i6 || 270 == i6) {
                    this.L = recordingListener;
                    if (EGL14.EGL_NO_CONTEXT == this.m) {
                        a();
                    }
                    MediaManager mediaManager = new MediaManager(fileDescriptor, i3, i4, this.B, this.A, i2, recordingListener);
                    this.H = mediaManager;
                    this.H.setEncoderCount(2);
                    this.H.initVideoEncoderWithSharedContext(this.m, i5, true, str);
                    this.H.initAudioEncoder();
                    this.H.startRecording();
                    this.K = true;
                    this.I = false;
                    LOG.d(str2, "-- startRecording 2--");
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("StickerApi-> startRecording(...) screenOrientation = ");
                    sb.append(i2);
                    sb.append(" is invalid");
                    throw new RuntimeException(sb.toString());
                }
            }
        } finally {
            this.f45e.unlock();
        }
    }

    public void startRecording(@NonNull String str, RecordingListener recordingListener, int i2, @NonNull int i3, @NonNull int i4, int i5, String str2) {
        int i6 = i2;
        String str3 = "video_test_log";
        LOG.d(str3, "-- startRecording 1--");
        try {
            this.f45e.lock();
            if (!this.g) {
                LOG.d(f41a, "startRecording-> StickerApi is not init.");
                return;
            }
            this.f45e.unlock();
            if (i3 != 0 && i4 != 0 && str.length() != 0) {
                if (this.H != null) {
                    throw new RuntimeException("Recording has been started already.");
                } else if (i6 == 0 || 90 == i6 || 180 == i6 || 270 == i6) {
                    this.L = recordingListener;
                    if (EGL14.EGL_NO_CONTEXT == this.m) {
                        a();
                    }
                    MediaManager mediaManager = new MediaManager(str, i3, i4, this.B, this.A, i2, recordingListener);
                    this.H = mediaManager;
                    this.H.setEncoderCount(2);
                    this.H.initVideoEncoderWithSharedContext(this.m, i5, true, str2);
                    this.H.initAudioEncoder();
                    this.H.startRecording();
                    this.K = true;
                    this.I = false;
                    LOG.d(str3, "-- startRecording 2--");
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("StickerApi-> startRecording(...) screenOrientation = ");
                    sb.append(i2);
                    sb.append(" is invalid");
                    throw new RuntimeException(sb.toString());
                }
            }
        } finally {
            this.f45e.unlock();
        }
    }

    public void startRender(int i2, boolean z2) {
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00ad A[Catch:{ all -> 0x00c6, Exception -> 0x00d5 }] */
    public void startRender(int i2, boolean z2, int i3, int i4, boolean z3, int[] iArr, byte[] bArr, boolean z4) {
        ASVLOFFSCREEN asvloffscreen;
        boolean z5;
        int[] iArr2 = iArr;
        String str = "lock -> startRender unlock";
        String str2 = "times";
        String str3 = "render lock";
        try {
            this.f45e.lock();
            boolean z6 = this.g;
            String str4 = f41a;
            if (z6) {
                if (this.h) {
                    this.f45e.unlock();
                    if (this.T == null) {
                        LOG.d(str4, "mBackgroundBuffer == null");
                        return;
                    }
                    try {
                        this.j = true;
                        LOG.d(str4, "lock -> startRender lock");
                        d.a(str3);
                        this.f46f.lock();
                        ASVLOFFSCREEN asvloffscreen2 = (ASVLOFFSCREEN) this.T.clone();
                        this.f46f.unlock();
                        d.a(str2, str3);
                        LOG.d(str4, str);
                        if (asvloffscreen2 != null && asvloffscreen2.getHeight() > 0) {
                            if (asvloffscreen2.getWidth() > 0) {
                                int height = asvloffscreen2.getHeight();
                                int width = asvloffscreen2.getWidth();
                                if (z4 && !this.U) {
                                    if (!this.V) {
                                        if (this.J) {
                                            height = asvloffscreen2.getHeight() * 2;
                                            width = asvloffscreen2.getWidth() * 2;
                                        }
                                        int i5 = height;
                                        asvloffscreen = asvloffscreen2;
                                        this.G.renderWithBackground(asvloffscreen2, i2, z2, i3, i5, width, i4, z3, iArr, bArr);
                                        height = i5;
                                        z5 = true;
                                        if (iArr2 != null) {
                                            this.G.renderBackgroundWithTexture(iArr2[0], 0, false);
                                            a(iArr2[0], asvloffscreen.getHeight(), height, z5);
                                            a(iArr2[0]);
                                        }
                                        this.j = false;
                                        return;
                                    }
                                }
                                asvloffscreen = asvloffscreen2;
                                this.G.renderBackgroundWithImageData(asvloffscreen, i2, z2, iArr2);
                                z5 = false;
                                if (iArr2 != null) {
                                }
                                this.j = false;
                                return;
                            }
                        }
                        this.j = false;
                        return;
                    } catch (Exception e2) {
                        try {
                            StringBuilder sb = new StringBuilder();
                            sb.append("startRender meet error = ");
                            sb.append(e2.getMessage());
                            LOG.d(str4, sb.toString());
                        } catch (Throwable th) {
                            this.j = false;
                            throw th;
                        }
                    } catch (Throwable th2) {
                        this.f46f.unlock();
                        d.a(str2, str3);
                        LOG.d(str4, str);
                        throw th2;
                    }
                }
            }
            LOG.d(str4, "startRender() failed, engine is not inited or startRender process not ready! ");
            this.f45e.unlock();
        } catch (Throwable th3) {
            this.f45e.unlock();
            throw th3;
        }
    }

    public void stopRecording() {
        String str = "video_test_log";
        LOG.d(str, "-- stopRecording 1--");
        try {
            this.f45e.lock();
            if (!this.g) {
                this.f45e.unlock();
                return;
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            this.f45e.unlock();
            throw th;
        }
        this.f45e.unlock();
        if (this.K) {
            LOG.d(str, "-- stopRecording 2--");
            if (this.H != null) {
                LOG.d(str, "-- stopRecording 3--");
                resumeRecording();
                LOG.d(str, "-- stopRecording 4--x");
                this.K = false;
                this.H.stopRecording();
                LOG.d(str, "-- stopRecording 5--");
                this.H = null;
                this.I = false;
                this.F = 0;
                this.E = 0;
                this.C = 0;
                MediaResultCallback mediaResultCallback = this.N;
                if (mediaResultCallback != null) {
                    mediaResultCallback.onVideoResult();
                }
            }
            LOG.d(str, "-- stopRecording 6--");
        }
    }

    public void unInit() {
        String str = "video_test_log";
        LOG.d(str, "-- unInit 1--");
        stopRecording();
        LOG.d(str, "-- unInit 2--");
        try {
            this.f45e.lock();
            if (!this.g) {
                LOG.d(f41a, "uninit () failed, engine is not inited. ");
                this.f45e.unlock();
                return;
            }
            this.g = false;
            int i2 = 100;
            while (true) {
                if (!this.i && !this.j) {
                    break;
                }
                i2--;
                if (i2 <= 0) {
                    break;
                }
                Thread.sleep(1);
            }
            this.k = false;
            this.J = false;
            this.u.clear();
            this.u = null;
            if (this.S != null && !this.S.isRecycled()) {
                this.S.isRecycled();
                this.S = null;
            }
            if (this.y != null) {
                this.y.deleteTexture();
                this.y = null;
            }
            if (this.x != null) {
                this.x.unInit();
                this.x = null;
            }
            if (this.v != null) {
                this.v.unInit();
                this.v = null;
            }
            if (this.w != null) {
                this.w.unInit();
                this.w = null;
            }
            this.T = null;
            this.m = EGL14.EGL_NO_CONTEXT;
            this.n = EGL14.EGL_NO_SURFACE;
            this.l = EGL14.EGL_NO_DISPLAY;
            this.q = 0;
            this.r = 0;
            this.i = false;
            this.j = false;
            this.h = false;
            this.f45e.unlock();
            LOG.d(str, "-- unInit 3--");
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            this.f45e.unlock();
            throw th;
        }
    }
}
