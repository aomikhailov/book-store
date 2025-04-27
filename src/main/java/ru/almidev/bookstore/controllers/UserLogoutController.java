package ru.almidev.bookstore.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.almidev.bookstore.services.SessionManagerService;

import java.io.IOException;

import static ru.almidev.bookstore.attributes.AttributeNames.CONTENT_TEMPLATE;
import static ru.almidev.bookstore.attributes.AttributeNames.HEAD;
import static ru.almidev.bookstore.views.ViewPaths.USER_LOGOUT_JSP;

@WebServlet("/user/logout")
public class UserLogoutController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionManagerService sessionManagerService = new SessionManagerService(req, resp);
        req.setAttribute(HEAD, "Учётная запись");
        sessionManagerService.deleteUserSession();
        req.setAttribute(CONTENT_TEMPLATE, USER_LOGOUT_JSP);
        renderView(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionManagerService sessionManagerService = new SessionManagerService(req, resp);
        req.setAttribute(HEAD, "Учётная запись");
        sessionManagerService.deleteUserSession();
        req.setAttribute(CONTENT_TEMPLATE, USER_LOGOUT_JSP);
        renderView(req, resp);
    }
}


