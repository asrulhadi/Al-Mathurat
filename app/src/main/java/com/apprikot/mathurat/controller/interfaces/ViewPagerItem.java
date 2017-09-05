package com.apprikot.mathurat.controller.interfaces;

import android.os.Bundle;

public interface ViewPagerItem {
    void onPageSelected();

    void onPageUnselected();

    void onMessage(int messageId, Bundle data);

    void setPageIndex(int pageIndex);

    int getPageIndex();
}