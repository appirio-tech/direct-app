DROP TABLE project;
CREATE TABLE project (
  project_id                    INTEGER                         NOT NULL,
  PRIMARY KEY(project_id)
);
DROP TABLE phase_type_lu;
CREATE TABLE phase_type_lu (
  phase_type_id                 INTEGER                         NOT NULL,
  PRIMARY KEY(phase_type_id)
);
DROP TABLE project_phase;
CREATE TABLE project_phase (
  project_phase_id              INTEGER                         NOT NULL,
  project_id                    INTEGER                         NOT NULL,
  phase_type_id                 INTEGER                         NOT NULL,
  PRIMARY KEY(project_phase_id),
  FOREIGN KEY(phase_type_id)
    REFERENCES phase_type_lu(phase_type_id),
  FOREIGN KEY(project_id)
    REFERENCES project(project_id)
);
DROP TABLE resource_role_lu;
CREATE TABLE resource_role_lu (
  resource_role_id              INTEGER                         NOT NULL,
  PRIMARY KEY(resource_role_id)
);
DROP TABLE resource;
CREATE TABLE resource (
  resource_id                   INTEGER                         NOT NULL,
  resource_role_id              INTEGER                         NOT NULL,
  project_id                    INTEGER,
  project_phase_id              INTEGER,
  PRIMARY KEY(resource_id),
  FOREIGN KEY(project_id)
    REFERENCES project(project_id),
  FOREIGN KEY(resource_role_id)
    REFERENCES resource_role_lu(resource_role_id),
  FOREIGN KEY(project_phase_id)
    REFERENCES project_phase(project_phase_id)
);
DROP TABLE upload_type_lu;
CREATE TABLE upload_type_lu (
  upload_type_id                INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(upload_type_id)
);
DROP TABLE upload_status_lu;
CREATE TABLE upload_status_lu (
  upload_status_id              INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(upload_status_id)
);
DROP TABLE upload;
CREATE TABLE upload (
  upload_id                     INTEGER                         NOT NULL,
  project_id                    INTEGER                         NOT NULL,
  resource_id                   INTEGER                         NOT NULL,
  upload_type_id                INTEGER                         NOT NULL,
  upload_status_id              INTEGER                         NOT NULL,
  parameter                     VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(upload_id),
  FOREIGN KEY(upload_type_id)
    REFERENCES upload_type_lu(upload_type_id),
  FOREIGN KEY(upload_status_id)
    REFERENCES upload_status_lu(upload_status_id),
  FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id),
  FOREIGN KEY(project_id)
    REFERENCES project(project_id)
);
DROP TABLE submission_status_lu;
CREATE TABLE submission_status_lu (
  submission_status_id          INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(submission_status_id)
);
DROP TABLE submission_type_lu;
CREATE TABLE submission_type_lu (
  submission_type_id          INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(submission_type_id)
);
DROP TABLE submission;
CREATE TABLE submission (
  submission_id                 INTEGER                         NOT NULL,
  upload_id                     INTEGER                         NOT NULL,
  submission_status_id          INTEGER                         NOT NULL,
  submission_type_id          INTEGER,
  screening_score       DECIMAL(5,2),
  initial_score         DECIMAL(5,2),
  final_score           DECIMAL(5,2),
  placement             DECIMAL(3,0),
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(submission_id),
  FOREIGN KEY(submission_status_id)
    REFERENCES submission_status_lu(submission_status_id),
  FOREIGN KEY(upload_id)
    REFERENCES upload(upload_id)
);
DROP TABLE resource_submission;
CREATE TABLE resource_submission (
  resource_id                   INTEGER                         NOT NULL,
  submission_id                 INTEGER                         NOT NULL,
  PRIMARY KEY(resource_id, submission_id),
  FOREIGN KEY(submission_id)
    REFERENCES submission(submission_id),
  FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id)
);
DROP TABLE deliverable_lu;
CREATE TABLE deliverable_lu (
  deliverable_id                INTEGER                         NOT NULL,
  phase_type_id                 INTEGER                         NOT NULL,
  resource_role_id              INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(64)                     NOT NULL,
  submission_type_id            INTEGER,
  required                      DECIMAL(1, 0)                   NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(deliverable_id),
  FOREIGN KEY(phase_type_id)
    REFERENCES phase_type_lu(phase_type_id),
  FOREIGN KEY(resource_role_id)
    REFERENCES resource_role_lu(resource_role_id),
  FOREIGN KEY(submission_type_id)
    REFERENCES submission_type_lu(submission_type_id)
);
DROP TABLE id_sequences;
CREATE TABLE id_sequences (
  name                  VARCHAR(255)    NOT NULL,
  next_block_start      INTEGER         NOT NULL,
  block_size            INTEGER         NOT NULL,
  exhausted             INTEGER         NOT NULL,
  PRIMARY KEY (name)
);