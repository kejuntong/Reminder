package com.kevintong.reminder.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kevintong.reminder.FontLoader;
import com.kevintong.reminder.activities.CreateTaskActivity;

/**
 * Created by kevintong on 2017-03-31.
 */

public class CustomFontTextView extends TextView {
    public CustomFontTextView(Context context) {
        super(context);
        FontLoader.setTextViewCheri(context, this);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontLoader.setTextViewCheri(context, this);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        FontLoader.setTextViewCheri(context, this);
    }

}
