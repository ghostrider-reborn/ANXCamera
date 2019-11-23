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
    public static final boolean IS_HONGMI = d.getBoolean(d.wr, false);
    public static final boolean IS_MI2A = Build.IS_MI2A;
    public static final boolean IS_XIAOMI = d.getBoolean(d.wq, false);
    public static final boolean rA = (rz || ry);
    public static final boolean rB = (Build.IS_HONGMI_TWO && !Build.IS_HONGMI_TWO_A && !Build.IS_HONGMI_TWO_S);
    public static final boolean rC = Build.IS_HONGMI_TWO_S;
    public static final boolean rD = (rB || rC);
    public static final boolean rE = Build.IS_HONGMI_TWOS_LTE_MTK;
    public static final boolean rF = Build.IS_HONGMI_TWO_A;
    public static final boolean rG = Build.IS_HONGMI_THREE;
    public static final boolean rH = "gucci".equals(rp);
    public static final boolean rI = "hermes".equals(rp);
    public static final boolean rJ = "hennessy".equals(rp);
    public static final boolean rK = "dior".equals(rp);
    public static final boolean rL = "kenzo".equals(rp);
    public static final boolean rM = "kate".equals(rp);
    public static final boolean rN = (Build.IS_HONGMI_TWOX || "HM2014816".equals(rp));
    public static final boolean rO = Build.IS_HONGMI_TWOX_LC;
    public static final boolean rP = Build.IS_MIFOUR;
    public static final boolean rQ = Build.IS_MIPAD;
    public static final boolean rR = Build.IS_MIFIVE;
    public static final boolean rS = "leo".equals(rp);
    public static final boolean rT = "ferrari".equals(rp);
    public static final boolean rU = "ido".equals(rp);
    public static final boolean rV = "aqua".equals(rp);
    public static final boolean rW = "gemini".equals(rp);
    public static final boolean rX = "gold".equals(rp);
    public static final boolean rY = "capricorn".equals(rp);
    public static final boolean rZ = "natrium".equals(rp);
    public static final String rp = Build.DEVICE;
    public static final String rq = "qcom";
    public static final String rr = "mediatek";
    public static final String rs = "leadcore";
    public static final String rt = "nvidia";
    public static final String ru = "intel";
    private static final int rv = 100;
    public static final String rw = Build.MODEL;
    public static final boolean rx = Build.IS_MITWO;
    public static final boolean ry = "pisces".equals(rp);
    public static final boolean rz = ("cancro".equals(rp) && Build.MODEL.startsWith("MI 3"));
    public static final boolean sA = "violet".equals(rp);
    public static final boolean sB = "pisces".equals(rp);
    public static final boolean sC = "hammerhead".equals(rp);
    public static final boolean sD = "santoni".equals(rp);
    public static final boolean sE = "polaris".equals(rp);
    public static final boolean sF = "sirius".equals(rp);
    public static final boolean sG = "dipper".equals(rp);
    public static final boolean sH = "ursa".equals(rp);
    public static final boolean sI = "equuleus".equals(rp);
    public static final boolean sJ = "andromeda".equals(rp);
    public static final boolean sK = "perseus".equals(rp);
    public static final boolean sL = "cepheus".equals(rp);
    public static final boolean sM = "grus".equals(rp);
    public static final boolean sN = "begonia".equals(rp);
    public static final boolean sO = "begoniain".equals(rp);
    public static final boolean sP = "pine".equals(rp);
    public static final boolean sQ = "pyxis".equals(rp);
    public static final boolean sR = "vela".equals(rp);
    public static final boolean sS = "laurus".equals(rp);
    public static final boolean sT = "laurel_sprout".equals(rp);
    public static boolean sU = false;
    public static final boolean sV = Build.IS_STABLE_VERSION;
    public static final boolean sW = Build.IS_CM_CUSTOMIZATION_TEST;
    private static final int sZ = 1;
    public static final boolean sb = "lithium".equals(rp);
    public static final boolean sc = "scorpio".equals(rp);
    public static final boolean se = "libra".equals(rp);
    public static final boolean sf = "land".equals(rp);
    public static final boolean sg = "hydrogen".equals(rp);
    public static final boolean sh = "helium".equals(rp);
    public static final boolean si = "omega".equals(rp);
    public static final boolean sj = rp.startsWith("nike");
    public static final boolean sk = rp.startsWith("mark");
    public static final boolean sl = rp.startsWith("prada");
    public static final boolean sm = rp.startsWith("mido");
    public static final boolean sn = "rolex".equals(rp);
    public static final boolean so = "sagit".equals(rp);
    public static final boolean sp = "centaur".equals(rp);
    public static final boolean sq = "achilles".equals(rp);
    public static final boolean sr = "jason".equals(rp);
    public static final boolean ss = "tiffany".equals(rp);
    public static final boolean st = "ulysse".equals(rp);
    public static final boolean su = "oxygen".equals(rp);
    public static final boolean sv = "chiron".equals(rp);
    public static final boolean sw = "ugg".equals(rp);
    public static final boolean sx = "vince".equals(rp);
    public static final boolean sy = "whyred".equals(rp);
    public static final boolean sz = "beryllium".equals(rp);
    private static final int ta = 4;
    private static final int tb = 8;
    private static ArrayList<String> tc;
    private static final String[] td = {"KR", "JP"};
    private static long te = 4294967296L;
    private static Boolean tf;
    private static final AtomicReference<Optional<Boolean>> tg = new AtomicReference<>(Optional.empty());

    static {
        boolean z = true;
        if (!"tucana".equals(rp) || !SystemProperties.get("persist.camera.rearMain.vendorID", "03").equals("03")) {
            z = false;
        }
        sU = z;
    }

    private static boolean Q(String str) {
        for (String equals : td) {
            if (TextUtils.equals(str, equals)) {
                return true;
            }
        }
        return false;
    }

    public static boolean gQ() {
        return DataRepository.dataItemFeature().gQ();
    }

    public static boolean gX() {
        return DataRepository.dataItemFeature().gX();
    }

    public static int getBurstShootCount() {
        return d.getInteger(d.wD, 100);
    }

    public static String getGivenName() {
        return kw() ? "_l" : kE() ? "_in" : !DataRepository.dataItemFeature().getBoolean(c.ut, false) ? "" : (android.os.Build.MODEL.contains("BROWN EDITION") || android.os.Build.MODEL.contains("Explorer")) ? "_a" : android.os.Build.MODEL.contains("ROY") ? "_b" : kB() ? "_s" : (kz() || kC()) ? "_global" : kA() ? "_premium" : kx() ? ky() ? "_global_pro" : "_global" : "";
    }

    public static boolean iE() {
        if (kw()) {
            return false;
        }
        return d.getBoolean(d.wE, false);
    }

    public static boolean iF() {
        return !d.getBoolean(d.xu, false);
    }

    public static boolean iG() {
        return rO || d.getBoolean(d.xD, false);
    }

    public static boolean iH() {
        return d.getBoolean(d.wA, false);
    }

    public static boolean iI() {
        return !iJ();
    }

    public static boolean iJ() {
        if (!Build.IS_INTERNATIONAL_BUILD) {
            return false;
        }
        String str = Util.sRegion;
        return TextUtils.isEmpty(str) ? Q(Locale.getDefault().getCountry()) : Q(str);
    }

    public static boolean iK() {
        return d.getBoolean(d.wB, false);
    }

    public static boolean iL() {
        return d.getBoolean(d.wF, false);
    }

    public static boolean iM() {
        return !Build.IS_INTERNATIONAL_BUILD && d.getBoolean(d.wG, false);
    }

    public static boolean iN() {
        return d.getBoolean(d.wH, false);
    }

    public static boolean iO() {
        return ((float) Util.sWindowHeight) / ((float) Util.sWindowWidth) >= 2.0f && d.getBoolean(d.xE, false);
    }

    public static boolean iP() {
        return d.getBoolean(d.wI, false);
    }

    public static boolean iQ() {
        return d.getBoolean(d.wJ, false);
    }

    public static boolean iR() {
        return d.getBoolean(d.xs, false);
    }

    public static boolean iS() {
        return d.getBoolean(d.wK, false);
    }

    public static boolean iT() {
        return !sW && d.getBoolean(d.wL, false);
    }

    public static boolean iU() {
        return d.getBoolean(d.wM, false);
    }

    public static boolean iV() {
        return rq.equals(d.getString(d.wu));
    }

    public static boolean iW() {
        return rt.equals(d.getString(d.wu));
    }

    public static boolean iX() {
        return rs.equals(d.getString(d.wu));
    }

    public static boolean iY() {
        return d.getBoolean(d.wv, false);
    }

    public static boolean iZ() {
        return false;
    }

    public static boolean isLowRamDevice() {
        if (tf == null) {
            tf = Util.getTotalMemory(CameraAppImpl.getAndroidContext()) < te ? Boolean.TRUE : Boolean.FALSE;
        }
        return tf.booleanValue();
    }

    public static boolean isMTKPlatform() {
        if (!tg.get().isPresent()) {
            synchronized (tg) {
                if (!tg.get().isPresent()) {
                    tg.set(Optional.of(Boolean.valueOf(rr.equals(d.getString(d.wu)))));
                }
            }
        }
        return ((Boolean) tg.get().get()).booleanValue();
    }

    public static boolean isPad() {
        return d.getBoolean(d.wt, false);
    }

    public static boolean isSupportSuperResolution() {
        return d.getBoolean(d.yi, false);
    }

    public static boolean isSupportedOpticalZoom() {
        return d.getBoolean(d.xP, false);
    }

    public static boolean jA() {
        return d.getBoolean(d.xf, false);
    }

    public static int jB() {
        return d.getInteger(d.xW, AutoLockManager.HIBERNATION_TIMEOUT);
    }

    public static boolean jC() {
        return d.getBoolean(d.xg, false);
    }

    public static boolean jD() {
        return d.getBoolean(d.xh, false);
    }

    public static boolean jE() {
        return false;
    }

    public static boolean jF() {
        return d.getBoolean(d.xl, false);
    }

    public static boolean jG() {
        return d.getBoolean(d.ww, false);
    }

    public static boolean jH() {
        return d.getBoolean(d.xm, false);
    }

    public static boolean jI() {
        return !Build.IS_INTERNATIONAL_BUILD && d.getBoolean(d.xo, false);
    }

    public static String jJ() {
        return d.getString(d.wx);
    }

    public static String jK() {
        return d.getString(d.wy);
    }

    public static boolean jL() {
        return d.getBoolean(d.xL, false);
    }

    public static boolean jM() {
        return !Build.IS_INTERNATIONAL_BUILD && d.getBoolean(d.xn, false);
    }

    public static boolean jN() {
        return (rL && Build.IS_INTERNATIONAL_BUILD) || sf;
    }

    public static boolean jO() {
        return true;
    }

    public static boolean jP() {
        return d.getBoolean(d.xt, false);
    }

    public static boolean jQ() {
        return !rz && !rP && !Build.IS_HONGMI_TWOX && !rF && d.getBoolean(d.xM, true);
    }

    public static boolean jR() {
        return !d.getBoolean(d.xy, false);
    }

    public static boolean jS() {
        return !d.getBoolean(d.xN, false) && !isMTKPlatform();
    }

    public static boolean jT() {
        return d.getBoolean(d.xz, true);
    }

    public static boolean jU() {
        return d.getBoolean(d.xO, false);
    }

    public static ArrayList<String> jV() {
        if (tc == null) {
            tc = new ArrayList<>();
            String[] stringArray = d.getStringArray(d.wz);
            if (stringArray != null) {
                Collections.addAll(tc, stringArray);
            }
        }
        return tc;
    }

    public static boolean jW() {
        return d.getBoolean(d.xr, false);
    }

    public static boolean jX() {
        return d.getBoolean(d.wC, false);
    }

    public static boolean jY() {
        return false;
    }

    public static boolean jZ() {
        return d.getBoolean(d.xx, true);
    }

    public static boolean ja() {
        return d.getBoolean(d.wN, false);
    }

    public static boolean jb() {
        return d.getBoolean(d.wP, false);
    }

    public static boolean jc() {
        return d.getBoolean(d.wQ, false);
    }

    public static boolean jd() {
        return d.getBoolean(d.wR, false);
    }

    public static boolean je() {
        return d.getBoolean(d.wS, false);
    }

    public static boolean jf() {
        return (d.getInteger(d.th, 0) & 1) != 0;
    }

    public static boolean jg() {
        return (d.getInteger(d.th, 0) & 13) != 0;
    }

    public static boolean jh() {
        return !DataRepository.dataItemFeature().fM() && IS_HONGMI;
    }

    public static boolean ji() {
        return (d.getInteger(d.th, 0) & 4) != 0;
    }

    public static boolean jj() {
        return d.getBoolean(d.xG, false) && jk();
    }

    public static boolean jk() {
        return false;
    }

    public static boolean jl() {
        return d.getBoolean(d.wY, false);
    }

    public static boolean jm() {
        return d.getBoolean(d.wV, false);
    }

    public static boolean jn() {
        return !rF && !rO && !Build.IS_HONGMI_TWOX && !rz && !rG && !rB && !rC && !rE && !rx && !IS_MI2A && !rA && d.getBoolean(d.xH, true);
    }

    public static boolean jo() {
        return false;
    }

    public static boolean jp() {
        return d.getBoolean(d.xa, false);
    }

    public static boolean jq() {
        return iV() && 21 <= Build.VERSION.SDK_INT;
    }

    public static boolean jr() {
        return d.getBoolean(d.xb, false);
    }

    public static boolean js() {
        return d.getBoolean(d.xc, false);
    }

    public static boolean jt() {
        return !IS_XIAOMI && !IS_HONGMI;
    }

    public static boolean ju() {
        return d.getBoolean(d.xI, false);
    }

    public static boolean jv() {
        return d.getBoolean(d.xd, false);
    }

    public static boolean jw() {
        return d.getBoolean(d.xe, false);
    }

    public static boolean jx() {
        return !rF && !rO && !miui.os.Build.IS_HONGMI_TWOX && !rz && !rG && !rB && !rC && !rE && !rx && !IS_MI2A && !rA && !rP && d.getBoolean(d.xJ, true);
    }

    public static boolean jy() {
        return d.getBoolean(d.xK, false);
    }

    public static boolean jz() {
        return rx && !IS_MI2A;
    }

    public static boolean kA() {
        return rp.equalsIgnoreCase("raphael") && miui.os.Build.MODEL.endsWith("Premium Edition");
    }

    public static boolean kB() {
        return rp.equalsIgnoreCase("lavender") && "India_48_5".equalsIgnoreCase(SystemProperties.get("ro.boot.hwc"));
    }

    public static boolean kC() {
        return rp.equalsIgnoreCase("davinci") && miui.os.Build.IS_INTERNATIONAL_BUILD;
    }

    public static boolean kD() {
        return sK && miui.os.Build.IS_INTERNATIONAL_BUILD;
    }

    public static boolean kE() {
        return sz && "India".equalsIgnoreCase(SystemProperties.get("ro.boot.hwc"));
    }

    public static boolean kF() {
        return kD() || sQ || sM;
    }

    public static boolean kG() {
        return d.getBoolean(d.yl, false);
    }

    public static boolean ka() {
        return d.getBoolean(d.xS, false);
    }

    public static boolean kb() {
        return sy ? "India".equals(SystemProperties.get("ro.boot.hwc")) : d.getBoolean(d.xA, false);
    }

    public static boolean kc() {
        return sb || sv || sE;
    }

    public static boolean kd() {
        return d.getBoolean(d.ya, true);
    }

    public static boolean ke() {
        return d.getBoolean(d.xT, true);
    }

    public static boolean kf() {
        return d.getBoolean(d.xv, false);
    }

    public static boolean kg() {
        return kf() && d.getBoolean(d.xw, true);
    }

    private static boolean kh() {
        return SystemProperties.getBoolean("ro.hardware.fp.fod", false);
    }

    private static boolean ki() {
        return d.getBoolean(d.xB, false) || kh();
    }

    public static boolean kj() {
        return !ki() && DataRepository.dataItemFeature().hx() && jV() != null && !jV().isEmpty();
    }

    public static boolean kk() {
        return d.getBoolean(d.xY, false);
    }

    public static boolean kl() {
        return d.getBoolean(d.yh, false);
    }

    public static boolean km() {
        return d.getBoolean(d.yb, false);
    }

    public static boolean kn() {
        return d.getBoolean(d.yc, false);
    }

    public static boolean ko() {
        return d.getBoolean(d.yd, false);
    }

    public static boolean kp() {
        return d.getBoolean(d.ye, false);
    }

    public static boolean kq() {
        return ko() || kp();
    }

    public static boolean kr() {
        return d.getBoolean(d.xV, false);
    }

    public static boolean ks() {
        return d.getBoolean(d.yg, false);
    }

    public static boolean kt() {
        return d.getBoolean(d.yj, true);
    }

    public static boolean ku() {
        return d.getBoolean(d.yk, false);
    }

    public static boolean kv() {
        return d.getBoolean(d.ym, false);
    }

    public static boolean kw() {
        if ("onc".equals(rp)) {
            String str = SystemProperties.get("ro.boot.hwversion");
            return !TextUtils.isEmpty(str) && '2' == str.charAt(0);
        }
        return false;
    }

    public static boolean kx() {
        return rp.equalsIgnoreCase("tucana") && miui.os.Build.IS_INTERNATIONAL_BUILD;
    }

    public static boolean ky() {
        return rp.equalsIgnoreCase("tucana") && !sU;
    }

    public static boolean kz() {
        return rp.equalsIgnoreCase("raphael") && miui.os.Build.IS_INTERNATIONAL_BUILD;
    }

    public static boolean s(boolean z) {
        String str = SystemProperties.get("ro.miui.customized.region");
        if (sU) {
            return true;
        }
        if ("fr_sfr".equals(str)) {
            return false;
        }
        return z;
    }
}
