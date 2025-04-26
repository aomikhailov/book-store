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

    private ViewPaths() {

    }
}
