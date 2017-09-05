package com.apprikot.mathurat.controller.fragments.Athkar;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.application.TrackApps;
import com.apprikot.mathurat.controller.fragments.base.BaseFragment;
import com.apprikot.mathurat.controller.interfaces.OnItemSwipeListener;
import com.apprikot.mathurat.controller.interfaces.ScrollViewListener;
import com.apprikot.mathurat.controller.listeners.OnSwipeTouchListener;
import com.apprikot.mathurat.model.MathuratData;
import com.apprikot.mathurat.view.custom.CustomTextViewContent;
import com.apprikot.mathurat.view.custom.ResponsiveScrollView;

public class AthkarPagerItemFragment extends BaseFragment implements ScrollViewListener{

    public static final String TAG = AthkarPagerItemFragment.class.getSimpleName();
    public static final String EXTRA_THAKER = "thaker";
    private MathuratData mathuratData;
    private CustomTextViewContent thakerTxt;
    private ResponsiveScrollView sv;
    public OnItemSwipeListener onItemSwipeListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mathuratData = getArguments().getParcelable(EXTRA_THAKER);
            onItemSwipeListener = getArguments().getParcelable(PARCELABLE_INTERFACE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_athkar_pager_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
    }


    public static AthkarPagerItemFragment newInstance(MathuratData mathuratData, OnItemSwipeListener onFragmentSwipeListener) {
        AthkarPagerItemFragment athkarPagerItemFragment = new AthkarPagerItemFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_THAKER, mathuratData);
        bundle.putParcelable(PARCELABLE_INTERFACE, onFragmentSwipeListener);
        athkarPagerItemFragment.setArguments(bundle);
        return athkarPagerItemFragment;
    }

    private void setupView() {
        sv = (ResponsiveScrollView) root.findViewById(R.id.sv);
        sv.setOnScrollListener(this, sv);
        thakerTxt = (CustomTextViewContent) root.findViewById(R.id.thaker_txt);
        thakerTxt.setText(Html.fromHtml(mathuratData.isArabic_txt ? mathuratData.text_ar : mathuratData.text_en));
        thakerTxt.setTag(mathuratData);
        thakerTxt.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {

            @Override
            public void onSwipeTop() {
            }

            @Override
            public void onSwipeRight() {
                onItemSwipeListener.onFragmentSwiped(mathuratData);
            }

            @Override
            public void onSwipeLeft() {
                onItemSwipeListener.onFragmentSwiped(mathuratData);
            }

            @Override
            public void onSwipeBottom() {
            }

            @Override
            public void onClick() {
                onItemSwipeListener.onFragmentTextClick(mathuratData);
            }

            @Override
            public void onLongClick() {
            }

            @Override
            public void onDoubleClick() {
            }

        });
    }



    @Override
    public void onScrollStopped(String move) {
        onItemSwipeListener.onScrolledEnd(mathuratData, move);
    }

    @Override
    public void onResume() {
        super.onResume();
        TrackApps.getInstance().trackScreenView(AthkarPagerItemFragment.class.getSimpleName());
    }
}