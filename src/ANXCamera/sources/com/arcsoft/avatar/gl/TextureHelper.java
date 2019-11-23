package com.arcsoft.avatar.gl;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import java.nio.IntBuffer;

public class TextureHelper {

    /* renamed from: a  reason: collision with root package name */
    private int[] f25a = new int[1];

    public void deleteTexture() {
        GLES20.glDeleteTextures(this.f25a.length, IntBuffer.wrap(this.f25a));
    }

    public int getTextureId() {
        if (this.f25a[0] != 0) {
            return this.f25a[0];
        }
        throw new RuntimeException("Error generating texture name.");
    }

    public void initTexture() {
        GLES20.glGenTextures(this.f25a.length, this.f25a, 0);
        if (this.f25a[0] != 0) {
            GLES20.glBindTexture(3553, this.f25a[0]);
            GLES20.glTexParameteri(3553, 10241, 9728);
            GLES20.glTexParameteri(3553, 10240, 9728);
            GLES20.glBindTexture(3553, 0);
            return;
        }
        throw new RuntimeException("Error generating texture name.");
    }

    public void loadTexture(Bitmap bitmap) {
        GLES20.glBindTexture(3553, this.f25a[0]);
        GLUtils.texImage2D(3553, 0, bitmap, 0);
        GLES20.glBindTexture(3553, 0);
    }
}
