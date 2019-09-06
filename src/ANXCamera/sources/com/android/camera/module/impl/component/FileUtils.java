package com.android.camera.module.impl.component;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.text.TextUtils;
import com.android.camera.log.Log;
import com.android.camera.storage.Storage;
import com.ss.android.ugc.effectmanager.effect.model.ComposerHelper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {
    public static final String BEAUTY_12_DIR;
    public static final String BEAUTY_12_FILENAME = "Beauty_12.zip";
    public static final String CACHE;
    public static final String CONCAT_VIDEO_DIR;
    public static final String DOC;
    public static final String FACE_RESHAPE = "FaceReshape_V2.zip";
    public static final String FILTER;
    public static final String FILTER_DIR;
    public static final String MODELS_DIR;
    public static final String MUSIC;
    public static final String MUSIC_EFFECT_DIR = RESOURCE_DIR;
    public static final String MUSIC_LOCAL;
    public static final String MUSIC_ONLINE;
    public static final String PHONEPARAM = "phoneParams.txt";
    public static final String RESHAPE_DIR_NAME;
    public static final String RESOURCE_DIR;
    public static List<String> RESOURCE_LIST_CN = new ArrayList();
    public static List<String> RESOURCE_LIST_GLOBAL = new ArrayList();
    public static final String ROOT_DIR;
    public static final String STICKER_RESOURCE_DIR;
    public static final String SUFFIX = ".zip";
    public static final String TAG = "FileUtils";
    public static final String VIDEO_TMP;
    public static final List<String> musicList = new ArrayList();

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getPath());
        sb.append("/MIUI/Camera/");
        ROOT_DIR = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(ROOT_DIR);
        sb2.append("resource/");
        RESOURCE_DIR = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(RESOURCE_DIR);
        sb3.append("stickers/");
        STICKER_RESOURCE_DIR = sb3.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(ROOT_DIR);
        sb4.append("model/");
        MODELS_DIR = sb4.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(ROOT_DIR);
        sb5.append("tmp/");
        VIDEO_TMP = sb5.toString();
        StringBuilder sb6 = new StringBuilder();
        sb6.append(ROOT_DIR);
        sb6.append("concat/");
        CONCAT_VIDEO_DIR = sb6.toString();
        StringBuilder sb7 = new StringBuilder();
        sb7.append(ROOT_DIR);
        sb7.append("cache/");
        CACHE = sb7.toString();
        StringBuilder sb8 = new StringBuilder();
        sb8.append(ROOT_DIR);
        sb8.append("music/");
        MUSIC = sb8.toString();
        StringBuilder sb9 = new StringBuilder();
        sb9.append(ROOT_DIR);
        sb9.append("doc/");
        DOC = sb9.toString();
        StringBuilder sb10 = new StringBuilder();
        sb10.append(RESOURCE_DIR);
        sb10.append("filter/");
        FILTER_DIR = sb10.toString();
        StringBuilder sb11 = new StringBuilder();
        sb11.append(FILTER_DIR);
        sb11.append("Filter_02/");
        FILTER = sb11.toString();
        StringBuilder sb12 = new StringBuilder();
        sb12.append(RESOURCE_DIR);
        sb12.append("Beauty_12/");
        BEAUTY_12_DIR = sb12.toString();
        StringBuilder sb13 = new StringBuilder();
        sb13.append(RESOURCE_DIR);
        sb13.append("FaceReshape_V2/");
        RESHAPE_DIR_NAME = sb13.toString();
        StringBuilder sb14 = new StringBuilder();
        sb14.append(MUSIC);
        sb14.append("local/");
        MUSIC_LOCAL = sb14.toString();
        StringBuilder sb15 = new StringBuilder();
        sb15.append(MUSIC);
        sb15.append("online/");
        MUSIC_ONLINE = sb15.toString();
        RESOURCE_LIST_CN.add("0eb0e0214f7bc7f7bbfb4e9f4dba7f99");
        RESOURCE_LIST_CN.add("a75682e81788cc12f68682b9c9067f70");
        String str = "24991e783f23920397ac8aeed15994c2";
        RESOURCE_LIST_CN.add(str);
        RESOURCE_LIST_GLOBAL.add(str);
        RESOURCE_LIST_GLOBAL.add("9b74385fe7e8cb81e1b88ce3b293bdf2");
        RESOURCE_LIST_GLOBAL.add("0a673064d64fce91ee41b405c6f74dca");
        musicList.add("music00001.mp3");
        musicList.add("music00002.mp3");
        musicList.add("music00003.mp3");
        musicList.add("music00004.mp3");
    }

    public static String GetFileName(String str) {
        int lastIndexOf = str.lastIndexOf("/");
        int lastIndexOf2 = str.lastIndexOf(".");
        if (lastIndexOf2 != -1) {
            return str.substring(lastIndexOf + 1, lastIndexOf2);
        }
        return null;
    }

    public static void UnZipAssetFolder(Context context, String str, String str2) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("live/");
        sb.append(str);
        String sb2 = sb.toString();
        InputStream open = context.getAssets().open(sb2);
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        } else if (file.isFile()) {
            file.delete();
            file.mkdirs();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str2);
        sb3.append(File.separator);
        sb3.append(GetFileName(sb2));
        File file2 = new File(sb3.toString());
        if (file2.exists()) {
            deleteDir(file2);
        }
        file2.mkdirs();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str2);
        sb4.append(GetFileName(sb2));
        String sb5 = sb4.toString();
        open.close();
        UnZipFolder(context.getAssets().open(sb2), sb5);
    }

    public static int UnZipFileFolder(String str, String str2) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(str));
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        } else if (file.isFile()) {
            file.delete();
            file.mkdirs();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(File.separator);
        sb.append(GetFileName(str));
        File file2 = new File(sb.toString());
        if (file2.exists()) {
            deleteDir(file2);
        }
        file2.mkdirs();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append(File.separator);
        sb2.append(GetFileName(str));
        String sb3 = sb2.toString();
        fileInputStream.close();
        UnZipFolder(new FileInputStream(new File(str)), sb3);
        return 0;
    }

    private static void UnZipFolder(InputStream inputStream, String str) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry == null) {
                break;
            }
            String name = nextEntry.getName();
            if (nextEntry.isDirectory()) {
                String substring = name.substring(0, name.length() - 1);
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(File.separator);
                sb.append(substring);
                File file = new File(sb.toString());
                if (!file.exists()) {
                    file.mkdirs();
                }
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(File.separator);
                sb2.append(name);
                File file2 = new File(sb2.toString());
                if (file2.exists()) {
                    break;
                }
                file2.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = zipInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                    fileOutputStream.flush();
                }
                fileOutputStream.close();
            }
        }
        zipInputStream.close();
    }

    public static boolean checkFileConsist(String str) {
        File file = new File(str);
        return file.exists() && !file.isDirectory();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:10|11|12|13|14|20|15) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0072 */
    public static boolean copyAssetsDirectory(Context context, String str, String str2) {
        String[] list;
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        AssetManager assets = context.getAssets();
        try {
            if (str.endsWith("/")) {
                str = str.substring(0, str.length() - 1);
            }
            for (String str3 : assets.list(str)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(File.separator);
                sb.append(str3);
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str2);
                sb3.append(File.separator);
                sb3.append(str3);
                String sb4 = sb3.toString();
                InputStream open = assets.open(sb2);
                StringBuilder sb5 = new StringBuilder();
                sb5.append(str2);
                sb5.append(File.separator);
                copyFileIfNeed(context, sb5.toString(), sb2, str3);
                open.close();
                copyAssetsDirectory(context, sb2, sb4);
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return true;
    }

    public static void copyFile(File file, File file2) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        FileChannel channel = fileInputStream.getChannel();
        channel.transferTo(0, channel.size(), fileOutputStream.getChannel());
        fileInputStream.close();
        fileOutputStream.close();
    }

    public static boolean copyFileIfNeed(Context context, String str, String str2) {
        return copyFileIfNeed(context, str, str2, str2);
    }

    private static boolean copyFileIfNeed(Context context, String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append("live/");
        sb.append(str2);
        String sb2 = sb.toString();
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(str3);
        String sb4 = sb3.toString();
        if (!sb4.isEmpty()) {
            File file2 = new File(sb4);
            if (!file2.exists()) {
                try {
                    file2.createNewFile();
                    InputStream open = context.getApplicationContext().getAssets().open(sb2);
                    if (open == null) {
                        return false;
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int read = open.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, read);
                    }
                    open.close();
                    fileOutputStream.close();
                } catch (IOException unused) {
                    file2.delete();
                    return false;
                }
            }
        }
        return true;
    }

    public static synchronized String createtFileName(String str, String str2) {
        String sb;
        synchronized (FileUtils.class) {
            String format = new SimpleDateFormat("MMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(format);
            sb2.append('.');
            sb2.append(str2);
            sb = sb2.toString();
        }
        return sb;
    }

    public static boolean delDir(String str) {
        boolean z = false;
        if (str.isEmpty()) {
            return false;
        }
        File file = new File(str);
        if (!file.isFile() && file.exists() && deleteDir(file)) {
            z = true;
        }
        return z;
    }

    private static boolean deleteDir(File file) {
        if (file.isDirectory()) {
            String[] list = file.list();
            for (String file2 : list) {
                if (!deleteDir(new File(file, file2))) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static boolean deleteFile(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return file.delete();
        }
        for (File deleteFile : file.listFiles()) {
            if (!deleteFile(deleteFile)) {
                return false;
            }
        }
        return file.delete();
    }

    public static boolean deleteFile(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return deleteFile(new File(str));
    }

    public static boolean deleteSubFiles(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (!file.exists() || !file.isDirectory()) {
            return false;
        }
        for (File delete : file.listFiles()) {
            delete.delete();
        }
        return true;
    }

    private static void doDeleteEmptyDir(String str) {
        if (new File(str).delete()) {
            PrintStream printStream = System.out;
            StringBuilder sb = new StringBuilder();
            sb.append("Successfully deleted empty directory: ");
            sb.append(str);
            printStream.println(sb.toString());
            return;
        }
        PrintStream printStream2 = System.out;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Failed to delete empty directory: ");
        sb2.append(str);
        printStream2.println(sb2.toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x007c A[SYNTHETIC, Splitter:B:53:0x007c] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0086 A[SYNTHETIC, Splitter:B:58:0x0086] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0092 A[SYNTHETIC, Splitter:B:65:0x0092] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x009c A[SYNTHETIC, Splitter:B:70:0x009c] */
    public static byte[] getFileBytes(String str) {
        FileInputStream fileInputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(str);
        if (!file.exists() || file.length() <= 0) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                byte[] bArr = new byte[1024];
                byteArrayOutputStream = null;
                while (true) {
                    try {
                        int read = fileInputStream.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        if (byteArrayOutputStream == null) {
                            byteArrayOutputStream = new ByteArrayOutputStream();
                        }
                        byteArrayOutputStream.write(bArr, 0, read);
                    } catch (Exception e2) {
                        e = e2;
                        try {
                            e.printStackTrace();
                            if (byteArrayOutputStream != null) {
                            }
                            if (fileInputStream != null) {
                            }
                            return null;
                        } catch (Throwable th) {
                            th = th;
                            if (byteArrayOutputStream != null) {
                            }
                            if (fileInputStream != null) {
                            }
                            throw th;
                        }
                    }
                }
                if (byteArrayOutputStream != null) {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    try {
                        fileInputStream.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                    return byteArray;
                }
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                try {
                    fileInputStream.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
                return null;
            } catch (Exception e7) {
                e = e7;
                byteArrayOutputStream = null;
                e.printStackTrace();
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e9) {
                        e9.printStackTrace();
                    }
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                byteArrayOutputStream = null;
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e10) {
                        e10.printStackTrace();
                    }
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e11) {
                        e11.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Exception e12) {
            e = e12;
            fileInputStream = null;
            byteArrayOutputStream = null;
            e.printStackTrace();
            if (byteArrayOutputStream != null) {
            }
            if (fileInputStream != null) {
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
            byteArrayOutputStream = null;
            if (byteArrayOutputStream != null) {
            }
            if (fileInputStream != null) {
            }
            throw th;
        }
    }

    public static File getOutputMediaFile() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        if (file.exists() || file.mkdirs()) {
            String format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINESE).format(new Date());
            StringBuilder sb = new StringBuilder();
            sb.append(file.getPath());
            sb.append(File.separator);
            sb.append("IMG_");
            sb.append(format);
            sb.append(Storage.JPEG_SUFFIX);
            return new File(sb.toString());
        }
        Log.e("FileUtil", "failed to create directory");
        return null;
    }

    public static boolean hasDir(String str) {
        File file = new File(str);
        return file.exists() && file.isDirectory();
    }

    private static boolean hasParentDir(InputStream inputStream) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        boolean z = true;
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry == null) {
                zipInputStream.close();
                return z;
            } else if (nextEntry.getName().equals(ComposerHelper.CONFIG_FILE_NAME)) {
                z = false;
            }
        }
    }

    public static boolean makeDir(String str) {
        if (str.isEmpty()) {
            return false;
        }
        File file = new File(str);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return false;
    }

    public static void makeSureNoMedia(String str) {
        File file = new File(str, Storage.AVOID_SCAN_FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void saveBitmap(Bitmap bitmap, String str) {
        String str2 = TAG;
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            StringBuilder sb = new StringBuilder();
            sb.append("saveBitmap: ");
            sb.append(file.getAbsolutePath());
            Log.d(str2, sb.toString());
        } catch (IOException e2) {
            e2.printStackTrace();
            Log.d(str2, "saveBitmap: return");
        }
    }
}
