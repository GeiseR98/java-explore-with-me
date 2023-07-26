--DROP TABLE IF EXISTS users, categories, locations, events, compilations, compilations_events CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    name  VARCHAR(250)                        NOT NULL,
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
    annotation         VARCHAR(2000)                        NOT NULL,
    category_id        BIGINT                               NOT NULL,
    confirmed_requests INT                                  NOT NULL,
    created_on         TIMESTAMP WITHOUT TIME ZONE,
    description        VARCHAR(7000)                        NOT NULL,
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

CREATE TABLE IF NOT EXISTS compilations
(
    id     INT GENERATED ALWAYS AS IDENTITY     NOT NULL PRIMARY KEY,
    pinned BOOLEAN                              NOT NULL,
    title  VARCHAR(1024)                        NOT NULL,
);

CREATE TABLE IF NOT EXISTS compilations_events
(
    compilations_id INTEGER REFERENCES compilations (id) ON DELETE CASCADE,
    events_id       INTEGER REFERENCES events (id)       ON DELETE CASCADE,
    PRIMARY KEY (compilations_id, events_id)
);

