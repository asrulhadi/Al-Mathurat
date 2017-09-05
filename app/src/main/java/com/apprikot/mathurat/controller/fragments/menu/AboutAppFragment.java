package com.apprikot.mathurat.controller.fragments.menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.application.TrackApps;
import com.apprikot.mathurat.controller.enums.ToolbarItem;
import com.apprikot.mathurat.controller.fragments.base.BaseFragment;
import com.apprikot.mathurat.view.custom.CustomTextView;


public class AboutAppFragment extends BaseFragment {



    private WebView webview;
    private CustomTextView powered_by;

    public AboutAppFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_app, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar();
        setUpView();

    }

    private void setUpToolbar() {
        setToolbarItems(ToolbarItem.BACK, ToolbarItem.NONE, ToolbarItem.NONE, ToolbarItem.NONE);
        setToolbarCenter(ToolbarItem.TEXT, getString(R.string.about_app));
    }


    private void setUpView() {
        powered_by = (CustomTextView) root.findViewById(R.id.powered_by);
        attachClickListener(function, powered_by);
        webview = (WebView) root.findViewById(R.id.webview);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        String url = getString(R.string.about_us_url);
        webview.loadUrl(url);
    }


    @Override
    public void onToolbarItemClicked(ToolbarItem toolbarItem, ImageView checkableImageView) {
        super.onToolbarItemClicked(toolbarItem, checkableImageView);
        switch (toolbarItem) {
            case BACK: {
                getActivity().onBackPressed();
                break;
            }
        }
    }

    View.OnClickListener function = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            vibrator.vibrate(Constants.VIBRATE_VALUE);
            switch (view.getId()){
                case R.id.powered_by:{
                    String url = getString(R.string.apprikot_url);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                    break;
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        TrackApps.getInstance().trackScreenView(AboutAppFragment.class.getSimpleName());
    }
}
