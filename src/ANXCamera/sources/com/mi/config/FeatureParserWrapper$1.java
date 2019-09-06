package com.mi.config;

import java.util.HashMap;

class FeatureParserWrapper$1 extends HashMap<String, String> {
    FeatureParserWrapper$1() {
        put(d.Kp, "o_0x00_s_s_l");
        put(d.Ro, "o_0x01_r_p_s_f");
        put(d.VENDOR, "o_0x02_soc_vendor");
        put(d.Pp, "o_0x03_support_3d_face_beauty");
        put(d.Qp, "o_0x04_support_mi_face_beauty");
        put(d.Cp, "o_0x05_is_support_optical_zoom");
        put(d.No, "o_0x06_is_support_peaking_mf");
        put(d.Tp, "o_0x08_is_support_dynamic_light_spot");
        put(d.wo, "o_0x07_support_hfr");
        put(d.jo, "o_0x08_support_movie_solid");
        put(d.Vo, "o_0x09_support_tilt_shift");
        put(d.Oo, "o_0x10_support_gradienter");
        put(d.Xp, "o_0x11_picture_water_mark");
        put(d.Wo, "o_0x12_magic_mirror");
        put(d.lo, "o_0x13_age_detection");
        put(d.f245io, "o_0x14_burst_count");
        put(d._n, "o_0x15_support_dual_sd_card");
        put(d.Mp, "o_0x16_support_psensor_pocket_mode");
        put(d.Vp, "o_0x17_support_super_resolution");
        put(d.Xo, "o_0x18_support_camera_quick_snap");
    }

    public String put(String str, String str2) {
        String str3 = "The key \"";
        if (str2 == null || !str2.startsWith("o_0x")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str3);
            sb.append(str);
            sb.append("\" must be mapped to non-null string starting with \"o_0x\"");
            throw new IllegalStateException(sb.toString());
        }
        String str4 = (String) super.put(str, str2);
        if (str4 == null) {
            return null;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str3);
        sb2.append(str);
        sb2.append("\" has already be mapped to \"");
        sb2.append(str4);
        sb2.append("\"");
        throw new IllegalStateException(sb2.toString());
    }
}
