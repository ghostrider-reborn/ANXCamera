package com.android.camera.fragment.vv;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.o;
import com.bumptech.glide.request.f;
import java.util.Locale;

public class VVGalleryAdapter extends Adapter<CommonRecyclerViewHolder> implements OnClickListener {
    private f mGlideOptions = new f();
    private OnClickListener mParentClickListener;
    private ResourceSelectedListener mResourceSelectedListener;
    private int mSelectedIndex;
    private VVList mVVList;

    public VVGalleryAdapter(VVList vVList, OnClickListener onClickListener, ResourceSelectedListener resourceSelectedListener) {
        this.mVVList = vVList;
        this.mParentClickListener = onClickListener;
        this.mResourceSelectedListener = resourceSelectedListener;
        this.mGlideOptions.q(false);
        this.mGlideOptions.a(o.NONE);
    }

    private String getDurationString(long j) {
        int round = Math.round(((float) j) / 1000.0f);
        return String.format(Locale.ENGLISH, "00:%02d", new Object[]{Integer.valueOf(Math.abs(round))});
    }

    public int getItemCount() {
        VVList vVList = this.mVVList;
        if (vVList != null) {
            return vVList.getSize();
        }
        return 0;
    }

    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
        VVItem vVItem = (VVItem) this.mVVList.getItem(i);
        commonRecyclerViewHolder.itemView.setTag(Integer.valueOf(i));
        commonRecyclerViewHolder.itemView.setOnClickListener(this);
        ImageView imageView = (ImageView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_image);
        ImageView imageView2 = (ImageView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_play);
        ImageView imageView3 = (ImageView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_indicator);
        TextView textView = (TextView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_text);
        TextView textView2 = (TextView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_duration);
        textView.setText(vVItem.name);
        if (this.mSelectedIndex == i) {
            textView.setTextColor(-1);
            imageView2.setVisibility(0);
            imageView3.setVisibility(0);
            textView2.setVisibility(0);
            textView2.setText(getDurationString(vVItem.getTotalDuration()));
        } else {
            textView.setTextColor(textView.getResources().getColor(R.color.vv_gallery_item_text));
            imageView2.setVisibility(8);
            imageView3.setVisibility(8);
            textView2.setVisibility(8);
        }
        ViewCompat.setTransitionName(imageView, vVItem.name);
        c.d(commonRecyclerViewHolder.itemView).load(vVItem.coverPath).b(this.mGlideOptions).a(imageView);
    }

    public void onClick(View view) {
        int intValue = ((Integer) view.getTag()).intValue();
        this.mResourceSelectedListener.onResourceSelected((VVItem) this.mVVList.getItem(intValue));
        if (this.mSelectedIndex != intValue) {
            this.mSelectedIndex = intValue;
            notifyDataSetChanged();
            return;
        }
        this.mParentClickListener.onClick(view);
    }

    @NonNull
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CommonRecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_vv_gallery_item, viewGroup, false));
    }
}
