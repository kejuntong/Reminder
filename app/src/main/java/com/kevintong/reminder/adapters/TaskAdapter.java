package com.kevintong.reminder.adapters;

import android.content.Context;
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
import com.kevintong.reminder.MyApp;
import com.kevintong.reminder.R;
import com.kevintong.reminder.UtilMethods;
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
        final String taskId = ((TaskName) getGroup(groupPosition)).getTaskId();
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_task_title, parent, false);
        }
        final TextView textView = (TextView) convertView.findViewById(R.id.title_text);
        textView.setText(taskTitle);

        Button removeButton = (Button) convertView.findViewById(R.id.remove_button);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskDbUtilMethods.removeItemFromTaskTable(MyApp.dbHelper, taskId);
                removeItemCallback.onCallback(null);
            }
        });

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
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Object detailObject = ((TaskDetails) getChild(groupPosition, childPosition)).getDetailObject();
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_task_details, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.detail_text);
        if (detailObject != null) {
            if (detailObject instanceof String) {
                textView.setText((String) detailObject);
            } else if (detailObject instanceof Long) {
                textView.setText(UtilMethods.timeToString((Long) detailObject));
            }
        }

        return convertView;
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
}
