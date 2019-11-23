package com.arcsoft.avatar.util;

import com.arcsoft.avatar.AvatarConfig;

public class AvatarConfigUtils {
    public static int getCurrentConfigIdWithType(int i, AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue) {
        switch (i) {
            case 1:
                return aSAvatarConfigValue.configHairStyleID;
            case 2:
                return aSAvatarConfigValue.configHairColorID;
            case 3:
                return aSAvatarConfigValue.configFaceColorID;
            case 4:
                return aSAvatarConfigValue.configEyeColorID;
            case 5:
                return aSAvatarConfigValue.configLipColorID;
            case 6:
                return aSAvatarConfigValue.configHairHighlightColorID;
            case 7:
                return aSAvatarConfigValue.configFrecklesID;
            case 8:
                return aSAvatarConfigValue.configNevusID;
            case 9:
                return aSAvatarConfigValue.configEyewearStyleID;
            case 10:
                return aSAvatarConfigValue.configEyewearFrameID;
            case 11:
                return aSAvatarConfigValue.configEyewearLensesID;
            case 12:
                return aSAvatarConfigValue.configHeadwearStyleID;
            case 13:
                return aSAvatarConfigValue.configHeadwearColorID;
            case 14:
                return aSAvatarConfigValue.configHatStyleID;
            case 15:
                return aSAvatarConfigValue.configHatColorID;
            case 16:
                return aSAvatarConfigValue.configBeardStyleID;
            case 17:
                return aSAvatarConfigValue.configBeardColorID;
            case 18:
                return aSAvatarConfigValue.configEarringStyleID;
            case 19:
                return aSAvatarConfigValue.configEyelashStyleID;
            case 20:
                return aSAvatarConfigValue.configEyebrowColorID;
            case 21:
                return aSAvatarConfigValue.configFaceShapeID;
            case 22:
                return aSAvatarConfigValue.configEyeShapeID;
            case 23:
                return aSAvatarConfigValue.configMouthShapeID;
            case 26:
                return aSAvatarConfigValue.configNoseShapeID;
            case 29:
                return aSAvatarConfigValue.configEarShapeID;
            case 30:
                return aSAvatarConfigValue.configEyebrowShapeID;
            default:
                return -1;
        }
    }

    public static float getCurrentContinuousValueWithType(int i, AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue) {
        switch (i) {
            case 2:
                return aSAvatarConfigValue.configHairColorValue;
            case 3:
                return aSAvatarConfigValue.configFaceColorValue;
            case 4:
                return aSAvatarConfigValue.configEyeColorValue;
            case 5:
                return aSAvatarConfigValue.configLipColorValue;
            case 6:
                return aSAvatarConfigValue.configHairHighlightColorValue;
            case 10:
                return aSAvatarConfigValue.configEyewearFrameValue;
            case 11:
                return aSAvatarConfigValue.configEyewearLensesValue;
            case 13:
                return aSAvatarConfigValue.configHeadwearColorValue;
            case 17:
                return aSAvatarConfigValue.configBeardColorValue;
            case 20:
                return aSAvatarConfigValue.configEyebrowColorValue;
            case 21:
                return aSAvatarConfigValue.configFaceShapeValue;
            case 22:
                return aSAvatarConfigValue.configEyeShapeValue;
            case 23:
                return aSAvatarConfigValue.configMouthShapeValue;
            case 26:
                return aSAvatarConfigValue.configNoseShapeValue;
            case 29:
                return aSAvatarConfigValue.configEarShapeValue;
            case 30:
                return aSAvatarConfigValue.configEyebrowShapeValue;
            default:
                return -1.0f;
        }
    }

    public static int getMatchConfigType(int i) {
        if (i == 1) {
            return 2;
        }
        if (i == 12) {
            return 13;
        }
        if (i == 14) {
            return 15;
        }
        if (i == 16) {
            return 17;
        }
        if (i == 30) {
            return 20;
        }
        switch (i) {
            case 21:
                return 3;
            case 22:
                return 4;
            case 23:
                return 5;
            default:
                return 0;
        }
    }

    public static boolean isColorConfigComponentType(int i) {
        return i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 13 || i == 17 || i == 20 || i == 10 || i == 11;
    }

    public static boolean isSupportContinuousConfigInfo(AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo) {
        if (aSAvatarConfigInfo.configType == 21 || aSAvatarConfigInfo.configType == 23 || aSAvatarConfigInfo.configType == 26 || aSAvatarConfigInfo.configType == 22 || aSAvatarConfigInfo.configType == 10 || aSAvatarConfigInfo.configType == 11) {
            return false;
        }
        return aSAvatarConfigInfo.isSupportContinuous;
    }

    public static void updateConfigID(int i, int i2, AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue) {
        switch (i) {
            case 1:
                aSAvatarConfigValue.configHairStyleID = i2;
                return;
            case 2:
                aSAvatarConfigValue.configHairColorID = i2;
                return;
            case 3:
                aSAvatarConfigValue.configFaceColorID = i2;
                return;
            case 4:
                aSAvatarConfigValue.configEyeColorID = i2;
                return;
            case 5:
                aSAvatarConfigValue.configLipColorID = i2;
                return;
            case 6:
                aSAvatarConfigValue.configHairHighlightColorID = i2;
                return;
            case 7:
                aSAvatarConfigValue.configFrecklesID = i2;
                return;
            case 8:
                aSAvatarConfigValue.configNevusID = i2;
                return;
            case 9:
                aSAvatarConfigValue.configEyewearStyleID = i2;
                return;
            case 10:
                aSAvatarConfigValue.configEyewearFrameID = i2;
                return;
            case 11:
                aSAvatarConfigValue.configEyewearLensesID = i2;
                return;
            case 12:
                aSAvatarConfigValue.configHeadwearStyleID = i2;
                return;
            case 13:
                aSAvatarConfigValue.configHeadwearColorID = i2;
                return;
            case 14:
                aSAvatarConfigValue.configHatStyleID = i2;
                return;
            case 15:
                aSAvatarConfigValue.configHatColorID = i2;
                return;
            case 16:
                aSAvatarConfigValue.configBeardStyleID = i2;
                return;
            case 17:
                aSAvatarConfigValue.configBeardColorID = i2;
                return;
            case 18:
                aSAvatarConfigValue.configEarringStyleID = i2;
                return;
            case 19:
                aSAvatarConfigValue.configEyelashStyleID = i2;
                return;
            case 20:
                aSAvatarConfigValue.configEyebrowColorID = i2;
                return;
            case 21:
                aSAvatarConfigValue.configFaceShapeID = i2;
                return;
            case 22:
                aSAvatarConfigValue.configEyeShapeID = i2;
                return;
            case 23:
                aSAvatarConfigValue.configMouthShapeID = i2;
                return;
            case 26:
                aSAvatarConfigValue.configNoseShapeID = i2;
                return;
            case 29:
                aSAvatarConfigValue.configEarShapeID = i2;
                return;
            case 30:
                aSAvatarConfigValue.configEyebrowShapeID = i2;
                return;
            default:
                return;
        }
    }

    public static void updateConfigValue(int i, float f, AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue) {
        switch (i) {
            case 2:
                aSAvatarConfigValue.configHairColorValue = f;
                return;
            case 3:
                aSAvatarConfigValue.configFaceColorValue = f;
                return;
            case 4:
                aSAvatarConfigValue.configEyeColorValue = f;
                return;
            case 5:
                aSAvatarConfigValue.configLipColorValue = f;
                return;
            case 6:
                aSAvatarConfigValue.configHairHighlightColorValue = f;
                return;
            case 10:
                aSAvatarConfigValue.configEyewearFrameValue = f;
                return;
            case 11:
                aSAvatarConfigValue.configEyewearLensesValue = f;
                return;
            case 13:
                aSAvatarConfigValue.configHeadwearColorValue = f;
                return;
            case 17:
                aSAvatarConfigValue.configBeardColorValue = f;
                return;
            case 20:
                aSAvatarConfigValue.configEyebrowColorValue = f;
                return;
            case 21:
                aSAvatarConfigValue.configFaceShapeValue = f;
                return;
            case 22:
                aSAvatarConfigValue.configEyeShapeValue = f;
                return;
            case 23:
                aSAvatarConfigValue.configMouthShapeValue = f;
                return;
            case 26:
                aSAvatarConfigValue.configNoseShapeValue = f;
                return;
            case 29:
                aSAvatarConfigValue.configEarShapeValue = f;
                return;
            case 30:
                aSAvatarConfigValue.configEyebrowShapeValue = f;
                return;
            default:
                return;
        }
    }
}
