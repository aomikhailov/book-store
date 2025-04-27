package ru.almidev.bookstore.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.almidev.bookstore.models.AppUser;
import ru.almidev.bookstore.services.SessionManagerService;
import ru.almidev.bookstore.services.UserLoginService;

import java.io.IOException;

import static ru.almidev.bookstore.attributes.AttributeNames.CONTENT_TEMPLATE;
import static ru.almidev.bookstore.attributes.AttributeNames.HEAD;
import static ru.almidev.bookstore.views.ViewPaths.USER_LOGIN_FORM_JSP;
import static ru.almidev.bookstore.views.ViewPaths.USER_LOGIN_SUCCESS_JSP;

@WebServlet("/user/login")
public class UserLoginController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionManagerService sessionManagerService = new SessionManagerService(req, resp);
        req.setAttribute(HEAD, "Учётная запись");
        req.setAttribute(CONTENT_TEMPLATE, USER_LOGIN_FORM_JSP);
        renderView(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionManagerService sessionManagerService = new SessionManagerService(req, resp);
        UserLoginService userLoginService = new UserLoginService(req);
        AppUser appUser = userLoginService.processLoginRequest();
        req.setAttribute(HEAD, "Учётная запись");

        if (appUser != null) {
            sessionManagerService.createUserSession(appUser);
            req.setAttribute(CONTENT_TEMPLATE, USER_LOGIN_SUCCESS_JSP);
        }
        else {
            req.setAttribute(CONTENT_TEMPLATE, USER_LOGIN_FORM_JSP);
        }
        renderView(req, resp);
    }
}


