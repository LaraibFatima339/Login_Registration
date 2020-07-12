package com.example.loginregistration_web.models;

public class LoginResponse {

    private boolean error;
    private String message;
    private String userid;
    private String username;
    private String firstname;
    private String lastname;
    private String email;

    public LoginResponse(boolean error, String message, String userid, String username, String firstname, String lastname, String email) {
        this.error = error;
        this.message = message;
        this.userid = userid;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }



}