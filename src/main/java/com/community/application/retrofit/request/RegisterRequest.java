package com.community.application.retrofit.request;

public class RegisterRequest {
    private String username;
    private String name;
    private String email;
    private String password;

    public RegisterRequest(String username, String name, String email, String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString(){
        return "username: " + username + " name: " + name + " email " + email + " password " + password;
    }
}
