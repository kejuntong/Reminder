package com.kevintong.reminder.models;

/**
 * Created by kevintong on 2017-03-28.
 */

public class TaskDetails {

    // dummy item for holding remove and edit buttons
    public final static int DUMMY_ITEM = 0;

    public final static int TASK_DESC = 1;
    public final static int TASK_TIME = 2;

    int whichDetail;
    Object detailObject;

    public TaskDetails(int whichDetail, Object detail){
        this.whichDetail = whichDetail;
        this.detailObject = detail;
    }

    // for dummy item
    public TaskDetails(){
        this.whichDetail = DUMMY_ITEM;
    }

    public int getWhichDetail(){
        return this.whichDetail;
    }

    public Object getDetailObject(){
        return this.detailObject;
    }

}
