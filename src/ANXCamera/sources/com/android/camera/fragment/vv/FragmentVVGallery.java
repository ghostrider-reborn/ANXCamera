package com.android.camera.fragment.vv;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.DefaultItemAnimator;
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
    private int mHolderWidth;
    private LinearLayoutManagerWrapper mLayoutManager;
    private int mPreviewIndex = -1;
    private View mProgressView;
    private RecyclerView mRecyclerView;
    private View mRecyclerViewLayout;
    private ResourceSelectedListener mResourceSelectedListener;
    private int mTotalWidth;
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
            rect.set(recyclerView.getChildPosition(view) == 0 ? this.mEffectListLeft : 0, this.mVerticalPadding, this.mHorizontalPadding, this.mVerticalPadding);
        }
    }

    /* access modifiers changed from: private */
    public void initList() {
        if (isAdded()) {
            DataRepository.dataItemLive().setVVVersion(this.mVVList.version);
            this.mResourceSelectedListener.onResourceReady();
            if (this.mPreviewIndex < 0 || this.mPreviewIndex >= this.mVVList.getSize()) {
                this.mResourceSelectedListener.onResourceSelected((VVItem) this.mVVList.getItem(0));
            } else {
                this.mResourceSelectedListener.onResourceSelected((VVItem) this.mVVList.getItem(this.mPreviewIndex));
            }
            this.mRecyclerViewLayout.setVisibility(0);
            this.mProgressView.setVisibility(8);
            this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), "vv_gallery");
            this.mLayoutManager.setOrientation(0);
            VVGalleryAdapter vVGalleryAdapter = new VVGalleryAdapter(this.mVVList, this.mLayoutManager, this.mPreviewIndex, this, this.mResourceSelectedListener);
            this.mGalleryAdapter = vVGalleryAdapter;
            this.mRecyclerView.setLayoutManager(this.mLayoutManager);
            this.mRecyclerView.addItemDecoration(new EffectItemPadding(getContext()));
            this.mRecyclerView.setAdapter(this.mGalleryAdapter);
            if (this.mPreviewIndex >= 0) {
                setItemInCenter(this.mPreviewIndex);
            }
            DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
            defaultItemAnimator.setChangeDuration(150);
            defaultItemAnimator.setMoveDuration(150);
            defaultItemAnimator.setAddDuration(150);
            this.mRecyclerView.setItemAnimator(defaultItemAnimator);
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
        final String vVVersion = DataRepository.dataItemLive().getVVVersion();
        Completable.create(new CompletableOnSubscribe() {
            public void subscribe(CompletableEmitter completableEmitter) throws Exception {
                try {
                    FragmentVVGallery.this.mVVList.createResourcesList(new JSONObject(ResourceManager.getInstance().getAssetCache("vv/info.json", FragmentVVGallery.this.getContext())));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                boolean z = true;
                if (!vVVersion.equals(FragmentVVGallery.this.mVVList.version)) {
                    z = false;
                }
                ResourceManager.getInstance().decompressResource(FragmentVVGallery.this.getContext(), FragmentVVGallery.this.mVVList, LiveSubVVImpl.TEMPLATE_PATH, z);
                completableEmitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Action) new Action() {
            public void run() throws Exception {
                FragmentVVGallery.this.initList();
            }
        });
    }

    private void setItemInCenter(int i) {
        this.mLayoutManager.scrollToPositionWithOffset(i, (this.mTotalWidth / 2) - (this.mHolderWidth / 2));
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
        Context context = getContext();
        this.mTotalWidth = context.getResources().getDisplayMetrics().widthPixels;
        this.mHolderWidth = context.getResources().getDimensionPixelSize(R.dimen.vv_list_item_image_width);
        loadItemList();
    }

    public boolean onBackEvent(int i) {
        if (!isVisible() || i == 2) {
            return false;
        }
        ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).onConfigChanged(216);
        return true;
    }

    public void onClick(View view) {
        transformToPreview(((Integer) view.getTag()).intValue(), view.findViewById(R.id.vv_gallery_item_image));
    }

    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (!z && this.mPreviewIndex != -1 && this.mGalleryAdapter != null) {
            this.mGalleryAdapter.onSelected(this.mPreviewIndex, (View) null, false);
            setItemInCenter(this.mPreviewIndex);
            this.mPreviewIndex = -1;
        }
    }

    /* access modifiers changed from: protected */
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
    }

    public void setPreviewData(int i) {
        this.mPreviewIndex = i;
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
