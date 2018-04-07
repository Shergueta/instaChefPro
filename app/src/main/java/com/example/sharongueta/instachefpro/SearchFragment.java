package com.example.sharongueta.instachefpro;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sharongueta.instachefpro.Activities.RecipeDetailsActivity;
import com.example.sharongueta.instachefpro.Model.Recipe;
import com.example.sharongueta.instachefpro.Model.RecipeViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sharongueta on 14/03/2018.
 */

public class SearchFragment extends Fragment {


    private static final String TAG="SearchFragment";

    private EditText searchRecipe;
    private ListView recipesList;
    ProgressBar progressBar ;

    private RecipeViewModel searchRecipeVm;
    private RecipeListAdapter recipeListAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.search_screen,container,false);
        searchRecipeVm = ViewModelProviders.of(this).get(RecipeViewModel.class);

        bindWidgetsOfView(view);
        setupAdapter();
        getAllRecipes();
        setupSearchText();

        return view;
    }

    private void setupAdapter() {
        recipeListAdapter = new RecipeListAdapter();
        recipesList.setAdapter(recipeListAdapter);
    }

    private  void setupSearchText(){
        searchRecipe.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                recipeListAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }
    private void getAllRecipes() {

        progressBar.setVisibility(View.VISIBLE);

        searchRecipeVm.getAllRecipes().observe(this, new Observer<List<Recipe>>(){

            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {

                progressBar.setVisibility(View.GONE);
                searchRecipeVm.setRecipesDetailsSnapshotList(recipes);
                recipeListAdapter.getFilter().filter("");

            }

        });



    }

    private void bindWidgetsOfView(View view) {

        searchRecipe = view.findViewById(R.id.search_screen_SearchPlainText);
        recipesList = view.findViewById(R.id.search_screen_listOfSearchResult);
        progressBar = view.findViewById(R.id.search_screen_progressBar);

    }


    public class RecipeListAdapter extends BaseAdapter implements Filterable{

        @Override
        public int getCount() {
            return searchRecipeVm.getFilteredRecipesDetailsSnapshotList().size();
        }

        @Override
        public Object getItem(int position) {
            return searchRecipeVm.getFilteredRecipesDetailsSnapshotList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null)
            {
                convertView= View.inflate(getContext(),R.layout.recipe_search_row,null);

            }
            TextView recipeName = convertView.findViewById(R.id.recipe_search_row_recipe_name);
            final ImageView recipeImage = convertView.findViewById(R.id.recipe_search_row_recipe_image);

            final Recipe recipeInCtx = searchRecipeVm.getFilteredRecipesDetailsSnapshotList().get(position);
            recipeName.setText(recipeInCtx.getName());

            if (recipeInCtx.getUrlPhoto()!=null && !recipeInCtx.getUrlPhoto().equals("NO_LOGO"))
                Picasso.with(getContext()).load(recipeInCtx.getUrlPhoto()).networkPolicy(NetworkPolicy.OFFLINE).into(recipeImage,new Callback() {


                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getContext()).load(recipeInCtx.getUrlPhoto()).into(recipeImage);
                    }
                    });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), RecipeDetailsActivity.class);
                    intent.putExtra("recipeId", recipeInCtx.getRecipeId());
                    intent.putExtra("viewType", "courseDetails");
                    startActivity(intent);
                }
            });
        return convertView;
        }



        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    if (constraint == null || constraint.length() == 0) {
                        //no constraint given, just return all the data. (no search)
                        results.count = searchRecipeVm.getRecipesDetailsSnapshotList().size();
                        results.values = searchRecipeVm.getRecipesDetailsSnapshotList();
                    } else {//do the search
                        List<Recipe> resultsData = new ArrayList<>();
                        String searchStr = constraint.toString().toUpperCase();
                        for (Recipe o : searchRecipeVm.getRecipesDetailsSnapshotList())
                            if (o.getName().toUpperCase().startsWith(searchStr))
                                resultsData.add(o);
                        results.count = resultsData.size();
                        results.values = resultsData;
                    }
                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    searchRecipeVm.setFilteredRecipesDetailsSnapshotList((ArrayList<Recipe>) results.values);
                    notifyDataSetChanged();
                }
            };
}
    }
}