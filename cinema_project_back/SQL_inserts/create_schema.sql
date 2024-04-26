--SELECT table_name, column_name, data_type FROM information_schema.columns WHERE table_name = 'tickets';

----------------------------------- REGISTRATION PART --------------------------------

CREATE TABLE registered_users (
                                  id BIGSERIAL PRIMARY KEY ,
                                  login VARCHAR(60) UNIQUE,
                                  fname VARCHAR(60),
                                  lname VARCHAR(60),
                                  mail VARCHAR(60) UNIQUE ,
                                  password VARCHAR(60),
                                  phone VARCHAR(12) UNIQUE
);

CREATE SEQUENCE registered_users_seq
    INCREMENT BY 1
    START WITH 0
    MINVALUE 0
    OWNED BY registered_users.id;


CREATE TABLE roles (
                       id BIGINT PRIMARY KEY,
                       role VARCHAR(60) UNIQUE
);

CREATE TABLE registered_users_roles (
                                        user_id BIGINT,
                                        role_id BIGINT,
                                        CONSTRAINT fk_role
                                            FOREIGN KEY (role_id)
                                                REFERENCES roles(id),
                                        CONSTRAINT fk_user
                                            FOREIGN KEY (user_id)
                                                REFERENCES registered_users(id),
                                        CONSTRAINT pk_user_role
                                            PRIMARY KEY (user_id, role_id)
);

CREATE TABLE refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    registered_user_id BIGINT,
    token VARCHAR UNIQUE,

    CONSTRAINT fk_registered_user
        FOREIGN KEY (registered_user_id)
            REFERENCES registered_users(id)
);

CREATE SEQUENCE refresh_token_seq
    INCREMENT BY 1
    START WITH 0
    MINVALUE 0
    OWNED BY refresh_tokens.id;




----------------------------------- CINEMA PART --------------------------------

CREATE TABLE movies(
                       id BIGSERIAL PRIMARY KEY,
                       title VARCHAR(120),
                       year INT,
                       length_min INT,
                       description VARCHAR,
                       image_url VARCHAR(255)
);


CREATE TABLE rooms(
                      id BIGINT PRIMARY KEY,
                      number_of_rows INT,
                      number_of_cols INT
);


CREATE TABLE shows(
                      id BIGSERIAL PRIMARY KEY,
                      start_time TIMESTAMP,
                      end_time TIMESTAMP,
                      movie_id BIGINT,
                      room_id BIGINT,

                      CONSTRAINT fk_show_movie
                          FOREIGN KEY (movie_id)
                              REFERENCES movies(id),
                      CONSTRAINT fk_show_room
                          FOREIGN KEY (room_id)
                              REFERENCES rooms(id)
);



CREATE TABLE sits(
                     id BIGSERIAL PRIMARY KEY,
                     order_num INT,
                     room_id BIGINT,
                     CONSTRAINT fk_sit_room
                         FOREIGN KEY (room_id)
                             REFERENCES rooms(id)
);


CREATE TABLE tickets(
                        id BIGSERIAL PRIMARY KEY,
                        show_id BIGINT,
                        sit_id BIGINT,
                        user_id BIGINT,
                        buyer_fname VARCHAR(60),
                        buyer_lname VARCHAR(60),
                        buyer_mail VARCHAR(60),

                        CONSTRAINT fk_ticket_show
                            FOREIGN KEY (show_id)
                                REFERENCES shows(id),
                        CONSTRAINT fk_ticket_sit
                            FOREIGN KEY (sit_id)
                                REFERENCES sits(id)
);

CREATE SEQUENCE tickets_seq
    INCREMENT BY 1
    START WITH 0
    MINVALUE 0
    OWNED BY tickets.id;


CREATE TABLE genres(
                        id BIGSERIAL PRIMARY KEY,
                        genre VARCHAR(60) UNIQUE
);


CREATE TABLE movies_genres(
                        genre_id BIGINT,
                        movie_id BIGINT,

                        CONSTRAINT fk_genre
                            FOREIGN KEY (genre_id)
                                REFERENCES genres(id),
                        CONSTRAINT fk_movie
                            FOREIGN KEY (movie_id)
                                REFERENCES movies(id),
                        CONSTRAINT pk_movie_genre
                            PRIMARY KEY (movie_id, genre_id)
);

CREATE TABLE events(
                id BIGSERIAL PRIMARY KEY,
                title VARCHAR(60) UNIQUE,
                start_date TIMESTAMP,
                description VARCHAR
);