package ru.almidev.bookstore.dao;

import ru.almidev.bookstore.models.BookAuthor;
import ru.almidev.bookstore.models.BookCatalog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DAO класс для управления сущностями {@link BookCatalog}.
 * Выполняет операции создания, чтения, обновления и удаления для таблицы `book_catalog`.
 */
public class BookCatalogDao extends BaseDao<BookCatalog, Integer> {

    @Override
    public BookCatalog findById(Integer id) throws SQLException {
        String query = "SELECT * FROM book_catalog WHERE book_id = ?";
        List<Map<String, Object>> results = databaseHelper.executeQuery(query, id);

        if (!results.isEmpty()) {
            return mapRowToBookCatalog(results.getFirst());
        }
        return null;
    }

    @Override
    public List<BookCatalog> findAll() throws SQLException {
        String query = "SELECT * FROM book_catalog";
        List<Map<String, Object>> results = databaseHelper.executeQuery(query);

        List<BookCatalog> books = new ArrayList<>();
        for (Map<String, Object> row : results) {
            books.add(mapRowToBookCatalog(row));
        }
        return books;
    }

    public List<BookCatalog> findAllByBookAuthor(Integer authorId) throws SQLException {
        String query = "SELECT * FROM book_catalog WHERE author_id = ?";
        List<Map<String, Object>> results = databaseHelper.executeQuery(query, authorId);

        List<BookCatalog> books = new ArrayList<>();
        for (Map<String, Object> row : results) {
            books.add(mapRowToBookCatalog(row));
        }
        return books;
    }

    public List<BookCatalog> findAllByBookAuthor(BookAuthor bookAuthor) throws SQLException {
        return findAllByBookAuthor(bookAuthor.getAuthorId());
    }

    @Override
    public void save(BookCatalog entity) throws SQLException {
        String query = "INSERT INTO book_catalog (title, author_id, price) VALUES (?, ?, ?)";
        databaseHelper.executeUpdate(query, entity.getTitle(), entity.getAuthor().getAuthorId(), entity.getPrice());
    }

    @Override
    public void update(BookCatalog entity) throws SQLException {
        String query = "UPDATE book_catalog SET title = ?, author_id = ?, price = ? WHERE book_id = ?";
        databaseHelper.executeUpdate(query, entity.getTitle(), entity.getAuthor().getAuthorId(), entity.getPrice(), entity.getBookId());
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String query = "DELETE FROM book_catalog WHERE book_id = ?";
        databaseHelper.executeUpdate(query, id);
    }

    private BookCatalog mapRowToBookCatalog(Map<String, Object> row) throws SQLException {
        BookCatalog bookCatalog = new BookCatalog();
        bookCatalog.setBookId((Integer) row.get("book_id"));
        bookCatalog.setTitle((String) row.get("title"));
        bookCatalog.setPrice( row.get("price") != null ? Double.parseDouble(row.get("price").toString()) : 0.0);
        BookAuthor bookAuthor = new BookAuthorDao().findById((Integer) row.get("author_id"));
        bookCatalog.setAuthor(bookAuthor);
        return bookCatalog;
    }
}