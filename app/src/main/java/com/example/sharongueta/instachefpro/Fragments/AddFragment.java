package com.example.sharongueta.instachefpro.Fragments;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sharongueta.instachefpro.ViewModels.AddViewModel;
import com.example.sharongueta.instachefpro.Activities.MainActivity;
import com.example.sharongueta.instachefpro.Model.CreateRecipeRequest;
import com.example.sharongueta.instachefpro.Model.Recipe;
import com.example.sharongueta.instachefpro.Model.ResourceUploadRequest;
import com.example.sharongueta.instachefpro.R;
import com.example.sharongueta.instachefpro.ViewModels.UserViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

/**
 * Created by sharongueta on 29/03/2018.
 */
public class AddFragment extends Fragment implements View.OnClickListener{

    private static final int GALLERY_REQUEST_CODE = 999;


    private Button addRecipePhoto;
    private Button finishButton;
    private ProgressBar progressBarPhoto;
    private ProgressBar progressBarFinish;
    private ImageView recipePhoto;
    private EditText recipeName;
    private EditText description;
    private EditText ingredients;

    private AddViewModel addVm;
    private UserViewModel userViewModel;
   FusedLocationProviderClient mFusedLocationProviderClien;


    public AddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.add_screen, container, false);
        bindViewsOfWidget(view);
        setListeners();
        addVm = ViewModelProviders.of(this).get(AddViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);



        mFusedLocationProviderClien = LocationServices.getFusedLocationProviderClient(this.getActivity());
        getDeviceLocation();


        return view;
    }

    private void bindViewsOfWidget(View view) {

        addRecipePhoto = view.findViewById(R.id.add_screen_addPhotoButton);
        finishButton = view.findViewById(R.id.add_screen_finishButton);
        progressBarFinish = view.findViewById(R.id.add_screen_Progress_bar_finish);
        progressBarPhoto = view.findViewById(R.id.add_screen_Progress_bar_addPhoto);
        recipePhoto = view.findViewById(R.id.add_screen_recipeImage);
        recipeName = view.findViewById(R.id.add_screen_RecipeName_plainText);
        description = view.findViewById(R.id.add_screen_TheRecipe_plainText);
        ingredients = view.findViewById(R.id.add_screen_ingredients_PlainText);

    }

    private void setListeners() {

        addRecipePhoto.setOnClickListener(this);
        finishButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.add_screen_addPhotoButton:
                addImage();
                break;

            case R.id.add_screen_finishButton:
                finishButton.setClickable(false);
                createRecipe();
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        addRecipePhoto.setVisibility(View.GONE);
        progressBarPhoto.setVisibility(View.VISIBLE);
        final Uri localImgUri = data.getData();

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            addVm.uploadRecipePhoto(data.getData().toString()).observe(this, new Observer<ResourceUploadRequest>() {
                @Override
                public void onChanged(@Nullable ResourceUploadRequest resourceUploadRequest) {

                    progressBarPhoto.setVisibility(View.GONE);
                    addRecipePhoto.setVisibility(View.VISIBLE);

                    if (resourceUploadRequest.isSucceeded()) {
                        addVm.setRecipePhotoUrl(resourceUploadRequest.getDownloadUri().toString());

                        Picasso.with(getContext()).load(localImgUri).into(recipePhoto);
                    } else {
                        addVm.setRecipePhotoUrl("NO_LOGO");
                        Toast.makeText(getContext(), resourceUploadRequest.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


    }

    private void addImage() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    private void createRecipe() {

        Recipe recipe = new Recipe();
        recipe.setName(recipeName.getText().toString().trim());
        recipe.setDescription(description.getText().toString().trim());
        recipe.setIngredients(ingredients.getText().toString().trim());
        recipe.setUrlPhoto(addVm.getRecipePhotoUrl());
        recipe.setUserId(userViewModel.getUserId());
        recipe.setLat(addVm.getCurrentLocation().getLatitude());
        recipe.setLon(addVm.getCurrentLocation().getLongitude());
        //recipe.setLocation(new Location());

        if (recipeDetailsAreValid(recipe) == true) {
            createRecipeWithDetails(recipe);
            Toast.makeText(getContext(), "your recipe added", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), MainActivity.class));
        } else
            finishButton.setClickable(true);
    }

    private boolean recipeDetailsAreValid(Recipe recipe) {

        //return (isValid(recipe.getName())&& isValid(recipe.getIngredients())&&isValid(recipe.getDescription()));

        if (isValid(recipe.getName())) {
            if (isValid(recipe.getIngredients())) {

                if (isValid(recipe.getDescription())) {
                    return true;
                } else {
                    description.setError("description must not be empty");
                    description.requestFocus();
                    return false;
                }
            } else {

                ingredients.setError("ingredients must not be empty");
                ingredients.requestFocus();
                return false;

            }
        } else {
            recipeName.setError("recipe name must not be empty");
            recipeName.requestFocus();
            return false;
        }


    }

    private boolean isValid(String name) {

        if (name.isEmpty())
            return false;
        else return true;
    }

    private void createRecipeWithDetails(final Recipe recipeToAdd) {

        //add details for the first table " Recipes "

        addVm.createRecipe(recipeToAdd).observe(this, new Observer<CreateRecipeRequest>() {
            @Override
            public void onChanged(@Nullable CreateRecipeRequest createRecipeRequest) {
                progressBarFinish.setVisibility(View.GONE);
                if (createRecipeRequest.isSuccess()) {

                    Toast.makeText(getContext(), createRecipeRequest.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), createRecipeRequest.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });

        //add details for the second table " UsersRecipes "

        addVm.addToDbRecipesId(recipeToAdd).observe(this, new Observer<CreateRecipeRequest>() {
            @Override
            public void onChanged(@Nullable CreateRecipeRequest createRecipeRequest) {
                progressBarFinish.setVisibility(View.GONE);

                if (createRecipeRequest.isSuccess()) {

                    Toast.makeText(getContext(), createRecipeRequest.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), createRecipeRequest.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });


    }



    private void getDeviceLocation() {
        addVm.getDeviceLocation(mFusedLocationProviderClien).observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                addVm.setCurrentLocation(location);
            }
        });

    }


}
