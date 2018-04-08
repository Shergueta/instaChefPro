package com.example.sharongueta.instachefpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.sharongueta.instachefpro.Activities.MapActivity;

public class MapsFragment extends Fragment  {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getContext(), MapActivity.class);
        startActivity(intent);
    }
}
