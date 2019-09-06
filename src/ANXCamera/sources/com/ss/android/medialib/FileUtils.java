package com.ss.android.medialib;

import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return file;
    }

    /* JADX WARNING: type inference failed for: r0v1 */
    /* JADX WARNING: type inference failed for: r11v1, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r0v2, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r11v3 */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r9v0 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r11v4, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r2v4, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r0v5, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r11v6 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r11v7 */
    /* JADX WARNING: type inference failed for: r11v8, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r2v6, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r0v8, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r11v10 */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r11v11 */
    /* JADX WARNING: type inference failed for: r11v12 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r2v9, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r11v13 */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: type inference failed for: r11v14 */
    /* JADX WARNING: type inference failed for: r11v15 */
    /* JADX WARNING: type inference failed for: r0v12 */
    /* JADX WARNING: type inference failed for: r11v16 */
    /* JADX WARNING: type inference failed for: r11v17 */
    /* JADX WARNING: type inference failed for: r11v18 */
    /* JADX WARNING: type inference failed for: r11v19 */
    /* JADX WARNING: type inference failed for: r11v20 */
    /* JADX WARNING: type inference failed for: r11v21, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r0v13 */
    /* JADX WARNING: type inference failed for: r9v1 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r0v14 */
    /* JADX WARNING: type inference failed for: r0v15 */
    /* JADX WARNING: type inference failed for: r9v2 */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r0v16 */
    /* JADX WARNING: type inference failed for: r0v17 */
    /* JADX WARNING: type inference failed for: r0v18, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r3v14, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r8v0, types: [java.nio.channels.WritableByteChannel] */
    /* JADX WARNING: type inference failed for: r0v19 */
    /* JADX WARNING: type inference failed for: r0v20 */
    /* JADX WARNING: type inference failed for: r0v21 */
    /* JADX WARNING: type inference failed for: r0v22 */
    /* JADX WARNING: type inference failed for: r0v23 */
    /* JADX WARNING: type inference failed for: r0v24 */
    /* JADX WARNING: type inference failed for: r0v25 */
    /* JADX WARNING: type inference failed for: r0v26 */
    /* JADX WARNING: type inference failed for: r11v23 */
    /* JADX WARNING: type inference failed for: r11v24 */
    /* JADX WARNING: type inference failed for: r11v25 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r0v27 */
    /* JADX WARNING: type inference failed for: r0v28 */
    /* JADX WARNING: type inference failed for: r11v26 */
    /* JADX WARNING: type inference failed for: r0v29 */
    /* JADX WARNING: type inference failed for: r11v27 */
    /* JADX WARNING: type inference failed for: r11v28 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: type inference failed for: r2v15 */
    /* JADX WARNING: type inference failed for: r0v30 */
    /* JADX WARNING: type inference failed for: r0v31 */
    /* JADX WARNING: type inference failed for: r11v29 */
    /* JADX WARNING: type inference failed for: r0v32 */
    /* JADX WARNING: type inference failed for: r2v16 */
    /* JADX WARNING: type inference failed for: r2v17 */
    /* JADX WARNING: type inference failed for: r2v18 */
    /* JADX WARNING: type inference failed for: r11v30 */
    /* JADX WARNING: type inference failed for: r11v31 */
    /* JADX WARNING: type inference failed for: r11v32 */
    /* JADX WARNING: type inference failed for: r11v33 */
    /* JADX WARNING: type inference failed for: r11v34 */
    /* JADX WARNING: type inference failed for: r0v33 */
    /* JADX WARNING: type inference failed for: r0v34 */
    /* JADX WARNING: type inference failed for: r0v35 */
    /* JADX WARNING: type inference failed for: r0v36 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r11v3
  assigns: []
  uses: []
  mth insns count: 160
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
    /* JADX WARNING: Removed duplicated region for block: B:100:0x00d0 A[SYNTHETIC, Splitter:B:100:0x00d0] */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x00df A[SYNTHETIC, Splitter:B:108:0x00df] */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x00e9 A[SYNTHETIC, Splitter:B:113:0x00e9] */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x00f3 A[SYNTHETIC, Splitter:B:118:0x00f3] */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x00fd A[SYNTHETIC, Splitter:B:123:0x00fd] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0082 A[SYNTHETIC, Splitter:B:59:0x0082] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x008c A[SYNTHETIC, Splitter:B:64:0x008c] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0096 A[SYNTHETIC, Splitter:B:69:0x0096] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00a0 A[SYNTHETIC, Splitter:B:74:0x00a0] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x00b2 A[SYNTHETIC, Splitter:B:85:0x00b2] */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x00bc A[SYNTHETIC, Splitter:B:90:0x00bc] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x00c6 A[SYNTHETIC, Splitter:B:95:0x00c6] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:56:0x007d=Splitter:B:56:0x007d, B:82:0x00ad=Splitter:B:82:0x00ad} */
    /* JADX WARNING: Unknown variable types count: 32 */
    public static boolean fileChannelCopy(String str, String str2) {
        ? r11;
        FileOutputStream fileOutputStream;
        ? r2;
        ? r0;
        ? r112;
        ? r22;
        ? r02;
        ? r113;
        ? r23;
        ? r03;
        ? r114;
        ? r04;
        ? r24;
        ? r115;
        ? r25;
        ? r05;
        ? r116;
        ? r06;
        ? r26;
        ? r117;
        ? r118;
        ? r07;
        ? r08;
        ? r09;
        if (!isSdcardWritable()) {
            return false;
        }
        ? r010 = 0;
        try {
            ? fileInputStream = new FileInputStream(str);
            try {
                fileOutputStream = new FileOutputStream(str2);
                try {
                    ? channel = fileInputStream.getChannel();
                    try {
                        r07 = r010;
                        r08 = r010;
                        r09 = r010;
                        ? channel2 = fileOutputStream.getChannel();
                        channel.transferTo(0, channel.size(), channel2);
                        r07 = channel2;
                        r08 = channel2;
                        r09 = channel2;
                        try {
                            fileInputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        if (channel != 0) {
                            try {
                                channel.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                        if (channel2 != 0) {
                            try {
                                channel2.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        }
                        return true;
                    } catch (FileNotFoundException e6) {
                        e = e6;
                        ? r9 = fileInputStream;
                        r24 = r07;
                        r03 = r9;
                        r113 = channel;
                        r112 = r113;
                        r22 = r23;
                        r02 = r03;
                        e.printStackTrace();
                        r112 = r113;
                        r22 = r23;
                        r02 = r03;
                        if (r03 != 0) {
                        }
                        if (r113 != 0) {
                        }
                        if (fileOutputStream != null) {
                        }
                        if (r23 != 0) {
                        }
                        return false;
                    } catch (IOException e7) {
                        e = e7;
                        ? r92 = fileInputStream;
                        r26 = r08;
                        r05 = r92;
                        r115 = channel;
                        try {
                            r112 = r115;
                            r22 = r25;
                            r02 = r05;
                            e.printStackTrace();
                            r112 = r115;
                            r22 = r25;
                            r02 = r05;
                            if (r05 != 0) {
                            }
                            if (r115 != 0) {
                            }
                            if (fileOutputStream != null) {
                            }
                            if (r25 != 0) {
                            }
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            ? r93 = r22;
                            r2 = r02;
                            r0 = r93;
                            r11 = r112;
                            if (r2 != 0) {
                            }
                            if (r11 != 0) {
                            }
                            if (fileOutputStream != null) {
                            }
                            if (r0 != 0) {
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        r2 = fileInputStream;
                        r11 = channel;
                        r0 = r09;
                        if (r2 != 0) {
                        }
                        if (r11 != 0) {
                        }
                        if (fileOutputStream != null) {
                        }
                        if (r0 != 0) {
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e8) {
                    e = e8;
                    r117 = 0;
                    r04 = fileInputStream;
                    r114 = r117;
                    r24 = r114;
                    r113 = r114;
                    r03 = r04;
                    r112 = r113;
                    r22 = r23;
                    r02 = r03;
                    e.printStackTrace();
                    r112 = r113;
                    r22 = r23;
                    r02 = r03;
                    if (r03 != 0) {
                    }
                    if (r113 != 0) {
                    }
                    if (fileOutputStream != null) {
                    }
                    if (r23 != 0) {
                    }
                    return false;
                } catch (IOException e9) {
                    e = e9;
                    r118 = 0;
                    r06 = fileInputStream;
                    r116 = r118;
                    r26 = r116;
                    r115 = r116;
                    r05 = r06;
                    r112 = r115;
                    r22 = r25;
                    r02 = r05;
                    e.printStackTrace();
                    r112 = r115;
                    r22 = r25;
                    r02 = r05;
                    if (r05 != 0) {
                    }
                    if (r115 != 0) {
                    }
                    if (fileOutputStream != null) {
                    }
                    if (r25 != 0) {
                    }
                    return false;
                } catch (Throwable th3) {
                    th = th3;
                    r11 = 0;
                    r0 = r010;
                    r2 = fileInputStream;
                    if (r2 != 0) {
                    }
                    if (r11 != 0) {
                    }
                    if (fileOutputStream != null) {
                    }
                    if (r0 != 0) {
                    }
                    throw th;
                }
            } catch (FileNotFoundException e10) {
                e = e10;
                fileOutputStream = null;
                r117 = 0;
                r04 = fileInputStream;
                r114 = r117;
                r24 = r114;
                r113 = r114;
                r03 = r04;
                r112 = r113;
                r22 = r23;
                r02 = r03;
                e.printStackTrace();
                r112 = r113;
                r22 = r23;
                r02 = r03;
                if (r03 != 0) {
                    try {
                        r03.close();
                    } catch (IOException e11) {
                        e11.printStackTrace();
                    }
                }
                if (r113 != 0) {
                    try {
                        r113.close();
                    } catch (IOException e12) {
                        e12.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e13) {
                        e13.printStackTrace();
                    }
                }
                if (r23 != 0) {
                    try {
                        r23.close();
                    } catch (IOException e14) {
                        e14.printStackTrace();
                    }
                }
                return false;
            } catch (IOException e15) {
                e = e15;
                fileOutputStream = null;
                r118 = 0;
                r06 = fileInputStream;
                r116 = r118;
                r26 = r116;
                r115 = r116;
                r05 = r06;
                r112 = r115;
                r22 = r25;
                r02 = r05;
                e.printStackTrace();
                r112 = r115;
                r22 = r25;
                r02 = r05;
                if (r05 != 0) {
                    try {
                        r05.close();
                    } catch (IOException e16) {
                        e16.printStackTrace();
                    }
                }
                if (r115 != 0) {
                    try {
                        r115.close();
                    } catch (IOException e17) {
                        e17.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e18) {
                        e18.printStackTrace();
                    }
                }
                if (r25 != 0) {
                    try {
                        r25.close();
                    } catch (IOException e19) {
                        e19.printStackTrace();
                    }
                }
                return false;
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = null;
                r11 = 0;
                r0 = r010;
                r2 = fileInputStream;
                if (r2 != 0) {
                    try {
                        r2.close();
                    } catch (IOException e20) {
                        e20.printStackTrace();
                    }
                }
                if (r11 != 0) {
                    try {
                        r11.close();
                    } catch (IOException e21) {
                        e21.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e22) {
                        e22.printStackTrace();
                    }
                }
                if (r0 != 0) {
                    try {
                        r0.close();
                    } catch (IOException e23) {
                        e23.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException e24) {
            e = e24;
            fileOutputStream = null;
            r114 = 0;
            r04 = r010;
            r24 = r114;
            r113 = r114;
            r03 = r04;
            r112 = r113;
            r22 = r23;
            r02 = r03;
            e.printStackTrace();
            r112 = r113;
            r22 = r23;
            r02 = r03;
            if (r03 != 0) {
            }
            if (r113 != 0) {
            }
            if (fileOutputStream != null) {
            }
            if (r23 != 0) {
            }
            return false;
        } catch (IOException e25) {
            e = e25;
            fileOutputStream = null;
            r116 = 0;
            r06 = r010;
            r26 = r116;
            r115 = r116;
            r05 = r06;
            r112 = r115;
            r22 = r25;
            r02 = r05;
            e.printStackTrace();
            r112 = r115;
            r22 = r25;
            r02 = r05;
            if (r05 != 0) {
            }
            if (r115 != 0) {
            }
            if (fileOutputStream != null) {
            }
            if (r25 != 0) {
            }
            return false;
        } catch (Throwable th5) {
            th = th5;
            fileOutputStream = null;
            r11 = 0;
            r2 = 0;
            r0 = r010;
            if (r2 != 0) {
            }
            if (r11 != 0) {
            }
            if (fileOutputStream != null) {
            }
            if (r0 != 0) {
            }
            throw th;
        }
    }

    public static String getPath() {
        if (msFolderPath == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            sb.append(File.separator);
            sb.append(DEFAULT_FOLDER_NAME);
            msFolderPath = sb.toString();
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
        } catch (Exception unused) {
            return false;
        }
    }
}
