package com.apprikot.mathurat.controller.enums;


import com.apprikot.mathurat.R;

public enum MenuMainItem {
    EVALUATION(R.drawable.evaluation, R.string.evaluation),
    SETTING(R.drawable.setting, R.string.setting),
    SHARE_APP(R.drawable.share_app, R.string.share),
    ABOUT_APP(R.drawable.about_app, R.string.about_app);

    public int iconResId;
    public int titleResId;

    MenuMainItem(int iconResId, int titleResId) {
        this.iconResId = iconResId;
        this.titleResId = titleResId;
    }
}