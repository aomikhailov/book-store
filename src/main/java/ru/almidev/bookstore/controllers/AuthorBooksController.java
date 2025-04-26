package ru.almidev.bookstore.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.almidev.bookstore.models.BookAuthor;
import ru.almidev.bookstore.services.SessionManagerService;

import java.io.IOException;

import static ru.almidev.bookstore.attributes.AttributeNames.*;
import static ru.almidev.bookstore.views.ViewPaths.AUTHOR_BOOKS_JSP;

@WebServlet("/author/books")
public class AuthorBooksController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean authorNotFound = false;
        SessionManagerService sessionManagerService = new SessionManagerService(req, resp);
        BookAuthor bookAuthor = null;

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            bookAuthor = bookstoreService.findAuthor(id);
            if (bookAuthor == null) {
                authorNotFound = true;
            }
        } catch (NumberFormatException | NullPointerException e) {
            authorNotFound = true;
        }

        if (authorNotFound) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        req.setAttribute(HEAD, "Книги по авторам");
        req.setAttribute(AUTHOR_FIO,  bookAuthor.getFio());
        req.setAttribute(BOOK_CATALOG_LIST, bookstoreService.findAllBooksOfAuthor(bookAuthor));
        req.setAttribute(CONTENT_TEMPLATE, AUTHOR_BOOKS_JSP);
        renderView(req, resp);
    }
}



