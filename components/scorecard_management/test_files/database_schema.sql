CREATE TABLE project_category_lu (
  project_category_id           INTEGER                         NOT NULL,
  PRIMARY KEY(project_category_id)
);
CREATE TABLE scorecard_type_lu (
  scorecard_type_id             INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(scorecard_type_id)
);
CREATE TABLE scorecard_status_lu (
  scorecard_status_id           INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(scorecard_status_id)
);
CREATE TABLE scorecard (
  scorecard_id                  INTEGER                         NOT NULL,
  scorecard_status_id           INTEGER                         NOT NULL,
  scorecard_type_id             INTEGER                         NOT NULL,
  project_category_id           INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  version                       VARCHAR(16)                     NOT NULL,
  min_score                     FLOAT                           NOT NULL,
  max_score                     FLOAT                           NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(scorecard_id),
  FOREIGN KEY(scorecard_type_id)
    REFERENCES scorecard_type_lu(scorecard_type_id),
  FOREIGN KEY(project_category_id)
    REFERENCES project_category_lu(project_category_id),
  FOREIGN KEY(scorecard_status_id)
    REFERENCES scorecard_status_lu(scorecard_status_id)
);
CREATE TABLE scorecard_group (
  scorecard_group_id            INTEGER                         NOT NULL,
  scorecard_id                  INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  weight                        FLOAT                           NOT NULL,
  sort                          DECIMAL(3, 0)                   NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(scorecard_group_id),
  FOREIGN KEY(scorecard_id)
    REFERENCES scorecard(scorecard_id)
);
CREATE TABLE scorecard_section (
  scorecard_section_id          INTEGER                         NOT NULL,
  scorecard_group_id            INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  weight                        FLOAT                           NOT NULL,
  sort                          DECIMAL(3, 0)                   NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(scorecard_section_id),
  FOREIGN KEY(scorecard_group_id)
    REFERENCES scorecard_group(scorecard_group_id)
);
CREATE TABLE scorecard_question_type_lu (
  scorecard_question_type_id    INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(scorecard_question_type_id)
);
CREATE TABLE scorecard_question (
  scorecard_question_id         INTEGER                         NOT NULL,
  scorecard_question_type_id    INTEGER                         NOT NULL,
  scorecard_section_id          INTEGER                         NOT NULL,
  description                   LVARCHAR(4096)                  NOT NULL,
  guideline                     LVARCHAR(4096),
  weight                        FLOAT                           NOT NULL,
  sort                          DECIMAL(3, 0)                   NOT NULL,
  upload_document               DECIMAL(1, 0)                   NOT NULL,
  upload_document_required      DECIMAL(1, 0)                   NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(scorecard_question_id),
  FOREIGN KEY(scorecard_section_id)
    REFERENCES scorecard_section(scorecard_section_id),
  FOREIGN KEY(scorecard_question_type_id)
    REFERENCES scorecard_question_type_lu(scorecard_question_type_id)
);
CREATE TABLE project_phase (
  project_phase_id              INTEGER                         NOT NULL,
  PRIMARY KEY(project_phase_id)
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
  PRIMARY KEY(project_phase_id, phase_criteria_type_id),
  FOREIGN KEY(project_phase_id)
    REFERENCES project_phase(project_phase_id),
  FOREIGN KEY(phase_criteria_type_id)
    REFERENCES phase_criteria_type_lu(phase_criteria_type_id)
);

INSERT INTO scorecard_status_lu(scorecard_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_status_lu(scorecard_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Inactive', 'Inactive', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_status_lu(scorecard_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT);

INSERT INTO phase_criteria_type_lu(phase_criteria_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Scorecard ID', 'Scorecard ID', 'System', CURRENT, 'System', CURRENT);


CREATE TABLE id_sequences (
  name                  VARCHAR(255)    NOT NULL,
  next_block_start      INTEGER         NOT NULL,
  block_size            INTEGER         NOT NULL,
  exhausted             INTEGER         NOT NULL,
  PRIMARY KEY (name)
);

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('scorecard_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('scorecard_group_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('scorecard_section_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('scorecard_question_id_seq', 1, 20, 0);
