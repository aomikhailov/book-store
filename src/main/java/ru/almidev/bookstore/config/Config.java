package ru.almidev.bookstore.config;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * Класс для работы с переменными конфигурации, загружаемыми из файла .env.
 * Предоставляет методы для получения значений ключевых параметров приложения.
 */
public class Config {
    private static final Dotenv dotenv;

    static {
        try {
            dotenv = Dotenv.configure().load();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка инициализации переменных окружения из файла .env", e);
        }
    }

    public static String getAppName() {
        return dotenv.get("APP_NAME");
    }

    public static String getDbDriver() {
        return dotenv.get("DB_DRIVER");
    }

    public static String getDbUrl() {
        return dotenv.get("DB_URL");
    }

    public static String getDbUser() {
       return dotenv.get("DB_USER");
    }

    public static String getDbPassword() {
        return dotenv.get("DB_PASSWORD");
    }

    public static String getViewPath() {
        return dotenv.get("VIEW_PATH");
    }
}