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
        StringBuilder sb = new StringBuilder();
        sb.append(".");
        sb.append(System.currentTimeMillis());
        File file2 = new File(file, sb.toString());
        boolean mkdir = file2.mkdir();
        if (mkdir) {
            mkdir = file2.delete();
        }
        return mkdir;
    }

    public static void clearPath(String str) {
        String[] list;
        String str2;
        File file = new File(str);
        if (file.exists() && file.isDirectory()) {
            for (String str3 : file.list()) {
                if (str.endsWith(File.separator)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(str3);
                    str2 = sb.toString();
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(File.separator);
                    sb2.append(str3);
                    str2 = sb2.toString();
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
            } catch (IOException unused) {
            }
        }
    }

    public static void close(ZipFile zipFile) {
        if (zipFile != null) {
            try {
                zipFile.close();
            } catch (IOException unused) {
            }
        }
    }

    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r3v0, types: [java.io.BufferedInputStream] */
    /* JADX WARNING: type inference failed for: r2v1, types: [java.io.BufferedOutputStream] */
    /* JADX WARNING: type inference failed for: r5v3 */
    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r5v4, types: [java.io.BufferedOutputStream] */
    /* JADX WARNING: type inference failed for: r2v3, types: [java.io.BufferedInputStream] */
    /* JADX WARNING: type inference failed for: r3v2 */
    /* JADX WARNING: type inference failed for: r5v6 */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r3v4, types: [java.io.BufferedInputStream] */
    /* JADX WARNING: type inference failed for: r5v7 */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r5v8 */
    /* JADX WARNING: type inference failed for: r5v9, types: [java.io.BufferedOutputStream] */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r5v11 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r3v7 */
    /* JADX WARNING: type inference failed for: r5v12 */
    /* JADX WARNING: type inference failed for: r5v13 */
    /* JADX WARNING: type inference failed for: r5v14 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v1
  assigns: []
  uses: []
  mth insns count: 69
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x006a A[SYNTHETIC, Splitter:B:44:0x006a] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0074 A[SYNTHETIC, Splitter:B:49:0x0074] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0082 A[SYNTHETIC, Splitter:B:57:0x0082] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x008c A[SYNTHETIC, Splitter:B:62:0x008c] */
    /* JADX WARNING: Unknown variable types count: 10 */
    public static boolean copyFile(File file, File file2) {
        ? r3;
        ? r2;
        ? r5;
        ? r32;
        ? r52;
        ? r22;
        ? r53;
        if (!file.exists() || !file.isFile() || file2.isDirectory()) {
            return false;
        }
        if (file2.exists()) {
            file2.delete();
        }
        ? r23 = 0;
        try {
            byte[] bArr = new byte[2048];
            ? bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            try {
                ? bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file2));
                while (true) {
                    try {
                        int read = bufferedInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        bufferedOutputStream.write(bArr, 0, read);
                    } catch (IOException e2) {
                        e = e2;
                        r53 = bufferedOutputStream;
                    } catch (Throwable th) {
                        th = th;
                        r32 = bufferedInputStream;
                        r5 = bufferedOutputStream;
                        r2 = r5;
                        r3 = r32;
                        if (r3 != 0) {
                        }
                        if (r2 != 0) {
                        }
                        throw th;
                    }
                }
                bufferedOutputStream.flush();
                try {
                    bufferedInputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                try {
                    bufferedOutputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                return true;
            } catch (IOException e5) {
                e = e5;
                r53 = 0;
                r22 = bufferedInputStream;
                r52 = r53;
                try {
                    e.printStackTrace();
                    if (r22 != 0) {
                        try {
                            r22.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    if (r52 != 0) {
                        try {
                            r52.close();
                        } catch (IOException e7) {
                            e7.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    r32 = r22;
                    r5 = r52;
                    r2 = r5;
                    r3 = r32;
                    if (r3 != 0) {
                    }
                    if (r2 != 0) {
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                r2 = r23;
                r3 = bufferedInputStream;
                if (r3 != 0) {
                    try {
                        r3.close();
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                }
                if (r2 != 0) {
                    try {
                        r2.close();
                    } catch (IOException e9) {
                        e9.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (IOException e10) {
            e = e10;
            r52 = 0;
            r22 = r23;
            e.printStackTrace();
            if (r22 != 0) {
            }
            if (r52 != 0) {
            }
            return false;
        } catch (Throwable th4) {
            th = th4;
            r3 = 0;
            r2 = r23;
            if (r3 != 0) {
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
                            StringBuilder sb = new StringBuilder();
                            sb.append(str);
                            sb.append(str3);
                            str2 = sb.toString();
                        } else {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(str);
                            sb2.append(File.separator);
                            sb2.append(str3);
                            str2 = sb2.toString();
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
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return 0;
    }

    public static long getAvailableBytes(String str) {
        try {
            if (!TextUtils.isEmpty(str) && new File(str).exists()) {
                StatFs statFs = new StatFs(str);
                return ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return 0;
    }

    public static String getFileExtension(String str) {
        int lastIndexOf = str.lastIndexOf(".");
        return (lastIndexOf < 0 || lastIndexOf >= str.length() + -1) ? "" : str.substring(lastIndexOf + 1).toUpperCase();
    }

    public static String getFileName(String str) {
        String str2 = "/";
        if (str.endsWith(str2)) {
            str = str.substring(0, str.length() - 1);
        }
        int lastIndexOf = str.lastIndexOf(str2);
        return (lastIndexOf <= 0 || lastIndexOf >= str.length() + -1) ? str : str.substring(lastIndexOf + 1);
    }

    public static String getFileNameWithoutExtension(String str) {
        String fileName = getFileName(str);
        if (fileName == null || fileName.length() <= 0) {
            return fileName;
        }
        int lastIndexOf = fileName.lastIndexOf(46);
        return lastIndexOf > 0 ? fileName.substring(0, lastIndexOf) : fileName;
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
                int i = 0;
                while (i < listFiles.length) {
                    try {
                        length += getFileSize(listFiles[i].getAbsolutePath());
                        i++;
                    } catch (StackOverflowError e2) {
                        e2.printStackTrace();
                        return 0;
                    } catch (OutOfMemoryError e3) {
                        e3.printStackTrace();
                        return 0;
                    }
                }
            }
        }
        return length;
    }

    public static String getParentFilePath(String str) {
        String str2 = "/";
        if (str.endsWith(str2)) {
            str = str.substring(0, str.length() - 1);
        }
        int lastIndexOf = str.lastIndexOf(str2);
        if (lastIndexOf >= 0) {
            return str.substring(0, lastIndexOf);
        }
        return null;
    }

    public static boolean mkdir(String str) {
        return new File(str).mkdirs();
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0026 A[SYNTHETIC, Splitter:B:17:0x0026] */
    public static String readFileFirstLine(String str) {
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader(str));
            try {
                String readLine = bufferedReader2.readLine();
                bufferedReader2.close();
                try {
                    bufferedReader2.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                return readLine;
            } catch (Exception unused) {
                bufferedReader = bufferedReader2;
                String str2 = "";
                if (bufferedReader != null) {
                }
                return str2;
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
        } catch (Exception unused2) {
            String str22 = "";
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            return str22;
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
        Integer valueOf = Integer.valueOf(-1);
        VEJavaCalls.callStaticMethod("android.os.FileUtils", "setPermissions", str, Integer.valueOf(i), valueOf, valueOf);
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:21:0x0033=Splitter:B:21:0x0033, B:15:0x0029=Splitter:B:15:0x0029} */
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
                try {
                    fileWriter2.close();
                } catch (Exception unused) {
                }
            } catch (IOException e2) {
                e = e2;
                fileWriter = fileWriter2;
                e.printStackTrace();
                if (fileWriter == null) {
                }
                fileWriter.close();
            } catch (Throwable th) {
                th = th;
                fileWriter = fileWriter2;
                try {
                    th.printStackTrace();
                    fileWriter.close();
                } finally {
                    if (fileWriter != null) {
                        try {
                            fileWriter.close();
                        } catch (Exception unused2) {
                        }
                    }
                }
            }
        } catch (IOException e3) {
            e = e3;
            e.printStackTrace();
            if (fileWriter == null) {
                return;
            }
            fileWriter.close();
        } catch (Throwable th2) {
            th = th2;
            th.printStackTrace();
            if (fileWriter == null) {
                return;
            }
            fileWriter.close();
        }
    }
}
