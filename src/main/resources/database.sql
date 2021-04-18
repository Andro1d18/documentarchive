DROP TABLE IF EXISTS user_rolesss;
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




CREATE TABLE user_roles
(
    user_id INT NOT NULL,
    role    VARCHAR(100),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE documents
(
    id          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    author      INT          NOT NULL,
    created     TIMESTAMP    DEFAULT now() NOT NULL,
    FOREIGN KEY (author) REFERENCES users (id)
);

-- Insert data

INSERT INTO users
VALUES (1, 'andro1d1', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');
INSERT INTO users
VALUES (2, 'user', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');

INSERT INTO user_roles
VALUES (1, 'ADMIN'),
       (2, 'USER');

INSERT INTO documents (name, description, author, created)
VALUES ('файл.doc', 'пример устаревшего MS word', 1, '2020-11-01 14:30:00'),
       ('рецепты.pdf', 'рецепты в pdf', 1, '2021-02-01 8:30:00'),
       ('список.txt', 'Список покупок', 1, '2019-11-01 22:30:00'),
       ('films.txt', 'Мои любимые фильмы', 2, '2002-11-01 14:30:00'),
       ('List.xls', 'Штатное рассписание', 2, '2017-09-01 10:30:00');






