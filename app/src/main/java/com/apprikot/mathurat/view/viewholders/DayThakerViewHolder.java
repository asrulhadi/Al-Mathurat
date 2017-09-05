package com.apprikot.mathurat.view.viewholders;

import android.text.Html;
import android.view.View;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.interfaces.Listable;
import com.apprikot.mathurat.controller.listeners.OnItemClickListener;
import com.apprikot.mathurat.model.MathuratData;
import com.apprikot.mathurat.view.custom.CustomTextViewContent;
import com.github.lzyzsd.circleprogress.DonutProgress;

/**
 * Created by alhassen on 8/21/2016.
 */
public class DayThakerViewHolder extends BaseViewHolder {


    private CustomTextViewContent txt_value;
    private DonutProgress donutProgress;

    public DayThakerViewHolder(View itemView, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        super(itemView, onItemClickCallback);
        txt_value = (CustomTextViewContent) find(R.id.txt_value);
        donutProgress = (DonutProgress) find(R.id.donut_progress);
        attachClickListener(itemView);
    }

    @Override
    public void draw(Listable listable) {
        super.draw(listable);
        MathuratData data = (MathuratData) listable;
        txt_value.setText(Html.fromHtml(data.isArabic_txt ? data.text_ar: data.text_en));
        int lastCounter = data.sat_counter + data.sun_counter + data.mon_counter + data.tus_counter + data.wen_counter + data.thu_counter + data.fri_counter;
        donutProgress.setMax(lastCounter);
        switch (data.evaluation_day){
            case 0:{
                donutProgress.setProgress(data.sat_counter);
                break;
            }
            case 1:{
                donutProgress.setProgress(data.sun_counter);
                break;
            }
            case 2:{
                donutProgress.setProgress(data.mon_counter);
                break;
            }
            case 3:{
                donutProgress.setProgress(data.tus_counter);
                break;
            }
            case 4:{
                donutProgress.setProgress(data.wen_counter);
                break;
            }
            case 5:{
                donutProgress.setProgress(data.thu_counter);
                break;
            }
            case 6:{
                donutProgress.setProgress(data.fri_counter);
                break;
            }
        }
    }
}