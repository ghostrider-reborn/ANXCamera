package com.ss.android.vesdk.runtime.cloudconfig;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.apps.photos.api.PhotosOemApi;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class AutoInjector implements IInjector {
    public static final String TAG = "AutoInjector";

    private Map<String, String> createParamMap(JSONObject jSONObject) throws JSONException {
        HashMap hashMap = new HashMap();
        String str = "record_camera_type";
        boolean has = jSONObject.has(str);
        String str2 = "1";
        String str3 = TAG;
        if (has) {
            int i = jSONObject.getInt(str);
            if (i >= 1) {
                hashMap.put(str, String.valueOf(i));
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Fetched config doesn't pass:(value >= 1) record_camera_type = ");
                sb.append(i);
                Log.w(str3, sb.toString());
                hashMap.put(str, str2);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: record_camera_type");
            hashMap.put(str, str2);
        }
        String str4 = "record_camera_compat_level";
        if (jSONObject.has(str4)) {
            int i2 = jSONObject.getInt(str4);
            if (i2 >= 0) {
                hashMap.put(str4, String.valueOf(i2));
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Fetched config doesn't pass:(value >= 0) record_camera_compat_level = ");
                sb2.append(i2);
                Log.w(str3, sb2.toString());
                hashMap.put(str4, str2);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: record_camera_compat_level");
            hashMap.put(str4, str2);
        }
        String str5 = "record_video_sw_crf";
        String str6 = "15";
        if (jSONObject.has(str5)) {
            int i3 = jSONObject.getInt(str5);
            if (i3 < 1 || i3 > 50) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Fetched config doesn't pass:(value >= 1 && value <= 50) record_video_sw_crf = ");
                sb3.append(i3);
                Log.w(str3, sb3.toString());
                hashMap.put(str5, str6);
            } else {
                hashMap.put(str5, String.valueOf(i3));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: record_video_sw_crf");
            hashMap.put(str5, str6);
        }
        String str7 = "record_video_sw_maxrate";
        String str8 = "15000000";
        if (jSONObject.has(str7)) {
            int i4 = jSONObject.getInt(str7);
            if (i4 < 100000 || i4 > 100000000) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Fetched config doesn't pass:(value >= 100000 && value <= 100000000) record_video_sw_maxrate = ");
                sb4.append(i4);
                Log.w(str3, sb4.toString());
                hashMap.put(str7, str8);
            } else {
                hashMap.put(str7, String.valueOf(i4));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: record_video_sw_maxrate");
            hashMap.put(str7, str8);
        }
        String str9 = "record_video_sw_preset";
        String str10 = "0";
        if (jSONObject.has(str9)) {
            int i5 = jSONObject.getInt(str9);
            if (i5 < 0 || i5 > 9) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append("Fetched config doesn't pass:(value >= 0 && value <= 9) record_video_sw_preset = ");
                sb5.append(i5);
                Log.w(str3, sb5.toString());
                hashMap.put(str9, str10);
            } else {
                hashMap.put(str9, String.valueOf(i5));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: record_video_sw_preset");
            hashMap.put(str9, str10);
        }
        String str11 = "35";
        if (jSONObject.has("record_video_sw_gop")) {
            int i6 = jSONObject.getInt("record_video_sw_gop");
            if (i6 >= 1) {
                hashMap.put("record_video_sw_gop", String.valueOf(i6));
            } else {
                StringBuilder sb6 = new StringBuilder();
                sb6.append("Fetched config doesn't pass:(value >= 1) record_video_sw_gop = ");
                sb6.append(i6);
                Log.w(str3, sb6.toString());
                hashMap.put("record_video_sw_gop", str11);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: record_video_sw_gop");
            hashMap.put("record_video_sw_gop", str11);
        }
        String str12 = "2";
        if (jSONObject.has("record_video_sw_qp")) {
            int i7 = jSONObject.getInt("record_video_sw_qp");
            if (i7 < 1 || i7 > 50) {
                StringBuilder sb7 = new StringBuilder();
                sb7.append("Fetched config doesn't pass:(value >= 1 && value <= 50) record_video_sw_qp = ");
                sb7.append(i7);
                Log.w(str3, sb7.toString());
                hashMap.put("record_video_sw_qp", str12);
            } else {
                hashMap.put("record_video_sw_qp", String.valueOf(i7));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: record_video_sw_qp");
            hashMap.put("record_video_sw_qp", str12);
        }
        if (jSONObject.has("record_sw_bitrate_mode")) {
            int i8 = jSONObject.getInt("record_sw_bitrate_mode");
            if (i8 < 0 || i8 > 2) {
                StringBuilder sb8 = new StringBuilder();
                sb8.append("Fetched config doesn't pass:(value >= 0 && value <= 2) record_sw_bitrate_mode = ");
                sb8.append(i8);
                Log.w(str3, sb8.toString());
                hashMap.put("record_sw_bitrate_mode", str2);
            } else {
                hashMap.put("record_sw_bitrate_mode", String.valueOf(i8));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: record_sw_bitrate_mode");
            hashMap.put("record_sw_bitrate_mode", str2);
        }
        String str13 = "4194304";
        if (jSONObject.has("record_video_hw_bitrate")) {
            int i9 = jSONObject.getInt("record_video_hw_bitrate");
            if (i9 > 0) {
                hashMap.put("record_video_hw_bitrate", String.valueOf(i9));
            } else {
                StringBuilder sb9 = new StringBuilder();
                sb9.append("Fetched config doesn't pass:(value > 0) record_video_hw_bitrate = ");
                sb9.append(i9);
                Log.w(str3, sb9.toString());
                hashMap.put("record_video_hw_bitrate", str13);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: record_video_hw_bitrate");
            hashMap.put("record_video_hw_bitrate", str13);
        }
        if (jSONObject.has("record_encode_mode")) {
            int i10 = jSONObject.getInt("record_encode_mode");
            if (i10 == 0 || i10 == 1) {
                hashMap.put("record_encode_mode", String.valueOf(i10));
            } else {
                StringBuilder sb10 = new StringBuilder();
                sb10.append("Fetched config doesn't pass:(value == 0 || value == 1) record_encode_mode = ");
                sb10.append(i10);
                Log.w(str3, sb10.toString());
                hashMap.put("record_encode_mode", str10);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: record_encode_mode");
            hashMap.put("record_encode_mode", str10);
        }
        if (jSONObject.has("record_hw_bitrate_mode")) {
            int i11 = jSONObject.getInt("record_hw_bitrate_mode");
            if (i11 >= 0) {
                hashMap.put("record_hw_bitrate_mode", String.valueOf(i11));
            } else {
                StringBuilder sb11 = new StringBuilder();
                sb11.append("Fetched config doesn't pass:(value >= 0) record_hw_bitrate_mode = ");
                sb11.append(i11);
                Log.w(str3, sb11.toString());
                hashMap.put("record_hw_bitrate_mode", str10);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: record_hw_bitrate_mode");
            hashMap.put("record_hw_bitrate_mode", str10);
        }
        if (jSONObject.has("record_hw_profile")) {
            int i12 = jSONObject.getInt("record_hw_profile");
            if (i12 >= 0) {
                hashMap.put("record_hw_profile", String.valueOf(i12));
            } else {
                StringBuilder sb12 = new StringBuilder();
                sb12.append("Fetched config doesn't pass:(value >= 0) record_hw_profile = ");
                sb12.append(i12);
                Log.w(str3, sb12.toString());
                hashMap.put("record_hw_profile", str10);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: record_hw_profile");
            hashMap.put("record_hw_profile", str10);
        }
        if (jSONObject.has("record_resolution_width")) {
            int i13 = jSONObject.getInt("record_resolution_width");
            if (i13 % 16 != 0 || i13 < 160 || i13 > 5120) {
                StringBuilder sb13 = new StringBuilder();
                sb13.append("Fetched config doesn't pass:(value % 16 == 0 && value >= 160 && value <= 5120) record_resolution_width = ");
                sb13.append(i13);
                Log.w(str3, sb13.toString());
                hashMap.put("record_resolution_width", "576");
            } else {
                hashMap.put("record_resolution_width", String.valueOf(i13));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: record_resolution_width");
            hashMap.put("record_resolution_width", "576");
        }
        if (jSONObject.has("record_resolution_height")) {
            int i14 = jSONObject.getInt("record_resolution_height");
            if (i14 % 16 != 0 || i14 < 160 || i14 > 5120) {
                StringBuilder sb14 = new StringBuilder();
                sb14.append("Fetched config doesn't pass:(value % 16 == 0 && value >= 160 && value <= 5120) record_resolution_height = ");
                sb14.append(i14);
                Log.w(str3, sb14.toString());
                hashMap.put("record_resolution_height", "1024");
            } else {
                hashMap.put("record_resolution_height", String.valueOf(i14));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: record_resolution_height");
            hashMap.put("record_resolution_height", "1024");
        }
        if (jSONObject.has("import_video_sw_crf")) {
            int i15 = jSONObject.getInt("import_video_sw_crf");
            if (i15 < 1 || i15 > 50) {
                StringBuilder sb15 = new StringBuilder();
                sb15.append("Fetched config doesn't pass:(value >= 1 && value <= 50) import_video_sw_crf = ");
                sb15.append(i15);
                Log.w(str3, sb15.toString());
                hashMap.put("import_video_sw_crf", str6);
            } else {
                hashMap.put("import_video_sw_crf", String.valueOf(i15));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: import_video_sw_crf");
            hashMap.put("import_video_sw_crf", str6);
        }
        if (jSONObject.has("import_video_sw_maxrate")) {
            int i16 = jSONObject.getInt("import_video_sw_maxrate");
            if (i16 < 100000 || i16 > 100000000) {
                StringBuilder sb16 = new StringBuilder();
                sb16.append("Fetched config doesn't pass:(value >= 100000 && value <= 100000000) import_video_sw_maxrate = ");
                sb16.append(i16);
                Log.w(str3, sb16.toString());
                hashMap.put("import_video_sw_maxrate", str8);
            } else {
                hashMap.put("import_video_sw_maxrate", String.valueOf(i16));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: import_video_sw_maxrate");
            hashMap.put("import_video_sw_maxrate", str8);
        }
        if (jSONObject.has("import_video_sw_preset")) {
            int i17 = jSONObject.getInt("import_video_sw_preset");
            if (i17 < 0 || i17 > 9) {
                StringBuilder sb17 = new StringBuilder();
                sb17.append("Fetched config doesn't pass:(value >= 0 && value <= 9) import_video_sw_preset = ");
                sb17.append(i17);
                Log.w(str3, sb17.toString());
                hashMap.put("import_video_sw_preset", str10);
            } else {
                hashMap.put("import_video_sw_preset", String.valueOf(i17));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: import_video_sw_preset");
            hashMap.put("import_video_sw_preset", str10);
        }
        if (jSONObject.has("import_video_sw_gop")) {
            int i18 = jSONObject.getInt("import_video_sw_gop");
            if (i18 >= 1) {
                hashMap.put("import_video_sw_gop", String.valueOf(i18));
            } else {
                StringBuilder sb18 = new StringBuilder();
                sb18.append("Fetched config doesn't pass:(value >= 1) import_video_sw_gop = ");
                sb18.append(i18);
                Log.w(str3, sb18.toString());
                hashMap.put("import_video_sw_gop", str11);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: import_video_sw_gop");
            hashMap.put("import_video_sw_gop", str11);
        }
        if (jSONObject.has("import_video_sw_qp")) {
            int i19 = jSONObject.getInt("import_video_sw_qp");
            if (i19 < 1 || i19 > 50) {
                StringBuilder sb19 = new StringBuilder();
                sb19.append("Fetched config doesn't pass:(value >= 1 && value <= 50) import_video_sw_qp = ");
                sb19.append(i19);
                Log.w(str3, sb19.toString());
                hashMap.put("import_video_sw_qp", str12);
            } else {
                hashMap.put("import_video_sw_qp", String.valueOf(i19));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: import_video_sw_qp");
            hashMap.put("import_video_sw_qp", str12);
        }
        if (jSONObject.has("import_sw_bitrate_mode")) {
            int i20 = jSONObject.getInt("import_sw_bitrate_mode");
            if (i20 < 0 || i20 > 2) {
                StringBuilder sb20 = new StringBuilder();
                sb20.append("Fetched config doesn't pass:(value >= 0 && value <= 2) import_sw_bitrate_mode = ");
                sb20.append(i20);
                Log.w(str3, sb20.toString());
                hashMap.put("import_sw_bitrate_mode", str10);
            } else {
                hashMap.put("import_sw_bitrate_mode", String.valueOf(i20));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: import_sw_bitrate_mode");
            hashMap.put("import_sw_bitrate_mode", str10);
        }
        if (jSONObject.has("import_encode_mode")) {
            int i21 = jSONObject.getInt("import_encode_mode");
            if (i21 == 0 || i21 == 1) {
                hashMap.put("import_encode_mode", String.valueOf(i21));
            } else {
                StringBuilder sb21 = new StringBuilder();
                sb21.append("Fetched config doesn't pass:(value == 0 || value == 1) import_encode_mode = ");
                sb21.append(i21);
                Log.w(str3, sb21.toString());
                hashMap.put("import_encode_mode", str10);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: import_encode_mode");
            hashMap.put("import_encode_mode", str10);
        }
        if (jSONObject.has("import_video_hw_bitrate")) {
            int i22 = jSONObject.getInt("import_video_hw_bitrate");
            if (i22 > 0) {
                hashMap.put("import_video_hw_bitrate", String.valueOf(i22));
            } else {
                StringBuilder sb22 = new StringBuilder();
                sb22.append("Fetched config doesn't pass:(value > 0) import_video_hw_bitrate = ");
                sb22.append(i22);
                Log.w(str3, sb22.toString());
                hashMap.put("import_video_hw_bitrate", str13);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: import_video_hw_bitrate");
            hashMap.put("import_video_hw_bitrate", str13);
        }
        if (jSONObject.has("import_hw_bitrate_mode")) {
            int i23 = jSONObject.getInt("import_hw_bitrate_mode");
            if (i23 >= 0) {
                hashMap.put("import_hw_bitrate_mode", String.valueOf(i23));
            } else {
                StringBuilder sb23 = new StringBuilder();
                sb23.append("Fetched config doesn't pass:(value >= 0) import_hw_bitrate_mode = ");
                sb23.append(i23);
                Log.w(str3, sb23.toString());
                hashMap.put("import_hw_bitrate_mode", str10);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: import_hw_bitrate_mode");
            hashMap.put("import_hw_bitrate_mode", str10);
        }
        if (jSONObject.has("import_hw_profile")) {
            int i24 = jSONObject.getInt("import_hw_profile");
            if (i24 >= 0) {
                hashMap.put("import_hw_profile", String.valueOf(i24));
            } else {
                StringBuilder sb24 = new StringBuilder();
                sb24.append("Fetched config doesn't pass:(value >= 0) import_hw_profile = ");
                sb24.append(i24);
                Log.w(str3, sb24.toString());
                hashMap.put("import_hw_profile", str10);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: import_hw_profile");
            hashMap.put("import_hw_profile", str10);
        }
        if (jSONObject.has("import_shorter_pixels")) {
            int i25 = jSONObject.getInt("import_shorter_pixels");
            if (i25 % 16 != 0 || i25 < 160 || i25 > 5120) {
                StringBuilder sb25 = new StringBuilder();
                sb25.append("Fetched config doesn't pass:(value % 16 == 0 && value >= 160 && value <= 5120) import_shorter_pixels = ");
                sb25.append(i25);
                Log.w(str3, sb25.toString());
                hashMap.put("import_shorter_pixels", "576");
            } else {
                hashMap.put("import_shorter_pixels", String.valueOf(i25));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: import_shorter_pixels");
            hashMap.put("import_shorter_pixels", "576");
        }
        if (jSONObject.has("synthetic_video_sw_crf")) {
            int i26 = jSONObject.getInt("synthetic_video_sw_crf");
            if (i26 < 1 || i26 > 50) {
                StringBuilder sb26 = new StringBuilder();
                sb26.append("Fetched config doesn't pass:(value >= 1 && value <= 50) synthetic_video_sw_crf = ");
                sb26.append(i26);
                Log.w(str3, sb26.toString());
                hashMap.put("synthetic_video_sw_crf", str6);
            } else {
                hashMap.put("synthetic_video_sw_crf", String.valueOf(i26));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: synthetic_video_sw_crf");
            hashMap.put("synthetic_video_sw_crf", str6);
        }
        if (jSONObject.has("synthetic_video_sw_maxrate")) {
            int i27 = jSONObject.getInt("synthetic_video_sw_maxrate");
            if (i27 < 100000 || i27 > 100000000) {
                StringBuilder sb27 = new StringBuilder();
                sb27.append("Fetched config doesn't pass:(value >= 100000 && value <= 100000000) synthetic_video_sw_maxrate = ");
                sb27.append(i27);
                Log.w(str3, sb27.toString());
                hashMap.put("synthetic_video_sw_maxrate", str8);
            } else {
                hashMap.put("synthetic_video_sw_maxrate", String.valueOf(i27));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: synthetic_video_sw_maxrate");
            hashMap.put("synthetic_video_sw_maxrate", str8);
        }
        if (jSONObject.has("synthetic_video_sw_preset")) {
            int i28 = jSONObject.getInt("synthetic_video_sw_preset");
            if (i28 < 0 || i28 > 9) {
                StringBuilder sb28 = new StringBuilder();
                sb28.append("Fetched config doesn't pass:(value >= 0 && value <= 9) synthetic_video_sw_preset = ");
                sb28.append(i28);
                Log.w(str3, sb28.toString());
                hashMap.put("synthetic_video_sw_preset", str10);
            } else {
                hashMap.put("synthetic_video_sw_preset", String.valueOf(i28));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: synthetic_video_sw_preset");
            hashMap.put("synthetic_video_sw_preset", str10);
        }
        if (jSONObject.has("synthetic_video_sw_gop")) {
            int i29 = jSONObject.getInt("synthetic_video_sw_gop");
            if (i29 >= 1) {
                hashMap.put("synthetic_video_sw_gop", String.valueOf(i29));
            } else {
                StringBuilder sb29 = new StringBuilder();
                sb29.append("Fetched config doesn't pass:(value >= 1) synthetic_video_sw_gop = ");
                sb29.append(i29);
                Log.w(str3, sb29.toString());
                hashMap.put("synthetic_video_sw_gop", str11);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: synthetic_video_sw_gop");
            hashMap.put("synthetic_video_sw_gop", str11);
        }
        if (jSONObject.has("synthetic_video_sw_qp")) {
            int i30 = jSONObject.getInt("synthetic_video_sw_qp");
            if (i30 < 1 || i30 > 50) {
                StringBuilder sb30 = new StringBuilder();
                sb30.append("Fetched config doesn't pass:(value >= 1 && value <= 50) synthetic_video_sw_qp = ");
                sb30.append(i30);
                Log.w(str3, sb30.toString());
                hashMap.put("synthetic_video_sw_qp", str12);
            } else {
                hashMap.put("synthetic_video_sw_qp", String.valueOf(i30));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: synthetic_video_sw_qp");
            hashMap.put("synthetic_video_sw_qp", str12);
        }
        if (jSONObject.has("synthetic_sw_bitrate_mode")) {
            int i31 = jSONObject.getInt("synthetic_sw_bitrate_mode");
            if (i31 < 0 || i31 > 2) {
                StringBuilder sb31 = new StringBuilder();
                sb31.append("Fetched config doesn't pass:(value >= 0 && value <= 2) synthetic_sw_bitrate_mode = ");
                sb31.append(i31);
                Log.w(str3, sb31.toString());
                hashMap.put("synthetic_sw_bitrate_mode", str2);
            } else {
                hashMap.put("synthetic_sw_bitrate_mode", String.valueOf(i31));
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: synthetic_sw_bitrate_mode");
            hashMap.put("synthetic_sw_bitrate_mode", str2);
        }
        if (jSONObject.has("synthetic_encode_mode")) {
            int i32 = jSONObject.getInt("synthetic_encode_mode");
            if (i32 == 0 || i32 == 1) {
                hashMap.put("synthetic_encode_mode", String.valueOf(i32));
            } else {
                StringBuilder sb32 = new StringBuilder();
                sb32.append("Fetched config doesn't pass:(value == 0 || value == 1) synthetic_encode_mode = ");
                sb32.append(i32);
                Log.w(str3, sb32.toString());
                hashMap.put("synthetic_encode_mode", str10);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: synthetic_encode_mode");
            hashMap.put("synthetic_encode_mode", str10);
        }
        if (jSONObject.has("synthetic_video_hw_bitrate")) {
            int i33 = jSONObject.getInt("synthetic_video_hw_bitrate");
            if (i33 >= 0) {
                hashMap.put("synthetic_video_hw_bitrate", String.valueOf(i33));
            } else {
                StringBuilder sb33 = new StringBuilder();
                sb33.append("Fetched config doesn't pass:(value >= 0) synthetic_video_hw_bitrate = ");
                sb33.append(i33);
                Log.w(str3, sb33.toString());
                hashMap.put("synthetic_video_hw_bitrate", str13);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: synthetic_video_hw_bitrate");
            hashMap.put("synthetic_video_hw_bitrate", str13);
        }
        if (jSONObject.has("synthetic_hw_bitrate_mode")) {
            int i34 = jSONObject.getInt("synthetic_hw_bitrate_mode");
            if (i34 >= 0) {
                hashMap.put("synthetic_hw_bitrate_mode", String.valueOf(i34));
            } else {
                StringBuilder sb34 = new StringBuilder();
                sb34.append("Fetched config doesn't pass:(value >= 0) synthetic_hw_bitrate_mode = ");
                sb34.append(i34);
                Log.w(str3, sb34.toString());
                hashMap.put("synthetic_hw_bitrate_mode", str10);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: synthetic_hw_bitrate_mode");
            hashMap.put("synthetic_hw_bitrate_mode", str10);
        }
        if (jSONObject.has("synthetic_hw_profile")) {
            int i35 = jSONObject.getInt("synthetic_hw_profile");
            if (i35 >= 0) {
                hashMap.put("synthetic_hw_profile", String.valueOf(i35));
            } else {
                StringBuilder sb35 = new StringBuilder();
                sb35.append("Fetched config doesn't pass:(value >= 0) synthetic_hw_profile = ");
                sb35.append(i35);
                Log.w(str3, sb35.toString());
                hashMap.put("synthetic_hw_profile", str10);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: synthetic_hw_profile");
            hashMap.put("synthetic_hw_profile", str10);
        }
        if (jSONObject.has("earphone_echo_normal")) {
            int i36 = jSONObject.getInt("earphone_echo_normal");
            if (i36 == 0 || i36 == 1) {
                hashMap.put("earphone_echo_normal", String.valueOf(i36));
            } else {
                StringBuilder sb36 = new StringBuilder();
                sb36.append("Fetched config doesn't pass:(value == 0 || value == 1) earphone_echo_normal = ");
                sb36.append(i36);
                Log.w(str3, sb36.toString());
                hashMap.put("earphone_echo_normal", str2);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: earphone_echo_normal");
            hashMap.put("earphone_echo_normal", str2);
        }
        if (jSONObject.has("earphone_echo_aaudio")) {
            int i37 = jSONObject.getInt("earphone_echo_aaudio");
            if (i37 == 0 || i37 == 1) {
                hashMap.put("earphone_echo_aaudio", String.valueOf(i37));
            } else {
                StringBuilder sb37 = new StringBuilder();
                sb37.append("Fetched config doesn't pass:(value == 0 || value == 1) earphone_echo_aaudio = ");
                sb37.append(i37);
                Log.w(str3, sb37.toString());
                hashMap.put("earphone_echo_aaudio", str2);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: earphone_echo_aaudio");
            hashMap.put("earphone_echo_aaudio", str2);
        }
        if (jSONObject.has("earphone_echo_huawei")) {
            int i38 = jSONObject.getInt("earphone_echo_huawei");
            if (i38 == 0 || i38 == 1) {
                hashMap.put("earphone_echo_huawei", String.valueOf(i38));
            } else {
                StringBuilder sb38 = new StringBuilder();
                sb38.append("Fetched config doesn't pass:(value == 0 || value == 1) earphone_echo_huawei = ");
                sb38.append(i38);
                Log.w(str3, sb38.toString());
                hashMap.put("earphone_echo_huawei", str2);
            }
        } else {
            Log.w(str3, "Fetched config doesn't contain: earphone_echo_huawei");
            hashMap.put("earphone_echo_huawei", str2);
        }
        return hashMap;
    }

    private void fillWithDefaultValue(@NonNull VECloudConfig vECloudConfig) {
        vECloudConfig.mRecordCameraType = 1;
        vECloudConfig.mRecordCameraCompatLevel = 1;
        vECloudConfig.mRecordSWEncodeCRF = 15;
        vECloudConfig.mRecordVideoSWMaxrate = 15000000;
        vECloudConfig.mRecordVideoSWPreset = 0;
        vECloudConfig.mRecordVideoSWGop = 35;
        vECloudConfig.mRecordVideoSWQP = 2;
        vECloudConfig.mRecordSWBitrateMode = 1;
        vECloudConfig.mRecordHWEncodeBPS = 4194304;
        vECloudConfig.mRecordEncodeMode = 0;
        vECloudConfig.mRecordHwBitrateMode = 0;
        vECloudConfig.mRecordHwProfile = 0;
        vECloudConfig.mRecordResolutionWidth = 576;
        vECloudConfig.mRecordResolutionHeight = 1024;
        vECloudConfig.mImportSWEncodeCRF = 15;
        vECloudConfig.mImportVideoSWMaxrate = 15000000;
        vECloudConfig.mImportVideoSWPreset = 0;
        vECloudConfig.mImportVideoSWGop = 35;
        vECloudConfig.mImportVideoSWQP = 2;
        vECloudConfig.mImportSWBitrateMode = 0;
        vECloudConfig.mImportEncodeMode = 0;
        vECloudConfig.mImportHWEncodeBPS = 4194304;
        vECloudConfig.mImportHwBitrateMode = 0;
        vECloudConfig.mImportHwProfile = 0;
        vECloudConfig.mImportShortEdgeValue = 576;
        vECloudConfig.mCompileEncodeSWCRF = 15;
        vECloudConfig.mCompileEncodeSWMaxrate = 15000000;
        vECloudConfig.mCompileEncodeSWCRFPreset = 0;
        vECloudConfig.mCompileEncodeSWGOP = 35;
        vECloudConfig.mCompileVideoSWQP = 2;
        vECloudConfig.mCompileSWBitrateMode = 1;
        vECloudConfig.mCompileEncodeMode = 0;
        vECloudConfig.mCompileEncodeHWBPS = 4194304;
        vECloudConfig.mCompileHwBitrateMode = 0;
        vECloudConfig.mCompileHwProfile = 0;
        vECloudConfig.mEarphoneEchoNormal = 1;
        vECloudConfig.mEarphoneEchoAAudio = 1;
        vECloudConfig.mEarphoneEchoHuawei = 1;
    }

    public void inject(Map<String, String> map, @NonNull VECloudConfig vECloudConfig) {
        fillWithDefaultValue(vECloudConfig);
        if (map == null) {
            Log.w(TAG, "Inject source map is null. Everything will be overridden with default value in CloudConfig!!!");
        }
        String str = "record_camera_type";
        if (map.containsKey(str)) {
            vECloudConfig.mRecordCameraType = Integer.parseInt((String) map.get(str));
        }
        String str2 = "record_camera_compat_level";
        if (map.containsKey(str2)) {
            vECloudConfig.mRecordCameraCompatLevel = Integer.parseInt((String) map.get(str2));
        }
        String str3 = "record_video_sw_crf";
        if (map.containsKey(str3)) {
            vECloudConfig.mRecordSWEncodeCRF = Integer.parseInt((String) map.get(str3));
        }
        String str4 = "record_video_sw_maxrate";
        if (map.containsKey(str4)) {
            vECloudConfig.mRecordVideoSWMaxrate = Integer.parseInt((String) map.get(str4));
        }
        String str5 = "record_video_sw_preset";
        if (map.containsKey(str5)) {
            vECloudConfig.mRecordVideoSWPreset = Integer.parseInt((String) map.get(str5));
        }
        String str6 = "record_video_sw_gop";
        if (map.containsKey(str6)) {
            vECloudConfig.mRecordVideoSWGop = Integer.parseInt((String) map.get(str6));
        }
        String str7 = "record_video_sw_qp";
        if (map.containsKey(str7)) {
            vECloudConfig.mRecordVideoSWQP = Integer.parseInt((String) map.get(str7));
        }
        String str8 = "record_sw_bitrate_mode";
        if (map.containsKey(str8)) {
            vECloudConfig.mRecordSWBitrateMode = Integer.parseInt((String) map.get(str8));
        }
        String str9 = "record_video_hw_bitrate";
        if (map.containsKey(str9)) {
            vECloudConfig.mRecordHWEncodeBPS = Integer.parseInt((String) map.get(str9));
        }
        String str10 = "record_encode_mode";
        if (map.containsKey(str10)) {
            vECloudConfig.mRecordEncodeMode = Integer.parseInt((String) map.get(str10));
        }
        String str11 = "record_hw_bitrate_mode";
        if (map.containsKey(str11)) {
            vECloudConfig.mRecordHwBitrateMode = Integer.parseInt((String) map.get(str11));
        }
        String str12 = "record_hw_profile";
        if (map.containsKey(str12)) {
            vECloudConfig.mRecordHwProfile = Integer.parseInt((String) map.get(str12));
        }
        String str13 = "record_resolution_width";
        if (map.containsKey(str13)) {
            vECloudConfig.mRecordResolutionWidth = Integer.parseInt((String) map.get(str13));
        }
        String str14 = "record_resolution_height";
        if (map.containsKey(str14)) {
            vECloudConfig.mRecordResolutionHeight = Integer.parseInt((String) map.get(str14));
        }
        String str15 = "import_video_sw_crf";
        if (map.containsKey(str15)) {
            vECloudConfig.mImportSWEncodeCRF = Integer.parseInt((String) map.get(str15));
        }
        if (map.containsKey("import_video_sw_maxrate")) {
            vECloudConfig.mImportVideoSWMaxrate = Integer.parseInt((String) map.get("import_video_sw_maxrate"));
        }
        if (map.containsKey("import_video_sw_preset")) {
            vECloudConfig.mImportVideoSWPreset = Integer.parseInt((String) map.get("import_video_sw_preset"));
        }
        if (map.containsKey("import_video_sw_gop")) {
            vECloudConfig.mImportVideoSWGop = Integer.parseInt((String) map.get("import_video_sw_gop"));
        }
        if (map.containsKey("import_video_sw_qp")) {
            vECloudConfig.mImportVideoSWQP = Integer.parseInt((String) map.get("import_video_sw_qp"));
        }
        if (map.containsKey("import_sw_bitrate_mode")) {
            vECloudConfig.mImportSWBitrateMode = Integer.parseInt((String) map.get("import_sw_bitrate_mode"));
        }
        if (map.containsKey("import_encode_mode")) {
            vECloudConfig.mImportEncodeMode = Integer.parseInt((String) map.get("import_encode_mode"));
        }
        if (map.containsKey("import_video_hw_bitrate")) {
            vECloudConfig.mImportHWEncodeBPS = Integer.parseInt((String) map.get("import_video_hw_bitrate"));
        }
        if (map.containsKey("import_hw_bitrate_mode")) {
            vECloudConfig.mImportHwBitrateMode = Integer.parseInt((String) map.get("import_hw_bitrate_mode"));
        }
        if (map.containsKey("import_hw_profile")) {
            vECloudConfig.mImportHwProfile = Integer.parseInt((String) map.get("import_hw_profile"));
        }
        if (map.containsKey("import_shorter_pixels")) {
            vECloudConfig.mImportShortEdgeValue = Integer.parseInt((String) map.get("import_shorter_pixels"));
        }
        if (map.containsKey("synthetic_video_sw_crf")) {
            vECloudConfig.mCompileEncodeSWCRF = Integer.parseInt((String) map.get("synthetic_video_sw_crf"));
        }
        if (map.containsKey("synthetic_video_sw_maxrate")) {
            vECloudConfig.mCompileEncodeSWMaxrate = Integer.parseInt((String) map.get("synthetic_video_sw_maxrate"));
        }
        if (map.containsKey("synthetic_video_sw_preset")) {
            vECloudConfig.mCompileEncodeSWCRFPreset = Integer.parseInt((String) map.get("synthetic_video_sw_preset"));
        }
        if (map.containsKey("synthetic_video_sw_gop")) {
            vECloudConfig.mCompileEncodeSWGOP = Integer.parseInt((String) map.get("synthetic_video_sw_gop"));
        }
        if (map.containsKey("synthetic_video_sw_qp")) {
            vECloudConfig.mCompileVideoSWQP = Integer.parseInt((String) map.get("synthetic_video_sw_qp"));
        }
        if (map.containsKey("synthetic_sw_bitrate_mode")) {
            vECloudConfig.mCompileSWBitrateMode = Integer.parseInt((String) map.get("synthetic_sw_bitrate_mode"));
        }
        if (map.containsKey("synthetic_encode_mode")) {
            vECloudConfig.mCompileEncodeMode = Integer.parseInt((String) map.get("synthetic_encode_mode"));
        }
        if (map.containsKey("synthetic_video_hw_bitrate")) {
            vECloudConfig.mCompileEncodeHWBPS = Integer.parseInt((String) map.get("synthetic_video_hw_bitrate"));
        }
        if (map.containsKey("synthetic_hw_bitrate_mode")) {
            vECloudConfig.mCompileHwBitrateMode = Integer.parseInt((String) map.get("synthetic_hw_bitrate_mode"));
        }
        if (map.containsKey("synthetic_hw_profile")) {
            vECloudConfig.mCompileHwProfile = Integer.parseInt((String) map.get("synthetic_hw_profile"));
        }
        if (map.containsKey("earphone_echo_normal")) {
            vECloudConfig.mEarphoneEchoNormal = Integer.parseInt((String) map.get("earphone_echo_normal"));
        }
        if (map.containsKey("earphone_echo_aaudio")) {
            vECloudConfig.mEarphoneEchoAAudio = Integer.parseInt((String) map.get("earphone_echo_aaudio"));
        }
        if (map.containsKey("earphone_echo_huawei")) {
            vECloudConfig.mEarphoneEchoHuawei = Integer.parseInt((String) map.get("earphone_echo_huawei"));
        }
    }

    public Map<String, String> parse(@NonNull JSONObject jSONObject) {
        String str = TAG;
        String str2 = "code";
        try {
            if (jSONObject.getInt(str2) == 0) {
                return createParamMap(jSONObject.getJSONObject(PhotosOemApi.PATH_SPECIAL_TYPE_DATA));
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Fetched Config return code is not 0 but ");
            sb.append(jSONObject.getInt(str2));
            Log.e(str, sb.toString());
            return null;
        } catch (JSONException e2) {
            Log.e(str, e2.getMessage());
            e2.printStackTrace();
            return null;
        }
    }
}
