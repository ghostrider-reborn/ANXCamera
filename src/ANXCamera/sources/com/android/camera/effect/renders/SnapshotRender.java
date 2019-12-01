package com.android.camera.effect.renders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.Image;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.provider.MiuiSettings;
import android.util.Size;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningTiltValue;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.FrameBuffer;
import com.android.camera.effect.MiYuvImage;
import com.android.camera.effect.ShaderNativeUtil;
import com.android.camera.effect.SnapshotCanvas;
import com.android.camera.effect.draw_mode.DrawBasicTexAttribute;
import com.android.camera.effect.draw_mode.DrawYuvAttribute;
import com.android.camera.effect.framework.gles.EglCore;
import com.android.camera.effect.framework.gles.PbufferSurface;
import com.android.camera.effect.framework.graphics.Block;
import com.android.camera.effect.framework.graphics.Splitter;
import com.android.camera.effect.framework.image.MemYuvImage;
import com.android.camera.effect.framework.utils.CounterUtil;
import com.android.camera.log.Log;
import com.android.camera.watermark.WaterMarkBitmap;
import com.android.camera.watermark.WaterMarkData;
import com.arcsoft.supernight.SuperNightProcess;
import com.mi.config.b;
import com.ss.android.ttve.common.TEDefine;
import com.xiaomi.camera.base.ImageUtil;
import com.xiaomi.camera.core.FilterProcessor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Locale;

public class SnapshotRender {
    private static final boolean DUMP_TEXTURE = false;
    private static final int EGL_CONTEXT_CLIENT_VERSION = 12440;
    private static final int QUEUE_LIMIT = 7;
    /* access modifiers changed from: private */
    public static final String TAG = SnapshotRender.class.getSimpleName();
    /* access modifiers changed from: private */
    public int mAdjHeight;
    /* access modifiers changed from: private */
    public int mAdjWidth;
    /* access modifiers changed from: private */
    public int mBlockHeight;
    /* access modifiers changed from: private */
    public int mBlockWidth;
    /* access modifiers changed from: private */
    public String mCurrentCustomWaterMarkText;
    /* access modifiers changed from: private */
    public DeviceWatermarkParam mDeviceWaterMarkParam;
    /* access modifiers changed from: private */
    public Bitmap mDualCameraWaterMarkBitmap;
    /* access modifiers changed from: private */
    public EglCore mEglCore;
    private EGLHandler mEglHandler;
    private HandlerThread mEglThread;
    /* access modifiers changed from: private */
    public CounterUtil mFrameCounter;
    /* access modifiers changed from: private */
    public Bitmap mFrontCameraWaterMarkBitmap;
    private volatile int mImageQueueSize;
    /* access modifiers changed from: private */
    public final Object mLock;
    /* access modifiers changed from: private */
    public MemYuvImage mMemImage;
    /* access modifiers changed from: private */
    public int mQuality;
    private boolean mRelease;
    /* access modifiers changed from: private */
    public boolean mReleasePending;
    /* access modifiers changed from: private */
    public PbufferSurface mRenderSurface;
    /* access modifiers changed from: private */
    public Splitter mSplitter;
    private int mTextureId;
    /* access modifiers changed from: private */
    public CounterUtil mTotalCounter;
    /* access modifiers changed from: private */
    public Bitmap mUltraPixelCameraWaterMarkBitmap;

    private class EGLHandler extends Handler {
        public static final int MSG_DRAW_MAIN_ASYNC = 1;
        public static final int MSG_DRAW_MAIN_SYNC = 2;
        public static final int MSG_INIT_EGL_SYNC = 0;
        public static final int MSG_PREPARE_EFFECT_RENDER = 6;
        public static final int MSG_RELEASE = 5;
        private FrameBuffer mFrameBuffer;
        private SnapshotCanvas mGLCanvas;
        private FrameBuffer mWatermarkFrameBuffer;

        public EGLHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARNING: type inference failed for: r7v3 */
        /* JADX WARNING: type inference failed for: r7v6 */
        /* JADX WARNING: type inference failed for: r7v7 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Removed duplicated region for block: B:43:0x020b  */
        /* JADX WARNING: Removed duplicated region for block: B:68:0x036c  */
        /* JADX WARNING: Removed duplicated region for block: B:71:0x0381  */
        /* JADX WARNING: Removed duplicated region for block: B:73:0x038a  */
        /* JADX WARNING: Removed duplicated region for block: B:76:0x0411  */
        /* JADX WARNING: Removed duplicated region for block: B:77:0x0445  */
        /* JADX WARNING: Removed duplicated region for block: B:80:0x047d  */
        /* JADX WARNING: Unknown variable types count: 1 */
        private byte[] applyEffect(DrawYuvAttribute drawYuvAttribute) {
            PipeRender pipeRender;
            boolean z;
            int i;
            int i2;
            byte[] bArr;
            int[] iArr;
            boolean z2;
            DrawYuvAttribute drawYuvAttribute2;
            int i3;
            int i4;
            int i5;
            int i6;
            int i7;
            int i8;
            int i9;
            byte[] bArr2;
            int[] iArr2;
            byte[] bArr3;
            DrawYuvAttribute drawYuvAttribute3;
            int i10;
            int i11;
            ? r7;
            int i12;
            int i13;
            DrawYuvAttribute drawYuvAttribute4 = drawYuvAttribute;
            PipeRender effectRender = getEffectRender(drawYuvAttribute);
            if (effectRender == null) {
                Log.w(SnapshotRender.TAG, "init render failed");
                return null;
            }
            int width = drawYuvAttribute4.mPictureSize.getWidth();
            int height = drawYuvAttribute4.mPictureSize.getHeight();
            long currentTimeMillis = System.currentTimeMillis();
            Image.Plane plane = drawYuvAttribute4.mImage.getPlanes()[0];
            Image.Plane plane2 = drawYuvAttribute4.mImage.getPlanes()[1];
            int rowStride = plane.getRowStride();
            int rowStride2 = plane2.getRowStride();
            String access$400 = SnapshotRender.TAG;
            Log.d(access$400, "plane0 stride =  " + plane.getRowStride() + ", plane1 stride = " + plane2.getRowStride());
            boolean isOutputSquare = drawYuvAttribute.isOutputSquare();
            if (drawYuvAttribute4.mEffectIndex != FilterInfo.FILTER_ID_NONE || CameraSettings.isAgeGenderAndMagicMirrorWaterOpen() || isOutputSquare || CameraSettings.isTiltShiftOn() || (!drawYuvAttribute4.mApplyWaterMark && drawYuvAttribute4.mTimeWatermark == null)) {
                pipeRender = effectRender;
                z = isOutputSquare;
                i2 = width;
                i = height;
                drawYuvAttribute2 = drawYuvAttribute4;
                z2 = false;
                iArr = null;
                bArr = null;
            } else {
                iArr = Util.getWatermarkRange(drawYuvAttribute4.mPictureSize.getWidth(), drawYuvAttribute4.mPictureSize.getHeight(), (drawYuvAttribute4.mJpegRotation + 270) % MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT, drawYuvAttribute4.mApplyWaterMark, drawYuvAttribute4.mTimeWatermark != null, SnapshotRender.this.mDeviceWaterMarkParam.isMiMovieWatermarkEnable() ? 0.31f : 0.11f);
                bArr = ImageUtil.getYuvData(drawYuvAttribute4.mImage);
                MiYuvImage subYuvImage = Util.getSubYuvImage(bArr, width, height, rowStride, rowStride2, iArr);
                String access$4002 = SnapshotRender.TAG;
                Log.d(access$4002, "get sub range data spend total tome =" + (System.currentTimeMillis() - currentTimeMillis));
                i2 = width;
                i = height;
                z = isOutputSquare;
                pipeRender = effectRender;
                DrawYuvAttribute drawYuvAttribute5 = new DrawYuvAttribute(drawYuvAttribute4.mImage, drawYuvAttribute4.mPreviewSize, new Size(iArr[2], iArr[3]), drawYuvAttribute4.mEffectIndex, drawYuvAttribute4.mOrientation, drawYuvAttribute4.mJpegRotation, drawYuvAttribute4.mShootRotation, drawYuvAttribute4.mDate, drawYuvAttribute4.mMirror, drawYuvAttribute4.mApplyWaterMark, drawYuvAttribute4.mTiltShiftMode, drawYuvAttribute4.mTimeWatermark, drawYuvAttribute4.mAttribute, drawYuvAttribute4.mWaterInfos);
                drawYuvAttribute5.mYuvImage = subYuvImage;
                drawYuvAttribute2 = drawYuvAttribute5;
                z2 = true;
            }
            PipeRender pipeRender2 = pipeRender;
            updateRenderParameters(pipeRender2, drawYuvAttribute2, false);
            if (pipeRender2 instanceof PipeRender) {
                pipeRender2.setFrameBufferSize(drawYuvAttribute2.mPictureSize.getWidth(), drawYuvAttribute2.mPictureSize.getHeight());
            }
            int i14 = z2 ? iArr[2] : i2;
            int i15 = z2 ? iArr[3] : i;
            checkFrameBuffer(i14, i15);
            this.mGLCanvas.beginBindFrameBuffer(this.mFrameBuffer);
            long currentTimeMillis2 = System.currentTimeMillis();
            GLES20.glFlush();
            pipeRender2.setParentFrameBufferId(this.mFrameBuffer.getId());
            pipeRender2.draw(drawYuvAttribute2);
            String access$4003 = SnapshotRender.TAG;
            Log.d(access$4003, "drawTime=" + (System.currentTimeMillis() - currentTimeMillis2));
            pipeRender2.deleteBuffer();
            int i16 = i2;
            int i17 = i;
            drawYuvAttribute2.mOriginalSize = new Size(i16, i17);
            if (!z) {
                i5 = i17;
                i6 = i16;
                i4 = 0;
            } else if (i16 > i17) {
                i4 = (i16 - i17) / 2;
                i6 = i17;
                i5 = i6;
            } else {
                i3 = (i17 - i16) / 2;
                i6 = i16;
                i5 = i6;
                i4 = 0;
                if (!drawYuvAttribute2.mApplyWaterMark) {
                    if (z2) {
                        r7 = 1;
                        i11 = -iArr[0];
                        i10 = -iArr[1];
                    } else {
                        r7 = 1;
                        i11 = 0;
                        i10 = 0;
                    }
                    long currentTimeMillis3 = System.currentTimeMillis();
                    if (!z2) {
                        iArr = Util.getWatermarkRange(i6, i5, (drawYuvAttribute2.mJpegRotation + 270) % MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT, drawYuvAttribute2.mApplyWaterMark, drawYuvAttribute2.mTimeWatermark != null ? r7 : false, SnapshotRender.this.mDeviceWaterMarkParam.isMiMovieWatermarkEnable() ? 0.31f : 0.11f);
                        int i18 = iArr[0];
                        i12 = iArr[r7];
                        i13 = i18;
                    } else {
                        i13 = 0;
                        i12 = 0;
                    }
                    int i19 = drawYuvAttribute2.mJpegQuality;
                    int i20 = (i19 <= 0 || i19 > 97) ? 97 : i19;
                    i8 = i17;
                    i7 = i16;
                    i9 = i15;
                    drawAgeGenderAndMagicMirrorWater(drawYuvAttribute2.mWaterInfos, i11, i10, i16, i17, drawYuvAttribute2.mPreviewSize.getWidth(), drawYuvAttribute2.mPreviewSize.getHeight(), drawYuvAttribute2.mJpegRotation, false, false);
                    bArr2 = ShaderNativeUtil.getPicture(i13 + i4, i12 + i3, iArr[2], iArr[3], i20);
                    String access$4004 = SnapshotRender.TAG;
                    Log.d(access$4004, "for remove watermark spend more time = " + (System.currentTimeMillis() - currentTimeMillis3));
                    drawWaterMark(i11 + i4, i10 + i3, i6, i5, drawYuvAttribute2.mJpegRotation, drawYuvAttribute2.mTimeWatermark, false);
                    String access$4005 = SnapshotRender.TAG;
                    Log.d(access$4005, "watermarkTime=" + (System.currentTimeMillis() - currentTimeMillis3));
                    this.mGLCanvas.endBindFrameBuffer();
                    Size size = z2 ? new Size(i14, i9) : drawYuvAttribute2.mOriginalSize;
                    checkWatermarkFrameBuffer(size);
                    this.mGLCanvas.beginBindFrameBuffer(this.mWatermarkFrameBuffer);
                    long currentTimeMillis4 = System.currentTimeMillis();
                    RgbToYuvRender rgbToYuvRender = (RgbToYuvRender) fetchRender(FilterInfo.FILTER_ID_RGB2YUV);
                    updateRenderParameters(rgbToYuvRender, drawYuvAttribute2, false);
                    rgbToYuvRender.setParentFrameBufferId(this.mFrameBuffer.getId());
                    rgbToYuvRender.drawTexture(this.mFrameBuffer.getTexture().getId(), 0.0f, 0.0f, (float) size.getWidth(), (float) size.getHeight(), true);
                    String access$4006 = SnapshotRender.TAG;
                    Log.d(access$4006, "rgb2YuvTime=" + (System.currentTimeMillis() - currentTimeMillis4));
                    iArr2 = iArr;
                } else {
                    i8 = i17;
                    i7 = i16;
                    i9 = i15;
                    iArr2 = iArr;
                    bArr2 = null;
                }
                GLES20.glPixelStorei(SuperNightProcess.ASVL_PAF_RAW12_RGGB_12B, 1);
                long currentTimeMillis5 = System.currentTimeMillis();
                if (!z2) {
                    i14 = drawYuvAttribute2.mOriginalSize.getWidth();
                }
                if (!z2) {
                    i9 = drawYuvAttribute2.mOriginalSize.getHeight();
                }
                int ceil = (int) Math.ceil(((double) i14) / 2.0d);
                int ceil2 = (int) Math.ceil((((double) i9) * 3.0d) / 4.0d);
                ByteBuffer allocate = ByteBuffer.allocate(ceil * ceil2 * 4);
                GLES20.glReadPixels(0, 0, ceil, ceil2, 6408, 5121, allocate);
                allocate.rewind();
                Log.d(SnapshotRender.TAG, String.format(Locale.ENGLISH, "readSize=%dx%d imageSize=%dx%d", new Object[]{Integer.valueOf(ceil), Integer.valueOf(ceil2), Integer.valueOf(i14), Integer.valueOf(i9)}));
                String access$4007 = SnapshotRender.TAG;
                Log.d(access$4007, "readTime=" + (System.currentTimeMillis() - currentTimeMillis5));
                byte[] array = allocate.array();
                if (!z2) {
                    long currentTimeMillis6 = System.currentTimeMillis();
                    Util.coverSubYuvImage(bArr, i6, i5, rowStride, rowStride2, allocate.array(), iArr2);
                    String access$4008 = SnapshotRender.TAG;
                    Log.d(access$4008, "cover sub range data spend total time =" + (System.currentTimeMillis() - currentTimeMillis6));
                    bArr3 = bArr;
                } else {
                    bArr3 = array;
                }
                long currentTimeMillis7 = System.currentTimeMillis();
                ImageUtil.updateYuvImage(drawYuvAttribute2.mImage, bArr3, z2);
                String access$4009 = SnapshotRender.TAG;
                Log.d(access$4009, "updateImageTime=" + (System.currentTimeMillis() - currentTimeMillis7));
                this.mGLCanvas.endBindFrameBuffer();
                this.mGLCanvas.recycledResources();
                drawYuvAttribute3 = drawYuvAttribute;
                if (drawYuvAttribute3.mApplyWaterMark) {
                    int[] reverseCalculateRange = reverseCalculateRange(i7, i8, drawYuvAttribute3.mOutputSize.getWidth(), drawYuvAttribute3.mOutputSize.getHeight(), iArr2);
                    drawYuvAttribute3.mDataOfTheRegionUnderWatermarks = bArr2;
                    drawYuvAttribute3.mCoordinatesOfTheRegionUnderWatermarks = reverseCalculateRange;
                }
                return bArr3;
            }
            i3 = 0;
            if (!drawYuvAttribute2.mApplyWaterMark) {
            }
            GLES20.glPixelStorei(SuperNightProcess.ASVL_PAF_RAW12_RGGB_12B, 1);
            long currentTimeMillis52 = System.currentTimeMillis();
            if (!z2) {
            }
            if (!z2) {
            }
            int ceil3 = (int) Math.ceil(((double) i14) / 2.0d);
            int ceil22 = (int) Math.ceil((((double) i9) * 3.0d) / 4.0d);
            ByteBuffer allocate2 = ByteBuffer.allocate(ceil3 * ceil22 * 4);
            GLES20.glReadPixels(0, 0, ceil3, ceil22, 6408, 5121, allocate2);
            allocate2.rewind();
            Log.d(SnapshotRender.TAG, String.format(Locale.ENGLISH, "readSize=%dx%d imageSize=%dx%d", new Object[]{Integer.valueOf(ceil3), Integer.valueOf(ceil22), Integer.valueOf(i14), Integer.valueOf(i9)}));
            String access$40072 = SnapshotRender.TAG;
            Log.d(access$40072, "readTime=" + (System.currentTimeMillis() - currentTimeMillis52));
            byte[] array2 = allocate2.array();
            if (!z2) {
            }
            long currentTimeMillis72 = System.currentTimeMillis();
            ImageUtil.updateYuvImage(drawYuvAttribute2.mImage, bArr3, z2);
            String access$40092 = SnapshotRender.TAG;
            Log.d(access$40092, "updateImageTime=" + (System.currentTimeMillis() - currentTimeMillis72));
            this.mGLCanvas.endBindFrameBuffer();
            this.mGLCanvas.recycledResources();
            drawYuvAttribute3 = drawYuvAttribute;
            if (drawYuvAttribute3.mApplyWaterMark) {
            }
            return bArr3;
        }

        private byte[] blockSplitApplyEffect(DrawYuvAttribute drawYuvAttribute) {
            int i;
            int i2;
            int i3;
            int i4;
            int i5;
            int i6;
            int[] iArr;
            RectF rectF;
            int i7;
            int i8;
            int[] iArr2;
            int i9;
            int i10;
            int i11;
            int i12;
            List<Block> list;
            RectF rectF2;
            RectF rectF3;
            int i13;
            Block block;
            RectF rectF4;
            List<Block> list2;
            int i14;
            int i15;
            int i16;
            RectF rectF5;
            int i17;
            int i18;
            int i19;
            int i20;
            int i21;
            int i22;
            int i23;
            int i24;
            int i25;
            int i26;
            DrawYuvAttribute drawYuvAttribute2 = drawYuvAttribute;
            GLES20.glGetIntegerv(3379, IntBuffer.allocate(2));
            SnapshotRender.this.mTotalCounter.reset("TOTAL");
            PipeRender effectRender = getEffectRender(drawYuvAttribute);
            if (effectRender == null) {
                Log.w(SnapshotRender.TAG, "init render failed");
                return null;
            }
            int width = drawYuvAttribute2.mPictureSize.getWidth();
            int height = drawYuvAttribute2.mPictureSize.getHeight();
            Image.Plane plane = drawYuvAttribute2.mImage.getPlanes()[0];
            Image.Plane plane2 = drawYuvAttribute2.mImage.getPlanes()[1];
            int rowStride = plane.getRowStride();
            int rowStride2 = plane2.getRowStride();
            SnapshotRender.this.mMemImage.mWidth = width;
            SnapshotRender.this.mMemImage.mHeight = height;
            SnapshotRender.this.mMemImage.parseImage(drawYuvAttribute2.mImage);
            Log.d(SnapshotRender.TAG, "plane0 stride =  " + plane.getRowStride() + ", plane1 stride = " + plane2.getRowStride());
            updateRenderParameters(effectRender, drawYuvAttribute2, true);
            int i27 = rowStride2;
            int i28 = rowStride;
            List<Block> split = SnapshotRender.this.mSplitter.split(width, height, SnapshotRender.this.mBlockWidth, SnapshotRender.this.mBlockHeight, SnapshotRender.this.mAdjWidth, SnapshotRender.this.mAdjHeight);
            boolean isOutputSquare = drawYuvAttribute.isOutputSquare();
            if (isOutputSquare) {
                if (width > height) {
                    i2 = (width - height) / 2;
                    i = 0;
                    i4 = height;
                } else {
                    i = (height - width) / 2;
                    i2 = 0;
                    i4 = width;
                }
                i3 = i4;
            } else {
                i2 = 0;
                i = 0;
                i4 = height;
                i3 = width;
            }
            if (drawYuvAttribute2.mApplyWaterMark) {
                int[] watermarkRange = Util.getWatermarkRange(i3, i4, (drawYuvAttribute2.mJpegRotation + 270) % MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT, drawYuvAttribute2.mApplyWaterMark, drawYuvAttribute2.mTimeWatermark != null, SnapshotRender.this.mDeviceWaterMarkParam.isMiMovieWatermarkEnable() ? 0.31f : 0.11f);
                i6 = i28;
                RectF rectF6 = new RectF((float) (watermarkRange[0] + i2), (float) (watermarkRange[1] + i), (float) (watermarkRange[0] + watermarkRange[2] + i2), (float) (watermarkRange[1] + watermarkRange[3] + i));
                i5 = i4;
                ShaderNativeUtil.genWaterMarkRange(watermarkRange[0] + i2, watermarkRange[1] + i, watermarkRange[2], watermarkRange[3], 4);
                iArr = watermarkRange;
                rectF = rectF6;
            } else {
                i6 = i28;
                i5 = i4;
                rectF = null;
                iArr = null;
            }
            RectF rectF7 = new RectF();
            boolean z = drawYuvAttribute2.mEffectIndex == FilterInfo.FILTER_ID_NONE && !CameraSettings.isAgeGenderAndMagicMirrorWaterOpen() && !isOutputSquare && !CameraSettings.isTiltShiftOn() && (drawYuvAttribute2.mApplyWaterMark || drawYuvAttribute2.mTimeWatermark != null);
            int i29 = 0;
            while (i29 < split.size()) {
                SnapshotRender.this.mFrameCounter.reset(String.format("[loop%d/%d]begin", new Object[]{Integer.valueOf(i29), Integer.valueOf(split.size())}));
                Block block2 = split.get(i29);
                int i30 = block2.mWidth;
                int i31 = block2.mHeight;
                int[] offset = block2.getOffset(width, height);
                rectF7.left = (float) offset[0];
                Block block3 = block2;
                rectF7.top = (float) offset[1];
                rectF7.right = (float) (offset[0] + i30);
                rectF7.bottom = (float) (offset[1] + i31);
                if (z) {
                    i20 = i31;
                    iArr2 = iArr;
                    i19 = i6;
                    i18 = height;
                    i22 = i30;
                    i17 = width;
                    rectF5 = rectF7;
                    i15 = i29;
                    i16 = i5;
                    i14 = i3;
                    list2 = split;
                    rectF4 = rectF;
                    block = block3;
                    i21 = 0;
                    if (!rectangle_collision(rectF7.left, rectF7.top, rectF7.width(), rectF7.height(), rectF.left, rectF.top, rectF.width(), rectF.height())) {
                        i8 = i19;
                        i7 = i27;
                        i12 = i18;
                        i11 = i17;
                        rectF3 = rectF5;
                        i10 = i16;
                        i13 = i15;
                        i9 = i14;
                        list = list2;
                        rectF2 = rectF4;
                        drawYuvAttribute2 = drawYuvAttribute;
                        i29 = i13 + 1;
                        rectF7 = rectF3;
                        rectF = rectF2;
                        split = list;
                        height = i12;
                        width = i11;
                        i3 = i9;
                        iArr = iArr2;
                        i27 = i7;
                        i5 = i10;
                        i6 = i8;
                    }
                } else {
                    i20 = i31;
                    i15 = i29;
                    rectF5 = rectF7;
                    i14 = i3;
                    list2 = split;
                    rectF4 = rectF;
                    i18 = height;
                    i17 = width;
                    iArr2 = iArr;
                    i21 = 0;
                    i19 = i6;
                    i16 = i5;
                    block = block3;
                    i22 = i30;
                }
                if (effectRender instanceof PipeRender) {
                    effectRender.setFrameBufferSize(i22, i20);
                }
                checkFrameBuffer(i22, i20);
                effectRender.setParentFrameBufferId(this.mFrameBuffer.getId());
                this.mGLCanvas.beginBindFrameBuffer(this.mFrameBuffer.getId(), i22, i20);
                GLES20.glViewport(i21, i21, i22, i20);
                int i32 = i18;
                int i33 = i17;
                int[] yUVOffset = block.getYUVOffset(i19, i27, i33, i32);
                int i34 = i20;
                drawYuvAttribute2 = drawYuvAttribute;
                drawYuvAttribute2.mOffsetY = yUVOffset[i21];
                drawYuvAttribute2.mOffsetUV = yUVOffset[1];
                drawYuvAttribute2.mBlockWidth = i22;
                drawYuvAttribute2.mBlockHeight = i34;
                effectRender.draw(drawYuvAttribute2);
                Object[] objArr = new Object[2];
                int i35 = i15;
                objArr[i21] = Integer.valueOf(i35);
                List<Block> list3 = list2;
                objArr[1] = Integer.valueOf(list3.size());
                SnapshotRender.this.mFrameCounter.tick(String.format("[loop%d/%d]gl drawFrame", objArr));
                int i36 = i16;
                int i37 = i14;
                drawYuvAttribute2.mOriginalSize = new Size(i37, i36);
                if (drawYuvAttribute2.mApplyWaterMark) {
                    CounterUtil counterUtil = new CounterUtil();
                    counterUtil.reset("drawWaterMark");
                    int i38 = i2 - offset[i21];
                    int i39 = i - offset[1];
                    CounterUtil counterUtil2 = counterUtil;
                    i10 = i36;
                    i9 = i37;
                    i23 = i35;
                    int i40 = i34;
                    i12 = i32;
                    i11 = i33;
                    i8 = i19;
                    i25 = i22;
                    i7 = i27;
                    i24 = 0;
                    list = list3;
                    drawAgeGenderAndMagicMirrorWater(drawYuvAttribute2.mWaterInfos, -offset[i21], -offset[1], i33, i32, drawYuvAttribute2.mPreviewSize.getWidth(), drawYuvAttribute2.mPreviewSize.getHeight(), drawYuvAttribute2.mJpegRotation, false, false);
                    rectF3 = rectF5;
                    rectF2 = rectF4;
                    if (rectangle_collision(rectF3.left, rectF3.top, rectF3.width(), rectF3.height(), rectF2.left, rectF2.top, rectF2.width(), rectF2.height())) {
                        float[] intersectRect = getIntersectRect(rectF3.left, rectF3.top, rectF3.right, rectF3.bottom, rectF2.left, rectF2.top, rectF2.right, rectF2.bottom);
                        ShaderNativeUtil.mergeWaterMarkRange((int) intersectRect[0], (int) intersectRect[1], (int) (intersectRect[2] - intersectRect[0]), (int) (intersectRect[3] - intersectRect[1]), offset[0], offset[1], 4);
                        drawWaterMark(i38, i39, i9, i10, drawYuvAttribute2.mJpegRotation, drawYuvAttribute2.mTimeWatermark, false);
                    }
                    CounterUtil counterUtil3 = counterUtil2;
                    counterUtil3.tick("drawWaterMark");
                    this.mGLCanvas.endBindFrameBuffer();
                    i26 = i40;
                    Size size = new Size(i25, i26);
                    checkWatermarkFrameBuffer(size);
                    this.mGLCanvas.beginBindFrameBuffer(this.mWatermarkFrameBuffer);
                    RgbToYuvRender rgbToYuvRender = (RgbToYuvRender) fetchRender(FilterInfo.FILTER_ID_RGB2YUV);
                    updateRenderParameters(rgbToYuvRender, drawYuvAttribute2, true);
                    rgbToYuvRender.setParentFrameBufferId(this.mFrameBuffer.getId());
                    rgbToYuvRender.drawTexture(this.mFrameBuffer.getTexture().getId(), 0.0f, 0.0f, (float) size.getWidth(), (float) size.getHeight(), true);
                    counterUtil3.tick("drawWaterMark rgb2yuv");
                } else {
                    i10 = i36;
                    i9 = i37;
                    i23 = i35;
                    i26 = i34;
                    i12 = i32;
                    i11 = i33;
                    i24 = i21;
                    i8 = i19;
                    i7 = i27;
                    rectF2 = rectF4;
                    list = list3;
                    i25 = i22;
                    rectF3 = rectF5;
                }
                GLES20.glPixelStorei(SuperNightProcess.ASVL_PAF_RAW12_RGGB_12B, 1);
                ShaderNativeUtil.mergeYUV(i25, i26, yUVOffset[i24], yUVOffset[1]);
                Object[] objArr2 = new Object[2];
                i13 = i23;
                objArr2[i24] = Integer.valueOf(i13);
                objArr2[1] = Integer.valueOf(list.size());
                SnapshotRender.this.mFrameCounter.tick(String.format("[loop%d/%d]gl mergeYUV", objArr2));
                i29 = i13 + 1;
                rectF7 = rectF3;
                rectF = rectF2;
                split = list;
                height = i12;
                width = i11;
                i3 = i9;
                iArr = iArr2;
                i27 = i7;
                i5 = i10;
                i6 = i8;
            }
            int[] iArr3 = iArr;
            effectRender.deleteBuffer();
            this.mGLCanvas.endBindFrameBuffer();
            this.mGLCanvas.recycledResources();
            if (drawYuvAttribute2.mApplyWaterMark) {
                byte[] waterMarkRange = ShaderNativeUtil.getWaterMarkRange(SnapshotRender.this.mQuality, 4);
                int[] reverseCalculateRange = reverseCalculateRange(drawYuvAttribute2.mPictureSize.getWidth(), drawYuvAttribute2.mPictureSize.getHeight(), drawYuvAttribute2.mOutputSize.getWidth(), drawYuvAttribute2.mOutputSize.getHeight(), iArr3);
                drawYuvAttribute2.mDataOfTheRegionUnderWatermarks = waterMarkRange;
                drawYuvAttribute2.mCoordinatesOfTheRegionUnderWatermarks = reverseCalculateRange;
            }
            SnapshotRender.this.mTotalCounter.tick("TOTAL");
            return null;
        }

        private void checkFrameBuffer(int i, int i2) {
            if (this.mFrameBuffer == null || this.mFrameBuffer.getWidth() != i || this.mFrameBuffer.getHeight() != i2) {
                this.mFrameBuffer = new FrameBuffer(this.mGLCanvas, i, i2, 0);
            }
        }

        private void checkWatermarkFrameBuffer(Size size) {
            if (this.mWatermarkFrameBuffer == null || this.mWatermarkFrameBuffer.getWidth() < size.getWidth() || this.mWatermarkFrameBuffer.getHeight() < size.getHeight()) {
                this.mWatermarkFrameBuffer = new FrameBuffer(this.mGLCanvas, size.getWidth(), size.getHeight(), 0);
            }
        }

        private byte[] compress(Bitmap bitmap) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return byteArray;
        }

        private void drawAgeGenderAndMagicMirrorWater(List<WaterMarkData> list, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z, boolean z2) {
            if (b.jF() && !z && CameraSettings.isAgeGenderAndMagicMirrorWaterOpen()) {
                WaterMarkBitmap waterMarkBitmap = new WaterMarkBitmap(list);
                WaterMarkData waterMarkData = waterMarkBitmap.getWaterMarkData();
                if (waterMarkData != null) {
                    AgeGenderAndMagicMirrorWaterMark ageGenderAndMagicMirrorWaterMark = new AgeGenderAndMagicMirrorWaterMark(waterMarkData.getImage(), i3, i4, i7, i5, i6, 0.0f, 0.0f);
                    drawWaterMark(ageGenderAndMagicMirrorWaterMark, i, i2, i7 - waterMarkData.getOrientation(), z2);
                }
                waterMarkBitmap.releaseBitmap();
                Log.d(WaterMarkBitmap.class.getSimpleName(), "Draw age_gender_and_magic_mirror water mark");
            }
        }

        private boolean drawImage(DrawYuvAttribute drawYuvAttribute, boolean z) {
            byte[] applyEffect = (CameraSettings.isTiltShiftOn() || (drawYuvAttribute.mEffectIndex == FilterInfo.FILTER_ID_NONE && !CameraSettings.isAgeGenderAndMagicMirrorWaterOpen() && !CameraSettings.isTiltShiftOn() && !z && (drawYuvAttribute.mApplyWaterMark || drawYuvAttribute.mTimeWatermark != null))) ? applyEffect(drawYuvAttribute) : blockSplitApplyEffect(drawYuvAttribute);
            String access$400 = SnapshotRender.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("mainLen=");
            sb.append(applyEffect == null ? TEDefine.FACE_BEAUTY_NULL : Integer.valueOf(applyEffect.length));
            Log.d(access$400, sb.toString());
            return true;
        }

        private void drawWaterMark(WaterMark waterMark, int i, int i2, int i3, boolean z) {
            this.mGLCanvas.getState().pushState();
            if (z) {
                int i4 = (i3 + MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT) % MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT;
                int i5 = waterMark.mPictureWidth;
                if (i4 == 90 || i4 == 270) {
                    int i6 = waterMark.mPictureHeight;
                    this.mGLCanvas.getState().translate(0.0f, (float) ((i6 / 2) + i2));
                    this.mGLCanvas.getState().rotate(180.0f, 1.0f, 0.0f, 0.0f);
                    this.mGLCanvas.getState().translate(0.0f, (float) (((-i6) / 2) - i2));
                } else {
                    this.mGLCanvas.getState().translate((float) (i5 / 2), 0.0f);
                    this.mGLCanvas.getState().rotate(180.0f, 0.0f, 1.0f, 0.0f);
                    this.mGLCanvas.getState().translate((float) ((-i5) / 2), 0.0f);
                }
            }
            if (i3 != 0) {
                this.mGLCanvas.getState().translate((float) (waterMark.getCenterX() + i), (float) (waterMark.getCenterY() + i2));
                this.mGLCanvas.getState().rotate((float) (-i3), 0.0f, 0.0f, 1.0f);
                this.mGLCanvas.getState().translate((float) ((-i) - waterMark.getCenterX()), (float) ((-i2) - waterMark.getCenterY()));
            }
            BasicRender basicRender = this.mGLCanvas.getBasicRender();
            DrawBasicTexAttribute drawBasicTexAttribute = new DrawBasicTexAttribute(waterMark.getTexture(), i + waterMark.getLeft(), i2 + waterMark.getTop(), waterMark.getWidth(), waterMark.getHeight());
            basicRender.draw(drawBasicTexAttribute);
            this.mGLCanvas.getState().popState();
        }

        private Render fetchRender(int i) {
            RenderGroup effectRenderGroup = this.mGLCanvas.getEffectRenderGroup();
            Render render = effectRenderGroup.getRender(i);
            if (render != null) {
                return render;
            }
            this.mGLCanvas.prepareEffectRenders(false, i);
            return effectRenderGroup.getRender(i);
        }

        private PipeRender getEffectRender(DrawYuvAttribute drawYuvAttribute) {
            PipeRender pipeRender = new PipeRender(this.mGLCanvas);
            pipeRender.addRender(fetchRender(FilterInfo.FILTER_ID_YUV2RGB));
            if (drawYuvAttribute.mEffectIndex != FilterInfo.FILTER_ID_NONE) {
                Render fetchRender = fetchRender(drawYuvAttribute.mEffectIndex);
                if (fetchRender != null) {
                    pipeRender.addRender(fetchRender);
                }
            }
            if (drawYuvAttribute.mTiltShiftMode != null) {
                Render render = null;
                if (ComponentRunningTiltValue.TILT_CIRCLE.equals(drawYuvAttribute.mTiltShiftMode)) {
                    render = fetchRender(FilterInfo.FILTER_ID_GAUSSIAN);
                } else if (ComponentRunningTiltValue.TILT_PARALLEL.equals(drawYuvAttribute.mTiltShiftMode)) {
                    render = fetchRender(FilterInfo.FILTER_ID_TILTSHIFT);
                }
                if (render != null) {
                    pipeRender.addRender(render);
                }
            }
            if (!drawYuvAttribute.mApplyWaterMark) {
                pipeRender.addRender(fetchRender(FilterInfo.FILTER_ID_RGB2YUV));
            }
            return pipeRender;
        }

        private Bitmap getGPURBGA(int i, int i2, int i3, int i4) {
            int i5 = i3 * i4;
            int[] iArr = new int[i5];
            int[] iArr2 = new int[i5];
            IntBuffer wrap = IntBuffer.wrap(iArr);
            wrap.position(0);
            GLES20.glReadPixels(i, i2, i3, i4, 6408, 5121, wrap);
            int i6 = 0;
            int i7 = 0;
            while (i6 < i4) {
                int i8 = i7;
                for (int i9 = 0; i9 < i3; i9++) {
                    int i10 = iArr[i8];
                    iArr2[i8] = (i10 & -16711936) | ((i10 << 16) & 16711680) | ((i10 >> 16) & 255);
                    i8++;
                }
                i6++;
                i7 = i8;
            }
            return Bitmap.createBitmap(iArr2, i3, i4, Bitmap.Config.ARGB_8888);
        }

        private Bitmap getGPUYYY(int i, int i2, int i3, int i4) {
            int i5 = i3 >> 1;
            byte[] bArr = new byte[(i5 * i5 * 4)];
            int i6 = i3 * i4;
            int[] iArr = new int[i6];
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            wrap.position(0);
            GLES20.glReadPixels(i, i2, i5, i4 >> 1, 6408, 5121, wrap);
            int i7 = 0;
            for (int i8 = 0; i8 < i6; i8++) {
                byte b2 = bArr[i8];
                iArr[i7] = (b2 & 255) | ((b2 << 8) & 65280) | -16777216 | ((b2 << 16) & 16711680);
                i7++;
            }
            return Bitmap.createBitmap(iArr, i3, i4, Bitmap.Config.ARGB_8888);
        }

        private float[] getIntersectRect(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
            float[] fArr = new float[4];
            if (f <= f5) {
                f = f5;
            }
            fArr[0] = f;
            if (f2 <= f6) {
                f2 = f6;
            }
            fArr[1] = f2;
            int i = (f3 > f7 ? 1 : (f3 == f7 ? 0 : -1));
            fArr[2] = f7;
            if (f4 >= f8) {
                f4 = f8;
            }
            fArr[3] = f4;
            return fArr;
        }

        private void initEGL(EGLContext eGLContext, boolean z) {
            if (SnapshotRender.this.mEglCore == null) {
                EglCore unused = SnapshotRender.this.mEglCore = new EglCore(eGLContext, 2);
            }
            if (z && SnapshotRender.this.mRenderSurface != null) {
                SnapshotRender.this.mRenderSurface.release();
                PbufferSurface unused2 = SnapshotRender.this.mRenderSurface = null;
            }
            PbufferSurface unused3 = SnapshotRender.this.mRenderSurface = new PbufferSurface(SnapshotRender.this.mEglCore, 1, 1);
            SnapshotRender.this.mRenderSurface.makeCurrent();
        }

        private boolean rectangle_collision(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
            return f <= f7 + f5 && f + f3 >= f5 && f2 <= f8 + f6 && f2 + f4 >= f6;
        }

        private void release() {
            this.mFrameBuffer = null;
            this.mWatermarkFrameBuffer = null;
            if (SnapshotRender.this.mRenderSurface != null) {
                SnapshotRender.this.mRenderSurface.makeCurrent();
                this.mGLCanvas.recycledResources();
                SnapshotRender.this.mRenderSurface.makeNothingCurrent();
            }
            this.mGLCanvas = null;
            SnapshotRender.this.destroy();
        }

        private int[] reverseCalculateRange(int i, int i2, int i3, int i4, int[] iArr) {
            int[] iArr2 = new int[4];
            float f = ((float) i4) / ((float) i2);
            if (((float) i3) / ((float) i) == f || i3 == i4) {
                iArr2[0] = (int) (((float) iArr[0]) * f);
                iArr2[1] = (int) (((float) iArr[1]) * f);
                iArr2[2] = (int) (((float) iArr[2]) * f);
                iArr2[3] = (int) (((float) iArr[3]) * f);
                return iArr2;
            }
            String access$400 = SnapshotRender.TAG;
            Log.e(access$400, "orgin w:" + i3 + " origin h:" + i4 + " image w:" + i + " image h:" + i2 + " in different ratio");
            return null;
        }

        private void updateRenderParameters(Render render, DrawYuvAttribute drawYuvAttribute, boolean z) {
            if (z) {
                render.setViewportSize(SnapshotRender.this.mBlockWidth, SnapshotRender.this.mBlockHeight);
            } else {
                render.setViewportSize(drawYuvAttribute.mPictureSize.getWidth(), drawYuvAttribute.mPictureSize.getHeight());
            }
            render.setPreviewSize(drawYuvAttribute.mPreviewSize.getWidth(), drawYuvAttribute.mPreviewSize.getHeight());
            render.setEffectRangeAttribute(drawYuvAttribute.mAttribute);
            render.setMirror(drawYuvAttribute.mMirror);
            render.setSnapshotSize(drawYuvAttribute.mPictureSize.getWidth(), drawYuvAttribute.mPictureSize.getHeight());
            render.setOrientation(drawYuvAttribute.mOrientation);
            render.setShootRotation(drawYuvAttribute.mShootRotation);
            render.setJpegOrientation(drawYuvAttribute.mJpegRotation);
        }

        public void drawWaterMark(int i, int i2, int i3, int i4, int i5, String str, boolean z) {
            if (str != null) {
                int i6 = i5;
                NewStyleTextWaterMark newStyleTextWaterMark = new NewStyleTextWaterMark(str, i3, i4, i6, SnapshotRender.this.mDeviceWaterMarkParam.isMiMovieWatermarkEnable());
                drawWaterMark(newStyleTextWaterMark, i, i2, i6, z);
            }
            Bitmap bitmap = null;
            if (SnapshotRender.this.mFrontCameraWaterMarkBitmap != null && SnapshotRender.this.mDeviceWaterMarkParam.isFrontWatermarkEnable()) {
                bitmap = SnapshotRender.this.mFrontCameraWaterMarkBitmap;
            } else if (SnapshotRender.this.mDualCameraWaterMarkBitmap != null && SnapshotRender.this.mDeviceWaterMarkParam.isDualWatermarkEnable()) {
                if (SnapshotRender.this.mCurrentCustomWaterMarkText != null && !SnapshotRender.this.mCurrentCustomWaterMarkText.equals(CameraSettings.getCustomWatermark())) {
                    String unused = SnapshotRender.this.mCurrentCustomWaterMarkText = CameraSettings.getCustomWatermark();
                    Bitmap unused2 = SnapshotRender.this.mDualCameraWaterMarkBitmap = SnapshotRender.this.loadCameraWatermark(CameraAppImpl.getAndroidContext());
                }
                bitmap = SnapshotRender.this.mDualCameraWaterMarkBitmap;
                boolean equals = CameraSettings.getCustomWatermark().equals(CameraSettings.getDefaultWatermarkStr());
                if (SnapshotRender.this.mDeviceWaterMarkParam.isUltraWatermarkEnable() && equals) {
                    if (SnapshotRender.this.mUltraPixelCameraWaterMarkBitmap == null) {
                        Bitmap unused3 = SnapshotRender.this.mUltraPixelCameraWaterMarkBitmap = SnapshotRender.this.loadUltraWatermark(CameraAppImpl.getAndroidContext());
                    }
                    if (SnapshotRender.this.mUltraPixelCameraWaterMarkBitmap != null) {
                        bitmap = SnapshotRender.this.mUltraPixelCameraWaterMarkBitmap;
                    }
                }
            }
            Bitmap bitmap2 = bitmap;
            if (bitmap2 != null) {
                ImageWaterMark imageWaterMark = new ImageWaterMark(bitmap2, i3, i4, i5, SnapshotRender.this.mDeviceWaterMarkParam.getSize(), SnapshotRender.this.mDeviceWaterMarkParam.getPaddingX(), SnapshotRender.this.mDeviceWaterMarkParam.getPaddingY(), SnapshotRender.this.mDeviceWaterMarkParam.isMiMovieWatermarkEnable());
                drawWaterMark(imageWaterMark, i, i2, i5, z);
            }
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    initEGL((EGLContext) null, false);
                    this.mGLCanvas = new SnapshotCanvas();
                    return;
                case 1:
                    drawImage((DrawYuvAttribute) message.obj, false);
                    this.mGLCanvas.recycledResources();
                    if (SnapshotRender.this.mReleasePending && !hasMessages(1)) {
                        release();
                    }
                    synchronized (SnapshotRender.this.mLock) {
                        SnapshotRender.access$310(SnapshotRender.this);
                    }
                    return;
                case 2:
                    FilterProcessor.YuvAttributeWrapper yuvAttributeWrapper = (FilterProcessor.YuvAttributeWrapper) message.obj;
                    DrawYuvAttribute drawYuvAttribute = yuvAttributeWrapper.mAttribute;
                    ConditionVariable conditionVariable = yuvAttributeWrapper.mBlocker;
                    if (drawYuvAttribute.mPictureSize.getWidth() == 0 || drawYuvAttribute.mPictureSize.getHeight() == 0) {
                        Log.e(SnapshotRender.TAG, String.format("yuv image is broken width %d height %d", new Object[]{Integer.valueOf(drawYuvAttribute.mPictureSize.getWidth()), Integer.valueOf(drawYuvAttribute.mPictureSize.getHeight())}));
                        if (conditionVariable != null) {
                            conditionVariable.open();
                            return;
                        }
                        return;
                    }
                    this.mGLCanvas.setSize(drawYuvAttribute.mPictureSize.getWidth(), drawYuvAttribute.mPictureSize.getHeight());
                    int access$500 = SnapshotRender.this.calEachBlockHeight(drawYuvAttribute.mPictureSize.getWidth(), drawYuvAttribute.mPictureSize.getHeight());
                    int unused = SnapshotRender.this.mBlockWidth = drawYuvAttribute.mPictureSize.getWidth();
                    IntBuffer allocate = IntBuffer.allocate(2);
                    GLES20.glGetIntegerv(3379, allocate);
                    boolean z = false;
                    while (SnapshotRender.this.mBlockWidth > allocate.get(0)) {
                        int unused2 = SnapshotRender.this.mBlockWidth = SnapshotRender.this.mBlockWidth / 2;
                        access$500 /= 2;
                        if ((drawYuvAttribute.mJpegRotation + MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT) % 180 == 0) {
                            z = true;
                        }
                    }
                    int unused3 = SnapshotRender.this.mBlockHeight = drawYuvAttribute.mPictureSize.getHeight() / access$500;
                    int unused4 = SnapshotRender.this.mAdjWidth = SnapshotRender.this.mBlockWidth;
                    int unused5 = SnapshotRender.this.mAdjHeight = SnapshotRender.this.mBlockHeight;
                    if (SnapshotRender.this.mBlockHeight % 4 != 0) {
                        SnapshotRender.access$712(SnapshotRender.this, 4 - (SnapshotRender.this.mBlockHeight % 4));
                    }
                    drawImage(drawYuvAttribute, z);
                    this.mGLCanvas.recycledResources();
                    if (conditionVariable != null) {
                        conditionVariable.open();
                        return;
                    }
                    return;
                case 5:
                    release();
                    return;
                case 6:
                    this.mGLCanvas.prepareEffectRenders(false, message.arg1);
                    return;
                default:
                    return;
            }
        }
    }

    private static class RenderHolder {
        /* access modifiers changed from: private */
        public static SnapshotRender render = new SnapshotRender();

        private RenderHolder() {
        }
    }

    private static /* synthetic */ void $closeResource(Throwable th, AutoCloseable autoCloseable) {
        if (th != null) {
            try {
                autoCloseable.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
        } else {
            autoCloseable.close();
        }
    }

    private SnapshotRender() {
        this.mQuality = 97;
        this.mImageQueueSize = 0;
        this.mLock = new Object();
        String str = TAG;
        Log.d(str, "SnapshotRender created " + this);
        this.mEglThread = new HandlerThread("SnapshotRender");
        this.mEglThread.start();
        this.mEglHandler = new EGLHandler(this.mEglThread.getLooper());
        if (this.mMemImage == null) {
            this.mMemImage = new MemYuvImage();
        }
        this.mFrameCounter = new CounterUtil();
        this.mTotalCounter = new CounterUtil();
        this.mSplitter = new Splitter();
        this.mEglHandler.sendEmptyMessage(0);
        this.mRelease = false;
    }

    static /* synthetic */ int access$310(SnapshotRender snapshotRender) {
        int i = snapshotRender.mImageQueueSize;
        snapshotRender.mImageQueueSize = i - 1;
        return i;
    }

    static /* synthetic */ int access$712(SnapshotRender snapshotRender, int i) {
        int i2 = snapshotRender.mBlockHeight + i;
        snapshotRender.mBlockHeight = i2;
        return i2;
    }

    /* access modifiers changed from: private */
    public int calEachBlockHeight(int i, int i2) {
        int i3 = 1;
        while (i * i2 > 6000000) {
            i2 >>= 1;
            i3 <<= 1;
        }
        return i3;
    }

    /* access modifiers changed from: private */
    public void destroy() {
        this.mRelease = true;
        this.mReleasePending = false;
        if (this.mRenderSurface != null) {
            this.mRenderSurface.release();
            this.mRenderSurface = null;
        }
        if (this.mEglCore != null) {
            this.mEglCore.release();
            this.mEglCore = null;
        }
        this.mEglThread.quit();
        if (this.mDualCameraWaterMarkBitmap != null && !this.mDualCameraWaterMarkBitmap.isRecycled()) {
            this.mDualCameraWaterMarkBitmap.recycle();
            this.mDualCameraWaterMarkBitmap = null;
        }
        if (this.mFrontCameraWaterMarkBitmap != null && !this.mFrontCameraWaterMarkBitmap.isRecycled()) {
            this.mFrontCameraWaterMarkBitmap.recycle();
            this.mFrontCameraWaterMarkBitmap = null;
        }
        if (this.mUltraPixelCameraWaterMarkBitmap != null && !this.mUltraPixelCameraWaterMarkBitmap.isRecycled()) {
            this.mUltraPixelCameraWaterMarkBitmap.recycle();
            this.mUltraPixelCameraWaterMarkBitmap = null;
        }
        System.gc();
        String str = TAG;
        Log.d(str, "SnapshotRender released " + this);
    }

    public static SnapshotRender getRender() {
        return RenderHolder.render;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004d, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004e, code lost:
        r1 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0052, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0053, code lost:
        r3 = r1;
        r1 = r0;
        r0 = r3;
     */
    public Bitmap loadCameraWatermark(Context context) {
        FileInputStream fileInputStream;
        Throwable th;
        Throwable th2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inPurgeable = true;
        options.inPremultiplied = false;
        if (!DataRepository.dataItemFeature().fQ() && !DataRepository.dataItemFeature().fR()) {
            return BitmapFactory.decodeFile(CameraSettings.getDualCameraWaterMarkFilePathVendor(), options);
        }
        File file = new File(context.getFilesDir(), Util.WATERMARK_FILE_NAME);
        if (!file.exists()) {
            Util.generateWatermark2File();
        }
        try {
            fileInputStream = new FileInputStream(file);
            Bitmap decodeStream = BitmapFactory.decodeStream(fileInputStream, (Rect) null, options);
            $closeResource((Throwable) null, fileInputStream);
            return decodeStream;
        } catch (Exception e) {
            Log.d(TAG, "Failed to load app camera watermark ", e);
            return null;
        }
        $closeResource(th, fileInputStream);
        throw th2;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0045, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0046, code lost:
        r1 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004a, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004b, code lost:
        r4 = r1;
        r1 = r0;
        r0 = r4;
     */
    public Bitmap loadUltraWatermark(Context context) {
        FileInputStream fileInputStream;
        Throwable th;
        Throwable th2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inPurgeable = true;
        options.inPremultiplied = false;
        if (DataRepository.dataItemFeature().fQ() || DataRepository.dataItemFeature().fR()) {
            File file = new File(context.getFilesDir(), Util.WATERMARK_ULTRA_PIXEL_FILE_NAME);
            if (!file.exists()) {
                return Util.generateUltraPixelWatermark2File();
            }
            try {
                fileInputStream = new FileInputStream(file);
                Bitmap decodeStream = BitmapFactory.decodeStream(fileInputStream, (Rect) null, options);
                $closeResource((Throwable) null, fileInputStream);
                return decodeStream;
            } catch (Exception e) {
                Log.d(TAG, "Failed to load app camera watermark ", e);
            }
        }
        return null;
        $closeResource(th, fileInputStream);
        throw th2;
    }

    public boolean isRelease() {
        return this.mReleasePending || this.mRelease;
    }

    public void prepareEffectRender(DeviceWatermarkParam deviceWatermarkParam, int i) {
        this.mDeviceWaterMarkParam = deviceWatermarkParam;
        if (deviceWatermarkParam.isDualWatermarkEnable() && this.mDualCameraWaterMarkBitmap == null) {
            this.mDualCameraWaterMarkBitmap = loadCameraWatermark(CameraAppImpl.getAndroidContext());
            this.mCurrentCustomWaterMarkText = CameraSettings.getCustomWatermark();
        } else if (deviceWatermarkParam.isFrontWatermarkEnable() && this.mFrontCameraWaterMarkBitmap == null) {
            this.mFrontCameraWaterMarkBitmap = Util.loadFrontCameraWatermark(CameraAppImpl.getAndroidContext());
        }
        if (i != FilterInfo.FILTER_ID_NONE) {
            this.mEglHandler.obtainMessage(6, i, 0).sendToTarget();
        }
    }

    public boolean processImageAsync(DrawYuvAttribute drawYuvAttribute) {
        Log.d(TAG, "queueSize=" + this.mImageQueueSize);
        if (this.mImageQueueSize >= 7) {
            Log.d(TAG, "queueSize is full");
            return false;
        }
        synchronized (this.mLock) {
            this.mImageQueueSize++;
        }
        this.mEglHandler.obtainMessage(1, drawYuvAttribute).sendToTarget();
        return true;
    }

    public void processImageSync(FilterProcessor.YuvAttributeWrapper yuvAttributeWrapper) {
        this.mEglHandler.obtainMessage(2, yuvAttributeWrapper).sendToTarget();
    }

    public void release() {
        if (this.mEglHandler.hasMessages(1)) {
            Log.d(TAG, "release: try to release but message is not null, so pending it");
            this.mReleasePending = true;
            return;
        }
        this.mEglHandler.sendEmptyMessage(5);
    }
}
