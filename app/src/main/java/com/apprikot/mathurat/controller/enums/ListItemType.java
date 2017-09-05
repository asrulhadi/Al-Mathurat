package com.apprikot.mathurat.controller.enums;


import com.apprikot.mathurat.R;
import com.apprikot.mathurat.view.viewholders.BlankViewHolder;
import com.apprikot.mathurat.view.viewholders.DayThakerViewHolder;
import com.apprikot.mathurat.view.viewholders.ItemMenuViewHolder;
import com.apprikot.mathurat.view.viewholders.LargeThakerViewHolder;
import com.apprikot.mathurat.view.viewholders.MiniThakerViewHolder;
import com.apprikot.mathurat.view.viewholders.NotifyItemViewHolder;

public enum ListItemType {
//    BANNERS(BannerViewHolder.class, R.layout.item_banner, true),
    MENU_ITEM(ItemMenuViewHolder.class, R.layout.item_menu),
    MINI_THAKER(MiniThakerViewHolder.class, R.layout.mini_thaker),
    LARGE_THAKER(LargeThakerViewHolder.class, R.layout.large_thaker),
    DAY_THAKER(DayThakerViewHolder.class, R.layout.day_thaker),
    NOTIFY_ITEM(NotifyItemViewHolder.class, R.layout.swipe_row_item),
    NONE(BlankViewHolder.class, 0);

    public Class viewHolderClass;
    public int layoutResId;
    public boolean isFragment;

    ListItemType(Class viewHolderClass, int layoutResId) {
        this.viewHolderClass = viewHolderClass;
        this.layoutResId = layoutResId;
    }

    ListItemType(Class viewHolderClass, int layoutResId, boolean isFragment) {
        this(viewHolderClass, layoutResId);
        this.isFragment = isFragment;
    }
}