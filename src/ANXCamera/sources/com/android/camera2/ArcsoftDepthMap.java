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
    private static final String TAG = ArcsoftDepthMap.class.getSimpleName();
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

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v12, resolved type: java.lang.Throwable} */
    /* JADX WARNING: type inference failed for: r3v16 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x038e  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0125  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x02fb  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0306  */
    public byte[] writePortraitExif(int i, byte[] bArr, byte[] bArr2, @NonNull int[] iArr, byte[] bArr3, @NonNull int[] iArr2, byte[] bArr4, int[] iArr3, int i2, boolean z, boolean z2, boolean z3, PictureInfo pictureInfo, int i3, int i4) {
        byte[] bArr5;
        byte[] bArr6;
        String str;
        byte[] bArr7;
        byte[] bArr8;
        Throwable th;
        byte[] bArr9;
        Throwable th2;
        byte[] bArr10;
        Throwable th3;
        byte[] bArr11;
        byte[] bArr12;
        Throwable th4;
        int i5;
        int i6 = i;
        byte[] bArr13 = bArr;
        byte[] bArr14 = bArr2;
        byte[] bArr15 = bArr3;
        byte[] bArr16 = bArr4;
        int[] iArr4 = iArr3;
        int i7 = i2;
        Point focusPoint = getFocusPoint();
        int blurLevel = getBlurLevel();
        boolean isFrontCamera = pictureInfo.isFrontCamera();
        int i8 = -1;
        int i9 = 10;
        if (i6 > 0) {
            if (isFrontCamera) {
                i5 = (!pictureInfo.isAiEnabled() || pictureInfo.getAiType() != 10) ? 40 : 70;
            } else {
                if (pictureInfo.isAiEnabled() && pictureInfo.getAiType() == 10) {
                    i5 = 30;
                }
                i8 = 5;
            }
            i9 = i5;
            i8 = 5;
        } else {
            i9 = -1;
        }
        Log.d(TAG, "writePortraitExif: focusPoint: " + focusPoint);
        Log.d(TAG, "writePortraitExif: blurLevel: " + blurLevel);
        Log.d(TAG, "writePortraitExif: shineThreshold: " + i8);
        Log.d(TAG, "writePortraitExif: shineLevel: " + i9);
        Log.d(TAG, "writePortraitExif: lightingPattern: " + i7);
        Log.d(TAG, "writePortraitExif: isMimovie: " + z3);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                ExifInterface exifInterface = new ExifInterface();
                exifInterface.readExif(bArr13);
                exifInterface.addXiaomiDepthmapVersion(i6);
                exifInterface.addDepthMapBlurLevel(blurLevel);
                exifInterface.addPortraitLighting(i7);
                if (z2) {
                    exifInterface.addFrontMirror(z);
                }
                exifInterface.writeExif(bArr13, (OutputStream) byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    $closeResource((Throwable) null, byteArrayOutputStream);
                    bArr5 = byteArray;
                } catch (IOException e) {
                    bArr5 = byteArray;
                    Log.e(TAG, "writePortraitExif(): Failed to write depthmap associated exif metadata");
                    if (bArr5 != null) {
                    }
                    byte[] bArr17 = bArr13;
                    Log.e(TAG, "writePortraitExif(): #1: return original jpeg");
                    return bArr17;
                }
                if (bArr5 != null || bArr5.length <= bArr13.length) {
                    byte[] bArr172 = bArr13;
                    Log.e(TAG, "writePortraitExif(): #1: return original jpeg");
                    return bArr172;
                }
                try {
                    XmlSerializer newSerializer = Xml.newSerializer();
                    StringWriter stringWriter = new StringWriter();
                    newSerializer.setOutput(stringWriter);
                    bArr6 = bArr5;
                    StringWriter stringWriter2 = stringWriter;
                    try {
                        newSerializer.startDocument("UTF-8", true);
                        newSerializer.startTag((String) null, "depthmap");
                        newSerializer.attribute((String) null, "version", String.valueOf(i));
                        newSerializer.attribute((String) null, "focuspoint", focusPoint.x + "," + focusPoint.y);
                        newSerializer.attribute((String) null, "blurlevel", String.valueOf(blurLevel));
                        newSerializer.attribute((String) null, "shinethreshold", String.valueOf(i8));
                        newSerializer.attribute((String) null, "shinelevel", String.valueOf(i9));
                        newSerializer.attribute((String) null, "rawlength", String.valueOf(i3));
                        newSerializer.attribute((String) null, "depthlength", String.valueOf(i4));
                        newSerializer.attribute((String) null, "mimovie", String.valueOf(z3));
                        newSerializer.endTag((String) null, "depthmap");
                        if (bArr16 != null && bArr16.length > 0 && iArr4 != null && iArr4.length >= 4) {
                            newSerializer.startTag((String) null, "subimage");
                            newSerializer.attribute((String) null, "offset", String.valueOf(bArr16.length + (bArr14 != null ? bArr14.length : 0) + (bArr15 != null ? bArr15.length : 0) + i3 + i4));
                            newSerializer.attribute((String) null, "length", String.valueOf(bArr16.length));
                            newSerializer.attribute((String) null, "paddingx", String.valueOf(iArr4[0]));
                            newSerializer.attribute((String) null, "paddingy", String.valueOf(iArr4[1]));
                            newSerializer.attribute((String) null, "width", String.valueOf(iArr4[2]));
                            newSerializer.attribute((String) null, "height", String.valueOf(iArr4[3]));
                            newSerializer.endTag((String) null, "subimage");
                        }
                        if (bArr14 != null && bArr14.length > 0) {
                            newSerializer.startTag((String) null, "lenswatermark");
                            newSerializer.attribute((String) null, "offset", String.valueOf(bArr14.length + (bArr15 != null ? bArr15.length : 0) + i3 + i4));
                            newSerializer.attribute((String) null, "length", String.valueOf(bArr14.length));
                            newSerializer.attribute((String) null, "width", String.valueOf(iArr[0]));
                            newSerializer.attribute((String) null, "height", String.valueOf(iArr[1]));
                            newSerializer.attribute((String) null, "paddingx", String.valueOf(iArr[2]));
                            newSerializer.attribute((String) null, "paddingy", String.valueOf(iArr[3]));
                            newSerializer.endTag((String) null, "lenswatermark");
                        }
                        if (bArr15 != null && bArr15.length > 0) {
                            newSerializer.startTag((String) null, "timewatermark");
                            newSerializer.attribute((String) null, "offset", String.valueOf(bArr15.length + i3 + i4));
                            newSerializer.attribute((String) null, "length", String.valueOf(bArr15.length));
                            newSerializer.attribute((String) null, "width", String.valueOf(iArr2[0]));
                            newSerializer.attribute((String) null, "height", String.valueOf(iArr2[1]));
                            newSerializer.attribute((String) null, "paddingx", String.valueOf(iArr2[2]));
                            newSerializer.attribute((String) null, "paddingy", String.valueOf(iArr2[3]));
                            newSerializer.endTag((String) null, "timewatermark");
                        }
                        newSerializer.endDocument();
                        str = stringWriter2.toString();
                    } catch (IOException e2) {
                        Log.e(TAG, "writePortraitExif(): Failed to generate depthmap associated xmp metadata");
                        str = null;
                        if (str == null) {
                        }
                    }
                } catch (IOException e3) {
                    bArr6 = bArr5;
                    Log.e(TAG, "writePortraitExif(): Failed to generate depthmap associated xmp metadata");
                    str = null;
                    if (str == null) {
                    }
                }
                if (str == null) {
                    Log.e(TAG, "writePortraitExif(): #2: return original jpeg");
                    return bArr;
                }
                byte[] bArr18 = bArr;
                try {
                    bArr7 = bArr6;
                    try {
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr7);
                        try {
                            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                            try {
                                XMPMeta createXMPMeta = XmpHelper.createXMPMeta();
                                createXMPMeta.setProperty(XmpHelper.XIAOMI_XMP_METADATA_NAMESPACE, XmpHelper.XIAOMI_XMP_METADATA_PROPERTY_NAME, str);
                                XmpHelper.writeXMPMeta(byteArrayInputStream, byteArrayOutputStream2, createXMPMeta);
                                if (bArr16 != null) {
                                    try {
                                        if (bArr16.length > 0 && iArr4 != null && iArr4.length >= 4) {
                                            byteArrayOutputStream2.write(bArr16);
                                        }
                                    } catch (Throwable th5) {
                                        th = th5;
                                        bArr11 = null;
                                        th3 = null;
                                        try {
                                            $closeResource(th3, byteArrayOutputStream2);
                                            throw th;
                                        } catch (Throwable th6) {
                                            th = th6;
                                            bArr10 = bArr11;
                                            bArr8 = bArr10;
                                            th = bArr10;
                                            $closeResource(th, byteArrayInputStream);
                                            throw th;
                                        }
                                    }
                                }
                                if (bArr14 != null) {
                                    byteArrayOutputStream2.write(bArr14);
                                }
                                if (bArr15 != null) {
                                    byteArrayOutputStream2.write(bArr15);
                                }
                                byteArrayOutputStream2.flush();
                                bArr8 = byteArrayOutputStream2.toByteArray();
                                th = null;
                            } catch (Throwable th7) {
                                th = th7;
                                bArr11 = null;
                                th3 = null;
                                $closeResource(th3, byteArrayOutputStream2);
                                throw th;
                            }
                            try {
                                $closeResource((Throwable) null, byteArrayOutputStream2);
                                try {
                                    $closeResource((Throwable) null, byteArrayInputStream);
                                } catch (XMPException | IOException e4) {
                                }
                            } catch (Throwable th8) {
                                th = th8;
                                $closeResource(th, byteArrayInputStream);
                                throw th;
                            }
                        } catch (Throwable th9) {
                            th = th9;
                            bArr10 = null;
                            bArr8 = bArr10;
                            th = bArr10;
                            $closeResource(th, byteArrayInputStream);
                            throw th;
                        }
                    } catch (XMPException | IOException e5) {
                        bArr8 = null;
                        Log.d(TAG, "writePortraitExif(): Failed to insert depthmap associated xmp metadata");
                        if (bArr8 == null) {
                        }
                        Log.e(TAG, "writePortraitExif(): #3: return original jpeg");
                        return bArr18;
                    }
                } catch (XMPException | IOException e6) {
                    bArr7 = bArr6;
                    bArr8 = null;
                    Log.d(TAG, "writePortraitExif(): Failed to insert depthmap associated xmp metadata");
                    if (bArr8 == null) {
                    }
                    Log.e(TAG, "writePortraitExif(): #3: return original jpeg");
                    return bArr18;
                }
                if (bArr8 == null && bArr8.length > bArr7.length) {
                    return bArr8;
                }
                Log.e(TAG, "writePortraitExif(): #3: return original jpeg");
                return bArr18;
            } catch (Throwable th10) {
                th = th10;
            }
            $closeResource(th4, byteArrayOutputStream);
            throw th;
        } catch (IOException e7) {
            bArr5 = null;
            Log.e(TAG, "writePortraitExif(): Failed to write depthmap associated exif metadata");
            if (bArr5 != null) {
            }
            byte[] bArr1722 = bArr13;
            Log.e(TAG, "writePortraitExif(): #1: return original jpeg");
            return bArr1722;
        }
    }
}
