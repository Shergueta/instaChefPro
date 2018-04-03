package com.example.sharongueta.instachefpro;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sharongueta.instachefpro.Model.Recipe;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by sharongueta on 03/04/2018.
 */

public class RecipeDetailsFragment extends Fragment {

    private String recipeId;

    TextView recipeName;
    TextView description;
    TextView ingredients;
    ImageView recipeImage;
    RecipeViewModel recipeVm;
    ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recipe_details_screen, container, false);
        //recipeId= getArguments().getString("recipeId");

        recipeVm = ViewModelProviders.of(this).get(RecipeViewModel.class);
        bindWidgets(view);
        //populateData();
        return view;


    }

    private void bindWidgets(View view) {
        recipeName= view.findViewById(R.id.recipe_details_recipeName);
        description= view.findViewById(R.id.recipe_details_recipeDescription);
        ingredients= view.findViewById(R.id.recipe_details_recipeIngredients);
        recipeImage= view.findViewById(R.id.recipe_details_imageView);
        progressBar = view.findViewById(R.id.recipe_details_progressBar);

    }

    private void populateData() {
        progressBar.setVisibility(View.VISIBLE);
        recipeVm.getRecipe(recipeId).observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                progressBar.setVisibility(View.GONE);
                //populateViewModel(recipe);
                //populateViewsWithData(recipe);
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
            Picasso.with(getContext()).load(recipe.getUrlPhoto()).networkPolicy(NetworkPolicy.OFFLINE).into(recipeImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(getContext()).load(recipe.getUrlPhoto()).into(recipeImage);
                }
            });
    }

}
