CREATE TABLE project (
  project_id                    INTEGER                         NOT NULL,
  PRIMARY KEY(project_id)
);

CREATE TABLE phase_type_lu (
  phase_type_id                 INTEGER                         NOT NULL,
  PRIMARY KEY(phase_type_id)
);

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

CREATE TABLE resource_role_lu (
  resource_role_id              INTEGER                         NOT NULL,
  PRIMARY KEY(resource_role_id)
);

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
  -- Field upload_desc was added in the version 1.2
  upload_desc                   VARCHAR(254),
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

-- Table submission_type_lu was added in the version 1.1
CREATE TABLE submission_type_lu (
  submission_type_id            INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(submission_type_id)
);

-- create table for PrizeType entity
CREATE TABLE prize_type_lu (
  prize_type_id                             DECIMAL(10,0)                         NOT NULL,
  prize_type_desc                               VARCHAR(254)                    NOT NULL,
  PRIMARY KEY(prize_type_id)
);
 
-- create table for Prize entity
CREATE TABLE prize (
  prize_id                                  DECIMAL(10,0)                         NOT NULL,
  place                                     INTEGER                         NOT NULL,
  prize_amount                              DECIMAL(10,2)                          NOT NULL,
  prize_type_id                             DECIMAL(10,0)                         NOT NULL,
  number_of_submissions                     INTEGER                         NOT NULL,
  create_user                               VARCHAR(64)                     NOT NULL,
  create_date                               DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                               VARCHAR(64)                     NOT NULL,
  modify_date                               DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(prize_id),
  FOREIGN KEY(prize_type_id)
    REFERENCES prize_type_lu(prize_type_id)
);

CREATE TABLE submission (
  submission_id                 INTEGER                         NOT NULL,
  -- Field upload_id was removed in the version 1.2, see upload_submission table
  -- upload_id                  INTEGER                         NOT NULL,
  submission_status_id          INTEGER                         NOT NULL,
  submission_type_id            INTEGER                         NOT NULL,
  screening_score		DECIMAL(5,2),
  initial_score 		DECIMAL(5,2),
  final_score 			DECIMAL(5,2),
  placement 			DECIMAL(3,0),
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  -- Field feedback_thumb was added in the version 1.2
  feedback_thumb                BOOLEAN(1)                    NOT NULL,
  -- Field user_rank was added in the version 1.2
  user_rank                     DECIMAL(5,0)                    NOT NULL,
  -- Field mark_for_purchase was added in the version 1.2
  mark_for_purchase             BOOLEAN(1)                      NOT NULL,
  prize_id                      DECIMAL(10,0),
  PRIMARY KEY(submission_id),
  FOREIGN KEY(submission_status_id)
    REFERENCES submission_status_lu(submission_status_id),
  FOREIGN KEY(submission_type_id)
    REFERENCES submission_type_lu(submission_type_id),
  FOREIGN KEY(prize_id)
    REFERENCES prize(prize_id)
  -- FOREIGN KEY(upload_id)
  --  REFERENCES upload(upload_id)
);

-- Table upload_submission was added in the version 1.2
CREATE TABLE upload_submission (
  upload_id                     INTEGER                         NOT NULL,
  submission_id                 INTEGER                         NOT NULL,
  PRIMARY KEY(upload_id),
  FOREIGN KEY(upload_id)
    REFERENCES upload(upload_id)
    ON DELETE CASCADE,
  FOREIGN KEY(submission_id)
    REFERENCES submission(submission_id)
    ON DELETE CASCADE
);

CREATE TABLE resource_submission (
  resource_id                   INTEGER                         NOT NULL,
  submission_id                 INTEGER                         NOT NULL,
  PRIMARY KEY(resource_id, submission_id),
  FOREIGN KEY(submission_id)
    REFERENCES submission(submission_id),
  FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id)
);

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

-- Table file_type_lu was added in the version 1.2
CREATE TABLE file_type_lu (
  file_type_id                  DECIMAL(3,0)                    NOT NULL,
  file_type_desc                VARCHAR(100)                    NOT NULL,
  sort                          DECIMAL(3,0)                    NOT NULL,
  image_file_ind                DECIMAL(1,0)                    NOT NULL,
  extension                     VARCHAR(10)                     NOT NULL,
  bundled_file_ind              DECIMAL(1,0) DEFAULT 0          NOT NULL,
  PRIMARY KEY(file_type_id)
);

-- Table mime_type_lu was added in the version 1.2
CREATE TABLE mime_type_lu (
  mime_type_id                  DECIMAL(12,0)                   NOT NULL,
  file_type_id                  DECIMAL(3,0)                    NOT NULL,
  mime_type_desc                VARCHAR(100)                    NOT NULL,
  PRIMARY KEY(mime_type_id),
  FOREIGN KEY(file_type_id)
    REFERENCES file_type_lu(file_type_id)
);

-- Table submission_image was added in the version 1.2
CREATE TABLE submission_image (
  submission_id                 INTEGER                         NOT NULL,
  image_id                      DECIMAL(10,0)                   NOT NULL,
  sort_order                    INTEGER                         NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3) DEFAULT CURRENT YEAR TO FRACTION(3),
  create_date                   DATETIME YEAR TO FRACTION(3) DEFAULT CURRENT YEAR TO FRACTION(3),
  PRIMARY KEY(submission_id, image_id),
  FOREIGN KEY(submission_id)
    REFERENCES submission(submission_id)
    ON DELETE CASCADE
);