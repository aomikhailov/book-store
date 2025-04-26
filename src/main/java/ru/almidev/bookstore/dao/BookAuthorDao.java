package ru.almidev.bookstore.dao;

import ru.almidev.bookstore.models.BookAuthor;

import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DAO класс для управления сущностями {@link BookAuthor}.
 * Выполняет операции создания, чтения, обновления и удаления для таблицы `book_author`.
 */
public class BookAuthorDao extends BaseDao<BookAuthor, Integer> {

    private final String TABLE_NAME = "book_author";
    private final String ID_FIELD_NAME = "author_id";


    @Override
    public BookAuthor findById(Integer id) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_FIELD_NAME + " = ?";
        List<Map<String, Object>> results = databaseHelper.executeQuery(query, id);

        if (!results.isEmpty()) {
            return mapRowToBookAuthor(results.getFirst());
        }
        return null;
    }

    @Override
    public List<BookAuthor> findAll() throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME;
        List<Map<String, Object>> results = databaseHelper.executeQuery(query);

        List<BookAuthor> authors = new ArrayList<>();
        for (Map<String, Object> row : results) {
            authors.add(mapRowToBookAuthor(row));
        }
        return authors;
    }

    @Override
    public void save(BookAuthor entity) throws SQLException {
        if (entity.getAuthorId() == null) {
            entity.setAuthorId(getNextAutoIncrementValue());
        }
        String query = "INSERT INTO " + TABLE_NAME + " (author_id, last_name, first_name, middle_name, birth_date) VALUES (?, ?, ?, ?, ?)";
        databaseHelper.executeUpdate(query, entity.getAuthorId(), entity.getLastName(), entity.getFirstName(), entity.getMiddleName(), entity.getBirthDate());
    }

    @Override
    public void update(BookAuthor entity) throws SQLException {
        String query = "UPDATE " + TABLE_NAME + " SET last_name = ?, first_name = ?, middle_name = ?, birth_date = ? WHERE " + ID_FIELD_NAME + " = ?";
        databaseHelper.executeUpdate(query, entity.getLastName(), entity.getFirstName(), entity.getMiddleName(), entity.getBirthDate(), entity.getAuthorId());
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_FIELD_NAME + " = ?";
        databaseHelper.executeUpdate(query, id);
    }

    private BookAuthor mapRowToBookAuthor(Map<String, Object> row) {
        BookAuthor author = new BookAuthor();
        author.setAuthorId((Integer) row.get("author_id"));
        author.setLastName((String) row.get("last_name"));
        author.setFirstName((String) row.get("first_name"));
        author.setMiddleName((String) row.get("middle_name"));
        author.setBirthDate((Date) row.get("birth_date"));
        return author;
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