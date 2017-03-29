package com.kevintong.reminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kevintong on 2017-03-28.
 */

public class TaskAdapter extends BaseExpandableListAdapter{

    Context mContext;
    LayoutInflater layoutInflater;

    ArrayList<String> headerDataList;
    ArrayList<ArrayList<TaskDetails>> childDataList;

    public TaskAdapter(Context context, ArrayList<String> headerList, ArrayList<ArrayList<TaskDetails>> childList){
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

        String taskTitle = (String) getGroup(groupPosition);
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.view_test_row, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.text_view);
        textView.setText(taskTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String detailString = ((TaskDetails) getChild(groupPosition, childPosition)).getDetailString();
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.view_test_row, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.text_view);
        textView.setText(detailString);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
