package com.android.camera.module.impl.component;

import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import com.android.camera.CameraAppImpl;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol.BottomPopupTips;
import com.android.camera.protocol.ModeProtocol.CameraClickObservable;
import com.android.camera.protocol.ModeProtocol.CameraClickObservable.ClickObserver;
import com.android.camera.protocol.ModeProtocol.TopAlert;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CameraClickObservableImpl implements CameraClickObservable {
    private List<Integer[]> mBottomTipObservableArray = new ArrayList();
    private List<ClickObserver> mBottomTipObserverArray = new ArrayList();
    private List<Integer[]> mBottomTipTipMsgArray = new ArrayList();

    public static CameraClickObservableImpl create() {
        return new CameraClickObservableImpl();
    }

    public void addObservable(int[] iArr, ClickObserver clickObserver, int... iArr2) {
        if (iArr2 != null) {
            Integer[] numArr = new Integer[iArr2.length];
            int length = iArr2.length;
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                int i4 = i3 + 1;
                numArr[i3] = Integer.valueOf(iArr2[i2]);
                i2++;
                i3 = i4;
            }
            this.mBottomTipObservableArray.add(numArr);
            Integer[] numArr2 = new Integer[iArr.length];
            int length2 = iArr.length;
            int i5 = 0;
            while (i < length2) {
                int i6 = i5 + 1;
                numArr2[i5] = Integer.valueOf(iArr[i]);
                i++;
                i5 = i6;
            }
            this.mBottomTipTipMsgArray.add(numArr2);
            this.mBottomTipObserverArray.add(clickObserver);
        }
    }

    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(227, this);
    }

    public void subscribe(int i) {
        BottomPopupTips bottomPopupTips = (BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        String currentBottomTipMsg = bottomPopupTips != null ? bottomPopupTips.getCurrentBottomTipMsg() : null;
        int i2 = -1;
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        for (Integer[] numArr : this.mBottomTipTipMsgArray) {
            int length = numArr.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    break;
                }
                Integer num = numArr[i3];
                try {
                    String string = resources.getString(num.intValue());
                    if (string != null && string.equals(currentBottomTipMsg)) {
                        i2 = this.mBottomTipTipMsgArray.indexOf(numArr);
                        break;
                    }
                    if (num.intValue() > 0 && topAlert != null && topAlert.isCurrentRecommendTipText(num.intValue())) {
                        i2 = this.mBottomTipTipMsgArray.indexOf(numArr);
                        break;
                    }
                    i3++;
                } catch (NotFoundException unused) {
                }
            }
        }
        if (i2 >= 0 && Arrays.asList((Integer[]) this.mBottomTipObservableArray.get(i2)).contains(Integer.valueOf(i))) {
            ((ClickObserver) this.mBottomTipObserverArray.get(i2)).action();
        }
    }

    public void unRegisterProtocol() {
        this.mBottomTipObservableArray.clear();
        this.mBottomTipObserverArray.clear();
        this.mBottomTipTipMsgArray.clear();
        ModeCoordinatorImpl.getInstance().detachProtocol(227, this);
    }
}
