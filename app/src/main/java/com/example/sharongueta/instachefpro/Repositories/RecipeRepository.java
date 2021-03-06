package com.example.sharongueta.instachefpro.Repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.sharongueta.instachefpro.Model.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sharongueta on 03/04/2018.
 */

public class RecipeRepository {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static final RecipeRepository ourInstance = new RecipeRepository();

    public static RecipeRepository getInstance() {
        return ourInstance;
    }

    public LiveData<Recipe> getRecipe(String recipeId){

        DatabaseReference recipeRef =  database.getReference("Recipes").child(recipeId);

        final MutableLiveData <Recipe> recipeLiveData = new MutableLiveData<>();
        recipeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeLiveData.setValue(dataSnapshot.getValue(Recipe.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return recipeLiveData;
    }

    public LiveData<List<Recipe>> getAllRecipes() {

        final MutableLiveData<List<Recipe>> recipeDetailsListLiveData = new MutableLiveData<>();

        DatabaseReference coursesRef = database.getReference("Recipes");

        coursesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Recipe> recipeDetailsList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Recipe cd = new Recipe();
                    cd.setRecipeId(ds.getKey());
                    cd.setName(ds.getValue(Recipe.class).getName());
                    cd.setUrlPhoto(ds.getValue(Recipe.class).getUrlPhoto());
                    cd.setUserId(ds.getValue(Recipe.class).getUserId());
                    cd.setIngredients(ds.getValue(Recipe.class).getIngredients());
                    cd.setDescription(ds.getValue(Recipe.class).getDescription());
                    cd.setLon(ds.getValue(Recipe.class).getLon());
                    cd.setLat(ds.getValue(Recipe.class).getLat());
                    recipeDetailsList.add(cd);

                }

               recipeDetailsListLiveData.setValue(recipeDetailsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return recipeDetailsListLiveData;
    }

}
