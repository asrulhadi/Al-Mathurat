package com.apprikot.mathurat.controller.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.apprikot.mathurat.controller.receiver.NotifyReceiver;
import com.apprikot.mathurat.model.MathuratData;
import com.apprikot.mathurat.model.TasbehData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static int countWord(String word, String text) {
        int i = 0;
        Pattern p = Pattern.compile(word);
        Matcher m = p.matcher(text);
        while (m.find()) {
            i++;
        }
        return i;
    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean isNumeric(String... params) {
        for (String param : params) {
            if (!org.apache.commons.lang3.StringUtils.isNumeric(param)) {
                return false;
            }
        }
        return true;
    }

    public static String pojoToJson(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void replaceData(Context context, List<MathuratData> list, MathuratData obj) {
        int index = list.indexOf(obj);
        list.set(index, obj);
        PrefHelp.setData(context, list);
    }

    public static void replaceTasbeh(Context context, List<TasbehData> list, TasbehData obj) {
        int index = list.indexOf(obj);
        list.set(index, obj);
        PrefHelp.setTasbeh(context, list);
    }
    public static MathuratData calcDayCounter(int day, MathuratData obj) {
        switch (day){
            case Calendar.SATURDAY:{
                obj.sat_counter += 1;
                break;
            }
            case Calendar.SUNDAY:{
                obj.sun_counter += 1;
                break;
            }
            case Calendar.MONDAY:{
                obj.mon_counter += 1;
                break;
            }
            case Calendar.TUESDAY:{
                obj.tus_counter += 1;
                break;
            }
            case Calendar.WEDNESDAY:{
                obj.wen_counter += 1;
                break;
            }
            case Calendar.THURSDAY:{
                obj.thu_counter += 1;
                break;
            }
            case Calendar.FRIDAY:{
                obj.fri_counter += 1;
                break;
            }
        }
        return obj;
    }


    public static void saveAndOffAllNotify(Context context, List<MathuratData> list) {

        NotifyReceiver.cancelAlarms(context);

        List<MathuratData> mathuratDatas = new ArrayList<>();
        List<Integer> notifyIds = new ArrayList<>();
        for (MathuratData data : list){
            if(data.isNotify){
                data.isNotify = false;
                mathuratDatas.add(data);
                notifyIds.add(data.id);
            }else{
                mathuratDatas.add(data);
            }
        }
        PrefHelp.setData(context, mathuratDatas);
        PrefHelp.setNotifyId(context, notifyIds);
    }

    public static void backAndONAllNotify(Context context) {
        List<MathuratData> mathuratDatas = PrefHelp.getData(context);
        List<Integer> notifyIds = PrefHelp.getNotifyId(context);
        for (Integer id : notifyIds){
            MathuratData data = mathuratDatas.get(id);
            data.isNotify = true;
            mathuratDatas.set(id, data);
        }
        PrefHelp.setData(context, mathuratDatas);
        NotifyReceiver.setAlarms(context);
    }

    public static String buildRepeatedParam(String paramKey, List<Long> params) {
        List<String> paramsStringList = new ArrayList<>();
        final String separator = "_AND_" + paramKey + "_EQU_";
        for (long param : params) {
            paramsStringList.add(String.valueOf(param));
        }
        return org.apache.commons.lang3.StringUtils.join(paramsStringList, separator);
    }

    public static String trimStartingZeros(String str) {
        return str.replaceFirst("^0+(?!$)", "");
    }

    public static String urlDecode(String text) {
        try {
            return URLDecoder.decode(text, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String urlEncode(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String fromAssets(Context context, String fileName) {
        try {
            StringBuilder sb = new StringBuilder();
            InputStream json = context.getAssets().open("json/" + fileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            return sb.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap imgFromAssets(Context context, String fileName) {
        try{
            InputStream json = context.getAssets().open("images/" + fileName);
            Bitmap bmp = BitmapFactory.decodeStream(json);
            return bmp;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getImageId(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public static final String getExtension(final String filename) {
        if (filename == null) return null;
        final String afterLastSlash = filename.substring(filename.lastIndexOf('/') + 1);
        final int afterLastBackslash = afterLastSlash.lastIndexOf('\\') + 1;
        final int dotIndex = afterLastSlash.indexOf('.', afterLastBackslash);
        return (dotIndex == -1) ? "" : afterLastSlash.substring(dotIndex + 1);
    }
}
