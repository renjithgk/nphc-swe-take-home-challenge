CREATE TABLE IF NOT EXISTS EMPLOYEE (
id varchar(50) NOT NULL PRIMARY KEY,
name varchar(100) NOT NULL,
login varchar(50) UNIQUE NOT NULL,
salary decimal(20, 2) NOT NULL,
start_date varchar(50) NOT NULL,
created_at timestamp NOT NULL,
modified_at timestamp NOT NULL
);