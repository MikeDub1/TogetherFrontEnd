package com.example.aisparkdevspring2021.Matches;

public class MatchesObject {
    private String userID;

    public MatchesObject (String userID){
        this.userID = userID;

    }
    public String getUserID(){
        return userID;
    }
    public void setUserID(String userID){
        this.userID = userID;

    }
}

