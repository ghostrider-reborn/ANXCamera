package com.android.camera;

import com.android.camera.log.Log;

public class Exif {
    private static final String TAG = "CameraExif";

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0066, code lost:
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0067, code lost:
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0068, code lost:
        if (r2 <= 8) goto L_0x00d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x006a, code lost:
        r3 = pack(r11, r1, 4, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0071, code lost:
        if (r3 == 1229531648) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0076, code lost:
        if (r3 == 1296891946) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0078, code lost:
        com.android.camera.log.Log.e(TAG, "Invalid byte order");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x007d, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x007e, code lost:
        if (r3 != 1229531648) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0080, code lost:
        r3 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0082, code lost:
        r3 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0083, code lost:
        r4 = pack(r11, r1 + 4, 4, r3) + 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x008c, code lost:
        if (r4 < 10) goto L_0x00ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x008e, code lost:
        if (r4 <= r2) goto L_0x0091;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0091, code lost:
        r1 = r1 + r4;
        r2 = r2 - r4;
        r4 = pack(r11, r1 - 2, 2, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0099, code lost:
        r9 = r4 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x009b, code lost:
        if (r4 <= 0) goto L_0x00d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x009f, code lost:
        if (r2 < 12) goto L_0x00d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00a7, code lost:
        if (pack(r11, r1, 2, r3) != 274) goto L_0x00c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00a9, code lost:
        r11 = pack(r11, r1 + 8, 2, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00ae, code lost:
        if (r11 == 1) goto L_0x00c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00b1, code lost:
        if (r11 == 3) goto L_0x00c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00b4, code lost:
        if (r11 == 6) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00b6, code lost:
        if (r11 == 8) goto L_0x00be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00b8, code lost:
        com.android.camera.log.Log.i(TAG, "Unsupported orientation");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00bd, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00be, code lost:
        return 270;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00c1, code lost:
        return 90;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00c4, code lost:
        return 180;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00c7, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00c8, code lost:
        r1 = r1 + 12;
        r2 = r2 - 12;
        r4 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00ce, code lost:
        com.android.camera.log.Log.e(TAG, "Invalid offset");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x00d3, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x00d4, code lost:
        com.android.camera.log.Log.i(TAG, "Orientation not found");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x00d9, code lost:
        return 0;
     */
    public static int getOrientation(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        int i = 0;
        while (true) {
            if (i + 3 >= bArr.length) {
                break;
            }
            int i2 = i + 1;
            if ((bArr[i] & 255) != 255) {
                break;
            }
            byte b2 = bArr[i2] & 255;
            if (b2 != 255) {
                i2++;
                if (!(b2 == 216 || b2 == 1)) {
                    if (b2 != 217 && b2 != 218) {
                        int pack = pack(bArr, i2, 2, false);
                        if (pack < 2) {
                            break;
                        }
                        int i3 = i2 + pack;
                        if (i3 <= bArr.length) {
                            if (b2 == 225 && pack >= 8 && pack(bArr, i2 + 2, 4, false) == 1165519206 && pack(bArr, i2 + 6, 2, false) == 0) {
                                i = i2 + 8;
                                int i4 = pack - 8;
                                break;
                            }
                            i = i3;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            i = i2;
        }
        Log.e(TAG, "Invalid length");
        return 0;
    }

    private static int pack(byte[] bArr, int i, int i2, boolean z) {
        int i3;
        if (z) {
            i += i2 - 1;
            i3 = -1;
        } else {
            i3 = 1;
        }
        byte b2 = 0;
        while (true) {
            int i4 = i2 - 1;
            if (i2 <= 0) {
                return b2;
            }
            b2 = (bArr[i] & 255) | (b2 << 8);
            i += i3;
            i2 = i4;
        }
    }
}
