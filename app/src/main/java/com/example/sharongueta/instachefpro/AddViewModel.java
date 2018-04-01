
package com.example.sharongueta.instachefpro;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.sharongueta.instachefpro.Model.CreateRecipeRequest;
import com.example.sharongueta.instachefpro.Model.Recipe;
import com.example.sharongueta.instachefpro.Model.ResourceUploadRequest;

/**
 * Created by sharongueta on 28/03/2018.
 */

public class AddViewModel extends ViewModel{

    private AddRepository addRepository;

    private String recipePhotoUrl;


    public AddViewModel() {

        addRepository= AddRepository.getInstance();
        recipePhotoUrl = "NO_LOGO";
    }

    public String getRecipePhotoUrl() {
        return recipePhotoUrl;
    }

    public void setRecipePhotoUrl(String recipePhotoUrl) {
        this.recipePhotoUrl = recipePhotoUrl;
    }


    public LiveData<ResourceUploadRequest> uploadRecipePhoto(String profilePhotoUrl) {
        return addRepository.uploadRecipePhoto(profilePhotoUrl);

    }

    public LiveData<CreateRecipeRequest> createRecipe(Recipe recipe) {
        return addRepository.createRecipe(recipe);
    }

    public LiveData<CreateRecipeRequest> addToDbRecipesId(final Recipe recipe){
        return addRepository.addToDbRecipesId(recipe);
    }
}
