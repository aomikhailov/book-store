package ru.almidev.bookstore.services;

import jakarta.servlet.http.HttpServletRequest;
import ru.almidev.bookstore.dao.AppUserDao;
import ru.almidev.bookstore.models.AppUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static ru.almidev.bookstore.attributes.AttributeNames.USER_LOGIN_ERROR;
import static ru.almidev.bookstore.attributes.AttributeNames.USER_LOGIN_RESULT;

public class UserLoginService {

    private final AppUserDao appUserDao = new AppUserDao();
    private final HttpServletRequest httpServletRequest;

    public UserLoginService(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public static boolean isValidLogin(String login) {
        if (login == null || login.isEmpty()) {
            return false;
        }
        String regex = "^[a-zA-Z0-9_.-]{5,20}$";
        return login.matches(regex);
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        String regex = "^[a-zA-Z0-9_.-]{7,20}$";
        return password.matches(regex);
    }

    /**
     * Вычисляет MD5-хеш указанной строки и возвращает его в виде строки в верхнем регистре.
     *
     * @param input входная строка для вычисления MD5-хеша
     * @return строка с MD5-хешем в шестнадцатеричном формате в верхнем регистре
     */
    public static String calculateMD5(String input) {
        try {
            // Создаем объект MessageDigest с алгоритмом MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Преобразуем переданную строку в байты и вычисляем хеш
            byte[] hashBytes = md.digest(input.getBytes());

            // Преобразуем байты в шестнадцатеричный (текстовый) формат
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0'); // Добавляем ведущий ноль для единичных байтов
                }
                hexString.append(hex);
            }
            return hexString.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка: MD5 алгоритм недоступен", e);
        }
    }

    public AppUser processLoginRequest() {

        String loginField = "Логин";
        String passwordField = "Пароль";
        String msgErrorEmptyField = "Ошибка: поле \"%s\" должно быть заполнено.";
        String msgErrorWrongValue = "Ошибка: поле \"%s\" содержит недопустимое значение.";


        String login = httpServletRequest.getParameter("login");
        String password = httpServletRequest.getParameter("password");

        if (login == null || login.isEmpty()) {
            httpServletRequest.setAttribute(USER_LOGIN_RESULT, String.format(msgErrorEmptyField, loginField));
            httpServletRequest.setAttribute(USER_LOGIN_ERROR, 1);
            return null;
        }

        if (!isValidLogin(login)) {
            httpServletRequest.setAttribute(USER_LOGIN_RESULT, String.format(msgErrorWrongValue, loginField));
            httpServletRequest.setAttribute(USER_LOGIN_ERROR, 1);
            return null;
        }

        if (password == null || password.isEmpty()) {
            httpServletRequest.setAttribute(USER_LOGIN_RESULT, String.format(msgErrorEmptyField, passwordField));
            httpServletRequest.setAttribute(USER_LOGIN_ERROR, 1);
            return null;
        }

        if (!isValidPassword(password)) {
            httpServletRequest.setAttribute(USER_LOGIN_RESULT, String.format(msgErrorWrongValue, passwordField));
            httpServletRequest.setAttribute(USER_LOGIN_ERROR, 1);
            return null;
        }

        AppUser appUser = getAuthenticatedUser(login, password);

        if (appUser == null) {
            httpServletRequest.setAttribute(USER_LOGIN_RESULT, "Ошибка: пользователь с указанным именем и паролем не найден.");
            httpServletRequest.setAttribute(USER_LOGIN_ERROR, 1);
            return null;
        }

        return appUser;

    }

    private AppUser getAuthenticatedUser(String login, String password) {

        if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
            return null;
        }

        try {
            AppUser appUser = appUserDao.findByLogin(login);
            if (appUser == null) {
                return null;
            }

            if (appUser.getPassword().equals(calculateMD5(password))) {
                return appUser;
            }

        } catch (SQLException e) {
            return null;
        }
        return null;
    }

}
