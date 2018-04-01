package com.example.sharongueta.instachefpro;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharongueta.instachefpro.Model.User;
import com.squareup.picasso.Picasso;

/**
 * Created by sharongueta on 15/03/2018.
 */

public class ProfileFragment extends Fragment  {
    private static final String TAG="ProfileFragment";

    private Button btnTest;
    private TextView firstName ;
    private TextView lastName;
    private ImageView profilePhoto;

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




        return view;
    }



    private void bindWidgetsOfView(View view){

       profilePhoto = view.findViewById(R.id.profile_screen_profile_photo);

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


}

