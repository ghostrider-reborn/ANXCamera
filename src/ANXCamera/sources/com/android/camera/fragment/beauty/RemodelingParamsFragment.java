package com.android.camera.fragment.beauty;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.android.camera.R;
import com.android.camera.data.data.TypeItem;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol.MakeupProtocol;
import java.util.List;

public class RemodelingParamsFragment extends MakeupParamsFragment {
    public /* synthetic */ void a(AdapterView adapterView, View view, int i, long j) {
        this.mSelectedParam = i;
        String str = ((TypeItem) this.mItemList.get(i)).mKeyOrType;
        MakeupProtocol makeupProtocol = (MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
        if (makeupProtocol != null) {
            makeupProtocol.onMakeupItemSelected(str, true);
        }
    }

    /* access modifiers changed from: protected */
    public String getShineType() {
        return "4";
    }

    /* access modifiers changed from: protected */
    public void initExtraType() {
        this.mHeaderElement = 1;
        this.mFooterElement = -1;
        List<TypeItem> list = this.mItemList;
        if (list != null && !list.isEmpty()) {
            if ("pref_beautify_skin_smooth_ratio_key".equals(((TypeItem) this.mItemList.get(0)).mKeyOrType)) {
                this.mFooterElement = 2;
            }
        }
    }

    /* access modifiers changed from: protected */
    public OnItemClickListener initOnItemClickListener() {
        return new a(this);
    }

    /* access modifiers changed from: protected */
    public void onClearClick() {
        super.onClearClick();
        toast(getResources().getString(R.string.beauty_clear_toast));
    }
}
