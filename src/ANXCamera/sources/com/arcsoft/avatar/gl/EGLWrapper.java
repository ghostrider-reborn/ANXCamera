package com.arcsoft.avatar.gl;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.view.Surface;
import com.arcsoft.avatar.util.b;

public class EGLWrapper {

    /* renamed from: a reason: collision with root package name */
    private static final String f59a = "Arc_EGLWrapper";

    /* renamed from: b reason: collision with root package name */
    private static final int f60b = 12610;

    /* renamed from: c reason: collision with root package name */
    private EGLContext f61c;

    /* renamed from: d reason: collision with root package name */
    private EGLDisplay f62d;

    /* renamed from: e reason: collision with root package name */
    private EGLSurface f63e;

    /* renamed from: f reason: collision with root package name */
    private EGLConfig[] f64f;
    private EGLContext g;
    private boolean h;
    private Surface i;
    private int j;
    private int k;
    private b l;

    public EGLWrapper(int i2, int i3) {
        this.f61c = EGL14.EGL_NO_CONTEXT;
        this.f62d = EGL14.EGL_NO_DISPLAY;
        this.f63e = EGL14.EGL_NO_SURFACE;
        this.f64f = new EGLConfig[1];
        this.g = EGL14.EGL_NO_CONTEXT;
        this.h = false;
        this.l = new b(f59a);
        this.h = true;
        this.j = i2;
        this.k = i3;
        c();
    }

    public EGLWrapper(Surface surface) {
        this.f61c = EGL14.EGL_NO_CONTEXT;
        this.f62d = EGL14.EGL_NO_DISPLAY;
        this.f63e = EGL14.EGL_NO_SURFACE;
        this.f64f = new EGLConfig[1];
        this.g = EGL14.EGL_NO_CONTEXT;
        this.h = false;
        this.l = new b(f59a);
        if (surface != null) {
            this.i = surface;
            c();
            return;
        }
        throw new NullPointerException();
    }

    public EGLWrapper(Surface surface, EGLContext eGLContext) {
        this.f61c = EGL14.EGL_NO_CONTEXT;
        this.f62d = EGL14.EGL_NO_DISPLAY;
        this.f63e = EGL14.EGL_NO_SURFACE;
        this.f64f = new EGLConfig[1];
        this.g = EGL14.EGL_NO_CONTEXT;
        this.h = false;
        this.l = new b(f59a);
        if (surface != null) {
            this.i = surface;
            this.g = eGLContext;
            c();
            return;
        }
        throw new NullPointerException();
    }

    private void a() {
        this.f63e = EGL14.eglCreateWindowSurface(this.f62d, this.f64f[0], this.i, new int[]{12344}, 0);
        a("eglCreateWindowSurface");
        if (this.f63e == null) {
            throw new RuntimeException("surface == null");
        }
    }

    private void a(String str) {
        int eglGetError = EGL14.eglGetError();
        if (eglGetError != 12288) {
            new Exception("NOT_ERROR_JUST_SEE_CALL_STACK").printStackTrace();
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(": EGL_ERROR_CODE: 0x");
            sb.append(Integer.toHexString(eglGetError));
            throw new RuntimeException(sb.toString());
        }
    }

    private void b() {
        this.f63e = EGL14.eglCreatePbufferSurface(this.f62d, this.f64f[0], new int[]{12375, this.j, 12374, this.k, 12344}, 0);
        a("createEGLPbufferSurface");
        if (this.f63e == null) {
            throw new RuntimeException("surface == null");
        }
    }

    private void c() {
        this.f62d = EGL14.eglGetDisplay(0);
        EGLDisplay eGLDisplay = this.f62d;
        if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
            int[] iArr = new int[2];
            if (EGL14.eglInitialize(eGLDisplay, iArr, 0, iArr, 1)) {
                int[] iArr2 = this.h ? new int[]{12339, 1, 12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 8, 12610, 1, 12344} : new int[]{12339, 4, 12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 8, 12610, 1, 12344};
                int[] iArr3 = new int[1];
                EGLDisplay eGLDisplay2 = this.f62d;
                EGLConfig[] eGLConfigArr = this.f64f;
                if (EGL14.eglChooseConfig(eGLDisplay2, iArr2, 0, eGLConfigArr, 0, eGLConfigArr.length, iArr3, 0)) {
                    this.f61c = EGL14.eglCreateContext(this.f62d, this.f64f[0], this.g, new int[]{12440, 2, 12344}, 0);
                    a("eglCreateContext");
                    if (this.f61c != null) {
                        if (this.h) {
                            b();
                        } else {
                            a();
                        }
                        this.j = getWidth();
                        this.k = getHeight();
                        b bVar = this.l;
                        StringBuilder sb = new StringBuilder();
                        sb.append("egl_Setup , display=");
                        sb.append(this.f62d);
                        sb.append(" ,context=");
                        sb.append(this.f61c);
                        sb.append(" ,sharedContext= ");
                        sb.append(this.g);
                        sb.append(", surface=");
                        sb.append(this.f63e);
                        sb.append(", w=");
                        sb.append(this.j);
                        sb.append(" ,h=");
                        sb.append(this.k);
                        bVar.a(sb.toString());
                        return;
                    }
                    throw new RuntimeException("eglCreateContext == null");
                }
                throw new RuntimeException("eglChooseConfig [RGBA888 + recordable] ES2 EGL_config_fail...");
            }
            this.f62d = null;
            throw new RuntimeException("EGL14.eglInitialize fail...");
        }
        throw new RuntimeException("EGL14.eglGetDisplay fail...");
    }

    private void d() {
        EGLDisplay eGLDisplay = this.f62d;
        if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
            EGL14.eglDestroySurface(eGLDisplay, this.f63e);
            this.f63e = EGL14.EGL_NO_SURFACE;
        }
    }

    public int getHeight() {
        int[] iArr = new int[1];
        EGL14.eglQuerySurface(this.f62d, this.f63e, 12374, iArr, 0);
        return iArr[0];
    }

    public Surface getSurface() {
        return this.i;
    }

    public int getWidth() {
        int[] iArr = new int[1];
        EGL14.eglQuerySurface(this.f62d, this.f63e, 12375, iArr, 0);
        return iArr[0];
    }

    public boolean makeCurrent() {
        EGLDisplay eGLDisplay = this.f62d;
        if (eGLDisplay != null) {
            EGLSurface eGLSurface = this.f63e;
            if (eGLSurface != null) {
                boolean eglMakeCurrent = EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.f61c);
                if (!eglMakeCurrent) {
                    a("makeCurrent");
                }
                b bVar = this.l;
                StringBuilder sb = new StringBuilder();
                sb.append("makeCurrent()-> ");
                sb.append(eglMakeCurrent);
                bVar.a(sb.toString());
                return eglMakeCurrent;
            }
        }
        this.l.a("makeCurrent()-> failed");
        return false;
    }

    public void makeUnCurrent() {
        EGLDisplay eGLDisplay = this.f62d;
        EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
        if (!EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT)) {
            a("makeUnCurrent");
        }
    }

    public void release() {
        EGLDisplay eGLDisplay = this.f62d;
        if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
            EGL14.eglDestroySurface(eGLDisplay, this.f63e);
            EGL14.eglDestroyContext(this.f62d, this.f61c);
            EGL14.eglTerminate(this.f62d);
        }
        this.f62d = EGL14.EGL_NO_DISPLAY;
        this.f61c = EGL14.EGL_NO_CONTEXT;
        this.f63e = EGL14.EGL_NO_SURFACE;
        this.g = EGL14.EGL_NO_CONTEXT;
        try {
            if (this.i != null) {
                this.i.release();
            }
        } catch (Exception e2) {
            b bVar = this.l;
            StringBuilder sb = new StringBuilder();
            sb.append("release release mSurface failed : ");
            sb.append(e2.getMessage());
            bVar.c(sb.toString());
        }
        this.i = null;
    }

    public void setPresentationTime(long j2) {
        EGLExt.eglPresentationTimeANDROID(this.f62d, this.f63e, j2);
        a("eglPresentationTimeANDROID");
    }

    public boolean swapBuffers() {
        EGLDisplay eGLDisplay = this.f62d;
        if (eGLDisplay != null) {
            EGLSurface eGLSurface = this.f63e;
            if (eGLSurface != null) {
                boolean eglSwapBuffers = EGL14.eglSwapBuffers(eGLDisplay, eGLSurface);
                if (!eglSwapBuffers) {
                    a("makeCurrent");
                }
                return eglSwapBuffers;
            }
        }
        return false;
    }

    public void updateSize(int i2, int i3) {
        if (i2 != this.j || i3 != this.k) {
            this.l.a("re-create EGLSurface");
            d();
            a();
            this.j = getWidth();
            this.k = getHeight();
        }
    }
}
