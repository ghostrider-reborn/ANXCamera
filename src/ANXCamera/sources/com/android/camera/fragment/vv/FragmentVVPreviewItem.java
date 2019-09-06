package com.android.camera.fragment.vv;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.fragment.BaseViewPagerFragment;
import com.android.camera.ui.TextureVideoView;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.o;
import com.bumptech.glide.request.f;
import java.util.Locale;

public class FragmentVVPreviewItem extends BaseViewPagerFragment implements OnClickListener {
    private OnClickListener mClickListener;
    private boolean mFirstPreviewItem;
    private f mGlideOptions;
    private int mImageHeight;
    private int mImageWidth;
    private int mIndex;
    private boolean mIsPlaying;
    private TextureVideoView mTextureVideoView;
    private boolean mTransitionHide;
    private VVItem mVVItem;

    private String getDurationString(long j) {
        float f2 = ((float) j) / 1000.0f;
        return String.format(Locale.ENGLISH, "%.1f", new Object[]{Float.valueOf(Math.abs(f2))});
    }

    private void initView(View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.vv_preview_item_image);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.vv_preview_item_collapsing);
        this.mTextureVideoView = (TextureVideoView) view.findViewById(R.id.vv_preview_texture_video);
        TextView textView = (TextView) view.findViewById(R.id.vv_preview_item_hint);
        view.setTag(Integer.valueOf(this.mIndex));
        imageView2.setTag(Integer.valueOf(this.mIndex));
        imageView2.setOnClickListener(this);
        if (this.mImageWidth > 0 && this.mImageHeight > 0) {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) imageView.getLayoutParams();
            marginLayoutParams.width = this.mImageWidth;
            marginLayoutParams.height = this.mImageHeight;
            MarginLayoutParams marginLayoutParams2 = (MarginLayoutParams) this.mTextureVideoView.getLayoutParams();
            marginLayoutParams2.width = this.mImageWidth;
            marginLayoutParams2.height = this.mImageHeight;
        }
        ViewCompat.setTransitionName(imageView, this.mVVItem.name);
        this.mTextureVideoView.setVisibility(4);
        this.mTextureVideoView.setLoop(true);
        this.mGlideOptions = new f();
        this.mGlideOptions.q(false);
        this.mGlideOptions.a(o.NONE);
        c.i(getContext()).load(this.mVVItem.coverPath).b(this.mGlideOptions).a(imageView);
        Resources resources = getResources();
        VVItem vVItem = this.mVVItem;
        textView.setText(resources.getString(R.string.vv_duartion_hint, new Object[]{vVItem.name, Integer.valueOf(vVItem.getEssentialFragmentSize()), getDurationString(this.mVVItem.getTotalDuration())}));
    }

    /* access modifiers changed from: private */
    public void startPlay() {
        if (!this.mIsPlaying) {
            this.mIsPlaying = true;
            this.mTextureVideoView.setVideoPath(this.mVVItem.previewVideoPath);
            this.mTextureVideoView.setVisibility(0);
            this.mTextureVideoView.start(0);
        }
    }

    private void stopPlay() {
        if (this.mIsPlaying) {
            this.mIsPlaying = false;
            this.mTextureVideoView.stop();
            this.mTextureVideoView.setVisibility(4);
        }
    }

    public void onClick(View view) {
        onViewCreatedAndJumpOut();
        this.mClickListener.onClick(view);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_vv_preview_item, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    public void onPause() {
        super.onPause();
        stopPlay();
    }

    /* access modifiers changed from: protected */
    public void onViewCreatedAndJumpOut() {
        super.onViewCreatedAndJumpOut();
        stopPlay();
    }

    /* access modifiers changed from: protected */
    public void onViewCreatedAndVisibleToUser(boolean z) {
        super.onViewCreatedAndVisibleToUser(z);
        if (this.mFirstPreviewItem) {
            this.mTextureVideoView.postDelayed(new Runnable() {
                public void run() {
                    if (FragmentVVPreviewItem.this.isVisible()) {
                        FragmentVVPreviewItem.this.startPlay();
                    }
                }
            }, 400);
        } else {
            startPlay();
        }
    }

    public void setData(int i, VVItem vVItem, int i2, int i3, OnClickListener onClickListener, int i4) {
        this.mIndex = i;
        this.mVVItem = vVItem;
        this.mImageWidth = i2;
        this.mImageHeight = i3;
        this.mClickListener = onClickListener;
        boolean z = false;
        this.mTransitionHide = Math.abs(i - i4) == 1;
        if (i == i4) {
            z = true;
        }
        this.mFirstPreviewItem = z;
    }
}
