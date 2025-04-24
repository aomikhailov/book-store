package ru.almidev.bookstore.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Базовый интерфейс DAO для выполнения операций с сущностями.
 *
 * @param <T>  тип сущности.
 * @param <ID> тип идентификатора сущности.
 */
public interface GenericDao<T, ID> {
    T findById(ID id) throws SQLException;
    List<T> findAll() throws SQLException;
    void save(T entity) throws SQLException;
    void update(T entity) throws SQLException;
    void deleteById(ID id) throws SQLException;
}