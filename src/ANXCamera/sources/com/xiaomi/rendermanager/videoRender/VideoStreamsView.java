package com.xiaomi.rendermanager.videoRender;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Debug;
import android.os.Debug.MemoryInfo;
import android.provider.MiuiSettings.ScreenEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import com.android.camera.Util;
import com.xiaomi.rendermanager.videoRender.VideoRenderer.I420Frame;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class VideoStreamsView extends GLSurfaceView implements Renderer {
    private static final String FRAGMENT_SHADER_STRING = "precision mediump float;\nvarying vec2 interp_tc;\n\nuniform float width;\nuniform float height;\nuniform float width_stride;\nuniform float height_stride;\nuniform float offset;\nuniform float slope;\nuniform float sharpCoff;\nuniform float sourceCoff;\nuniform sampler2D y_tex;\nuniform sampler2D u_tex;\nuniform sampler2D v_tex;\n\nvarying highp float imageWidthFactor; \nvarying highp float imageHeightFactor; \nvarying highp vec2 leftTextureCoordinate;\nvarying highp vec2 rightTextureCoordinate; \nvarying highp vec2 topTextureCoordinate;\nvarying highp vec2 bottomTextureCoordinate;\n\nvarying highp float centerMultiplier;\nvarying highp float edgeMultiplier;\n\nuniform sampler2D inputImageTexture;\n\nvoid main() {\n  float wRatio = (width - 1.0) / width_stride;\n  float hRatio = height / height_stride;\n  vec2 pos = interp_tc * vec2(wRatio, hRatio);\n  mediump vec2 widthStep = vec2(imageWidthFactor, 0.0);\n  mediump vec2 heightStep = vec2(0.0, imageHeightFactor);\n  vec2 leftTextureCoordinate = interp_tc.xy - widthStep/width;\n  vec2 rightTextureCoordinate = interp_tc.xy + widthStep/width;\n  vec2 topTextureCoordinate = interp_tc.xy + heightStep/height;     \n  vec2 bottomTextureCoordinate = interp_tc.xy - heightStep/height;\n  vec2 lpos = leftTextureCoordinate * vec2(wRatio, hRatio);\n  vec2 rpos = rightTextureCoordinate * vec2(wRatio, hRatio);\n  vec2 tpos = topTextureCoordinate * vec2(wRatio, hRatio);\n  vec2 bpos = bottomTextureCoordinate * vec2(wRatio, hRatio);\n  mediump float ly = texture2D(y_tex, lpos).r;\n  mediump float lu = texture2D(u_tex, lpos).r - .5;\n  mediump float lv = texture2D(v_tex, lpos).r - .5;\n  mediump float ry = texture2D(y_tex, rpos).r;\n  mediump float ru = texture2D(u_tex, rpos).r - .5;\n  mediump float rv = texture2D(v_tex, rpos).r - .5;\n  mediump float ty = texture2D(y_tex, tpos).r;\n  mediump float tu = texture2D(u_tex, tpos).r - .5;\n  mediump float tv = texture2D(v_tex, tpos).r - .5;\n  mediump float by = texture2D(y_tex, bpos).r;\n  mediump float bu = texture2D(u_tex, bpos).r - .5;\n  mediump float bv = texture2D(v_tex, bpos).r - .5;\n  float y =  texture2D(y_tex, pos).r;\n  float u =  texture2D(u_tex, pos).r - .5;\n  float v =  texture2D(v_tex, pos).r - .5;\n  y = (y * centerMultiplier - (ly + ry +ty  + by) * edgeMultiplier)*sharpCoff + y*sourceCoff;\n  y = (1.0 + slope)*y  -  slope*offset;\n  gl_FragColor = vec4(y + 1.403 * v,                       y - 0.344 * u - 0.714 * v,                       y + 1.77 * u, 1);\n}\n";
    private static final int ORIENTATION_DOWN = 1;
    private static final int ORIENTATION_LEFT = 2;
    private static final int ORIENTATION_RIGHT = 3;
    private static final int ORIENTATION_UP = 0;
    private static final String TAG = "VideoStreamsView";
    private static final String VERTEX_SHADER_STRING = "varying vec2 interp_tc;\nattribute vec4 in_pos;\nattribute vec2 in_tc;\n\nvarying float imageWidthFactor; \nvarying float imageHeightFactor; \nuniform float sharpStrength; \nvarying float sharpness;\n\nvarying vec2 textureCoordinate;\n\nvarying float centerMultiplier;\nvarying float edgeMultiplier;\n\n\nvoid main() {\n  gl_Position = in_pos;\n    \n imageWidthFactor = 1.0;\n imageHeightFactor = 1.0;\n sharpness = sharpStrength;\n    \n    textureCoordinate = in_tc.xy;\n    \n    centerMultiplier = 1.0 + 4.0 * sharpness;\n    edgeMultiplier = sharpness;\n  interp_tc = in_tc ;\n}\n";
    private static final FloatBuffer downTextureCoord = directNativeFloatBuffer(new float[]{0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f});
    private static final FloatBuffer downTextureCoordMirror = directNativeFloatBuffer(new float[]{1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f});
    private static final FloatBuffer leftTextureCoord = directNativeFloatBuffer(new float[]{1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f});
    private static final FloatBuffer leftTextureCoordMirror = directNativeFloatBuffer(new float[]{0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f});
    private static int logCounter;
    private static final FloatBuffer rightTextureCoord = directNativeFloatBuffer(new float[]{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f});
    private static final FloatBuffer rightTextureCoordMirror = directNativeFloatBuffer(new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f});
    private static final FloatBuffer upTextureCoord = directNativeFloatBuffer(new float[]{1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f});
    private static final FloatBuffer upTextureCoordMirror = directNativeFloatBuffer(new float[]{0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f});
    private static final FloatBuffer vertices = directNativeFloatBuffer(new float[]{1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f});
    public int _RatioHeight;
    public int _RatioWidth;
    public int _RatioX = 0;
    public int _RatioY = 0;
    public int _lastFrameAngle = 0;
    public int _lastYStride = 0;
    public int _lastYUVHeight = 0;
    public int _lastYUVWidth = 0;
    public int _oriViewHeight;
    public int _oriViewWidth;
    private boolean _renderModelChanged = false;
    private float _shiftUpVideoRatio = 0.0f;
    private float _shiftUpVideoRatioDelta = -1.0f;
    private float _shiftUpVideoValue = 0.0f;
    private float _shiftUpViewRatio = 0.0f;
    private float _shiftUpViewRatioDelta = -1.0f;
    public boolean _surfaceSizeChanged = true;
    private float canvasToFrameRatio = 1.0f;
    public int currentIndex;
    private int debug_increment = 0;
    private FramePool framePool = null;
    private I420Frame framesToRender;
    private int heightLocation = -1;
    private int heightStrideLocation = -1;
    public boolean isRenderThreadRunning = false;
    private long lastFPSLogTime = System.nanoTime();
    public long nativeObject = 0;
    private long numFramesSinceLastLog = 0;
    private int offsetLocation = -1;
    public String participantUID = "";
    private int posLocation = -1;
    private RenderModel renderModel;
    private Point screenDimensions;
    public int screenHeight = 0;
    public int screenWidth = 0;
    private int sharpCoffLocation = -1;
    private int sharpStrengthLocation = -1;
    private int slopeLocation = -1;
    private int sourceCoffLocation = -1;
    private int tcLocation = -1;
    private int widthLocation = -1;
    private int widthStrideLocation = -1;
    private int[] yuvTextures = {-1, -1, -1};

    public enum RenderModel {
        RENDER_MODEL_AUTO,
        RENDER_MODEL_FIT,
        RENDER_MODEL_CUT
    }

    public VideoStreamsView(Context context, Point point) {
        super(context);
        this._oriViewWidth = point.x;
        this._oriViewHeight = point.y;
        Log.i("VideoStreamView", "init stream view");
        this.screenDimensions = point;
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(0);
        setDebugFlags(1);
        this.nativeObject = create();
        synchronized (this) {
            this.framePool = new FramePool();
            Log.i("VideoStreamView", "creating the java frame pool");
        }
        this.currentIndex = 0;
        this.renderModel = RenderModel.RENDER_MODEL_AUTO;
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        int width = defaultDisplay.getWidth();
        this.screenWidth = width;
        this.screenWidth = width;
        int height = defaultDisplay.getHeight();
        this.screenHeight = height;
        this.screenHeight = height;
        StringBuilder sb = new StringBuilder();
        sb.append(" get screen resolution:");
        sb.append(this.screenWidth);
        sb.append("x");
        sb.append(this.screenHeight);
        Log.i("VideoStreamView", sb.toString());
        Log.i("VideoStreamView", " init OK");
    }

    public VideoStreamsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Log.i("VideoStreamView", "init stream view");
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        int width = defaultDisplay.getWidth();
        this.screenWidth = width;
        this.screenWidth = width;
        int height = defaultDisplay.getHeight();
        this.screenHeight = height;
        this.screenHeight = height;
        StringBuilder sb = new StringBuilder();
        sb.append(" get screen resolution:");
        sb.append(this.screenWidth);
        sb.append("x");
        sb.append(this.screenHeight);
        Log.i("VideoStreamView", sb.toString());
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{16842996, 16842997});
        int layoutDimension = obtainStyledAttributes.getLayoutDimension(0, -1);
        int layoutDimension2 = obtainStyledAttributes.getLayoutDimension(1, -1);
        obtainStyledAttributes.recycle();
        if (layoutDimension < 0) {
            layoutDimension = this.screenWidth;
        }
        if (layoutDimension2 < 0) {
            layoutDimension2 = this.screenHeight;
        }
        this._oriViewWidth = layoutDimension;
        this._oriViewHeight = layoutDimension2;
        this.screenDimensions = new Point(this._oriViewWidth, this._oriViewHeight);
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(0);
        setDebugFlags(1);
        synchronized (this) {
            this.framePool = new FramePool();
            Log.i(TAG, "VideoStreamView: creating the java frame pool");
        }
        this.currentIndex = 0;
        this.renderModel = RenderModel.RENDER_MODEL_AUTO;
        Log.i("VideoStreamView", " init OK");
    }

    private static void abortUnless(boolean z, String str) {
        if (!z) {
            throw new RuntimeException(str);
        }
    }

    private static void addShaderTo(int i, String str, int i2) {
        boolean z = true;
        int[] iArr = {0};
        int glCreateShader = GLES20.glCreateShader(i);
        abortUnless(glCreateShader != 0, "Create opengl shader failed.");
        GLES20.glShaderSource(glCreateShader, str);
        checkNoGLES2Error();
        GLES20.glCompileShader(glCreateShader);
        checkNoGLES2Error();
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 1) {
            z = false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(GLES20.glGetShaderInfoLog(glCreateShader));
        sb.append(", source: ");
        sb.append(str);
        abortUnless(z, sb.toString());
        GLES20.glAttachShader(i2, glCreateShader);
        checkNoGLES2Error();
        GLES20.glDeleteShader(glCreateShader);
        checkNoGLES2Error();
    }

    private native boolean bindStream(long j, String str, boolean z);

    private static void checkNoGLES2Error() {
        int glGetError = GLES20.glGetError();
        if (glGetError != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("GLES20 error:");
            sb.append(glGetError);
            Log.e(TAG, sb.toString());
        }
        boolean z = glGetError == 0;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("GLES20 error: ");
        sb2.append(glGetError);
        abortUnless(z, sb2.toString());
    }

    private native long create();

    private native void destory(long j);

    private static FloatBuffer directNativeFloatBuffer(float[] fArr) {
        FloatBuffer asFloatBuffer = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        asFloatBuffer.put(fArr);
        asFloatBuffer.flip();
        return asFloatBuffer;
    }

    private void drawRectangle(int[] iArr, FloatBuffer floatBuffer) {
        for (int i = 0; i < 3; i++) {
            GLES20.glActiveTexture(33984 + i);
            checkNoGLES2Error();
            GLES20.glBindTexture(3553, iArr[i]);
            checkNoGLES2Error();
        }
        GLES20.glVertexAttribPointer(this.posLocation, 2, 5126, false, 0, floatBuffer);
        checkNoGLES2Error();
        GLES20.glEnableVertexAttribArray(this.posLocation);
        checkNoGLES2Error();
        GLES20.glDrawArrays(5, 0, 4);
        checkNoGLES2Error();
    }

    private static void fill(ByteBuffer byteBuffer, int i) {
        for (int i2 = 0; i2 < byteBuffer.capacity(); i2++) {
            byteBuffer.put(i2, (byte) i);
        }
    }

    private native void setPreviewRender(long j);

    private native void setShiftUp(long j, float f2, float f3, float f4, float f5, float f6);

    private native void setUIViewSize(long j, long j2, long j3);

    private native boolean setWindowsResolution(String str, float f2, float f3);

    private void sleep(int i) {
    }

    private void texImage2D(I420Frame i420Frame, int[] iArr) {
        FloatBuffer floatBuffer;
        int i = 0;
        while (i < 3) {
            ByteBuffer byteBuffer = i420Frame.yuvPlanes[i];
            GLES20.glActiveTexture(33984 + i);
            checkNoGLES2Error();
            GLES20.glBindTexture(3553, iArr[i]);
            checkNoGLES2Error();
            int[] iArr2 = i420Frame.yuvStrides;
            int i2 = i == 0 ? iArr2[0] : iArr2[0] / 2;
            int i3 = i420Frame.height;
            if (i != 0) {
                i3 /= 2;
            }
            GLES20.glTexImage2D(3553, 0, 6409, i2, i3, 0, 6409, 5121, byteBuffer);
            checkNoGLES2Error();
            i++;
        }
        GLES20.glUniform1f(this.widthLocation, (float) i420Frame.width);
        GLES20.glUniform1f(this.heightLocation, (float) i420Frame.height);
        GLES20.glUniform1f(this.widthStrideLocation, (float) i420Frame.yuvStrides[0]);
        GLES20.glUniform1f(this.heightStrideLocation, (float) i420Frame.height);
        GLES20.glUniform1f(this.offsetLocation, i420Frame.offset);
        GLES20.glUniform1f(this.slopeLocation, i420Frame.slope);
        GLES20.glUniform1f(this.sourceCoffLocation, i420Frame.sourceCoff);
        GLES20.glUniform1f(this.sharpCoffLocation, i420Frame.sharpCoff);
        GLES20.glUniform1f(this.sharpStrengthLocation, i420Frame.sharpStrength);
        GLES20.glEnableVertexAttribArray(this.tcLocation);
        checkNoGLES2Error();
        boolean z = i420Frame.localPreview && !i420Frame.backCamera;
        FloatBuffer floatBuffer2 = null;
        int i4 = (i420Frame.rotateAngle + Util.LIMIT_SURFACE_WIDTH) % ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT;
        if (i4 != 0) {
            if (i4 == 90) {
                floatBuffer = z ? leftTextureCoordMirror : leftTextureCoord;
            } else if (i4 == 180) {
                floatBuffer = z ? downTextureCoordMirror : downTextureCoord;
            } else if (i4 != 270) {
                StringBuilder sb = new StringBuilder();
                sb.append("totalAngle: ");
                sb.append(i4);
                sb.append(" not supported.");
                abortUnless(false, sb.toString());
            } else {
                floatBuffer = z ? rightTextureCoordMirror : rightTextureCoord;
            }
            floatBuffer2 = floatBuffer;
        } else {
            floatBuffer = z ? upTextureCoordMirror : upTextureCoord;
            floatBuffer2 = floatBuffer;
        }
        GLES20.glVertexAttribPointer(this.tcLocation, 2, 5126, false, 0, floatBuffer2);
        checkNoGLES2Error();
    }

    private native boolean unbindStream(long j);

    /* access modifiers changed from: private */
    public void updateFrames() {
        int i = logCounter;
        logCounter = i + 1;
        if (i % 200 == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("The view size top:");
            sb.append(getTop());
            sb.append(" bottom:");
            sb.append(getBottom());
            sb.append(" left:");
            sb.append(getLeft());
            sb.append(" right:");
            sb.append(getRight());
            sb.append(" visiblility:");
            sb.append(getVisibility());
            sb.append(" 0 for visible 4 for invisible and 8 for gone");
            sb.append(" view:");
            sb.append(this);
            Log.i(TAG, sb.toString());
        }
        requestRender();
    }

    public int CalcRatioViewPort(int i, int i2, int i3) {
        int i4;
        int i5;
        int i6 = i;
        int i7 = i2;
        int i8 = i3;
        if (i8 == 90 || i8 == 270) {
            i4 = i6;
            i5 = i7;
        } else {
            i5 = i6;
            i4 = i7;
        }
        double d2 = ((double) i5) * 1.0d;
        int i9 = this._oriViewWidth;
        float f2 = (float) (d2 / ((double) i9));
        double d3 = ((double) i4) * 1.0d;
        int i10 = this._oriViewHeight;
        float f3 = (float) (d3 / ((double) i10));
        if (f2 > f3) {
            this._RatioHeight = i10;
            this._RatioWidth = (i10 * i5) / i4;
            this._RatioX = (i9 - this._RatioWidth) / 2;
            this._RatioY = 0;
        } else {
            this._RatioWidth = i9;
            this._RatioHeight = (i9 * i4) / i5;
            this._RatioY = (i10 - this._RatioHeight) / 2;
            this._RatioX = 0;
        }
        int i11 = this._oriViewWidth * this._oriViewHeight;
        int i12 = this._RatioWidth * this._RatioHeight;
        float f4 = ((float) i11) / ((float) i12);
        StringBuilder sb = new StringBuilder();
        sb.append("VideoStreamView: VideoStreamView opengl setting first calculate: ");
        sb.append(this._RatioX);
        String str = " _RatioY:";
        sb.append(str);
        sb.append(this._RatioY);
        String str2 = " _RatioWidth:";
        sb.append(str2);
        sb.append(this._RatioWidth);
        String str3 = " _RatioHeight:";
        sb.append(str3);
        sb.append(this._RatioHeight);
        String str4 = " screen height:";
        sb.append(str4);
        sb.append(this._oriViewHeight);
        String str5 = " screen Width:";
        sb.append(str5);
        String str6 = str5;
        sb.append(this._oriViewWidth);
        String str7 = " frame height:";
        sb.append(str7);
        sb.append(i7);
        String str8 = " frame width:";
        sb.append(str8);
        sb.append(i6);
        String str9 = str8;
        sb.append(" showRatio:");
        sb.append(f4);
        sb.append(" screen:");
        sb.append(i11);
        sb.append(" showsize:");
        sb.append(i12);
        sb.append(" wRatio:");
        sb.append(f2);
        sb.append(" hRatio:");
        sb.append(f3);
        sb.append(" rotateImageWidth:");
        sb.append(i5);
        sb.append(" rotateImageHeight:");
        sb.append(i4);
        String sb2 = sb.toString();
        String str10 = TAG;
        Log.i(str10, sb2);
        if ((((double) f4) >= 0.45d || this.renderModel != RenderModel.RENDER_MODEL_AUTO) && this.renderModel != RenderModel.RENDER_MODEL_FIT) {
            Log.i(str10, "VideoStreamView: opengl setting using CUT modal");
        } else {
            Log.i(str10, "VideoStreamView: opengl setting using FIT modal");
            if (f3 > f2) {
                this._RatioHeight = this._oriViewHeight;
                this._RatioWidth = (this._RatioHeight * i5) / i4;
                this._RatioX = (this._oriViewWidth - this._RatioWidth) / 2;
                this._RatioY = 0;
            } else {
                this._RatioWidth = this._oriViewWidth;
                this._RatioHeight = (this._RatioWidth * i4) / i5;
                this._RatioX = 0;
                this._RatioY = (this._oriViewHeight - this._RatioHeight) / 2;
            }
        }
        if (f2 < f3) {
            this.canvasToFrameRatio = (((float) (this._oriViewHeight * i5)) * 1.0f) / ((float) (this._oriViewWidth * i4));
        } else {
            this.canvasToFrameRatio = (((float) (this._oriViewWidth * i4)) * 1.0f) / ((float) (this._oriViewHeight * i5));
        }
        float max = ((float) this._oriViewWidth) / ((float) Math.max(this._oriViewHeight, 1));
        if (Math.abs((((float) i5) / ((float) Math.max(i4, 1))) - this._shiftUpVideoRatio) < this._shiftUpVideoRatioDelta && Math.abs(max - this._shiftUpViewRatio) < this._shiftUpViewRatioDelta) {
            this._RatioY = (int) (((float) this._RatioY) + (this._shiftUpVideoValue * ((float) this._oriViewHeight)));
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("VideoStreamView: VideoStreamView opengl setting _RatioX: ");
        sb3.append(this._RatioX);
        sb3.append(str);
        sb3.append(this._RatioY);
        sb3.append(str2);
        sb3.append(this._RatioWidth);
        sb3.append(str3);
        sb3.append(this._RatioHeight);
        sb3.append(str4);
        sb3.append(this._oriViewHeight);
        sb3.append(str6);
        sb3.append(this._oriViewWidth);
        sb3.append(str7);
        String str11 = str9;
        sb3.append(i2);
        sb3.append(str11);
        sb3.append(i6);
        Log.i(str10, sb3.toString());
        return 0;
    }

    public boolean bindRenderWithStream(String str, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("VideoStreamView: bindRenderWithStream with:");
        sb.append(str);
        sb.append(" view:");
        sb.append(this);
        Log.i(TAG, sb.toString());
        this.participantUID = str;
        if (this.nativeObject == 0) {
            this.nativeObject = create();
        }
        setWindowsResolution(this.participantUID, ((float) this._oriViewWidth) / ((float) this.screenWidth), ((float) this._oriViewHeight) / ((float) this.screenHeight));
        return bindStream(this.nativeObject, str, z);
    }

    public void destoryNativeRender() {
        destory(this.nativeObject);
        synchronized (this) {
            Log.i(TAG, "VideoStreamView:release the java frame pool");
            this.framePool = null;
        }
    }

    public void generateTexture(int i, int i2) {
        Log.i(TAG, "VideoStreamView: Generate the texture.");
        ByteBuffer allocate = ByteBuffer.allocate(i * i2);
        int i3 = 0;
        fill(allocate, 0);
        int i4 = i / 2;
        ByteBuffer allocate2 = ByteBuffer.allocate((i4 * i2) / 2);
        fill(allocate2, 128);
        int[] iArr = this.yuvTextures;
        GLES20.glGenTextures(3, iArr, 0);
        checkNoGLES2Error();
        while (i3 < 3) {
            int i5 = i3 == 0 ? i : i4;
            int i6 = i3 == 0 ? i2 : i2 / 2;
            GLES20.glActiveTexture(33984 + i3);
            checkNoGLES2Error();
            GLES20.glBindTexture(3553, iArr[i3]);
            checkNoGLES2Error();
            GLES20.glTexImage2D(3553, 0, 6409, i5, i6, 0, 6409, 5121, i3 == 0 ? allocate : allocate2);
            checkNoGLES2Error();
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            checkNoGLES2Error();
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            checkNoGLES2Error();
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            checkNoGLES2Error();
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            checkNoGLES2Error();
            i3++;
        }
    }

    public float getConvertRatio() {
        return this.renderModel == RenderModel.RENDER_MODEL_FIT ? 1.0f / this.canvasToFrameRatio : this.canvasToFrameRatio;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i(TAG, "attach the surface to window");
        this.isRenderThreadRunning = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i(TAG, "detach the surface to window");
        this.isRenderThreadRunning = false;
    }

    public void onDrawFrame(GL10 gl10) {
        synchronized (this) {
            if (this.framesToRender == null) {
                Log.i(TAG, "onDrawFrame: framesToRender  is null !");
                return;
            }
            I420Frame i420Frame = this.framesToRender;
            this.framesToRender = null;
            if (this.framePool == null) {
                Log.i(TAG, "onDrawFrame: framePool is null !");
                return;
            }
            if (i420Frame == null) {
                Log.i(TAG, "updateFrames: frame is null !");
            }
            if (i420Frame != null) {
                if (!(this._lastYUVWidth == i420Frame.width && this._lastYUVHeight == i420Frame.height && !this._renderModelChanged && !this._surfaceSizeChanged && this._lastFrameAngle == i420Frame.rotateAngle && this._lastYStride == i420Frame.yuvStrides[0])) {
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Generate texture because one of following property change: _lastYUVWidth:");
                    sb.append(this._lastYUVWidth);
                    sb.append(" _lastYUVHeight:");
                    sb.append(this._lastYUVHeight);
                    sb.append(" _renderModelChanged:");
                    sb.append(this._renderModelChanged);
                    sb.append(" _surfaceSizeChanged:");
                    sb.append(this._surfaceSizeChanged);
                    sb.append(" _lastYStride:");
                    sb.append(this._lastYStride);
                    Log.i(str, sb.toString());
                    this._lastYStride = i420Frame.yuvStrides[0];
                    CalcRatioViewPort(i420Frame.width, i420Frame.height, (i420Frame.rotateAngle + Util.LIMIT_SURFACE_WIDTH) % ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT);
                    checkNoGLES2Error();
                    this._lastYUVWidth = i420Frame.width;
                    this._lastYUVHeight = i420Frame.height;
                    this._lastFrameAngle = i420Frame.rotateAngle;
                    this._surfaceSizeChanged = false;
                    this._renderModelChanged = false;
                }
                texImage2D(i420Frame, this.yuvTextures);
                checkNoGLES2Error();
                this.framePool.returnFrame(i420Frame);
            }
            abortUnless(i420Frame != null, "Nothing to render!");
            GLES20.glViewport(this._RatioX, this._RatioY, this._RatioWidth, this._RatioHeight);
            GLES20.glClear(16384);
            Boolean valueOf = Boolean.valueOf(false);
            FloatBuffer directNativeFloatBuffer = directNativeFloatBuffer(new float[]{0.0f - ((float) ((((double) this.debug_increment) * 0.01d) + 0.5d)), 0.0f - ((float) ((((double) this.debug_increment) * 0.01d) + 0.5d)), 0.0f - ((float) ((((double) this.debug_increment) * 0.01d) + 0.5d)), 0.0f - ((float) ((((double) this.debug_increment) * 0.01d) + 0.5d)), (float) ((((double) this.debug_increment) * 0.01d) + 0.5d), (float) ((((double) this.debug_increment) * 0.01d) + 0.5d), (float) ((((double) this.debug_increment) * 0.01d) + 0.5d), 0.0f - ((float) ((((double) this.debug_increment) * 0.01d) + 0.5d))});
            this.debug_increment++;
            this.debug_increment %= 50;
            int[] iArr = this.yuvTextures;
            if (!valueOf.booleanValue()) {
                directNativeFloatBuffer = vertices;
            }
            drawRectangle(iArr, directNativeFloatBuffer);
            checkNoGLES2Error();
            this.numFramesSinceLastLog++;
            long nanoTime = System.nanoTime();
            if (this.lastFPSLogTime == -1 || ((double) (nanoTime - this.lastFPSLogTime)) > 1.0E9d) {
                this.lastFPSLogTime = nanoTime;
                this.numFramesSinceLastLog = 1;
            }
            checkNoGLES2Error();
        }
    }

    public void onPause() {
        StringBuilder sb = new StringBuilder();
        sb.append("VideoStreamView: VideoStreamView paused render. view:");
        sb.append(this);
        Log.i(TAG, sb.toString());
        super.onPause();
    }

    public void onResume() {
        StringBuilder sb = new StringBuilder();
        sb.append("VideoStreamView: VideoStreamView resume render. view:");
        sb.append(this);
        Log.i(TAG, sb.toString());
        super.onResume();
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("VideoStreamView: On Surface changed with with:");
        sb.append(i);
        sb.append(" height:");
        sb.append(i2);
        String sb2 = sb.toString();
        String str = TAG;
        Log.i(str, sb2);
        this._oriViewWidth = i;
        this._oriViewHeight = i2;
        this._surfaceSizeChanged = true;
        float f2 = ((float) i) / ((float) this.screenWidth);
        float f3 = ((float) i2) / ((float) this.screenHeight);
        StringBuilder sb3 = new StringBuilder();
        sb3.append("VideoStreamView: On Surface set width_ratio:");
        sb3.append(f2);
        sb3.append(" height_ratio:");
        sb3.append(f3);
        Log.i(str, sb3.toString());
        setWindowsResolution(this.participantUID, f2, f3);
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        String str = TAG;
        Log.i(str, "VideoStreamView: surface created");
        try {
            generateTexture(1024, 1024);
        } catch (Exception unused) {
            MemoryInfo memoryInfo = new MemoryInfo();
            Debug.getMemoryInfo(memoryInfo);
            StringBuilder sb = new StringBuilder();
            sb.append(" dalvikPrivateDirty: ");
            sb.append(memoryInfo.dalvikPrivateDirty);
            String str2 = "";
            sb.append(str2);
            Log.i(str, sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(" nativePss: ");
            sb2.append(memoryInfo.nativePss);
            sb2.append(str2);
            Log.i(str, sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(" otherSharedDirty: ");
            sb3.append(memoryInfo.otherSharedDirty);
            sb3.append(str2);
            Log.i(str, sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(" totalPrivate: ");
            sb4.append(memoryInfo.getTotalPrivateDirty());
            sb4.append(", ");
            sb4.append(" totalPss:");
            sb4.append(memoryInfo.getTotalPss());
            sb4.append(" totalShared");
            sb4.append(memoryInfo.getTotalSharedDirty());
            sb4.append(str2);
            Log.i(str, sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append("NativeHeapSizeTotal:");
            sb5.append(Debug.getNativeHeapSize() >> 10);
            Log.i(str, sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append("NativeAllocatedHeapSize:");
            sb6.append(Debug.getNativeHeapAllocatedSize() >> 10);
            Log.i(str, sb6.toString());
            StringBuilder sb7 = new StringBuilder();
            sb7.append("NativeAllocatedFree:");
            sb7.append(Debug.getNativeHeapFreeSize() >> 10);
            Log.i(str, sb7.toString());
        }
        int glCreateProgram = GLES20.glCreateProgram();
        boolean z = false;
        abortUnless(glCreateProgram > 0, "Create OpenGL program failed.");
        addShaderTo(35633, VERTEX_SHADER_STRING, glCreateProgram);
        checkNoGLES2Error();
        addShaderTo(35632, FRAGMENT_SHADER_STRING, glCreateProgram);
        checkNoGLES2Error();
        GLES20.glLinkProgram(glCreateProgram);
        checkNoGLES2Error();
        int[] iArr = {0};
        iArr[0] = 0;
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        checkNoGLES2Error();
        abortUnless(iArr[0] == 1, GLES20.glGetProgramInfoLog(glCreateProgram));
        checkNoGLES2Error();
        GLES20.glUseProgram(glCreateProgram);
        checkNoGLES2Error();
        int glGetUniformLocation = GLES20.glGetUniformLocation(glCreateProgram, "y_tex");
        abortUnless(glGetUniformLocation != -1, "get y_tex failed.");
        checkNoGLES2Error();
        GLES20.glUniform1i(glGetUniformLocation, 0);
        checkNoGLES2Error();
        int glGetUniformLocation2 = GLES20.glGetUniformLocation(glCreateProgram, "u_tex");
        abortUnless(glGetUniformLocation2 != -1, "get u_tex failed.");
        checkNoGLES2Error();
        GLES20.glUniform1i(glGetUniformLocation2, 1);
        checkNoGLES2Error();
        int glGetUniformLocation3 = GLES20.glGetUniformLocation(glCreateProgram, "v_tex");
        abortUnless(glGetUniformLocation3 != -1, "get v_tex failed.");
        checkNoGLES2Error();
        GLES20.glUniform1i(glGetUniformLocation3, 2);
        checkNoGLES2Error();
        this.posLocation = GLES20.glGetAttribLocation(glCreateProgram, "in_pos");
        abortUnless(this.posLocation != -1, "Get opengl variable in_pos address failed.");
        checkNoGLES2Error();
        this.tcLocation = GLES20.glGetAttribLocation(glCreateProgram, "in_tc");
        abortUnless(this.posLocation != -1, "Get opengl variable in_tc address failed.");
        checkNoGLES2Error();
        GLES20.glEnableVertexAttribArray(this.tcLocation);
        checkNoGLES2Error();
        GLES20.glVertexAttribPointer(this.tcLocation, 2, 5126, false, 0, upTextureCoord);
        checkNoGLES2Error();
        this.widthStrideLocation = GLES20.glGetUniformLocation(glCreateProgram, "width_stride");
        abortUnless(this.widthStrideLocation != -1, "Get opengl variable width_stride address failed.");
        checkNoGLES2Error();
        GLES20.glUniform1f(this.widthStrideLocation, 256.0f);
        checkNoGLES2Error();
        this.heightStrideLocation = GLES20.glGetUniformLocation(glCreateProgram, "height_stride");
        abortUnless(this.heightStrideLocation != -1, "Get opengl variable height_stride address failed.");
        checkNoGLES2Error();
        GLES20.glUniform1f(this.heightStrideLocation, 256.0f);
        checkNoGLES2Error();
        this.offsetLocation = GLES20.glGetUniformLocation(glCreateProgram, "offset");
        abortUnless(this.offsetLocation != -1, "Get opengl variable offset_stride address failed.");
        checkNoGLES2Error();
        GLES20.glUniform1f(this.offsetLocation, 256.0f);
        checkNoGLES2Error();
        this.slopeLocation = GLES20.glGetUniformLocation(glCreateProgram, "slope");
        abortUnless(this.slopeLocation != -1, "Get opengl variable slope address failed.");
        checkNoGLES2Error();
        GLES20.glUniform1f(this.slopeLocation, 256.0f);
        checkNoGLES2Error();
        this.sharpCoffLocation = GLES20.glGetUniformLocation(glCreateProgram, "sharpCoff");
        abortUnless(this.sharpCoffLocation != -1, "Get opengl variable sharpCoffLocation address failed.");
        checkNoGLES2Error();
        GLES20.glUniform1f(this.sharpCoffLocation, 256.0f);
        checkNoGLES2Error();
        this.sharpStrengthLocation = GLES20.glGetUniformLocation(glCreateProgram, "sharpStrength");
        abortUnless(this.sharpStrengthLocation != -1, "Get opengl variable sharpStrengthLocation address failed.");
        checkNoGLES2Error();
        GLES20.glUniform1f(this.sharpStrengthLocation, 256.0f);
        checkNoGLES2Error();
        this.sourceCoffLocation = GLES20.glGetUniformLocation(glCreateProgram, "sourceCoff");
        abortUnless(this.sourceCoffLocation != -1, "Get opengl variable sourceCoffLocation address failed.");
        checkNoGLES2Error();
        GLES20.glUniform1f(this.sourceCoffLocation, 256.0f);
        checkNoGLES2Error();
        this.widthLocation = GLES20.glGetUniformLocation(glCreateProgram, "width");
        abortUnless(this.widthLocation != -1, "Get opengl variable width address failed.");
        checkNoGLES2Error();
        GLES20.glUniform1f(this.widthLocation, 256.0f);
        checkNoGLES2Error();
        this.heightLocation = GLES20.glGetUniformLocation(glCreateProgram, "height");
        if (this.heightLocation != -1) {
            z = true;
        }
        abortUnless(z, "Get opengl variable height address failed.");
        checkNoGLES2Error();
        GLES20.glUniform1f(this.heightLocation, 256.0f);
        checkNoGLES2Error();
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        checkNoGLES2Error();
        GLES20.glPixelStorei(3317, 1);
        checkNoGLES2Error();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0067, code lost:
        if (r3.framesToRender != null) goto L_0x0070;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0069, code lost:
        android.util.Log.w(TAG, "updateFrame renderFrame framesToRender is null");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0070, code lost:
        if (r4 == false) goto L_0x007a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0072, code lost:
        queueEvent(new com.xiaomi.rendermanager.videoRender.VideoStreamsView.AnonymousClass2(r3));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x007a, code lost:
        return;
     */
    public void renderFrame(I420Frame i420Frame) {
        abortUnless(FramePool.validateDimensions(i420Frame), "Frame too large!");
        if (!this.isRenderThreadRunning) {
            Log.w(TAG, "VideoStreamView: The render thread is not running, so we discard this frame.");
            return;
        }
        synchronized (this) {
            if (this.framePool == null) {
                Log.w(TAG, "VideoStreamView: frame pool is null!");
                return;
            }
            I420Frame takeFrame = this.framePool.takeFrame(i420Frame);
            if (takeFrame == null) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("VideoStreamView: Take an frame from frame pool is empty, discard this frame:");
                sb.append(i420Frame.width);
                sb.append(" X ");
                sb.append(i420Frame.height);
                Log.w(str, sb.toString());
                return;
            }
            takeFrame.copyFrom(i420Frame);
            boolean z = this.framesToRender == null;
            I420Frame i420Frame2 = this.framesToRender;
            if (i420Frame2 != null) {
                this.framePool.returnFrame(i420Frame2);
            }
            this.framesToRender = takeFrame;
        }
    }

    public void setRenderModel(RenderModel renderModel2) {
        if (renderModel2 != this.renderModel) {
            this.renderModel = renderModel2;
            this._renderModelChanged = true;
        }
    }

    public void setShiftUpImpl(float f2, float f3, float f4, float f5, float f6) {
        this._shiftUpVideoRatio = f4;
        this._shiftUpVideoRatioDelta = f5;
        this._shiftUpViewRatio = f2;
        this._shiftUpViewRatioDelta = f3;
        this._shiftUpVideoValue = f6;
    }

    public void setShiftUpInternal(float f2, float f3, float f4, float f5, float f6) {
        StringBuilder sb = new StringBuilder();
        sb.append("VideoStreamView: setShiftUp with:");
        sb.append(f2);
        sb.append(" viewRatioDelta:");
        sb.append(f3);
        sb.append(" videoRatio:");
        sb.append(f4);
        sb.append(" videoRatioDelta:");
        sb.append(f5);
        sb.append(" shiftUpValue:");
        sb.append(f6);
        sb.append(" view:");
        sb.append(this);
        Log.i(TAG, sb.toString());
        setShiftUp(this.nativeObject, f2, f3, f4, f5, f6);
    }

    public void setSize(final int i, final int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("VideoStreamView: The thread id (NATIVE thread) for setSize is:");
        sb.append(Thread.currentThread().getId());
        Log.i(TAG, sb.toString());
        queueEvent(new Runnable() {
            public void run() {
                StringBuilder sb = new StringBuilder();
                sb.append("VideoStreamView: The thread id for videoSizeChanged is:");
                sb.append(Thread.currentThread().getId());
                Log.i(VideoStreamsView.TAG, sb.toString());
                this.videoSizeChanged(i, i2);
            }
        });
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        StringBuilder sb = new StringBuilder();
        sb.append("surfaceCreated, this pointer is  ");
        sb.append(this);
        Log.i(TAG, sb.toString());
        super.surfaceCreated(surfaceHolder);
        this.isRenderThreadRunning = true;
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        StringBuilder sb = new StringBuilder();
        sb.append("surfaceDestroyed, this pointer is  ");
        sb.append(this);
        Log.i(TAG, sb.toString());
        super.surfaceDestroyed(surfaceHolder);
        this.isRenderThreadRunning = false;
        synchronized (this) {
            if (this.framesToRender != null) {
                if (this.framePool != null) {
                    this.framePool.returnFrame(this.framesToRender);
                }
                this.framesToRender = null;
            }
        }
    }

    public boolean unbindRenderWithStream() {
        StringBuilder sb = new StringBuilder();
        sb.append("VideoStreamView: unbindRenderWithStream");
        sb.append(this);
        Log.i(TAG, sb.toString());
        return unbindStream(this.nativeObject);
    }

    public void videoSizeChanged(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("VideoStreamView: The video size changed to ");
        sb.append(i);
        sb.append(" ");
        sb.append(i2);
        Log.i(TAG, sb.toString());
    }
}
