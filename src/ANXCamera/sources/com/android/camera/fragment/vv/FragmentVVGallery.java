package com.android.camera.fragment.vv;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.android.camera.R;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.module.impl.component.LiveSubVVImpl;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import org.json.JSONException;
import org.json.JSONObject;

public class FragmentVVGallery extends BaseFragment implements View.OnClickListener, ModeProtocol.HandleBackTrace {
    private VVGalleryAdapter mGalleryAdapter;
    private View mProgressView;
    private RecyclerView mRecyclerView;
    private View mRecyclerViewLayout;
    private ResourceSelectedListener mResourceSelectedListener;
    /* access modifiers changed from: private */
    public VVList mVVList;

    private static class EffectItemPadding extends RecyclerView.ItemDecoration {
        protected int mEffectListLeft;
        protected int mHorizontalPadding;
        protected int mVerticalPadding;

        public EffectItemPadding(Context context) {
            this.mHorizontalPadding = context.getResources().getDimensionPixelSize(R.dimen.effect_item_padding_horizontal);
            this.mVerticalPadding = context.getResources().getDimensionPixelSize(R.dimen.effect_item_padding_vertical);
            this.mEffectListLeft = context.getResources().getDimensionPixelSize(R.dimen.effect_list_padding_left);
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int i = recyclerView.getChildPosition(view) == 0 ? this.mEffectListLeft : 0;
            int i2 = this.mVerticalPadding;
            rect.set(i, i2, this.mHorizontalPadding, i2);
        }
    }

    /* access modifiers changed from: private */
    public void initList() {
        if (isAdded()) {
            this.mResourceSelectedListener.onResourceReady();
            this.mResourceSelectedListener.onResourceSelected((VVItem) this.mVVList.getItem(0));
            this.mRecyclerViewLayout.setVisibility(0);
            this.mProgressView.setVisibility(8);
            this.mGalleryAdapter = new VVGalleryAdapter(this.mVVList, this, this.mResourceSelectedListener);
            LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), "vv_gallery");
            linearLayoutManagerWrapper.setOrientation(0);
            this.mRecyclerView.setLayoutManager(linearLayoutManagerWrapper);
            this.mRecyclerView.addItemDecoration(new EffectItemPadding(getContext()));
            this.mRecyclerView.setAdapter(this.mGalleryAdapter);
        }
    }

    private void loadItemList() {
        this.mVVList = (VVList) ResourceManager.getInstance().getResourceList(1);
        if (this.mVVList != null) {
            initList();
            return;
        }
        this.mVVList = new VVList();
        this.mRecyclerViewLayout.setVisibility(8);
        this.mProgressView.setVisibility(0);
        Completable.create(new CompletableOnSubscribe() {
            public void subscribe(CompletableEmitter completableEmitter) throws Exception {
                try {
                    FragmentVVGallery.this.mVVList.createResourcesList(new JSONObject(ResourceManager.getInstance().getAssetCache("vv/info.json", FragmentVVGallery.this.getContext())));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                ResourceManager.getInstance().decompressResource(FragmentVVGallery.this.getContext(), FragmentVVGallery.this.mVVList, LiveSubVVImpl.TEMPLATE_PATH, true);
                completableEmitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Action) new Action() {
            public void run() throws Exception {
                FragmentVVGallery.this.initList();
            }
        });
    }

    private void transformToPreview(int i, View view) {
        FragmentVVPreview fragmentVVPreview = new FragmentVVPreview();
        fragmentVVPreview.setPreviewData(i, this.mVVList);
        fragmentVVPreview.setResourceSelectedListener(this.mResourceSelectedListener);
        fragmentVVPreview.registerProtocol();
        getFragmentManager().beginTransaction().addSharedElement(view, ViewCompat.getTransitionName(view)).addToBackStack(getFragmentTag()).add(R.id.vv_lift, fragmentVVPreview, fragmentVVPreview.getFragmentTag()).hide(this).commitAllowingStateLoss();
    }

    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_VV_GALLERY;
    }

    /* access modifiers changed from: protected */
    public int getLayoutResourceId() {
        return R.layout.fragment_vv_gallery;
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.mProgressView = view.findViewById(R.id.vv_updating);
        this.mRecyclerViewLayout = view.findViewById(R.id.vv_list_layout);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.vv_list);
        loadItemList();
    }

    public boolean onBackEvent(int i) {
        if (!isVisible()) {
            return false;
        }
        ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).onConfigChanged(216);
        return true;
    }

    public void onClick(View view) {
        transformToPreview(((Integer) view.getTag()).intValue(), view.findViewById(R.id.vv_gallery_item_image));
    }

    /* access modifiers changed from: protected */
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
    }

    public void setResourceSelectedListener(ResourceSelectedListener resourceSelectedListener) {
        this.mResourceSelectedListener = resourceSelectedListener;
    }

    /* access modifiers changed from: protected */
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
    }
}
