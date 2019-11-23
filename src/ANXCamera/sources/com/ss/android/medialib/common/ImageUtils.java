package com.ss.android.medialib.common;

import android.graphics.Bitmap;
import com.android.camera.storage.Storage;
import com.ss.android.medialib.FileUtils;
import com.ss.android.vesdk.VELogUtil;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {
    private static final String TAG = ImageUtils.class.getSimpleName();

    public static void saveBitmap(Bitmap bitmap) {
        saveBitmap(bitmap, System.currentTimeMillis() + Storage.JPEG_SUFFIX);
    }

    public static void saveBitmap(Bitmap bitmap, String str) {
        VELogUtil.i(TAG, "saving Bitmap : " + str);
        saveBitmapWithPath(bitmap, FileUtils.getPath() + "/" + str, Bitmap.CompressFormat.JPEG);
        VELogUtil.i(TAG, "Bitmap " + str + " saved!");
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x007b A[SYNTHETIC, Splitter:B:30:0x007b] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0087 A[SYNTHETIC, Splitter:B:35:0x0087] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0095 A[SYNTHETIC, Splitter:B:41:0x0095] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00a1 A[SYNTHETIC, Splitter:B:46:0x00a1] */
    /* JADX WARNING: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    public static void saveBitmapWithPath(Bitmap bitmap, String str, Bitmap.CompressFormat compressFormat) {
        FileOutputStream fileOutputStream;
        VELogUtil.i(TAG, "Bitmap " + str + "saving");
        BufferedOutputStream bufferedOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(str);
            try {
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(fileOutputStream);
                try {
                    bitmap.compress(compressFormat, 100, bufferedOutputStream2);
                    bufferedOutputStream2.flush();
                    try {
                        bufferedOutputStream2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fileOutputStream.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    VELogUtil.i(TAG, "Bitmap " + str + " saved!");
                } catch (IOException e3) {
                    e = e3;
                    bufferedOutputStream = bufferedOutputStream2;
                } catch (Throwable th) {
                    th = th;
                    bufferedOutputStream = bufferedOutputStream2;
                    if (bufferedOutputStream != null) {
                    }
                    if (fileOutputStream != null) {
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e = e4;
                try {
                    VELogUtil.e(TAG, "Err when saving bitmap...");
                    e.printStackTrace();
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (fileOutputStream == null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException e7) {
                            e7.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e8) {
                            e8.printStackTrace();
                        }
                    }
                    throw th;
                }
            }
        } catch (IOException e9) {
            e = e9;
            fileOutputStream = null;
            VELogUtil.e(TAG, "Err when saving bitmap...");
            e.printStackTrace();
            if (bufferedOutputStream != null) {
            }
            if (fileOutputStream == null) {
            }
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
            if (bufferedOutputStream != null) {
            }
            if (fileOutputStream != null) {
            }
            throw th;
        }
    }
}
