package com.apprikot.mathurat.controller.fragments.Athkar;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.MediaStore;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.application.TrackApps;
import com.apprikot.mathurat.controller.components.Constants;
import com.apprikot.mathurat.controller.enums.ListItemType;
import com.apprikot.mathurat.controller.enums.ToolbarItem;
import com.apprikot.mathurat.controller.fragments.base.BaseListFragmentNew;
import com.apprikot.mathurat.controller.fragments.tasbeh.TasbehFragment;
import com.apprikot.mathurat.controller.interfaces.ActionListener;
import com.apprikot.mathurat.controller.interfaces.Listable;
import com.apprikot.mathurat.controller.interfaces.OnItemSwipeListener;
import com.apprikot.mathurat.controller.utils.PrefHelp;
import com.apprikot.mathurat.controller.utils.StringUtils;
import com.apprikot.mathurat.model.MathuratData;
import com.apprikot.mathurat.view.adapters.AthkarPagerAdapter;
import com.apprikot.mathurat.view.adapters.BlankPagerAdapter;
import com.apprikot.mathurat.view.custom.CheckableImageView;
import com.apprikot.mathurat.view.custom.VerticalViewPager;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AthkarFragment extends BaseListFragmentNew implements ActionListener, ViewPager.OnPageChangeListener, OnItemSwipeListener {

    public static final String TAG = AthkarFragment.class.getSimpleName();
//    public static final String SOUND_PATH = Constants.JSON_SOUND_PATH;
    public static final String SOUND_PATH = Constants.JSON_SOUND;

    private List<MathuratData> mathuratDataList;
    private List<MathuratData> mathuratListTmp = new ArrayList<>();
    private MathuratData mathuratData;
    private VerticalViewPager verticalViewPager;
    private LinearLayout share, detail;
    private DonutProgress donut_progress;
    private CheckableImageView refresh_progress;
    private RelativeLayout fullPage;
    private AthkarPagerAdapter athkarPager;
    private int selectedPage;
    private boolean shareIntent = false;


    private CheckableImageView lang, speed, previous, next, anchor, repeat, play_pause;
    private MediaPlayer mp;
    private int currentSoundId = 0;
    private int currDay;
    private int count = 0;
    private boolean isRepeat = false; // if true repeat sound
    private boolean langchange = false; // if true lang is english
    private boolean speedChange = false; // if true speed 1.5
    private boolean isAnchor = false; // if true sound fast


    public AthkarFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_athkar, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar c = Calendar.getInstance();
        currDay = c.get(Calendar.DAY_OF_WEEK);
        mathuratDataList = PrefHelp.getData(getActivity());
        count = mathuratDataList.size() - 1;
        setToolbarItem();
        setUpView();
        checkVisibality();
        setUpViewPager();
        loadData();
    }

    private void checkVisibality() {

//        if(PrefHelp.isPagerVisible(getActivity())){
//            recyclerView.setVisibility(View.GONE);
//            fullPage.setVisibility(View.VISIBLE);
//        }else{
//            recyclerView.setVisibility(View.VISIBLE);
//            fullPage.setVisibility(View.GONE);
//        }

        if (recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void setUpViewPager() {
        if (athkarPager == null) {
            athkarPager = new AthkarPagerAdapter(getChildFragmentManager(), mathuratDataList, this);
            selectedPage = 0;
        } else {
            athkarPager = athkarPager.clone(getChildFragmentManager(), mathuratDataList, this);
            selectedPage = currentSoundId;
        }
        verticalViewPager.setPageMargin(getActivity().getResources().getDimensionPixelOffset(R.dimen.value10));
        verticalViewPager.setAdapter(athkarPager);
        verticalViewPager.setCurrentItem(selectedPage);
        verticalViewPager.addOnPageChangeListener(this);
        onPageSelected(selectedPage);
    }

    private void setUpView() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        if(mp == null){
            mp = new MediaPlayer();
            mp.setOnCompletionListener(onCompletionListener);
        }

        verticalViewPager = (VerticalViewPager) root.findViewById(R.id.vertical_vp);
        share = (LinearLayout) root.findViewById(R.id.share);
        detail = (LinearLayout) root.findViewById(R.id.detail);
        donut_progress = (DonutProgress) root.findViewById(R.id.donut_progress);
        refresh_progress = (CheckableImageView) root.findViewById(R.id.refresh_progress);
        fullPage = (RelativeLayout) root.findViewById(R.id.fullPage);


        lang = (CheckableImageView) root.findViewById(R.id.lang);
        lang.setChecked(PrefHelp.isTextAudioLangChange(getActivity()));
        speed = (CheckableImageView) root.findViewById(R.id.speed);
        anchor = (CheckableImageView) root.findViewById(R.id.anchor);
        if (lang.isChecked()) {
            speed.setClickable(false);
            anchor.setClickable(false);
        } else {
            speed.setClickable(true);
            anchor.setClickable(true);
        }

        previous = (CheckableImageView) root.findViewById(R.id.previous);
        next = (CheckableImageView) root.findViewById(R.id.next);
        repeat = (CheckableImageView) root.findViewById(R.id.repeat);
        play_pause = (CheckableImageView) root.findViewById(R.id.play_pause);

        attachClickListener(fullFunction, share, detail, donut_progress);
        attachClickListener(playerFunction, lang, speed, previous, next, anchor, repeat, play_pause);

    }

    private void loadData() {
        List<Listable> items = new ArrayList<>();
        for (MathuratData mathuratData : mathuratDataList) {
            mathuratData.listItemType = ListItemType.MINI_THAKER;
            items.add(mathuratData);
        }
        drawItems(items);
        layoutManager.scrollToPositionWithOffset(currentSoundId, 15);
    }

    private void setToolbarItem() {
        setToolbarItems(ToolbarItem.LEFT_MENU, ToolbarItem.TASBEH, ToolbarItem.NONE, ToolbarItem.RV_SCREEN);
        setToolbarCenter(ToolbarItem.TEXT, getString(R.string.app_name));
    }

    @Override
    public void onToolbarItemClicked(ToolbarItem toolbarItem, ImageView checkableImageView) {
        super.onToolbarItemClicked(toolbarItem, checkableImageView);
        vibrator.vibrate(Constants.VIBRATE_VALUE);
        switch (toolbarItem) {
            case LEFT_MENU: {
                openSideMenu(GravityCompat.START);
                break;
            }
            case TASBEH: {
                moveToMainFragment(new TasbehFragment(), R.id.container, true);
                break;
            }
            case RV_SCREEN: {
                if (getCurrentFragmentManager().getBackStackEntryCount() > 0) {
                    getCurrentFragmentManager().popBackStack();
                }
                if (toolbarAdapter.isItemChecked(toolbarItem)) {
                    toolbarAdapter.setItemChecked(toolbarItem, false, false);
                    recyclerView.setVisibility(View.VISIBLE);
                    fullPage.setVisibility(View.GONE);
                } else {
                    toolbarAdapter.setItemChecked(toolbarItem, true, false);
                    recyclerView.setVisibility(View.GONE);
                    fullPage.setVisibility(View.VISIBLE);

                }
                break;
            }
        }
    }

    @Override
    public void onItemClicked(View view, Listable listableItem, int position) {
        super.onItemClicked(view, listableItem, position);
        vibrator.vibrate(Constants.VIBRATE_VALUE);
        MathuratData rv_mathuratData = (MathuratData) listableItem;
        mathuratData = rv_mathuratData;
        switch (view.getId()) {
            case R.id.share: {
                shareIntent = true;
                try {
                    Uri imageUri = null;
                    try {
                        String imgName = mathuratData.isArabic_txt ? mathuratData.img_ar : mathuratData.img_en;
                        imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), StringUtils.imgFromAssets(getActivity(), imgName), null, null));
                    } catch (Exception e) {
                        Log.d(TAG, e.getMessage());
                    }
                    String shareTxt = mathuratData.isArabic_txt ? mathuratData.text_ar : mathuratData.text_en;
                    shareTxt = shareTxt.replace("ِ<br>", "\n");
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("image/*");
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareTxt);
                    startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
                } catch (Exception ex) {
                    Log.d(TAG, ex.getMessage());
                }
                break;
            }
            case R.id.info: {
                moveToMainFragmentWithAnimation(DetailFragment.newInstance(mathuratData), R.id.detailContainer, true);
                break;
            }
            case R.id.donut_progress: {
                mathuratData.counter = mathuratData.counter - 1;
                if (mathuratData.counter == 0) {
                    mathuratData.refresh_counter = true;
                }
                mathuratData = StringUtils.calcDayCounter(currDay, mathuratData);
                StringUtils.replaceData(getActivity(), mathuratDataList, mathuratData);
                generalStickyListAdapter.notifyItemChanged(position);
                break;
            }
            case R.id.refresh_progress: {
                mathuratData.counter = mathuratData.max_counter;
                mathuratData.refresh_counter = false;
                StringUtils.replaceData(getActivity(), mathuratDataList, mathuratData);
                generalStickyListAdapter.notifyItemChanged(position);
                break;
            }
            case R.id.txt_value: {
                currentSoundId = rv_mathuratData.id;
                recyclerView.scrollToPosition(currentSoundId);
                verticalViewPager.setCurrentItem(currentSoundId);
                playAudio(mathuratData);
                break;
            }
        }
    }


    View.OnClickListener fullFunction = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            vibrator.vibrate(Constants.VIBRATE_VALUE);
            switch (view.getId()) {
                case R.id.share: {
                    shareIntent = true;
                    try {
                        Uri imageUri = null;
                        try {
                            String imgName = mathuratData.isArabic_txt ? mathuratData.img_ar : mathuratData.img_en;
                            imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), StringUtils.imgFromAssets(getActivity(), imgName), null, null));
                        } catch (Exception e) {
                            Log.d(TAG, e.getMessage());
                        }
                        String shareTxt = mathuratData.isArabic_txt ? mathuratData.text_ar : mathuratData.text_en;
                        shareTxt = shareTxt.replace("ِ<br>", "\n");
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("image/*");
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareTxt);
                        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
                    } catch (Exception ex) {
                        Log.d(TAG, ex.getMessage());
                    }
                    break;
                }
                case R.id.detail: {
                    Log.d(TAG, "detail");
                    moveToMainFragmentWithAnimation(DetailFragment.newInstance(mathuratData), R.id.detailContainer, true);
                    break;
                }
                case R.id.donut_progress: {
                    Log.d(TAG, "donut_progress");
                    decreaseCounter();
                    break;
                }
            }
        }
    };


    private void decreaseCounter() {
        if (!mathuratData.refresh_counter) {
            mathuratData.counter = mathuratData.counter - 1;
            donut_progress.setProgress(mathuratData.counter);
            mathuratDataList.set(selectedPage, mathuratData);
            mathuratData = StringUtils.calcDayCounter(currDay, mathuratData);
            if (mathuratData.counter == 0) {
                mathuratData.refresh_counter = true;
                donut_progress.setShowText(false);
                refresh_progress.setVisibility(View.VISIBLE);
            }
        } else {
            mathuratData.refresh_counter = false;
            mathuratData.counter = mathuratData.max_counter;
            donut_progress.setShowText(true);
            refresh_progress.setVisibility(View.GONE);
            mathuratDataList.set(selectedPage, mathuratData);
            donut_progress.setProgress(mathuratData.counter);
        }
        StringUtils.replaceData(getActivity(), mathuratDataList, mathuratData);
    }


    @Override
    public void onAction(int action) {
        if (action == Constants.ACTION_BLANK_ADAPTER) {
            verticalViewPager.setAdapter(new BlankPagerAdapter(getActivity().getSupportFragmentManager()));
        } else if (action == Constants.ACTION_ORIGINAL_ADAPTER) {
            verticalViewPager.setAdapter(athkarPager);
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
        mathuratData = athkarPager.getItemData(position);
        currentSoundId = mathuratData.id;
        if (!mathuratData.refresh_counter) {
            donut_progress.setShowText(true);
            refresh_progress.setVisibility(View.GONE);
            donut_progress.setMax(mathuratData.max_counter);
            donut_progress.setProgress(mathuratData.counter);
        } else {
            donut_progress.setShowText(false);
            refresh_progress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "onPageScrollStateChanged - Position: " + state);
    }


    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int swipPosition = viewHolder.getAdapterPosition();
            currentSoundId = swipPosition;
            mathuratData = mathuratDataList.get(currentSoundId);
            changeTextAudioThakerLang(mathuratData);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        }

        @Override
        public long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
            return animationType == ItemTouchHelper.ANIMATION_TYPE_SWIPE_SUCCESS ? DEFAULT_SWIPE_ANIMATION_DURATION : DEFAULT_DRAG_ANIMATION_DURATION;
        }

    };

    @Override
    public void onFragmentSwiped(MathuratData thaker) {
        currentSoundId = thaker.id;
        changeTextAudioThakerLang(thaker);
    }

    private void changeTextAudioThakerLang(MathuratData thaker) {

        List<Listable> items = new ArrayList<>();
        lang.setChecked(!lang.isChecked());
        langchange = lang.isChecked();
        if (langchange) {
//            PrefHelp.setLang(getActivity(), "en");
            thaker.langchange = true;
            mathuratListTmp.clear();
            for (MathuratData mathurat : mathuratDataList) {
                mathurat.langchange = true;
                mathurat.isArabic_txt = !mathurat.isArabic_txt;
                mathuratListTmp.add(mathurat);
            }
            mathuratDataList.clear();
            mathuratDataList.addAll(mathuratListTmp);
            speed.setChecked(false);
            anchor.setChecked(false);
            speed.setClickable(false);
            anchor.setClickable(false);
        } else {
//            PrefHelp.setLang(getActivity(), "ar");
            thaker.langchange = false;
            mathuratListTmp.clear();
            for (MathuratData mathurat : mathuratDataList) {
                mathurat.langchange = false;
                mathurat.isArabic_txt = !mathurat.isArabic_txt;
                mathuratListTmp.add(mathurat);
            }
            mathuratDataList.clear();
            mathuratDataList.addAll(mathuratListTmp);
            speed.setChecked(speedChange);
            anchor.setChecked(isAnchor);
            speed.setClickable(true);
            anchor.setClickable(true);
        }
        mathuratDataList.set(currentSoundId, thaker);
        PrefHelp.setIsTextAudioLangChange(getActivity(), langchange);

        items.addAll(mathuratDataList);
        generalStickyListAdapter = null;
        drawItems(items);
        setUpViewPager();
        recyclerView.scrollToPosition(thaker.id);
        verticalViewPager.setCurrentItem(thaker.id);
        if(mp != null){
            if(mp.isPlaying()){
                playAudio(thaker);
            }
        }
        PrefHelp.setData(getActivity(), mathuratDataList);
    }

    @Override
    public void onFragmentTextClick(MathuratData thaker) {
        currentSoundId = thaker.id;
        mathuratData = mathuratDataList.get(thaker.id);
        recyclerView.scrollToPosition(currentSoundId);
        playAudio(mathuratData);

    }

    @Override
    public void onScrolledEnd(MathuratData obj, String move) {
        switch (move) {
            case "up": {
                int pos = obj.id - 1;
                if (!(pos < 0 || pos > count)) {
                    verticalViewPager.setCurrentItem(pos, true);
                    onPageSelected(pos);
                }
                break;
            }
            case "down": {
                int pos = obj.id + 1;
                if (!(pos < 0 || pos > count)) {
                    verticalViewPager.setCurrentItem(pos, true);
                    onPageSelected(pos);
                }
                break;
            }
        }

    }

    @Override
    public void onFragmentTasbehClick() {

    }

    public void playAudio(MathuratData data) {
        try {


//            ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(getActivity(), Constants.VERSION_CODE, 0);
//            AssetFileDescriptor afd = null;
//            if(expansionFile != null){
//                mp.reset();
//                if (data.langchange) {
//                    afd = expansionFile.getAssetFileDescriptor(SOUND_PATH + data.sound_en_name);
//                } else {
//                    if (data.speedChange) {
//                        afd = expansionFile.getAssetFileDescriptor(SOUND_PATH + data.sound_ar_fast);
//                    } else {
//                        afd = expansionFile.getAssetFileDescriptor(SOUND_PATH + data.sound_ar_echo);
//                    }
//                }
//                if(afd != null){
//                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//                    mp.prepare();
//                    mp.start();
//                    play_pause.setImageResource(R.drawable.ic_pause);
//                }else{
//                    getToast(R.string.audio_messing).show();
//                }
//            }else{
//                getToast(R.string.audio_messing).show();
//            }

            /**

            ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(getActivity(), Constants.VERSION_CODE, 0);
            AssetFileDescriptor afd = null;
            if(expansionFile != null){
                mp.reset();
                if (data.langchange) {
                    afd = expansionFile.getAssetFileDescriptor(SOUND_PATH + data.sound_en_name);
                } else {
                    if (data.speedChange) {
                        afd = expansionFile.getAssetFileDescriptor(SOUND_PATH + data.sound_ar_fast);
                    } else if (data.isAnchor) {
                        afd = expansionFile.getAssetFileDescriptor(SOUND_PATH + data.sound_ar_echo);
                    } else {
                        afd = expansionFile.getAssetFileDescriptor(SOUND_PATH + data.sound_ar_name);
                    }
                }
                if(afd != null){
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mp.prepare();
                    mp.start();
                    play_pause.setImageResource(R.drawable.ic_pause);
                }else{
                    getToast(R.string.audio_messing).show();
                }
            }else{
                getToast(R.string.audio_messing).show();
            }

            mp.reset();
            AssetFileDescriptor afd = null;
            if (data.langchange) {
                afd = getActivity().getAssets().openFd(SOUND_PATH + data.sound_en_name);
            } else {
                if (data.speedChange) {
                    afd = getActivity().getAssets().openFd(SOUND_PATH + data.sound_ar_fast);
                } else if (data.isAnchor) {
                    afd = getActivity().getAssets().openFd(SOUND_PATH + data.sound_ar_echo);
                } else {
                    afd = getActivity().getAssets().openFd(SOUND_PATH + data.sound_ar_name);
                }
            }
            if (afd != null) {
                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mp.prepare();
                mp.start();
                play_pause.setImageResource(R.drawable.ic_pause);
            } else {
                getToast(R.string.audio_messing).show();
            }

            **/
            mp.reset();
            AssetFileDescriptor afd = null;
            if (data.langchange) {
                afd = getActivity().getAssets().openFd(SOUND_PATH + data.sound_en_name);
            } else {
                if (data.speedChange) {
                    afd = getActivity().getAssets().openFd(SOUND_PATH + data.sound_ar_fast);
                } else {
                    afd = getActivity().getAssets().openFd(SOUND_PATH + data.sound_ar_echo);
                }
            }
            if (afd != null) {
                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mp.prepare();
                mp.start();
                play_pause.setImageResource(R.drawable.ic_pause);
            } else {
                getToast(R.string.audio_messing).show();
            }

            recyclerView.scrollToPosition(currentSoundId);
            verticalViewPager.setCurrentItem(currentSoundId);
            onPageSelected(currentSoundId);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            if (!mathuratData.refresh_counter) {
                mathuratData.counter = mathuratData.counter - 1;
                donut_progress.setProgress(mathuratData.counter);
                mathuratDataList.set(currentSoundId, mathuratData);
                mathuratData = StringUtils.calcDayCounter(currDay, mathuratData);
                if (mathuratData.counter == 0) {
                    mathuratData.refresh_counter = true;
                    donut_progress.setShowText(false);
                    refresh_progress.setVisibility(View.VISIBLE);
                }
            } else {
                mathuratData.refresh_counter = false;
                mathuratData.counter = mathuratData.max_counter;
                donut_progress.setShowText(true);
                refresh_progress.setVisibility(View.GONE);
                mathuratDataList.set(currentSoundId, mathuratData);
                donut_progress.setProgress(mathuratData.counter);
            }
            generalStickyListAdapter.notifyItemChanged(currentSoundId);
            StringUtils.replaceData(getContext(), mathuratDataList, mathuratData);
            if (isRepeat) {
                playAudio(mathuratData);
            } else {
                if (currentSoundId < (mathuratDataList.size() - 1)) {
                    currentSoundId += 1;
                    playAudio(mathuratDataList.get(currentSoundId));
                    recyclerView.scrollToPosition(currentSoundId);
                    layoutManager.scrollToPositionWithOffset(currentSoundId, 15);
                    verticalViewPager.setCurrentItem(currentSoundId, true);
                    onPageSelected(currentSoundId);
                } else {
                    currentSoundId = 0;
                    mp.reset();
                    play_pause.setImageResource(R.drawable.ic_play);
                }
            }
        }
    };


    View.OnClickListener playerFunction = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (getCurrentFragmentManager().getBackStackEntryCount() > 0) {
                getCurrentFragmentManager().popBackStack();
            }
            switch (view.getId()) {
                case R.id.play_pause: {
                    if (mp.isPlaying()) {
                        if (mp != null) {
                            mp.pause();
                            play_pause.setImageResource(R.drawable.ic_play);
                        }
                    } else {
                        if (mp != null) {
                            if (currentSoundId == 0) {
                                playAudio(mathuratDataList.get(currentSoundId));
                            } else {
                                mp.start();
                            }
                            play_pause.setImageResource(R.drawable.ic_pause);
                        }
                    }
                    break;
                }
                case R.id.lang: {
                    mathuratData = mathuratDataList.get(currentSoundId);
                    changeTextAudioThakerLang(mathuratData);
                    break;
                }
                case R.id.anchor: {
                    anchor.setChecked(!anchor.isChecked());
                    isAnchor = anchor.isChecked();
                    mathuratData = mathuratDataList.get(currentSoundId);
                    if (isAnchor) {
                        mathuratData.isAnchor = true;
                        mathuratListTmp.clear();
                        for (MathuratData mathurat : mathuratDataList) {
                            mathurat.isAnchor = true;
                            mathuratListTmp.add(mathurat);
                        }
                        mathuratDataList.clear();
                        mathuratDataList.addAll(mathuratListTmp);
                    } else {
                        mathuratData.isAnchor = false;
                        mathuratListTmp.clear();
                        for (MathuratData mathurat : mathuratDataList) {
                            mathurat.isAnchor = false;
                            mathuratListTmp.add(mathurat);
                        }
                        mathuratDataList.clear();
                        mathuratDataList.addAll(mathuratListTmp);
                    }
                    mathuratDataList.set(currentSoundId, mathuratData);
                    playAudio(mathuratData);
                    break;
                }
                case R.id.speed: {
                    speed.setChecked(!speed.isChecked());
                    speedChange = speed.isChecked();
                    mathuratData = mathuratDataList.get(currentSoundId);
                    if (speedChange) {
                        mathuratData.speedChange = true;
                        mathuratListTmp.clear();
                        for (MathuratData mathurat : mathuratDataList) {
                            mathurat.speedChange = true;
                            mathuratListTmp.add(mathurat);
                        }
                        mathuratDataList.clear();
                        mathuratDataList.addAll(mathuratListTmp);
                    } else {
                        mathuratData.speedChange = false;
                        mathuratListTmp.clear();
                        for (MathuratData mathurat : mathuratDataList) {
                            mathurat.speedChange = false;
                            mathuratListTmp.add(mathurat);
                        }
                        mathuratDataList.clear();
                        mathuratDataList.addAll(mathuratListTmp);
                    }
                    mathuratDataList.set(currentSoundId, mathuratData);
                    playAudio(mathuratData);
                    break;
                }
                case R.id.repeat: {
                    repeat.setChecked(!repeat.isChecked());
                    isRepeat = repeat.isChecked();
                    break;
                }
                case R.id.next: {
                    if (currentSoundId < (mathuratDataList.size() - 1)) {
                        currentSoundId += 1;
                    } else {
                        currentSoundId = 0;
                    }
                    verticalViewPager.setCurrentItem(currentSoundId);
                    layoutManager.scrollToPositionWithOffset(currentSoundId, 15);
                    mathuratData = mathuratDataList.get(currentSoundId);
                    if (mathuratData != null) {
                        playAudio(mathuratData);
                    }
                    break;
                }
                case R.id.previous: {
                    if (currentSoundId > 0) {
                        currentSoundId -= 1;
                    } else {
                        currentSoundId = mathuratDataList.size() - 1;
                    }
                    verticalViewPager.setCurrentItem(currentSoundId);
                    layoutManager.scrollToPositionWithOffset(currentSoundId, 15);
                    mathuratData = mathuratDataList.get(currentSoundId);
                    if(mathuratData != null){
                        playAudio(mathuratData);
                    }
                    break;
                }
            }
        }
    };

    @Override
    public void onResume() {
        if (mp != null) {
            if (mp.isPlaying()) {
                play_pause.setImageResource(R.drawable.ic_pause);
            } else {
                play_pause.setImageResource(R.drawable.ic_play);
            }
        }
        super.onResume();
        TrackApps.getInstance().trackScreenView(AthkarFragment.class.getSimpleName());
    }

    @Override
    public void onDestroy() {
        mp.release();
        super.onDestroy();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
