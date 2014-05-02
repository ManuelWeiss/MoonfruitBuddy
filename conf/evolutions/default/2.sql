# add questions and answers

# --- !Ups

CREATE TABLE questions (
    id            varchar(31) PRIMARY KEY,
	text          varchar(1023),
	scale_explain varchar(1023),
	scale_from    double DEFAULT 0.0,
	scale_to      double DEFAULT 10.0
);

INSERT INTO questions (id, text, scale_explain, scale_from, scale_to)
		VALUES ('test-q1', 'Where would you like to live?', 'From under the sea to the edge of space', 0, 7);
INSERT INTO questions (id, text, scale_explain)
		VALUES ('test-q2', 'Silly question No. 2', 'not at all - totally');
INSERT INTO questions (id, text, scale_explain)
		VALUES ('test-q3', 'Silly question No. 3', 'not at all - totally');
INSERT INTO questions (id, text, scale_explain)
		VALUES ('test-q4', 'Silly question No. 4', 'not at all - totally');
INSERT INTO questions (id, text, scale_explain)
		VALUES ('test-q5', 'Silly question No. 5', 'not at all - totally');
INSERT INTO questions (id, text, scale_explain)
		VALUES ('test-q6', 'Silly question No. 6', 'not at all - totally');
INSERT INTO questions (id, text, scale_explain)
		VALUES ('test-q7', 'Silly question No. 7', 'not at all - totally');

CREATE TABLE answers (
    user_id     varchar(255) REFERENCES users (id),
    question_id varchar(31) REFERENCES questions (id),
    answer      double,
    UNIQUE      (user_id, question_id)
);

INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test1@moonfruit.com', 'test-q1', 1.2);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test1@moonfruit.com', 'test-q2', 2.5);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test1@moonfruit.com', 'test-q3', 0.1);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test1@moonfruit.com', 'test-q4', 10.0);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test1@moonfruit.com', 'test-q5', 6.66);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test1@moonfruit.com', 'test-q6', 0.0);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test1@moonfruit.com', 'test-q7', 5.0);

INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test2@moonfruit.com', 'test-q1', 1.2);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test2@moonfruit.com', 'test-q2', 3.0);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test2@moonfruit.com', 'test-q3', 0.1);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test2@moonfruit.com', 'test-q4', 10.0);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test2@moonfruit.com', 'test-q5', 7.01);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test2@moonfruit.com', 'test-q6', 0.0);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test2@moonfruit.com', 'test-q7', 5.0);

INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test3@moonfruit.com', 'test-q1', 5.0);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test3@moonfruit.com', 'test-q2', 12.0);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test3@moonfruit.com', 'test-q3', 0.0);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test3@moonfruit.com', 'test-q4', 3.2);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test3@moonfruit.com', 'test-q5', 9.0);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test3@moonfruit.com', 'test-q6', 10.0);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test3@moonfruit.com', 'test-q7', 7.0);

INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test4@moonfruit.com', 'test-q1', 5.1);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test4@moonfruit.com', 'test-q2', 11.0);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test4@moonfruit.com', 'test-q3', 0.5);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test4@moonfruit.com', 'test-q4', 2.9);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test4@moonfruit.com', 'test-q5', 8.2);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test4@moonfruit.com', 'test-q6', 10.0);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test4@moonfruit.com', 'test-q7', 4.0);

INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test5@moonfruit.com', 'test-q1', 5.4);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test5@moonfruit.com', 'test-q2', 10.0);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test5@moonfruit.com', 'test-q3', 0.7);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test5@moonfruit.com', 'test-q4', 2.5);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test5@moonfruit.com', 'test-q5', 8.0);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test5@moonfruit.com', 'test-q6', 11.0);
INSERT INTO answers (user_id, question_id, answer)
		VALUES ('test5@moonfruit.com', 'test-q7', 5.0);

# --- !Downs

DROP TABLE answers;
DROP TABLE questions;
