package ru.almidev.bookstore.controllers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.almidev.bookstore.models.BookCatalog;
import ru.almidev.bookstore.models.UserSession;
import ru.almidev.bookstore.services.BookstoreService;
import ru.almidev.bookstore.services.SessionManagerService;

import java.io.IOException;

import java.util.List;

import static ru.almidev.bookstore.attributes.AttributeNames.*;
import static ru.almidev.bookstore.views.ViewPaths.*;

@WebServlet("/")
public class IndexController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SessionManagerService sessionManagerService = new SessionManagerService(req,resp);
        BookstoreService bookstoreService = new BookstoreService();
        List<BookCatalog> bookCatalogList = bookstoreService.findAllBooks();
        req.setAttribute(HEAD, "Главная страница");
        req.setAttribute(BOOK_CATALOG_LIST, bookCatalogList);
        req.setAttribute(CONTENT_TEMPLATE, INDEX_JSP);

        renderView(req, resp);
    }
}



