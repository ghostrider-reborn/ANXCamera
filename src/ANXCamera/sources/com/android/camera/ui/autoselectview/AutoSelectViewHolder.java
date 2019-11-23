package com.android.camera.ui.autoselectview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.ui.autoselectview.SelectItemBean;

public abstract class AutoSelectViewHolder<T extends SelectItemBean> extends RecyclerView.ViewHolder {
    public AutoSelectViewHolder(@NonNull View view) {
        super(view);
    }

    public abstract void setData(T t, int i);

    public void setSelectState(TextView textView, boolean z) {
        textView.setTextColor(this.itemView.getContext().getColor(z ? R.color.mimoji_edit_type_text_selected : R.color.mimoji_edit_type_text_normal));
    }

    @Deprecated
    public void setViewAlpha(float f) {
    }
}
