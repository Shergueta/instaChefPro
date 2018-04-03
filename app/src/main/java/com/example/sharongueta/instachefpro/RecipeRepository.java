package com.example.sharongueta.instachefpro;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.sharongueta.instachefpro.Model.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by sharongueta on 03/04/2018.
 */

class RecipeRepository {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static final RecipeRepository ourInstance = new RecipeRepository();

    public static RecipeRepository getInstance() {
        return ourInstance;
    }

    public LiveData<Recipe> getRecipe(String recipeId){

        DatabaseReference recipeRef =  database.getReference("Recipes").child(recipeId);

        final MutableLiveData <Recipe> recipeLiveDatae = new MutableLiveData<>();
        recipeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeLiveDatae.setValue(dataSnapshot.getValue(Recipe.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return recipeLiveDatae;
    }

}
