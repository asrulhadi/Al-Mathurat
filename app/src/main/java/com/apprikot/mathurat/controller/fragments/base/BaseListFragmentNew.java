package com.apprikot.mathurat.controller.fragments.base;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.enums.ListItemType;
import com.apprikot.mathurat.controller.interfaces.Configurable;
import com.apprikot.mathurat.controller.interfaces.Listable;
import com.apprikot.mathurat.controller.listeners.OnItemClickListener;
import com.apprikot.mathurat.view.adapters.BlankAdapter;
import com.apprikot.mathurat.view.adapters.GeneralStickyListAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseListFragmentNew extends BaseFragment implements
        Configurable,
        OnItemClickListener.OnItemClickCallback,
        StickyRecyclerHeadersTouchListener.OnHeaderClickListener {
    protected RecyclerView recyclerView;
    protected LinearLayoutManager layoutManager;
    protected GeneralStickyListAdapter generalStickyListAdapter;
    protected StickyRecyclerHeadersDecoration headersDecor;
    protected boolean mHasHeaders;
    protected boolean mPendingRequest;
    protected boolean mLoadMore;
    protected boolean mIsLoadingMore;
    protected int mStartIndex;
    protected int mPageIndex;

    public static final int DIRECTION_UP = -1;
    public static final int DIRECTION_DOWN = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root = view;
        initRecyclerView();
        if (generalStickyListAdapter == null) {

            sendRequest();
        } else {
            if (mHasHeaders) {
                initStickyHeader();
            }
            recyclerView.setAdapter(generalStickyListAdapter);
            postPrepareRecyclerView();
        }
    }

    protected void sendRequest() {
    }

    protected void postPrepareRecyclerView() {
    }

//    @Override
//    public void onResponseNewReady(Response response) {
//        super.onResponseNewReady(response);
//        mIsLoadingMore = false;
//        if (response.result == null) {
//            if (mPageIndex == 0) {
//                if (generalStickyListAdapter == null) {
//                    setLoadingUtilsState(LoadingUtils.State.NO_DATA);
//                }
//            } else {
//                removeLoadMoreItem(generalStickyListAdapter.getItems());
//                generalStickyListAdapter.notifyDataSetChanged();
//            }
//            return;
//        }
//        setLoadingUtilsState(LoadingUtils.State.DONE);
//        drawItems(getItems(response.result));
//    }

    protected List<Listable> getItems(Object result) {
        return new ArrayList<>();
    }

    protected Map<Long, Listable> getHeadersMap() {
        return new HashMap<>();
    }

    protected ListItemType getHeaderListItemType() {
        return ListItemType.NONE;
    }

    protected void drawItems(List<Listable> items) {

        if (generalStickyListAdapter == null) {
            generalStickyListAdapter = new GeneralStickyListAdapter(getActivity(), items,
                    this, BaseListFragmentNew.this);
            if (mHasHeaders) {
                generalStickyListAdapter.setHeaders(getHeadersMap(), getHeaderListItemType());
                initStickyHeader();
            }
            recyclerView.setAdapter(generalStickyListAdapter);
            postPrepareRecyclerView();
        } else {
            generalStickyListAdapter.clear();
            generalStickyListAdapter.addAll(items);
            generalStickyListAdapter.notifyDataSetChanged();
        }
    }

//    protected void removeLoadMoreItem(List<Listable> items) {
//        int index = items.indexOf(new LoadingMore(this));
//        if (index != -1) {
//            LoadingMore loadingMore = (LoadingMore) items.get(index);
//            loadingMore.release();
//            items.remove(index);
//        }
//    }

    protected void initRecyclerView() {
        recyclerView = (RecyclerView) root.findViewById(R.id.rv);
        if (recyclerView != null) {
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            if (generalStickyListAdapter == null) {
                recyclerView.setAdapter(new BlankAdapter());
            }
        }
    }

    private void initStickyHeader() {
        headersDecor = new StickyRecyclerHeadersDecoration(generalStickyListAdapter);
        recyclerView.addItemDecoration(headersDecor);
        StickyRecyclerHeadersTouchListener touchListener = new StickyRecyclerHeadersTouchListener(
                recyclerView, headersDecor);
        touchListener.setOnHeaderClickListener(this);
        recyclerView.addOnItemTouchListener(touchListener);
    }

    protected void setListMargins(int left, int top, int right, int bottom) {
        if (recyclerView.getLayoutParams() instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) recyclerView.getLayoutParams()).setMargins(left, top, right, bottom);
        } else if (recyclerView.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
            ((CoordinatorLayout.LayoutParams) recyclerView.getLayoutParams()).setMargins(left, top, right, bottom);
        }
    }

    @Override
    public void onItemClicked(View view, Listable listableItem, int position) {
    }


    @Override
    public Configurable config() {
        return this;
    }

    @Override
    public Configurable hasGridLayout(boolean hasGridLayout) {
        return null;
    }

    @Override
    public Configurable hasHeaders(boolean hasHeaders) {
        mHasHeaders = hasHeaders;
        return this;
    }

    @Override
    public Configurable hasMargins(boolean hasMargins) {
        // TODO: remove this, and remove all margins
        // TODO: replace by inner margins inside cells
        if (!hasMargins) {
            setListMargins(0, 0, 0, 0);
        }
        return this;
    }

    @Override
    public Configurable hasLoadMore(boolean loadMore) {
        mLoadMore = loadMore;
        return this;
    }

    @Override
    public Configurable withStartIndex(int startIndex) {
        mStartIndex = startIndex;
        return this;
    }

    @Override
    public Configurable hasPullRefresh(boolean hasPullRefresh) {
        return null;
    }

    @Override
    public Configurable fromPageNum(int fromIndex) {
        return null;
    }

    @Override
    public Configurable toPageNum(int toIndex) {
        return null;
    }

//    @Override
//    public void loadMore() {
//        if (!mIsLoadingMore) {
//            mIsLoadingMore = true;
//            sendRequest();
//        }
//    }

    @Override
    public void onDestroyView() {
        mIsLoadingMore = false;
        super.onDestroyView();
    }

    @Override
    public void onHeaderClick(View header, int position, long headerId, int x, int y) {
    }
}