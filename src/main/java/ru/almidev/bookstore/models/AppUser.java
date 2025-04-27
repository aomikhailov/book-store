package ru.almidev.bookstore.models;

import java.time.LocalDateTime;

/**
 * Класс представляет сущность пользователя приложения.
 */
public class AppUser {
    private Integer userId;
    private String fullName;
    private String login;
    private String password;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdOn;

    public AppUser() {
    }

    public AppUser(Integer userId, String fullName, String login, String password, String email, String phoneNumber, LocalDateTime createdOn) {
        this.userId = userId;
        this.fullName = fullName;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdOn = createdOn;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}