package com.apprikot.mathurat.controller.fragments.tasbeh;

import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.application.TrackApps;
import com.apprikot.mathurat.controller.components.Constants;
import com.apprikot.mathurat.controller.enums.ToolbarItem;
import com.apprikot.mathurat.controller.fragments.base.BaseFragment;
import com.apprikot.mathurat.controller.interfaces.ActionListener;
import com.apprikot.mathurat.controller.interfaces.OnItemSwipeListener;
import com.apprikot.mathurat.controller.utils.PrefHelp;
import com.apprikot.mathurat.controller.utils.StringUtils;
import com.apprikot.mathurat.model.MathuratData;
import com.apprikot.mathurat.model.TasbehData;
import com.apprikot.mathurat.view.adapters.BlankPagerAdapter;
import com.apprikot.mathurat.view.adapters.TasbehPagerAdapter;
import com.apprikot.mathurat.view.custom.CheckableImageView;
import com.apprikot.mathurat.view.custom.CustomEditText;
import com.github.lzyzsd.circleprogress.DonutProgress;
import java.util.List;

public class TasbehFragment extends BaseFragment implements ActionListener, ViewPager.OnPageChangeListener, OnItemSwipeListener {


    public static final String TAG = TasbehFragment.class.getSimpleName();
    private List<TasbehData> tasbehDataList;
    private TasbehData tasbehData;
    private ViewPager horizentalViewPager;
    private DonutProgress donut_progress;
    private CheckableImageView sound, setting, refresh_progress;
    private TasbehPagerAdapter tasbehPager;
    protected int selectedPage;

    private boolean isSound = true;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;
    private View promptsView;
    private MediaPlayer mp;

    private CustomEditText userInput;

    public TasbehFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tasbeh, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
        setToolbarItem();
        setUpView();
        setUpViewPager();

    }

    private void setUpViewPager() {
        if (tasbehPager == null) {
            tasbehPager = new TasbehPagerAdapter(getChildFragmentManager(), tasbehDataList, this);
            selectedPage = 0;
        } else {
            tasbehPager = tasbehPager.clone(getChildFragmentManager(), tasbehDataList, this);
        }
        horizentalViewPager.setPageMargin(getActivity().getResources().getDimensionPixelOffset(R.dimen.value20));
        horizentalViewPager.setAdapter(tasbehPager);
        horizentalViewPager.setCurrentItem(selectedPage);
        horizentalViewPager.addOnPageChangeListener(this);
        onPageSelected(selectedPage);
    }

    private void setUpView() {
        mp = MediaPlayer.create(getActivity(), R.raw.click_sound);
        horizentalViewPager = (ViewPager) root.findViewById(R.id.horizental_vp);
//        horizentalViewPager.setOnItemClickListener(pagerClick);
        sound = (CheckableImageView) root.findViewById(R.id.sound);
        sound.setChecked(isSound);
        setting = (CheckableImageView) root.findViewById(R.id.setting);
        refresh_progress = (CheckableImageView) root.findViewById(R.id.refresh_progress);
        donut_progress = (DonutProgress) root.findViewById(R.id.donut_progress);
        attachClickListener(fullFunction, sound, setting, donut_progress);

    }

    private void loadData() {
        tasbehDataList = PrefHelp.getTasbeh(getActivity());
    }

    private void setToolbarItem() {
        setToolbarItems(ToolbarItem.BACK, ToolbarItem.NONE, ToolbarItem.NONE, ToolbarItem.NONE);
//        setToolbarCenter(ToolbarItem.TEXT, tasbeh);
    }

    @Override
    public void onToolbarItemClicked(ToolbarItem toolbarItem, ImageView checkableImageView) {
        super.onToolbarItemClicked(toolbarItem, checkableImageView);
        vibrator.vibrate(Constants.VIBRATE_VALUE);
        switch (toolbarItem) {
            case BACK: {
                getActivity().onBackPressed();
                break;
            }
        }
    }


    View.OnClickListener fullFunction = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.sound: {
                    sound.setChecked(!sound.isChecked());
                    isSound = sound.isChecked();
                    if (isSound) {
                        mp.start();
                    } else {
                        vibrator.vibrate(Constants.VIBRATE_VALUE);
                    }
                    break;
                }
                case R.id.setting: {
                    vibrator.vibrate(Constants.VIBRATE_VALUE);
                    LayoutInflater li = LayoutInflater.from(getActivity());
                    promptsView = li.inflate(R.layout.prompts, null);
                    userInput = (CustomEditText) promptsView.findViewById(R.id.userInput);
                    alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setView(promptsView);
                    alertDialogBuilder.setCancelable(true).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int id) {
                                            if (!userInput.getText().toString().equalsIgnoreCase("") && !userInput.getText().toString().isEmpty()) {
                                                if(isNumeric(userInput.getText().toString())){
                                                    vibrator.vibrate(Constants.VIBRATE_VALUE);
                                                    tasbehData.isMaxSet = true;
                                                    tasbehData.max_counter = Integer.parseInt(userInput.getText().toString());
                                                    tasbehData.counter = 0;
                                                    donut_progress.setMax(tasbehData.max_counter);
                                                    donut_progress.setProgress(tasbehData.counter);
                                                    donut_progress.setFinishedStrokeColor(R.color.color_3);
                                                    tasbehDataList.set(selectedPage, tasbehData);
                                                    StringUtils.replaceTasbeh(getActivity(), tasbehDataList, tasbehData);
                                                }else{
                                                    getToast(R.string.valid_value).show();
                                                    dialog.dismiss();
                                                }
                                            } else {
                                                getToast(R.string.no_value_enterd).show();
                                                dialog.dismiss();
                                            }
                                        }
                                    }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            vibrator.vibrate(Constants.VIBRATE_VALUE);
                                            dialog.cancel();
                                        }
                                    });
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    break;
                }
                case R.id.donut_progress: {
                    increaseCounter();
                    break;
                }
            }
        }
    };

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(.\\d+)?");
    }


//    ClickableViewPager.OnItemClickListener pagerClick = new ClickableViewPager.OnItemClickListener() {
//        @Override
//        public void onItemClick(int position) {
//            Log.d(TAG, " Pager Click ");
//            increaseCounter();
//        }
//    };

    private void increaseCounter() {
        if (tasbehData.isMaxSet) {
            if (!tasbehData.refresh_counter) {
                tasbehData.counter = tasbehData.counter + 1;
                donut_progress.setProgress(tasbehData.counter);
                tasbehDataList.set(selectedPage, tasbehData);
                if (tasbehData.counter == tasbehData.max_counter) {
                    if (isSound) {
                        mp.start();
                    } else {
                        vibrator.vibrate(Constants.VIBRATE_FINSH_VALUE);
                    }
                    tasbehData.refresh_counter = true;
                    donut_progress.setShowText(false);
                    refresh_progress.setVisibility(View.VISIBLE);
                } else {
                    if (isSound) {
                        mp.start();
                    } else {
                        vibrator.vibrate(Constants.VIBRATE_VALUE);
                    }
                }
            } else {
                tasbehData.refresh_counter = false;
                tasbehData.counter = 0;
                donut_progress.setShowText(true);
                refresh_progress.setVisibility(View.GONE);
                tasbehDataList.set(selectedPage, tasbehData);
                donut_progress.setProgress(tasbehData.counter);
                if (isSound) {
                    mp.start();
                } else {
                    vibrator.vibrate(Constants.VIBRATE_VALUE);
                }
            }
        } else {
            tasbehData.counter = tasbehData.counter + 1;
            donut_progress.setMax(tasbehData.counter + 1);
            donut_progress.setProgress(tasbehData.counter);
            tasbehDataList.set(selectedPage, tasbehData);
            if (isSound) {
                mp.start();
            } else {
                vibrator.vibrate(Constants.VIBRATE_VALUE);
            }
        }
        StringUtils.replaceTasbeh(getActivity(), tasbehDataList, tasbehData);
    }

    @Override
    public void onAction(int action) {
        if (action == Constants.ACTION_BLANK_ADAPTER) {
            horizentalViewPager.setAdapter(new BlankPagerAdapter(getActivity().getSupportFragmentManager()));
        } else if (action == Constants.ACTION_ORIGINAL_ADAPTER) {
            horizentalViewPager.setAdapter(tasbehPager);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(TAG, "onPageScrolled - Position: " + position);

    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageSelected - Position: " + position);
        selectedPage = position;
        tasbehData = tasbehPager.getItemData(position);
        if (tasbehData.isMaxSet) {
            donut_progress.setFinishedStrokeColor(R.color.color_3);
            if (!tasbehData.refresh_counter) {
                donut_progress.setShowText(true);
                refresh_progress.setVisibility(View.GONE);
                donut_progress.setMax(tasbehData.max_counter);
                donut_progress.setProgress(tasbehData.counter);
            } else {
                donut_progress.setShowText(false);
                refresh_progress.setVisibility(View.VISIBLE);
            }
        } else {
            donut_progress.setFinishedStrokeColor(Color.TRANSPARENT);
            donut_progress.setUnfinishedStrokeColor(Color.TRANSPARENT);
            donut_progress.setShowText(true);
            refresh_progress.setVisibility(View.GONE);
            donut_progress.setMax(tasbehData.counter + 1);
            donut_progress.setProgress(tasbehData.counter);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        TrackApps.getInstance().trackScreenView(TasbehFragment.class.getSimpleName());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "onPageScrollStateChanged - Position: " + state);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onFragmentSwiped(MathuratData data) {

    }

    @Override
    public void onFragmentTextClick(MathuratData mathuratData) {
    }

    @Override
    public void onScrolledEnd(MathuratData mathuratData, String move) {

    }

    @Override
    public void onFragmentTasbehClick() {
        increaseCounter();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
