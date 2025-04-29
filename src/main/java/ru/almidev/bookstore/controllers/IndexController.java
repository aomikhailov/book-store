package ru.almidev.bookstore.controllers;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.almidev.bookstore.services.SessionManagerService;

import java.io.IOException;
import java.util.regex.Pattern;

import static ru.almidev.bookstore.attributes.AttributeNames.*;
import static ru.almidev.bookstore.views.ViewPaths.INDEX_JSP;

public class IndexController extends BaseController {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // ToDo: исправить этот костыль через конфигурацию
        String requestURI = req.getRequestURI();
        if (!(requestURI.replace("//","/").equals("/"))) {
            req.setAttribute(ERROR_CODE,404);
            req.getRequestDispatcher("/error").forward(req, resp);
            return;
        }

        SessionManagerService sessionManagerService = new SessionManagerService(req, resp);
        req.setAttribute(HEAD, "Главная страница");
        req.setAttribute(CONTENT_TEMPLATE, INDEX_JSP);
        renderView(req, resp);
    }
}



