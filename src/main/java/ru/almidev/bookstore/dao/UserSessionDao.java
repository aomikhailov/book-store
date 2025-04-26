package ru.almidev.bookstore.dao;

import ru.almidev.bookstore.models.AppUser;
import ru.almidev.bookstore.models.UserSession;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DAO класс для управления сущностями {@link UserSession}.
 * Выполняет операции создания, чтения, обновления и удаления для таблицы `user_session`.
 */
public class UserSessionDao extends BaseDao<UserSession, Integer> {

    private final String TABLE_NAME = "user_session";
    private final String ID_FIELD_NAME = "session_id";

    @Override
    public UserSession findById(Integer id) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_FIELD_NAME + " = ?";
        List<Map<String, Object>> results = databaseHelper.executeQuery(query, id);

        if (!results.isEmpty()) {
            return mapRowToUserSession(results.getFirst());
        }
        return null;
    }

    public UserSession findBySessionToken(String sessionToken) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE session_token = ?";
        List<Map<String, Object>> results = databaseHelper.executeQuery(query, sessionToken);

        if (!results.isEmpty()) {
            return mapRowToUserSession(results.getFirst());
        }
        return null;
    }

    public UserSession findByUserId(Integer userId) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ?";
        List<Map<String, Object>> results = databaseHelper.executeQuery(query, userId);

        if (!results.isEmpty()) {
            return mapRowToUserSession(results.getFirst());
        }
        return null;
    }

    @Override
    public List<UserSession> findAll() throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME ;
        List<Map<String, Object>> results = databaseHelper.executeQuery(query);

        List<UserSession> sessions = new ArrayList<>();
        for (Map<String, Object> row : results) {
            sessions.add(mapRowToUserSession(row));
        }
        return sessions;
    }

    @Override
    public void save(UserSession entity) throws SQLException {
        if (entity.getSessionId() == null) {
            entity.setSessionId(getNextAutoIncrementValue());
        }
        String query = "INSERT INTO " + TABLE_NAME + " (session_id, user_id, session_token, created_on, expires_on) VALUES (?, ?, ?, ?, ?)";
        databaseHelper.executeUpdate(query, entity.getSessionId(), entity.getUser().getUserId(), entity.getSessionToken(),
                entity.getCreatedOn(), entity.getExpiresOn());
    }

    @Override
    public void update(UserSession entity) throws SQLException {
        String query = "UPDATE " + TABLE_NAME + " SET user_id = ?, session_token = ?, created_on = ?, expires_on = ? WHERE " + ID_FIELD_NAME + " = ?";
        databaseHelper.executeUpdate(query, entity.getUser().getUserId(), entity.getSessionToken(),
                entity.getCreatedOn(), entity.getExpiresOn(), entity.getSessionId());
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_FIELD_NAME + " = ?";
        databaseHelper.executeUpdate(query, id);
    }

    private UserSession mapRowToUserSession(Map<String, Object> row) throws SQLException {
        UserSession session = new UserSession();
        session.setSessionId((Integer) row.get("session_id"));
        session.setSessionToken((String) row.get("session_token"));
        session.setCreatedOn(((Timestamp) row.get("created_on")).toLocalDateTime());
        session.setExpiresOn(((Timestamp) row.get("expires_on")).toLocalDateTime());
        AppUser user = new AppUserDao().findById((Integer) row.get("user_id"));
        session.setUser(user);
        return session;
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