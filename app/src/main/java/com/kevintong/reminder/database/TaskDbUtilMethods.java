package com.kevintong.reminder.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.kevintong.reminder.models.Task;

/**
 * Created by kevintong on 2017-03-31.
 */

public class TaskDbUtilMethods {

    public static void writeAnItemToTaskTable(
            TaskDbHelper dbHelper,
            String taskTitle,
            String taskDesc,
            Long taskTime)
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

    public static void removeItemFromTaskTable(TaskDbHelper dbHelper, String taskId){
        SQLiteDatabase writeDb = dbHelper.getWritableDatabase();
        writeDb.delete(TaskDbContract.TestDbEntry.TABLE, TaskDbContract.TestDbEntry._ID + "=" + taskId, null);
        writeDb.close();
    }

    public static void updateItemFromTaskTable(TaskDbHelper dbHelper, Task task){
        ContentValues values = new ContentValues();
        values.put(TaskDbContract.TestDbEntry.COL_ONE, task.getTaskName());
        values.put(TaskDbContract.TestDbEntry.COL_TWO, task.getTaskDesc());
        values.put(TaskDbContract.TestDbEntry.COL_THREE, task.getTaskTime());
        SQLiteDatabase writeDb = dbHelper.getWritableDatabase();
        writeDb.update(TaskDbContract.TestDbEntry.TABLE, values, TaskDbContract.TestDbEntry._ID + "=" + task.getTaskId(), null);
        writeDb.close();
    }

}
