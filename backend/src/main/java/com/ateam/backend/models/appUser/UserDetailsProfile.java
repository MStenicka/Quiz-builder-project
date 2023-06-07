package com.ateam.backend.models.appUser;

public class UserDetailsProfile {

    private String username;
    private String email;
    private Long phone;
    private String city;

    private Role role;

    public UserDetailsProfile(String username, String email, Long phone, String city, Role role) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.role = role;
    }

    public UserDetailsProfile(){}

    @Override
    public String toString() {
        return "UserDetailsProfile{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", city='" + city + '\'' +
                ", role=" + role +
                '}';
    }
}
