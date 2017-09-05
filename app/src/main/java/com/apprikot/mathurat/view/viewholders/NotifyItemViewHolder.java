package com.apprikot.mathurat.view.viewholders;

import android.view.View;
import android.widget.LinearLayout;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.interfaces.Listable;
import com.apprikot.mathurat.controller.listeners.OnItemClickListener;
import com.apprikot.mathurat.model.MathuratData;
import com.apprikot.mathurat.view.custom.CustomTextViewContent;
import com.daimajia.swipe.SwipeLayout;

/**
 * Created by alhassen on 8/21/2016.
 */
public class NotifyItemViewHolder extends BaseViewHolder {



    private CustomTextViewContent txt_value;
    private LinearLayout edit_notify, delete_notify;
    private SwipeLayout swipeLayout;

    public NotifyItemViewHolder(View itemView, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        super(itemView, onItemClickCallback);
        swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, swipeLayout.findViewById(R.id.bottom_wrapper));
        swipeLayout.addSwipeListener(swipListen);
        txt_value = (CustomTextViewContent) find(R.id.txt_value);
        edit_notify = (LinearLayout) find(R.id.edit_notify);
        delete_notify = (LinearLayout) find(R.id.delete_notify);
        attachClickListener(swipeLayout, edit_notify, delete_notify);
    }

    @Override
    public void draw(Listable listable) {
        super.draw(listable);
        MathuratData data = (MathuratData) listable;
        txt_value.setText(data.isArabic_txt ? data.text_ar: data.text_en);
    }


    SwipeLayout.SwipeListener swipListen = new SwipeLayout.SwipeListener() {
        @Override
        public void onStartOpen(SwipeLayout layout) {

        }

        @Override
        public void onOpen(SwipeLayout layout) {

        }

        @Override
        public void onStartClose(SwipeLayout layout) {

        }

        @Override
        public void onClose(SwipeLayout layout) {

        }

        @Override
        public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

        }

        @Override
        public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

        }
    };
}