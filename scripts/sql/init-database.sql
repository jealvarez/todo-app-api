USE mysql;

DROP SCHEMA IF EXISTS todoapp;

CREATE SCHEMA IF NOT EXISTS todoapp
    DEFAULT CHARACTER SET utf8mb4;

DROP USER IF EXISTS 'todoapp'@'localhost';
DROP USER IF EXISTS 'todoapp'@'%';

CREATE USER IF NOT EXISTS 'todoapp'@'localhost'
    IDENTIFIED BY 'todoapp';

CREATE USER IF NOT EXISTS 'todoapp'@'%'
    IDENTIFIED BY 'todoapp';

GRANT ALL PRIVILEGES ON todoapp.* TO 'todoapp'@'localhost';

GRANT ALL PRIVILEGES ON todoapp.* TO 'todoapp'@'%';

FLUSH PRIVILEGES;
