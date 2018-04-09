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
import com.example.sharongueta.instachefpro.Model.RecipeViewModel;
import com.example.sharongueta.instachefpro.Model.User;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sharongueta on 18/03/2018.
 */

public class HomeFragment extends Fragment {
    private static final String TAG="HomeFragment";

    private ListView  postsList;
    RecipeViewModel recipeVm;
    UserViewModel userVm;

    private String urlProfilePhoto = null;

    ProgressBar progressBar;

    private String nameOfUser;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.home_screen,container,false);
        recipeVm = ViewModelProviders.of(this).get(RecipeViewModel.class);
        userVm = ViewModelProviders.of(this).get(UserViewModel.class);


        bindWidgets(view);
       // getAllRecipes();
        populateFeedList();
        return view;


    }


    private void bindWidgets(View view) {

      postsList = view.findViewById(R.id.home_screen_listView);
      progressBar = view.findViewById(R.id.home_screen_progressBar);


    }

    private void populateFeedList() {

        final FeedListAdapter adapter = new FeedListAdapter();
        this.postsList.setAdapter(adapter);

        recipeVm.getAllRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                progressBar.setVisibility(View.GONE);
                recipeVm.setRecipesDetailsSnapshotList(recipes);
                adapter.notifyDataSetChanged();

            }
        });
        userVm.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                userVm.setAllUsersSnapshotList(users);
                adapter.notifyDataSetChanged();
            }
        });

    }

       private class FeedListAdapter extends BaseAdapter {


            @Override
            public int getCount() {
                return recipeVm.getRecipesDetailsSnapshotList().size();
            }

            @Override
            public Object getItem(int position) {
                return recipeVm.getRecipesDetailsSnapshotList().get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {

                if (view == null) {
                    view = View.inflate(getContext(), R.layout.home_list_row, null);
                }

                Button viewMoreButton= view.findViewById(R.id.home_list_row_showRecipeButton);
                final TextView userName = view.findViewById(R.id.home_list_row_userName);
                TextView recipeName=view.findViewById(R.id.home_list_row_recipeName);
                final ImageView userProfileImage = view.findViewById(R.id.home_list_row_userProfileImage);
                final ImageView recipeImage = view.findViewById(R.id.home_list_row_recipeImage);

                final Recipe recipe = recipeVm.getRecipesDetailsSnapshotList().get(position);

                recipeName.setText(recipe.getName());
                for (int i = 0 ; i < userVm.getAllUsersSnapshotList().size();i++){
                    String userIdFromUser= userVm.getAllUsersSnapshotList().get(i).getUserId();
                    String userIdFromRecipe= recipeVm.getRecipesDetailsSnapshotList().get(position).getUserId();
                    if(userIdFromUser.equals(userIdFromRecipe)){
                        final User user = userVm.getAllUsersSnapshotList().get(i);
                        userName.setText(user.getFirstName()+user.getLastName());
                        if( user.getLogoUrl()!=null&& !user.getLogoUrl().equals("NO_LOGO"))
                            Picasso.with(getContext()).load(user.getLogoUrl()).networkPolicy(NetworkPolicy.OFFLINE).into(userProfileImage, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {
                                    Picasso.with(getContext()).load(recipe.getUrlPhoto()).into(recipeImage);
                                }
                            });

                        break;
                    }
                }


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


                    }
                });

                return view;

            }


        }


}







