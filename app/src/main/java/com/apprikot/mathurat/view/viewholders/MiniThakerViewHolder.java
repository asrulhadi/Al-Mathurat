package com.apprikot.mathurat.view.viewholders;

import android.text.Html;
import android.view.View;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.interfaces.Listable;
import com.apprikot.mathurat.controller.listeners.OnItemClickListener;
import com.apprikot.mathurat.model.MathuratData;
import com.apprikot.mathurat.view.custom.CheckableImageView;
import com.apprikot.mathurat.view.custom.CustomTextViewContent;
import com.github.lzyzsd.circleprogress.DonutProgress;

/**
 * Created by alhassen on 8/21/2016.
 */
public class MiniThakerViewHolder extends BaseViewHolder {


    private CustomTextViewContent txt_value;
    private CheckableImageView share, info, refresh_progress;
    private DonutProgress donutProgress;

    public MiniThakerViewHolder(View itemView, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        super(itemView, onItemClickCallback);
        txt_value = (CustomTextViewContent) find(R.id.txt_value);
        share = (CheckableImageView) find(R.id.share);
        info = (CheckableImageView) find(R.id.info);
        refresh_progress = (CheckableImageView) find(R.id.refresh_progress);
        donutProgress = (DonutProgress) find(R.id.donut_progress);
        attachClickListener(txt_value, share, info, refresh_progress, donutProgress);
    }

    @Override
    public void draw(Listable listable) {
        super.draw(listable);
        final MathuratData data = (MathuratData) listable;
        txt_value.setText(Html.fromHtml(data.isArabic_txt ? data.text_ar: data.text_en));
        if(data.refresh_counter){
            donutProgress.setShowText(false);
            refresh_progress.setVisibility(View.VISIBLE);
            donutProgress.setMax(data.max_counter);
            donutProgress.setProgress(data.counter);
        }else{
            donutProgress.setShowText(true);
            refresh_progress.setVisibility(View.GONE);
            donutProgress.setMax(data.max_counter);
            donutProgress.setProgress(data.counter);
        }

    }
}