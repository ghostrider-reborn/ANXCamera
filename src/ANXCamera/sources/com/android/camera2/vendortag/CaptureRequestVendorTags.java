package com.android.camera2.vendortag;

import android.graphics.Rect;
import android.hardware.camera2.CaptureRequest;
import android.util.Log;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import com.mi.config.b;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class CaptureRequestVendorTags {
    public static final VendorTag<CaptureRequest.Key<Boolean>> AI_SCENE = create($$Lambda$CaptureRequestVendorTags$HILu0RXNksnz1vcqpQUFkZdmqso.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AI_SCENE_APPLY = create($$Lambda$CaptureRequestVendorTags$IF_7fPep7jSIN4LgAnxLIPCTbM.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AI_SCENE_PERIOD = create($$Lambda$CaptureRequestVendorTags$j0NE7DX4b6Qn5RwTqkgs2RmOTLQ.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> ASD_DIRTY_ENABLE = create($$Lambda$CaptureRequestVendorTags$zR2FO3H2HmjJSa8HpwUEsuib8SE.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_APPLY_IN_PREVIEW = create($$Lambda$CaptureRequestVendorTags$c8qIrgMwCaSY_fflIKRmnCkNcs.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<float[]>> AUTOZOOM_CENTER_OFFSET = create($$Lambda$CaptureRequestVendorTags$FMnrggI5GKjJVs4BWd54EvGUWRo.INSTANCE, float[].class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_FORCE_LOCK = create($$Lambda$CaptureRequestVendorTags$DIxFBeXzVNre9o2dCrlErDk7gXk.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Float>> AUTOZOOM_MINIMUM_SCALING = create($$Lambda$CaptureRequestVendorTags$JhUzVLNDDK1VpIRI0LzjersRtiI.INSTANCE, Float.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_MODE = create($$Lambda$CaptureRequestVendorTags$ngFT8jvIscAymjXAm5MvEHmK4rc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Float>> AUTOZOOM_SCALE_OFFSET = create($$Lambda$CaptureRequestVendorTags$1Mj1QaCI3HWhIV5dkBjNMAok54.INSTANCE, Float.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_SELECT = create($$Lambda$CaptureRequestVendorTags$c9Y8GNLT0S9D6UB4s962pEuwc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<float[]>> AUTOZOOM_START = create($$Lambda$CaptureRequestVendorTags$8AM09VTJ08SaHcTZdeWJDXoajrk.INSTANCE, float[].class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_STOP = create($$Lambda$CaptureRequestVendorTags$gnPEjHlBRH6TX1KFVsXMiUTL10.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_UNSELECT = create($$Lambda$CaptureRequestVendorTags$Ea59wfk0Ucso2_YFXlgPMysyRsw.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> BACKWARD_CAPTURE_HINT = create($$Lambda$CaptureRequestVendorTags$2OCTSB33Ee9h31j3LR0u1AWboY.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> BACK_SOFT_LIGHT = create($$Lambda$CaptureRequestVendorTags$9CdBzm0Kn3rcym41Q5tRV4TSR4.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_BLUSHER = create($$Lambda$CaptureRequestVendorTags$ACZWhXyKAjy2C7X69wKvWIw3amo.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_BODY_SLIM = create($$Lambda$CaptureRequestVendorTags$hDburGihh0YWlFC0oV6yLYqhg.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_CHIN = create($$Lambda$CaptureRequestVendorTags$YZ5AL7icUsZTVWae2tT4Ysqai4w.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_ENLARGE_EYE = create($$Lambda$CaptureRequestVendorTags$GUjXgCIXZ2LPNKZbv325kS1vO6U.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_EYEBROW_DYE = create($$Lambda$CaptureRequestVendorTags$z6AiN8IsbtieyrXvGIDpZ4Oisk.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_HAIRLINE = create($$Lambda$CaptureRequestVendorTags$eI_evtNnLMzh6GfAPP9k9TC7yJA.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_HEAD_SLIM = create($$Lambda$CaptureRequestVendorTags$xuWSjDFqulHa_e5I7EEp6x0jqg.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_JELLY_LIPS = create($$Lambda$CaptureRequestVendorTags$HByGZ8HiXjXUVub5pZLHBBo5A.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_LEG_SLIM = create($$Lambda$CaptureRequestVendorTags$VDMt5_MmHKYWm0_KGdqM94QuM9I.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<String>> BEAUTY_LEVEL = create($$Lambda$CaptureRequestVendorTags$2iFEEyXC1jpz_GvKoMRemUhWo_s.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_LIPS = create($$Lambda$CaptureRequestVendorTags$DGB3ZyGBfkj1bKwPkcReOlduI.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_NECK = create($$Lambda$CaptureRequestVendorTags$XaYZ2GOOOZbLjaeI1GzUyg5Gxpo.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_NOSE = create($$Lambda$CaptureRequestVendorTags$Jgt2QCMh3sDUlPpwwDorDZjy8.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_PUPIL_LINE = create($$Lambda$CaptureRequestVendorTags$htDy7KGtPff58XZPzkdKeUNe4kY.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_RISORIUS = create($$Lambda$CaptureRequestVendorTags$vSCtzeOe8p5EuZE_YPhEGkfTCfE.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SHOULDER_SLIM = create($$Lambda$CaptureRequestVendorTags$yI6gZu2Fm64BDQBeBmrtrRwSA5o.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SKIN_COLOR = create($$Lambda$CaptureRequestVendorTags$X7qUkvcDbZ4psSIxyLoHNgJcl8.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SKIN_SMOOTH = create($$Lambda$CaptureRequestVendorTags$BHtDch0Gs4ItvQ6WazDxALvn4A.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SLIM_FACE = create($$Lambda$CaptureRequestVendorTags$k33SiSO1i_ShyPj0gGa5h28cxec.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SLIM_NOSE = create($$Lambda$CaptureRequestVendorTags$YjifdVuJb3AUfENtjkQhfGICQvc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SMILE = create($$Lambda$CaptureRequestVendorTags$BDuaJ7S1SHE5tl2Y0QHHd0BvNng.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<String>> BOKEH_F_NUMBER = create($$Lambda$CaptureRequestVendorTags$Ggc_jDwit6L5rtNFiIOZ2xdJZjU.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BURST_CAPTURE_HINT = create($$Lambda$CaptureRequestVendorTags$P4QmFXA98tCuux2q_IBfwWsJN6I.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BURST_SHOOT_FPS = create($$Lambda$CaptureRequestVendorTags$9JNAHQZhisLvIjliakpBP0IA2Ec.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BUTT_SLIM = create($$Lambda$CaptureRequestVendorTags$rulajpsKI4yIptijAFPRJyECf8.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> CAMERA_AI_30 = create($$Lambda$CaptureRequestVendorTags$aw9iMHPxMnC_QHiGj8OiITOJNpc.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> CONTRAST_LEVEL = create($$Lambda$CaptureRequestVendorTags$iDEPwF0wUCZhoapWAEEvOHE5vjw.INSTANCE, Integer.class);
    public static VendorTag<CaptureRequest.Key<Integer>> CONTROL_AI_SCENE_MODE = create($$Lambda$CaptureRequestVendorTags$WOTAPPjnB61KtyGFnsSOKeV3MjI.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<byte[]>> CONTROL_DISTORTION_FPC_DATA = create($$Lambda$CaptureRequestVendorTags$pX2QCaswHQRrEIjlMKNvBzieM5w.INSTANCE, byte[].class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> CONTROL_ENABLE_REMOSAIC = create($$Lambda$CaptureRequestVendorTags$KR8NZ2w4cM1GNpWEMbovIo_nYfo.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<int[]>> CONTROL_QUICK_PREVIEW = create($$Lambda$CaptureRequestVendorTags$3cAtBJyJ3KjImb0VW3Aj5hyPuHk.INSTANCE, int[].class);
    public static final int[] CONTROL_QUICK_PREVIEW_OFF = {0};
    public static final int[] CONTROL_QUICK_PREVIEW_ON = {1};
    public static final VendorTag<CaptureRequest.Key<int[]>> CONTROL_REMOSAIC_HINT = create($$Lambda$CaptureRequestVendorTags$IYoru3o01XJ5zRfIvwla42SZUWA.INSTANCE, int[].class);
    public static final int[] CONTROL_REMOSAIC_HINT_OFF = {0};
    public static final int[] CONTROL_REMOSAIC_HINT_ON = {1};
    public static final VendorTag<CaptureRequest.Key<String>> CUSTOM_WATERMARK_TEXT = create($$Lambda$CaptureRequestVendorTags$XN_FL2E3DJbXgfuwTE59Mr7zIM.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> DEFLICKER_ENABLED = create($$Lambda$CaptureRequestVendorTags$sEnRC09tEh3Mz3pVmgCAbmY3t8I.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> DEPURPLE = create($$Lambda$CaptureRequestVendorTags$wKDY0c0gXhzCK4iWxBroSGPOVF4.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> DEVICE_ORIENTATION = create($$Lambda$CaptureRequestVendorTags$X1e0821cSwGhU4edmz_bTQOdeDk.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> EXPOSURE_METERING = create($$Lambda$CaptureRequestVendorTags$OCaAdvvErqHlAttFuaRzLd40Hw.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> EYE_LIGHT_STRENGTH = create($$Lambda$CaptureRequestVendorTags$1M_nHhSofLjX5WWNPkMzh09VBM.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> EYE_LIGHT_TYPE = create($$Lambda$CaptureRequestVendorTags$L58TFNWbeEB7SkUmVmFLtVDRdVs.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FACE_AGE_ANALYZE_ENABLED = create($$Lambda$CaptureRequestVendorTags$zw6Qxt69ba9BOWUr5mTFaszhGig.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FACE_SCORE_ENABLED = create($$Lambda$CaptureRequestVendorTags$qShAD55VwLfK1o_e17JFzNJG0.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FLAW_DETECT_ENABLE = create($$Lambda$CaptureRequestVendorTags$LY200S4xyiQIRHle8d1DOTOb6E8.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FRONT_MIRROR = create($$Lambda$CaptureRequestVendorTags$P7JEoBfionuz9E6F2I3RKPIsM.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FRONT_SINGLE_CAMERA_BOKEH = create($$Lambda$CaptureRequestVendorTags$pcEuwJnbRXBhrwh16LwSC9FiQ0Q.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> HDR_BRACKET_MODE = create($$Lambda$CaptureRequestVendorTags$LQsEkEW4macs4jg3iYWkRrKE1oo.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HDR_CHECKER_ENABLE = create($$Lambda$CaptureRequestVendorTags$mDvXeQIj4FltabddFWrIpQueC88.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HDR_ENABLED = create($$Lambda$CaptureRequestVendorTags$l_lJ7lq3jSGzCP4GuXW5BQdQ.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> HFPSVR_MODE = create($$Lambda$CaptureRequestVendorTags$JeXp8ydUmrJYSY8ttDo1IV0AaTg.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HHT_ENABLED = create($$Lambda$CaptureRequestVendorTags$2E2Nv8Ppdjs6BAySwS_JjnbS8gU.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HINT_FOR_RAW_REPROCESS = create($$Lambda$CaptureRequestVendorTags$R7uaSMmVj296Do6eZZM8VeY9s.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Long>> ISO_EXP = create($$Lambda$CaptureRequestVendorTags$F1fSvXInzrK13kLy20pRFR9YGMM.INSTANCE, Long.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> IS_HFR_PREVIEW = create($$Lambda$CaptureRequestVendorTags$8Yp9zyKHJ5owf0XQjdE2310bA.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> LENS_DIRTY_DETECT = create($$Lambda$CaptureRequestVendorTags$2I_2SwGiJpNmQRedUNOLlcllQ.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> MACRO_MODE = create($$Lambda$CaptureRequestVendorTags$a28q0HoZC2GUBqjS134nmHPCYpU.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> MFNR_ENABLED = create($$Lambda$CaptureRequestVendorTags$BEJuL3ocfGndd8j0LPNKC69dq4.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> MTK_EXPOSURE_METERING_MODE = create($$Lambda$CaptureRequestVendorTags$PIosAq81tgexSoVOnL4JiwekbPU.INSTANCE, Byte.class);
    public static final byte MTK_EXPOSURE_METERING_MODE_AVERAGE = 2;
    public static final byte MTK_EXPOSURE_METERING_MODE_CENTER_WEIGHT = 0;
    public static final byte MTK_EXPOSURE_METERING_MODE_SOPT = 1;
    public static final VendorTag<CaptureRequest.Key<Integer>> MULTIFRAME_INPUTNUM = create($$Lambda$CaptureRequestVendorTags$twcKjzJz_LX3c0WIMRpOHuglqk.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> MiMOVIE_ENABLED = create($$Lambda$CaptureRequestVendorTags$WJlovXo5mMD4cfOJ_yd5Y5h58o.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> NORMAL_WIDE_LENS_DISTORTION_CORRECTION_LEVEL = create($$Lambda$CaptureRequestVendorTags$PDwzYlOYUz_CLanUSCDRSM4m8uo.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<MarshalQueryableASDScene.ASDScene[]>> ON_TRIPOD_MODE = create($$Lambda$CaptureRequestVendorTags$YAH9CeUYOjVv8FKEh_LxgVjBeOs.INSTANCE, MarshalQueryableASDScene.ASDScene[].class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> PARALLEL_ENABLED = create($$Lambda$CaptureRequestVendorTags$Updl7cGjEiucVqyOqaapCJXMSis.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<byte[]>> PARALLEL_PATH = create($$Lambda$CaptureRequestVendorTags$iij5Fp_m6Nrvquk7RGgyUYUgI4U.INSTANCE, byte[].class);
    public static final VendorTag<CaptureRequest.Key<Integer>> PORTRAIT_LIGHTING = create($$Lambda$CaptureRequestVendorTags$erxqlMkCjh62eGDgYegF8uiSZeg.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Rect>> POST_PROCESS_CROP_REGION = create($$Lambda$CaptureRequestVendorTags$UqTB4rzNTHOm5R7r4exeERp0kug.INSTANCE, Rect.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> REAR_BOKEH_ENABLE = create($$Lambda$CaptureRequestVendorTags$VH1hhf7huMiommSa7qDEXsp4TOY.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> RECORDING_END_STREAM = create($$Lambda$CaptureRequestVendorTags$755CiLEb2JAenmPUYlbafIjARNk.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SANPSHOT_FLIP_MODE = create($$Lambda$CaptureRequestVendorTags$cn58amV3v8kSbInROtjcn0kQOJA.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SATURATION = create($$Lambda$CaptureRequestVendorTags$zGtn7rW_YSXBMNhP6MiYETLrqeE.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SAT_FALLBACK_ENABLE = create($$Lambda$CaptureRequestVendorTags$8dMZeuSsWRAjrUe4so9mLTTuqoA.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SAT_IS_ZOOMING = create($$Lambda$CaptureRequestVendorTags$MRi9tuKRw9lok9aOmQ41oF0_Aiw.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> SCREEN_LIGHT_HINT = create($$Lambda$CaptureRequestVendorTags$QjOKqY7uY9MGydVSSpDM5LpF3Q.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SELECT_PRIORITY = create($$Lambda$CaptureRequestVendorTags$1ZBolJO6K0DsGw2REPIteMWucFI.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SHARPNESS_CONTROL = create($$Lambda$CaptureRequestVendorTags$Zcs_by6sBTZHPJtRt7KgddOv2yI.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<int[]>> SMVR_MODE = create($$Lambda$CaptureRequestVendorTags$t8aEtF7v0nEkXXEJmrxn1PFMJw.INSTANCE, int[].class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SNAP_SHOT_TORCH = create($$Lambda$CaptureRequestVendorTags$PqtOSTKMenuOagjsBaSq0gMdMg.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> ST_ENABLED = create($$Lambda$CaptureRequestVendorTags$PKoizjg6Ai8OQWPHrosp2TPwZkI.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> ST_FAST_ZOOM_IN = create($$Lambda$CaptureRequestVendorTags$R7w2a6GsSJtj46fl7s7Gq9tfvqM.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SUPER_NIGHT_SCENE_ENABLED = create($$Lambda$CaptureRequestVendorTags$853AXUIzHKHm7T4tutNtLL7SFB8.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SUPER_RESOLUTION_ENABLED = create($$Lambda$CaptureRequestVendorTags$AXo8WznLe0GH0tLuqMcSY9dpjQ8.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SW_MFNR_ENABLED = create($$Lambda$CaptureRequestVendorTags$mlQiJhyauT7d2hmG1v3CF_Lx8.INSTANCE, Boolean.class);
    private static final String TAG = "CaptureRequestVendorTags";
    public static VendorTag<CaptureRequest.Key<Boolean>> ULTRA_PIXEL_PORTRAIT_ENABLED = create($$Lambda$CaptureRequestVendorTags$0i_h7NPpczIHWfwzU9ipeXS_xWk.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> ULTRA_WIDE_LENS_DISTORTION_CORRECTION_LEVEL = create($$Lambda$CaptureRequestVendorTags$Pyr8YxQGEum0zpYwhtvqDP3PRxs.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> USE_CUSTOM_WB = create($$Lambda$CaptureRequestVendorTags$xkm4igvM5ijR0WcgqmIMMaCRwoY.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> USE_ISO_VALUE = create($$Lambda$CaptureRequestVendorTags$1DR3P1Yr1JOB4EOSSxlsO646AY.INSTANCE, Integer.class);
    public static final int VALUE_HFPSVR_MODE_OFF = 0;
    public static final int VALUE_HFPSVR_MODE_ON = 1;
    public static final int VALUE_SANPSHOT_FLIP_MODE_OFF = 0;
    public static final int VALUE_SANPSHOT_FLIP_MODE_ON = 1;
    public static final int VALUE_SELECT_PRIORITY_EXP_TIME_PRIORITY = 1;
    public static final int VALUE_SELECT_PRIORITY_ISO_PRIORITY = 0;
    public static final int[] VALUE_SMVR_MODE_120FPS = {120, 4};
    public static final int[] VALUE_SMVR_MODE_240FPS = {240, 8};
    public static final int VALUE_VIDEO_RECORD_CONTROL_PREPARE = 0;
    public static final int VALUE_VIDEO_RECORD_CONTROL_START = 1;
    public static final int VALUE_VIDEO_RECORD_CONTROL_STOP = 2;
    public static final byte VALUE_ZSL_CAPTURE_MODE_OFF = 0;
    public static final byte VALUE_ZSL_CAPTURE_MODE_ON = 1;
    public static final VendorTag<CaptureRequest.Key<Integer>> VIDEO_RECORD_CONTROL = create($$Lambda$CaptureRequestVendorTags$3bWdsY79sNXCz1Ib2ohRz_oJs.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<String>> WATERMARK_APPLIEDTYPE = create($$Lambda$CaptureRequestVendorTags$LfyibVkh_qYuNcF0RKBQrhcYBi8.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<String>> WATERMARK_AVAILABLETYPE = create($$Lambda$CaptureRequestVendorTags$3yBN_ejOckSpoh2_12hO47OJqGU.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<String>> WATERMARK_FACE = create($$Lambda$CaptureRequestVendorTags$ZPKjGda4RS3IUlhLi5NAQMRB2hc.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<String>> WATERMARK_TIME = create($$Lambda$CaptureRequestVendorTags$m53irQd9bwdsGbRq7fy59yGl4gw.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> WHOLE_BODY_SLIM = create($$Lambda$CaptureRequestVendorTags$8k9IwcX1hS4syErDGZeIgHMi2Q.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> ZSL_CAPTURE_MODE = create($$Lambda$CaptureRequestVendorTags$vPM_W4oGDno62U41mINm4Nif5YY.INSTANCE, Byte.class);
    private static Constructor<CaptureRequest.Key> requestConstructor;

    private static <T> VendorTag<CaptureRequest.Key<T>> create(final Supplier<String> supplier, final Class<T> cls) {
        return new VendorTag<CaptureRequest.Key<T>>() {
            /* access modifiers changed from: protected */
            public CaptureRequest.Key<T> create() {
                return CaptureRequestVendorTags.requestKey(getName(), cls);
            }

            public String getName() {
                return (String) supplier.get();
            }
        };
    }

    static /* synthetic */ String lambda$static$0() {
        return "com.vidhance.autozoom.mode";
    }

    static /* synthetic */ String lambda$static$1() {
        return "com.vidhance.autozoom.applyinpreview";
    }

    static /* synthetic */ String lambda$static$10() {
        return "xiaomi.video.recordControl";
    }

    static /* synthetic */ String lambda$static$100() {
        return "xiaomi.superResolution.cropRegionMtk";
    }

    static /* synthetic */ String lambda$static$101() {
        return "com.mediatek.3afeature.aeMeteringMode";
    }

    static /* synthetic */ String lambda$static$102() {
        return "com.mediatek.configure.setting.initrequest";
    }

    static /* synthetic */ String lambda$static$103() {
        return "xiaomi.ai.asd.sceneDetected";
    }

    static /* synthetic */ String lambda$static$104() {
        return "xiaomi.ai.misd.StateScene";
    }

    static /* synthetic */ String lambda$static$105() {
        return "xiaomi.ai.flaw.enabled";
    }

    static /* synthetic */ String lambda$static$106() {
        return "xiaomi.ai.asd.dirtyEnable";
    }

    static /* synthetic */ String lambda$static$11() {
        return "xiaomi.hdr.enabled";
    }

    static /* synthetic */ String lambda$static$12() {
        return "xiaomi.hdr.hdrChecker.enabled";
    }

    static /* synthetic */ String lambda$static$13() {
        return "xiaomi.parallel.path";
    }

    static /* synthetic */ String lambda$static$14() {
        return "xiaomi.parallel.enabled";
    }

    static /* synthetic */ String lambda$static$15() {
        return "xiaomi.hht.enabled";
    }

    static /* synthetic */ String lambda$static$16() {
        return "xiaomi.node.hfr.deflicker.enabled";
    }

    static /* synthetic */ String lambda$static$17() {
        return "xiaomi.superportrait.enabled";
    }

    static /* synthetic */ String lambda$static$18() {
        return "xiaomi.superResolution.enabled";
    }

    static /* synthetic */ String lambda$static$19() {
        return "xiaomi.mfnr.enabled";
    }

    static /* synthetic */ String lambda$static$2() {
        return "com.vidhance.autozoom.minimumscaling";
    }

    static /* synthetic */ String lambda$static$20() {
        return "xiaomi.swmf.enabled";
    }

    static /* synthetic */ String lambda$static$21() {
        return "xiaomi.bokeh.enabled";
    }

    static /* synthetic */ String lambda$static$22() {
        return "xiaomi.bokehrear.enabled";
    }

    static /* synthetic */ String lambda$static$23() {
        return "xiaomi.bokeh.fNumberApplied";
    }

    static /* synthetic */ String lambda$static$24() {
        return "xiaomi.smoothTransition.enabled";
    }

    static /* synthetic */ String lambda$static$25() {
        return "xiaomi.smoothTransition.fallback";
    }

    static /* synthetic */ String lambda$static$26() {
        return "xiaomi.smoothTransition.fastZoomIn";
    }

    static /* synthetic */ String lambda$static$27() {
        return "xiaomi.ai.add.enabled";
    }

    static /* synthetic */ String lambda$static$28() {
        return "xiaomi.portrait.lighting";
    }

    static /* synthetic */ String lambda$static$29() {
        return "xiaomi.ai.segment.enabled";
    }

    static /* synthetic */ String lambda$static$3() {
        return "com.vidhance.autozoom.stop";
    }

    static /* synthetic */ String lambda$static$30() {
        return "xiaomi.faceGenderAndAge.enabled";
    }

    static /* synthetic */ String lambda$static$31() {
        return "xiaomi.faceScore.enabled";
    }

    static /* synthetic */ String lambda$static$32() {
        return "xiaomi.device.orientation";
    }

    static /* synthetic */ String lambda$static$33() {
        return "xiaomi.beauty.beautyLevelApplied";
    }

    static /* synthetic */ String lambda$static$34() {
        return "xiaomi.beauty.skinColorRatio";
    }

    static /* synthetic */ String lambda$static$35() {
        return "xiaomi.beauty.slimFaceRatio";
    }

    static /* synthetic */ String lambda$static$36() {
        return "xiaomi.beauty.skinSmoothRatio";
    }

    static /* synthetic */ String lambda$static$37() {
        return "xiaomi.beauty.enlargeEyeRatio";
    }

    static /* synthetic */ String lambda$static$38() {
        return "xiaomi.beauty.noseRatio";
    }

    static /* synthetic */ String lambda$static$39() {
        return "xiaomi.beauty.risoriusRatio";
    }

    static /* synthetic */ String lambda$static$4() {
        return "com.vidhance.autozoom.start_region";
    }

    static /* synthetic */ String lambda$static$40() {
        return "xiaomi.beauty.lipsRatio";
    }

    static /* synthetic */ String lambda$static$41() {
        return "xiaomi.beauty.chinRatio";
    }

    static /* synthetic */ String lambda$static$42() {
        return "xiaomi.beauty.neckRatio";
    }

    static /* synthetic */ String lambda$static$43() {
        return "xiaomi.beauty.smileRatio";
    }

    static /* synthetic */ String lambda$static$44() {
        return "xiaomi.beauty.slimNoseRatio";
    }

    static /* synthetic */ String lambda$static$45() {
        return "xiaomi.beauty.hairlineRatio";
    }

    static /* synthetic */ String lambda$static$46() {
        return "xiaomi.watermark.availableType";
    }

    static /* synthetic */ String lambda$static$47() {
        return "xiaomi.watermark.typeApplied";
    }

    static /* synthetic */ String lambda$static$48() {
        return "xiaomi.watermark.time";
    }

    static /* synthetic */ String lambda$static$49() {
        return "xiaomi.watermark.face";
    }

    static /* synthetic */ String lambda$static$5() {
        return "com.vidhance.autozoom.select";
    }

    static /* synthetic */ String lambda$static$50() {
        return "xiaomi.snapshotTorch.enabled";
    }

    static /* synthetic */ String lambda$static$51() {
        return "xiaomi.flip.enabled";
    }

    static /* synthetic */ String lambda$static$52() {
        return "xiaomi.burst.captureHint";
    }

    static /* synthetic */ String lambda$static$53() {
        return "xiaomi.burst.shootFPS";
    }

    static /* synthetic */ String lambda$static$54() {
        return "xiaomi.beauty.eyeBrowDyeRatio";
    }

    static /* synthetic */ String lambda$static$55() {
        return "xiaomi.beauty.pupilLineRatio";
    }

    static /* synthetic */ String lambda$static$56() {
        return "xiaomi.beauty.lipGlossRatio";
    }

    static /* synthetic */ String lambda$static$57() {
        return "xiaomi.beauty.blushRatio";
    }

    static /* synthetic */ String lambda$static$58() {
        return "xiaomi.beauty.eyeLightType";
    }

    static /* synthetic */ String lambda$static$59() {
        return "xiaomi.beauty.eyeLightStrength";
    }

    static /* synthetic */ String lambda$static$6() {
        return "com.vidhance.autozoom.unselect";
    }

    static /* synthetic */ String lambda$static$60() {
        return "xiaomi.supernight.enabled";
    }

    static /* synthetic */ String lambda$static$61() {
        return "xiaomi.mimovie.enabled";
    }

    static /* synthetic */ String lambda$static$62() {
        return "xiaomi.beauty.headSlimRatio";
    }

    static /* synthetic */ String lambda$static$63() {
        return "xiaomi.beauty.bodySlimRatio";
    }

    static /* synthetic */ String lambda$static$64() {
        return "xiaomi.beauty.shoulderSlimRatio";
    }

    static /* synthetic */ String lambda$static$65() {
        return "xiaomi.beauty.legSlimRatio";
    }

    static /* synthetic */ String lambda$static$66() {
        return "xiaomi.beauty.oneKeySlimRatio";
    }

    static /* synthetic */ String lambda$static$67() {
        return "xiaomi.beauty.buttPlumpSlimRatio";
    }

    static /* synthetic */ String lambda$static$68() {
        return "xiaomi.distortion.distortionLevelApplied";
    }

    static /* synthetic */ String lambda$static$69() {
        return "xiaomi.distortion.ultraWideDistortionLevel";
    }

    static /* synthetic */ String lambda$static$7() {
        return "com.vidhance.autozoom.force_lock";
    }

    static /* synthetic */ String lambda$static$70() {
        return "xiaomi.snapshot.front.ScreenLighting.enabled";
    }

    static /* synthetic */ String lambda$static$71() {
        return "xiaomi.softlightMode.enabled";
    }

    static /* synthetic */ String lambda$static$72() {
        return "xiaomi.snapshot.backwardfetchframe.enabled";
    }

    static /* synthetic */ String lambda$static$73() {
        return "org.codeaurora.qcamera3.iso_exp_priority.select_priority";
    }

    static /* synthetic */ String lambda$static$74() {
        return "org.codeaurora.qcamera3.iso_exp_priority.use_iso_exp_priority";
    }

    static /* synthetic */ String lambda$static$75() {
        return "org.codeaurora.qcamera3.iso_exp_priority.use_iso_value";
    }

    static /* synthetic */ String lambda$static$76() {
        return b.isMTKPlatform() ? "xiaomi.camera.awb.cct" : "com.qti.stats.awbwrapper.AWBCCT";
    }

    static /* synthetic */ String lambda$static$77() {
        return "org.codeaurora.qcamera3.saturation.use_saturation";
    }

    static /* synthetic */ String lambda$static$78() {
        return "org.codeaurora.qcamera3.sharpness.strength";
    }

    static /* synthetic */ String lambda$static$79() {
        return "org.codeaurora.qcamera3.exposure_metering.exposure_metering_mode";
    }

    static /* synthetic */ String lambda$static$8() {
        return "com.vidhance.autozoom.center_offset";
    }

    static /* synthetic */ String lambda$static$80() {
        return "org.quic.camera.recording.endOfStream";
    }

    static /* synthetic */ String lambda$static$81() {
        return "xiaomi.ai.asd.enabled";
    }

    static /* synthetic */ String lambda$static$82() {
        return "xiaomi.ai.asd.sceneApplied";
    }

    static /* synthetic */ String lambda$static$83() {
        return "xiaomi.ai.asd.period";
    }

    static /* synthetic */ String lambda$static$84() {
        return "org.codeaurora.qcamera3.contrast.level";
    }

    static /* synthetic */ String lambda$static$85() {
        return "xiaomi.hfrPreview.isHFRPreview";
    }

    static /* synthetic */ String lambda$static$86() {
        return "org.codeaurora.qcamera3.ae_bracket.mode";
    }

    static /* synthetic */ String lambda$static$87() {
        return "xiaomi.multiframe.inputNum";
    }

    static /* synthetic */ String lambda$static$88() {
        return "xiaomi.depurple.enabled";
    }

    static /* synthetic */ String lambda$static$89() {
        return "xiaomi.MacroMode.enabled";
    }

    static /* synthetic */ String lambda$static$9() {
        return "com.vidhance.autozoom.scale_offset";
    }

    static /* synthetic */ String lambda$static$90() {
        return "xiaomi.watermark.custom";
    }

    static /* synthetic */ String lambda$static$91() {
        return "xiaomi.satIsZooming.satIsZooming";
    }

    static /* synthetic */ String lambda$static$92() {
        return "com.mediatek.streamingfeature.hfpsMode";
    }

    static /* synthetic */ String lambda$static$93() {
        return "com.mediatek.smvrfeature.smvrMode";
    }

    static /* synthetic */ String lambda$static$94() {
        return "com.mediatek.control.capture.zsl.mode";
    }

    static /* synthetic */ String lambda$static$95() {
        return "com.mediatek.control.capture.flipmode";
    }

    static /* synthetic */ String lambda$static$96() {
        return "com.mediatek.control.capture.remosaicenable";
    }

    static /* synthetic */ String lambda$static$97() {
        return "xiaomi.remosaic.enabled";
    }

    static /* synthetic */ String lambda$static$98() {
        return "xiaomi.distortion.distortioFpcData";
    }

    static /* synthetic */ String lambda$static$99() {
        return "com.mediatek.control.capture.hintForRawReprocess";
    }

    public static void preload() {
        Log.d(TAG, "preloading...");
    }

    static <T> CaptureRequest.Key<T> requestKey(String str, Class<T> cls) {
        try {
            if (requestConstructor == null) {
                requestConstructor = CaptureRequest.Key.class.getConstructor(new Class[]{String.class, cls.getClass()});
                requestConstructor.setAccessible(true);
            }
            return requestConstructor.newInstance(new Object[]{str, cls});
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            Log.d(TAG, "Cannot find/call Key constructor: " + e.getMessage());
            return null;
        }
    }
}
