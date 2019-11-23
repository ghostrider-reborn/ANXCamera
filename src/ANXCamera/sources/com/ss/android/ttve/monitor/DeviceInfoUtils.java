package com.ss.android.ttve.monitor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v4.util.ArraySet;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.regex.Pattern;

public class DeviceInfoUtils {
    private static final String CPU_FILE = "/proc/cpuinfo";
    private static final String HARDWARE_PATTERN = "Hardware";
    private static final String TAG = "DeviceInfoUtils";
    private static String sCpuHardware = null;

    private static void closeQuietly(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static long getExternalStorage(Context context) {
        String str;
        try {
            str = Environment.getExternalStorageDirectory().getAbsolutePath();
        } catch (Exception e) {
            str = "/sdcard";
        }
        try {
            StatFs statFs = new StatFs(str);
            return Build.VERSION.SDK_INT >= 18 ? (statFs.getBlockCountLong() * statFs.getBlockSizeLong()) / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED : (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
        } catch (Exception e2) {
            return -1;
        }
    }

    public static long getInternalStorage() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        return Build.VERSION.SDK_INT >= 18 ? (statFs.getBlockCountLong() * statFs.getBlockSizeLong()) / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED : (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0055 A[SYNTHETIC, Splitter:B:23:0x0055] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0061 A[SYNTHETIC, Splitter:B:28:0x0061] */
    public static String getMaxCpuFreq() {
        String str = "";
        InputStream inputStream = null;
        try {
            InputStream inputStream2 = new ProcessBuilder(new String[]{"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"}).start().getInputStream();
            try {
                while (inputStream2.read(new byte[24]) != -1) {
                    str = str + new String(r1);
                }
                if (inputStream2 != null) {
                    try {
                        inputStream2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e2) {
                e = e2;
                inputStream = inputStream2;
                try {
                    e.printStackTrace();
                    str = "0";
                    if (inputStream != null) {
                    }
                    return str.trim();
                } catch (Throwable th) {
                    th = th;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                inputStream = inputStream2;
                if (inputStream != null) {
                }
                throw th;
            }
        } catch (IOException e4) {
            e = e4;
            e.printStackTrace();
            str = "0";
            if (inputStream != null) {
                inputStream.close();
            }
            return str.trim();
        }
        return str.trim();
    }

    public static int getNumOfCores() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return Pattern.matches("cpu[0-9]", file.getName());
                }
            }).length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public static String getProperty(String str) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls, new Object[]{str, EnvironmentCompat.MEDIA_UNKNOWN});
        } catch (Exception e) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    public static int getRealScreenHeight(Context context) {
        if (context == null) {
            return 0;
        }
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 17) {
            Point point = new Point();
            defaultDisplay.getRealSize(point);
            return point.y;
        }
        try {
            return ((Integer) Display.class.getMethod("getRawHeight", new Class[0]).invoke(defaultDisplay, new Object[0])).intValue();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return 0;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return 0;
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
            return 0;
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
            return 0;
        }
    }

    public static int getScreenHeight(Context context) {
        if (context == null) {
            return 0;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        if (context == null) {
            return 0;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static Set<Sensor> getSensorUsability(Context context) {
        ArraySet arraySet = new ArraySet();
        SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
        if (sensorManager == null) {
            return arraySet;
        }
        for (int defaultSensor : new int[]{1, 13, 9, 4, 5, 10, 2, 3, 6, 8, 12, 11, 7, 15}) {
            Sensor defaultSensor2 = sensorManager.getDefaultSensor(defaultSensor);
            if (defaultSensor2 != null) {
                arraySet.add(defaultSensor2);
            }
        }
        return arraySet;
    }

    public static String getSimOperator(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getSimOperator();
        } catch (Exception e) {
            Log.w(TAG, "No permission");
            return "";
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0075 A[SYNTHETIC, Splitter:B:31:0x0075] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0081 A[SYNTHETIC, Splitter:B:36:0x0081] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0094 A[SYNTHETIC, Splitter:B:45:0x0094] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00a0 A[SYNTHETIC, Splitter:B:50:0x00a0] */
    public static long getTotalMemory() {
        FileReader fileReader;
        BufferedReader bufferedReader;
        long j;
        IOException e;
        try {
            fileReader = new FileReader("/proc/meminfo");
            try {
                bufferedReader = new BufferedReader(fileReader, 8192);
            } catch (IOException e2) {
                e = e2;
                bufferedReader = null;
                try {
                    e.printStackTrace();
                    if (bufferedReader != null) {
                    }
                    if (fileReader != null) {
                    }
                    j = 0;
                    return j / 1024;
                } catch (Throwable th) {
                    th = th;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
                if (bufferedReader != null) {
                }
                if (fileReader != null) {
                }
                throw th;
            }
            try {
                String readLine = bufferedReader.readLine();
                String[] split = readLine.split("\\s+");
                for (String str : split) {
                    Log.i(readLine, str + "/t");
                }
                j = (long) Integer.valueOf(split[1]).intValue();
                try {
                    bufferedReader.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
                try {
                    fileReader.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            } catch (IOException e7) {
                e = e7;
                e.printStackTrace();
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                }
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e9) {
                        e9.printStackTrace();
                    }
                }
                j = 0;
                return j / 1024;
            }
        } catch (IOException e10) {
            fileReader = null;
            e = e10;
            bufferedReader = null;
            e.printStackTrace();
            if (bufferedReader != null) {
            }
            if (fileReader != null) {
            }
            j = 0;
            return j / 1024;
        } catch (Throwable th3) {
            fileReader = null;
            th = th3;
            bufferedReader = null;
            if (bufferedReader != null) {
            }
            if (fileReader != null) {
            }
            throw th;
        }
        return j / 1024;
    }

    @SuppressLint({"infer"})
    public static String readCpuHardware() {
        BufferedReader bufferedReader;
        FileReader fileReader;
        Throwable th;
        if (!TextUtils.isEmpty(sCpuHardware)) {
            return sCpuHardware;
        }
        try {
            fileReader = new FileReader(CPU_FILE);
            try {
                bufferedReader = new BufferedReader(fileReader);
                try {
                    String readLine = bufferedReader.readLine();
                    while (true) {
                        if (readLine == null) {
                            break;
                        }
                        if (readLine.startsWith(HARDWARE_PATTERN) && readLine.contains(":")) {
                            String substring = readLine.substring(readLine.indexOf(":") + 1);
                            if (!TextUtils.isEmpty(substring)) {
                                sCpuHardware = substring.trim();
                                break;
                            }
                        }
                        readLine = bufferedReader.readLine();
                    }
                    if (TextUtils.isEmpty(sCpuHardware)) {
                        sCpuHardware = getProperty("ro.board.platform");
                    }
                    String str = sCpuHardware;
                    closeQuietly(bufferedReader);
                    closeQuietly(fileReader);
                    return str;
                } catch (IOException e) {
                    closeQuietly(bufferedReader);
                    closeQuietly(fileReader);
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    closeQuietly(bufferedReader);
                    closeQuietly(fileReader);
                    throw th;
                }
            } catch (IOException e2) {
                bufferedReader = null;
                closeQuietly(bufferedReader);
                closeQuietly(fileReader);
                return null;
            } catch (Throwable th3) {
                Throwable th4 = th3;
                bufferedReader = null;
                th = th4;
                closeQuietly(bufferedReader);
                closeQuietly(fileReader);
                throw th;
            }
        } catch (IOException e3) {
            fileReader = null;
            bufferedReader = null;
            closeQuietly(bufferedReader);
            closeQuietly(fileReader);
            return null;
        } catch (Throwable th5) {
            bufferedReader = null;
            th = th5;
            fileReader = null;
            closeQuietly(bufferedReader);
            closeQuietly(fileReader);
            throw th;
        }
    }
}
