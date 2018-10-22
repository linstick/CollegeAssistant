package com.linstick.collegeassistant.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.linstick.collegeassistant.fragments.ModuleSwipeNoteFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    //    List<BaseSwipeNoteFragment> mFragmentList;
    List<ModuleSwipeNoteFragment> mFragmentList;
    List<String> mTitleList;

    public ViewPagerAdapter(FragmentManager fm, List<ModuleSwipeNoteFragment> mList, List<String> mTitleList) {
        super(fm);
        this.mFragmentList = mList;
        this.mTitleList = mTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
