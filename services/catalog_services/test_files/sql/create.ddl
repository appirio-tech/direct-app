--------------- comp_catalog ------------------
create table comp_catalog (
    component_id DECIMAL(12,0) not null,
    current_version DECIMAL(12,0) not null,
    short_desc lvarchar,
    component_name VARCHAR(254) not null,
    description lvarchar(10000),
    function_desc lvarchar,
    create_time DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    status_id DECIMAL(12,0) not null,
    root_category_id DECIMAL(12,0),
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null
);

create index comp_catalog_root_cat_id_idx on comp_catalog
    (
    root_category_id
    );

alter table comp_catalog add constraint primary key 
    (component_id)
    constraint pk_comp_catalog;


--------------- categories ------------------
create table categories (
    category_id DECIMAL(12,0) not null,
    parent_category_id DECIMAL(12,0),
    category_name VARCHAR(100) not null,
    description VARCHAR(254) not null,
    status_id DECIMAL(12,0) not null,
    viewable DECIMAL(1,0) default 1
);

alter table categories add constraint primary key 
    (category_id)
    constraint pk_categories;


--------------- comp_categories ------------------
create table comp_categories (
    comp_categories_id DECIMAL(12,0) not null,
    component_id DECIMAL(12,0),
    category_id DECIMAL(12,0)
);

create unique cluster index comp_ctgy_i1 on comp_categories
    (
    component_id, 
    category_id
    );

alter table comp_categories add constraint primary key 
    (comp_categories_id)
    constraint pk_comp_categories;

alter table comp_categories add constraint foreign key 
    (category_id)
    references categories
    (category_id) 
    constraint fk_comp_ctgy1;

alter table comp_categories add constraint foreign key 
    (component_id)
    references comp_catalog
    (component_id) 
    constraint fk_comp_ctgy2;


--------------- catalog ------------------
create table catalog (
    catalog_id DECIMAL(12,0) not null,
    catalog_name VARCHAR(100) not null
);

alter table catalog add constraint primary key 
    (catalog_id)
    constraint catalog_pk;

--------------- category_catalog ------------------
create table category_catalog (
    catalog_id DECIMAL(12,0) not null,
    category_id DECIMAL(12,0) not null
);

alter table category_catalog add constraint primary key 
    (category_id)
    constraint catalog_category_xref_pk;

alter table category_catalog add constraint foreign key 
    (catalog_id)
    references catalog
    (catalog_id) 
    constraint category_catalog_catalog_fk;

alter table category_catalog add constraint foreign key 
    (category_id)
    references categories
    (category_id) 
    constraint category_catalog_category_fk;



--------------- phase ------------------
create table phase (
    phase_id DECIMAL(12,0) not null,
    description VARCHAR(254) not null
);

alter table phase add constraint primary key 
    (phase_id)
    constraint pk_phase;


--------------- comp_versions ------------------
create table comp_versions (
    comp_vers_id DECIMAL(12,0) not null,
    component_id DECIMAL(12,0),
    version DECIMAL(12,0) not null,
    version_text CHAR(20) not null,
    create_time DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    phase_id DECIMAL(12,0) not null,
    phase_time DATETIME YEAR TO FRACTION not null,
    price DECIMAL(10,2) not null,
    comments lvarchar,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    suspended_ind DECIMAL(1,0) default 0
);

create unique cluster index comp_versions_i2 on comp_versions
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

alter table comp_versions add constraint foreign key 
    (phase_id)
    references phase
    (phase_id) 
    constraint fk_comp_phase;


--------------- technology_types ------------------
create table technology_types (
    technology_type_id DECIMAL(12,0) not null,
    technology_name VARCHAR(100) not null,
    description VARCHAR(254) not null,
    status_id DECIMAL(12,0) not null
);

alter table technology_types add constraint primary key 
    (technology_type_id)
    constraint pk_technology_type;
    
    
    
--------------- comp_technology ------------------
create table comp_technology (
    comp_tech_id DECIMAL(12,0) not null,
    comp_vers_id DECIMAL(12,0),
    technology_type_id DECIMAL(12,0)
);

create unique cluster index comp_tech_i1 on comp_technology
    (
    comp_vers_id, 
    technology_type_id
    );

alter table comp_technology add constraint primary key 
    (comp_tech_id)
    constraint pk_comp_technology;

alter table comp_technology add constraint foreign key 
    (comp_vers_id)
    references comp_versions
    (comp_vers_id) 
    constraint fk_comp_tech1;

alter table comp_technology add constraint foreign key 
    (technology_type_id)
    references technology_types
    (technology_type_id) 
    constraint fk_comp_tech2;
    
    
--------------- comp_version_dates ------------------    
create table comp_version_dates (
    comp_version_dates_id DECIMAL(12,0) not null,
    comp_vers_id DECIMAL(12,0) not null,
    phase_id DECIMAL(12,0) not null,
    posting_date DATETIME YEAR TO DAY not null,
    initial_submission_date DATETIME YEAR TO DAY not null,
    winner_announced_date DATETIME YEAR TO DAY not null,
    final_submission_date DATETIME YEAR TO DAY not null,
    estimated_dev_date DATETIME YEAR TO DAY,
    price DECIMAL(10,2) not null,
    total_submissions DECIMAL(12,0) default 0 not null,
    status_id DECIMAL(12,0) default 301 not null,
    create_time DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    level_id DECIMAL(12,0),
    screening_complete_date DATETIME YEAR TO DAY,
    review_complete_date DATETIME YEAR TO DAY,
    aggregation_complete_date DATETIME YEAR TO DAY,
    phase_complete_date DATETIME YEAR TO DAY,
    production_date DATETIME YEAR TO DAY,
    aggregation_complete_date_comment VARCHAR(254),
    phase_complete_date_comment VARCHAR(254),
    review_complete_date_comment VARCHAR(254),
    winner_announced_date_comment VARCHAR(254),
    initial_submission_date_comment VARCHAR(254),
    screening_complete_date_comment VARCHAR(254),
    final_submission_date_comment VARCHAR(254),
    production_date_comment VARCHAR(254),
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null
);


create index compverdates_idx2 on comp_version_dates
    (
    comp_vers_id, 
    phase_id
    );

alter table comp_version_dates add constraint primary key 
    (comp_version_dates_id)
    constraint pk_comp_version_dates_id;

        
--------------- client ------------------    
create table client (
    client_id INT not null
);

alter table client add constraint primary key 
    (client_id)
    constraint client_pk;
    

--------------- user_client ------------------    
create table user_client (
    user_id DECIMAL(10,0) not null,
    client_id INT not null,
    admin_ind DECIMAL(1,0)
);

alter table user_client add constraint primary key 
    (user_id, client_id)
    constraint user_client_pk;
       


--------------- comp_client ------------------    
create table comp_client (
    component_id DECIMAL(12,0) not null,
    client_id INT not null
);


alter table comp_client add constraint primary key 
    (component_id, client_id)
    constraint comp_client_pk;
    

--------------- comp_user ------------------    
create table comp_user (
    component_id DECIMAL(12,0) not null,
    user_id DECIMAL(10,0) not null
);


alter table comp_user add constraint primary key 
    (component_id, user_id)
    constraint comp_user_pk;



--------------- comp_jive_category_xref ------------------    
create table comp_jive_category_xref (
    comp_vers_id DECIMAL(12,0) not null,
    jive_category_id INT not null
);

alter table comp_jive_category_xref add constraint primary key 
    (jive_category_id, comp_vers_id)
    constraint pk_comp_jive_xr643;

alter table comp_jive_category_xref add constraint foreign key 
    (comp_vers_id)
    references comp_versions
    (comp_vers_id) 
    constraint fk_comp_jive_ca109;


--------------- comp_link ------------------    
create table comp_link (
    comp_link_id DECIMAL(12,0) not null,
    comp_vers_id DECIMAL(12,0) not null,
    link VARCHAR(254) not null
);

alter table comp_link add constraint primary key 
    (comp_link_id)
    constraint comp_link_pk;

alter table comp_link add constraint foreign key 
    (comp_vers_id)
    references comp_versions
    (comp_vers_id) 
    constraint comp_link_vers_fk;
    
--------------- doc_types ------------------
create table doc_types (
document_type_id DECIMAL(12,0) not null,
description VARCHAR(254) not null,
status_id DECIMAL(12,0) not null
);
alter table doc_types add constraint primary key
(document_type_id)
constraint pk_doc_types;
--------------- comp_documentation ------------------
create table comp_documentation (
document_id DECIMAL(12,0) not null,
comp_vers_id DECIMAL(12,0),
document_type_id DECIMAL(12,0),
document_name VARCHAR(254) not null,
url VARCHAR(254) not null
);
alter table comp_documentation add constraint primary key
(document_id)
constraint pk_comp_documentat;
alter table comp_documentation add constraint foreign key
(comp_vers_id)
references comp_versions
(comp_vers_id)
constraint fk_comp_doc1;
alter table comp_documentation add constraint foreign key
(document_type_id)
references doc_types
(document_type_id)
constraint fk_comp_doc_ref2;


--------------- comp_dependencies ------------------
create table comp_dependencies (
    comp_dependency_id DECIMAL(12,0) not null,
    comp_vers_id DECIMAL(12,0),
    child_comp_vers_id DECIMAL(12,0)
);

alter table comp_dependencies add constraint primary key 
    (comp_dependency_id)
    constraint pk_comp_dependenci;

alter table comp_dependencies add constraint foreign key 
    (comp_vers_id)
    references comp_versions
    (comp_vers_id) 
    constraint fk_comp_depend1;

alter table comp_dependencies add constraint foreign key 
    (child_comp_vers_id)
    references comp_versions
    (comp_vers_id) 
    constraint fk_comp_depend2;
