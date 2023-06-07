package com.ateam.backend.models.appUser;

public class PasswordResetReqData {

    private String username;

    private String email;

    public PasswordResetReqData(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
