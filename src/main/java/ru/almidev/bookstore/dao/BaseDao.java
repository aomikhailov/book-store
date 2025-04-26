package ru.almidev.bookstore.dao;
import ru.almidev.bookstore.helpers.DatabaseHelper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Абстрактный класс для базовой реализации DAO.
 *
 * @param <T>  тип сущности.
 * @param <ID> тип идентификатора сущности.
 */
public abstract class BaseDao<T, ID> implements GenericDao<T, ID> {
    protected final DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    public Integer getNextAutoIncrementValue(String tableName) throws SQLException, SQLException {

        // Приведение имени таблицы к верхнему регистру, чтобы соответствовать H2
        String upperCaseTableName = tableName.toUpperCase();
        String query = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";

        List<Map<String, Object>> results = databaseHelper.executeQuery(query, upperCaseTableName);

        if (!results.isEmpty()) {
            Object autoIncrementValue = results.getFirst().get("AUTO_INCREMENT");
            if (autoIncrementValue != null) {
                return ((Number) autoIncrementValue).intValue();
            }
        }

        throw new IllegalStateException(
                "Не удалось получить значение AUTO_INCREMENT для таблицы: " + tableName
        );
    }

}
