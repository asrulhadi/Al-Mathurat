package com.apprikot.mathurat.view.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.apprikot.mathurat.controller.enums.ListItemType;
import com.apprikot.mathurat.controller.interfaces.Listable;
import com.apprikot.mathurat.controller.listeners.OnItemClickListener;
import com.apprikot.mathurat.model.UnderHeaderVo;
import com.apprikot.mathurat.view.viewholders.BaseViewHolder;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GeneralStickyListAdapter extends GeneralListAdapter
        implements StickyRecyclerHeadersAdapter<BaseViewHolder> {
    private Map<Long, Listable> mHeadersMap;
    private ListItemType mHeaderItemType;
    private String TAG = "Adapter";

    public GeneralStickyListAdapter(Context context, List<? extends Listable> data,
                                    OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        super(context, data, onItemClickCallback);
    }

    public GeneralStickyListAdapter(Context context, List<? extends Listable> data,
                                    OnItemClickListener.OnItemClickCallback onItemClickCallback, Fragment fragment) {
        super(context, data, onItemClickCallback, fragment);
        TAG = fragment.getClass().getSimpleName();
    }

    @Override
    public long getHeaderId(int position) {
        return mItems.get(position) instanceof UnderHeaderVo ?
                ((UnderHeaderVo) mItems.get(position)).headerId : -1;
    }

    @Override
    public BaseViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        try {
            View view = mInflater.inflate(mHeaderItemType.layoutResId, parent, false);
            Constructor constructor = mHeaderItemType.viewHolderClass.getConstructor(View.class,
                    OnItemClickListener.OnItemClickCallback.class);
            return (BaseViewHolder) constructor.newInstance(view, mOnItemClickCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int position) {
        long headerId = getHeaderId(position);
        if (headerId != -1) {
            holder.draw(mHeadersMap.get(headerId));
        }
    }

    public void setHeaders(Map<Long, Listable> headersMap, ListItemType headerItemType) {
        mHeadersMap = new LinkedHashMap<>(headersMap);
        mHeaderItemType = headerItemType;
    }

    public void putHeader(long headerId, Listable listableItem) {
        mHeadersMap.put(headerId, listableItem);
    }

    public Listable getHeader(long headerId) {
        return mHeadersMap.get(headerId);
    }

    public Map<Long, Listable> getHeaders() {
        return mHeadersMap;
    }
}