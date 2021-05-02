-- Insert data

DELETE FROM  user_roles;
DELETE FROM  documents_grants;
DELETE FROM  documents;
DELETE FROM  users;

ALTER TABLE DOCUMENTS ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 1;

INSERT INTO users
VALUES (1, 'andro1d1', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');
INSERT INTO users
VALUES (2, 'user', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');

INSERT INTO user_roles
VALUES (1, 'ADMIN'),
       (2, 'USER');

INSERT INTO documents (name, description, author, AUTHORNAME, created)
VALUES ('Жезлов Андрей (1).pdf', 'Резюме', 1, 'andro1d1', '2020-11-01 14:30:00'),
       ('Spring_in_Action_5th_Edition.pdf', 'Книга',  1, 'andro1d1', '2021-02-01 8:30:00'),
       ('SpringSecurityApp-master.zip', 'Архив',  1, 'andro1d1', '2019-11-01 22:30:00'),
       ('error.log', 'Лог ошибки', 2, 'user','2002-11-01 14:30:00'),
       ('doc-Прилож1-ЗАЯВЛЕНИЕ+О+ДОСТАВКЕ+ПЕНСИИ.docx', 'Штатное рассписание', 2, 'user','2017-09-01 10:30:00');

INSERT INTO documents_grants (DOCUMENT_ID, USER_ID, granted)
VALUES (1, 1, 12),
       (2, 1, 13),
       (3, 1, 14),
       (4, 2, 15),
       (5, 2, 16),
       (5, 1, 17);






