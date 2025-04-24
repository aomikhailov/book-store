package ru.almidev.bookstore.dao;
import ru.almidev.bookstore.helpers.DatabaseHelper;

/**
 * Абстрактный класс для базовой реализации DAO.
 *
 * @param <T>  тип сущности.
 * @param <ID> тип идентификатора сущности.
 */
public abstract class BaseDao<T, ID> implements GenericDao<T, ID> {
    protected final DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
}
