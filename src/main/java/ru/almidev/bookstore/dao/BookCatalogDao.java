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

    private final String TABLE_NAME = "book_catalog";
    private final String ID_FIELD_NAME = "book_id";


    @Override
    public BookCatalog findById(Integer id) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_FIELD_NAME + " = ?";
        List<Map<String, Object>> results = databaseHelper.executeQuery(query, id);

        if (!results.isEmpty()) {
            return mapRowToBookCatalog(results.getFirst());
        }
        return null;
    }

    @Override
    public List<BookCatalog> findAll() throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME;
        List<Map<String, Object>> results = databaseHelper.executeQuery(query);

        List<BookCatalog> books = new ArrayList<>();
        for (Map<String, Object> row : results) {
            books.add(mapRowToBookCatalog(row));
        }
        return books;
    }

    public List<BookCatalog> findAllByBookAuthor(Integer authorId) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE author_id = ?";
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
        if (entity.getBookId() == null) {
            entity.setBookId(getNextAutoIncrementValue());
        }
        String query = "INSERT INTO " + TABLE_NAME + " (book_id, title, author_id, price) VALUES (?, ?, ?, ?)";
        databaseHelper.executeUpdate(query, entity.getBookId(), entity.getTitle(), entity.getAuthor().getAuthorId(), entity.getPrice());
    }

    @Override
    public void update(BookCatalog entity) throws SQLException {
        String query = "UPDATE " + TABLE_NAME + " SET title = ?, author_id = ?, price = ? WHERE " + ID_FIELD_NAME + " = ?";
        databaseHelper.executeUpdate(query, entity.getTitle(), entity.getAuthor().getAuthorId(), entity.getPrice(), entity.getBookId());
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_FIELD_NAME + " = ?";
        databaseHelper.executeUpdate(query, id);
    }

    private BookCatalog mapRowToBookCatalog(Map<String, Object> row) throws SQLException {
        BookCatalog bookCatalog = new BookCatalog();
        bookCatalog.setBookId((Integer) row.get("book_id"));
        bookCatalog.setTitle((String) row.get("title"));
        bookCatalog.setPrice(row.get("price") != null ? Double.parseDouble(row.get("price").toString()) : 0.0);
        BookAuthor bookAuthor = new BookAuthorDao().findById((Integer) row.get("author_id"));
        bookCatalog.setAuthor(bookAuthor);
        return bookCatalog;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdFieldName() {
        return ID_FIELD_NAME;
    }
}