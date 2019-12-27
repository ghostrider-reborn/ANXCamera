package com.android.camera2;

import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.Xml;
import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPMeta;
import com.android.camera.XmpHelper;
import com.android.camera.log.Log;
import com.android.gallery3d.exif.ExifInterface;
import com.xiaomi.camera.core.PictureInfo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import org.xmlpull.v1.XmlSerializer;

public class ArcsoftDepthMap {
    private static final int BLUR_LEVEL_START_ADDR = 16;
    private static final int DAPTH_MAP_START_ADDR = 152;
    private static final int DATA_LENGTH_4 = 4;
    private static final int DATA_LENGTH_START_ADDR = 148;
    private static final int HEADER_LENGTH_START_ADDR = 4;
    private static final int HEADER_START_ADDR = 0;
    private static final int POINT_X_START_ADDR = 8;
    private static final int POINT_Y_START_ADDR = 12;
    private static final String TAG = "ArcsoftDepthMap";
    @Deprecated
    public static final int TAG_DEPTH_MAP_BLUR_LEVEL = ExifInterface.defineTag(2, -30575);
    @Deprecated
    public static final int TAG_DEPTH_MAP_FOCUS_POINT = ExifInterface.defineTag(2, -30576);
    private byte[] mDepthMapHeader;
    private byte[] mDepthMapOriginalData;

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

    public ArcsoftDepthMap(byte[] bArr) {
        if (bArr != null) {
            int headerTag = getHeaderTag(bArr);
            if (headerTag == 128) {
                this.mDepthMapOriginalData = bArr;
                this.mDepthMapHeader = getDepthMapHeader();
                return;
            }
            throw new IllegalArgumentException("Illegal depth format! 0x80 != " + headerTag);
        }
        throw new IllegalArgumentException("Null depth data!");
    }

    private static byte[] getBytes(byte[] bArr, int i, int i2) {
        if (i2 <= 0 || i < 0 || i2 > bArr.length - i) {
            throw new IllegalArgumentException("WRONG ARGUMENT: from =" + i + ", length = " + i2);
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    private static int getHeaderTag(byte[] bArr) {
        return getInteger(getBytes(bArr, 0, 4));
    }

    private static int getInteger(byte[] bArr) {
        if (bArr.length == 4) {
            int i = 0;
            for (int i2 = 0; i2 < 4; i2++) {
                i += (bArr[i2] & 255) << (i2 * 8);
            }
            return i;
        }
        throw new IllegalArgumentException("bytes can not covert to a integer value!");
    }

    public static boolean isDepthMapData(byte[] bArr) {
        boolean z = bArr != null && bArr.length > 4 && getHeaderTag(bArr) == 128;
        if (!z) {
            Log.e(TAG, "Illegal depthmap format");
        }
        return z;
    }

    public int getBlurLevel() {
        return getInteger(getBytes(this.mDepthMapHeader, 16, 4));
    }

    public byte[] getDepthMapData() {
        return getBytes(this.mDepthMapOriginalData, 152, getDepthMapLength());
    }

    public byte[] getDepthMapHeader() {
        return getBytes(this.mDepthMapOriginalData, 0, getInteger(getBytes(this.mDepthMapOriginalData, 4, 4)));
    }

    public int getDepthMapLength() {
        return getInteger(getBytes(this.mDepthMapHeader, 148, 4));
    }

    public Point getFocusPoint() {
        return new Point(getInteger(getBytes(this.mDepthMapHeader, 8, 4)), getInteger(getBytes(this.mDepthMapHeader, 12, 4)));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:127:0x02e9, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x02ea, code lost:
        r4 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:?, code lost:
        $closeResource(r3, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x02ee, code lost:
        throw r4;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:108:0x02d2, B:125:0x02e8] */
    public byte[] writePortraitExif(int i, byte[] bArr, byte[] bArr2, @NonNull int[] iArr, byte[] bArr3, @NonNull int[] iArr2, byte[] bArr4, int[] iArr3, int i2, boolean z, boolean z2, PictureInfo pictureInfo, int i3, int i4) {
        byte[] bArr5;
        byte[] bArr6;
        String str;
        byte[] bArr7;
        byte[] bArr8;
        Throwable th;
        Throwable th2;
        int i5 = i;
        byte[] bArr9 = bArr;
        byte[] bArr10 = bArr2;
        byte[] bArr11 = bArr3;
        byte[] bArr12 = bArr4;
        int[] iArr4 = iArr3;
        int i6 = i2;
        Point focusPoint = getFocusPoint();
        int blurLevel = getBlurLevel();
        boolean isFrontCamera = pictureInfo.isFrontCamera();
        int i7 = -1;
        int i8 = 5;
        if (i5 > 0) {
            i7 = isFrontCamera ? (!pictureInfo.isAiEnabled() || pictureInfo.getAiType() != 10) ? 40 : 70 : (!pictureInfo.isAiEnabled() || pictureInfo.getAiType() != 10) ? 10 : 30;
        } else {
            i8 = -1;
        }
        Log.d(TAG, "writePortraitExif: focusPoint: " + focusPoint);
        Log.d(TAG, "writePortraitExif: blurLevel: " + blurLevel);
        Log.d(TAG, "writePortraitExif: shineThreshold: " + i8);
        Log.d(TAG, "writePortraitExif: shineLevel: " + i7);
        Log.d(TAG, "writePortraitExif: lightingPattern: " + i6);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                ExifInterface exifInterface = new ExifInterface();
                exifInterface.readExif(bArr9);
                exifInterface.addXiaomiDepthmapVersion(i5);
                exifInterface.addDepthMapBlurLevel(blurLevel);
                exifInterface.addPortraitLighting(i6);
                if (z2) {
                    exifInterface.addFrontMirror(z ? 1 : 0);
                }
                exifInterface.writeExif(bArr9, (OutputStream) byteArrayOutputStream);
                bArr5 = byteArrayOutputStream.toByteArray();
                try {
                    $closeResource((Throwable) null, byteArrayOutputStream);
                } catch (IOException unused) {
                }
            } catch (Throwable th3) {
                Throwable th4 = th3;
                $closeResource(th2, byteArrayOutputStream);
                throw th4;
            }
        } catch (IOException unused2) {
            bArr5 = null;
            Log.e(TAG, "writePortraitExif(): Failed to write depthmap associated exif metadata");
            bArr6 = bArr5;
            if (bArr6 != null) {
            }
            Log.e(TAG, "writePortraitExif(): #1: return original jpeg");
            return bArr9;
        }
        bArr6 = bArr5;
        if (bArr6 != null || bArr6.length <= bArr9.length) {
            Log.e(TAG, "writePortraitExif(): #1: return original jpeg");
            return bArr9;
        }
        try {
            XmlSerializer newSerializer = Xml.newSerializer();
            StringWriter stringWriter = new StringWriter();
            newSerializer.setOutput(stringWriter);
            newSerializer.startDocument("UTF-8", true);
            newSerializer.startTag((String) null, "depthmap");
            newSerializer.attribute((String) null, "version", String.valueOf(i));
            newSerializer.attribute((String) null, "focuspoint", focusPoint.x + "," + focusPoint.y);
            newSerializer.attribute((String) null, "blurlevel", String.valueOf(blurLevel));
            newSerializer.attribute((String) null, "shinethreshold", String.valueOf(i8));
            newSerializer.attribute((String) null, "shinelevel", String.valueOf(i7));
            newSerializer.attribute((String) null, "rawlength", String.valueOf(i3));
            newSerializer.attribute((String) null, "depthlength", String.valueOf(i4));
            newSerializer.endTag((String) null, "depthmap");
            if (bArr12 != null) {
                if (bArr12.length > 0 && iArr4 != null && iArr4.length >= 4) {
                    newSerializer.startTag((String) null, "subimage");
                    newSerializer.attribute((String) null, "offset", String.valueOf(bArr12.length + (bArr10 != null ? bArr10.length : 0) + (bArr11 != null ? bArr11.length : 0) + i3 + i4));
                    newSerializer.attribute((String) null, "length", String.valueOf(bArr12.length));
                    newSerializer.attribute((String) null, "paddingx", String.valueOf(iArr4[0]));
                    newSerializer.attribute((String) null, "paddingy", String.valueOf(iArr4[1]));
                    newSerializer.attribute((String) null, "width", String.valueOf(iArr4[2]));
                    newSerializer.attribute((String) null, "height", String.valueOf(iArr4[3]));
                    newSerializer.endTag((String) null, "subimage");
                }
            }
            if (bArr10 != null && bArr10.length > 0) {
                newSerializer.startTag((String) null, "lenswatermark");
                newSerializer.attribute((String) null, "offset", String.valueOf(bArr10.length + (bArr11 != null ? bArr11.length : 0) + i3 + i4));
                newSerializer.attribute((String) null, "length", String.valueOf(bArr10.length));
                newSerializer.attribute((String) null, "width", String.valueOf(iArr[0]));
                newSerializer.attribute((String) null, "height", String.valueOf(iArr[1]));
                newSerializer.attribute((String) null, "paddingx", String.valueOf(iArr[2]));
                newSerializer.attribute((String) null, "paddingy", String.valueOf(iArr[3]));
                newSerializer.endTag((String) null, "lenswatermark");
            }
            if (bArr11 != null && bArr11.length > 0) {
                newSerializer.startTag((String) null, "timewatermark");
                newSerializer.attribute((String) null, "offset", String.valueOf(bArr11.length + i3 + i4));
                newSerializer.attribute((String) null, "length", String.valueOf(bArr11.length));
                newSerializer.attribute((String) null, "width", String.valueOf(iArr2[0]));
                newSerializer.attribute((String) null, "height", String.valueOf(iArr2[1]));
                newSerializer.attribute((String) null, "paddingx", String.valueOf(iArr2[2]));
                newSerializer.attribute((String) null, "paddingy", String.valueOf(iArr2[3]));
                newSerializer.endTag((String) null, "timewatermark");
            }
            newSerializer.endDocument();
            str = stringWriter.toString();
        } catch (IOException unused3) {
            Log.e(TAG, "writePortraitExif(): Failed to generate depthmap associated xmp metadata");
            str = null;
        }
        if (str == null) {
            Log.e(TAG, "writePortraitExif(): #2: return original jpeg");
            return bArr9;
        }
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr6);
            try {
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                try {
                    XMPMeta createXMPMeta = XmpHelper.createXMPMeta();
                    createXMPMeta.setProperty(XmpHelper.XIAOMI_XMP_METADATA_NAMESPACE, XmpHelper.XIAOMI_XMP_METADATA_PROPERTY_NAME, str);
                    XmpHelper.writeXMPMeta(byteArrayInputStream, byteArrayOutputStream2, createXMPMeta);
                    if (bArr12 != null) {
                        try {
                            if (bArr12.length > 0 && iArr4 != null && iArr4.length >= 4) {
                                byteArrayOutputStream2.write(bArr12);
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            bArr8 = null;
                            try {
                                throw th;
                            } catch (Throwable th6) {
                                th = th6;
                                bArr7 = bArr8;
                                Throwable th7 = th;
                                throw th7;
                            }
                        }
                    }
                    if (bArr10 != null) {
                        byteArrayOutputStream2.write(bArr10);
                    }
                    if (bArr11 != null) {
                        byteArrayOutputStream2.write(bArr11);
                    }
                    byteArrayOutputStream2.flush();
                    bArr7 = byteArrayOutputStream2.toByteArray();
                    try {
                        $closeResource((Throwable) null, byteArrayOutputStream2);
                        $closeResource((Throwable) null, byteArrayInputStream);
                    } catch (Throwable th8) {
                        th = th8;
                        Throwable th72 = th;
                        throw th72;
                    }
                } catch (Throwable th9) {
                    bArr8 = null;
                    th = th9;
                    throw th;
                }
            } catch (Throwable th10) {
                th = th10;
                bArr8 = null;
                bArr7 = bArr8;
                Throwable th722 = th;
                throw th722;
            }
        } catch (XMPException | IOException unused4) {
            bArr7 = null;
            Log.d(TAG, "writePortraitExif(): Failed to insert depthmap associated xmp metadata");
            if (bArr7 == null) {
            }
            Log.e(TAG, "writePortraitExif(): #3: return original jpeg");
            return bArr9;
        }
        if (bArr7 == null && bArr7.length > bArr6.length) {
            return bArr7;
        }
        Log.e(TAG, "writePortraitExif(): #3: return original jpeg");
        return bArr9;
    }
}
