--DROP TABLE IF EXISTS users, categories, locations, events;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    name  VARCHAR(256)                        NOT NULL,
    email VARCHAR(320)                        NOT NULL,
    UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    name  VARCHAR(255)                        NOT NULL,
    UNIQUE (name)
);

CREATE TABLE if NOT EXISTS locations
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    lat   FLOAT                               NOT NULL,
    lon   FLOAT                               NOT NULL
);

CREATE TABLE if NOT EXISTS events
(
    id                 BIGINT GENERATED ALWAYS AS IDENTITY  NOT NULL PRIMARY KEY,
    annotation         VARCHAR(256)                         NOT NULL,
    category_id        INT                                  NOT NULL,
    confirmed_requests INT                                  NOT NULL,
    created_on         TIMESTAMP WITHOUT TIME ZONE,
    description        VARCHAR(1024)                        NOT NULL,
    event_date         TIMESTAMP WITHOUT TIME ZONE,
    initiator_id       INT                                  NOT NULL,
    location_id        INT                                  NOT NULL,
    paid               BOOLEAN                              NOT NULL,
    participant_limit  INT                                  NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN                              NOT NULL,
    state              VARCHAR(50)                          NOT NULL,
    title              VARCHAR(120)                         NOT NULL,
    views              INT                                  NOT NULL,
    CONSTRAINT fk_categories FOREIGN KEY (category_id)  REFERENCES categories (id),
    CONSTRAINT fk_initiator  FOREIGN KEY (initiator_id) REFERENCES users (id),
    CONSTRAINT fk_locations  FOREIGN KEY (location_id)  REFERENCES locations (id)
);

