CREATE TABLE tc_direct_project (
project_id  INTEGER NOT NULL,
name        VARCHAR(200) NOT NULL,
description LVARCHAR(10000),
user_id     INTEGER NOT NULL, -- id of user who creates project
create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
modify_date DATETIME YEAR TO FRACTION(3),
PRIMARY KEY(project_id) --primary key description
);

CREATE TABLE competition (
competition_id INTEGER NOT NULL,
project_id     INTEGER NOT NULL,
PRIMARY KEY(competition_id),
FOREIGN KEY(project_id) REFERENCES tc_direct_project(project_id) -- foreign key to tc_direct_project table
);

CREATE SEQUENCE project_sequence MINVALUE 1 START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE competition_sequence MINVALUE 1 START WITH 1 INCREMENT BY 1;

-- comment if an entry with the same name already exists
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('PHONE_SEQ', 1, 10, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('EMAIL_SEQ', 1, 10, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('ADDRESS_SEQ', 1, 10, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('USER_SEQ', 1, 10, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('USER_GROUP_SEQ', 1, 10, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('SECURITY_SEQ', 1, 10, 0);

-- a default timezone assumed to exist by UserServiceBean#registerUser
-- comment if a timezone with the same timezone_id already exists
INSERT INTO timezone_lu (timezone_id, timezone_desc) VALUES (1, 'Default Timezone');
