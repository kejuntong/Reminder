package com.kevintong.reminder;

import android.content.Context;
import android.util.DisplayMetrics;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by kejuntong on 2017-11-18.
 */

public class UtilMethods {

    public static String timeToString(long timeStamp){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm aaa @ EEE, MMM d, yyyy", Locale.US);
        return sdf.format(timeStamp);
    }

    public static int dpToPx (Context context, int dp){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
