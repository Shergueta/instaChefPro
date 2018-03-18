package com.example.sharongueta.instachefpro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by sharongueta on 14/03/2018.
 */
//
public class AddFragment extends Fragment {
    private static final String TAG="AddFragment";

    private Button btnTest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.add_screen,container,false);
        btnTest= view.findViewById(R.id.add_screen_addPhotoButton);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"Testing button add",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
