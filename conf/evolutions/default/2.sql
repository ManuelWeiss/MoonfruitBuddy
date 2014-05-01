# add questions and answers

# --- !Ups

CREATE TABLE questions (
    id            varchar(31) PRIMARY KEY,
	text          varchar(1023),
	scale_explain varchar(1023),
	scale_from    double DEFAULT 0.0,
	scale_to      double DEFAULT 10.0
);

INSERT INTO questions (id, text, scale_explain, scale_from, scale_to) VALUES ('test-q1', 'Where would you like to live?', 'From under the sea to the edge of space', 0, 7);
INSERT INTO questions (id, text, scale_explain) VALUES ('test-q2', 'Silly question No. 2', 'not at all - totally');
INSERT INTO questions (id, text, scale_explain) VALUES ('test-q3', 'Silly question No. 3', 'not at all - totally');
INSERT INTO questions (id, text, scale_explain) VALUES ('test-q4', 'Silly question No. 4', 'not at all - totally');
INSERT INTO questions (id, text, scale_explain) VALUES ('test-q5', 'Silly question No. 5', 'not at all - totally');

CREATE TABLE answers (
    user_id     varchar(255) REFERENCES users (id),
    question_id varchar(31) REFERENCES questions (id),
    answer      double,
    UNIQUE      (user_id, question_id)
);

# --- !Downs

DROP TABLE answers;
DROP TABLE questions;
