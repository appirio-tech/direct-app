CREATE TABLE company (
company_id INTEGER NOT NULL,
name VARCHAR(64),
passcode VARCHAR(64),
creation_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
creation_user VARCHAR(64),
modification_user VARCHAR(64),
modification_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
is_deleted SMALLINT
);

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


CREATE TABLE client (
client_id INTEGER NOT NULL,
client_status_id INT NOT NULL,
name VARCHAR(64),
company_id INT,
payment_term_id INT,
salestax DECIMAL(7,3),
start_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
end_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
creation_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
creation_user VARCHAR(64),
modification_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
modification_user VARCHAR(64),
code_name VARCHAR(64),
is_deleted SMALLINT
);


CREATE TABLE project (
project_id serial NOT NULL,
project_status_id INT NOT NULL,
client_id INT NOT NULL,
company_id INT,
name VARCHAR(64),
active SMALLINT,
sales_tax DECIMAL(8,3),
po_box_number VARCHAR(20),
payment_terms_id INT,
description VARCHAR(255),
start_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
end_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
creation_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
creation_user VARCHAR(64),
modification_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
modification_user VARCHAR(64),
is_deleted SMALLINT,
parent_project_id INT,
is_manual_prize_setting SMALLINT
);

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

CREATE TABLE user_account (
	user_account_id int NOT NULL,
	company_id int,
	account_status_id int NOT NULL,
	user_name varchar(64) NOT NULL,
	password varchar(64) NOT NULL,
	creation_date DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION,
	creation_user varchar(64),
	modification_date DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION,
	modification_user varchar(64),
	user_status_id int,
	user_type_id int,
	billable_type int
);

CREATE TABLE project_manager (
	project_id int NOT NULL,
	user_account_id int NOT NULL,
	pay_rate decimal(9,2),
	cost decimal(5,2) NOT NULL,
	active smallint NOT NULL,
	creation_date DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION,
	creation_user varchar(64),
	modification_date DATETIME YEAR TO FRACTION DEFAULT CURRENT YEAR TO FRACTION,
	modification_user varchar(64)
);

create table project_contest_fee (
    project_contest_fee_id INT not null,
    project_id INT not null,
    contest_type VARCHAR(64) not null,
    sub_type VARCHAR(64),
    contest_fee DECIMAL(5,0),
    creation_user VARCHAR(64),
    creation_date DATETIME YEAR TO FRACTION,
    modification_user VARCHAR(64),
    modification_date DATETIME YEAR TO FRACTION,
    is_deleted SMALLINT,
    name VARCHAR(64)
);

create table project_worker (
    project_id INT not null,
    user_account_id INT not null,
    start_date DATETIME YEAR TO SECOND not null,
    end_date DATETIME YEAR TO SECOND not null,
    pay_rate DECIMAL(9,2),
    cost DECIMAL(5,2) not null,
    active SMALLINT not null,
    creation_date DATETIME YEAR TO SECOND not null,
    creation_user VARCHAR(64) not null,
    modification_date DATETIME YEAR TO SECOND not null,
    modification_user VARCHAR(64) not null
);


CREATE SYNONYM client_company FOR company;
CREATE SYNONYM client_project FOR project;


alter table user_account add constraint primary key 
	(user_account_id)
	constraint user_account_pk;

alter table project_manager add constraint primary key 
	(project_id, user_account_id)
	constraint project_manager_pk;

alter table company add constraint primary key 
	(company_id)
	constraint company_pk;
	
	
alter table client add constraint primary key 
	(client_id)
	constraint client_pk;

alter table project add constraint primary key 
	(project_id)
	constraint project_pk;
  	
	
alter table client_status add constraint primary key 
	(client_status_id)
	constraint client_status_pk;

alter table project_status add constraint primary key 
	(project_status_id)
	constraint project_status_pk;

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

alter table client add constraint FOREIGN KEY(company_id)
    REFERENCES company(company_id)
constraint client_company_FKIndex1;

alter table project add constraint FOREIGN KEY(company_id)
    REFERENCES company(company_id)
constraint project_company_FKIndex3;
 
ALTER TABLE user_account
	ADD CONSTRAINT FOREIGN KEY (company_id) 
	REFERENCES company (company_id);
 
ALTER TABLE project_manager
	ADD CONSTRAINT FOREIGN KEY (project_id) 
	REFERENCES project (project_id);
 
ALTER TABLE project_manager
	ADD CONSTRAINT FOREIGN KEY (user_account_id) 
	REFERENCES user_account (user_account_id);

alter table project_worker add constraint primary key 
	(project_id, user_account_id)
	constraint u172_253;

alter table project_worker add constraint foreign key 
	(user_account_id)
	references 'informix'.user_account
	(user_account_id) 
	constraint r172_580;

alter table project_worker add constraint foreign key 
	(project_id)
	references project
	(project_id) 
	constraint r172_579;




create sequence PROJECT_CONTEST_FEE_SEQ;


grant select on "informix".PROJECT_CONTEST_FEE_SEQ to "public" as "informix";
