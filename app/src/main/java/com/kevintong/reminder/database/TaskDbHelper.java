package com.kevintong.reminder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kevintong on 2017-03-23.
 */

public class TaskDbHelper extends SQLiteOpenHelper{

    public TaskDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TaskDbContract.TestDbEntry.TABLE + " ( " +
                TaskDbContract.TestDbEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskDbContract.TestDbEntry.COL_ONE + " TEXT , " +
                TaskDbContract.TestDbEntry.COL_TWO + " TEXT , " +
                TaskDbContract.TestDbEntry.COL_THREE + " TEXT);";

        System.out.println("test create SQL string: " + createTable);

        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TaskDbContract.TestDbEntry.TABLE);
        onCreate(sqLiteDatabase);
    }
}
