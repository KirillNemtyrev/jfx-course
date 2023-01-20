package com.community.application.retrofit.request;

public class PasswordRequest {
    private String oldPassword;
    private String newPassword;

    public PasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
