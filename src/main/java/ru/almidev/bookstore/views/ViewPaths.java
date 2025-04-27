package ru.almidev.bookstore.views;

import ru.almidev.bookstore.config.Config;

/**
 * Класс содержит предопределенные пути до JSP-шаблонов, используемых в приложении.
 * Пути формируются на основе базового пути, заданного в конфигурации.
 * Конструктор закрыт, так как класс не предназначен для создания экземпляров.
 */
public class ViewPaths {

    public static final String BASE_JSP = Config.getViewPath().concat("base.jsp");
    public static final String DEFAULT_JSP = Config.getViewPath().concat("default.jsp");
    public static final String INDEX_JSP = Config.getViewPath().concat("index.jsp");
    public static final String CATALOG_JSP = Config.getViewPath().concat("catalog.jsp");
    public static final String ERROR_JSP = Config.getViewPath().concat("error.jsp");
    public static final String AUTHORS_JSP = Config.getViewPath().concat("authors.jsp");
    public static final String AUTHOR_BOOKS_JSP = Config.getViewPath().concat("authorBooks.jsp");
    public static final String USER_CART_JSP = Config.getViewPath().concat("userCart.jsp");
    public static final String USER_LOGIN_FORM_JSP = Config.getViewPath().concat("userLoginForm.jsp");
    public static final String USER_LOGIN_SUCCESS_JSP = Config.getViewPath().concat("userLoginSuccess.jsp");
    public static final String USER_LOGOUT_JSP = Config.getViewPath().concat("userLogout.jsp");
    private ViewPaths() {

    }
}
