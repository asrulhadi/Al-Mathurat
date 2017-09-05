package com.apprikot.mathurat.view.viewholders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.apprikot.mathurat.controller.interfaces.Listable;
import com.apprikot.mathurat.controller.listeners.OnItemClickListener;
import com.bumptech.glide.Glide;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    protected OnItemClickListener onItemClickListener;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public BaseViewHolder(View itemView, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        super(itemView);
        if (onItemClickCallback != null) {
            onItemClickListener = new OnItemClickListener(getAdapterPosition(), onItemClickCallback);
        }
    }

    public void draw(Listable listable) {
        if (onItemClickListener != null) {
            onItemClickListener.setListableItem(listable);
            onItemClickListener.setPosition(getAdapterPosition());
        }
    }

    protected void attachClickListener(View... views) {
        for (View view : views) {
            view.setOnClickListener(onItemClickListener);
        }
    }

    protected View find(int id) {
        return itemView.findViewById(id);
    }

    protected Context getContext() {
        return itemView.getContext();
    }

    protected String getString(int resId) {
        return getContext().getString(resId);
    }

    protected int getColor(int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    protected void loadLogo(ImageView imageView, String url, int stubResId) {
        Glide.with(getContext())
                .load(url)
                .placeholder(stubResId)
                .fitCenter()
                .dontAnimate()
                .into(imageView);
    }

    protected void loadCircularImage(ImageView imageView, String url, int stubResId) {
        Glide.with(getContext())
                .load(url)
                .placeholder(stubResId)
                .dontAnimate()
                .into(imageView);
    }

    protected void loadWeatherIcon(ImageView imageView, long icon) {
        Glide.with(getContext())
                .load("http://o.kooora.com/i/icons/w/" + icon + ".gif")
                .fitCenter()
                .dontAnimate()
                .into(imageView);
    }
}