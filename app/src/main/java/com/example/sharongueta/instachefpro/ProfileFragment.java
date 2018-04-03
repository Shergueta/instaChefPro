package com.example.sharongueta.instachefpro;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sharongueta.instachefpro.Activities.RecipeDetailsActivity;
import com.example.sharongueta.instachefpro.Model.Recipe;
import com.example.sharongueta.instachefpro.Model.User;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sharongueta on 15/03/2018.
 */

public class ProfileFragment extends Fragment  {
    private static final String TAG="ProfileFragment";

    private Button btnTest;
    private TextView fullName ;
    private ListView recipesList;
    private ImageView profilePhoto;
    private ProgressBar userRecipesProgressBar;
    private ProgressBar userDetailsProgressBar;
    private UserViewModel userVm;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.profile_screen,container,false);
        bindWidgetsOfView(view);

        // get current user
            userVm = ViewModelProviders.of(this).get(UserViewModel.class);

            userVm.getCurrentUser().observe(this, new Observer<User>() {
                @Override
                public void onChanged(@Nullable User user) {
                    // get url from user
                    loadImageFromUrl(user.getLogoUrl());


                }
            });

            populateData();

        return view;
    }


    private void bindWidgetsOfView(View view){

        fullName= view.findViewById(R.id.profile_screen_fullNameProfile);
       profilePhoto = view.findViewById(R.id.profile_screen_profilePhoto);
       userDetailsProgressBar= view.findViewById(R.id.profile_screen_progress_bar);
       userRecipesProgressBar = view.findViewById(R.id.profile_screen_List_rogress_bar);
       recipesList = view.findViewById(R.id.profile_screen_RecipesList);

    }


    private void loadImageFromUrl(String url) {

        Picasso.with(getContext()).load(url).placeholder(R.mipmap.ic_launcher).
                error(R.mipmap.ic_launcher)
                .into(profilePhoto, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });

    }


    private void populateData() {
        populateUserDetails();
        populateUserRecipesList();
    }

    private void populateUserRecipesList() {

        userRecipesProgressBar.setVisibility(View.VISIBLE);
        final RecipeListAdapter adapter = new RecipeListAdapter();
        recipesList.setAdapter(adapter);

        userVm.getRecipesOfUserLiveDataList().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                userRecipesProgressBar.setVisibility(View.GONE);
                userVm.setUserRecipesSnapshotList(recipes);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void populateUserDetails() {
        userDetailsProgressBar.setVisibility(View.VISIBLE);
        userVm.getCurrentUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                userDetailsProgressBar.setVisibility(View.GONE);
                fullName.setText(user.getFirstName() + " " + user.getLastName());

            }
        });
    }


    class RecipeListAdapter extends BaseAdapter{

        public RecipeListAdapter() {
        }

        @Override
        public int getCount() {
            return userVm.getUserRecipesSnapshotList().size();
        }

        @Override
        public Object getItem(int position) {
            return userVm.getUserRecipesSnapshotList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            if (view == null) {
                view = View.inflate(getContext(), R.layout.recipes_list_row, null);
            }

            Button recipeName= view.findViewById(R.id.recipe_list_row_recipeNamebuttom);

            final ImageView recipeImage = view.findViewById(R.id.recipes_liw_ImageRecipest_ro);




            final Recipe recipe = userVm.getUserRecipesSnapshotList().get(position);
            recipeName.setText(recipe.getName());
            String ids = recipe.getRecipeId();

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

            view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {


                                            Intent intent = new Intent(getContext(), RecipeDetailsActivity.class);
                                            String id =recipe.getRecipeId();
                                            intent.putExtra("recipeId", id);
                                            //intent.putExtra("viewType", "offerCourseDetails");
                                            startActivity(intent);


//                                             RecipeDetailsFragment nextFrag = new RecipeDetailsFragment();
//                                                FragmentManager manager = getFragmentManager();
//                                                manager.beginTransaction()
//
//                                                        .replace(R.id.profile_main, nextFrag, nextFrag.getTag())
//                                                        .commit();

//
//                                            FragmentManager fm = getFragmentManager();
//                                            FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                                            fragmentTransaction.replace(R.id.profile_main, new RecipeDetailsFragment());
//                                            fragmentTransaction.commit();

                                        }
                                    });

        return view;

        }
    }
}






