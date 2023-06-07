package com.ateam.backend.models.appUser;

import com.ateam.backend.exceptions.UserNotFoundException;
import com.ateam.backend.models.quiz.Quiz;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private Long phone;
    private String city;
    private String activationCode;

    private String resetCode;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean isActive;

    public AppUser(Long id, String username, String email, Role role) {
        this.role = Role.USER;
    }

    @OneToMany(mappedBy = "author", cascade=CascadeType.ALL)
    private List<Quiz> quizzes;

    @ManyToOne
    private Quiz quizToSolve;

    public AppUser(Long id, String username, String password, String email, String activationCode, Long phone, String city, Role role, boolean isActive) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.activationCode = activationCode;
        this.phone = phone;
        this.city = city;
        this.role = role;
        this.isActive = isActive;
    }

    public AppUser() {
        this.role = Role.USER;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getResetCode() {
        return resetCode;
    }
    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role +
                ", activationCode='" + activationCode + '\'' +
                '}';
    }
}