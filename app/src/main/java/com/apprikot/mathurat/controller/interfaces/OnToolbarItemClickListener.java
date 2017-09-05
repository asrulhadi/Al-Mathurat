package com.apprikot.mathurat.controller.interfaces;

import android.widget.ImageView;

import com.apprikot.mathurat.controller.enums.ToolbarItem;


public interface OnToolbarItemClickListener {
    void onToolbarItemClicked(ToolbarItem toolbarItem, ImageView checkableImageView);
}