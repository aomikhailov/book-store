package ru.almidev.bookstore.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.almidev.bookstore.dao.AppUserDao;
import ru.almidev.bookstore.dao.UserSessionDao;
import ru.almidev.bookstore.models.AppUser;
import ru.almidev.bookstore.models.UserSession;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static ru.almidev.bookstore.attributes.AttributeNames.*;

public class SessionManagerService {
    public static final String SESSION_COOKIE_NAME = "SESSION_ID";
    public static final int SESSION_EXPIRE_SECONDS = 60 * 60; // 1 час

    private final HttpServletRequest req;
    private final HttpServletResponse resp;

    private final UserSession userSession;

    /**
     * Конструктор для создания экземпляра SessionManagerService.
     *
     * @param req  HTTP-запрос, используемый для взаимодействия с сеансом.
     * @param resp HTTP-ответ, используемый для взаимодействия с сеансом.
     */
    public SessionManagerService(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
        userSession = loadOrCreateUserSession();
        setUserSessionAttributes();
    }

    /**
     * Устанавливает cookie для сеанса с заданным токеном и временем жизни.
     *
     * @param sessionToken  Токен сеанса, сохраняемый в cookie.
     * @param maxAgeSeconds Время жизни cookie в секундах.
     */
    public void setSessionCookie(String sessionToken, int maxAgeSeconds) {
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionToken);
        cookie.setMaxAge(maxAgeSeconds);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }

    /**
     * Извлекает значение cookie по имени SESSION_COOKIE_NAME из текущего HTTP-запроса.
     *
     * @return Значение cookie, если оно существует, или null, если cookie отсутствует.
     */
    public String getSessionCookie() {
        if (req.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : req.getCookies()) {
            if (SESSION_COOKIE_NAME.equals(cookie.getName())) {
                return (String) cookie.getValue();
            }
        }
        return null;
    }

    /**
     * Генерирует уникальный токен для сеанса.
     *
     * @return Сгенерированный строковый токен.
     */
    private String createSessionToken() {
        return java.util.UUID.randomUUID().toString().trim().toUpperCase();
    }

    /**
     * Возвращает объект UserSession на основе токена из cookie или null,
     * если токен отсутствует или сессия не найдена в базе.
     *
     * @return Объект UserSession или null.
     */
    private UserSession loadUserSession() {
        String sessionToken = getSessionCookie();
        UserSession userSession = null;

        if (sessionToken == null) {
            return null;
        }

        try {
            userSession = new UserSessionDao().findBySessionToken(sessionToken);
        } catch (SQLException _) {
        }

        return userSession;
    }

    /**
     * Создаёт новую сессию для случайного пользователя (ID 1 или 2) и сохраняет её в базе.
     * Устанавливает созданный токен в cookie.
     *
     * @return Объект созданного UserSession.
     */
    private UserSession createUserSession() throws SQLException {

        // Случайный выбор пользователя из имеющихся в тестовой БД
        // Для демо режима
        int userId = new Random().nextBoolean() ? 1 : 2;
        AppUser appUser = new AppUserDao().findById(userId);

        // Объявление токена сессии
        String sessionToken = createSessionToken();


       // Поиск имеющейся сессии у этого пользователя
        UserSessionDao userSessionDao = new UserSessionDao();
        UserSession userSession = userSessionDao.findByUserId(userId);

        if (userSession != null) {
            sessionToken = userSession.getSessionToken().trim();
            userSession.setExpiresOn(LocalDateTime.now().plusSeconds(SESSION_EXPIRE_SECONDS));
            userSession.setSessionToken(sessionToken);
            userSessionDao.update(userSession);
        }
        else{
            userSession = new UserSession();
            sessionToken = createSessionToken();
            userSession.setSessionId(userSessionDao.getNextAutoIncrementValue());
            userSession.setUser(appUser);
            userSession.setSessionToken(sessionToken);
            userSession.setCreatedOn(LocalDateTime.now());
            userSession.setExpiresOn(LocalDateTime.now().plusSeconds(SESSION_EXPIRE_SECONDS));
            userSessionDao.save(userSession);
        }

        // Сохранение в сессию
        setSessionCookie(sessionToken, SESSION_EXPIRE_SECONDS);

        return userSession;
    }


    /**
     * Загружает текущую сессию пользователя или создаёт новую, если сессия не найдена.
     *
     * @return Объект {@link UserSession}, представляющий текущую либо новую созданную сессию.
     * @throws RuntimeException если возникает ошибка при работе с базой данных.
     */
    private UserSession loadOrCreateUserSession() {
        try {
            UserSession userSession = loadUserSession();
            if (userSession == null) {
                userSession = createUserSession();
            } else {
                updateUserSession(userSession);
            }
            return userSession;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserSession getLoggedUserSession() {
        return userSession;
    }

    private List<UserSession> getUnloggedUsersSessions() {
        try {
            List<UserSession> unloggedUsersSessions = new UserSessionDao().findAll();

            // Получаем текущую сессию пользователя
            UserSession loggedUserSession = getLoggedUserSession();
            if (loggedUserSession != null) {
                unloggedUsersSessions.removeIf(session ->
                        session.getSessionId().equals(loggedUserSession.getSessionId()));
            }
            return unloggedUsersSessions;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }



    /**
     * Устанавливает атрибуты сессии пользователя в текущем HTTP-запросе.
     * <ul>
     *     <li>Добавляет текущую активную пользовательскую сессию как атрибут {@code LOGGED_USER_SESSION}.</li>
     *     <li>Добавляет список неавторизованных пользовательских сессий как атрибут {@code UNLOGGED_USERS_SESSIONS}.</li>
     * </ul>
     */
    private void setUserSessionAttributes() {
        req.setAttribute(LOGGED_USER_SESSION, userSession);
        req.setAttribute(UNLOGGED_USERS_SESSIONS, getUnloggedUsersSessions());
    }



    /**
     * Обновляет указанную пользовательскую сессию, продлевая время её истечения,
     * сохраняет изменения в базе данных и обновляет cookie сессии.
     *
     * @param userSession Объект {@link UserSession}, представляющий текущую сессию пользователя.
     * @throws SQLException Если возникает ошибка при обновлении в базе данных.
     */
    private void updateUserSession(UserSession userSession) throws SQLException {
        UserSessionDao userSessionDao = new UserSessionDao();
        userSession.setExpiresOn(LocalDateTime.now().plusSeconds(SESSION_EXPIRE_SECONDS));
        userSessionDao.update(userSession);
        setSessionCookie(userSession.getSessionToken().trim(), SESSION_EXPIRE_SECONDS);
    }
}