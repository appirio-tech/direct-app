CREATE TABLE project_type_lu (
  project_type_id               INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  is_generic			CHAR(1)				NOT NULL,
  PRIMARY KEY(project_type_id)
);
CREATE TABLE project_category_lu (
  project_category_id           INTEGER                         NOT NULL,
  project_type_id               INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(project_category_id),
  FOREIGN KEY(project_type_id)
    REFERENCES project_type_lu(project_type_id)
);
CREATE TABLE project_status_lu (
  project_status_id             INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(project_status_id)
);
CREATE TABLE project (
  project_id                    INTEGER                         NOT NULL,
  project_status_id             INTEGER                         NOT NULL,
  project_category_id           INTEGER                         NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(project_id),
  FOREIGN KEY(project_category_id)
    REFERENCES project_category_lu(project_category_id),
  FOREIGN KEY(project_status_id)
    REFERENCES project_status_lu(project_status_id)
);
CREATE TABLE project_info_type_lu (
  project_info_type_id          INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(25)                     NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(project_info_type_id)
);
CREATE TABLE project_info (
  project_id                    INTEGER                         NOT NULL,
  project_info_type_id          INTEGER                         NOT NULL,
  value                         LVARCHAR(4096)                  NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(project_id, project_info_type_id),
  FOREIGN KEY(project_info_type_id)
    REFERENCES project_info_type_lu(project_info_type_id),
  FOREIGN KEY(project_id)
    REFERENCES project(project_id)
);
CREATE TABLE project_audit (
  project_audit_id              INTEGER                         NOT NULL,
  project_id                    INTEGER                         NOT NULL,
  update_reason                 VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(project_audit_id),
  FOREIGN KEY(project_id)
    REFERENCES project(project_id)
);
CREATE TABLE resource (
  resource_id                   INTEGER                         NOT NULL,
  project_id                    INTEGER,
  PRIMARY KEY(resource_id),
  FOREIGN KEY(project_id)
    REFERENCES project(project_id)
);
CREATE TABLE resource_info_type_lu (
  resource_info_type_id         INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(resource_info_type_id)
);
CREATE TABLE resource_info (
  resource_id                   INTEGER                         NOT NULL,
  resource_info_type_id         INTEGER                         NOT NULL,
  value                         LVARCHAR(4096)                  NOT NULL,
  PRIMARY KEY(resource_id, resource_info_type_id)
);

CREATE TABLE id_sequences (
  name VARCHAR(255) NOT NULL,
  next_block_start INTEGER NOT NULL,
  block_size INTEGER NOT NULL,
  exhausted INTEGER NOT NULL,
  PRIMARY KEY (name)
);

-- create table for FileType entity
CREATE TABLE file_type_lu (
  file_type_id                               INTEGER                         NOT NULL,
  description                                VARCHAR(254)                    NOT NULL,
  sort                                       INTEGER                         NOT NULL,
  image_file                                 BOOLEAN                         NOT NULL,
  extension                                  VARCHAR(20)                     NOT NULL,
  bundled_file                               BOOLEAN                         NOT NULL,
  create_user                               VARCHAR(64)                     NOT NULL,
  create_date                               DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                               VARCHAR(64)                     NOT NULL,
  modify_date                               DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(file_type_id)
);

-- create table for PrizeType entity
CREATE TABLE prize_type_lu (
  prize_type_id                             INTEGER                         NOT NULL,
  description                               VARCHAR(254)                    NOT NULL,
  PRIMARY KEY(prize_type_id)
);

-- create table for Prize entity
CREATE TABLE prize (
  prize_id                                  INTEGER                         NOT NULL,
  place                                     INTEGER                         NOT NULL,
  prize_amount                              FLOAT                          NOT NULL,
  prize_type_id                             INTEGER                         NOT NULL,
  number_of_submissions                     INTEGER                         NOT NULL,
  create_user                               VARCHAR(64)                     NOT NULL,
  create_date                               DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                               VARCHAR(64)                     NOT NULL,
  modify_date                               DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(prize_id),
  FOREIGN KEY(prize_type_id)
    REFERENCES prize_type_lu(prize_type_id)
);

-- create table for ProjectStudioSpecification entity
CREATE TABLE project_studio_specification (
  project_studio_spec_id                    INTEGER                         NOT NULL,
  goals                                     LVARCHAR (2000)                    NOT NULL,
  target_audience                           LVARCHAR (2000)                    NOT NULL,
  branding_guidelines                       LVARCHAR (2000)                    NOT NULL,
  disliked_design_websites                  LVARCHAR (2000)                    NOT NULL,
  other_instructions                        LVARCHAR (2000)                    NOT NULL,
  winning_criteria                          LVARCHAR (2000)                   NOT NULL,
  submitters_locked_between_rounds          BOOLEAN                         NOT NULL,
  round_one_introduction                    LVARCHAR (2000)                    NOT NULL,
  round_two_introduction                    LVARCHAR (2000)                    NOT NULL,
  colors                                    LVARCHAR (2000)                    NOT NULL,
  fonts                                     LVARCHAR (2000)                    NOT NULL,
  layout_and_size                           LVARCHAR (2000)                    NOT NULL,
  create_user                               VARCHAR(64)                     NOT NULL,
  create_date                               DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                               VARCHAR(64)                     NOT NULL,
  modify_date                               DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(project_studio_spec_id)
);

-- add a project_studio_spec field
ALTER TABLE project add (
  project_studio_spec_id                    INTEGER                        
 
);

ALTER TABLE project ADD CONSTRAINT FOREIGN KEY(project_studio_spec_id) REFERENCES project_studio_specification(project_studio_spec_id);

-- add a tc_direct_project_id field
ALTER TABLE project add (
  tc_direct_project_id                    INTEGER                         
);

-- add relationship for project table and prize table
CREATE TABLE project_prize_xref (
  project_id                    INTEGER                         NOT NULL,
  prize_id                      INTEGER                         NOT NULL,
  PRIMARY KEY(project_id, prize_id),
  FOREIGN KEY(project_id)
    REFERENCES project(project_id),
  FOREIGN KEY(prize_id)
    REFERENCES prize(prize_id)
);

-- add relationship for project table and file type table
CREATE TABLE project_file_type_xref (
  project_id                    INTEGER                         NOT NULL,
  file_type_id                  INTEGER                         NOT NULL,
  PRIMARY KEY(project_id, file_type_id),
  FOREIGN KEY(project_id)
    REFERENCES project(project_id),
  FOREIGN KEY(file_type_id)
    REFERENCES file_type_lu(file_type_id)
);

create table default_terms (
    project_category_id INT not null,
    resource_role_id INT not null,
    terms_of_use_id DECIMAL(10,0) not null,
    cca BOOLEAN
);

create table audit_action_type_lu (
    audit_action_type_id INT not null,
    name VARCHAR(50) not null,
    description VARCHAR(50) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null
);

alter table audit_action_type_lu add constraint primary key
  (audit_action_type_id)
  constraint audit_action_type_lu_pkey;

create table project_info_audit (
project_id int not null,
project_info_type_id int not null,
value varchar(255),
audit_action_type_id int not null,
action_date datetime year to fraction not null,
action_user VARCHAR(64) not null
);

alter table project_info_audit add constraint foreign key
(audit_action_type_id)
references audit_action_type_lu
(audit_action_type_id)
constraint project_info_audit_audit_action_type_lu_fk;

alter table project_info_audit add constraint foreign key
(project_id)
references project
(project_id)
constraint project_info_audit_project_fk;

alter table project_info_audit add constraint foreign key
(project_info_type_id)
references project_info_type_lu
(project_info_type_id)
constraint project_info_audit_project_info_type_lu_fk;

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('project_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('project_audit_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('file_type_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('prize_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('studio_spec_id_seq', 1, 20, 0);

INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'External Reference ID', 'External Reference ID', 'System', CURRENT, 'System', CURRENT);
