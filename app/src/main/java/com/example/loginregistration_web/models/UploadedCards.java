package com.example.loginregistration_web.models;

import com.google.gson.annotations.SerializedName;

public class UploadedCards {

    @SerializedName("taskid")
    private String taskid;

    @SerializedName("cardid")
    private String cardid;

    @SerializedName("data")
    private String data;

    @SerializedName("datafile")
    private String datafile;

    public UploadedCards(String taskid, String cardid, String data, String datafile) {
        this.taskid = taskid;
        this.cardid = cardid;
        this.data = data;
        this.datafile = datafile;
    }


    public String getTaskid() {
        return taskid;
    }

    public String getCardid() {
        return cardid;
    }

    public String getData() {
        return data;
    }

    public String getDatafile() {
        return datafile;
    }
}
