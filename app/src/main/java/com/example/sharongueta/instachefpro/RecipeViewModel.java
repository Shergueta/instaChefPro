package com.example.sharongueta.instachefpro;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.sharongueta.instachefpro.Model.Recipe;

/**
 * Created by sharongueta on 03/04/2018.
 */

public class RecipeViewModel extends ViewModel {

    private RecipeRepository recipeRepository;
    private  UserRepository userRepository;


    public RecipeViewModel() {

        recipeRepository= RecipeRepository.getInstance();

    }

    public LiveData<Recipe> getRecipe(String recipeId){

        return recipeRepository.getRecipe(recipeId);
    }




}
