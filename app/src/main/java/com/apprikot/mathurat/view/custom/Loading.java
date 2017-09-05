package com.apprikot.mathurat.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.apprikot.mathurat.R;


public class Loading extends RelativeLayout {
    private ImageView circleImageView;
//    private ImageView dotsImageView;

    public Loading(Context context) {
        super(context);
        init();
    }

    public Loading(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Loading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.custom_loading, this);
        circleImageView = (ImageView) findViewById(R.id.img_circle);
//        dotsImageView = (ImageView) findViewById(R.id.img_dots);
    }

    public void rotate() {
        circleImageView.clearAnimation();
        circleImageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotation));
    }

    public void setDark() {
//        dotsImageView.setImageResource(R.drawable.loading_dots_black);
    }

    public void clearCircleAnimation() {
        circleImageView.clearAnimation();
    }
}