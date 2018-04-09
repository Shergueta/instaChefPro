package com.example.sharongueta.instachefpro.Activities;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sharongueta.instachefpro.ViewModels.AuthViewModel;
import com.example.sharongueta.instachefpro.Model.RegisterRequest;
import com.example.sharongueta.instachefpro.Model.ResourceUploadRequest;
import com.example.sharongueta.instachefpro.Model.ServerRequest;
import com.example.sharongueta.instachefpro.Model.User;
import com.example.sharongueta.instachefpro.R;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.squareup.picasso.Picasso;

/**
 * Created by sharongueta on 19/03/2018.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GALLERY_REQUEST_CODE = 888;
    private static final String YA = " ";
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerBtn;
    private ImageView profileImage;
    private Button addPhotoButton;
    private ProgressBar progressBar;
    private ProgressBar addPhotoProgressBar;
    private AuthViewModel authVm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        authVm = ViewModelProviders.of(this).get(AuthViewModel.class);
        bindWidgets();
        setListeners();

    }

    private void bindWidgets() {

        firstNameEditText = findViewById(R.id.register_screen_FirstNamePlainText);
        lastNameEditText = findViewById(R.id.register_screen_LastNamePlainText);
        emailEditText = findViewById(R.id.register_screen_EmailPlainText);
        passwordEditText= findViewById(R.id.register_screen_PasswordPlainText);
        registerBtn =  findViewById(R.id.register_screen_RegisterButton);
        profileImage = findViewById(R.id.register_screen_imageView);
        addPhotoButton = findViewById(R.id.register_screen_AddPicButton);
        addPhotoProgressBar = findViewById(R.id.register_screen_addPhoto_Progress_bar);
        progressBar = findViewById(R.id.register_screen_done_progress_bar);

    }


    private void setListeners() {
        registerBtn.setOnClickListener(this);
        addPhotoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.register_screen_AddPicButton:
                addImage();
                break;

            case R.id.register_screen_RegisterButton:
                registerBtn.setClickable(false);
                registerUser();
                break;



          //  case R.id.button2:

        }
    }

    private void addImage() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        addPhotoButton.setVisibility(View.GONE);
        addPhotoProgressBar.setVisibility(View.VISIBLE);
        final Uri localPhotoUri = data.getData();

        if(requestCode==GALLERY_REQUEST_CODE&& resultCode == Activity.RESULT_OK){

            authVm.uploadProfilePhoto(data.getData().toString()).observe(this, new Observer<ResourceUploadRequest>() {
                @Override
                public void onChanged(@Nullable ResourceUploadRequest resourceUploadRequest) {
                    addPhotoProgressBar.setVisibility(View.GONE);
                    addPhotoButton.setVisibility(View.VISIBLE);

                    if (resourceUploadRequest.isSucceeded()){
                        authVm.setProfilePhotoUrl(resourceUploadRequest.getDownloadUri().toString());
                        Picasso.with(getApplicationContext()).load(localPhotoUri).into(profileImage);
                    } else {
                        authVm.setProfilePhotoUrl("NO_LOGO");
                        Toast.makeText(getApplicationContext(), resourceUploadRequest.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void registerUser() {

        User userToRegister = new User();
        userToRegister.setEmail(emailEditText.getText().toString().trim());
        userToRegister.setFirstName(firstNameEditText.getText().toString().trim());
        userToRegister.setLastName(lastNameEditText.getText().toString().trim());
        userToRegister.setLogoUrl(authVm.getProfilePhotoUrl());
        String userPassword = passwordEditText.getText().toString();

        if (userDetailsAreValid(userToRegister) && isPasswordIsValid(userPassword)) {
            submitUserDetailsToDB(userToRegister, userPassword);
        } else
            registerBtn.setClickable(true);

    }

    private boolean userDetailsAreValid(User userToRegister) {

        return (isEmailValid(userToRegister.getEmail())
                && NameIsValid(userToRegister.getFirstName())&&NameIsValid(userToRegister.getLastName()));

    }

    private boolean isEmailValid(String email) {
        if (email.isEmpty()) {
            emailEditText.setError("Email must not be empty");
            emailEditText.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Email must be valid");
            emailEditText.requestFocus();
            return false;
        } else
            return true;
    }

    private boolean isPasswordIsValid(String password) {
        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters long");
            passwordEditText.requestFocus();
            return false;
        } else
            return true;
    }

    private boolean NameIsValid(String name) {
        if (name == null || name.isEmpty()) {
            emailEditText.setError("Email must not be empty");
            emailEditText.requestFocus();
            return false;
        } else
            return true;
    }


    private void submitUserDetailsToDB(final User userToRegister, String userPassword) {

        progressBar.setVisibility(View.VISIBLE);

        RegisterRequest registerRequest = new RegisterRequest(userToRegister.getEmail(), userPassword);

        authVm.register(registerRequest).observe(this, new Observer<RegisterRequest>() {

            @Override
            public void onChanged(@Nullable RegisterRequest registerRequest) {
                if (registerRequest.isSuccessful()) {

                    userToRegister.setUserId(registerRequest.getUserId());
                    createUserWithDetails(userToRegister);

                } else if (registerRequest.getException() instanceof FirebaseAuthEmailException) {

                    emailEditText.setError("Email already exists");
                    emailEditText.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    registerBtn.setClickable(true);

                } else {

                    Toast.makeText(getApplicationContext(), registerRequest.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    registerBtn.setClickable(true);
                }
            }
        });
    }

    private void createUserWithDetails(User userToRegister) {

        authVm.createUser(userToRegister).observe(this, new Observer<ServerRequest>() {

            @Override
            public void onChanged(@Nullable ServerRequest serverRequest) {
                progressBar.setVisibility(View.GONE);
                registerBtn.setClickable(true);

                if (serverRequest.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), serverRequest.getMessage(), Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), serverRequest.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
