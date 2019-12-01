package com.mi.config;

import android.content.res.Resources;
import android.os.Build;
import android.os.SystemProperties;
import android.os.statistics.E2EScenario;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.Size;
import com.android.camera.CameraAppImpl;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.data.DataItemBase;
import com.android.camera.log.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Locale;
import miui.os.Build;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: DataItemFeature */
public class a extends DataItemBase implements c {
    private static final String TAG = "DataFeature";
    private static final boolean ro = false;
    private String rn;

    public a() {
        fx();
    }

    private void K(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        Iterator<String> keys = jSONObject.keys();
        SimpleArrayMap<String, Object> values = getValues();
        while (keys.hasNext()) {
            String next = keys.next();
            if (values.put(next, jSONObject.opt(next)) != null) {
                throw new IllegalStateException("Duplicate key is found in the configuration file: " + next);
            }
        }
    }

    private int O(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        char charAt = str.charAt(0);
        if (Character.isDigit(charAt)) {
            return Integer.parseInt(String.valueOf(charAt));
        }
        return -1;
    }

    private Size P(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String substring = str.substring(str.indexOf(58) + 1);
        if (TextUtils.isEmpty(substring)) {
            return null;
        }
        String[] split = substring.replace(" ", "").split("x");
        if (split.length >= 2) {
            return new Size(Integer.valueOf(split[0]).intValue(), Integer.valueOf(split[1]).intValue());
        }
        return null;
    }

    private static String a(Resources resources) {
        String str = "feature_" + b.rp;
        try {
            String string = resources.getString(R.string.device_feature_configuration_file_name);
            return (string == null || string.length() == 0 || E2EScenario.DEFAULT_CATEGORY.equals(string)) ? str : string;
        } catch (Resources.NotFoundException e) {
            Log.d(TAG, "Device feature configuration file name undefined", e);
            return str;
        }
    }

    private boolean gj() {
        return getBoolean(c.vD, false);
    }

    public boolean L(String str) {
        return getValues().containsKey(str);
    }

    public String M(String str) {
        return getString(c.vf, str);
    }

    public String N(String str) {
        return getString(c.vg, str);
    }

    public boolean fA() {
        if (this.rn == null) {
            this.rn = SystemProperties.get("ro.boot.hwc");
        }
        if ("india".equalsIgnoreCase(this.rn)) {
            return true;
        }
        return !TextUtils.isEmpty(this.rn) && this.rn.toLowerCase(Locale.ENGLISH).startsWith("india_");
    }

    public boolean fB() {
        return Build.getRegion().endsWith("IN");
    }

    public boolean fC() {
        if (this.rn == null) {
            this.rn = SystemProperties.get("ro.boot.hwc");
        }
        return "cn".equalsIgnoreCase(this.rn);
    }

    public boolean fD() {
        return getBoolean(c.tj, false) && fA();
    }

    public boolean fE() {
        return getBoolean(c.uk, false) && fA();
    }

    public boolean fF() {
        return getBoolean(c.tk, false);
    }

    public boolean fG() {
        return getBoolean(c.tl, false);
    }

    public boolean fH() {
        return getBoolean(c.tm, false);
    }

    public boolean fI() {
        return getBoolean(c.tT, false);
    }

    public boolean fJ() {
        return getBoolean(c.tn, false);
    }

    public boolean fK() {
        return getBoolean(c.to, false);
    }

    public boolean fL() {
        return (fA() || fB()) && getBoolean(c.tp, false);
    }

    public boolean fM() {
        return getBoolean(c.tr, true);
    }

    public boolean fN() {
        return getBoolean(c.tq, false);
    }

    public boolean fO() {
        return getBoolean(c.ts, false);
    }

    public boolean fP() {
        return getBoolean(c.tt, false);
    }

    public boolean fQ() {
        return getBoolean(c.tu, false);
    }

    public boolean fR() {
        return getBoolean(c.tv, false);
    }

    public boolean fS() {
        return getBoolean(c.tw, false);
    }

    public boolean fT() {
        return ((float) Util.sWindowHeight) / ((float) Util.sWindowWidth) >= 2.1666667f && getBoolean(c.tx, false);
    }

    public boolean fU() {
        return ((float) Util.sWindowHeight) / ((float) Util.sWindowWidth) >= 2.2222223f && getBoolean(c.tA, false);
    }

    public boolean fV() {
        return ((double) Math.abs((((float) Util.sWindowHeight) / ((float) Util.sWindowWidth)) - 2.1111112f)) <= 0.02d && getBoolean(c.ty, false);
    }

    public boolean fW() {
        return ((double) Math.abs((((float) Util.sWindowHeight) / ((float) Util.sWindowWidth)) - 2.0833333f)) < 0.02d && getBoolean(c.tz, false);
    }

    public boolean fX() {
        return getBoolean(c.tB, false);
    }

    public boolean fY() {
        return getBoolean(c.tC, false);
    }

    public boolean fZ() {
        return Build.VERSION.SDK_INT >= 28 && getBoolean(c.tJ, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0047, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004b, code lost:
        if (r0 != null) goto L_0x004d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0056, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0059, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x005a, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x005b, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x005a A[ExcHandler: IOException | JSONException (r0v2 'e' java.lang.Exception A[CUSTOM_DECLARE]), Splitter:B:5:0x0023] */
    public void fx() {
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        int identifier = resources.getIdentifier(a(resources), "raw", "com.android.camera");
        if (identifier <= 0) {
            Log.e(TAG, "feature list default");
            return;
        }
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resources.openRawResource(identifier)));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine);
                } else {
                    K(sb.toString());
                    bufferedReader.close();
                    return;
                }
            }
        } catch (IOException | JSONException e) {
        } catch (Throwable th) {
            r0.addSuppressed(th);
        }
    }

    public boolean fy() {
        return getBoolean(c.th, false);
    }

    public int fz() {
        return getInt(c.ti, 20);
    }

    public int gA() {
        return getInt(c.tZ, 280);
    }

    public float gB() {
        return (float) getDoubleFromValues(c.ua, 0.8766000270843506d);
    }

    public boolean gC() {
        return getBoolean(c.ub, false);
    }

    public boolean gD() {
        return getBoolean(c.uY, false) && !miui.os.Build.IS_INTERNATIONAL_BUILD;
    }

    public boolean gE() {
        return getBoolean(c.vF, false);
    }

    public boolean gF() {
        return getBoolean(c.vX, false);
    }

    public boolean gG() {
        return getBoolean(c.vH, false);
    }

    public boolean gH() {
        return getBoolean(c.tW, false);
    }

    public String gI() {
        return getString(c.vl, "v0");
    }

    public boolean gJ() {
        return getBoolean(c.uH, false);
    }

    public boolean gK() {
        return getBoolean(c.ug, false);
    }

    public boolean gL() {
        return getBoolean(c.uh, false);
    }

    public boolean gM() {
        return getBoolean(c.ui, false);
    }

    public boolean gN() {
        return getBoolean(c.un, true);
    }

    public boolean gO() {
        return getBoolean(c.uo, false);
    }

    public int gP() {
        return getInt(c.up, 0);
    }

    public boolean gQ() {
        if (!Util.isGlobalVersion()) {
            return false;
        }
        return getBoolean(c.uv, false);
    }

    public boolean gR() {
        return getBoolean(c.uw, false);
    }

    public boolean gS() {
        return getBoolean(c.ux, false);
    }

    public boolean gT() {
        return getBoolean(c.ul, false);
    }

    public boolean gU() {
        return gV() < 0 || getBoolean(c.um, false);
    }

    public int gV() {
        return O(getString(c.vM, ""));
    }

    public int gW() {
        return O(getString(c.vN, ""));
    }

    public boolean gX() {
        return getBoolean(c.uq, false);
    }

    public boolean gY() {
        return getBoolean(c.ur, false);
    }

    public boolean gZ() {
        if (b.kw()) {
            return false;
        }
        return getBoolean(c.vc, true);
    }

    public boolean ga() {
        return getBoolean(c.vZ, false);
    }

    public boolean gb() {
        return getBoolean(c.tK, false);
    }

    public boolean gd() {
        return getBoolean(c.tQ, true);
    }

    public boolean ge() {
        return getBoolean(c.tR, false);
    }

    public boolean gf() {
        return getBoolean(c.tL, false);
    }

    public boolean gg() {
        return getBoolean(c.tD, false);
    }

    public int gh() {
        return getInt(c.uj, 180);
    }

    public boolean gi() {
        return Build.VERSION.SDK_INT > 28 ? gj() : getBoolean(c.tE, false);
    }

    public boolean gk() {
        return getBoolean(c.tI, false);
    }

    public boolean gl() {
        return getBoolean(c.tH, false);
    }

    public boolean gm() {
        return getBoolean(c.tF, false);
    }

    public boolean gn() {
        return !gm() && getBoolean(c.tG, false);
    }

    public boolean go() {
        return !gm() && !gn() && getBoolean(c.vx, false);
    }

    public boolean gp() {
        return getBoolean(c.tF, false) || getBoolean(c.tG, false) || getBoolean(c.vx, false);
    }

    public boolean gq() {
        return getBoolean(c.tM, false);
    }

    public String gr() {
        return getString(c.tN, "");
    }

    public int gs() {
        return getInt(c.tO, 350);
    }

    public int gt() {
        return getInt(c.tP, 300);
    }

    public boolean gu() {
        return getBoolean(c.tV, true);
    }

    public boolean gv() {
        return getBoolean(c.uc, false);
    }

    public boolean gw() {
        return getBoolean(c.us, false);
    }

    public boolean gx() {
        return getBoolean(c.vH, false);
    }

    public boolean gy() {
        return getBoolean(c.uu, true);
    }

    public boolean gz() {
        if (Util.isGlobalVersion()) {
            return false;
        }
        return getBoolean(c.tY, false);
    }

    public String hA() {
        return getString(c.vb, "common");
    }

    public boolean hB() {
        return getBoolean(c.vd, !hP());
    }

    public boolean hC() {
        return getBoolean(c.ve, false);
    }

    public boolean hD() {
        return getBoolean(c.vj, false);
    }

    public boolean hE() {
        return getBoolean(c.vk, false);
    }

    public boolean hF() {
        return getBoolean(c.vm, false);
    }

    public boolean hG() {
        if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            return false;
        }
        return getBoolean(c.vn, false);
    }

    public boolean hH() {
        return getBoolean(c.vo, false);
    }

    public boolean hI() {
        return getBoolean(c.vp, false);
    }

    public boolean hJ() {
        return getBoolean(c.vq, true);
    }

    public boolean hK() {
        return getBoolean(c.vs, false);
    }

    public boolean hL() {
        return getBoolean(c.vr, false);
    }

    public boolean hM() {
        return getBoolean(c.vt, false);
    }

    public boolean hN() {
        return getBoolean(c.vu, false);
    }

    public boolean hO() {
        return getBoolean(c.vv, true);
    }

    public boolean hP() {
        return getBoolean(c.vw, false);
    }

    public boolean hQ() {
        return getBoolean(c.vy, false);
    }

    public boolean hR() {
        return true;
    }

    public boolean hS() {
        return getBoolean(c.vz, false);
    }

    public int hT() {
        if (b.sU) {
            return 6;
        }
        return getInt(c.vA, 5);
    }

    public int hU() {
        return getInt(c.vC, 0);
    }

    public int hV() {
        return getInt(c.vI, 0);
    }

    public boolean hW() {
        return getBoolean(c.vE, false);
    }

    public boolean hX() {
        return getBoolean(c.vG, false);
    }

    public boolean hY() {
        return getBoolean(c.vJ, false);
    }

    public boolean hZ() {
        return getBoolean(c.vK, false);
    }

    public boolean ha() {
        return getBoolean(c.uy, true);
    }

    public boolean hb() {
        return getBoolean(c.uz, false);
    }

    public boolean hc() {
        if (Build.VERSION.SDK_INT < 28) {
            return false;
        }
        return getBoolean(c.uB, false);
    }

    public boolean hd() {
        return getBoolean(c.uC, false);
    }

    public boolean he() {
        return getBoolean(c.uD, false);
    }

    public boolean hf() {
        return getBoolean(c.uE, false);
    }

    public boolean hg() {
        return getBoolean(c.uF, false);
    }

    public boolean hh() {
        return getBoolean(c.uG, true);
    }

    public boolean hi() {
        return getBoolean(c.uI, false);
    }

    public boolean hj() {
        return getBoolean(c.uJ, false);
    }

    public boolean hk() {
        return getBoolean(c.tX, false);
    }

    public boolean hl() {
        return getBoolean(c.uK, true);
    }

    public boolean hm() {
        return getBoolean(c.uL, false);
    }

    public boolean hn() {
        return getBoolean(c.uO, false);
    }

    public long ho() {
        return (long) getInt(c.uM, -1);
    }

    public int hp() {
        return getInt(c.uN, -1);
    }

    public boolean hq() {
        return getBoolean(c.uS, false);
    }

    public boolean hr() {
        return getBoolean(c.uU, true);
    }

    public boolean hs() {
        return getBoolean(c.vi, false);
    }

    public boolean ht() {
        switch (gV()) {
            case 1:
            case 2:
            case 3:
                return hr();
            default:
                return false;
        }
    }

    public boolean hu() {
        return 3 == gV();
    }

    public boolean hv() {
        return getBoolean(c.uV, true);
    }

    public boolean hw() {
        return getBoolean(c.uW, true);
    }

    public boolean hx() {
        return getBoolean(c.uX, true);
    }

    public boolean hy() {
        return getBoolean(c.uZ, false);
    }

    public boolean hz() {
        return getBoolean(c.va, false);
    }

    public boolean iA() {
        return getBoolean(c.wm, false);
    }

    public boolean iB() {
        return getBoolean(c.wl, false);
    }

    public boolean iC() {
        return getBoolean(c.wk, false);
    }

    public String iD() {
        return getString(c.wo, "");
    }

    public boolean ia() {
        return getBoolean(c.vL, false);
    }

    public Size ib() {
        return P(getString(c.vM, ""));
    }

    public Size ic() {
        return P(getString(c.vN, ""));
    }

    public boolean id() {
        return getBoolean(c.vO, true);
    }

    public boolean ie() {
        return getBoolean(c.vY, false);
    }

    /* renamed from: if  reason: not valid java name */
    public int m2if() {
        return getInt(c.vP, -1);
    }

    public boolean ig() {
        return getInt(c.vQ, -1) == 0;
    }

    public boolean ih() {
        return getInt(c.vQ, -1) == 1;
    }

    public boolean ii() {
        return getInt(c.vQ, -1) == 2;
    }

    public boolean ij() {
        return getBoolean(c.vR, false);
    }

    public boolean ik() {
        return getBoolean(c.vS, false);
    }

    public boolean il() {
        return getBoolean(c.vT, false);
    }

    public boolean im() {
        return getBoolean(c.vB, false);
    }

    public boolean in() {
        return getBoolean(c.vU, false);
    }

    public boolean ip() {
        return getBoolean(c.vV, false);
    }

    public boolean iq() {
        return getBoolean(c.wa, false);
    }

    public int ir() {
        return getInt(c.wi, 0);
    }

    public boolean is() {
        return getBoolean(c.vW, false);
    }

    /* access modifiers changed from: protected */
    public boolean isMutable() {
        return false;
    }

    public boolean isSRRequireReprocess() {
        return getBoolean(c.wn, false);
    }

    public boolean isSupport4KUHDEIS() {
        return getBoolean(c.uf, false);
    }

    public boolean isSupport960VideoEditor() {
        return getBoolean(c.wj, false);
    }

    public boolean isSupportBeautyBody() {
        return getBoolean(c.tU, false);
    }

    public boolean isSupportBokehAdjust() {
        return getBoolean(c.uA, false);
    }

    public boolean isSupportMacroMode() {
        return getBoolean(c.uT, false);
    }

    public boolean isSupportNormalWideLDC() {
        return getBoolean(c.ud, false);
    }

    public boolean isSupportShortVideoBeautyBody() {
        return getBoolean(c.uP, false);
    }

    public boolean isSupportUltraWide() {
        return getBoolean(c.tS, false);
    }

    public boolean isSupportUltraWideLDC() {
        return getBoolean(c.ue, false);
    }

    public boolean isTransient() {
        return true;
    }

    public boolean it() {
        return getBoolean(c.wc, false);
    }

    public String iu() {
        return getString(c.wd, (String) null);
    }

    public boolean iv() {
        return getBoolean(c.we, false);
    }

    public boolean iw() {
        return getBoolean(c.wf, false);
    }

    public boolean ix() {
        return getInt(c.wg, 0) == 0;
    }

    public boolean iy() {
        return getInt(c.wg, 0) == 1;
    }

    public boolean iz() {
        return getBoolean(c.wh, false);
    }

    public String p(boolean z) {
        return z ? getString(c.uQ, "4.5") : getString(c.uR, "4");
    }

    public String provideKey() {
        return null;
    }

    public boolean q(boolean z) {
        return z && getBoolean(c.vh, false);
    }

    public int r(boolean z) {
        if (!z) {
            return 0;
        }
        return getInt(c.wb, 0);
    }

    public boolean shouldCheckSatFallbackState() {
        return getBoolean(c.wp, false);
    }
}
