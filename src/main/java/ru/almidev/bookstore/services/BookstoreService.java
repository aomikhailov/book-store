package ru.almidev.bookstore.services;

import ru.almidev.bookstore.dao.BookAuthorDao;
import ru.almidev.bookstore.dao.BookCatalogDao;
import ru.almidev.bookstore.models.BookAuthor;
import ru.almidev.bookstore.models.BookCatalog;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class BookstoreService {

    private final BookAuthorDao bookAuthorDao = new BookAuthorDao();
    private final  BookCatalogDao bookCatalogDao = new BookCatalogDao();

    public List<BookCatalog> findAllBooks() {
         try {
            return bookCatalogDao.findAll();
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    public List<BookCatalog> findAllBooksOfAuthor(BookAuthor bookAuthor) {
        try {
            return bookCatalogDao.findAllByBookAuthor(bookAuthor);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    public List<BookAuthor> findAllAuthors() {
         try {
            return bookAuthorDao.findAll();
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    public BookAuthor findAuthor(Integer AuthorId) {
        try {
            return bookAuthorDao.findById(AuthorId);
        } catch (SQLException e) {
            return null;
        }
    }
}
