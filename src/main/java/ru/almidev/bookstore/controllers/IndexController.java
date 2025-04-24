package ru.almidev.bookstore.controllers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.almidev.bookstore.config.Config;
import ru.almidev.bookstore.dao.BookCatalogDao;
import ru.almidev.bookstore.models.BookCatalog;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
public class IndexController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BookCatalogDao bookCatalogDao = new BookCatalogDao();
        List<BookCatalog> bookCatalogList;
        try {
             bookCatalogList = bookCatalogDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("bookCatalogList", bookCatalogList);
//        req.setAttribute("pageTitle", "Главная страница");
//        req.setAttribute("pageHead", "Главная страница");
//        req.setAttribute("contentTemplate", "Главная страница");
//
        req.setAttribute("pageTitle", Config.getAppName());
        req.setAttribute("pageHead", Config.getDbUser()+"["+Config.getDbPassword()+"]");
        req.setAttribute("contentTemplate", Config.getViewPath().concat("index.jsp"));
        renderView(req, resp);
    }
}



