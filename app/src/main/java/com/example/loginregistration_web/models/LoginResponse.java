package com.example.loginregistration_web.models;

public class LoginResponse {

    private boolean error;
    private String message;
    private int UserID;
    private String UserName;
    private String FirstName;
    private String LastName;
    private String Email;

    public LoginResponse(boolean error, String message, int userID, String userName, String firstName, String lastName, String email) {
        this.error = error;
        this.message = message;
        UserID = userID;
        UserName = userName;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public int getUserID() {
        return UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }


}