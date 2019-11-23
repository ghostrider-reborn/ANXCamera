package com.android.camera;

import b.a.a.a.a.c;
import b.a.a.a.b.b;
import com.android.camera.log.Log;
import java.util.ArrayList;

public class WatermarkMiSysUtils {
    private static final String CAMERA_FILE_DIR = "/mnt/vendor/persist/camera/";
    private static final String TAG = "WatermarkMiSysUtils";

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0018 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    public static int eraseFile(String str) {
        c cVar;
        b bVar;
        boolean z;
        Exception e;
        try {
            cVar = c.t(true);
            try {
                bVar = b.u(true);
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                bVar = null;
                int i = -1;
                if (cVar != null) {
                }
                return -1;
            }
        } catch (Exception e3) {
            e = e3;
            cVar = null;
            e.printStackTrace();
            bVar = null;
            int i2 = -1;
            if (cVar != null) {
            }
            return -1;
        }
        int i22 = -1;
        if (cVar != null || bVar == null) {
            return -1;
        }
        try {
            z = bVar.q("/mnt/vendor/persist/camera/", str);
            try {
                Log.d(TAG, "file " + str + " isExist for iMiSys20.IsExists:" + z);
            } catch (Exception e4) {
                e = e4;
            }
        } catch (Exception e5) {
            e = e5;
            z = false;
            e.printStackTrace();
            if (z) {
            }
        }
        if (z) {
            return -1;
        }
        try {
            int n = cVar.n("/mnt/vendor/persist/camera/", str);
            try {
                Log.e(TAG, "/mnt/vendor/persist/camera/" + str + " eraseResult:" + n);
                return n;
            } catch (Exception e6) {
                e = e6;
                i22 = n;
                e.printStackTrace();
                return i22;
            }
        } catch (Exception e7) {
            e = e7;
            e.printStackTrace();
            return i22;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0018 A[ADDED_TO_REGION] */
    public static boolean isFileExist(String str) {
        c cVar;
        b bVar;
        boolean z;
        try {
            cVar = c.t(true);
            try {
                bVar = b.u(true);
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                bVar = null;
                if (cVar != null) {
                }
                return false;
            }
        } catch (Exception e2) {
            e = e2;
            cVar = null;
            e.printStackTrace();
            bVar = null;
            if (cVar != null) {
            }
            return false;
        }
        if (cVar != null || bVar == null) {
            return false;
        }
        try {
            z = bVar.q("/mnt/vendor/persist/camera/", str);
            try {
                Log.d(TAG, "file " + str + " isExist for iMiSys20.IsExists:" + z);
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Exception e4) {
            e = e4;
            z = false;
            e.printStackTrace();
            return z;
        }
        return z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0018 A[ADDED_TO_REGION] */
    public static boolean writeFileToPersist(byte[] bArr, String str) {
        b bVar;
        c cVar;
        try {
            cVar = c.t(true);
            try {
                bVar = b.u(true);
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                bVar = null;
                if (cVar != null) {
                }
                return false;
            }
        } catch (Exception e2) {
            e = e2;
            cVar = null;
            e.printStackTrace();
            bVar = null;
            if (cVar != null) {
            }
            return false;
        }
        if (cVar != null || bVar == null) {
            return false;
        }
        try {
            ArrayList arrayList = new ArrayList();
            for (byte valueOf : bArr) {
                arrayList.add(Byte.valueOf(valueOf));
            }
            Log.d(TAG, "data.length=" + bArr.length + " byteData.size=" + arrayList.size());
            int a2 = bVar.a("/mnt/vendor/persist/camera/", str, arrayList, (long) arrayList.size());
            StringBuilder sb = new StringBuilder();
            sb.append("writeResult for iMiSys20.MiSysWriteBuffer:");
            sb.append(a2);
            Log.d(TAG, sb.toString());
            return a2 == 0;
        } catch (Exception e3) {
            e3.printStackTrace();
        } catch (Throwable th) {
        }
        return false;
    }
}
