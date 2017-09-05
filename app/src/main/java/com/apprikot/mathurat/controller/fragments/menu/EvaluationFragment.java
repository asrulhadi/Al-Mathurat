package com.apprikot.mathurat.controller.fragments.menu;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.application.TrackApps;
import com.apprikot.mathurat.controller.components.Constants;
import com.apprikot.mathurat.controller.enums.ListItemType;
import com.apprikot.mathurat.controller.enums.ToolbarItem;
import com.apprikot.mathurat.controller.fragments.base.BaseListFragmentNew;
import com.apprikot.mathurat.controller.interfaces.Listable;
import com.apprikot.mathurat.controller.utils.PrefHelp;
import com.apprikot.mathurat.model.MathuratData;
import com.apprikot.mathurat.view.custom.CheckableImageView;
import com.apprikot.mathurat.view.custom.CustomTextView;
import com.db.chart.Tools;
import com.db.chart.animation.Animation;
import com.db.chart.animation.easing.LinearEase;
import com.db.chart.model.BarSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.view.BarChartView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class EvaluationFragment extends BaseListFragmentNew {


    public EvaluationFragment() {
    }

    private List<MathuratData> mathuratDataList;

    private List<MathuratData> satList = new ArrayList<>();
    private List<MathuratData> sunList = new ArrayList<>();
    private List<MathuratData> monList = new ArrayList<>();
    private List<MathuratData> tusList = new ArrayList<>();
    private List<MathuratData> wenList = new ArrayList<>();
    private List<MathuratData> thuList = new ArrayList<>();
    private List<MathuratData> friList = new ArrayList<>();

    private final String[] mLabels = {"Sat", "Sun", "Mon", "Tues", "Wed", "Thu", "Fri"};
    private List<Float> mValues = new ArrayList<>();
    private float sat = 0, sun = 0, mon = 0, tus = 0, wen = 0, thu = 0, fri = 0;
    private float max = 5;
    private float[] valArray;
    private final Integer[] days = {R.string.sat_thaker, R.string.sun_thaker, R.string.mon_thaker, R.string.tues_thaker, R.string.wens_thaker, R.string.thu_thaker, R.string.fri_thaker};
    private BarChartView mChart;
    private CheckableImageView arrow_left, arrow_right;
    //    private CustomTextView day_thaker, x_title;
    private CustomTextView day_thaker;
    private int currDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_evaluation, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar();
        prepareDateData();
        setChartValue();
        prepareChart();
        setUpDayList();

    }

    private void setUpToolbar() {
        setToolbarItems(ToolbarItem.BACK, ToolbarItem.NONE, ToolbarItem.NONE, ToolbarItem.NONE);
        setToolbarCenter(ToolbarItem.TEXT, getString(R.string.evaluation));
    }

    private void prepareDateData() {
        mathuratDataList = PrefHelp.getData(getActivity());
        for (MathuratData mathuratData : mathuratDataList) {
            if (mathuratData.sat_counter != 0) {
                satList.add(mathuratData);
            }
            if (mathuratData.sun_counter != 0) {
                sunList.add(mathuratData);
            }
            if (mathuratData.mon_counter != 0) {
                monList.add(mathuratData);
            }
            if (mathuratData.tus_counter != 0) {
                tusList.add(mathuratData);
            }
            if (mathuratData.wen_counter != 0) {
                wenList.add(mathuratData);
            }
            if (mathuratData.thu_counter != 0) {
                thuList.add(mathuratData);
            }
            if (mathuratData.fri_counter != 0) {
                friList.add(mathuratData);
            }
        }
    }

    private void setChartValue() {
        for (MathuratData data : mathuratDataList) {
            sat += data.sat_counter;
            sun += data.sun_counter;
            mon += data.mon_counter;
            tus += data.tus_counter;
            wen += data.wen_counter;
            thu += data.thu_counter;
            fri += data.fri_counter;
        }
        mValues.add(sat);
        mValues.add(sun);
        mValues.add(mon);
        mValues.add(tus);
        mValues.add(wen);
        mValues.add(thu);
        mValues.add(fri);
        valArray = new float[mValues.size()];
        for (int i = 0; i < mValues.size(); i++) {
            if (max < mValues.get(i)) {
                max = mValues.get(i);
            }
            valArray[i] = mValues.get(i);
        }
    }

    private void prepareChart() {
//        x_title = (CustomTextView) root.findViewById(x_title);
//        RotateAnimation ranim = (RotateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_text);
//        ranim.setFillAfter(true);
//        x_title.setAnimation(ranim);
        mChart = (BarChartView) root.findViewById(R.id.chart);
        BarSet dataset = new BarSet(mLabels, valArray);
        dataset.setColor(ContextCompat.getColor(getActivity(), R.color.color_8));
        mChart.addData(dataset);
        if (max >= 5) {
            mChart.setStep((int) (max / 5));
        }
        mChart.setBarSpacing(Tools.fromDpToPx(15));
        mChart.setXLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setYLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setXAxis(true)
                .setYAxis(true);
        mChart.show(new Animation().setEasing(new LinearEase()));
    }

    private void setUpDayList() {
        arrow_left = (CheckableImageView) root.findViewById(R.id.arrow_left);
        arrow_right = (CheckableImageView) root.findViewById(R.id.arrow_right);
        day_thaker = (CustomTextView) root.findViewById(R.id.day_thaker);
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SATURDAY: {
                currDay = 0;
                day_thaker.setText(getString(days[currDay]));
                drawDayItem(satList, 0);
                break;
            }
            case Calendar.SUNDAY: {
                currDay = 1;
                day_thaker.setText(getString(days[currDay]));
                drawDayItem(sunList, 1);
                break;
            }
            case Calendar.MONDAY: {
                currDay = 2;
                day_thaker.setText(getString(days[currDay]));
                drawDayItem(monList, 2);
                break;
            }
            case Calendar.TUESDAY: {
                currDay = 3;
                day_thaker.setText(getString(days[currDay]));
                drawDayItem(tusList, 3);
                break;
            }
            case Calendar.WEDNESDAY: {
                currDay = 4;
                day_thaker.setText(getString(days[currDay]));
                drawDayItem(wenList, 4);
                break;
            }
            case Calendar.THURSDAY: {
                currDay = 5;
                day_thaker.setText(getString(days[currDay]));
                drawDayItem(thuList, 5);
                break;
            }
            case Calendar.FRIDAY: {
                currDay = 6;
                day_thaker.setText(getString(days[currDay]));
                drawDayItem(friList, 6);
                break;
            }
        }
        attachClickListener(function, arrow_left, arrow_right);
    }

    private void drawDayItem(List<MathuratData> list, int day) {
        List<Listable> items = new ArrayList<>();
        for (MathuratData mathurat : list) {
            mathurat.listItemType = ListItemType.DAY_THAKER;
            mathurat.evaluation_day = day;
            items.add(mathurat);
        }
        drawItems(items);
    }

    @Override
    public void onToolbarItemClicked(ToolbarItem toolbarItem, ImageView checkableImageView) {
        super.onToolbarItemClicked(toolbarItem, checkableImageView);
//        vibrator.vibrate(Constants.VIBRATE_VALUE);
        switch (toolbarItem) {
            case BACK: {
                getActivity().onBackPressed();
                break;
            }
        }
    }

    View.OnClickListener function = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            vibrator.vibrate(Constants.VIBRATE_VALUE);
            switch (view.getId()) {
                case R.id.arrow_left: {
                    if (currDay > 0) {
                        currDay -= 1;
                    } else {
                        currDay = days.length - 1;
                    }
                    day_thaker.setText(days[currDay]);
                    drawMovmentDay(currDay);
                    break;
                }
                case R.id.arrow_right: {
                    if (currDay < (days.length - 1)) {
                        currDay += 1;
                    } else {
                        currDay = 0;
                    }
                    day_thaker.setText(days[currDay]);
                    drawMovmentDay(currDay);
                    break;
                }
            }
        }
    };


    private void drawMovmentDay(int day) {
        switch (day) {
            case 0: {
                drawDayItem(satList, day);
                break;
            }
            case 1: {
                drawDayItem(sunList, day);
                break;
            }
            case 2: {
                drawDayItem(monList, day);
                break;
            }
            case 3: {
                drawDayItem(tusList, day);
                break;
            }
            case 4: {
                drawDayItem(wenList, day);
                break;
            }
            case 5: {
                drawDayItem(thuList, day);
                break;
            }
            case 6: {
                drawDayItem(friList, day);
                break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        TrackApps.getInstance().trackScreenView(EvaluationFragment.class.getSimpleName());
    }
}
