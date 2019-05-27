package com.example.admin;

public class LoginCred {

    private String userName;
    private String password;

    public LoginCred(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
