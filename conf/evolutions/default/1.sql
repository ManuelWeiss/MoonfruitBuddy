# users schema

# --- !Ups

CREATE TABLE users (
    id         varchar(255) unique not null,
    name       varchar(255),
    department varchar(255),
    team       varchar(255)
);

# --- !Downs

DROP TABLE users;