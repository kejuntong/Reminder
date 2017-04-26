package com.kevintong.reminder.models;

/**
 * Created by kevintong on 2017-04-26.
 */

public class TaskName {

    String taskName;
    String taskId;

    public TaskName(String taskName, String taskId){
        this.taskName = taskName;
        this.taskId = taskId;
    }

    public String getTaskName(){
        return this.taskName;
    }

    public String getTaskId(){
        return taskId;
    }

}
