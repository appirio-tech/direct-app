

CREATE TABLE user (
   user_id  SERIAL NOT NULL,
   email_address VARCHAR(64),
   handle    VARCHAR(64)
);

alter table user add constraint primary key 
	(user_id)
	constraint pk_user;

CREATE TABLE project (
	project_id SERIAL NOT NULL,
    project_name VARCHAR(80) not null
);

alter table project add constraint primary key 
	(project_id)
	constraint pk_project;
	
create table project_milestone (
    project_milestone_id Serial NOT NULL,
    name VARCHAR(80) not null
);

alter table project_milestone add constraint primary key 
	(project_milestone_id)
	constraint pk_project_milestone;

create table project_milestone_owner (
    project_milestone_owner_id Serial NOT NULL,
    user_id INT NOT NULL,
    user_handle VARCHAR(45) NOT NULL,
    project_milestone_id INT NOT NULL
);

alter table project_milestone_owner add constraint primary key 
	(project_milestone_owner_id)
	constraint pk_project_milestone_owner;

create table direct_project_task_list (
    direct_project_task_list_id Serial NOT NULL,
    project_id INT NOT NULL,
    name VARCHAR(80) NOT NULL,
    notes VARCHAR(250),
    create_date DATETIME YEAR TO FRACTION NOT NULL,
    create_user VARCHAR(64) NOT NULL,
    modify_date DATETIME YEAR TO FRACTION,
    modify_user VARCHAR(64),
    is_active CHAR DEFAULT 'Y' NOT NULL,
    is_default CHAR DEFAULT 'N' NOT NULL
);

alter table direct_project_task_list add constraint primary key 
	(direct_project_task_list_id)
	constraint pk_direct_project_task_list;
	
	
create table direct_project_task_list_permitted_user (
    direct_project_task_list_id INT NOT NULL,
    user_id INT NOT NULL
);

alter table direct_project_task_list_permitted_user add constraint primary key 
	(direct_project_task_list_id, user_id)
	constraint pk_direct_project_task_list_permitted_user;

	
create table direct_project_task_status (
    direct_project_task_status_id int NOT NULL,
    name VARCHAR(64) NOT NULL
);

alter table direct_project_task_status add constraint primary key 
	(direct_project_task_status_id)
	constraint pk_direct_project_task_status_id;

create table direct_project_task_priority (
    direct_project_task_priority_id int NOT NULL,
    name VARCHAR(64) NOT NULL
);

alter table direct_project_task_priority add constraint primary key 
	(direct_project_task_priority_id)
	constraint pk_direct_project_task_priority;


create table direct_project_task (
    direct_project_task_id Serial NOT NULL,
    direct_project_task_list_id INT NOT NULL,
    name VARCHAR(80) NOT NULL,
    notes VARCHAR(250),
    create_date DATETIME YEAR TO FRACTION NOT NULL,
    create_user VARCHAR(64) NOT NULL,
    modify_date DATETIME YEAR TO FRACTION,
    modify_user VARCHAR(64),
    start_date DATETIME YEAR TO FRACTION,
    due_date DATETIME YEAR TO FRACTION,
    status INT NOT NULL,
    priority INT NOT NULL
);

alter table direct_project_task add constraint primary key 
	(direct_project_task_id)
	constraint pk_direct_project_task;
	
create table direct_project_task_attachment (
    direct_project_task_attachment_id Serial NOT NULL,
    direct_project_task_id INT NOT NULL,
    file_name VARCHAR(64) NOT NULL,
    mime_type VARCHAR(64) NOT NULL,
    create_date DATETIME YEAR TO FRACTION NOT NULL,
    create_user VARCHAR(64) NOT NULL,
    modify_date DATETIME YEAR TO FRACTION,
    modify_user VARCHAR(64)
);

alter table direct_project_task_attachment add constraint primary key 
	(direct_project_task_attachment_id)
	constraint pk_direct_project_task_attachment;

create table direct_project_task_contest_xref (
    direct_project_task_id INT NOT NULL,
    project_id INT NOT NULL
);

alter table direct_project_task_contest_xref add constraint primary key 
	(direct_project_task_id, project_id)
	constraint pk_direct_project_task_contest_xref;

create table direct_project_task_assignee (
    direct_project_task_id INT NOT NULL,
    user_id INT NOT NULL
);

alter table direct_project_task_assignee add constraint primary key 
	(direct_project_task_id, user_id)
	constraint pk_direct_project_task_assignee;


create table direct_project_task_milestone_xref (
    direct_project_task_id INT NOT NULL,
    project_milestone_id INT NOT NULL
);

alter table direct_project_task_milestone_xref add constraint primary key 
	(direct_project_task_id, project_milestone_id)
	constraint pk_direct_project_task_milestone_xref;


create table direct_project_task_list_contest_xref (
    direct_project_task_list_id INT NOT NULL,
    project_id INT NOT NULL
);

alter table direct_project_task_list_contest_xref add constraint primary key 
	(direct_project_task_list_id, project_id)
	constraint pk_direct_project_task_list_contest_xref;

create table direct_project_task_list_milestone_xref (
    direct_project_task_list_id INT NOT NULL,
    project_milestone_id INT NOT NULL
);

alter table direct_project_task_list_milestone_xref add constraint primary key 
	(direct_project_task_list_id, project_milestone_id)
	constraint pk_direct_project_task_list_milestone_xref;

	
	
alter table direct_project_task_list add constraint foreign key 
	(project_id)
	references project
	(project_id) 
	constraint fk_task_list_to_direct_project;

alter table direct_project_task_list_permitted_user add constraint foreign key 
	(user_id)
	references user
	(user_id) 
	constraint fk_task_list_permitted_user_to_user;

alter table direct_project_task_list_permitted_user add constraint foreign key 
	(direct_project_task_list_id)
	references direct_project_task_list
	(direct_project_task_list_id) 
	constraint fk_task_list_permitted_user_to_task_list;

alter table direct_project_task add constraint foreign key 
	(direct_project_task_list_id)
	references direct_project_task_list
	(direct_project_task_list_id) 
	constraint fk_task_to_task_list;

alter table direct_project_task add constraint foreign key 
	(status)
	references direct_project_task_status
	(direct_project_task_status_id) 
	constraint fk_task_to_task_status;

alter table direct_project_task add constraint foreign key 
	(priority)
	references direct_project_task_priority
	(direct_project_task_priority_id) 
	constraint fk_task_to_task_priority;

alter table direct_project_task_attachment add constraint foreign key 
	(direct_project_task_id)
	references direct_project_task
	(direct_project_task_id) 
	constraint fk_task_attachment_to_task;

alter table direct_project_task_contest_xref add constraint foreign key 
	(direct_project_task_id)
	references direct_project_task
	(direct_project_task_id) 
	constraint fk_task_contest_xref_to_task;

alter table direct_project_task_contest_xref add constraint foreign key 
	(project_id)
	references project
	(project_id) 
	constraint fk_task_contest_xref_to_project;

alter table direct_project_task_assignee add constraint foreign key 
	(direct_project_task_id)
	references direct_project_task
	(direct_project_task_id) 
	constraint fk_task_assignee_to_task;

alter table direct_project_task_assignee add constraint foreign key 
	(user_id)
	references user
	(user_id) 
	constraint fk_task_assignee_to_user;

alter table direct_project_task_milestone_xref add constraint foreign key 
	(direct_project_task_id)
	references direct_project_task
	(direct_project_task_id) 
	constraint fk_task_milestone_xref_to_task;

alter table direct_project_task_milestone_xref add constraint foreign key 
	(project_milestone_id)
	references project_milestone
	(project_milestone_id) 
	constraint fk_task_milestone_xref_to_project;


alter table direct_project_task_list_contest_xref add constraint foreign key 
	(project_id)
	references project
	(project_id) 
	constraint fk_task_list_contest_xref_to_project;

alter table direct_project_task_list_contest_xref add constraint foreign key 
	(direct_project_task_list_id)
	references direct_project_task_list
	(direct_project_task_list_id) 
	constraint fk_task_list_context_xref_to_task_list;

alter table direct_project_task_list_milestone_xref add constraint foreign key 
	(project_milestone_id)
	references project_milestone
	(project_milestone_id) 
	constraint fk_task_list_milestone_xref_to_project;

alter table direct_project_task_list_milestone_xref add constraint foreign key 
	(direct_project_task_list_id)
	references direct_project_task_list
	(direct_project_task_list_id) 
	constraint fk_task_list_milestone_xref_to_task_list;
	
alter table direct_project_task lock mode(row);
alter table direct_project_task_list lock mode(row);