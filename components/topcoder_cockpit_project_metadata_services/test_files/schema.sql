DROP TABLE audit_action_type_lu;
DROP TABLE tc_direct_project;
DROP TABLE direct_project_metadata;
DROP TABLE direct_project_metadata_predefined_value;
DROP TABLE direct_project_metadata_key;
DROP TABLE direct_project_metadata_audit;
DROP TABLE direct_project_metadata_predefined_value_audit;
DROP TABLE direct_project_metadata_key_audit;

CREATE TABLE audit_action_type_lu (
	audit_action_type_id INTEGER NOT NULL,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(50) NOT NULL,
	create_user VARCHAR(64) NOT NULL,
	create_date DATETIME YEAR TO FRACTION(3) NOT NULL,
	modify_user VARCHAR(64) NOT NULL,
	modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,
	PRIMARY KEY (audit_action_type_id) CONSTRAINT audit_action_type_lu_pkey );

CREATE TABLE direct_project_metadata (
	id SERIAL NOT NULL,
	tc_direct_project_id DECIMAL(10,0) NOT NULL,
	project_metadata_key_id INTEGER NOT NULL,
	metadata_value LVARCHAR(500) NOT NULL,
	PRIMARY KEY (id) );

CREATE TABLE direct_project_metadata_audit (
	id DECIMAL(10,0) NOT NULL,
	project_metadata_id DECIMAL(10,0) NOT NULL,
	tc_direct_project_id DECIMAL(10,0) NOT NULL,
	project_metadata_key_id DECIMAL(10,0) NOT NULL,
	metadata_value LVARCHAR(500) NOT NULL,
	audit_action_type_id INTEGER NOT NULL,
	action_date DATETIME YEAR TO FRACTION(3) NOT NULL,
	action_user_id DECIMAL(10,0) NOT NULL );

CREATE TABLE direct_project_metadata_key (
	id SERIAL NOT NULL, name VARCHAR(45) NOT NULL,
	description VARCHAR(255),
	grouping CHAR(1),
	client_id DECIMAL(10,0),
	single CHAR(1) NOT NULL,
	PRIMARY KEY (id) );

CREATE TABLE direct_project_metadata_key_audit (
	id DECIMAL(10,0) NOT NULL,
	project_metadata_key_id DECIMAL(10,0) NOT NULL,
	name VARCHAR(45) NOT NULL,
	description VARCHAR(255),
	grouping CHAR(1),
	client_id DECIMAL(10,0),
	audit_action_type_id INTEGER NOT NULL,
	action_date DATETIME YEAR TO FRACTION(3) NOT NULL,
	action_user_id DECIMAL(12,0) NOT NULL,
	single CHAR(1) NOT NULL );

CREATE TABLE direct_project_metadata_predefined_value (
	id SERIAL NOT NULL,
	project_metadata_key_id INTEGER NOT NULL,
	predefined_metadata_value LVARCHAR(500) NOT NULL,
	position INTEGER NOT NULL,
	list_order INTEGER NOT NULL ,
	PRIMARY KEY (id) );

CREATE TABLE direct_project_metadata_predefined_value_audit (
	id DECIMAL(10,0) NOT NULL,
	project_metadata_predefined_value_id DECIMAL(10,0) NOT NULL,
	project_metadata_key_id DECIMAL(10,0) NOT NULL,
	predefined_metadata_value LVARCHAR(500) NOT NULL,
	position INTEGER NOT NULL,
	audit_action_type_id INTEGER NOT NULL,
	action_date DATETIME YEAR TO FRACTION(3) NOT NULL,
	action_user_id DECIMAL(12,0) NOT NULL );

create table tc_direct_project(
	project_id integer not null ,
	name varchar(200) not null ,
	description lvarchar(10000),
	project_status_id integer not null ,
	user_id integer not null ,
	create_date datetime year to fraction(3) not null ,
	modify_date datetime year to fraction(3),
	primary key (project_id) constraint tc_direct_project_pkey
);

ALTER TABLE direct_project_metadata ADD CONSTRAINT FOREIGN KEY (project_metadata_key_id) REFERENCES direct_project_metadata_key (id) CONSTRAINT fk_project_metadata_project_metadata_type;
ALTER TABLE direct_project_metadata_audit ADD CONSTRAINT FOREIGN KEY (audit_action_type_id) REFERENCES audit_action_type_lu (audit_action_type_id) CONSTRAINT fk_project_metadata_audit_audit_action_type_lu1;
ALTER TABLE direct_project_metadata_key_audit ADD CONSTRAINT FOREIGN KEY (audit_action_type_id) REFERENCES audit_action_type_lu (audit_action_type_id) CONSTRAINT fk_project_metadata_key_audit_audit_action_type_lu1;
ALTER TABLE direct_project_metadata_predefined_value ADD CONSTRAINT FOREIGN KEY (project_metadata_key_id) REFERENCES direct_project_metadata_key (id) CONSTRAINT fk_project_metadata_type_value_project_metadata_type1;
ALTER TABLE direct_project_metadata_predefined_value_audit ADD CONSTRAINT FOREIGN KEY (audit_action_type_id) REFERENCES audit_action_type_lu (audit_action_type_id) CONSTRAINT fk_project_metadata_predefined_value_audit_audit_action_type_1;





