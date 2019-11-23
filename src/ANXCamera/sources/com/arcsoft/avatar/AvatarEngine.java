package com.arcsoft.avatar;

import com.arcsoft.avatar.AvatarConfig;
import com.arcsoft.avatar.util.ASVLOFFSCREEN;
import com.arcsoft.avatar.util.LOG;
import com.arcsoft.avatar.util.d;
import com.ss.android.ttve.common.TEDefine;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

public class AvatarEngine implements AvatarConfig {

    /* renamed from: a  reason: collision with root package name */
    private static final String f2a = "AvatarEngine";
    /* access modifiers changed from: private */

    /* renamed from: b  reason: collision with root package name */
    public int f3b = -1;
    private int c = -1;
    /* access modifiers changed from: private */
    public TreeMap<String, AvatarConfig.ASAvatarConfigInfo> d = new TreeMap<>();
    private long e = 0;

    static {
        System.loadLibrary("Arcsoft_Avatar_Jni");
        System.loadLibrary(f2a);
    }

    private native int nativeAvatarProcess(long j, int i, int i2, int i3, byte[] bArr, byte[] bArr2, int i4, boolean z, int i5);

    private native int nativeAvatarProcessEx(long j, int i, int i2, int i3, ByteBuffer[] byteBufferArr, byte[] bArr, int i4, boolean z, int i5);

    private native int nativeAvatarProcessEx2(long j, ASVLOFFSCREEN asvloffscreen, byte[] bArr, int i, boolean z, int i2);

    private native int nativeAvatarProfile(long j, String str, int i, int i2, int i3, byte[] bArr, int i4, boolean z, AvatarConfig.ASAvatarProfileResult aSAvatarProfileResult, AvatarConfig.ASAvatarProfileInfo aSAvatarProfileInfo, AvatarConfig.UpdateProgressCallback updateProgressCallback);

    private native int nativeAvatarRender(long j, int i, int i2, int i3, int i4, boolean z, int[] iArr);

    private native long nativeCreate();

    private native int nativeDestroy(long j);

    private native int nativeGetConfig(long j, int i, int i2, AvatarConfig.GetConfigCallback getConfigCallback);

    private native int nativeGetConfigValue(long j, AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue);

    private native int nativeGetSupportConfigType(long j, int i, AvatarConfig.GetSupportConfigTypeCallback getSupportConfigTypeCallback);

    private native int nativeInit(long j, String str, String str2);

    private native boolean nativeIsSupportSwitchGender(long j);

    private native int nativeLoadColorValue(String str);

    private native int nativeLoadConfig(long j, String str);

    private native int nativeOutlineCreateEngine(long j, String str);

    private native int nativeOutlineDestroyEngine(long j);

    private native int nativeOutlineProcess(long j, byte[] bArr, int i, int i2, int i3, int i4, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo);

    private native int nativeOutlineProcessEx(long j, ASVLOFFSCREEN asvloffscreen, int i, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo);

    private native int nativeProcessOutlineExpression(long j, byte[] bArr, int i, int i2, int i3, int i4, boolean z, int i5, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo);

    private native int nativeProcessWithInfo(long j, byte[] bArr, int i, int i2, int i3, int i4, boolean z, int i5, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo);

    private native int nativeProcessWithInfoEx(long j, ASVLOFFSCREEN asvloffscreen, int i, boolean z, int i2, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo, boolean z2);

    private native int nativeReleaseRender(long j);

    private native int nativeRenderBackgroundWithImageData(long j, ASVLOFFSCREEN asvloffscreen, int i, boolean z, int[] iArr);

    private native int nativeRenderBackgroundWithTexture(long j, int i, int i2, boolean z);

    private native int nativeRenderThumb(long j, int i, int i2, int i3, int i4, byte[] bArr, int i5, int i6, int i7, float[] fArr, float f);

    private native int nativeRenderWithBackground(long j, ASVLOFFSCREEN asvloffscreen, int i, boolean z, int i2, int i3, int i4, int i5, boolean z2, int[] iArr, byte[] bArr);

    private native int nativeSaveConfig(long j, String str);

    private native int nativeSetConfig(long j, AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo);

    private native int nativeSetRenderScene(long j, boolean z, float f);

    private native int nativeSetTemplate(long j, String str);

    private native int nativeSwitchGender(long j, boolean z);

    private native int nativeUnInit(long j);

    public synchronized int avatarProcess(int i, int i2, int i3, byte[] bArr, byte[] bArr2, int i4, boolean z, int i5) {
        int nativeAvatarProcess;
        synchronized (this) {
            nativeAvatarProcess = nativeAvatarProcess(this.e, i, i2, i3, bArr, bArr2, i4, z, i5);
        }
        return nativeAvatarProcess;
    }

    public synchronized int avatarProcessEx(int i, int i2, int i3, ByteBuffer[] byteBufferArr, byte[] bArr, int i4, boolean z, int i5) {
        int nativeAvatarProcessEx;
        synchronized (this) {
            nativeAvatarProcessEx = nativeAvatarProcessEx(this.e, i, i2, i3, byteBufferArr, bArr, i4, z, i5);
        }
        return nativeAvatarProcessEx;
    }

    public synchronized int avatarProcessEx2(ASVLOFFSCREEN asvloffscreen, byte[] bArr, int i, boolean z, int i2) {
        return nativeAvatarProcessEx2(this.e, asvloffscreen, bArr, i, z, i2);
    }

    public int avatarProcessWithInfo(byte[] bArr, int i, int i2, int i3, int i4, boolean z, int i5, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        d.a("avatarProcessWithInfo");
        int nativeProcessWithInfo = nativeProcessWithInfo(this.e, bArr, i, i2, i3, i4, z, i5, aSAvatarProcessInfo);
        d.a("performance", "avatarProcessWithInfo");
        return nativeProcessWithInfo;
    }

    public int avatarProcessWithInfoEx(ASVLOFFSCREEN asvloffscreen, int i, boolean z, int i2, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo, boolean z2) {
        return nativeProcessWithInfoEx(this.e, asvloffscreen, i, z, i2, aSAvatarProcessInfo, z2);
    }

    public synchronized int avatarProfile(String str, int i, int i2, int i3, byte[] bArr, int i4, boolean z, AvatarConfig.ASAvatarProfileResult aSAvatarProfileResult, AvatarConfig.ASAvatarProfileInfo aSAvatarProfileInfo, AvatarConfig.UpdateProgressCallback updateProgressCallback) {
        int nativeAvatarProfile;
        synchronized (this) {
            nativeAvatarProfile = nativeAvatarProfile(this.e, str, i, i2, i3, bArr, i4, z, aSAvatarProfileResult, aSAvatarProfileInfo, updateProgressCallback);
        }
        return nativeAvatarProfile;
    }

    public synchronized void avatarRender(int i, int i2, int i3, int i4, boolean z, int[] iArr) {
        nativeAvatarRender(this.e, i, i2, i3, i4, z, iArr);
    }

    public int checkOutlineInfo(AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        if (aSAvatarProcessInfo == null) {
            LOG.d("CheckOutLine", TEDefine.FACE_BEAUTY_NULL);
            return 1;
        }
        LOG.d("CheckOutLine", "faceCount = " + aSAvatarProcessInfo.getFaceCount());
        if (aSAvatarProcessInfo.getFaceCount() > 1) {
            return 10;
        }
        if (aSAvatarProcessInfo.shelterIsNull()) {
            LOG.d("CheckOutLine", "shelterFlags == null");
            return 1;
        } else if (aSAvatarProcessInfo.getFaceCount() <= 0) {
            return 1;
        } else {
            return aSAvatarProcessInfo.checkOutLineInfo();
        }
    }

    public synchronized int createOutlineEngine(String str) {
        return nativeOutlineCreateEngine(this.e, str);
    }

    public synchronized void destroy() {
        d.a("destroy");
        nativeDestroy(this.e);
        this.e = 0;
        d.a("performance", "destroy");
    }

    public synchronized int destroyOutlineEngine() {
        return nativeOutlineDestroyEngine(this.e);
    }

    public synchronized ArrayList<AvatarConfig.ASAvatarConfigInfo> getConfig(final int i, int i2) {
        final ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList;
        arrayList = new ArrayList<>();
        nativeGetConfig(this.e, i, i2, new AvatarConfig.GetConfigCallback() {
            public void onGetConfig(int i, int i2, int i3, int i4, String str, String str2, int i5, int i6, boolean z, boolean z2, boolean z3, float f) {
                AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo = new AvatarConfig.ASAvatarConfigInfo();
                aSAvatarConfigInfo.configID = i;
                aSAvatarConfigInfo.configType = i3;
                aSAvatarConfigInfo.gender = i4;
                aSAvatarConfigInfo.name = str;
                aSAvatarConfigInfo.configThumbPath = str2;
                aSAvatarConfigInfo.isDefault = z;
                aSAvatarConfigInfo.isValid = z2;
                aSAvatarConfigInfo.isSupportContinuous = z3;
                aSAvatarConfigInfo.continuousValue = f;
                aSAvatarConfigInfo.startColorValue = i5;
                aSAvatarConfigInfo.endColorValue = i6;
                if (i3 == 5 && i2 != -1) {
                    String num = new Integer(i2).toString();
                    if (!AvatarEngine.this.d.containsKey(num)) {
                        AvatarEngine.this.d.put(num, aSAvatarConfigInfo);
                    }
                    if (i2 != AvatarEngine.this.f3b) {
                        return;
                    }
                }
                arrayList.add(aSAvatarConfigInfo);
                LOG.d(AvatarEngine.f2a, "type = " + i + " info = " + aSAvatarConfigInfo.toString());
            }
        });
        return arrayList;
    }

    public synchronized void getConfigValue(AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue) {
        nativeGetConfigValue(this.e, aSAvatarConfigValue);
        this.f3b = aSAvatarConfigValue.configFaceColorID;
        this.c = aSAvatarConfigValue.configLipColorID;
    }

    public synchronized ArrayList<AvatarConfig.ASAvatarConfigType> getSupportConfigType(int i) {
        final ArrayList<AvatarConfig.ASAvatarConfigType> arrayList;
        arrayList = new ArrayList<>();
        nativeGetSupportConfigType(this.e, i, new AvatarConfig.GetSupportConfigTypeCallback() {
            public void onGetSupportConfigType(String str, int i) {
                AvatarConfig.ASAvatarConfigType aSAvatarConfigType = new AvatarConfig.ASAvatarConfigType();
                aSAvatarConfigType.configType = i;
                aSAvatarConfigType.configTypeDesc = str;
                arrayList.add(aSAvatarConfigType);
            }
        });
        return arrayList;
    }

    public synchronized void init(String str, String str2) {
        d.a("init");
        this.e = nativeCreate();
        int nativeInit = nativeInit(this.e, str, str2);
        LOG.d(f2a, "init res = " + nativeInit);
        d.a("performance", "init");
    }

    public synchronized boolean isSupportSwitchGender() {
        return nativeIsSupportSwitchGender(this.e);
    }

    public synchronized int loadColorValue(String str) {
        return nativeLoadColorValue(str);
    }

    public synchronized void loadConfig(String str) {
        d.a("loadConfig");
        nativeLoadConfig(this.e, str);
        d.a("performance", "loadConfig");
    }

    public synchronized int outlineProcess(byte[] bArr, int i, int i2, int i3, int i4) {
        AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo;
        aSAvatarProcessInfo = new AvatarConfig.ASAvatarProcessInfo();
        d.a("outlineProcess");
        int nativeOutlineProcess = nativeOutlineProcess(this.e, bArr, i, i2, i3, i4, aSAvatarProcessInfo);
        d.a("performance", "outlineProcess");
        LOG.d("CheckOutLine", "nativeOutlineProcess = " + nativeOutlineProcess);
        return checkOutlineInfo(aSAvatarProcessInfo);
    }

    public synchronized int outlineProcessEx(ASVLOFFSCREEN asvloffscreen, int i) {
        AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo;
        aSAvatarProcessInfo = new AvatarConfig.ASAvatarProcessInfo();
        d.a("outlineProcessEx");
        int nativeOutlineProcessEx = nativeOutlineProcessEx(this.e, asvloffscreen, i, aSAvatarProcessInfo);
        d.a("performance", "outlineProcessEx");
        LOG.d("CheckOutLine", "nativeOutlineProcess = " + nativeOutlineProcessEx);
        return checkOutlineInfo(aSAvatarProcessInfo);
    }

    public synchronized int processOutlineExpression(byte[] bArr, int i, int i2, int i3, int i4, boolean z, int i5, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        int nativeProcessOutlineExpression;
        synchronized (this) {
            nativeProcessOutlineExpression = nativeProcessOutlineExpression(this.e, bArr, i, i2, i3, i4, z, i5, aSAvatarProcessInfo);
        }
        return nativeProcessOutlineExpression;
    }

    public synchronized void releaseRender() {
        d.a("releaseRender");
        nativeReleaseRender(this.e);
        d.a("performance", "releaseRender");
    }

    public int renderBackgroundWithImageData(ASVLOFFSCREEN asvloffscreen, int i, boolean z, int[] iArr) {
        return nativeRenderBackgroundWithImageData(this.e, asvloffscreen, i, z, iArr);
    }

    public int renderBackgroundWithTexture(int i, int i2, boolean z) {
        return nativeRenderBackgroundWithTexture(this.e, i, i2, z);
    }

    public synchronized int renderThumb(int i, int i2, int i3, int i4, byte[] bArr, int i5, int i6, int i7, float[] fArr, float f) {
        int nativeRenderThumb;
        synchronized (this) {
            d.a("renderThumb");
            nativeRenderThumb = nativeRenderThumb(this.e, i, i2, i3, i4, bArr, i5, i6, i7, fArr, f);
            d.a("performance", "renderThumb");
        }
        return nativeRenderThumb;
    }

    public int renderWithBackground(ASVLOFFSCREEN asvloffscreen, int i, boolean z, int i2, int i3, int i4, int i5, boolean z2, int[] iArr, byte[] bArr) {
        return nativeRenderWithBackground(this.e, asvloffscreen, i, z, i2, i3, i4, i5, z2, iArr, bArr);
    }

    public synchronized int saveConfig(String str) {
        return nativeSaveConfig(this.e, str);
    }

    public synchronized int setConfig(AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo) {
        if (aSAvatarConfigInfo.configType == 3) {
            this.f3b = aSAvatarConfigInfo.configID;
            String num = new Integer(this.f3b).toString();
            if (this.d.size() > 0 && this.d.containsKey(num)) {
                boolean z = false;
                Iterator<AvatarConfig.ASAvatarConfigInfo> it = this.d.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    } else if (it.next().configID == this.c) {
                        z = true;
                        break;
                    }
                }
                if (z) {
                    nativeSetConfig(this.e, this.d.get(num));
                }
            }
        } else if (aSAvatarConfigInfo.configType == 5) {
            this.c = aSAvatarConfigInfo.configID;
        }
        return nativeSetConfig(this.e, aSAvatarConfigInfo);
    }

    public synchronized void setRenderScene(boolean z, float f) {
        nativeSetRenderScene(this.e, z, f);
    }

    public synchronized void setTemplatePath(String str) {
        d.a("setTemplatePath");
        nativeSetTemplate(this.e, str);
        d.a("performance", "setTemplatePath");
    }

    public synchronized void switchGender(boolean z) {
        nativeSwitchGender(this.e, z);
    }

    public synchronized void unInit() {
        d.a("unInit");
        int nativeUnInit = nativeUnInit(this.e);
        d.a("performance", "unInit");
        LOG.d(f2a, "uninit res = " + nativeUnInit);
    }
}
