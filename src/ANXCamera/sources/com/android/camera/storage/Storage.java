package com.android.camera.storage;

import android.app.usage.StorageStatsManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.DngCreator;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StatFs;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.support.v4.app.FrameMetricsAggregator;
import android.text.TextUtils;
import android.util.Size;
import com.android.camera.ActivityBase;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.ExifHelper;
import com.android.camera.FileCompat;
import com.android.camera.LocationManager;
import com.android.camera.R;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.gallery3d.exif.ExifInterface;
import com.mi.config.b;
import com.ss.android.ttve.common.TEDefine;
import com.xiaomi.camera.core.PictureInfo;
import com.xiaomi.camera.parallelservice.util.ParallelUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;
import miui.os.Build;
import miui.reflect.Method;

public class Storage {
    public static final String AVOID_SCAN_FILE_NAME = ".nomedia";
    public static int BUCKET_ID = DIRECTORY.toLowerCase(Locale.ENGLISH).hashCode();
    private static final String CAMERA_STORAGE_PATH_SUFFIX = "/DCIM/Camera";
    private static final String CAMERA_STORAGE_PATH_TEMP = "/DCIM/Camera/.temp";
    public static String CAMERA_TEMP_DIRECTORY = (FIRST_CONSIDER_STORAGE_PATH + CAMERA_STORAGE_PATH_TEMP);
    public static String DIRECTORY = (FIRST_CONSIDER_STORAGE_PATH + CAMERA_STORAGE_PATH_SUFFIX);
    public static String FIRST_CONSIDER_STORAGE_PATH = (b.rB ? SECONDARY_STORAGE_PATH : PRIMARY_STORAGE_PATH);
    public static String HIDEDIRECTORY = (FIRST_CONSIDER_STORAGE_PATH + HIDE_CAMERA_STORAGE_PATH_SUFFIX);
    private static final String HIDE_CAMERA_STORAGE_PATH_SUFFIX = "/DCIM/Camera/.ubifocus";
    public static final String HSR_120_SUFFIX = "_HSR_120";
    public static final String HSR_240_SUFFIX = "_HSR_240";
    public static final String JPEG_SUFFIX = ".jpg";
    private static final AtomicLong LEFT_SPACE = new AtomicLong(0);
    public static final String LIVE_SHOT_PREFIX = "MV";
    public static final long LOW_STORAGE_THRESHOLD = 52428800;
    private static final int MAX_WRITE_RETRY = (Build.IS_ALPHA_BUILD ? 1 : 3);
    protected static final String PARALLEL_PROCESS_EXIF_PROCESS_TAG = "processing";
    public static final long PREPARING = -2;
    public static int PRIMARY_BUCKET_ID = (PRIMARY_STORAGE_PATH + CAMERA_STORAGE_PATH_SUFFIX).toLowerCase(Locale.ENGLISH).hashCode();
    public static int PRIMARY_RAW_BUCKET_ID = (PRIMARY_STORAGE_PATH + CAMERA_STORAGE_PATH_SUFFIX + RAW_PATH_SUFFIX).toLowerCase(Locale.ENGLISH).hashCode();
    private static final String PRIMARY_STORAGE_PATH = Environment.getExternalStorageDirectory().toString();
    public static final float QUOTA_RATIO = 0.9f;
    public static String RAW_DIRECTORY = (DIRECTORY + RAW_PATH_SUFFIX);
    private static final String RAW_PATH_SUFFIX = "/Raw";
    public static final String RAW_SUFFIX = ".dng";
    private static final String SAVE_TO_CLOUD_ALBUM_ACTION = "com.miui.gallery.SAVE_TO_CLOUD";
    private static final String SAVE_TO_CLOUD_ALBUM_CACHE_LOCATION_KEY = "extra_cache_location";
    private static final String SAVE_TO_CLOUD_ALBUM_FILE_LENGTH = "extra_file_length";
    private static final String SAVE_TO_CLOUD_ALBUM_PACKAGE = "com.miui.gallery";
    private static final String SAVE_TO_CLOUD_ALBUM_PATH_KAY = "extra_file_path";
    private static final String SAVE_TO_CLOUD_ALBUM_STORE_ID_KAY = "extra_media_store_id";
    private static final String SAVE_TO_CLOUD_ALBUM_TEMP_FILE_KAY = "extra_is_temp_file";
    public static int SECONDARY_BUCKET_ID = (SECONDARY_STORAGE_PATH + CAMERA_STORAGE_PATH_SUFFIX).toLowerCase(Locale.ENGLISH).hashCode();
    public static int SECONDARY_RAW_BUCKET_ID = (SECONDARY_STORAGE_PATH + CAMERA_STORAGE_PATH_SUFFIX + RAW_PATH_SUFFIX).toLowerCase(Locale.ENGLISH).hashCode();
    private static String SECONDARY_STORAGE_PATH = System.getenv("SECONDARY_STORAGE");
    private static final String TAG = Storage.class.getSimpleName();
    public static final String UBIFOCUS_SUFFIX = "_UBIFOCUS_";
    public static final long UNAVAILABLE = -1;
    public static final long UNKNOWN_SIZE = -3;
    private static String sCurrentStoragePath = FIRST_CONSIDER_STORAGE_PATH;
    private static long sQuotaBytes;
    private static boolean sQuotaSupported;
    private static long sReserveBytes;
    private static WeakReference<StorageListener> sStorageListener;

    public interface StorageListener {
        void onStoragePathChanged();
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

    static {
        File file = new File(DIRECTORY + File.separator + AVOID_SCAN_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    public static Uri addDNGToDataBase(Context context, String str, long j, int i, int i2, int i3) {
        String name = new File(str).getName();
        String substring = name.substring(0, name.lastIndexOf(46) - 1);
        ContentValues contentValues = new ContentValues(7);
        contentValues.put("title", substring);
        contentValues.put("_display_name", name);
        contentValues.put("media_type", 1);
        contentValues.put("datetaken", Long.valueOf(j));
        contentValues.put("mime_type", "image/x-adobe-dng");
        contentValues.put("_data", str);
        contentValues.put("width", Integer.valueOf(i));
        contentValues.put("height", Integer.valueOf(i2));
        contentValues.put("orientation", Integer.valueOf(i3));
        try {
            return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } catch (Exception e) {
            String str2 = TAG;
            Log.e(str2, "Failed to write MediaStore, path " + str, e);
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:122:0x018e, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x0190, code lost:
        r0 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x018a A[SYNTHETIC, Splitter:B:120:0x018a] */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0190 A[Catch:{ Throwable -> 0x0190, all -> 0x018e }, ExcHandler: Throwable (th java.lang.Throwable), PHI: r8 r21 r22 
  PHI: (r8v20 boolean) = (r8v22 boolean), (r8v29 boolean), (r8v29 boolean) binds: [B:120:0x018a, B:93:0x015c, B:94:?] A[DONT_GENERATE, DONT_INLINE]
  PHI: (r21v11 boolean) = (r21v13 boolean), (r21v22 boolean), (r21v22 boolean) binds: [B:120:0x018a, B:93:0x015c, B:94:?] A[DONT_GENERATE, DONT_INLINE]
  PHI: (r22v11 byte[]) = (r22v13 byte[]), (r22v22 byte[]), (r22v22 byte[]) binds: [B:120:0x018a, B:93:0x015c, B:94:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:93:0x015c] */
    /* JADX WARNING: Removed duplicated region for block: B:161:0x01ee A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x01f0  */
    /* JADX WARNING: Removed duplicated region for block: B:201:0x02f0 A[LOOP:0: B:1:0x005e->B:201:0x02f0, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:204:0x0192 A[EDGE_INSN: B:204:0x0192->B:126:0x0192 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:205:0x01eb A[SYNTHETIC] */
    public static Uri addImage(Context context, String str, long j, Location location, int i, byte[] bArr, int i2, int i3, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, String str2, PictureInfo pictureInfo) {
        byte[] bArr2;
        boolean z6;
        boolean z7;
        byte[] bArr3;
        int i4;
        boolean z8;
        String str3;
        String str4;
        String str5;
        String str6;
        boolean z9;
        Throwable th;
        OutputStream fileOutputStream;
        Throwable th2;
        Throwable th3;
        ParcelFileDescriptor parcelFileDescriptor;
        Throwable th4;
        final Context context2 = context;
        long j2 = j;
        Location location2 = location;
        int i5 = i;
        boolean z10 = z3;
        String str7 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("addImage: parallel=");
        boolean z11 = z5;
        sb.append(z11);
        sb.append(" appendExif=");
        sb.append(z4);
        Log.d(str7, sb.toString());
        byte[] updateExif = updateExif(bArr, z11, str2, pictureInfo, i5, i2, i3);
        String generateFilepath4Jpeg = generateFilepath4Jpeg(str, z2, z10);
        Log.d(TAG, "addImage: path=" + generateFilepath4Jpeg);
        boolean z12 = z4;
        int i6 = 0;
        while (true) {
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(updateExif));
                try {
                    if (isUseDocumentMode()) {
                        try {
                            fileOutputStream = FileCompat.getFileOutputStream(generateFilepath4Jpeg, true);
                        } catch (Throwable th5) {
                            th = th5;
                            bArr2 = updateExif;
                            th = null;
                            try {
                                $closeResource(th, bufferedInputStream);
                                throw th;
                            } catch (Exception e) {
                                e = e;
                            }
                        }
                    } else {
                        fileOutputStream = new FileOutputStream(generateFilepath4Jpeg);
                    }
                    OutputStream outputStream = fileOutputStream;
                    try {
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                        if (!z) {
                            z9 = z12;
                            bArr2 = updateExif;
                            byte[] bArr4 = new byte[4096];
                            while (true) {
                                int read = bufferedInputStream.read(bArr4);
                                if (read == -1) {
                                    break;
                                }
                                bufferedOutputStream.write(bArr4, 0, read);
                            }
                        } else {
                            try {
                                boolean z13 = i5 % 180 == 0;
                                z9 = z12;
                                try {
                                    Bitmap flipJpeg = flipJpeg(updateExif, z13, !z13);
                                    if (flipJpeg == null) {
                                        bArr2 = updateExif;
                                        byte[] bArr5 = new byte[4096];
                                        while (true) {
                                            int read2 = bufferedInputStream.read(bArr5);
                                            if (read2 == -1) {
                                                break;
                                            }
                                            bufferedOutputStream.write(bArr5, 0, read2);
                                        }
                                    } else {
                                        ExifInterface exif = Util.getExif(updateExif);
                                        byte[] thumbnailBytes = exif.getThumbnailBytes();
                                        if (thumbnailBytes != null) {
                                            bArr2 = updateExif;
                                            try {
                                                Bitmap flipJpeg2 = flipJpeg(thumbnailBytes, z13, !z13);
                                                if (flipJpeg2 != null) {
                                                    exif.setCompressedThumbnail(flipJpeg2);
                                                    flipJpeg2.recycle();
                                                }
                                                z9 = false;
                                            } catch (Throwable th6) {
                                                th = th6;
                                                z12 = z9;
                                                th3 = null;
                                                $closeResource(th3, bufferedOutputStream);
                                                throw th;
                                            }
                                        } else {
                                            bArr2 = updateExif;
                                        }
                                        exif.writeExif(flipJpeg, (OutputStream) bufferedOutputStream);
                                        flipJpeg.recycle();
                                    }
                                } catch (Throwable th7) {
                                    th = th7;
                                    bArr2 = updateExif;
                                    z12 = z9;
                                    th3 = null;
                                    $closeResource(th3, bufferedOutputStream);
                                    throw th;
                                }
                            } catch (Throwable th8) {
                                th = th8;
                                z9 = z12;
                                bArr2 = updateExif;
                                th3 = null;
                                $closeResource(th3, bufferedOutputStream);
                                throw th;
                            }
                        }
                        z12 = z9;
                        if (z12) {
                            try {
                                bufferedOutputStream.flush();
                                if (!isUseDocumentMode()) {
                                    ExifHelper.writeExifByFilePath(generateFilepath4Jpeg, i5, location2, j2);
                                } else {
                                    try {
                                        parcelFileDescriptor = FileCompat.getParcelFileDescriptor(generateFilepath4Jpeg, false);
                                        try {
                                            ExifHelper.writeExifByFd(parcelFileDescriptor.getFileDescriptor(), i5, location2, j2);
                                            if (parcelFileDescriptor != null) {
                                                $closeResource((Throwable) null, parcelFileDescriptor);
                                            }
                                        } catch (Throwable th9) {
                                            th = th9;
                                        }
                                    } catch (Exception e2) {
                                        Log.e(TAG, "write exif failed, file is " + generateFilepath4Jpeg, e2);
                                    }
                                }
                            } catch (Throwable th10) {
                                th = th10;
                                th3 = null;
                                $closeResource(th3, bufferedOutputStream);
                                throw th;
                            }
                        }
                        try {
                            $closeResource((Throwable) null, bufferedOutputStream);
                            if (outputStream != null) {
                                try {
                                    $closeResource((Throwable) null, outputStream);
                                } catch (Throwable th11) {
                                }
                            }
                            $closeResource((Throwable) null, bufferedInputStream);
                            z6 = false;
                            break;
                        } catch (Throwable th12) {
                            th = th12;
                            th2 = null;
                            if (outputStream != null) {
                                break;
                            }
                            $closeResource(th2, outputStream);
                            break;
                            throw th;
                        }
                    } catch (Throwable th13) {
                        th = th13;
                        z9 = z12;
                        bArr2 = updateExif;
                        th2 = null;
                        if (outputStream != null) {
                        }
                        throw th;
                    }
                } catch (Throwable th14) {
                    th = th14;
                    z9 = z12;
                    bArr2 = updateExif;
                    th = null;
                    $closeResource(th, bufferedInputStream);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                z9 = z12;
                bArr2 = updateExif;
            }
            int i7 = i2;
            context2 = context;
            String str8 = str;
            boolean z14 = z2;
            updateExif = bArr2;
        }
        if (!z6) {
            return null;
        }
        if (z10) {
            bArr3 = bArr2;
            boolean isProduceFocusInfoSuccess = Util.isProduceFocusInfoSuccess(bArr3);
            i4 = i2;
            int centerFocusDepthIndex = Util.getCenterFocusDepthIndex(bArr3, i4, i3);
            if (isProduceFocusInfoSuccess) {
                str6 = "_";
                str5 = str;
            } else {
                str5 = str;
                str6 = UBIFOCUS_SUFFIX;
            }
            str3 = str5.substring(0, str5.lastIndexOf(str6));
            String generateFilepath4Jpeg2 = generateFilepath4Jpeg(str3, false, false);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str3);
            sb2.append(isProduceFocusInfoSuccess ? "_" : UBIFOCUS_SUFFIX);
            sb2.append(centerFocusDepthIndex);
            z8 = z2;
            String generateFilepath4Jpeg3 = generateFilepath4Jpeg(sb2.toString(), z8, false);
            if (generateFilepath4Jpeg2 == null || generateFilepath4Jpeg3 == null) {
                String str9 = TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("oldPath: ");
                if (generateFilepath4Jpeg3 == null) {
                    generateFilepath4Jpeg3 = TEDefine.FACE_BEAUTY_NULL;
                }
                sb3.append(generateFilepath4Jpeg3);
                sb3.append(" newPath: ");
                sb3.append(generateFilepath4Jpeg2 == null ? TEDefine.FACE_BEAUTY_NULL : generateFilepath4Jpeg2);
                Log.e(str9, sb3.toString());
            } else {
                new File(generateFilepath4Jpeg3).renameTo(new File(generateFilepath4Jpeg2));
            }
            if (!isProduceFocusInfoSuccess) {
                deleteImage(str3);
            }
            str4 = generateFilepath4Jpeg2;
            z7 = false;
        } else {
            i4 = i2;
            bArr3 = bArr2;
            z8 = z2;
            z7 = false;
            str4 = generateFilepath4Jpeg;
            str3 = str;
        }
        if (z8 && !z10) {
            return null;
        }
        byte[] bArr6 = bArr3;
        Location location3 = location2;
        Uri insertToMediaStore = insertToMediaStore(context2, str3, str3 + JPEG_SUFFIX, j2, "image/jpeg", i5, str4, new File(str4).length(), i4, i3, location2, z5);
        if (insertToMediaStore == null) {
            Log.e(TAG, "failed to insert to DB: " + str4);
            return null;
        }
        saveToCloudAlbum(context, str4, (long) bArr6.length, z5, ContentUris.parseId(insertToMediaStore), location3 == null ? true : z7);
        return insertToMediaStore;
        if (i6 < MAX_WRITE_RETRY) {
            z6 = true;
            if (!z6) {
            }
        } else {
            int i72 = i2;
            context2 = context;
            String str82 = str;
            boolean z142 = z2;
            updateExif = bArr2;
        }
        dumpExceptionEnv(e, generateFilepath4Jpeg);
        Log.e(TAG, "Failed to write image", e);
        i6++;
        if (Util.isQuotaExceeded(e) && (context2 instanceof ActivityBase)) {
            ActivityBase activityBase = (ActivityBase) context2;
            if (!activityBase.isActivityPaused()) {
                activityBase.runOnUiThread(new Runnable() {
                    public void run() {
                        ToastUtils.showToast(context2, (int) R.string.spaceIsLow_content_primary_storage_priority);
                    }
                });
            }
            i6 = MAX_WRITE_RETRY;
        } else if (i6 < MAX_WRITE_RETRY) {
            System.gc();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e4) {
            }
        }
        if (i6 < MAX_WRITE_RETRY) {
        }
        if (parcelFileDescriptor == null) {
            break;
        }
        $closeResource(th4, parcelFileDescriptor);
        break;
        throw th;
        th = th;
        throw th;
    }

    public static Uri addImageForEffect(Context context, String str, long j, Location location, int i, byte[] bArr, int i2, int i3, boolean z, boolean z2, String str2, PictureInfo pictureInfo) {
        return addImage(context, str, j, location, i, bArr, i2, i3, z, false, false, false, z2, str2, pictureInfo);
    }

    public static Uri addImageForGroupOrPanorama(Context context, String str, int i, long j, Location location, int i2, int i3) {
        File file;
        Context context2 = context;
        String str2 = str;
        if (context2 == null || str2 == null) {
            return null;
        }
        try {
            file = new File(str2);
        } catch (Exception e) {
            String str3 = TAG;
            Log.e(str3, "Failed to open panorama file: " + e.getMessage(), e);
            file = null;
        }
        if (file == null || !file.exists()) {
            return null;
        }
        String name = file.getName();
        String str4 = str2;
        Uri insertToMediaStore = insertToMediaStore(context2, name, name, j, "image/jpeg", i, str2, file.length(), i2, i3, location, false);
        saveToCloudAlbum(context, str4, -1, location == null);
        return insertToMediaStore;
    }

    public static Uri addImageForSnapCamera(Context context, String str, long j, Location location, int i, byte[] bArr, int i2, int i3, boolean z, boolean z2, boolean z3, String str2, PictureInfo pictureInfo) {
        return addImage(context, str, j, location, i, bArr, i2, i3, z, z2, z3, false, false, str2, pictureInfo);
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:24:0x0099=Splitter:B:24:0x0099, B:57:0x00bf=Splitter:B:57:0x00bf} */
    public static Uri addRawImage(Context context, String str, CameraCharacteristics cameraCharacteristics, CaptureResult captureResult, byte[] bArr, long j, int i, int i2, int i3) {
        Throwable th;
        Throwable th2;
        OutputStream fileOutputStream;
        Throwable th3;
        BufferedOutputStream bufferedOutputStream;
        Throwable th4;
        String generateRawFilepath = generateRawFilepath(str);
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        try {
            DngCreator dngCreator = new DngCreator(cameraCharacteristics, captureResult);
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(bArr));
                try {
                    boolean z = true;
                    fileOutputStream = isUseDocumentMode() ? FileCompat.getFileOutputStream(generateRawFilepath, true) : new FileOutputStream(generateRawFilepath);
                    try {
                        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                        try {
                            dngCreator.setOrientation(Integer.parseInt(ExifHelper.getExifOrientation(i3)));
                            int i4 = i;
                            int i5 = i2;
                            dngCreator.writeInputStream(bufferedOutputStream, new Size(i4, i5), bufferedInputStream, 0);
                            Uri addDNGToDataBase = addDNGToDataBase(context, generateRawFilepath, j, i4, i5, i3);
                            Log.d(TAG, "addRawImage path " + generateRawFilepath + ", uri = " + addDNGToDataBase);
                            if (currentLocation != null) {
                                z = false;
                            }
                            saveToCloudAlbum(context, generateRawFilepath, -1, z);
                            $closeResource((Throwable) null, bufferedOutputStream);
                            if (fileOutputStream != null) {
                                $closeResource((Throwable) null, fileOutputStream);
                            }
                            $closeResource((Throwable) null, bufferedInputStream);
                            $closeResource((Throwable) null, dngCreator);
                            return addDNGToDataBase;
                        } catch (Throwable th5) {
                            th = th5;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                    }
                } catch (Throwable th7) {
                    th = th7;
                }
                if (fileOutputStream != null) {
                    $closeResource(th3, fileOutputStream);
                }
                throw th;
                $closeResource(th4, bufferedOutputStream);
                throw th;
                $closeResource(th, dngCreator);
                throw th;
                $closeResource(th2, bufferedInputStream);
                throw th;
            } catch (Throwable th8) {
                th = th8;
            }
        } catch (Throwable th9) {
            Log.w(TAG, "addRawImage failed, path " + generateRawFilepath, th9);
            return null;
        }
    }

    public static void deleteImage(String str) {
        File file = new File(HIDEDIRECTORY);
        if (file.exists() && file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                if (file2.getName().indexOf(str) != -1) {
                    file2.delete();
                }
            }
        }
    }

    private static boolean deleteSdcardFile(String str) {
        boolean deleteFile;
        int i = 0;
        do {
            i++;
            deleteFile = FileCompat.deleteFile(str);
            if (deleteFile) {
                break;
            }
        } while (i < 5);
        return deleteFile;
    }

    private static void dumpExceptionEnv(Exception exc, String str) {
        boolean z;
        if (exc instanceof FileNotFoundException) {
            long maxMemory = Runtime.getRuntime().maxMemory();
            long j = Runtime.getRuntime().totalMemory();
            long freeMemory = Runtime.getRuntime().freeMemory();
            File file = new File(str);
            try {
                File file2 = new File(str + ".ex");
                z = file2.createNewFile();
                file2.delete();
            } catch (IOException e) {
                z = false;
            }
            String str2 = TAG;
            Locale locale = Locale.ENGLISH;
            Object[] objArr = new Object[7];
            objArr[0] = Long.valueOf(maxMemory);
            objArr[1] = Long.valueOf(j);
            objArr[2] = Long.valueOf(freeMemory);
            objArr[3] = file.exists() ? "exists" : "not exists";
            objArr[4] = file.isDirectory() ? "isDirectory" : "isNotDirectory";
            objArr[5] = file.canWrite() ? "canWrite" : "canNotWrite";
            objArr[6] = z ? "testFileCanWrite" : "testFileCannotWrite";
            Log.e(str2, String.format(locale, "Failed to write image, memory state(max:%d, total:%d, free:%d), file state(%s;%s;%s;%s)", objArr), exc);
        }
    }

    public static Bitmap flipJpeg(byte[] bArr, boolean z, boolean z2) {
        if (bArr == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        Matrix matrix = new Matrix();
        float f = 1.0f;
        float f2 = z ? -1.0f : 1.0f;
        if (z2) {
            f = -1.0f;
        }
        matrix.setScale(f2, f, ((float) decodeByteArray.getWidth()) * 0.5f, ((float) decodeByteArray.getHeight()) * 0.5f);
        try {
            Bitmap createBitmap = Bitmap.createBitmap(decodeByteArray, 0, 0, decodeByteArray.getWidth(), decodeByteArray.getHeight(), matrix, true);
            if (createBitmap != decodeByteArray) {
                decodeByteArray.recycle();
            }
            if (createBitmap.getWidth() == -1 || createBitmap.getHeight() == -1) {
                return null;
            }
            return createBitmap;
        } catch (Exception e) {
            Log.w(TAG, "Failed to rotate thumbnail", e);
            return null;
        }
    }

    public static String generateFilepath(String str) {
        return DIRECTORY + '/' + str;
    }

    public static String generateFilepath(String str, String str2) {
        return DIRECTORY + '/' + str + str2;
    }

    public static String generateFilepath4Jpeg(String str) {
        return generateFilepath(str, JPEG_SUFFIX);
    }

    public static String generateFilepath4Jpeg(String str, boolean z, boolean z2) {
        if (z && isLowStorageSpace(HIDEDIRECTORY)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(z ? HIDEDIRECTORY : DIRECTORY);
        sb.append('/');
        sb.append(str);
        sb.append(z2 ? ".y" : JPEG_SUFFIX);
        return sb.toString();
    }

    public static String generatePrimaryDirectoryPath() {
        return PRIMARY_STORAGE_PATH + CAMERA_STORAGE_PATH_SUFFIX;
    }

    public static String generatePrimaryFilepath(String str) {
        return generatePrimaryDirectoryPath() + '/' + str;
    }

    public static String generatePrimaryTempFile() {
        return PRIMARY_STORAGE_PATH + CAMERA_STORAGE_PATH_TEMP;
    }

    public static String generateRawFilepath(String str) {
        return RAW_DIRECTORY + "/" + str + RAW_SUFFIX;
    }

    public static String generateTempFilepath() {
        return DIRECTORY + "/.temp";
    }

    public static long getAvailableSpace() {
        return getAvailableSpace(DIRECTORY);
    }

    public static long getAvailableSpace(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.w(TAG, "getAvailableSpace path is empty");
            return -1;
        }
        File file = new File(str);
        boolean mkdirs = isUseDocumentMode() ? FileCompat.mkdirs(str) : Util.mkdirs(file, FrameMetricsAggregator.EVERY_DURATION, -1, -1);
        if (!file.exists() || !file.isDirectory()) {
            Log.w(TAG, "getAvailableSpace path = " + str + ", exists = " + file.exists() + ", isDirectory = " + file.isDirectory() + ", canWrite = " + file.canWrite());
            return -1;
        }
        if (mkdirs && str.endsWith(CAMERA_STORAGE_PATH_SUFFIX)) {
            if (MediaProviderUtil.insertCameraDirectory(CameraAppImpl.getAndroidContext(), str) != null) {
                Log.d(TAG, "insertDirectory success, path " + str);
            } else {
                Log.w(TAG, "insertDirectory fail, path " + str);
            }
        }
        try {
            if (HIDEDIRECTORY.equals(str)) {
                Util.createFile(new File(HIDEDIRECTORY + File.separator + AVOID_SCAN_FILE_NAME));
            }
            long availableBytes = new StatFs(str).getAvailableBytes();
            if (DIRECTORY.equals(str)) {
                if (isUsePhoneStorage() && isQuotaSupported() && availableBytes < sReserveBytes) {
                    Context androidContext = CameraAppImpl.getAndroidContext();
                    ApplicationInfo applicationInfo = androidContext.getApplicationInfo();
                    try {
                        long totalBytes = sQuotaBytes - ((StorageStatsManager) androidContext.getSystemService(StorageStatsManager.class)).queryExternalStatsForUser(applicationInfo.storageUuid, UserHandle.getUserHandleForUid(applicationInfo.uid)).getTotalBytes();
                        if (totalBytes < 0) {
                            totalBytes = 0;
                        }
                        if (totalBytes < availableBytes) {
                            availableBytes = totalBytes;
                        }
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }
                setLeftSpace(availableBytes);
            }
            return availableBytes;
        } catch (Exception e2) {
            Log.i(TAG, "Fail to access external storage", e2);
            return -3;
        }
    }

    public static long getLeftSpace() {
        long j = LEFT_SPACE.get();
        String str = TAG;
        Log.i(str, "getLeftSpace() return " + j);
        return j;
    }

    private static Intent getSaveToCloudIntent(Context context, String str, long j, boolean z, long j2, boolean z2) {
        Intent intent = new Intent(SAVE_TO_CLOUD_ALBUM_ACTION);
        intent.setPackage("com.miui.gallery");
        List<ResolveInfo> queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
        if (queryBroadcastReceivers != null && queryBroadcastReceivers.size() > 0) {
            intent.setComponent(new ComponentName("com.miui.gallery", queryBroadcastReceivers.get(0).activityInfo.name));
        }
        intent.putExtra(SAVE_TO_CLOUD_ALBUM_PATH_KAY, str);
        intent.putExtra(SAVE_TO_CLOUD_ALBUM_FILE_LENGTH, j);
        if (z) {
            intent.putExtra(SAVE_TO_CLOUD_ALBUM_TEMP_FILE_KAY, true);
            intent.putExtra(SAVE_TO_CLOUD_ALBUM_STORE_ID_KAY, j2);
        } else {
            intent.putExtra(SAVE_TO_CLOUD_ALBUM_TEMP_FILE_KAY, false);
        }
        if (z2) {
            intent.putExtra(SAVE_TO_CLOUD_ALBUM_CACHE_LOCATION_KEY, LocationManager.instance().getLastKnownLocation());
            Log.d(TAG, "broadcast last location to gallery");
        }
        return intent;
    }

    public static boolean hasSecondaryStorage() {
        return Build.VERSION.SDK_INT == 28 ? UserHandle.myUserId() == 0 && b.iY() && SECONDARY_STORAGE_PATH != null : b.iY() && SECONDARY_STORAGE_PATH != null;
    }

    private static void initQuota(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            StorageStatsManager storageStatsManager = (StorageStatsManager) context.getSystemService(StorageStatsManager.class);
            Class[] clsArr = {StorageStatsManager.class};
            Method method = Util.getMethod(clsArr, "isQuotaSupported", "(Ljava/util/UUID;)Z");
            if (method != null) {
                sQuotaSupported = method.invokeBoolean(clsArr[0], storageStatsManager, new Object[]{StorageManager.UUID_DEFAULT});
                if (sQuotaSupported) {
                    long totalBytes = new StatFs(PRIMARY_STORAGE_PATH).getTotalBytes();
                    sQuotaBytes = (long) (0.9f * ((float) totalBytes));
                    sReserveBytes = totalBytes - sQuotaBytes;
                    String str = TAG;
                    Log.d(str, "quota: " + sQuotaBytes + "|" + sReserveBytes);
                }
            }
        }
    }

    public static void initStorage(Context context) {
        initQuota(context);
        if (b.iY()) {
            FileCompat.updateSDPath();
            String sdcardPath = CompatibilityUtils.getSdcardPath(context);
            String str = TAG;
            Log.v(str, "initStorage sd=" + sdcardPath);
            if (sdcardPath != null) {
                SECONDARY_STORAGE_PATH = sdcardPath;
                SECONDARY_BUCKET_ID = (SECONDARY_STORAGE_PATH + CAMERA_STORAGE_PATH_SUFFIX).toLowerCase(Locale.ENGLISH).hashCode();
            } else {
                SECONDARY_STORAGE_PATH = null;
            }
            readSystemPriorityStorage();
        }
    }

    private static Uri insertToMediaStore(Context context, String str, String str2, long j, String str3, int i, String str4, long j2, int i2, int i3, Location location, boolean z) {
        Uri uri;
        ContentValues contentValues = new ContentValues(11);
        contentValues.put("title", str);
        contentValues.put("_display_name", str2);
        contentValues.put("datetaken", Long.valueOf(j));
        contentValues.put("mime_type", str3);
        contentValues.put("orientation", Integer.valueOf(i));
        contentValues.put("_data", str4);
        contentValues.put("_size", Long.valueOf(j2));
        contentValues.put("width", Integer.valueOf(i2));
        contentValues.put("height", Integer.valueOf(i3));
        if (location != null) {
            contentValues.put("latitude", Double.valueOf(location.getLatitude()));
            contentValues.put("longitude", Double.valueOf(location.getLongitude()));
        }
        if (!z) {
            try {
                uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                try {
                    Log.d(TAG, "insert: title " + str + ", orientation = " + i + ", uri = " + uri);
                    return uri;
                } catch (Exception e) {
                    e = e;
                }
            } catch (Exception e2) {
                e = e2;
                uri = null;
                Log.e(TAG, "Failed to write MediaStore:" + e.getMessage(), e);
                return uri;
            }
        } else {
            Uri insert = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            try {
                ParallelUtil.insertImageToParallelService(context, ContentUris.parseId(insert), str4);
                Log.d(TAG, "parallel insert: title " + str + ", orientation = " + i + ", uri = " + insert);
                return insert;
            } catch (Exception e3) {
                e = e3;
                uri = insert;
                Log.e(TAG, "Failed to write MediaStore:" + e.getMessage(), e);
                return uri;
            }
        }
    }

    public static boolean isCurrentStorageIsSecondary() {
        return SECONDARY_STORAGE_PATH != null && SECONDARY_STORAGE_PATH.equals(sCurrentStoragePath);
    }

    public static boolean isDirectoryExistsAndCanWrite(String str) {
        File file = new File(str);
        return file.exists() && file.isDirectory() && file.canWrite();
    }

    public static boolean isLowStorageAtLastPoint() {
        return getLeftSpace() < LOW_STORAGE_THRESHOLD;
    }

    public static boolean isLowStorageSpace(String str) {
        return getAvailableSpace(str) < LOW_STORAGE_THRESHOLD;
    }

    public static boolean isPhoneStoragePriority() {
        return PRIMARY_STORAGE_PATH.equals(FIRST_CONSIDER_STORAGE_PATH);
    }

    public static boolean isQuotaSupported() {
        return sQuotaSupported && sQuotaBytes > 0;
    }

    public static boolean isRelatedStorage(Uri uri) {
        if (uri == null) {
            return false;
        }
        String path = uri.getPath();
        if (path != null) {
            return path.equals(PRIMARY_STORAGE_PATH) || path.equals(SECONDARY_STORAGE_PATH);
        }
        return false;
    }

    public static boolean isSecondPhoneStorage(String str) {
        return str != null && !TextUtils.isEmpty(SECONDARY_STORAGE_PATH) && str.startsWith(SECONDARY_STORAGE_PATH);
    }

    public static boolean isUseDocumentMode() {
        if (Build.VERSION.SDK_INT >= 28 && !isUsePhoneStorage()) {
            return !DataRepository.dataItemFeature().hX() || !isDirectoryExistsAndCanWrite(sCurrentStoragePath);
        }
        return false;
    }

    public static boolean isUsePhoneStorage() {
        return PRIMARY_STORAGE_PATH.equals(sCurrentStoragePath);
    }

    public static Uri newImage(Context context, String str, long j, int i, int i2, int i3) {
        String generateFilepath4Jpeg = generateFilepath4Jpeg(str);
        ContentValues contentValues = new ContentValues(6);
        contentValues.put("datetaken", Long.valueOf(j));
        contentValues.put("orientation", Integer.valueOf(i));
        contentValues.put("_data", generateFilepath4Jpeg);
        contentValues.put("width", Integer.valueOf(i2));
        contentValues.put("height", Integer.valueOf(i3));
        contentValues.put("mime_type", "image/jpeg");
        try {
            return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } catch (Exception e) {
            String str2 = TAG;
            Log.e(str2, "Failed to new image" + e);
            return null;
        }
    }

    public static void readSystemPriorityStorage() {
        boolean z;
        if (hasSecondaryStorage()) {
            z = PriorityStorageBroadcastReceiver.isPriorityStorage();
            CameraSettings.setPriorityStoragePreference(z);
        } else {
            z = false;
        }
        FIRST_CONSIDER_STORAGE_PATH = z ? SECONDARY_STORAGE_PATH : PRIMARY_STORAGE_PATH;
        sCurrentStoragePath = FIRST_CONSIDER_STORAGE_PATH;
        updateDirectory();
    }

    private static boolean renameSdcardFile(String str, String str2) {
        boolean renameFile;
        int i = 0;
        boolean z = false;
        while (true) {
            i++;
            try {
                renameFile = FileCompat.renameFile(str, str2);
                if (!renameFile && i < 5) {
                    z = renameFile;
                }
            } catch (IOException e) {
                Log.e(TAG, "renameSdcardFile failed", e);
                return z;
            }
        }
        return renameFile;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0094 A[SYNTHETIC, Splitter:B:24:0x0094] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x009c A[SYNTHETIC, Splitter:B:27:0x009c] */
    /* JADX WARNING: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    public static void saveMorphoPanoramaOriginalPic(ByteBuffer byteBuffer, int i, String str) {
        File file = new File(DIRECTORY + File.separator + str + File.separator);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileChannel fileChannel = null;
        try {
            File file2 = new File(generateFilepath4Jpeg(str + File.separator + str + "_" + i));
            if (!file2.exists()) {
                file2.createNewFile();
            }
            FileChannel channel = new FileOutputStream(file2, false).getChannel();
            try {
                channel.write(byteBuffer);
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e2) {
                e = e2;
                fileChannel = channel;
                try {
                    String str2 = TAG;
                    Log.e(str2, "saveMorphoPanoramaOriginalPic  " + e.toString());
                    if (fileChannel == null) {
                        fileChannel.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    if (fileChannel != null) {
                        try {
                            fileChannel.close();
                        } catch (Exception e3) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileChannel = channel;
                if (fileChannel != null) {
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            String str22 = TAG;
            Log.e(str22, "saveMorphoPanoramaOriginalPic  " + e.toString());
            if (fileChannel == null) {
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0089 A[SYNTHETIC, Splitter:B:26:0x0089] */
    public static void saveOriginalPic(byte[] bArr, int i, String str) {
        File file = new File(DIRECTORY + File.separator + str + File.separator);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream fileOutputStream = null;
        try {
            File file2 = new File(generateFilepath4Jpeg(str + File.separator + str + "_" + i));
            if (!file2.exists()) {
                file2.createNewFile();
            }
            FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
            try {
                fileOutputStream2.write(bArr);
            } catch (Exception e) {
                e = e;
                fileOutputStream = fileOutputStream2;
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (Exception e2) {
                    }
                }
                throw th;
            }
            try {
                fileOutputStream2.flush();
                fileOutputStream2.close();
            } catch (Exception e3) {
            }
        } catch (Exception e4) {
            e = e4;
            try {
                Log.e(TAG, "saveMorphoPanoramaOriginalPic exception occurs", e);
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileOutputStream != null) {
                }
                throw th;
            }
        }
    }

    public static void saveToCloudAlbum(Context context, String str, long j, boolean z) {
        saveToCloudAlbum(context, str, j, false, -1, z);
    }

    public static void saveToCloudAlbum(Context context, String str, long j, boolean z, long j2, boolean z2) {
        context.sendBroadcast(getSaveToCloudIntent(context, str, j, z, j2, z2));
    }

    public static boolean secondaryStorageMounted() {
        StringBuilder sb = new StringBuilder();
        sb.append(SECONDARY_STORAGE_PATH);
        sb.append(File.separator);
        sb.append(Environment.DIRECTORY_DCIM);
        return hasSecondaryStorage() && getAvailableSpace(sb.toString()) > 0;
    }

    private static void setLeftSpace(long j) {
        LEFT_SPACE.set(j);
        String str = TAG;
        Log.i(str, "setLeftSpace(" + j + ")");
    }

    public static void setStorageListener(StorageListener storageListener) {
        if (storageListener != null) {
            sStorageListener = new WeakReference<>(storageListener);
        }
    }

    public static void switchStoragePathIfNeeded() {
        if (hasSecondaryStorage()) {
            String str = FIRST_CONSIDER_STORAGE_PATH;
            String str2 = SECONDARY_STORAGE_PATH;
            if (FIRST_CONSIDER_STORAGE_PATH.equals(SECONDARY_STORAGE_PATH)) {
                str2 = PRIMARY_STORAGE_PATH;
            }
            String str3 = sCurrentStoragePath;
            if (!isLowStorageSpace(str + CAMERA_STORAGE_PATH_SUFFIX)) {
                sCurrentStoragePath = str;
            } else {
                if (!isLowStorageSpace(str2 + CAMERA_STORAGE_PATH_SUFFIX)) {
                    sCurrentStoragePath = str2;
                } else {
                    return;
                }
            }
            if (!sCurrentStoragePath.equals(str3)) {
                updateDirectory();
                if (!(sStorageListener == null || sStorageListener.get() == null)) {
                    ((StorageListener) sStorageListener.get()).onStoragePathChanged();
                }
            }
            String str4 = TAG;
            Log.d(str4, "Storage path is switched path = " + DIRECTORY + ", FIRST_CONSIDER_STORAGE_PATH = " + FIRST_CONSIDER_STORAGE_PATH + ", SECONDARY_STORAGE_PATH = " + SECONDARY_STORAGE_PATH);
        }
    }

    public static void switchToPhoneStorage() {
        FIRST_CONSIDER_STORAGE_PATH = PRIMARY_STORAGE_PATH;
        if (!PRIMARY_STORAGE_PATH.equals(sCurrentStoragePath)) {
            Log.v(TAG, "switchToPhoneStorage");
            sCurrentStoragePath = PRIMARY_STORAGE_PATH;
            updateDirectory();
            if (sStorageListener != null && sStorageListener.get() != null) {
                ((StorageListener) sStorageListener.get()).onStoragePathChanged();
            }
        }
    }

    private static void updateDirectory() {
        DIRECTORY = sCurrentStoragePath + CAMERA_STORAGE_PATH_SUFFIX;
        RAW_DIRECTORY = DIRECTORY + RAW_PATH_SUFFIX;
        HIDEDIRECTORY = sCurrentStoragePath + HIDE_CAMERA_STORAGE_PATH_SUFFIX;
        BUCKET_ID = DIRECTORY.toLowerCase(Locale.ENGLISH).hashCode();
    }

    private static byte[] updateExif(byte[] bArr, boolean z, String str, PictureInfo pictureInfo, int i, int i2, int i3) {
        if (!z && TextUtils.isEmpty(str) && pictureInfo == null) {
            return bArr;
        }
        long currentTimeMillis = System.currentTimeMillis();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ExifInterface exifInterface = new ExifInterface();
            exifInterface.readExif(bArr);
            if (z) {
                exifInterface.addParallelProcessComment("processing", i, i2, i3);
            }
            if (!TextUtils.isEmpty(str)) {
                String str2 = TAG;
                Log.d(str2, "save algorithm: " + str);
                exifInterface.addAlgorithmComment(str);
            }
            if (pictureInfo != null) {
                String str3 = TAG;
                Log.d(str3, "save xiaomi comment: " + pictureInfo.getInfoString() + ", aiType = " + pictureInfo.getAiType());
                exifInterface.addAiType(pictureInfo.getAiType());
                if (pictureInfo.isBokehFrontCamera()) {
                    exifInterface.addFrontMirror(pictureInfo.isFrontMirror() ? 1 : 0);
                }
                if ("tucana".equals(b.rp)) {
                    pictureInfo.setAfRoi(i, i2, i3);
                    exifInterface.addXpComment(pictureInfo.getXpCommentBytes());
                }
                exifInterface.addXiaomiComment(pictureInfo.getInfoString());
            }
            exifInterface.writeExif(bArr, (OutputStream) byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            bArr = byteArray;
        } catch (Exception e) {
            String str4 = TAG;
            Log.e(str4, "updateExif error " + e.getMessage(), e);
        }
        String str5 = TAG;
        Log.v(str5, "update exif cost=" + (System.currentTimeMillis() - currentTimeMillis));
        return bArr;
    }

    public static boolean updateImage(Context context, byte[] bArr, ExifInterface exifInterface, Uri uri, String str, Location location, int i, int i2, int i3, String str2) {
        String str3;
        boolean z;
        BufferedInputStream bufferedInputStream;
        Throwable th;
        OutputStream fileOutputStream;
        Throwable th2;
        byte[] bArr2 = bArr;
        ExifInterface exifInterface2 = exifInterface;
        String str4 = str;
        String str5 = str2;
        boolean z2 = false;
        boolean z3 = str5 == null && isUseDocumentMode();
        String generateFilepath4Jpeg = generateFilepath4Jpeg(str);
        if (z3) {
            str3 = generateFilepath4Jpeg;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(str5 != null ? generateFilepath4Jpeg(str2) : generateFilepath4Jpeg);
            sb.append(".tmp");
            str3 = sb.toString();
        }
        File file = new File(str3);
        if (bArr2 != null) {
            try {
                bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(bArr2));
                fileOutputStream = isUseDocumentMode() ? FileCompat.getFileOutputStream(str3, true) : new BufferedOutputStream(new FileOutputStream(file));
                if (exifInterface2 == null) {
                    byte[] bArr3 = new byte[4096];
                    while (true) {
                        int read = bufferedInputStream.read(bArr3);
                        if (read == -1) {
                            break;
                        }
                        fileOutputStream.write(bArr3, 0, read);
                    }
                } else {
                    try {
                        exifInterface2.writeExif(bArr2, fileOutputStream);
                    } catch (IOException e) {
                        try {
                            Log.e(TAG, "Failed to rewrite Exif");
                            fileOutputStream.write(bArr2);
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    }
                }
                if (fileOutputStream != null) {
                    $closeResource((Throwable) null, fileOutputStream);
                }
                $closeResource((Throwable) null, bufferedInputStream);
            } catch (Exception e2) {
                Log.e(TAG, "Failed to write image", e2);
                return false;
            } catch (Throwable th4) {
                $closeResource(th, bufferedInputStream);
                throw th4;
            }
        } else if (str5 != null) {
            str3 = generateFilepath4Jpeg(str2);
        }
        long length = file.length();
        if (!isUseDocumentMode()) {
            boolean renameTo = file.renameTo(new File(generateFilepath4Jpeg));
            if (!(exifInterface2 == null || str5 == null)) {
                try {
                    new File(generateFilepath4Jpeg(str2)).delete();
                } catch (Exception e3) {
                    Log.e(TAG, "Exception when delete old file " + str5, e3);
                }
            }
            if (!renameTo) {
                Log.w(TAG, "renameTo failed, tmpPath = " + str3);
                return false;
            }
        }
        ContentValues contentValues = new ContentValues(10);
        contentValues.put("title", str4);
        contentValues.put("_display_name", str4 + JPEG_SUFFIX);
        String str6 = isUseDocumentMode() ? str3 : generateFilepath4Jpeg;
        if (bArr2 != null) {
            contentValues.put("mime_type", "image/jpeg");
            contentValues.put("orientation", Integer.valueOf(i));
            contentValues.put("_size", Long.valueOf(length));
            contentValues.put("width", Integer.valueOf(i2));
            contentValues.put("height", Integer.valueOf(i3));
            if (location != null) {
                contentValues.put("latitude", Double.valueOf(location.getLatitude()));
                contentValues.put("longitude", Double.valueOf(location.getLongitude()));
            }
            contentValues.put("_data", str6);
        } else if (str5 != null) {
            contentValues.put("_data", str6);
        }
        context.getContentResolver().update(uri, contentValues, (String) null, (String[]) null);
        if (!z3 && isUseDocumentMode()) {
            String str7 = generateFilepath4Jpeg + ".mid";
            if (renameSdcardFile(generateFilepath4Jpeg, str7)) {
                z = renameSdcardFile(str3, generateFilepath4Jpeg);
                if (z) {
                    deleteSdcardFile(str7);
                } else {
                    Log.w(TAG, "fail to rename " + str3 + " to " + generateFilepath4Jpeg);
                    deleteSdcardFile(str3);
                }
            } else {
                Log.w(TAG, "fail to rename " + generateFilepath4Jpeg + " to " + str7);
                deleteSdcardFile(str7);
                z = false;
            }
            FileCompat.removeDocumentFileForPath(str3);
            FileCompat.removeDocumentFileForPath(generateFilepath4Jpeg);
            if (!z) {
                Log.w(TAG, "renameTo failed, tmpPath = " + str3);
                return false;
            }
        }
        long length2 = (long) bArr2.length;
        if (location == null) {
            z2 = true;
        }
        saveToCloudAlbum(context, generateFilepath4Jpeg, length2, z2);
        return true;
        if (fileOutputStream != null) {
            $closeResource(th2, fileOutputStream);
        }
        throw th;
    }

    public static boolean updateImageSize(ContentResolver contentResolver, Uri uri, long j) {
        ContentValues contentValues = new ContentValues(1);
        contentValues.put("_size", Long.valueOf(j));
        try {
            contentResolver.update(uri, contentValues, (String) null, (String[]) null);
            return true;
        } catch (Exception e) {
            String str = TAG;
            Log.e(str, "Failed to updateMediaStore" + e);
            return false;
        }
    }

    public static boolean updateImageWithExtraExif(Context context, byte[] bArr, ExifInterface exifInterface, Uri uri, String str, Location location, int i, int i2, int i3, String str2, boolean z, boolean z2, String str3, PictureInfo pictureInfo) {
        return updateImage(context, updateExif(bArr, z2, str3, pictureInfo, i, i2, i3), exifInterface, uri, str, location, i, i2, i3, str2);
    }
}
