package com.android.camera.fragment.mimoji;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.log.Log;
import com.android.camera.ui.autoselectview.AutoSelectAdapter;
import com.android.camera.ui.autoselectview.AutoSelectHorizontalView;
import com.android.camera.ui.autoselectview.AutoSelectViewHolder;
import com.arcsoft.avatar.AvatarConfig;
import java.util.ArrayList;

public class MimojiTypeAdapter extends AutoSelectAdapter<MimojiTypeBean> {
    private OnSelectListener onSelectListener;

    public interface OnSelectListener {
        void onSelectListener(AvatarConfig.ASAvatarConfigType aSAvatarConfigType, int i);
    }

    public static class TypeViewHolder extends AutoSelectViewHolder<MimojiTypeBean> {
        private TextView typeView;

        TypeViewHolder(View view) {
            super(view);
            this.typeView = (TextView) view.findViewById(R.id.tv_type);
        }

        public TextView getTypeView() {
            return this.typeView;
        }

        public void setData(MimojiTypeBean mimojiTypeBean, final int i) {
            String replaceTabTitle = AvatarEngineManager.replaceTabTitle(this.itemView.getContext(), mimojiTypeBean.getASAvatarConfigType().configType);
            TextView textView = this.typeView;
            textView.setText(replaceTabTitle + "");
            TextView textView2 = this.typeView;
            boolean z = true;
            if (mimojiTypeBean.getAlpha() != 1) {
                z = false;
            }
            setSelectState(textView2, z);
            this.typeView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (TypeViewHolder.this.itemView != null && TypeViewHolder.this.itemView.getParent() != null) {
                        try {
                            ((AutoSelectHorizontalView) TypeViewHolder.this.itemView.getParent()).moveToPosition(i);
                        } catch (ClassCastException e) {
                            Log.e(Log.VIEW_TAG, "recyclerview 类型不正确");
                        }
                    }
                }
            });
        }
    }

    public MimojiTypeAdapter(ArrayList<MimojiTypeBean> arrayList) {
        super(arrayList);
    }

    @NonNull
    public AutoSelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TypeViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mimoij_type_item, viewGroup, false));
    }

    public void onSelectedPositionFinish(int i) {
        if (this.onSelectListener != null && i > -1 && i < getItemCount()) {
            this.onSelectListener.onSelectListener(((MimojiTypeBean) getDataList().get(i)).getASAvatarConfigType(), i);
        }
    }

    public void setOnSelectListener(OnSelectListener onSelectListener2) {
        this.onSelectListener = onSelectListener2;
    }
}
