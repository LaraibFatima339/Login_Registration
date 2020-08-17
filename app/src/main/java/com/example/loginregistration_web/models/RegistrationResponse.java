package com.example.loginregistration_web.models;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;


    public RegistrationResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
