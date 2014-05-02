# users schema

# --- !Ups

CREATE TABLE users (
    id         varchar(255) unique not null,
    name       varchar(255),
    department varchar(255),
    team       varchar(255)
);

INSERT INTO users (id, name, department, team) VALUES ('test1@moonfruit.com', 'Test user 1', 'Marketing', 'Team 1');
INSERT INTO users (id, name, department, team) VALUES ('test2@moonfruit.com', 'Test user 2', 'Developers', 'Team 2');
INSERT INTO users (id, name, department, team) VALUES ('test3@moonfruit.com', 'Test user 3', 'Product', 'Team 3');
INSERT INTO users (id, name, department, team) VALUES ('test4@moonfruit.com', 'Test user 4', 'Design', 'Team 4');
INSERT INTO users (id, name, department, team) VALUES ('test5@moonfruit.com', 'Test user 5', 'Operations', 'Team 5');

# --- !Downs

DROP TABLE users;