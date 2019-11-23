package com.ss.android.medialib;

import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtils {
    private static final String DEFAULT_FOLDER_NAME = "BDMedia";
    protected static String msFolderPath;

    public static boolean checkFileExists(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return new File(str).exists();
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

    /* JADX WARNING: type inference failed for: r0v1 */
    /* JADX WARNING: type inference failed for: r0v2, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r0v5, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r0v6, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: type inference failed for: r0v12 */
    /* JADX WARNING: type inference failed for: r0v13 */
    /* JADX WARNING: type inference failed for: r0v14 */
    /* JADX WARNING: type inference failed for: r0v16 */
    /* JADX WARNING: type inference failed for: r0v18 */
    /* JADX WARNING: type inference failed for: r0v20 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x0116 A[SYNTHETIC, Splitter:B:104:0x0116] */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0127 A[SYNTHETIC, Splitter:B:112:0x0127] */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x0133 A[SYNTHETIC, Splitter:B:117:0x0133] */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x013f A[SYNTHETIC, Splitter:B:122:0x013f] */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x014b A[SYNTHETIC, Splitter:B:127:0x014b] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00b9 A[SYNTHETIC, Splitter:B:64:0x00b9] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x00c5 A[SYNTHETIC, Splitter:B:69:0x00c5] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00d1 A[SYNTHETIC, Splitter:B:74:0x00d1] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00dd A[SYNTHETIC, Splitter:B:79:0x00dd] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x00f2 A[SYNTHETIC, Splitter:B:89:0x00f2] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x00fe A[SYNTHETIC, Splitter:B:94:0x00fe] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x010a A[SYNTHETIC, Splitter:B:99:0x010a] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:61:0x00b3=Splitter:B:61:0x00b3, B:86:0x00ec=Splitter:B:86:0x00ec} */
    public static boolean fileChannelCopy(String str, String str2) {
        FileChannel fileChannel;
        FileChannel fileChannel2;
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream2;
        FileOutputStream fileOutputStream2;
        FileOutputStream fileOutputStream3;
        if (!isSdcardWritable()) {
            return false;
        }
        ? r0 = 0;
        try {
            fileInputStream = new FileInputStream(str);
            try {
                fileOutputStream3 = new FileOutputStream(str2);
                try {
                    fileChannel = fileInputStream.getChannel();
                    try {
                        fileChannel2 = fileOutputStream3.getChannel();
                    } catch (FileNotFoundException e) {
                        fileChannel2 = null;
                        r0 = fileInputStream;
                        fileOutputStream2 = fileOutputStream3;
                        e = e;
                        fileInputStream2 = r0;
                        e.printStackTrace();
                        fileInputStream2 = r0;
                        if (r0 != 0) {
                        }
                        if (fileChannel != null) {
                        }
                        if (fileOutputStream != null) {
                        }
                        if (fileChannel2 != null) {
                        }
                        return false;
                    } catch (IOException e2) {
                        fileChannel2 = null;
                        r0 = fileInputStream;
                        fileOutputStream = fileOutputStream3;
                        e = e2;
                        try {
                            fileInputStream2 = r0;
                            e.printStackTrace();
                            fileInputStream2 = r0;
                            if (r0 != 0) {
                            }
                            if (fileChannel != null) {
                            }
                            if (fileOutputStream != null) {
                            }
                            if (fileChannel2 != null) {
                            }
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            FileOutputStream fileOutputStream4 = fileOutputStream;
                            fileInputStream = fileInputStream2;
                            r0 = fileOutputStream4;
                        }
                    } catch (Throwable th2) {
                        fileChannel2 = null;
                        r0 = fileOutputStream3;
                        th = th2;
                        if (fileInputStream != null) {
                        }
                        if (fileChannel != null) {
                        }
                        if (r0 != 0) {
                        }
                        if (fileChannel2 != null) {
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e3) {
                    fileChannel2 = null;
                    r0 = fileInputStream;
                    fileOutputStream2 = fileOutputStream3;
                    e = e3;
                    fileChannel = null;
                    fileInputStream2 = r0;
                    e.printStackTrace();
                    fileInputStream2 = r0;
                    if (r0 != 0) {
                        try {
                            r0.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (fileChannel != null) {
                        try {
                            fileChannel.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    if (fileChannel2 != null) {
                        try {
                            fileChannel2.close();
                        } catch (IOException e7) {
                            e7.printStackTrace();
                        }
                    }
                    return false;
                } catch (IOException e8) {
                    fileChannel2 = null;
                    r0 = fileInputStream;
                    fileOutputStream = fileOutputStream3;
                    e = e8;
                    fileChannel = null;
                    fileInputStream2 = r0;
                    e.printStackTrace();
                    fileInputStream2 = r0;
                    if (r0 != 0) {
                        try {
                            r0.close();
                        } catch (IOException e9) {
                            e9.printStackTrace();
                        }
                    }
                    if (fileChannel != null) {
                        try {
                            fileChannel.close();
                        } catch (IOException e10) {
                            e10.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e11) {
                            e11.printStackTrace();
                        }
                    }
                    if (fileChannel2 != null) {
                        try {
                            fileChannel2.close();
                        } catch (IOException e12) {
                            e12.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th3) {
                    fileChannel2 = null;
                    r0 = fileOutputStream3;
                    th = th3;
                    fileChannel = null;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e13) {
                            e13.printStackTrace();
                        }
                    }
                    if (fileChannel != null) {
                        try {
                            fileChannel.close();
                        } catch (IOException e14) {
                            e14.printStackTrace();
                        }
                    }
                    if (r0 != 0) {
                        try {
                            r0.close();
                        } catch (IOException e15) {
                            e15.printStackTrace();
                        }
                    }
                    if (fileChannel2 != null) {
                        try {
                            fileChannel2.close();
                        } catch (IOException e16) {
                            e16.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e17) {
                e = e17;
                fileChannel = null;
                fileChannel2 = null;
                r0 = fileInputStream;
                fileOutputStream2 = null;
                fileInputStream2 = r0;
                e.printStackTrace();
                fileInputStream2 = r0;
                if (r0 != 0) {
                }
                if (fileChannel != null) {
                }
                if (fileOutputStream != null) {
                }
                if (fileChannel2 != null) {
                }
                return false;
            } catch (IOException e18) {
                e = e18;
                fileChannel = null;
                fileChannel2 = null;
                r0 = fileInputStream;
                fileOutputStream = null;
                fileInputStream2 = r0;
                e.printStackTrace();
                fileInputStream2 = r0;
                if (r0 != 0) {
                }
                if (fileChannel != null) {
                }
                if (fileOutputStream != null) {
                }
                if (fileChannel2 != null) {
                }
                return false;
            } catch (Throwable th4) {
                th = th4;
                fileChannel = null;
                fileChannel2 = null;
                if (fileInputStream != null) {
                }
                if (fileChannel != null) {
                }
                if (r0 != 0) {
                }
                if (fileChannel2 != null) {
                }
                throw th;
            }
            try {
                fileChannel.transferTo(0, fileChannel.size(), fileChannel2);
                try {
                    fileInputStream.close();
                } catch (IOException e19) {
                    e19.printStackTrace();
                }
                if (fileChannel != null) {
                    try {
                        fileChannel.close();
                    } catch (IOException e20) {
                        e20.printStackTrace();
                    }
                }
                try {
                    fileOutputStream3.close();
                } catch (IOException e21) {
                    e21.printStackTrace();
                }
                if (fileChannel2 == null) {
                    return true;
                }
                try {
                    fileChannel2.close();
                    return true;
                } catch (IOException e22) {
                    e22.printStackTrace();
                    return true;
                }
            } catch (FileNotFoundException e23) {
                FileInputStream fileInputStream3 = fileInputStream;
                fileOutputStream2 = fileOutputStream3;
                e = e23;
                r0 = fileInputStream3;
            } catch (IOException e24) {
                FileInputStream fileInputStream4 = fileInputStream;
                fileOutputStream = fileOutputStream3;
                e = e24;
                r0 = fileInputStream4;
                fileInputStream2 = r0;
                e.printStackTrace();
                fileInputStream2 = r0;
                if (r0 != 0) {
                }
                if (fileChannel != null) {
                }
                if (fileOutputStream != null) {
                }
                if (fileChannel2 != null) {
                }
                return false;
            } catch (Throwable th5) {
                Throwable th6 = th5;
                r0 = fileOutputStream3;
                th = th6;
                if (fileInputStream != null) {
                }
                if (fileChannel != null) {
                }
                if (r0 != 0) {
                }
                if (fileChannel2 != null) {
                }
                throw th;
            }
        } catch (FileNotFoundException e25) {
            e = e25;
            fileChannel = null;
            fileOutputStream2 = null;
            fileChannel2 = null;
            fileInputStream2 = r0;
            e.printStackTrace();
            fileInputStream2 = r0;
            if (r0 != 0) {
            }
            if (fileChannel != null) {
            }
            if (fileOutputStream != null) {
            }
            if (fileChannel2 != null) {
            }
            return false;
        } catch (IOException e26) {
            e = e26;
            fileChannel = null;
            fileOutputStream = null;
            fileChannel2 = null;
            fileInputStream2 = r0;
            e.printStackTrace();
            fileInputStream2 = r0;
            if (r0 != 0) {
            }
            if (fileChannel != null) {
            }
            if (fileOutputStream != null) {
            }
            if (fileChannel2 != null) {
            }
            return false;
        } catch (Throwable th7) {
            th = th7;
            fileChannel = null;
            fileInputStream = null;
            fileChannel2 = null;
            if (fileInputStream != null) {
            }
            if (fileChannel != null) {
            }
            if (r0 != 0) {
            }
            if (fileChannel2 != null) {
            }
            throw th;
        }
    }

    public static String getPath() {
        if (msFolderPath == null) {
            msFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + DEFAULT_FOLDER_NAME;
            File file = new File(msFolderPath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return msFolderPath;
    }

    public static boolean isSdcardWritable() {
        try {
            return "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e) {
            return false;
        }
    }
}
