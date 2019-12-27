package com.android.camera;

import android.location.Location;
import android.media.ExifInterface;
import android.os.Build;
import android.text.TextUtils;
import com.android.camera.log.Log;
import com.android.gallery3d.exif.ExifInterface;
import com.mi.config.b;
import java.io.File;
import java.io.FileDescriptor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class ExifHelper {
    private static final String DATETIME_FORMAT_STR = "yyyy:MM:dd HH:mm:ss";
    private static final String GPS_DATE_FORMAT_STR = "yyyy:MM:dd";
    private static final String GPS_TIME_FORMAT_STR = "HH:mm:ss";
    private static final String TAG = "ExifHelper";
    private static DateFormat mDateTimeStampFormat = new SimpleDateFormat(DATETIME_FORMAT_STR);
    private static DateFormat mGPSDateStampFormat = new SimpleDateFormat(GPS_DATE_FORMAT_STR);
    private static DateFormat mGPSTimeStampFormat = new SimpleDateFormat(GPS_TIME_FORMAT_STR);

    static {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        mGPSDateStampFormat.setTimeZone(timeZone);
        mGPSTimeStampFormat.setTimeZone(timeZone);
    }

    public static String convertDoubleToLaLon(double d2) {
        int floor = (int) Math.floor(Math.abs(d2));
        double d3 = (double) floor;
        double floor2 = Math.floor((Math.abs(d2) - d3) * 60.0d);
        double floor3 = Math.floor(((Math.abs(d2) - d3) - (floor2 / 60.0d)) * 3600000.0d);
        if (d2 < 0.0d) {
            return "-" + floor + "/1," + ((int) floor2) + "/1," + ((int) floor3) + "/1000";
        }
        return floor + "/1," + ((int) floor2) + "/1," + ((int) floor3) + "/1000";
    }

    public static String getExifOrientation(int i) {
        if (i == 0) {
            return String.valueOf(1);
        }
        if (i == 90) {
            return String.valueOf(6);
        }
        if (i == 180) {
            return String.valueOf(3);
        }
        if (i == 270) {
            return String.valueOf(8);
        }
        throw new AssertionError("invalid: " + i);
    }

    private static void writeExif(String str, FileDescriptor fileDescriptor, int i, Location location, long j) {
        try {
            if (TextUtils.isEmpty(str) || (Util.isPathExist(str) && new File(str).length() != 0)) {
                ExifInterface exifInterface = fileDescriptor == null ? new ExifInterface(str) : new ExifInterface(fileDescriptor);
                exifInterface.setAttribute("GPSDateStamp", mGPSDateStampFormat.format(Long.valueOf(j)));
                exifInterface.setAttribute("GPSTimeStamp", mGPSTimeStampFormat.format(Long.valueOf(j)));
                exifInterface.setAttribute("DateTime", mDateTimeStampFormat.format(Long.valueOf(j)));
                exifInterface.setAttribute("Orientation", getExifOrientation(i));
                exifInterface.setAttribute("Make", Build.MANUFACTURER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    exifInterface.setAttribute("GPSLatitude", convertDoubleToLaLon(latitude));
                    exifInterface.setAttribute("GPSLongitude", convertDoubleToLaLon(longitude));
                    if (latitude > 0.0d) {
                        exifInterface.setAttribute("GPSLatitudeRef", "N");
                    } else {
                        exifInterface.setAttribute("GPSLatitudeRef", "S");
                    }
                    if (longitude > 0.0d) {
                        exifInterface.setAttribute("GPSLongitudeRef", ExifInterface.GpsLongitudeRef.EAST);
                    } else {
                        exifInterface.setAttribute("GPSLongitudeRef", ExifInterface.GpsLongitudeRef.WEST);
                    }
                }
                if (!b.tm) {
                    if (!b.IS_MI2A) {
                        exifInterface.setAttribute("Model", b.sm);
                        exifInterface.saveAttributes();
                        return;
                    }
                }
                exifInterface.setAttribute("Model", "MiTwo");
                exifInterface.setAttribute("FocalLength", "354/100");
                exifInterface.saveAttributes();
                return;
            }
            Log.e(TAG, "writeExif. the file:[" + str + "] is not exist or empty");
        } catch (Exception e2) {
            Log.w(TAG, "write exif error, filePath = " + str, e2);
        }
    }

    public static void writeExifByFd(FileDescriptor fileDescriptor, int i, Location location, long j) {
        writeExif((String) null, fileDescriptor, i, location, j);
    }

    public static void writeExifByFilePath(String str, int i, Location location, long j) {
        writeExif(str, (FileDescriptor) null, i, location, j);
    }
}
