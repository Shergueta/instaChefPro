package com.example.sharongueta.instachefpro.Repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.sharongueta.instachefpro.Model.CreateRecipeRequest;
import com.example.sharongueta.instachefpro.Model.Recipe;
import com.example.sharongueta.instachefpro.Model.ResourceUploadRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

/**
 * Created by sharongueta on 28/03/2018.
 */

public class AddRepository {

    private static final AddRepository ourInstance = new AddRepository();
    public FusedLocationProviderClient mFusedLocationProviderClient;

    public static AddRepository getInstance() {
        return ourInstance;
    }
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference usersRecipes = database.getReference("UserRecipes");

    DatabaseReference recipes = database.getReference("Recipes");

    FirebaseStorage storage = FirebaseStorage.getInstance();

    String recipeKey;

    public AddRepository() {
    }

    public LiveData<ResourceUploadRequest> uploadRecipePhoto(String recipePhotoUrl) {

        final MutableLiveData<ResourceUploadRequest> resourceUploadLiveData = new MutableLiveData<>();

        StorageReference storageReference = storage.getReference().child("ProfilePhoto/"+ UUID.randomUUID().toString());
        storageReference.putFile(Uri.parse(recipePhotoUrl)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                resourceUploadLiveData.setValue(new ResourceUploadRequest(true,taskSnapshot.getDownloadUrl(),"Image uploaded successfully"));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                resourceUploadLiveData.setValue(new ResourceUploadRequest(false,null,e.getMessage()));
            }
        });


        return resourceUploadLiveData;

    }

    public LiveData<CreateRecipeRequest> createRecipe(final Recipe recipe){

        final MutableLiveData<CreateRecipeRequest> liveData = new MutableLiveData<>();

        DatabaseReference recipeToAddRef = recipes.push();
         recipeKey = recipeToAddRef.getKey();

        recipeToAddRef.setValue(recipe).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    liveData.setValue(new CreateRecipeRequest(true,"Recipe created seccesfully",recipeKey));
                }
                else {
                    liveData.setValue(new CreateRecipeRequest(false,
                            task.getException().getMessage(),
                            recipeKey));
                }
            }
        });

        return liveData;


    }

    public LiveData<CreateRecipeRequest> addToDbRecipesId(final Recipe recipe){
        final MutableLiveData<CreateRecipeRequest> liveData = new MutableLiveData<>();

        DatabaseReference recipeToAddRef = usersRecipes.child(recipe.getUserId());

        recipeToAddRef.child(recipeKey).setValue(recipe).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    liveData.setValue(new CreateRecipeRequest(true,"Recipe created seccesfully",recipeKey));
                }
                else {
                    liveData.setValue(new CreateRecipeRequest(false,
                            task.getException().getMessage(),
                            recipeKey));
                }
            }
        });


        return liveData;
    }

    public LiveData<Location> getDeviceLocation(FusedLocationProviderClient mFusedLocationProviderClient){

        final MutableLiveData<Location> liveData = new MutableLiveData<>();
        try{
            if(checkPremission())
            {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Location cuurentLocation= (Location) task.getResult();
                            liveData.setValue(cuurentLocation);
                        }
                        else
                        {}
                    }}); }
        }catch (SecurityException e){ }

        return liveData;

    }

    boolean checkPremission(){
        return true;
    }


}