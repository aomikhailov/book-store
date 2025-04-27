package ru.almidev.bookstore.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.almidev.bookstore.models.AppUser;
import ru.almidev.bookstore.services.SessionManagerService;

import java.io.IOException;

import static ru.almidev.bookstore.attributes.AttributeNames.*;
import static ru.almidev.bookstore.views.ViewPaths.USER_CART_JSP;

@WebServlet("/user/cart")
public class UserCartController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionManagerService sessionManagerService = new SessionManagerService(req, resp);
        AppUser appUser=sessionManagerService.getLoggedUserSession().getUser();

        if (appUser == null) {
            throw  new RuntimeException("Этот раздел сайта предназначен только для авторизованных пользователей.");
        }

        req.setAttribute(HEAD, "Корзина");
        req.setAttribute(USER_FULL_NAME, appUser.getFullName());
        req.setAttribute(USER_CART_LIST, bookstoreService.findAllUserCartByAppUser(appUser));
        req.setAttribute(CONTENT_TEMPLATE, USER_CART_JSP);
        renderView(req, resp);
    }
}



