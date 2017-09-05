package com.apprikot.mathurat.controller.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;
public class FontUtils {
    public enum CustomFont {

//        GE_LIGHT("Adobe Arabic Regular.ttf"),
//        GE_MEDIUM("Adobe Arabic Regular.ttf"),
//        GE_BOLD("Adobe Arabic Regular.ttf");

        GE_LIGHT("Amiri-Bold.ttf"),
        GE_MEDIUM("Amiri-Bold.ttf"),
        GE_BOLD("Amiri-Bold.ttf");


        private String mFontName;

        CustomFont(String fontName) {
            mFontName = fontName;
        }

        public String getFontName() {
            return mFontName;
        }
    }

    private static Map<CustomFont, Typeface> customFonts;

    public static Typeface getFont(Context context, CustomFont customFont) {
        if (customFonts == null) {
            customFonts = new HashMap<>();
        }

        if (!customFonts.containsKey(customFont) || customFonts.get(customFont) == null) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + customFont.getFontName());
            customFonts.put(customFont, typeface);
        }

        return customFonts.get(customFont);
    }
}