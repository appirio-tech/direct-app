------ project_payment_type_lu
create table 'informix'.project_payment_type_lu (
    project_payment_type_id serial NOT NULL,
    name VARCHAR(64) NOT NULL,
    mergeable boolean(1) NOT NULL
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
    create_date DATETIME YEAR TO FRACTION NOT NULL
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