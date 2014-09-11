CREATE DATABASE common_oltp IN ol_topcoder WITH BUFFERED LOG;
database common_oltp;

grant dba to informix ;
grant connect to coder ;
grant connect to db_sales_im ;
grant connect to veredox ;
grant connect to openaim ;
grant connect to truveo ;
grant connect to cockpit ;
grant connect to winformula ;
grant connect to openxtraz ;
-- User public does not have connect privilege;
create table 'informix'.security_user (
    login_id DECIMAL(12,0) not null,
    user_id VARCHAR(50) not null,
    password VARCHAR(50) not null,
    create_user_id DECIMAL(12,0)
)
extent size 5000 next size 2500
lock mode row;

revoke all on security_user from 'public';
create table 'informix'.user_role_xref (
    user_role_id DECIMAL(12,0) not null,
    login_id DECIMAL(12,0),
    role_id DECIMAL(12,0),
    create_user_id DECIMAL(12,0),
    security_status_id DECIMAL(3,0)
)
extent size 1500 next size 750
lock mode row;

revoke all on user_role_xref from 'public';
create table 'informix'.user_group_xref (
    user_group_id DECIMAL(12,0) not null,
    login_id DECIMAL(12,0),
    group_id DECIMAL(12,0),
    create_user_id DECIMAL(12,0),
    security_status_id DECIMAL(3,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 10000 next size 5000
lock mode row;

revoke all on user_group_xref from 'public';
create table 'informix'.security_groups (
    group_id DECIMAL(12,0) not null,
    description VARCHAR(254) not null,
    create_user_id DECIMAL(12,0)
)
extent size 128 next size 128
lock mode row;

revoke all on security_groups from 'public';
create table 'informix'.group_role_xref (
    group_role_id DECIMAL(12,0) not null,
    group_id DECIMAL(12,0),
    role_id DECIMAL(12,0),
    create_user_id DECIMAL(12,0),
    security_status_id DECIMAL(3,0)
)
extent size 128 next size 128
lock mode row;

revoke all on group_role_xref from 'public';
create table 'informix'.security_roles (
    role_id DECIMAL(12,0) not null,
    description VARCHAR(254) not null,
    create_user_id DECIMAL(12,0)
)
extent size 500 next size 250
lock mode row;

revoke all on security_roles from 'public';
create table 'informix'.security_perms (
    role_id DECIMAL(12,0) not null,
    permission VARCHAR(254) not null,
    create_user_id DECIMAL(12,0),
    security_status_id DECIMAL(3,0)
)
extent size 1000 next size 500
lock mode row;

revoke all on security_perms from 'public';
create table 'informix'.address (
    address_id DECIMAL(10,0),
    address_type_id DECIMAL(5,0),
    address1 VARCHAR(254),
    address2 VARCHAR(254),
    city VARCHAR(64),
    state_code CHAR(2),
    zip VARCHAR(15),
    country_code CHAR(3),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    address3 VARCHAR(254),
    province VARCHAR(64)
)
extent size 15000 next size 7500
lock mode row;

revoke all on address from 'public';
create table 'informix'.address_type_lu (
    address_type_id DECIMAL(5,0),
    address_type_desc VARCHAR(100),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 64 next size 64
lock mode row;

revoke all on address_type_lu from 'public';
create table 'informix'.phone_type_lu (
    phone_type_id DECIMAL(5,0),
    phone_type_desc VARCHAR(100),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 64 next size 64
lock mode row;

revoke all on phone_type_lu from 'public';
create table 'informix'.email (
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

revoke all on email from 'public';
create table 'informix'.user_address_xref (
    user_id DECIMAL(10,0),
    address_id DECIMAL(10,0)
)
extent size 2500 next size 1250
lock mode row;

revoke all on user_address_xref from 'public';
create table 'informix'.phone (
    user_id DECIMAL(10,0),
    phone_id DECIMAL(10,0),
    phone_type_id DECIMAL(5,0),
    phone_number VARCHAR(64),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    primary_ind DECIMAL(1,0)
)
extent size 5000 next size 2500
lock mode row;

revoke all on phone from 'public';
create table 'informix'.user_status_lu (
    user_status_id DECIMAL(5,0),
    description VARCHAR(100)
)
extent size 64 next size 64
lock mode row;

revoke all on user_status_lu from 'public';
create table 'informix'.terms_of_use (
    terms_of_use_id DECIMAL(10,0),
    terms_text TEXT,
    terms_of_use_type_id DECIMAL(5,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    title VARCHAR(50) NOT NULL,
    electronically_signable decimal(1,0) NOT NULL,
    url VARCHAR(100)
)
extent size 512 next size 512
lock mode row;

revoke all on terms_of_use from 'public';
create table 'informix'.terms_of_use_type (
    terms_of_use_type_id DECIMAL(5,0),
    terms_of_use_type_desc VARCHAR(100),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 64 next size 64
lock mode row;

revoke all on terms_of_use_type from 'public';
create table 'informix'.user_terms_of_use_xref (
    user_id DECIMAL(10,0),
    terms_of_use_id DECIMAL(10,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 500 next size 250
lock mode row;

revoke all on user_terms_of_use_xref from 'public';
create table 'informix'.project_role_terms_of_use_xref (
    project_id INT not null,
    resource_role_id INT not null,
    terms_of_use_id DECIMAL(10,0) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    sort_order DECIMAL(1,0) DEFAULT 1 NOT NULL
)
extent size 2000 next size 2000
lock mode row;

revoke all on project_role_terms_of_use_xref from 'public';
create table 'informix'.state (
    state_code VARCHAR(2),
    state_name VARCHAR(35) not null,
    region_code VARCHAR(3),
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    demographic_decline DECIMAL(1,0)
)
extent size 64 next size 64
lock mode row;

revoke all on state from 'public';
create table 'informix'.country (
    country_code VARCHAR(3),
    country_name VARCHAR(40) not null,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    participating DECIMAL(1,0),
    default_taxform_id DECIMAL(10,0),
    longitude DECIMAL(10,7),
    latitude DECIMAL(10,7)
)
extent size 64 next size 64
lock mode row;

revoke all on country from 'public';
create table 'informix'.dual (
    value INT
)
extent size 16 next size 16
lock mode row;

revoke all on dual from 'public';
create table 'informix'.contact (
    contact_id DECIMAL(10,0) not null,
    company_id DECIMAL(10,0) not null,
    title VARCHAR(100),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 500 next size 250
lock mode row;

revoke all on contact from 'public';
create table 'informix'.note (
    note_id DECIMAL(10,0) not null,
    text TEXT,
    submitted_by DECIMAL(10,0) not null,
    note_type_id DECIMAL(3,0) not null,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 2048 next size 2048
lock mode row;

revoke all on note from 'public';
create table 'informix'.user_note_xref (
    user_id DECIMAL(10,0) not null,
    note_id DECIMAL(10,0) not null,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 64 next size 64
lock mode row;

revoke all on user_note_xref from 'public';
create table 'informix'.note_type_lu (
    note_type_id DECIMAL(3,0) not null,
    note_type_desc VARCHAR(100),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 64 next size 64
lock mode row;

revoke all on note_type_lu from 'public';
create table 'informix'.email_type_lu (
    email_type_id DECIMAL(5,0),
    email_type_desc VARCHAR(100),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 64 next size 64
lock mode row;

revoke all on email_type_lu from 'public';
create table 'informix'.key_generation (
    user_def CHAR(18) not null,
    high_value DECIMAL(13) not null
)
extent size 64 next size 64
lock mode row;

revoke all on key_generation from 'public';
create table 'informix'.company (
    company_id DECIMAL(10,0),
    company_name VARCHAR(100),
    primary_contact_id DECIMAL(10,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    timezone_id DECIMAL(5,0) default 143
)
extent size 500 next size 250
lock mode row;

revoke all on company from 'public';
create table 'informix'.sequence_object (
    name VARCHAR(25) not null,
    id DECIMAL(3,0),
    current_value DECIMAL(12,0)
)
extent size 64 next size 64
lock mode row;

revoke all on sequence_object from 'public';
create table 'informix'.company_datasource_xref (
    datasource_id DECIMAL(10,0) not null,
    company_id DECIMAL(10,0) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 16 next size 16
lock mode page;

revoke all on company_datasource_xref from 'public';
create table 'informix'.datasource_type_lu (
    datasource_type_desc VARCHAR(100) not null,
    datasource_type_id DECIMAL(10,0) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 16 next size 16
lock mode page;

revoke all on datasource_type_lu from 'public';
create table 'informix'.datasource_lu (
    datasource_id DECIMAL(10,0) not null,
    datasource_name VARCHAR(100) not null,
    datasource_desc VARCHAR(100) not null,
    datasource_type_id DECIMAL(10,0) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 16 next size 16
lock mode page;

revoke all on datasource_lu from 'public';
create table 'informix'.achievement_type_lu (
    achievement_type_id DECIMAL(5,0) not null,
    achievement_type_desc VARCHAR(64) not null
)
extent size 32 next size 32
lock mode row;

revoke all on achievement_type_lu from 'public';
create table 'informix'.user_achievement (
    user_id DECIMAL(10,0),
    achievement_date DATE not null,
    achievement_type_id DECIMAL(5,0) not null,
    description VARCHAR(255),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 256 next size 256
lock mode row;

revoke all on user_achievement from 'public';
create table 'informix'.preference_group_lu (
    preference_group_id DECIMAL(5,0) not null,
    preference_group_desc VARCHAR(64) not null,
    sort_order DECIMAL(5,0)
)
extent size 16 next size 16
lock mode row;

revoke all on preference_group_lu from 'public';
create table 'informix'.preference_lu (
    preference_id DECIMAL(5,0) not null,
    preference_name VARCHAR(64) not null,
    preference_desc VARCHAR(200),
    preference_group_id DECIMAL(5,0) not null,
    preference_type_id DECIMAL(5,0),
    sort_order DECIMAL(5,0)
)
extent size 16 next size 16
lock mode row;

revoke all on preference_lu from 'public';
create table 'informix'.user_status_type_lu (
    user_status_type_id DECIMAL(3,0),
    description VARCHAR(100)
)
extent size 64 next size 64
lock mode row;

revoke all on user_status_type_lu from 'public';
create table 'informix'.user_status (
    user_id DECIMAL(10,0),
    user_status_type_id DECIMAL(3,0),
    user_status_id DECIMAL(5,0)
)
extent size 64 next size 64
lock mode row;

revoke all on user_status from 'public';
create table 'informix'.event_lu (
    event_id DECIMAL(3,0),
    event_description VARCHAR(100),
    event_handler VARCHAR(100)
)
extent size 16 next size 16
lock mode row;

revoke all on event_lu from 'public';
create table 'informix'.user_event_status_lu (
    status_id DECIMAL(3,0),
    status_desc VARCHAR(100)
)
extent size 16 next size 16
lock mode row;

revoke all on user_event_status_lu from 'public';
create table 'informix'.user_event (
    user_event_id SERIAL not null,
    user_id DECIMAL(10,0),
    status_id DECIMAL(3,0),
    event_id DECIMAL(3,0),
    event_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 16 next size 16
lock mode row;

revoke all on user_event from 'public';
create table 'informix'.email_status_lu (
    status_id DECIMAL(3,0),
    status_desc VARCHAR(100),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 64 next size 64
lock mode row;

revoke all on email_status_lu from 'public';
create table 'informix'.email_bounce_log (
    log_id SERIAL not null,
    to_address VARCHAR(254),
    from_address VARCHAR(254),
    subject VARCHAR(254),
    bounce_type VARCHAR(50),
    processed_ind DECIMAL(1,0) default 0,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 5000 next size 2500
lock mode row;

revoke all on email_bounce_log from 'public';
create table 'informix'.bounce_type_lu (
    bounce_type VARCHAR(10),
    bounce_type_desc VARCHAR(100)
)
extent size 64 next size 64
lock mode row;

revoke all on bounce_type_lu from 'public';
create table 'informix'.preference_value (
    preference_value_id DECIMAL(5,0) not null,
    preference_id DECIMAL(5,0) not null,
    value VARCHAR(64),
    desc VARCHAR(128),
    sort_order DECIMAL(5,0)
)
extent size 64 next size 64
lock mode row;

revoke all on preference_value from 'public';
create table 'informix'.preference_type_lu (
    preference_type_id DECIMAL(5,0) not null,
    desc VARCHAR(128) not null
)
extent size 16 next size 16
lock mode row;

revoke all on preference_type_lu from 'public';
create table 'informix'.db_growth (
    dbase VARCHAR(25),
    table_name VARCHAR(50),
    table_size INT,
    num_extents INT,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 10000 next size 10000
lock mode page;

revoke all on db_growth from 'public';
create table 'informix'.request (
    user_id DECIMAL(10,0),
    session_id CHAR(50),
    url VARCHAR(254),
    timestamp DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 40000 next size 20000
lock mode page;

revoke all on request from 'public';
create table 'informix'.timezone_lu (
    timezone_id DECIMAL(5,0),
    timezone_desc VARCHAR(100),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 64 next size 64
lock mode row;

revoke all on timezone_lu from 'public';
create table 'informix'.company_terms_of_use_xref (
    company_id DECIMAL(10,0),
    terms_of_use_id DECIMAL(5,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 32 next size 32
lock mode row;

revoke all on company_terms_of_use_xref from 'public';
create table 'informix'.continent (
    continent_id DECIMAL(2,0),
    continent_name VARCHAR(20)
)
extent size 16 next size 16
lock mode row;

revoke all on continent from 'public';
create table 'informix'.user_preference (
    user_id DECIMAL(10,0) not null,
    preference_id DECIMAL(5,0) not null,
    value VARCHAR(254),
    preference_value_id DECIMAL(5,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default NULL
)
extent size 512 next size 512
lock mode row;

revoke all on user_preference from 'public';
create table 'informix'.calendar (
    calendar_id DECIMAL(10,0),
    year DECIMAL(4,0),
    month_numeric DECIMAL(2,0),
    month_alpha VARCHAR(10),
    day_of_month DECIMAL(2,0),
    day_of_week DECIMAL(1,0),
    week_day VARCHAR(15),
    year_month VARCHAR(7),
    week_of_year DECIMAL(2,0),
    day_of_year DECIMAL(3,0),
    holiday CHAR(1),
    weekend CHAR(1),
    date DATETIME YEAR TO DAY,
    week_year DECIMAL(4,0),
    quarter_of_year DECIMAL(1,0)
)
extent size 256 next size 256
lock mode page;

revoke all on calendar from 'public';
create table 'informix'.audit_user (
    user_id DECIMAL(10,0),
    column_name VARCHAR(30),
    old_value VARCHAR(254),
    new_value VARCHAR(254),
    timestamp DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 15000 next size 4000
lock mode row;

revoke all on audit_user from 'public';
create table 'informix'.security_status_lu (
    security_status_id DECIMAL(3,0),
    status_desc VARCHAR(200)
)
extent size 16 next size 16
lock mode row;

revoke all on security_status_lu from 'public';
create table 'informix'.registration_type_lu (
    registration_type_id DECIMAL(3,0),
    registration_type_name VARCHAR(200),
    registration_type_desc lvarchar,
    active_ind DECIMAL(1,0) default 1,
    security_group_id DECIMAL(12,0),
    sort DECIMAL(3,0)
)
extent size 16 next size 16
lock mode row;

revoke all on registration_type_lu from 'public';
create table 'informix'.notify_lu (
    notify_id DECIMAL(5,0) not null,
    name VARCHAR(255),
    status VARCHAR(3),
    sort DECIMAL(5,0),
    notify_type_id DECIMAL(5,0)
)
extent size 32 next size 32
lock mode row;

revoke all on notify_lu from 'public';
create table 'informix'.user_notify_xref (
    user_id DECIMAL(10,0) not null,
    notify_id DECIMAL(5,0) not null
)
extent size 5000 next size 2000
lock mode row;

revoke all on user_notify_xref from 'public';
create table 'informix'.registration_type_notify_xref (
    registration_type_id DECIMAL(3,0) not null,
    notify_id DECIMAL(5,0) not null
)
extent size 32 next size 32
lock mode row;

revoke all on registration_type_notify_xref from 'public';
create table 'informix'.demographic_question (
    demographic_question_id DECIMAL(10,0),
    demographic_question_text VARCHAR(255),
    selectable VARCHAR(1),
    demographic_question_desc VARCHAR(255)
)
extent size 64 next size 64
lock mode row;

revoke all on demographic_question from 'public';
create table 'informix'.demographic_answer (
    demographic_answer_id DECIMAL(10,0),
    demographic_question_id DECIMAL(10,0),
    demographic_answer_text VARCHAR(255),
    sort DECIMAL(10,0),
    status VARCHAR(1)
)
extent size 64 next size 64
lock mode row;

revoke all on demographic_answer from 'public';
create table 'informix'.demographic_response (
    user_id DECIMAL(10,0),
    demographic_answer_id DECIMAL(10,0),
    demographic_response VARCHAR(255),
    demographic_question_id DECIMAL(10,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 40000 next size 15000
lock mode row;

revoke all on demographic_response from 'public';
create table 'informix'.demographic_assignment (
    demographic_question_id DECIMAL(10,0),
    coder_type_id DECIMAL(3,0),
    registration_type_id DECIMAL(3,0),
    sort DECIMAL(10,0),
    status VARCHAR(1),
    is_required DECIMAL(1,0) default 1
)
extent size 64 next size 64
lock mode row;

revoke all on demographic_assignment from 'public';
create table 'informix'.id_sequences (
    name VARCHAR(254),
    next_block_start DECIMAL(12,0) not null,
    block_size DECIMAL(10,0) not null,
    exhausted DECIMAL(1,0) default 0 not null
)
extent size 64 next size 64
lock mode row;

revoke all on id_sequences from 'public';
create table 'informix'.password_recovery (
    password_recovery_id DECIMAL(10,0) not null,
    user_id DECIMAL(10,0) not null,
    recovery_address VARCHAR(100),
    expire_date DATETIME YEAR TO FRACTION not null,
    used_ind DECIMAL(1,0)
)
extent size 5000 next size 5000
lock mode row;

revoke all on password_recovery from 'public';
create table 'informix'.secret_question (
    user_id DECIMAL(10,0) not null,
    question VARCHAR(254) not null,
    response VARCHAR(254) not null
)
extent size 10000 next size 10000
lock mode row;

revoke all on secret_question from 'public';
create table 'informix'.event_type_lu (
    event_type_id DECIMAL(3,0),
    event_type_desc VARCHAR(100)
)
extent size 64 next size 64
lock mode row;

revoke all on event_type_lu from 'public';
create table 'informix'.event (
    event_id DECIMAL(10,0),
    event_type_id DECIMAL(3,0),
    event_desc VARCHAR(100),
    start_registration DATETIME YEAR TO FRACTION,
    end_registration DATETIME YEAR TO FRACTION,
    terms_of_use_id DECIMAL(10,0),
    survey_id DECIMAL(10,0),
    event_short_desc VARCHAR(100),
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    parent_event_id DECIMAL(10,0)
)
extent size 64 next size 64
lock mode row;

revoke all on event from 'public';
create table 'informix'.event_registration (
    event_id DECIMAL(10,0),
    user_id DECIMAL(10,0),
    eligible_ind DECIMAL(1,0),
    notes VARCHAR(255),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 500 next size 250
lock mode row;

revoke all on event_registration from 'public';
create table 'informix'.user (
    user_id DECIMAL(10,0) not null,
    first_name VARCHAR(64),
    last_name VARCHAR(64),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    handle VARCHAR(50) not null,
    last_login DATETIME YEAR TO FRACTION,
    status VARCHAR(3) not null,
    password VARCHAR(30),
    activation_code VARCHAR(32),
    middle_name VARCHAR(64),
    handle_lower VARCHAR(50),
    timezone_id DECIMAL(5,0),
    last_site_hit_date DATETIME YEAR TO FRACTION
)
extent size 10000 next size 5000
lock mode row;

revoke all on user from 'public';
create table 'informix'.registration_type_preference_xref (
    registration_type_id DECIMAL(3,0) not null,
    preference_id DECIMAL(5,0) not null
)
extent size 16 next size 16
lock mode row;

revoke all on registration_type_preference_xref from 'public';
create table 'informix'.notify_type_lu (
    notify_type_id DECIMAL(5,0) not null,
    notify_type_desc VARCHAR(64) not null,
    notify_type_sort_order DECIMAL(5,0)
)
extent size 16 next size 16
lock mode row;

revoke all on notify_type_lu from 'public';
create table 'informix'.school_type_lu (
    school_type_id DECIMAL(3,0),
    school_type_desc VARCHAR(100)
)
extent size 16 next size 16
lock mode row;

revoke all on school_type_lu from 'public';
create table 'informix'.school (
    school_id DECIMAL(10,0),
    sort_letter CHAR(1),
    city VARCHAR(50),
    state_code VARCHAR(2),
    country_code VARCHAR(3),
    user_id DECIMAL(10,0) not null,
    name VARCHAR(100) not null,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    short_name VARCHAR(20),
    school_type_id DECIMAL(3,0),
    address_id DECIMAL(10,0),
    viewable DECIMAL(1,0) default 1,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 2500 next size 500
lock mode row;

revoke all on school from 'public';
create table 'informix'.school_association_type_lu (
    school_association_type_id DECIMAL(3,0),
    school_association_type_desc VARCHAR(50)
)
extent size 16 next size 16
lock mode row;

revoke all on school_association_type_lu from 'public';
create table 'informix'.user_school (
    user_school_id DECIMAL(10,0) not null,
    user_id DECIMAL(10,0) not null,
    school_id DECIMAL(10,0) not null,
    school_association_type_id DECIMAL(3,0) not null,
    primary_ind boolean not null,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 5000 next size 2500
lock mode row;

revoke all on user_school from 'public';
create table 'informix'.professor_status_lu (
    status_id DECIMAL(3,0) not null,
    description VARCHAR(30) not null
)
extent size 16 next size 16
lock mode row;

revoke all on professor_status_lu from 'public';
create table 'informix'.professor (
    user_id DECIMAL(10,0) not null,
    status_id DECIMAL(3,0) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)
extent size 16 next size 16
lock mode row;

revoke all on professor from 'public';
create table 'informix'.permission_code (
    code VARCHAR(100) not null,
    group_id DECIMAL(12,0) not null,
    next_page VARCHAR(100) not null
)
extent size 32 next size 32
lock mode row;

revoke all on permission_code from 'public';

create table 'informix'.user_security_key (
    user_id DECIMAL(10,0) not null,
    security_key LVARCHAR(1024) not null,
    security_key_type_id smallint not null
)
extent size 32 next size 32
lock mode row;

revoke all on user_security_key from 'public';


create table 'informix'.contest_eligibility (
    contest_eligibility_id DECIMAL(10, 0) not null,
    contest_id DECIMAL(10,0) not null,
    is_studio SMALLINT NOT NULL,
    PRIMARY KEY (contest_eligibility_id) constraint contest_eligibility_pk
)
extent size 32 next size 32
lock mode row;

revoke all on contest_eligibility from 'public';

create table 'informix'.group_contest_eligibility (
    contest_eligibility_id DECIMAL(10, 0) not null,
    group_id DECIMAL(10,0) not null,
    PRIMARY KEY (contest_eligibility_id) constraint group_contest_eligibility_pk
)
extent size 32 next size 32
lock mode row;

revoke all on group_contest_eligibility from 'public';

create table 'informix'.client_terms_mapping (
    client_terms_mapping_id DECIMAL(10,0) not null,
    client_project_id DECIMAL(10,0) not null,
    terms_of_use_id DECIMAL(10,0) not null,
    resource_role_id DECIMAL(10,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    PRIMARY KEY (client_terms_mapping_id) constraint client_terms_mapping_pk
) extent size 32 next size 32
lock mode row;

revoke all on client_terms_mapping from 'public';


CREATE SEQUENCE "informix".CONTEST_ELIGIBILITY_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1;

revoke all on "informix".CONTEST_ELIGIBILITY_SEQ from "public";


create view "informix".email_user (user_id,first_name,last_name,
       create_date,modify_date,handle,last_login,status,password,
       activation_code,middle_name,email) as
   select x0.user_id ,x0.first_name ,x0.last_name ,x0.create_date ,
       x0.modify_date ,x0.handle ,x0.last_login ,x0.status ,x0.password ,
       x0.activation_code ,x0.middle_name ,x1.address 
   from "informix".user x0 ,"informix".email x1 
   where ((((x0.user_id = x1.user_id ) AND (x1.primary_ind = 1. ) ) AND (x1.status_id = 1. ) ) AND (x0.status = 'A' ) ) ;
revoke all on email_user from 'public';

grant select on email_user to 'informix' with grant option ;

grant all on sequence_object to 'coder' as 'informix';

grant delete on user_role_xref to 'public' as 'informix';

grant select on user_role_xref to 'public' as 'informix';

grant insert on user_role_xref to 'public' as 'informix';

grant update on user_role_xref to 'public' as 'informix';

grant delete on user_group_xref to 'public' as 'informix';

grant insert on user_group_xref to 'public' as 'informix';

grant select on user_group_xref to 'public' as 'informix';

grant update on user_group_xref to 'public' as 'informix';

grant update on security_groups to 'public' as 'informix';

grant insert on security_groups to 'public' as 'informix';

grant select on security_groups to 'public' as 'informix';

grant delete on security_groups to 'public' as 'informix';

grant select on group_role_xref to 'public' as 'informix';

grant delete on group_role_xref to 'public' as 'informix';

grant insert on group_role_xref to 'public' as 'informix';

grant update on group_role_xref to 'public' as 'informix';

grant update on security_roles to 'public' as 'informix';

grant delete on security_roles to 'public' as 'informix';

grant select on security_roles to 'public' as 'informix';

grant insert on security_roles to 'public' as 'informix';

grant update on security_perms to 'public' as 'informix';

grant insert on security_perms to 'public' as 'informix';

grant delete on security_perms to 'public' as 'informix';

grant select on security_perms to 'public' as 'informix';

grant update on address to 'public' as 'informix';

grant delete on address to 'public' as 'informix';

grant insert on address to 'public' as 'informix';

grant index on address to 'public' as 'informix';

grant select on address to 'public' as 'informix';

grant update on address_type_lu to 'public' as 'informix';

grant insert on address_type_lu to 'public' as 'informix';

grant delete on address_type_lu to 'public' as 'informix';

grant index on address_type_lu to 'public' as 'informix';

grant select on address_type_lu to 'public' as 'informix';

grant index on phone_type_lu to 'public' as 'informix';

grant delete on phone_type_lu to 'public' as 'informix';

grant select on phone_type_lu to 'public' as 'informix';

grant update on phone_type_lu to 'public' as 'informix';

grant insert on phone_type_lu to 'public' as 'informix';

grant select on email to 'public' as 'informix';

grant update on email to 'public' as 'informix';

grant insert on email to 'public' as 'informix';

grant delete on email to 'public' as 'informix';

grant index on email to 'public' as 'informix';

grant update on user_address_xref to 'public' as 'informix';

grant insert on user_address_xref to 'public' as 'informix';

grant index on user_address_xref to 'public' as 'informix';

grant select on user_address_xref to 'public' as 'informix';

grant delete on user_address_xref to 'public' as 'informix';

grant select on phone to 'public' as 'informix';

grant delete on phone to 'public' as 'informix';

grant insert on phone to 'public' as 'informix';

grant update on phone to 'public' as 'informix';

grant index on phone to 'public' as 'informix';

grant insert on user_status_lu to 'public' as 'informix';

grant update on user_status_lu to 'public' as 'informix';

grant select on user_status_lu to 'public' as 'informix';

grant delete on user_status_lu to 'public' as 'informix';

grant index on user_status_lu to 'public' as 'informix';

grant index on terms_of_use to 'public' as 'informix';

grant delete on terms_of_use to 'public' as 'informix';

grant update on terms_of_use to 'public' as 'informix';

grant insert on terms_of_use to 'public' as 'informix';

grant select on terms_of_use to 'public' as 'informix';

grant insert on terms_of_use_type to 'public' as 'informix';

grant select on terms_of_use_type to 'public' as 'informix';

grant delete on terms_of_use_type to 'public' as 'informix';

grant update on terms_of_use_type to 'public' as 'informix';

grant index on terms_of_use_type to 'public' as 'informix';

grant delete on user_terms_of_use_xref to 'public' as 'informix';

grant insert on user_terms_of_use_xref to 'public' as 'informix';

grant select on user_terms_of_use_xref to 'public' as 'informix';

grant update on user_terms_of_use_xref to 'public' as 'informix';

grant index on user_terms_of_use_xref to 'public' as 'informix';

grant delete on project_role_terms_of_use_xref to 'public' as 'informix';

grant update on project_role_terms_of_use_xref to 'public' as 'informix';

grant insert on project_role_terms_of_use_xref to 'public' as 'informix';

grant select on project_role_terms_of_use_xref to 'public' as 'informix';

grant index on project_role_terms_of_use_xref to 'public' as 'informix';

grant index on state to 'public' as 'informix';

grant insert on state to 'public' as 'informix';

grant select on state to 'public' as 'informix';

grant update on state to 'public' as 'informix';

grant delete on state to 'public' as 'informix';

grant index on country to 'public' as 'informix';

grant select on country to 'public' as 'informix';

grant update on country to 'public' as 'informix';

grant insert on country to 'public' as 'informix';

grant delete on country to 'public' as 'informix';

grant insert on dual to 'public' as 'informix';

grant select on dual to 'public' as 'informix';

grant index on dual to 'public' as 'informix';

grant update on dual to 'public' as 'informix';

grant delete on dual to 'public' as 'informix';

grant delete on contact to 'public' as 'informix';

grant select on contact to 'public' as 'informix';

grant index on contact to 'public' as 'informix';

grant insert on contact to 'public' as 'informix';

grant update on contact to 'public' as 'informix';

grant select on note to 'public' as 'informix';

grant insert on note to 'public' as 'informix';

grant delete on note to 'public' as 'informix';

grant index on note to 'public' as 'informix';

grant update on note to 'public' as 'informix';

grant index on user_note_xref to 'public' as 'informix';

grant update on user_note_xref to 'public' as 'informix';

grant insert on user_note_xref to 'public' as 'informix';

grant delete on user_note_xref to 'public' as 'informix';

grant select on user_note_xref to 'public' as 'informix';

grant delete on note_type_lu to 'public' as 'informix';

grant select on note_type_lu to 'public' as 'informix';

grant insert on note_type_lu to 'public' as 'informix';

grant index on note_type_lu to 'public' as 'informix';

grant update on note_type_lu to 'public' as 'informix';

grant update on registration_type_preference_xref to 'public' as 'informix';

grant insert on registration_type_preference_xref to 'public' as 'informix';

grant select on registration_type_preference_xref to 'public' as 'informix';

grant index on registration_type_preference_xref to 'public' as 'informix';

grant delete on registration_type_preference_xref to 'public' as 'informix';

grant update on email_type_lu to 'public' as 'informix';

grant insert on email_type_lu to 'public' as 'informix';

grant select on email_type_lu to 'public' as 'informix';

grant delete on email_type_lu to 'public' as 'informix';

grant index on email_type_lu to 'public' as 'informix';

grant index on key_generation to 'public' as 'informix';

grant select on key_generation to 'public' as 'informix';

grant insert on key_generation to 'public' as 'informix';

grant update on key_generation to 'public' as 'informix';

grant delete on key_generation to 'public' as 'informix';

grant select on company to 'public' as 'informix';

grant update on company to 'public' as 'informix';

grant insert on company to 'public' as 'informix';

grant delete on company to 'public' as 'informix';

grant index on company to 'public' as 'informix';

grant update on sequence_object to 'public' as 'informix';

grant delete on sequence_object to 'public' as 'informix';

grant insert on sequence_object to 'public' as 'informix';

grant select on sequence_object to 'public' as 'informix';

grant update on company_datasource_xref to 'public' as 'informix';

grant insert on company_datasource_xref to 'public' as 'informix';

grant index on company_datasource_xref to 'public' as 'informix';

grant select on company_datasource_xref to 'public' as 'informix';

grant delete on company_datasource_xref to 'public' as 'informix';

grant delete on datasource_type_lu to 'public' as 'informix';

grant update on datasource_type_lu to 'public' as 'informix';

grant select on datasource_type_lu to 'public' as 'informix';

grant insert on datasource_type_lu to 'public' as 'informix';

grant index on datasource_type_lu to 'public' as 'informix';

grant insert on datasource_lu to 'public' as 'informix';

grant select on datasource_lu to 'public' as 'informix';

grant update on datasource_lu to 'public' as 'informix';

grant delete on datasource_lu to 'public' as 'informix';

grant index on datasource_lu to 'public' as 'informix';

grant index on achievement_type_lu to 'public' as 'informix';

grant delete on achievement_type_lu to 'public' as 'informix';

grant insert on achievement_type_lu to 'public' as 'informix';

grant select on achievement_type_lu to 'public' as 'informix';

grant update on achievement_type_lu to 'public' as 'informix';

grant update on user_achievement to 'public' as 'informix';

grant index on user_achievement to 'public' as 'informix';

grant insert on user_achievement to 'public' as 'informix';

grant select on user_achievement to 'public' as 'informix';

grant delete on user_achievement to 'public' as 'informix';

grant delete on preference_group_lu to 'public' as 'informix';

grant insert on preference_group_lu to 'public' as 'informix';

grant index on preference_group_lu to 'public' as 'informix';

grant select on preference_group_lu to 'public' as 'informix';

grant update on preference_group_lu to 'public' as 'informix';

grant select on preference_lu to 'public' as 'informix';

grant delete on preference_lu to 'public' as 'informix';

grant index on preference_lu to 'public' as 'informix';

grant update on preference_lu to 'public' as 'informix';

grant insert on preference_lu to 'public' as 'informix';

grant delete on user_status_type_lu to 'public' as 'informix';

grant insert on user_status_type_lu to 'public' as 'informix';

grant select on user_status_type_lu to 'public' as 'informix';

grant index on user_status_type_lu to 'public' as 'informix';

grant update on user_status_type_lu to 'public' as 'informix';

grant update on user_status to 'public' as 'informix';

grant index on user_status to 'public' as 'informix';

grant select on user_status to 'public' as 'informix';

grant delete on user_status to 'public' as 'informix';

grant insert on user_status to 'public' as 'informix';

grant insert on event_lu to 'public' as 'informix';

grant update on event_lu to 'public' as 'informix';

grant delete on event_lu to 'public' as 'informix';

grant index on event_lu to 'public' as 'informix';

grant select on event_lu to 'public' as 'informix';

grant delete on user_event_status_lu to 'public' as 'informix';

grant insert on user_event_status_lu to 'public' as 'informix';

grant select on user_event_status_lu to 'public' as 'informix';

grant update on user_event_status_lu to 'public' as 'informix';

grant index on user_event_status_lu to 'public' as 'informix';

grant select on user_event to 'public' as 'informix';

grant index on user_event to 'public' as 'informix';

grant delete on user_event to 'public' as 'informix';

grant update on user_event to 'public' as 'informix';

grant insert on user_event to 'public' as 'informix';

grant delete on email_status_lu to 'public' as 'informix';

grant index on email_status_lu to 'public' as 'informix';

grant insert on email_status_lu to 'public' as 'informix';

grant select on email_status_lu to 'public' as 'informix';

grant update on email_status_lu to 'public' as 'informix';

grant update on email_bounce_log to 'public' as 'informix';

grant select on email_bounce_log to 'public' as 'informix';

grant index on email_bounce_log to 'public' as 'informix';

grant delete on email_bounce_log to 'public' as 'informix';

grant insert on email_bounce_log to 'public' as 'informix';

grant update on bounce_type_lu to 'public' as 'informix';

grant insert on bounce_type_lu to 'public' as 'informix';

grant index on bounce_type_lu to 'public' as 'informix';

grant select on bounce_type_lu to 'public' as 'informix';

grant delete on bounce_type_lu to 'public' as 'informix';

grant select on preference_value to 'public' as 'informix';

grant insert on preference_value to 'public' as 'informix';

grant update on preference_value to 'public' as 'informix';

grant index on preference_value to 'public' as 'informix';

grant delete on preference_value to 'public' as 'informix';

grant index on preference_type_lu to 'public' as 'informix';

grant delete on preference_type_lu to 'public' as 'informix';

grant update on preference_type_lu to 'public' as 'informix';

grant insert on preference_type_lu to 'public' as 'informix';

grant select on preference_type_lu to 'public' as 'informix';

grant index on db_growth to 'public' as 'informix';

grant insert on db_growth to 'public' as 'informix';

grant select on db_growth to 'public' as 'informix';

grant delete on db_growth to 'public' as 'informix';

grant update on db_growth to 'public' as 'informix';

grant insert on request to 'public' as 'informix';

grant update on request to 'public' as 'informix';

grant delete on request to 'public' as 'informix';

grant select on request to 'public' as 'informix';

grant index on request to 'public' as 'informix';

grant select on timezone_lu to 'public' as 'informix';

grant insert on timezone_lu to 'public' as 'informix';

grant index on timezone_lu to 'public' as 'informix';

grant delete on timezone_lu to 'public' as 'informix';

grant update on timezone_lu to 'public' as 'informix';

grant index on company_terms_of_use_xref to 'public' as 'informix';

grant select on company_terms_of_use_xref to 'public' as 'informix';

grant update on company_terms_of_use_xref to 'public' as 'informix';

grant insert on company_terms_of_use_xref to 'public' as 'informix';

grant delete on company_terms_of_use_xref to 'public' as 'informix';

grant update on continent to 'public' as 'informix';

grant delete on continent to 'public' as 'informix';

grant index on continent to 'public' as 'informix';

grant select on continent to 'public' as 'informix';

grant insert on continent to 'public' as 'informix';

grant delete on user_preference to 'public' as 'informix';

grant index on user_preference to 'public' as 'informix';

grant select on user_preference to 'public' as 'informix';

grant insert on user_preference to 'public' as 'informix';

grant update on user_preference to 'public' as 'informix';

grant index on calendar to 'public' as 'informix';

grant select on calendar to 'public' as 'informix';

grant update on calendar to 'public' as 'informix';

grant insert on calendar to 'public' as 'informix';

grant delete on calendar to 'public' as 'informix';

grant insert on audit_user to 'public' as 'informix';

grant index on audit_user to 'public' as 'informix';

grant delete on audit_user to 'public' as 'informix';

grant select on audit_user to 'public' as 'informix';

grant update on audit_user to 'public' as 'informix';

grant delete on security_status_lu to 'public' as 'informix';

grant insert on security_status_lu to 'public' as 'informix';

grant index on security_status_lu to 'public' as 'informix';

grant update on security_status_lu to 'public' as 'informix';

grant select on security_status_lu to 'public' as 'informix';

grant insert on registration_type_lu to 'public' as 'informix';

grant index on registration_type_lu to 'public' as 'informix';

grant delete on registration_type_lu to 'public' as 'informix';

grant update on registration_type_lu to 'public' as 'informix';

grant select on registration_type_lu to 'public' as 'informix';

grant index on notify_lu to 'public' as 'informix';

grant update on notify_lu to 'public' as 'informix';

grant select on notify_lu to 'public' as 'informix';

grant delete on notify_lu to 'public' as 'informix';

grant insert on notify_lu to 'public' as 'informix';

grant insert on user_notify_xref to 'public' as 'informix';

grant update on user_notify_xref to 'public' as 'informix';

grant select on user_notify_xref to 'public' as 'informix';

grant index on user_notify_xref to 'public' as 'informix';

grant delete on user_notify_xref to 'public' as 'informix';

grant delete on registration_type_notify_xref to 'public' as 'informix';

grant insert on registration_type_notify_xref to 'public' as 'informix';

grant update on registration_type_notify_xref to 'public' as 'informix';

grant select on registration_type_notify_xref to 'public' as 'informix';

grant index on registration_type_notify_xref to 'public' as 'informix';

grant update on demographic_question to 'public' as 'informix';

grant delete on demographic_question to 'public' as 'informix';

grant index on demographic_question to 'public' as 'informix';

grant insert on demographic_question to 'public' as 'informix';

grant select on demographic_question to 'public' as 'informix';

grant index on demographic_answer to 'public' as 'informix';

grant update on demographic_answer to 'public' as 'informix';

grant insert on demographic_answer to 'public' as 'informix';

grant delete on demographic_answer to 'public' as 'informix';

grant select on demographic_answer to 'public' as 'informix';

grant select on demographic_response to 'public' as 'informix';

grant update on demographic_response to 'public' as 'informix';

grant insert on demographic_response to 'public' as 'informix';

grant delete on demographic_response to 'public' as 'informix';

grant index on demographic_response to 'public' as 'informix';

grant update on demographic_assignment to 'public' as 'informix';

grant insert on demographic_assignment to 'public' as 'informix';

grant delete on demographic_assignment to 'public' as 'informix';

grant index on demographic_assignment to 'public' as 'informix';

grant select on demographic_assignment to 'public' as 'informix';

grant update on id_sequences to 'public' as 'informix';

grant select on id_sequences to 'public' as 'informix';

grant index on id_sequences to 'public' as 'informix';

grant delete on id_sequences to 'public' as 'informix';

grant insert on id_sequences to 'public' as 'informix';

grant insert on password_recovery to 'public' as 'informix';

grant select on password_recovery to 'public' as 'informix';

grant index on password_recovery to 'public' as 'informix';

grant update on password_recovery to 'public' as 'informix';

grant delete on password_recovery to 'public' as 'informix';

grant insert on secret_question to 'public' as 'informix';

grant update on secret_question to 'public' as 'informix';

grant index on secret_question to 'public' as 'informix';

grant select on secret_question to 'public' as 'informix';

grant delete on secret_question to 'public' as 'informix';

grant insert on event_type_lu to 'public' as 'informix';

grant index on event_type_lu to 'public' as 'informix';

grant update on event_type_lu to 'public' as 'informix';

grant delete on event_type_lu to 'public' as 'informix';

grant select on event_type_lu to 'public' as 'informix';

grant select on event to 'public' as 'informix';

grant insert on event to 'public' as 'informix';

grant index on event to 'public' as 'informix';

grant delete on event to 'public' as 'informix';

grant update on event to 'public' as 'informix';

grant delete on event_registration to 'public' as 'informix';

grant update on event_registration to 'public' as 'informix';

grant insert on event_registration to 'public' as 'informix';

grant select on event_registration to 'public' as 'informix';

grant index on event_registration to 'public' as 'informix';

grant index on user to 'public' as 'informix';

grant delete on user to 'public' as 'informix';

grant select on user to 'public' as 'informix';

grant update on user to 'public' as 'informix';

grant insert on user to 'public' as 'informix';

grant select on notify_type_lu to 'public' as 'informix';

grant index on notify_type_lu to 'public' as 'informix';

grant delete on notify_type_lu to 'public' as 'informix';

grant insert on notify_type_lu to 'public' as 'informix';

grant update on notify_type_lu to 'public' as 'informix';

grant select on school_type_lu to 'public' as 'informix';

grant delete on school_type_lu to 'public' as 'informix';

grant update on school_type_lu to 'public' as 'informix';

grant insert on school_type_lu to 'public' as 'informix';

grant index on school_type_lu to 'public' as 'informix';

grant index on school to 'public' as 'informix';

grant update on school to 'public' as 'informix';

grant insert on school to 'public' as 'informix';

grant select on school to 'public' as 'informix';

grant delete on school to 'public' as 'informix';

grant select on school_association_type_lu to 'public' as 'informix';

grant delete on school_association_type_lu to 'public' as 'informix';

grant index on school_association_type_lu to 'public' as 'informix';

grant insert on school_association_type_lu to 'public' as 'informix';

grant update on school_association_type_lu to 'public' as 'informix';

grant delete on user_school to 'public' as 'informix';

grant update on user_school to 'public' as 'informix';

grant index on user_school to 'public' as 'informix';

grant insert on user_school to 'public' as 'informix';

grant select on user_school to 'public' as 'informix';

grant update on professor_status_lu to 'public' as 'informix';

grant select on professor_status_lu to 'public' as 'informix';

grant delete on professor_status_lu to 'public' as 'informix';

grant index on professor_status_lu to 'public' as 'informix';

grant insert on professor_status_lu to 'public' as 'informix';

grant insert on professor to 'public' as 'informix';

grant select on professor to 'public' as 'informix';

grant update on professor to 'public' as 'informix';

grant delete on professor to 'public' as 'informix';

grant index on professor to 'public' as 'informix';

grant update on permission_code to 'public' as 'informix';

grant index on permission_code to 'public' as 'informix';

grant delete on permission_code to 'public' as 'informix';

grant select on permission_code to 'public' as 'informix';
grant insert on permission_code to 'public' as 'informix';

grant insert on user_security_key to 'public' as 'informix';

grant update on user_security_key to 'public' as 'informix';

grant index on user_security_key to 'public' as 'informix';

grant delete on user_security_key to 'public' as 'informix';

grant select on user_security_key to 'public' as 'informix';

grant insert on user_security_key to 'public' as 'informix';

grant select on email_user to 'public' as 'informix';

grant select,update,insert,delete on security_user to public as informix;

grant select,update,insert,delete on contest_eligibility to public as informix;

grant select,update,insert,delete on group_contest_eligibility to public as informix;

grant select,update,insert,delete on client_terms_mapping to public as informix;


grant select on "informix".CONTEST_ELIGIBILITY_SEQ to "public" as "informix";
