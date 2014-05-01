# items schema

# --- !Ups

CREATE TABLE items (
    id varchar(255),
    data varchar(1023) not null,
    priority bigint not null default 0,
    inserted SERIAL
);
CREATE SEQUENCE counter;

# --- !Downs

DROP TABLE items;
DROP SEQUENCE counter;