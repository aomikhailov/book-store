package ru.almidev.bookstore.controllers;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.almidev.bookstore.config.Config;
import ru.almidev.bookstore.helpers.DatabaseHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaseController extends HttpServlet {

    private final Map<String, Object> defaultAttributes;

    /**
     * Конструктор базового контроллера. Инициализирует карту атрибутов по умолчанию,
     * в том числе заголовок страницы, название приложения и стандартный шаблон контента.
     */
    public BaseController() {
        defaultAttributes = new HashMap<>();
        defaultAttributes.put("pageTitle", Config.getAppName());
        defaultAttributes.put("pageHead", Config.getAppName());
        defaultAttributes.put("contentTemplate", Config.getViewPath().concat("/default.jsp"));
    }

    /**
     * Рендерит представление для текущего запроса, устанавливая недостающие
     * атрибуты по умолчанию и перенаправляя на базовый JSP.
     *
     * @param req объект запроса {@link HttpServletRequest}, содержащий данные запроса.
     * @param resp объект ответа {@link HttpServletResponse}, используемый для передачи данных ответа.
     * @throws ServletException если отправка или обработка запроса вызывает ошибку.
     * @throws IOException если возникает ошибка ввода-вывода.
     */
    protected void renderView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setMissingDefaultAttributes(req);
        req.getRequestDispatcher(Config.getViewPath() + "base.jsp").forward(req, resp);
    }

    /**
     * Устанавливает недостающие атрибуты по умолчанию в объект запроса.
     *
     * @param req объект запроса {@link HttpServletRequest}, в который добавляются атрибуты.
     */
    private void setMissingDefaultAttributes (HttpServletRequest req) {
        for (Map.Entry<String, Object> entry : defaultAttributes.entrySet()) {
            String attributeName = entry.getKey();
            Object defaultValue = entry.getValue();

            if (req.getAttribute(attributeName) == null) {
                req.setAttribute(attributeName, defaultValue);
            }
        }
    }

}
