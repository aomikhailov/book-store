package ru.almidev.bookstore.models;

import java.time.LocalDateTime;

/**
 * Класс представляет сущность сессии пользователя приложения.
 */
public class UserSession {
    private Integer sessionId;
    private AppUser user;
    private String sessionToken;
    private LocalDateTime createdOn;
    private LocalDateTime expiresOn;

    public UserSession() {
    }

    public UserSession(Integer sessionId, AppUser user, String sessionToken, LocalDateTime createdOn, LocalDateTime expiresOn) {
        this.sessionId = sessionId;
        this.user = user;
        this.sessionToken = sessionToken;
        this.createdOn = createdOn;
        this.expiresOn = expiresOn;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(LocalDateTime expiresOn) {
        this.expiresOn = expiresOn;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "sessionId=" + sessionId +
                ", user=" + user +
                ", sessionToken='" + sessionToken + '\'' +
                ", createdOn=" + createdOn +
                ", expiresOn=" + expiresOn +
                '}';
    }
}