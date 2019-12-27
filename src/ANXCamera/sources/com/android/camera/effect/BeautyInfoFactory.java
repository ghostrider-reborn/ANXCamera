package com.android.camera.effect;

import com.android.camera.R;
import com.miui.filtersdk.filter.helper.FilterFactory;
import com.miui.filtersdk.filter.helper.FilterType;
import java.util.ArrayList;
import java.util.Collections;

public class BeautyInfoFactory {

    /* renamed from: com.android.camera.effect.BeautyInfoFactory$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$filtersdk$filter$helper$FilterType = new int[FilterType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(42:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|42) */
        /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
            return;
         */
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
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_NATURE.ordinal()] = 1;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_JAPANESE.ordinal()] = 2;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_PINK.ordinal()] = 3;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_STORY.ordinal()] = 4;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_FAIRYTALE.ordinal()] = 5;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_MAZE.ordinal()] = 6;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_RIDDLE.ordinal()] = 7;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_MOVIE.ordinal()] = 8;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_M_TEA.ordinal()] = 9;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_M_LILT.ordinal()] = 10;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_M_SEPIA.ordinal()] = 11;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_M_WHITEANDBLACK.ordinal()] = 12;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_LIVELY.ordinal()] = 13;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_SODA.ordinal()] = 14;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_FILM.ordinal()] = 15;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_KOIZORA.ordinal()] = 16;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_COOKIE.ordinal()] = 17;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_QUIET.ordinal()] = 18;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_DELICACY.ordinal()] = 19;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_BERRY.ordinal()] = 20;
        }
    }

    public static int getFilterDegree(FilterType filterType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$filtersdk$filter$helper$FilterType[filterType.ordinal()];
        if (i == 1) {
            return 70;
        }
        switch (i) {
            case 13:
                return 60;
            case 14:
                return 70;
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                return 80;
            default:
                return 100;
        }
    }

    public static int getIndiaFilterDegree(FilterType filterType) {
        return 100;
    }

    public static ArrayList<FilterInfo> initBeautyFilterInfo() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        ArrayList<FilterInfo> arrayList = new ArrayList<>();
        arrayList.add(new FilterInfo(FilterInfo.FILTER_ID_NONE, R.string.pref_camera_coloreffect_entry_none, R.drawable.portait_effect_image_none, 0));
        FilterType[] filtersByScene = FilterFactory.getFiltersByScene(FilterFactory.FilterScene.BEAUTY);
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
                    i5 = R.string.portait_effect_entry_nature;
                    i4 = R.drawable.portait_effect_image_nature;
                    break;
                case 2:
                    i3 = 20;
                    i5 = R.string.portait_effect_entry_japanese;
                    i4 = R.drawable.portait_effect_image_japanese;
                    break;
                case 3:
                    i3 = 30;
                    i5 = R.string.portait_effect_entry_pink;
                    i4 = R.drawable.portait_effect_image_pink;
                    break;
                case 4:
                    i3 = 40;
                    i5 = R.string.portait_effect_entry_story;
                    i4 = R.drawable.portait_effect_image_story;
                    break;
                case 5:
                    i3 = 50;
                    i5 = R.string.portait_effect_entry_fairytale;
                    i4 = R.drawable.portait_effect_image_fairytale;
                    break;
                case 6:
                    i3 = 80;
                    i5 = R.string.portait_effect_entry_maze;
                    i4 = R.drawable.portait_effect_image_maze;
                    break;
                case 7:
                    i3 = 100;
                    i5 = R.string.portait_effect_entry_riddle;
                    i4 = R.drawable.portait_effect_image_riddle;
                    break;
                case 8:
                    i3 = 110;
                    i5 = R.string.portait_effect_entry_movie;
                    i4 = R.drawable.portait_effect_image_movie;
                    break;
                case 9:
                    i3 = 120;
                    i5 = R.string.portait_effect_entry_tea;
                    i4 = R.drawable.portait_effect_image_m_tea;
                    break;
                case 10:
                    i3 = 130;
                    i5 = R.string.portait_effect_entry_lilt;
                    i4 = R.drawable.portait_effect_image_m_lilt;
                    break;
                case 11:
                    i3 = 140;
                    i5 = R.string.portait_effect_entry_sepia;
                    i4 = R.drawable.portait_effect_image_m_sepia;
                    break;
                case 12:
                    i3 = 150;
                    i5 = R.string.portait_effect_entry_blackwhite;
                    i4 = R.drawable.portait_effect_image_m_blackwhite;
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
                FilterInfo filterInfo = new FilterInfo(2, filterType.ordinal(), i2, i, i3);
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

    public static ArrayList<FilterInfo> initIndiaBeautyFilterInfo() {
        return new ArrayList<>();
    }
}
