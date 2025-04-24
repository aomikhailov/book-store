package ru.almidev.bookstore.helpers;

import ru.almidev.bookstore.config.Config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс DatabaseHelper предоставляет удобный интерфейс для работы с базой данных,
 * реализуя паттерн Singleton для обеспечения единственного экземпляра подключения к базе данных.
 * Он обеспечивает выполнение SQL-запросов и управление соединением с использованием
 * try-with-resources.
 */
public class DatabaseHelper {

    private final Connection connection;

    // Приватный конструктор для инициализации соединения
    private DatabaseHelper() throws SQLException {
        connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
    }

    /**
     * Реализация паттерна Singleton с использованием метода Билла Пью.
     * Вложенный статический класс обеспечивает ленивую загрузку.
     */
    private static class DatabaseHelperHolder {
        private static final DatabaseHelper INSTANCE;

        static {
            try {
                INSTANCE = new DatabaseHelper();
            } catch (SQLException e) {
                throw new ExceptionInInitializerError("Ошибка создания экземпляра DatabaseHelper: " + e.getMessage());
            }
        }
    }

    /**
     * Возвращает единственный экземпляр класса {@code DatabaseHelper}.
     */
    public static DatabaseHelper getInstance() {
        return DatabaseHelperHolder.INSTANCE;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeUpdate(String query) throws SQLException {
        executeUpdate(query, new Object[0]);
    }

    public void executeUpdate(String query, Object... parameters) throws SQLException {
        validateConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]); // Индексы в SQL начинаются с 1
            }
            preparedStatement.executeUpdate();
        }
    }

    public List<Map<String, Object>> executeQuery(String query, Object... parameters) throws SQLException {
        validateConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]); // Индексы начинаются с 1
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Map<String, Object>> results = new ArrayList<>();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(metaData.getColumnName(i), resultSet.getObject(i));
                    }
                    results.add(row);
                }
                return results;
            }
        }
    }

    public List<Map<String, Object>> executeQuery(String query) throws SQLException {
        return executeQuery(query, new Object[0]);
    }

    public List<Map<String, Object>> executeQueryFromFile(String filename, Object... parameters) throws SQLException, IOException {
        String query = loadSQL(filename);
        return executeQuery(query, parameters);
    }

    public List<Map<String, Object>> executeQueryFromFile(String filename) throws SQLException, IOException {
        String query = loadSQL(filename);
        return executeQuery(query);
    }

    private String loadSQL(String filename) throws IOException {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя файла SQL не может быть null или пустым");
        }
        ClassLoader classLoader = DatabaseHelper.class.getClassLoader();
        try (InputStream input = classLoader.getResourceAsStream("sql/" + filename)) {
            if (input == null) {
                throw new FileNotFoundException("SQL файл не найден: " + filename);
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private void validateConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Соединение с базой данных закрыто!");
        }
    }

}


