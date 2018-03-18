package com.example.sharongueta.instachefpro;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by sharongueta on 14/03/2018.
 */

public class MapFragment extends Fragment {
private static final String TAG="MapFragment";

private Button btnTest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.map_screen,container,false);
        btnTest= view.findViewById(R.id.map_screen_TestButton);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"Testing button map",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
