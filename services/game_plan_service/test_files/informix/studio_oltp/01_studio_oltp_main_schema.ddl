CREATE DATABASE studio_oltp IN ol_topcoder WITH BUFFERED LOG;
database studio_oltp;

grant dba to informix ;
grant connect to coder ;
grant connect to readonly ;
grant connect to veredox ;
grant connect to openaim ;
grant connect to truveo ;
grant connect to winformula ;
grant connect to openxtraz ;
grant connect to cockpit ;

create table "informix".dual 
  (
    value integer
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".dual from "public" as "informix";

create table "informix".command_group_lu 
  (
    command_group_id decimal(5,0) not null ,
    command_group_name varchar(100),
    primary key (command_group_id)  constraint "informix".command_group_lu_pk
  )  extent size 32 next size 32 lock mode row;
revoke all on "informix".command_group_lu from "public" as "informix";

create table "informix".command 
  (
    command_id decimal(10,0),
    command_desc varchar(100),
    command_group_id decimal(5,0),
    primary key (command_id)  constraint "informix".command_pk
  )  extent size 64 next size 64 lock mode row;
revoke all on "informix".command from "public" as "informix";

create table "informix".data_type 
  (
    data_type_id decimal(5,0),
    data_type_desc varchar(16),
    primary key (data_type_id)  constraint "informix".data_type_pk
  )  extent size 64 next size 64 lock mode row;
revoke all on "informix".data_type from "public" as "informix";

create table "informix".input_lu 
  (
    input_id decimal(10,0),
    input_code varchar(25),
    data_type_id decimal(5,0),
    input_desc varchar(100),
    primary key (input_id)  constraint "informix".input_lu_pkey
  )  extent size 32 next size 32 lock mode row;
revoke all on "informix".input_lu from "public" as "informix";

create table "informix".query 
  (
    query_id decimal(10,0),
    text text,
    name varchar(100),
    ranking integer,
    column_index integer,
    primary key (query_id)  constraint "informix".query_pk
  )  extent size 500 next size 250 lock mode row;
revoke all on "informix".query from "public" as "informix";

create table "informix".command_query_xref 
  (
    command_id decimal(10,0),
    query_id decimal(10,0),
    sort_order decimal(3,0),
    primary key (command_id,query_id)  constraint "informix".commandqueryxref_pk
  )  extent size 64 next size 64 lock mode row;
revoke all on "informix".command_query_xref from "public" as "informix";

create table "informix".query_input_xref 
  (
    query_id decimal(10,0),
    optional char(1),
    default_value varchar(100),
    input_id decimal(10,0),
    sort_order decimal(3,0),
    primary key (query_id,input_id)  constraint "informix".query_input_pk
  )  extent size 64 next size 64 lock mode row;
revoke all on "informix".query_input_xref from "public" as "informix";

create table "informix".path 
  (
    path_id decimal(10,0) not null ,
    path varchar(254) not null ,
    modify_date datetime year to fraction(3) 
        default current year to fraction(3),
    primary key (path_id)  constraint "informix".path_pk
  )  extent size 64 next size 64 lock mode row;
revoke all on "informix".path from "public" as "informix";

create table "informix".contest 
  (
    contest_id decimal(10,0) not null ,
    name varchar(254) not null ,
    start_time datetime year to fraction(3),
    end_time datetime year to fraction(3),
    contest_status_id decimal(3,0),
    forum_id integer,
    event_id decimal(10,0),
    project_id integer,
    contest_channel_id decimal(3,0),
    contest_type_id decimal(3,0),
    tc_direct_project_id integer,
    create_user_id decimal(10,0),
    winner_announcement_time datetime year to fraction(3),
    contest_detailed_status_id integer,
    launch_immediately decimal(1,0),
    deleted decimal(1,0),
    contest_milestone_prize_id DECIMAL(10,0),
    is_multi_round boolean(1),
    non_winning_submissions_purchased boolean(1),
    contest_general_info_id DECIMAL(10,0),
    contest_multi_round_information_id DECIMAL(10,0),
    contest_specifications_id DECIMAL(10,0),
    primary key (contest_id)  constraint "informix".contest_pk
  )  extent size 100 next size 100 lock mode row;
revoke all on "informix".contest from "public" as "informix";

create table "informix".review_status_lu 
  (
    review_status_id decimal(3,0) not null ,
    review_status_desc varchar(100) not null ,
    primary key (review_status_id)  constraint "informix".reviewstatuslu_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".review_status_lu from "public" as "informix";

create table "informix".submission_type_lu 
  (
    submission_type_id decimal(3,0) not null ,
    submission_type_desc varchar(100) not null ,
    primary key (submission_type_id)  constraint "informix".submissiontypelu_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".submission_type_lu from "public" as "informix";

create table "informix".submission_review 
  (
    submission_id decimal(10,0) not null ,
    reviewer_id decimal(10,0) not null ,
    text lvarchar,
    review_status_id decimal(3,0) not null ,
    modify_date datetime year to fraction(3) 
        default current year to fraction(3),
    primary key (submission_id)  constraint "informix".submissionreview_pk
  )  extent size 2000 next size 2000 lock mode row;
revoke all on "informix".submission_review from "public" as "informix";

create table "informix".submission_review_audit 
  (
    submission_id decimal(10,0) not null ,
    column_name varchar(50),
    old_value lvarchar,
    new_value lvarchar,
    timestamp datetime year to fraction(3) 
        default current year to fraction(3)
  )  extent size 5000 next size 5000 lock mode row;
revoke all on "informix".submission_review_audit from "public" as "informix";

create table "informix".contest_registration 
  (
    contest_id decimal(10,0) not null ,
    user_id decimal(10,0) not null ,
    terms_of_use_id decimal(5,0) not null ,
    create_date datetime year to fraction(3) 
        default current year to fraction(3),
    primary key (contest_id,user_id)  constraint "informix".contestreg_pk
  )  extent size 500 next size 250 lock mode row;
revoke all on "informix".contest_registration from "public" as "informix";

create table "informix".contest_property_lu 
  (
    property_id decimal(5,0),
    property_desc varchar(100),
    property_name varchar(100),
    primary key (property_id)  constraint "informix".contestpropertylu_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".contest_property_lu from "public" as "informix";

create table "informix".document_type_lu 
  (
    document_type_id decimal(3,0) not null ,
    document_type_desc varchar(100) not null ,
    primary key (document_type_id)  constraint "informix".documenttypelu_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".document_type_lu from "public" as "informix";

create table "informix".document 
  (
    document_id decimal(10,0) not null ,
    path_id decimal(10,0) not null ,
    original_file_name varchar(254) not null ,
    system_file_name varchar(254) not null ,
    document_type_id decimal(3,0) not null ,
    create_date datetime year to fraction(3) 
        default current year to fraction(3),
    mime_type_id decimal(5,0) not null ,
    description varchar(254),
    primary key (document_id)  constraint "informix".document_pk
  )  extent size 64 next size 64 lock mode row;
revoke all on "informix".document from "public" as "informix";

create table "informix".contest_document_xref 
  (
    contest_id decimal(10,0),
    document_id decimal(10,0),
    create_date datetime year to fraction(3) 
        default current year to fraction(3),
    primary key (contest_id,document_id)  constraint "informix".contest_document_xref_pk
  )  extent size 64 next size 64 lock mode row;
revoke all on "informix".contest_document_xref from "public" as "informix";

create table "informix".prize 
  (
    prize_id decimal(10,0),
    place integer,
    amount decimal(10,2) not null ,
    prize_type_id decimal(3,0) not null ,
    create_date datetime year to fraction(3) 
        default current year to fraction(3),
    primary key (prize_id)  constraint "informix".prize_pk
  )  extent size 128 next size 128 lock mode row;
revoke all on "informix".prize from "public" as "informix";

create table "informix".prize_type_lu 
  (
    prize_type_id decimal(3,0) not null ,
    prize_type_desc varchar(100) not null ,
    primary key (prize_type_id)  constraint "informix".prizetypelu_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".prize_type_lu from "public" as "informix";

create table "informix".contest_prize_xref 
  (
    contest_id decimal(10,0),
    prize_id decimal(10,0),
    create_date datetime year to fraction(3) 
        default current year to fraction(3),
    primary key (contest_id,prize_id)  constraint "informix".contest_prize_xref_pk
  )  extent size 64 next size 64 lock mode row;
revoke all on "informix".contest_prize_xref from "public" as "informix";

create table "informix".contest_status_lu 
  (
    contest_status_id decimal(3,0) not null ,
    contest_status_desc varchar(100) not null ,
    name varchar(100),
    primary key (contest_status_id)  constraint "informix".conteststatuslu_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".contest_status_lu from "public" as "informix";

create table "informix".contest_file_type_xref 
  (
    contest_id decimal(10,0),
    file_type_id decimal(3,0),
    create_date datetime year to fraction(3) 
        default current year to fraction(3),
    primary key (contest_id,file_type_id)  constraint "informix".contest_file_type_xref_pk
  )  extent size 64 next size 64 lock mode row;
revoke all on "informix".contest_file_type_xref from "public" as "informix";

create table "informix".file_type_lu 
  (
    file_type_id decimal(3,0),
    file_type_desc varchar(100) not null ,
    sort decimal(3,0) not null ,
    image_file_ind decimal(1,0) not null ,
    extension varchar(10) not null ,
    bundled_file_ind decimal(1,0) 
        default 0 not null ,
    primary key (file_type_id)  constraint "informix".file_type_lu_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".file_type_lu from "public" as "informix";

create table "informix".mime_type_lu 
  (
    mime_type_id decimal(5,0),
    file_type_id decimal(3,0) not null ,
    mime_type_desc varchar(100) not null ,
    primary key (mime_type_id)  constraint "informix".mime_type_lu_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".mime_type_lu from "public" as "informix";

create table "informix".contest_result 
  (
    submission_id decimal(10,0),
    contest_id decimal(10,0),
    create_date datetime year to fraction(3) 
        default current year to fraction(3),
    placed integer,
    final_score float,
    primary key (submission_id)  constraint "informix".contest_result_pk
  )  extent size 64 next size 64 lock mode row;
revoke all on "informix".contest_result from "public" as "informix";

create table "informix".command_execution 
  (
    command_id decimal(10,0),
    timestamp datetime year to fraction(3) 
        default current year to fraction(3),
    execution_time integer
  )  extent size 1000000 next size 1000000 lock mode page;
revoke all on "informix".command_execution from "public" as "informix";

create table "informix".contest_config 
  (
    contest_id decimal(10,0),
    property_id decimal(5,0),
    property_value lvarchar(10000),
    primary key (contest_id,property_id)  constraint "informix".contestconfig_pk
  )  extent size 100 next size 100 lock mode row;
revoke all on "informix".contest_config from "public" as "informix";

create table "informix".submission_status_lu 
  (
    submission_status_id decimal(3,0) not null ,
    submission_status_desc varchar(100) not null ,
    primary key (submission_status_id)  constraint "informix".submissiontatuslu_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".submission_status_lu from "public" as "informix";

create table "informix".submission_audit 
  (
    submission_id decimal(10,0) not null ,
    column_name varchar(50),
    old_value varchar(254),
    new_value varchar(254),
    timestamp datetime year to fraction(3) 
        default current year to fraction(3)
  )  extent size 5000 next size 5000 lock mode row;
revoke all on "informix".submission_audit from "public" as "informix";

create table "informix".submission_prize_xref 
  (
    submission_id decimal(10,0),
    prize_id decimal(10,0),
    create_date datetime year to fraction(3) 
        default current year to fraction(3),
    primary key (submission_id,prize_id)  constraint "informix".submission_prize_xref_pk
  )  extent size 64 next size 64 lock mode row;
revoke all on "informix".submission_prize_xref from "public" as "informix";

create table "informix".contest_type_lu 
  (
    contest_type_id decimal(3,0) not null ,
    contest_type_desc varchar(100) not null ,
    require_preview_image "informix".boolean not null ,
    require_preview_file "informix".boolean not null ,
    include_gallery "informix".boolean not null ,
    primary key (contest_type_id)  constraint "informix".contesttypelu_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".contest_type_lu from "public" as "informix";

create table "informix".contest_channel_lu 
  (
    contest_channel_id decimal(3,0) not null ,
    contest_channel_desc varchar(100) not null ,
    primary key (contest_channel_id)  constraint "informix".contestchannellu_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".contest_channel_lu from "public" as "informix";

create table "informix".medium_lu 
  (
    medium_id decimal(3,0) not null ,
    medium_desc varchar(100) not null ,
    primary key (medium_id)  constraint "informix".mediumlu_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".medium_lu from "public" as "informix";

create table "informix".contest_medium_xref 
  (
    contest_id decimal(10,0),
    medium_id decimal(3,0),
    create_date datetime year to fraction(3) 
        default current year to fraction(3),
    primary key (contest_id,medium_id)  constraint "informix".contest_medium_xref_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".contest_medium_xref from "public" as "informix";

create table "informix".payment_status_lu 
  (
    payment_status_id decimal(3,0) not null ,
    payments_status_desc varchar(100) not null ,
    primary key (payment_status_id)  constraint "informix".paymentstatuslu_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".payment_status_lu from "public" as "informix";

create table "informix".submission_payment 
  (
    submission_id decimal(10,0) not null ,
    payment_status_id decimal(3,0) not null ,
    price decimal(10,2) not null ,
    paypal_order_id varchar(128),
    create_date datetime year to fraction(3) 
        default current year to fraction(3) not null ,
    sale_reference_id varchar(128),
    sale_type_id integer,
    primary key (submission_id)  constraint "informix".submissionpayment_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".submission_payment from "public" as "informix";

create table "informix".submission_image 
  (
    submission_id decimal(10,0) not null ,
    image_id decimal(10,0) not null ,
    sort_order integer not null ,
    modify_date datetime year to fraction(3) 
        default current year to fraction(3),
    create_date datetime year to fraction(3) 
        default current year to fraction(3),
    primary key (submission_id,image_id)  constraint "informix".submission_image_pk
  )  extent size 750 next size 750 lock mode row;
revoke all on "informix".submission_image from "public" as "informix";

create table "informix".tco08_onsite 
  (
    coder_id decimal(10,0)
  )  extent size 32 next size 32 lock mode page;
revoke all on "informix".tco08_onsite from "public" as "informix";

create table "informix".contest_type_file_type 
  (
    file_type_id decimal(3,0) not null ,
    contest_type_id decimal(3,0),
    primary key (file_type_id,contest_type_id)  constraint "informix".contesttypefiletype_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".contest_type_file_type from "public" as "informix";

create table "informix".contest_type_config 
  (
    property_id decimal(5,0) not null ,
    contest_type_id decimal(3,0) not null ,
    required "informix".boolean not null ,
    default_value lvarchar(10000),
    primary key (property_id,contest_type_id)  constraint "informix".contesttypeconfig_pk
  )  extent size 64 next size 64 lock mode row;
revoke all on "informix".contest_type_config from "public" as "informix";

create table "informix".contest_detailed_status_lu 
  (
    contest_detailed_status_id decimal(3,0) not null ,
    contest_detailed_status_desc varchar(100) not null ,
    contest_status_id decimal(3,0) not null ,
    name varchar(100),
    primary key (contest_detailed_status_id)  constraint "informix".contestdetailedstatuslu_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".contest_detailed_status_lu from "public" as "informix";

create table "informix".contest_detailed_status_relation 
  (
    from_contest_status_id decimal(3,0) not null ,
    to_contest_status_id decimal(3,0) not null ,
    primary key (from_contest_status_id,to_contest_status_id)  constraint "informix".u188_161
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".contest_detailed_status_relation from "public" as "informix";

create table "informix".contest_payment 
  (
    contest_id decimal(10,0) not null ,
    payment_status_id decimal(3,0) not null ,
    price decimal(10,2) not null ,
    paypal_order_id varchar(128),
    create_date datetime year to fraction(3) 
        default current year to fraction(3) not null ,
    sale_reference_id varchar(128),
    sale_type_id integer,
    contest_payment_id integer,
    primary key (contest_payment_id)  constraint "informix".contest_payment_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".contest_payment from "public" as "informix";

create table "informix".contest_change_history 
  (
    contest_id decimal(10,0) not null ,
    transaction_id decimal(10,0) not null ,
    timestamp datetime year to fraction(3) 
        default current year to fraction(3),
    username varchar(32) not null ,
    is_user_admin decimal(1,0) not null ,
    field_name varchar(64) not null ,
    old_data lvarchar(1024) not null ,
    new_data lvarchar(1024) not null 
  )  extent size 2500 next size 2500 lock mode row;
revoke all on "informix".contest_change_history from "public" as "informix";

create table "informix".sale_type_lu 
  (
    sale_type_id integer not null ,
    sale_type_name varchar(128) not null ,
    primary key (sale_type_id)  constraint "informix".sale_type_lu_pk
  )  extent size 16 next size 16 lock mode row;
revoke all on "informix".sale_type_lu from "public" as "informix";

create table "informix".submission 
  (
    submission_id decimal(10,0) not null ,
    submitter_id decimal(10,0) not null ,
    contest_id decimal(10,0) not null ,
    create_date datetime year to fraction(3) 
        default current year to fraction(3),
    original_file_name varchar(254) not null ,
    system_file_name varchar(254) not null ,
    path_id decimal(10,0) not null ,
    submission_type_id decimal(3,0) not null ,
    mime_type_id decimal(5,0) not null ,
    rank decimal(5,0),
    submission_date datetime year to fraction(3),
    height integer,
    width integer,
    submission_status_id decimal(3,0) not null ,
    modify_date datetime year to fraction(3) 
        default current year to fraction(3),
    or_submission_id integer,
    payment_id decimal(10),
    feedback_text lvarchar(1000),
    feedback_thumb decimal(3,0),
    user_rank decimal(5,0),
    award_milestone_prize boolean(1),
    primary key (submission_id)  constraint "informix".submission_pk
  )  extent size 2000 next size 2000 lock mode row;
revoke all on "informix".submission from "public" as "informix";

create table 'informix'.rboard_status_lu (
 status_id DECIMAL(3,0) not null,
 status_desc VARCHAR(64),
 primary key (status_id) constraint rboard_stat_lu_pk
 
) extent size 16 next size 16 lock mode row;

revoke all on "informix".rboard_status_lu from public as informix;



-- create new review board related tables
create table 'informix'.rboard_user (
 user_id DECIMAL(10,0) not null,
 contest_type_id DECIMAL(12,0) not null,
 status_id DECIMAL(3,0) not null,
 immune_ind DECIMAL(1,0) not null,
 primary key (user_id, contest_type_id) constraint rboard_user_pk
) extent size 16 next size 16 lock mode row;

revoke all on "informix".rboard_user from public as informix;

CREATE TABLE 'informix'.contest_general_info (
  contest_general_info_id DECIMAL(10,0) NOT NULL,
  goals LVARCHAR(2000),
  target_audience LVARCHAR(2000),
  branding_guidelines LVARCHAR(2000),
  disliked_designs_websites LVARCHAR(2000),
  other_instructions LVARCHAR(2000),
  winning_criteria LVARCHAR(2000),
  primary key (contest_general_info_id)  constraint contest_general_info_pk
) extent size 16 next size 16 lock mode row;

revoke all on 'informix'.contest_general_info from public as informix;



CREATE TABLE 'informix'.contest_milestone_prize (
  contest_milestone_prize_id DECIMAL(10,0) NOT NULL,
  prize_type_id DECIMAL(3,0) NOT NULL,
  amount DECIMAL(10,2),
  number_of_submissions SMALLINT,
  create_date DATETIME YEAR to FRACTION(3) NOT Null,
  primary key (contest_milestone_prize_id)  constraint milestone_prize_pk
  
) extent size 16 next size 16 lock mode row;

revoke all on 'informix'.contest_milestone_prize from public as informix;



CREATE TABLE 'informix'.contest_multi_round_information (
  contest_multi_round_information_id DECIMAL(10,0) NOT NULL,
  milestone_date DATETIME YEAR to FRACTION(3),
  submitters_locked_between_rounds boolean(1),
  round_one_introduction LVARCHAR(2000),
  round_two_introduction LVARCHAR(2000),
  primary key (contest_multi_round_information_id)  constraint multi_round_information_pk
)  extent size 16 next size 16 lock mode row;

revoke all on 'informix'.contest_multi_round_information from public as informix;



CREATE TABLE 'informix'.contest_specifications (
  contest_specifications_id DECIMAL(10,0) NOT NULL,
  colors LVARCHAR(2000),
  fonts LVARCHAR(2000),
  layout_and_size LVARCHAR(2000),
  additional_requirements_and_restrictions LVARCHAR(2000),
  primary key (contest_specifications_id)  constraint contest_specifications_pk
)  extent size 16 next size 16 lock mode row;

revoke all on 'informix'.contest_specifications from public as informix;



create table 'informix'.resource_role_lu (
    resource_role_id DECIMAL(10,0) not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    primary key (resource_role_id) constraint resource_role_pk
) extent size 16 next size 16 lock mode row;

revoke all on 'informix'.resource_role_lu from public as informix;

CREATE TABLE 'informix'.resource (
  resource_id DECIMAL(3,0) NOT NULL,
  resource_name VARCHAR(128),
  resource_role_id DECIMAL(10,0),
  primary key (resource_id)  constraint resource_pk
) extent size 16 next size 16 lock mode row;

revoke all on 'informix'.resource from public as informix;



CREATE TABLE 'informix'.submission_milestone_prize_xref (
  submission_id DECIMAL(10,0) NOT NULL,
  contest_milestone_prize_id DECIMAL(10,0) NOT NULL,
  create_date DATETIME YEAR to FRACTION(3) DEFAULT CURRENT YEAR TO FRACTION(3),
  PRIMARY KEY (submission_id, contest_milestone_prize_id) CONSTRAINT submission_milestone_prize_xref_pk
) extent size 16 next size 16 lock mode row;

revoke all on 'informix'.submission_milestone_prize_xref from public as informix;



CREATE TABLE 'informix'.contest_resource_xref (
  contest_id DECIMAL(10,0) NOT NULL,
  resource_id DECIMAL(3,0) NOT NULL,
  create_date DATETIME YEAR to FRACTION(3),
  PRIMARY KEY (contest_id, resource_id) CONSTRAINT contest_resource_xref_pk
) extent size 16 next size 16 lock mode row;

revoke all on contest_resource_xref from public as informix;


create table 'informix'.studio_competition_pipeline_info (
    id DECIMAL(10,0) not null,
    competition_id DECIMAL(10,0),
    review_payment DECIMAL(7,3),
    specification_review_payment DECIMAL(7,3),
    contest_fee DECIMAL(7,3),
    client_name VARCHAR(45),
    confidence INT,
    client_approval SMALLINT,
    pricing_approval SMALLINT,
    has_wiki_specification SMALLINT,
    passed_spec_review SMALLINT,
    has_dependent_competitions SMALLINT,
    was_reposted SMALLINT,
    notes VARCHAR(150),
    contest_id DECIMAL(10,0),
    primary key (id) constraint pipeline_info_pk
    
) extent size 16 next size 16 lock mode row;


revoke all on 'informix'.studio_competition_pipeline_info from public as informix;


create table 'informix'.studio_competition_change_history (
    id DECIMAL(10,0) not null,
    manager VARCHAR(45),
    client VARCHAR(45),
    type VARCHAR(45),
    new_data VARCHAR(45),
    old_data VARCHAR(45),
    initial_data VARCHAR(45),
    change_time DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    change_type VARCHAR(45),
    studio_competition_pipeline_info_id DECIMAL(10,0),
    primary key (id) constraint pipeline_history_info_pk
    
) extent size 16 next size 16 lock mode row;

revoke all on 'informix'.studio_competition_change_history from public as informix;


create table 'informix'.studio_competition_pipeline_resources (
    id DECIMAL(10,0) not null,
    studio_competition_pipeline_info_id DECIMAL(10,0),
    resource_id DECIMAL(3,0),
    primary key	(id) constraint pipeline_resource_pk

) extent size 16 next size 16 lock mode row;

revoke all on 'informix'.studio_competition_pipeline_resources from public as informix;


create table  "informix".electronic_affirmation (
	submission_id decimal(10,0) NOT NULL,
	create_time datetime year to fraction(3) 
        default current year to fraction(3),
	PRIMARY KEY (submission_id) constraint electronic_affirmation_pk
) extent size 16 next size 16 lock mode row;

revoke all on 'informix'.electronic_affirmation from public as informix;





create sequence "informix".studio_contest_seq increment by 1 start with 1000000 maxvalue 9223372036854775807 minvalue 1000000 cache 20  order;
Alter sequence "informix".studio_contest_seq restart with 1001058;

revoke all on "informix".studio_contest_seq from "public";

create sequence "informix".studio_document_seq increment by 1 start with 1000000 maxvalue 9223372036854775807 minvalue 1000000 cache 20  order;
Alter sequence "informix".studio_document_seq restart with 1001260;

revoke all on "informix".studio_document_seq from "public";

create sequence "informix".studio_path_seq increment by 1 start with 1000000 maxvalue 9223372036854775807 minvalue 1000000 cache 20  order;
Alter sequence "informix".studio_path_seq restart with 1001260;

revoke all on "informix".studio_path_seq from "public";

create sequence "informix".studio_prize_seq increment by 1 start with 1000000 maxvalue 9223372036854775807 minvalue 1000000 cache 20  order;
Alter sequence "informix".studio_prize_seq restart with 1005248;

revoke all on "informix".studio_prize_seq from "public";

create sequence "informix".studio_submission_seq increment by 1 start with 1000000 maxvalue 9223372036854775807 minvalue 1000000 cache 20  order;
Alter sequence "informix".studio_submission_seq restart with 1000469;

revoke all on "informix".studio_submission_seq from "public";

create sequence "informix".contest_payment_seq increment by 1 start with 1000674 maxvalue 9223372036854775807 minvalue 1000674 cache 20  order;
Alter sequence "informix".contest_payment_seq restart with 1000980;

revoke all on "informix".contest_payment_seq from "public";

create sequence "informix".permission_type_seq increment by 1 maxvalue 9223372036854775807 minvalue 1 cache 20  order;
Alter sequence "informix".permission_type_seq restart with 70;

revoke all on "informix".permission_type_seq from "public";

create sequence "informix".permission_seq increment by 1 maxvalue 9223372036854775807 minvalue 1 cache 20  order;
Alter sequence "informix".permission_seq restart with 684;

revoke all on "informix".permission_seq from "public";


CREATE SEQUENCE CONTEST_MILESTONE_PRIZE_SEQ;
revoke all on "informix".CONTEST_MILESTONE_PRIZE_SEQ from "public";


CREATE SEQUENCE CONTEST_MULTI_ROUND_INFORMATION_SEQ;
revoke all on "informix".CONTEST_MULTI_ROUND_INFORMATION_SEQ from "public";

create sequence CONTEST_SPECIFICATIONS_SEQ;
revoke all on "informix".CONTEST_SPECIFICATIONS_SEQ from "public";




grant select on "informix".dual to "public" as "informix";
grant update on "informix".dual to "public" as "informix";
grant insert on "informix".dual to "public" as "informix";
grant delete on "informix".dual to "public" as "informix";
grant index on "informix".dual to "public" as "informix";
grant select on "informix".command_group_lu to "public" as "informix";
grant update on "informix".command_group_lu to "public" as "informix";
grant insert on "informix".command_group_lu to "public" as "informix";
grant delete on "informix".command_group_lu to "public" as "informix";
grant index on "informix".command_group_lu to "public" as "informix";
grant select on "informix".command to "public" as "informix";
grant update on "informix".command to "public" as "informix";
grant insert on "informix".command to "public" as "informix";
grant delete on "informix".command to "public" as "informix";
grant index on "informix".command to "public" as "informix";
grant select on "informix".data_type to "public" as "informix";
grant update on "informix".data_type to "public" as "informix";
grant insert on "informix".data_type to "public" as "informix";
grant delete on "informix".data_type to "public" as "informix";
grant index on "informix".data_type to "public" as "informix";
grant select on "informix".input_lu to "public" as "informix";
grant update on "informix".input_lu to "public" as "informix";
grant insert on "informix".input_lu to "public" as "informix";
grant delete on "informix".input_lu to "public" as "informix";
grant index on "informix".input_lu to "public" as "informix";
grant select on "informix".query to "public" as "informix";
grant update on "informix".query to "public" as "informix";
grant insert on "informix".query to "public" as "informix";
grant delete on "informix".query to "public" as "informix";
grant index on "informix".query to "public" as "informix";
grant select on "informix".command_query_xref to "public" as "informix";
grant update on "informix".command_query_xref to "public" as "informix";
grant insert on "informix".command_query_xref to "public" as "informix";
grant delete on "informix".command_query_xref to "public" as "informix";
grant index on "informix".command_query_xref to "public" as "informix";
grant select on "informix".query_input_xref to "public" as "informix";
grant update on "informix".query_input_xref to "public" as "informix";
grant insert on "informix".query_input_xref to "public" as "informix";
grant delete on "informix".query_input_xref to "public" as "informix";
grant index on "informix".query_input_xref to "public" as "informix";
grant select on "informix".path to "public" as "informix";
grant update on "informix".path to "public" as "informix";
grant insert on "informix".path to "public" as "informix";
grant delete on "informix".path to "public" as "informix";
grant index on "informix".path to "public" as "informix";
grant select on "informix".contest to "public" as "informix";
grant update on "informix".contest to "public" as "informix";
grant insert on "informix".contest to "public" as "informix";
grant delete on "informix".contest to "public" as "informix";
grant index on "informix".contest to "public" as "informix";
grant select on "informix".review_status_lu to "public" as "informix";
grant update on "informix".review_status_lu to "public" as "informix";
grant insert on "informix".review_status_lu to "public" as "informix";
grant delete on "informix".review_status_lu to "public" as "informix";
grant index on "informix".review_status_lu to "public" as "informix";
grant select on "informix".submission_type_lu to "public" as "informix";
grant update on "informix".submission_type_lu to "public" as "informix";
grant insert on "informix".submission_type_lu to "public" as "informix";
grant delete on "informix".submission_type_lu to "public" as "informix";
grant index on "informix".submission_type_lu to "public" as "informix";
grant select on "informix".submission_review to "public" as "informix";
grant update on "informix".submission_review to "public" as "informix";
grant insert on "informix".submission_review to "public" as "informix";
grant delete on "informix".submission_review to "public" as "informix";
grant index on "informix".submission_review to "public" as "informix";
grant select on "informix".submission_review_audit to "public" as "informix";
grant update on "informix".submission_review_audit to "public" as "informix";
grant insert on "informix".submission_review_audit to "public" as "informix";
grant delete on "informix".submission_review_audit to "public" as "informix";
grant index on "informix".submission_review_audit to "public" as "informix";
grant select on "informix".contest_registration to "public" as "informix";
grant update on "informix".contest_registration to "public" as "informix";
grant insert on "informix".contest_registration to "public" as "informix";
grant delete on "informix".contest_registration to "public" as "informix";
grant index on "informix".contest_registration to "public" as "informix";
grant select on "informix".contest_property_lu to "public" as "informix";
grant update on "informix".contest_property_lu to "public" as "informix";
grant insert on "informix".contest_property_lu to "public" as "informix";
grant delete on "informix".contest_property_lu to "public" as "informix";
grant index on "informix".contest_property_lu to "public" as "informix";
grant select on "informix".document_type_lu to "public" as "informix";
grant update on "informix".document_type_lu to "public" as "informix";
grant insert on "informix".document_type_lu to "public" as "informix";
grant delete on "informix".document_type_lu to "public" as "informix";
grant index on "informix".document_type_lu to "public" as "informix";
grant select on "informix".document to "public" as "informix";
grant update on "informix".document to "public" as "informix";
grant insert on "informix".document to "public" as "informix";
grant delete on "informix".document to "public" as "informix";
grant index on "informix".document to "public" as "informix";
grant select on "informix".contest_document_xref to "public" as "informix";
grant update on "informix".contest_document_xref to "public" as "informix";
grant insert on "informix".contest_document_xref to "public" as "informix";
grant delete on "informix".contest_document_xref to "public" as "informix";
grant index on "informix".contest_document_xref to "public" as "informix";
grant select on "informix".prize to "public" as "informix";
grant update on "informix".prize to "public" as "informix";
grant insert on "informix".prize to "public" as "informix";
grant delete on "informix".prize to "public" as "informix";
grant index on "informix".prize to "public" as "informix";
grant select on "informix".prize_type_lu to "public" as "informix";
grant update on "informix".prize_type_lu to "public" as "informix";
grant insert on "informix".prize_type_lu to "public" as "informix";
grant delete on "informix".prize_type_lu to "public" as "informix";
grant index on "informix".prize_type_lu to "public" as "informix";
grant select on "informix".contest_prize_xref to "public" as "informix";
grant update on "informix".contest_prize_xref to "public" as "informix";
grant insert on "informix".contest_prize_xref to "public" as "informix";
grant delete on "informix".contest_prize_xref to "public" as "informix";
grant index on "informix".contest_prize_xref to "public" as "informix";
grant select on "informix".contest_status_lu to "public" as "informix";
grant update on "informix".contest_status_lu to "public" as "informix";
grant insert on "informix".contest_status_lu to "public" as "informix";
grant delete on "informix".contest_status_lu to "public" as "informix";
grant index on "informix".contest_status_lu to "public" as "informix";
grant select on "informix".contest_file_type_xref to "public" as "informix";
grant update on "informix".contest_file_type_xref to "public" as "informix";
grant insert on "informix".contest_file_type_xref to "public" as "informix";
grant delete on "informix".contest_file_type_xref to "public" as "informix";
grant index on "informix".contest_file_type_xref to "public" as "informix";
grant select on "informix".file_type_lu to "public" as "informix";
grant update on "informix".file_type_lu to "public" as "informix";
grant insert on "informix".file_type_lu to "public" as "informix";
grant delete on "informix".file_type_lu to "public" as "informix";
grant index on "informix".file_type_lu to "public" as "informix";
grant select on "informix".mime_type_lu to "public" as "informix";
grant update on "informix".mime_type_lu to "public" as "informix";
grant insert on "informix".mime_type_lu to "public" as "informix";
grant delete on "informix".mime_type_lu to "public" as "informix";
grant index on "informix".mime_type_lu to "public" as "informix";
grant select on "informix".contest_result to "public" as "informix";
grant update on "informix".contest_result to "public" as "informix";
grant insert on "informix".contest_result to "public" as "informix";
grant delete on "informix".contest_result to "public" as "informix";
grant index on "informix".contest_result to "public" as "informix";
grant select on "informix".command_execution to "public" as "informix";
grant update on "informix".command_execution to "public" as "informix";
grant insert on "informix".command_execution to "public" as "informix";
grant delete on "informix".command_execution to "public" as "informix";
grant index on "informix".command_execution to "public" as "informix";
grant select on "informix".contest_config to "public" as "informix";
grant update on "informix".contest_config to "public" as "informix";
grant insert on "informix".contest_config to "public" as "informix";
grant delete on "informix".contest_config to "public" as "informix";
grant index on "informix".contest_config to "public" as "informix";
grant select on "informix".submission_status_lu to "public" as "informix";
grant update on "informix".submission_status_lu to "public" as "informix";
grant insert on "informix".submission_status_lu to "public" as "informix";
grant delete on "informix".submission_status_lu to "public" as "informix";
grant index on "informix".submission_status_lu to "public" as "informix";
grant select on "informix".submission_audit to "public" as "informix";
grant update on "informix".submission_audit to "public" as "informix";
grant insert on "informix".submission_audit to "public" as "informix";
grant delete on "informix".submission_audit to "public" as "informix";
grant index on "informix".submission_audit to "public" as "informix";
grant select on "informix".submission_prize_xref to "public" as "informix";
grant update on "informix".submission_prize_xref to "public" as "informix";
grant insert on "informix".submission_prize_xref to "public" as "informix";
grant delete on "informix".submission_prize_xref to "public" as "informix";
grant index on "informix".submission_prize_xref to "public" as "informix";
grant select on "informix".contest_type_lu to "public" as "informix";
grant update on "informix".contest_type_lu to "public" as "informix";
grant insert on "informix".contest_type_lu to "public" as "informix";
grant delete on "informix".contest_type_lu to "public" as "informix";
grant index on "informix".contest_type_lu to "public" as "informix";
grant select on "informix".contest_channel_lu to "public" as "informix";
grant update on "informix".contest_channel_lu to "public" as "informix";
grant insert on "informix".contest_channel_lu to "public" as "informix";
grant delete on "informix".contest_channel_lu to "public" as "informix";
grant index on "informix".contest_channel_lu to "public" as "informix";
grant select on "informix".medium_lu to "public" as "informix";
grant update on "informix".medium_lu to "public" as "informix";
grant insert on "informix".medium_lu to "public" as "informix";
grant delete on "informix".medium_lu to "public" as "informix";
grant index on "informix".medium_lu to "public" as "informix";
grant select on "informix".contest_medium_xref to "public" as "informix";
grant update on "informix".contest_medium_xref to "public" as "informix";
grant insert on "informix".contest_medium_xref to "public" as "informix";
grant delete on "informix".contest_medium_xref to "public" as "informix";
grant index on "informix".contest_medium_xref to "public" as "informix";
grant select on "informix".payment_status_lu to "public" as "informix";
grant update on "informix".payment_status_lu to "public" as "informix";
grant insert on "informix".payment_status_lu to "public" as "informix";
grant delete on "informix".payment_status_lu to "public" as "informix";
grant index on "informix".payment_status_lu to "public" as "informix";
grant select on "informix".submission_payment to "public" as "informix";
grant update on "informix".submission_payment to "public" as "informix";
grant insert on "informix".submission_payment to "public" as "informix";
grant delete on "informix".submission_payment to "public" as "informix";
grant index on "informix".submission_payment to "public" as "informix";
grant select on "informix".submission_image to "public" as "informix";
grant update on "informix".submission_image to "public" as "informix";
grant insert on "informix".submission_image to "public" as "informix";
grant delete on "informix".submission_image to "public" as "informix";
grant index on "informix".submission_image to "public" as "informix";
grant select on "informix".tco08_onsite to "public" as "informix";
grant update on "informix".tco08_onsite to "public" as "informix";
grant insert on "informix".tco08_onsite to "public" as "informix";
grant delete on "informix".tco08_onsite to "public" as "informix";
grant index on "informix".tco08_onsite to "public" as "informix";
grant select on "informix".contest_type_file_type to "public" as "informix";
grant update on "informix".contest_type_file_type to "public" as "informix";
grant insert on "informix".contest_type_file_type to "public" as "informix";
grant delete on "informix".contest_type_file_type to "public" as "informix";
grant index on "informix".contest_type_file_type to "public" as "informix";
grant select on "informix".contest_type_config to "public" as "informix";
grant update on "informix".contest_type_config to "public" as "informix";
grant insert on "informix".contest_type_config to "public" as "informix";
grant delete on "informix".contest_type_config to "public" as "informix";
grant index on "informix".contest_type_config to "public" as "informix";
grant select on "informix".contest_detailed_status_lu to "public" as "informix";
grant update on "informix".contest_detailed_status_lu to "public" as "informix";
grant insert on "informix".contest_detailed_status_lu to "public" as "informix";
grant delete on "informix".contest_detailed_status_lu to "public" as "informix";
grant index on "informix".contest_detailed_status_lu to "public" as "informix";
grant select on "informix".contest_detailed_status_relation to "public" as "informix";
grant update on "informix".contest_detailed_status_relation to "public" as "informix";
grant insert on "informix".contest_detailed_status_relation to "public" as "informix";
grant delete on "informix".contest_detailed_status_relation to "public" as "informix";
grant index on "informix".contest_detailed_status_relation to "public" as "informix";
grant select on "informix".contest_payment to "public" as "informix";
grant update on "informix".contest_payment to "public" as "informix";
grant insert on "informix".contest_payment to "public" as "informix";
grant delete on "informix".contest_payment to "public" as "informix";
grant index on "informix".contest_payment to "public" as "informix";
grant select on "informix".contest_change_history to "public" as "informix";
grant update on "informix".contest_change_history to "public" as "informix";
grant insert on "informix".contest_change_history to "public" as "informix";
grant delete on "informix".contest_change_history to "public" as "informix";
grant index on "informix".contest_change_history to "public" as "informix";
grant select on "informix".sale_type_lu to "public" as "informix";
grant update on "informix".sale_type_lu to "public" as "informix";
grant insert on "informix".sale_type_lu to "public" as "informix";
grant delete on "informix".sale_type_lu to "public" as "informix";
grant index on "informix".sale_type_lu to "public" as "informix";
grant select on "informix".submission to "public" as "informix";
grant update on "informix".submission to "public" as "informix";
grant insert on "informix".submission to "public" as "informix";
grant delete on "informix".submission to "public" as "informix";
grant index on "informix".submission to "public" as "informix";

grant select on "informix".rboard_status_lu to "public" as "informix";
grant update on "informix".rboard_status_lu to "public" as "informix";
grant insert on "informix".rboard_status_lu to "public" as "informix";
grant delete on "informix".rboard_status_lu to "public" as "informix";
grant index on "informix".rboard_status_lu to "public" as "informix";
grant select on "informix".rboard_user to "public" as "informix";
grant update on "informix".rboard_user to "public" as "informix";
grant insert on "informix".rboard_user to "public" as "informix";
grant delete on "informix".rboard_user to "public" as "informix";
grant index on "informix".rboard_user to "public" as "informix";

grant select,insert,update,delete on 'informix'.contest_general_info to public as informix;
grant select,insert,update,delete on contest_milestone_prize to public as informix;
grant select,insert,update,delete on 'informix'.contest_multi_round_information to public as informix;
grant select,insert,update,delete on 'informix'.contest_specifications to public as informix;
grant select,insert,update,delete on 'informix'.resource_role_lu to public as informix;
grant select,insert,update,delete on 'informix'.resource to public as informix;
grant select,insert,update,delete on 'informix'.contest_resource_xref to public as informix;

grant select,insert,update,delete on 'informix'.submission_milestone_prize_xref to public as informix;
grant select,insert,update,delete on 'informix'.studio_competition_pipeline_info to public as informix;
grant select,insert,update,delete on 'informix'.studio_competition_change_history to public as informix;
grant select,insert,update,delete on 'informix'.studio_competition_pipeline_resources  to public as informix;

grant select,insert,update,delete on 'informix'.electronic_affirmation  to public as informix;


grant select on "informix".studio_contest_seq to "public" as "informix";
grant select on "informix".studio_document_seq to "public" as "informix";
grant select on "informix".studio_path_seq to "public" as "informix";
grant select on "informix".studio_prize_seq to "public" as "informix";
grant select on "informix".studio_submission_seq to "public" as "informix";
grant select on "informix".contest_payment_seq to "public" as "informix";
grant select on "informix".permission_type_seq to "public" as "informix";
grant select on "informix".permission_seq to "public" as "informix";

grant select on CONTEST_MILESTONE_PRIZE_SEQ to public as informix;
grant select on CONTEST_MULTI_ROUND_INFORMATION_SEQ to public as informix;
grant select on CONTEST_SPECIFICATIONS_SEQ to public as informix;




----- since vm does not have jive db, in dev/prod we create synonyms for jive tables, here we just create dummy tables

create table 'informix'.jiveforum (

forumid INTEGER not null,
name varchar(255),
nntpname varchar(255),
descripition varchar(255),
moddefaultthreadval INTEGER,
moddefaultmsgval INTEGER,
creationdate decimal,
modificationdate decimal,
categoryid INTEGER,
categoryindex INTEGER,
forumindexcounter INTEGER

)
extent size 16 next size 16
lock mode page;

revoke all on 'informix'.jiveforum from 'public';


create table 'informix'.jivemessage (

messageid INTEGER not null,
parentmessageid INTEGER,
threadid INTEGER,
forumid INTEGER,
forumindex INTEGER,
userid INTEGER,
subject varchar(255),
body text,
modvalue INTEGER,
rewardpoints INTEGER,
creationdate decimal,
modificationdate decimal
)
extent size 16 next size 16
lock mode page;

revoke all on 'informix'.jivemessage from 'public';


grant select on 'informix'.jiveforum to 'public' as 'informix';
grant insert on 'informix'.jiveforum to 'public' as 'informix';
grant update on 'informix'.jiveforum to 'public' as 'informix';
grant delete on 'informix'.jiveforum to 'public' as 'informix';
grant select on 'informix'.jivemessage to 'public' as 'informix';
grant insert on 'informix'.jivemessage to 'public' as 'informix';
grant update on 'informix'.jivemessage to 'public' as 'informix';
grant delete on 'informix'.jivemessage to 'public' as 'informix';

create table 'informix'.tc_direct_project (
    project_id INT not null,
    name VARCHAR(200) not null,
    description lvarchar(10000),
    user_id INT not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_date DATETIME YEAR TO FRACTION
);
 
alter table 'informix'.tc_direct_project add constraint primary key 
	(project_id)
	constraint tc_direct_project_pkey;

