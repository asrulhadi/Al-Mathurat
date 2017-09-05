package com.apprikot.mathurat.controller.components;

import com.apprikot.mathurat.BuildConfig;

public class Constants {

    public static final int MATCH_HALF_TIME = 45;
    public static final long VIBRATE_VALUE = 15;
    public static final long VIBRATE_FINSH_VALUE = 30;
    public static final int MATCH_FULL_TIME = MATCH_HALF_TIME * 2;

    public static final int ANIM_DURATION_PULSE = 200;

    public static final String JSON_SOUND_PATH =  "main."+BuildConfig.VERSION_CODE+".com.apprikot.mathurat/"; // "sound/";
    public static final String JSON_SOUND =  "sound/";
    public static final int VERSION_CODE = BuildConfig.VERSION_CODE;
    public static final String SHARE_URL = "https://play.google.com/store/apps/details?id="+BuildConfig.APPLICATION_ID;


    public static final String APP_NAME = "AL-Mathurat";
    public static final int ACTION_BLANK_ADAPTER = 1;
    public static final int ACTION_ORIGINAL_ADAPTER = 2;

    public static class Cache {
        public static final String USER = "USER";
    }

    public static class API {
        public static final String UNUSED = "null";
        public static final String PER_CHANNEL = "10";
        public static final String PER_PAGE = "10";
        public static final String PER_PAGE_MIN = "4";
        public static final String PER_PAGE_VIR = "21";
        public static final String PIC_WIDTH_BANNER = "1300";
        public static final String PIC_HEIGHT_BANNER = "500";
        public static final String PIC_WIDTH = "300";
        public static final String PIC_HIEGHT = "250";
    }

    public static class Order {
        public static final String LIKED = "liked";
        public static final String VIEWED = "viewed";
        public static final String RECENT = "recent";
    }

}