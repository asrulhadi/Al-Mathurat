package com.apprikot.mathurat.controller.fragments.home;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.enums.ToolbarItem;
import com.apprikot.mathurat.controller.fragments.Athkar.AthkarFragment;
import com.apprikot.mathurat.controller.fragments.base.BaseFragment;
import com.apprikot.mathurat.controller.fragments.tasbeh.TasbehFragment;
import com.apprikot.mathurat.controller.utils.PrefHelp;
import com.apprikot.mathurat.controller.utils.StringUtils;
import com.apprikot.mathurat.model.MathuratData;
import com.apprikot.mathurat.view.custom.CustomTextView;

import java.util.Calendar;
import java.util.List;


public class HomeFragment extends BaseFragment {


    private CustomTextView txt_middle, athkar_txt, tasbeh_txt;
    private List<MathuratData> mathuratDatas;
    private int randomNum = 0;
//    private MathuratData notifyData;
//    private Parcelable parcelable;


    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            parcelable = getArguments().getParcelable(EXTRA_PARCELABLE);
//            if (parcelable != null) {
//                if (parcelable instanceof MathuratData) {
//                    notifyData = (MathuratData) parcelable;
//                }
//            }
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        randomNum = PrefHelp.getRandomNum(getActivity());
        mathuratDatas = PrefHelp.getData(getActivity());
        setToolbarItems(ToolbarItem.LEFT_MENU, ToolbarItem.NONE, ToolbarItem.NONE, ToolbarItem.RIGHT_MENU);
        setToolbarCenter(ToolbarItem.TEXT, getString(R.string.app_name));
        if (PrefHelp.getIsAllNotifyOff(getActivity())) {
            toolbarAdapter.setItemChecked(ToolbarItem.RIGHT_MENU, true, false);
        }
        setDaysCounter(mathuratDatas);
        setUpView();
//        if(notifyData != null){
//            moveToMainFragment(new AthkarFragment(), R.id.container, true);
//        }
    }

    private void setDaysCounter(List<MathuratData> mathuratDatas) {
        Calendar c = Calendar.getInstance();
        int current_day = c.get(Calendar.DAY_OF_WEEK);
        switch (current_day) {
            case Calendar.SATURDAY: {
                for (MathuratData data : mathuratDatas) {
                    if (!data.reset_sat_counter) {
                        data.sat_counter = 0;
                        data.reset_sat_counter = true;
                        data.reset_sun_counter = false;
                        mathuratDatas.set(data.id, data);
                    }
                }
                break;
            }
            case Calendar.SUNDAY: {
                for (MathuratData data : mathuratDatas) {
                    if (!data.reset_sun_counter) {
                        data.sun_counter = 0;
                        data.reset_sun_counter = true;
                        data.reset_mon_counter = false;
                        mathuratDatas.set(data.id, data);

                    }
                }
                break;
            }
            case Calendar.MONDAY: {
                for (MathuratData data : mathuratDatas) {
                    if (!data.reset_mon_counter) {
                        data.mon_counter = 0;
                        data.reset_mon_counter = true;
                        data.reset_tus_counter = false;
                        mathuratDatas.set(data.id, data);
                    }
                }
                break;
            }
            case Calendar.TUESDAY: {
                for (MathuratData data : mathuratDatas) {
                    if (!data.reset_tus_counter) {
                        data.tus_counter = 0;
                        data.reset_tus_counter = true;
                        data.reset_wen_counter = false;
                        mathuratDatas.set(data.id, data);
                    }
                }
                break;
            }
            case Calendar.WEDNESDAY: {
                for (MathuratData data : mathuratDatas) {
                    if (!data.reset_wen_counter) {
                        data.wen_counter = 0;
                        data.reset_wen_counter = true;
                        data.reset_thu_counter = false;
                        mathuratDatas.set(data.id, data);
                    }
                }
                break;
            }
            case Calendar.THURSDAY: {
                for (MathuratData data : mathuratDatas) {
                    if (!data.reset_thu_counter) {
                        data.thu_counter = 0;
                        data.reset_thu_counter = true;
                        data.reset_fri_counter = false;
                        mathuratDatas.set(data.id, data);
                    }
                }
                break;
            }
            case Calendar.FRIDAY: {
                for (MathuratData data : mathuratDatas) {
                    if (!data.reset_fri_counter) {
                        data.fri_counter = 0;
                        data.reset_fri_counter = true;
                        data.reset_sat_counter = false;
                        mathuratDatas.set(data.id, data);
                    }
                }
                break;
            }
        }
        PrefHelp.setData(getActivity(), mathuratDatas);
    }

    private void setUpView() {
        txt_middle = (CustomTextView) root.findViewById(R.id.txt_middle);
        txt_middle.setText(mathuratDatas.get(randomNum).isArabic_txt ? mathuratDatas.get(randomNum).text_ar : mathuratDatas.get(randomNum).text_en);
        athkar_txt = (CustomTextView) root.findViewById(R.id.athkar_txt);
        tasbeh_txt = (CustomTextView) root.findViewById(R.id.tasbeh_txt);
        attachClickListener(func, athkar_txt, tasbeh_txt);

    }


    View.OnClickListener func = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.athkar_txt: {
                    moveToMainFragment(new AthkarFragment(), R.id.container, true);
                    break;
                }
                case R.id.tasbeh_txt: {
                    moveToMainFragment(new TasbehFragment(), R.id.container, true);
                    break;
                }
            }
        }
    };


    @Override
    public void onToolbarItemClicked(ToolbarItem toolbarItem, ImageView checkableImageView) {
        super.onToolbarItemClicked(toolbarItem, checkableImageView);
        switch (toolbarItem) {
            case LEFT_MENU: {
                openSideMenu(GravityCompat.START);
                break;
            }
            case RIGHT_MENU: {
                if (toolbarAdapter.isItemChecked(toolbarItem)) {
                    toolbarAdapter.setItemChecked(toolbarItem, !toolbarAdapter.isItemChecked(toolbarItem), true);
                    StringUtils.backAndONAllNotify(getActivity());
                    PrefHelp.setIsAllNotifyOff(getActivity(), false);
                } else {
                    toolbarAdapter.setItemChecked(toolbarItem, !toolbarAdapter.isItemChecked(toolbarItem), true);
                    StringUtils.saveAndOffAllNotify(getActivity(), mathuratDatas);
                    PrefHelp.setIsAllNotifyOff(getActivity(), true);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
