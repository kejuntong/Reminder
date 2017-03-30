package com.kevintong.reminder.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.kevintong.reminder.R;
import com.kevintong.reminder.adapters.TaskAdapter;
import com.kevintong.reminder.models.TaskDetails;
import com.kevintong.reminder.database.TaskDbContract;
import com.kevintong.reminder.database.TaskDbHelper;

import java.util.ArrayList;

public class HomeActivity extends Activity {

    TaskDbHelper dbHelper;

    ExpandableListView expandableListView;
    TaskAdapter taskAdapter;
    ArrayList<String> taskList;
    ArrayList<ArrayList<TaskDetails>> taskDetailList;

    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initRecyclerView();

        dbHelper = new TaskDbHelper(this, TaskDbContract.DB_NAME, null, TaskDbContract.DB_VERSION);

        loadDataFromDb();

        addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                writeAnItemInDb();
//
//                loadDataFromDb();

                startActivity(new Intent(HomeActivity.this, CreateTaskActivity.class));
            }
        });

    }

    public void initRecyclerView(){

        taskList = new ArrayList<>();
        taskDetailList = new ArrayList<>();
        expandableListView = (ExpandableListView) findViewById(R.id.list_view);
        taskAdapter = new TaskAdapter(HomeActivity.this, taskList, taskDetailList);
        expandableListView.setAdapter(taskAdapter);
    }

    public void loadDataFromDb(){
        SQLiteDatabase readDb = dbHelper.getReadableDatabase();
        Cursor cursor = readDb.rawQuery("SELECT * FROM " + TaskDbContract.TestDbEntry.TABLE, null);
//        Cursor cursor = readDb.query(TestDbContract.TestDbEntry.TABLE,
//                new String[]{TestDbContract.TestDbEntry._ID, TestDbContract.TestDbEntry.COL_ONE, TestDbContract.TestDbEntry.COL_TWO, TestDbContract.TestDbEntry.COL_THREE},
//                null, null, null, null, null);

        taskList.clear();
        taskDetailList.clear();

        while (cursor.moveToNext()){
            int idx = cursor.getColumnIndex(TaskDbContract.TestDbEntry.COL_ONE);
            String taskTitle = cursor.getString(idx);
            taskList.add(taskTitle);

            idx = cursor.getColumnIndex(TaskDbContract.TestDbEntry.COL_TWO);
            String taskDesc = cursor.getString(idx);
            idx = cursor.getColumnIndex(TaskDbContract.TestDbEntry.COL_THREE);
            String taskTime = cursor.getString(idx);

            ArrayList<TaskDetails> details = new ArrayList<>();
            details.add(new TaskDetails(TaskDetails.TASK_DESC, taskDesc));
            details.add(new TaskDetails(TaskDetails.TASK_TIME, taskTime));

            taskDetailList.add(details);
        }
        cursor.close();
        readDb.close();
        taskAdapter.notifyDataSetChanged();
    }

    public void writeAnItemInDb(){
        ContentValues values = new ContentValues();
        values.put(TaskDbContract.TestDbEntry.COL_ONE, "test title");
        values.put(TaskDbContract.TestDbEntry.COL_TWO, "added item des");
        values.put(TaskDbContract.TestDbEntry.COL_THREE, "added item time");
        SQLiteDatabase writeDb = dbHelper.getWritableDatabase();
        writeDb.insertWithOnConflict(TaskDbContract.TestDbEntry.TABLE,
                null, values, SQLiteDatabase.CONFLICT_REPLACE);
        writeDb.close();
    }

}
