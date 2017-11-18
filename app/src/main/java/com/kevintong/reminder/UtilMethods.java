package com.kevintong.reminder;

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

}
