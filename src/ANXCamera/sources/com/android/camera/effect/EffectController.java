package com.android.camera.effect;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.SparseArray;
import com.android.camera.CameraSettings;
import com.android.camera.EffectChangedListenerController;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.effect.renders.BeautificationWrapperRender;
import com.android.camera.effect.renders.FocusPeakingRender;
import com.android.camera.effect.renders.GaussianMaskEffectRender;
import com.android.camera.effect.renders.GradienterEffectRender;
import com.android.camera.effect.renders.GradienterSnapshotEffectRender;
import com.android.camera.effect.renders.NoneEffectRender;
import com.android.camera.effect.renders.PipeRenderPair;
import com.android.camera.effect.renders.RenderGroup;
import com.android.camera.effect.renders.RgbToYuvRender;
import com.android.camera.effect.renders.StickerRender;
import com.android.camera.effect.renders.TiltShiftMaskEffectRender;
import com.android.camera.effect.renders.WrapperRender;
import com.android.camera.effect.renders.XBlurEffectRender;
import com.android.camera.effect.renders.XGaussianEffectRender;
import com.android.camera.effect.renders.XTiltShiftEffectRender;
import com.android.camera.effect.renders.YBlurEffectRender;
import com.android.camera.effect.renders.YGaussianEffectRender;
import com.android.camera.effect.renders.YTiltShiftEffectRender;
import com.android.camera.effect.renders.YuvToRgbRender;
import com.android.camera.fragment.beauty.BeautyParameters;
import com.android.camera.fragment.beauty.LiveBeautyFilterFragment.LiveFilterItem;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.gallery3d.ui.GLCanvas;
import com.mi.config.b;
import com.mi.config.d;
import com.miui.filtersdk.filter.NewBeautificationFilter;
import com.miui.filtersdk.filter.base.BaseOriginalFilter;
import com.miui.filtersdk.filter.helper.FilterFactory;
import com.miui.filtersdk.filter.helper.FilterFactory.FilterScene;
import com.miui.filtersdk.filter.helper.FilterType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EffectController {
    private static final int BLUR_ALPHA_FRAME_NUM = 8;
    public static final int[] EFFECT_ALL_CHANGE_TYPES = {1, 2, 3, 4, 5, 6};
    public static final int EFFECT_CHANGE_BEAUTY = 3;
    public static final int EFFECT_CHANGE_FILTER = 1;
    public static final int EFFECT_CHANGE_FOCUS_PEAK = 4;
    public static final int EFFECT_CHANGE_GRADIENTER = 6;
    public static final int EFFECT_CHANGE_STICKER = 2;
    public static final int EFFECT_CHANGE_TILT = 5;
    private static final int MAX_BLUR_ALPHA = 212;
    private static final String TAG = "EffectController";
    private static EffectController sInstance;
    private boolean mBeautyEnable = false;
    private boolean mBeautyFrameReady = false;
    private boolean mBlur = false;
    private int mBlurStep = -1;
    private List<EffectChangedListener> mChangedListeners = new ArrayList();
    private String mCurrentSticker;
    private float mDeviceRotation;
    private boolean mDrawGradienter;
    private boolean mDrawPeaking;
    private boolean mDrawTilt;
    private int mEffectId = FilterInfo.FILTER_ID_NONE;
    private EffectRectAttribute mEffectRectAttribute = new EffectRectAttribute((AnonymousClass1) null);
    private SparseArray<ArrayList<FilterInfo>> mFilterInfoMap;
    private boolean mIsDrawMainFrame = true;
    private List<LiveFilterItem> mLiveFilters;
    private Object mLock = new Object();
    private boolean mNeedDestroyMakeup = false;
    private int mOrientation;
    private int mOverrideAiEffectIndex = -1;
    private int mOverrideEffectIndex = -1;
    private float mTiltShiftMaskAlpha;

    /* renamed from: com.android.camera.effect.EffectController$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$filtersdk$filter$helper$FilterType = new int[FilterType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(54:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|(3:53|54|56)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(56:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|56) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0056 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0062 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x007a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0086 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0092 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x009e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x00aa */
        /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x00b6 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x00c2 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x00ce */
        /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x00da */
        /* JADX WARNING: Missing exception handler attribute for start block: B:39:0x00e6 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:41:0x00f2 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:43:0x00fe */
        /* JADX WARNING: Missing exception handler attribute for start block: B:45:0x010a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:47:0x0116 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:49:0x0122 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:51:0x012e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:53:0x013a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_LIVELY.ordinal()] = 1;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_FILM.ordinal()] = 2;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_KOIZORA.ordinal()] = 3;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_LATTE.ordinal()] = 4;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_WARM.ordinal()] = 5;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_COOKIE.ordinal()] = 6;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_QUIET.ordinal()] = 7;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_SODA.ordinal()] = 8;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_DELICACY.ordinal()] = 9;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_LIGHT.ordinal()] = 10;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_BERRY.ordinal()] = 11;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_WHITEANDBLACK.ordinal()] = 12;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_FADE.ordinal()] = 13;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.S_FILM.ordinal()] = 14;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.S_YEARS.ordinal()] = 15;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.S_POLAROID.ordinal()] = 16;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.S_FOREST.ordinal()] = 17;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.S_BYGONE.ordinal()] = 18;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.S_WHITEANDBLACK.ordinal()] = 19;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_JAPANESE.ordinal()] = 20;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_ROMANCE.ordinal()] = 21;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_MAZE.ordinal()] = 22;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_MINT.ordinal()] = 23;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_FAIRYTALE.ordinal()] = 24;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_MOOD.ordinal()] = 25;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_PINK.ordinal()] = 26;
            try {
                $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_NATURE.ordinal()] = 27;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EffectChangeType {
    }

    public interface EffectChangedListener {
        void onEffectChanged(int... iArr);
    }

    public static class EffectRectAttribute {
        public int mInvertFlag;
        public PointF mPoint1;
        public PointF mPoint2;
        public float mRangeWidth;
        public RectF mRectF;

        private EffectRectAttribute() {
            this.mRectF = new RectF();
            this.mPoint1 = new PointF();
            this.mPoint2 = new PointF();
        }

        /* synthetic */ EffectRectAttribute(AnonymousClass1 r1) {
            this();
        }

        private EffectRectAttribute(EffectRectAttribute effectRectAttribute) {
            this.mRectF = new RectF();
            this.mPoint1 = new PointF();
            this.mPoint2 = new PointF();
            this.mRectF.set(effectRectAttribute.mRectF);
            this.mPoint1.set(effectRectAttribute.mPoint1);
            this.mPoint2.set(effectRectAttribute.mPoint2);
            this.mInvertFlag = effectRectAttribute.mInvertFlag;
            this.mRangeWidth = effectRectAttribute.mRangeWidth;
        }

        /* synthetic */ EffectRectAttribute(EffectRectAttribute effectRectAttribute, AnonymousClass1 r2) {
            this(effectRectAttribute);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("mRectF=");
            sb.append(this.mRectF);
            sb.append(" mPoint1=");
            sb.append(this.mPoint1);
            sb.append(" mPoint2=");
            sb.append(this.mPoint2);
            sb.append(" mInvertFlag=");
            sb.append(this.mInvertFlag);
            sb.append(" mRangeWidth=");
            sb.append(this.mRangeWidth);
            return sb.toString();
        }
    }

    private EffectController() {
        initialize();
    }

    private FilterScene convertToFilterScene(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 5 ? i != 6 ? FilterScene.NONE : FilterScene.LIGHTING : FilterScene.AI : FilterScene.STICKER : FilterScene.BEAUTY : FilterScene.NORMAL;
    }

    private RenderGroup getAiSceneRenderNew(GLCanvas gLCanvas, RenderGroup renderGroup, boolean z, boolean z2, int i) {
        if (z) {
            getRenderByCategory(gLCanvas, renderGroup, 5, z2);
            return renderGroup;
        }
        getRenderById(gLCanvas, renderGroup, z2, i);
        return renderGroup;
    }

    private RenderGroup getBeautyRender(GLCanvas gLCanvas, RenderGroup renderGroup, boolean z, int i) {
        if (z) {
            getRenderByCategory(gLCanvas, renderGroup, 2, false);
            return renderGroup;
        }
        getRenderById(gLCanvas, renderGroup, false, i);
        return renderGroup;
    }

    private int getDegree(FilterType filterType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$filtersdk$filter$helper$FilterType[filterType.ordinal()];
        if (i == 1) {
            return 60;
        }
        if (!(i == 2 || i == 3 || i == 11)) {
            switch (i) {
                case 6:
                case 7:
                case 9:
                    break;
                case 8:
                    return 70;
                default:
                    switch (i) {
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                            return 50;
                        case 24:
                        case 25:
                            return 60;
                        case 26:
                            return 70;
                        case 27:
                            return 80;
                        default:
                            return 100;
                    }
            }
        }
        return 80;
    }

    public static synchronized EffectController getInstance() {
        EffectController effectController;
        synchronized (EffectController.class) {
            if (sInstance == null) {
                sInstance = new EffectController();
            }
            effectController = sInstance;
        }
        return effectController;
    }

    private RenderGroup getLightingRenderNew(GLCanvas gLCanvas, RenderGroup renderGroup, boolean z, boolean z2, int i) {
        if (z) {
            getRenderByCategory(gLCanvas, renderGroup, 6, z2);
            return renderGroup;
        }
        getRenderById(gLCanvas, renderGroup, z2, i);
        return renderGroup;
    }

    private RenderGroup getMakeupRender(GLCanvas gLCanvas, RenderGroup renderGroup) {
        NewBeautificationFilter newBeautificationFilter = new NewBeautificationFilter();
        ArcsoftBeautyProcessor arcsoftBeautyProcessor = new ArcsoftBeautyProcessor();
        arcsoftBeautyProcessor.setRotation(CameraSettings.isFrontCamera() ? 270 : 90);
        BeautyParameters.getInstance().setMakeupProcessor(arcsoftBeautyProcessor);
        newBeautificationFilter.setBeautyProcessor(arcsoftBeautyProcessor);
        newBeautificationFilter.initBeautyProcessor(Util.sWindowWidth, Util.sWindowHeight);
        renderGroup.addRender(new BeautificationWrapperRender(gLCanvas, FilterInfo.RENDER_ID_MAKEUP, newBeautificationFilter, CameraSettings.isFrontCamera()));
        return renderGroup;
    }

    private RenderGroup getNormalRenderNew(GLCanvas gLCanvas, RenderGroup renderGroup, boolean z, boolean z2, int i) {
        if (z) {
            getRenderByCategory(gLCanvas, renderGroup, 1, z2);
            return renderGroup;
        }
        getRenderById(gLCanvas, renderGroup, z2, i);
        return renderGroup;
    }

    private RenderGroup getPrivateRender(GLCanvas gLCanvas, RenderGroup renderGroup, boolean z, boolean z2, int i) {
        boolean z3;
        GLCanvas gLCanvas2 = gLCanvas;
        RenderGroup renderGroup2 = renderGroup;
        int i2 = i;
        boolean z4 = true;
        if (renderGroup2.getRender(FilterInfo.FILTER_ID_BLUR) != null || (!z && i2 != FilterInfo.FILTER_ID_BLUR && i2 >= 0)) {
            z3 = false;
        } else {
            if (z || i2 == FilterInfo.FILTER_ID_BLUR || renderGroup2.isPartComplete(2)) {
                boolean z5 = renderGroup2.getPartRender(0) != null && (renderGroup2.getPartRender(0) instanceof XBlurEffectRender);
                boolean z6 = renderGroup2.getPartRender(1) != null && (renderGroup2.getPartRender(1) instanceof YBlurEffectRender);
                PipeRenderPair pipeRenderPair = new PipeRenderPair(gLCanvas, FilterInfo.FILTER_ID_BLUR, z5 ? renderGroup2.getPartRender(0) : new XBlurEffectRender(gLCanvas2), z6 ? renderGroup2.getPartRender(1) : new YBlurEffectRender(gLCanvas2), false);
                renderGroup2.addRender(pipeRenderPair);
                if (z5 || z6) {
                    renderGroup.clearPartRenders();
                }
            } else if (renderGroup2.getPartRender(0) == null) {
                renderGroup2.addPartRender(new XBlurEffectRender(gLCanvas2));
            } else if (renderGroup2.getPartRender(1) == null) {
                renderGroup2.addPartRender(new YBlurEffectRender(gLCanvas2));
            }
            z3 = true;
        }
        if (renderGroup2.getRender(FilterInfo.FILTER_ID_GRADIENTER) == null && (z || i2 == FilterInfo.FILTER_ID_GRADIENTER || (i2 < 0 && !z3))) {
            renderGroup2.addRender(z2 ? new GradienterSnapshotEffectRender(gLCanvas2, FilterInfo.FILTER_ID_GRADIENTER) : new GradienterEffectRender(gLCanvas2, FilterInfo.FILTER_ID_GRADIENTER));
            z3 = true;
        }
        if (renderGroup2.getRender(FilterInfo.FILTER_ID_TILTSHIFT) == null && b.xj() && (z || i2 == FilterInfo.FILTER_ID_TILTSHIFT || (i2 < 0 && !z3))) {
            if (z || i2 == FilterInfo.FILTER_ID_TILTSHIFT || renderGroup2.isPartComplete(3)) {
                PipeRenderPair pipeRenderPair2 = new PipeRenderPair(gLCanvas, FilterInfo.FILTER_ID_TILTSHIFT, new PipeRenderPair(gLCanvas2, renderGroup2.getPartRender(0) != null ? renderGroup2.getPartRender(0) : new XTiltShiftEffectRender(gLCanvas2), renderGroup2.getPartRender(1) != null ? renderGroup2.getPartRender(1) : new YTiltShiftEffectRender(gLCanvas2), false), renderGroup2.getPartRender(2) != null ? renderGroup2.getPartRender(2) : new TiltShiftMaskEffectRender(gLCanvas2), false);
                renderGroup2.addRender(pipeRenderPair2);
                renderGroup.clearPartRenders();
            } else if (renderGroup2.getPartRender(0) == null) {
                renderGroup2.addPartRender(new XTiltShiftEffectRender(gLCanvas2));
            } else if (renderGroup2.getPartRender(1) == null) {
                renderGroup2.addPartRender(new YTiltShiftEffectRender(gLCanvas2));
            } else if (renderGroup2.getPartRender(2) == null) {
                renderGroup2.addPartRender(new TiltShiftMaskEffectRender(gLCanvas2));
            }
            z3 = true;
        }
        if (d.getBoolean(d.Go, false) || renderGroup2.getRender(FilterInfo.FILTER_ID_GAUSSIAN) != null || (!z && i2 != FilterInfo.FILTER_ID_GAUSSIAN && (i2 >= 0 || z3))) {
            z4 = z3;
        } else if (z || i2 == FilterInfo.FILTER_ID_GAUSSIAN || renderGroup2.isPartComplete(3)) {
            PipeRenderPair pipeRenderPair3 = new PipeRenderPair(gLCanvas, FilterInfo.FILTER_ID_GAUSSIAN, new PipeRenderPair(gLCanvas2, renderGroup2.getPartRender(0) != null ? renderGroup2.getPartRender(0) : new XGaussianEffectRender(gLCanvas2), renderGroup2.getPartRender(1) != null ? renderGroup2.getPartRender(1) : new YGaussianEffectRender(gLCanvas2), false), renderGroup2.getPartRender(2) != null ? renderGroup2.getPartRender(2) : new GaussianMaskEffectRender(gLCanvas2), false);
            renderGroup2.addRender(pipeRenderPair3);
            renderGroup.clearPartRenders();
        } else if (renderGroup2.getPartRender(0) == null) {
            renderGroup2.addPartRender(new XGaussianEffectRender(gLCanvas2));
        } else if (renderGroup2.getPartRender(1) == null) {
            renderGroup2.addPartRender(new YGaussianEffectRender(gLCanvas2));
        } else if (renderGroup2.getPartRender(2) == null) {
            renderGroup2.addPartRender(new GaussianMaskEffectRender(gLCanvas2));
        }
        if (renderGroup2.getRender(FilterInfo.FILTER_ID_PEAKINGMF) == null && b.qj() && !z2 && (z || i2 == FilterInfo.FILTER_ID_PEAKINGMF || (i2 < 0 && !z4))) {
            renderGroup2.addRender(new FocusPeakingRender(gLCanvas2, FilterInfo.FILTER_ID_PEAKINGMF));
        }
        if (renderGroup2.getRender(FilterInfo.FILTER_ID_STICKER) == null && (z || i2 == FilterInfo.FILTER_ID_STICKER || (i2 < 0 && !z4))) {
            StickerRender stickerRender = new StickerRender(gLCanvas2, FilterInfo.FILTER_ID_STICKER, getCurrentSticker());
            BeautyParameters.getInstance().setStickerMakeupProcessor(stickerRender.getMakeupProcessor());
            renderGroup2.addRender(stickerRender);
        }
        if ((z || i2 == FilterInfo.FILTER_ID_YUV2RGB || (i2 < 0 && !z4)) && renderGroup2.getRender(FilterInfo.FILTER_ID_YUV2RGB) == null) {
            renderGroup2.addRender(new YuvToRgbRender(gLCanvas2, i2));
        }
        if ((z || i2 == FilterInfo.FILTER_ID_RGB2YUV || (i2 < 0 && !z4)) && renderGroup2.getRender(FilterInfo.FILTER_ID_RGB2YUV) == null) {
            renderGroup2.addRender(new RgbToYuvRender(gLCanvas2, i2));
        }
        return renderGroup2;
    }

    private RenderGroup getRenderByCategory(GLCanvas gLCanvas, RenderGroup renderGroup, int i, boolean z) {
        if (convertToFilterScene(i) == FilterScene.NONE) {
            return renderGroup;
        }
        ArrayList filterInfo = getFilterInfo(i);
        if (filterInfo != null) {
            Iterator it = filterInfo.iterator();
            while (it.hasNext()) {
                getRenderById(gLCanvas, renderGroup, z, ((FilterInfo) it.next()).getId());
            }
        }
        return renderGroup;
    }

    private RenderGroup getRenderById(GLCanvas gLCanvas, RenderGroup renderGroup, boolean z, int i) {
        if (i < 0) {
            return renderGroup;
        }
        int i2 = FilterInfo.FILTER_ID_NONE;
        NoneEffectRender noneEffectRender = null;
        if (i != i2 || !renderGroup.isNeedInit(i2)) {
            int i3 = FilterInfo.AI_SCENE_FILTER_ID_0_NONE;
            if (i != i3 || !renderGroup.isNeedInit(i3)) {
                if (renderGroup.getRender(i) == null) {
                    int index = FilterInfo.getIndex(i);
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("getRenderById: index = ");
                    sb.append(index);
                    Log.d(str, sb.toString());
                    if (index > -1 && index < FilterType.values().length) {
                        FilterType filterType = FilterType.values()[index];
                        BaseOriginalFilter filterByType = FilterFactory.getFilterByType(filterType);
                        if (filterByType != null) {
                            filterByType.setDegree(getDegree(filterType));
                        }
                        renderGroup.addRender(new WrapperRender(gLCanvas, i, filterByType));
                    }
                }
                return renderGroup;
            }
            if (z) {
                noneEffectRender = new NoneEffectRender(gLCanvas, FilterInfo.AI_SCENE_FILTER_ID_0_NONE);
            }
            renderGroup.addRender(noneEffectRender);
            return renderGroup;
        }
        if (z) {
            noneEffectRender = new NoneEffectRender(gLCanvas, FilterInfo.FILTER_ID_NONE);
        }
        renderGroup.addRender(noneEffectRender);
        return renderGroup;
    }

    private RenderGroup getStickerRender(GLCanvas gLCanvas, RenderGroup renderGroup, boolean z, int i) {
        if (z) {
            getRenderByCategory(gLCanvas, renderGroup, 3, false);
            return renderGroup;
        }
        getRenderById(gLCanvas, renderGroup, false, i);
        return renderGroup;
    }

    private ArrayList<FilterInfo> initAiSceneFilterInfo() {
        ArrayList<FilterInfo> arrayList = new ArrayList<>();
        FilterType[] filtersByScene = FilterFactory.getFiltersByScene(FilterScene.AI);
        int i = 0;
        arrayList.add(new FilterInfo(FilterInfo.AI_SCENE_FILTER_ID_0_NONE, 0));
        int length = filtersByScene.length;
        int i2 = 1;
        while (i < length) {
            int i3 = i2 + 1;
            arrayList.add(new FilterInfo(FilterInfo.getId(5, filtersByScene[i].ordinal()), i2));
            i++;
            i2 = i3;
        }
        return arrayList;
    }

    private ArrayList<FilterInfo> initLightingFilterInfo() {
        ArrayList<FilterInfo> arrayList = new ArrayList<>();
        FilterType[] filtersByScene = FilterFactory.getFiltersByScene(FilterScene.LIGHTING);
        int i = 0;
        arrayList.add(new FilterInfo(FilterInfo.FILTER_ID_NONE, 0));
        int length = filtersByScene.length;
        int i2 = 1;
        while (i < length) {
            int i3 = i2 + 1;
            arrayList.add(new FilterInfo(FilterInfo.getId(6, filtersByScene[i].ordinal()), i2));
            i++;
            i2 = i3;
        }
        return arrayList;
    }

    private ArrayList<FilterInfo> initNormalFilterInfoNew() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        ArrayList<FilterInfo> arrayList = new ArrayList<>();
        arrayList.add(new FilterInfo(FilterInfo.FILTER_ID_NONE, R.string.pref_camera_coloreffect_entry_none, R.drawable.color_effect_image_none, 0));
        FilterType[] filtersByScene = FilterFactory.getFiltersByScene(FilterScene.NORMAL);
        int length = filtersByScene.length;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        while (i6 < length) {
            FilterType filterType = filtersByScene[i6];
            switch (AnonymousClass1.$SwitchMap$com$miui$filtersdk$filter$helper$FilterType[filterType.ordinal()]) {
                case 1:
                    i3 = 10;
                    i5 = R.string.color_effect_entry_vivid;
                    i4 = R.drawable.color_effect_image_vivid;
                    break;
                case 2:
                    i3 = 20;
                    i5 = R.string.color_effect_entry_film;
                    i4 = R.drawable.color_effect_image_film;
                    break;
                case 3:
                    i3 = 30;
                    i5 = R.string.color_effect_entry_koizora;
                    i4 = R.drawable.color_effect_image_koizora;
                    break;
                case 4:
                    i3 = 40;
                    i5 = R.string.color_effect_entry_latte;
                    i4 = R.drawable.color_effect_image_latte;
                    break;
                case 5:
                    i3 = 50;
                    i5 = R.string.color_effect_entry_warm;
                    i4 = R.drawable.color_effect_image_warm;
                    break;
                case 6:
                    i3 = 60;
                    i5 = R.string.color_effect_entry_cookie;
                    i4 = R.drawable.color_effect_image_cookie;
                    break;
                case 7:
                    i3 = 70;
                    i5 = R.string.color_effect_entry_quiet;
                    i4 = R.drawable.color_effect_image_quiet;
                    break;
                case 8:
                    i3 = 80;
                    i5 = R.string.color_effect_entry_soda;
                    i4 = R.drawable.color_effect_image_soda;
                    break;
                case 9:
                    i3 = 90;
                    i5 = R.string.color_effect_entry_delicacy;
                    i4 = R.drawable.color_effect_image_delicacy;
                    break;
                case 10:
                    i3 = 100;
                    i5 = R.string.color_effect_entry_glimmer;
                    i4 = R.drawable.color_effect_image_glimmer;
                    break;
                case 11:
                    i3 = 110;
                    i5 = R.string.color_effect_entry_berry;
                    i4 = R.drawable.color_effect_image_berry;
                    break;
                case 12:
                    i3 = 120;
                    i5 = R.string.color_effect_entry_blackwhite;
                    i4 = R.drawable.color_effect_image_blackwhite;
                    break;
                case 13:
                    i3 = 130;
                    i5 = R.string.color_effect_entry_fade;
                    i4 = R.drawable.color_effect_image_fade;
                    break;
                default:
                    i2 = i7;
                    i = i8;
                    i3 = i9;
                    break;
            }
            i2 = i5;
            i = i4;
            if (i2 == 0 || i == 0) {
                i8 = i;
            } else {
                FilterInfo filterInfo = new FilterInfo(1, filterType.ordinal(), i2, i, i3);
                arrayList.add(filterInfo);
                i8 = 0;
                i2 = 0;
            }
            i6++;
            i9 = i3;
            i7 = i2;
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private ArrayList<FilterInfo> initPrivateFilterInfo() {
        ArrayList<FilterInfo> arrayList = new ArrayList<>();
        arrayList.add(new FilterInfo(FilterInfo.FILTER_ID_BLUR, 0));
        arrayList.add(new FilterInfo(FilterInfo.FILTER_ID_GRADIENTER, 1));
        FilterInfo filterInfo = new FilterInfo(FilterInfo.FILTER_ID_TILTSHIFT, 2);
        filterInfo.setNeedRect(true);
        arrayList.add(filterInfo);
        FilterInfo filterInfo2 = new FilterInfo(FilterInfo.FILTER_ID_GAUSSIAN, 3);
        filterInfo2.setNeedRect(true);
        arrayList.add(filterInfo2);
        arrayList.add(new FilterInfo(FilterInfo.FILTER_ID_PEAKINGMF, 4));
        arrayList.add(new FilterInfo(FilterInfo.FILTER_ID_STICKER, 5));
        return arrayList;
    }

    private ArrayList<FilterInfo> initStickerFilterInfo() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        ArrayList<FilterInfo> arrayList = new ArrayList<>();
        arrayList.add(new FilterInfo(FilterInfo.FILTER_ID_NONE, R.string.pref_camera_coloreffect_entry_none, R.drawable.video_effect_image_none, 0));
        FilterType[] filtersByScene = FilterFactory.getFiltersByScene(FilterScene.STICKER);
        int length = filtersByScene.length;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        while (i6 < length) {
            FilterType filterType = filtersByScene[i6];
            switch (AnonymousClass1.$SwitchMap$com$miui$filtersdk$filter$helper$FilterType[filterType.ordinal()]) {
                case 14:
                    i3 = 10;
                    i5 = R.string.video_effect_entry_film;
                    i4 = R.drawable.video_effect_image_film;
                    break;
                case 15:
                    i3 = 20;
                    i5 = R.string.video_effect_entry_holiday;
                    i4 = R.drawable.video_effect_image_holiday;
                    break;
                case 16:
                    i3 = 30;
                    i5 = R.string.video_effect_entry_polaroid;
                    i4 = R.drawable.video_effect_image_polaroid;
                    break;
                case 17:
                    i3 = 40;
                    i5 = R.string.video_effect_entry_forest;
                    i4 = R.drawable.video_effect_image_forest;
                    break;
                case 18:
                    i3 = 45;
                    i5 = R.string.video_effect_entry_bygone;
                    i4 = R.drawable.video_effect_image_bygone;
                    break;
                case 19:
                    i3 = 50;
                    i5 = R.string.video_effect_entry_blackwhite;
                    i4 = R.drawable.video_effect_image_blackwhite;
                    break;
                default:
                    i2 = i7;
                    i = i8;
                    i3 = i9;
                    break;
            }
            i2 = i5;
            i = i4;
            if (i2 == 0 || i == 0) {
                i8 = i;
            } else {
                FilterInfo filterInfo = new FilterInfo(3, filterType.ordinal(), i2, i, i3);
                arrayList.add(filterInfo);
                i8 = 0;
                i2 = 0;
            }
            i6++;
            i9 = i3;
            i7 = i2;
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private void initialize() {
        this.mFilterInfoMap = new SparseArray<>(6);
        this.mFilterInfoMap.put(5, initAiSceneFilterInfo());
        this.mFilterInfoMap.put(0, initPrivateFilterInfo());
        this.mFilterInfoMap.put(1, initNormalFilterInfoNew());
        this.mFilterInfoMap.put(6, initLightingFilterInfo());
        if (DataRepository.dataItemFeature().Qc()) {
            this.mFilterInfoMap.put(2, BeautyInfoFactory.initIndiaBeautyFilterInfo());
        } else {
            this.mFilterInfoMap.put(2, BeautyInfoFactory.initBeautyFilterInfo());
        }
        this.mFilterInfoMap.put(3, initStickerFilterInfo());
    }

    private void postNotifyEffectChanged(int... iArr) {
        synchronized (this.mLock) {
            for (EffectChangedListener onEffectChanged : this.mChangedListeners) {
                onEffectChanged.onEffectChanged(iArr);
            }
        }
    }

    public static synchronized void releaseInstance() {
        synchronized (EffectController.class) {
            if (sInstance != null && sInstance.mChangedListeners.size() == 0) {
                sInstance = null;
            }
        }
    }

    public void addChangeListener(EffectChangedListener effectChangedListener) {
        synchronized (this.mLock) {
            this.mChangedListeners.add(effectChangedListener);
            EffectChangedListenerController.addEffectChangedListener(effectChangedListener);
        }
    }

    public void clearEffectAttribute() {
        this.mEffectRectAttribute.mRectF.set(0.0f, 0.0f, 0.0f, 0.0f);
        this.mEffectRectAttribute.mPoint1.set(0.0f, 0.0f);
        this.mEffectRectAttribute.mPoint2.set(0.0f, 0.0f);
        this.mEffectRectAttribute.mRangeWidth = 0.0f;
    }

    public EffectRectAttribute copyEffectRectAttribute() {
        return new EffectRectAttribute(this.mEffectRectAttribute, null);
    }

    public void enableMakeup(boolean z) {
        this.mBeautyEnable = z;
        if (!z) {
            this.mNeedDestroyMakeup = true;
        }
        postNotifyEffectChanged(3);
    }

    public LiveFilterItem findLiveFilter(Context context, int i) {
        List<LiveFilterItem> liveFilterList = getLiveFilterList(context);
        if (liveFilterList == null) {
            return null;
        }
        for (LiveFilterItem liveFilterItem : liveFilterList) {
            if (liveFilterItem.id == i) {
                return liveFilterItem;
            }
        }
        return null;
    }

    public int getBlurAnimationValue() {
        int i = this.mBlurStep;
        if (i >= 0 && i <= 8) {
            this.mBlurStep = i + (this.mBlur ? 1 : -1);
            if (8 <= this.mBlurStep && this.mBlur) {
                this.mOverrideEffectIndex = FilterInfo.FILTER_ID_BLUR;
            }
            int i2 = this.mBlurStep;
            if (i2 >= 0 && i2 <= 8) {
                return (i2 * 212) / 8;
            }
        }
        return -1;
    }

    public String getCurrentSticker() {
        return this.mCurrentSticker;
    }

    public float getDeviceRotation() {
        return this.mDeviceRotation;
    }

    public EffectRectAttribute getEffectAttribute() {
        return this.mEffectRectAttribute;
    }

    public int getEffectCount(int i) {
        ArrayList arrayList = (ArrayList) this.mFilterInfoMap.get(i);
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public int getEffectForPreview(boolean z) {
        synchronized (this) {
            if (z) {
                if (this.mOverrideEffectIndex != -1) {
                    int i = this.mOverrideEffectIndex;
                    return i;
                }
            }
            if (this.mEffectId != FilterInfo.FILTER_ID_NONE || this.mOverrideAiEffectIndex == -1) {
                int i2 = this.mEffectId;
                return i2;
            }
            int i3 = this.mOverrideAiEffectIndex;
            return i3;
        }
    }

    public int getEffectForSaving(boolean z) {
        int i = this.mEffectId;
        return !(i != FilterInfo.FILTER_ID_NONE && FilterInfo.getCategory(i) != 6) ? FilterInfo.FILTER_ID_NONE : getEffectForPreview(z);
    }

    public RenderGroup getEffectGroup(GLCanvas gLCanvas, RenderGroup renderGroup, boolean z, boolean z2, int i) {
        if (!b.tj()) {
            return null;
        }
        if (!z && !renderGroup.isNeedInit(i)) {
            return renderGroup;
        }
        int i2 = 1;
        if (i > -1) {
            i2 = FilterInfo.getCategory(i);
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("getEffectGroup: renderId = ");
        sb.append(i);
        Log.d(str, sb.toString());
        String str2 = TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("getEffectGroup: category = ");
        sb2.append(i2);
        Log.d(str2, sb2.toString());
        switch (i2) {
            case 0:
                getPrivateRender(gLCanvas, renderGroup, z, z2, i);
                break;
            case 1:
                getNormalRenderNew(gLCanvas, renderGroup, z, z2, i);
                break;
            case 2:
                getBeautyRender(gLCanvas, renderGroup, z, i);
                break;
            case 3:
                getStickerRender(gLCanvas, renderGroup, z, i);
                break;
            case 4:
                getMakeupRender(gLCanvas, renderGroup);
                break;
            case 5:
                getAiSceneRenderNew(gLCanvas, renderGroup, z, z2, i);
                break;
            case 6:
                getLightingRenderNew(gLCanvas, renderGroup, z, z2, i);
                break;
            default:
                String str3 = TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("invalid renderId ");
                sb3.append(Integer.toHexString(i));
                Log.e(str3, sb3.toString());
                break;
        }
        return renderGroup;
    }

    public RectF getEffectRectF() {
        return new RectF(this.mEffectRectAttribute.mRectF);
    }

    public ArrayList<FilterInfo> getFilterInfo(int i) {
        return (ArrayList) this.mFilterInfoMap.get(i);
    }

    public int getInvertFlag() {
        return this.mEffectRectAttribute.mInvertFlag;
    }

    public List<LiveFilterItem> getLiveFilterList(Context context) {
        if (this.mLiveFilters == null) {
            TypedArray obtainTypedArray = context.getResources().obtainTypedArray(R.array.live_filter_icon);
            String[] stringArray = context.getResources().getStringArray(R.array.live_filter_name);
            String[] stringArray2 = context.getResources().getStringArray(R.array.live_filter_directory_name);
            this.mLiveFilters = new ArrayList();
            for (int i = 0; i < obtainTypedArray.length(); i++) {
                LiveFilterItem liveFilterItem = new LiveFilterItem();
                liveFilterItem.id = i;
                liveFilterItem.imageViewRes = obtainTypedArray.getDrawable(i);
                liveFilterItem.name = stringArray[i];
                liveFilterItem.directoryName = stringArray2[i];
                this.mLiveFilters.add(liveFilterItem);
            }
            obtainTypedArray.recycle();
        }
        return this.mLiveFilters;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public float getTiltShiftMaskAlpha() {
        return this.mTiltShiftMaskAlpha;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0058 A[ADDED_TO_REGION] */
    public boolean hasEffect() {
        boolean z;
        boolean z2;
        synchronized (this) {
            boolean tj = b.tj();
            z = true;
            boolean z3 = (this.mEffectId == FilterInfo.FILTER_ID_NONE || FilterInfo.getCategory(this.mEffectId) == 6) ? false : true;
            boolean isSquareModule = ModuleManager.isSquareModule();
            boolean isGradienterOn = CameraSettings.isGradienterOn();
            boolean isTiltShiftOn = CameraSettings.isTiltShiftOn();
            boolean Uc = DataRepository.dataItemFeature().Uc();
            boolean isDualCameraWaterMarkOpen = CameraSettings.isDualCameraWaterMarkOpen();
            boolean isFrontCameraWaterMarkOpen = CameraSettings.isFrontCameraWaterMarkOpen();
            boolean isTimeWaterMarkOpen = CameraSettings.isTimeWaterMarkOpen();
            boolean isAgeGenderWaterMarkOpen = CameraSettings.isAgeGenderWaterMarkOpen();
            boolean isMagicMirrorWaterMarkOpen = CameraSettings.isMagicMirrorWaterMarkOpen();
            if (!isDualCameraWaterMarkOpen && !isFrontCameraWaterMarkOpen && !isTimeWaterMarkOpen && !isAgeGenderWaterMarkOpen) {
                if (!isMagicMirrorWaterMarkOpen) {
                    z2 = false;
                    boolean z4 = !Uc && z2;
                    if (tj) {
                        if (!z3 && !isSquareModule && !isGradienterOn && !isTiltShiftOn) {
                            if (z4) {
                            }
                        }
                    }
                    z = false;
                }
            }
            z2 = true;
            if (!Uc) {
            }
            if (tj) {
            }
            z = false;
        }
        return z;
    }

    public boolean isBackGroundBlur() {
        return getEffectForPreview(true) == FilterInfo.FILTER_ID_BLUR;
    }

    public boolean isBeautyFrameReady() {
        return this.mBeautyFrameReady;
    }

    public boolean isBlurAnimationDone() {
        int i = this.mBlurStep;
        return i > 8 || i < 0;
    }

    public boolean isDrawGradienter() {
        return this.mDrawGradienter;
    }

    public boolean isDrawTilt() {
        return this.mDrawTilt;
    }

    public boolean isEffectPageSelected() {
        return this.mEffectId != FilterInfo.FILTER_ID_NONE;
    }

    public boolean isMainFrameDisplay() {
        return this.mIsDrawMainFrame;
    }

    public boolean isMakeupEnable() {
        return this.mBeautyEnable;
    }

    public boolean isNeedDrawPeaking() {
        return this.mDrawPeaking;
    }

    public boolean isNeedRect(int i) {
        ArrayList arrayList = (ArrayList) this.mFilterInfoMap.get(FilterInfo.getCategory(i));
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                FilterInfo filterInfo = (FilterInfo) it.next();
                if (filterInfo.getId() == i) {
                    return filterInfo.isNeedRect();
                }
            }
        }
        return false;
    }

    public boolean isStickerEnable() {
        return !TextUtils.isEmpty(this.mCurrentSticker);
    }

    public boolean needDestroyMakeup() {
        return this.mNeedDestroyMakeup;
    }

    public boolean removeChangeListener(EffectChangedListener effectChangedListener) {
        synchronized (this.mLock) {
            if (this.mChangedListeners.size() == 0) {
                return true;
            }
            boolean remove = this.mChangedListeners.remove(effectChangedListener);
            return remove;
        }
    }

    public void reset() {
        this.mBeautyEnable = false;
        this.mNeedDestroyMakeup = true;
        this.mCurrentSticker = null;
        this.mDrawPeaking = false;
        this.mDrawTilt = false;
        this.mDrawGradienter = false;
        postNotifyEffectChanged(EFFECT_ALL_CHANGE_TYPES);
    }

    public void setAiSceneEffect(int i) {
        if (FilterInfo.getCategory(i) == 5) {
            this.mOverrideAiEffectIndex = i;
        } else if (i == FilterInfo.FILTER_ID_NONE) {
            this.mOverrideAiEffectIndex = -1;
        }
        setEffect(i);
    }

    public void setBeautyFrameReady(boolean z) {
        this.mBeautyFrameReady = z;
        postNotifyEffectChanged(3);
    }

    public void setBlurEffect(boolean z) {
        if (z != this.mBlur) {
            if (!z) {
                this.mOverrideEffectIndex = -1;
            }
            int i = this.mBlurStep;
            int i2 = 8;
            if (i < 0 || 8 < i) {
                if (z) {
                    i2 = 0;
                }
                this.mBlurStep = i2;
            }
            this.mIsDrawMainFrame = true;
        }
        this.mBlur = z;
    }

    public void setCurrentSticker(String str) {
        this.mCurrentSticker = str;
        postNotifyEffectChanged(2);
    }

    public void setDestroyMakeup(boolean z) {
        this.mNeedDestroyMakeup = z;
    }

    public void setDeviceRotation(boolean z, float f2) {
        if (z) {
            f2 = -1.0f;
        }
        this.mDeviceRotation = f2;
    }

    public void setDrawGradienter(boolean z) {
        this.mDrawGradienter = z;
        postNotifyEffectChanged(6);
    }

    public void setDrawPeaking(boolean z) {
        this.mDrawPeaking = z;
        postNotifyEffectChanged(4);
    }

    public void setDrawTilt(boolean z) {
        this.mDrawTilt = z;
        postNotifyEffectChanged(5);
    }

    public void setEffect(int i) {
        synchronized (this) {
            this.mEffectId = i;
            postNotifyEffectChanged(1);
        }
    }

    public void setEffectAttribute(RectF rectF, PointF pointF, PointF pointF2, float f2) {
        this.mEffectRectAttribute.mRectF.set(rectF);
        this.mEffectRectAttribute.mPoint1.set(pointF);
        this.mEffectRectAttribute.mPoint2.set(pointF2);
        this.mEffectRectAttribute.mRangeWidth = f2;
    }

    public void setInvertFlag(int i) {
        this.mEffectRectAttribute.mInvertFlag = i;
    }

    public void setLightingEffect(int i) {
        FilterInfo.getCategory(i);
        setEffect(i);
    }

    public void setOrientation(int i) {
        this.mOrientation = i;
    }

    public void setTiltShiftMaskAlpha(float f2) {
        this.mTiltShiftMaskAlpha = f2;
    }
}
