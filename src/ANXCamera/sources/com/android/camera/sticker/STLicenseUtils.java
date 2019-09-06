package com.android.camera.sticker;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.android.camera.log.Log;
import com.sensetime.stmobile.STMobileAuthentificationNative;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class STLicenseUtils {
    private static final String LICENSE_NAME = "SenseME.lic";
    private static final String PREF_ACTIVATE_CODE = "activate_code";
    private static final String PREF_ACTIVATE_CODE_FILE = "activate_code_file";
    private static final String TAG = "STLicenseUtils";

    /* JADX WARNING: Removed duplicated region for block: B:26:0x004d A[SYNTHETIC, Splitter:B:26:0x004d] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0057 A[SYNTHETIC, Splitter:B:31:0x0057] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00f9 A[SYNTHETIC, Splitter:B:60:0x00f9] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0103 A[SYNTHETIC, Splitter:B:65:0x0103] */
    public static boolean checkLicense(Context context) {
        InputStreamReader inputStreamReader;
        int length;
        BufferedReader bufferedReader;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader2 = null;
        try {
            inputStreamReader = new InputStreamReader(context.getResources().getAssets().open(LICENSE_NAME));
            try {
                bufferedReader = new BufferedReader(inputStreamReader);
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine != null) {
                            sb.append(readLine);
                            sb.append("\n");
                        } else {
                            try {
                                break;
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                    } catch (IOException e3) {
                        e = e3;
                        try {
                            e.printStackTrace();
                            if (inputStreamReader != null) {
                                try {
                                    inputStreamReader.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            length = sb.toString().length();
                            String str = TAG;
                            if (length != 0) {
                            }
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader2 = bufferedReader;
                            if (inputStreamReader != null) {
                                try {
                                    inputStreamReader.close();
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                }
                            }
                            if (bufferedReader2 != null) {
                                try {
                                    bufferedReader2.close();
                                } catch (IOException e6) {
                                    e6.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    }
                }
                inputStreamReader.close();
            } catch (IOException e7) {
                e = e7;
                bufferedReader = null;
                e.printStackTrace();
                if (inputStreamReader != null) {
                }
                if (bufferedReader != null) {
                }
                length = sb.toString().length();
                String str2 = TAG;
                if (length != 0) {
                }
            } catch (Throwable th2) {
                th = th2;
                if (inputStreamReader != null) {
                }
                if (bufferedReader2 != null) {
                }
                throw th;
            }
            try {
                bufferedReader.close();
            } catch (IOException e8) {
                e8.printStackTrace();
            }
        } catch (IOException e9) {
            e = e9;
            inputStreamReader = null;
            bufferedReader = null;
            e.printStackTrace();
            if (inputStreamReader != null) {
            }
            if (bufferedReader != null) {
            }
            length = sb.toString().length();
            String str22 = TAG;
            if (length != 0) {
            }
        } catch (Throwable th3) {
            th = th3;
            inputStreamReader = null;
            if (inputStreamReader != null) {
            }
            if (bufferedReader2 != null) {
            }
            throw th;
        }
        length = sb.toString().length();
        String str222 = TAG;
        if (length != 0) {
            Log.e(str222, "read license data error");
            return false;
        }
        String sb2 = sb.toString();
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(PREF_ACTIVATE_CODE_FILE, 0);
        String str3 = PREF_ACTIVATE_CODE;
        String string = sharedPreferences.getString(str3, null);
        Integer valueOf = Integer.valueOf(-1);
        String str4 = "activeCode: ";
        if (string == null || STMobileAuthentificationNative.checkActiveCodeFromBuffer(sb2, sb2.length(), string) != 0) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str4);
            sb3.append(string == null);
            Log.e(str222, sb3.toString());
            String generateActiveCodeFromBuffer = STMobileAuthentificationNative.generateActiveCodeFromBuffer(sb2, sb2.length());
            if (generateActiveCodeFromBuffer == null || generateActiveCodeFromBuffer.length() == 0) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("generate license error: ");
                sb4.append(valueOf);
                Log.e(str222, sb4.toString());
                return false;
            }
            Editor edit = sharedPreferences.edit();
            edit.putString(str3, generateActiveCodeFromBuffer);
            edit.commit();
            return true;
        }
        StringBuilder sb5 = new StringBuilder();
        sb5.append(str4);
        sb5.append(string);
        Log.e(str222, sb5.toString());
        return true;
    }
}
