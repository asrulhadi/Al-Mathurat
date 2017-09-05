package com.apprikot.mathurat.view.viewholders;

import android.view.View;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.interfaces.Listable;
import com.apprikot.mathurat.controller.listeners.OnItemClickListener;
import com.apprikot.mathurat.model.MathuratData;
import com.apprikot.mathurat.view.custom.CustomTextViewContent;

/**
 * Created by alhassen on 8/21/2016.
 */
public class LargeThakerViewHolder extends BaseViewHolder {


    private CustomTextViewContent txt_value;

    public LargeThakerViewHolder(View itemView, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        super(itemView, onItemClickCallback);
        txt_value = (CustomTextViewContent) find(R.id.txt_value);
        attachClickListener(itemView);
    }

    @Override
    public void draw(Listable listable) {
        super.draw(listable);
        MathuratData data = (MathuratData) listable;
        txt_value.setText(data.isArabic_txt ? data.text_ar: data.text_en);
    }
}