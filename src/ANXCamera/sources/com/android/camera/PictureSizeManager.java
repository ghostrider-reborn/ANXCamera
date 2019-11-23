package com.android.camera;

import com.android.camera.data.DataRepository;
import java.util.ArrayList;
import java.util.List;

public class PictureSizeManager {
    private static final int LIMIT_PICTURE_SIZE = 0;
    private static final int LIMIT_WIDTH_SIZE = 1;
    private static final ArrayList<CameraSize> sPictureList = new ArrayList<>();

    private static CameraSize _findMaxRatio16_9(List<CameraSize> list) {
        int i = 0;
        int i2 = 0;
        for (CameraSize next : list) {
            if (((double) Math.abs(next.getRatio() - 1.7777777f)) < 0.02d && next.area() > i * i2) {
                i = next.getWidth();
                i2 = next.getHeight();
            }
        }
        return i != 0 ? new CameraSize(i, i2) : new CameraSize();
    }

    private static CameraSize _findMaxRatio18_7_5_9(List<CameraSize> list) {
        int i = 0;
        int i2 = 0;
        for (CameraSize next : list) {
            if (((double) Math.abs(next.getRatio() - 2.0833333f)) < 0.02d && next.area() > i * i2) {
                i = next.getWidth();
                i2 = next.getHeight();
            }
        }
        return i != 0 ? new CameraSize(i, i2) : new CameraSize();
    }

    private static CameraSize _findMaxRatio18_9(List<CameraSize> list) {
        int i = 0;
        int i2 = 0;
        for (CameraSize next : list) {
            if (((double) Math.abs(next.getRatio() - 2.0f)) < 0.02d && next.area() > i * i2) {
                i = next.getWidth();
                i2 = next.getHeight();
            }
        }
        return i != 0 ? new CameraSize(i, i2) : new CameraSize();
    }

    private static CameraSize _findMaxRatio19_5_9(List<CameraSize> list) {
        int i = 0;
        int i2 = 0;
        for (CameraSize next : list) {
            if (((double) Math.abs(next.getRatio() - 2.1666667f)) < 0.02d && next.area() > i * i2) {
                i = next.getWidth();
                i2 = next.getHeight();
            }
        }
        return i != 0 ? new CameraSize(i, i2) : new CameraSize();
    }

    private static CameraSize _findMaxRatio19_9(List<CameraSize> list) {
        int i = 0;
        int i2 = 0;
        for (CameraSize next : list) {
            if (((double) Math.abs(next.getRatio() - 2.1111112f)) < 0.02d && next.area() > i * i2) {
                i = next.getWidth();
                i2 = next.getHeight();
            }
        }
        return i != 0 ? new CameraSize(i, i2) : new CameraSize();
    }

    private static CameraSize _findMaxRatio1_1(List<CameraSize> list) {
        int i = 0;
        int i2 = 0;
        for (CameraSize next : list) {
            if (((double) Math.abs(next.getRatio() - 1.0f)) < 0.02d && next.area() > i * i2) {
                i = next.getWidth();
                i2 = next.getHeight();
            }
        }
        return i != 0 ? new CameraSize(i, i2) : new CameraSize();
    }

    private static CameraSize _findMaxRatio20_9(List<CameraSize> list) {
        int i = 0;
        int i2 = 0;
        for (CameraSize next : list) {
            if (((double) Math.abs(next.getRatio() - 2.2222223f)) < 0.02d && next.area() > i * i2) {
                i = next.getWidth();
                i2 = next.getHeight();
            }
        }
        return i != 0 ? new CameraSize(i, i2) : new CameraSize();
    }

    private static CameraSize _findMaxRatio4_3(List<CameraSize> list) {
        int i = 0;
        int i2 = 0;
        for (CameraSize next : list) {
            if (((double) Math.abs(next.getRatio() - 1.3333333f)) < 0.02d && next.area() > i * i2) {
                i = next.getWidth();
                i2 = next.getHeight();
            }
        }
        return i != 0 ? new CameraSize(i, i2) : new CameraSize();
    }

    public static CameraSize getBestPanoPictureSize() {
        CameraSize cameraSize;
        if (CameraSettings.isAspectRatio4_3(Util.sWindowWidth, Util.sWindowHeight)) {
            cameraSize = _findMaxRatio4_3(sPictureList);
        } else if (CameraSettings.isAspectRatio18_9(Util.sWindowWidth, Util.sWindowHeight)) {
            cameraSize = _findMaxRatio18_9(sPictureList);
            if (cameraSize.isEmpty()) {
                cameraSize = _findMaxRatio16_9(sPictureList);
            }
        } else {
            cameraSize = _findMaxRatio16_9(sPictureList);
        }
        return cameraSize.isEmpty() ? new CameraSize(sPictureList.get(0).width, sPictureList.get(0).height) : cameraSize;
    }

    public static CameraSize getBestPictureSize() {
        return getBestPictureSize((List<CameraSize>) sPictureList);
    }

    public static CameraSize getBestPictureSize(float f) {
        if (sPictureList.isEmpty()) {
            return new CameraSize();
        }
        CameraSize cameraSize = null;
        if (((double) Math.abs(f - 1.7777777f)) < 0.02d) {
            cameraSize = _findMaxRatio16_9(sPictureList);
        } else if (((double) Math.abs(f - 1.3333333f)) < 0.02d) {
            cameraSize = _findMaxRatio4_3(sPictureList);
        } else if (((double) Math.abs(f - 1.0f)) < 0.02d) {
            cameraSize = _findMaxRatio1_1(sPictureList);
        } else if (((double) Math.abs(f - 2.0f)) < 0.02d) {
            cameraSize = _findMaxRatio18_9(sPictureList);
        } else if (((double) Math.abs(f - 2.1111112f)) < 0.02d) {
            cameraSize = _findMaxRatio19_9(sPictureList);
        } else if (((double) Math.abs(f - 2.1666667f)) < 0.02d) {
            cameraSize = _findMaxRatio19_5_9(sPictureList);
        } else if (((double) Math.abs(f - 2.2222223f)) < 0.02d) {
            cameraSize = _findMaxRatio20_9(sPictureList);
        }
        return (cameraSize == null || cameraSize.isEmpty()) ? new CameraSize(sPictureList.get(0).width, sPictureList.get(0).height) : cameraSize;
    }

    public static CameraSize getBestPictureSize(List<CameraSize> list) {
        if (list == null || list.isEmpty()) {
            return new CameraSize();
        }
        float ratio = Util.getRatio(CameraSettings.getPictureSizeRatioString());
        CameraSize cameraSize = null;
        if (((double) Math.abs(ratio - 1.7777777f)) < 0.02d) {
            cameraSize = _findMaxRatio16_9(list);
        } else if (((double) Math.abs(ratio - 1.3333333f)) < 0.02d) {
            cameraSize = _findMaxRatio4_3(list);
        } else if (((double) Math.abs(ratio - 1.0f)) < 0.02d) {
            cameraSize = _findMaxRatio1_1(list);
        } else if (((double) Math.abs(ratio - 2.0f)) < 0.02d) {
            cameraSize = _findMaxRatio18_9(list);
        } else if (((double) Math.abs(ratio - 2.1111112f)) < 0.02d) {
            cameraSize = _findMaxRatio19_9(list);
        } else if (((double) Math.abs(ratio - 2.1666667f)) < 0.02d) {
            cameraSize = _findMaxRatio19_5_9(list);
        } else if (((double) Math.abs(ratio - 2.0833333f)) < 0.02d) {
            cameraSize = _findMaxRatio18_7_5_9(list);
        } else if (((double) Math.abs(ratio - 2.2222223f)) < 0.02d) {
            cameraSize = _findMaxRatio20_9(list);
        }
        return (cameraSize == null || cameraSize.isEmpty()) ? new CameraSize(list.get(0).width, list.get(0).height) : cameraSize;
    }

    public static CameraSize getBestSquareSize(List<CameraSize> list, int i) {
        int i2 = 0;
        if (list == null || list.isEmpty()) {
            return new CameraSize(0, 0);
        }
        for (CameraSize next : list) {
            if (next.getWidth() == next.getHeight() && ((i <= 0 || i >= next.getWidth()) && i2 < next.getWidth())) {
                i2 = next.getWidth();
            }
        }
        return new CameraSize(i2, i2);
    }

    public static void initialize(List<CameraSize> list, int i, int i2, int i3) {
        initializeBase(list, 0, i, i2, i3);
    }

    static void initializeBase(List<CameraSize> list, int i, int i2, int i3, int i4) {
        sPictureList.clear();
        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException("The supported picture size list return from hal is null!");
        }
        DataRepository.dataItemConfig().getComponentConfigRatio().initSensorRatio(list, i3, i4);
        if (i2 != 0) {
            ArrayList arrayList = new ArrayList();
            switch (i) {
                case 0:
                    for (CameraSize next : list) {
                        if (next.area() <= i2) {
                            arrayList.add(next);
                        }
                    }
                    break;
                case 1:
                    for (CameraSize next2 : list) {
                        if (next2.width <= i2) {
                            arrayList.add(next2);
                        }
                    }
                    break;
            }
            list = arrayList;
        }
        CameraSize _findMaxRatio4_3 = _findMaxRatio4_3(list);
        if (!_findMaxRatio4_3.isEmpty()) {
            sPictureList.add(_findMaxRatio4_3);
        }
        CameraSize _findMaxRatio1_1 = _findMaxRatio1_1(list);
        if (!_findMaxRatio1_1.isEmpty()) {
            sPictureList.add(_findMaxRatio1_1);
        }
        CameraSize _findMaxRatio16_9 = _findMaxRatio16_9(list);
        if (!_findMaxRatio16_9.isEmpty()) {
            sPictureList.add(_findMaxRatio16_9);
        }
        CameraSize _findMaxRatio18_9 = _findMaxRatio18_9(list);
        if (!_findMaxRatio18_9.isEmpty()) {
            sPictureList.add(_findMaxRatio18_9);
        }
        CameraSize _findMaxRatio19_9 = _findMaxRatio19_9(list);
        if (!_findMaxRatio19_9.isEmpty()) {
            sPictureList.add(_findMaxRatio19_9);
        }
        CameraSize _findMaxRatio19_5_9 = _findMaxRatio19_5_9(list);
        if (!_findMaxRatio19_5_9.isEmpty()) {
            sPictureList.add(_findMaxRatio19_5_9);
        }
        CameraSize _findMaxRatio18_7_5_9 = _findMaxRatio18_7_5_9(list);
        if (!_findMaxRatio18_7_5_9.isEmpty()) {
            sPictureList.add(_findMaxRatio18_7_5_9);
        }
        CameraSize _findMaxRatio20_9 = _findMaxRatio20_9(list);
        if (!_findMaxRatio20_9.isEmpty()) {
            sPictureList.add(_findMaxRatio20_9);
        }
        if (sPictureList.size() == 0) {
            throw new IllegalArgumentException("Not find the desire picture sizes!");
        }
    }

    public static void initializeLimitWidth(List<CameraSize> list, int i, int i2, int i3) {
        initializeBase(list, 1, i, i2, i3);
    }
}
