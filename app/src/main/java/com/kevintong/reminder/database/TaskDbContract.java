package com.kevintong.reminder.database;

import android.provider.BaseColumns;

/**
 * Created by kevintong on 2017-03-23.
 */

public class TaskDbContract {

    public static final String DB_NAME = "task.db";
    public static final int DB_VERSION = 1;

    public class TestDbEntry implements BaseColumns {
        public static final String TABLE = "task";
        public static final String COL_ONE = "task_title";
        public static final String COL_TWO = "task_desc";
        public static final String COL_THREE = "task_time";
    }


}
