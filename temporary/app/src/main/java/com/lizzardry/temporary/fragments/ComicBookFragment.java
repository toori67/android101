package com.lizzardry.temporary.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lizzardry.temporary.R;
import com.lizzardry.temporary.utils.ScreenUtil;
import com.lizzardry.temporary.views.ScrollDetectScrollView;

public class ComicBookFragment extends Fragment {
    public static final int TOTAL_SCROLL_HEIGHT_IN_DP = 1050;

    private ScrollDetectScrollView scrollBgView;
    private View bgShadow;
    private View slogan;
    private View drMan;
    private View rorschach;
    private View nightOwl;

    private ScreenUtil screenUtil;

    private Point screenSize;
    private float bgScrollDistance= 0;
    private float rorschachTransitionDistance = 0;
    private float nightOwlTransitionDistance;
    private float drManTransitionDistance;


    public ComicBookFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comic_book, container, false);
        initDimens();
        initUi(view);
        return view;
    }

    private void initDimens() {
        screenUtil = new ScreenUtil(getActivity());
        screenSize = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(screenSize);
        bgScrollDistance = screenUtil.dpToPx(ComicBookFragment.TOTAL_SCROLL_HEIGHT_IN_DP)/2;
        rorschachTransitionDistance = -1 * screenUtil.dpToPx(130);
        nightOwlTransitionDistance = screenUtil.dpToPx(260);
        drManTransitionDistance = screenUtil.dpToPx(130)/10;
    }

    private void initUi(View view) {
        bgShadow = view.findViewById(R.id.fragment_comic_bg_shadow);
        slogan = view.findViewById(R.id.fragment_comic_slogan);
        rorschach = view.findViewById(R.id.fragment_comic_rorschach);
        nightOwl = view.findViewById(R.id.fragment_comic_night_owl);
        drMan = view.findViewById(R.id.fragment_comic_dr_man);
        scrollBgView = (ScrollDetectScrollView) view.findViewById(R.id.fragment_comic_bg_scroll);
        scrollBgView.addOnScrollChangedListener(new ScrollDetectScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChanged(int x, int y, int oldX, int oldY) {
                float currentScrollProgress = 1.f - ((float) y) / bgScrollDistance;
                rorschach.setTranslationX(rorschachTransitionDistance * currentScrollProgress);
                nightOwl.setTranslationX(nightOwlTransitionDistance * (currentScrollProgress));
                float drManSin = (float) Math.sin(currentScrollProgress * Math.PI * 3);
                drMan.setTranslationY(drManSin * drManTransitionDistance);
                drMan.setAlpha((1 + drManSin) / 2.f + 0.3f);
                float alpha = 1 - currentScrollProgress;
                if (alpha > 1) {
                    alpha = 1;
                }
                slogan.setAlpha(alpha);
                bgShadow.setAlpha(alpha * 0.7f);
            }
        });
    }
}
