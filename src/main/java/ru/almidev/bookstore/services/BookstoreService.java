package ru.almidev.bookstore.services;

import ru.almidev.bookstore.dao.BookCatalogDao;
import ru.almidev.bookstore.models.BookCatalog;

import java.sql.SQLException;
import java.util.List;

public class BookstoreService {

    private final BookCatalogDao bookCatalogDao = new BookCatalogDao();

    public List<BookCatalog> findAllBooks() {
        try {
            return bookCatalogDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
