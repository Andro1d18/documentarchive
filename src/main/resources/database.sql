DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS documents_grants;
DROP TABLE IF EXISTS documents;
DROP TABLE IF EXISTS VERIFICATION;
DROP TABLE IF EXISTS users;


-- Table: users
CREATE TABLE users
(
    id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    enabled  BOOLEAN,
    UNIQUE (email),
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
    author      INT                     NOT NULL,
    authorName  VARCHAR(255)            NOT NULL,
    created     TIMESTAMP DEFAULT now() NOT NULL,
    FOREIGN KEY (author) REFERENCES users (id)
);

CREATE TABLE documents_grants
(
    document_id INT NOT NULL,
    user_id     INT NOT NULL,
    granted     INT NOT NULL,
    FOREIGN KEY (document_id) REFERENCES documents (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT document_user UNIQUE (document_id, user_id, granted)
);

CREATE TABLE Verification
(
    id      INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    token   VARCHAR(255) NOT NULL,
    user_id INT          NOT NULL,
    date    DATE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
)
