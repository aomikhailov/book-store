package ru.almidev.bookstore.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.almidev.bookstore.services.SessionManagerService;

import java.io.IOException;

import static ru.almidev.bookstore.attributes.AttributeNames.*;
import static ru.almidev.bookstore.views.ViewPaths.AUTHORS_JSP;


@WebServlet("/authors")
public class AuthorController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionManagerService sessionManagerService = new SessionManagerService(req, resp);
        req.setAttribute(HEAD, "Список авторов книг");
        req.setAttribute(BOOK_AUTHOR_LIST, bookstoreService.findAllAuthors());
        req.setAttribute(CONTENT_TEMPLATE, AUTHORS_JSP);
        renderView(req, resp);
    }
}



