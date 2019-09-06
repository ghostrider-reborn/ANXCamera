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
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.DngCreator;
import android.location.Location;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StatFs;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.provider.MediaStore.Files;
import android.provider.MediaStore.Images.Media;
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
    public static String CAMERA_TEMP_DIRECTORY = null;
    public static String DIRECTORY = null;
    public static String FIRST_CONSIDER_STORAGE_PATH = (b.xm ? SECONDARY_STORAGE_PATH : PRIMARY_STORAGE_PATH);
    public static String HIDEDIRECTORY = null;
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
    public static int PRIMARY_BUCKET_ID = 0;
    public static int PRIMARY_RAW_BUCKET_ID = 0;
    private static final String PRIMARY_STORAGE_PATH = Environment.getExternalStorageDirectory().toString();
    public static final float QUOTA_RATIO = 0.9f;
    public static String RAW_DIRECTORY = null;
    private static final String RAW_PATH_SUFFIX = "/Raw";
    public static final String RAW_SUFFIX = ".dng";
    private static final String SAVE_TO_CLOUD_ALBUM_ACTION = "com.miui.gallery.SAVE_TO_CLOUD";
    private static final String SAVE_TO_CLOUD_ALBUM_CACHE_LOCATION_KEY = "extra_cache_location";
    private static final String SAVE_TO_CLOUD_ALBUM_FILE_LENGTH = "extra_file_length";
    private static final String SAVE_TO_CLOUD_ALBUM_PACKAGE = "com.miui.gallery";
    private static final String SAVE_TO_CLOUD_ALBUM_PATH_KAY = "extra_file_path";
    private static final String SAVE_TO_CLOUD_ALBUM_STORE_ID_KAY = "extra_media_store_id";
    private static final String SAVE_TO_CLOUD_ALBUM_TEMP_FILE_KAY = "extra_is_temp_file";
    public static int SECONDARY_BUCKET_ID = 0;
    public static int SECONDARY_RAW_BUCKET_ID = 0;
    private static String SECONDARY_STORAGE_PATH = System.getenv("SECONDARY_STORAGE");
    private static final String TAG = "Storage";
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
        StringBuilder sb = new StringBuilder();
        sb.append(FIRST_CONSIDER_STORAGE_PATH);
        String str = CAMERA_STORAGE_PATH_SUFFIX;
        sb.append(str);
        DIRECTORY = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(DIRECTORY);
        String str2 = RAW_PATH_SUFFIX;
        sb2.append(str2);
        RAW_DIRECTORY = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(FIRST_CONSIDER_STORAGE_PATH);
        sb3.append(HIDE_CAMERA_STORAGE_PATH_SUFFIX);
        HIDEDIRECTORY = sb3.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(FIRST_CONSIDER_STORAGE_PATH);
        sb4.append(CAMERA_STORAGE_PATH_TEMP);
        CAMERA_TEMP_DIRECTORY = sb4.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(PRIMARY_STORAGE_PATH);
        sb5.append(str);
        PRIMARY_BUCKET_ID = sb5.toString().toLowerCase(Locale.ENGLISH).hashCode();
        StringBuilder sb6 = new StringBuilder();
        sb6.append(SECONDARY_STORAGE_PATH);
        sb6.append(str);
        SECONDARY_BUCKET_ID = sb6.toString().toLowerCase(Locale.ENGLISH).hashCode();
        StringBuilder sb7 = new StringBuilder();
        sb7.append(PRIMARY_STORAGE_PATH);
        sb7.append(str);
        sb7.append(str2);
        PRIMARY_RAW_BUCKET_ID = sb7.toString().toLowerCase(Locale.ENGLISH).hashCode();
        StringBuilder sb8 = new StringBuilder();
        sb8.append(SECONDARY_STORAGE_PATH);
        sb8.append(str);
        sb8.append(str2);
        SECONDARY_RAW_BUCKET_ID = sb8.toString().toLowerCase(Locale.ENGLISH).hashCode();
        StringBuilder sb9 = new StringBuilder();
        sb9.append(DIRECTORY);
        sb9.append(File.separator);
        sb9.append(AVOID_SCAN_FILE_NAME);
        File file = new File(sb9.toString());
        if (file.exists()) {
            file.delete();
        }
    }

    public static Uri addDNGToDataBase(Context context, String str, int i, int i2, int i3) {
        String name = new File(str).getName();
        String substring = name.substring(0, name.lastIndexOf(46) - 1);
        ContentValues contentValues = new ContentValues(7);
        contentValues.put("title", substring);
        contentValues.put("_display_name", name);
        contentValues.put("media_type", Integer.valueOf(1));
        contentValues.put("mime_type", "image/x-adobe-dng");
        contentValues.put("_data", str);
        contentValues.put("width", Integer.valueOf(i));
        contentValues.put("height", Integer.valueOf(i2));
        contentValues.put("orientation", Integer.valueOf(i3));
        try {
            return context.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
        } catch (Exception e2) {
            String str2 = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to write MediaStore, path ");
            sb.append(str);
            Log.e(str2, sb.toString(), e2);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:148:0x01c0  */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x01c2  */
    public static Uri addImage(Context context, String str, long j, Location location, int i, byte[] bArr, int i2, int i3, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, String str2, PictureInfo pictureInfo) {
        byte[] bArr2;
        boolean z6;
        byte[] bArr3;
        String str3;
        boolean z7;
        String str4;
        boolean z8;
        Throwable th;
        OutputStream outputStream;
        Throwable th2;
        Throwable th3;
        Throwable th4;
        final Context context2 = context;
        String str5 = str;
        long j2 = j;
        Location location2 = location;
        int i4 = i;
        boolean z9 = z2;
        boolean z10 = z3;
        String str6 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("addImage: parallel=");
        sb.append(z5);
        sb.append(" appendExif=");
        sb.append(z4);
        Log.d(str6, sb.toString());
        byte[] updateExif = updateExif(bArr, z5, str2, pictureInfo, i, i2, i3);
        String generateFilepath4Jpeg = generateFilepath4Jpeg(str5, z9, z10);
        String str7 = TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("addImage: path=");
        sb2.append(generateFilepath4Jpeg);
        Log.d(str7, sb2.toString());
        boolean z11 = z4;
        int i5 = 0;
        while (true) {
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(updateExif));
                try {
                    if (isUseDocumentMode()) {
                        try {
                            outputStream = FileCompat.getFileOutputStream(generateFilepath4Jpeg, true);
                        } catch (Throwable th5) {
                            th = th5;
                            bArr2 = updateExif;
                            throw th;
                        }
                    } else {
                        outputStream = new FileOutputStream(generateFilepath4Jpeg);
                    }
                    OutputStream outputStream2 = outputStream;
                    try {
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream2);
                        if (!z) {
                            z8 = z11;
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
                                boolean z12 = i4 % 180 == 0;
                                z8 = z11;
                                try {
                                    Bitmap flipJpeg = flipJpeg(updateExif, z12, !z12);
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
                                        bArr2 = updateExif;
                                        if (thumbnailBytes != null) {
                                            Bitmap flipJpeg2 = flipJpeg(thumbnailBytes, z12, !z12);
                                            if (flipJpeg2 != null) {
                                                exif.setCompressedThumbnail(flipJpeg2);
                                                flipJpeg2.recycle();
                                            }
                                            z8 = false;
                                        }
                                        exif.writeExif(flipJpeg, (OutputStream) bufferedOutputStream);
                                        flipJpeg.recycle();
                                    }
                                } catch (Throwable th6) {
                                    th = th6;
                                    th3 = th;
                                    throw th3;
                                }
                            } catch (Throwable th7) {
                                th = th7;
                                z8 = z11;
                                bArr2 = updateExif;
                                th3 = th;
                                throw th3;
                            }
                        }
                        z11 = z8;
                        if (z11) {
                            try {
                                bufferedOutputStream.flush();
                                if (!isUseDocumentMode()) {
                                    ExifHelper.writeExifByFilePath(generateFilepath4Jpeg, i4, location2, j2);
                                } else {
                                    try {
                                        ParcelFileDescriptor parcelFileDescriptor = FileCompat.getParcelFileDescriptor(generateFilepath4Jpeg, false);
                                        try {
                                            ExifHelper.writeExifByFd(parcelFileDescriptor.getFileDescriptor(), i4, location2, j2);
                                            if (parcelFileDescriptor != null) {
                                                $closeResource(null, parcelFileDescriptor);
                                            }
                                        } catch (Throwable th8) {
                                            Throwable th9 = th8;
                                            if (parcelFileDescriptor == null) {
                                                break;
                                            }
                                            $closeResource(th4, parcelFileDescriptor);
                                            break;
                                            throw th9;
                                        }
                                    } catch (Exception e2) {
                                        String str8 = TAG;
                                        StringBuilder sb3 = new StringBuilder();
                                        sb3.append("write exif failed, file is ");
                                        sb3.append(generateFilepath4Jpeg);
                                        Log.e(str8, sb3.toString(), e2);
                                    }
                                }
                            } catch (Throwable th10) {
                                th3 = th10;
                                z8 = z11;
                            }
                        }
                        try {
                            $closeResource(null, bufferedOutputStream);
                            if (outputStream2 != null) {
                                try {
                                    $closeResource(null, outputStream2);
                                } catch (Throwable th11) {
                                    th = th11;
                                    th = th;
                                    throw th;
                                }
                            }
                        } catch (Throwable th12) {
                            th = th12;
                            th2 = th;
                            throw th2;
                        }
                        try {
                            $closeResource(null, bufferedInputStream);
                            z6 = false;
                            break;
                        } catch (Exception e3) {
                            e = e3;
                        } catch (Throwable th13) {
                            Throwable th14 = th13;
                            $closeResource(th, bufferedInputStream);
                            throw th14;
                        }
                    } catch (Throwable th15) {
                        th = th15;
                        z8 = z11;
                        bArr2 = updateExif;
                        th2 = th;
                        throw th2;
                    }
                } catch (Throwable th16) {
                    th = th16;
                    z8 = z11;
                    bArr2 = updateExif;
                    th = th;
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                z8 = z11;
                bArr2 = updateExif;
                dumpExceptionEnv(e, generateFilepath4Jpeg);
                Log.e(TAG, "Failed to write image", e);
                i5++;
                if (Util.isQuotaExceeded(e) && (context2 instanceof ActivityBase)) {
                    ActivityBase activityBase = (ActivityBase) context2;
                    if (!activityBase.isActivityPaused()) {
                        activityBase.runOnUiThread(new Runnable() {
                            public void run() {
                                ToastUtils.showToast(context2, (int) R.string.spaceIsLow_content_primary_storage_priority);
                            }
                        });
                    }
                    i5 = MAX_WRITE_RETRY;
                } else if (i5 < MAX_WRITE_RETRY) {
                    System.gc();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException unused) {
                    }
                }
                if (i5 >= MAX_WRITE_RETRY) {
                    z6 = true;
                    if (!z6) {
                    }
                } else {
                    int i6 = i3;
                    context2 = context;
                    String str9 = str;
                    location2 = location;
                    boolean z13 = z2;
                    updateExif = bArr2;
                }
            }
            int i62 = i3;
            context2 = context;
            String str92 = str;
            location2 = location;
            boolean z132 = z2;
            updateExif = bArr2;
        }
        if (!z6) {
            return null;
        }
        if (z10) {
            boolean isProduceFocusInfoSuccess = Util.isProduceFocusInfoSuccess(bArr2);
            bArr3 = bArr2;
            int centerFocusDepthIndex = Util.getCenterFocusDepthIndex(bArr3, i2, i3);
            String str10 = "_";
            String str11 = UBIFOCUS_SUFFIX;
            String str12 = str;
            str3 = str12.substring(0, isProduceFocusInfoSuccess ? str12.lastIndexOf(str10) : str12.lastIndexOf(str11));
            String generateFilepath4Jpeg2 = generateFilepath4Jpeg(str3, false, false);
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str3);
            if (!isProduceFocusInfoSuccess) {
                str10 = str11;
            }
            sb4.append(str10);
            sb4.append(centerFocusDepthIndex);
            z7 = z2;
            String generateFilepath4Jpeg3 = generateFilepath4Jpeg(sb4.toString(), z7, false);
            if (generateFilepath4Jpeg2 == null || generateFilepath4Jpeg3 == null) {
                String str13 = TAG;
                StringBuilder sb5 = new StringBuilder();
                sb5.append("oldPath: ");
                String str14 = TEDefine.FACE_BEAUTY_NULL;
                if (generateFilepath4Jpeg3 == null) {
                    generateFilepath4Jpeg3 = str14;
                }
                sb5.append(generateFilepath4Jpeg3);
                sb5.append(" newPath: ");
                if (generateFilepath4Jpeg2 != null) {
                    str14 = generateFilepath4Jpeg2;
                }
                sb5.append(str14);
                Log.e(str13, sb5.toString());
            } else {
                new File(generateFilepath4Jpeg3).renameTo(new File(generateFilepath4Jpeg2));
            }
            if (!isProduceFocusInfoSuccess) {
                deleteImage(str3);
            }
            str4 = generateFilepath4Jpeg2;
        } else {
            str3 = str;
            int i7 = i3;
            z7 = z2;
            bArr3 = bArr2;
            str4 = generateFilepath4Jpeg;
        }
        if (z7 && !z10) {
            return null;
        }
        StringBuilder sb6 = new StringBuilder();
        sb6.append(str3);
        sb6.append(JPEG_SUFFIX);
        boolean z14 = false;
        byte[] bArr6 = bArr3;
        Uri insertToMediaStore = insertToMediaStore(context, str3, sb6.toString(), j, "image/jpeg", i, str4, new File(str4).length(), i2, i3, location, z5);
        if (insertToMediaStore == null) {
            String str15 = TAG;
            StringBuilder sb7 = new StringBuilder();
            sb7.append("failed to insert to DB: ");
            sb7.append(str4);
            Log.e(str15, sb7.toString());
            return null;
        }
        long length = (long) bArr6.length;
        long parseId = ContentUris.parseId(insertToMediaStore);
        if (location == null) {
            z14 = true;
        }
        saveToCloudAlbum(context, str4, length, z5, parseId, z14);
        return insertToMediaStore;
    }

    public static Uri addImageForEffect(Context context, String str, long j, Location location, int i, byte[] bArr, int i2, int i3, boolean z, boolean z2, String str2, PictureInfo pictureInfo) {
        return addImage(context, str, j, location, i, bArr, i2, i3, z, false, false, false, z2, str2, pictureInfo);
    }

    public static Uri addImageForGroupOrPanorama(Context context, String str, int i, long j, Location location, int i2, int i3) {
        File file;
        String str2 = str;
        if (!(context == null || str2 == null)) {
            try {
                file = new File(str2);
            } catch (Exception e2) {
                String str3 = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to open panorama file: ");
                sb.append(e2.getMessage());
                Log.e(str3, sb.toString(), e2);
                file = null;
            }
            if (file != null && file.exists()) {
                String name = file.getName();
                String str4 = str2;
                Uri insertToMediaStore = insertToMediaStore(context, name, name, j, "image/jpeg", i, str, file.length(), i2, i3, location, false);
                saveToCloudAlbum(context, str4, -1, location == null);
                return insertToMediaStore;
            }
        }
        return null;
    }

    public static Uri addImageForSnapCamera(Context context, String str, long j, Location location, int i, byte[] bArr, int i2, int i3, boolean z, boolean z2, boolean z3, String str2, PictureInfo pictureInfo) {
        return addImage(context, str, j, location, i, bArr, i2, i3, z, z2, z3, false, false, str2, pictureInfo);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:0x009d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        $closeResource(r1, r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a2, code lost:
        throw r2;
     */
    public static Uri addRawImage(Context context, String str, CameraCharacteristics cameraCharacteristics, CaptureResult captureResult, byte[] bArr, int i, int i2, int i3) {
        OutputStream outputStream;
        long j;
        BufferedOutputStream bufferedOutputStream;
        Context context2 = context;
        int i4 = i;
        int i5 = i2;
        String generateRawFilepath = generateRawFilepath(str);
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        try {
            DngCreator dngCreator = new DngCreator(cameraCharacteristics, captureResult);
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(bArr));
                try {
                    th = isUseDocumentMode() ? FileCompat.getFileOutputStream(generateRawFilepath, true) : new FileOutputStream(generateRawFilepath);
                    try {
                        BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(outputStream);
                        try {
                            dngCreator.setOrientation(Integer.parseInt(ExifHelper.getExifOrientation(i3)));
                            bufferedOutputStream = bufferedOutputStream2;
                            try {
                                dngCreator.writeInputStream(bufferedOutputStream2, new Size(i4, i5), bufferedInputStream, 0);
                                Uri addDNGToDataBase = addDNGToDataBase(context2, generateRawFilepath, i4, i5, i3);
                                String str2 = TAG;
                                StringBuilder sb = new StringBuilder();
                                sb.append("addRawImage path ");
                                sb.append(generateRawFilepath);
                                sb.append(", uri = ");
                                sb.append(addDNGToDataBase);
                                Log.d(str2, sb.toString());
                                saveToCloudAlbum(context2, generateRawFilepath, -1, currentLocation == null);
                                $closeResource(null, bufferedOutputStream);
                                if (outputStream != null) {
                                    $closeResource(null, outputStream);
                                }
                                $closeResource(null, bufferedInputStream);
                                $closeResource(null, dngCreator);
                                return addDNGToDataBase;
                            } catch (Throwable th) {
                                th = th;
                                Throwable th2 = th;
                                throw th2;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            bufferedOutputStream = bufferedOutputStream2;
                            Throwable th22 = th;
                            throw th22;
                        }
                    } finally {
                        try {
                        } catch (Throwable th4) {
                            Throwable th5 = th4;
                            if (outputStream != null) {
                                $closeResource(j, outputStream);
                            }
                            throw th5;
                        }
                    }
                } finally {
                    outputStream = th;
                    try {
                    } catch (Throwable th6) {
                        Throwable th7 = th6;
                        $closeResource(outputStream, bufferedInputStream);
                        throw th7;
                    }
                }
            } finally {
                Throwable th8 = th;
                try {
                } catch (Throwable th9) {
                    Throwable th10 = th9;
                    $closeResource(th8, dngCreator);
                    throw th10;
                }
            }
        } catch (Throwable th11) {
            String str3 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("addRawImage failed, path ");
            sb2.append(generateRawFilepath);
            Log.w(str3, sb2.toString(), th11);
            return null;
        }
    }

    public static void deleteImage(String str) {
        File[] listFiles;
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
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(".ex");
                File file2 = new File(sb.toString());
                z = file2.createNewFile();
                file2.delete();
            } catch (IOException unused) {
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
        Options options = new Options();
        options.inPurgeable = true;
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        Matrix matrix = new Matrix();
        float f2 = -1.0f;
        float f3 = z ? -1.0f : 1.0f;
        if (!z2) {
            f2 = 1.0f;
        }
        matrix.setScale(f3, f2, ((float) decodeByteArray.getWidth()) * 0.5f, ((float) decodeByteArray.getHeight()) * 0.5f);
        try {
            Bitmap createBitmap = Bitmap.createBitmap(decodeByteArray, 0, 0, decodeByteArray.getWidth(), decodeByteArray.getHeight(), matrix, true);
            if (createBitmap != decodeByteArray) {
                decodeByteArray.recycle();
            }
            if (createBitmap.getWidth() == -1 || createBitmap.getHeight() == -1) {
                return null;
            }
            return createBitmap;
        } catch (Exception e2) {
            Log.w(TAG, "Failed to rotate thumbnail", e2);
            return null;
        }
    }

    public static String generateFilepath(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(DIRECTORY);
        sb.append('/');
        sb.append(str);
        return sb.toString();
    }

    public static String generateFilepath(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(DIRECTORY);
        sb.append('/');
        sb.append(str);
        sb.append(str2);
        return sb.toString();
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
        StringBuilder sb = new StringBuilder();
        sb.append(PRIMARY_STORAGE_PATH);
        sb.append(CAMERA_STORAGE_PATH_SUFFIX);
        return sb.toString();
    }

    public static String generatePrimaryFilepath(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(generatePrimaryDirectoryPath());
        sb.append('/');
        sb.append(str);
        return sb.toString();
    }

    public static String generatePrimaryTempFile() {
        StringBuilder sb = new StringBuilder();
        sb.append(PRIMARY_STORAGE_PATH);
        sb.append(CAMERA_STORAGE_PATH_TEMP);
        return sb.toString();
    }

    public static String generateRawFilepath(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(RAW_DIRECTORY);
        sb.append("/");
        sb.append(str);
        sb.append(RAW_SUFFIX);
        return sb.toString();
    }

    public static String generateTempFilepath() {
        StringBuilder sb = new StringBuilder();
        sb.append(DIRECTORY);
        sb.append("/.temp");
        return sb.toString();
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
            String str2 = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("getAvailableSpace path = ");
            sb.append(str);
            sb.append(", exists = ");
            sb.append(file.exists());
            sb.append(", isDirectory = ");
            sb.append(file.isDirectory());
            sb.append(", canWrite = ");
            sb.append(file.canWrite());
            Log.w(str2, sb.toString());
            return -1;
        }
        if (mkdirs && str.endsWith(CAMERA_STORAGE_PATH_SUFFIX)) {
            if (MediaProviderUtil.insertCameraDirectory(CameraAppImpl.getAndroidContext(), str) != null) {
                String str3 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("insertDirectory success, path ");
                sb2.append(str);
                Log.d(str3, sb2.toString());
            } else {
                String str4 = TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("insertDirectory fail, path ");
                sb3.append(str);
                Log.w(str4, sb3.toString());
            }
        }
        try {
            if (HIDEDIRECTORY.equals(str)) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(HIDEDIRECTORY);
                sb4.append(File.separator);
                sb4.append(AVOID_SCAN_FILE_NAME);
                Util.createFile(new File(sb4.toString()));
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
                    } catch (IOException e2) {
                        Log.e(TAG, e2.getMessage(), e2);
                    }
                }
                setLeftSpace(availableBytes);
            }
            return availableBytes;
        } catch (Exception e3) {
            Log.i(TAG, "Fail to access external storage", e3);
            return -3;
        }
    }

    public static long getLeftSpace() {
        long j = LEFT_SPACE.get();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("getLeftSpace() return ");
        sb.append(j);
        Log.i(str, sb.toString());
        return j;
    }

    private static Intent getSaveToCloudIntent(Context context, String str, long j, boolean z, long j2, boolean z2) {
        Intent intent = new Intent(SAVE_TO_CLOUD_ALBUM_ACTION);
        String str2 = "com.miui.gallery";
        intent.setPackage(str2);
        List queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
        if (queryBroadcastReceivers != null && queryBroadcastReceivers.size() > 0) {
            intent.setComponent(new ComponentName(str2, ((ResolveInfo) queryBroadcastReceivers.get(0)).activityInfo.name));
        }
        intent.putExtra(SAVE_TO_CLOUD_ALBUM_PATH_KAY, str);
        intent.putExtra(SAVE_TO_CLOUD_ALBUM_FILE_LENGTH, j);
        String str3 = SAVE_TO_CLOUD_ALBUM_TEMP_FILE_KAY;
        if (z) {
            intent.putExtra(str3, true);
            intent.putExtra(SAVE_TO_CLOUD_ALBUM_STORE_ID_KAY, j2);
        } else {
            intent.putExtra(str3, false);
        }
        if (z2) {
            intent.putExtra(SAVE_TO_CLOUD_ALBUM_CACHE_LOCATION_KEY, LocationManager.instance().getLastKnownLocation());
            Log.d(TAG, "broadcast last location to gallery");
        }
        return intent;
    }

    public static boolean hasSecondaryStorage() {
        boolean z = true;
        if (VERSION.SDK_INT == 28) {
            if (UserHandle.myUserId() != 0 || !b.sj() || SECONDARY_STORAGE_PATH == null) {
                z = false;
            }
            return z;
        }
        if (!b.sj() || SECONDARY_STORAGE_PATH == null) {
            z = false;
        }
        return z;
    }

    private static void initQuota(Context context) {
        if (VERSION.SDK_INT >= 26) {
            StorageStatsManager storageStatsManager = (StorageStatsManager) context.getSystemService(StorageStatsManager.class);
            Class[] clsArr = {StorageStatsManager.class};
            Method method = Util.getMethod(clsArr, "isQuotaSupported", "(Ljava/util/UUID;)Z");
            if (method != null) {
                sQuotaSupported = method.invokeBoolean(clsArr[0], storageStatsManager, new Object[]{StorageManager.UUID_DEFAULT});
                if (sQuotaSupported) {
                    long totalBytes = new StatFs(PRIMARY_STORAGE_PATH).getTotalBytes();
                    sQuotaBytes = (long) (((float) totalBytes) * 0.9f);
                    sReserveBytes = totalBytes - sQuotaBytes;
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("quota: ");
                    sb.append(sQuotaBytes);
                    sb.append("|");
                    sb.append(sReserveBytes);
                    Log.d(str, sb.toString());
                }
            }
        }
    }

    public static void initStorage(Context context) {
        initQuota(context);
        if (b.sj()) {
            FileCompat.updateSDPath();
            String sdcardPath = CompatibilityUtils.getSdcardPath(context);
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("initStorage sd=");
            sb.append(sdcardPath);
            Log.v(str, sb.toString());
            if (sdcardPath != null) {
                SECONDARY_STORAGE_PATH = sdcardPath;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(SECONDARY_STORAGE_PATH);
                sb2.append(CAMERA_STORAGE_PATH_SUFFIX);
                SECONDARY_BUCKET_ID = sb2.toString().toLowerCase(Locale.ENGLISH).hashCode();
            } else {
                SECONDARY_STORAGE_PATH = null;
            }
            readSystemPriorityStorage();
        }
    }

    private static Uri insertToMediaStore(Context context, String str, String str2, long j, String str3, int i, String str4, long j2, int i2, int i3, Location location, boolean z) {
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
        String str5 = ", uri = ";
        String str6 = ", orientation = ";
        if (!z) {
            try {
                Uri insert = context.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
                String str7 = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("insert: title ");
                sb.append(str);
                sb.append(str6);
                sb.append(i);
                sb.append(str5);
                sb.append(insert);
                Log.d(str7, sb.toString());
                return insert;
            } catch (Exception e2) {
                String str8 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Failed to write MediaStore:");
                sb2.append(e2.getMessage());
                Log.e(str8, sb2.toString(), e2);
                return null;
            }
        } else {
            Uri insert2 = context.getContentResolver().insert(Files.getContentUri("external"), contentValues);
            ParallelUtil.insertImageToParallelService(context, ContentUris.parseId(insert2), str4);
            String str9 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("parallel insert: title ");
            sb3.append(str);
            sb3.append(str6);
            sb3.append(i);
            sb3.append(str5);
            sb3.append(insert2);
            Log.d(str9, sb3.toString());
            return insert2;
        }
    }

    public static boolean isCurrentStorageIsSecondary() {
        String str = SECONDARY_STORAGE_PATH;
        return str != null && str.equals(sCurrentStoragePath);
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
        if (VERSION.SDK_INT >= 28 && !isUsePhoneStorage()) {
            return !DataRepository.dataItemFeature().zb() || !isDirectoryExistsAndCanWrite(sCurrentStoragePath);
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
            return context.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
        } catch (Exception e2) {
            String str2 = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to new image");
            sb.append(e2);
            Log.e(str2, sb.toString());
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
        int i = 0;
        boolean z = false;
        do {
            i++;
            try {
                z = FileCompat.renameFile(str, str2);
                if (z) {
                    break;
                }
            } catch (IOException e2) {
                Log.e(TAG, "renameSdcardFile failed", e2);
            }
        } while (i < 5);
        return z;
    }

    public static void saveMorphoPanoramaOriginalPic(ByteBuffer byteBuffer, int i, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(DIRECTORY);
        sb.append(File.separator);
        sb.append(str);
        sb.append(File.separator);
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(File.separator);
        sb2.append(str);
        sb2.append("_");
        sb2.append(i);
        FileChannel fileChannel = null;
        try {
            File file2 = new File(generateFilepath4Jpeg(sb2.toString()));
            if (!file2.exists()) {
                file2.createNewFile();
            }
            fileChannel = new FileOutputStream(file2, false).getChannel();
            fileChannel.write(byteBuffer);
            if (fileChannel == null) {
                return;
            }
        } catch (Exception e2) {
            String str2 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("saveMorphoPanoramaOriginalPic  ");
            sb3.append(e2.toString());
            Log.e(str2, sb3.toString());
            if (fileChannel == null) {
                return;
            }
        } finally {
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (Exception unused) {
                }
            }
        }
        try {
            fileChannel.close();
        } catch (Exception unused2) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0079 A[SYNTHETIC, Splitter:B:22:0x0079] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0082 A[SYNTHETIC, Splitter:B:26:0x0082] */
    /* JADX WARNING: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    public static void saveOriginalPic(byte[] bArr, int i, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(DIRECTORY);
        sb.append(File.separator);
        sb.append(str);
        sb.append(File.separator);
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(File.separator);
        sb2.append(str);
        sb2.append("_");
        sb2.append(i);
        FileOutputStream fileOutputStream = null;
        try {
            File file2 = new File(generateFilepath4Jpeg(sb2.toString()));
            if (!file2.exists()) {
                file2.createNewFile();
            }
            FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
            try {
                fileOutputStream2.write(bArr);
            } catch (Exception e2) {
                e = e2;
                fileOutputStream = fileOutputStream2;
                try {
                    Log.e(TAG, "saveMorphoPanoramaOriginalPic exception occurs", e);
                    if (fileOutputStream == null) {
                    }
                } catch (Throwable th) {
                    th = th;
                    if (fileOutputStream != null) {
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (Exception unused) {
                    }
                }
                throw th;
            }
            try {
                fileOutputStream2.flush();
                fileOutputStream2.close();
            } catch (Exception unused2) {
            }
        } catch (Exception e3) {
            e = e3;
            Log.e(TAG, "saveMorphoPanoramaOriginalPic exception occurs", e);
            if (fileOutputStream == null) {
                fileOutputStream.flush();
                fileOutputStream.close();
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
        StringBuilder sb = new StringBuilder();
        sb.append("setLeftSpace(");
        sb.append(j);
        sb.append(")");
        Log.i(str, sb.toString());
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
            if (str.equals(str2)) {
                str2 = PRIMARY_STORAGE_PATH;
            }
            String str3 = sCurrentStoragePath;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            String str4 = CAMERA_STORAGE_PATH_SUFFIX;
            sb.append(str4);
            if (!isLowStorageSpace(sb.toString())) {
                sCurrentStoragePath = str;
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(str4);
                if (!isLowStorageSpace(sb2.toString())) {
                    sCurrentStoragePath = str2;
                } else {
                    return;
                }
            }
            if (!sCurrentStoragePath.equals(str3)) {
                updateDirectory();
                WeakReference<StorageListener> weakReference = sStorageListener;
                if (!(weakReference == null || weakReference.get() == null)) {
                    ((StorageListener) sStorageListener.get()).onStoragePathChanged();
                }
            }
            String str5 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Storage path is switched path = ");
            sb3.append(DIRECTORY);
            sb3.append(", FIRST_CONSIDER_STORAGE_PATH = ");
            sb3.append(FIRST_CONSIDER_STORAGE_PATH);
            sb3.append(", SECONDARY_STORAGE_PATH = ");
            sb3.append(SECONDARY_STORAGE_PATH);
            Log.d(str5, sb3.toString());
        }
    }

    public static void switchToPhoneStorage() {
        String str = PRIMARY_STORAGE_PATH;
        FIRST_CONSIDER_STORAGE_PATH = str;
        if (!str.equals(sCurrentStoragePath)) {
            Log.v(TAG, "switchToPhoneStorage");
            sCurrentStoragePath = PRIMARY_STORAGE_PATH;
            updateDirectory();
            WeakReference<StorageListener> weakReference = sStorageListener;
            if (weakReference != null && weakReference.get() != null) {
                ((StorageListener) sStorageListener.get()).onStoragePathChanged();
            }
        }
    }

    private static void updateDirectory() {
        StringBuilder sb = new StringBuilder();
        sb.append(sCurrentStoragePath);
        sb.append(CAMERA_STORAGE_PATH_SUFFIX);
        DIRECTORY = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(DIRECTORY);
        sb2.append(RAW_PATH_SUFFIX);
        RAW_DIRECTORY = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sCurrentStoragePath);
        sb3.append(HIDE_CAMERA_STORAGE_PATH_SUFFIX);
        HIDEDIRECTORY = sb3.toString();
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
                StringBuilder sb = new StringBuilder();
                sb.append("save algorithm: ");
                sb.append(str);
                Log.d(str2, sb.toString());
                exifInterface.addAlgorithmComment(str);
            }
            if (pictureInfo != null) {
                String str3 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("save xiaomi comment: ");
                sb2.append(pictureInfo.getInfoString());
                sb2.append(", aiType = ");
                sb2.append(pictureInfo.getAiType());
                Log.d(str3, sb2.toString());
                exifInterface.addAiType(pictureInfo.getAiType());
                if (pictureInfo.isBokehFrontCamera()) {
                    exifInterface.addFrontMirror(pictureInfo.isFrontMirror() ? 1 : 0);
                }
                exifInterface.addXiaomiComment(pictureInfo.getInfoString());
            }
            exifInterface.writeExif(bArr, (OutputStream) byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            bArr = byteArray;
        } catch (Exception e2) {
            String str4 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("updateExif error ");
            sb3.append(e2.getMessage());
            Log.e(str4, sb3.toString(), e2);
        }
        String str5 = TAG;
        StringBuilder sb4 = new StringBuilder();
        sb4.append("update exif cost=");
        sb4.append(System.currentTimeMillis() - currentTimeMillis);
        Log.v(str5, sb4.toString());
        return bArr;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:24|25|26|27) */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0086, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0087, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0089, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x008a, code lost:
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x008b, code lost:
        if (r12 != null) goto L_0x008d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        $closeResource(r1, r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0090, code lost:
        throw r2;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x0063 */
    public static boolean updateImage(Context context, byte[] bArr, ExifInterface exifInterface, Uri uri, String str, Location location, int i, int i2, int i3, String str2) {
        String str3;
        boolean z;
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
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(bArr2));
                try {
                    OutputStream fileOutputStream = isUseDocumentMode() ? FileCompat.getFileOutputStream(str3, true) : new BufferedOutputStream(new FileOutputStream(file));
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
                        exifInterface2.writeExif(bArr2, fileOutputStream);
                        Log.e(TAG, "Failed to rewrite Exif");
                        fileOutputStream.write(bArr2);
                    }
                    if (fileOutputStream != null) {
                        $closeResource(null, fileOutputStream);
                    }
                    $closeResource(null, bufferedInputStream);
                } catch (Throwable th) {
                    Throwable th2 = th;
                    try {
                        throw th2;
                    } catch (Throwable th3) {
                        Throwable th4 = th3;
                        $closeResource(th2, bufferedInputStream);
                        throw th4;
                    }
                }
            } catch (Exception e2) {
                Log.e(TAG, "Failed to write image", e2);
                return false;
            }
        } else if (str5 != null) {
            str3 = generateFilepath4Jpeg(str2);
        }
        long length = file.length();
        String str6 = "renameTo failed, tmpPath = ";
        if (!isUseDocumentMode()) {
            boolean renameTo = file.renameTo(new File(generateFilepath4Jpeg));
            if (!(exifInterface2 == null || str5 == null)) {
                try {
                    new File(generateFilepath4Jpeg(str2)).delete();
                } catch (Exception e3) {
                    String str7 = TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Exception when delete old file ");
                    sb2.append(str5);
                    Log.e(str7, sb2.toString(), e3);
                }
            }
            if (!renameTo) {
                String str8 = TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str6);
                sb3.append(str3);
                Log.w(str8, sb3.toString());
                return false;
            }
        }
        ContentValues contentValues = new ContentValues(10);
        contentValues.put("title", str4);
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str4);
        sb4.append(JPEG_SUFFIX);
        contentValues.put("_display_name", sb4.toString());
        String str9 = isUseDocumentMode() ? str3 : generateFilepath4Jpeg;
        String str10 = "_data";
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
            contentValues.put(str10, str9);
        } else if (str5 != null) {
            contentValues.put(str10, str9);
        }
        context.getContentResolver().update(uri, contentValues, null, null);
        if (!z3 && isUseDocumentMode()) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(generateFilepath4Jpeg);
            sb5.append(".mid");
            String sb6 = sb5.toString();
            String str11 = " to ";
            String str12 = "fail to rename ";
            if (renameSdcardFile(generateFilepath4Jpeg, sb6)) {
                z = renameSdcardFile(str3, generateFilepath4Jpeg);
                if (z) {
                    deleteSdcardFile(sb6);
                } else {
                    String str13 = TAG;
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append(str12);
                    sb7.append(str3);
                    sb7.append(str11);
                    sb7.append(generateFilepath4Jpeg);
                    Log.w(str13, sb7.toString());
                    deleteSdcardFile(str3);
                }
            } else {
                String str14 = TAG;
                StringBuilder sb8 = new StringBuilder();
                sb8.append(str12);
                sb8.append(generateFilepath4Jpeg);
                sb8.append(str11);
                sb8.append(sb6);
                Log.w(str14, sb8.toString());
                deleteSdcardFile(sb6);
                z = false;
            }
            FileCompat.removeDocumentFileForPath(str3);
            FileCompat.removeDocumentFileForPath(generateFilepath4Jpeg);
            if (!z) {
                String str15 = TAG;
                StringBuilder sb9 = new StringBuilder();
                sb9.append(str6);
                sb9.append(str3);
                Log.w(str15, sb9.toString());
                return false;
            }
        }
        long length2 = (long) bArr2.length;
        if (location == null) {
            z2 = true;
        }
        saveToCloudAlbum(context, generateFilepath4Jpeg, length2, z2);
        return true;
    }

    public static boolean updateImageSize(ContentResolver contentResolver, Uri uri, long j) {
        ContentValues contentValues = new ContentValues(1);
        contentValues.put("_size", Long.valueOf(j));
        try {
            contentResolver.update(uri, contentValues, null, null);
            return true;
        } catch (Exception e2) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to updateMediaStore");
            sb.append(e2);
            Log.e(str, sb.toString());
            return false;
        }
    }

    public static boolean updateImageWithExtraExif(Context context, byte[] bArr, ExifInterface exifInterface, Uri uri, String str, Location location, int i, int i2, int i3, String str2, boolean z, boolean z2, String str3, PictureInfo pictureInfo) {
        return updateImage(context, updateExif(bArr, z2, str3, pictureInfo, i, i2, i3), exifInterface, uri, str, location, i, i2, i3, str2);
    }
}
