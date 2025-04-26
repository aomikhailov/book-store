package ru.almidev.bookstore.controllers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.almidev.bookstore.dao.BookCatalogDao;
import ru.almidev.bookstore.models.BookCatalog;
import ru.almidev.bookstore.services.BookstoreService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static ru.almidev.bookstore.attributes.AttributeNames.*;
import static ru.almidev.bookstore.views.ViewPaths.*;

@WebServlet("/")
public class IndexController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BookstoreService bookstoreService = new BookstoreService();
        List<BookCatalog> bookCatalogList = bookstoreService.findAllBooks();

        req.setAttribute(HEAD, "Главная страница");
        req.setAttribute(BOOK_CATALOG_LIST, bookCatalogList);
        req.setAttribute(CONTENT_TEMPLATE, INDEX_JSP);
        renderView(req, resp);
    }
}



