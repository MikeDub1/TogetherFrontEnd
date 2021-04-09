package com.example.aisparkdevspring2021.Cards;

import androidx.annotation.NonNull;

public class BackOfCards {

    private String userId, name, bio, profileImageUrl;

    public BackOfCards(String name, String bio, String profileImageUrl)
    {
        this.name = name;
        this.bio = bio;
        this.profileImageUrl = profileImageUrl;
    }

    private void setName(String name){this.name = name;}
    private String getName(String name){return name;}

    private void setBio(String bio){this.bio = bio;}
    private String getBio(String bio){return bio;}

    private void setProfileImageUrl(String name){this.profileImageUrl = profileImageUrl;}
    private String getProfileImageUrl(String name){return profileImageUrl;}

}
