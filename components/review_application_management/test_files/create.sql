create table 'informix'.project (
    project_id INT not null,
    project_status_id INT not null,
    project_category_id INT not null,
    project_studio_spec_id INTEGER,                        
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null,
    tc_direct_project_id INT
)
extent size 1000 next size 1000
lock mode row;   
revoke all on 'informix'.project from public;

create table 'informix'.project_phase (
    project_phase_id INT not null,
    project_id INT not null,
    phase_type_id INT not null,
    phase_status_id INT not null,
    fixed_start_time DATETIME YEAR TO FRACTION,
    scheduled_start_time DATETIME YEAR TO FRACTION not null,
    scheduled_end_time DATETIME YEAR TO FRACTION not null,
    actual_start_time DATETIME YEAR TO FRACTION,
    actual_end_time DATETIME YEAR TO FRACTION,
    duration DECIMAL(16,0) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
extent size 20000 next size 10000
lock mode row;
revoke all on 'informix'.project_phase from 'public';

create table 'informix'.phase_dependency (
    dependency_phase_id INT not null,
    dependent_phase_id INT not null,
    dependency_start DECIMAL(1,0) not null,
    dependent_start DECIMAL(1,0) not null,
    lag_time DECIMAL(16,0) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
extent size 20000 next size 10000
lock mode row;
revoke all on 'informix'.phase_dependency from 'public';

create table 'informix'.resource_role_lu (
    resource_role_id INT not null,
    phase_type_id INT,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
extent size 64 next size 64
lock mode row;
revoke all on 'informix'.resource_role_lu from 'public';



create table 'informix'.resource (
    resource_id INT not null,
    resource_role_id INT not null,
    project_id INT,
    project_phase_id INT,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
extent size 15000 next size 10000
lock mode row;
revoke all on resource from 'public';

create table 'informix'.phase_criteria (
    project_phase_id INT not null,
    phase_criteria_type_id INT not null,
    parameter VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
extent size 10000 next size 5000
lock mode row;
revoke all on phase_criteria from 'public';


------ review_auction_category_lu 
create table 'informix'.review_auction_category_lu (
    review_auction_category_id serial NOT NULL,
    name VARCHAR(64) NOT NULL
)
extent size 64 next size 64
lock mode row;

revoke all on review_auction_category_lu from 'public';

grant select,insert,update,delete on review_auction_category_lu to public as 'informix';

------ review_auction_type_lu
create table 'informix'.review_auction_type_lu (
    review_auction_type_id serial NOT NULL,
    name VARCHAR(64) NOT NULL,
    review_auction_category_id INT NOT NULL
)
extent size 64 next size 64
lock mode row;

revoke all on review_auction_type_lu from 'public';

grant select,insert,update,delete on review_auction_type_lu to public as 'informix';

------ review_application_role_lu
create table 'informix'.review_application_role_lu (
    review_application_role_id serial NOT NULL,
    name VARCHAR(64) NOT NULL,
    review_auction_type_id INT NOT NULL,
    positions INT NOT NULL,
    order_index INT NOT NULL
)
extent size 64 next size 64
lock mode row;

revoke all on review_application_role_lu from 'public';

grant select,insert,update,delete on review_application_role_lu to public as 'informix';

------ review_application_role_resource_role_xref
create table 'informix'.review_application_role_resource_role_xref (
    review_application_role_id INT NOT NULL,
    resource_role_id INT NOT NULL,
    unique_role BOOLEAN NOT NULL
)
extent size 64 next size 64
lock mode row;

revoke all on review_application_role_resource_role_xref from 'public';

grant select,insert,update,delete on review_application_role_resource_role_xref to public as 'informix';

------ review_application_status_lu
create table 'informix'.review_application_status_lu (
    review_application_status_id serial NOT NULL,
    name VARCHAR(64) NOT NULL
)
extent size 64 next size 64
lock mode row;

revoke all on review_application_status_lu from 'public';

grant select,insert,update,delete on review_application_status_lu to public as 'informix';

------ review_auction
create table 'informix'.review_auction (
    review_auction_id serial NOT NULL,
    project_id INT NOT NULL,
    review_auction_type_id INT NOT NULL
)
extent size 400 next size 200
lock mode row;

revoke all on review_auction from 'public';

grant select,insert,update,delete on review_auction to public as 'informix';

------ review_application
create table 'informix'.review_application (
    review_application_id serial NOT NULL,
    user_id DECIMAL(10,0) NOT NULL,
    review_auction_id INT NOT NULL,
    review_application_role_id INT NOT NULL,
    review_application_status_id INT NOT NULL,
    create_date DATETIME YEAR TO FRACTION NOT NULL
)
extent size 400 next size 200
lock mode row;

revoke all on review_application from 'public';

grant select,insert,update,delete on review_application to public as 'informix';

------ CONSTRAINTS

------ primary keys

alter table 'informix'.project add constraint primary key 
    (project_id)
    constraint pk_project;
    
alter table 'informix'.resource_role_lu add constraint primary key 
    (resource_role_id)
    constraint pk_resource_role_lu;
    
alter table 'informix'.resource add constraint primary key 
    (resource_id)
    constraint pk_resource;
    
alter table 'informix'.phase_criteria add constraint primary key 
    (project_phase_id, phase_criteria_type_id)
    constraint pk_phase_criteria;

alter table 'informix'.review_auction_category_lu add constraint primary key 
    (review_auction_category_id)
    constraint review_auction_category_lu_pk;

alter table 'informix'.review_auction_type_lu add constraint primary key 
    (review_auction_type_id)
    constraint review_auction_type_lu_pk;

alter table 'informix'.review_application_role_lu add constraint primary key 
    (review_application_role_id)
    constraint review_application_role_lu_pk;

alter table 'informix'.review_application_role_resource_role_xref add constraint primary key 
    (review_application_role_id, resource_role_id)
    constraint review_application_role_resource_role_xref_pk;

alter table 'informix'.review_application_status_lu add constraint primary key 
    (review_application_status_id)
    constraint review_application_status_lu_pk;

alter table 'informix'.review_auction add constraint primary key 
    (review_auction_id)
    constraint review_auction_pk;

alter table 'informix'.review_application add constraint primary key 
    (review_application_id)
    constraint review_application_pk;


------ foreign keys
alter table 'informix'.review_auction_type_lu add constraint foreign key 
    (review_auction_category_id)
    references 'informix'.review_auction_category_lu
    (review_auction_category_id) 
    constraint reviewauctiontypelu_reviewauctioncategorylu_fk;

alter table 'informix'.review_application_role_lu add constraint foreign key 
    (review_auction_type_id)
    references 'informix'.review_auction_type_lu
    (review_auction_type_id) 
    constraint reviewapplicationrolelu_reviewauctiontypelu_fk;

alter table 'informix'.review_application_role_resource_role_xref add constraint foreign key 
    (review_application_role_id)
    references 'informix'.review_application_role_lu
    (review_application_role_id) 
    constraint reviewapplicationroleresourcerolexref_reviewapplicationrolelu_fk;

alter table 'informix'.review_application_role_resource_role_xref add constraint foreign key 
    (resource_role_id)
    references 'informix'.resource_role_lu
    (resource_role_id) 
    constraint reviewapplicationroleresourcerolexref_resourcerolelu_fk;

alter table 'informix'.review_auction add constraint foreign key 
    (review_auction_type_id)
    references 'informix'.review_auction_type_lu
    (review_auction_type_id) 
    constraint reviewauction_reviewauctiontypelu_fk;

alter table 'informix'.review_auction add constraint foreign key 
    (project_id)
    references 'informix'.project
    (project_id) 
    constraint reviewauction_project_fk;

alter table 'informix'.review_application add constraint foreign key 
    (review_auction_id)
    references 'informix'.review_auction
    (review_auction_id) on delete cascade
    constraint reviewapplication_reviewauction_fk;

alter table 'informix'.review_application add constraint foreign key 
    (review_application_role_id)
    references 'informix'.review_application_role_lu
    (review_application_role_id) 
    constraint reviewapplication_reviewapplicationrolelu_fk;

alter table 'informix'.review_application add constraint foreign key 
    (review_application_status_id)
    references 'informix'.review_application_status_lu
    (review_application_status_id) 
    constraint reviewapplication_reviewapplicationstatuslu_fk;