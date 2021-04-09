DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS documents;
DROP TABLE IF EXISTS users;


-- Table: users
CREATE TABLE users
(
    id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,

    UNIQUE  (username)
);


-- Table: roles
CREATE TABLE roles
(
    id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);


-- Table for mapping user and roles: user_roles
CREATE TABLE user_roles
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id),

    UNIQUE (user_id, role_id)
);

CREATE TABLE documents
(
    id          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    author      INT          NOT NULL,
    FOREIGN KEY (author) REFERENCES users (id)
);

-- Insert data

INSERT INTO users
VALUES (1, 'andro1d1', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');
INSERT INTO users
VALUES (2, 'user', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');

INSERT INTO roles
VALUES (1, 'ROLE_USER');
INSERT INTO roles
VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_roles
VALUES (1, 2);
INSERT INTO user_roles
VALUES (2, 1);

INSERT INTO documents (name, description, author)
VALUES ('файл.doc', 'пример устаревшего MS word', 1),
       ('рецепты.pdf', 'рецепты в pdf', 1),
       ('список.txt', 'Список покупок', 1),
       ('films.txt', 'Мои любимые фильмы', 2),
       ('List.xls', 'Штатное рассписание', 2);






