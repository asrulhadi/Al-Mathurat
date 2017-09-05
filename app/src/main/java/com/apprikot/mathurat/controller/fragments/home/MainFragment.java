package com.apprikot.mathurat.controller.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.fragments.base.BaseFragment;

import java.util.List;

public class MainFragment extends BaseFragment {
    public static final String EXTRA_FRAGMENT_CLASS_NAME = "FRAGMENT_CLASS_NAME";

    public String fragmentClassName;

    public MainFragment() {
    }

    public static MainFragment newInstance(String fragmentClassName) {
        try {
            MainFragment fragment = new MainFragment();
            Bundle data = new Bundle();
            data.putString(EXTRA_FRAGMENT_CLASS_NAME, fragmentClassName);
            fragment.setArguments(data);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MainFragment newInstance(String fragmentClassName, Parcelable parcelable) {
        try {
            MainFragment fragment = new MainFragment();
            Bundle data = new Bundle();
            data.putString(EXTRA_FRAGMENT_CLASS_NAME, fragmentClassName);
            data.putParcelable(EXTRA_PARCELABLE, parcelable);
            fragment.setArguments(data);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static MainFragment newInstance(String fragmentClassName, Parcelable parcelable, List<Parcelable> list) {
//        try {
//            MainFragment fragment = new MainFragment();
//            Bundle data = new Bundle();
//            data.putString(EXTRA_FRAGMENT_CLASS_NAME, fragmentClassName);
//            data.putParcelable(EXTRA_PARCELABLE, parcelable);
//            data.putParcelableArrayList(EXTRA_LIST_PARCELABLE, (ArrayList<? extends Parcelable>) list);
//            fragment.setArguments(data);
//            return fragment;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            fragmentClassName = getArguments().getString(EXTRA_FRAGMENT_CLASS_NAME);
            Parcelable parcelable = getArguments().getParcelable(EXTRA_PARCELABLE);
            Bundle args = new Bundle();
            args.putParcelable(EXTRA_PARCELABLE, parcelable);
            Fragment fragment = Fragment.instantiate(getActivity(), fragmentClassName, args);
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment, null);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager childFragmentManager = getChildFragmentManager();
        if (childFragmentManager != null) {
            List<Fragment> nestedFragments = childFragmentManager.getFragments();
            if (nestedFragments == null || nestedFragments.size() == 0) return;
            for (Fragment childFragment : nestedFragments) {
                if (childFragment != null && !childFragment.isDetached() && !childFragment.isRemoving()) {
                    childFragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }
}