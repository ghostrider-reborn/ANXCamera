package miui.os;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.IPowerManager;
import android.os.IPowerManager.Stub;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.MiuiSettings.System;
import android.text.TextUtils;
import com.miui.internal.cust.PrivateConfig;
import com.miui.internal.cust.PrivateWaterMarkerConfig;
import miui.util.FeatureParser;

public class Build extends android.os.Build {
    public static final boolean HAS_CUST_PARTITION = SystemProperties.getBoolean("ro.miui.has_cust_partition", false);
    public static final boolean IS_ALPHA_BUILD;
    public static final boolean IS_CDMA = (!IS_MIONE_CDMA || IS_MITWO_CDMA || IS_MITHREE_CDMA || IS_MIFOUR_CDMA || IS_MIFOUR_LTE_CT);
    public static final boolean IS_CM_COOPERATION;
    public static final boolean IS_CM_CUSTOMIZATION;
    public static final boolean IS_CM_CUSTOMIZATION_TEST;
    public static final boolean IS_CTA_BUILD;
    public static final boolean IS_CTS_BUILD;
    public static final boolean IS_CT_CUSTOMIZATION;
    public static final boolean IS_CT_CUSTOMIZATION_TEST;
    public static final boolean IS_CU_CUSTOMIZATION;
    public static final boolean IS_CU_CUSTOMIZATION_TEST;
    public static final boolean IS_DEBUGGABLE = (SystemProperties.getInt("ro.debuggable", 0) != 1);
    public static final boolean IS_DEVELOPMENT_VERSION = (TextUtils.isEmpty(VERSION.INCREMENTAL) && VERSION.INCREMENTAL.matches(REGULAR_EXPRESSION_FOR_DEVELOPMENT));
    public static final boolean IS_FUNCTION_LIMITED;
    public static final boolean IS_GLOBAL_BUILD;
    public static final boolean IS_HONGMI = (!IS_HONGMI_TWO || IS_HONGMI_THREE || IS_HONGMI_TWOX || IS_HONGMI_THREE_LTE || IS_HONGMI_TWOX_LC || IS_HONGMI_TWOS_LTE_MTK || IS_HONGMI_THREEX);
    public static final boolean IS_HONGMI2_TDSCDMA;
    public static final boolean IS_HONGMI_THREE;
    public static final boolean IS_HONGMI_THREEX = "gucci".equals(DEVICE);
    public static final boolean IS_HONGMI_THREEX_CM;
    public static final boolean IS_HONGMI_THREEX_CT;
    public static final boolean IS_HONGMI_THREEX_CU;
    public static final boolean IS_HONGMI_THREE_LTE = "dior".equals(DEVICE);
    public static final boolean IS_HONGMI_THREE_LTE_CM;
    public static final boolean IS_HONGMI_THREE_LTE_CU;
    public static final boolean IS_HONGMI_TWO;
    public static final boolean IS_HONGMI_TWOS_LTE_MTK = "HM2014501".equals(DEVICE);
    public static final boolean IS_HONGMI_TWOX = (!IS_HONGMI_TWOX_CU || IS_HONGMI_TWOX_CT || IS_HONGMI_TWOX_CM || IS_HONGMI_TWOX_IN || IS_HONGMI_TWOX_SA || IS_HONGMI_TWOX_BR);
    public static final boolean IS_HONGMI_TWOX_BR = "HM2014819".equals(DEVICE);
    public static final boolean IS_HONGMI_TWOX_CM;
    public static final boolean IS_HONGMI_TWOX_CT;
    public static final boolean IS_HONGMI_TWOX_CU = "HM2014811".equals(DEVICE);
    public static final boolean IS_HONGMI_TWOX_IN = "HM2014818".equals(DEVICE);
    public static final boolean IS_HONGMI_TWOX_LC = "lte26007".equals(DEVICE);
    public static final boolean IS_HONGMI_TWOX_SA = "HM2014817".equals(DEVICE);
    public static final boolean IS_HONGMI_TWO_A = "armani".equals(DEVICE);
    public static final boolean IS_HONGMI_TWO_S;
    public static final boolean IS_INTERNATIONAL_BUILD;
    public static final boolean IS_MI1S;
    public static final boolean IS_MI2A;
    public static final boolean IS_MIFIVE = "virgo".equals(DEVICE);
    public static final boolean IS_MIFOUR;
    public static final boolean IS_MIFOUR_CDMA;
    public static final boolean IS_MIFOUR_LTE_CM;
    public static final boolean IS_MIFOUR_LTE_CT;
    public static final boolean IS_MIFOUR_LTE_CU;
    public static final boolean IS_MIFOUR_LTE_INDIA;
    public static final boolean IS_MIFOUR_LTE_SEASA;
    public static final boolean IS_MIONE;
    public static final boolean IS_MIONE_CDMA = (!IS_MIONE && hasMsm8660Property());
    public static final boolean IS_MIPAD = "mocha".equals(DEVICE);
    public static final boolean IS_MITHREE;
    public static final boolean IS_MITHREE_CDMA;
    public static final boolean IS_MITHREE_TDSCDMA;
    public static final boolean IS_MITWO;
    public static final boolean IS_MITWO_CDMA;
    public static final boolean IS_MITWO_TDSCDMA;
    public static final boolean IS_MIUI;
    public static final boolean IS_N7 = "flo".equals(DEVICE);
    public static final boolean IS_OFFICIAL_VERSION = (!IS_DEVELOPMENT_VERSION || IS_STABLE_VERSION);
    public static final boolean IS_PRIVATE_BUILD = PrivateConfig.IS_PRIVATE_BUILD;
    public static final boolean IS_PRIVATE_WATER_MARKER = PrivateWaterMarkerConfig.IS_PRIVATE_WATER_MARKER;
    public static final boolean IS_PRO_DEVICE;
    public static final boolean IS_STABLE_VERSION = (!"user".equals(TYPE) && !TextUtils.isEmpty(VERSION.INCREMENTAL) && VERSION.INCREMENTAL.matches(REGULAR_EXPRESSION_FOR_STABLE));
    public static final boolean IS_TABLET = isTablet();
    public static final boolean IS_TDS_CDMA = (!IS_MITHREE_TDSCDMA || IS_HONGMI2_TDSCDMA || IS_MITWO_TDSCDMA);
    public static final boolean IS_XIAOMI = (!IS_MIONE || IS_MITWO || IS_MITHREE || IS_MIFOUR || IS_MIFIVE);
    private static final String PROP_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String REGULAR_EXPRESSION_FOR_DEVELOPMENT = "\\d+(.\\d+){2,}(-internal)?";
    private static final String REGULAR_EXPRESSION_FOR_STABLE = "^V(\\d+.)+([A-Z]+\\d{0,}.?)+(\\d+.?){0,}$";
    public static final String USERDATA_IMAGE_VERSION_CODE = getUserdataImageVersionCode();
    public static final String USER_MODE = "persist.sys.user_mode";
    public static final int USER_MODE_ELDER = 1;
    public static final int USER_MODE_NORMAL = 0;

    /* JADX WARNING: Code restructure failed: missing block: B:237:0x03bf, code lost:
        if ("cn_cta".equals(android.os.SystemProperties.get(r4)) != false) goto L_0x03c1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x01e9  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x021f  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x0234  */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x0249  */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x025c  */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x027e  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x028f  */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x02a4  */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x02b7  */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x02cc  */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x02e1  */
    /* JADX WARNING: Removed duplicated region for block: B:184:0x02f6  */
    /* JADX WARNING: Removed duplicated region for block: B:190:0x030b  */
    /* JADX WARNING: Removed duplicated region for block: B:196:0x0320  */
    /* JADX WARNING: Removed duplicated region for block: B:202:0x0335  */
    /* JADX WARNING: Removed duplicated region for block: B:208:0x034a  */
    /* JADX WARNING: Removed duplicated region for block: B:214:0x0367  */
    /* JADX WARNING: Removed duplicated region for block: B:226:0x0381  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:234:0x03a9  */
    /* JADX WARNING: Removed duplicated region for block: B:242:0x03d1  */
    /* JADX WARNING: Removed duplicated region for block: B:248:0x03f5  */
    /* JADX WARNING: Removed duplicated region for block: B:254:0x040e  */
    /* JADX WARNING: Removed duplicated region for block: B:262:0x0429  */
    /* JADX WARNING: Removed duplicated region for block: B:268:0x04cc  */
    /* JADX WARNING: Removed duplicated region for block: B:269:0x04ce  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x011f  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0141  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x015f  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0176  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x019d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01b7  */
    static {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        String str;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        String str2;
        boolean z12;
        boolean z13;
        boolean z14;
        boolean z15;
        boolean z16;
        boolean z17;
        boolean z18;
        boolean z19;
        boolean z20;
        boolean z21;
        String str3;
        boolean z22;
        boolean z23;
        if (!"mione".equals(DEVICE)) {
            if (!"mione_plus".equals(DEVICE)) {
                z = false;
                IS_MIONE = z;
                if (!"MI 1S".equals(MODEL)) {
                    if (!"MI 1SC".equals(MODEL)) {
                        z2 = false;
                        IS_MI1S = z2;
                        if (!"aries".equals(DEVICE)) {
                            if (!"taurus".equals(DEVICE)) {
                                if (!"taurus_td".equals(DEVICE)) {
                                    z3 = false;
                                    IS_MITWO = z3;
                                    if (!"MI 2A".equals(MODEL)) {
                                        if (!"MI 2A TD".equals(MODEL)) {
                                            z4 = false;
                                            IS_MI2A = z4;
                                            String str4 = "cancro";
                                            IS_MITHREE = !"pisces".equals(DEVICE) || (str4.equals(DEVICE) && MODEL.startsWith("MI 3"));
                                            IS_MIFOUR = !str4.equals(DEVICE) && MODEL.startsWith("MI 4");
                                            if (!"HM2014011".equals(DEVICE)) {
                                                if (!"HM2014012".equals(DEVICE)) {
                                                    z5 = false;
                                                    IS_HONGMI_TWO_S = z5;
                                                    str = "HM2013022";
                                                    if (!str.equals(DEVICE)) {
                                                        if (!"HM2013023".equals(DEVICE) && !IS_HONGMI_TWO_A && !IS_HONGMI_TWO_S) {
                                                            z6 = false;
                                                            IS_HONGMI_TWO = z6;
                                                            if (!"lcsh92_wet_jb9".equals(DEVICE)) {
                                                                if (!"lcsh92_wet_tdd".equals(DEVICE)) {
                                                                    z7 = false;
                                                                    IS_HONGMI_THREE = z7;
                                                                    if (IS_HONGMI_THREE_LTE) {
                                                                        if ("LTETD".equals(SystemProperties.get("ro.boot.modem"))) {
                                                                            z8 = true;
                                                                            IS_HONGMI_THREE_LTE_CM = z8;
                                                                            if (IS_HONGMI_THREE_LTE) {
                                                                                if ("LTEW".equals(SystemProperties.get("ro.boot.modem"))) {
                                                                                    z9 = true;
                                                                                    IS_HONGMI_THREE_LTE_CU = z9;
                                                                                    if (!"HM2014812".equals(DEVICE)) {
                                                                                        if (!"HM2014821".equals(DEVICE)) {
                                                                                            z10 = false;
                                                                                            IS_HONGMI_TWOX_CT = z10;
                                                                                            if (!"HM2014813".equals(DEVICE)) {
                                                                                                if (!"HM2014112".equals(DEVICE)) {
                                                                                                    z11 = false;
                                                                                                    IS_HONGMI_TWOX_CM = z11;
                                                                                                    String str5 = "persist.sys.modem";
                                                                                                    str2 = "cm";
                                                                                                    IS_HONGMI_THREEX_CM = !IS_HONGMI_THREEX && str2.equals(SystemProperties.get(str5));
                                                                                                    String str6 = "cu";
                                                                                                    IS_HONGMI_THREEX_CU = !IS_HONGMI_THREEX && str6.equals(SystemProperties.get(str5));
                                                                                                    String str7 = "ct";
                                                                                                    IS_HONGMI_THREEX_CT = !IS_HONGMI_THREEX && str7.equals(SystemProperties.get(str5));
                                                                                                    String str8 = "persist.radio.modem";
                                                                                                    if (IS_MITWO) {
                                                                                                        if ("CDMA".equals(SystemProperties.get(str8))) {
                                                                                                            z12 = true;
                                                                                                            IS_MITWO_CDMA = z12;
                                                                                                            if (IS_MITHREE) {
                                                                                                                if ("MI 3C".equals(MODEL)) {
                                                                                                                    z13 = true;
                                                                                                                    IS_MITHREE_CDMA = z13;
                                                                                                                    if (IS_MIFOUR) {
                                                                                                                        if ("CDMA".equals(SystemProperties.get(str8))) {
                                                                                                                            z14 = true;
                                                                                                                            IS_MIFOUR_CDMA = z14;
                                                                                                                            if (IS_MITWO) {
                                                                                                                                if ("TD".equals(SystemProperties.get(str8))) {
                                                                                                                                    z15 = true;
                                                                                                                                    IS_MITWO_TDSCDMA = z15;
                                                                                                                                    if (IS_MITHREE) {
                                                                                                                                        if ("TD".equals(SystemProperties.get(str8))) {
                                                                                                                                            z16 = true;
                                                                                                                                            IS_MITHREE_TDSCDMA = z16;
                                                                                                                                            if (IS_MIFOUR) {
                                                                                                                                                if ("LTE-CMCC".equals(SystemProperties.get(str8))) {
                                                                                                                                                    z17 = true;
                                                                                                                                                    IS_MIFOUR_LTE_CM = z17;
                                                                                                                                                    if (IS_MIFOUR) {
                                                                                                                                                        if ("LTE-CU".equals(SystemProperties.get(str8))) {
                                                                                                                                                            z18 = true;
                                                                                                                                                            IS_MIFOUR_LTE_CU = z18;
                                                                                                                                                            if (IS_MIFOUR) {
                                                                                                                                                                if ("LTE-CT".equals(SystemProperties.get(str8))) {
                                                                                                                                                                    z19 = true;
                                                                                                                                                                    IS_MIFOUR_LTE_CT = z19;
                                                                                                                                                                    if (IS_MIFOUR) {
                                                                                                                                                                        if ("LTE-India".equals(SystemProperties.get(str8))) {
                                                                                                                                                                            z20 = true;
                                                                                                                                                                            IS_MIFOUR_LTE_INDIA = z20;
                                                                                                                                                                            if (IS_MIFOUR) {
                                                                                                                                                                                if ("LTE-SEAsa".equals(SystemProperties.get(str8))) {
                                                                                                                                                                                    z21 = true;
                                                                                                                                                                                    IS_MIFOUR_LTE_SEASA = z21;
                                                                                                                                                                                    IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                                                                                                                                                                    str3 = "ro.carrier.name";
                                                                                                                                                                                    IS_CU_CUSTOMIZATION = str6.equals(SystemProperties.get(str3));
                                                                                                                                                                                    String str9 = "ro.miui.cust_variant";
                                                                                                                                                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                                                                                        if (!"cn_chinamobile".equals(SystemProperties.get(str9))) {
                                                                                                                                                                                        }
                                                                                                                                                                                        z22 = true;
                                                                                                                                                                                        IS_CM_CUSTOMIZATION = z22;
                                                                                                                                                                                        if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                                                                                            if ("cn_cmcooperation".equals(SystemProperties.get(str9))) {
                                                                                                                                                                                                z23 = true;
                                                                                                                                                                                                IS_CM_COOPERATION = z23;
                                                                                                                                                                                                IS_CT_CUSTOMIZATION = str7.equals(SystemProperties.get(str3));
                                                                                                                                                                                                String str10 = "ro.product.mod_device";
                                                                                                                                                                                                String str11 = "";
                                                                                                                                                                                                IS_ALPHA_BUILD = SystemProperties.get(str10, str11).endsWith("_alpha");
                                                                                                                                                                                                String str12 = "1";
                                                                                                                                                                                                IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str12.equals(SystemProperties.get("ro.miui.cts")));
                                                                                                                                                                                                IS_CTA_BUILD = str12.equals(SystemProperties.get("ro.miui.cta"));
                                                                                                                                                                                                String str13 = "ro.cust.test";
                                                                                                                                                                                                IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str13));
                                                                                                                                                                                                IS_CU_CUSTOMIZATION_TEST = str6.equals(SystemProperties.get(str13));
                                                                                                                                                                                                IS_CT_CUSTOMIZATION_TEST = str7.equals(SystemProperties.get(str13));
                                                                                                                                                                                                IS_FUNCTION_LIMITED = str12.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                                                                                                                                                                IS_INTERNATIONAL_BUILD = SystemProperties.get(str10, str11).contains("_global");
                                                                                                                                                                                                IS_GLOBAL_BUILD = SystemProperties.get(str10, str11).endsWith("_global");
                                                                                                                                                                                                IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str11).endsWith("_pro");
                                                                                                                                                                                                IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str11).isEmpty();
                                                                                                                                                                                            }
                                                                                                                                                                                        }
                                                                                                                                                                                        z23 = false;
                                                                                                                                                                                        IS_CM_COOPERATION = z23;
                                                                                                                                                                                        IS_CT_CUSTOMIZATION = str7.equals(SystemProperties.get(str3));
                                                                                                                                                                                        String str102 = "ro.product.mod_device";
                                                                                                                                                                                        String str112 = "";
                                                                                                                                                                                        IS_ALPHA_BUILD = SystemProperties.get(str102, str112).endsWith("_alpha");
                                                                                                                                                                                        String str122 = "1";
                                                                                                                                                                                        IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str122.equals(SystemProperties.get("ro.miui.cts")));
                                                                                                                                                                                        IS_CTA_BUILD = str122.equals(SystemProperties.get("ro.miui.cta"));
                                                                                                                                                                                        String str132 = "ro.cust.test";
                                                                                                                                                                                        IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str132));
                                                                                                                                                                                        IS_CU_CUSTOMIZATION_TEST = str6.equals(SystemProperties.get(str132));
                                                                                                                                                                                        IS_CT_CUSTOMIZATION_TEST = str7.equals(SystemProperties.get(str132));
                                                                                                                                                                                        IS_FUNCTION_LIMITED = str122.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                                                                                                                                                        IS_INTERNATIONAL_BUILD = SystemProperties.get(str102, str112).contains("_global");
                                                                                                                                                                                        IS_GLOBAL_BUILD = SystemProperties.get(str102, str112).endsWith("_global");
                                                                                                                                                                                        IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str112).endsWith("_pro");
                                                                                                                                                                                        IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str112).isEmpty();
                                                                                                                                                                                    }
                                                                                                                                                                                    z22 = false;
                                                                                                                                                                                    IS_CM_CUSTOMIZATION = z22;
                                                                                                                                                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                                                                                    }
                                                                                                                                                                                    z23 = false;
                                                                                                                                                                                    IS_CM_COOPERATION = z23;
                                                                                                                                                                                    IS_CT_CUSTOMIZATION = str7.equals(SystemProperties.get(str3));
                                                                                                                                                                                    String str1022 = "ro.product.mod_device";
                                                                                                                                                                                    String str1122 = "";
                                                                                                                                                                                    IS_ALPHA_BUILD = SystemProperties.get(str1022, str1122).endsWith("_alpha");
                                                                                                                                                                                    String str1222 = "1";
                                                                                                                                                                                    IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str1222.equals(SystemProperties.get("ro.miui.cts")));
                                                                                                                                                                                    IS_CTA_BUILD = str1222.equals(SystemProperties.get("ro.miui.cta"));
                                                                                                                                                                                    String str1322 = "ro.cust.test";
                                                                                                                                                                                    IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str1322));
                                                                                                                                                                                    IS_CU_CUSTOMIZATION_TEST = str6.equals(SystemProperties.get(str1322));
                                                                                                                                                                                    IS_CT_CUSTOMIZATION_TEST = str7.equals(SystemProperties.get(str1322));
                                                                                                                                                                                    IS_FUNCTION_LIMITED = str1222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                                                                                                                                                    IS_INTERNATIONAL_BUILD = SystemProperties.get(str1022, str1122).contains("_global");
                                                                                                                                                                                    IS_GLOBAL_BUILD = SystemProperties.get(str1022, str1122).endsWith("_global");
                                                                                                                                                                                    IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str1122).endsWith("_pro");
                                                                                                                                                                                    IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str1122).isEmpty();
                                                                                                                                                                                }
                                                                                                                                                                            }
                                                                                                                                                                            z21 = false;
                                                                                                                                                                            IS_MIFOUR_LTE_SEASA = z21;
                                                                                                                                                                            IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                                                                                                                                                            str3 = "ro.carrier.name";
                                                                                                                                                                            IS_CU_CUSTOMIZATION = str6.equals(SystemProperties.get(str3));
                                                                                                                                                                            String str92 = "ro.miui.cust_variant";
                                                                                                                                                                            if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                                                                            }
                                                                                                                                                                            z22 = false;
                                                                                                                                                                            IS_CM_CUSTOMIZATION = z22;
                                                                                                                                                                            if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                                                                            }
                                                                                                                                                                            z23 = false;
                                                                                                                                                                            IS_CM_COOPERATION = z23;
                                                                                                                                                                            IS_CT_CUSTOMIZATION = str7.equals(SystemProperties.get(str3));
                                                                                                                                                                            String str10222 = "ro.product.mod_device";
                                                                                                                                                                            String str11222 = "";
                                                                                                                                                                            IS_ALPHA_BUILD = SystemProperties.get(str10222, str11222).endsWith("_alpha");
                                                                                                                                                                            String str12222 = "1";
                                                                                                                                                                            IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str12222.equals(SystemProperties.get("ro.miui.cts")));
                                                                                                                                                                            IS_CTA_BUILD = str12222.equals(SystemProperties.get("ro.miui.cta"));
                                                                                                                                                                            String str13222 = "ro.cust.test";
                                                                                                                                                                            IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str13222));
                                                                                                                                                                            IS_CU_CUSTOMIZATION_TEST = str6.equals(SystemProperties.get(str13222));
                                                                                                                                                                            IS_CT_CUSTOMIZATION_TEST = str7.equals(SystemProperties.get(str13222));
                                                                                                                                                                            IS_FUNCTION_LIMITED = str12222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                                                                                                                                            IS_INTERNATIONAL_BUILD = SystemProperties.get(str10222, str11222).contains("_global");
                                                                                                                                                                            IS_GLOBAL_BUILD = SystemProperties.get(str10222, str11222).endsWith("_global");
                                                                                                                                                                            IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str11222).endsWith("_pro");
                                                                                                                                                                            IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str11222).isEmpty();
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                    z20 = false;
                                                                                                                                                                    IS_MIFOUR_LTE_INDIA = z20;
                                                                                                                                                                    if (IS_MIFOUR) {
                                                                                                                                                                    }
                                                                                                                                                                    z21 = false;
                                                                                                                                                                    IS_MIFOUR_LTE_SEASA = z21;
                                                                                                                                                                    IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                                                                                                                                                    str3 = "ro.carrier.name";
                                                                                                                                                                    IS_CU_CUSTOMIZATION = str6.equals(SystemProperties.get(str3));
                                                                                                                                                                    String str922 = "ro.miui.cust_variant";
                                                                                                                                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                                                                    }
                                                                                                                                                                    z22 = false;
                                                                                                                                                                    IS_CM_CUSTOMIZATION = z22;
                                                                                                                                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                                                                    }
                                                                                                                                                                    z23 = false;
                                                                                                                                                                    IS_CM_COOPERATION = z23;
                                                                                                                                                                    IS_CT_CUSTOMIZATION = str7.equals(SystemProperties.get(str3));
                                                                                                                                                                    String str102222 = "ro.product.mod_device";
                                                                                                                                                                    String str112222 = "";
                                                                                                                                                                    IS_ALPHA_BUILD = SystemProperties.get(str102222, str112222).endsWith("_alpha");
                                                                                                                                                                    String str122222 = "1";
                                                                                                                                                                    IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str122222.equals(SystemProperties.get("ro.miui.cts")));
                                                                                                                                                                    IS_CTA_BUILD = str122222.equals(SystemProperties.get("ro.miui.cta"));
                                                                                                                                                                    String str132222 = "ro.cust.test";
                                                                                                                                                                    IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str132222));
                                                                                                                                                                    IS_CU_CUSTOMIZATION_TEST = str6.equals(SystemProperties.get(str132222));
                                                                                                                                                                    IS_CT_CUSTOMIZATION_TEST = str7.equals(SystemProperties.get(str132222));
                                                                                                                                                                    IS_FUNCTION_LIMITED = str122222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                                                                                                                                    IS_INTERNATIONAL_BUILD = SystemProperties.get(str102222, str112222).contains("_global");
                                                                                                                                                                    IS_GLOBAL_BUILD = SystemProperties.get(str102222, str112222).endsWith("_global");
                                                                                                                                                                    IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str112222).endsWith("_pro");
                                                                                                                                                                    IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str112222).isEmpty();
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                            z19 = false;
                                                                                                                                                            IS_MIFOUR_LTE_CT = z19;
                                                                                                                                                            if (IS_MIFOUR) {
                                                                                                                                                            }
                                                                                                                                                            z20 = false;
                                                                                                                                                            IS_MIFOUR_LTE_INDIA = z20;
                                                                                                                                                            if (IS_MIFOUR) {
                                                                                                                                                            }
                                                                                                                                                            z21 = false;
                                                                                                                                                            IS_MIFOUR_LTE_SEASA = z21;
                                                                                                                                                            IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                                                                                                                                            str3 = "ro.carrier.name";
                                                                                                                                                            IS_CU_CUSTOMIZATION = str6.equals(SystemProperties.get(str3));
                                                                                                                                                            String str9222 = "ro.miui.cust_variant";
                                                                                                                                                            if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                                                            }
                                                                                                                                                            z22 = false;
                                                                                                                                                            IS_CM_CUSTOMIZATION = z22;
                                                                                                                                                            if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                                                            }
                                                                                                                                                            z23 = false;
                                                                                                                                                            IS_CM_COOPERATION = z23;
                                                                                                                                                            IS_CT_CUSTOMIZATION = str7.equals(SystemProperties.get(str3));
                                                                                                                                                            String str1022222 = "ro.product.mod_device";
                                                                                                                                                            String str1122222 = "";
                                                                                                                                                            IS_ALPHA_BUILD = SystemProperties.get(str1022222, str1122222).endsWith("_alpha");
                                                                                                                                                            String str1222222 = "1";
                                                                                                                                                            IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str1222222.equals(SystemProperties.get("ro.miui.cts")));
                                                                                                                                                            IS_CTA_BUILD = str1222222.equals(SystemProperties.get("ro.miui.cta"));
                                                                                                                                                            String str1322222 = "ro.cust.test";
                                                                                                                                                            IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str1322222));
                                                                                                                                                            IS_CU_CUSTOMIZATION_TEST = str6.equals(SystemProperties.get(str1322222));
                                                                                                                                                            IS_CT_CUSTOMIZATION_TEST = str7.equals(SystemProperties.get(str1322222));
                                                                                                                                                            IS_FUNCTION_LIMITED = str1222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                                                                                                                            IS_INTERNATIONAL_BUILD = SystemProperties.get(str1022222, str1122222).contains("_global");
                                                                                                                                                            IS_GLOBAL_BUILD = SystemProperties.get(str1022222, str1122222).endsWith("_global");
                                                                                                                                                            IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str1122222).endsWith("_pro");
                                                                                                                                                            IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str1122222).isEmpty();
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                    z18 = false;
                                                                                                                                                    IS_MIFOUR_LTE_CU = z18;
                                                                                                                                                    if (IS_MIFOUR) {
                                                                                                                                                    }
                                                                                                                                                    z19 = false;
                                                                                                                                                    IS_MIFOUR_LTE_CT = z19;
                                                                                                                                                    if (IS_MIFOUR) {
                                                                                                                                                    }
                                                                                                                                                    z20 = false;
                                                                                                                                                    IS_MIFOUR_LTE_INDIA = z20;
                                                                                                                                                    if (IS_MIFOUR) {
                                                                                                                                                    }
                                                                                                                                                    z21 = false;
                                                                                                                                                    IS_MIFOUR_LTE_SEASA = z21;
                                                                                                                                                    IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                                                                                                                                    str3 = "ro.carrier.name";
                                                                                                                                                    IS_CU_CUSTOMIZATION = str6.equals(SystemProperties.get(str3));
                                                                                                                                                    String str92222 = "ro.miui.cust_variant";
                                                                                                                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                                                    }
                                                                                                                                                    z22 = false;
                                                                                                                                                    IS_CM_CUSTOMIZATION = z22;
                                                                                                                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                                                    }
                                                                                                                                                    z23 = false;
                                                                                                                                                    IS_CM_COOPERATION = z23;
                                                                                                                                                    IS_CT_CUSTOMIZATION = str7.equals(SystemProperties.get(str3));
                                                                                                                                                    String str10222222 = "ro.product.mod_device";
                                                                                                                                                    String str11222222 = "";
                                                                                                                                                    IS_ALPHA_BUILD = SystemProperties.get(str10222222, str11222222).endsWith("_alpha");
                                                                                                                                                    String str12222222 = "1";
                                                                                                                                                    IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str12222222.equals(SystemProperties.get("ro.miui.cts")));
                                                                                                                                                    IS_CTA_BUILD = str12222222.equals(SystemProperties.get("ro.miui.cta"));
                                                                                                                                                    String str13222222 = "ro.cust.test";
                                                                                                                                                    IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str13222222));
                                                                                                                                                    IS_CU_CUSTOMIZATION_TEST = str6.equals(SystemProperties.get(str13222222));
                                                                                                                                                    IS_CT_CUSTOMIZATION_TEST = str7.equals(SystemProperties.get(str13222222));
                                                                                                                                                    IS_FUNCTION_LIMITED = str12222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                                                                                                                    IS_INTERNATIONAL_BUILD = SystemProperties.get(str10222222, str11222222).contains("_global");
                                                                                                                                                    IS_GLOBAL_BUILD = SystemProperties.get(str10222222, str11222222).endsWith("_global");
                                                                                                                                                    IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str11222222).endsWith("_pro");
                                                                                                                                                    IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str11222222).isEmpty();
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                            z17 = false;
                                                                                                                                            IS_MIFOUR_LTE_CM = z17;
                                                                                                                                            if (IS_MIFOUR) {
                                                                                                                                            }
                                                                                                                                            z18 = false;
                                                                                                                                            IS_MIFOUR_LTE_CU = z18;
                                                                                                                                            if (IS_MIFOUR) {
                                                                                                                                            }
                                                                                                                                            z19 = false;
                                                                                                                                            IS_MIFOUR_LTE_CT = z19;
                                                                                                                                            if (IS_MIFOUR) {
                                                                                                                                            }
                                                                                                                                            z20 = false;
                                                                                                                                            IS_MIFOUR_LTE_INDIA = z20;
                                                                                                                                            if (IS_MIFOUR) {
                                                                                                                                            }
                                                                                                                                            z21 = false;
                                                                                                                                            IS_MIFOUR_LTE_SEASA = z21;
                                                                                                                                            IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                                                                                                                            str3 = "ro.carrier.name";
                                                                                                                                            IS_CU_CUSTOMIZATION = str6.equals(SystemProperties.get(str3));
                                                                                                                                            String str922222 = "ro.miui.cust_variant";
                                                                                                                                            if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                                            }
                                                                                                                                            z22 = false;
                                                                                                                                            IS_CM_CUSTOMIZATION = z22;
                                                                                                                                            if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                                            }
                                                                                                                                            z23 = false;
                                                                                                                                            IS_CM_COOPERATION = z23;
                                                                                                                                            IS_CT_CUSTOMIZATION = str7.equals(SystemProperties.get(str3));
                                                                                                                                            String str102222222 = "ro.product.mod_device";
                                                                                                                                            String str112222222 = "";
                                                                                                                                            IS_ALPHA_BUILD = SystemProperties.get(str102222222, str112222222).endsWith("_alpha");
                                                                                                                                            String str122222222 = "1";
                                                                                                                                            IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str122222222.equals(SystemProperties.get("ro.miui.cts")));
                                                                                                                                            IS_CTA_BUILD = str122222222.equals(SystemProperties.get("ro.miui.cta"));
                                                                                                                                            String str132222222 = "ro.cust.test";
                                                                                                                                            IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str132222222));
                                                                                                                                            IS_CU_CUSTOMIZATION_TEST = str6.equals(SystemProperties.get(str132222222));
                                                                                                                                            IS_CT_CUSTOMIZATION_TEST = str7.equals(SystemProperties.get(str132222222));
                                                                                                                                            IS_FUNCTION_LIMITED = str122222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                                                                                                            IS_INTERNATIONAL_BUILD = SystemProperties.get(str102222222, str112222222).contains("_global");
                                                                                                                                            IS_GLOBAL_BUILD = SystemProperties.get(str102222222, str112222222).endsWith("_global");
                                                                                                                                            IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str112222222).endsWith("_pro");
                                                                                                                                            IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str112222222).isEmpty();
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                    z16 = false;
                                                                                                                                    IS_MITHREE_TDSCDMA = z16;
                                                                                                                                    if (IS_MIFOUR) {
                                                                                                                                    }
                                                                                                                                    z17 = false;
                                                                                                                                    IS_MIFOUR_LTE_CM = z17;
                                                                                                                                    if (IS_MIFOUR) {
                                                                                                                                    }
                                                                                                                                    z18 = false;
                                                                                                                                    IS_MIFOUR_LTE_CU = z18;
                                                                                                                                    if (IS_MIFOUR) {
                                                                                                                                    }
                                                                                                                                    z19 = false;
                                                                                                                                    IS_MIFOUR_LTE_CT = z19;
                                                                                                                                    if (IS_MIFOUR) {
                                                                                                                                    }
                                                                                                                                    z20 = false;
                                                                                                                                    IS_MIFOUR_LTE_INDIA = z20;
                                                                                                                                    if (IS_MIFOUR) {
                                                                                                                                    }
                                                                                                                                    z21 = false;
                                                                                                                                    IS_MIFOUR_LTE_SEASA = z21;
                                                                                                                                    IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                                                                                                                    str3 = "ro.carrier.name";
                                                                                                                                    IS_CU_CUSTOMIZATION = str6.equals(SystemProperties.get(str3));
                                                                                                                                    String str9222222 = "ro.miui.cust_variant";
                                                                                                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                                    }
                                                                                                                                    z22 = false;
                                                                                                                                    IS_CM_CUSTOMIZATION = z22;
                                                                                                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                                    }
                                                                                                                                    z23 = false;
                                                                                                                                    IS_CM_COOPERATION = z23;
                                                                                                                                    IS_CT_CUSTOMIZATION = str7.equals(SystemProperties.get(str3));
                                                                                                                                    String str1022222222 = "ro.product.mod_device";
                                                                                                                                    String str1122222222 = "";
                                                                                                                                    IS_ALPHA_BUILD = SystemProperties.get(str1022222222, str1122222222).endsWith("_alpha");
                                                                                                                                    String str1222222222 = "1";
                                                                                                                                    IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str1222222222.equals(SystemProperties.get("ro.miui.cts")));
                                                                                                                                    IS_CTA_BUILD = str1222222222.equals(SystemProperties.get("ro.miui.cta"));
                                                                                                                                    String str1322222222 = "ro.cust.test";
                                                                                                                                    IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str1322222222));
                                                                                                                                    IS_CU_CUSTOMIZATION_TEST = str6.equals(SystemProperties.get(str1322222222));
                                                                                                                                    IS_CT_CUSTOMIZATION_TEST = str7.equals(SystemProperties.get(str1322222222));
                                                                                                                                    IS_FUNCTION_LIMITED = str1222222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                                                                                                    IS_INTERNATIONAL_BUILD = SystemProperties.get(str1022222222, str1122222222).contains("_global");
                                                                                                                                    IS_GLOBAL_BUILD = SystemProperties.get(str1022222222, str1122222222).endsWith("_global");
                                                                                                                                    IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str1122222222).endsWith("_pro");
                                                                                                                                    IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str1122222222).isEmpty();
                                                                                                                                }
                                                                                                                            }
                                                                                                                            z15 = false;
                                                                                                                            IS_MITWO_TDSCDMA = z15;
                                                                                                                            if (IS_MITHREE) {
                                                                                                                            }
                                                                                                                            z16 = false;
                                                                                                                            IS_MITHREE_TDSCDMA = z16;
                                                                                                                            if (IS_MIFOUR) {
                                                                                                                            }
                                                                                                                            z17 = false;
                                                                                                                            IS_MIFOUR_LTE_CM = z17;
                                                                                                                            if (IS_MIFOUR) {
                                                                                                                            }
                                                                                                                            z18 = false;
                                                                                                                            IS_MIFOUR_LTE_CU = z18;
                                                                                                                            if (IS_MIFOUR) {
                                                                                                                            }
                                                                                                                            z19 = false;
                                                                                                                            IS_MIFOUR_LTE_CT = z19;
                                                                                                                            if (IS_MIFOUR) {
                                                                                                                            }
                                                                                                                            z20 = false;
                                                                                                                            IS_MIFOUR_LTE_INDIA = z20;
                                                                                                                            if (IS_MIFOUR) {
                                                                                                                            }
                                                                                                                            z21 = false;
                                                                                                                            IS_MIFOUR_LTE_SEASA = z21;
                                                                                                                            IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                                                                                                            str3 = "ro.carrier.name";
                                                                                                                            IS_CU_CUSTOMIZATION = str6.equals(SystemProperties.get(str3));
                                                                                                                            String str92222222 = "ro.miui.cust_variant";
                                                                                                                            if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                            }
                                                                                                                            z22 = false;
                                                                                                                            IS_CM_CUSTOMIZATION = z22;
                                                                                                                            if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                            }
                                                                                                                            z23 = false;
                                                                                                                            IS_CM_COOPERATION = z23;
                                                                                                                            IS_CT_CUSTOMIZATION = str7.equals(SystemProperties.get(str3));
                                                                                                                            String str10222222222 = "ro.product.mod_device";
                                                                                                                            String str11222222222 = "";
                                                                                                                            IS_ALPHA_BUILD = SystemProperties.get(str10222222222, str11222222222).endsWith("_alpha");
                                                                                                                            String str12222222222 = "1";
                                                                                                                            IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str12222222222.equals(SystemProperties.get("ro.miui.cts")));
                                                                                                                            IS_CTA_BUILD = str12222222222.equals(SystemProperties.get("ro.miui.cta"));
                                                                                                                            String str13222222222 = "ro.cust.test";
                                                                                                                            IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str13222222222));
                                                                                                                            IS_CU_CUSTOMIZATION_TEST = str6.equals(SystemProperties.get(str13222222222));
                                                                                                                            IS_CT_CUSTOMIZATION_TEST = str7.equals(SystemProperties.get(str13222222222));
                                                                                                                            IS_FUNCTION_LIMITED = str12222222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                                                                                            IS_INTERNATIONAL_BUILD = SystemProperties.get(str10222222222, str11222222222).contains("_global");
                                                                                                                            IS_GLOBAL_BUILD = SystemProperties.get(str10222222222, str11222222222).endsWith("_global");
                                                                                                                            IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str11222222222).endsWith("_pro");
                                                                                                                            IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str11222222222).isEmpty();
                                                                                                                        }
                                                                                                                    }
                                                                                                                    z14 = false;
                                                                                                                    IS_MIFOUR_CDMA = z14;
                                                                                                                    if (IS_MITWO) {
                                                                                                                    }
                                                                                                                    z15 = false;
                                                                                                                    IS_MITWO_TDSCDMA = z15;
                                                                                                                    if (IS_MITHREE) {
                                                                                                                    }
                                                                                                                    z16 = false;
                                                                                                                    IS_MITHREE_TDSCDMA = z16;
                                                                                                                    if (IS_MIFOUR) {
                                                                                                                    }
                                                                                                                    z17 = false;
                                                                                                                    IS_MIFOUR_LTE_CM = z17;
                                                                                                                    if (IS_MIFOUR) {
                                                                                                                    }
                                                                                                                    z18 = false;
                                                                                                                    IS_MIFOUR_LTE_CU = z18;
                                                                                                                    if (IS_MIFOUR) {
                                                                                                                    }
                                                                                                                    z19 = false;
                                                                                                                    IS_MIFOUR_LTE_CT = z19;
                                                                                                                    if (IS_MIFOUR) {
                                                                                                                    }
                                                                                                                    z20 = false;
                                                                                                                    IS_MIFOUR_LTE_INDIA = z20;
                                                                                                                    if (IS_MIFOUR) {
                                                                                                                    }
                                                                                                                    z21 = false;
                                                                                                                    IS_MIFOUR_LTE_SEASA = z21;
                                                                                                                    IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                                                                                                    str3 = "ro.carrier.name";
                                                                                                                    IS_CU_CUSTOMIZATION = str6.equals(SystemProperties.get(str3));
                                                                                                                    String str922222222 = "ro.miui.cust_variant";
                                                                                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                    }
                                                                                                                    z22 = false;
                                                                                                                    IS_CM_CUSTOMIZATION = z22;
                                                                                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                                                                                    }
                                                                                                                    z23 = false;
                                                                                                                    IS_CM_COOPERATION = z23;
                                                                                                                    IS_CT_CUSTOMIZATION = str7.equals(SystemProperties.get(str3));
                                                                                                                    String str102222222222 = "ro.product.mod_device";
                                                                                                                    String str112222222222 = "";
                                                                                                                    IS_ALPHA_BUILD = SystemProperties.get(str102222222222, str112222222222).endsWith("_alpha");
                                                                                                                    String str122222222222 = "1";
                                                                                                                    IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str122222222222.equals(SystemProperties.get("ro.miui.cts")));
                                                                                                                    IS_CTA_BUILD = str122222222222.equals(SystemProperties.get("ro.miui.cta"));
                                                                                                                    String str132222222222 = "ro.cust.test";
                                                                                                                    IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str132222222222));
                                                                                                                    IS_CU_CUSTOMIZATION_TEST = str6.equals(SystemProperties.get(str132222222222));
                                                                                                                    IS_CT_CUSTOMIZATION_TEST = str7.equals(SystemProperties.get(str132222222222));
                                                                                                                    IS_FUNCTION_LIMITED = str122222222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                                                                                    IS_INTERNATIONAL_BUILD = SystemProperties.get(str102222222222, str112222222222).contains("_global");
                                                                                                                    IS_GLOBAL_BUILD = SystemProperties.get(str102222222222, str112222222222).endsWith("_global");
                                                                                                                    IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str112222222222).endsWith("_pro");
                                                                                                                    IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str112222222222).isEmpty();
                                                                                                                }
                                                                                                            }
                                                                                                            z13 = false;
                                                                                                            IS_MITHREE_CDMA = z13;
                                                                                                            if (IS_MIFOUR) {
                                                                                                            }
                                                                                                            z14 = false;
                                                                                                            IS_MIFOUR_CDMA = z14;
                                                                                                            if (IS_MITWO) {
                                                                                                            }
                                                                                                            z15 = false;
                                                                                                            IS_MITWO_TDSCDMA = z15;
                                                                                                            if (IS_MITHREE) {
                                                                                                            }
                                                                                                            z16 = false;
                                                                                                            IS_MITHREE_TDSCDMA = z16;
                                                                                                            if (IS_MIFOUR) {
                                                                                                            }
                                                                                                            z17 = false;
                                                                                                            IS_MIFOUR_LTE_CM = z17;
                                                                                                            if (IS_MIFOUR) {
                                                                                                            }
                                                                                                            z18 = false;
                                                                                                            IS_MIFOUR_LTE_CU = z18;
                                                                                                            if (IS_MIFOUR) {
                                                                                                            }
                                                                                                            z19 = false;
                                                                                                            IS_MIFOUR_LTE_CT = z19;
                                                                                                            if (IS_MIFOUR) {
                                                                                                            }
                                                                                                            z20 = false;
                                                                                                            IS_MIFOUR_LTE_INDIA = z20;
                                                                                                            if (IS_MIFOUR) {
                                                                                                            }
                                                                                                            z21 = false;
                                                                                                            IS_MIFOUR_LTE_SEASA = z21;
                                                                                                            IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                                                                                            str3 = "ro.carrier.name";
                                                                                                            IS_CU_CUSTOMIZATION = str6.equals(SystemProperties.get(str3));
                                                                                                            String str9222222222 = "ro.miui.cust_variant";
                                                                                                            if (str2.equals(SystemProperties.get(str3))) {
                                                                                                            }
                                                                                                            z22 = false;
                                                                                                            IS_CM_CUSTOMIZATION = z22;
                                                                                                            if (str2.equals(SystemProperties.get(str3))) {
                                                                                                            }
                                                                                                            z23 = false;
                                                                                                            IS_CM_COOPERATION = z23;
                                                                                                            IS_CT_CUSTOMIZATION = str7.equals(SystemProperties.get(str3));
                                                                                                            String str1022222222222 = "ro.product.mod_device";
                                                                                                            String str1122222222222 = "";
                                                                                                            IS_ALPHA_BUILD = SystemProperties.get(str1022222222222, str1122222222222).endsWith("_alpha");
                                                                                                            String str1222222222222 = "1";
                                                                                                            IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str1222222222222.equals(SystemProperties.get("ro.miui.cts")));
                                                                                                            IS_CTA_BUILD = str1222222222222.equals(SystemProperties.get("ro.miui.cta"));
                                                                                                            String str1322222222222 = "ro.cust.test";
                                                                                                            IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str1322222222222));
                                                                                                            IS_CU_CUSTOMIZATION_TEST = str6.equals(SystemProperties.get(str1322222222222));
                                                                                                            IS_CT_CUSTOMIZATION_TEST = str7.equals(SystemProperties.get(str1322222222222));
                                                                                                            IS_FUNCTION_LIMITED = str1222222222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                                                                            IS_INTERNATIONAL_BUILD = SystemProperties.get(str1022222222222, str1122222222222).contains("_global");
                                                                                                            IS_GLOBAL_BUILD = SystemProperties.get(str1022222222222, str1122222222222).endsWith("_global");
                                                                                                            IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str1122222222222).endsWith("_pro");
                                                                                                            IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str1122222222222).isEmpty();
                                                                                                        }
                                                                                                    }
                                                                                                    z12 = false;
                                                                                                    IS_MITWO_CDMA = z12;
                                                                                                    if (IS_MITHREE) {
                                                                                                    }
                                                                                                    z13 = false;
                                                                                                    IS_MITHREE_CDMA = z13;
                                                                                                    if (IS_MIFOUR) {
                                                                                                    }
                                                                                                    z14 = false;
                                                                                                    IS_MIFOUR_CDMA = z14;
                                                                                                    if (IS_MITWO) {
                                                                                                    }
                                                                                                    z15 = false;
                                                                                                    IS_MITWO_TDSCDMA = z15;
                                                                                                    if (IS_MITHREE) {
                                                                                                    }
                                                                                                    z16 = false;
                                                                                                    IS_MITHREE_TDSCDMA = z16;
                                                                                                    if (IS_MIFOUR) {
                                                                                                    }
                                                                                                    z17 = false;
                                                                                                    IS_MIFOUR_LTE_CM = z17;
                                                                                                    if (IS_MIFOUR) {
                                                                                                    }
                                                                                                    z18 = false;
                                                                                                    IS_MIFOUR_LTE_CU = z18;
                                                                                                    if (IS_MIFOUR) {
                                                                                                    }
                                                                                                    z19 = false;
                                                                                                    IS_MIFOUR_LTE_CT = z19;
                                                                                                    if (IS_MIFOUR) {
                                                                                                    }
                                                                                                    z20 = false;
                                                                                                    IS_MIFOUR_LTE_INDIA = z20;
                                                                                                    if (IS_MIFOUR) {
                                                                                                    }
                                                                                                    z21 = false;
                                                                                                    IS_MIFOUR_LTE_SEASA = z21;
                                                                                                    IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                                                                                    str3 = "ro.carrier.name";
                                                                                                    IS_CU_CUSTOMIZATION = str6.equals(SystemProperties.get(str3));
                                                                                                    String str92222222222 = "ro.miui.cust_variant";
                                                                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                                                                    }
                                                                                                    z22 = false;
                                                                                                    IS_CM_CUSTOMIZATION = z22;
                                                                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                                                                    }
                                                                                                    z23 = false;
                                                                                                    IS_CM_COOPERATION = z23;
                                                                                                    IS_CT_CUSTOMIZATION = str7.equals(SystemProperties.get(str3));
                                                                                                    String str10222222222222 = "ro.product.mod_device";
                                                                                                    String str11222222222222 = "";
                                                                                                    IS_ALPHA_BUILD = SystemProperties.get(str10222222222222, str11222222222222).endsWith("_alpha");
                                                                                                    String str12222222222222 = "1";
                                                                                                    IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str12222222222222.equals(SystemProperties.get("ro.miui.cts")));
                                                                                                    IS_CTA_BUILD = str12222222222222.equals(SystemProperties.get("ro.miui.cta"));
                                                                                                    String str13222222222222 = "ro.cust.test";
                                                                                                    IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str13222222222222));
                                                                                                    IS_CU_CUSTOMIZATION_TEST = str6.equals(SystemProperties.get(str13222222222222));
                                                                                                    IS_CT_CUSTOMIZATION_TEST = str7.equals(SystemProperties.get(str13222222222222));
                                                                                                    IS_FUNCTION_LIMITED = str12222222222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                                                                    IS_INTERNATIONAL_BUILD = SystemProperties.get(str10222222222222, str11222222222222).contains("_global");
                                                                                                    IS_GLOBAL_BUILD = SystemProperties.get(str10222222222222, str11222222222222).endsWith("_global");
                                                                                                    IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str11222222222222).endsWith("_pro");
                                                                                                    IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str11222222222222).isEmpty();
                                                                                                }
                                                                                            }
                                                                                            z11 = true;
                                                                                            IS_HONGMI_TWOX_CM = z11;
                                                                                            String str52 = "persist.sys.modem";
                                                                                            str2 = "cm";
                                                                                            IS_HONGMI_THREEX_CM = !IS_HONGMI_THREEX && str2.equals(SystemProperties.get(str52));
                                                                                            String str62 = "cu";
                                                                                            IS_HONGMI_THREEX_CU = !IS_HONGMI_THREEX && str62.equals(SystemProperties.get(str52));
                                                                                            String str72 = "ct";
                                                                                            IS_HONGMI_THREEX_CT = !IS_HONGMI_THREEX && str72.equals(SystemProperties.get(str52));
                                                                                            String str82 = "persist.radio.modem";
                                                                                            if (IS_MITWO) {
                                                                                            }
                                                                                            z12 = false;
                                                                                            IS_MITWO_CDMA = z12;
                                                                                            if (IS_MITHREE) {
                                                                                            }
                                                                                            z13 = false;
                                                                                            IS_MITHREE_CDMA = z13;
                                                                                            if (IS_MIFOUR) {
                                                                                            }
                                                                                            z14 = false;
                                                                                            IS_MIFOUR_CDMA = z14;
                                                                                            if (IS_MITWO) {
                                                                                            }
                                                                                            z15 = false;
                                                                                            IS_MITWO_TDSCDMA = z15;
                                                                                            if (IS_MITHREE) {
                                                                                            }
                                                                                            z16 = false;
                                                                                            IS_MITHREE_TDSCDMA = z16;
                                                                                            if (IS_MIFOUR) {
                                                                                            }
                                                                                            z17 = false;
                                                                                            IS_MIFOUR_LTE_CM = z17;
                                                                                            if (IS_MIFOUR) {
                                                                                            }
                                                                                            z18 = false;
                                                                                            IS_MIFOUR_LTE_CU = z18;
                                                                                            if (IS_MIFOUR) {
                                                                                            }
                                                                                            z19 = false;
                                                                                            IS_MIFOUR_LTE_CT = z19;
                                                                                            if (IS_MIFOUR) {
                                                                                            }
                                                                                            z20 = false;
                                                                                            IS_MIFOUR_LTE_INDIA = z20;
                                                                                            if (IS_MIFOUR) {
                                                                                            }
                                                                                            z21 = false;
                                                                                            IS_MIFOUR_LTE_SEASA = z21;
                                                                                            IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                                                                            str3 = "ro.carrier.name";
                                                                                            IS_CU_CUSTOMIZATION = str62.equals(SystemProperties.get(str3));
                                                                                            String str922222222222 = "ro.miui.cust_variant";
                                                                                            if (str2.equals(SystemProperties.get(str3))) {
                                                                                            }
                                                                                            z22 = false;
                                                                                            IS_CM_CUSTOMIZATION = z22;
                                                                                            if (str2.equals(SystemProperties.get(str3))) {
                                                                                            }
                                                                                            z23 = false;
                                                                                            IS_CM_COOPERATION = z23;
                                                                                            IS_CT_CUSTOMIZATION = str72.equals(SystemProperties.get(str3));
                                                                                            String str102222222222222 = "ro.product.mod_device";
                                                                                            String str112222222222222 = "";
                                                                                            IS_ALPHA_BUILD = SystemProperties.get(str102222222222222, str112222222222222).endsWith("_alpha");
                                                                                            String str122222222222222 = "1";
                                                                                            IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str122222222222222.equals(SystemProperties.get("ro.miui.cts")));
                                                                                            IS_CTA_BUILD = str122222222222222.equals(SystemProperties.get("ro.miui.cta"));
                                                                                            String str132222222222222 = "ro.cust.test";
                                                                                            IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str132222222222222));
                                                                                            IS_CU_CUSTOMIZATION_TEST = str62.equals(SystemProperties.get(str132222222222222));
                                                                                            IS_CT_CUSTOMIZATION_TEST = str72.equals(SystemProperties.get(str132222222222222));
                                                                                            IS_FUNCTION_LIMITED = str122222222222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                                                            IS_INTERNATIONAL_BUILD = SystemProperties.get(str102222222222222, str112222222222222).contains("_global");
                                                                                            IS_GLOBAL_BUILD = SystemProperties.get(str102222222222222, str112222222222222).endsWith("_global");
                                                                                            IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str112222222222222).endsWith("_pro");
                                                                                            IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str112222222222222).isEmpty();
                                                                                        }
                                                                                    }
                                                                                    z10 = true;
                                                                                    IS_HONGMI_TWOX_CT = z10;
                                                                                    if (!"HM2014813".equals(DEVICE)) {
                                                                                    }
                                                                                    z11 = true;
                                                                                    IS_HONGMI_TWOX_CM = z11;
                                                                                    String str522 = "persist.sys.modem";
                                                                                    str2 = "cm";
                                                                                    IS_HONGMI_THREEX_CM = !IS_HONGMI_THREEX && str2.equals(SystemProperties.get(str522));
                                                                                    String str622 = "cu";
                                                                                    IS_HONGMI_THREEX_CU = !IS_HONGMI_THREEX && str622.equals(SystemProperties.get(str522));
                                                                                    String str722 = "ct";
                                                                                    IS_HONGMI_THREEX_CT = !IS_HONGMI_THREEX && str722.equals(SystemProperties.get(str522));
                                                                                    String str822 = "persist.radio.modem";
                                                                                    if (IS_MITWO) {
                                                                                    }
                                                                                    z12 = false;
                                                                                    IS_MITWO_CDMA = z12;
                                                                                    if (IS_MITHREE) {
                                                                                    }
                                                                                    z13 = false;
                                                                                    IS_MITHREE_CDMA = z13;
                                                                                    if (IS_MIFOUR) {
                                                                                    }
                                                                                    z14 = false;
                                                                                    IS_MIFOUR_CDMA = z14;
                                                                                    if (IS_MITWO) {
                                                                                    }
                                                                                    z15 = false;
                                                                                    IS_MITWO_TDSCDMA = z15;
                                                                                    if (IS_MITHREE) {
                                                                                    }
                                                                                    z16 = false;
                                                                                    IS_MITHREE_TDSCDMA = z16;
                                                                                    if (IS_MIFOUR) {
                                                                                    }
                                                                                    z17 = false;
                                                                                    IS_MIFOUR_LTE_CM = z17;
                                                                                    if (IS_MIFOUR) {
                                                                                    }
                                                                                    z18 = false;
                                                                                    IS_MIFOUR_LTE_CU = z18;
                                                                                    if (IS_MIFOUR) {
                                                                                    }
                                                                                    z19 = false;
                                                                                    IS_MIFOUR_LTE_CT = z19;
                                                                                    if (IS_MIFOUR) {
                                                                                    }
                                                                                    z20 = false;
                                                                                    IS_MIFOUR_LTE_INDIA = z20;
                                                                                    if (IS_MIFOUR) {
                                                                                    }
                                                                                    z21 = false;
                                                                                    IS_MIFOUR_LTE_SEASA = z21;
                                                                                    IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                                                                    str3 = "ro.carrier.name";
                                                                                    IS_CU_CUSTOMIZATION = str622.equals(SystemProperties.get(str3));
                                                                                    String str9222222222222 = "ro.miui.cust_variant";
                                                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                                                    }
                                                                                    z22 = false;
                                                                                    IS_CM_CUSTOMIZATION = z22;
                                                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                                                    }
                                                                                    z23 = false;
                                                                                    IS_CM_COOPERATION = z23;
                                                                                    IS_CT_CUSTOMIZATION = str722.equals(SystemProperties.get(str3));
                                                                                    String str1022222222222222 = "ro.product.mod_device";
                                                                                    String str1122222222222222 = "";
                                                                                    IS_ALPHA_BUILD = SystemProperties.get(str1022222222222222, str1122222222222222).endsWith("_alpha");
                                                                                    String str1222222222222222 = "1";
                                                                                    IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str1222222222222222.equals(SystemProperties.get("ro.miui.cts")));
                                                                                    IS_CTA_BUILD = str1222222222222222.equals(SystemProperties.get("ro.miui.cta"));
                                                                                    String str1322222222222222 = "ro.cust.test";
                                                                                    IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str1322222222222222));
                                                                                    IS_CU_CUSTOMIZATION_TEST = str622.equals(SystemProperties.get(str1322222222222222));
                                                                                    IS_CT_CUSTOMIZATION_TEST = str722.equals(SystemProperties.get(str1322222222222222));
                                                                                    IS_FUNCTION_LIMITED = str1222222222222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                                                    IS_INTERNATIONAL_BUILD = SystemProperties.get(str1022222222222222, str1122222222222222).contains("_global");
                                                                                    IS_GLOBAL_BUILD = SystemProperties.get(str1022222222222222, str1122222222222222).endsWith("_global");
                                                                                    IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str1122222222222222).endsWith("_pro");
                                                                                    IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str1122222222222222).isEmpty();
                                                                                }
                                                                            }
                                                                            z9 = false;
                                                                            IS_HONGMI_THREE_LTE_CU = z9;
                                                                            if (!"HM2014812".equals(DEVICE)) {
                                                                            }
                                                                            z10 = true;
                                                                            IS_HONGMI_TWOX_CT = z10;
                                                                            if (!"HM2014813".equals(DEVICE)) {
                                                                            }
                                                                            z11 = true;
                                                                            IS_HONGMI_TWOX_CM = z11;
                                                                            String str5222 = "persist.sys.modem";
                                                                            str2 = "cm";
                                                                            IS_HONGMI_THREEX_CM = !IS_HONGMI_THREEX && str2.equals(SystemProperties.get(str5222));
                                                                            String str6222 = "cu";
                                                                            IS_HONGMI_THREEX_CU = !IS_HONGMI_THREEX && str6222.equals(SystemProperties.get(str5222));
                                                                            String str7222 = "ct";
                                                                            IS_HONGMI_THREEX_CT = !IS_HONGMI_THREEX && str7222.equals(SystemProperties.get(str5222));
                                                                            String str8222 = "persist.radio.modem";
                                                                            if (IS_MITWO) {
                                                                            }
                                                                            z12 = false;
                                                                            IS_MITWO_CDMA = z12;
                                                                            if (IS_MITHREE) {
                                                                            }
                                                                            z13 = false;
                                                                            IS_MITHREE_CDMA = z13;
                                                                            if (IS_MIFOUR) {
                                                                            }
                                                                            z14 = false;
                                                                            IS_MIFOUR_CDMA = z14;
                                                                            if (IS_MITWO) {
                                                                            }
                                                                            z15 = false;
                                                                            IS_MITWO_TDSCDMA = z15;
                                                                            if (IS_MITHREE) {
                                                                            }
                                                                            z16 = false;
                                                                            IS_MITHREE_TDSCDMA = z16;
                                                                            if (IS_MIFOUR) {
                                                                            }
                                                                            z17 = false;
                                                                            IS_MIFOUR_LTE_CM = z17;
                                                                            if (IS_MIFOUR) {
                                                                            }
                                                                            z18 = false;
                                                                            IS_MIFOUR_LTE_CU = z18;
                                                                            if (IS_MIFOUR) {
                                                                            }
                                                                            z19 = false;
                                                                            IS_MIFOUR_LTE_CT = z19;
                                                                            if (IS_MIFOUR) {
                                                                            }
                                                                            z20 = false;
                                                                            IS_MIFOUR_LTE_INDIA = z20;
                                                                            if (IS_MIFOUR) {
                                                                            }
                                                                            z21 = false;
                                                                            IS_MIFOUR_LTE_SEASA = z21;
                                                                            IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                                                            str3 = "ro.carrier.name";
                                                                            IS_CU_CUSTOMIZATION = str6222.equals(SystemProperties.get(str3));
                                                                            String str92222222222222 = "ro.miui.cust_variant";
                                                                            if (str2.equals(SystemProperties.get(str3))) {
                                                                            }
                                                                            z22 = false;
                                                                            IS_CM_CUSTOMIZATION = z22;
                                                                            if (str2.equals(SystemProperties.get(str3))) {
                                                                            }
                                                                            z23 = false;
                                                                            IS_CM_COOPERATION = z23;
                                                                            IS_CT_CUSTOMIZATION = str7222.equals(SystemProperties.get(str3));
                                                                            String str10222222222222222 = "ro.product.mod_device";
                                                                            String str11222222222222222 = "";
                                                                            IS_ALPHA_BUILD = SystemProperties.get(str10222222222222222, str11222222222222222).endsWith("_alpha");
                                                                            String str12222222222222222 = "1";
                                                                            IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str12222222222222222.equals(SystemProperties.get("ro.miui.cts")));
                                                                            IS_CTA_BUILD = str12222222222222222.equals(SystemProperties.get("ro.miui.cta"));
                                                                            String str13222222222222222 = "ro.cust.test";
                                                                            IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str13222222222222222));
                                                                            IS_CU_CUSTOMIZATION_TEST = str6222.equals(SystemProperties.get(str13222222222222222));
                                                                            IS_CT_CUSTOMIZATION_TEST = str7222.equals(SystemProperties.get(str13222222222222222));
                                                                            IS_FUNCTION_LIMITED = str12222222222222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                                            IS_INTERNATIONAL_BUILD = SystemProperties.get(str10222222222222222, str11222222222222222).contains("_global");
                                                                            IS_GLOBAL_BUILD = SystemProperties.get(str10222222222222222, str11222222222222222).endsWith("_global");
                                                                            IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str11222222222222222).endsWith("_pro");
                                                                            IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str11222222222222222).isEmpty();
                                                                        }
                                                                    }
                                                                    z8 = false;
                                                                    IS_HONGMI_THREE_LTE_CM = z8;
                                                                    if (IS_HONGMI_THREE_LTE) {
                                                                    }
                                                                    z9 = false;
                                                                    IS_HONGMI_THREE_LTE_CU = z9;
                                                                    if (!"HM2014812".equals(DEVICE)) {
                                                                    }
                                                                    z10 = true;
                                                                    IS_HONGMI_TWOX_CT = z10;
                                                                    if (!"HM2014813".equals(DEVICE)) {
                                                                    }
                                                                    z11 = true;
                                                                    IS_HONGMI_TWOX_CM = z11;
                                                                    String str52222 = "persist.sys.modem";
                                                                    str2 = "cm";
                                                                    IS_HONGMI_THREEX_CM = !IS_HONGMI_THREEX && str2.equals(SystemProperties.get(str52222));
                                                                    String str62222 = "cu";
                                                                    IS_HONGMI_THREEX_CU = !IS_HONGMI_THREEX && str62222.equals(SystemProperties.get(str52222));
                                                                    String str72222 = "ct";
                                                                    IS_HONGMI_THREEX_CT = !IS_HONGMI_THREEX && str72222.equals(SystemProperties.get(str52222));
                                                                    String str82222 = "persist.radio.modem";
                                                                    if (IS_MITWO) {
                                                                    }
                                                                    z12 = false;
                                                                    IS_MITWO_CDMA = z12;
                                                                    if (IS_MITHREE) {
                                                                    }
                                                                    z13 = false;
                                                                    IS_MITHREE_CDMA = z13;
                                                                    if (IS_MIFOUR) {
                                                                    }
                                                                    z14 = false;
                                                                    IS_MIFOUR_CDMA = z14;
                                                                    if (IS_MITWO) {
                                                                    }
                                                                    z15 = false;
                                                                    IS_MITWO_TDSCDMA = z15;
                                                                    if (IS_MITHREE) {
                                                                    }
                                                                    z16 = false;
                                                                    IS_MITHREE_TDSCDMA = z16;
                                                                    if (IS_MIFOUR) {
                                                                    }
                                                                    z17 = false;
                                                                    IS_MIFOUR_LTE_CM = z17;
                                                                    if (IS_MIFOUR) {
                                                                    }
                                                                    z18 = false;
                                                                    IS_MIFOUR_LTE_CU = z18;
                                                                    if (IS_MIFOUR) {
                                                                    }
                                                                    z19 = false;
                                                                    IS_MIFOUR_LTE_CT = z19;
                                                                    if (IS_MIFOUR) {
                                                                    }
                                                                    z20 = false;
                                                                    IS_MIFOUR_LTE_INDIA = z20;
                                                                    if (IS_MIFOUR) {
                                                                    }
                                                                    z21 = false;
                                                                    IS_MIFOUR_LTE_SEASA = z21;
                                                                    IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                                                    str3 = "ro.carrier.name";
                                                                    IS_CU_CUSTOMIZATION = str62222.equals(SystemProperties.get(str3));
                                                                    String str922222222222222 = "ro.miui.cust_variant";
                                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                                    }
                                                                    z22 = false;
                                                                    IS_CM_CUSTOMIZATION = z22;
                                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                                    }
                                                                    z23 = false;
                                                                    IS_CM_COOPERATION = z23;
                                                                    IS_CT_CUSTOMIZATION = str72222.equals(SystemProperties.get(str3));
                                                                    String str102222222222222222 = "ro.product.mod_device";
                                                                    String str112222222222222222 = "";
                                                                    IS_ALPHA_BUILD = SystemProperties.get(str102222222222222222, str112222222222222222).endsWith("_alpha");
                                                                    String str122222222222222222 = "1";
                                                                    IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str122222222222222222.equals(SystemProperties.get("ro.miui.cts")));
                                                                    IS_CTA_BUILD = str122222222222222222.equals(SystemProperties.get("ro.miui.cta"));
                                                                    String str132222222222222222 = "ro.cust.test";
                                                                    IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str132222222222222222));
                                                                    IS_CU_CUSTOMIZATION_TEST = str62222.equals(SystemProperties.get(str132222222222222222));
                                                                    IS_CT_CUSTOMIZATION_TEST = str72222.equals(SystemProperties.get(str132222222222222222));
                                                                    IS_FUNCTION_LIMITED = str122222222222222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                                    IS_INTERNATIONAL_BUILD = SystemProperties.get(str102222222222222222, str112222222222222222).contains("_global");
                                                                    IS_GLOBAL_BUILD = SystemProperties.get(str102222222222222222, str112222222222222222).endsWith("_global");
                                                                    IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str112222222222222222).endsWith("_pro");
                                                                    IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str112222222222222222).isEmpty();
                                                                }
                                                            }
                                                            z7 = true;
                                                            IS_HONGMI_THREE = z7;
                                                            if (IS_HONGMI_THREE_LTE) {
                                                            }
                                                            z8 = false;
                                                            IS_HONGMI_THREE_LTE_CM = z8;
                                                            if (IS_HONGMI_THREE_LTE) {
                                                            }
                                                            z9 = false;
                                                            IS_HONGMI_THREE_LTE_CU = z9;
                                                            if (!"HM2014812".equals(DEVICE)) {
                                                            }
                                                            z10 = true;
                                                            IS_HONGMI_TWOX_CT = z10;
                                                            if (!"HM2014813".equals(DEVICE)) {
                                                            }
                                                            z11 = true;
                                                            IS_HONGMI_TWOX_CM = z11;
                                                            String str522222 = "persist.sys.modem";
                                                            str2 = "cm";
                                                            IS_HONGMI_THREEX_CM = !IS_HONGMI_THREEX && str2.equals(SystemProperties.get(str522222));
                                                            String str622222 = "cu";
                                                            IS_HONGMI_THREEX_CU = !IS_HONGMI_THREEX && str622222.equals(SystemProperties.get(str522222));
                                                            String str722222 = "ct";
                                                            IS_HONGMI_THREEX_CT = !IS_HONGMI_THREEX && str722222.equals(SystemProperties.get(str522222));
                                                            String str822222 = "persist.radio.modem";
                                                            if (IS_MITWO) {
                                                            }
                                                            z12 = false;
                                                            IS_MITWO_CDMA = z12;
                                                            if (IS_MITHREE) {
                                                            }
                                                            z13 = false;
                                                            IS_MITHREE_CDMA = z13;
                                                            if (IS_MIFOUR) {
                                                            }
                                                            z14 = false;
                                                            IS_MIFOUR_CDMA = z14;
                                                            if (IS_MITWO) {
                                                            }
                                                            z15 = false;
                                                            IS_MITWO_TDSCDMA = z15;
                                                            if (IS_MITHREE) {
                                                            }
                                                            z16 = false;
                                                            IS_MITHREE_TDSCDMA = z16;
                                                            if (IS_MIFOUR) {
                                                            }
                                                            z17 = false;
                                                            IS_MIFOUR_LTE_CM = z17;
                                                            if (IS_MIFOUR) {
                                                            }
                                                            z18 = false;
                                                            IS_MIFOUR_LTE_CU = z18;
                                                            if (IS_MIFOUR) {
                                                            }
                                                            z19 = false;
                                                            IS_MIFOUR_LTE_CT = z19;
                                                            if (IS_MIFOUR) {
                                                            }
                                                            z20 = false;
                                                            IS_MIFOUR_LTE_INDIA = z20;
                                                            if (IS_MIFOUR) {
                                                            }
                                                            z21 = false;
                                                            IS_MIFOUR_LTE_SEASA = z21;
                                                            IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                                            str3 = "ro.carrier.name";
                                                            IS_CU_CUSTOMIZATION = str622222.equals(SystemProperties.get(str3));
                                                            String str9222222222222222 = "ro.miui.cust_variant";
                                                            if (str2.equals(SystemProperties.get(str3))) {
                                                            }
                                                            z22 = false;
                                                            IS_CM_CUSTOMIZATION = z22;
                                                            if (str2.equals(SystemProperties.get(str3))) {
                                                            }
                                                            z23 = false;
                                                            IS_CM_COOPERATION = z23;
                                                            IS_CT_CUSTOMIZATION = str722222.equals(SystemProperties.get(str3));
                                                            String str1022222222222222222 = "ro.product.mod_device";
                                                            String str1122222222222222222 = "";
                                                            IS_ALPHA_BUILD = SystemProperties.get(str1022222222222222222, str1122222222222222222).endsWith("_alpha");
                                                            String str1222222222222222222 = "1";
                                                            IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str1222222222222222222.equals(SystemProperties.get("ro.miui.cts")));
                                                            IS_CTA_BUILD = str1222222222222222222.equals(SystemProperties.get("ro.miui.cta"));
                                                            String str1322222222222222222 = "ro.cust.test";
                                                            IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str1322222222222222222));
                                                            IS_CU_CUSTOMIZATION_TEST = str622222.equals(SystemProperties.get(str1322222222222222222));
                                                            IS_CT_CUSTOMIZATION_TEST = str722222.equals(SystemProperties.get(str1322222222222222222));
                                                            IS_FUNCTION_LIMITED = str1222222222222222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                            IS_INTERNATIONAL_BUILD = SystemProperties.get(str1022222222222222222, str1122222222222222222).contains("_global");
                                                            IS_GLOBAL_BUILD = SystemProperties.get(str1022222222222222222, str1122222222222222222).endsWith("_global");
                                                            IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str1122222222222222222).endsWith("_pro");
                                                            IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str1122222222222222222).isEmpty();
                                                        }
                                                    }
                                                    z6 = true;
                                                    IS_HONGMI_TWO = z6;
                                                    if (!"lcsh92_wet_jb9".equals(DEVICE)) {
                                                    }
                                                    z7 = true;
                                                    IS_HONGMI_THREE = z7;
                                                    if (IS_HONGMI_THREE_LTE) {
                                                    }
                                                    z8 = false;
                                                    IS_HONGMI_THREE_LTE_CM = z8;
                                                    if (IS_HONGMI_THREE_LTE) {
                                                    }
                                                    z9 = false;
                                                    IS_HONGMI_THREE_LTE_CU = z9;
                                                    if (!"HM2014812".equals(DEVICE)) {
                                                    }
                                                    z10 = true;
                                                    IS_HONGMI_TWOX_CT = z10;
                                                    if (!"HM2014813".equals(DEVICE)) {
                                                    }
                                                    z11 = true;
                                                    IS_HONGMI_TWOX_CM = z11;
                                                    String str5222222 = "persist.sys.modem";
                                                    str2 = "cm";
                                                    IS_HONGMI_THREEX_CM = !IS_HONGMI_THREEX && str2.equals(SystemProperties.get(str5222222));
                                                    String str6222222 = "cu";
                                                    IS_HONGMI_THREEX_CU = !IS_HONGMI_THREEX && str6222222.equals(SystemProperties.get(str5222222));
                                                    String str7222222 = "ct";
                                                    IS_HONGMI_THREEX_CT = !IS_HONGMI_THREEX && str7222222.equals(SystemProperties.get(str5222222));
                                                    String str8222222 = "persist.radio.modem";
                                                    if (IS_MITWO) {
                                                    }
                                                    z12 = false;
                                                    IS_MITWO_CDMA = z12;
                                                    if (IS_MITHREE) {
                                                    }
                                                    z13 = false;
                                                    IS_MITHREE_CDMA = z13;
                                                    if (IS_MIFOUR) {
                                                    }
                                                    z14 = false;
                                                    IS_MIFOUR_CDMA = z14;
                                                    if (IS_MITWO) {
                                                    }
                                                    z15 = false;
                                                    IS_MITWO_TDSCDMA = z15;
                                                    if (IS_MITHREE) {
                                                    }
                                                    z16 = false;
                                                    IS_MITHREE_TDSCDMA = z16;
                                                    if (IS_MIFOUR) {
                                                    }
                                                    z17 = false;
                                                    IS_MIFOUR_LTE_CM = z17;
                                                    if (IS_MIFOUR) {
                                                    }
                                                    z18 = false;
                                                    IS_MIFOUR_LTE_CU = z18;
                                                    if (IS_MIFOUR) {
                                                    }
                                                    z19 = false;
                                                    IS_MIFOUR_LTE_CT = z19;
                                                    if (IS_MIFOUR) {
                                                    }
                                                    z20 = false;
                                                    IS_MIFOUR_LTE_INDIA = z20;
                                                    if (IS_MIFOUR) {
                                                    }
                                                    z21 = false;
                                                    IS_MIFOUR_LTE_SEASA = z21;
                                                    IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                                    str3 = "ro.carrier.name";
                                                    IS_CU_CUSTOMIZATION = str6222222.equals(SystemProperties.get(str3));
                                                    String str92222222222222222 = "ro.miui.cust_variant";
                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                    }
                                                    z22 = false;
                                                    IS_CM_CUSTOMIZATION = z22;
                                                    if (str2.equals(SystemProperties.get(str3))) {
                                                    }
                                                    z23 = false;
                                                    IS_CM_COOPERATION = z23;
                                                    IS_CT_CUSTOMIZATION = str7222222.equals(SystemProperties.get(str3));
                                                    String str10222222222222222222 = "ro.product.mod_device";
                                                    String str11222222222222222222 = "";
                                                    IS_ALPHA_BUILD = SystemProperties.get(str10222222222222222222, str11222222222222222222).endsWith("_alpha");
                                                    String str12222222222222222222 = "1";
                                                    IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str12222222222222222222.equals(SystemProperties.get("ro.miui.cts")));
                                                    IS_CTA_BUILD = str12222222222222222222.equals(SystemProperties.get("ro.miui.cta"));
                                                    String str13222222222222222222 = "ro.cust.test";
                                                    IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str13222222222222222222));
                                                    IS_CU_CUSTOMIZATION_TEST = str6222222.equals(SystemProperties.get(str13222222222222222222));
                                                    IS_CT_CUSTOMIZATION_TEST = str7222222.equals(SystemProperties.get(str13222222222222222222));
                                                    IS_FUNCTION_LIMITED = str12222222222222222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                                    IS_INTERNATIONAL_BUILD = SystemProperties.get(str10222222222222222222, str11222222222222222222).contains("_global");
                                                    IS_GLOBAL_BUILD = SystemProperties.get(str10222222222222222222, str11222222222222222222).endsWith("_global");
                                                    IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str11222222222222222222).endsWith("_pro");
                                                    IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str11222222222222222222).isEmpty();
                                                }
                                            }
                                            z5 = true;
                                            IS_HONGMI_TWO_S = z5;
                                            str = "HM2013022";
                                            if (!str.equals(DEVICE)) {
                                            }
                                            z6 = true;
                                            IS_HONGMI_TWO = z6;
                                            if (!"lcsh92_wet_jb9".equals(DEVICE)) {
                                            }
                                            z7 = true;
                                            IS_HONGMI_THREE = z7;
                                            if (IS_HONGMI_THREE_LTE) {
                                            }
                                            z8 = false;
                                            IS_HONGMI_THREE_LTE_CM = z8;
                                            if (IS_HONGMI_THREE_LTE) {
                                            }
                                            z9 = false;
                                            IS_HONGMI_THREE_LTE_CU = z9;
                                            if (!"HM2014812".equals(DEVICE)) {
                                            }
                                            z10 = true;
                                            IS_HONGMI_TWOX_CT = z10;
                                            if (!"HM2014813".equals(DEVICE)) {
                                            }
                                            z11 = true;
                                            IS_HONGMI_TWOX_CM = z11;
                                            String str52222222 = "persist.sys.modem";
                                            str2 = "cm";
                                            IS_HONGMI_THREEX_CM = !IS_HONGMI_THREEX && str2.equals(SystemProperties.get(str52222222));
                                            String str62222222 = "cu";
                                            IS_HONGMI_THREEX_CU = !IS_HONGMI_THREEX && str62222222.equals(SystemProperties.get(str52222222));
                                            String str72222222 = "ct";
                                            IS_HONGMI_THREEX_CT = !IS_HONGMI_THREEX && str72222222.equals(SystemProperties.get(str52222222));
                                            String str82222222 = "persist.radio.modem";
                                            if (IS_MITWO) {
                                            }
                                            z12 = false;
                                            IS_MITWO_CDMA = z12;
                                            if (IS_MITHREE) {
                                            }
                                            z13 = false;
                                            IS_MITHREE_CDMA = z13;
                                            if (IS_MIFOUR) {
                                            }
                                            z14 = false;
                                            IS_MIFOUR_CDMA = z14;
                                            if (IS_MITWO) {
                                            }
                                            z15 = false;
                                            IS_MITWO_TDSCDMA = z15;
                                            if (IS_MITHREE) {
                                            }
                                            z16 = false;
                                            IS_MITHREE_TDSCDMA = z16;
                                            if (IS_MIFOUR) {
                                            }
                                            z17 = false;
                                            IS_MIFOUR_LTE_CM = z17;
                                            if (IS_MIFOUR) {
                                            }
                                            z18 = false;
                                            IS_MIFOUR_LTE_CU = z18;
                                            if (IS_MIFOUR) {
                                            }
                                            z19 = false;
                                            IS_MIFOUR_LTE_CT = z19;
                                            if (IS_MIFOUR) {
                                            }
                                            z20 = false;
                                            IS_MIFOUR_LTE_INDIA = z20;
                                            if (IS_MIFOUR) {
                                            }
                                            z21 = false;
                                            IS_MIFOUR_LTE_SEASA = z21;
                                            IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                            str3 = "ro.carrier.name";
                                            IS_CU_CUSTOMIZATION = str62222222.equals(SystemProperties.get(str3));
                                            String str922222222222222222 = "ro.miui.cust_variant";
                                            if (str2.equals(SystemProperties.get(str3))) {
                                            }
                                            z22 = false;
                                            IS_CM_CUSTOMIZATION = z22;
                                            if (str2.equals(SystemProperties.get(str3))) {
                                            }
                                            z23 = false;
                                            IS_CM_COOPERATION = z23;
                                            IS_CT_CUSTOMIZATION = str72222222.equals(SystemProperties.get(str3));
                                            String str102222222222222222222 = "ro.product.mod_device";
                                            String str112222222222222222222 = "";
                                            IS_ALPHA_BUILD = SystemProperties.get(str102222222222222222222, str112222222222222222222).endsWith("_alpha");
                                            String str122222222222222222222 = "1";
                                            IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str122222222222222222222.equals(SystemProperties.get("ro.miui.cts")));
                                            IS_CTA_BUILD = str122222222222222222222.equals(SystemProperties.get("ro.miui.cta"));
                                            String str132222222222222222222 = "ro.cust.test";
                                            IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str132222222222222222222));
                                            IS_CU_CUSTOMIZATION_TEST = str62222222.equals(SystemProperties.get(str132222222222222222222));
                                            IS_CT_CUSTOMIZATION_TEST = str72222222.equals(SystemProperties.get(str132222222222222222222));
                                            IS_FUNCTION_LIMITED = str122222222222222222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                            IS_INTERNATIONAL_BUILD = SystemProperties.get(str102222222222222222222, str112222222222222222222).contains("_global");
                                            IS_GLOBAL_BUILD = SystemProperties.get(str102222222222222222222, str112222222222222222222).endsWith("_global");
                                            IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str112222222222222222222).endsWith("_pro");
                                            IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str112222222222222222222).isEmpty();
                                        }
                                    }
                                    z4 = true;
                                    IS_MI2A = z4;
                                    String str42 = "cancro";
                                    IS_MITHREE = !"pisces".equals(DEVICE) || (str42.equals(DEVICE) && MODEL.startsWith("MI 3"));
                                    IS_MIFOUR = !str42.equals(DEVICE) && MODEL.startsWith("MI 4");
                                    if (!"HM2014011".equals(DEVICE)) {
                                    }
                                    z5 = true;
                                    IS_HONGMI_TWO_S = z5;
                                    str = "HM2013022";
                                    if (!str.equals(DEVICE)) {
                                    }
                                    z6 = true;
                                    IS_HONGMI_TWO = z6;
                                    if (!"lcsh92_wet_jb9".equals(DEVICE)) {
                                    }
                                    z7 = true;
                                    IS_HONGMI_THREE = z7;
                                    if (IS_HONGMI_THREE_LTE) {
                                    }
                                    z8 = false;
                                    IS_HONGMI_THREE_LTE_CM = z8;
                                    if (IS_HONGMI_THREE_LTE) {
                                    }
                                    z9 = false;
                                    IS_HONGMI_THREE_LTE_CU = z9;
                                    if (!"HM2014812".equals(DEVICE)) {
                                    }
                                    z10 = true;
                                    IS_HONGMI_TWOX_CT = z10;
                                    if (!"HM2014813".equals(DEVICE)) {
                                    }
                                    z11 = true;
                                    IS_HONGMI_TWOX_CM = z11;
                                    String str522222222 = "persist.sys.modem";
                                    str2 = "cm";
                                    IS_HONGMI_THREEX_CM = !IS_HONGMI_THREEX && str2.equals(SystemProperties.get(str522222222));
                                    String str622222222 = "cu";
                                    IS_HONGMI_THREEX_CU = !IS_HONGMI_THREEX && str622222222.equals(SystemProperties.get(str522222222));
                                    String str722222222 = "ct";
                                    IS_HONGMI_THREEX_CT = !IS_HONGMI_THREEX && str722222222.equals(SystemProperties.get(str522222222));
                                    String str822222222 = "persist.radio.modem";
                                    if (IS_MITWO) {
                                    }
                                    z12 = false;
                                    IS_MITWO_CDMA = z12;
                                    if (IS_MITHREE) {
                                    }
                                    z13 = false;
                                    IS_MITHREE_CDMA = z13;
                                    if (IS_MIFOUR) {
                                    }
                                    z14 = false;
                                    IS_MIFOUR_CDMA = z14;
                                    if (IS_MITWO) {
                                    }
                                    z15 = false;
                                    IS_MITWO_TDSCDMA = z15;
                                    if (IS_MITHREE) {
                                    }
                                    z16 = false;
                                    IS_MITHREE_TDSCDMA = z16;
                                    if (IS_MIFOUR) {
                                    }
                                    z17 = false;
                                    IS_MIFOUR_LTE_CM = z17;
                                    if (IS_MIFOUR) {
                                    }
                                    z18 = false;
                                    IS_MIFOUR_LTE_CU = z18;
                                    if (IS_MIFOUR) {
                                    }
                                    z19 = false;
                                    IS_MIFOUR_LTE_CT = z19;
                                    if (IS_MIFOUR) {
                                    }
                                    z20 = false;
                                    IS_MIFOUR_LTE_INDIA = z20;
                                    if (IS_MIFOUR) {
                                    }
                                    z21 = false;
                                    IS_MIFOUR_LTE_SEASA = z21;
                                    IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                                    str3 = "ro.carrier.name";
                                    IS_CU_CUSTOMIZATION = str622222222.equals(SystemProperties.get(str3));
                                    String str9222222222222222222 = "ro.miui.cust_variant";
                                    if (str2.equals(SystemProperties.get(str3))) {
                                    }
                                    z22 = false;
                                    IS_CM_CUSTOMIZATION = z22;
                                    if (str2.equals(SystemProperties.get(str3))) {
                                    }
                                    z23 = false;
                                    IS_CM_COOPERATION = z23;
                                    IS_CT_CUSTOMIZATION = str722222222.equals(SystemProperties.get(str3));
                                    String str1022222222222222222222 = "ro.product.mod_device";
                                    String str1122222222222222222222 = "";
                                    IS_ALPHA_BUILD = SystemProperties.get(str1022222222222222222222, str1122222222222222222222).endsWith("_alpha");
                                    String str1222222222222222222222 = "1";
                                    IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str1222222222222222222222.equals(SystemProperties.get("ro.miui.cts")));
                                    IS_CTA_BUILD = str1222222222222222222222.equals(SystemProperties.get("ro.miui.cta"));
                                    String str1322222222222222222222 = "ro.cust.test";
                                    IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str1322222222222222222222));
                                    IS_CU_CUSTOMIZATION_TEST = str622222222.equals(SystemProperties.get(str1322222222222222222222));
                                    IS_CT_CUSTOMIZATION_TEST = str722222222.equals(SystemProperties.get(str1322222222222222222222));
                                    IS_FUNCTION_LIMITED = str1222222222222222222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                                    IS_INTERNATIONAL_BUILD = SystemProperties.get(str1022222222222222222222, str1122222222222222222222).contains("_global");
                                    IS_GLOBAL_BUILD = SystemProperties.get(str1022222222222222222222, str1122222222222222222222).endsWith("_global");
                                    IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str1122222222222222222222).endsWith("_pro");
                                    IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str1122222222222222222222).isEmpty();
                                }
                            }
                        }
                        z3 = true;
                        IS_MITWO = z3;
                        if (!"MI 2A".equals(MODEL)) {
                        }
                        z4 = true;
                        IS_MI2A = z4;
                        String str422 = "cancro";
                        IS_MITHREE = !"pisces".equals(DEVICE) || (str422.equals(DEVICE) && MODEL.startsWith("MI 3"));
                        IS_MIFOUR = !str422.equals(DEVICE) && MODEL.startsWith("MI 4");
                        if (!"HM2014011".equals(DEVICE)) {
                        }
                        z5 = true;
                        IS_HONGMI_TWO_S = z5;
                        str = "HM2013022";
                        if (!str.equals(DEVICE)) {
                        }
                        z6 = true;
                        IS_HONGMI_TWO = z6;
                        if (!"lcsh92_wet_jb9".equals(DEVICE)) {
                        }
                        z7 = true;
                        IS_HONGMI_THREE = z7;
                        if (IS_HONGMI_THREE_LTE) {
                        }
                        z8 = false;
                        IS_HONGMI_THREE_LTE_CM = z8;
                        if (IS_HONGMI_THREE_LTE) {
                        }
                        z9 = false;
                        IS_HONGMI_THREE_LTE_CU = z9;
                        if (!"HM2014812".equals(DEVICE)) {
                        }
                        z10 = true;
                        IS_HONGMI_TWOX_CT = z10;
                        if (!"HM2014813".equals(DEVICE)) {
                        }
                        z11 = true;
                        IS_HONGMI_TWOX_CM = z11;
                        String str5222222222 = "persist.sys.modem";
                        str2 = "cm";
                        IS_HONGMI_THREEX_CM = !IS_HONGMI_THREEX && str2.equals(SystemProperties.get(str5222222222));
                        String str6222222222 = "cu";
                        IS_HONGMI_THREEX_CU = !IS_HONGMI_THREEX && str6222222222.equals(SystemProperties.get(str5222222222));
                        String str7222222222 = "ct";
                        IS_HONGMI_THREEX_CT = !IS_HONGMI_THREEX && str7222222222.equals(SystemProperties.get(str5222222222));
                        String str8222222222 = "persist.radio.modem";
                        if (IS_MITWO) {
                        }
                        z12 = false;
                        IS_MITWO_CDMA = z12;
                        if (IS_MITHREE) {
                        }
                        z13 = false;
                        IS_MITHREE_CDMA = z13;
                        if (IS_MIFOUR) {
                        }
                        z14 = false;
                        IS_MIFOUR_CDMA = z14;
                        if (IS_MITWO) {
                        }
                        z15 = false;
                        IS_MITWO_TDSCDMA = z15;
                        if (IS_MITHREE) {
                        }
                        z16 = false;
                        IS_MITHREE_TDSCDMA = z16;
                        if (IS_MIFOUR) {
                        }
                        z17 = false;
                        IS_MIFOUR_LTE_CM = z17;
                        if (IS_MIFOUR) {
                        }
                        z18 = false;
                        IS_MIFOUR_LTE_CU = z18;
                        if (IS_MIFOUR) {
                        }
                        z19 = false;
                        IS_MIFOUR_LTE_CT = z19;
                        if (IS_MIFOUR) {
                        }
                        z20 = false;
                        IS_MIFOUR_LTE_INDIA = z20;
                        if (IS_MIFOUR) {
                        }
                        z21 = false;
                        IS_MIFOUR_LTE_SEASA = z21;
                        IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                        str3 = "ro.carrier.name";
                        IS_CU_CUSTOMIZATION = str6222222222.equals(SystemProperties.get(str3));
                        String str92222222222222222222 = "ro.miui.cust_variant";
                        if (str2.equals(SystemProperties.get(str3))) {
                        }
                        z22 = false;
                        IS_CM_CUSTOMIZATION = z22;
                        if (str2.equals(SystemProperties.get(str3))) {
                        }
                        z23 = false;
                        IS_CM_COOPERATION = z23;
                        IS_CT_CUSTOMIZATION = str7222222222.equals(SystemProperties.get(str3));
                        String str10222222222222222222222 = "ro.product.mod_device";
                        String str11222222222222222222222 = "";
                        IS_ALPHA_BUILD = SystemProperties.get(str10222222222222222222222, str11222222222222222222222).endsWith("_alpha");
                        String str12222222222222222222222 = "1";
                        IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str12222222222222222222222.equals(SystemProperties.get("ro.miui.cts")));
                        IS_CTA_BUILD = str12222222222222222222222.equals(SystemProperties.get("ro.miui.cta"));
                        String str13222222222222222222222 = "ro.cust.test";
                        IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str13222222222222222222222));
                        IS_CU_CUSTOMIZATION_TEST = str6222222222.equals(SystemProperties.get(str13222222222222222222222));
                        IS_CT_CUSTOMIZATION_TEST = str7222222222.equals(SystemProperties.get(str13222222222222222222222));
                        IS_FUNCTION_LIMITED = str12222222222222222222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                        IS_INTERNATIONAL_BUILD = SystemProperties.get(str10222222222222222222222, str11222222222222222222222).contains("_global");
                        IS_GLOBAL_BUILD = SystemProperties.get(str10222222222222222222222, str11222222222222222222222).endsWith("_global");
                        IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str11222222222222222222222).endsWith("_pro");
                        IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str11222222222222222222222).isEmpty();
                    }
                }
                z2 = true;
                IS_MI1S = z2;
                if (!"aries".equals(DEVICE)) {
                }
                z3 = true;
                IS_MITWO = z3;
                if (!"MI 2A".equals(MODEL)) {
                }
                z4 = true;
                IS_MI2A = z4;
                String str4222 = "cancro";
                IS_MITHREE = !"pisces".equals(DEVICE) || (str4222.equals(DEVICE) && MODEL.startsWith("MI 3"));
                IS_MIFOUR = !str4222.equals(DEVICE) && MODEL.startsWith("MI 4");
                if (!"HM2014011".equals(DEVICE)) {
                }
                z5 = true;
                IS_HONGMI_TWO_S = z5;
                str = "HM2013022";
                if (!str.equals(DEVICE)) {
                }
                z6 = true;
                IS_HONGMI_TWO = z6;
                if (!"lcsh92_wet_jb9".equals(DEVICE)) {
                }
                z7 = true;
                IS_HONGMI_THREE = z7;
                if (IS_HONGMI_THREE_LTE) {
                }
                z8 = false;
                IS_HONGMI_THREE_LTE_CM = z8;
                if (IS_HONGMI_THREE_LTE) {
                }
                z9 = false;
                IS_HONGMI_THREE_LTE_CU = z9;
                if (!"HM2014812".equals(DEVICE)) {
                }
                z10 = true;
                IS_HONGMI_TWOX_CT = z10;
                if (!"HM2014813".equals(DEVICE)) {
                }
                z11 = true;
                IS_HONGMI_TWOX_CM = z11;
                String str52222222222 = "persist.sys.modem";
                str2 = "cm";
                IS_HONGMI_THREEX_CM = !IS_HONGMI_THREEX && str2.equals(SystemProperties.get(str52222222222));
                String str62222222222 = "cu";
                IS_HONGMI_THREEX_CU = !IS_HONGMI_THREEX && str62222222222.equals(SystemProperties.get(str52222222222));
                String str72222222222 = "ct";
                IS_HONGMI_THREEX_CT = !IS_HONGMI_THREEX && str72222222222.equals(SystemProperties.get(str52222222222));
                String str82222222222 = "persist.radio.modem";
                if (IS_MITWO) {
                }
                z12 = false;
                IS_MITWO_CDMA = z12;
                if (IS_MITHREE) {
                }
                z13 = false;
                IS_MITHREE_CDMA = z13;
                if (IS_MIFOUR) {
                }
                z14 = false;
                IS_MIFOUR_CDMA = z14;
                if (IS_MITWO) {
                }
                z15 = false;
                IS_MITWO_TDSCDMA = z15;
                if (IS_MITHREE) {
                }
                z16 = false;
                IS_MITHREE_TDSCDMA = z16;
                if (IS_MIFOUR) {
                }
                z17 = false;
                IS_MIFOUR_LTE_CM = z17;
                if (IS_MIFOUR) {
                }
                z18 = false;
                IS_MIFOUR_LTE_CU = z18;
                if (IS_MIFOUR) {
                }
                z19 = false;
                IS_MIFOUR_LTE_CT = z19;
                if (IS_MIFOUR) {
                }
                z20 = false;
                IS_MIFOUR_LTE_INDIA = z20;
                if (IS_MIFOUR) {
                }
                z21 = false;
                IS_MIFOUR_LTE_SEASA = z21;
                IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
                str3 = "ro.carrier.name";
                IS_CU_CUSTOMIZATION = str62222222222.equals(SystemProperties.get(str3));
                String str922222222222222222222 = "ro.miui.cust_variant";
                if (str2.equals(SystemProperties.get(str3))) {
                }
                z22 = false;
                IS_CM_CUSTOMIZATION = z22;
                if (str2.equals(SystemProperties.get(str3))) {
                }
                z23 = false;
                IS_CM_COOPERATION = z23;
                IS_CT_CUSTOMIZATION = str72222222222.equals(SystemProperties.get(str3));
                String str102222222222222222222222 = "ro.product.mod_device";
                String str112222222222222222222222 = "";
                IS_ALPHA_BUILD = SystemProperties.get(str102222222222222222222222, str112222222222222222222222).endsWith("_alpha");
                String str122222222222222222222222 = "1";
                IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str122222222222222222222222.equals(SystemProperties.get("ro.miui.cts")));
                IS_CTA_BUILD = str122222222222222222222222.equals(SystemProperties.get("ro.miui.cta"));
                String str132222222222222222222222 = "ro.cust.test";
                IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str132222222222222222222222));
                IS_CU_CUSTOMIZATION_TEST = str62222222222.equals(SystemProperties.get(str132222222222222222222222));
                IS_CT_CUSTOMIZATION_TEST = str72222222222.equals(SystemProperties.get(str132222222222222222222222));
                IS_FUNCTION_LIMITED = str122222222222222222222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
                IS_INTERNATIONAL_BUILD = SystemProperties.get(str102222222222222222222222, str112222222222222222222222).contains("_global");
                IS_GLOBAL_BUILD = SystemProperties.get(str102222222222222222222222, str112222222222222222222222).endsWith("_global");
                IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str112222222222222222222222).endsWith("_pro");
                IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str112222222222222222222222).isEmpty();
            }
        }
        z = true;
        IS_MIONE = z;
        if (!"MI 1S".equals(MODEL)) {
        }
        z2 = true;
        IS_MI1S = z2;
        if (!"aries".equals(DEVICE)) {
        }
        z3 = true;
        IS_MITWO = z3;
        if (!"MI 2A".equals(MODEL)) {
        }
        z4 = true;
        IS_MI2A = z4;
        String str42222 = "cancro";
        IS_MITHREE = !"pisces".equals(DEVICE) || (str42222.equals(DEVICE) && MODEL.startsWith("MI 3"));
        IS_MIFOUR = !str42222.equals(DEVICE) && MODEL.startsWith("MI 4");
        if (!"HM2014011".equals(DEVICE)) {
        }
        z5 = true;
        IS_HONGMI_TWO_S = z5;
        str = "HM2013022";
        if (!str.equals(DEVICE)) {
        }
        z6 = true;
        IS_HONGMI_TWO = z6;
        if (!"lcsh92_wet_jb9".equals(DEVICE)) {
        }
        z7 = true;
        IS_HONGMI_THREE = z7;
        if (IS_HONGMI_THREE_LTE) {
        }
        z8 = false;
        IS_HONGMI_THREE_LTE_CM = z8;
        if (IS_HONGMI_THREE_LTE) {
        }
        z9 = false;
        IS_HONGMI_THREE_LTE_CU = z9;
        if (!"HM2014812".equals(DEVICE)) {
        }
        z10 = true;
        IS_HONGMI_TWOX_CT = z10;
        if (!"HM2014813".equals(DEVICE)) {
        }
        z11 = true;
        IS_HONGMI_TWOX_CM = z11;
        String str522222222222 = "persist.sys.modem";
        str2 = "cm";
        IS_HONGMI_THREEX_CM = !IS_HONGMI_THREEX && str2.equals(SystemProperties.get(str522222222222));
        String str622222222222 = "cu";
        IS_HONGMI_THREEX_CU = !IS_HONGMI_THREEX && str622222222222.equals(SystemProperties.get(str522222222222));
        String str722222222222 = "ct";
        IS_HONGMI_THREEX_CT = !IS_HONGMI_THREEX && str722222222222.equals(SystemProperties.get(str522222222222));
        String str822222222222 = "persist.radio.modem";
        if (IS_MITWO) {
        }
        z12 = false;
        IS_MITWO_CDMA = z12;
        if (IS_MITHREE) {
        }
        z13 = false;
        IS_MITHREE_CDMA = z13;
        if (IS_MIFOUR) {
        }
        z14 = false;
        IS_MIFOUR_CDMA = z14;
        if (IS_MITWO) {
        }
        z15 = false;
        IS_MITWO_TDSCDMA = z15;
        if (IS_MITHREE) {
        }
        z16 = false;
        IS_MITHREE_TDSCDMA = z16;
        if (IS_MIFOUR) {
        }
        z17 = false;
        IS_MIFOUR_LTE_CM = z17;
        if (IS_MIFOUR) {
        }
        z18 = false;
        IS_MIFOUR_LTE_CU = z18;
        if (IS_MIFOUR) {
        }
        z19 = false;
        IS_MIFOUR_LTE_CT = z19;
        if (IS_MIFOUR) {
        }
        z20 = false;
        IS_MIFOUR_LTE_INDIA = z20;
        if (IS_MIFOUR) {
        }
        z21 = false;
        IS_MIFOUR_LTE_SEASA = z21;
        IS_HONGMI2_TDSCDMA = str.equals(DEVICE);
        str3 = "ro.carrier.name";
        IS_CU_CUSTOMIZATION = str622222222222.equals(SystemProperties.get(str3));
        String str9222222222222222222222 = "ro.miui.cust_variant";
        if (str2.equals(SystemProperties.get(str3))) {
        }
        z22 = false;
        IS_CM_CUSTOMIZATION = z22;
        if (str2.equals(SystemProperties.get(str3))) {
        }
        z23 = false;
        IS_CM_COOPERATION = z23;
        IS_CT_CUSTOMIZATION = str722222222222.equals(SystemProperties.get(str3));
        String str1022222222222222222222222 = "ro.product.mod_device";
        String str1122222222222222222222222 = "";
        IS_ALPHA_BUILD = SystemProperties.get(str1022222222222222222222222, str1122222222222222222222222).endsWith("_alpha");
        String str1222222222222222222222222 = "1";
        IS_CTS_BUILD = !SystemProperties.getBoolean("persist.sys.miui_optimization", !str1222222222222222222222222.equals(SystemProperties.get("ro.miui.cts")));
        IS_CTA_BUILD = str1222222222222222222222222.equals(SystemProperties.get("ro.miui.cta"));
        String str1322222222222222222222222 = "ro.cust.test";
        IS_CM_CUSTOMIZATION_TEST = str2.equals(SystemProperties.get(str1322222222222222222222222));
        IS_CU_CUSTOMIZATION_TEST = str622222222222.equals(SystemProperties.get(str1322222222222222222222222));
        IS_CT_CUSTOMIZATION_TEST = str722222222222.equals(SystemProperties.get(str1322222222222222222222222));
        IS_FUNCTION_LIMITED = str1222222222222222222222222.equals(SystemProperties.get(System.KEY_FUNCTION_LIMIT_SWITCH));
        IS_INTERNATIONAL_BUILD = SystemProperties.get(str1022222222222222222222222, str1122222222222222222222222).contains("_global");
        IS_GLOBAL_BUILD = SystemProperties.get(str1022222222222222222222222, str1122222222222222222222222).endsWith("_global");
        IS_PRO_DEVICE = SystemProperties.get("ro.miui.cust_device", str1122222222222222222222222).endsWith("_pro");
        IS_MIUI = !SystemProperties.get(PROP_MIUI_VERSION_CODE, str1122222222222222222222222).isEmpty();
    }

    protected Build() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static boolean checkRegion(String str) {
        return getRegion().equalsIgnoreCase(str);
    }

    public static String getCustVariant() {
        String str = "ro.miui.cust_variant";
        return !IS_INTERNATIONAL_BUILD ? SystemProperties.get(str, "cn") : SystemProperties.get(str, "hk");
    }

    public static String getRegion() {
        return SystemProperties.get("ro.miui.region", "CN");
    }

    public static int getUserMode() {
        return SystemProperties.getInt(USER_MODE, 0);
    }

    private static String getUserdataImageVersionCode() {
        String str = "";
        String str2 = SystemProperties.get("ro.miui.userdata_version", str);
        if (str.equals(str2)) {
            return "Unavailable";
        }
        String str3 = IS_INTERNATIONAL_BUILD ? "global" : "cn";
        String str4 = SystemProperties.get("ro.carrier.name", str);
        if (!str.equals(str4)) {
            StringBuilder sb = new StringBuilder();
            sb.append("_");
            sb.append(str4);
            str4 = sb.toString();
        }
        return String.format("%s(%s%s)", new Object[]{str2, str3, str4});
    }

    public static boolean hasCameraFlash(Context context) {
        return FeatureParser.getBoolean("support_torch", true);
    }

    private static boolean hasMsm8660Property() {
        String str = SystemProperties.get("ro.soc.name");
        return "msm8660".equals(str) || "unkown".equals(str);
    }

    private static boolean isTablet() {
        return SystemProperties.get("ro.build.characteristics").contains("tablet");
    }

    private static void reboot(boolean z, String str, boolean z2) {
        try {
            IPowerManager asInterface = Stub.asInterface(ServiceManager.getService("power"));
            if (asInterface != null) {
                asInterface.reboot(z, str, z2);
            }
        } catch (RemoteException e2) {
        }
    }

    public static void setUserMode(Context context, int i) {
        SystemProperties.set(USER_MODE, Integer.toString(i));
        reboot(false, null, false);
    }
}
