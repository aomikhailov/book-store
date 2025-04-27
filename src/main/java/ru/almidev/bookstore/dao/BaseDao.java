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

    public Integer getNextAutoIncrementValue() throws SQLException {

        // Приведение имени таблицы к верхнему регистру, чтобы соответствовать H2
        String upperCaseTableName = getTableName().toUpperCase();
        String idFieldName = getIdFieldName().toUpperCase();
        String query = "select max(" + idFieldName + ")+1 as next_auto_increment_value from " + upperCaseTableName;

        List<Map<String, Object>> results = databaseHelper.executeQuery(query);

        if (!results.isEmpty()) {
            Object autoIncrementValue = results.getFirst().get("next_auto_increment_value");
            if (autoIncrementValue != null) {
                return ((Number) autoIncrementValue).intValue();
            }
            else{
                return 1;
            }
        }

        throw new IllegalStateException("Не удалось получить значение AUTO_INCREMENT для поля " + idFieldName + " таблицы: " + upperCaseTableName);
    }


}
