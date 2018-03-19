package com.example.sharongueta.instachefpro.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sharongueta.instachefpro.AuthViewModel;
import com.example.sharongueta.instachefpro.Model.RegisterRequest;
import com.example.sharongueta.instachefpro.Model.User;
import com.example.sharongueta.instachefpro.R;
import com.google.firebase.auth.FirebaseAuthEmailException;

/**
 * Created by sharongueta on 19/03/2018.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String YA ="d" ;
    private ProgressBar progressBar;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    //private Spinner collegeSpinner;
    private Button registerBtn;

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
        progressBar= findViewById(R.id.register_screen_progress_bar);

    }

    private void setListeners() {
        registerBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_screen_RegisterButton:
                registerBtn.setClickable(false);
                registerUser();
                break;
        }
    }

    private void registerUser() {

        User userToRegister = new User();
        userToRegister.setEmail(emailEditText.getText().toString().trim());
        userToRegister.setFirstName(firstNameEditText.getText().toString().trim());
        userToRegister.setLastName(lastNameEditText.getText().toString().trim());
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
                    //createUserWithDetails(userToRegister);

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

//    private void createUserWithDetails(User userToRegister) {
//
//        authVm.createUser(userToRegister).observe(this, new Observer<ServerRequest>() {
//
//            @Override
//            public void onChanged(@Nullable ServerRequest serverRequest) {
//                progressBar.setVisibility(View.GONE);
//                registerBtn.setClickable(true);
//
//                if (serverRequest.isSuccessful()) {
//
//                    Toast.makeText(getApplicationContext(), serverRequest.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                    finish();
//                } else {
//                    Toast.makeText(getApplicationContext(), serverRequest.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
}
