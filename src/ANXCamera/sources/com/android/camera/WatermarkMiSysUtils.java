package com.android.camera;

import com.android.camera.log.Log;
import f.a.a.a.a.c;
import f.a.a.a.b.b;
import java.util.ArrayList;

public class WatermarkMiSysUtils {
    private static final String CAMERA_FILE_DIR = "/mnt/vendor/persist/camera/";
    private static final String TAG = "WatermarkMiSysUtils";

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0019 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:23:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    public static int eraseFile(String str) {
        c cVar;
        String str2 = TAG;
        String str3 = "/mnt/vendor/persist/camera/";
        b bVar = null;
        try {
            cVar = c.getService(true);
            try {
                bVar = b.getService(true);
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                int i = -1;
                if (cVar != null) {
                }
            }
        } catch (Exception e3) {
            e = e3;
            cVar = null;
            e.printStackTrace();
            int i2 = -1;
            return cVar != null ? -1 : -1;
        }
        int i22 = -1;
        if (cVar != null && bVar != null) {
            boolean z = false;
            try {
                z = bVar.j(str3, str);
                StringBuilder sb = new StringBuilder();
                sb.append("file ");
                sb.append(str);
                sb.append(" isExist for iMiSys20.IsExists:");
                sb.append(z);
                Log.d(str2, sb.toString());
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            if (!z) {
                return -1;
            }
            try {
                i22 = cVar.k(str3, str);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str3);
                sb2.append(str);
                sb2.append(" eraseResult:");
                sb2.append(i22);
                Log.e(str2, sb2.toString());
                return i22;
            } catch (Exception e5) {
                e5.printStackTrace();
                return i22;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0015 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:18:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    public static boolean isFileExist(String str) {
        c cVar;
        b bVar = null;
        try {
            cVar = c.getService(true);
            try {
                bVar = b.getService(true);
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                boolean z = false;
                if (cVar != null) {
                }
            }
        } catch (Exception e3) {
            e = e3;
            cVar = null;
            e.printStackTrace();
            boolean z2 = false;
            return cVar != null ? false : false;
        }
        boolean z22 = false;
        if (cVar != null && bVar != null) {
            try {
                z22 = bVar.j("/mnt/vendor/persist/camera/", str);
                String str2 = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("file ");
                sb.append(str);
                sb.append(" isExist for iMiSys20.IsExists:");
                sb.append(z22);
                Log.d(str2, sb.toString());
                return z22;
            } catch (Exception e4) {
                e4.printStackTrace();
                return z22;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0024 A[Catch:{ Exception -> 0x0075, all -> 0x0079 }, LOOP:0: B:14:0x0022->B:15:0x0024, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0073  */
    public static boolean writeFileToPersist(byte[] bArr, String str) {
        c cVar;
        b bVar;
        boolean z;
        int a2;
        String str2 = TAG;
        b bVar2 = null;
        try {
            cVar = c.getService(true);
            try {
                bVar2 = b.getService(true);
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                bVar = bVar2;
                z = false;
                try {
                    ArrayList arrayList = new ArrayList();
                    while (r5 < r3) {
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("data.length=");
                    sb.append(bArr.length);
                    sb.append(" byteData.size=");
                    sb.append(arrayList.size());
                    Log.d(str2, sb.toString());
                    a2 = bVar.a("/mnt/vendor/persist/camera/", str, arrayList, (long) arrayList.size());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("writeResult for iMiSys20.MiSysWriteBuffer:");
                    sb2.append(a2);
                    Log.d(str2, sb2.toString());
                    if (a2 == 0) {
                    }
                    return z;
                } catch (Exception e3) {
                    e3.printStackTrace();
                } catch (Throwable unused) {
                }
            }
        } catch (Exception e4) {
            e = e4;
            cVar = null;
            e.printStackTrace();
            bVar = bVar2;
            z = false;
            ArrayList arrayList2 = new ArrayList();
            while (r5 < r3) {
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append("data.length=");
            sb3.append(bArr.length);
            sb3.append(" byteData.size=");
            sb3.append(arrayList2.size());
            Log.d(str2, sb3.toString());
            a2 = bVar.a("/mnt/vendor/persist/camera/", str, arrayList2, (long) arrayList2.size());
            StringBuilder sb22 = new StringBuilder();
            sb22.append("writeResult for iMiSys20.MiSysWriteBuffer:");
            sb22.append(a2);
            Log.d(str2, sb22.toString());
            if (a2 == 0) {
            }
            return z;
        }
        bVar = bVar2;
        z = false;
        if (!(cVar == null || bVar == null)) {
            ArrayList arrayList22 = new ArrayList();
            for (byte valueOf : bArr) {
                arrayList22.add(Byte.valueOf(valueOf));
            }
            StringBuilder sb32 = new StringBuilder();
            sb32.append("data.length=");
            sb32.append(bArr.length);
            sb32.append(" byteData.size=");
            sb32.append(arrayList22.size());
            Log.d(str2, sb32.toString());
            a2 = bVar.a("/mnt/vendor/persist/camera/", str, arrayList22, (long) arrayList22.size());
            StringBuilder sb222 = new StringBuilder();
            sb222.append("writeResult for iMiSys20.MiSysWriteBuffer:");
            sb222.append(a2);
            Log.d(str2, sb222.toString());
            if (a2 == 0) {
                z = true;
            }
            return z;
        }
        return false;
    }
}
