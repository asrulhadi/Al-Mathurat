package com.apprikot.mathurat.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.utils.FontUtils;

public class CustomEditText extends EditText {
    public CustomEditText(Context context) {
        super(context);
        init(null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
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

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clearFocus();
        }
        return super.onKeyPreIme(keyCode, event);
    }
}