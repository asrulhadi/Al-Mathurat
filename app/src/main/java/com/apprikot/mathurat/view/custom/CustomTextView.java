package com.apprikot.mathurat.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.utils.FontUtils;


public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
        init(null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    protected void init(AttributeSet attrs) {
        int ordinal = 1;
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTabLayout);
            ordinal = typedArray.getInt(R.styleable.CustomTabLayout_font, 0);
            typedArray.recycle();
        }
        Typeface typeface = FontUtils.getFont(getContext(), FontUtils.CustomFont.values()[ordinal]);
        setTypeface(typeface);
    }

    public void setFont(FontUtils.CustomFont font) {
        setTypeface(FontUtils.getFont(getContext(), font));
    }
}