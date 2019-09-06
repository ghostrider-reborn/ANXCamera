package com.android.camera.module.impl.component;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.opengl.GLES20;
import android.util.Log;
import com.android.camera.Util;
import com.xiaomi.mediaprocess.OpenGlRender;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MiGLSurfaceViewRender {
    private static final String TAG = "MiGLSurfaceViewRender";
    private static float[] textureVertices = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private static final String vertexShaderString = "attribute vec4 vertexIn;attribute vec2 textureIn;varying vec2 textureOut;void main() {gl_Position = vertexIn;textureOut = textureIn;}";
    private static float[] vertexVertices = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
    private static final String yuvFragmentShaderString = "precision mediump float;uniform sampler2D tex_y;uniform sampler2D tex_u;uniform sampler2D tex_v;varying vec2 textureOut;void main() {vec4 c = vec4((texture2D(tex_y, textureOut).r - 16./255.) * 1.164);vec4 U = vec4(texture2D(tex_u, textureOut).r - 128./255.);vec4 V = vec4(texture2D(tex_v, textureOut).r - 128./255.);c += V * vec4(1.596, -0.813, 0, 0);c += U * vec4(0, -0.392, 2.017, 0);c.a = 1.0;gl_FragColor = c;}";
    public int ATTRIB_TEXTURE = 0;
    public int ATTRIB_VERTEX = 0;
    private OpenGlRender mOpenGlRender;
    private int mProgramId;
    public int m_textureUniformU;
    public int m_textureUniformV;
    public int m_textureUniformY;
    public int m_textureid_u;
    public int m_textureid_v;
    public int m_textureid_y;
    ByteBuffer textureVertices_buffer = null;
    ByteBuffer vertexVertices_buffer = null;

    public MiGLSurfaceViewRender(OpenGlRender openGlRender) {
        this.mOpenGlRender = openGlRender;
    }

    private void InitShaders() {
        this.vertexVertices_buffer = ByteBuffer.allocateDirect(vertexVertices.length * 4);
        this.vertexVertices_buffer.order(ByteOrder.nativeOrder());
        Matrix matrix = new Matrix();
        matrix.postRotate(-90.0f);
        float[] fArr = new float[8];
        matrix.mapPoints(fArr, vertexVertices);
        this.vertexVertices_buffer.asFloatBuffer().put(fArr);
        this.vertexVertices_buffer.position(0);
        this.textureVertices_buffer = ByteBuffer.allocateDirect(textureVertices.length * 4);
        this.textureVertices_buffer.order(ByteOrder.nativeOrder());
        this.textureVertices_buffer.asFloatBuffer().put(textureVertices);
        this.textureVertices_buffer.position(0);
        this.mProgramId = createProgram(vertexShaderString, yuvFragmentShaderString);
        this.ATTRIB_VERTEX = GLES20.glGetAttribLocation(this.mProgramId, "vertexIn");
        int i = this.ATTRIB_VERTEX;
        String str = "glGetAttribLocation error ";
        String str2 = TAG;
        if (i == -1) {
            Log.d(str2, str);
        }
        this.ATTRIB_TEXTURE = GLES20.glGetAttribLocation(this.mProgramId, "textureIn");
        if (this.ATTRIB_TEXTURE == -1) {
            Log.d(str2, str);
        }
        this.m_textureUniformY = GLES20.glGetUniformLocation(this.mProgramId, "tex_y");
        this.m_textureUniformU = GLES20.glGetUniformLocation(this.mProgramId, "tex_u");
        this.m_textureUniformV = GLES20.glGetUniformLocation(this.mProgramId, "tex_v");
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        this.m_textureid_y = iArr[0];
        int[] iArr2 = new int[1];
        GLES20.glGenTextures(1, iArr2, 0);
        this.m_textureid_u = iArr2[0];
        int[] iArr3 = new int[1];
        GLES20.glGenTextures(1, iArr3, 0);
        this.m_textureid_v = iArr3[0];
    }

    private int createProgram(String str, String str2) {
        int loadShader = loadShader(35633, str);
        int loadShader2 = loadShader(35632, str2);
        StringBuilder sb = new StringBuilder();
        sb.append("vertex shader: ");
        sb.append(str);
        String str3 = " -- ";
        sb.append(str3);
        sb.append(loadShader);
        String sb2 = sb.toString();
        String str4 = TAG;
        Log.d(str4, sb2);
        StringBuilder sb3 = new StringBuilder();
        sb3.append("fragment shader: ");
        sb3.append(str2);
        sb3.append(str3);
        sb3.append(loadShader2);
        Log.d(str4, sb3.toString());
        int glCreateProgram = GLES20.glCreateProgram();
        StringBuilder sb4 = new StringBuilder();
        sb4.append("program: ");
        sb4.append(glCreateProgram);
        Log.d(str4, sb4.toString());
        if (glCreateProgram != 0) {
            GLES20.glAttachShader(glCreateProgram, loadShader);
            GLES20.glAttachShader(glCreateProgram, loadShader2);
            GLES20.glLinkProgram(glCreateProgram);
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
            if (iArr[0] != 1) {
                GLES20.glDeleteProgram(glCreateProgram);
                glCreateProgram = 0;
            }
        }
        StringBuilder sb5 = new StringBuilder();
        sb5.append(" end if program: ");
        sb5.append(glCreateProgram);
        Log.d(str4, sb5.toString());
        return glCreateProgram;
    }

    private int loadShader(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        StringBuilder sb = new StringBuilder();
        sb.append("shader: ");
        sb.append(glCreateShader);
        String sb2 = sb.toString();
        String str2 = TAG;
        Log.d(str2, sb2);
        if (glCreateShader != 0) {
            GLES20.glShaderSource(glCreateShader, str);
            GLES20.glCompileShader(glCreateShader);
            int[] iArr = new int[1];
            GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
            if (iArr[0] == 0) {
                GLES20.glDeleteShader(glCreateShader);
                glCreateShader = 0;
            }
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("end shader: ");
        sb3.append(glCreateShader);
        Log.d(str2, sb3.toString());
        return glCreateShader;
    }

    public void bind(Rect rect, int i, int i2) {
        GLES20.glUseProgram(this.mProgramId);
        int i3 = Util.sWindowHeight;
        int i4 = rect.bottom;
        GLES20.glViewport(0, i3 - i4, rect.right, i4 - rect.top);
        GLES20.glBindTexture(3553, this.m_textureid_y);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glBindTexture(3553, this.m_textureid_u);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glBindTexture(3553, this.m_textureid_v);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
    }

    public void init() {
        String str = TAG;
        Log.d(str, "init :");
        InitShaders();
        StringBuilder sb = new StringBuilder();
        sb.append("onSurfaceCreated: y ");
        sb.append(this.m_textureid_y);
        sb.append(", u ");
        sb.append(this.m_textureid_u);
        sb.append(", v ");
        sb.append(this.m_textureid_v);
        sb.append(", Y ");
        sb.append(this.m_textureUniformY);
        sb.append(", U ");
        sb.append(this.m_textureUniformU);
        sb.append(", V ");
        sb.append(this.m_textureUniformV);
        sb.append(", ATTRIB_VERTEX: ");
        sb.append(this.ATTRIB_VERTEX);
        sb.append(", ATTRIB_TEXTURE ");
        sb.append(this.ATTRIB_TEXTURE);
        sb.append(", vertexVertices: ");
        sb.append(this.vertexVertices_buffer.remaining());
        sb.append(", textureVertices ");
        sb.append(this.textureVertices_buffer.remaining());
        Log.d(str, sb.toString());
        byte[] bArr = new byte[this.vertexVertices_buffer.remaining()];
        this.vertexVertices_buffer.get(bArr);
        byte[] bArr2 = new byte[this.textureVertices_buffer.remaining()];
        this.textureVertices_buffer.get(bArr2);
        this.mOpenGlRender.SetOpengGlRenderParams(this.m_textureid_y, this.m_textureid_u, this.m_textureid_v, this.m_textureUniformY, this.m_textureUniformU, this.m_textureUniformV, this.ATTRIB_VERTEX, this.ATTRIB_TEXTURE, bArr, bArr2);
    }
}
