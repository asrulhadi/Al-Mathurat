package com.apprikot.mathurat.controller.fragments.tasbeh;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.fragments.base.BaseFragment;
import com.apprikot.mathurat.controller.interfaces.OnItemSwipeListener;
import com.apprikot.mathurat.controller.listeners.OnSwipeTouchListener;
import com.apprikot.mathurat.model.TasbehData;
import com.apprikot.mathurat.view.custom.CustomTextViewContent;

public class TasbehPagerItemFragment extends BaseFragment {

    public static final String TAG = TasbehPagerItemFragment.class.getSimpleName();
    public static final String EXTRA_THAKER = "thaker";
    private TasbehData tasbehData;
    private CustomTextViewContent tasbeh_ar, tasbeh_en;
    private LinearLayout ln_tasbeh;
    public OnItemSwipeListener onItemSwipeListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tasbehData = getArguments().getParcelable(EXTRA_THAKER);
            onItemSwipeListener = getArguments().getParcelable(PARCELABLE_INTERFACE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tasbeh_pager_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
    }


    public static TasbehPagerItemFragment newInstance(TasbehData tasbehData, OnItemSwipeListener onFragmentSwipeListener) {
        TasbehPagerItemFragment tasbehPagerItemFragment = new TasbehPagerItemFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_THAKER, tasbehData);
        bundle.putParcelable(PARCELABLE_INTERFACE, onFragmentSwipeListener);
        tasbehPagerItemFragment.setArguments(bundle);
        return tasbehPagerItemFragment;
    }

    private void setupView() {
        ln_tasbeh = (LinearLayout) root.findViewById(R.id.ln_tasbeh);
        tasbeh_ar = (CustomTextViewContent) root.findViewById(R.id.tasbeh_ar);
        tasbeh_en = (CustomTextViewContent) root.findViewById(R.id.tasbeh_en);
        tasbeh_ar.setText(tasbehData.text_ar);
        tasbeh_en.setText(tasbehData.text_en);
        ln_tasbeh.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {

            @Override
            public void onSwipeTop() {
            }

            @Override
            public void onSwipeRight() {
            }

            @Override
            public void onSwipeLeft() {
            }

            @Override
            public void onSwipeBottom() {
            }

            @Override
            public void onClick() {
                onItemSwipeListener.onFragmentTasbehClick();
            }

            @Override
            public void onLongClick() {
            }

            @Override
            public void onDoubleClick() {
            }

        });
    }
}