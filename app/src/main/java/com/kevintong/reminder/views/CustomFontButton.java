package com.kevintong.reminder.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.kevintong.reminder.FontLoader;

/**
 * Created by kevintong on 2017-03-31.
 */

public class CustomFontButton extends Button {
    public CustomFontButton(Context context) {
        super(context);
        FontLoader.setTextViewLeadCoat(context, this);
    }

    public CustomFontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontLoader.setTextViewLeadCoat(context, this);
    }

    public CustomFontButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        FontLoader.setTextViewLeadCoat(context, this);
    }

}
