-- Создание таблиц, добавление демо-данных

-- Удаляем таблицы, если они существуют
DROP TABLE IF EXISTS cart_item_data;
DROP TABLE IF EXISTS cart_data;
DROP TABLE IF EXISTS book_catalog;
DROP TABLE IF EXISTS book_author;
DROP TABLE IF EXISTS app_user;

-- Создаем таблицу app_user (пользователи приложения)
CREATE TABLE app_user
(
    user_id    INT AUTO_INCREMENT PRIMARY KEY,
    user_name  VARCHAR(100) NOT NULL UNIQUE,
    full_name  VARCHAR(255),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Создаем таблицу book_author (авторы книг)
CREATE TABLE book_author
(
    author_id   INT AUTO_INCREMENT PRIMARY KEY,
    last_name   VARCHAR(100) NOT NULL,
    first_name  VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    birth_date  DATE
);

-- Создаем таблицу book_catalog (книги)
CREATE TABLE book_catalog
(
    book_id   INT AUTO_INCREMENT PRIMARY KEY,
    title     VARCHAR(255) NOT NULL,
    author_id INT          NOT NULL,
    price     DECIMAL(10, 2),
    FOREIGN KEY (author_id) REFERENCES book_author (author_id)
);

-- Создаем таблицу cart_data (корзины пользователей)
CREATE TABLE cart_data
(
    cart_id    INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES app_user (user_id)
);

-- Создаем таблицу cart_item_data (содержимое корзин)
CREATE TABLE cart_item_data
(
    item_id       INT AUTO_INCREMENT PRIMARY KEY,
    cart_id       INT NOT NULL,
    book_id       INT NOT NULL,
    book_quantity INT NOT NULL DEFAULT 1,
    FOREIGN KEY (cart_id) REFERENCES cart_data (cart_id),
    FOREIGN KEY (book_id) REFERENCES book_catalog (book_id)
);

-- Добавляем демонстрационные данные для таблицы app_user
INSERT INTO app_user (user_name, full_name)
VALUES ('user1', 'Иван Иванов'),
       ('user2', 'Мария Петрова');

-- Добавляем демонстрационные данные для таблицы book_author
INSERT INTO book_author (first_name, middle_name, last_name, birth_date)
VALUES ('Александр', 'Сергеевич', 'Пушкин', '1799-06-06'),
       ('Федор', 'Михайлович', 'Достоевский', '1821-11-11'),
       ('Лев', 'Николаевич', 'Толстой', '1828-09-09');

-- Добавляем демонстрационные данные для таблицы book_catalog
INSERT INTO book_catalog (title, author_id, price)
VALUES ('Евгений Онегин', 1, 350.00),
       ('Капитанская дочка', 1, 280.00),
       ('Преступление и наказание', 2, 420.00),
       ('Идиот', 2, 390.00),
       ('Война и мир', 3, 550.00),
       ('Анна Каренина', 3, 480.00);

-- Добавляем демонстрационные данные для таблицы cart_data
INSERT INTO cart_data (user_id, created_on)
VALUES (1, CURRENT_TIMESTAMP),
       (2, CURRENT_TIMESTAMP);

-- Добавляем демонстрационные данные для таблицы cart_item_data
INSERT INTO cart_item_data (cart_id, book_id, book_quantity)
VALUES (1, 1, 1),
       (1, 3, 2),
       (2, 5, 1),
       (2, 6, 1);

