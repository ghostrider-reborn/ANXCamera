package com.android.camera.fragment.mimoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.camera.R;
import com.android.camera.fragment.music.RoundedCornersTransformation;
import com.android.camera.ui.baseview.BaseRecyclerAdapter;
import com.android.camera.ui.baseview.BaseRecyclerViewHolder;
import com.arcsoft.avatar.AvatarConfig.ASAvatarConfigInfo;
import com.bumptech.glide.c;
import com.bumptech.glide.load.j;
import com.bumptech.glide.request.a.c.a;
import com.bumptech.glide.request.f;
import java.util.ArrayList;

public class MimojiThumbnailRecyclerAdapter extends BaseRecyclerAdapter<ASAvatarConfigInfo> {
    private Context mContext;
    private float selectIndex;

    static class ThumbnailViewViewHolder extends BaseRecyclerViewHolder<ASAvatarConfigInfo> {
        ImageView imageView;

        public ThumbnailViewViewHolder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail_image_view);
        }

        /* JADX WARNING: Removed duplicated region for block: B:10:0x006d  */
        /* JADX WARNING: Removed duplicated region for block: B:11:0x007b  */
        public void setData(ASAvatarConfigInfo aSAvatarConfigInfo, int i) {
            new a(300).setCrossFadeEnabled(true).build();
            if (aSAvatarConfigInfo != null) {
                Bitmap bitmap = aSAvatarConfigInfo.thum;
                if (bitmap != null && !bitmap.isRecycled()) {
                    c.i(this.itemView.getContext()).g(aSAvatarConfigInfo.thum).b(new f().i(this.imageView.getDrawable())).b(f.a((j<Bitmap>) new RoundedCornersTransformation<Bitmap>(20, 1))).a(this.imageView);
                    this.imageView.setBackground(MimojiThumbnailRecyclerAdapter.getSelectItem(aSAvatarConfigInfo.configType) != ((float) aSAvatarConfigInfo.configID) ? this.itemView.getContext().getDrawable(R.drawable.bg_mimoji_thumbnail_selected) : null);
                    String simpleName = ThumbnailViewViewHolder.class.getSimpleName();
                    StringBuilder sb = new StringBuilder();
                    sb.append("fmoji MimojiThumbnailAdapter getView position: ");
                    sb.append(i);
                    Log.i(simpleName, sb.toString());
                }
            }
            Log.e(ThumbnailViewViewHolder.class.getSimpleName(), "fmoji bitmap isRecycled");
            this.imageView.setBackground(MimojiThumbnailRecyclerAdapter.getSelectItem(aSAvatarConfigInfo.configType) != ((float) aSAvatarConfigInfo.configID) ? this.itemView.getContext().getDrawable(R.drawable.bg_mimoji_thumbnail_selected) : null);
            String simpleName2 = ThumbnailViewViewHolder.class.getSimpleName();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("fmoji MimojiThumbnailAdapter getView position: ");
            sb2.append(i);
            Log.i(simpleName2, sb2.toString());
        }
    }

    public MimojiThumbnailRecyclerAdapter(Context context, int i) {
        this(null);
        this.mContext = context;
        this.selectIndex = getSelectItem(i);
    }

    public MimojiThumbnailRecyclerAdapter(ArrayList<ASAvatarConfigInfo> arrayList) {
        super(arrayList);
        this.selectIndex = -1.0f;
    }

    public static float getSelectItem(int i) {
        return AvatarEngineManager.getInstance().getInnerConfigSelectIndex(i);
    }

    @NonNull
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ThumbnailViewViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mimoji_thumbnail_view, viewGroup, false));
    }

    public void setSelectItem(int i, int i2) {
        AvatarEngineManager instance = AvatarEngineManager.getInstance();
        if (instance != null) {
            instance.setInnerConfigSelectIndex(i, (float) i2);
        }
    }
}
