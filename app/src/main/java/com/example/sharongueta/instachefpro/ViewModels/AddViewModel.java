
package com.example.sharongueta.instachefpro.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;

import com.example.sharongueta.instachefpro.Model.CreateRecipeRequest;
import com.example.sharongueta.instachefpro.Model.Recipe;
import com.example.sharongueta.instachefpro.Model.ResourceUploadRequest;
import com.example.sharongueta.instachefpro.Repositories.AddRepository;
import com.google.android.gms.location.FusedLocationProviderClient;

/**
 * Created by sharongueta on 28/03/2018.
 */

public class AddViewModel extends ViewModel {

    private static final String YA = "d";
    private AddRepository addRepository;

    private String recipePhotoUrl;

    private Location currentLocation;

    public AddViewModel() {

        addRepository = AddRepository.getInstance();
        recipePhotoUrl = "NO_LOGO";
        currentLocation = null;
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

    public LiveData<CreateRecipeRequest> addToDbRecipesId(final Recipe recipe) {
        return addRepository.addToDbRecipesId(recipe);
    }




    public LiveData<Location> getDeviceLocation(FusedLocationProviderClient mFusedLocationProviderClient) {

        return addRepository.getDeviceLocation(mFusedLocationProviderClient);

    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }
}