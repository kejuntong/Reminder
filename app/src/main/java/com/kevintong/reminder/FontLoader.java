package com.kevintong.reminder;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by michael on 2016-05-20.
 */
public class FontLoader {

    public static final String FONT_ROBOTO_LIGHT = "fonts/Roboto-Light.ttf";

    private static Typeface typeface_ASMAN;


    public static void setTextViewASMAN(Context context, TextView textView) {
        try {
            if (typeface_ASMAN == null) {
                typeface_ASMAN = Typeface.createFromAsset(context.getAssets(), "fonts/ASMAN.TTF");
            }
            textView.setTypeface(typeface_ASMAN);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
