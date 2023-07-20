DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    name  VARCHAR(256)                        NOT NULL,
    email VARCHAR(320)                        NOT NULL,
    UNIQUE (email)
);

CREATE TABLE IF NOT EXIST category
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    name  VARCHAR(255)                        NOT NULL,
    UNIQUE (name)
);

CREATE TABLE if NOT EXIST locations
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    lat   FLOAT                               NOT NULL,
    lon   FLOAT                               NOT NULL
);

