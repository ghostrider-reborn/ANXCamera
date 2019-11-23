package com.mi.config;

import java.util.HashMap;

class FeatureParserWrapper$1 extends HashMap<String, String> {
    FeatureParserWrapper$1() {
        put(d.xY, "o_0x00_s_s_l");
        put(d.xi, "o_0x01_r_p_s_f");
        put(d.wu, "o_0x02_soc_vendor");
        put(d.yd, "o_0x03_support_3d_face_beauty");
        put(d.ye, "o_0x04_support_mi_face_beauty");
        put(d.xP, "o_0x05_is_support_optical_zoom");
        put(d.xe, "o_0x06_is_support_peaking_mf");
        put(d.yh, "o_0x08_is_support_dynamic_light_spot");
        put(d.wO, "o_0x07_support_hfr");
        put(d.wE, "o_0x08_support_movie_solid");
        put(d.xm, "o_0x09_support_tilt_shift");
        put(d.xf, "o_0x10_support_gradienter");
        put(d.yk, "o_0x11_picture_water_mark");
        put(d.xn, "o_0x12_magic_mirror");
        put(d.wG, "o_0x13_age_detection");
        put(d.wD, "o_0x14_burst_count");
        put(d.wv, "o_0x15_support_dual_sd_card");
        put(d.ya, "o_0x16_support_psensor_pocket_mode");
        put(d.yi, "o_0x17_support_super_resolution");
        put(d.xo, "o_0x18_support_camera_quick_snap");
        put(d.yl, "o_0x19_camera_role");
    }

    public String put(String str, String str2) {
        if (str2 == null || !str2.startsWith("o_0x")) {
            throw new IllegalStateException("The key \"" + str + "\" must be mapped to non-null string starting with \"o_0x\"");
        }
        String str3 = (String) super.put(str, str2);
        if (str3 == null) {
            return null;
        }
        throw new IllegalStateException("The key \"" + str + "\" has already be mapped to \"" + str3 + "\"");
    }
}
