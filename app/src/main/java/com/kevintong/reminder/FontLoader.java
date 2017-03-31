package com.kevintong.reminder;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by michael on 2016-05-20.
 */
public class FontLoader {

    private static Typeface typeface_lead_coat;
    private static Typeface typeface_aaw;


    public static void setTextViewLeadCoat(Context context, TextView textView) {
        try {
            if (typeface_lead_coat == null) {
                typeface_lead_coat = Typeface.createFromAsset(context.getAssets(), "fonts/leadcoat.ttf");
            }
            textView.setTypeface(typeface_lead_coat);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTextViewCheri(Context context, TextView textView) {
        try {
            if (typeface_aaw == null) {
                typeface_aaw = Typeface.createFromAsset(context.getAssets(), "fonts/cheri.ttf");
            }
            textView.setTypeface(typeface_aaw);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
