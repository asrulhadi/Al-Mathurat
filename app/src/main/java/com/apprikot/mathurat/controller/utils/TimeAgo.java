package com.apprikot.mathurat.controller.utils;


import android.content.res.Resources;
import android.util.SparseArray;

import java.util.Date;
import java.util.Locale;

public class TimeAgo {
    public static final int TIME_AGO_PREFIX = 0;
    public static final int TIME_AGO_SECONDS = 1;

    public static final int TIME_AGO_MINUTE = 20;
    public static final int TIME_AGO_2MINUTES = 21;
    public static final int TIME_AGO_MINUTES = 22;

    public static final int TIME_AGO_HOUR = 30;
    public static final int TIME_AGO_2HOURS = 31;
    public static final int TIME_AGO_HOURS = 32;

    public static final int TIME_AGO_DAY = 40;
    public static final int TIME_AGO_2DAYS = 41;
    public static final int TIME_AGO_DAYS = 42;

    public static final int TIME_AGO_MONTH = 50;
    public static final int TIME_AGO_2MONTHS = 51;
    public static final int TIME_AGO_MONTHS = 52;

    public static final int TIME_AGO_YEAR = 60;
    public static final int TIME_AGO_2YEARS = 61;
    public static final int TIME_AGO_YEARS = 62;

    public static SparseArray<String> array;

    public static SparseArray getStringsMap(Resources res) {
        if (array == null || array.size() == 0) {
            array = new SparseArray<>();
//            array.put(TIME_AGO_PREFIX, res.getString(R.string.time_ago_prefix));
//            array.put(TIME_AGO_SECONDS, res.getString(R.string.time_ago_seconds));
//
//            array.put(TIME_AGO_MINUTE, res.getString(R.string.time_ago_minute));
//            array.put(TIME_AGO_2MINUTES, res.getString(R.string.time_ago_2minutes));
//            array.put(TIME_AGO_MINUTES, res.getString(R.string.time_ago_minutes));
//
//            array.put(TIME_AGO_HOUR, res.getString(R.string.time_ago_hour));
//            array.put(TIME_AGO_2HOURS, res.getString(R.string.time_ago_2hours));
//            array.put(TIME_AGO_HOURS, res.getString(R.string.time_ago_hours));
//
//            array.put(TIME_AGO_DAY, res.getString(R.string.time_ago_day));
//            array.put(TIME_AGO_2DAYS, res.getString(R.string.time_ago_2days));
//            array.put(TIME_AGO_DAYS, res.getString(R.string.time_ago_days));
//
//            array.put(TIME_AGO_MONTH, res.getString(R.string.time_ago_month));
//            array.put(TIME_AGO_2MONTHS, res.getString(R.string.time_ago_2months));
//            array.put(TIME_AGO_MONTHS, res.getString(R.string.time_ago_months));
//
//            array.put(TIME_AGO_YEAR, res.getString(R.string.time_ago_year));
//            array.put(TIME_AGO_2YEARS, res.getString(R.string.time_ago_2years));
//            array.put(TIME_AGO_YEARS, res.getString(R.string.time_ago_years));
        }
        return array;
    }

    public static String timeAgo(Resources res, Date date) {
        return timeAgo(res, date.getTime());
    }

    public static String timeAgo(Resources res, long millis) {
        long diff = new Date().getTime() - millis;
        long seconds = Math.abs(diff) / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long months = days / 30;
        long years = days / 365;
        SparseArray stringsMap = getStringsMap(res);
        String format = "%d %s";
        String word;

        if (seconds < 60) {
            word = stringsMap.get(TIME_AGO_SECONDS).toString();
        } else if (minutes == 1) {
            word = stringsMap.get(TIME_AGO_MINUTE).toString();
        } else if (minutes == 2) {
            word = stringsMap.get(TIME_AGO_2MINUTES).toString();
        } else if (minutes <= 10) {
            word = String.format(Locale.ENGLISH, format, minutes, stringsMap.get(TIME_AGO_MINUTES).toString());
        } else if (minutes < 60) {
            word = String.format(Locale.ENGLISH, format, minutes, stringsMap.get(TIME_AGO_MINUTE).toString());
        } else if (hours == 1) {
            word = stringsMap.get(TIME_AGO_HOUR).toString();
        } else if (hours == 2) {
            word = stringsMap.get(TIME_AGO_2HOURS).toString();
        } else if (hours <= 10) {
            word = String.format(Locale.ENGLISH, format, hours, stringsMap.get(TIME_AGO_HOURS).toString());
        } else if (hours < 24) {
            word = String.format(Locale.ENGLISH, format, hours, stringsMap.get(TIME_AGO_HOUR).toString());
        } else if (days == 1) {
            word = stringsMap.get(TIME_AGO_DAY).toString();
        } else if (days == 2) {
            word = stringsMap.get(TIME_AGO_2DAYS).toString();
        } else if (days <= 10) {
            word = String.format(Locale.ENGLISH, format, days, stringsMap.get(TIME_AGO_DAYS).toString());
        } else if (days < 30) {
            word = String.format(Locale.ENGLISH, format, days, stringsMap.get(TIME_AGO_DAY).toString());
        } else if (months == 1) {
            word = stringsMap.get(TIME_AGO_MONTH).toString();
        } else if (months == 2) {
            word = stringsMap.get(TIME_AGO_2MONTHS).toString();
        } else if (months <= 10) {
            word = String.format(Locale.ENGLISH, format, months, stringsMap.get(TIME_AGO_MONTHS).toString());
        } else if (months < 12) {
            word = String.format(Locale.ENGLISH, format, months, stringsMap.get(TIME_AGO_MONTH).toString());
        } else if (years == 1) {
            word = stringsMap.get(TIME_AGO_YEAR).toString();
        } else if (years == 2) {
            word = stringsMap.get(TIME_AGO_2YEARS).toString();
        } else if (years <= 10) {
            word = String.format(Locale.ENGLISH, format, years, stringsMap.get(TIME_AGO_YEARS).toString());
        } else {
            word = String.format(Locale.ENGLISH, format, years, stringsMap.get(TIME_AGO_YEAR).toString());
        }

        String prefix = stringsMap.get(TIME_AGO_PREFIX).toString();
        return prefix.concat(" ").concat(word);
    }
}
