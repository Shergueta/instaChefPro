package com.example.sharongueta.instachefpro.Model;

import java.util.List;

/**
 * Created by sharongueta on 28/03/2018.
 */

public class UserRecepies {

    private String userId;
    private String userName;
    private List<String> recipesIds;

    public UserRecepies(String userId, String userName, List<String> recipesIds) {
        this.userId = userId;
        this.userName = userName;
        this.recipesIds = recipesIds;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getRecipesIds() {
        return recipesIds;
    }

    public void setRecipesIds(List<String> recipesIds) {
        this.recipesIds = recipesIds;
    }
}
