DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS documents_grants;
DROP TABLE IF EXISTS documents;
DROP TABLE IF EXISTS users;



-- Table: users
CREATE TABLE users
(
    id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    UNIQUE (username)
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
    id          INT                     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255)            NOT NULL,
    description VARCHAR(255)            NOT NULL,
    key         VARCHAR(255)            NOT NULL,
    author      INT                     NOT NULL,
    created     TIMESTAMP DEFAULT now() NOT NULL,
    FOREIGN KEY (author) REFERENCES users (id)
);

CREATE TABLE documents_grants
(
    id_document INT NOT NULL,
    id_user     INT NOT NULL,
    granted     INT ,
    FOREIGN KEY (id_document) REFERENCES documents (id) ON DELETE CASCADE,
    FOREIGN KEY (id_user) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT document_user UNIQUE (id_document, id_user)
);
-- Insert data

INSERT INTO users
VALUES (1, 'andro1d1', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');
INSERT INTO users
VALUES (2, 'user', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');

INSERT INTO user_roles
VALUES (1, 'ADMIN'),
       (2, 'USER');

INSERT INTO documents (name, description, key, author, created)
VALUES ('файл.doc', 'пример устаревшего MS word', '9d83393e-8086-415f-b832-15f0ba38f5fe', 1, '2020-11-01 14:30:00'),
       ('рецепты.pdf', 'рецепты в pdf', '56eb7f17-8028-4a6f-ac99-ded1fa4a16ea', 1, '2021-02-01 8:30:00'),
       ('список.txt', 'Список покупок', '449c4d25-d149-49c4-ae4c-870c1c940e24', 1, '2019-11-01 22:30:00'),
       ('films.txt', 'Мои любимые фильмы', '3efd25fb-7582-45f5-8d5f-f54e08bcda1a', 2, '2002-11-01 14:30:00'),
       ('List.xls', 'Штатное рассписание', '1d05df03-79d6-43a5-9feb-1cde7342082a', 2, '2017-09-01 10:30:00');

INSERT INTO documents_grants (id_document, id_user, granted)
VALUES (1, 1, 1),
       (2, 1, 1),
       (3, 1, 1),
       (4, 2, 1),
       (5, 2, 1),
       (5, 1, 1);






