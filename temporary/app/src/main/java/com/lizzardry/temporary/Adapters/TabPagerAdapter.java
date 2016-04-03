package com.lizzardry.temporary.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lizzardry.temporary.fragments.BaseFragment;
import com.lizzardry.temporary.fragments.ComicBookFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {
    public TabPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new BaseFragment();
        } else if (position == 1) {
            return new BaseFragment();
        } else {
            return new ComicBookFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Dummy Tab "+(++position) ;
    }
}
