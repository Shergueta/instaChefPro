package com.example.sharongueta.instachefpro;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.sharongueta.instachefpro.Model.ResourceUploadRequest;
import com.example.sharongueta.instachefpro.Model.ServerRequest;
import com.example.sharongueta.instachefpro.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

/**
 * Created by sharongueta on 19/03/2018.
 */

public class UserRepository {

    private static final UserRepository ourInstance = new UserRepository();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    DatabaseReference usersRef = database.getReference("users");
    FirebaseStorage storage = FirebaseStorage.getInstance();


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



    public LiveData<ResourceUploadRequest> uploadProfilePhoto(String profilePhotoURL){

        final MutableLiveData<ResourceUploadRequest> resourceUploadLiveData = new MutableLiveData<>();

        StorageReference storageReference = storage.getReference().child("ProfilePhoto/"+ UUID.randomUUID().toString());
        storageReference.putFile(Uri.parse(profilePhotoURL)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                resourceUploadLiveData.setValue(new ResourceUploadRequest(true,taskSnapshot.getDownloadUrl(),"Image uploaded successfully"));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                resourceUploadLiveData.setValue(new ResourceUploadRequest(false,null,e.getMessage()));
            }
        });


        return resourceUploadLiveData;
    }

    public LiveData<User> getCurrentUser() {

        final MutableLiveData<User> currentUserLiveData = new MutableLiveData<>();

        final String currentUserId = firebaseAuth.getCurrentUser().getUid();

        usersRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                currentUserLiveData.setValue(currentUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw new RuntimeException("Couldn't retrieve user with id: " + currentUserId);
            }
        });

        return currentUserLiveData;    }


        public String getCurrentUserID(){
        String currentUserId = firebaseAuth.getCurrentUser().getUid();
        return  currentUserId;
        }

}
