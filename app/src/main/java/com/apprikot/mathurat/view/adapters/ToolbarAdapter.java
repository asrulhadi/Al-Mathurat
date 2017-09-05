package com.apprikot.mathurat.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.components.Constants;
import com.apprikot.mathurat.controller.enums.ToolbarItem;
import com.apprikot.mathurat.controller.interfaces.OnToolbarItemClickListener;
import com.apprikot.mathurat.controller.utils.DimenUtils;
import com.apprikot.mathurat.view.custom.CheckableImageView;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ToolbarAdapter {
    public static final int ICONS_COUNT = 4;

    private Context mContext;
    private LinearLayout mToolbar;
    private List<ToolbarItem> toolbarItems;
    private List<CheckableImageView> checkableImageViews;
    private OnToolbarItemClickListener mClickListener;
    private ImageView centerImageView;
    private ImageView titleIconImageView;
    private CircleImageView circularImageView;
    private TextView titleTextView;
    private Map<Integer, ToolbarItem> idToolbarItemMap;

    public ToolbarAdapter(Activity activity, LinearLayout toolbar) {
        if (activity instanceof AppCompatActivity && toolbar != null) {
//            ((AppCompatActivity) activity).setSupportActionBar(toolbar);
            idToolbarItemMap = new HashMap<>();
            mContext = activity;
            mToolbar = toolbar;
        }
    }

    public ToolbarAdapter withClickListener(OnToolbarItemClickListener clickListener) {
        mClickListener = clickListener;
        return this;
    }

    public ToolbarAdapter withLayout(int layoutResId) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        LinearLayout toolbarLayout = (LinearLayout) inflater.inflate(layoutResId, mToolbar, false);
        mToolbar.removeAllViews();
        mToolbar.addView(toolbarLayout);

        titleIconImageView = (ImageView) toolbarLayout.findViewById(R.id.img_title_icon);
        centerImageView = (ImageView) toolbarLayout.findViewById(R.id.img_center);
        centerImageView.setOnClickListener(onTabItemClicked);
        idToolbarItemMap.put(centerImageView.getId(), ToolbarItem.IMAGE);

        circularImageView = (CircleImageView) toolbarLayout.findViewById(R.id.img_circular);
        circularImageView.setOnClickListener(onTabItemClicked);
        idToolbarItemMap.put(circularImageView.getId(), ToolbarItem.IMAGE);

        titleTextView = (TextView) toolbarLayout.findViewById(R.id.txt_title);
        return this;
    }

    public void initItems() {
        if (!(mToolbar.getChildAt(0) instanceof LinearLayout)) {
            return;
        }
        LinearLayout toolbarLayout = (LinearLayout) mToolbar.getChildAt(0);
        checkableImageViews = new ArrayList<>();
        for (int i = 0; i < toolbarLayout.getChildCount(); i++) {
            if (toolbarLayout.getChildAt(i) instanceof CheckableImageView) {
                checkableImageViews.add((CheckableImageView) toolbarLayout.getChildAt(i));
            }
        }
    }

    public ToolbarAdapter withItems(ToolbarItem... items) {
        if (items.length < ICONS_COUNT || mToolbar.getChildCount() == 0 ||
                !(mToolbar.getChildAt(0) instanceof LinearLayout)) {
            return this;
        }
        LinearLayout toolbarLayout = (LinearLayout) mToolbar.getChildAt(0);
        toolbarItems = new ArrayList<>();
        checkableImageViews = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < toolbarLayout.getChildCount() && index < ICONS_COUNT; i++) {
            if (toolbarLayout.getChildAt(i) instanceof CheckableImageView) {
                CheckableImageView checkableImageView = (CheckableImageView) toolbarLayout.getChildAt(i);
                ToolbarItem toolbarItem = items[index++];
                checkableImageView.setTag(toolbarItem);
                if (toolbarItem == ToolbarItem.NONE) {
                    checkableImageView.setVisibility(View.INVISIBLE);
                } else {
                    checkableImageView.setOnClickListener(onTabItemClicked);
                    checkableImageView.setVisibility(View.VISIBLE);
                }
                checkableImageView.setImageResource(toolbarItem.icon);
                toolbarItems.add(toolbarItem);
                checkableImageViews.add(checkableImageView);
                idToolbarItemMap.put(checkableImageView.getId(), toolbarItem);
            }
        }
        expandCenter();
        return this;
    }

    private void expandCenter() {
        if (toolbarItems.size() < ICONS_COUNT || checkableImageViews.size() < ICONS_COUNT) {
            return;
        }
        if (toolbarItems.get(1) == ToolbarItem.NONE && toolbarItems.get(2) == ToolbarItem.NONE) {
            checkableImageViews.get(1).setVisibility(View.GONE);
            checkableImageViews.get(2).setVisibility(View.GONE);
        }
        if (toolbarItems.get(0) == ToolbarItem.NONE && toolbarItems.get(3) == ToolbarItem.NONE) {
            checkableImageViews.get(0).setVisibility(View.GONE);
            checkableImageViews.get(3).setVisibility(View.GONE);
        }
    }

    private View.OnClickListener onTabItemClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onToolbarItemClicked(idToolbarItemMap.get(view.getId()), (ImageView) view);
            }
        }
    };

    public void clearCenter() {
        titleIconImageView.setVisibility(View.GONE);
        centerImageView.setVisibility(View.GONE);
        circularImageView.setVisibility(View.GONE);
        titleTextView.setVisibility(View.GONE);
    }

    public void setToolbarCenter(ToolbarItem toolbarItem, String extra) {
        clearCenter();
        switch (toolbarItem) {
            case TEXT: {
                setTitle(extra);
                break;
            }
            case IMAGE: {
                setImage(extra);
                break;
            }
        }
    }

    public void setToolbarCenter(ToolbarItem toolbarItem, int extraResId) {
        clearCenter();
        switch (toolbarItem) {
            case TEXT: {
                setTitle(extraResId);
                break;
            }
            case IMAGE: {
                setImage(extraResId);
                break;
            }
        }
    }

    public void setToolbarCenter(int titleResId, int iconResId) {
        clearCenter();
        setTitle(titleResId);
        setTitleIcon(iconResId);
    }

    public void setTitle(String title) {
        if (titleTextView != null) {
            titleTextView.setText(title);
            titleTextView.setVisibility(View.VISIBLE);
        }
    }

    public void setTitle(int titleResId) {
        if (titleTextView != null) {
            titleTextView.setText(titleResId);
            int textSize = DimenUtils.pixelsToDp(mContext, R.dimen.font_large);
            titleTextView.setTextSize(textSize);
            titleTextView.setVisibility(View.VISIBLE);
        }
    }

    public void setImage(String imageUrl) {
        if (centerImageView != null) {
            Glide.with(mContext).load(imageUrl).into(circularImageView);
            circularImageView.setVisibility(View.VISIBLE);
        }
    }

    public void setTitleIcon(int titleIconResId) {
        if (titleIconImageView != null) {
            titleIconImageView.setImageResource(titleIconResId);
            titleIconImageView.setVisibility(View.VISIBLE);
        }
    }

    public void setImage(int imageResId) {
        if (centerImageView != null) {
            centerImageView.setImageResource(imageResId);
            centerImageView.setVisibility(View.VISIBLE);
        }
    }

    public View getItemView(ToolbarItem toolbarItem) {
        int itemIndex = toolbarItems.indexOf(toolbarItem);
        if (itemIndex != -1) {
            return checkableImageViews.get(itemIndex);
        }
        return null;
    }

    public View getItemView(int itemIndex) {
        return checkableImageViews.get(itemIndex);
    }

    public void setItemChecked(ToolbarItem toolbarItem, boolean isChecked, boolean animate) {
        int itemIndex = toolbarItems.indexOf(toolbarItem);
        if (itemIndex != -1) {
            checkableImageViews.get(itemIndex).setChecked(isChecked);
            if (animate && isChecked) {
                YoYo.with(Techniques.Pulse).duration(Constants.ANIM_DURATION_PULSE)
                        .playOn(checkableImageViews.get(itemIndex));
            }
        }
    }

    public void setIcon(ToolbarItem toolbarItem, int iconResId) {
        int itemIndex = toolbarItems.indexOf(toolbarItem);
        if (itemIndex != -1) {
            checkableImageViews.get(itemIndex).setImageResource(iconResId);
        }
    }

    public boolean isItemChecked(ToolbarItem toolbarItem) {
        int itemIndex = toolbarItems.indexOf(toolbarItem);
        return itemIndex >= 0 && checkableImageViews.get(itemIndex).isChecked();
    }
}