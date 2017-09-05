package com.apprikot.mathurat.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.components.Constants;

public class SplashScreenActivity extends BaseActivity {
    public static final String TAG = SplashScreenActivity.class.getSimpleName();


    private Context mContext = this;
    private static long SLEEP_TIME = 1;
    private IntentLauncher launcher;
//    private VideoView videoView;
    private ImageView img_logo, img_logo_anim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        int random = new Random().nextInt(26 - 5) + 5;
//        if (random == 21 || random == 25) {
//            random += 1;
//        }
//        PrefHelp.setRandomNum(mContext, random);
        launcher = new IntentLauncher();
        setupView();
//        runBackGround();
//        animate();

    }

    private void setupView() {
        img_logo_anim = (ImageView) findViewById(R.id.img_logo_anim);
        img_logo = (ImageView) findViewById(R.id.img_logo);
        img_logo_anim.setBackgroundResource(R.drawable.rotate_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) img_logo_anim.getBackground();
        frameAnimation.start();
        launcher.start();

    }

//    private void runBackGround() {
//        videoView = (VideoView) findViewById(R.id.video_splash);
//        AppUtils.playVideo(mContext, videoView, R.raw.splash);
//        launcher.start();
//    }

//    private void animate() {
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo_in);
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                launcher.start();
//                AppUtils.playVideo(mContext, videoView, R.raw.splash);
//                videoView.setOnTouchListener(function);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
//        videoView.startAnimation(animation);
//    }


    private class IntentLauncher extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(SLEEP_TIME * 1000);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            img_logo.setOnClickListener(startApps);
//            videoView.setOnTouchListener(function);
//            Intent intent = new Intent(mContext, SampleDownloaderActivity.class);
//            startActivity(intent);
//            finish();
//            Intent intent = new Intent(mContext, MainActivity.class);
//            startActivity(intent);
//            finish();
        }
    }

    View.OnClickListener startApps = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            vibrator.vibrate(Constants.VIBRATE_VALUE);
            switch (view.getId()){
                case R.id.img_logo:{
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        }
    };

//    View.OnTouchListener function = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            vibrator.vibrate(Constants.VIBRATE_VALUE);
//            if (videoView.isPlaying()) {
//                videoView.stopPlayback();
//                finish();
//                Intent intent = new Intent(mContext, MainActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//
//            }
//            return true;
//        }
//    };


    @Override
    protected void onStop() {
//        videoView.stopPlayback();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        AppUtils.playVideo(mContext, videoView, R.raw.splash);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}