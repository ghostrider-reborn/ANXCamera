package com.android.camera.module.impl.component;

import android.content.res.Resources;
import com.android.camera.CameraAppImpl;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CameraClickObservableImpl implements ModeProtocol.CameraClickObservable {
    private List<Integer[]> mBottomTipObservableArray = new ArrayList();
    private List<ModeProtocol.CameraClickObservable.ClickObserver> mBottomTipObserverArray = new ArrayList();
    private List<Integer[]> mBottomTipTipMsgArray = new ArrayList();

    public static CameraClickObservableImpl create() {
        return new CameraClickObservableImpl();
    }

    public void addObservable(int[] iArr, ModeProtocol.CameraClickObservable.ClickObserver clickObserver, int... iArr2) {
        if (iArr2 != null) {
            Integer[] numArr = new Integer[iArr2.length];
            int length = iArr2.length;
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                numArr[i3] = Integer.valueOf(iArr2[i2]);
                i2++;
                i3++;
            }
            this.mBottomTipObservableArray.add(numArr);
            Integer[] numArr2 = new Integer[iArr.length];
            int length2 = iArr.length;
            int i4 = 0;
            while (i < length2) {
                numArr2[i4] = Integer.valueOf(iArr[i]);
                i++;
                i4++;
            }
            this.mBottomTipTipMsgArray.add(numArr2);
            this.mBottomTipObserverArray.add(clickObserver);
        }
    }

    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(227, this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0059, code lost:
        r2 = r8;
     */
    public void subscribe(int i) {
        int indexOf;
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        String currentBottomTipMsg = bottomPopupTips != null ? bottomPopupTips.getCurrentBottomTipMsg() : null;
        int i2 = -1;
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        for (Integer[] next : this.mBottomTipTipMsgArray) {
            int length = next.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    break;
                }
                Integer num = next[i3];
                try {
                    String string = resources.getString(num.intValue());
                    if (string != null && string.equals(currentBottomTipMsg)) {
                        indexOf = this.mBottomTipTipMsgArray.indexOf(next);
                        break;
                    }
                    if (num.intValue() > 0 && topAlert != null && topAlert.isCurrentRecommendTipText(num.intValue())) {
                        indexOf = this.mBottomTipTipMsgArray.indexOf(next);
                        break;
                    }
                    i3++;
                } catch (Resources.NotFoundException e) {
                }
            }
        }
        if (i2 >= 0 && Arrays.asList(this.mBottomTipObservableArray.get(i2)).contains(Integer.valueOf(i))) {
            this.mBottomTipObserverArray.get(i2).action();
        }
    }

    public void unRegisterProtocol() {
        this.mBottomTipObservableArray.clear();
        this.mBottomTipObserverArray.clear();
        this.mBottomTipTipMsgArray.clear();
        ModeCoordinatorImpl.getInstance().detachProtocol(227, this);
    }
}
