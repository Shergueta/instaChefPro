package com.example.sharongueta.instachefpro;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.sharongueta.instachefpro.Model.User;

/**
 * Created by sharongueta on 28/03/2018.
 */

public class UserViewModel extends ViewModel{


    private final UserRepository userRepository;
    private LiveData<User> currentUser;
    private String userId ;

    public UserViewModel() {
        userRepository = UserRepository.getInstance();
        currentUser = userRepository.getCurrentUser();
        userId = userRepository.getCurrentUserID();
}

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public String getUserId(){
        return userId;
    }


}
