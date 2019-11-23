package com.android.camera.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import java.util.List;

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    public BaseFragmentPagerAdapter(FragmentManager fragmentManager, List<Fragment> list) {
        super(fragmentManager);
        this.mFragmentList = list;
    }

    public int getCount() {
        if (this.mFragmentList == null) {
            return 0;
        }
        return this.mFragmentList.size();
    }

    public List<Fragment> getFragmentList() {
        return this.mFragmentList;
    }

    public Fragment getItem(int i) {
        return this.mFragmentList.get(i);
    }

    public long getItemId(int i) {
        Fragment item = getItem(i);
        return item != null ? (long) (i | item.hashCode()) : super.getItemId(i);
    }

    public void recycleFragmentList(FragmentManager fragmentManager) {
        if (this.mFragmentList != null) {
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            for (Fragment remove : this.mFragmentList) {
                beginTransaction.remove(remove);
            }
            beginTransaction.commit();
        }
    }
}
