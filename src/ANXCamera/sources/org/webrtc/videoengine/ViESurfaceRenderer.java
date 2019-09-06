package org.webrtc.videoengine;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Process;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ViESurfaceRenderer implements Callback {
    private static final String TAG = "WEBRTC";
    private Bitmap bitmap = null;
    private ByteBuffer byteBuffer = null;
    private float dstBottomScale = 1.0f;
    private float dstLeftScale = 0.0f;
    private Rect dstRect = new Rect();
    private float dstRightScale = 1.0f;
    private float dstTopScale = 0.0f;
    private Rect srcRect = new Rect();
    private SurfaceHolder surfaceHolder;

    public ViESurfaceRenderer(SurfaceView surfaceView) {
        this.surfaceHolder = surfaceView.getHolder();
        SurfaceHolder surfaceHolder2 = this.surfaceHolder;
        if (surfaceHolder2 != null) {
            surfaceHolder2.addCallback(this);
        }
    }

    private void changeDestRect(int i, int i2) {
        Rect rect = this.dstRect;
        rect.right = (int) (((float) rect.left) + (this.dstRightScale * ((float) i)));
        rect.bottom = (int) (((float) rect.top) + (this.dstBottomScale * ((float) i2)));
    }

    private void saveBitmapToJPEG(int i, int i2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.bitmap.compress(CompressFormat.JPEG, 100, byteArrayOutputStream);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(String.format("/sdcard/render_%d.jpg", new Object[]{Long.valueOf(System.currentTimeMillis())}));
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException | IOException unused) {
        }
    }

    public Bitmap CreateBitmap(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("CreateByteBitmap ");
        sb.append(i);
        sb.append(":");
        sb.append(i2);
        Log.d(TAG, sb.toString());
        if (this.bitmap == null) {
            try {
                Process.setThreadPriority(-4);
            } catch (Exception unused) {
            }
        }
        this.bitmap = Bitmap.createBitmap(i, i2, Config.RGB_565);
        Rect rect = this.srcRect;
        rect.left = 0;
        rect.top = 0;
        rect.bottom = i2;
        rect.right = i;
        return this.bitmap;
    }

    public ByteBuffer CreateByteBuffer(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("CreateByteBuffer ");
        sb.append(i);
        sb.append(":");
        sb.append(i2);
        Log.d(TAG, sb.toString());
        if (this.bitmap == null) {
            this.bitmap = CreateBitmap(i, i2);
            this.byteBuffer = ByteBuffer.allocateDirect(i * i2 * 2);
        }
        return this.byteBuffer;
    }

    public void DrawBitmap() {
        if (this.bitmap != null) {
            Canvas lockCanvas = this.surfaceHolder.lockCanvas();
            if (lockCanvas != null) {
                lockCanvas.drawBitmap(this.bitmap, this.srcRect, this.dstRect, null);
                this.surfaceHolder.unlockCanvasAndPost(lockCanvas);
            }
        }
    }

    public void DrawByteBuffer() {
        ByteBuffer byteBuffer2 = this.byteBuffer;
        if (byteBuffer2 != null) {
            byteBuffer2.rewind();
            this.bitmap.copyPixelsFromBuffer(this.byteBuffer);
            DrawBitmap();
        }
    }

    public void SetCoordinates(float f2, float f3, float f4, float f5) {
        StringBuilder sb = new StringBuilder();
        sb.append("SetCoordinates ");
        sb.append(f2);
        String str = ",";
        sb.append(str);
        sb.append(f3);
        sb.append(":");
        sb.append(f4);
        sb.append(str);
        sb.append(f5);
        Log.d(TAG, sb.toString());
        this.dstLeftScale = f2;
        this.dstTopScale = f3;
        this.dstRightScale = f4;
        this.dstBottomScale = f5;
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder2, int i, int i2, int i3) {
        String str = TAG;
        Log.d(str, "ViESurfaceRender::surfaceChanged");
        changeDestRect(i2, i3);
        StringBuilder sb = new StringBuilder();
        sb.append("ViESurfaceRender::surfaceChanged in_width:");
        sb.append(i2);
        sb.append(" in_height:");
        sb.append(i3);
        sb.append(" srcRect.left:");
        sb.append(this.srcRect.left);
        sb.append(" srcRect.top:");
        sb.append(this.srcRect.top);
        sb.append(" srcRect.right:");
        sb.append(this.srcRect.right);
        sb.append(" srcRect.bottom:");
        sb.append(this.srcRect.bottom);
        sb.append(" dstRect.left:");
        sb.append(this.dstRect.left);
        sb.append(" dstRect.top:");
        sb.append(this.dstRect.top);
        sb.append(" dstRect.right:");
        sb.append(this.dstRect.right);
        sb.append(" dstRect.bottom:");
        sb.append(this.dstRect.bottom);
        Log.d(str, sb.toString());
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder2) {
        Canvas lockCanvas = this.surfaceHolder.lockCanvas();
        if (lockCanvas != null) {
            Rect surfaceFrame = this.surfaceHolder.getSurfaceFrame();
            if (surfaceFrame != null) {
                changeDestRect(surfaceFrame.right - surfaceFrame.left, surfaceFrame.bottom - surfaceFrame.top);
                StringBuilder sb = new StringBuilder();
                sb.append("ViESurfaceRender::surfaceCreated dst.left:");
                sb.append(surfaceFrame.left);
                sb.append(" dst.top:");
                sb.append(surfaceFrame.top);
                sb.append(" dst.right:");
                sb.append(surfaceFrame.right);
                sb.append(" dst.bottom:");
                sb.append(surfaceFrame.bottom);
                sb.append(" srcRect.left:");
                sb.append(this.srcRect.left);
                sb.append(" srcRect.top:");
                sb.append(this.srcRect.top);
                sb.append(" srcRect.right:");
                sb.append(this.srcRect.right);
                sb.append(" srcRect.bottom:");
                sb.append(this.srcRect.bottom);
                sb.append(" dstRect.left:");
                sb.append(this.dstRect.left);
                sb.append(" dstRect.top:");
                sb.append(this.dstRect.top);
                sb.append(" dstRect.right:");
                sb.append(this.dstRect.right);
                sb.append(" dstRect.bottom:");
                sb.append(this.dstRect.bottom);
                Log.d(TAG, sb.toString());
            }
            this.surfaceHolder.unlockCanvasAndPost(lockCanvas);
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder2) {
        Log.d(TAG, "ViESurfaceRenderer::surfaceDestroyed");
        this.bitmap = null;
        this.byteBuffer = null;
    }
}
