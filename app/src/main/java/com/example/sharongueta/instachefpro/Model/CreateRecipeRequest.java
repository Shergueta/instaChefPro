package com.example.sharongueta.instachefpro.Model;

/**
 * Created by sharongueta on 01/04/2018.
 */

public class CreateRecipeRequest {

    public String idRecipe;

    public Boolean successful;

    public String message;

    public String userId;

    public CreateRecipeRequest() {
    }

    public CreateRecipeRequest(String idRecipe, Boolean successful, String message, String userId) {
        this.idRecipe = idRecipe;
        this.successful = successful;
        this.message = message;
        this.userId = userId;
    }

    public CreateRecipeRequest(boolean successful, String message, String idRecipe) {
        this.successful = successful;
        this.message = message;
        this.idRecipe = idRecipe;
    }



    public String getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(String idRecipe) {
        this.idRecipe = idRecipe;
    }

    public Boolean isSuccess() {
        return successful;
    }

    public void setSuccessful(Boolean success) {
        this.successful = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
