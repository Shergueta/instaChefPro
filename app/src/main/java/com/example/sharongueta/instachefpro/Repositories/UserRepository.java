package com.example.sharongueta.instachefpro.Repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.sharongueta.instachefpro.Model.Recipe;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by sharongueta on 19/03/2018.
 */

public class UserRepository {

    private static final UserRepository ourInstance = new UserRepository();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    DatabaseReference userRecipes= database.getReference("UserRecipes");

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

    public LiveData<User> getUserById(String id) {
        final MutableLiveData<User> userLiveData = new MutableLiveData<>();

        usersRef.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        return userLiveData;

    }


    public LiveData<List<Recipe>> getRecipesUserList() {

            final MutableLiveData<List<Recipe>> recipesUserLiveDataList = new MutableLiveData<>();
            final String currentUserId = firebaseAuth.getCurrentUser().getUid();

            userRecipes.child(currentUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    List<Recipe> recipesList = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Recipe r =  new Recipe();
                        r.setRecipeId(ds.getKey());
                        r.setName(ds.getValue(Recipe.class).getName());
                        r.setUrlPhoto(ds.getValue(Recipe.class).getUrlPhoto());
                        r.setDescription(ds.getValue(Recipe.class).getDescription());
                        r.setIngredients(ds.getValue(Recipe.class).getIngredients());
                        recipesList.add(r);
                    }
                    recipesUserLiveDataList.setValue(recipesList);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return recipesUserLiveDataList;

    }

    public LiveData<List<User>> getUsersList() {

            final MutableLiveData<List<User>> usersLiveDataList = new MutableLiveData<>();

            usersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<User> usersList = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        User user=  new User();
                        user.setFirstName(ds.getValue(User.class).getFirstName());
                        user.setLogoUrl(ds.getValue(User.class).getLogoUrl());
                        user.setLastName(ds.getValue(User.class).getLastName());
                        user.setUserId(ds.getValue(User.class).getUserId());
                        user.setEmail(ds.getValue(User.class).getEmail());
                        usersList.add(user);

                    }

                    usersLiveDataList.setValue(usersList);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            return usersLiveDataList;
    }
}
