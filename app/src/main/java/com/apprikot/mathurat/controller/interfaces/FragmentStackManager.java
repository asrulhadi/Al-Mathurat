package com.apprikot.mathurat.controller.interfaces;

import android.support.v4.app.Fragment;

public interface FragmentStackManager {

    void setCurrentTabFragment(Fragment currentTabFragment);
    Fragment getCurrentTabFragment();
    Fragment getCurrentMainFragment();
}