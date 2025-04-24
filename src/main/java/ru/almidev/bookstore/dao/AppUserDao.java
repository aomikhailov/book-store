package ru.almidev.bookstore.dao;

import ru.almidev.bookstore.models.AppUser;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DAO класс для управления сущностями {@link AppUser}.
 * Выполняет операции создания, чтения, обновления и удаления для таблицы `app_user`.
 */
public class AppUserDao extends BaseDao<AppUser, Integer> {

    @Override
    public AppUser findById(Integer id) throws SQLException {
        String query = "SELECT * FROM app_user WHERE user_id = ?";
        List<Map<String, Object>> results = databaseHelper.executeQuery(query, id);

        if (!results.isEmpty()) {
            return mapRowToAppUser(results.getFirst());
        }
        return null;
    }

    @Override
    public List<AppUser> findAll() throws SQLException {
        String query = "SELECT * FROM app_user";
        List<Map<String, Object>> results = databaseHelper.executeQuery(query);

        List<AppUser> users = new ArrayList<>();
        for (Map<String, Object> row : results) {
            users.add(mapRowToAppUser(row));
        }
        return users;
    }

    @Override
    public void save(AppUser entity) throws SQLException {
        String query = "INSERT INTO app_user (full_name, created_on) VALUES (?, ?)";
        databaseHelper.executeUpdate(query, entity.getFullName(), entity.getCreatedOn());
    }

    @Override
    public void update(AppUser entity) throws SQLException {
        String query = "UPDATE app_user SET full_name = ?, created_on = ? WHERE user_id = ?";
        databaseHelper.executeUpdate(query, entity.getFullName(), entity.getCreatedOn(), entity.getUserId());
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String query = "DELETE FROM app_user WHERE user_id = ?";
        databaseHelper.executeUpdate(query, id);
    }

    private AppUser mapRowToAppUser(Map<String, Object> row) {
        AppUser user = new AppUser();
        user.setUserId((Integer) row.get("user_id"));
        user.setFullName((String) row.get("full_name"));
        user.setCreatedOn(((Timestamp) row.get("created_on")).toLocalDateTime());
        return user;
    }
}