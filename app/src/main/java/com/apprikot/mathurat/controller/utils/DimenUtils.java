package com.apprikot.mathurat.controller.utils;

import android.content.Context;
import android.graphics.Point;

public class DimenUtils {
    public static int pixelsToDp(Context context, int dimenId) {
        float dimen = context.getResources().getDimension(dimenId);
        return (int) (dimen / context.getResources().getDisplayMetrics().density);
    }

    public static Point getScreenSize(Context context) {
        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        return new Point(width, height);
    }
}