package com.kevintong.reminder.models;

/**
 * Created by kejuntong on 2017-11-26.
 */

public class Task {

    String taskId;
    String taskName;
    String taskDesc;
    Long taskTime;

    public Task(String id, String name, String desc, Long time){
        this.taskId = id;
        this.taskName = name;
        this.taskDesc = desc;
        this.taskTime = time;
    }

    public String getTaskId(){
        return this.taskId;
    }

    public String getTaskName(){
        return this.taskName;
    }

    public String getTaskDesc(){
        return this.taskDesc;
    }

    public Long getTaskTime(){
        return this.taskTime;
    }

}
