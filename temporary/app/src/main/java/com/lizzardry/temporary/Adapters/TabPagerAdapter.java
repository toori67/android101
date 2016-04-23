package com.lizzardry.temporary.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lizzardry.temporary.fragments.BaseFragment;
import com.lizzardry.temporary.fragments.ComicBookFragment;
import com.lizzardry.temporary.fragments.DbListFragment;
import com.lizzardry.temporary.fragments.shared.SharedElementFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {
    public TabPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new BaseFragment();
        } else if (position == 1) {
            return new SharedElementFragment();
        } else if (position == 2) {
            return new ComicBookFragment();
        } else {
            return new DbListFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Collapsing Toolbar";
        } else if (position == 1) {
            return "Shared Elem";
        } else if (position == 2) {
            return "FragmentPagerTransform";
        } else {
            return "SQLiteDocSearch";
        }
    }
}
