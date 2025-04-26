package ru.almidev.bookstore.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.almidev.bookstore.config.Config;
import ru.almidev.bookstore.models.UserSession;
import ru.almidev.bookstore.services.BookstoreService;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static ru.almidev.bookstore.attributes.AttributeNames.*;
import static ru.almidev.bookstore.views.ViewPaths.BASE_JSP;
import static ru.almidev.bookstore.views.ViewPaths.DEFAULT_JSP;

public class BaseController extends HttpServlet {

    protected final BookstoreService bookstoreService;
    private final Map<String, Object> defaultAttributes;

    /**
     * Конструктор базового контроллера. Инициализирует карту атрибутов по умолчанию,
     * в том числе заголовок страницы, название приложения и стандартный шаблон контента.
     */
    public BaseController() {
        defaultAttributes = new HashMap<>();
        defaultAttributes.put(PAGE_TITLE, Config.getAppName());
        defaultAttributes.put(PAGE_HEAD, Config.getAppName());
        defaultAttributes.put(LOGGED_USER_SESSION, new UserSession());
        defaultAttributes.put(UNLOGGED_USERS_SESSIONS, Collections.emptyList());
        defaultAttributes.put(CONTENT_TEMPLATE, DEFAULT_JSP);
        bookstoreService = new BookstoreService();
    }

    /**
     * Рендерит представление для текущего запроса, устанавливая недостающие
     * атрибуты по умолчанию и перенаправляя на базовый JSP.
     *
     * @param req  объект запроса {@link HttpServletRequest}, содержащий данные запроса.
     * @param resp объект ответа {@link HttpServletResponse}, используемый для передачи данных ответа.
     * @throws ServletException если отправка или обработка запроса вызывает ошибку.
     * @throws IOException      если возникает ошибка ввода-вывода.
     */
    protected void renderView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processHeadAttribute(req);
        setMissingDefaultAttributes(req);
        // processSession(req,resp);
        req.getRequestDispatcher(BASE_JSP).forward(req, resp);
    }

    /**
     * Устанавливает недостающие атрибуты по умолчанию в объект запроса.
     *
     * @param req объект запроса {@link HttpServletRequest}, в который добавляются атрибуты.
     */
    private void setMissingDefaultAttributes(HttpServletRequest req) {
        for (Map.Entry<String, Object> entry : defaultAttributes.entrySet()) {
            String attributeName = entry.getKey();
            Object defaultValue = entry.getValue();

            if (req.getAttribute(attributeName) == null) {
                req.setAttribute(attributeName, defaultValue);
            }
        }
    }

    private void processHeadAttribute(HttpServletRequest req) {

        if (req.getAttribute(HEAD) != null) {
            if (req.getAttribute(PAGE_HEAD) == null) {
                req.setAttribute(PAGE_HEAD, Config.getAppName().concat(" / ").concat((String) req.getAttribute(HEAD)));
            }

            if (req.getAttribute(PAGE_TITLE) == null) {
                req.setAttribute(PAGE_TITLE, Config.getAppName().concat(" - ").concat((String) req.getAttribute(HEAD)));
            }
        }
    }


}
