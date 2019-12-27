package com.mi.config;

import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.android.camera.AutoLockManager;
import com.android.camera.CameraAppImpl;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import miui.os.Build;

/* compiled from: Device */
public class b {
    public static final boolean Am = Build.IS_HONGMI_TWOS_LTE_MTK;
    public static final boolean An = "polaris".equals(km);
    public static final boolean Bm = Build.IS_HONGMI_TWO_A;
    public static final boolean Bn = "sirius".equals(km);
    public static final boolean Cm = Build.IS_HONGMI_THREE;
    public static final boolean Cn = "dipper".equals(km);
    public static final boolean Dm = "gucci".equals(km);
    public static final boolean Dn = "ursa".equals(km);
    public static final boolean Em = "hermes".equals(km);
    public static final boolean En = "equuleus".equals(km);
    public static final boolean Fm = "hennessy".equals(km);
    public static final boolean Fn = "andromeda".equals(km);
    public static final boolean Gm = "dior".equals(km);
    public static final boolean Gn = "perseus".equals(km);
    public static final boolean Hm = "kenzo".equals(km);
    public static final boolean Hn = "cepheus".equals(km);
    public static final boolean IS_HONGMI = d.getBoolean(d.IS_HONGMI, false);
    public static final boolean IS_MI2A = Build.IS_MI2A;
    public static final boolean IS_XIAOMI = d.getBoolean(d.IS_XIAOMI, false);
    public static final boolean Im = "kate".equals(km);
    public static final boolean In = "grus".equals(km);
    public static final boolean Jm;
    public static final boolean Jn = "pine".equals(km);
    public static final boolean Km = Build.IS_HONGMI_TWOX_LC;
    public static final boolean Kn = "pyxis".equals(km);
    public static final boolean Lm = Build.IS_MIFOUR;
    public static final boolean Ln = "vela".equals(km);
    public static final boolean Mm = Build.IS_MIPAD;
    public static final boolean Mn = "laurus".equals(km);
    public static final boolean Nm = Build.IS_MIFIVE;
    public static final boolean Nn = "laurel_sprout".equals(km);
    public static final boolean Om = "leo".equals(km);
    public static final boolean Pm = "ferrari".equals(km);
    public static final boolean Pn = Build.IS_STABLE_VERSION;
    public static final boolean Qm = "ido".equals(km);
    public static final boolean Qn = Build.IS_CM_CUSTOMIZATION_TEST;
    public static final boolean Rm = "aqua".equals(km);
    private static final int Rn = 1;
    public static final boolean Sm = "gemini".equals(km);
    private static final int Sn = 4;
    public static final boolean Tm = "gold".equals(km);
    private static final int Tn = 8;
    public static final boolean Um = "capricorn".equals(km);
    private static ArrayList<String> Un = null;
    public static final boolean Vm = "natrium".equals(km);
    private static final String[] Vn = {"KR", "JP"};
    public static final boolean Wm = "lithium".equals(km);
    private static long Wn = 4294967296L;
    public static final boolean Xm = "scorpio".equals(km);
    private static Boolean Xn = null;
    public static final boolean Ym = "libra".equals(km);
    private static final AtomicReference<Optional<Boolean>> Yn = new AtomicReference<>(Optional.empty());
    public static final boolean Zm = "land".equals(km);
    public static final boolean _m = "hydrogen".equals(km);
    public static final boolean an = "helium".equals(km);
    public static final boolean bn = "omega".equals(km);
    public static final boolean cn = km.startsWith("nike");
    public static final boolean en = km.startsWith("mark");
    public static final boolean fn = km.startsWith("prada");
    public static final boolean gn = km.startsWith("mido");
    public static final boolean hn = "rolex".equals(km);
    public static final boolean jn = "sagit".equals(km);
    public static final String km = Build.DEVICE;
    public static final boolean kn = "centaur".equals(km);
    public static final String lm = "qcom";
    public static final boolean ln = "achilles".equals(km);
    public static final String mm = "mediatek";
    public static final boolean mn = "jason".equals(km);
    public static final String nm = "leadcore";
    public static final boolean nn = "tiffany".equals(km);
    public static final String om = "nvidia";
    public static final boolean pn = "ulysse".equals(km);
    public static final String qm = "intel";
    public static final boolean qn = "oxygen".equals(km);
    private static final int rm = 100;
    public static final boolean rn = "chiron".equals(km);
    public static final String sm = Build.MODEL;
    public static final boolean sn = "ugg".equals(km);
    public static final boolean tm = Build.IS_MITWO;
    public static final boolean tn = "vince".equals(km);
    public static final boolean um = "pisces".equals(km);
    public static final boolean un = "whyred".equals(km);
    public static final boolean vm = ("cancro".equals(km) && Build.MODEL.startsWith("MI 3"));
    public static final boolean vn = "beryllium".equals(km);
    public static final boolean wm = (vm || um);
    public static final boolean wn = "violet".equals(km);
    public static final boolean xm = (Build.IS_HONGMI_TWO && !Build.IS_HONGMI_TWO_A && !Build.IS_HONGMI_TWO_S);
    public static final boolean xn = "pisces".equals(km);
    public static final boolean ym = Build.IS_HONGMI_TWO_S;
    public static final boolean yn = "hammerhead".equals(km);
    public static final boolean zm = (xm || ym);
    public static final boolean zn = "santoni".equals(km);

    static {
        boolean z = true;
        if (!Build.IS_HONGMI_TWOX && !"HM2014816".equals(km)) {
            z = false;
        }
        Jm = z;
    }

    public static boolean Ai() {
        return lm.equals(d.getString(d.VENDOR));
    }

    public static boolean Aj() {
        return d.getBoolean(d.Ao, false);
    }

    public static boolean Bi() {
        return d.getBoolean(d.Gp, true);
    }

    public static boolean Bj() {
        return d.getBoolean(d.qo, false);
    }

    public static boolean Ci() {
        return true;
    }

    public static boolean Cj() {
        return d.getBoolean(d.zo, false);
    }

    public static boolean Di() {
        return false;
    }

    public static boolean Dj() {
        return d.getBoolean(d.xp, false);
    }

    public static boolean Ei() {
        return d.getBoolean(d.Qo, false);
    }

    public static boolean Ej() {
        return !IS_XIAOMI && !IS_HONGMI;
    }

    public static boolean Fi() {
        return d.getBoolean(d.Pp, false);
    }

    public static boolean Fj() {
        return !Bm && !Km && !Build.IS_HONGMI_TWOX && !vm && !Cm && !xm && !ym && !Am && !tm && !IS_MI2A && !wm && !Lm && d.getBoolean(d.wp, true);
    }

    private static boolean G(String str) {
        for (String equals : Vn) {
            if (TextUtils.equals(str, equals)) {
                return true;
            }
        }
        return false;
    }

    public static boolean Gi() {
        return d.getBoolean(d.ho, false);
    }

    public static boolean Gj() {
        return tm && !IS_MI2A;
    }

    public static boolean Hi() {
        return d.getBoolean(d.Tp, false);
    }

    public static boolean Hj() {
        return d.getBoolean(d.Xp, false);
    }

    public static boolean Ii() {
        return d.getBoolean(d.Fp, false);
    }

    public static boolean Ij() {
        return d.getBoolean(d.Wp, true);
    }

    public static boolean Ji() {
        return d.getBoolean(d.Np, false);
    }

    public static boolean Jj() {
        return d.getBoolean(d.Ip, false);
    }

    public static boolean Ki() {
        return d.getBoolean(d.dp, false);
    }

    public static boolean Li() {
        return d.getBoolean(d._o, false);
    }

    public static boolean Mi() {
        return d.getBoolean(d.Oo, false);
    }

    public static boolean Nh() {
        return !Qn && d.getBoolean(d.so, false);
    }

    public static boolean Ni() {
        return false;
    }

    public static boolean Ob() {
        return DataRepository.dataItemFeature().Ob();
    }

    public static boolean Oh() {
        return d.getBoolean(d.Sp, false);
    }

    public static boolean Oi() {
        return d.getBoolean(d.Qp, false);
    }

    public static String Ph() {
        return d.getString(d.bo);
    }

    public static boolean Pi() {
        return Fi() || Oi();
    }

    public static String Qh() {
        return d.getString(d.f2do);
    }

    public static boolean Qi() {
        return d.getBoolean(d.Mp, true);
    }

    public static ArrayList<String> Rh() {
        if (Un == null) {
            Un = new ArrayList<>();
            String[] stringArray = d.getStringArray(d.eo);
            if (stringArray != null) {
                Collections.addAll(Un, stringArray);
            }
        }
        return Un;
    }

    public static boolean Ri() {
        return !DataRepository.dataItemFeature().Ab() && IS_HONGMI;
    }

    public static String Sh() {
        return _h() ? "_l" : qi() ? "_in" : !DataRepository.dataItemFeature().getBoolean(c.Ls, false) ? "" : (android.os.Build.MODEL.contains("BROWN EDITION") || android.os.Build.MODEL.contains("Explorer")) ? "_a" : android.os.Build.MODEL.contains("ROY") ? "_b" : ai() ? "_s" : (ji() || ii()) ? "_global" : "";
    }

    public static boolean Si() {
        return d.getBoolean(d.Kp, false);
    }

    public static boolean Tc() {
        return DataRepository.dataItemFeature().Tc();
    }

    public static int Th() {
        return d.getInteger(d.HIBERNATION_TIMEOUT, AutoLockManager.HIBERNATION_TIMEOUT);
    }

    public static boolean Ti() {
        return d.getBoolean(d.bp, false);
    }

    public static boolean Uh() {
        return d.getBoolean(d.Yp, false);
    }

    public static boolean Ui() {
        return Ki() && d.getBoolean(d.ep, true);
    }

    public static boolean Vh() {
        return ((float) Util.sWindowHeight) / ((float) Util.sWindowWidth) >= 2.0f && d.getBoolean(d.qp, false);
    }

    public static boolean Vi() {
        return d.getBoolean(d.Op, false);
    }

    public static boolean Wh() {
        return !gl() && DataRepository.dataItemFeature().Ib() && Rh() != null && !Rh().isEmpty();
    }

    public static boolean Wi() {
        return (d.getInteger(d.Do, 0) & 13) != 0;
    }

    public static boolean Xh() {
        if (0 == 0) {
            return false;
        }
        String str = Util.sRegion;
        return TextUtils.isEmpty(str) ? G(Locale.getDefault().getCountry()) : G(str);
    }

    public static boolean Xi() {
        return 0 == 0 && d.getBoolean(d.lo, false);
    }

    public static boolean Yh() {
        return d.getBoolean(d.vp, false);
    }

    public static boolean Yi() {
        return d.getBoolean(d.vo, false);
    }

    public static boolean Zh() {
        return d.getBoolean(d.Uo, false);
    }

    public static boolean Zi() {
        return (d.getInteger(d.Do, 0) & 1) != 0;
    }

    public static boolean _h() {
        if (!"onc".equals(km)) {
            return false;
        }
        String str = SystemProperties.get("ro.boot.hwversion");
        return !TextUtils.isEmpty(str) && '2' == str.charAt(0);
    }

    public static boolean _i() {
        return (d.getInteger(d.Do, 0) & 4) != 0;
    }

    public static boolean ai() {
        return km.equalsIgnoreCase("lavender") && "India_48_5".equalsIgnoreCase(SystemProperties.get("ro.boot.hwc"));
    }

    public static boolean aj() {
        return false;
    }

    public static boolean bi() {
        return d.getBoolean(d.Lo, false);
    }

    public static boolean bj() {
        return d.getBoolean(d.Eo, false);
    }

    public static boolean ci() {
        return Ai() && 21 <= Build.VERSION.SDK_INT;
    }

    public static boolean cj() {
        return d.getBoolean(d.xo, false);
    }

    public static boolean di() {
        return Wm || rn || An;
    }

    public static boolean dj() {
        return !d.getBoolean(d.cp, false);
    }

    public static boolean ei() {
        return hi() || Kn || In;
    }

    public static boolean ej() {
        return d.getBoolean(d.ao, false);
    }

    public static boolean fi() {
        return un ? "India".equals(SystemProperties.get("ro.boot.hwc")) : d.getBoolean(d.jp, false);
    }

    public static boolean fj() {
        return d.getBoolean(d.ap, false);
    }

    private static boolean fl() {
        return SystemProperties.getBoolean("ro.hardware.fp.fod", false);
    }

    public static int getBurstShootCount() {
        return d.getInteger(d.f245io, 100);
    }

    public static boolean gi() {
        return !Bm && !Km && !miui.os.Build.IS_HONGMI_TWOX && !vm && !Cm && !xm && !ym && !Am && !tm && !IS_MI2A && !wm && d.getBoolean(d.tp, true);
    }

    public static boolean gj() {
        return false;
    }

    private static boolean gl() {
        return d.getBoolean(d.kp, false) || fl();
    }

    public static boolean hi() {
        return Gn && 0 != 0;
    }

    public static boolean hj() {
        return (Hm && 0 != 0) || Zm;
    }

    public static boolean ii() {
        return km.equalsIgnoreCase("davinci") && 0 != 0;
    }

    public static boolean ij() {
        return d.getBoolean(d.mo, false);
    }

    public static boolean isLowRamDevice() {
        if (Xn == null) {
            Xn = Util.getTotalMemory(CameraAppImpl.getAndroidContext()) < Wn ? Boolean.TRUE : Boolean.FALSE;
        }
        return Xn.booleanValue();
    }

    public static boolean isMTKPlatform() {
        if (!Yn.get().isPresent()) {
            synchronized (Yn) {
                if (!Yn.get().isPresent()) {
                    Yn.set(Optional.of(Boolean.valueOf(mm.equals(d.getString(d.VENDOR)))));
                }
            }
        }
        return ((Boolean) Yn.get().get()).booleanValue();
    }

    public static boolean isPad() {
        return d.getBoolean(d.Zn, false);
    }

    public static boolean isSupportSuperResolution() {
        return d.getBoolean(d.Vp, false);
    }

    public static boolean isSupportedOpticalZoom() {
        return d.getBoolean(d.Cp, false);
    }

    public static boolean ji() {
        return km.equalsIgnoreCase("raphael") && 0 != 0;
    }

    public static boolean jj() {
        return d.getBoolean(d.go, false);
    }

    public static boolean ki() {
        return d.getBoolean(d.Ko, false);
    }

    public static boolean kj() {
        return 0 == 0 && d.getBoolean(d.Wo, false);
    }

    public static boolean li() {
        return !d.getBoolean(d.Ap, false) && !isMTKPlatform();
    }

    public static boolean lj() {
        return d.getBoolean(d.Ho, false);
    }

    public static boolean mi() {
        return d.getBoolean(d.hp, true);
    }

    public static boolean mj() {
        if (_h()) {
            return false;
        }
        return d.getBoolean(d.jo, false);
    }

    public static boolean nj() {
        return !Xh();
    }

    public static boolean oi() {
        return d.getBoolean(d.yp, false);
    }

    public static boolean oj() {
        return d.getBoolean(d.po, false);
    }

    public static boolean pi() {
        return d.getBoolean(d.Mo, false);
    }

    public static boolean pj() {
        return d.getBoolean(d.yo, false);
    }

    public static boolean qi() {
        return vn && "India".equalsIgnoreCase(SystemProperties.get("ro.boot.hwc"));
    }

    public static boolean qj() {
        return d.getBoolean(d.No, false);
    }

    public static boolean ri() {
        return d.getBoolean(d.fp, true);
    }

    public static boolean rj() {
        return 0 == 0 && d.getBoolean(d.Xo, false);
    }

    public static boolean si() {
        return nm.equals(d.getString(d.VENDOR));
    }

    public static boolean sj() {
        return d.getBoolean(d._n, false);
    }

    public static boolean ti() {
        return d.getBoolean(d.Po, false);
    }

    public static boolean tj() {
        return d.getBoolean(d.fo, false);
    }

    public static boolean ui() {
        return d.getBoolean(d.uo, false);
    }

    public static boolean uj() {
        return d.getBoolean(d.ko, false);
    }

    public static boolean v(boolean z) {
        if ("fr_sfr".equals(SystemProperties.get("ro.miui.customized.region"))) {
            return false;
        }
        return z;
    }

    public static boolean vi() {
        return false;
    }

    public static boolean vj() {
        return d.getBoolean(d.Bp, false);
    }

    public static boolean wi() {
        return Km || d.getBoolean(d.np, false);
    }

    public static boolean wj() {
        return d.getBoolean(d.sp, false) && aj();
    }

    public static boolean xi() {
        return !vm && !Lm && !miui.os.Build.IS_HONGMI_TWOX && !Bm && d.getBoolean(d.zp, true);
    }

    public static boolean xj() {
        return d.getBoolean(d.Vo, false);
    }

    public static boolean yi() {
        return om.equals(d.getString(d.VENDOR));
    }

    public static boolean yj() {
        return d.getBoolean(d.oo, true);
    }

    public static boolean zi() {
        return !d.getBoolean(d.gp, false);
    }

    public static boolean zj() {
        return d.getBoolean(d.Jo, false);
    }
}
