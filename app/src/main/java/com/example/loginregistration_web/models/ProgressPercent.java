package com.example.loginregistration_web.models;

import com.google.gson.annotations.SerializedName;

public class ProgressPercent {
    @SerializedName("totaltasks")
    private String totaltasks;

    @SerializedName("newtasks")
    private String newtasks;

    @SerializedName("inprogresstasks")
    private String inprogresstasks;


    @SerializedName("completedtasks")
    private String completedtasks;

    public ProgressPercent(String totaltasks, String newtasks, String inprogresstasks, String completedtasks) {
        this.totaltasks = totaltasks;
        this.newtasks = newtasks;
        this.inprogresstasks = inprogresstasks;
        this.completedtasks = completedtasks;
    }

    public String getTotaltasks() {
        return totaltasks;
    }

    public String getNewtasks() {
        return newtasks;
    }

    public String getInprogresstasks() {
        return inprogresstasks;
    }

    public String getCompletedtasks() {
        return completedtasks;
    }
}

