package com.android.camera.fragment.dialog;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.ss.android.vesdk.VECommonCallback;

public class LiveRecordPreviewFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = "LivePreview";
    private ViewGroup mBottomLayout;

    public int getFragmentInto() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getLayoutResourceId() {
        return R.layout.fragment_live_record_preview;
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.mBottomLayout = (RelativeLayout) view.findViewById(R.id.live_preview_bottom_parent);
        ((ViewGroup.MarginLayoutParams) this.mBottomLayout.getLayoutParams()).bottomMargin = getResources().getDimensionPixelSize(R.dimen.bottom_margin_bottom) + Util.sNavigationBarHeight;
        ((ViewGroup.MarginLayoutParams) this.mBottomLayout.getLayoutParams()).height = Util.getBottomHeight(getResources());
        view.findViewById(R.id.live_preview_play).setOnClickListener(this);
        this.mBottomLayout.findViewById(R.id.live_preview_back).setOnClickListener(this);
        this.mBottomLayout.findViewById(R.id.live_preview_save).setOnClickListener(this);
        this.mBottomLayout.findViewById(R.id.live_preview_share).setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.live_preview_play) {
            ((ModeProtocol.LiveVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(209)).startPlay();
        } else if (id == R.id.live_preview_back) {
            ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        } else if (id == R.id.live_preview_save) {
            ((ModeProtocol.LiveVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(209)).combineVideoAudio((String) null, (VECommonCallback) null, (VECommonCallback) null);
        }
    }
}
