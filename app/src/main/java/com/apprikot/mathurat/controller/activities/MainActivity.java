package com.apprikot.mathurat.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.VideoView;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.components.Constants;
import com.apprikot.mathurat.controller.fragments.Athkar.AthkarFragment;
import com.apprikot.mathurat.controller.fragments.home.MainFragment;
import com.apprikot.mathurat.controller.fragments.menu.AboutAppFragment;
import com.apprikot.mathurat.controller.fragments.menu.EvaluationFragment;
import com.apprikot.mathurat.controller.interfaces.FragmentStackManager;
import com.apprikot.mathurat.controller.interfaces.Listable;
import com.apprikot.mathurat.controller.interfaces.SideMenuManager;
import com.apprikot.mathurat.controller.listeners.OnItemClickListener;
import com.apprikot.mathurat.controller.receiver.NotifyReceiver;
import com.apprikot.mathurat.controller.utils.AppUtils;
import com.apprikot.mathurat.controller.utils.PrefHelp;
import com.apprikot.mathurat.model.MathuratData;
import com.apprikot.mathurat.model.MemoryCache;
import com.apprikot.mathurat.model.MenuLeftDataAdapter;
import com.apprikot.mathurat.model.Notify;
import com.apprikot.mathurat.model.TasbehData;
import com.apprikot.mathurat.model.menu.LeftMenuMainItem;
import com.apprikot.mathurat.view.adapters.BlankAdapter;
import com.apprikot.mathurat.view.adapters.GeneralStickyListAdapter;
import com.apprikot.mathurat.view.utils.SimpleDividerItemDecoration;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity implements OnItemClickListener.OnItemClickCallback,
        FragmentStackManager,
        SideMenuManager {

    public static final String TAG = com.apprikot.mathurat.controller.activities.MainActivity.class.getSimpleName();
    public static final String DATA = "thaker_data";

    private Context mContext = this;
    private DrawerLayout drawerLayout;
    private RecyclerView leftRecyclerView;
    private MenuLeftDataAdapter menuLeftDataAdapter;
    private GeneralStickyListAdapter mMenuLeftListAdapter;
    private MainFragment mCurrentMainFragment;
    private NavigationView leftNavigationView;
    private VideoView videoView;
    private List<MathuratData> dataList;
    private List<MathuratData> dataListTmp = new ArrayList<>();
    private List<TasbehData> tasbehList;
    private Notify notify;
    private Configuration newConfig;
//    private MathuratData notifyData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources resources = mContext.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(PrefHelp.getLang(mContext));
        resources.updateConfiguration(configuration, displayMetrics);
        setContentView(R.layout.activity_main);
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            notifyData = extras.getParcelable(DATA);
//        }
        newConfig = new Configuration();
        setDataPref();
        runBackGround();
        initRecyclerViews();
        initDrawer();
        initSideMenus();
        NotifyReceiver.setAlarms(mContext);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            mCurrentMainFragment = MainFragment.newInstance(HomeFragment.class.getName());
            mCurrentMainFragment = MainFragment.newInstance(AthkarFragment.class.getName());
            fragmentTransaction.replace(R.id.container, mCurrentMainFragment);
            fragmentTransaction.commit();
        }
    }

    private void setDataPref() {
//        PrefHelp.setIsAudioLangChange(mContext, false);
        if(PrefHelp.getData(mContext) == null){
            dataList = MemoryCache.DataCache.getMathuratData(mContext);
            PrefHelp.setData(mContext, dataList);
        }else{
            dataList = PrefHelp.getData(mContext);
            for (MathuratData mathurat : dataList) {
                mathurat.langchange = false;
                mathurat.speedChange = false;
                mathurat.isAnchor = true;
                dataListTmp.add(mathurat);
            }
            dataList.clear();
            dataList.addAll(dataListTmp);
            PrefHelp.setData(mContext, dataList);
        }
        if(PrefHelp.getTasbeh(mContext) == null){
            tasbehList = MemoryCache.TasabehCache.getTasbehData(mContext);
            PrefHelp.setTasbeh(mContext, tasbehList);
        }
        if(PrefHelp.getAllNotify(mContext) == null){
            notify = MemoryCache.NotifyCache.getInitNotify(mContext);
            PrefHelp.setAllNotify(mContext, notify);
        }
        setDaysCounter(PrefHelp.getData(mContext));
    }

    private void runBackGround() {
        videoView = (VideoView) findViewById(R.id.video_view_play);
        AppUtils.playVideo(mContext, videoView, R.raw.android_bg_mathurat);
    }

    public void initRecyclerViews() {
        leftRecyclerView = (RecyclerView) findViewById(R.id.rv_left);
        leftRecyclerView.setAdapter(new BlankAdapter());
        leftRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(ContextCompat.getDrawable(mContext, R.drawable.rv_menu_line_divider)));
        leftRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void initDrawer() {
        leftNavigationView = (NavigationView) findViewById(R.id.navigation_left);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (drawerView.getId() == leftRecyclerView.getId()) {
                    leftRecyclerView.scrollToPosition(0);
                }
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    private void initSideMenus() {
        menuLeftDataAdapter = new MenuLeftDataAdapter(this);
        prepareLeftSideMenu();
    }

    private void prepareLeftSideMenu() {
        menuLeftDataAdapter.load();
        if (mMenuLeftListAdapter == null) {
            mMenuLeftListAdapter = new GeneralStickyListAdapter(mContext, menuLeftDataAdapter.menuMainItems, this);
            leftRecyclerView.setAdapter(mMenuLeftListAdapter);
        } else {
            mMenuLeftListAdapter.clear();
            mMenuLeftListAdapter.addAll(menuLeftDataAdapter.menuMainItems);
            mMenuLeftListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        vibrator.vibrate(Constants.VIBRATE_VALUE);
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        if (mCurrentMainFragment == null) {
            super.onBackPressed();
            Log.e(TAG, "tab fragment is null! this shouldn't happen");
            return;
        }

        if (mCurrentMainFragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
            mCurrentMainFragment.getChildFragmentManager().popBackStack();
            int countInStack = mCurrentMainFragment.getChildFragmentManager().getBackStackEntryCount();
            if (countInStack == 1) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, leftNavigationView);
            }
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void lockSideMenus() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, leftNavigationView);
    }

    @Override
    public void openSideMenu(int gravity) {
        if (!drawerLayout.isDrawerOpen(gravity)) {
            drawerLayout.openDrawer(gravity);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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
//        videoView.stopPlayback();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        NotifyReceiver.setAlarms(mContext);
//        updateResourcesLegacy();
        AppUtils.playVideo(mContext, videoView, R.raw.android_bg_mathurat);

    }


//    private void updateResourcesLegacy() {
//        Resources resources = mContext.getResources();
//        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
//        Configuration configuration = resources.getConfiguration();
//        configuration.locale = new Locale(PrefHelp.getLang(mContext));
//        resources.updateConfiguration(configuration, displayMetrics);
//        setContentView(R.layout.activity_main);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClicked(View view, Listable listableItem, int position) {
        vibrator.vibrate(Constants.VIBRATE_VALUE);
        if (listableItem instanceof LeftMenuMainItem) {
            LeftMenuMainItem leftMenuMainItem = (LeftMenuMainItem) listableItem;
            switch (leftMenuMainItem.menuMainItem) {
                case EVALUATION: {
                    moveToMainFragment(new EvaluationFragment(), R.id.container, true);
                    break;
                }
                case SETTING: {
                    Intent intent = new Intent(this, SettingActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                case SHARE_APP: {
                    try {
                        AppUtils.shareUrl(mContext, getString(R.string.app_name), Constants.SHARE_URL);
//                        Intent i = new Intent(Intent.ACTION_SEND);
//                        i.setType("text/plain");
//                        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
//                        String sAux = "https://play.google.com/store/apps/details?id="+ getApplicationContext().getPackageName();
//                        i.putExtra(Intent.EXTRA_TEXT, sAux);
//                        startActivity(Intent.createChooser(i, "choose one"));
                    } catch(Exception e) {
                        //e.toString();
                    }
                    break;
                }
                case ABOUT_APP: {
                    moveToMainFragment(new AboutAppFragment(), R.id.container, true);
                    break;
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START);
        }
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
        PrefHelp.setData(mContext, mathuratDatas);
    }



    @Override
    public void setCurrentTabFragment(Fragment currentTabFragment) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Fragment getCurrentTabFragment() {
        return null;
    }

    @Override
    public Fragment getCurrentMainFragment() {
        return mCurrentMainFragment;
    }

}