package com.apprikot.mathurat.model;

import android.content.Context;

import com.apprikot.mathurat.controller.utils.StringUtils;
import com.apprikot.mathurat.model.parser.BeanParser;
import com.apprikot.mathurat.model.parser.ListParser;

import java.util.ArrayList;
import java.util.List;

import static com.apprikot.mathurat.model.MemoryCache.DataCache.getMathuratData;

public class MemoryCache {

    public static final String TEMP_NOTFIY = "notify.json";
    public static final String TEMP_DATA = "data.json";
    public static final String TEMP_TASBEH = "tasbeh.json";

    // TODO: check this
    public static void loadToMemory(Context context) {
        getMathuratData(context);
    }

    /***
     * Data
     ***/

    public static class DataCache {

        private static List<MathuratData> mathuratDatas;

        public static List<MathuratData> getMathuratData(Context context) {
            if (DataCache.mathuratDatas == null) {
                String cachedJson = StringUtils.fromAssets(context, TEMP_DATA);
                DataCache.mathuratDatas = (List<MathuratData>) new ListParser().parse(MathuratData.class, cachedJson);
            }
            return DataCache.mathuratDatas;
        }

        public static List<MathuratData> clone(Context context) {
            List<MathuratData> cloneCopy = new ArrayList<>();
            for (MathuratData mathurat : getMathuratData(context)) {
                cloneCopy.add(new MathuratData(mathurat));
            }
            return cloneCopy;
        }

        public static void setMathuratData(List<MathuratData> mathuratDatas) {
            DataCache.mathuratDatas = mathuratDatas;
        }


        public static List<MathuratData> sort(List<MathuratData> mathurats) {
            List<MathuratData> tempMathurats = new ArrayList<>(mathurats);
            List<MathuratData> sortedMathurats = new ArrayList<>();
            for (int i = tempMathurats.size() - 1; i >= 0; i--) {
//                if (tempMathurats.get(i).isChecked) {
//                    sortedMathurats.add(0, tempMathurats.get(i));
//                    tempMathurats.remove(i);
//                }
            }
            sortedMathurats.addAll(tempMathurats);
            return sortedMathurats;
        }

        public static List<Integer> getMathuratIds(Context context) {
            List<Integer> mathuratIds = new ArrayList<>();
            for (MathuratData mathurat : getMathuratData(context)) {
                mathuratIds.add(mathurat.id);
            }
            return mathuratIds;
        }
    }




    /***
     * Tasbeh
     ***/

    public static class TasabehCache {

        private static List<TasbehData> tasbehDatas;

        public static List<TasbehData> getTasbehData(Context context) {
            if (TasabehCache.tasbehDatas == null) {
                String cachedJson = StringUtils.fromAssets(context, TEMP_TASBEH);
                TasabehCache.tasbehDatas = (List<TasbehData>) new ListParser().parse(TasbehData.class, cachedJson);
            }
            return TasabehCache.tasbehDatas;
        }

        public static List<TasbehData> clone(Context context) {
            List<TasbehData> cloneCopy = new ArrayList<>();
            for (TasbehData tasbehData : getTasbehData(context)) {
                cloneCopy.add(new TasbehData(tasbehData));
            }
            return cloneCopy;
        }

        public static void setMathuratData(List<MathuratData> mathuratDatas) {
            DataCache.mathuratDatas = mathuratDatas;
        }


        public static List<TasbehData> sort(List<TasbehData> tasbehDatas) {
            List<TasbehData> tempTasbeh = new ArrayList<>(tasbehDatas);
            List<TasbehData> sortedTasbeh = new ArrayList<>();
            for (int i = tempTasbeh.size() - 1; i >= 0; i--) {
//                if (tempMathurats.get(i).isChecked) {
//                    sortedMathurats.add(0, tempMathurats.get(i));
//                    tempMathurats.remove(i);
//                }
            }
            sortedTasbeh.addAll(tempTasbeh);
            return sortedTasbeh;
        }

        public static List<Integer> getTasbehIds(Context context) {
            List<Integer> tasbehIds = new ArrayList<>();
            for (TasbehData tasbeh : getTasbehData(context)) {
                tasbehIds.add(tasbeh.id);
            }
            return tasbehIds;
        }
    }


    /***
     * Notfiy
     ***/

    public static class NotifyCache {

        private static  Notify notify;

        public static Notify getInitNotify(Context context) {
            if (NotifyCache.notify == null) {
                String cachedJson = StringUtils.fromAssets(context, TEMP_NOTFIY);
                NotifyCache.notify = (Notify) new BeanParser().parse(Notify.class, cachedJson);
            }
            return NotifyCache.notify;
        }
    }

    // TODO: implement
    public static void clean() {
    }
}
