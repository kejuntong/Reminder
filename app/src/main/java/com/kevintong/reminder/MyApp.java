package com.kevintong.reminder;

import android.app.Application;

import com.kevintong.reminder.database.TaskDbContract;
import com.kevintong.reminder.database.TaskDbHelper;

/**
 * Created by kevintong on 2017-03-31.
 */

public class MyApp extends Application {

    public static TaskDbHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new TaskDbHelper(this, TaskDbContract.DB_NAME, null, TaskDbContract.DB_VERSION);
    }
}
