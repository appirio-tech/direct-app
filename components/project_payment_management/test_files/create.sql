
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
create table 'informix'.submission (
    submission_id INT not null,
    upload_id INT not null,
    submission_status_id INT not null,
    screening_score DECIMAL(5,2),
    initial_score DECIMAL(5,2),
    final_score DECIMAL(5,2),
    placement DECIMAL(3,0),
    submission_type_id INTEGER NOT NULL,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null,
    user_rank DECIMAL(5,0),
    mark_for_purchase BOOLEAN(1),
    prize_id INTEGER,
    file_size DECIMAL(18,0),
    view_count DECIMAL(10,0),
    system_file_name varchar(254) 
)
extent size 5000 next size 2000
lock mode row;

revoke all on submission from 'public';
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

revoke all on resource_role_lu from 'public';
create table 'informix'.project_category_lu (
    project_category_id INT not null,
    project_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null,
    display boolean(1),
    display_order INT,
    project_catalog_id INT,
    version DECIMAL(12,0) default 0 not null
)
extent size 64 next size 64
lock mode row;

REVOKE ALL ON project_category_lu FROM 'public';
create table 'informix'.upload (
    upload_id INT not null,
    project_id INT not null,
    project_phase_id INT,
    resource_id INT not null,
    upload_type_id INT not null,
    upload_status_id INT not null,
    parameter VARCHAR(254) not null,
    upload_desc VARCHAR(254),
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)
extent size 20000 next size 10000
lock mode row;

revoke all on upload from 'public';


------ project_payment_type_lu
create table 'informix'.project_payment_type_lu (
    project_payment_type_id serial NOT NULL,
    name VARCHAR(64) NOT NULL,
    mergeable boolean(1) NOT NULL,
    pacts_payment_type_id DECIMAL(3,0) NOT NULL
)
extent size 64 next size 64
lock mode row;

revoke all on project_payment_type_lu from 'public';

grant select,insert,update,delete on project_payment_type_lu to public as 'informix';

------ project_payment
create table 'informix'.project_payment (
    project_payment_id serial NOT NULL,
    project_payment_type_id INT NOT NULL,
    resource_id INT NOT NULL,
    submission_id INT,
    amount DECIMAL(12,2) NOT NULL,
    pacts_payment_id DECIMAL(10,0),
    create_user VARCHAR(64) NOT NULL,
    create_date DATETIME YEAR TO FRACTION NOT NULL,
    modify_user VARCHAR(64) NOT NULL, 
    modify_date DATETIME YEAR TO FRACTION NOT NULL
)
extent size 400 next size 200
lock mode row;

revoke all on project_payment from 'public';

grant select,insert,update,delete on project_payment to public as 'informix';

------ project_payment_adjustment
create table 'informix'.project_payment_adjustment (
    project_id INT NOT NULL,
    resource_role_id INT NOT NULL,
    fixed_amount DECIMAL(12,2),
    multiplier FLOAT
)
extent size 400 next size 200
lock mode row;

revoke all on project_payment_adjustment from 'public';

grant select,insert,update,delete on project_payment_adjustment to public as 'informix';

------ default_project_payment
create table 'informix'.default_project_payment (
    project_category_id INT NOT NULL,
    resource_role_id INT NOT NULL,
    fixed_amount DECIMAL(12,2) NOT NULL,
    base_coefficient FLOAT NOT NULL,
    incremental_coefficient FLOAT NOT NULL
)
extent size 64 next size 64
lock mode row;

revoke all on default_project_payment from 'public';

grant select,insert,update,delete on default_project_payment to public as 'informix';

------ CONSTRAINTS

------ primary keys

alter table 'informix'.resource add constraint primary key 
    (resource_id)
    constraint pk_resource;
    
alter table 'informix'.submission add constraint primary key 
    (submission_id)
    constraint pk_submission;
    
alter table 'informix'.project add constraint primary key 
    (project_id)
    constraint pk_project;
    
alter table 'informix'.resource_role_lu add constraint primary key 
    (resource_role_id)
    constraint pk_resource_role_lu;
    
alter table 'informix'.project_category_lu add constraint primary key 
    (project_category_id)
    constraint pk_project_category_lu;
    
alter table 'informix'.upload add constraint primary key 
    (upload_id)
    constraint pk_upload;
    
    
alter table 'informix'.project_payment_type_lu add constraint primary key 
    (project_payment_type_id)
    constraint project_payment_type_lu_pk;

alter table 'informix'.project_payment add constraint primary key 
    (project_payment_id)
    constraint project_payment_pk;

alter table 'informix'.project_payment_adjustment add constraint primary key 
    (project_id, resource_role_id)
    constraint project_payment_adjustment_pk;

alter table 'informix'.default_project_payment add constraint primary key 
    (project_category_id, resource_role_id)
    constraint default_project_payment_pk;


------ foreign keys
alter table 'informix'.project_payment add constraint foreign key 
    (project_payment_type_id)
    references 'informix'.project_payment_type_lu
    (project_payment_type_id) 
    constraint projectpayment_projectpaymenttypelu_fk;

alter table 'informix'.project_payment add constraint foreign key 
    (resource_id)
    references 'informix'.resource
    (resource_id) 
    constraint projectpayment_resource_fk;

alter table 'informix'.project_payment add constraint foreign key 
    (submission_id)
    references 'informix'.submission
    (submission_id) 
    constraint projectpayment_submission_fk;

alter table 'informix'.project_payment_adjustment add constraint foreign key 
    (project_id)
    references 'informix'.project
    (project_id) 
    constraint projectpaymentadjustment_project_fk;

alter table 'informix'.project_payment_adjustment add constraint foreign key 
    (resource_role_id)
    references 'informix'.resource_role_lu
    (resource_role_id) 
    constraint projectpaymentadjustment_resourcerolelu_fk;

alter table 'informix'.default_project_payment add constraint foreign key 
    (project_category_id)
    references 'informix'.project_category_lu
    (project_category_id) 
    constraint defaultprojectpayment_projectcategorylu_fk;

alter table 'informix'.default_project_payment add constraint foreign key 
    (resource_role_id)
    references 'informix'.resource_role_lu
    (resource_role_id) 
    constraint defaultprojectpayment_resourcerolelu_fk;