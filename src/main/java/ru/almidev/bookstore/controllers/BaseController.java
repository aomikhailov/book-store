package ru.almidev.bookstore.controllers;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.almidev.bookstore.config.Config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaseController extends HttpServlet {

    private final Map<String, Object> defaultAttributes;

    public BaseController() {
        defaultAttributes = new HashMap<>();
        defaultAttributes.put("pageTitle", "Заголовок веб-страницы по умолчанию");
        defaultAttributes.put("pageHead", "Заголовок страницы по умолчанию");
        defaultAttributes.put("contentTemplate", "/WEB-INF/views/default.jsp");
    }

    protected void renderView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setMissingDefaultAttributes(req);
        req.getRequestDispatcher(Config.getViewPath() + "base.jsp").forward(req, resp);
    }

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
