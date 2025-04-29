package ru.almidev.bookstore.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static ru.almidev.bookstore.attributes.AttributeNames.*;
import static ru.almidev.bookstore.views.ViewPaths.ERROR_JSP;

public class ErrorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    private void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Сначала пробуем взять вручную заданные атрибуты
        Integer code = (Integer) req.getAttribute(ERROR_CODE);
        String message = (String) req.getAttribute(ERROR_MESSAGE);
        String title = (String) req.getAttribute(ERROR_TITLE);

        // Если не заданы, пробуем получить из системных атрибутов
        if (code == null) {
            code = (Integer) req.getAttribute("jakarta.servlet.error.status_code");
        }
        if (message == null) {
            message = (String) req.getAttribute("jakarta.servlet.error.message");
        }

        // Устанавливаем в атрибуты для JSP
        req.setAttribute(ERROR_CODE, code);
        req.setAttribute(ERROR_MESSAGE, message);
        req.setAttribute(ERROR_TITLE, "");

        req.getRequestDispatcher(ERROR_JSP).forward(req, resp);
    }
}
