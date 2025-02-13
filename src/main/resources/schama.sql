DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    user_id       bigserial PRIMARY KEY,
    user_name     VARCHAR(100) NOT NULL,
    user_email    VARCHAR(100) NOT NULL,
    user_password VARCHAR(100) NOT NULL,
    constraint user_email_unique UNIQUE (user_email)
);
CREATE INDEX username_id ON users(user_email);

DROP TABLE IF EXISTS token;

CREATE TABLE token
(
    token_id   bigserial PRIMARY KEY,
    token      VARCHAR(50) NOT NULL,
    token_type VARCHAR(50) NOT NULL,
    revoked    BOOLEAN     NOT NULL,
    expired    BOOLEAN     NOT NULL,
    user_id    bigint REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    constraint token_unique UNIQUE (token)
);

DROP TABLE IF EXISTS flight;

CREATE TABLE flight
(
    flight_id   bigserial PRIMARY KEY,
    flight_code VARCHAR(50) NOT NULL,
    constraint flight_code_unique UNIQUE (flight_code)
);
CREATE
INDEX flight_code_id ON flight(flight_code);

DROP TABLE IF EXISTS seat;

CREATE TABLE seat
(
    seat_id   bigserial PRIMARY KEY,
    flight_id bigint REFERENCES flight (flight_id) ON DELETE CASCADE ON UPDATE CASCADE,
    seat_code VARCHAR(50) NOT NULL,
    available BOOLEAN     NOT NULL,
    constraint flight_id_seat_code_unique UNIQUE (flight_id, seat_code)
);
CREATE
INDEX flight_seat_code_id ON seat(flight_id, seat_code);

DROP TABLE IF EXISTS reservation;

CREATE TABLE reservation
(
    reservation_id bigserial PRIMARY KEY,
    flight_id      bigint REFERENCES flight (flight_id) ON DELETE CASCADE ON UPDATE CASCADE,
    seat_id        bigint REFERENCES seat (seat_id) ON DELETE CASCADE ON UPDATE CASCADE,
    status         VARCHAR(50) NOT NULL,
    creation_at    timestamp without time zone NOT NULL
);