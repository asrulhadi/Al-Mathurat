package com.apprikot.mathurat.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.apprikot.mathurat.controller.fragments.Athkar.AthkarPagerItemFragment;
import com.apprikot.mathurat.controller.interfaces.OnItemSwipeListener;
import com.apprikot.mathurat.model.MathuratData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alhassen on 1/24/2016.
 */
public class AthkarPagerAdapter extends FragmentStatePagerAdapter {

    private List<MathuratData> mathuratList;
    private List<Fragment> mFragments = new ArrayList<>();
    private OnItemSwipeListener onItemSwipeListener;

    public AthkarPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public AthkarPagerAdapter(FragmentManager fm, List<MathuratData> mathuratList, OnItemSwipeListener onItemSwipeListener) {
        super(fm);
        this.mathuratList = mathuratList;
        this.onItemSwipeListener = onItemSwipeListener;
    }


    public AthkarPagerAdapter clone(FragmentManager fm) {
        AthkarPagerAdapter athkarPagerAdapter = new AthkarPagerAdapter(fm);
        athkarPagerAdapter.setFragments(mFragments);
        return athkarPagerAdapter;
    }

    public AthkarPagerAdapter clone(FragmentManager fm, List<MathuratData> mathuratList, OnItemSwipeListener onFragmentSwipeListener) {
        AthkarPagerAdapter athkarPagerAdapter = new AthkarPagerAdapter(fm);
        athkarPagerAdapter.setFragments(mFragments);
        athkarPagerAdapter.setItems(mathuratList);
        athkarPagerAdapter.setOnItemSwipeListener(onFragmentSwipeListener);
        return athkarPagerAdapter;
    }

    public List<MathuratData> getItems() {
        return mathuratList;
    }

    public void setItems(List<MathuratData> mathuratList) {
        this.mathuratList = mathuratList;
    }

    @Override
    public Fragment getItem(int position) {
        return AthkarPagerItemFragment.newInstance(mathuratList.get(position), onItemSwipeListener);
    }

    public MathuratData getItemData(int position) {
        return mathuratList.get(position);
    }

    @Override
    public int getCount() {
        return mathuratList.size();
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