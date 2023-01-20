package com.community.application.retrofit.response;

public class SettingsResponse {
    private String username;
    private String name;
    private String email;
    private Long lastDividend;

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Long getLastDividend() {
        return lastDividend;
    }
}
