CREATE TABLE project_type_lu (
  project_type_id               INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
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
CREATE TABLE scorecard_assignment_lu (
  scorecard_assignment_id       INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(25)                     NOT NULL,
  scorecard_type_id             INTEGER                         NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(scorecard_assignment_id),
  FOREIGN KEY(scorecard_type_id)
    REFERENCES scorecard_type_lu(scorecard_type_id)
);
CREATE TABLE project_scorecard (
  project_id                    INTEGER                         NOT NULL,
  scorecard_id                  INTEGER                         NOT NULL,
  scorecard_assignment_id       INTEGER                         NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(project_id, scorecard_id, scorecard_assignment_id),
  FOREIGN KEY(project_id)
    REFERENCES project(project_id),
  FOREIGN KEY(scorecard_id)
    REFERENCES scorecard(scorecard_id),
  FOREIGN KEY(scorecard_assignment_id)
    REFERENCES scorecard_assignment_lu(scorecard_assignment_id)
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
  duration 						DECIMAL(16, 0)					NOT NULL,
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
CREATE TABLE resource_role_lu (
  resource_role_id              INTEGER                         NOT NULL,
  phase_type_id                 INTEGER,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(resource_role_id),
  FOREIGN KEY(phase_type_id)
    REFERENCES phase_type_lu(phase_type_id)
);
CREATE TABLE resource (
  resource_id                   INTEGER                         NOT NULL,
  resource_role_id              INTEGER                         NOT NULL,
  project_id                    INTEGER,
  project_phase_id              INTEGER,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(resource_id),
  FOREIGN KEY(project_id)
    REFERENCES project(project_id),
  FOREIGN KEY(resource_role_id)
    REFERENCES resource_role_lu(resource_role_id),
  FOREIGN KEY(project_phase_id)
    REFERENCES project_phase(project_phase_id)
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
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(resource_id, resource_info_type_id),
  FOREIGN KEY(resource_info_type_id)
    REFERENCES resource_info_type_lu(resource_info_type_id),
  FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id)
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
create table submission (
    submission_id INT not null,
    upload_id INT not null,
    submission_status_id INT not null,
    submission_type_id INT not null,
    screening_score DECIMAL(5,2),
    initial_score DECIMAL(5,2),
    final_score DECIMAL(5,2),
    placement DECIMAL(3,0),
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
extent size 5000 next size 2000
lock mode row;

alter table submission add constraint primary key 
	(submission_id)
	constraint pk_submission;

alter table submission add constraint foreign key 
	(submission_status_id)
	references submission_status_lu
	(submission_status_id) 
	constraint fk_submission_submissionstatuslu_submissionstatusid;

alter table submission add constraint foreign key 
	(submission_type_id)
	references submission_type_lu
	(submission_type_id) 
	constraint fk_submission_submissiontypelu_submissiontypeid;

alter table submission add constraint foreign key 
	(upload_id)
	references upload
	(upload_id) 
	constraint fk_submission_upload_uploadid;
CREATE TABLE resource_submission (
  resource_id                   INTEGER                         NOT NULL,
  submission_id                 INTEGER                         NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(resource_id, submission_id),
  FOREIGN KEY(submission_id)
    REFERENCES submission(submission_id),
  FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id)
);
CREATE TABLE comment_type_lu (
  comment_type_id               INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(comment_type_id)
);
CREATE TABLE review (
  review_id                     INTEGER                         NOT NULL,
  resource_id                   INTEGER                         NOT NULL,
  submission_id                 INTEGER,
  scorecard_id                  INTEGER                         NOT NULL,
  committed                     DECIMAL(1, 0)                   NOT NULL,
  score                         FLOAT,
  initial_score  								FLOAT,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(review_id),
  FOREIGN KEY(scorecard_id)
    REFERENCES scorecard(scorecard_id),
  FOREIGN KEY(submission_id)
    REFERENCES submission(submission_id),
  FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id)
);
CREATE TABLE review_item (
  review_item_id                INTEGER                         NOT NULL,
  review_id                     INTEGER                         NOT NULL,
  scorecard_question_id         INTEGER                         NOT NULL,
  upload_id                     INTEGER,
  answer                        VARCHAR(254)                    NOT NULL,
  sort                          DECIMAL(3, 0)                   NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(review_item_id),
  FOREIGN KEY(review_id)
    REFERENCES review(review_id),
  FOREIGN KEY(scorecard_question_id)
    REFERENCES scorecard_question(scorecard_question_id),
  FOREIGN KEY(upload_id)
    REFERENCES upload(upload_id)
);
CREATE TABLE review_comment (
  review_comment_id             INTEGER                         NOT NULL,
  resource_id                   INTEGER                         NOT NULL,
  review_id                     INTEGER                         NOT NULL,
  comment_type_id               INTEGER                         NOT NULL,
  content                       LVARCHAR(4096)                  NOT NULL,
  extra_info                    VARCHAR(254),
  sort                          DECIMAL(3, 0)                   NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(review_comment_id),
  FOREIGN KEY(review_id)
    REFERENCES review(review_id),
  FOREIGN KEY(comment_type_id)
    REFERENCES comment_type_lu(comment_type_id),
  FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id)
);
CREATE TABLE review_item_comment (
  review_item_comment_id        INTEGER                         NOT NULL,
  resource_id                   INTEGER                         NOT NULL,
  review_item_id                INTEGER                         NOT NULL,
  comment_type_id               INTEGER                         NOT NULL,
  content                       LVARCHAR(4096)                  NOT NULL,
  extra_info                    VARCHAR(254),
  sort                          DECIMAL(3, 0)                   NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(review_item_comment_id),
  FOREIGN KEY(review_item_id)
    REFERENCES review_item(review_item_id),
  FOREIGN KEY(comment_type_id)
    REFERENCES comment_type_lu(comment_type_id),
  FOREIGN KEY(resource_id)
    REFERENCES resource(resource_id)
);
CREATE TABLE deliverable_lu (
  deliverable_id                INTEGER                         NOT NULL,
  phase_type_id                 INTEGER                         NOT NULL,
  resource_role_id              INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(64)                     NOT NULL,
  per_submission                DECIMAL(1, 0)                   NOT NULL,
  required                      DECIMAL(1, 0)                   NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(deliverable_id),
  FOREIGN KEY(phase_type_id)
    REFERENCES phase_type_lu(phase_type_id),
  FOREIGN KEY(resource_role_id)
    REFERENCES resource_role_lu(resource_role_id)
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


CREATE TABLE notification_type_lu (
  notification_type_id          INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(notification_type_id)
);
CREATE TABLE notification (
  project_id                    INTEGER                         NOT NULL,
  external_ref_id               INTEGER                         NOT NULL,
  notification_type_id          INTEGER                         NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(project_id, external_ref_id, notification_type_id),
  FOREIGN KEY(project_id)
    REFERENCES project(project_id),
  FOREIGN KEY(notification_type_id)
    REFERENCES notification_type_lu(notification_type_id)
);


CREATE TABLE screening_status_lu (
  screening_status_id           INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(screening_status_id)
);
CREATE TABLE screening_task (
  screening_task_id             INTEGER                         NOT NULL,
  upload_id                     INTEGER                         NOT NULL,
  screening_status_id           INTEGER                         NOT NULL,
  screener_id                   INTEGER,
  start_timestamp               DATETIME YEAR TO FRACTION(3),
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(screening_task_id),
  FOREIGN KEY(upload_id)
    REFERENCES upload(upload_id),
  FOREIGN KEY(screening_status_id)
    REFERENCES screening_status_lu(screening_status_id)
);
CREATE TABLE response_severity_lu (
  response_severity_id          INTEGER                         NOT NULL,
  name                          VARCHAR(64)                     NOT NULL,
  description                   VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(response_severity_id)
);
CREATE TABLE screening_response_lu (
  screening_response_id         INTEGER                         NOT NULL,
  response_severity_id          INTEGER                         NOT NULL,
  response_code                 VARCHAR(16)                     NOT NULL,
  response_text                 VARCHAR(254)                    NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(screening_response_id),
  FOREIGN KEY(response_severity_id)
    REFERENCES response_severity_lu(response_severity_id)
);
CREATE TABLE screening_result (
  screening_result_id           INTEGER                         NOT NULL,
  screening_task_id             INTEGER                         NOT NULL,
  screening_response_id         INTEGER                         NOT NULL,
  dynamic_response_text         LVARCHAR(4096)                  NOT NULL,
  create_user                   VARCHAR(64)                     NOT NULL,
  create_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  modify_user                   VARCHAR(64)                     NOT NULL,
  modify_date                   DATETIME YEAR TO FRACTION(3)    NOT NULL,
  PRIMARY KEY(screening_result_id),
  FOREIGN KEY(screening_task_id)
    REFERENCES screening_task(screening_task_id),
  FOREIGN KEY(screening_response_id)
    REFERENCES screening_response_lu(screening_response_id)
);


CREATE TABLE id_sequences (
  name                  VARCHAR(255)    NOT NULL,
  next_block_start      INTEGER         NOT NULL,
  block_size            INTEGER         NOT NULL,
  exhausted             INTEGER         NOT NULL,
  PRIMARY KEY (name)
);

create table email (
    user_id DECIMAL(10,0),
    email_id DECIMAL(10,0),
    email_type_id DECIMAL(5,0),
    address VARCHAR(100),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    primary_ind DECIMAL(1,0),
    status_id DECIMAL(3,0)
)
extent size 7500 next size 2750
lock mode row;

create index email_user_id_idx on email
	(
	user_id, 
	primary_ind
	);

alter table email add constraint primary key 
	(email_id)
	constraint u110_23;



create table user (
    user_id DECIMAL(10,0) not null,
    first_name VARCHAR(64),
    last_name VARCHAR(64),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    handle VARCHAR(50) not null,
    last_login DATETIME YEAR TO FRACTION,
    status VARCHAR(3) not null,
    password VARCHAR(15),
    activation_code VARCHAR(32),
    middle_name VARCHAR(64),
    handle_lower VARCHAR(50),
    timezone_id DECIMAL(5,0)
)
extent size 10000 next size 5000
lock mode row;

create index user_handle_idx on user
	(
	handle
	);

create index user_lower_handle_idx on user
	(
	handle_lower
	);

alter table user add constraint primary key 
	(user_id)
	constraint u124_45;




create table user_rating (
    user_id DECIMAL(10) not null,
    rating DECIMAL(10) default 0 not null,
    phase_id DECIMAL(3) not null,
    vol DECIMAL(10) default 0 not null,
    rating_no_vol DECIMAL(10) default 0 not null,
    num_ratings DECIMAL(5) default 0 not null,
    mod_date_time DATETIME YEAR TO FRACTION default CURRENT YEAR TO
    FRACTION not null,
    create_date_time DATETIME YEAR TO FRACTION default CURRENT YEAR TO
    FRACTION not null,
    last_rated_project_id DECIMAL(12,0)
);

alter table user_rating add constraint primary key 
	(user_id, phase_id)
	constraint pk_user_rating;



create table user_reliability (
    user_id DECIMAL(10,0),
    rating DECIMAL(5,4),
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    phase_id DECIMAL(12,0)
)
extent size 32 next size 32
lock mode row;

alter table user_reliability add constraint primary key 
	(user_id, phase_id)
	constraint user_reliability_pkey;





create table comp_catalog (
    component_id DECIMAL(12,0) not null,
    current_version DECIMAL(12,0) not null,
    short_desc lvarchar,
    component_name VARCHAR(254) not null,
    description lvarchar,
    function_desc lvarchar,
    create_time DATETIME YEAR TO FRACTION not null,
    status_id DECIMAL(12,0) not null,
    root_category_id DECIMAL(12,0),
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION 
    not null
);

alter table comp_catalog add constraint primary key 
	(component_id)
	constraint pk_comp_catalog;





create table categories (
    category_id DECIMAL(12,0) not null,
    parent_category_id DECIMAL(12,0),
    category_name VARCHAR(100) not null,
    description VARCHAR(254) not null,
    status_id DECIMAL(12,0) not null,
    viewable DECIMAL(1,0) default 1
)
extent size 16 next size 16
lock mode row;

alter table categories add constraint primary key 
	(category_id)
	constraint pk_categories;



create table comp_versions (
    comp_vers_id DECIMAL(12,0) not null,
    component_id DECIMAL(12,0),
    version DECIMAL(12,0) not null,
    version_text CHAR(20) not null,
    create_time DATETIME YEAR TO FRACTION not null,
    phase_id DECIMAL(12,0) not null,
    phase_time DATETIME YEAR TO FRACTION not null,
    price DECIMAL(10,2) not null,
    comments lvarchar,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO
    FRACTION not null
)
extent size 500 next size 124
lock mode row;

create unique cluster index comp_versions_i2 on
comp_versions
	(
	component_id, 
	version
	);

alter table comp_versions add constraint primary key 
	(comp_vers_id)
	constraint pk_comp_versions;

alter table comp_versions add constraint foreign key 
	(component_id)
	references comp_catalog
	(component_id) 
	constraint fk_comp_versions;





create table comp_forum_xref (
    comp_forum_id DECIMAL(12,0) not null,
    comp_vers_id DECIMAL(12,0),
    forum_id DECIMAL(12,0),
    forum_type DECIMAL(5) not null
)
extent size 250 next size 124
lock mode row;

create unique cluster index comp_forum_xref_i1 on
comp_forum_xref
	(
	comp_vers_id, 
	forum_id
	);

alter table comp_forum_xref add constraint primary key 
	(comp_forum_id)
	constraint pk_comp_forum_xref;

alter table comp_forum_xref add constraint foreign key 
	(comp_vers_id)
	references comp_versions
	(comp_vers_id) 
	constraint fk_comp_forum2;

delete from id_sequences;

-- FOR resource management

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('resource_id_seq', 1, 1, 0); 
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('notification_type_id_seq', 1, 1, 0); 
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('resource_role_id_seq', 1, 1, 0); 

-- FOR project management

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('project_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('project_audit_id_seq', 1, 1, 0);

-- FOR phase_management

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('phase_id_seq', 1000, 1, 0);

-- FOR deliverable_management

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('upload_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('upload_type_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('upload_status_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('submission_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('submission_status_id_seq', 1, 1, 0);

-- FOR scorecard management

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('scorecard_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('scorecard_group_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('scorecard_section_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('scorecard_question_id_seq', 1, 1, 0);

-- FOR screening management

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('screening_task_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('screening_result_id_seq', 1, 1, 0);

-- FOR review management

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('review_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('review_item_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('review_comment_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('review_item_comment_id_seq', 1, 1, 0);
  
-- FOR review management

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('team_id_seq', 1, 1, 0);

-- create new lookup table for audit action types
create table 'informix'.audit_action_type_lu (
    audit_action_type_id INT not null,
    name VARCHAR(50) not null,
    description VARCHAR(50) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null
);

alter table 'informix'.audit_action_type_lu add constraint primary key 
	(audit_action_type_id)
	constraint audit_action_type_lu_pkey;

-- initialize this lookup table
insert into audit_action_type_lu values (1, "Create", "Create", "System", current, "System", current);
insert into audit_action_type_lu values (2, "Delete", "Delete", "System", current, "System", current);
insert into audit_action_type_lu values (3, "Update", "Update", "System", current, "System", current);

-- create audit table for project resources
create table 'informix'.project_user_audit  (
    project_user_audit_id DECIMAL(12,0) not null,
    project_id INT not null,
    resource_user_id DECIMAL(12,0) not null,
    resource_role_id INT not null,
    audit_action_type_id INT not null,
    action_date DATETIME YEAR TO FRACTION not null,
    action_user_id DECIMAL(12,0) not null
)
extent size 16 next size 16
lock mode row; 

alter table 'informix'.project_user_audit add constraint primary key 
	(project_user_audit_id)
	constraint project_user_audit_pkey;

-- enforce Referential Integrity.
alter table 'informix'.project_user_audit add constraint foreign key 
	(audit_action_type_id)
	references 'informix'.audit_action_type_lu
	(audit_action_type_id) 
	constraint project_user_audit_audit_action_type_lu_fk;

alter table 'informix'.project_user_audit add constraint foreign key 
	(project_id)
	references 'informix'.project
	(project_id) 
	constraint project_user_audit_project_fk;

alter table 'informix'.project_user_audit add constraint foreign key 
	(resource_role_id)
	references 'informix'.resource_role_lu
	(resource_role_id) 
	constraint project_user_audit_resource_role_lu_fk;

-- create sequence for project_user_audit
CREATE SEQUENCE PROJECT_USER_AUDIT_SEQ;

INSERT INTO project_type_lu(project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Component', 'Component', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_type_lu(project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Application', 'Application', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 1, 'Design', 'Design', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 1, 'Development', 'Development', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 1, 'Security', 'Security', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 1, 'Process', 'Process', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 1, 'Testing Competition', 'Testing Competition', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(6, 2, 'Specification', 'Specification', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(7, 2, 'Architecture', 'Architecture', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(8, 2, 'Component Production', 'Component Production', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(9, 2, 'Quality Assurance', 'Quality Assurance', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(10, 2, 'Deployment', 'Deployment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(11, 2, 'Security', 'Security', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(12, 2, 'Process', 'Process', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(13, 2, 'Testing Competition', 'Testing Competition', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(14, 2, 'Assembly Competition', 'Assembly Competition', 'System', CURRENT, 'System', CURRENT);

INSERT INTO scorecard_type_lu(scorecard_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Screening', 'Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_type_lu(scorecard_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Review', 'Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_type_lu(scorecard_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Client Review', 'Client Review', 'System', CURRENT, 'System', CURRENT);

INSERT INTO scorecard_status_lu(scorecard_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_status_lu(scorecard_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Inactive', 'Inactive', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_status_lu(scorecard_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT);

INSERT INTO scorecard_question_type_lu(scorecard_question_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Scale (1-4)', 'Scale (1-4)', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_question_type_lu(scorecard_question_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Scale (1-10)', 'Scale (1-10)', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_question_type_lu(scorecard_question_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Test Case', 'Test Case', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_question_type_lu(scorecard_question_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Yes/No', 'Yes/No', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_question_type_lu(scorecard_question_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Dynamic', 'Dynamic', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Inactive', 'Inactive', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Cancelled - Failed Review', 'Cancelled - Failed Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Cancelled - Failed Screening', 'Cancelled - Failed Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(6, 'Cancelled - Zero Submissions', 'Cancelled - Zero Submissions', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(7, 'Completed', 'Completed', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'External Reference ID', 'External Reference ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Component ID', 'Component ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Version ID', 'Version ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Developer Forum ID', 'Developer Forum ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Root Catalog ID', 'Root Catelog ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(6, 'Project Name', 'Project Name', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(7, 'Project Version', 'Project Version', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(8, 'SVN Module', 'SVN Module', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(9, 'Autopilot Option', 'Autopilot Option', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(10, 'Status Notification', 'Status Notification', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(11, 'Timeline Notification', 'Timeline Notification', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(12, 'Public', 'Public', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(13, 'Rated', 'Rated', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(14, 'Eligibility', 'Eligibility', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(15, 'Payments Required', 'Payments Required', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(16, 'Payments', 'Payments', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(17, 'Notes', 'Notes', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(18, 'Deactivated Timestamp', 'Deactivated Timestamp', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(19, 'Deactivated Phase', 'Deactivated Phase', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(20, 'Deactivated Reason', 'Deactivated Reason', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(21, 'Completion Timestamp', 'Completion Timestamp', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(22, 'Rated Timestamp', 'Rated Timestamp', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(23, 'Winner External Reference ID', 'Winner External Reference ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(24, 'Runner-up External Reference ID', 'Runner-up External Reference ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(25, 'Event Flag', 'Event Flag', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(26, 'Digital Run Flag', 'Digital Run Flag', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(27, 'AutoPilot AD Change', 'AutoPilot AD Change', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(28, 'Allow multiple submissions', 'Allow multiple submission', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(29, 'Contest Indicator', 'Whether or not this proje', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(30, 'DR points', 'DR points to award', 'pulky', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(31,"Admin Fee","Admin Fee", 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(32,"Billing Project","Billing Project", 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(33,"Review Cost","Review Cost", 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(34, 'Confidentiality Type', 'Confidentiality Type', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(35, 'Spec Review Cost', 'Spec Review Cost', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(36,'First Place Cost','First Place Cost', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(37,'Second Place Cost','Second Place Cost', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(38,'Reliability Bonus Cost','Reliability Bonus Cost', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(39,'Milestone Bonus Cost','Milestone Bonus Cost', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(40,'Cost Level','Cost Level', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(41, 'Approval Required', 'Approval Required', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(42, 'Requires Other Fixes', 'Requires Other Fixes', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(43, 'Send Winner Emails', 'Send Winner Emails', 'System', CURRENT, 'System', CURRENT);

INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Registration', 'Registration', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Submission', 'Submission', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Screening', 'Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Review', 'Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Appeals', 'Appeals', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(6, 'Appeals Response', 'Appeals Response', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(7, 'Aggregation', 'Aggregation', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(8, 'Aggregation Review', 'Aggregation Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(9, 'Final Fix', 'Final Fix', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(10, 'Final Review', 'Final Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(11, 'Approval', 'Approval', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(12, 'Post-Mortem', 'Post-Mortem', 'System', CURRENT, 'System', CURRENT);

INSERT INTO phase_status_lu(phase_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Scheduled', 'Scheduled', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_status_lu(phase_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Open', 'Open', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_status_lu(phase_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Closed', 'Closed', 'System', CURRENT, 'System', CURRENT);

INSERT INTO phase_criteria_type_lu(phase_criteria_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Scorecard ID', 'Scorecard ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_criteria_type_lu(phase_criteria_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Registration Number', 'Registration Number', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_criteria_type_lu(phase_criteria_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Submission Number', 'Submission Number', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_criteria_type_lu(phase_criteria_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'View Response During Appeals', 'View Response During Appeals', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_criteria_type_lu(phase_criteria_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Manual Screening', 'Manual Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_criteria_type_lu(phase_criteria_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(6, 'Reviewer Number', 'Reviewer Number', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_criteria_type_lu(phase_criteria_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(7, 'Post-Mortem Reviewer Number', 'Post-Mortem Reviewer Number', 'System', CURRENT, 'System', CURRENT);  

INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (1, NULL, 'Submitter', 'Submitter', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (2, 3, 'Primary Screener', 'Primary Screener', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (3, 3, 'Screener', 'Screener', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (4, 4, 'Reviewer', 'Reviewer', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (5, 4, 'Accuracy Reviewer', 'Accuracy Reviewer', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (6, 4, 'Failure Reviewer', 'Failure Reviewer', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (7, 4, 'Stress Reviewer', 'Stress Reviewer', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (8, 7, 'Aggregator', 'Aggregator', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (9, 10, 'Final Reviewer', 'Final Reviewer', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (10, NULL, 'Approver', 'Approver', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (11, NULL, 'Designer', 'Designer', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (12, NULL, 'Observer', 'Observer', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (13, NULL, 'Manager', 'Manager', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (14, NULL, 'Copilot', 'Copilot', 'System', '2010-01-13 08:54:35.000', 'System', '2010-01-13 08:54:35.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (15, NULL, 'Client Manager', 'Client Manager', 'System', '2010-01-13 08:54:35.000', 'System', '2010-01-13 08:54:35.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (16, NULL, 'Post-Mortem Reviewer', 'Post-Mortem Reviewer', 'System', current, 'System', current);
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (1001, NULL, 'Team Captain', 'Team Captain', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (1002, NULL, 'Free Agent', 'Free Agent', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (1003, NULL, 'Payment Manager', 'Payment Manager', 'System', '2009-03-16 20:27:00.000', 'System', '2009-03-16 20:27:00.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (86, NULL, 'Deactivated', 'Deactivated', 'System', '2008-12-04 14:51:25.000', 'System', '2008-12-04 14:51:25.000');

INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'External Reference ID', 'External Reference ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Handle', 'Handle', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Email', 'Email', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Rating', 'Rating', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Reliability', 'Reliability', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(6, 'Registration Date', 'Registration Date', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(7, 'Payment', 'Payment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(8, 'Payment Status', 'Payment Status', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(9, 'Screening Score', 'Screening Score', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(10, 'Initial Score', 'Initial Score', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(11, 'Final Score', 'Final Score', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(12, 'Placement', 'Placement', 'System', CURRENT, 'System', CURRENT);

INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Submission', 'Submission', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Test Case', 'Test Case', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Final Fix', 'Final Fix', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Review Document', 'Review Document', 'System', CURRENT, 'System', CURRENT);

INSERT INTO upload_status_lu(upload_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload_status_lu(upload_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT);

INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Failed Screening', 'Failed Manual Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Failed Review', 'Failed Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Completed Without Win', 'Completed Without Win', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT);

INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Comment', 'Comment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Recommended', 'Recommended', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Required', 'Required', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Appeal', 'Appeal', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Appeal Response', 'Appeal Response', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(6, 'Aggregation Comment', 'Aggregator Comment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(7, 'Aggregation Review Comment', 'Aggregator Comment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(8, 'Submitter Comment', 'Submitter Comment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(9, 'Final Fix Comment', 'Final Fix Comment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(10, 'Final Review Comment', 'Final Review Comment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(11, 'Manager Comment', 'Manager Comment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(12, 'Approval Review Comment', 'Approval Review Comment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(13, 'Approval Review Comment - Other Fixes', 'The final product meets all requirement but requires other fixes before it can be used', 'System', CURRENT, 'System', CURRENT);

INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(1, 2, 1, 'Submission', 'Submission', 0, 0, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(2, 3, 2, 'Screening Scorecard', 'Screening Scorecard', 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(3, 3, 3, 'Screening Scorecard', 'Screening Scorecard', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(4, 4, 4, 'Review Scorecard', 'Review Scorecard', 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(5, 4, 5, 'Review Scorecard', 'Review Scorecard', 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(6, 4, 6, 'Review Scorecard', 'Review Scorecard', 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(7, 4, 7, 'Review Scorecard', 'Review Scorecard', 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(8, 4, 5, 'Accuracy Test Cases', 'Accuracy Test Cases', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(9, 4, 6, 'Failure Test Cases', 'Failure Test Cases', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(10, 4, 7, 'Stress Test Cases', 'Stress Test Cases', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(11, 6, 4, 'Appeal Responses', 'Appeal Responses', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(12, 6, 5, 'Appeal Responses', 'Appeal Responses', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(13, 6, 6, 'Appeal Responses', 'Appeal Responses', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(14, 6, 7, 'Appeal Responses', 'Appeal Responses', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(15, 7, 8, 'Aggregation', 'Aggregation', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(16, 8, 4, 'Aggregation Review', 'Aggregation Review', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(17, 8, 5, 'Aggregation Review', 'Aggregation Review', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(18, 8, 6, 'Aggregation Review', 'Aggregation Review', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(19, 8, 7, 'Aggregation Review', 'Aggregation Review', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(20, 9, 1, 'Final Fix', 'Final Fix', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(21, 9, 1, 'Scorecard Comment', 'Scorecard Comment', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(22, 10, 9, 'Final Review', 'Final Review', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(23, 11, 10, 'Approval', 'Approval', 1, 1, 'System', CURRENT, 'System', CURRENT);

INSERT INTO notification_type_lu(notification_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Timeline Notification', 'Timeline Notification', 'System', CURRENT, 'System', CURRENT);

INSERT INTO screening_status_lu(screening_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Pending', 'Pending', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_status_lu(screening_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Screening', 'Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_status_lu(screening_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Failed', 'Failed', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_status_lu(screening_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Passed', 'Passed', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_status_lu(screening_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Passed with Warning', 'Passed with Warning', 'System', CURRENT, 'System', CURRENT);

INSERT INTO response_severity_lu(response_severity_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Fatal Error', 'Fatal Error', 'System', CURRENT, 'System', CURRENT);
INSERT INTO response_severity_lu(response_severity_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Warning', 'Warning', 'System', CURRENT, 'System', CURRENT);
INSERT INTO response_severity_lu(response_severity_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Success', 'Success', 'System', CURRENT, 'System', CURRENT);

INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(1, 1, 'TCS-001', 'Your submission distribution is not a jar file.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(2, 1, 'TCS-002', 'Your submission distribution is not a zip file.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(3, 1, 'TCS-003', 'Your submission does not conform to the directory standard.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(4, 1, 'TCS-004', 'Your submission does not contain a component specification document in rich text format (rtf).', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(5, 1, 'TCS-005', 'Your submission does not contain a /log directory from the successful execution of the unit tests.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(6, 1, 'TCS-006', 'Your submission is missing the appropriate unit test log files.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(7, 1, 'TCS-007', 'Your submission does not contain a zargo or zuml file.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(8, 1, 'TCS-008', 'Your submission does not contain one or more use cases.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(9, 1, 'TCS-009', 'Your submission does not contain one or more class diagrams.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(10, 1, 'TCS-010', 'Your submission does not contain one or more sequence diagrams.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(11, 1, 'TCS-011', 'Your submission does not contain source code under /src.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(12, 1, 'TCS-012', 'Your submission does not contain test source code under /src.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(13, 2, 'TCS-013', 'Checkstyle has produced the following warnings.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(14, 2, 'TCS-014', 'Your submission contains personal information.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(15, 3, 'TCS-015', 'Your submission has passed the auto screening process.', 'System', CURRENT, 'System', CURRENT);
  
create table 'informix'.project_info_audit (
project_id int not null,
project_info_type_id int not null,
value varchar(255),
audit_action_type_id int not null,
action_date datetime year to fraction not null,
action_user_id decimal(12,0) not null
);

alter table 'informix'.project_info_audit add constraint foreign key 
(audit_action_type_id)
references 'informix'.audit_action_type_lu
(audit_action_type_id) 
constraint project_info_audit_audit_action_type_lu_fk;

alter table 'informix'.project_info_audit add constraint foreign key 
(project_id)
references 'informix'.project
(project_id) 
constraint project_info_audit_project_fk;

alter table 'informix'.project_info_audit add constraint foreign key 
(project_info_type_id)
references 'informix'.project_info_type_lu
(project_info_type_id) 
constraint project_info_audit_project_info_type_lu_fk;

------------------------------------------------------------------------------
create table 'informix'.project_phase_audit (
project_phase_id int not null,
scheduled_start_time datetime year to fraction,
scheduled_end_time datetime year to fraction,
audit_action_type_id int not null,
action_date datetime year to fraction not null,
action_user_id decimal(12,0) not null
);

alter table 'informix'.project_phase_audit add constraint foreign key 
(audit_action_type_id)
references 'informix'.audit_action_type_lu
(audit_action_type_id) 
constraint project_phase_audit_audit_action_type_lu_fk;

alter table 'informix'.project_phase_audit add constraint foreign key 
(project_phase_id)
references 'informix'.project_phase
(project_phase_id) 
constraint project_phase_audit_project_phase_fk;

-- Generic project type and category
ALTER TABLE project_type_lu ADD is_generic BOOLEAN DEFAULT 'f' NOT NULL;

INSERT INTO project_type_lu (project_type_id, name, description, is_generic, create_user, create_date, modify_user, modify_date)
       VALUES (4, 'Generic', 'Generic (can not have projects created of that type)', 't', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_category_lu (project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
       VALUES (28, 4, 'Generic Scorecards',
               'Generic scorecards are available for selection when creating projects of all categories',
               'System', CURRENT, 'System', CURRENT);

-- Approval and Post-Mortem scorecard types
UPDATE scorecard_type_lu SET name = 'Approval', description = 'Approval' WHERE scorecard_type_id = 3;

INSERT INTO scorecard_type_lu (scorecard_type_id, name, description, create_user, create_date, modify_user, modify_date)
       VALUES (4, 'Post-Mortem', 'Post-Mortem', 'System', CURRENT, 'System', CURRENT);

INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, per_submission,
                            required, create_user, create_date, modify_user, modify_date)
       VALUES (24, 12, 16, 'Post-Mortem Review', 'Post-Mortem Review', 0, 1, 'System', CURRENT, 'System', CURRENT );

-- Approver role is unbound from Approval phase
UPDATE resource_role_lu SET phase_type_id = NULL WHERE resource_role_id = 10;

-- for specification submission and specification review, added in version 1.4
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('submission_type_id_seq', 1, 1, 0);

insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(44, 'Post-Mortem Required', 'Post-Mortem Required', 'System', CURRENT, 'System', CURRENT);

INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Contest Submission', 'The contest submission', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Specification Submission', 'The specification submission', 'System', CURRENT, 'System', CURRENT);

INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(13, 'Specification Submission', 'Specification Submission', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(14, 'Specification Review', 'Specification Review', 'System', CURRENT, 'System', CURRENT);

INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(14, 'Specification Review Comment', 'Specification Review Comment', 'System', CURRENT, 'System', CURRENT);

INSERT INTO resource_role_lu(resource_role_id, phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(17, 13, 'Specification Submitter', 'Specification Submitter', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_role_lu(resource_role_id, phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(18, 14, 'Specification Reviewer', 'Specification Reviewer', 'System', CURRENT, 'System', CURRENT);

INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES (25, 13, 1, 'Specification Submission', 'Specification Submission', 0, 0, 'System', CURRENT, 'System', CURRENT );
INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES (26, 14, 15, 'Specification Review', 'Specification Review', 1, 1, 'System', CURRENT, 'System', CURRENT );


create table link_type_lu (
link_type_id INT not null,
link_type_name VARCHAR(64) not null,
allow_overlap DECIMAL(1)
);


create table linked_project_xref (
source_project_id INT,
dest_project_id INT,
link_type_id INT
);

INSERT INTO link_type_lu(link_type_id, link_type_name,allow_overlap) VALUES(1,'Depends On', 0);
INSERT INTO link_type_lu(link_type_id, link_type_name,allow_overlap) VALUES(2,'Is Related To', 1);
INSERT INTO link_type_lu(link_type_id, link_type_name,allow_overlap) VALUES(3,'Requires Spec Review', 0);
INSERT INTO link_type_lu(link_type_id, link_type_name,allow_overlap) VALUES(4,'For Design', 0);
INSERT INTO link_type_lu(link_type_id, link_type_name,allow_overlap) VALUES(5,'Repost For', 1);

