package com.android.camera.fragment.mimoji;

import android.content.Context;
import android.content.res.Resources;
import com.android.camera.R;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.log.Log;
import com.android.camera.statistic.CameraStat;
import com.arcsoft.avatar.AvatarConfig.ASAvatarConfigInfo;
import com.arcsoft.avatar.AvatarConfig.ASAvatarConfigType;
import com.arcsoft.avatar.AvatarConfig.ASAvatarConfigValue;
import com.arcsoft.avatar.AvatarEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AvatarEngineManager {
    public static final String BearTemplatePath;
    public static final int CONFIGTYPE_EARRING = 18;
    public static final int CONFIGTYPE_EAR_SHAPE = 29;
    public static final int CONFIGTYPE_EYEBROW_COLOR = 20;
    public static final int CONFIGTYPE_EYEBROW_SHAPE = 30;
    public static final int CONFIGTYPE_EYEGLASS = 9;
    public static final int CONFIGTYPE_EYEGLASS_COLOR = 10;
    public static final int CONFIGTYPE_EYELASH = 19;
    public static final int CONFIGTYPE_EYE_COLOR = 4;
    public static final int CONFIGTYPE_EYE_SHAPE = 22;
    public static final int CONFIGTYPE_FACE_COLOR = 3;
    public static final int CONFIGTYPE_FEATURED_FACE = 21;
    public static final int CONFIGTYPE_FRECKLE = 7;
    public static final int CONFIGTYPE_HAIR_COLOR = 2;
    public static final int CONFIGTYPE_HAIR_STYLE = 1;
    public static final int CONFIGTYPE_HEADWEAR = 12;
    public static final int CONFIGTYPE_HEADWEAR_COLOR = 13;
    public static final int CONFIGTYPE_LENS_COLOR = 11;
    public static final int CONFIGTYPE_LIPS_COLOR = 5;
    public static final int CONFIGTYPE_MOUSE_SHAPE = 23;
    public static final int CONFIGTYPE_MUSTACHE = 16;
    public static final int CONFIGTYPE_MUSTACHE_COLOR = 17;
    public static final int CONFIGTYPE_NEVUS = 8;
    public static final int CONFIGTYPE_NOSE_SHAPE = 26;
    public static final String FACE_MODEL;
    public static final String FAKE_BEAR_CONFIGPATH = "bear";
    public static final String FAKE_PIG_CONFIGPATH = "pig";
    public static final String FAKE_RABBIT_CONFIGPATH = "rabbit";
    public static final String FAKE_ROYAN_CONFIGPATH = "royan";
    public static final String PersonTemplatePath;
    public static final String PigTemplatePath;
    public static final String RabbitTemplatePath;
    public static final String RoyanTemplatePath;
    public static final int THUMB_HEIGHT = 200;
    public static final int THUMB_WIDTH = 200;
    public static final String TRACK_DATA;
    public static final String TempEditConfigPath;
    public static final String TempOriginalConfigPath;
    private static AvatarEngineManager mInstance;
    private ASAvatarConfigValue mASAvatarConfigValue;
    private ASAvatarConfigValue mASAvatarConfigValueDefault;
    private boolean mAllNeedUpdate = false;
    private AvatarEngine mAvatar;
    private int mAvatarRef = 0;
    private Map<Integer, LinearLayoutManagerWrapper> mColorLayoutManagerMap = new ConcurrentHashMap();
    private Map<Integer, ArrayList<ASAvatarConfigInfo>> mConfigMap = new ConcurrentHashMap();
    private Map<Integer, Float> mInnerConfigSelectMap = new ConcurrentHashMap();
    private Map<Integer, Integer> mInterruptMap = new ConcurrentHashMap();
    private boolean mIsColorSelected = false;
    private Map<Integer, Boolean> mNeedUpdateMap = new ConcurrentHashMap();
    private int mSelectTabIndex = 0;
    private int mSelectType = 0;
    private CopyOnWriteArrayList<MimojiLevelBean> mSubConfigs = new CopyOnWriteArrayList<>();
    private ArrayList<ASAvatarConfigType> mTypeList = new ArrayList<>();

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(MimojiHelper.DATA_DIR);
        sb.append("track_data.dat");
        TRACK_DATA = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(MimojiHelper.DATA_DIR);
        sb2.append("config.txt");
        FACE_MODEL = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(MimojiHelper.MODEL_PATH);
        sb3.append("cartoon_xiaomi_v_0_0_0_27");
        PersonTemplatePath = sb3.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(MimojiHelper.MODEL_PATH);
        sb4.append("bear_v_0_0_0_5");
        BearTemplatePath = sb4.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(MimojiHelper.MODEL_PATH);
        sb5.append("pig_v_0_0_0_3");
        PigTemplatePath = sb5.toString();
        StringBuilder sb6 = new StringBuilder();
        sb6.append(MimojiHelper.MODEL_PATH);
        sb6.append("royan_v_0_0_0_7");
        RoyanTemplatePath = sb6.toString();
        StringBuilder sb7 = new StringBuilder();
        sb7.append(MimojiHelper.MODEL_PATH);
        sb7.append("rabbit_v_0_0_0_4");
        RabbitTemplatePath = sb7.toString();
        StringBuilder sb8 = new StringBuilder();
        sb8.append(MimojiHelper.DATA_DIR);
        sb8.append("origin_config.dat");
        TempOriginalConfigPath = sb8.toString();
        StringBuilder sb9 = new StringBuilder();
        sb9.append(MimojiHelper.DATA_DIR);
        sb9.append("edit_config.dat");
        TempEditConfigPath = sb9.toString();
    }

    public static boolean filterTypeTitle(int i) {
        if (!(i == 1 || i == 12 || i == 16 || i == 30 || i == 8 || i == 9)) {
            switch (i) {
                case 21:
                case 22:
                case 23:
                    break;
                default:
                    return true;
            }
        }
        return false;
    }

    public static synchronized AvatarEngineManager getInstance() {
        AvatarEngineManager avatarEngineManager;
        synchronized (AvatarEngineManager.class) {
            if (mInstance == null) {
                mInstance = new AvatarEngineManager();
            }
            avatarEngineManager = mInstance;
        }
        return avatarEngineManager;
    }

    public static Map<String, String> getMimojiConfigValue(ASAvatarConfigValue aSAvatarConfigValue) {
        HashMap hashMap = new HashMap();
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_HARISTYLE, String.valueOf(aSAvatarConfigValue.configHairStyleID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_FEATURE_FACE, String.valueOf(aSAvatarConfigValue.configFaceShapeID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_EYE_SHAPE, String.valueOf(aSAvatarConfigValue.configEyeShapeID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_MOUTH_SHAPE, String.valueOf(aSAvatarConfigValue.configMouthShapeID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_MUSTACHE, String.valueOf(aSAvatarConfigValue.configBeardStyleID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_FRECKLE, String.valueOf(aSAvatarConfigValue.configFrecklesID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_EYEGLASS, String.valueOf(aSAvatarConfigValue.configEyewearStyleID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_HEADWEAR, String.valueOf(aSAvatarConfigValue.configHeadwearStyleID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_EAR, String.valueOf(aSAvatarConfigValue.configEarShapeID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_EYELASH, String.valueOf(aSAvatarConfigValue.configEyelashStyleID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_EYEBROW_SHAPE, String.valueOf(aSAvatarConfigValue.configEyebrowShapeID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_NOSE, String.valueOf(aSAvatarConfigValue.configNoseShapeID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_EARING, String.valueOf(aSAvatarConfigValue.configEarringStyleID));
        return hashMap;
    }

    public static boolean isPrefabModel(String str) {
        return str.equals(FAKE_PIG_CONFIGPATH) || str.equals(FAKE_BEAR_CONFIGPATH) || str.equals(FAKE_ROYAN_CONFIGPATH) || str.equals(FAKE_RABBIT_CONFIGPATH);
    }

    public static String replaceTabTitle(Context context, int i) {
        Resources resources = context.getResources();
        if (i == 1) {
            return resources.getString(R.string.mimoji_hairstyle);
        }
        if (i == 12) {
            return resources.getString(R.string.mimoji_ornament);
        }
        if (i == 16) {
            return resources.getString(R.string.mimoji_mustache);
        }
        if (i == 30) {
            return resources.getString(R.string.mimoji_eyebrow);
        }
        if (i == 8) {
            return resources.getString(R.string.mimoji_freckle);
        }
        if (i == 9) {
            return resources.getString(R.string.mimoji_eyeglass);
        }
        switch (i) {
            case 21:
                return resources.getString(R.string.mimoji_featured_face);
            case 22:
                return resources.getString(R.string.mimoji_eye);
            case 23:
                return resources.getString(R.string.mimoji_nose_lisps);
            default:
                return "";
        }
    }

    public static boolean showConfigTypeName(int i) {
        return (i == 1 || i == 7 || i == 9 || i == 12 || i == 16 || i == 26 || i == 30 || i == 21 || i == 22) ? false : true;
    }

    public synchronized AvatarEngine addAvatarConfig(ASAvatarConfigInfo aSAvatarConfigInfo) {
        if (aSAvatarConfigInfo == null) {
            Log.d("AvatarEngineManager", "AvatarConfig.ASAvatarConfigInfo is null");
            return null;
        }
        AvatarEngine queryAvatar = queryAvatar();
        queryAvatar.setConfig(aSAvatarConfigInfo);
        return queryAvatar;
    }

    public void clear() {
        this.mSelectType = 0;
        this.mSelectTabIndex = 0;
        this.mSubConfigs.clear();
        this.mColorLayoutManagerMap.clear();
    }

    public ASAvatarConfigValue getASAvatarConfigValue() {
        return this.mASAvatarConfigValue;
    }

    public LinearLayoutManagerWrapper getColorLayoutManagerMap(int i) {
        return (LinearLayoutManagerWrapper) this.mColorLayoutManagerMap.get(Integer.valueOf(i));
    }

    public int getColorType(int i) {
        if (i == 1) {
            return 2;
        }
        if (i == 12) {
            return 13;
        }
        if (i == 16) {
            return 17;
        }
        if (i == 21) {
            return 3;
        }
        if (i != 23) {
            return i != 30 ? -1 : 20;
        }
        return 5;
    }

    public synchronized ArrayList<ASAvatarConfigInfo> getConfigList(int i) {
        return this.mConfigMap.size() <= 0 ? null : (ArrayList) this.mConfigMap.get(Integer.valueOf(i));
    }

    public ASAvatarConfigType getConfigTypeForIndex(int i) {
        ArrayList<ASAvatarConfigType> arrayList = this.mTypeList;
        if (arrayList == null || arrayList.size() <= 0) {
            return null;
        }
        return (ASAvatarConfigType) this.mTypeList.get(i);
    }

    public ArrayList<ASAvatarConfigType> getConfigTypeList() {
        return this.mTypeList;
    }

    public float getInnerConfigSelectIndex(int i) {
        if (this.mInnerConfigSelectMap.get(Integer.valueOf(i)) == null) {
            return -1.0f;
        }
        return ((Float) this.mInnerConfigSelectMap.get(Integer.valueOf(i))).floatValue();
    }

    public int getInterruptIndex(int i) {
        Integer num = (Integer) this.mInterruptMap.get(Integer.valueOf(i));
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public ArrayList<ASAvatarConfigInfo> getSelectConfigList() {
        if (this.mConfigMap.size() <= 0) {
            return null;
        }
        return (ArrayList) this.mConfigMap.get(Integer.valueOf(this.mSelectType));
    }

    public int getSelectType() {
        return this.mSelectType;
    }

    public int getSelectTypeIndex() {
        return this.mSelectTabIndex;
    }

    public ArrayList<ASAvatarConfigInfo> getSubConfigColorList(int i) {
        if (i == 1) {
            return getConfigList(2);
        }
        if (i == 12) {
            return getConfigList(13);
        }
        if (i == 16) {
            return getConfigList(17);
        }
        if (i == 30) {
            return getConfigList(20);
        }
        switch (i) {
            case 21:
                return getConfigList(3);
            case 22:
                return getConfigList(4);
            case 23:
                return getConfigList(5);
            default:
                return null;
        }
    }

    public CopyOnWriteArrayList<MimojiLevelBean> getSubConfigList(Context context) {
        return getSubConfigList(context, this.mSelectType);
    }

    public CopyOnWriteArrayList<MimojiLevelBean> getSubConfigList(Context context, int i) {
        this.mSubConfigs.clear();
        Resources resources = context.getResources();
        if (i == 1) {
            MimojiLevelBean mimojiLevelBean = new MimojiLevelBean();
            mimojiLevelBean.thumnails = getConfigList(1);
            ArrayList<ASAvatarConfigInfo> arrayList = mimojiLevelBean.thumnails;
            if (arrayList != null && arrayList.size() > 0) {
                mimojiLevelBean.configType = 1;
                mimojiLevelBean.configTypeName = resources.getString(R.string.mimoji_hairstyle);
                this.mSubConfigs.add(mimojiLevelBean);
            }
        } else if (i == 12) {
            MimojiLevelBean mimojiLevelBean2 = new MimojiLevelBean();
            mimojiLevelBean2.thumnails = getConfigList(12);
            ArrayList<ASAvatarConfigInfo> arrayList2 = mimojiLevelBean2.thumnails;
            if (arrayList2 != null && arrayList2.size() > 0) {
                mimojiLevelBean2.configType = 12;
                mimojiLevelBean2.configTypeName = resources.getString(R.string.mimoji_headwear);
                this.mSubConfigs.add(mimojiLevelBean2);
            }
            MimojiLevelBean mimojiLevelBean3 = new MimojiLevelBean();
            mimojiLevelBean3.thumnails = getConfigList(18);
            ArrayList<ASAvatarConfigInfo> arrayList3 = mimojiLevelBean3.thumnails;
            if (arrayList3 != null && arrayList3.size() > 0) {
                mimojiLevelBean3.configType = 18;
                mimojiLevelBean3.configTypeName = resources.getString(R.string.mimoji_earring);
                this.mSubConfigs.add(mimojiLevelBean3);
            }
        } else if (i == 16) {
            MimojiLevelBean mimojiLevelBean4 = new MimojiLevelBean();
            mimojiLevelBean4.thumnails = getConfigList(16);
            ArrayList<ASAvatarConfigInfo> arrayList4 = mimojiLevelBean4.thumnails;
            if (arrayList4 != null && arrayList4.size() > 0) {
                mimojiLevelBean4.configType = 16;
                mimojiLevelBean4.configTypeName = resources.getString(R.string.mimoji_mustache);
                this.mSubConfigs.add(mimojiLevelBean4);
            }
        } else if (i == 30) {
            MimojiLevelBean mimojiLevelBean5 = new MimojiLevelBean();
            mimojiLevelBean5.thumnails = getConfigList(30);
            ArrayList<ASAvatarConfigInfo> arrayList5 = mimojiLevelBean5.thumnails;
            if (arrayList5 != null && arrayList5.size() > 0) {
                mimojiLevelBean5.configType = 30;
                mimojiLevelBean5.configTypeName = resources.getString(R.string.mimoji_eyebrow_shape);
                this.mSubConfigs.add(mimojiLevelBean5);
            }
        } else if (i == 8) {
            MimojiLevelBean mimojiLevelBean6 = new MimojiLevelBean();
            mimojiLevelBean6.thumnails = getConfigList(7);
            ArrayList<ASAvatarConfigInfo> arrayList6 = mimojiLevelBean6.thumnails;
            if (arrayList6 != null && arrayList6.size() > 0) {
                mimojiLevelBean6.configType = 7;
                mimojiLevelBean6.configTypeName = resources.getString(R.string.mimoji_freckle);
                this.mSubConfigs.add(mimojiLevelBean6);
            }
            MimojiLevelBean mimojiLevelBean7 = new MimojiLevelBean();
            mimojiLevelBean7.thumnails = getConfigList(8);
            ArrayList<ASAvatarConfigInfo> arrayList7 = mimojiLevelBean7.thumnails;
            if (arrayList7 != null && arrayList7.size() > 0) {
                mimojiLevelBean7.configType = 8;
                mimojiLevelBean7.configTypeName = resources.getString(R.string.mimoji_mole);
                this.mSubConfigs.add(mimojiLevelBean7);
            }
        } else if (i != 9) {
            switch (i) {
                case 21:
                    MimojiLevelBean mimojiLevelBean8 = new MimojiLevelBean();
                    mimojiLevelBean8.thumnails.addAll(getConfigList(21));
                    ArrayList<ASAvatarConfigInfo> arrayList8 = mimojiLevelBean8.thumnails;
                    if (arrayList8 != null && arrayList8.size() > 0) {
                        mimojiLevelBean8.configType = 21;
                        mimojiLevelBean8.configTypeName = resources.getString(R.string.mimoji_featured_face);
                        this.mSubConfigs.add(mimojiLevelBean8);
                    }
                    MimojiLevelBean mimojiLevelBean9 = new MimojiLevelBean();
                    mimojiLevelBean9.thumnails = getConfigList(29);
                    ArrayList<ASAvatarConfigInfo> arrayList9 = mimojiLevelBean9.thumnails;
                    if (arrayList9 != null && arrayList9.size() > 0) {
                        mimojiLevelBean9.configType = 29;
                        mimojiLevelBean9.configTypeName = resources.getString(R.string.mimoji_ear);
                        this.mSubConfigs.add(mimojiLevelBean9);
                        break;
                    }
                case 22:
                    MimojiLevelBean mimojiLevelBean10 = new MimojiLevelBean();
                    mimojiLevelBean10.thumnails = getConfigList(22);
                    ArrayList<ASAvatarConfigInfo> arrayList10 = mimojiLevelBean10.thumnails;
                    if (arrayList10 != null && arrayList10.size() > 0) {
                        mimojiLevelBean10.configType = 22;
                        mimojiLevelBean10.configTypeName = resources.getString(R.string.mimoji_eye_shape);
                        this.mSubConfigs.add(mimojiLevelBean10);
                    }
                    MimojiLevelBean mimojiLevelBean11 = new MimojiLevelBean();
                    mimojiLevelBean11.thumnails = getConfigList(19);
                    ArrayList<ASAvatarConfigInfo> arrayList11 = mimojiLevelBean11.thumnails;
                    if (arrayList11 != null && arrayList11.size() > 0) {
                        mimojiLevelBean11.configType = 19;
                        mimojiLevelBean11.configTypeName = resources.getString(R.string.mimoji_eyelash);
                        this.mSubConfigs.add(mimojiLevelBean11);
                        break;
                    }
                case 23:
                    MimojiLevelBean mimojiLevelBean12 = new MimojiLevelBean();
                    mimojiLevelBean12.thumnails = getConfigList(26);
                    ArrayList<ASAvatarConfigInfo> arrayList12 = mimojiLevelBean12.thumnails;
                    if (arrayList12 != null && arrayList12.size() > 0) {
                        mimojiLevelBean12.configType = 26;
                        mimojiLevelBean12.configTypeName = resources.getString(R.string.mimoji_nose);
                        this.mSubConfigs.add(mimojiLevelBean12);
                    }
                    MimojiLevelBean mimojiLevelBean13 = new MimojiLevelBean();
                    mimojiLevelBean13.thumnails = getConfigList(23);
                    ArrayList<ASAvatarConfigInfo> arrayList13 = mimojiLevelBean13.thumnails;
                    if (arrayList13 != null && arrayList13.size() > 0) {
                        mimojiLevelBean13.configType = 23;
                        mimojiLevelBean13.configTypeName = resources.getString(R.string.mimoji_mouth_type);
                        this.mSubConfigs.add(mimojiLevelBean13);
                        break;
                    }
            }
        } else {
            MimojiLevelBean mimojiLevelBean14 = new MimojiLevelBean();
            mimojiLevelBean14.thumnails = getConfigList(9);
            ArrayList<ASAvatarConfigInfo> arrayList14 = mimojiLevelBean14.thumnails;
            if (arrayList14 != null && arrayList14.size() > 0) {
                mimojiLevelBean14.configType = 9;
                mimojiLevelBean14.configTypeName = resources.getString(R.string.mimoji_eyeglass);
                this.mSubConfigs.add(mimojiLevelBean14);
            }
        }
        return this.mSubConfigs;
    }

    public void initUpdatePara() {
        this.mInterruptMap.clear();
        this.mNeedUpdateMap.clear();
        this.mAllNeedUpdate = true;
    }

    public boolean isColorSelected() {
        return this.mIsColorSelected;
    }

    public boolean isNeedUpdate(int i) {
        Boolean bool = (Boolean) this.mNeedUpdateMap.get(Integer.valueOf(i));
        boolean z = true;
        if (bool == null) {
            this.mNeedUpdateMap.put(Integer.valueOf(i), Boolean.valueOf(false));
            return true;
        }
        if (!bool.booleanValue() && !this.mAllNeedUpdate) {
            z = false;
        }
        return z;
    }

    public void putColorLayoutManagerMap(int i, LinearLayoutManagerWrapper linearLayoutManagerWrapper) {
        this.mColorLayoutManagerMap.put(Integer.valueOf(i), linearLayoutManagerWrapper);
    }

    public void putConfigList(int i, ArrayList<ASAvatarConfigInfo> arrayList) {
        if (!this.mConfigMap.containsKey(Integer.valueOf(i))) {
            this.mConfigMap.put(Integer.valueOf(i), arrayList);
        }
    }

    public synchronized AvatarEngine queryAvatar() {
        if (this.mAvatar == null) {
            Log.d("AvatarEngineManager", "avatar create");
            this.mAvatar = new AvatarEngine();
        }
        this.mAvatarRef++;
        return this.mAvatar;
    }

    public synchronized void releaseAvatar() {
        Log.d("AvatarEngineManager", "avatar destroy");
        this.mAvatar.destroy();
        this.mAvatar = null;
    }

    public void resetData() {
        this.mInnerConfigSelectMap.clear();
        this.mASAvatarConfigValue = (ASAvatarConfigValue) this.mASAvatarConfigValueDefault.clone();
        setASAvatarConfigValue(this.mASAvatarConfigValue);
        initUpdatePara();
    }

    public void setASAvatarConfigValue(ASAvatarConfigValue aSAvatarConfigValue) {
        this.mASAvatarConfigValue = aSAvatarConfigValue;
        if (aSAvatarConfigValue != null) {
            String str = FragmentMimojiEdit.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("value 属性:gender = ");
            sb.append(aSAvatarConfigValue.gender);
            sb.append(" configHairStyleID = ");
            sb.append(aSAvatarConfigValue.configHairStyleID);
            sb.append(" configHairColorID = ");
            sb.append(aSAvatarConfigValue.configHairColorID);
            sb.append(" configHairColorValue = ");
            sb.append(aSAvatarConfigValue.configHairColorValue);
            sb.append(" configFaceColorID = ");
            sb.append(aSAvatarConfigValue.configFaceColorID);
            sb.append(" configFaceColorValue = ");
            sb.append(aSAvatarConfigValue.configFaceColorValue);
            sb.append(" configEyeColorID = ");
            sb.append(aSAvatarConfigValue.configEyeColorID);
            sb.append(" configEyeColorValue = ");
            sb.append(aSAvatarConfigValue.configEyeColorValue);
            sb.append(" configLipColorID = ");
            sb.append(aSAvatarConfigValue.configLipColorID);
            sb.append(" configLipColorValue = ");
            sb.append(aSAvatarConfigValue.configLipColorValue);
            sb.append(" configHairHighlightColorID = ");
            sb.append(aSAvatarConfigValue.configHairHighlightColorID);
            sb.append(" configHairHighlightColorValue = ");
            sb.append(aSAvatarConfigValue.configHairHighlightColorValue);
            sb.append(" configFrecklesID = ");
            sb.append(aSAvatarConfigValue.configFrecklesID);
            sb.append(" configNevusID = ");
            sb.append(aSAvatarConfigValue.configNevusID);
            sb.append(" configEyewearStyleID = ");
            sb.append(aSAvatarConfigValue.configEyewearStyleID);
            sb.append(" configEyewearFrameID = ");
            sb.append(aSAvatarConfigValue.configEyewearFrameID);
            sb.append(" configEyewearFrameValue = ");
            sb.append(aSAvatarConfigValue.configEyewearFrameValue);
            sb.append(" configEyewearLensesID = ");
            sb.append(aSAvatarConfigValue.configEyewearLensesID);
            sb.append(" configEyewearLensesValue = ");
            sb.append(aSAvatarConfigValue.configEyewearLensesValue);
            sb.append(" configHeadwearStyleID = ");
            sb.append(aSAvatarConfigValue.configHeadwearStyleID);
            sb.append(" configHeadwearColorID = ");
            sb.append(aSAvatarConfigValue.configHeadwearColorID);
            sb.append(" configHeadwearColorValue = ");
            sb.append(aSAvatarConfigValue.configHeadwearColorValue);
            sb.append(" configBeardStyleID = ");
            sb.append(aSAvatarConfigValue.configBeardStyleID);
            sb.append(" configBeardColorID = ");
            sb.append(aSAvatarConfigValue.configBeardColorID);
            sb.append(" configBeardColorValue = ");
            sb.append(aSAvatarConfigValue.configBeardColorValue);
            sb.append(" configEarringStyleID = ");
            sb.append(aSAvatarConfigValue.configEarringStyleID);
            sb.append(" configEyelashStyleID = ");
            sb.append(aSAvatarConfigValue.configEyelashStyleID);
            sb.append(" configEyebrowColorID = ");
            sb.append(aSAvatarConfigValue.configEyebrowColorID);
            sb.append(" configEyebrowColorValue = ");
            sb.append(aSAvatarConfigValue.configEyebrowColorValue);
            sb.append(" configFaceShapeID = ");
            sb.append(aSAvatarConfigValue.configFaceShapeID);
            sb.append(" configFaceShapeValue = ");
            sb.append(aSAvatarConfigValue.configFaceShapeValue);
            sb.append(" configEyeShapeID = ");
            sb.append(aSAvatarConfigValue.configEyeShapeID);
            sb.append(" configEyeShapeValue = ");
            sb.append(aSAvatarConfigValue.configEyeShapeValue);
            sb.append(" configMouthShapeID = ");
            sb.append(aSAvatarConfigValue.configMouthShapeID);
            sb.append(" configMouthShapeValue = ");
            sb.append(aSAvatarConfigValue.configMouthShapeValue);
            sb.append(" configNoseShapeID = ");
            sb.append(aSAvatarConfigValue.configNoseShapeID);
            sb.append(" configNoseShapeValue = ");
            sb.append(aSAvatarConfigValue.configNoseShapeValue);
            sb.append(" configEarShapeID = ");
            sb.append(aSAvatarConfigValue.configEarShapeID);
            sb.append(" configEarShapeValue = ");
            sb.append(aSAvatarConfigValue.configEarShapeValue);
            sb.append(" configEyebrowShapeID = ");
            sb.append(aSAvatarConfigValue.configEyebrowShapeID);
            sb.append(" configEyebrowShapeValue = ");
            sb.append(aSAvatarConfigValue.configEyebrowShapeValue);
            Log.i(str, sb.toString());
            this.mInnerConfigSelectMap.put(Integer.valueOf(1), Float.valueOf((float) aSAvatarConfigValue.configHairStyleID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(2), Float.valueOf((float) aSAvatarConfigValue.configHairColorID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(3), Float.valueOf((float) aSAvatarConfigValue.configFaceColorID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(21), Float.valueOf((float) aSAvatarConfigValue.configFaceShapeID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(4), Float.valueOf(aSAvatarConfigValue.configEyeColorValue));
            this.mInnerConfigSelectMap.put(Integer.valueOf(5), Float.valueOf((float) aSAvatarConfigValue.configLipColorID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(7), Float.valueOf((float) aSAvatarConfigValue.configFrecklesID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(8), Float.valueOf((float) aSAvatarConfigValue.configNevusID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(9), Float.valueOf((float) aSAvatarConfigValue.configEyewearStyleID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(16), Float.valueOf((float) aSAvatarConfigValue.configBeardStyleID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(17), Float.valueOf((float) aSAvatarConfigValue.configBeardColorID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(19), Float.valueOf((float) aSAvatarConfigValue.configEyelashStyleID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(20), Float.valueOf((float) aSAvatarConfigValue.configEyebrowColorID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(22), Float.valueOf((float) aSAvatarConfigValue.configEyeShapeID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(23), Float.valueOf((float) aSAvatarConfigValue.configMouthShapeID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(26), Float.valueOf((float) aSAvatarConfigValue.configNoseShapeID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(29), Float.valueOf((float) aSAvatarConfigValue.configEarShapeID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(30), Float.valueOf((float) aSAvatarConfigValue.configEyebrowShapeID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(13), Float.valueOf((float) aSAvatarConfigValue.configHeadwearColorID));
            this.mInnerConfigSelectMap.put(Integer.valueOf(12), Float.valueOf((float) aSAvatarConfigValue.configHeadwearStyleID));
        }
    }

    public void setASAvatarConfigValueDefault(ASAvatarConfigValue aSAvatarConfigValue) {
        this.mASAvatarConfigValueDefault = (ASAvatarConfigValue) aSAvatarConfigValue.clone();
    }

    public void setAllNeedUpdate(boolean z, boolean z2) {
        this.mAllNeedUpdate = z;
        this.mIsColorSelected = z2;
        this.mInterruptMap.clear();
    }

    public void setConfigTypeList(ArrayList<ASAvatarConfigType> arrayList) {
        this.mTypeList = arrayList;
    }

    public void setInnerConfigSelectIndex(int i, float f2) {
        this.mInnerConfigSelectMap.put(Integer.valueOf(i), Float.valueOf(f2));
    }

    public void setInterruptIndex(int i, int i2) {
        this.mInterruptMap.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public void setIsColorSelected(boolean z) {
        this.mIsColorSelected = z;
    }

    public void setSelectType(int i) {
        this.mSelectType = i;
    }

    public void setSelectTypeIndex(int i) {
        this.mSelectTabIndex = i;
    }

    public void setTypeNeedUpdate(int i, boolean z) {
        this.mNeedUpdateMap.put(Integer.valueOf(i), Boolean.valueOf(z));
    }
}
