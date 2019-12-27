package com.android.camera.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.constant.ColorConstant;
import com.android.camera.fragment.mimoji.AvatarEngineManager;
import com.android.camera.fragment.mimoji.FragmentMimojiEdit;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.arcsoft.avatar.AvatarConfig;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MimojiTypeSelectView extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private ColorActivateTextView mLastActivateTextView;
    private OnMimojiTypeClickedListener mOnMimojiTypeClickedListener;

    public interface OnMimojiTypeClickedListener {
        void onMimojiTypeClicked(int i);
    }

    public MimojiTypeSelectView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public MimojiTypeSelectView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView();
    }

    public MimojiTypeSelectView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        initView();
    }

    private static final int getChildMeasureWidth(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i = marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
        int measuredWidth = view.getMeasuredWidth();
        if (measuredWidth > 0) {
            return measuredWidth + i;
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        view.measure(makeMeasureSpec, makeMeasureSpec);
        return view.getMeasuredWidth() + i;
    }

    private void scrollTo(int i, List<Completable> list) {
        ((MimojiTypeHorizonScrollView) getParent()).smoothScrollTo(-i, 0);
    }

    private void setSelection(int i, List<Completable> list) {
        ColorActivateTextView colorActivateTextView = this.mLastActivateTextView;
        if (colorActivateTextView != null) {
            colorActivateTextView.setActivated(false);
        }
        ViewGroup viewGroup = (ViewGroup) getChildAt(i);
        View findViewById = viewGroup.findViewById(R.id.mode_select_red_dot);
        if (findViewById.getVisibility() == 0) {
            findViewById.setVisibility(4);
        }
        ColorActivateTextView colorActivateTextView2 = (ColorActivateTextView) viewGroup.findViewById(R.id.text_item_title);
        colorActivateTextView2.setActivated(true);
        if (Util.isAccessible()) {
            colorActivateTextView2.setContentDescription(colorActivateTextView2.getText().toString() + getContext().getString(R.string.accessibility_selected));
            colorActivateTextView2.sendAccessibilityEvent(128);
        }
        this.mLastActivateTextView = colorActivateTextView2;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 += Util.getChildMeasureWidth(getChildAt(i3));
        }
        int childMeasureWidth = (getResources().getDisplayMetrics().widthPixels / 2) - (i2 + (getChildMeasureWidth(viewGroup) / 2));
        if (Util.isLayoutRTL(getContext())) {
            childMeasureWidth = -childMeasureWidth;
        }
        scrollTo(childMeasureWidth, list);
    }

    public void init() {
        Iterator<AvatarConfig.ASAvatarConfigType> it = AvatarEngineManager.getInstance().getConfigTypeList().iterator();
        int i = 0;
        while (it.hasNext()) {
            AvatarConfig.ASAvatarConfigType next = it.next();
            ArrayList<AvatarConfig.ASAvatarConfigInfo> config = AvatarEngineManager.getInstance().queryAvatar().getConfig(next.configType, AvatarEngineManager.getInstance().getASAvatarConfigValue().gender);
            Log.i(FragmentMimojiEdit.TAG, "putConfigList:" + next.configTypeDesc + ":" + next.configType);
            AvatarEngineManager.getInstance().putConfigList(next.configType, config);
            if (!AvatarEngineManager.filterTypeTitle(next.configType)) {
                ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.mimoji_type_select_view, this, false);
                ColorActivateTextView colorActivateTextView = (ColorActivateTextView) viewGroup.findViewById(R.id.text_item_title);
                colorActivateTextView.setActivateColor(ColorConstant.COLOR_COMMON_SELECTED);
                viewGroup.setOnClickListener(this);
                colorActivateTextView.setNormalCor(-1);
                colorActivateTextView.setTypeface(Typeface.defaultFromStyle(1));
                colorActivateTextView.setTextSize(17.5f);
                colorActivateTextView.setText(AvatarEngineManager.replaceTabTitle(this.mContext, next.configType));
                Message obtain = Message.obtain();
                obtain.arg1 = i;
                obtain.arg2 = next.configType;
                viewGroup.setTag(obtain);
                addView(viewGroup);
                i++;
            }
        }
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            if (i2 == 0 || i2 == getChildCount() - 1) {
                View childAt = getChildAt(i2);
                int childMeasureWidth = (getResources().getDisplayMetrics().widthPixels - getChildMeasureWidth(childAt)) / 2;
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                if (i2 == 0) {
                    marginLayoutParams.setMarginStart(childMeasureWidth);
                } else {
                    marginLayoutParams.setMarginEnd(childMeasureWidth);
                }
                childAt.setLayoutParams(marginLayoutParams);
            }
        }
        ModeProtocol.MimojiEditor mimojiEditor = (ModeProtocol.MimojiEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(224);
        if (mimojiEditor != null) {
            mimojiEditor.onTypeConfigSelect(1);
        }
        setSelection(0, (List<Completable>) null);
    }

    public void initView() {
    }

    public void onClick(View view) {
        Message message = (Message) view.getTag();
        int i = message.arg1;
        int i2 = message.arg2;
        setSelection(i, (List<Completable>) null);
        ModeProtocol.MimojiEditor mimojiEditor = (ModeProtocol.MimojiEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(224);
        if (mimojiEditor != null && i2 != AvatarEngineManager.getInstance().getSelectType()) {
            mimojiEditor.onTypeConfigSelect(i2);
        }
    }

    public void setOnModeClickedListener(OnMimojiTypeClickedListener onMimojiTypeClickedListener) {
        this.mOnMimojiTypeClickedListener = onMimojiTypeClickedListener;
    }
}
