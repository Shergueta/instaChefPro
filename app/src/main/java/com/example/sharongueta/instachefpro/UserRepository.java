package com.example.sharongueta.instachefpro;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.sharongueta.instachefpro.Model.ServerRequest;
import com.example.sharongueta.instachefpro.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sharongueta on 19/03/2018.
 */

public class UserRepository {

    private static final UserRepository ourInstance = new UserRepository();

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference usersRef = database.getReference("users");

    public static UserRepository getInstance() {
        return ourInstance;
    }

    private UserRepository() {
    }

    public LiveData<ServerRequest> createUser(final User user) {
        final MutableLiveData<ServerRequest> liveData = new MutableLiveData<>();

        DatabaseReference userToAdd = usersRef.child(user.getUserId());

        userToAdd.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ServerRequest serverRequest;

                if (task.isSuccessful())
                    serverRequest = new ServerRequest(true, "User registered successfully", user.getUserId());

                else
                    serverRequest = new ServerRequest(false, task.getException().getMessage(), user.getUserId());

                liveData.setValue(serverRequest);
            }
        });

        return liveData;
    }

}
