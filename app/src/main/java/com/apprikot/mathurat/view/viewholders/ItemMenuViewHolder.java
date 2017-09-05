package com.apprikot.mathurat.view.viewholders;

import android.view.View;
import android.widget.TextView;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.interfaces.Listable;
import com.apprikot.mathurat.controller.listeners.OnItemClickListener;
import com.apprikot.mathurat.model.menu.LeftMenuMainItem;
import com.apprikot.mathurat.view.custom.CheckableImageView;

/**
 * Created by alhassen on 8/21/2016.
 */
public class ItemMenuViewHolder extends BaseViewHolder {
    private CheckableImageView img_menu;
    private TextView txt_menu;
    private int checkedColor, unCheckedColor;

    public ItemMenuViewHolder(View itemView, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        super(itemView, onItemClickCallback);
        img_menu = (CheckableImageView) find(R.id.img_menu);
        txt_menu = (TextView) find(R.id.txt_menu);
        checkedColor = getColor(R.color.txt_color);
        unCheckedColor = getColor(R.color.white);
        attachClickListener(itemView);
    }

    @Override
    public void draw(Listable listable) {
        super.draw(listable);
        LeftMenuMainItem leftMenuMainItem = (LeftMenuMainItem) listable;
        txt_menu.setText(leftMenuMainItem.menuMainItem.titleResId);
        txt_menu.setTextColor(leftMenuMainItem.isChecked() ? checkedColor : unCheckedColor);
        img_menu.setImageResource(leftMenuMainItem.menuMainItem.iconResId);
        img_menu.setChecked(leftMenuMainItem.isChecked());
    }
}