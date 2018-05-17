--
-- This script creates new or alters existing database tables to accomodate the changes necessary
-- for Client Project EJB integration.
--
-- 

-- client_user_xref
CREATE TABLE client_user_xref (
  client_id         INTEGER NOT NULL,
  user_id           INTEGER NOT NULL,
  creation_date     DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION,
  creation_user     VARCHAR(64),
  modification_date DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION,
  modification_user VARCHAR(64)
);

-- project_user_xref
CREATE TABLE project_user_xref (
  project_id        INTEGER NOT NULL,
  user_id           INTEGER NOT NULL,
  creation_date     DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION,
  creation_user     VARCHAR(64),
  modification_date DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION,
  modification_user VARCHAR(64)
);

-- client_status
CREATE TABLE client_status (
  client_status_id  INTEGER NOT NULL,
  name              VARCHAR(64),
  description       VARCHAR(255),
  is_deleted        SMALLINT,
  creation_date     DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION,
  creation_user     VARCHAR(64),
  modification_date DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION,
  modification_user VARCHAR(64)
);

-- project_status
CREATE TABLE project_status (
  project_status_id INTEGER NOT NULL,
  name              VARCHAR(64),
  description       VARCHAR(255),
  is_deleted        SMALLINT,
  creation_date     DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION,
  creation_user     VARCHAR(64),
  modification_date DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION,
  modification_user VARCHAR(64)
);

-- project_xref
CREATE TABLE project_xref (
  parent_project_id INTEGER NOT NULL,
  child_project_id  INTEGER NOT NULL,
  is_deleted        SMALLINT,
  creation_date     DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION,
  creation_user     VARCHAR(64),
  modification_date DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION,
  modification_user VARCHAR(64)
);



-- id_sequences
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('client_id', 1, 20, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('client_status_id', 1, 20, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted) VALUES ('test', 1, 20, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted)
       VALUES ('com.topcoder.clients.manager.dao.DAOCompanyManager', 1, 20, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted)
       VALUES ('com.topcoder.clients.manager.dao.DAOClientManager', 1, 20, 0);
INSERT INTO id_sequences (name, next_block_start, block_size, exhausted)
       VALUES ('com.topcoder.clients.manager.dao.DAOProjectManager', 1, 20, 0);


CREATE SYNONYM client_company FOR company;
CREATE SYNONYM client_project FOR project;

-- company
ALTER TABLE company ADD is_deleted SMALLINT;

-- client
ALTER TABLE client ADD code_name VARCHAR(64);
ALTER TABLE client ADD is_deleted SMALLINT;
ALTER TABLE client ADD client_status_id INTEGER;
ALTER TABLE client ADD enable_effort_hours SMALLINT;

-- project
ALTER TABLE project ADD project_status_id INTEGER;
ALTER TABLE project ADD client_id INTEGER;
ALTER TABLE project ADD parent_project_id INTEGER;
ALTER TABLE project ADD is_deleted SMALLINT;

alter table client_status add constraint primary key 
	(client_status_id)
	constraint client_status_pk;

alter table prject_status add constraint primary key 
	(prject_status_id)
	constraint prject_status_pk;

alter table project_xref add constraint primary key 
	(parent_project_id, child_project_id)
	constraint project_xref_pk;

alter table client_user_xref add constraint foreign key 
	(client_id) references client (client_id)
	constraint client_user_xref_client_fk;

alter table client_user_xref add constraint foreign key 
	(user_id) references user_account (user_account_id)
	constraint client_user_xref_user_account_fk;

alter table project_user_xref add constraint foreign key 
	(user_id) references user_account (user_account_id)
	constraint project_user_xref_user_account_fk;

alter table project_user_xref add constraint foreign key 
	(project_id) references project (project_id)
	constraint project_user_xref_project_fk;

alter table project_xref add constraint foreign key 
	(parent_project_id) references project (project_id)
	constraint project_xref_parent_project_fk;

alter table project_xref add constraint foreign key 
	(child_project_id) references project (project_id)
	constraint project_xref_child_project_fk;

alter table client add constraint foreign key 
	(client_status_id) references client_status (client_status_id)
	constraint client_client_status_fk;

alter table project add constraint foreign key 
	(client_id) references client (client_id)
	constraint project_client_fk;

alter table project add constraint foreign key 
	(project_status_id) references project_status (project_status_id)
	constraint project_project_status_fk;

alter table project add constraint foreign key 
	(parent_project_id) references project (project_id)
	constraint project_parent_project_fk;
