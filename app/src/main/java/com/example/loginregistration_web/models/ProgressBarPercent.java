package com.example.loginregistration_web.models;

import com.google.gson.annotations.SerializedName;

public class ProgressBarPercent {

    @SerializedName("error")
    private boolean error;

    @SerializedName("percentage")
    private String percentage;

    public ProgressBarPercent(boolean error, String percentage) {
        this.error = error;
        this.percentage = percentage;
    }

    public boolean isError() {
        return error;
    }

    public String getPercentage() {
        return percentage;
    }
}
