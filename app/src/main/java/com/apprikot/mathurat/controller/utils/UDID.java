package com.apprikot.mathurat.controller.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import org.apache.commons.codec.binary.Hex;

import java.util.Arrays;

public class UDID {
    public static String getDeviceId(Context context) {
        String result = UdidPrefs.getUdid(context);
        if (result == null) {
            String androidId = getAndroidId(context);
            String serial = Build.SERIAL;
            String info = getDeviceInfo();
            String udid = org.apache.commons.lang3.StringUtils.join(Arrays.asList(androidId, serial, info), "-");
            String hash = HashUtils.calcHash(udid);
            result = (hash == null ? udid : new String(Hex.encodeHex(hash.getBytes())));
            UdidPrefs.setUdid(context, result);
            Log.i(AppUtils.class.getSimpleName(), "udid: " + result);
        }
        return result;
    }

    private static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private static String getDeviceInfo() {
        return "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10;
    }

    private static class UdidPrefs {
        private static SharedPreferences prefs = null;

        private static final String UDID = "UDID";

        private static SharedPreferences getPrefs(Context context) {
            if (prefs == null) {
                prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
            }
            return prefs;
        }

        public static void setUdid(Context context, String udid) {
            SharedPreferences.Editor prefEditor = getPrefs(context).edit();
            prefEditor.putString(UDID, udid).commit();
        }

        public static String getUdid(Context context) {
            return getPrefs(context).getString(UDID, null);
        }
    }
}
