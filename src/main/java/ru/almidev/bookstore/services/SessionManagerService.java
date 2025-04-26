package ru.almidev.bookstore.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SessionManagerService {

    public static final String DEFAULT_SESSION_TOKEN="abc1234567890abcdef1234567890abcdef1234567890abcdef1234567890ab";
    public static final String SESSION_COOKIE_NAME = "SESSION_ID";
    public static final int SESSION_EXPIRE_SECONDS = 60 * 60; // 1 час


    public void createSession(HttpServletResponse resp, String sessionToken) {
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionToken);
        cookie.setMaxAge(SESSION_EXPIRE_SECONDS); // 1 час
        cookie.setPath("/");
        resp.addCookie(cookie);
    }

    public String getSessionToken(HttpServletRequest req) {
        if (req.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : req.getCookies()) {
            if (SESSION_COOKIE_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private String createSessionToken() {
        return java.util.UUID.randomUUID().toString();
    }
}
