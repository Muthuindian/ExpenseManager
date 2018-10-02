package com.zoho.expensetracker.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> mTabsList;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mTabsList) {
        super(fm);
        this.mTabsList = mTabsList;
    }

    @Override
    public Fragment getItem(int position) {
        return mTabsList.get(position);
    }

    @Override
    public int getCount() {
        return mTabsList.size();
    }
}
