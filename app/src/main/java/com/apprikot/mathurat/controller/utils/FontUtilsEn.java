package com.apprikot.mathurat.controller.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

public class FontUtilsEn {
    public enum CustomFont {


        GE_LIGHT("GE SS Two Light.otf"),
        GE_MEDIUM("GE SS Two Light.otf"),
        GE_BOLD("GE SS Two Light.otf");


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