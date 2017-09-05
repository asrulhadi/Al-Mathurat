package com.apprikot.mathurat.controller.enums;


import com.apprikot.mathurat.R;

public enum ToolbarItem {
    NONE(0),
    LEFT_MENU(R.drawable.toolbar1),
    TASBEH(R.drawable.ic_tasbeh),
    RIGHT_MENU(R.drawable.selector_all_notify),
    RV_SCREEN(R.drawable.selector_rv_screen),
    BACK(R.drawable.ic_back),
    NOTIFY_CLOSE(R.drawable.notfiy_thaker_close),
    TEXT(0),
    IMAGE(0),
    RIGHT_TEXT(1);

    public int icon;

    ToolbarItem(int icon) {
        this.icon = icon;
    }
}