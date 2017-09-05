package com.apprikot.mathurat.controller.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.enums.ToolbarItem;
import com.apprikot.mathurat.controller.interfaces.FragmentStackManager;
import com.apprikot.mathurat.controller.interfaces.OnToolbarItemClickListener;
import com.apprikot.mathurat.controller.interfaces.SideMenuManager;
import com.apprikot.mathurat.controller.utils.PrefHelp;
import com.apprikot.mathurat.view.adapters.ToolbarAdapter;
import com.apprikot.mathurat.view.custom.LoadingUtils;
import com.apprikot.mathurat.view.dialogs.LoadingDialog;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity implements  OnToolbarItemClickListener {


    public static final String TAG = BaseActivity.class.getSimpleName();
    public static final String EXTRA_PARCELABLE = "parcelable";
    public static final String PARCELABLE_LIST = "parcelable_list";
    public static final String EXTRA_LONG = "integer";

    public static boolean sDown;
    public LoadingDialog progressDialog;
    public LoadingUtils loadingUtils;
    public Toolbar toolbar;
    public ToolbarAdapter toolbarAdapter;
    public Toast toast;
    protected boolean isEnglish;
    public Vibrator vibrator;


    public static Fragment newInstance(Class fragmentClass, Parcelable parcelable) {
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            Bundle data = new Bundle();
            data.putParcelable(EXTRA_PARCELABLE, parcelable);
            fragment.setArguments(data);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Fragment();
    }

    public static Fragment newInstance(Class fragmentClass, long extraLong) {
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            Bundle data = new Bundle();
            data.putLong(EXTRA_LONG, extraLong);
            fragment.setArguments(data);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Fragment();
    }

    public static Fragment newInstance(Class fragmentClass, Parcelable parcelable, long extraLong) {
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            Bundle data = new Bundle();
            data.putParcelable(EXTRA_PARCELABLE, parcelable);
            data.putLong(EXTRA_LONG, extraLong);
            fragment.setArguments(data);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Fragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(PrefHelp.getLang(this));
        resources.updateConfiguration(configuration, displayMetrics);
        vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        grantPermission();
    }

    private void grantPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
            }
        }
    }


    protected void moveToMainFragment(Fragment fragment, int containerId, boolean addToBackStack) {
        FragmentStackManager fragmentStackManager = (FragmentStackManager) this;
        if (fragmentStackManager.getCurrentMainFragment() == null) {
            Log.e(TAG, "currentMainFragment is null! this shouldn't happen");
            return;
        }
        FragmentTransaction fragmentTransaction = fragmentStackManager.getCurrentMainFragment()
                .getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerId, fragment, null);
        if (addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        lockSideMenus();
    }



    protected void lockSideMenus() {
        if (this instanceof SideMenuManager) {
            SideMenuManager sideMenuManager = (SideMenuManager) this;
            sideMenuManager.lockSideMenus();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            sDown = false;
            Log.i("Touch", "Up");
        } else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            sDown = true;
            Log.i("Touch", "Down");
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void onToolbarItemClicked(ToolbarItem toolbarItem, ImageView checkableImageView) {
    }

    // Toolbar-Stuff

    protected void initToolBar(LinearLayout toolbar) {
        if (toolbar != null) {
            toolbarAdapter = new ToolbarAdapter(this, toolbar)
                    .withClickListener(this)
                    .withLayout(R.layout.toolbar_basic);
        }
    }

    protected void setToolbarItems(ToolbarItem... items) {
        if (toolbarAdapter != null) {
            toolbarAdapter.withItems(items);
        }
    }

    protected void setToolbarCenter(ToolbarItem toolbarItem, String extra) {
        if (toolbarAdapter != null) {
            toolbarAdapter.clearCenter();
            switch (toolbarItem) {
                case TEXT: {
                    toolbarAdapter.setTitle(extra);
                    break;
                }
                case IMAGE: {
                    toolbarAdapter.setImage(extra);
                    break;
                }
            }
        }
    }

    protected void setToolbarCenter(ToolbarItem toolbarItem, int extraResId) {
        if (toolbarAdapter != null) {
            toolbarAdapter.clearCenter();
            switch (toolbarItem) {
                case TEXT: {
                    toolbarAdapter.setTitle(extraResId);
                    break;
                }
                case IMAGE: {
                    toolbarAdapter.setImage(extraResId);
                    break;
                }
            }
        }
    }

    public void attachClickListener(View.OnClickListener onClick, View... views) {
        for (View view : views) {
            view.setOnClickListener(onClick);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}