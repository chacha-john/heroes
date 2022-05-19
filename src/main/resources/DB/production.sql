create DATABASE chachaup;
\c chachaup;
create TABLE IF NOT EXISTS heroes (id SERIAL PRIMARY KEY, name VARCHAR, superpower VARCHAR, weakness VARCHAR, age int, squadId int );
create TABLE IF NOT EXISTS squads (id SERIAL PRIMARY KEY, maxSize int, count int, name VARCHAR, cause VARCHAR);
CREATE DATABASE chachaup_test;