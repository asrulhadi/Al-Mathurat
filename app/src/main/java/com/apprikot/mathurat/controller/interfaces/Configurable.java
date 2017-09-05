package com.apprikot.mathurat.controller.interfaces;

public interface Configurable {
    Configurable config();

    Configurable hasGridLayout(boolean hasGridLayout);

    Configurable hasHeaders(boolean hasHeaders);

    Configurable hasMargins(boolean hasMargins);

    Configurable hasLoadMore(boolean loadMore);

    Configurable withStartIndex(int startIndex);

    Configurable hasPullRefresh(boolean hasPullRefresh);

    Configurable fromPageNum(int fromIndex);

    Configurable toPageNum(int toIndex);
}