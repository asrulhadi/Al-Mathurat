package com.apprikot.mathurat.controller.fragments.Athkar;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.enums.ToolbarItem;
import com.apprikot.mathurat.controller.fragments.base.BaseFragment;
import com.apprikot.mathurat.controller.interfaces.OnWheelChangedListener;
import com.apprikot.mathurat.controller.interfaces.OnWheelClickedListener;
import com.apprikot.mathurat.controller.interfaces.OnWheelScrollListener;
import com.apprikot.mathurat.controller.receiver.NotifyReceiver;
import com.apprikot.mathurat.controller.utils.PrefHelp;
import com.apprikot.mathurat.controller.utils.StringUtils;
import com.apprikot.mathurat.model.MathuratData;
import com.apprikot.mathurat.view.adapters.ArrayWheelAdapter;
import com.apprikot.mathurat.view.adapters.NumericWheelAdapter;
import com.apprikot.mathurat.view.custom.CheckableImageView;
import com.apprikot.mathurat.view.custom.CustomTextView;
import com.apprikot.mathurat.view.custom.WheelView;

import java.util.ArrayList;
import java.util.List;

import static com.apprikot.mathurat.R.id.hour;


public class NotifyFragment extends BaseFragment {

    private static final String TAG = NotifyFragment.class.getSimpleName();

    private WheelView hours, mins, ampm;
    private boolean timeChanged = false;
    private boolean timeScrolled = false;
    private int hourInt, minInt, ampmInt;
    private List<MathuratData> mathuratDatas;
    private MathuratData mathuratData;

    private CheckableImageView ic_notify, sa, su, mo, tu, we, th, fr;
    private CustomTextView notify_text;
    private List<Boolean> listday;


    public NotifyFragment() {
    }

    public static NotifyFragment newInstance(ArrayList<MathuratData> list, MathuratData obj) {
        NotifyFragment fragment = new NotifyFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_LIST_PARCELABLE, list);
        args.putParcelable(EXTRA_PARCELABLE, obj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mathuratDatas = getArguments().getParcelableArrayList(EXTRA_LIST_PARCELABLE);
            if(mathuratDatas == null){
                mathuratDatas = PrefHelp.getData(getActivity());
            }
            mathuratData = getArguments().getParcelable(EXTRA_PARCELABLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notify, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarItem();
        setUpView();
        prepareView(mathuratData);
    }

    private void prepareView(MathuratData mathuratData) {
        ic_notify.setChecked(mathuratData.isNotify);
        notify_text.setText(mathuratData.isNotify ? getString(R.string.notify_on) : getString(R.string.notify_off));
        listday = getDaysNotify(mathuratData.notify_days);
        sa.setChecked(listday.get(listday.size() -1));
        su.setChecked(listday.get(0));
        mo.setChecked(listday.get(1));
        tu.setChecked(listday.get(2));
        we.setChecked(listday.get(3));
        th.setChecked(listday.get(4));
        fr.setChecked(listday.get(5));
    }

    private List<Boolean> getDaysNotify(String notify_date) {
        List<Boolean> repeatingDays = new ArrayList<>();
        Log.d(TAG, "notify_day: " + notify_date);
        String[] repeatingDaysPrepare = notify_date.split(",");
        for (int i = 0; i < repeatingDaysPrepare.length; ++i) {
            repeatingDays.add(i, Boolean.valueOf(repeatingDaysPrepare[i]));
        }
        return repeatingDays;
    }
    private String setDaysNotify(List<Boolean> list) {
        String days = "";
        for (int i = 0; i < list.size(); i++) {
            days += list.get(i);
            if(!(i == list.size() -1)){
                days += ",";
            }
        }
        Log.d(TAG, "dayd: " + days);
        return days;
    }

    private void setToolbarItem() {
        setToolbarItems(ToolbarItem.NOTIFY_CLOSE, ToolbarItem.NONE, ToolbarItem.NONE, ToolbarItem.NONE);
        setToolbarCenter(ToolbarItem.TEXT, R.string.notify_thaker_setting);
    }

    private void setUpView() {
        notify_text = (CustomTextView) root.findViewById(R.id.notify_text);
        ic_notify = (CheckableImageView) root.findViewById(R.id.ic_notify);
        sa = (CheckableImageView) root.findViewById(R.id.sa);
        su = (CheckableImageView) root.findViewById(R.id.su);
        mo = (CheckableImageView) root.findViewById(R.id.mo);
        tu = (CheckableImageView) root.findViewById(R.id.tu);
        we = (CheckableImageView) root.findViewById(R.id.we);
        th = (CheckableImageView) root.findViewById(R.id.th);
        fr = (CheckableImageView) root.findViewById(R.id.fr);


        hours = (WheelView) root.findViewById(hour);
        NumericWheelAdapter hourAdapter = new NumericWheelAdapter(getActivity(), 0, 11);
        hourAdapter.setItemResource(R.layout.wheel_text_item);
        hourAdapter.setItemTextResource(R.id.text);
        hours.setViewAdapter(hourAdapter);
        hours.setCyclic(true);

        mins = (WheelView) root.findViewById(R.id.mins);
        NumericWheelAdapter minAdapter = new NumericWheelAdapter(getActivity(), 0, 59, "%02d");
        minAdapter.setItemResource(R.layout.wheel_text_item);
        minAdapter.setItemTextResource(R.id.text);
        mins.setViewAdapter(minAdapter);
        mins.setCyclic(true);

        ampm = (WheelView) root.findViewById(R.id.ampm);
        ArrayWheelAdapter<String> ampmAdapter = new ArrayWheelAdapter<String>(getActivity(), new String[]{"AM", "PM"});
        ampmAdapter.setItemResource(R.layout.wheel_text_item);
        ampmAdapter.setItemTextResource(R.id.text);
        ampm.setViewAdapter(ampmAdapter);

        hours.setCurrentItem(mathuratData.notify_hour);
        mins.setCurrentItem(mathuratData.notify_min);
        ampm.setCurrentItem(mathuratData.notify_am_pm);

        hours.addChangingListener(wheelListener);
        mins.addChangingListener(wheelListener);
        ampm.addChangingListener(wheelListener);

        hours.addClickingListener(click);
        mins.addClickingListener(click);
        ampm.addClickingListener(click);

        hours.addScrollingListener(scrollListener);
        mins.addScrollingListener(scrollListener);
        ampm.addScrollingListener(scrollListener);


        attachClickListener(function, ic_notify, sa, su, mo, tu, we, th, fr);

    }

    @Override
    public void onToolbarItemClicked(ToolbarItem toolbarItem, ImageView checkableImageView) {
        super.onToolbarItemClicked(toolbarItem, checkableImageView);
        switch (toolbarItem) {
            case NOTIFY_CLOSE: {
                saveNotify();
                getActivity().onBackPressed();
                break;
            }
        }
    }

    private void saveNotify() {
        if(mathuratData.isNotify){
            mathuratData.notify_hour = hourInt;
            mathuratData.notify_min = minInt;
            mathuratData.notify_am_pm = ampmInt;
            mathuratData.notify_days = setDaysNotify(listday);
        }else{
            mathuratData.notify_hour = 0;
            mathuratData.notify_min = 0;
            mathuratData.notify_am_pm = 0;
            mathuratData.notify_days = getString(R.string.no_notify_set);
        }
        StringUtils.replaceData(getActivity(), mathuratDatas, mathuratData);
        NotifyReceiver.setAlarms(getActivity());
    }

    View.OnClickListener function = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ic_notify:{
                    if(!ic_notify.isChecked()){
                        mathuratData.isNotify = true;
                        notify_text.setText(getString(R.string.notify_on));
                    }else{
                        mathuratData.isNotify = false;
                        notify_text.setText(getString(R.string.notify_off));
                    }
                    ic_notify.toggle();
                    break;
                }
                case R.id.sa:{
                    if(!sa.isChecked()){
                        listday.set(listday.size() -1 , true);
                    }else{
                        listday.set(listday.size() -1 , false);
                    }
                    sa.toggle();
                    break;
                }
                case R.id.su:{
                    if(!su.isChecked()){
                        listday.set(0 , true);
                    }else{

                        listday.set(0 , false);
                    }
                    su.toggle();
                    break;
                }
                case R.id.mo:{
                    if(!mo.isChecked()){
                        listday.set(1 , true);
                    }else{

                        listday.set(1 , false);
                    }
                    mo.toggle();
                    break;
                }
                case R.id.tu:{
                    if(!tu.isChecked()){
                        listday.set(2 , true);
                    }else{
                        listday.set(2 , false);
                    }
                    tu.toggle();
                    break;
                }
                case R.id.we:{
                    if(!we.isChecked()){
                        listday.set(3 , true);
                    }else{
                        listday.set(3 , false);
                    }
                    we.toggle();
                    break;
                }
                case R.id.th:{
                    if(!th.isChecked()){
                        listday.set(4 , true);
                    }else{
                        listday.set(4 , false);
                    }
                    th.toggle();
                    break;
                }
                case R.id.fr:{
                    if(!fr.isChecked()){
                        listday.set(5 , true);
                    }else{
                        listday.set(5 , false);
                    }
                    fr.toggle();
                    break;
                }
            }
        }
    };

    OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!timeScrolled) {
                timeChanged = true;
                hourInt = hours.getCurrentItem();
                minInt = mins.getCurrentItem();
                ampmInt = ampm.getCurrentItem();
                timeChanged = false;
                Log.d(TAG, "items: " + hourInt + " - " + minInt + " - " + ampmInt);
            }
        }
    };


    OnWheelClickedListener click = new OnWheelClickedListener() {
        public void onItemClicked(WheelView wheel, int itemIndex) {
            wheel.setCurrentItem(itemIndex, true);
        }
    };


    OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            timeScrolled = true;
        }

        public void onScrollingFinished(WheelView wheel) {
            timeScrolled = false;
            timeChanged = true;
            hourInt = hours.getCurrentItem();
            minInt = mins.getCurrentItem();
            ampmInt = ampm.getCurrentItem();
            timeChanged = false;
            Log.d(TAG, "items: " + hourInt + " - " + minInt + " - " + ampmInt);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}