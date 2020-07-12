package com.example.loginregistration_web.models;

import com.google.gson.annotations.SerializedName;

public class TaskDetails {
    @SerializedName("username")
    private String username;

    @SerializedName("taskid")
    private String taskid;

    @SerializedName("name")
    private String taskname;

    @SerializedName("description")
    private String description;


    @SerializedName("duedate")
    private String duedate;


    public TaskDetails(String username, String taskid, String taskname, String description, String duedate) {
        this.username = username;
        this.taskid = taskid;
        this.taskname = taskname;
        this.description = description;
        this.duedate = duedate;
    }

    public String getUsername() {
        return username;
    }

    public String getTaskid () { return taskid;}

    public String getTaskname() {
        return taskname;
    }

    public String getDescription() {
        return description;
    }

    public String getDuedate() {
        return duedate;
    }

}