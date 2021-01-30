CREATE TABLE IF NOT EXISTS SALARY (
id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
name varchar(50) NOT NULL,
login varchar(50) NOT NULL,
salary decimal(20, 5) NOT NULL,
start_date varchar(50) NOT NULL,
created_at timestamp NOT NULL,
modified_at timestamp NOT NULL,
version INT
);