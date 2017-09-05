package com.apprikot.mathurat.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.apprikot.mathurat.controller.fragments.tasbeh.TasbehPagerItemFragment;
import com.apprikot.mathurat.controller.interfaces.OnItemSwipeListener;
import com.apprikot.mathurat.model.TasbehData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alhassen on 1/24/2016.
 */
public class TasbehPagerAdapter extends FragmentStatePagerAdapter {
    private List<TasbehData> tasbehDataList;
    private OnItemSwipeListener onItemSwipeListener;

    private List<Fragment> mFragments = new ArrayList<>();

    public TasbehPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public TasbehPagerAdapter(FragmentManager fm, List<TasbehData> tasbehDataList, OnItemSwipeListener onItemSwipeListener) {
        super(fm);
        this.tasbehDataList = tasbehDataList;
        this.onItemSwipeListener = onItemSwipeListener;
    }


    public TasbehPagerAdapter clone(FragmentManager fm) {
        TasbehPagerAdapter athkarPagerAdapter = new TasbehPagerAdapter(fm);
        athkarPagerAdapter.setFragments(mFragments);
        return athkarPagerAdapter;
    }

    public TasbehPagerAdapter clone(FragmentManager fm, List<TasbehData> tasbehDataList, OnItemSwipeListener onFragmentSwipeListener) {
        TasbehPagerAdapter tasbehPagerAdapter = new TasbehPagerAdapter(fm);
        tasbehPagerAdapter.setFragments(mFragments);
        tasbehPagerAdapter.setOnItemSwipeListener(onFragmentSwipeListener);
        tasbehPagerAdapter.setItems(tasbehDataList);
        return tasbehPagerAdapter;
    }

    public List<TasbehData> getItems() {
        return tasbehDataList;
    }

    public void setItems(List<TasbehData> tasbehDataList) {
        this.tasbehDataList = tasbehDataList;
    }

    @Override
    public Fragment getItem(int position) {
        return TasbehPagerItemFragment.newInstance(tasbehDataList.get(position), onItemSwipeListener);
    }

    public TasbehData getItemData(int position) {
        return tasbehDataList.get(position);
    }

    @Override
    public int getCount() {
        return tasbehDataList.size();
    }

    public Fragment get(int index) {
        return mFragments.get(index);
    }

    public List<Fragment> getFragments() {
        return mFragments;
    }

    public void setFragments(List<Fragment> fragments) {
        mFragments = fragments;
    }

    public OnItemSwipeListener getOnItemSwipeListener() {
        return onItemSwipeListener;
    }

    public void setOnItemSwipeListener(OnItemSwipeListener onFragmentSwipeListener) {
        this.onItemSwipeListener = onFragmentSwipeListener;
    }

}