package com.kevintong.reminder.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.kevintong.reminder.CallbackInterface;
import com.kevintong.reminder.Constants;
import com.kevintong.reminder.MyApp;
import com.kevintong.reminder.R;
import com.kevintong.reminder.adapters.TaskAdapter;
import com.kevintong.reminder.database.TaskDbUtilMethods;
import com.kevintong.reminder.models.TaskDetails;
import com.kevintong.reminder.database.TaskDbContract;
import com.kevintong.reminder.database.TaskDbHelper;
import com.kevintong.reminder.models.TaskName;

import java.util.ArrayList;

public class HomeActivity extends Activity {

    ExpandableListView expandableListView;
    TaskAdapter taskAdapter;
    ArrayList<TaskName> taskList;
    ArrayList<ArrayList<TaskDetails>> taskDetailList;

    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initListView();

        setAddButton();

        loadDataFromDb();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.TASK_LIST_CHANGE){
            if (resultCode == RESULT_OK){
                // load data from data base when there is any change
                loadDataFromDb();
            }
        }
    }

    private void setAddButton(){
        addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Animation addButtonAnim = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.button_clicked);
                addButton.startAnimation(addButtonAnim);

                addButtonAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        addButton.setClickable(false);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        addButton.setClickable(true);
                        startActivityForResult(new Intent(HomeActivity.this, CreateTaskActivity.class), Constants.TASK_LIST_CHANGE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        });
    }

    public void initListView(){

        taskList = new ArrayList<>();
        taskDetailList = new ArrayList<>();
        expandableListView = (ExpandableListView) findViewById(R.id.list_view);
        taskAdapter = new TaskAdapter(HomeActivity.this, taskList, taskDetailList);

        taskAdapter.setRemoveItemCallback(new CallbackInterface() {
            @Override
            public void onCallback(Object object) {
                loadDataFromDb();
            }
        });

        expandableListView.setAdapter(taskAdapter);
    }

    public void loadDataFromDb(){
        SQLiteDatabase readDb = MyApp.dbHelper.getReadableDatabase();
        Cursor cursor = readDb.rawQuery("SELECT * FROM " + TaskDbContract.TestDbEntry.TABLE, null);
//        Cursor cursor = readDb.query(TestDbContract.TestDbEntry.TABLE,
//                new String[]{TestDbContract.TestDbEntry._ID, TestDbContract.TestDbEntry.COL_ONE, TestDbContract.TestDbEntry.COL_TWO, TestDbContract.TestDbEntry.COL_THREE},
//                null, null, null, null, null);

        taskList.clear();
        taskDetailList.clear();

        while (cursor.moveToNext()){
            int idx = cursor.getColumnIndex(TaskDbContract.TestDbEntry.COL_ONE);
            String taskTitle = cursor.getString(idx);

            idx = cursor.getColumnIndex(TaskDbContract.TestDbEntry._ID);
            String taskId = cursor.getString(idx);

            taskList.add(new TaskName(taskTitle, taskId));

            idx = cursor.getColumnIndex(TaskDbContract.TestDbEntry.COL_TWO);
            String taskDesc = cursor.getString(idx);
            idx = cursor.getColumnIndex(TaskDbContract.TestDbEntry.COL_THREE);
            Long taskTime = cursor.getLong(idx);

            ArrayList<TaskDetails> details = new ArrayList<>();
            details.add(new TaskDetails(TaskDetails.TASK_DESC, taskDesc));
            details.add(new TaskDetails(TaskDetails.TASK_TIME, taskTime));

            // dummy item for holding remove and edit buttons
            details.add(new TaskDetails());

            taskDetailList.add(details);
        }
        cursor.close();
        readDb.close();
        taskAdapter.notifyDataSetChanged();
    }

}
