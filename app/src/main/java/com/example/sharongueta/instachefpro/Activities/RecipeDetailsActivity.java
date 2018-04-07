package com.example.sharongueta.instachefpro.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sharongueta.instachefpro.Model.Recipe;
import com.example.sharongueta.instachefpro.Model.RecipeViewModel;
import com.example.sharongueta.instachefpro.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by sharongueta on 03/04/2018.
 */

public class RecipeDetailsActivity extends AppCompatActivity {


    private String recipeId;

    TextView recipeName;
    TextView description;
    TextView ingredients;
    ImageView recipeImage;
    RecipeViewModel recipeVm;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details_screen);
        Intent intent = getIntent();
        recipeId = intent.getStringExtra("recipeId");
        recipeVm = ViewModelProviders.of(this).get(RecipeViewModel.class);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        bindWidgets();
        populateData();
    }


    private void bindWidgets( ) {
        recipeName= findViewById(R.id.recipe_details_recipeName);
        description= findViewById(R.id.recipe_details_recipeDescription);
        ingredients= findViewById(R.id.recipe_details_recipeIngredients);
        recipeImage= findViewById(R.id.recipe_details_imageView);
        progressBar = findViewById(R.id.recipe_details_progressBar);

    }

    private void populateData() {
        progressBar.setVisibility(View.VISIBLE);
        recipeVm.getRecipe(recipeId).observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                progressBar.setVisibility(View.GONE);
                //populateViewModel(recipe);
                populateViewsWithData(recipe);
            }
        });
    }


//    private void populateViewModel(Recipe recipe) {
//    }

    private void populateViewsWithData( Recipe recipe){
        recipeName.setText(recipe.getName());
        ingredients.setText(recipe.getIngredients());
        description.setText(recipe.getDescription());

        setRecipeImage(recipe);

    }

    private void setRecipeImage(final Recipe recipe) {
        if (recipe.getUrlPhoto() != null && !recipe.getUrlPhoto().equals("NO_LOGO"))
            Picasso.with(getApplicationContext()).load(recipe.getUrlPhoto()).networkPolicy(NetworkPolicy.OFFLINE).into(recipeImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(getApplicationContext()).load(recipe.getUrlPhoto()).into(recipeImage);
                }
            });
    }
}
