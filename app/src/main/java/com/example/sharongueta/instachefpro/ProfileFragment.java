package com.example.sharongueta.instachefpro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sharongueta on 15/03/2018.
 */

public class ProfileFragment extends Fragment {
    private static final String TAG="ProfileFragment";

    private Button btnTest;
    private TextView firstName ;
    private TextView lastName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.profile_screen,container,false);
        btnTest=view.findViewById(R.id.profile_screen_TestButton);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"Testing button profile",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
