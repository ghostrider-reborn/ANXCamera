package com.ss.android.ugc.effectmanager.common.utils;

import android.os.Environment;
import android.text.TextUtils;
import com.ss.android.ugc.effectmanager.common.exception.UnzipException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {
    public static boolean checkFileExists(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return new File(str).exists();
    }

    public static String combineFilePath(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        sb.append(str);
        if (TextUtils.isEmpty(str2)) {
            str2 = "";
        }
        sb.append(str2);
        return sb.toString();
    }

    public static File createFile(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(str);
        if (!file.exists()) {
            if (!z) {
                file.mkdirs();
            } else {
                try {
                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    public static boolean ensureDirExists(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.exists();
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0060 A[SYNTHETIC, Splitter:B:32:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x006a A[SYNTHETIC, Splitter:B:37:0x006a] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0072 A[SYNTHETIC, Splitter:B:42:0x0072] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x007c A[SYNTHETIC, Splitter:B:47:0x007c] */
    public static String getFileContent(String str) {
        FileReader fileReader;
        BufferedReader bufferedReader;
        Exception e;
        File file = new File(str);
        if (!checkFileExists(file.getPath())) {
            return "";
        }
        String str2 = "";
        try {
            fileReader = new FileReader(file);
            try {
                bufferedReader = new BufferedReader(fileReader);
                while (true) {
                    try {
                        if (bufferedReader.readLine() != null) {
                            str2 = str2 + r1;
                        } else {
                            try {
                                break;
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                        try {
                            e.printStackTrace();
                            if (fileReader != null) {
                            }
                            if (bufferedReader != null) {
                            }
                            return str2;
                        } catch (Throwable th) {
                            th = th;
                            if (fileReader != null) {
                            }
                            if (bufferedReader != null) {
                            }
                            throw th;
                        }
                    }
                }
                fileReader.close();
            } catch (Exception e4) {
                e = e4;
                bufferedReader = null;
                e.printStackTrace();
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                return str2;
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
                throw th;
            }
            try {
                bufferedReader.close();
            } catch (IOException e8) {
                e8.printStackTrace();
            }
        } catch (Exception e9) {
            fileReader = null;
            e = e9;
            bufferedReader = null;
            e.printStackTrace();
            if (fileReader != null) {
            }
            if (bufferedReader != null) {
            }
            return str2;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
            fileReader = null;
            if (fileReader != null) {
            }
            if (bufferedReader != null) {
            }
            throw th;
        }
        return str2;
    }

    public static InputStream getFileStream(String str) {
        File file = new File(str);
        if (!checkFileExists(file.getPath())) {
            return null;
        }
        try {
            return new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isSdcardAvailable() {
        String externalStorageState = Environment.getExternalStorageState();
        return "mounted".equals(externalStorageState) || "mounted_ro".equals(externalStorageState);
    }

    public static boolean isSdcardWritable() {
        try {
            return "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e) {
            return false;
        }
    }

    public static void removeDir(File file) {
        if (file != null && file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length != 0) {
                for (File file2 : listFiles) {
                    if (file2.isDirectory()) {
                        removeDir(file2);
                    } else {
                        file2.delete();
                    }
                }
                file.delete();
            }
        }
    }

    public static void removeDir(String str) {
        removeDir(new File(str));
    }

    public static boolean removeFile(String str) {
        if (!isSdcardWritable() || TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        return file.exists() && file.canWrite() && file.delete();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: java.io.BufferedOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.io.BufferedOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.io.BufferedOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.io.BufferedOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.io.BufferedOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: java.io.BufferedOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: java.util.zip.ZipInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: java.io.BufferedOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: java.io.BufferedOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: java.io.BufferedOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: java.io.BufferedOutputStream} */
    /* JADX WARNING: type inference failed for: r1v0, types: [java.util.zip.ZipInputStream] */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00c0 A[SYNTHETIC, Splitter:B:46:0x00c0] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00c8 A[Catch:{ IOException -> 0x00c4 }] */
    /* JADX WARNING: Unknown variable types count: 1 */
    public static void unZip(String str, String str2) throws UnzipException {
        ? r1;
        BufferedOutputStream bufferedOutputStream;
        BufferedOutputStream bufferedOutputStream2;
        BufferedOutputStream bufferedOutputStream3 = null;
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(str)));
            while (true) {
                try {
                    ZipEntry nextEntry = zipInputStream.getNextEntry();
                    if (nextEntry != null) {
                        String name = nextEntry.getName();
                        if (nextEntry.isDirectory()) {
                            new File(str2 + File.separator + name).mkdirs();
                        } else {
                            File file = new File(str2 + File.separator + name);
                            if (file.exists()) {
                                file.delete();
                            } else {
                                file.getParentFile().mkdirs();
                            }
                            file.createNewFile();
                            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                            try {
                                byte[] bArr = new byte[2048];
                                while (true) {
                                    int read = zipInputStream.read(bArr);
                                    if (read == -1) {
                                        break;
                                    }
                                    bufferedOutputStream.write(bArr, 0, read);
                                }
                                bufferedOutputStream.flush();
                                bufferedOutputStream.close();
                                bufferedOutputStream3 = bufferedOutputStream;
                            } catch (IOException e) {
                                e = e;
                                bufferedOutputStream3 = zipInputStream;
                                try {
                                    throw new UnzipException(e.getMessage());
                                } catch (Throwable th) {
                                    th = th;
                                    bufferedOutputStream2 = bufferedOutputStream3;
                                    bufferedOutputStream3 = bufferedOutputStream;
                                    r1 = bufferedOutputStream2;
                                    if (r1 != 0) {
                                    }
                                    if (bufferedOutputStream3 != null) {
                                    }
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                bufferedOutputStream2 = zipInputStream;
                                bufferedOutputStream3 = bufferedOutputStream;
                                r1 = bufferedOutputStream2;
                                if (r1 != 0) {
                                }
                                if (bufferedOutputStream3 != null) {
                                }
                                throw th;
                            }
                        }
                    } else {
                        try {
                            break;
                        } catch (IOException e2) {
                            e2.printStackTrace();
                            return;
                        }
                    }
                } catch (IOException e3) {
                    e = e3;
                    bufferedOutputStream = bufferedOutputStream3;
                    bufferedOutputStream3 = zipInputStream;
                    throw new UnzipException(e.getMessage());
                } catch (Throwable th3) {
                    th = th3;
                    r1 = zipInputStream;
                    if (r1 != 0) {
                        try {
                            r1.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                            throw th;
                        }
                    }
                    if (bufferedOutputStream3 != null) {
                        bufferedOutputStream3.close();
                    }
                    throw th;
                }
            }
            zipInputStream.close();
            if (bufferedOutputStream3 != null) {
                bufferedOutputStream3.close();
            }
        } catch (IOException e5) {
            e = e5;
            bufferedOutputStream = null;
            throw new UnzipException(e.getMessage());
        } catch (Throwable th4) {
            th = th4;
            r1 = 0;
            if (r1 != 0) {
            }
            if (bufferedOutputStream3 != null) {
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0045 A[SYNTHETIC, Splitter:B:32:0x0045] */
    public static void writeToExternal(String str, String str2) {
        synchronized (FileUtils.class) {
            File file = new File(str2);
            if (!file.exists()) {
                createFile(file.getPath(), true);
            }
            FileOutputStream fileOutputStream = null;
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    fileOutputStream2.write(str.getBytes());
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e) {
                        e = e;
                    }
                } catch (Exception e2) {
                    e = e2;
                    fileOutputStream = fileOutputStream2;
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                try {
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                            e = e4;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    throw th;
                }
            }
        }
        e.printStackTrace();
    }
}
