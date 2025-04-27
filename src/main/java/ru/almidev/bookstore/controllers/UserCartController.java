package ru.almidev.bookstore.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.almidev.bookstore.enums.UserCartActionEnum;
import ru.almidev.bookstore.models.AppUser;
import ru.almidev.bookstore.models.BookCatalog;
import ru.almidev.bookstore.services.SessionManagerService;
import ru.almidev.bookstore.services.UserCartService;

import java.io.IOException;

import static ru.almidev.bookstore.attributes.AttributeNames.*;
import static ru.almidev.bookstore.views.ViewPaths.USER_CART_JSP;

@WebServlet("/user/cart")
public class UserCartController extends BaseController {

//    Только для отладки
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        processRequest(req, resp);
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Общий метод для обработки запросов GET и POST.
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionManagerService sessionManagerService = new SessionManagerService(req, resp);
        AppUser appUser = sessionManagerService.getLoggedUserSession().getUser();

        if (appUser == null) {
            throw new RuntimeException("Этот раздел сайта предназначен только для авторизованных пользователей.");
        }

        UserCartService userCartService = new UserCartService(req, appUser);
        UserCartActionEnum action = userCartService.getUserCartActionFromHttpRequest();
        BookCatalog book = userCartService.getBookCatalogFromHttpRequest();

        if (action != null && book != null) {
            if (action == UserCartActionEnum.ADD) {
                userCartService.addBookToUserCart(book);
            } else if (action == UserCartActionEnum.DEL) {
                userCartService.delBookFromUserCart(book);
            }
        }

        req.setAttribute(HEAD, "Корзина");
        req.setAttribute(USER_FULL_NAME, appUser.getFullName());
        req.setAttribute(USER_CART_LIST, userCartService.getUserCartList(appUser));
        req.setAttribute(CONTENT_TEMPLATE, USER_CART_JSP);

        renderView(req, resp);
    }
}


