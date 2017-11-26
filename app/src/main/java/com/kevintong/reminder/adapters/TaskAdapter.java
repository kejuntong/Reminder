package com.kevintong.reminder.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kevintong.reminder.CallbackInterface;
import com.kevintong.reminder.Constants;
import com.kevintong.reminder.MyApp;
import com.kevintong.reminder.R;
import com.kevintong.reminder.UtilMethods;
import com.kevintong.reminder.activities.EditTaskActivity;
import com.kevintong.reminder.database.TaskDbContract;
import com.kevintong.reminder.database.TaskDbUtilMethods;
import com.kevintong.reminder.models.TaskDetails;
import com.kevintong.reminder.models.TaskName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by kevintong on 2017-03-28.
 */

public class TaskAdapter extends BaseExpandableListAdapter{

    Context mContext;
    LayoutInflater layoutInflater;

    ArrayList<TaskName> headerDataList;
    ArrayList<ArrayList<TaskDetails>> childDataList;

    int expandingPos = -1;
    int collapsePos = -1;

    CallbackInterface removeItemCallback;
    CallbackInterface editItemCallback;

    public TaskAdapter(Context context, ArrayList<TaskName> headerList, ArrayList<ArrayList<TaskDetails>> childList){
        this.mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.headerDataList = headerList;
        this.childDataList = childList;
    }

    @Override
    public int getGroupCount() {
        return this.headerDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childDataList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.headerDataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.childDataList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        final String taskTitle = ((TaskName) getGroup(groupPosition)).getTaskName();

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_task_title, parent, false);
        }
        final TextView textView = (TextView) convertView.findViewById(R.id.title_text);
        textView.setText(taskTitle);

        if (groupPosition == expandingPos){
            Animation textAnim = AnimationUtils.loadAnimation(mContext, R.anim.task_expand);
            textView.startAnimation(textAnim);
            expandingPos = -1;
        } else if (groupPosition == collapsePos){
            Animation textAnim = AnimationUtils.loadAnimation(mContext, R.anim.task_collapse);
            textView.startAnimation(textAnim);
            collapsePos = -1;
        }

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        TaskDetails taskDetail = (TaskDetails) getChild(groupPosition, childPosition);

        // for dummy item
        int whichDetail = taskDetail.getWhichDetail();
        boolean needInflateView = (convertView == null) || (convertView.getTag() == null) || ((int) convertView.getTag() != whichDetail);
        if (whichDetail == TaskDetails.DUMMY_ITEM){
            if (needInflateView){
                convertView = layoutInflater.inflate(R.layout.item_task_details_dummy, parent, false);
                convertView.setTag(whichDetail);
            }
            Button removeButton = (Button) convertView.findViewById(R.id.remove_button);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TaskDbUtilMethods.removeItemFromTaskTable(MyApp.dbHelper, ((TaskName) getGroup(groupPosition)).getTaskId());
                    if (removeItemCallback != null) {
                        removeItemCallback.onCallback(null);
                    }
                }
            });

            final Button editButton = (Button) convertView.findViewById(R.id.edit_button);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Animation editButtonAnim = AnimationUtils.loadAnimation(mContext, R.anim.button_clicked);
                    editButton.startAnimation(editButtonAnim);

                    editButtonAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            editButton.setClickable(false);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            editButton.setClickable(true);

                            String rowId = ((TaskName) getGroup(groupPosition)).getTaskId();
                            SQLiteDatabase readDb = MyApp.dbHelper.getReadableDatabase();
                            Cursor cursor = readDb.rawQuery("SELECT * FROM " + TaskDbContract.TestDbEntry.TABLE +
                                    " WHERE " + TaskDbContract.TestDbEntry._ID + " = " + rowId + " LIMIT 1", null);
                            if (cursor.moveToFirst()){
                                int idx = cursor.getColumnIndex(TaskDbContract.TestDbEntry.COL_ONE);
                                String taskTitle = cursor.getString(idx);
                                idx = cursor.getColumnIndex(TaskDbContract.TestDbEntry.COL_TWO);
                                String taskDesc = cursor.getString(idx);
                                idx = cursor.getColumnIndex(TaskDbContract.TestDbEntry.COL_THREE);
                                Long taskTime = cursor.getLong(idx);

                                Intent intent = new Intent(mContext, EditTaskActivity.class);
                                intent.putExtra(Constants.TASK_ID, rowId);
                                intent.putExtra(Constants.TASK_TITLE, taskTitle);
                                intent.putExtra(Constants.TASK_DESC, taskDesc);
                                intent.putExtra(Constants.TASK_TIME, taskTime);

                                cursor.close();
                                readDb.close();

                                ((Activity) mContext).startActivityForResult(intent, Constants.TASK_LIST_CHANGE);
                            }

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            });

        }
        // for normal task detail items
        else {
            if (needInflateView){
                convertView = layoutInflater.inflate(R.layout.item_task_details, parent, false);
                convertView.setTag(whichDetail);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.detail_text);
            Object detailObject = taskDetail.getDetailObject();
            if (detailObject != null) {
                if (detailObject instanceof String) {
                    textView.setText((String) detailObject);
                } else if (detailObject instanceof Long) {
                    // not sure why it can become 0 when stored as null
                    if ((long) detailObject != 0) {
                        textView.setText(UtilMethods.timeToString((Long) detailObject));
                    } else {
                        textView.setText("");
                    }
                }
            }
        }


        return convertView;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return super.getChildType(groupPosition, childPosition);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
        expandingPos = groupPosition;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
        collapsePos = groupPosition;
    }

    public void setRemoveItemCallback(CallbackInterface cbi){
        this.removeItemCallback = cbi;
    }

    public void setEditItemCallback(CallbackInterface cbi){
        this.editItemCallback = cbi;
    }
}
