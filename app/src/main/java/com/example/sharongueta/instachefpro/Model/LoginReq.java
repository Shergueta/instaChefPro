package com.example.sharongueta.instachefpro.Model;

/**
 * Created by sharongueta on 18/03/2018.
 */

public class LoginReq {

    private String email;
    private String password;

    public LoginReq(String email, String password) {
        this.email = email;
        this.password = password;
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


}
