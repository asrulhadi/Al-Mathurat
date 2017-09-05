package com.apprikot.mathurat.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.components.Constants;
import com.apprikot.mathurat.controller.enums.ToolbarItem;
import com.apprikot.mathurat.controller.interfaces.OnWheelChangedListener;
import com.apprikot.mathurat.controller.interfaces.OnWheelClickedListener;
import com.apprikot.mathurat.controller.interfaces.OnWheelScrollListener;
import com.apprikot.mathurat.controller.receiver.NotifyReceiver;
import com.apprikot.mathurat.controller.utils.AppUtils;
import com.apprikot.mathurat.controller.utils.PrefHelp;
import com.apprikot.mathurat.model.Notify;
import com.apprikot.mathurat.view.adapters.ArrayWheelAdapter;
import com.apprikot.mathurat.view.adapters.NumericWheelAdapter;
import com.apprikot.mathurat.view.custom.CheckableImageView;
import com.apprikot.mathurat.view.custom.CustomTextView;
import com.apprikot.mathurat.view.custom.CustomTextViewContent;
import com.apprikot.mathurat.view.custom.WheelView;
import com.bumptech.glide.Glide;

public class SettingActivity extends BaseActivity {


    private Context mContext = this;
    private WheelView hours, mins, ampm;
    private boolean timeChanged = false;
    private boolean timeScrolled = false;
    private int hourInt, minInt, ampmInt;
    private Notify notify;
    private CheckableImageView english, arabic, minus, pluse, all_notify_on, all_notify_off;
    private CustomTextView lang;
    private CustomTextViewContent example;
    private VideoView videoView;
    private int max_size_font, min_size_font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        max_size_font = getResources().getInteger(R.integer.max_size_font);
        min_size_font = getResources().getInteger(R.integer.min_size_font);
        runBackGround();
        loadData();
        setToolbarItem();
        setUpView();
    }


    private void runBackGround() {
        videoView = (VideoView) findViewById(R.id.video_view_play);
        AppUtils.playVideo(mContext, videoView, R.raw.android_bg_mathurat);
    }

    private void setToolbarItem() {
        initToolBar((LinearLayout) findViewById(R.id.toolbar));
        setToolbarItems(ToolbarItem.BACK, ToolbarItem.NONE, ToolbarItem.NONE, ToolbarItem.NONE);
        setToolbarCenter(ToolbarItem.TEXT, R.string.setting);
    }

    private void setUpView() {
        lang = (CustomTextView) findViewById(R.id.lang);
        example = (CustomTextViewContent) findViewById(R.id.example);
        english = (CheckableImageView) findViewById(R.id.english);
        english.setChecked(PrefHelp.getLang(mContext).equalsIgnoreCase("en"));
        arabic = (CheckableImageView) findViewById(R.id.arabic);
        arabic.setChecked(PrefHelp.getLang(mContext).equalsIgnoreCase("ar"));
        lang.setText(PrefHelp.getLang(mContext).equalsIgnoreCase("ar") ? getString(R.string.lang_text) : getString(R.string.lang_text_en));
        minus = (CheckableImageView) findViewById(R.id.minus);
        pluse = (CheckableImageView) findViewById(R.id.pluse);

        all_notify_on = (CheckableImageView) findViewById(R.id.all_notify_on);
        all_notify_on.setChecked(notify.isNotify);
        all_notify_off = (CheckableImageView) findViewById(R.id.all_notify_off);
        all_notify_off.setChecked(!notify.isNotify);

        hours = (WheelView) findViewById(R.id.hour);
        NumericWheelAdapter hourAdapter = new NumericWheelAdapter(mContext, 1, 12);
        hourAdapter.setItemResource(R.layout.wheel_text_item);
        hourAdapter.setItemTextResource(R.id.text);
        hours.setViewAdapter(hourAdapter);
        hours.setCyclic(true);

        mins = (WheelView) findViewById(R.id.mins);
        NumericWheelAdapter minAdapter = new NumericWheelAdapter(mContext, 0, 59, "%02d");
        minAdapter.setItemResource(R.layout.wheel_text_item);
        minAdapter.setItemTextResource(R.id.text);
        mins.setViewAdapter(minAdapter);
        mins.setCyclic(true);

        ampm = (WheelView) findViewById(R.id.ampm);
        ArrayWheelAdapter<String> ampmAdapter = new ArrayWheelAdapter<String>(mContext, new String[]{"AM", "PM"});
        ampmAdapter.setItemResource(R.layout.wheel_text_item);
        ampmAdapter.setItemTextResource(R.id.text);
        ampm.setViewAdapter(ampmAdapter);
        if (notify.notify_hour == 0) {
            hours.setCurrentItem(notify.notify_hour + 11);
        } else {
            hours.setCurrentItem(notify.notify_hour - 1);
        }
        mins.setCurrentItem(notify.notify_min);
        ampm.setCurrentItem(notify.notify_am_pm);

        hours.addChangingListener(wheelListener);
        mins.addChangingListener(wheelListener);
        ampm.addChangingListener(wheelListener);

        hours.addClickingListener(click);
        mins.addClickingListener(click);
        ampm.addClickingListener(click);

        hours.addScrollingListener(scrollListener);
        mins.addScrollingListener(scrollListener);
        ampm.addScrollingListener(scrollListener);

        attachClickListener(function, english, arabic, minus, pluse, all_notify_on, all_notify_off);
    }


    private void loadData() {
        notify = PrefHelp.getAllNotify(mContext);
    }


    @Override
    public void onToolbarItemClicked(ToolbarItem toolbarItem, ImageView checkableImageView) {
        super.onToolbarItemClicked(toolbarItem, checkableImageView);
        switch (toolbarItem) {
            case BACK: {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            }
        }
    }

    View.OnClickListener function = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            vibrator.vibrate(Constants.VIBRATE_VALUE);
            switch (view.getId()) {
                case R.id.english: {
                    if (!english.isChecked()) {
                        english.toggle();
                        arabic.toggle();
                        lang.setText(getString(R.string.lang_text_en));
                        PrefHelp.setLang(mContext, "en");
                        Intent intent = getIntent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        overridePendingTransition(0, 0);
                        startActivity(intent);
                        finish();
                    }
                    break;
                }
                case R.id.arabic: {
                    if (!arabic.isChecked()) {
                        arabic.toggle();
                        english.toggle();
                        lang.setText(getString(R.string.lang_text));
                        PrefHelp.setLang(mContext, "ar");
                        Intent intent = getIntent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        overridePendingTransition(0, 0);
                        startActivity(intent);
                        finish();
                    }
                    break;
                }

                case R.id.all_notify_on: {
                    if (!all_notify_on.isChecked()) {
                        all_notify_on.toggle();
                        all_notify_off.toggle();
                        notify.isNotify = all_notify_on.isChecked();
                        notify.notify_hour = hourInt;
                        notify.notify_min = minInt;
                        notify.notify_am_pm = ampmInt;
                        notify.notify_days = "true,true,true,true,true,true,true";
                        PrefHelp.setAllNotify(mContext, notify);
                        NotifyReceiver.setAlarms(mContext);
                    }
                    break;
                }
                case R.id.all_notify_off: {
                    if (!all_notify_off.isChecked()) {
                        all_notify_off.toggle();
                        all_notify_on.toggle();
                        notify.isNotify = all_notify_on.isChecked();
                        notify.notify_days = "false,false,false,false,false,false,false";
                        PrefHelp.setAllNotify(mContext, notify);
                        NotifyReceiver.setAlarms(mContext);
                    }
                    break;
                }

                case R.id.pluse: {
                    float currentSize = PrefHelp.getFontSize(mContext);
                    if (((int) currentSize) < max_size_font) {
                        PrefHelp.setFontSize(mContext, currentSize + 1);
                        example.setTextSize(PrefHelp.getFontSize(mContext));
                        Intent intent = getIntent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        overridePendingTransition(0, 0);
                        startActivity(intent);
                        finish();
                    }
                    break;
                }
                case R.id.minus: {
                    float currentSize = PrefHelp.getFontSize(mContext);
                    if (((int) currentSize) >= min_size_font) {
                        PrefHelp.setFontSize(mContext, currentSize - 1);
                        example.setTextSize(PrefHelp.getFontSize(mContext));
                        Intent intent = getIntent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        overridePendingTransition(0, 0);
                        startActivity(intent);
                        finish();
                    }
                    break;
                }
            }
        }
    };


    OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!timeScrolled) {
                timeChanged = true;
                hourInt = hours.getCurrentItem() + 1;
                if (hourInt == 12) {
                    hourInt = 0;
                }
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
            hourInt = hours.getCurrentItem() + 1;
            if (hourInt == 12) {
                hourInt = 0;
            }
            minInt = mins.getCurrentItem();
            ampmInt = ampm.getCurrentItem();
            timeChanged = false;
            if(all_notify_on.isChecked()){
                saveNotify();
            }
            Log.d(TAG, "items: " + hourInt + " - " + minInt + " - " + ampmInt);
        }
    };


    public void saveNotify(){
        notify.isNotify = all_notify_on.isChecked();
        notify.notify_hour = hourInt;
        notify.notify_min = minInt;
        notify.notify_am_pm = ampmInt;
        notify.notify_days = "true,true,true,true,true,true,true";
        PrefHelp.setAllNotify(mContext, notify);
        NotifyReceiver.setAlarms(mContext);
    }


    @Override
    protected void onStop() {
        videoView.stopPlayback();
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppUtils.playVideo(mContext, videoView, R.raw.android_bg_mathurat);
    }

    @Override
    protected void onDestroy() {
        videoView.stopPlayback();
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
//        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }


}
