package com.mi.config;

import com.android.camera.data.DataRepository;
import java.util.Collections;
import java.util.Map;
import miui.util.FeatureParser;

@Deprecated
/* compiled from: FeatureParserWrapper */
public class d {
    public static final String Ao = "support_camera_ubifocus";
    public static final String Ap = "is_hrf_video_capture_support";
    public static final String Bo = "camera_supported_scene";
    public static final String Bp = "is_support_stereo";
    public static final String Co = "camera_supported_ai_scene";
    public static final String Cp = "is_support_optical_zoom";
    public static final String Do = "camera_supported_asd";
    public static final String Dp = "is_support_portrait";
    public static final String Eo = "support_camera_audio_focus";
    public static final String Ep = "camera_is_support_portrait_front";
    public static final String Fo = "is_camera_use_morpho_lib";
    public static final String Fp = "is_support_fhd_fhr";
    public static final String Go = "is_camera_replace_higher_cost_effect";
    public static final String Gp = "is_rgb888_egl_prefer";
    public static final String HIBERNATION_TIMEOUT = "camera_hibernation_timeout_in_minutes";
    public static final String Ho = "support_camera_manual_function";
    public static final String Hp = "is_legacy_face_beauty";
    public static final String IS_HONGMI = "is_hongmi";
    public static final String IS_XIAOMI = "is_xiaomi";
    public static final String Io = "support_camera_press_down_capture";
    public static final String Ip = "use_legacy_normal_filter";
    public static final String Jo = "support_camera_torch_capture";
    public static final String Jp = "support_front_hht_enhance";
    public static final String Ko = "is_camera_freeze_after_hdr_capture";
    public static final String Kp = "support_screen_light";
    public static final String Lo = "is_camera_face_detection_need_orientation";
    public static final String Lp = "support_parallel_process";
    public static final String Mo = "is_camera_hold_blur_background";
    public static final String Mp = "support_psensor_pocket_mode";
    public static final String No = "support_camera_peaking_mf";
    public static final String Np = "support_front_beauty_mfnr";
    public static final String Oo = "support_camera_gradienter";
    public static final String Op = "support_video_hfr_mode";
    public static final String Po = "is_camera_lower_qrscan_frequency";
    public static final String Pp = "support_3d_face_beauty";
    public static final String Qo = "is_camera_preview_with_subthread_looper";
    public static final String Qp = "support_mi_face_beauty";
    public static final String Ro = "camera_reduce_preview_flag";
    public static final String Rp = "support_lens_dirty_detect";
    public static final String So = "camera_focus_success_flag";
    public static final String Sp = "enable_algorithm_in_file_suffix";
    public static final String To = "camera_exposure_compensation_steps_num";
    public static final String Tp = "support_camera_dynamic_light_spot";
    public static final String Uo = "is_camera_app_water_mark";
    public static final String Up = "support_zoom_mfnr";
    public static final String VENDOR = "vendor";
    public static final String Vo = "support_camera_tilt_shift";
    public static final String Vp = "support_super_resolution";
    public static final String Wo = "support_camera_magic_mirror";
    public static final String Wp = "support_realtime_manual_exposure_time";
    public static final String Xo = "support_camera_quick_snap";
    public static final String Xp = "support_picture_watermark";
    public static final String Yo = "camera_front_count_down_margin";
    public static final String Yp = "sensor_has_latency";
    public static final String Zn = "is_pad";
    public static final String Zo = "support_camera_groupshot";
    private static final Map<String, String> Zp = Collections.unmodifiableMap(new FeatureParserWrapper$1());
    public static final String _n = "support_dual_sd_card";
    public static final String _o = "is_full_size_effect";
    public static final String ao = "support_edge_handgrip";
    public static final String ap = "support_camera_face_info_water_mark";
    public static final String bo = "camera_continuous_shot_callback_class";
    public static final String bp = "support_camera_square_mode";
    public static final String cp = "is_camera_use_still_effect_image";

    /* renamed from: do  reason: not valid java name */
    public static final String f2do = "camera_continuous_shot_callback_setter";
    public static final String dp = "support_front_flash";
    public static final String eo = "fp_nav_event_name_list";
    public static final String ep = "support_video_front_flash";
    public static final String fo = "support_camera_shader_effect";
    public static final String fp = "is_camera_isp_rotated";
    public static final String go = "support_camera_burst_shoot";
    public static final String gp = "support_full_size_panorama";
    public static final String ho = "support_camera_burst_shoot_denoise";
    public static final String hp = "support_hfr_video_pause";

    /* renamed from: io  reason: collision with root package name */
    public static final String f245io = "burst_shoot_count";
    public static final String jo = "support_camera_movie_solid";
    public static final String jp = "is_front_remosic_sensor";
    public static final String ko = "support_camera_skin_beauty";
    public static final String kp = "front_fingerprint_sensor";
    public static final String lo = "support_camera_age_detection";
    public static final String mo = "support_camera_record_location";
    public static final String mp = "cmcc_strategic_phone";
    public static final String np = "is_need_force_recycle_effect";
    public static final String oo = "support_camera_water_mark";
    public static final String po = "support_camera_new_style_time_water_mark";
    public static final String qo = "support_camera_video_pause";
    public static final String qp = "is_18x9_ratio_screen";
    public static final String rp = "camera_extra_picture_size";
    public static final String so = "support_camera_boost_brightness";
    public static final String sp = "is_support_tele_asd_night";
    public static final String tp = "is_front_video_quality_1080p";
    public static final String uo = "is_lower_size_effect";
    public static final String vo = "support_camera_aohdr";
    public static final String vp = "is_capture_stop_face_detection";
    public static final String wo = "support_camera_hfr";
    public static final String wp = "is_video_snapshot_size_limit";
    public static final String xo = "support_chroma_flash";
    public static final String xp = "is_surface_size_limit";
    public static final String yo = "support_object_track";
    public static final String yp = "is_hal_does_caf_when_flash_on";
    public static final String zo = "support_camera_4k_quality";
    public static final String zp = "is_new_hdr_param_key_used";

    private static String H(String str) {
        return Zp.get(str);
    }

    public static boolean getBoolean(String str, boolean z) {
        String H = H(str);
        return (H == null || !DataRepository.dataItemFeature().l(H)) ? FeatureParser.getBoolean(str, z) : DataRepository.dataItemFeature().getBoolean(H, z);
    }

    public static Float getFloat(String str, float f2) {
        String H = H(str);
        return (H == null || !DataRepository.dataItemFeature().l(H)) ? FeatureParser.getFloat(str, f2) : Float.valueOf(DataRepository.dataItemFeature().getFloat(H, f2));
    }

    public static int getInteger(String str, int i) {
        String H = H(str);
        return (H == null || !DataRepository.dataItemFeature().l(H)) ? FeatureParser.getInteger(str, i) : DataRepository.dataItemFeature().getInt(H, i);
    }

    public static String getString(String str) {
        String H = H(str);
        return (H == null || !DataRepository.dataItemFeature().l(H)) ? FeatureParser.getString(str) : DataRepository.dataItemFeature().getString(H, "N/A");
    }

    public static String[] getStringArray(String str) {
        return FeatureParser.getStringArray(str);
    }
}
