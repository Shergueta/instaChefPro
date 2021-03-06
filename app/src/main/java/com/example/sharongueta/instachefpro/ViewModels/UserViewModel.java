package com.example.sharongueta.instachefpro.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.sharongueta.instachefpro.Model.Recipe;
import com.example.sharongueta.instachefpro.Model.User;
import com.example.sharongueta.instachefpro.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sharongueta on 28/03/2018.
 */

public class UserViewModel extends ViewModel {


    private final UserRepository userRepository;
    private LiveData<User> currentUser;
    private List<Recipe> userRecipesSnapshotList = new ArrayList<>();
    private LiveData<List<Recipe>> userRecipesLiveDataList;
    private LiveData<List<User>> usersLiveDataList;
    private List<User> allUsersSnapshotList = new ArrayList<>();

    private String userId;


    public UserViewModel() {
        userRepository = UserRepository.getInstance();
        currentUser = userRepository.getCurrentUser();
        userId = userRepository.getCurrentUserID();
        userRecipesLiveDataList = userRepository.getRecipesUserList();
        usersLiveDataList = userRepository.getUsersList();
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public LiveData<User> getUserById(String id){
        return userRepository.getUserById(id);
    }

    public String getUserId() {
        return userId;
    }


    public LiveData<List<Recipe>> getRecipesOfUserLiveDataList(){
        return userRecipesLiveDataList;
    }


    public List<Recipe> getUserRecipesSnapshotList(){

        return userRecipesSnapshotList;
    }
    public void setUserRecipesSnapshotList(List<Recipe> userRecipesSnapshotList) {
        this.userRecipesSnapshotList = userRecipesSnapshotList;
    }


    public LiveData<List<User>> getAllUsers() {
        return usersLiveDataList;
    }

    public void setUsersLiveDataList(LiveData<List<User>> usersLiveDataList) {
        this.usersLiveDataList = usersLiveDataList;
    }

    public List<User> getAllUsersSnapshotList() {
        return allUsersSnapshotList;
    }

    public void setAllUsersSnapshotList(List<User> allUsersSnapshotList) {
        this.allUsersSnapshotList = allUsersSnapshotList;
    }
}
