package com.xiaomi.camera.parallelservice.util;

import com.android.camera.log.Log;
import com.android.gallery3d.exif.ExifInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ParallelExifUtil {
    private static final String TAG = ParallelExifUtil.class.getSimpleName();

    /* JADX WARNING: Removed duplicated region for block: B:27:0x009d A[SYNTHETIC, Splitter:B:27:0x009d] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00a5 A[Catch:{ IOException -> 0x00a1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00b7 A[SYNTHETIC, Splitter:B:36:0x00b7] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00bf A[Catch:{ IOException -> 0x00bb }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    public static void updateExif(String str) {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        Exception e;
        FileOutputStream fileOutputStream2;
        Log.v(TAG, "updateExif path:" + str);
        File file = new File(str);
        if (!file.exists()) {
            Log.e(TAG, "updateExif path not exist. " + str);
            return;
        }
        File file2 = new File(str + ".tmp");
        try {
            file2.createNewFile();
            fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream2 = new FileOutputStream(file2);
                try {
                    byte[] bArr = new byte[((int) file.length())];
                    fileInputStream.read(bArr);
                    fileOutputStream2.write(ExifInterface.removeParallelProcessComment(bArr));
                    fileOutputStream2.close();
                    file.delete();
                    file2.renameTo(file);
                    try {
                        fileInputStream.close();
                        fileOutputStream2.close();
                    } catch (IOException e2) {
                        Log.e(TAG, "close image file failed!", e2);
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (Exception e4) {
                e = e4;
                fileOutputStream2 = null;
                e = e;
                try {
                    Log.e(TAG, "process exif failed!", e);
                    if (fileInputStream != null) {
                    }
                    if (fileOutputStream == null) {
                    }
                } catch (Throwable th) {
                    th = th;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e5) {
                            Log.e(TAG, "close image file failed!", e5);
                            throw th;
                        }
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = null;
                th = th;
                if (fileInputStream != null) {
                }
                if (fileOutputStream != null) {
                }
                throw th;
            }
        } catch (Exception e6) {
            e = e6;
            fileInputStream = null;
            fileOutputStream2 = null;
            e = e;
            Log.e(TAG, "process exif failed!", e);
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (fileOutputStream == null) {
                fileOutputStream.close();
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
            fileOutputStream = null;
            th = th;
            if (fileInputStream != null) {
            }
            if (fileOutputStream != null) {
            }
            throw th;
        }
    }
}
