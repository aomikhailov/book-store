package ru.almidev.bookstore.models;
import java.time.LocalDateTime;

/**
 * Класс представляет сущность пользователя приложения.
 */
public class AppUser {
    private Integer userId;
    private String fullName;
    private LocalDateTime createdOn;

    public AppUser() {
    }

    public AppUser(Integer userId, String fullName, LocalDateTime createdOn) {
        this.userId = userId;
        this.fullName = fullName;
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
                ", createdOn=" + createdOn +
                '}';
    }
}
