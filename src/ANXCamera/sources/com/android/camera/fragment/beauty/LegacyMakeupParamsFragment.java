package com.android.camera.fragment.beauty;

import android.view.View;
import android.widget.AdapterView;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.miui.filtersdk.beauty.BeautyParameterType;

@Deprecated
public class LegacyMakeupParamsFragment extends MakeupParamsFragment {
    /* access modifiers changed from: protected */
    public AdapterView.OnItemClickListener initOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                LegacyMakeupParamsFragment.this.mSelectedParam = i;
                switch (i) {
                    case 0:
                        BeautyParameterType beautyParameterType = BeautyParameterType.SHRINK_FACE_RATIO;
                        break;
                    case 1:
                        BeautyParameterType beautyParameterType2 = BeautyParameterType.WHITEN_STRENGTH;
                        break;
                    case 2:
                        BeautyParameterType beautyParameterType3 = BeautyParameterType.SMOOTH_STRENGTH;
                        break;
                    default:
                        BeautyParameterType beautyParameterType4 = BeautyParameterType.WHITEN_STRENGTH;
                        break;
                }
                ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
                if (makeupProtocol != null) {
                    makeupProtocol.onMakeupItemSelected((String) null, true);
                }
            }
        };
    }
}
