package com.lizzardry.temporary;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.lizzardry.ril.RetroImageLoader;
import com.lizzardry.temporary.Adapters.TabPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private String[] coverUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coverUrls = getResources().getStringArray(R.array.page_covers);
        initUi();
        initEvent();
    }

    private void initUi() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        imageView = (ImageView) findViewById(R.id.backdrop);
        mViewPager= (ViewPager) findViewById(R.id.content_main_view_pager);
        mTabLayout= (TabLayout) findViewById(R.id.detail_tabs);
    }

    private void initEvent() {
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(tabPagerAdapter);
        mTabLayout.setTabsFromPagerAdapter(tabPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RetroImageLoader.getInstance().displayImage(coverUrls[position], imageView);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        RetroImageLoader.getInstance().displayImage(coverUrls[0], imageView);
    }
}
