CREATE TABLE id_sequences (
	name			VARCHAR(255) NOT NULL,
				PRIMARY KEY (name),
	next_block_start	INT8 NOT NULL,
	block_size		INT NOT NULL,
	exhausted		INT default 0
);
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('team_manager_id_generator', 1, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('team_id_seq', 1, 20);
INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('team_manager_id_generator', 1, 20);
