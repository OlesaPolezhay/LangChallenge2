DROP TABLE IF EXISTS lang_quiz;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    chat_id    INTEGER UNIQUE                NOT NULL,
    name       VARCHAR                       NOT NULL,
    score      INTEGER             DEFAULT 0 NOT NULL,
    high_score INTEGER             DEFAULT 0 NOT NULL,
    bot_state  VARCHAR                       NOT NULL,
    dayNumber      INTEGER DEFAULT 0,
    questionNumber INTEGER DEFAULT 0
);

CREATE TABLE lang_quiz
(
    id             SERIAL PRIMARY KEY,
    question       VARCHAR NOT NULL,
    picture        VARCHAR ,
    sticker        VARCHAR ,
    answer_correct VARCHAR NOT NULL,
    option1        VARCHAR NOT NULL,
    option2        VARCHAR NOT NULL,
    option3        VARCHAR NOT NULL,
    day_number      INTEGER DEFAULT 0,
    question_number INTEGER DEFAULT 0,
    unique (day_number, question_number)
);

CREATE TABLE theory
(
	dayNumber      INTEGER NOT NULL,
	theory        VARCHAR NOT NULL,
	msgNumber      INTEGER NOT NULL,
	UNIQUE(dayNumber, theory, msgNumber)
)

