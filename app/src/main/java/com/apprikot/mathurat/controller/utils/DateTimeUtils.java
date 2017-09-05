package com.apprikot.mathurat.controller.utils;

import android.content.Context;

import com.apprikot.mathurat.controller.enums.Language;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

// TODO: refactor
public class DateTimeUtils {
    public static final long MS_IN_MIN = 60000;
    public static final long MS_IN_DAY = 60000L * 60 * 24;

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT2 = "dd-MM-yyyy";
    public static final String DATE_FORMAT3 = "yyyy-MM-dd";
    public static final String DATE_FORMAT4 = "yyyy/MM/dd";
    public static final String DATE_FORMAT_ARTICLE = "dd-MM-yyyy HH:mm";
    public static final String DATE_FORMAT_ARTICLE_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT5 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT6 = "dd-MMM-yyyy";


    public static final String DEFAULT_YEAR = "0001";

    /* TODO: clean
    public static Date parseDateTime(String dateAsString) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
            SimpleDateFormat df2 = new SimpleDateFormat(DATE_FORMAT_ARTICLE_FULL, Locale.ENGLISH);
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            df2.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date;
            try {
                date = df.parse(dateAsString);
                return df.parse(df.format(date));
            } catch (Exception e) {
                date = df2.parse(dateAsString);
                return df2.parse(df2.format(date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    */

    public static Date parseDateTime(String dateAsString) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT5, Locale.ENGLISH);
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = df.parse(dateAsString);
            return df.parse(df.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDateTimeIgnoreTimezone(String dateAsString) {
        try {
            SimpleDateFormat outFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
            return outFormat.parse(dateAsString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDate(String dateAsString) {
        try {
            SimpleDateFormat outFormat = new SimpleDateFormat(DATE_FORMAT3, Locale.ENGLISH);
            return outFormat.parse(dateAsString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT3, Locale.US);
        return sdf.format(date);
    }

    public static String getDate(String dateAsString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT2, Locale.US);
        return sdf.format(parseDateTime(dateAsString));
    }

    public static String getDateTennisFormat(String dateAsString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT4, Locale.US);
        return sdf.format(parseDateTime(dateAsString));
    }

    public static String getDateYMD(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT2, Locale.US);
        return sdf.format(date);
    }
    public static String getDateYMDWithMName(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT6, Locale.US);
        return sdf.format(date);
    }

    public static String getDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return sdf.format(date);
    }

    public static String getDateTime(String dateAsString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_ARTICLE, Locale.US);
        return sdf.format(parseDateTime(dateAsString));
    }

    private static String[] days;

    private static String[] getDays(Context context) {
        if (days == null) {
//            days = context.getResources().getStringArray(R.array.days);
        }
        return days;
    }

    public static String getDayName(Context context, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return getDays(context)[day - 1];
    }

    public static String getDayMonth(Date date, String locale) {
        try {
            Locale loc = new Locale(locale);
            SimpleDateFormat outFormat = new SimpleDateFormat("dd-MM", loc);
            return outFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getArticleDateTime(String date) {
        try {
            return new SimpleDateFormat(DATE_FORMAT_ARTICLE, Locale.ENGLISH).format(parseDateTime(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseArticleDateTime(String dateAsString) {
        try {
            SimpleDateFormat outFormat = new SimpleDateFormat(DATE_FORMAT_ARTICLE_FULL, Locale.ENGLISH);
            return outFormat.parse(dateAsString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getArticleDateTime2(String date) {
        try {
            return new SimpleDateFormat(DATE_FORMAT_ARTICLE, Locale.ENGLISH).format(parseArticleDateTime(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCommentDateTime(Context context, String date) {
        try {
            return TimeAgo.timeAgo(context.getResources(), parseDateTime(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getYearMonthArDay(Date date) {
        try {
            Locale arLoc = new Locale(Language.ARABIC.lang);
            Locale enLoc = new Locale(Language.ENGLISH.lang);
            String month = new SimpleDateFormat("MMMM", arLoc).format(date);
            String day = new SimpleDateFormat("dd", enLoc).format(date);
            String year = new SimpleDateFormat("yyyy", enLoc).format(date);
            return String.format("%s %s %s", day, month, year);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDayMonthYear(Date date) {
        try {
            return new SimpleDateFormat(DATE_FORMAT2, Locale.ENGLISH).format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDayMonthYear(String date) {
        try {
            return new SimpleDateFormat(DATE_FORMAT2, Locale.ENGLISH).format(parseDateTime(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//    public static Date getDateWithTimeZone(String dateAsString) {
//        try {
//            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
//            df.setTimeZone(TimeZone.getTimeZone("UTC"));
//            Date date = df.parse(dateAsString);
//            df.setTimeZone(TimeZone.getDefault());
//            return  df.parse(df.format(date));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static String getTime(String dateAsString) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = df.parse(dateAsString);
            df.setTimeZone(TimeZone.getDefault());
            return new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTime(Date date) {
        try {
            int gmtOffset = TimeZone.getDefault().getRawOffset();
            long fixedDate = date.getTime() + gmtOffset;
            return new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date(fixedDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static float getTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        return tz.getOffset(now.getTime()) / 1000.f / 3600.f;
    }

    public static int getTimeZoneMs() {
        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        return tz.getOffset(now.getTime());
    }

    public static String getDayMonth(Date date) {
        try {
            Locale arLoc = new Locale(Language.ARABIC.lang);
            Locale enLoc = new Locale(Language.ENGLISH.lang);
            String month = new SimpleDateFormat("MMMM", arLoc).format(date);
            String day = new SimpleDateFormat("dd", enLoc).format(date);
            return String.format(Locale.ENGLISH, "%s %s", day, month);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getAge(Date dateOfBirth) {
        if (dateOfBirth == null) {
            return 0;
        }
        Calendar dob = Calendar.getInstance();
        dob.setTime(dateOfBirth);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        boolean case1 = today.get(Calendar.MONTH) < dob.get(Calendar.MONTH);
        boolean case2 = today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH);
        return (case1 || case2 ? --age : age);
    }
}