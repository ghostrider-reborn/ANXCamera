package com.ss.android.vesdk;

import android.os.StatFs;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.ZipFile;

public class VEFileUtils {
    public static boolean canWrite(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        File file2 = new File(file, "." + System.currentTimeMillis());
        boolean mkdir = file2.mkdir();
        return mkdir ? file2.delete() : mkdir;
    }

    public static void clearPath(String str) {
        String str2;
        File file = new File(str);
        if (file.exists() && file.isDirectory()) {
            for (String str3 : file.list()) {
                if (str.endsWith(File.separator)) {
                    str2 = str + str3;
                } else {
                    str2 = str + File.separator + str3;
                }
                File file2 = new File(str2);
                if (file2.isFile()) {
                    file2.delete();
                }
                if (file2.isDirectory()) {
                    deletePath(str2);
                }
            }
        }
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static void close(ZipFile zipFile) {
        if (zipFile != null) {
            try {
                zipFile.close();
            } catch (IOException e) {
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.io.BufferedInputStream} */
    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v1, types: [java.io.BufferedOutputStream] */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0073 A[SYNTHETIC, Splitter:B:44:0x0073] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x007f A[SYNTHETIC, Splitter:B:49:0x007f] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x008e A[SYNTHETIC, Splitter:B:57:0x008e] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x009a A[SYNTHETIC, Splitter:B:62:0x009a] */
    public static boolean copyFile(File file, File file2) {
        BufferedInputStream bufferedInputStream;
        BufferedOutputStream bufferedOutputStream;
        if (!file.exists() || !file.isFile() || file2.isDirectory()) {
            return false;
        }
        if (file2.exists()) {
            file2.delete();
        }
        ? r2 = 0;
        try {
            byte[] bArr = new byte[2048];
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            try {
                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file2));
                while (true) {
                    try {
                        int read = bufferedInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        bufferedOutputStream.write(bArr, 0, read);
                    } catch (IOException e) {
                        e = e;
                    } catch (Throwable th) {
                        th = th;
                        r2 = bufferedOutputStream;
                        if (bufferedInputStream != null) {
                        }
                        if (r2 != 0) {
                        }
                        throw th;
                    }
                }
                bufferedOutputStream.flush();
                try {
                    bufferedInputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                try {
                    bufferedOutputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                return true;
            } catch (IOException e4) {
                e = e4;
                bufferedOutputStream = null;
                r2 = bufferedInputStream;
                try {
                    e.printStackTrace();
                    if (r2 != 0) {
                        try {
                            r2.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedInputStream = r2;
                    r2 = bufferedOutputStream;
                    if (bufferedInputStream != null) {
                    }
                    if (r2 != 0) {
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
                if (r2 != 0) {
                    try {
                        r2.close();
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (IOException e9) {
            e = e9;
            bufferedOutputStream = null;
            e.printStackTrace();
            if (r2 != 0) {
            }
            if (bufferedOutputStream != null) {
            }
            return false;
        } catch (Throwable th4) {
            th = th4;
            bufferedInputStream = null;
            if (bufferedInputStream != null) {
            }
            if (r2 != 0) {
            }
            throw th;
        }
    }

    public static boolean copyFile(String str, String str2) {
        return copyFile(new File(str), new File(str2));
    }

    public static void deleteFile(String str) {
        if (!TextUtils.isEmpty(str)) {
            File file = new File(str);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }
    }

    public static void deletePath(String str) {
        String str2;
        File file = new File(str);
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            String[] list = file.list();
            if (list != null) {
                for (String str3 : list) {
                    if (str3 != null) {
                        if (str.endsWith(File.separator)) {
                            str2 = str + str3;
                        } else {
                            str2 = str + File.separator + str3;
                        }
                        File file2 = new File(str2);
                        if (file2.isFile()) {
                            file2.delete();
                        }
                        if (file2.isDirectory()) {
                            deletePath(str2);
                        }
                    }
                }
                file.delete();
            }
        }
    }

    public static boolean exists(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return new File(str).exists();
    }

    public static long getAllBytes(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            if (!TextUtils.isEmpty(str) && new File(str).exists()) {
                StatFs statFs = new StatFs(str);
                return ((long) statFs.getBlockSize()) * ((long) statFs.getBlockCount());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getAvailableBytes(String str) {
        try {
            if (TextUtils.isEmpty(str) || !new File(str).exists()) {
                return 0;
            }
            StatFs statFs = new StatFs(str);
            return ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getFileExtension(String str) {
        int lastIndexOf = str.lastIndexOf(".");
        return (lastIndexOf < 0 || lastIndexOf >= str.length() + -1) ? "" : str.substring(lastIndexOf + 1).toUpperCase();
    }

    public static String getFileName(String str) {
        if (str.endsWith("/")) {
            str = str.substring(0, str.length() - 1);
        }
        int lastIndexOf = str.lastIndexOf("/");
        return (lastIndexOf <= 0 || lastIndexOf >= str.length() + -1) ? str : str.substring(lastIndexOf + 1);
    }

    public static String getFileNameWithoutExtension(String str) {
        String fileName = getFileName(str);
        if (fileName != null && fileName.length() > 0) {
            int lastIndexOf = fileName.lastIndexOf(46);
            if (lastIndexOf > 0) {
                return fileName.substring(0, lastIndexOf);
            }
        }
        return fileName;
    }

    public static long getFileSize(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        File file = new File(str);
        if (!file.exists()) {
            return 0;
        }
        long length = file.length();
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                int length2 = listFiles.length;
                int i = 0;
                while (i < length2) {
                    try {
                        length += getFileSize(listFiles[i].getAbsolutePath());
                        i++;
                    } catch (StackOverflowError e) {
                        e.printStackTrace();
                        return 0;
                    } catch (OutOfMemoryError e2) {
                        e2.printStackTrace();
                        return 0;
                    }
                }
            }
        }
        return length;
    }

    public static String getParentFilePath(String str) {
        if (str.endsWith("/")) {
            str = str.substring(0, str.length() - 1);
        }
        int lastIndexOf = str.lastIndexOf("/");
        if (lastIndexOf >= 0) {
            return str.substring(0, lastIndexOf);
        }
        return null;
    }

    public static boolean mkdir(String str) {
        return new File(str).mkdirs();
    }

    public static String readFileFirstLine(String str) {
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader(str));
            try {
                String readLine = bufferedReader2.readLine();
                bufferedReader2.close();
                try {
                    bufferedReader2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return readLine;
            } catch (Exception e2) {
                bufferedReader = bufferedReader2;
            } catch (Throwable th) {
                BufferedReader bufferedReader3 = bufferedReader2;
                if (bufferedReader3 != null) {
                    try {
                        bufferedReader3.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Exception e4) {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            return "";
        }
    }

    public static boolean renameFile(String str, String str2) {
        File file = new File(str);
        File file2 = new File(str2);
        if (!file.exists()) {
            return false;
        }
        return file.renameTo(file2);
    }

    public static void setPermissions(String str, int i) {
        VEJavaCalls.callStaticMethod("android.os.FileUtils", "setPermissions", str, Integer.valueOf(i), -1, -1);
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0035 A[SYNTHETIC, Splitter:B:21:0x0035] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0048 A[SYNTHETIC, Splitter:B:31:0x0048] */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:18:0x0030=Splitter:B:18:0x0030, B:24:0x003a=Splitter:B:24:0x003a} */
    public static void write(String str, String str2, boolean z) {
        FileWriter fileWriter = null;
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileWriter fileWriter2 = new FileWriter(str, z);
            try {
                fileWriter2.write(str2);
            } catch (IOException e) {
                e = e;
                fileWriter = fileWriter2;
            } catch (Throwable th) {
                th = th;
                fileWriter = fileWriter2;
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                    } catch (Exception e2) {
                    }
                }
                throw th;
            }
            try {
                fileWriter2.close();
            } catch (Exception e3) {
            }
        } catch (IOException e4) {
            e = e4;
            e.printStackTrace();
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (Throwable th2) {
            th = th2;
            th.printStackTrace();
            if (fileWriter == null) {
            }
        }
    }
}
