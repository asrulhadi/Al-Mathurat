package com.apprikot.mathurat.controller.listeners;

import android.view.View;

import com.apprikot.mathurat.controller.interfaces.Listable;

public class OnItemClickListener implements View.OnClickListener {
    private Listable listableItem;
    private int position;
    private OnItemClickCallback onItemClickCallback;

    public OnItemClickListener(int position, OnItemClickCallback onItemClickCallback) {
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onClick(View view) {
        onItemClickCallback.onItemClicked(view, listableItem, position);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setListableItem(Listable listableItem) {
        this.listableItem = listableItem;
    }

    public OnItemClickCallback getOnItemClickCallback() {
        return onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(View view, Listable listableItem, int position);
    }
}