package com.apprikot.mathurat.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apprikot.mathurat.R;


public class LoadingUtils extends RelativeLayout {
    public enum State {
        LOADING, NO_DATA, NO_CONNECTION, DONE, NO_DATA_WITH_ICON
    }

    private Loading mLoading;
    private TextView titleTextView;
    private View messageView;
    private State mState = State.DONE;
    private ImageView iconImageView;


    public LoadingUtils(Context context) {
        super(context);
        init();
    }

    public LoadingUtils(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingUtils(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.custom_loading_utils, this);
        mLoading = (Loading) findViewById(R.id.loading);
        titleTextView = (TextView) findViewById(R.id.txt_title);
        messageView = findViewById(R.id.layout_msg);
        iconImageView = (ImageView) findViewById(R.id.img_icon);
    }

    public State getState() {
        return mState;
    }

    public void setState(State state, int titleResId) {
        setState(state);
        titleTextView.setText(titleResId);
    }

    public void setState(State state, int titleResId, int iconResId) {
        setState(state);
        titleTextView.setText(titleResId);
        iconImageView.setImageResource(iconResId);

    }

    public void setState(State state) {
        if (mState == state) {
            return;
        }
        mState = state;
        switch (mState) {
            case LOADING: {
                messageView.setVisibility(View.GONE);
                mLoading.setVisibility(View.VISIBLE);
                mLoading.rotate();
                break;
            }
            case NO_DATA: {
                mLoading.setVisibility(View.GONE);
                mLoading.clearAnimation();
                messageView.setVisibility(View.VISIBLE);
                break;
            }
            case DONE: {
                mLoading.setVisibility(View.GONE);
                mLoading.clearAnimation();
                messageView.setVisibility(View.GONE);
                break;
            }
            case NO_DATA_WITH_ICON: {
                mLoading.setVisibility(View.GONE);
                mLoading.clearAnimation();
                messageView.setVisibility(View.VISIBLE);
                iconImageView.setVisibility(View.VISIBLE);
                break;
            }

        }
    }
}