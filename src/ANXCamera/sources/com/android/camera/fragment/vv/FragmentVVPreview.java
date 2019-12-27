package com.android.camera.fragment.vv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.camera.R;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.BaseFragmentPagerAdapter;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.protocol.ModeProtocol;
import java.util.ArrayList;

public class FragmentVVPreview extends BaseFragment implements View.OnClickListener, ModeProtocol.HandleBackTrace {
    private BaseFragmentPagerAdapter mPreviewAdapter;
    private int mPreviewIndex;
    private ViewGroup mPreviewLayout;
    private ViewPager mPreviewViewPager;
    /* access modifiers changed from: private */
    public ResourceSelectedListener mResourceSelectedListener;
    /* access modifiers changed from: private */
    public VVList mVVList;

    private void initViewPager() {
        ViewGroup.LayoutParams layoutParams = this.mPreviewViewPager.getLayoutParams();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.vv_preview_image_height);
        int i = (int) ((((float) dimensionPixelSize) / 9.0f) * 16.0f);
        layoutParams.width = i;
        this.mPreviewViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f2, int i2) {
            }

            public void onPageSelected(int i) {
                if (FragmentVVPreview.this.mResourceSelectedListener != null) {
                    FragmentVVPreview.this.mResourceSelectedListener.onResourceSelected((VVItem) FragmentVVPreview.this.mVVList.getItem(i));
                }
            }
        });
        this.mPreviewViewPager.setPageTransformer(true, new VVPreviewTransformer());
        ArrayList arrayList = new ArrayList(this.mVVList.getSize());
        for (int i2 = 0; i2 < this.mVVList.getSize(); i2++) {
            FragmentVVPreviewItem fragmentVVPreviewItem = new FragmentVVPreviewItem();
            fragmentVVPreviewItem.setData(i2, (VVItem) this.mVVList.getItem(i2), i, dimensionPixelSize, this, this.mPreviewIndex);
            arrayList.add(fragmentVVPreviewItem);
        }
        this.mPreviewAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), arrayList);
        this.mPreviewViewPager.setAdapter(this.mPreviewAdapter);
        this.mPreviewViewPager.setOffscreenPageLimit(2);
        this.mPreviewViewPager.setCurrentItem(this.mPreviewIndex, false);
    }

    private void transformToGallery(int i, View view) {
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        if (view != null) {
            beginTransaction.addSharedElement(view, ViewCompat.getTransitionName(view));
        }
        FragmentVVGallery fragmentVVGallery = (FragmentVVGallery) FragmentUtils.getFragmentByTag(getFragmentManager(), String.valueOf(BaseFragmentDelegate.FRAGMENT_VV_GALLERY));
        if (fragmentVVGallery == null) {
            FragmentVVGallery fragmentVVGallery2 = new FragmentVVGallery();
            fragmentVVGallery2.setResourceSelectedListener(this.mResourceSelectedListener);
            fragmentVVGallery2.registerProtocol();
            beginTransaction.addToBackStack(getFragmentTag()).replace(R.id.vv_lift, fragmentVVGallery2, fragmentVVGallery2.getFragmentTag()).commitAllowingStateLoss();
            return;
        }
        beginTransaction.remove(this).show(fragmentVVGallery).commitAllowingStateLoss();
    }

    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_VV_PREVIEW;
    }

    /* access modifiers changed from: protected */
    public int getLayoutResourceId() {
        return R.layout.fragment_vv_preview;
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.mPreviewLayout = (ViewGroup) view.findViewById(R.id.vv_preview_layout);
        this.mPreviewViewPager = (ViewPager) view.findViewById(R.id.vv_viewpager);
        initViewPager();
    }

    public boolean onBackEvent(int i) {
        transformToGallery(0, (View) null);
        return true;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.vv_preview_item_collapsing) {
            transformToGallery(((Integer) view.getTag()).intValue(), ((ViewGroup) view.getParent()).findViewById(R.id.vv_preview_item_image));
        }
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        postponeEnterTransition();
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(17760257));
        setSharedElementReturnTransition((Object) null);
    }

    public void onPause() {
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
    }

    public void setPreviewData(int i, VVList vVList) {
        this.mPreviewIndex = i;
        this.mVVList = vVList;
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
