package com.apprikot.mathurat.view.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.view.custom.CustomTextView;


public class CustomDialog extends AlertDialog {
    private Context mContext;
    private View dialogView;
    private View separatorView;
    private View.OnClickListener mOnClickListener;
    private CustomTextView titleTextView;
    private CustomTextView msgTextView;
    private CustomTextView positiveButton;
    private CustomTextView negativeButton;
    private Object mTag;

    public CustomDialog(Context context, View.OnClickListener onClickListener) {
        super(context);
        init(context, onClickListener, null);
    }

    public CustomDialog(Context context, View.OnClickListener onClickListener, Object tag) {
        super(context);
        init(context, onClickListener, tag);
    }

    private void init(Context context, View.OnClickListener onClickListener, Object tag) {
        mContext = context;
        mTag = tag;
        mOnClickListener = onClickListener;
        LayoutInflater inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.dialog_custom, null, false);
        prepareViews(dialogView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setView(dialogView);
        getWindow().getAttributes().windowAnimations = R.style.LoadingDialogAnimation;
        super.onCreate(savedInstanceState);
    }

    private void prepareViews(View view) {
        separatorView = view.findViewById(R.id.view_separator);
        titleTextView = (CustomTextView) view.findViewById(R.id.txt_title);
        msgTextView = (CustomTextView) view.findViewById(R.id.txt_msg);
        positiveButton = (CustomTextView) view.findViewById(R.id.btn_positive);
        positiveButton.setOnClickListener(mOnClickListener);
        positiveButton.setTag(mTag);
        negativeButton = (CustomTextView) view.findViewById(R.id.btn_negative);
        negativeButton.setOnClickListener(mOnClickListener);
        negativeButton.setTag(mTag);
    }

    public void draw(int titleResId,
                     int msgResId,
                     int positiveButtonCaptionResId,
                     int negativeButtonCaptionResId) {
        String msg = mContext.getString(msgResId);
        draw(titleResId, msg, positiveButtonCaptionResId, negativeButtonCaptionResId);
    }

    public void draw(int titleResId,
                     String msg,
                     int positiveButtonCaptionResId,
                     int negativeButtonCaptionResId) {
        titleTextView.setText(titleResId);
        msgTextView.setText(msg);
        positiveButton.setText(positiveButtonCaptionResId);
        if (negativeButtonCaptionResId == 0) {
            negativeButton.setVisibility(View.GONE);
            separatorView.setVisibility(View.GONE);
        } else {
            negativeButton.setVisibility(View.VISIBLE);
            negativeButton.setText(negativeButtonCaptionResId);
            separatorView.setVisibility(View.VISIBLE);
        }
    }
}