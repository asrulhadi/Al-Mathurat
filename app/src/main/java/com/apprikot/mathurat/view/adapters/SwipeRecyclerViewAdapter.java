package com.apprikot.mathurat.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.interfaces.OnNotifyClick;
import com.apprikot.mathurat.controller.utils.StringUtils;
import com.apprikot.mathurat.model.MathuratData;
import com.apprikot.mathurat.view.custom.CustomTextViewContent;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.List;

public class SwipeRecyclerViewAdapter extends RecyclerSwipeAdapter<SwipeRecyclerViewAdapter.SimpleViewHolder> {


    private Context mContext;
    private List<MathuratData> notifyMathuratDatas;
    private List<MathuratData> mathuratDataList;
    private OnNotifyClick onNotifyClick;

    public SwipeRecyclerViewAdapter(Context context, List<MathuratData> notifyMathuratDatas, List<MathuratData> mathuratDataList, OnNotifyClick onNotifyClick) {
        this.mContext = context;
        this.notifyMathuratDatas = notifyMathuratDatas;
        this.mathuratDataList = mathuratDataList;
        this.onNotifyClick = onNotifyClick;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_row_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final MathuratData item = notifyMathuratDatas.get(position);
        viewHolder.txt_value.setText(item.isArabic_txt ? item.text_ar : item.text_en);
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        // Drag From Left
//        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));
        // Drag From Right
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));
        // Handling different events when swiping
        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });

        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, " onClick : " + item.getName() + " \n" + item.getEmailId(), Toast.LENGTH_SHORT).show();
            }
        });


        viewHolder.edit_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNotifyClick.onNotifyClick(item);
            }
        });

        viewHolder.delete_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                notifyMathuratDatas.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, notifyMathuratDatas.size());
                mItemManger.closeAllItems();
                item.isNotify = false;
                StringUtils.replaceData(mContext, mathuratDataList, item);
            }
        });
        mItemManger.bindView(viewHolder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return notifyMathuratDatas.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    public void addAll(List<MathuratData> items) {
        notifyMathuratDatas.addAll(items);
    }
    public void clear() {
        notifyMathuratDatas.clear();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        CustomTextViewContent txt_value;
        LinearLayout edit_notify;
        LinearLayout delete_notify;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            txt_value = (CustomTextViewContent) itemView.findViewById(R.id.txt_value);
            edit_notify = (LinearLayout) itemView.findViewById(R.id.edit_notify);
            delete_notify = (LinearLayout) itemView.findViewById(R.id.delete_notify);
        }
    }
}
