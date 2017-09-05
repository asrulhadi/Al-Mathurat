package com.apprikot.mathurat.controller.interfaces;


import android.os.Parcelable;

import com.apprikot.mathurat.model.MathuratData;

public interface OnItemSwipeListener extends Parcelable{
    void onFragmentSwiped(MathuratData mathuratData);
    void onFragmentTextClick(MathuratData mathuratData);
    void onScrolledEnd(MathuratData mathuratData, String move);
    void onFragmentTasbehClick();

}