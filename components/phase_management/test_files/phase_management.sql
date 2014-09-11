CREATE TABLE project (
  project_id                    INTEGER                         NOT NULL,
  PRIMARY KEY(project_id)
);
CREATE TABLE phase_status_lu (
  phase_status_id               INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(phase_status_id)
);
CREATE TABLE phase_type_lu (
  phase_type_id                 INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(phase_type_id)
);
CREATE TABLE project_phase (
  project_phase_id              INTEGER                         NOT NULL,
  project_id                    INTEGER                         NOT NULL,
  phase_type_id                 INTEGER                         NOT NULL,
  phase_status_id               INTEGER                         NOT NULL,
  fixed_start_time              DATETIME YEAR TO FRACTION(3),
  scheduled_start_time          DATETIME YEAR TO FRACTION(3)    NOT NULL,
  scheduled_end_time            DATETIME YEAR TO FRACTION(3)    NOT NULL,
  actual_start_time             DATETIME YEAR TO FRACTION(3),
  actual_end_time               DATETIME YEAR TO FRACTION(3),
  duration                      DECIMAL(16, 0)     							NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(project_phase_id),
  FOREIGN KEY(phase_type_id)
    REFERENCES phase_type_lu(phase_type_id),
  FOREIGN KEY(project_id)
    REFERENCES project(project_id),
  FOREIGN KEY(phase_status_id)
    REFERENCES phase_status_lu(phase_status_id)
);
CREATE TABLE phase_dependency (
  dependency_phase_id           INTEGER                         NOT NULL,
  dependent_phase_id            INTEGER                         NOT NULL,
  dependency_start              DECIMAL(1, 0)                   NOT NULL,
  dependent_start               DECIMAL(1, 0)                   NOT NULL,
  lag_time                      DECIMAL(16, 0)                  NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(dependency_phase_id, dependent_phase_id),
  FOREIGN KEY(dependency_phase_id)
    REFERENCES project_phase(project_phase_id),
  FOREIGN KEY(dependent_phase_id)
    REFERENCES project_phase(project_phase_id)
);
CREATE TABLE phase_criteria_type_lu (
  phase_criteria_type_id        INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(phase_criteria_type_id)
);
CREATE TABLE phase_criteria (
  project_phase_id              INTEGER                         NOT NULL,
  phase_criteria_type_id        INTEGER                         NOT NULL,
  parameter                     VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(project_phase_id, phase_criteria_type_id),
  FOREIGN KEY(project_phase_id)
    REFERENCES project_phase(project_phase_id),
  FOREIGN KEY(phase_criteria_type_id)
    REFERENCES phase_criteria_type_lu(phase_criteria_type_id)
);

CREATE TABLE id_sequences (
  name                  VARCHAR(255)    NOT NULL,
  next_block_start      INTEGER         NOT NULL,
  block_size            INTEGER         NOT NULL,
  exhausted             INTEGER         NOT NULL,
  PRIMARY KEY (name)
);

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('phase_id_seq', 1, 20, 0);