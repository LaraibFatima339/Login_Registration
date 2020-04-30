package com.example.loginregistration_web.models;

public class RegistrationResponse {

    private boolean error;
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
