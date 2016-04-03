package com.lizzardry.temporary.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class ScrollDetectScrollView extends ScrollView {
    public interface OnScrollChangeListener {
        void onScrollChanged(int x, int y, int oldX, int oldY);
    }

    private List<OnScrollChangeListener> onScrollChangeListeners;

    public ScrollDetectScrollView(Context context) {
        super(context);
        init();
    }

    public ScrollDetectScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollDetectScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        onScrollChangeListeners = new ArrayList<>();
    }

    public void addOnScrollChangedListener(OnScrollChangeListener listener){
        this.onScrollChangeListeners.add(listener);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        for(OnScrollChangeListener listener : onScrollChangeListeners) {
            listener.onScrollChanged(l, t, oldl, oldt);
        }
    }
}
