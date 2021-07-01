DROP TABLE IF EXISTS POSTS;
DROP TABLE IF EXISTS USER_ROLES;
DROP TABLE IF EXISTS USERS;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE USERS
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    username         VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_username_idx ON users (username);

CREATE TABLE USER_ROLES
(
    user_id         INTEGER NOT NULL,
    role            VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE POSTS
(
    id              INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    title           VARCHAR         NOT NULL,
    body            TEXT            NOT NULL,
    creation_date   TIMESTAMP       NOT NULL,
    user_id         INTEGER         NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);