package com.lizzardry.temporary.fragments;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ScrollView;

import com.lizzardry.temporary.R;

public class ComicPagerTransform implements ViewPager.PageTransformer {
    private ScrollView comicScrollView;
    private int scrollHeight = 0;
    public ComicPagerTransform(int scrollHeight) {
        // scroll 은 두번!
        this.scrollHeight = scrollHeight/2;
    }
    @Override
    public void transformPage(View page, float position) {
        comicScrollView = (ScrollView) page.findViewById(R.id.fragment_comic_bg_scroll);

        if (comicScrollView != null) {
            if (position >= -1 && position <= 1) {
                comicScrollView.smoothScrollTo(0, ((int) (scrollHeight * (1 - position))));
            }
        }
    }
}
