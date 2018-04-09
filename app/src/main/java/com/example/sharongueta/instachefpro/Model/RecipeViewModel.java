package com.example.sharongueta.instachefpro.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.sharongueta.instachefpro.RecipeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sharongueta on 04/04/2018.
 */

public class RecipeViewModel extends ViewModel {

    private LiveData<List<Recipe>> recipesDetailsLiveDataList;
    private List<Recipe> recipesDetailsSnapshotList = new ArrayList<>();
    private List<Recipe> filteredRecipesDetailsSnapshotList;


    public HashMap<String, String> meMap=new HashMap<String, String>();


    private final RecipeRepository recipeRepository;

    public RecipeViewModel() {
        meMap = new HashMap<>();
      //  meMap.put("bla","ha");
       // String res = meMap.get("bla");
        recipeRepository = RecipeRepository.getInstance();
        recipesDetailsLiveDataList = recipeRepository.getAllRecipes();
        filteredRecipesDetailsSnapshotList = recipesDetailsSnapshotList;
    }

    public LiveData<Recipe> getRecipe(String recipeId){

        return recipeRepository.getRecipe(recipeId);
    }
    public LiveData<List<Recipe>> getAllRecipes() {
        return recipesDetailsLiveDataList;
    }

    public List<Recipe> getRecipesDetailsSnapshotList() {
        return recipesDetailsSnapshotList;
    }

    public void setRecipesDetailsSnapshotList(List<Recipe> recipesDetailsSnapshotList) {
        this.recipesDetailsSnapshotList = recipesDetailsSnapshotList;
    }

    public List<Recipe> getFilteredRecipesDetailsSnapshotList() {
        return filteredRecipesDetailsSnapshotList;
    }

    public void setFilteredRecipesDetailsSnapshotList(List<Recipe> filteredRecipesDetailsSnapshotList) {
        this.filteredRecipesDetailsSnapshotList = filteredRecipesDetailsSnapshotList;
    }

    public HashMap<String, String> getMeMap() {
        return meMap;
    }

    public void setMeMap(HashMap<String, String> meMap) {
        this.meMap = meMap;
    }
}

