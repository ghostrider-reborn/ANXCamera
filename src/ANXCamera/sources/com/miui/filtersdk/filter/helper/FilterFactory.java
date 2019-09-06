package com.miui.filtersdk.filter.helper;

import android.util.Log;
import com.miui.filtersdk.filter.base.BaseOriginalFilter;
import com.miui.filtersdk.filter.base.ColorLookupFilter;
import com.miui.filtersdk.filter.base.ColorLookupFilter4x4;
import java.util.ArrayList;

public class FilterFactory {
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_AUTUMN = "filter/aiscene/A-AUTUMN.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_BACKLIGHT = "filter/aiscene/A-GLOBAL-NONE.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_BEACH = "filter/aiscene/A-BEACH.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_BUDDHA = "filter/aiscene/A-BUDDHA.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_BUILDING = "filter/aiscene/A-BUILDING.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_CANDLELIGHT = "filter/aiscene/A-CANDLELIGHT.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_CAR = "filter/aiscene/A-CAR.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_CAT = "filter/aiscene/A-CAT.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_CITY = "filter/aiscene/A-CITY.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_CLOUD = "filter/aiscene/A-CLOUD.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_COW = "filter/aiscene/A-COW.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_CURRY = "filter/aiscene/A-CURRY.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_DOC = "filter/aiscene/A-DOC.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_DOG = "filter/aiscene/A-DOG.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_DRIVING = "filter/aiscene/A-DRIVING.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_FLOWER = "filter/aiscene/A-FLOWER.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_FOOD = "filter/aiscene/A-FOOD.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_GRASS = "filter/aiscene/A-GRASS.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_GREEN_PLANTS = "filter/aiscene/A-GREEN_PLANTS.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_HUMAN = "filter/aiscene/A-HUMAN.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_JEWELRY = "filter/aiscene/A-JEWELRY.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_MAPLE_LEAVES = "filter/aiscene/A-MAPLE_LEAVES.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_MOTORBIKE = "filter/aiscene/A-MOTORBIKE.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_NIGHT = "filter/aiscene/A-NIGHT.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_OVERCAST = "filter/aiscene/A-OVERCAST.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_PPT = "filter/aiscene/A-PPT.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_SEA = "filter/aiscene/A-SEA.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_SILHOUETTE = "filter/aiscene/A-SILHOUETTE.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_SKY = "filter/aiscene/A-SKY.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_SNOW = "filter/aiscene/A-SNOW.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_SUCCULENT = "filter/aiscene/A-SUCCULENT.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_SUNRISE_SUNSET = "filter/aiscene/A-SUNRISE_SUNSET.png";
    private static final String LOOKUP_TABLE_PATH_AI_SCENE_TEMPLE = "filter/aiscene/A-TEMPLE.png";
    private static final String LOOKUP_TABLE_PATH_BEAUTY_FAIRYTALE = "filter/beauty/B-FAIRYTALE.png";
    private static final String LOOKUP_TABLE_PATH_BEAUTY_JAPANESE = "filter/beauty/B-JAPANESE.png";
    private static final String LOOKUP_TABLE_PATH_BEAUTY_MAZE = "filter/beauty/B-MAZE.png";
    private static final String LOOKUP_TABLE_PATH_BEAUTY_MINT = "filter/beauty/B-MINT.png";
    private static final String LOOKUP_TABLE_PATH_BEAUTY_MOOD = "filter/beauty/B-HEART.png";
    private static final String LOOKUP_TABLE_PATH_BEAUTY_MOVIE = "filter/beauty/B-MOVIE.png";
    private static final String LOOKUP_TABLE_PATH_BEAUTY_M_LILT = "filter/beauty/B-M-LILT.png";
    private static final String LOOKUP_TABLE_PATH_BEAUTY_M_SEPIA = "filter/beauty/B-M-SEPIA.png";
    private static final String LOOKUP_TABLE_PATH_BEAUTY_M_TEA = "filter/beauty/B-M-TEA.png";
    private static final String LOOKUP_TABLE_PATH_BEAUTY_M_WHITEANDBLACK = "filter/beauty/B-M-WHITEANDBLACK.png";
    private static final String LOOKUP_TABLE_PATH_BEAUTY_NATURE = "filter/beauty/B-NATURE.png";
    private static final String LOOKUP_TABLE_PATH_BEAUTY_PINK = "filter/beauty/B-PINK.png";
    private static final String LOOKUP_TABLE_PATH_BEAUTY_RIDDLE = "filter/beauty/B-RIDDLE.png";
    private static final String LOOKUP_TABLE_PATH_BEAUTY_ROMANCE = "filter/beauty/B-ROMANCE.png";
    private static final String LOOKUP_TABLE_PATH_BEAUTY_STORY = "filter/beauty/B-STORY.png";
    private static final String LOOKUP_TABLE_PATH_BEAUTY_WHITEANDBLACK = "filter/beauty/B-WHITEANDBLACK.png";
    private static final String LOOKUP_TABLE_PATH_COMMON_WHITEANDBLACK = "filter/common/WHITEANDBLACK.png";
    private static final String LOOKUP_TABLE_PATH_LIGHTING_DOT = "filter/lighting/L-DOT.png";
    private static final String LOOKUP_TABLE_PATH_LIGHTING_HOLI = "filter/lighting/L-HOLI.png";
    private static final String LOOKUP_TABLE_PATH_LIGHTING_LEAF = "filter/lighting/L-LEAF.png";
    private static final String LOOKUP_TABLE_PATH_LIGHTING_MOVIE = "filter/lighting/L-MOVIE.png";
    private static final String LOOKUP_TABLE_PATH_LIGHTING_NATURE = "filter/lighting/L-NATURE.png";
    private static final String LOOKUP_TABLE_PATH_LIGHTING_RAINBOW = "filter/lighting/L-RAINBOW.png";
    private static final String LOOKUP_TABLE_PATH_LIGHTING_SHUTTER = "filter/lighting/L-SHUTTER.png";
    private static final String LOOKUP_TABLE_PATH_LIGHTING_STAGE = "filter/lighting/L-STAGE.png";
    private static final String LOOKUP_TABLE_PATH_NORMAL_BERRY = "filter/normal/N-BERRY.png";
    private static final String LOOKUP_TABLE_PATH_NORMAL_COOKIE = "filter/normal/N-COOKIE.png";
    private static final String LOOKUP_TABLE_PATH_NORMAL_DELICACY = "filter/normal/N-DELICACY.png";
    private static final String LOOKUP_TABLE_PATH_NORMAL_FADE = "filter/normal/N-FADE.png";
    private static final String LOOKUP_TABLE_PATH_NORMAL_FILM = "filter/normal/N-FILM.png";
    private static final String LOOKUP_TABLE_PATH_NORMAL_KOIZORA = "filter/normal/N-KOIZORA.png";
    private static final String LOOKUP_TABLE_PATH_NORMAL_LATTE = "filter/normal/N-LATTE.png";
    private static final String LOOKUP_TABLE_PATH_NORMAL_LIGHT = "filter/normal/N-LIGHT.png";
    private static final String LOOKUP_TABLE_PATH_NORMAL_LIVELY = "filter/normal/N-LIVELY.png";
    private static final String LOOKUP_TABLE_PATH_NORMAL_QUIET = "filter/normal/N-QUIET.png";
    private static final String LOOKUP_TABLE_PATH_NORMAL_SODA = "filter/normal/N-SODA.png";
    private static final String LOOKUP_TABLE_PATH_NORMAL_WARM = "filter/normal/N-WARM.png";
    private static final String LOOKUP_TABLE_PATH_VIDEO_BYGONE = "filter/video/S-BYGONE.png";
    private static final String LOOKUP_TABLE_PATH_VIDEO_FILM = "filter/video/S-FILM.png";
    private static final String LOOKUP_TABLE_PATH_VIDEO_FOREST = "filter/video/S-FOREST.png";
    private static final String LOOKUP_TABLE_PATH_VIDEO_POLAROID = "filter/video/S-POLAROID.png";
    private static final String LOOKUP_TABLE_PATH_VIDEO_YEARS = "filter/video/S-YEARS.png";
    private static final String TAG = "FilterFactory";

    /* renamed from: com.miui.filtersdk.filter.helper.FilterFactory$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$filtersdk$filter$helper$FilterType = new int[FilterType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(152:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64|65|66|67|68|69|70|71|72|73|74|75|76|77|78|79|80|81|82|83|84|85|86|87|88|89|90|91|92|93|94|95|96|97|98|99|100|101|102|103|104|105|106|107|108|109|110|111|112|113|114|115|116|117|118|119|120|121|122|123|124|125|126|127|128|129|130|131|132|133|134|135|136|137|138|139|140|141|142|143|144|145|146|147|148|149|150|(3:151|152|154)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(154:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64|65|66|67|68|69|70|71|72|73|74|75|76|77|78|79|80|81|82|83|84|85|86|87|88|89|90|91|92|93|94|95|96|97|98|99|100|101|102|103|104|105|106|107|108|109|110|111|112|113|114|115|116|117|118|119|120|121|122|123|124|125|126|127|128|129|130|131|132|133|134|135|136|137|138|139|140|141|142|143|144|145|146|147|148|149|150|151|152|154) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:101:0x025a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:103:0x0266 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:105:0x0272 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:107:0x027e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:109:0x028a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:111:0x0296 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:113:0x02a2 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:115:0x02ae */
        /* JADX WARNING: Missing exception handler attribute for start block: B:117:0x02ba */
        /* JADX WARNING: Missing exception handler attribute for start block: B:119:0x02c6 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:121:0x02d2 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:123:0x02de */
        /* JADX WARNING: Missing exception handler attribute for start block: B:125:0x02ea */
        /* JADX WARNING: Missing exception handler attribute for start block: B:127:0x02f6 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:129:0x0302 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:131:0x030e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:133:0x031a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:135:0x0326 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:137:0x0332 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:139:0x033e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:141:0x034a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:143:0x0356 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:145:0x0362 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:147:0x036e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:149:0x037a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:151:0x0386 */
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
        /* JADX WARNING: Missing exception handler attribute for start block: B:55:0x0146 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:57:0x0152 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:59:0x015e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:61:0x016a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:63:0x0176 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:65:0x0182 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:67:0x018e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:69:0x019a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:71:0x01a6 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:73:0x01b2 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:75:0x01be */
        /* JADX WARNING: Missing exception handler attribute for start block: B:77:0x01ca */
        /* JADX WARNING: Missing exception handler attribute for start block: B:79:0x01d6 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:81:0x01e2 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:83:0x01ee */
        /* JADX WARNING: Missing exception handler attribute for start block: B:85:0x01fa */
        /* JADX WARNING: Missing exception handler attribute for start block: B:87:0x0206 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:89:0x0212 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:91:0x021e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:93:0x022a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:95:0x0236 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:97:0x0242 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:99:0x024e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A1_DOC.ordinal()] = 1;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A2_FLOWER.ordinal()] = 2;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A3_FOOD.ordinal()] = 3;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A4_PPT.ordinal()] = 4;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A5_SKY.ordinal()] = 5;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A6_SUNRISE_SUNSET.ordinal()] = 6;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A7_CAT.ordinal()] = 7;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A8_DOG.ordinal()] = 8;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A9_GREEN_PLANTS.ordinal()] = 9;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A10_NIGHT.ordinal()] = 10;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A11_SNOW.ordinal()] = 11;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A12_SEA.ordinal()] = 12;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A13_AUTUMN.ordinal()] = 13;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A14_CANDLELIGHT.ordinal()] = 14;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A15_CAR.ordinal()] = 15;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A16_GRASS.ordinal()] = 16;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A17_MAPLE_LEAVES.ordinal()] = 17;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A18_SUCCULENT.ordinal()] = 18;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A19_BUILDING.ordinal()] = 19;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A20_CITY.ordinal()] = 20;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A21_CLOUD.ordinal()] = 21;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A22_OVERCAST.ordinal()] = 22;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A23_BACKLIGHT.ordinal()] = 23;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A24_SILHOUETTE.ordinal()] = 24;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A25_HUMAN.ordinal()] = 25;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A26_JEWELRY.ordinal()] = 26;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A27_BUDDHA.ordinal()] = 27;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A28_COW.ordinal()] = 28;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A29_CURRY.ordinal()] = 29;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A30_MOTORBIKE.ordinal()] = 30;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A31_TEMPLE.ordinal()] = 31;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A32_BEACH.ordinal()] = 32;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.A33_DRIVING.ordinal()] = 33;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.L_NATURE.ordinal()] = 34;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.L_STAGE.ordinal()] = 35;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.L_MOVIE.ordinal()] = 36;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.L_RAINBOW.ordinal()] = 37;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.L_SHUTTER.ordinal()] = 38;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.L_DOT.ordinal()] = 39;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.L_LEAF.ordinal()] = 40;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.L_HOLI.ordinal()] = 41;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_BERRY.ordinal()] = 42;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_COOKIE.ordinal()] = 43;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_DELICACY.ordinal()] = 44;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_FADE.ordinal()] = 45;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_FILM.ordinal()] = 46;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_KOIZORA.ordinal()] = 47;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_LATTE.ordinal()] = 48;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_LIGHT.ordinal()] = 49;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_LIVELY.ordinal()] = 50;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_QUIET.ordinal()] = 51;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_SODA.ordinal()] = 52;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_WARM.ordinal()] = 53;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_FAIRYTALE.ordinal()] = 54;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_JAPANESE.ordinal()] = 55;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_MINT.ordinal()] = 56;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_MOOD.ordinal()] = 57;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_NATURE.ordinal()] = 58;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_PINK.ordinal()] = 59;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_ROMANCE.ordinal()] = 60;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_MAZE.ordinal()] = 61;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_WHITEANDBLACK.ordinal()] = 62;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_STORY.ordinal()] = 63;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_RIDDLE.ordinal()] = 64;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_MOVIE.ordinal()] = 65;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_M_TEA.ordinal()] = 66;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_M_LILT.ordinal()] = 67;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_M_SEPIA.ordinal()] = 68;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.B_M_WHITEANDBLACK.ordinal()] = 69;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.S_FILM.ordinal()] = 70;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.S_YEARS.ordinal()] = 71;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.S_POLAROID.ordinal()] = 72;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.S_FOREST.ordinal()] = 73;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.S_WHITEANDBLACK.ordinal()] = 74;
            $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.N_WHITEANDBLACK.ordinal()] = 75;
            try {
                $SwitchMap$com$miui$filtersdk$filter$helper$FilterType[FilterType.S_BYGONE.ordinal()] = 76;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public enum FilterScene {
        AI,
        NORMAL,
        BEAUTY,
        STICKER,
        PORTRAIT,
        LIGHTING,
        NONE
    }

    public static int getAllFilterTypeCount() {
        return FilterType.values().length;
    }

    public static FilterType[] getAllFilters() {
        return FilterType.values();
    }

    public static BaseOriginalFilter getFilterByType(FilterType filterType) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("getFilterByType: ");
        sb.append(filterType);
        sb.append("; ordinal = ");
        sb.append(filterType.ordinal());
        Log.d(str, sb.toString());
        switch (AnonymousClass1.$SwitchMap$com$miui$filtersdk$filter$helper$FilterType[filterType.ordinal()]) {
            case 1:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_DOC);
            case 2:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_FLOWER);
            case 3:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_FOOD);
            case 4:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_PPT);
            case 5:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_SKY);
            case 6:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_SUNRISE_SUNSET);
            case 7:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_CAT);
            case 8:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_DOG);
            case 9:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_GREEN_PLANTS);
            case 10:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_NIGHT);
            case 11:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_SNOW);
            case 12:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_SEA);
            case 13:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_AUTUMN);
            case 14:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_CANDLELIGHT);
            case 15:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_CAR);
            case 16:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_GRASS);
            case 17:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_MAPLE_LEAVES);
            case 18:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_SUCCULENT);
            case 19:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_BUILDING);
            case 20:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_CITY);
            case 21:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_CLOUD);
            case 22:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_OVERCAST);
            case 23:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_BACKLIGHT);
            case 24:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_SILHOUETTE);
            case 25:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_HUMAN);
            case 26:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_JEWELRY);
            case 27:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_BUDDHA);
            case 28:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_COW);
            case 29:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_CURRY);
            case 30:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_MOTORBIKE);
            case 31:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_TEMPLE);
            case 32:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_BEACH);
            case 33:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_AI_SCENE_DRIVING);
            case 34:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_LIGHTING_NATURE);
            case 35:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_LIGHTING_STAGE);
            case 36:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_LIGHTING_MOVIE);
            case 37:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_LIGHTING_RAINBOW);
            case 38:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_LIGHTING_SHUTTER);
            case 39:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_LIGHTING_DOT);
            case 40:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_LIGHTING_LEAF);
            case 41:
                return new ColorLookupFilter4x4(LOOKUP_TABLE_PATH_LIGHTING_HOLI);
            case 42:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_NORMAL_BERRY);
            case 43:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_NORMAL_COOKIE);
            case 44:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_NORMAL_DELICACY);
            case 45:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_NORMAL_FADE);
            case 46:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_NORMAL_FILM);
            case 47:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_NORMAL_KOIZORA);
            case 48:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_NORMAL_LATTE);
            case 49:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_NORMAL_LIGHT);
            case 50:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_NORMAL_LIVELY);
            case 51:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_NORMAL_QUIET);
            case 52:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_NORMAL_SODA);
            case 53:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_NORMAL_WARM);
            case 54:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_BEAUTY_FAIRYTALE);
            case 55:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_BEAUTY_JAPANESE);
            case 56:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_BEAUTY_MINT);
            case 57:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_BEAUTY_MOOD);
            case 58:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_BEAUTY_NATURE);
            case 59:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_BEAUTY_PINK);
            case 60:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_BEAUTY_ROMANCE);
            case 61:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_BEAUTY_MAZE);
            case 62:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_BEAUTY_WHITEANDBLACK);
            case 63:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_BEAUTY_STORY);
            case 64:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_BEAUTY_RIDDLE);
            case 65:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_BEAUTY_MOVIE);
            case 66:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_BEAUTY_M_TEA);
            case 67:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_BEAUTY_M_LILT);
            case 68:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_BEAUTY_M_SEPIA);
            case 69:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_BEAUTY_M_WHITEANDBLACK);
            case 70:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_VIDEO_FILM);
            case 71:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_VIDEO_YEARS);
            case 72:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_VIDEO_POLAROID);
            case 73:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_VIDEO_FOREST);
            case 74:
            case 75:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_COMMON_WHITEANDBLACK);
            case 76:
                return new ColorLookupFilter(LOOKUP_TABLE_PATH_VIDEO_BYGONE);
            default:
                return null;
        }
    }

    public static FilterType[] getFiltersByScene(FilterScene filterScene) {
        FilterType[] values;
        ArrayList arrayList = new ArrayList();
        for (FilterType filterType : FilterType.values()) {
            if (filterType.getFilterScene() == filterScene) {
                arrayList.add(filterType);
            }
        }
        return (FilterType[]) arrayList.toArray(new FilterType[arrayList.size()]);
    }
}
