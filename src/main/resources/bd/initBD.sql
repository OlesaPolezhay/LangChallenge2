DROP TABLE IF EXISTS lang_quiz;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    chat_id    INTEGER UNIQUE                NOT NULL,
    name       VARCHAR                       NOT NULL,
    score      INTEGER             DEFAULT 0 NOT NULL,
    high_score INTEGER             DEFAULT 0 NOT NULL,
    bot_state  VARCHAR                       NOT NULL
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
    option3        VARCHAR NOT NULL
);