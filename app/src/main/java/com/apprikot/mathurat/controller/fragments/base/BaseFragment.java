package com.apprikot.mathurat.controller.fragments.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.enums.ToolbarItem;
import com.apprikot.mathurat.controller.interfaces.FragmentStackManager;
import com.apprikot.mathurat.controller.interfaces.OnToolbarItemClickListener;
import com.apprikot.mathurat.controller.interfaces.SideMenuManager;
import com.apprikot.mathurat.view.adapters.ToolbarAdapter;

public class BaseFragment extends Fragment implements OnToolbarItemClickListener,
        DialogInterface.OnCancelListener, DialogInterface.OnDismissListener{

    public static final String EXTRA_PARCELABLE = "parcelable";
    public static final String EXTRA_LONG = "integer";
    public static final String EXTRA_LIST_PARCELABLE = "list_parcelable";
    public static final String PARCELABLE_INTERFACE = "serializable";
    protected View root;
    protected ToolbarAdapter toolbarAdapter;
    protected long extraLong;
    private Toast toast;
    public static boolean isAnimationDisabled;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            extraLong = getArguments().getLong(EXTRA_LONG, -1);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root = view;
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        initToolBar();
    }


    protected void moveToFragment(Fragment fragment, int containerId, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.rotate_fragment_in, R.anim.rotate_fragment_out);
        fragmentTransaction.replace(containerId, fragment, null);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    protected void moveToChildFragment(Fragment fragment, int containerId, boolean addToBackStack) {
        if (getActivity() instanceof FragmentStackManager) {
            FragmentStackManager fragmentStackManager = (FragmentStackManager) getActivity();
            FragmentTransaction fragmentTransaction = fragmentStackManager.getCurrentTabFragment()
                    .getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(containerId, fragment, null);
            if (addToBackStack) fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void moveToMainFragmentWithAnimation(Fragment fragment, int containerId, boolean addToBackStack) {
        if (getActivity() instanceof FragmentStackManager) {
            FragmentStackManager fragmentStackManager = (FragmentStackManager) getActivity();
            FragmentTransaction fragmentTransaction = fragmentStackManager.getCurrentMainFragment().getChildFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.flip_horizental_out, R.anim.flip_horizental_in);
            fragmentTransaction.replace(containerId, fragment, null);
            if (addToBackStack) fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            lockSideMenus();
        }
    }

    public void moveToMainFragment(Fragment fragment, int containerId, boolean addToBackStack) {
        if (getActivity() instanceof FragmentStackManager) {
            FragmentStackManager fragmentStackManager = (FragmentStackManager) getActivity();
            FragmentTransaction fragmentTransaction = fragmentStackManager.getCurrentMainFragment().getChildFragmentManager().beginTransaction();
//            if(fragment instanceof DetailFragment)
//            fragmentTransaction.setCustomAnimations(R.anim.rotate_fragment_in, R.anim.rotate_fragment_out);
            fragmentTransaction.replace(containerId, fragment, null);
            if (addToBackStack) fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            lockSideMenus();
        }
    }

    public void moveToMainFragment(Fragment fragment, int containerId, boolean addToBackStack, String tag) {
        if (getActivity() instanceof FragmentStackManager) {
            FragmentStackManager fragmentStackManager = (FragmentStackManager) getActivity();
            FragmentTransaction fragmentTransaction = fragmentStackManager.getCurrentMainFragment()
                    .getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(containerId, fragment, null);
            if (addToBackStack) fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
            lockSideMenus();
        }
    }

    protected void lockSideMenus() {
        if (getActivity() instanceof SideMenuManager) {
            SideMenuManager sideMenuManager = (SideMenuManager) getActivity();
            sideMenuManager.lockSideMenus();
        }
    }

    protected void openSideMenu(int gravity) {
        if (getActivity() instanceof SideMenuManager) {
            SideMenuManager sideMenuManager = (SideMenuManager) getActivity();
            sideMenuManager.openSideMenu(gravity);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    protected Toast getToast(int textResId) {
        return getToast(getString(textResId));
    }

    @SuppressLint("ShowToast")
    public Toast getToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(getActivity(), R.string.app_name, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        return toast;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (isAnimationDisabled) {
            Animation a = new Animation() {
            };
            a.setDuration(0);
            return a;
        }

        Animation animation = super.onCreateAnimation(transit, enter, nextAnim);

        if (root == null || !root.isHardwareAccelerated()) {
            return animation;
        }

        if (animation == null && nextAnim != 0) {
            animation = AnimationUtils.loadAnimation(getActivity(), nextAnim);
        }

        if (animation != null) {
            root.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    root.setLayerType(View.LAYER_TYPE_NONE, null);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        return animation;
    }


    public void attachClickListener(View.OnClickListener onClick, View... views) {
        for (View view : views) {
            view.setOnClickListener(onClick);
        }
    }

    @Override
    public void onToolbarItemClicked(ToolbarItem toolbarItem, ImageView checkableImageView) {
    }

    // Toolbar-Stuff

    protected void initToolBar() {
        LinearLayout toolbar = (LinearLayout) root.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbarAdapter = new ToolbarAdapter(getActivity(), toolbar)
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


    @Override
    public void onCancel(DialogInterface dialog) {
        Log.i("Dialog", "Canceled");

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.i("Dialog", "Dismissed");
    }


    protected FragmentManager getCurrentFragmentManager() {
        if (getActivity() instanceof FragmentStackManager) {
            FragmentStackManager fragmentStackManager = (FragmentStackManager) getActivity();
            return fragmentStackManager.getCurrentMainFragment().getChildFragmentManager();
        }
        return null;
    }

}