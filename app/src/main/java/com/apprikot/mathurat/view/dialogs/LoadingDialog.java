package com.apprikot.mathurat.view.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.view.custom.Loading;

public class LoadingDialog extends AlertDialog implements DialogInterface.OnShowListener {
    private Context mContext;
    private Loading mLoading;

    public LoadingDialog(Context context) {
        super(context, R.style.LoadingDialogTheme);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialog_loading, null, false);
        mLoading = (Loading) dialogView.findViewById(R.id.loading);
        setView(dialogView);
        setCanceledOnTouchOutside(false);
        setOnShowListener(this);
        super.onCreate(savedInstanceState);
        initWindow();
    }

    @Override
    public void onShow(DialogInterface dialog) {
        mLoading.rotate();
    }

    private void initWindow() {
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setDimAmount(0.2f);
        getWindow().getAttributes().windowAnimations = R.style.LoadingDialogAnimation;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        /* TODO: Uncomment is you want below dialog to be clickable
        getWindow().setFlags(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY |
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, PixelFormat.TRANSLUCENT);
        */
    }
}