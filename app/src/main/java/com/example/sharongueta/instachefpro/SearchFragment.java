package com.example.sharongueta.instachefpro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sharongueta on 14/03/2018.
 */

public class SearchFragment extends Fragment {
    private static final String TAG="SearchFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.search_screen,container,false);


        return view;
    }
}