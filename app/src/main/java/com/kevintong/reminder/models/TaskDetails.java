package com.kevintong.reminder.models;

/**
 * Created by kevintong on 2017-03-28.
 */

public class TaskDetails {

    public final static int TASK_DESC = 1;
    public final static int TASK_TIME = 2;

    int whichDetail;
    String detailString;

    public TaskDetails(int whichDetail, String detailString){
        this.whichDetail = whichDetail;
        this.detailString = detailString;
    }

    public TaskDetails(){
        this.whichDetail = TASK_DESC;
        this.detailString = "test desc";
    }

    public int getWhichDetail(){
        return this.whichDetail;
    }

    public String getDetailString(){
        return this.detailString;
    }

}
