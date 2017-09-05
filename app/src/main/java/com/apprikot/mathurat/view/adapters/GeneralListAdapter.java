package com.apprikot.mathurat.view.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.apprikot.mathurat.controller.enums.ListItemType;
import com.apprikot.mathurat.controller.interfaces.Listable;
import com.apprikot.mathurat.controller.listeners.OnItemClickListener;
import com.apprikot.mathurat.view.viewholders.BaseViewHolder;

import java.lang.reflect.Constructor;
import java.util.List;

public class GeneralListAdapter extends RecyclerArrayAdapter<Listable, BaseViewHolder> {
    protected Fragment fragment;

    protected Context context;

    public GeneralListAdapter(Context context, List<? extends Listable> data,
                              OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        super(context, onItemClickCallback);
        this.context = context;
        addAll(data);
    }

    public GeneralListAdapter(Context context, List<? extends Listable> data,
                              OnItemClickListener.OnItemClickCallback onItemClickCallback, Fragment fragment) {
        this(context, data, onItemClickCallback);
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        try {
            ListItemType listItemType = ListItemType.values()[viewType];
            View view = mInflater.inflate(listItemType.layoutResId, viewGroup, false);
            if (listItemType.isFragment) {
                Constructor constructor = listItemType.viewHolderClass.getConstructor(View.class, Fragment.class);
                return (BaseViewHolder) constructor.newInstance(view, fragment);
            } else {
                Constructor constructor = listItemType.viewHolderClass.getConstructor(View.class, OnItemClickListener.OnItemClickCallback.class);
                return (BaseViewHolder) constructor.newInstance(view, mOnItemClickCallback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, int position) {
        viewHolder.draw(mItems.get(position));
//        setAnimation(viewHolder.itemView, position);
//        if (mItems.get(position) instanceof LoadingMore) {
//            ((LoadingMore) mItems.get(position)).loadMore();
//        }
//        if (mItems.get(position) instanceof HolderLoadingMore) {
//            ((HolderLoadingMore) mItems.get(position)).viewHolderLoadMore();
//        }
    }

//    private void setAnimation(View viewToAnimate, int position)
//    {
//        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
//        viewToAnimate.startAnimation(animation);
//    }
}