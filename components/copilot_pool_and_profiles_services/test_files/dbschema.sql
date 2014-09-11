-- -----------------------------------------------------
-- Table copilot_profile_status
-- -----------------------------------------------------
CREATE TABLE copilot_profile_status (
  copilot_profile_status_id DECIMAL(10,0) NOT NULL,
  name VARCHAR(20) NOT NULL,
  create_user VARCHAR(45) NOT NULL,
  create_date DATETIME YEAR TO FRACTION NOT NULL,
  modify_user VARCHAR(45) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION NOT NULL,
  PRIMARY KEY (copilot_profile_status_id) );

-- -----------------------------------------------------
-- Table copilot_profile
-- -----------------------------------------------------
CREATE TABLE copilot_profile (
  copilot_profile_id DECIMAL(10,0) NOT NULL,
  user_id DECIMAL(10,0) NOT NULL,
  copilot_profile_status_id DECIMAL(10,0) NOT NULL,
  suspension_count INTEGER NOT NULL,
  reliability DECIMAL(4,2) NOT NULL,
  activation_date DATETIME YEAR TO FRACTION,
  show_copilot_earnings CHAR(1) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION NOT NULL,
  PRIMARY KEY (copilot_profile_id))
;

ALTER TABLE copilot_profile ADD CONSTRAINT
    FOREIGN KEY (copilot_profile_status_id )
    REFERENCES copilot_profile_status(copilot_profile_status_id ) ON DELETE CASCADE CONSTRAINT fk_copilot_profile_copilot_profile_status;

-- -----------------------------------------------------
-- Table copilot_project_status
-- -----------------------------------------------------
CREATE TABLE copilot_project_status (
  copilot_project_status_id DECIMAL(10,0) NOT NULL,
  name VARCHAR(10) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION NOT NULL,
  PRIMARY KEY (copilot_project_status_id) )
;


-- -----------------------------------------------------
-- Table copilot_type
-- -----------------------------------------------------
CREATE TABLE copilot_type (
  copilot_type_id DECIMAL(10, 0) NOT NULL,
  name VARCHAR(20) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  modify_date VARCHAR(64) NOT NULL,
  PRIMARY KEY (copilot_type_id) )
;


-- -----------------------------------------------------
-- Table copilot_project
-- -----------------------------------------------------
CREATE TABLE copilot_project (
  copilot_project_id DECIMAL(10,0) NOT NULL,
  copilot_profile_id DECIMAL(10,0) NOT NULL,
  name VARCHAR(255),
  tc_direct_project_id DECIMAL(10,0) NOT NULL,
  copilot_type_id DECIMAL(10,0) NOT NULL,
  copilot_project_status_id DECIMAL(10,0) NOT NULL,
  customer_feedback LVARCHAR(4096),
  customer_rating DECIMAL(2,1),
  pm_feedback LVARCHAR(4096),
  pm_rating DECIMAL(2,1),
  private_project CHAR(1) NOT NULL,
  completion_date DATETIME YEAR TO FRACTION,
  create_user VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION NOT NULL,
  PRIMARY KEY (copilot_project_id))
;

ALTER TABLE copilot_project ADD CONSTRAINT
    FOREIGN KEY (copilot_project_status_id )
    REFERENCES copilot_project_status(copilot_project_status_id ) ON DELETE CASCADE CONSTRAINT fk_copilot_project_copilot_project_status;
ALTER TABLE copilot_project ADD CONSTRAINT
    FOREIGN KEY (copilot_profile_id )
    REFERENCES copilot_profile(copilot_profile_id ) ON DELETE CASCADE CONSTRAINT fk_copilot_project_copilot_profile;
ALTER TABLE copilot_project ADD CONSTRAINT
    FOREIGN KEY (copilot_type_id )
    REFERENCES copilot_type(copilot_type_id ) ON DELETE CASCADE CONSTRAINT fk_copilot_project_copilot_type;

-- -----------------------------------------------------
-- Table copilot_project_info_type
-- -----------------------------------------------------
CREATE TABLE copilot_project_info_type (
  copilot_project_info_type_id DECIMAL(10,0) NOT NULL,
  name VARCHAR(20) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION NOT NULL,
  PRIMARY KEY (copilot_project_info_type_id) )
;


-- -----------------------------------------------------
-- Table copilot_project_info
-- -----------------------------------------------------
CREATE TABLE copilot_project_info (
  copilot_project_info_id DECIMAL(10,0) NOT NULL,
  copilot_project_id DECIMAL(10,0) NOT NULL,
  copilot_project_info_type_id DECIMAL(10,0) NOT NULL,
  value VARCHAR(100) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION NOT NULL,
  PRIMARY KEY (copilot_project_info_id))
;

ALTER TABLE copilot_project_info ADD CONSTRAINT
    FOREIGN KEY (copilot_project_id )
    REFERENCES copilot_project(copilot_project_id ) ON DELETE CASCADE CONSTRAINT fk_copilot_project_info_copilot_project;
ALTER TABLE copilot_project_info ADD CONSTRAINT
    FOREIGN KEY (copilot_project_info_type_id )
    REFERENCES copilot_project_info_type(copilot_project_info_type_id ) ON DELETE CASCADE CONSTRAINT fk_copilot_project_info_copilot_project_info_type;

-- -----------------------------------------------------
-- Table copilot_project_plan
-- -----------------------------------------------------
CREATE TABLE copilot_project_plan (
  copilot_project_plan_id DECIMAL(10,0) NOT NULL,
  copilot_project_id DECIMAL(10,0) NOT NULL,
  planned_duration INTEGER NOT NULL,
  planned_bug_races INTEGER NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION NOT NULL,
  PRIMARY KEY (copilot_project_plan_id));

ALTER TABLE copilot_project_plan ADD CONSTRAINT
    FOREIGN KEY (copilot_project_id)
    REFERENCES copilot_project(copilot_project_id) ON DELETE CASCADE CONSTRAINT fk_copilot_project_plan_copilot_project;

-- -----------------------------------------------------
-- Table planned_contest
-- -----------------------------------------------------
CREATE TABLE planned_contest (
  planned_contest_id DECIMAL(10,0) NOT NULL,
  name VARCHAR(45) NOT NULL,
  description VARCHAR(200),
  project_category_id DECIMAL(10,0) NOT NULL,
  copilot_project_plan_id DECIMAL(10,0) NOT NULL,
  start_date DATETIME YEAR TO FRACTION NOT NULL,
  end_date DATETIME YEAR TO FRACTION NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION NOT NULL,
  PRIMARY KEY (planned_contest_id));

ALTER TABLE planned_contest ADD CONSTRAINT
    FOREIGN KEY (copilot_project_plan_id )
    REFERENCES copilot_project_plan(copilot_project_plan_id ) ON DELETE CASCADE CONSTRAINT fk_planned_contest_copilot_project_plan;

-- -----------------------------------------------------
-- Table copilot_profile_info_type
-- -----------------------------------------------------
CREATE TABLE copilot_profile_info_type (
  copilot_profile_info_type_id DECIMAL(10,0) NOT NULL,
  name VARCHAR(20) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION NOT NULL,
  PRIMARY KEY (copilot_profile_info_type_id) );


-- -----------------------------------------------------
-- Table copilot_profile_info
-- -----------------------------------------------------
CREATE TABLE copilot_profile_info (
  copilot_profile_info_id DECIMAL(10,0) NOT NULL,
  copilot_profile_info_type_id DECIMAL(10,0) NOT NULL,
  value VARCHAR(100) NOT NULL,
  create_user VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION NOT NULL,
  copilot_profile_id DECIMAL(10,0) NOT NULL,
  PRIMARY KEY (copilot_profile_info_id));

ALTER TABLE copilot_profile_info ADD CONSTRAINT
    FOREIGN KEY (copilot_profile_info_type_id )
    REFERENCES copilot_profile_info_type(copilot_profile_info_type_id ) ON DELETE CASCADE CONSTRAINT fk_copilot_profile_info_copilot_project_info_type;
ALTER TABLE copilot_profile_info ADD CONSTRAINT
    FOREIGN KEY (copilot_profile_id )
    REFERENCES copilot_profile(copilot_profile_id ) ON DELETE CASCADE CONSTRAINT fk_copilot_profile_info_copilot_profile;

-- -----------------------------------------------------
-- TCS TABLES
-- -----------------------------------------------------
create table payment (
    payment_id DECIMAL(10,0),
    payment_desc VARCHAR(100),
    payment_type_id DECIMAL(3,0),
    payment_type_desc VARCHAR(100),
    reference_id DECIMAL(10,0),
    parent_payment_id DECIMAL(10,0),
    charity_ind DECIMAL(1,0) default 1 not null,
    show_in_profile_ind DECIMAL(1,0) default 1 not null,
    show_details_ind DECIMAL(1,0) default 1 not null,
    payment_status_id DECIMAL(3,0),
    payment_status_desc VARCHAR(200),
    client VARCHAR(100),
    modified_calendar_id DECIMAL(10,0),
    modified_time_id DECIMAL(10,0),
    installment_number DECIMAL(3,0),
    primary key(payment_id)
);

create table user_payment (
    payment_id DECIMAL(10,0),
    user_id DECIMAL(10,0),
    net_amount DECIMAL(12,2),
    gross_amount DECIMAL(12,2),
    due_calendar_id DECIMAL(10,0),
    paid_calendar_id DECIMAL(10,0),
    total_amount DECIMAL(12,2),
    primary key(payment_id)
);

create table project (
    project_id INT not null,
    project_status_id INT not null,
    project_category_id INT not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null,
    tc_direct_project_id INT,
    primary key(project_id)
);

create table resource (
    resource_id INT not null,
    resource_role_id INT not null,
    project_id INT,
    project_phase_id INT,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null,
    primary key(resource_id)
);

create table resource_info (
    resource_id INT not null,
    resource_info_type_id INT not null,
    value VARCHAR(255) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null,
    primary key(resource_id)
);

create table customfield (
  id decimal(18,0) not null,
  customfieldtypekey varchar(255),
  customfieldsearcherkey varchar(255),
  cfname varchar(255),
  description text,
  defaultvalue varchar(255),
  fieldtype decimal(18,0),
  project decimal(18,0),
  issuetype varchar(255),
  primary key (id)
);

create table customfieldvalue (
  id decimal(18,0) not null,
  issue decimal(18,0),
  customfield decimal(18,0),
  parentkey varchar(255),
  stringvalue varchar(255),
  numbervalue decimal(18,6),
  textvalue lvarchar(4096),
  datevalue datetime year to fraction,
  valuetype varchar(255),
  primary key (id)
);

create table jiraissue (
  id decimal(18,0) not null,
  pkey varchar(255),
  project decimal(18,0),
  reporter varchar(255),
  assignee varchar(255),
  issuetype varchar(255),
  summary varchar(255),
  description lvarchar(4096),
  environment lvarchar(4096),
  priority varchar(255),
  resolution varchar(255),
  issuestatus varchar(255),
  created datetime year to fraction,
  updated datetime year to fraction,
  duedate datetime year to fraction,
  resolutiondate datetime year to fraction,
  votes decimal(18,0),
  timeoriginalestimate decimal(18,0),
  timeestimate decimal(18,0),
  timespent decimal(18,0),
  workflow_id decimal(18,0),
  security decimal(18,0),
  fixfor decimal(18,0),
  component decimal(18,0),
  primary key (id)
);
