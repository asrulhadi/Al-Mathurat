package com.apprikot.mathurat.view.dialogs;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.view.custom.CustomTextView;


public class CustomPositiveAlertDialog extends AlertDialog {
    private Context mContext;
    private View dialogView;
    private View.OnClickListener mOnClickListener;
    private CustomTextView msgTextView;
    private CustomTextView positiveButton;
    private Object mTag;

    public CustomPositiveAlertDialog(Context context, View.OnClickListener onClickListener) {
        super(context);
        init(context, onClickListener, null);
    }

    public CustomPositiveAlertDialog(Context context, View.OnClickListener onClickListener, Object tag) {
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
        super.onCreate(savedInstanceState);
    }

    private void prepareViews(View view) {
        msgTextView = (CustomTextView) view.findViewById(R.id.txt_msg);
        positiveButton = (CustomTextView) view.findViewById(R.id.btn_positive);
        positiveButton.setOnClickListener(mOnClickListener);
        positiveButton.setTag(mTag);
    }

    public void draw(int msgResId, int positiveButtonCaptionResId) {
        String msg = mContext.getString(msgResId);
        draw(msg, positiveButtonCaptionResId);
    }

    public void draw(String msg, int positiveButtonCaptionResId) {
        msgTextView.setText(msg);
        positiveButton.setText(positiveButtonCaptionResId);
    }
}