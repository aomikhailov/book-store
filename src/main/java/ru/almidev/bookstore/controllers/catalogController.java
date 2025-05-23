package ru.almidev.bookstore.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.almidev.bookstore.models.BookCatalog;
import ru.almidev.bookstore.services.SessionManagerService;

import java.io.IOException;
import java.util.List;

import static ru.almidev.bookstore.attributes.AttributeNames.*;
import static ru.almidev.bookstore.views.ViewPaths.CATALOG_JSP;


@WebServlet("/catalog")
public class catalogController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SessionManagerService sessionManagerService = new SessionManagerService(req, resp);
        List<BookCatalog> bookCatalogList = bookstoreService.findAllBooks();
        req.setAttribute(HEAD, "Каталог");
        req.setAttribute(BOOK_CATALOG_LIST, bookCatalogList);
        req.setAttribute(CONTENT_TEMPLATE, CATALOG_JSP);
        renderView(req, resp);
    }
}



