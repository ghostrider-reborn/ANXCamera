package com.arcsoft.avatar;

import android.graphics.Bitmap;
import com.android.camera.statistic.CameraStat;
import com.arcsoft.avatar.util.LOG;
import java.io.Serializable;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

public interface AvatarConfig {

    @Documented
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ASAvatarConfigComponentType {
        public static final int BEARD_COLOR = 17;
        public static final int BEARD_STYLE = 16;
        public static final int CUSTOM_EXPRESSION = 32;
        public static final int EARRING_STYLE = 18;
        public static final int EAR_SHAPE = 29;
        public static final int EYEBROW_COLOR = 20;
        public static final int EYEBROW_SHAPE = 30;
        public static final int EYELASH_STYLE = 19;
        public static final int EYEWEAR_FRAME = 10;
        public static final int EYEWEAR_LENSES = 11;
        public static final int EYEWEAR_STYLE = 9;
        public static final int EYE_COLOR = 4;
        public static final int EYE_SHAPE = 22;
        public static final int FACE_COLOR = 3;
        public static final int FACE_SHAPE = 21;
        public static final int FRECKLES = 7;
        public static final int GENDER = 31;
        public static final int HAIR_COLOR = 2;
        public static final int HAIR_HIGHLIGHT_COLOR = 6;
        public static final int HAIR_STYLE = 1;
        public static final int HAT_COLOR = 15;
        public static final int HAT_STYLE = 14;
        public static final int HEADWEAR_COLOR = 13;
        public static final int HEADWEAR_STYLE = 12;
        public static final int LIP_COLOR = 5;
        public static final int MOUTH_HSHAPE = 25;
        public static final int MOUTH_SHAPE = 23;
        public static final int MOUTH_WSHAPE = 24;
        public static final int NEVUS = 8;
        public static final int NONE = 0;
        public static final int NOSE_HSHAPE = 28;
        public static final int NOSE_SHAPE = 26;
        public static final int NOSE_WSHAPE = 27;
    }

    @Documented
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ASAvatarConfigGenderType {
        public static final int FEMALE = 2;
        public static final int MALE = 1;
        public static final int UNKNOWN = 0;
    }

    public static class ASAvatarConfigInfo {
        public int configID;
        public String configThumbPath;
        public int configType;
        public float continuousValue;
        public int endColorValue;
        public int gender;
        public boolean isDefault;
        public boolean isSupportContinuous;
        public boolean isValid;
        public String name;
        public int startColorValue;
        public Bitmap thum;

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("configID = ");
            sb.append(this.configID);
            sb.append(" configType = ");
            sb.append(this.configType);
            sb.append(" gender = ");
            sb.append(this.gender);
            sb.append(" name = ");
            sb.append(this.name);
            sb.append(" configThumbPath = ");
            sb.append(this.configThumbPath);
            sb.append(" isDefault = ");
            sb.append(this.isDefault);
            sb.append(" isValid = ");
            sb.append(this.isValid);
            sb.append(" isSupportContinuous = ");
            sb.append(this.isSupportContinuous);
            sb.append(" continuousValue = ");
            sb.append(this.continuousValue);
            sb.append(" startColorValue = ");
            sb.append(this.startColorValue);
            sb.append(" endColorValue = ");
            sb.append(this.endColorValue);
            sb.append("thum = ");
            sb.append(this.thum);
            sb.append("\n");
            return sb.toString();
        }
    }

    public static class ASAvatarConfigType {
        public int configType;
        public String configTypeDesc;
        public boolean refreshThum = true;

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("configTypeDesc = ");
            sb.append(this.configTypeDesc);
            sb.append(" configType = ");
            sb.append(this.configType);
            sb.append(" refreshThum = ");
            sb.append(this.refreshThum);
            return sb.toString();
        }
    }

    public static class ASAvatarConfigValue implements Cloneable {
        public int configBeardColorID;
        public float configBeardColorValue;
        public int configBeardStyleID;
        public int configEarShapeID;
        public float configEarShapeValue;
        public int configEarringStyleID;
        public int configEyeColorID;
        public float configEyeColorValue;
        public int configEyeShapeID;
        public float configEyeShapeValue;
        public int configEyebrowColorID;
        public float configEyebrowColorValue;
        public int configEyebrowShapeID;
        public float configEyebrowShapeValue;
        public int configEyelashStyleID;
        public int configEyewearFrameID;
        public float configEyewearFrameValue;
        public int configEyewearLensesID;
        public float configEyewearLensesValue;
        public int configEyewearStyleID;
        public int configFaceColorID;
        public float configFaceColorValue;
        public int configFaceShapeID;
        public float configFaceShapeValue;
        public int configFrecklesID;
        public int configGenderID;
        public int configHairColorID;
        public float configHairColorValue;
        public int configHairHighlightColorID;
        public float configHairHighlightColorValue;
        public int configHairStyleID;
        public int configHatColorID;
        public float configHatColorValue;
        public int configHatStyleID;
        public int configHeadwearColorID;
        public float configHeadwearColorValue;
        public int configHeadwearStyleID;
        public int configLipColorID;
        public float configLipColorValue;
        public int configMouthShapeID;
        public float configMouthShapeValue;
        public int configNevusID;
        public int configNoseShapeID;
        public float configNoseShapeValue;
        public int config_CustomExpression_ID;
        public int gender;

        public Object clone() {
            try {
                return (ASAvatarConfigValue) super.clone();
            } catch (CloneNotSupportedException e2) {
                e2.printStackTrace();
                return null;
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("gender = ");
            sb.append(this.gender);
            sb.append(" hairStyleID = ");
            sb.append(this.configHairStyleID);
            sb.append(" hairColorID = ");
            sb.append(this.configHairColorID);
            sb.append(" hairColorValue = ");
            sb.append(this.configHairColorValue);
            sb.append(" faceColorID = ");
            sb.append(this.configFaceColorID);
            sb.append(" faceColorValue = ");
            sb.append(this.configFaceColorValue);
            sb.append(" eyeColorID = ");
            sb.append(this.configEyeColorID);
            sb.append(" eyeColorValue = ");
            sb.append(this.configEyeColorValue);
            sb.append(" lipColorID = ");
            sb.append(this.configLipColorID);
            sb.append(" lipColorValue = ");
            sb.append(this.configLipColorValue);
            sb.append(" hairHighlightColorID = ");
            sb.append(this.configHairHighlightColorID);
            sb.append(" hairHighlightColorValue = ");
            sb.append(this.configHairHighlightColorValue);
            sb.append(" frecklesID = ");
            sb.append(this.configFrecklesID);
            sb.append(" nevusID = ");
            sb.append(this.configNevusID);
            sb.append(" eyewearStyleID = ");
            sb.append(this.configEyewearStyleID);
            sb.append(" eyewearFrameID = ");
            sb.append(this.configEyewearFrameID);
            sb.append(" eyewearFrameValue = ");
            sb.append(this.configEyewearFrameValue);
            sb.append(" eyewearLensesID = ");
            sb.append(this.configEyewearLensesID);
            sb.append(" eyewearLensesValue = ");
            sb.append(this.configEyewearLensesValue);
            sb.append(" headwearStyleID = ");
            sb.append(this.configHeadwearStyleID);
            sb.append(" headwearColorID = ");
            sb.append(this.configHeadwearColorID);
            sb.append(" headwearColorValue = ");
            sb.append(this.configHeadwearColorValue);
            sb.append(" hatStyleID = ");
            sb.append(this.configHatStyleID);
            sb.append(" hatColorID = ");
            sb.append(this.configHatColorID);
            sb.append(" hatColorValue = ");
            sb.append(this.configHatColorValue);
            sb.append(" beardStyleID = ");
            sb.append(this.configBeardStyleID);
            sb.append(" beardColorID = ");
            sb.append(this.configBeardColorID);
            sb.append(" beardColorValue = ");
            sb.append(this.configBeardColorValue);
            sb.append(" earringStyleID = ");
            sb.append(this.configEarringStyleID);
            sb.append(" eyelashStyleID = ");
            sb.append(this.configEyelashStyleID);
            sb.append(" eyebrowColorID = ");
            sb.append(this.configEyebrowColorID);
            sb.append(" eyebrowColorValue = ");
            sb.append(this.configEyebrowColorValue);
            sb.append(" faceShapeID = ");
            sb.append(this.configFaceShapeID);
            sb.append(" faceShapeValue = ");
            sb.append(this.configFaceShapeValue);
            sb.append(" eyeShapeID = ");
            sb.append(this.configEyeShapeID);
            sb.append(" eyeShapeValue = ");
            sb.append(this.configEyeShapeValue);
            sb.append(" mouthShapeID = ");
            sb.append(this.configMouthShapeID);
            sb.append(" mouthShapeValue = ");
            sb.append(this.configMouthShapeValue);
            sb.append(" noseShapeID = ");
            sb.append(this.configNoseShapeID);
            sb.append(" noseShapeValue = ");
            sb.append(this.configNoseShapeValue);
            sb.append(" earShapeID = ");
            sb.append(this.configEarShapeID);
            sb.append(" earShapeValue = ");
            sb.append(this.configEarShapeValue);
            sb.append(" eyebrowShapeID = ");
            sb.append(this.configEyebrowShapeID);
            sb.append(" eyebrowShapeValue = ");
            sb.append(this.configEyebrowShapeValue);
            sb.append("\n");
            return sb.toString();
        }
    }

    public interface ASAvatarOutLineStatusCode {
        public static final int STATUS_FACE_BEYOND_20_DEGREES = 9;
        public static final int STATUS_FACE_OCCLUSION = 6;
        public static final int STATUS_FACE_TOO_BIG = 7;
        public static final int STATUS_FACE_TOO_SMALL = 8;
        public static final int STATUS_LEFT_EYES_OCCLUSION = 2;
        public static final int STATUS_MOUTH_OCCLUSION = 4;
        public static final int STATUS_MULTIPLE_FACES = 10;
        public static final int STATUS_NORMAL = 0;
        public static final int STATUS_NOSE_OCCLUSION = 5;
        public static final int STATUS_NO_FACE = 1;
        public static final int STATUS_RIGHT_EYES_OCCLUSION = 3;
    }

    public static class ASAvatarProcessInfo {
        private static final float F_THRESHOLD = 0.5f;
        private static final int Max_Express_Num = 69;
        private static final int Max_Outline_Num = 154;
        private static final float OUTLINE_THRESHOLD_VALUE = 0.8f;
        private float[] expWeights;
        private ASRect face;
        private int faceCount;
        private float[] faceOrientations;
        private boolean isMirror;
        private int orientation;
        private float[] orientationLeftEyes;
        private float[] orientationRightEyes;
        private float[] orientations;
        private ASPointF[] outlines = new ASPointF[154];
        private int processHeight;
        private int processWidth;
        private int result;
        private float[] shelterFlags;
        private float zoomInScale;

        public boolean checkFaceBlocking() {
            float f2 = 0.0f;
            int i = 0;
            float f3 = 0.0f;
            float f4 = 0.0f;
            float f5 = 0.0f;
            float f6 = 0.0f;
            float f7 = 0.0f;
            float f8 = 0.0f;
            float f9 = 0.0f;
            float f10 = 0.0f;
            while (true) {
                float[] fArr = this.shelterFlags;
                if (i >= fArr.length) {
                    break;
                }
                if (i >= 0 && i <= 18) {
                    f7 += fArr[i];
                } else if (i >= 19 && i <= 36) {
                    f8 += this.shelterFlags[i];
                } else if (i >= 37 && i <= 46) {
                    f9 += this.shelterFlags[i];
                } else if (i >= 47 && i <= 56) {
                    f10 += this.shelterFlags[i];
                } else if (i >= 57 && i <= 68) {
                    f3 += this.shelterFlags[i];
                } else if (i >= 69 && i <= 80) {
                    f4 += this.shelterFlags[i];
                } else if (i >= 81 && i <= 92) {
                    f5 += this.shelterFlags[i];
                } else if (i >= 93 && i <= 112) {
                    f6 += this.shelterFlags[i];
                }
                i++;
            }
            for (int i2 = 7; i2 <= 29; i2++) {
                f2 += this.shelterFlags[i2];
            }
            float f11 = f7 / 19.0f;
            float f12 = f8 / 18.0f;
            float f13 = f9 / 10.0f;
            float f14 = f10 / 10.0f;
            float f15 = f3 / 12.0f;
            float f16 = f4 / 12.0f;
            float f17 = f5 / 12.0f;
            float f18 = f6 / 20.0f;
            float f19 = f2 / 23.0f;
            StringBuilder sb = new StringBuilder();
            sb.append("leftFace = ");
            sb.append(f11);
            String str = "CheckOutLine";
            LOG.d(str, sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("rightFace = ");
            sb2.append(f12);
            LOG.d(str, sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append("leftEyeBrow = ");
            sb3.append(f13);
            LOG.d(str, sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append("rightEyeBrow = ");
            sb4.append(f14);
            LOG.d(str, sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append("leftEye = ");
            sb5.append(f15);
            LOG.d(str, sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append("rightEye = ");
            sb6.append(f16);
            LOG.d(str, sb6.toString());
            StringBuilder sb7 = new StringBuilder();
            sb7.append("nose = ");
            sb7.append(f17);
            LOG.d(str, sb7.toString());
            StringBuilder sb8 = new StringBuilder();
            sb8.append("mouth = ");
            sb8.append(f18);
            LOG.d(str, sb8.toString());
            StringBuilder sb9 = new StringBuilder();
            sb9.append("chin = ");
            sb9.append(f19);
            LOG.d(str, sb9.toString());
            if (f11 > 0.5f && f13 > 0.5f && f15 > 0.5f) {
                LOG.d(str, "--- > left is blocking <---");
                return true;
            } else if (f12 > 0.5f && f14 > 0.5f && f16 > 0.5f) {
                LOG.d(str, "--- > right is blocking <---");
                return true;
            } else if (f13 <= 0.4f || f14 <= 0.4f || f16 <= 0.4f || f15 <= 0.4f) {
                int i3 = (f19 > 0.4f ? 1 : (f19 == 0.4f ? 0 : -1));
                if (i3 > 0 && f18 > 0.4f && f17 > 0.4f) {
                    LOG.d(str, "--- > central is blocking <---");
                    return true;
                } else if (f11 <= 0.4f || f12 <= 0.4f || i3 <= 0) {
                    return false;
                } else {
                    LOG.d(str, "--- > left & right is blocking <---");
                    return true;
                }
            } else {
                LOG.d(str, "--- > top is blocking <---");
                return true;
            }
        }

        public int checkOutLineInfo() {
            int i;
            float[] fArr = this.faceOrientations;
            float f2 = fArr[0];
            float f3 = fArr[1];
            int i2 = 2;
            float f4 = fArr[2];
            if (((f2 < -110.0f || f2 > -70.0f) && ((f2 < -20.0f || f2 > 20.0f) && ((f2 < 160.0f || f2 > 180.0f) && ((f2 < -180.0f || f2 > -160.0f) && (f2 < 70.0f || f2 > 110.0f))))) || -20.0f > f3 || f3 > 20.0f || -20.0f > f4 || f4 > 20.0f) {
                return 9;
            }
            float f5 = 0.0f;
            float f6 = 0.0f;
            for (int i3 = 0; i3 <= 36; i3++) {
                f6 += this.shelterFlags[i3];
            }
            float f7 = f6 / 36.0f;
            StringBuilder sb = new StringBuilder();
            sb.append("fFaceValue = ");
            sb.append(f7);
            String str = "CheckOutLine";
            LOG.d(str, sb.toString());
            if (f7 > OUTLINE_THRESHOLD_VALUE) {
                return 6;
            }
            float f8 = 0.0f;
            for (int i4 = 69; i4 <= 80; i4++) {
                f8 += this.shelterFlags[i4];
            }
            float f9 = f8 / 12.0f;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("fLeftEyeValue = ");
            sb2.append(f9);
            LOG.d(str, sb2.toString());
            float f10 = 0.0f;
            for (int i5 = 57; i5 <= 68; i5++) {
                f10 += this.shelterFlags[i5];
            }
            float f11 = f10 / 12.0f;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("fRightEyeValue = ");
            sb3.append(f11);
            LOG.d(str, sb3.toString());
            if (f11 > f9) {
                i2 = 3;
            } else {
                f11 = f9;
            }
            float f12 = 0.0f;
            for (int i6 = 93; i6 <= 112; i6++) {
                f12 += this.shelterFlags[i6];
            }
            float f13 = f12 / 20.0f;
            StringBuilder sb4 = new StringBuilder();
            sb4.append("fMouthEyeValue = ");
            sb4.append(f13);
            LOG.d(str, sb4.toString());
            if (f13 > f11) {
                i2 = 4;
                f11 = f13;
            }
            for (int i7 = 81; i7 <= 119; i7++) {
                f5 += this.shelterFlags[i7];
            }
            float f14 = f5 / 39.0f;
            StringBuilder sb5 = new StringBuilder();
            sb5.append("fNOSEEyeValue = ");
            sb5.append(f14);
            LOG.d(str, sb5.toString());
            if (f14 > f11) {
                i = 5;
            } else {
                f14 = f11;
                i = i2;
            }
            StringBuilder sb6 = new StringBuilder();
            sb6.append("fMax = ");
            sb6.append(f14);
            sb6.append(" res = ");
            sb6.append(i);
            LOG.d(str, sb6.toString());
            if (f14 <= OUTLINE_THRESHOLD_VALUE) {
                i = 0;
            }
            return i;
        }

        public int getFaceCount() {
            return this.faceCount;
        }

        public void setEmpty() {
            ASPointF[] aSPointFArr;
            this.processHeight = 0;
            this.processWidth = 0;
            this.orientation = 0;
            this.isMirror = false;
            this.faceCount = 0;
            for (ASPointF aSPointF : this.outlines) {
                aSPointF.x = 0.0f;
                aSPointF.y = 0.0f;
            }
            ASRect aSRect = this.face;
            aSRect.bottom = 0;
            aSRect.right = 0;
            aSRect.top = 0;
            aSRect.left = 0;
            Arrays.fill(this.faceOrientations, 0.0f);
            this.result = 0;
            Arrays.fill(this.orientations, 0.0f);
            Arrays.fill(this.orientationLeftEyes, 0.0f);
            Arrays.fill(this.orientationRightEyes, 0.0f);
            Arrays.fill(this.expWeights, 0.0f);
            this.zoomInScale = 0.0f;
        }

        public boolean shelterIsNull() {
            return this.shelterFlags == null;
        }
    }

    public static class ASAvatarProfileInfo implements Serializable {
        private int eyeShape;
        private int faceShape;
        private int gender;
        private int glassType;
        private byte[] hairColor;
        private int hairType;
        private int hasFringe;
        private int mouthShape;
        private int noseShape;
        private byte[] skinColor;
        private int skinColorScale;

        public String getHairType() {
            String str;
            switch (this.hairType) {
                case 0:
                    str = "光寸头";
                    break;
                case 1:
                    str = "直短发";
                    break;
                case 2:
                    str = "卷短发";
                    break;
                case 3:
                    str = "丸子马尾";
                    break;
                case 4:
                    str = "哪吒头";
                    break;
                case 5:
                    str = "直中短发";
                    break;
                case 6:
                    str = "卷中短发";
                    break;
                case 7:
                    str = "直中发";
                    break;
                case 8:
                    str = "卷中发";
                    break;
                case 9:
                    str = "直长发";
                    break;
                case 10:
                    str = "卷长发";
                    break;
                case 11:
                    str = "双马尾";
                    break;
                case 12:
                    str = "双麻花辫";
                    break;
                default:
                    str = "unknow";
                    break;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Hair Type = ");
            sb.append(str);
            return sb.toString();
        }

        public String getHasFringe() {
            return this.hasFringe == 0 ? CameraStat.LOCATION_WITHOUT : CameraStat.LOCATION_WITH;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("gender = ");
            sb.append(this.gender);
            sb.append("\nfaceShape = ");
            sb.append(this.faceShape);
            sb.append("\neyeShape = ");
            sb.append(this.eyeShape);
            sb.append("\nmouthShape = ");
            sb.append(this.mouthShape);
            sb.append("\nnoseShape = ");
            sb.append(this.noseShape);
            sb.append("\nhairType = ");
            sb.append(this.hairType);
            sb.append("\nhasFringe = ");
            sb.append(this.hasFringe);
            sb.append("\nhairColor = ");
            sb.append(Arrays.toString(this.hairColor));
            sb.append("\nskinColor = ");
            sb.append(Arrays.toString(this.skinColor));
            sb.append("\nskinColorScale = ");
            sb.append(this.skinColorScale);
            sb.append("\nglassType = ");
            sb.append(this.glassType);
            return sb.toString();
        }
    }

    public static class ASAvatarProfileResult implements Serializable {
        public int gender;
        public int status;
    }

    public interface ASAvatarProfileStatusCode {
        public static final int STATUS_FAILED_NOFACE = 1;
        public static final int STATUS_SUCCESS_FACESHAPE = 128;
        public static final int STATUS_SUCCESS_FACIAL = 2;
        public static final int STATUS_SUCCESS_GENDER = 16;
        public static final int STATUS_SUCCESS_GLASS = 64;
        public static final int STATUS_SUCCESS_HAIRCOLOR = 8;
        public static final int STATUS_SUCCESS_HAIRSTYLE = 4;
        public static final int STATUS_SUCCESS_SKINCOLOR = 32;
        public static final int STATUS_UNKNOWN = 0;
    }

    public static class ASPointF {
        public float x;
        public float y;
    }

    public static class ASRect {
        public int bottom;
        public int left;
        public int right;
        public int top;
    }

    public interface GetConfigCallback {
        void onGetConfig(int i, int i2, int i3, int i4, String str, String str2, int i5, int i6, boolean z, boolean z2, boolean z3, float f2);
    }

    public interface GetSupportConfigTypeCallback {
        void onGetSupportConfigType(String str, int i);
    }

    public interface UpdateProgressCallback {
        void onUpdateProgress(int i);
    }
}
