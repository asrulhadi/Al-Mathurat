package com.apprikot.mathurat.controller.fragments.Athkar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.components.Constants;
import com.apprikot.mathurat.controller.fragments.base.BaseFragment;
import com.apprikot.mathurat.model.MathuratData;
import com.apprikot.mathurat.view.custom.CustomTextViewContent;


public class ThakerFragment extends BaseFragment {

    private static final String TAG = ThakerFragment.class.getSimpleName();

    private MathuratData mathuratData;
    private CustomTextViewContent benifet_txt;
    private RelativeLayout rl;

    public ThakerFragment() {
    }

    public static ThakerFragment newInstance(MathuratData obj) {
        ThakerFragment fragment = new ThakerFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_PARCELABLE, obj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mathuratData = getArguments().getParcelable(EXTRA_PARCELABLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thaker, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView();

    }

    private void setUpView() {
        benifet_txt = (CustomTextViewContent) root.findViewById(R.id.benifet_txt);
        String ben_txt = mathuratData.isArabic_txt ? mathuratData.text_ar : mathuratData.text_en;
        benifet_txt.setText(ben_txt);
        rl = (RelativeLayout) root.findViewById(R.id.rl);
        attachClickListener(function, rl, benifet_txt);
    }


    View.OnClickListener function = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            vibrator.vibrate(Constants.VIBRATE_VALUE);
            switch (view.getId()) {
                case R.id.rl: {
                    getActivity().onBackPressed();
                    break;
                }
                case R.id.benifet_txt: {
                    getActivity().onBackPressed();
                    break;
                }
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
