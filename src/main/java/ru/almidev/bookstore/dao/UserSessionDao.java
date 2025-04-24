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

    @Override
    public UserSession findById(Integer id) throws SQLException {
        String query = "SELECT * FROM user_session WHERE session_id = ?";
        List<Map<String, Object>> results = databaseHelper.executeQuery(query, id);

        if (!results.isEmpty()) {
            return mapRowToUserSession(results.getFirst());
        }
        return null;
    }

    @Override
    public List<UserSession> findAll() throws SQLException {
        String query = "SELECT * FROM user_session";
        List<Map<String, Object>> results = databaseHelper.executeQuery(query);

        List<UserSession> sessions = new ArrayList<>();
        for (Map<String, Object> row : results) {
            sessions.add(mapRowToUserSession(row));
        }
        return sessions;
    }

    @Override
    public void save(UserSession entity) throws SQLException {
        String query = "INSERT INTO user_session (user_id, session_token, created_on, expires_on) VALUES (?, ?, ?, ?)";
        databaseHelper.executeUpdate(query, entity.getUser().getUserId(), entity.getSessionToken(),
                entity.getCreatedOn(), entity.getExpiresOn());
    }

    @Override
    public void update(UserSession entity) throws SQLException {
        String query = "UPDATE user_session SET user_id = ?, session_token = ?, created_on = ?, expires_on = ? WHERE session_id = ?";
        databaseHelper.executeUpdate(query, entity.getUser().getUserId(), entity.getSessionToken(),
                entity.getCreatedOn(), entity.getExpiresOn(), entity.getSessionId());
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String query = "DELETE FROM user_session WHERE session_id = ?";
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
}