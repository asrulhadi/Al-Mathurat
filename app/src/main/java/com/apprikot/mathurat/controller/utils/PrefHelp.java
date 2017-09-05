package com.apprikot.mathurat.controller.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.apprikot.mathurat.model.MathuratData;
import com.apprikot.mathurat.model.Notify;
import com.apprikot.mathurat.model.TasbehData;
import com.apprikot.mathurat.model.parser.BeanParser;
import com.apprikot.mathurat.model.parser.ListParser;

import java.util.List;


public class PrefHelp {
    private static SharedPreferences prefs = null;
    private static final String LANG = "lang";
    private static final String ALL_NOTIFY_OFF = "all_notify_off";
    private static final String DATA = "data";
    private static final String TASBEH = "tasbeh";
    private static final String NOTIFY_ID = "notify_id";
    private static final String FONT_SIZE = "font_size";
    private static final String RANDOM = "random";
    private static final String ALL_NOTIFY = "all_notify";
    private static final String AUDIO_LANG = "audio_lang";
    private static final String PAGER_VISIBLE = "pager_visible";

    private static SharedPreferences getPrefs(Context context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        }
        return prefs;
    }

    public static String getLang(Context context) {
        return getPrefs(context).getString(LANG, "en");
    }

    public static void setLang(Context context, String lang) {
        SharedPreferences.Editor prefEditor = getPrefs(context).edit();
        prefEditor.putString(LANG, lang).commit();
    }

    public static Integer getRandomNum(Context context) {
        return getPrefs(context).getInt(RANDOM, -1);
    }

    public static void setRandomNum(Context context, int random) {
        SharedPreferences.Editor prefEditor = getPrefs(context).edit();
        prefEditor.putInt(RANDOM, random).commit();
    }

    public static void setIsAllNotifyOff(Context context, boolean off) {
        SharedPreferences.Editor prefEditor = getPrefs(context).edit();
        prefEditor.putBoolean(ALL_NOTIFY_OFF, off).apply();
    }

    public static boolean getIsAllNotifyOff(Context context) {
        return getPrefs(context).getBoolean(ALL_NOTIFY_OFF, false);
    }

    public static void setIsTextAudioLangChange(Context context, boolean off) {
        SharedPreferences.Editor prefEditor = getPrefs(context).edit();
        prefEditor.putBoolean(AUDIO_LANG, off).apply();
    }

    public static boolean isTextAudioLangChange(Context context) {
        return getPrefs(context).getBoolean(AUDIO_LANG, false);
    }

    public static void setIsPagerVisible(Context context, boolean off) {
        SharedPreferences.Editor prefEditor = getPrefs(context).edit();
        prefEditor.putBoolean(PAGER_VISIBLE, off).apply();
    }

    public static boolean isPagerVisible(Context context) {
        return getPrefs(context).getBoolean(PAGER_VISIBLE, false);
    }


    public static void setData(Context context, List<MathuratData> data) {
        SharedPreferences.Editor prefEditor = getPrefs(context).edit();
        String json = StringUtils.pojoToJson(data);
        prefEditor.putString(DATA, json).commit();
    }

    public static List<MathuratData> getData(Context context) {
        String json = getPrefs(context).getString(DATA, null);
        return (List<MathuratData>) new ListParser().parse(MathuratData.class, json);
    }

    public static void setTasbeh(Context context, List<TasbehData> tasbehDatas) {
        SharedPreferences.Editor prefEditor = getPrefs(context).edit();
        String json = StringUtils.pojoToJson(tasbehDatas);
        prefEditor.putString(TASBEH, json).commit();
    }

    public static List<TasbehData> getTasbeh(Context context) {
        String json = getPrefs(context).getString(TASBEH, null);
        return (List<TasbehData>) new ListParser().parse(TasbehData.class, json);
    }


    public static void setNotifyId(Context context, List<Integer> ids) {
        SharedPreferences.Editor prefEditor = getPrefs(context).edit();
        String json = StringUtils.pojoToJson(ids);
        prefEditor.putString(NOTIFY_ID, json).commit();
    }

    public static List<Integer> getNotifyId(Context context) {
        String json = getPrefs(context).getString(NOTIFY_ID, null);
        return (List<Integer>) new ListParser().parse(Integer.class, json);
    }


    public static void setFontSize(Context context, float fontSize) {
        SharedPreferences.Editor prefEditor = getPrefs(context).edit();
        prefEditor.putFloat(FONT_SIZE, fontSize).commit();
    }

    public static float getFontSize(Context context) {
        return getPrefs(context).getFloat(FONT_SIZE, 16);
    }

    public static void setAllNotify(Context context, Notify notfiy) {
        SharedPreferences.Editor prefEditor = getPrefs(context).edit();
        String json = StringUtils.pojoToJson(notfiy);
        prefEditor.putString(ALL_NOTIFY, json).commit();
    }

    public static Notify getAllNotify(Context context) {
        String json = getPrefs(context).getString(ALL_NOTIFY, null);
        if(json == null)
            return null;
        return (Notify) new BeanParser().parse(Notify.class, json);
    }

}
