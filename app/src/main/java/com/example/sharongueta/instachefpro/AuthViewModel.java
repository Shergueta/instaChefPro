package com.example.sharongueta.instachefpro;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.sharongueta.instachefpro.Model.LoginRequest;
import com.example.sharongueta.instachefpro.Model.RegisterRequest;
import com.example.sharongueta.instachefpro.Model.ResourceUploadRequest;
import com.example.sharongueta.instachefpro.Model.ServerRequest;
import com.example.sharongueta.instachefpro.Model.User;

/**
 * Created by sharongueta on 19/03/2018.
 */

public class AuthViewModel extends ViewModel {

    private AuthRepository authRepository;
    private UserRepository userRepository;

    private String profilePhotoUrl;

    public AuthViewModel() {
        authRepository = AuthRepository.getInstance();
        userRepository = UserRepository.getInstance();
        profilePhotoUrl = "NO_LOGO";
    }


    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }


    public LiveData<LoginRequest> signIn(LoginRequest loginRequest) {
        return authRepository.signIn(loginRequest);
    }


    public LiveData<RegisterRequest> register(RegisterRequest registerRequest) {
        return authRepository.register(registerRequest);
    }

    public LiveData<ServerRequest> createUser(User user) {
        return userRepository.createUser(user);
    }



    public LiveData<ResourceUploadRequest> uploadProfilePhoto(String profilePhotoUrl) {
        return userRepository.uploadProfilePhoto(profilePhotoUrl);
    }
}


