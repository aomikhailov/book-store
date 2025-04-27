package ru.almidev.bookstore.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.almidev.bookstore.dao.UserSessionDao;
import ru.almidev.bookstore.models.AppUser;
import ru.almidev.bookstore.models.UserSession;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static ru.almidev.bookstore.attributes.AttributeNames.LOGGED_USER_SESSION;

public class SessionManagerService {
    public static final String SESSION_COOKIE_NAME = "SESSION_ID";
    public static final int SESSION_EXPIRE_SECONDS = 60 * 60; // 1 час

    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final UserSessionDao userSessionDao = new UserSessionDao();
    private UserSession userSession=null;

    /**
     * Конструктор для создания экземпляра SessionManagerService.
     *
     * @param req  HTTP-запрос, используемый для взаимодействия с сеансом.
     * @param resp HTTP-ответ, используемый для взаимодействия с сеансом.
     */
    public SessionManagerService(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
        loadUserSession();
    }

    /**
     * Обновляет активную пользовательскую сессию, продлевая её время.
     * Сохраняет изменения в базе данных и обновляет cookie сессии.
     */
    public void updateUserSession() {
        if (userSession == null) {
            deleteSessionCookie();
            req.setAttribute(LOGGED_USER_SESSION, null);
            return;
        }
        try {
            userSession.setExpiresOn(LocalDateTime.now().plusSeconds(SESSION_EXPIRE_SECONDS));
            userSessionDao.update(userSession);
            setSessionCookie(userSession.getSessionToken().trim(), SESSION_EXPIRE_SECONDS);
            req.setAttribute(LOGGED_USER_SESSION, userSession);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаляет текущую пользовательскую сессию.
     * <p>
     * Если сессия отсутствует, удаляет cookie и сбрасывает атрибут запроса.
     * Если сессия существует, удаляет её из базы данных, удаляет cookie и сбрасывает атрибут запроса.
     * В случае ошибки базы данных выбрасывает RuntimeException.
     */
    public void deleteUserSession() {
        if (userSession == null) {
            deleteSessionCookie();
            req.setAttribute(LOGGED_USER_SESSION, null);
            return;
        }
        try {
            userSessionDao.deleteById(userSession.getSessionId());
            deleteSessionCookie();
            req.setAttribute(LOGGED_USER_SESSION, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Создаёт новую сессию для случайного пользователя (ID 1 или 2) и сохраняет её в базе.
     * Устанавливает созданный токен в cookie.
     */
    public void createUserSession(AppUser appUser) {

        String sessionToken = null;
        UserSession userSession = null;

        try {
            // Поиск имеющейся сессии у этого пользователя
            UserSessionDao userSessionDao = new UserSessionDao();
            userSession = userSessionDao.findByUserId(appUser.getUserId());

            if (userSession != null) {
                sessionToken = userSession.getSessionToken().trim();
                userSession.setExpiresOn(LocalDateTime.now().plusSeconds(SESSION_EXPIRE_SECONDS));
                userSession.setSessionToken(sessionToken);
                userSessionDao.update(userSession);
            } else {
                userSession = new UserSession();
                sessionToken = createSessionToken();
                userSession.setUser(appUser);
                userSession.setSessionToken(sessionToken);
                userSession.setCreatedOn(LocalDateTime.now());
                userSession.setExpiresOn(LocalDateTime.now().plusSeconds(SESSION_EXPIRE_SECONDS));
                userSessionDao.save(userSession);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания сессии:".concat(e.getMessage()), e);
        }

        this.userSession = userSession;
        setSessionCookie(userSession.getSessionToken().trim(), SESSION_EXPIRE_SECONDS);
        req.setAttribute(LOGGED_USER_SESSION, userSession);
    }

    public UserSession getLoggedUserSession() {
        return userSession;
    }

    /**
     * Устанавливает cookie для сеанса с заданным токеном и временем жизни.
     *
     * @param sessionToken  Токен сеанса, сохраняемый в cookie.
     * @param maxAgeSeconds Время жизни cookie в секундах.
     */
    private void setSessionCookie(String sessionToken, int maxAgeSeconds) {
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionToken);
        cookie.setMaxAge(maxAgeSeconds);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }

    /**
     * Удаляет cookie текущей сессии, делая её недействительной для клиента.
     * Устанавливает пустое значение и время жизни равное нулю.
     */
    private void deleteSessionCookie() {
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }

    /**
     * Извлекает значение cookie по имени SESSION_COOKIE_NAME из текущего HTTP-запроса.
     *
     * @return Значение cookie, если оно существует, или null, если cookie отсутствует.
     */
    private String getSessionCookie() {
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

    /**
     * Загружает пользовательскую сессию, используя токен из cookie.
     * <p>
     * Если токен отсутствует, метод завершает выполнение.
     * Если токен найден, пытается получить сессию из базы данных и обновить её.
     * При возникновении SQL-исключения оно игнорируется.
     */
    private void loadUserSession() {
        String sessionToken = getSessionCookie();

        if (sessionToken == null) {
            return;
        }

        try {
            this.userSession = new UserSessionDao().findBySessionToken(sessionToken);
            updateUserSession();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Генерирует уникальный токен для сеанса.
     *
     * @return Сгенерированный строковый токен.
     */
    private String createSessionToken() {
        return java.util.UUID.randomUUID().toString().trim().toUpperCase();
    }
}