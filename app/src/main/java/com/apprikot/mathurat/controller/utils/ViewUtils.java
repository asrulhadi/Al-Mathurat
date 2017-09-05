package com.apprikot.mathurat.controller.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.apprikot.mathurat.R;

public class ViewUtils {
    public static void hideKeyboard(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (editText == null) {
                continue;
            }

            editText.clearFocus();
            InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(EditText editText) {
        if (editText == null) {
            return;
        }
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInputFromWindow(editText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
    }

    public static void setConditioned(TextView textView, String text, boolean isValid) {
        if (isValid) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    public static void setWithDefault(TextView textView, String text, String defaultText) {
        boolean isValid = text != null && !text.trim().isEmpty();
        textView.setText(isValid ? text : defaultText);
    }

    public static int getFullImageWidth(Context context, boolean hasMargins) {
        int screenWidth = DimenUtils.getScreenSize(context).x;
        int dimenRedId = hasMargins ? R.dimen.margin8 : R.dimen.margin4;
        int margins = (int) (context.getResources().getDimension(dimenRedId) * 2);
        return screenWidth - margins;
    }

    /***
     * Colorizing Result
     ***/

    private static final int RED = 0, GREEN = 1, BLUE = 2, GRAY = 3;
    public static int[] colors;

    /***
     * New-Instance
     ***/

    public static final String EXTRA_PARCELABLE = "PARCELABLE";
    public static final String EXTRA_LONG = "INTEGER";
    public static final String EXTRA_LIST_PARCELABLE = "LIST_PARCELABLE";
    public static final String EXTRA_AD = "AD";

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

//    public static Fragment newInstance(Class fragmentClass, Parcelable parcelable, AdRequestEntity adRequestEntity) {
//        try {
//            Fragment fragment = (Fragment) fragmentClass.newInstance();
//            Bundle data = new Bundle();
//            data.putParcelable(EXTRA_PARCELABLE, parcelable);
//            if (adRequestEntity != null) {
//                data.putSerializable(EXTRA_AD, adRequestEntity);
//            }
//            fragment.setArguments(data);
//            return fragment;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new Fragment();
//    }

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

    /***
     * Tool-Bar
     ***/
    public static int toolbarHeight = -1;

    public static int getToolbarHeight(Activity activity) {
        if (activity == null) return -1;
        if (toolbarHeight == -1) {
            TypedValue tv = new TypedValue();
            if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                toolbarHeight = TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
            }
        }
        return toolbarHeight;
    }
}
