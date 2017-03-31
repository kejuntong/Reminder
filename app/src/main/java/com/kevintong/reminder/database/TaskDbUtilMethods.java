package com.kevintong.reminder.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by kevintong on 2017-03-31.
 */

public class TaskDbUtilMethods {

    public static void writeAnItemToTaskTable(
            TaskDbHelper dbHelper,
            String taskTitle,
            String taskDesc,
            String taskTime)
    {
        ContentValues values = new ContentValues();
        values.put(TaskDbContract.TestDbEntry.COL_ONE, taskTitle);
        values.put(TaskDbContract.TestDbEntry.COL_TWO, taskDesc);
        values.put(TaskDbContract.TestDbEntry.COL_THREE, taskTime);
        SQLiteDatabase writeDb = dbHelper.getWritableDatabase();
        writeDb.insertWithOnConflict(TaskDbContract.TestDbEntry.TABLE,
                null, values, SQLiteDatabase.CONFLICT_REPLACE);
        writeDb.close();
    }

}
