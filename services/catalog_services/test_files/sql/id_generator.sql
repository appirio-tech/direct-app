CREATE TABLE id_sequences (
    name VARCHAR(255) NOT NULL, PRIMARY KEY (name),
    next_block_start DECIMAL(12,0) NOT NULL,
    block_size INT NOT NULL,
    exhausted DECIMAL(1,0) default 0
);

-- create required sequences
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('COMPONENT_SEQ', 1, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('COMPVERSION_SEQ', 1, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('COMPVERSIONDATES_SEQ', 1, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('TECHNOLOGY_SEQ', 1, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('PHASE_SEQ', 100, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('COMPLINK_SEQ', 1, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('CATEGORY_SEQ', 1, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('COMPFORUM_SEQ', 1, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('COMPTECH_SEQ', 1, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('COMPCATEGORY_SEQ', 1, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('COMPDOCUMENT_SEQ', 1, 20);