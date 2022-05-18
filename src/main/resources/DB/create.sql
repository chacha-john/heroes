--SET MODE postgreSQL;

CREATE TABLE IF NOT EXISTS heroes (
id int PRIMARY KEY auto_increment,
name VARCHAR,
superpower VARCHAR,
weakness VARCHAR,
age int,
squadId int
);

CREATE TABLE IF NOT EXISTS squads (
id int PRIMARY KEY auto_increment,
maxSize int,
count int,
name VARCHAR,
cause VARCHAR
);