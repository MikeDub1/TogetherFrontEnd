package com.example.aisparkdevspring2021.Cards;

public class cards {
    private String userId;
    private String name;
    private String profileImageUrl;
    private String bio;

    public cards (String userID, String name, String profileImageUrl, String bio){
        this.userId = userID;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
    }



    public String getUserId(){
        return userId;
    }
    public void setUserID(String userID){
        this.userId = userId;
    }

    public String getName(){
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setName(String name){
        this.name = name;
    }

    private void setBio(String bio){this.bio = bio;}
    private String getBio(String bio){return bio;}
}