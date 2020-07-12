package com.example.loginregistration_web.models;

import com.google.gson.annotations.SerializedName;

public class CardDetails {

    @SerializedName("username")
    private String username;

    @SerializedName("cardid")
    private String cardid;

    @SerializedName("text")
    private String text;

    @SerializedName("description")
    private String description;

    @SerializedName("points")
    private String points;

    public CardDetails(String username, String cardid, String text, String description, String points) {
        this.username = username;
        this.cardid = cardid;
        this.text = text;
        this.description = description;
        this.points = points;
    }

    public String getUsername() {
        return username;
    }

    public String getCardid() {
        return cardid;
    }

    public String getText() {
        return text;
    }

    public String getDescription() {
        return description;
    }

    public String getPoints() {
        return points;
    }
}
