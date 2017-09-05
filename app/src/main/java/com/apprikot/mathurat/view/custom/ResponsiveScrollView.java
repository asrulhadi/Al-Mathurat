package com.apprikot.mathurat.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.apprikot.mathurat.controller.interfaces.ScrollViewListener;

public class ResponsiveScrollView extends ScrollView {

    public static final String TAG = ResponsiveScrollView.class.getSimpleName();

    private ScrollViewListener scrollViewListener = null;
    private ScrollView sv;

    public ResponsiveScrollView(Context context) {
        this(context, null, 0);
    }

    public ResponsiveScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ResponsiveScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(canScroll(sv)){
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:{
                    onTouchEvent(ev);
                }
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(canScroll(sv)){
            int eventAction = ev.getAction();
            int value = getMeasuredHeight() + computeVerticalScrollOffset();
            switch (eventAction) {
                case MotionEvent.ACTION_UP:{
                    if(value == computeVerticalScrollRange()){
                        scrollViewListener.onScrollStopped("down");
                    }
                    if(computeVerticalScrollOffset() == 0){
                        scrollViewListener.onScrollStopped("up");
                    }
                }
            }
        }
        return super.onTouchEvent(ev);
    }

    public ScrollViewListener getOnScrollListener() {
        return scrollViewListener;
    }

    public void setOnScrollListener(ScrollViewListener mOnScrollListener , ScrollView sv) {
        this.scrollViewListener = mOnScrollListener;
        this.sv = sv;
    }

    private boolean canScroll(ScrollView scrollView) {
        View child = (View) scrollView.getChildAt(0);
        if (child != null) {
            int childHeight = (child).getHeight();
            return scrollView.getHeight() < childHeight + scrollView.getPaddingTop() + scrollView.getPaddingBottom();
        }
        return false;
    }

}