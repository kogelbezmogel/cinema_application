DELETE FROM registered_users_roles;
DELETE FROM refresh_tokens;
DELETE FROM registered_users;
DELETE FROM roles;
DELETE FROM movies_genres;
DELETE FROM tickets;
DELETE FROM sits;
DELETE FROM shows;
DELETE FROM movies;
DELETE FROM rooms;
DELETE FROM genres;
DELETE FROM events;

DROP SEQUENCE registered_users_seq;
DROP TABLE registered_users_roles;
DROP TABLE refresh_tokens;
DROP TABLE registered_users;
DROP TABLE roles;

DROP SEQUENCE tickets_seq;
DROP TABLE movies_genres;
DROP TABLE tickets;
DROP TABLE sits;
DROP TABLE shows;
DROP TABLE movies;
DROP TABLE rooms;
DROP TABLE genres;
DROP TABLE events;