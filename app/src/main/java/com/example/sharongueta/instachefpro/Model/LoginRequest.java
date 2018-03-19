package com.example.sharongueta.instachefpro.Model;

/**
 * Created by sharongueta on 18/03/2018.
 */

public class LoginRequest {

    private String email;
    private String password;
    private Boolean successful;
    private String message;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginRequest(boolean successful, String email, String password) {
        this.successful = successful;
        this.email = email;
        this.password = password;
    }

    public LoginRequest(boolean successful, String email, String password, String message) {
        this.successful = successful;
        this.email = email;
        this.password = password;
        this.message = message;
    }

    public LoginRequest() {
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
