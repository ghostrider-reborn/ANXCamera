package com.android.camera;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import java.util.Locale;

public class ActivityLauncher {
    private static String URL_ANXBOUNCE = "https://studios.aeonax.com/bounceredirector.html";
    private static String URL_ANXCAMERACHAT = "https://telegram.me/ANXMiuiCameraChat/";
    private static String URL_ANXCAMERAUPDATE = "http://camera.aeonax.com/#predownloads";
    private static String URL_MIUI_PRIVACY_POLICY = "https://privacy.mi.com/all/";

    private ActivityLauncher() {
    }

    public static void launchANXBounce(Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(URL_ANXBOUNCE));
        context.startActivity(intent);
    }

    public static void launchANXCameraChat(Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(URL_ANXCAMERACHAT));
        context.startActivity(intent);
    }

    public static void launchANXCameraWebpage(Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(URL_ANXCAMERAUPDATE));
        context.startActivity(intent);
    }

    public static void launchPrivacyPolicyWebpage(Context context) {
        String str;
        Intent intent = new Intent("android.intent.action.VIEW");
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        String country = locale.getCountry();
        if (TextUtils.isEmpty(language) || TextUtils.isEmpty(country)) {
            str = URL_MIUI_PRIVACY_POLICY;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(URL_MIUI_PRIVACY_POLICY);
            sb.append("%s_%s");
            str = String.format(sb.toString(), new Object[]{language, country});
        }
        intent.setData(Uri.parse(str));
        context.startActivity(intent);
    }
}
