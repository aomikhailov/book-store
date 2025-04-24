-- Удаляем таблицы, если они существуют
DROP TABLE IF EXISTS user_cart;
DROP TABLE IF EXISTS user_session;
DROP TABLE IF EXISTS book_catalog;
DROP TABLE IF EXISTS book_author;
DROP TABLE IF EXISTS app_user;

-- Таблица пользователей (только ID)
CREATE TABLE app_user
(
    user_id    INT AUTO_INCREMENT PRIMARY KEY,
    full_name  VARCHAR(255),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблица сессий пользователей
CREATE TABLE user_session
(
    session_id     INT AUTO_INCREMENT PRIMARY KEY,
    user_id        INT NOT NULL,
    session_token  CHAR(64) NOT NULL UNIQUE,
    created_on     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_on     TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES app_user (user_id)
);


-- Таблица авторов книг
CREATE TABLE book_author
(
    author_id   INT AUTO_INCREMENT PRIMARY KEY,
    last_name   VARCHAR(100) NOT NULL,
    first_name  VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    birth_date  DATE
);

-- Таблица каталога книг
CREATE TABLE book_catalog
(
    book_id   INT AUTO_INCREMENT PRIMARY KEY,
    title     VARCHAR(255) NOT NULL,
    author_id INT NOT NULL,
    price     DECIMAL(10, 2),
    FOREIGN KEY (author_id) REFERENCES book_author (author_id)
);

-- Таблица корзины пользователя
CREATE TABLE user_cart
(
    item_id       INT AUTO_INCREMENT PRIMARY KEY,
    user_id       INT NOT NULL,
    book_id       INT NOT NULL,
    book_quantity INT NOT NULL DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES app_user (user_id),
    FOREIGN KEY (book_id) REFERENCES book_catalog (book_id)
);

-- Данные пользователей
INSERT INTO app_user (full_name)
VALUES ('Иван Иванов'),
       ('Мария Петрова');

-- Пример сессий
INSERT INTO user_session (user_id, session_token, expires_on)
VALUES (1, 'abc1234567890abcdef1234567890abcdef1234567890abcdef1234567890ab', DATEADD('MINUTE', 10, CURRENT_TIMESTAMP)),
    (2, 'def9876543210fedcba9876543210fedcba9876543210fedcba9876543210de', DATEADD('MINUTE', 10, CURRENT_TIMESTAMP));

-- Данные авторов
INSERT INTO book_author (first_name, middle_name, last_name, birth_date)
VALUES ('Александр', 'Сергеевич', 'Пушкин', '1799-06-06'),
       ('Федор', 'Михайлович', 'Достоевский', '1821-11-11'),
       ('Лев', 'Николаевич', 'Толстой', '1828-09-09');

-- Данные книг
INSERT INTO book_catalog (title, author_id, price)
VALUES ('Евгений Онегин', 1, 350.00),
       ('Капитанская дочка', 1, 280.00),
       ('Преступление и наказание', 2, 420.00),
       ('Идиот', 2, 390.00),
       ('Война и мир', 3, 550.00),
       ('Анна Каренина', 3, 480.00);

-- Данные корзины
INSERT INTO user_cart (user_id, book_id, book_quantity)
VALUES (1, 1, 1),
       (1, 3, 2),
       (2, 5, 1),
       (2, 6, 1);
