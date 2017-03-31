package com.kevintong.reminder.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;

import com.kevintong.reminder.FontLoader;

/**
 * Created by kevintong on 2017-03-31.
 */

public class CustomFontEditText extends EditText {
    public CustomFontEditText(Context context) {
        super(context);
        FontLoader.setTextViewCheri(context, this);
    }

    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontLoader.setTextViewCheri(context, this);
    }

    public CustomFontEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        FontLoader.setTextViewCheri(context, this);
    }

}
