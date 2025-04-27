package ru.almidev.bookstore.services;

import ru.almidev.bookstore.dao.BookAuthorDao;
import ru.almidev.bookstore.dao.BookCatalogDao;
import ru.almidev.bookstore.dao.UserCartDao;
import ru.almidev.bookstore.models.BookAuthor;
import ru.almidev.bookstore.models.BookCatalog;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class BookstoreService {

    private final BookAuthorDao bookAuthorDao = new BookAuthorDao();
    private final BookCatalogDao bookCatalogDao = new BookCatalogDao();
    private final UserCartDao userCartDao = new UserCartDao();

    /**
     * Извлекает список всех записей каталога книг из базы данных.
     *
     * @return Список объектов {@code BookCatalog}, представляющих все доступные записи каталога,
     * либо пустой список, если произошла ошибка при выполнении запроса.
     */
    public List<BookCatalog> findAllBooks() {
        try {
            return bookCatalogDao.findAll();
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Ищет все книги указанного автора.
     *
     * @param bookAuthor Объект {@code BookAuthor}, представляющий автора книги.
     * @return Список объектов {@code BookCatalog}, соответствующих книгам автора,
     * либо пустой список, если произошла ошибка или книги не найдены.
     */
    public List<BookCatalog> findAllBooksOfAuthor(BookAuthor bookAuthor) {
        try {
            return bookCatalogDao.findAllByBookAuthor(bookAuthor);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Извлекает список всех авторов книг из базы данных.
     *
     * @return Список объектов {@code BookAuthor}, представляющих всех авторов,
     * либо пустой список, если произошла ошибка при выполнении запроса.
     */
    public List<BookAuthor> findAllAuthors() {
        try {
            return bookAuthorDao.findAll();
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Ищет автора книги по его идентификатору.
     *
     * @param AuthorId Идентификатор автора книги.
     * @return Объект {@code BookAuthor}, соответствующий указанному идентификатору,
     * или {@code null}, если автор не найден или произошла ошибка.
     */
    public BookAuthor findAuthor(Integer AuthorId) {
        try {
            return bookAuthorDao.findById(AuthorId);
        } catch (SQLException e) {
            return null;
        }
    }

}
