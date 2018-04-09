package com.example.sharongueta.instachefpro.Model;

/**
 * Created by sharongueta on 28/03/2018.
 */

public class Recipe  {

    private String name;
    private String ingredients;
    private String description;
    private String urlPhoto;
    private String recipeId;
    private String userId;
    private double lon;
    private double lat;


    public Recipe(String userId) {

        this.userId=userId;
    }

    public Recipe() {
    }

    public Recipe(String name, String ingredients, String description, String urlPhoto, String recipeId, String userId, double lon, double lat) {
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.urlPhoto = urlPhoto;
        this.recipeId = recipeId;
        this.userId = userId;
        this.lon = lon;
        this.lat = lat;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
