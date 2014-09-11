CREATE DATABASE tcs_catalog IN ol_topcoder WITH BUFFERED LOG;
database tcs_catalog;

grant dba to informix ;
grant connect to coder ;
grant connect to readonly ;
grant connect to veredox ;
grant connect to openaim ;
grant connect to truveo ;
grant connect to winformula ;
grant connect to openxtraz ;
-- User public does not have connect privilege;
create table 'informix'.company_size (
    company_size_id DECIMAL(12,0) not null,
    description VARCHAR(25) not null
)
lock mode row;

revoke all on company_size from 'public';
create table 'informix'.comp_categories (
    comp_categories_id DECIMAL(12,0) not null,
    component_id DECIMAL(12,0),
    category_id DECIMAL(12,0)
)

lock mode row;

revoke all on comp_categories from 'public';
create table 'informix'.comp_dependencies (
    comp_dependency_id DECIMAL(12,0) not null,
    comp_vers_id DECIMAL(12,0),
    child_comp_vers_id DECIMAL(12,0)
)

lock mode row;

revoke all on comp_dependencies from 'public';
create table 'informix'.comp_documentation (
    document_id DECIMAL(12,0) not null,
    comp_vers_id DECIMAL(12,0),
    document_type_id DECIMAL(12,0),
    document_name VARCHAR(254) not null,
    url VARCHAR(254) not null
)

lock mode row;

revoke all on comp_documentation from 'public';
create table 'informix'.comp_download (
    download_id DECIMAL(12,0) not null,
    comp_vers_id DECIMAL(12,0),
    url VARCHAR(254) not null,
    description VARCHAR(254) not null
)

lock mode row;

revoke all on comp_download from 'public';
create table 'informix'.comp_examples (
    example_id DECIMAL(12,0) not null,
    comp_vers_id DECIMAL(12,0),
    url VARCHAR(254) not null,
    description lvarchar
)

lock mode row;

revoke all on comp_examples from 'public';
create table 'informix'.comp_keywords (
    comp_keywords_id DECIMAL(12,0) not null,
    component_id DECIMAL(12,0),
    keyword VARCHAR(50) not null
)

lock mode row;

revoke all on comp_keywords from 'public';
create table 'informix'.comp_reviews (
    comp_reviews_id DECIMAL(12,0) not null,
    comp_vers_id DECIMAL(12,0),
    login_id DECIMAL(12,0),
    review_time DATETIME YEAR TO FRACTION not null,
    rating DECIMAL(5) not null,
    comments lvarchar not null
)

lock mode row;

revoke all on comp_reviews from 'public';
create table 'informix'.comp_technology (
    comp_tech_id DECIMAL(12,0) not null,
    comp_vers_id DECIMAL(12,0),
    technology_type_id DECIMAL(12,0)
)

lock mode row;

revoke all on comp_technology from 'public';
create table 'informix'.contact_type (
    contact_type_id DECIMAL(12,0) not null,
    description VARCHAR(254) not null,
    status_id DECIMAL(12,0) not null
)

lock mode row;

revoke all on contact_type from 'public';
create table 'informix'.country_codes (
    country_code DECIMAL(12,0) not null,
    description VARCHAR(100) not null
)

lock mode row;

revoke all on country_codes from 'public';
create table 'informix'.doc_types (
    document_type_id DECIMAL(12,0) not null,
    description VARCHAR(254) not null,
    status_id DECIMAL(12,0) not null
)

lock mode row;

revoke all on doc_types from 'public';
create table 'informix'.download_tracking (
    download_track_id DECIMAL(12,0) not null,
    comp_vers_id DECIMAL(12,0),
    login_id DECIMAL(12,0),
    license_level_id DECIMAL(12,0),
    download_id DECIMAL(12,0),
    unit_cost DECIMAL(12,0) not null,
    price DECIMAL(10,2) not null,
    create_time DATETIME YEAR TO SECOND not null
)

lock mode row;

revoke all on download_tracking from 'public';
create table 'informix'.key_generation (
    user_def CHAR(18) not null,
    high_value DECIMAL(13) not null
)

lock mode row;

revoke all on key_generation from 'public';
create table 'informix'.license_level (
    license_level_id DECIMAL(12,0) not null,
    price_multiplier DECIMAL(10,3) not null,
    description VARCHAR(254) not null,
    status_id DECIMAL(12,0)
)

lock mode row;

revoke all on license_level from 'public';
create table 'informix'.phase (
    phase_id DECIMAL(12,0) not null,
    description VARCHAR(254) not null
)

lock mode row;

revoke all on phase from 'public';
create table 'informix'.price_tiers (
    tier_id DECIMAL(12,0) not null,
    discount_percent DECIMAL(5,2) not null
)

lock mode row;

revoke all on price_tiers from 'public';
create table 'informix'.roles (
    role_id DECIMAL(12,0) not null,
    role_name VARCHAR(100) not null,
    description VARCHAR(254) not null,
    status_id DECIMAL(12,0) not null
)

lock mode row;

revoke all on roles from 'public';
create table 'informix'.status (
    status_id DECIMAL(12,0) not null,
    description VARCHAR(254) not null
)

lock mode row;

revoke all on status from 'public';
create table 'informix'.technology_types (
    technology_type_id DECIMAL(12,0) not null,
    technology_name VARCHAR(100) not null,
    description VARCHAR(254) not null,
    status_id DECIMAL(12,0) not null
)

lock mode row;

revoke all on technology_types from 'public';
create table 'informix'.user_contact (
    user_contact_id DECIMAL(12,0) not null,
    contact_type_id DECIMAL(12,0),
    login_id DECIMAL(12,0),
    contact_info VARCHAR(254) not null
)

lock mode row;

revoke all on user_contact from 'public';
create table 'informix'.user_member (
    user_member_id DECIMAL(12,0) not null,
    login_id DECIMAL(12,0),
    tc_rating DECIMAL(5) not null,
    tcs_rating DECIMAL(5) not null
)

lock mode row;

revoke all on user_member from 'public';
create table 'informix'.user_technologies (
    user_tech_id DECIMAL(12,0) not null,
    technology_type_id DECIMAL(12,0),
    login_id DECIMAL(12,0),
    rating DECIMAL(5) not null,
    months DECIMAL(5) not null
)

lock mode row;

revoke all on user_technologies from 'public';
create table 'informix'.word_search (
    word_search_id DECIMAL(12,0) not null,
    document_id DECIMAL(12,0) not null,
    index_word CHAR(100) not null,
    nbr_occurrences DECIMAL(12,0) not null
)

lock mode row;

revoke all on word_search from 'public';
create table 'informix'.word_search_ctgy (
    word_srch_ctgy_id DECIMAL(12,0) not null,
    category_id CHAR(50) not null,
    document_id DECIMAL(12,0) not null
)

lock mode row;

revoke all on word_search_ctgy from 'public';
create table 'informix'.word_search_doc (
    document_id DECIMAL(12,0) not null
)

lock mode row;

revoke all on word_search_doc from 'public';
create table 'informix'.word_search_excl (
    word_srch_exl_id DECIMAL(12,0) not null,
    exclude_word VARCHAR(100) not null
)

lock mode row;

revoke all on word_search_excl from 'public';
create table 'informix'.command_group_lu (
    command_group_id DECIMAL(5,0) not null,
    command_group_name VARCHAR(100)
)

lock mode row;

revoke all on command_group_lu from 'public';
create table 'informix'.command (
    command_id DECIMAL(10,0),
    command_desc VARCHAR(100),
    command_group_id DECIMAL(5,0)
)

lock mode row;

revoke all on command from 'public';
create table 'informix'.query (
    query_id DECIMAL(10,0),
    text TEXT,
    name VARCHAR(100),
    ranking INT,
    column_index INT
)

lock mode row;

revoke all on query from 'public';
create table 'informix'.input_lu (
    input_id DECIMAL(10,0),
    input_code VARCHAR(25),
    data_type_id DECIMAL(5,0),
    input_desc VARCHAR(100)
)

lock mode row;

revoke all on input_lu from 'public';
create table 'informix'.query_input_xref (
    query_id DECIMAL(10,0),
    optional CHAR(1),
    default_value VARCHAR(100),
    input_id DECIMAL(10,0),
    sort_order DECIMAL(3,0)
)

lock mode row;

revoke all on query_input_xref from 'public';
create table 'informix'.command_query_xref (
    command_id DECIMAL(10,0),
    query_id DECIMAL(10,0),
    sort_order DECIMAL(3,0)
)

lock mode row;

revoke all on command_query_xref from 'public';
create table 'informix'.tc_user_xref (
    user_id DECIMAL(12,0),
    tc_coder_id DECIMAL(10)
)

lock mode row;

revoke all on tc_user_xref from 'public';
create table 'informix'.component_inquiry (
    component_inquiry_id DECIMAL(12,0) not null,
    component_id DECIMAL(12,0),
    user_id DECIMAL(12,0) not null,
    comment VARCHAR(254),
    agreed_to_terms DECIMAL(1) not null,
    rating DECIMAL(5) not null,
    phase DECIMAL(12,0),
    tc_user_id DECIMAL(12,0),
    version DECIMAL(12,0),
    create_time DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    project_id DECIMAL(10,0)
)

lock mode row;

revoke all on component_inquiry from 'public';
create table 'informix'.comp_version_dates (
    comp_version_dates_id DECIMAL(12,0) not null,
    comp_vers_id DECIMAL(12,0) not null,
    phase_id DECIMAL(12,0) not null,
    posting_date DATETIME YEAR TO DAY not null,
    initial_submission_date DATETIME YEAR TO DAY not null,
    winner_announced_date DATETIME YEAR TO DAY not null,
    final_submission_date DATETIME YEAR TO DAY not null,
    estimated_dev_date DATETIME YEAR TO DAY,
    price DECIMAL(10,2),
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
)

lock mode row;

revoke all on comp_version_dates from 'public';
create table 'informix'.comp_level (
    level_id DECIMAL(12,0) not null,
    description VARCHAR(254) not null
)

lock mode row;

revoke all on comp_level from 'public';
create table 'informix'.user_master_tmp (
    login_id DECIMAL(12,0) not null,
    last_login_time DATETIME YEAR TO FRACTION not null,
    num_logins DECIMAL(7) not null,
    status_id DECIMAL(12,0) not null
)

lock mode row;

revoke all on user_master_tmp from 'public';
create table 'informix'.user_master (
    login_id DECIMAL(12,0) not null,
    last_login_time DATETIME YEAR TO FRACTION not null,
    num_logins DECIMAL(7) not null,
    status_id DECIMAL(12,0)
)

lock mode row;

revoke all on user_master from 'public';
create table 'informix'.comp_version_dates_history (
    comp_version_dates_history_id DECIMAL(12,0),
    comp_vers_id DECIMAL(12,0),
    phase_id DECIMAL(12,0),
    posting_date DATETIME YEAR TO DAY,
    initial_submission_date DATETIME YEAR TO DAY,
    winner_announced_date DATETIME YEAR TO DAY,
    final_submission_date DATETIME YEAR TO DAY,
    estimated_dev_date DATETIME YEAR TO DAY,
    price DECIMAL(10,2),
    total_submissions DECIMAL(12,0) default 0,
    status_id DECIMAL(12,0),
    create_time DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
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
    production_date_comment VARCHAR(254)
)

lock mode row;

revoke all on comp_version_dates_history from 'public';
create table 'informix'.review_resp (
    review_resp_id DECIMAL(12,0) not null,
    review_resp_name VARCHAR(254) not null,
    phase_id DECIMAL(5,0)
)

lock mode row;

revoke all on review_resp from 'public';
create table 'informix'.sample_inquiry (
    sample_inquiry_id DECIMAL(16) not null,
    first_name VARCHAR(250) not null,
    last_name VARCHAR(250) not null,
    email_address VARCHAR(250) not null,
    catalog VARCHAR(40) not null,
    contact_me DECIMAL(1) not null,
    country_id DECIMAL(16) not null,
    create_date_time DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null
)

lock mode row;

revoke all on sample_inquiry from 'public';
create table 'informix'.comp_level_phase (
    level_id DECIMAL(12,0) not null,
    phase_id DECIMAL(12,0) not null,
    price_increase DECIMAL(18,2)
)

lock mode row;

revoke all on comp_level_phase from 'public';
create table 'informix'.user_component_score (
    user_component_score_id DECIMAL(10) not null,
    user_id DECIMAL(10) not null,
    level_id DECIMAL(4) not null,
    comp_vers_id DECIMAL(10) not null,
    phase_id DECIMAL(3) not null,
    score DECIMAL(5,2) not null,
    money DECIMAL(10,2) default 0 not null,
    processed DECIMAL(16) default 0 not null,
    rating DECIMAL(6),
    place DECIMAL(4),
    multiplier DECIMAL(3,0),
    submission_date DATETIME YEAR TO DAY,
    mod_date_time DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    create_date_time DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null
)

lock mode row;

revoke all on user_component_score from 'public';
create table 'informix'.user_rating (
    user_id DECIMAL(10) not null,
    rating DECIMAL(10) default 0 not null,
    phase_id DECIMAL(3) not null,
    vol DECIMAL(10) default 0 not null,
    rating_no_vol DECIMAL(10) default 0 not null,
    num_ratings DECIMAL(5) default 0 not null,
    mod_date_time DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    create_date_time DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    last_rated_project_id DECIMAL(12,0)
)

lock mode row;

revoke all on user_rating from 'public';
create table 'informix'.tcs_ratings_history (
    score DECIMAL(5,2) not null,
    vol DECIMAL(10,0) not null,
    rating DECIMAL(10,0) not null,
    timestamp DATETIME YEAR TO FRACTION not null,
    level_id DECIMAL(3,0) not null,
    phase_id DECIMAL(3,0) not null
)

lock mode row;

revoke all on tcs_ratings_history from 'public';
create table 'informix'.rboard_status_lu (
    status_id DECIMAL(3,0) not null,
    status_desc VARCHAR(64)
)

lock mode row;

revoke all on rboard_status_lu from 'public';
create table 'informix'.rboard_user_audit (
    user_id DECIMAL(10,0) not null,
    phase_id DECIMAL(5,0),
    data_element VARCHAR(64) not null,
    old_val VARCHAR(255),
    new_val VARCHAR(255),
    timestamp DATETIME YEAR TO SECOND default CURRENT YEAR TO SECOND
)

lock mode row;

revoke all on rboard_user_audit from 'public';
create table 'informix'.rboard_notes (
    user_id DECIMAL(10,0) not null,
    note VARCHAR(255),
    timestamp DATETIME YEAR TO SECOND default CURRENT YEAR TO SECOND
)

lock mode row;

revoke all on rboard_notes from 'public';
create table 'informix'.rboard_contact_sched (
    user_id DECIMAL(10,0) not null,
    phase_id DECIMAL(5,0),
    contact_on DATE not null,
    note VARCHAR(255)
)

lock mode row;

revoke all on rboard_contact_sched from 'public';
create table 'informix'.rboard_application (
    user_id DECIMAL(10,0) not null,
    project_id DECIMAL(12,0) not null,
    phase_id DECIMAL(5,0) not null,
    review_resp_id DECIMAL(3,0),
    primary_ind DECIMAL(1,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode row;

revoke all on rboard_application from 'public';
create table 'informix'.rboard_payment (
    project_id DECIMAL(10,0) not null,
    phase_id DECIMAL(5,0) not null,
    primary_ind DECIMAL(1,0) not null,
    amount DECIMAL(7,2) not null,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode row;

revoke all on rboard_payment from 'public';
create table 'informix'.contest_type_lu (
    contest_type_id DECIMAL(5,0),
    contest_type_desc VARCHAR(64)
)

lock mode row;

revoke all on contest_type_lu from 'public';
create table 'informix'.contest (
    contest_id DECIMAL(10,0),
    contest_name VARCHAR(128),
    phase_id DECIMAL(5,0),
    contest_type_id DECIMAL(5,0),
    start_date DATETIME YEAR TO FRACTION,
    end_date DATETIME YEAR TO FRACTION,
    event_id DECIMAL(10,0),
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    contest_result_calculator_id INT,
    project_category_id INT
)

lock mode row;

revoke all on contest from 'public';
create table 'informix'.contest_project_xref (
    contest_id DECIMAL(10,0),
    project_id DECIMAL(10,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null
)

lock mode row;

revoke all on contest_project_xref from 'public';
create table 'informix'.prize_type_lu (
    prize_type_id DECIMAL(5,0),
    prize_type_desc VARCHAR(64)
)

lock mode row;

revoke all on prize_type_lu from 'public';
create table 'informix'.user_contest_prize (
    contest_prize_id DECIMAL(10,0),
    user_id DECIMAL(10,0),
    payment DECIMAL(10,2)
)

lock mode row;

revoke all on user_contest_prize from 'public';
create table 'informix'.project_result (
    user_id DECIMAL(10,0),
    project_id DECIMAL(10,0),
    old_rating DECIMAL(5,0),
    new_rating DECIMAL(5,0),
    old_reliability DECIMAL(5,4),
    new_reliability DECIMAL(5,4),
    raw_score DECIMAL(5,2),
    final_score DECIMAL(5,2),
    payment DECIMAL(10,2),
    placed DECIMAL(6,0),
    rating_ind DECIMAL(1,0),
    valid_submission_ind DECIMAL(1,0),
    reliability_ind DECIMAL(1,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    reliable_submission_ind DECIMAL(1,0),
    passed_review_ind DECIMAL(1,0),
    point_adjustment FLOAT,
    current_reliability_ind DECIMAL(1,0),
    rating_order INT
)

lock mode row;

revoke all on project_result from 'public';
create table 'informix'.user_reliability (
    user_id DECIMAL(10,0),
    rating DECIMAL(5,4),
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    phase_id DECIMAL(12,0)
)

lock mode row;

revoke all on user_reliability from 'public';
create table 'informix'.royalty (
    user_id DECIMAL(10,0),
    amount DECIMAL(7,2),
    description VARCHAR(254),
    royalty_date DATE,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null
)

lock mode row;

revoke all on royalty from 'public';
create table 'informix'.user_event_xref (
    user_id DECIMAL(10,0) not null,
    event_id DECIMAL(10,0) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null
)

lock mode row;

revoke all on user_event_xref from 'public';
create table 'informix'.rboard_payment_stage (
    component_name VARCHAR(254),
    level_id VARCHAR(10),
    phase_desc VARCHAR(254),
    handle VARCHAR(20),
    function VARCHAR(10),
    total_payment DECIMAL(7,2),
    date_paid VARCHAR(20),
    check_num DECIMAL(10,0)
)

lock mode row;

revoke all on rboard_payment_stage from 'public';
create table 'informix'.notification_mail_type_lu (
    notification_mail_type_id DECIMAL(3,0),
    mail_template VARCHAR(254) not null,
    subject VARCHAR(254) not null,
    from VARCHAR(254) not null
)

lock mode row;

revoke all on notification_mail_type_lu from 'public';
create table 'informix'.notification_event (
    notification_event_id DECIMAL(5,0),
    event VARCHAR(254) not null,
    notification_mail_type_id DECIMAL(3,0) not null,
    description VARCHAR(254)
)

lock mode row;

revoke all on notification_event from 'public';
create table 'informix'.user_notification_event_xref (
    notification_event_id DECIMAL(5,0) not null,
    user_id DECIMAL(10,0) not null
)

lock mode row;

revoke all on user_notification_event_xref from 'public';
create table 'informix'.dual (
    value INT
)

lock mode row;

revoke all on dual from 'public';
create table 'informix'.comp_reg_answer (
    comp_reg_answer_id DECIMAL(10,0),
    comp_reg_question_id DECIMAL(10,0) not null,
    answer_text VARCHAR(250) not null,
    sort_order DECIMAL(3,0),
    is_active DECIMAL(1,0) default 1
)

lock mode row;

revoke all on comp_reg_answer from 'public';
create table 'informix'.comp_reg_response (
    comp_reg_question_id DECIMAL(10,0) not null,
    comp_reg_answer_id DECIMAL(10,0),
    response_text TEXT,
    user_id DECIMAL(10,0),
    project_id DECIMAL(10,0) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null
)

lock mode row;

revoke all on comp_reg_response from 'public';
create table 'informix'.question_style (
    question_style_id DECIMAL(3,0),
    question_style_desc VARCHAR(25) not null,
    status_id DECIMAL(12,0) not null
)

lock mode row;

revoke all on question_style from 'public';
create table 'informix'.gp_user_reliability (
    user_id DECIMAL(10,0),
    rating DECIMAL(5,4),
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode page;

revoke all on gp_user_reliability from 'public';
create table 'informix'.comp_reg_question (
    comp_reg_question_id DECIMAL(10,0),
    question_text lvarchar not null,
    question_style_id DECIMAL(3,0) not null,
    is_active DECIMAL(1,0) default 1,
    is_required DECIMAL(1,0) default 1
)

lock mode row;

revoke all on comp_reg_question from 'public';
create table 'informix'.project_wager (
    user_id DECIMAL(10,0),
    project_id DECIMAL(10,0),
    wager_amount DECIMAL(5,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode row;

revoke all on project_wager from 'public';
create table 'informix'.catalog (
    catalog_id DECIMAL(12,0) not null,
    catalog_name VARCHAR(100) not null
)

lock mode row;

revoke all on catalog from 'public';
create table 'informix'.rboard_user (
    user_id DECIMAL(10,0) not null,
    project_type_id DECIMAL(12,0) not null,
    catalog_id DECIMAL(12,0) not null,
    status_id DECIMAL(3,0) not null,
    immune_ind DECIMAL(1,0) not null
)

lock mode row;

revoke all on rboard_user from 'public';
create table 'informix'.category_catalog (
    catalog_id DECIMAL(12,0) not null,
    category_id DECIMAL(12,0) not null
)

lock mode row;

revoke all on category_catalog from 'public';
create table 'informix'.specification_type (
    specification_type_id DECIMAL(5,0) not null,
    specification_type_desc VARCHAR(50) not null
)

lock mode row;

revoke all on specification_type from 'public';
create table 'informix'.specification (
    specification_id DECIMAL(12,0) not null,
    specification_uploader_id DECIMAL(12,0) not null,
    specification_type_id DECIMAL(5,0) not null,
    passed_auto_screening DECIMAL(1,0),
    specification_url VARCHAR(255) not null,
    specification_remote_filename VARCHAR(50) not null,
    specification_upload_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on specification from 'public';
create table 'informix'.command_execution (
    command_id DECIMAL(10,0),
    timestamp DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    execution_time INT
)

lock mode page;

revoke all on command_execution from 'public';
create table 'informix'.project_type_lu (
    project_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    is_generic boolean default 'f'not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on project_type_lu from 'public';
create table 'informix'.project_category_lu (
    project_category_id INT not null,
    project_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on project_category_lu from 'public';
create table 'informix'.scorecard_type_lu (
    scorecard_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on scorecard_type_lu from 'public';
create table 'informix'.scorecard_status_lu (
    scorecard_status_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on scorecard_status_lu from 'public';
create table 'informix'.scorecard (
    scorecard_id INT not null,
    scorecard_status_id INT not null,
    scorecard_type_id INT not null,
    project_category_id INT not null,
    name VARCHAR(64) not null,
    version VARCHAR(16) not null,
    min_score FLOAT not null,
    max_score FLOAT not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on scorecard from 'public';
create table 'informix'.scorecard_group (
    scorecard_group_id INT not null,
    scorecard_id INT not null,
    name VARCHAR(64) not null,
    weight FLOAT not null,
    sort DECIMAL(3,0) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on scorecard_group from 'public';
create table 'informix'.scorecard_question_type_lu (
    scorecard_question_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on scorecard_question_type_lu from 'public';
create table 'informix'.scorecard_question (
    scorecard_question_id INT not null,
    scorecard_question_type_id INT not null,
    scorecard_section_id INT not null,
    description lvarchar(4096) not null,
    guideline lvarchar(4096),
    weight FLOAT not null,
    sort DECIMAL(3,0) not null,
    upload_document DECIMAL(1,0) not null,
    upload_document_required DECIMAL(1,0) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on scorecard_question from 'public';
create table 'informix'.project_status_lu (
    project_status_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on project_status_lu from 'public';
create table 'informix'.project (
    project_id INT not null,
    project_status_id INT not null,
    project_category_id INT not null,
    tc_direct_project_id INTEGER,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on project from 'public';
create table 'informix'.project_info_type_lu (
    project_info_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(25) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on project_info_type_lu from 'public';
create table 'informix'.phase_status_lu (
    phase_status_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on phase_status_lu from 'public';
create table 'informix'.phase_type_lu (
    phase_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on phase_type_lu from 'public';
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

lock mode row;

revoke all on project_phase from 'public';
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

lock mode row;

revoke all on phase_dependency from 'public';
create table 'informix'.phase_criteria_type_lu (
    phase_criteria_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on phase_criteria_type_lu from 'public';
create table 'informix'.phase_criteria (
    project_phase_id INT not null,
    phase_criteria_type_id INT not null,
    parameter VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on phase_criteria from 'public';
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

lock mode row;

revoke all on resource_role_lu from 'public';
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

lock mode row;

revoke all on resource from 'public';
create table 'informix'.resource_info_type_lu (
    resource_info_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on resource_info_type_lu from 'public';
create table 'informix'.upload_type_lu (
    upload_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on upload_type_lu from 'public';
create table 'informix'.upload_status_lu (
    upload_status_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on upload_status_lu from 'public';
create table 'informix'.upload (
    upload_id INT not null,
    project_id INT not null,
    resource_id INT not null,
    upload_type_id INT not null,
    upload_status_id INT not null,
    parameter VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on upload from 'public';
create table 'informix'.submission_status_lu (
    submission_status_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on submission_status_lu from 'public';
create table 'informix'.submission (
    submission_id INT not null,
    upload_id INT not null,
    submission_status_id INT not null,
    screening_score DECIMAL(5,2),
    initial_score DECIMAL(5,2),
    final_score DECIMAL(5,2),
    placement DECIMAL(3,0),
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on submission from 'public';
create table 'informix'.resource_submission (
    resource_id INT not null,
    submission_id INT not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on resource_submission from 'public';
create table 'informix'.comment_type_lu (
    comment_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on comment_type_lu from 'public';
create table 'informix'.review (
    review_id INT not null,
    resource_id INT not null,
    submission_id INT,
    scorecard_id INT not null,
    committed DECIMAL(1,0) not null,
    score FLOAT,
    initial_score DECIMAL(5,2),
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on review from 'public';
create table 'informix'.review_item (
    review_item_id INT not null,
    review_id INT not null,
    scorecard_question_id INT not null,
    upload_id INT,
    answer VARCHAR(254) not null,
    sort DECIMAL(3,0) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on review_item from 'public';
create table 'informix'.review_comment (
    review_comment_id INT not null,
    resource_id INT not null,
    review_id INT not null,
    comment_type_id INT not null,
    content lvarchar(4096) not null,
    extra_info VARCHAR(254),
    sort DECIMAL(3,0) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on review_comment from 'public';
create table 'informix'.review_item_comment (
    review_item_comment_id INT not null,
    resource_id INT not null,
    review_item_id INT not null,
    comment_type_id INT not null,
    content lvarchar(4096) not null,
    extra_info VARCHAR(254),
    sort DECIMAL(3,0) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on review_item_comment from 'public';
create table 'informix'.deliverable_lu (
    deliverable_id INT not null,
    phase_type_id INT not null,
    resource_role_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(64) not null,
    per_submission DECIMAL(1,0) not null,
    required DECIMAL(1,0) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on deliverable_lu from 'public';
create table 'informix'.project_audit (
    project_audit_id INT not null,
    project_id INT not null,
    update_reason VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on project_audit from 'public';
create table 'informix'.notification_type_lu (
    notification_type_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on notification_type_lu from 'public';
create table 'informix'.notification (
    project_id INT not null,
    external_ref_id INT not null,
    notification_type_id INT not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on notification from 'public';
create table 'informix'.screening_status_lu (
    screening_status_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on screening_status_lu from 'public';
create table 'informix'.screening_task (
    screening_task_id INT not null,
    upload_id INT not null,
    screening_status_id INT not null,
    screener_id INT,
    start_timestamp DATETIME YEAR TO FRACTION,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on screening_task from 'public';
create table 'informix'.response_severity_lu (
    response_severity_id INT not null,
    name VARCHAR(64) not null,
    description VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on response_severity_lu from 'public';
create table 'informix'.screening_response_lu (
    screening_response_id INT not null,
    response_severity_id INT not null,
    response_code VARCHAR(16) not null,
    response_text VARCHAR(254) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on screening_response_lu from 'public';
create table 'informix'.screening_result (
    screening_result_id INT not null,
    screening_task_id INT not null,
    screening_response_id INT not null,
    dynamic_response_text lvarchar(4096) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on screening_result from 'public';
create table 'informix'.default_scorecard (
    project_category_id INT not null,
    scorecard_type_id INT not null,
    scorecard_id INT not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on default_scorecard from 'public';
create table 'informix'.user_role (
    user_role_id DECIMAL(12,0) not null,
    comp_vers_id DECIMAL(12,0),
    role_id DECIMAL(12,0),
    login_id DECIMAL(12,0),
    description VARCHAR(254)
)

lock mode row;

revoke all on user_role from 'public';
create table 'informix'.resource_info (
    resource_id INT not null,
    resource_info_type_id INT not null,
    value VARCHAR(255) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on resource_info from 'public';
create table 'informix'.project_info (
    project_id INT not null,
    project_info_type_id INT not null,
    value VARCHAR(255) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on project_info from 'public';
create table 'informix'.gp_user_contest_prize (
    contest_prize_id DECIMAL(10,0),
    user_id DECIMAL(10,0),
    payment DECIMAL(10,2)
)

lock mode row;

revoke all on gp_user_contest_prize from 'public';
create table 'informix'.user_rating_audit (
    user_id DECIMAL(10,0),
    phase_id DECIMAL(3,0),
    column_name VARCHAR(30),
    old_value VARCHAR(254),
    new_value VARCHAR(254),
    timestamp DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode row;

revoke all on user_rating_audit from 'public';
create table 'informix'.user_reliability_audit (
    user_id DECIMAL(10,0),
    phase_id DECIMAL(3,0),
    column_name VARCHAR(30),
    old_value VARCHAR(254),
    new_value VARCHAR(254),
    timestamp DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode row;

revoke all on user_reliability_audit from 'public';
create table 'informix'.comp_versions (
    comp_vers_id DECIMAL(12,0) not null,
    component_id DECIMAL(12,0),
    version DECIMAL(12,0) not null,
    version_text CHAR(20) not null,
    create_time datetime year to fraction(3)  default current year to fraction(3),
    phase_id DECIMAL(12,0) not null,
    phase_time DATETIME YEAR TO FRACTION not null,
    price DECIMAL(10,2) not null,
    comments lvarchar,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
	suspended_ind DECIMAL(1,0) default 0,
	browse VARCHAR(255) default null,
	location VARCHAR(255) default null,
	issue_tracker_path VARCHAR(100) default null,
	revision VARCHAR(10) default null
)

lock mode row;

revoke all on comp_versions from 'public';
create table 'informix'.comp_jive_category_xref (
    comp_vers_id DECIMAL(12,0) not null,
    jive_category_id INT not null
)

lock mode page;

revoke all on comp_jive_category_xref from 'public';
create table 'informix'.comp_forum_xref_bak (
    comp_forum_id DECIMAL(12,0) not null,
    comp_vers_id DECIMAL(12,0),
    forum_id DECIMAL(12,0),
    forum_type DECIMAL(5) not null,
    jive_category_id DECIMAL(15,0)
)

lock mode row;

revoke all on comp_forum_xref_bak from 'public';
create table 'informix'.contest_prize (
    contest_prize_id DECIMAL(10,0),
    contest_id DECIMAL(10,0),
    prize_type_id DECIMAL(5,0),
    place DECIMAL(2,0),
    prize_amount DECIMAL(10,2),
    prize_desc VARCHAR(100),
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null
)

lock mode row;

revoke all on contest_prize from 'public';
create table 'informix'.season (
    season_id DECIMAL(6,0),
    name NVARCHAR(254),
    rookie_competition_ind DECIMAL(1,0),
    next_rookie_season_id DECIMAL(6,0),
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null
)

lock mode row;

revoke all on season from 'public';
create table 'informix'.stage (
    stage_id DECIMAL(6,0),
    season_id DECIMAL(6,0) not null,
    name NVARCHAR(254) not null,
    start_date DATETIME YEAR TO FRACTION not null,
    end_date DATETIME YEAR TO FRACTION not null,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null
)

lock mode row;

revoke all on stage from 'public';
create table 'informix'.contest_stage_xref (
    contest_id DECIMAL(10,0),
    stage_id DECIMAL(6,0),
    top_trip_winners DECIMAL(3,0),
    top_performers_factor DECIMAL(4,2)
)

lock mode row;

revoke all on contest_stage_xref from 'public';
create table 'informix'.contest_season_xref (
    contest_id DECIMAL(10,0),
    season_id DECIMAL(6,0)
)

lock mode row;

revoke all on contest_season_xref from 'public';
create table 'informix'.contest_result_calculator_lu (
    contest_result_calculator_id INT,
    class_name VARCHAR(100) not null
)

lock mode row;

revoke all on contest_result_calculator_lu from 'public';
create table 'informix'.scorecard_section (
    scorecard_section_id INT not null,
    scorecard_group_id INT not null,
    name lvarchar(1024) not null,
    weight FLOAT not null,
    sort DECIMAL(3,0) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on scorecard_section from 'public';
create table 'informix'.member_ban (
    ban_id INT not null,
    user_id INT not null,
    timestamp DATETIME YEAR TO FRACTION not null,
    expiration DATETIME YEAR TO FRACTION not null,
    removed boolean not null
)

lock mode row;

revoke all on member_ban from 'public';
create table 'informix'.message (
    message_id INT not null,
    from_handle VARCHAR(50) not null,
    project_id INT not null,
    project_name VARCHAR(50) not null,
    timestamp DATETIME YEAR TO FRACTION not null,
    subject VARCHAR(250),
    message lvarchar(4096) not null
)

lock mode row;

revoke all on message from 'public';
create table 'informix'.message_to_handle (
    message_id INT not null,
    to_handle VARCHAR(50) not null
)

lock mode row;

revoke all on message_to_handle from 'public';
create table 'informix'.status_assignment (
    status_assignment_id INT not null,
    offer_id INT not null,
    status_id SMALLINT not null,
    timestamp DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on status_assignment from 'public';
create table 'informix'.offer (
    offer_id INT not null,
    from_user_id INT not null,
    to_user_id INT not null,
    position_id INT not null,
    payment SMALLINT not null,
    message lvarchar(4096),
    current_status_assignment_id INT not null,
    rejection_cause lvarchar(4096)
)

lock mode row;

revoke all on offer from 'public';
create table 'informix'.team_header (
    team_id INT not null,
    name VARCHAR(32) not null,
    description VARCHAR(255) not null,
    finalized boolean not null,
    project_id INT not null,
    captain_resource_id INT not null,
    captain_payment SMALLINT not null
)

lock mode row;

revoke all on team_header from 'public';
create table 'informix'.team_properties (
    team_id INT not null,
    key VARCHAR(255) not null,
    value TEXT
)

lock mode row;

revoke all on team_properties from 'public';
create table 'informix'.team_position (
    position_id INT not null,
    team_id INT not null,
    name VARCHAR(32) not null,
    description VARCHAR(255) not null,
    published boolean not null,
    resource_id INT,
    payment SMALLINT not null
)

lock mode row;

revoke all on team_position from 'public';
create table 'informix'.team_position_properties (
    position_id INT not null,
    key VARCHAR(255) not null,
    value TEXT
)

lock mode row;

revoke all on team_position_properties from 'public';
create table 'informix'.team_manager_audit (
    team_manager_audit_id INT not null,
    user_id INT not null,
    entity_id INT not null,
    entity_type VARCHAR(15) not null,
    date DATETIME YEAR TO FRACTION not null,
    active boolean not null
)

lock mode row;

revoke all on team_manager_audit from 'public';
create table 'informix'.team_manager_audit_details (
    team_manager_audit_id INT not null,
    name VARCHAR(31) not null,
    old_value VARCHAR(255),
    new_value VARCHAR(255)
)

lock mode row;

revoke all on team_manager_audit_details from 'public';
create table 'informix'.team_manager_audit_custom_details (
    team_manager_audit_id INT not null,
    name VARCHAR(255) not null,
    old_value TEXT,
    new_value TEXT
)

lock mode row;

revoke all on team_manager_audit_custom_details from 'public';
create table 'informix'.facade_audit (
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    create_user VARCHAR(64) not null,
    session_key INT not null,
    message VARCHAR(255)
)

lock mode row;

revoke all on facade_audit from 'public';
create table 'informix'.comp_catalog (
    component_id DECIMAL(12,0) not null,
    current_version DECIMAL(12,0) not null,
    short_desc lvarchar,
    component_name VARCHAR(254) not null,
    description lvarchar(10000),
    function_desc lvarchar,
    create_time datetime year to fraction(3)  default current year to fraction(3),
    status_id DECIMAL(12,0) not null,
    root_category_id DECIMAL(12,0),
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    public_ind DECIMAL(1,0) default 0 not null
)

lock mode row;

revoke all on comp_catalog from 'public';
create table 'informix'.project_pablo (
    project_id INT not null,
    project_status_id INT not null,
    project_category_id INT not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on project_pablo from 'public';
create table 'informix'.framework (
    framework_id INT,
    name VARCHAR(100),
    description lvarchar
)

lock mode row;

revoke all on framework from 'public';
create table 'informix'.widget (
    widget_id INT,
    framework_id INT,
    name VARCHAR(100),
    description lvarchar
)

lock mode row;

revoke all on widget from 'public';
create table 'informix'.widget_version (
    widget_version_id INT,
    version_major INT not null,
    version_minor INT not null,
    version_increment INT not null,
    version_build INT not null,
    widget_id INT not null,
    notes lvarchar,
    version_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    download_url lvarchar(300),
    deprecate_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    current_version boolean,
    minimum_version boolean
)

lock mode row;

revoke all on widget_version from 'public';
create table 'informix'.project_info_before_moving_price (
    project_id INT not null,
    project_info_type_id INT not null,
    value VARCHAR(255) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION not null
)

lock mode row;

revoke all on project_info_before_moving_price from 'public';
create table 'informix'.comp_version_dates_before_moving_price (
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
)

lock mode row;

revoke all on comp_version_dates_before_moving_price from 'public';
create table 'informix'.dr_points (
    dr_points_id DECIMAL(10,0) not null,
    track_id DECIMAL(10,0) not null,
    dr_points_reference_type_id INT not null,
    dr_points_operation_id INT not null,
    dr_points_type_id INT not null,
    dr_points_status_id INT not null,
    dr_points_desc VARCHAR(100) not null,
    user_id DECIMAL(10,0) not null,
    amount DECIMAL(10,2) not null,
    application_date DATETIME YEAR TO FRACTION not null,
    award_date DATETIME YEAR TO FRACTION not null,
    reference_id DECIMAL(10,0),
    is_potential boolean default 'f',
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode row;

revoke all on dr_points from 'public';
create table 'informix'.dr_points_operation_lu (
    dr_points_operation_id INT not null,
    dr_points_operation_desc VARCHAR(50) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode page;

revoke all on dr_points_operation_lu from 'public';
create table 'informix'.dr_points_reference_type_lu (
    dr_points_reference_type_id INT not null,
    dr_points_reference_type_desc VARCHAR(50) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode page;

revoke all on dr_points_reference_type_lu from 'public';
create table 'informix'.dr_points_type_lu (
    dr_points_type_id INT not null,
    dr_points_type_desc VARCHAR(50) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode page;

revoke all on dr_points_type_lu from 'public';
create table 'informix'.dr_points_status_lu (
    dr_points_status_id INT not null,
    dr_points_status_desc VARCHAR(50) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode page;

revoke all on dr_points_status_lu from 'public';
create table 'informix'.points_calculator_lu (
    points_calculator_id INT not null,
    class_name VARCHAR(100) not null,
    points_calculator_desc VARCHAR(50) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode page;

revoke all on points_calculator_lu from 'public';
create table 'informix'.track (
    track_id DECIMAL(10,0) not null,
    points_calculator_id INT not null,
    track_type_id INT not null,
    track_status_id INT not null,
    track_desc VARCHAR(50) not null,
    track_start_date DATETIME YEAR TO FRACTION not null,
    track_end_date DATETIME YEAR TO FRACTION not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode page;

revoke all on track from 'public';
create table 'informix'.track_contest (
    track_contest_id DECIMAL(10,0) not null,
    track_contest_result_calculator_id INT not null,
    track_contest_type_id INT not null,
    track_id DECIMAL(10,0) not null,
    track_contest_desc VARCHAR(128) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode page;

revoke all on track_contest from 'public';
create table 'informix'.track_contest_result_calculator_lu (
    track_contest_result_calculator_id INT not null,
    class_name VARCHAR(100) not null,
    track_contest_result_calculator_desc VARCHAR(50) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode page;

revoke all on track_contest_result_calculator_lu from 'public';
create table 'informix'.track_contest_type_lu (
    track_contest_type_id INT not null,
    track_contest_type_desc VARCHAR(50) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode page;

revoke all on track_contest_type_lu from 'public';
create table 'informix'.track_project_category_xref (
    track_id DECIMAL(10,0) not null,
    project_category_id INT not null
)

lock mode page;

revoke all on track_project_category_xref from 'public';
create table 'informix'.track_type_lu (
    track_type_id INT not null,
    track_type_desc VARCHAR(50) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode page;

revoke all on track_type_lu from 'public';
create table 'informix'.track_status_lu (
    track_status_id INT not null,
    track_status_desc VARCHAR(50) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
)

lock mode page;

revoke all on track_status_lu from 'public';
create table 'informix'.categories (
    category_id DECIMAL(12,0) not null,
    parent_category_id DECIMAL(12,0),
    category_name VARCHAR(100) not null,
    description VARCHAR(254) not null,
    status_id DECIMAL(12,0) not null,
    viewable DECIMAL(1,0) default 1,
    is_custom boolean default 'f'
)

lock mode row;

revoke all on categories from 'public';
create table 'informix'.comp_client (
    component_id DECIMAL(12,0) not null,
    client_id INT not null
)

lock mode row;

revoke all on comp_client from 'public';
create table 'informix'.comp_user (
    component_id DECIMAL(12,0) not null,
    user_id DECIMAL(10,0) not null
)

lock mode row;

revoke all on comp_user from 'public';
create table 'informix'.user_client (
    user_id DECIMAL(10,0) not null,
    client_id DECIMAL(10,0) not null,
    admin_ind DECIMAL(12,0)
)

lock mode row;

revoke all on user_client from 'public';
create table 'informix'.comp_link (
    comp_link_id INT,
    comp_vers_id VARCHAR(255),
    link VARCHAR(255)
)

lock mode row;

revoke all on comp_link from 'public';

create table 'informix'.link_type_lu (
    link_type_id INT not null,
    link_type_name VARCHAR(64) not null,
    allow_overlap DECIMAL(1) 
)

lock mode row;
 
revoke all on link_type_lu from 'public';

create table 'informix'.linked_project_xref (
    source_project_id INT,
    dest_project_id INT,
    link_type_id INT
)

lock mode row;
 
revoke all on linked_project_xref from 'public';


create table 'informix'.third_party_library(

third_party_library_id DECIMAL(10,0) not null,
name varchar(50),
version varchar(50),
description varchar(100),
url varchar(50),
license varchar(50),
usage_comments varchar(150),
path varchar(200),
alias varchar(50),
notes varchar(150),
category varchar(50)
)  lock mode row;

 
revoke all on third_party_library from public;



create view "informix".v_latest_version (version,component_name,
       component_id,comp_vers_id,phase_id) as
   select max(x0.version ) ,x1.component_name ,x1.component_id ,
       x0.comp_vers_id ,x0.phase_id 
   from "informix".comp_versions x0 ,"informix".comp_catalog x1 
   where (((x0.phase_id IN (112. ,113. )) AND (x1.component_id = x0.component_id ) ) AND (x1.status_id = 102. ) ) group by x1.component_name ,
       x1.component_id ,x0.comp_vers_id ,x0.phase_id ;
revoke all on v_latest_version from 'public';
create view "informix".user_customer (user_customer_id,login_id,
       first_name,last_name,company,address,city,postal_code,
       country_code,telephone_country,telephone_area,telephone_nbr,
       use_components,use_consultants,receive_tcsnews,receive_newshtml,
       company_size_id,tier_id,activation_code,email_address) as
   select x0.user_id ,x0.user_id ,x0.first_name ,x0.last_name ,
       x6.company_name ,((x1.address1 || ' ' ) || x1.address2 ) ,
       x1.city ,x1.zip ,x1.country_code ,'' ,'' ,x4.phone_number ,
       0 ,0 ,0 ,0 ,1 ,0 ,x0.activation_code ,x3.address 
   from common_oltp:"informix".user x0 ,common_oltp:"informix".address x1 ,common_oltp:"informix".user_address_xref x2 ,common_oltp:"informix".email x3 ,outer(common_oltp:"informix".phone x4 ,common_oltp:"informix".contact x5 ,common_oltp:"informix".company x6 ) 
   where ((((((((x0.user_id = x2.user_id ) AND (x1.address_id = x2.address_id ) ) AND (x3.primary_ind = 1. ) ) AND (x3.user_id = x0.user_id ) ) AND (x4.user_id = x0.user_id ) ) AND (x4.primary_ind = 1. ) ) AND (x5.contact_id = x0.user_id ) ) AND (x5.company_id = x6.company_id ) ) ;
revoke all on user_customer from 'public';


create table 'informix'.project_spec (
   project_spec_id INTEGER NOT NULL,
   project_id INTEGER NOT NULL,
   version INTEGER NOT NULL,
   detailed_requirements LVARCHAR,
   submission_deliverables LVARCHAR,
   environment_setup_instruction LVARCHAR,
   final_submission_guidelines  LVARCHAR,
   create_user VARCHAR(64),
   create_date datetime year to fraction(3),
   modify_user VARCHAR(64),
   modify_date datetime year to fraction(3) 
)

lock mode row;

revoke all on project_spec from 'public';

-- SIZE = 5 ROWS
create table sale_status_lu (
    sale_status_id INTEGER NOT NULL,
    sale_status_desc VARCHAR(100) NOT NULL
)
lock mode row;

revoke all on sale_status_lu from 'public';

-- SIZE = 5 ROWS
create table sale_type_lu (
    sale_type_id INTEGER NOT NULL,
    sale_type_name VARCHAR(100) NOT NULL
)
lock mode row;

revoke all on sale_type_lu from 'public';

-- SIZE = 4000 ROWS PER YEAR IN THE FIRST YEAR
create table contest_sale (
    contest_sale_id INTEGER not null,
    contest_id INTEGER not null,
    sale_status_id INTEGER not null,
    price DECIMAL(10,2) not null,
    paypal_order_id VARCHAR(128),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    sale_reference_id VARCHAR(128),
    sale_type_id INTEGER
)

lock mode row;

revoke all on contest_sale from 'public';

create table 'informix'.software_competition_pipeline_info (
    id DECIMAL(10,0) not null,
    competition_id INT,
    review_payment DECIMAL(7,3),
    specification_review_payment DECIMAL(7,3),
    contest_fee DECIMAL(7,3),
    client_name VARCHAR(45),
    confidence DECIMAL(10,0),
    client_approval SMALLINT,
    pricing_approval SMALLINT,
    has_wiki_specification SMALLINT,
    passed_spec_review SMALLINT,
    has_dependent_competitions SMALLINT,
    was_reposted SMALLINT,
    notes VARCHAR(150),
    component_id DECIMAL(12,0),
    primary key (id) constraint pipeline_info_pk
   
)  lock mode row;

revoke all on 'informix'.software_competition_pipeline_info from public as informix;

create table 'informix'.software_competition_change_history (
    id DECIMAL(10,0) not null,
    manager VARCHAR(45),
    client VARCHAR(45),
    type VARCHAR(45),
    new_data VARCHAR(45),
    old_data VARCHAR(45),
    initial_data VARCHAR(45),
    change_time DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    change_type VARCHAR(45),
    software_competition_pipeline_info_id DECIMAL(10,0),
    primary key (id) constraint pipeline_history_pk
)  lock mode row;

revoke all on 'informix'.software_competition_change_history from public as informix;

create table 'informix'.audit_action_type_lu (
    audit_action_type_id INT not null,
    name VARCHAR(50) not null,
    description VARCHAR(50) not null,
    create_user VARCHAR(64) not null,
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null,
    modify_user VARCHAR(64) not null,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION not null
)  lock mode row;
revoke all on "informix".audit_action_type_lu from "public" as "informix";

create table 'informix'.project_user_audit  (
    project_user_audit_id DECIMAL(12,0) not null,
    project_id INT not null,
    resource_user_id DECIMAL(12,0) not null,
    resource_role_id INT not null,
    audit_action_type_id INT not null,
    action_date DATETIME YEAR TO FRACTION not null,
    action_user_id DECIMAL(12,0) not null
)  lock mode row;
revoke all on "informix".project_user_audit from "public" as "informix";


CREATE SEQUENCE PROJECT_USER_AUDIT_SEQ;
revoke all on "informix".PROJECT_USER_AUDIT_SEQ from "public";

create table 'informix'.project_info_audit (
	project_id int not null,
	project_info_type_id int not null,
	value varchar(255),
	audit_action_type_id int not null,
	action_date datetime year to fraction not null,
	action_user_id decimal(12,0) not null
)  lock mode row;
revoke all on 'informix'.project_info_audit from 'public' as 'informix';

create table 'informix'.project_phase_audit (
	project_phase_id int not null,
	scheduled_start_time datetime year to fraction,
	scheduled_end_time datetime year to fraction,
	audit_action_type_id int not null,
	action_date datetime year to fraction not null,
	action_user_id decimal(12,0) not null
)  lock mode row;
revoke all on 'informix'.project_phase_audit from 'public' as 'informix';

CREATE TABLE "informix".client_billing_config (
 
            client_billing_id DECIMAL(10,0) not null, 
            client_billing_config_type_id INT not null, 
            value VARCHAR(255) not null, 
    create_user VARCHAR(64) not null, 
    create_date DATETIME YEAR TO FRACTION not null, 
    modify_user VARCHAR(64) not null, 
    modify_date DATETIME YEAR TO FRACTION not null 
)  lock mode row;
revoke all on "informix".client_billing_config from public;

CREATE TABLE "informix".client_billing_config_type_lu ( 
            client_billing_config_type_id INT not null, 
    name VARCHAR(64) not null, 
    description VARCHAR(25) not null, 
    create_user VARCHAR(64) not null, 
    create_date DATETIME YEAR TO FRACTION not null, 
    modify_user VARCHAR(64) not null, 
    modify_date DATETIME YEAR TO FRACTION not null 
)  lock mode row; 
revoke all on "informix".client_billing_config_type_lu from public;

grant select on v_latest_version to 'informix' with grant option ;

grant select on user_customer to 'informix' with grant option ;

grant update(activation_code,city,company,country_code,email_address,first_name,last_name,login_id,postal_code,telephone_nbr,user_customer_id) on user_customer to 'informix' with grant option ;

grant select on user_customer to 'coder' as 'informix';

grant insert on comp_client to 'public' as 'informix';

grant update on comp_client to 'public' as 'informix';

grant delete on comp_client to 'public' as 'informix';

grant index on comp_client to 'public' as 'informix';

grant select on comp_client to 'public' as 'informix';

grant update on company_size to 'public' as 'informix';

grant select on company_size to 'public' as 'informix';

grant insert on company_size to 'public' as 'informix';

grant index on company_size to 'public' as 'informix';

grant delete on company_size to 'public' as 'informix';

grant index on comp_categories to 'public' as 'informix';

grant select on comp_categories to 'public' as 'informix';

grant update on comp_categories to 'public' as 'informix';

grant delete on comp_categories to 'public' as 'informix';

grant insert on comp_categories to 'public' as 'informix';

grant insert on comp_dependencies to 'public' as 'informix';

grant index on comp_dependencies to 'public' as 'informix';

grant delete on comp_dependencies to 'public' as 'informix';

grant update on comp_dependencies to 'public' as 'informix';

grant select on comp_dependencies to 'public' as 'informix';

grant update on comp_documentation to 'public' as 'informix';

grant index on comp_documentation to 'public' as 'informix';

grant insert on comp_documentation to 'public' as 'informix';

grant delete on comp_documentation to 'public' as 'informix';

grant select on comp_documentation to 'public' as 'informix';

grant index on comp_download to 'public' as 'informix';

grant update on comp_download to 'public' as 'informix';

grant select on comp_download to 'public' as 'informix';

grant delete on comp_download to 'public' as 'informix';

grant insert on comp_download to 'public' as 'informix';

grant update on comp_examples to 'public' as 'informix';

grant index on comp_examples to 'public' as 'informix';

grant insert on comp_examples to 'public' as 'informix';

grant delete on comp_examples to 'public' as 'informix';

grant select on comp_examples to 'public' as 'informix';

grant select on scorecard_section to 'public' as 'informix';

grant insert on scorecard_section to 'public' as 'informix';

grant update on scorecard_section to 'public' as 'informix';

grant delete on scorecard_section to 'public' as 'informix';

grant insert on comp_keywords to 'public' as 'informix';

grant index on comp_keywords to 'public' as 'informix';

grant delete on comp_keywords to 'public' as 'informix';

grant select on comp_keywords to 'public' as 'informix';

grant update on comp_keywords to 'public' as 'informix';

grant insert on comp_reviews to 'public' as 'informix';

grant select on comp_reviews to 'public' as 'informix';

grant update on comp_reviews to 'public' as 'informix';

grant delete on comp_reviews to 'public' as 'informix';

grant index on comp_reviews to 'public' as 'informix';

grant insert on comp_technology to 'public' as 'informix';

grant select on comp_technology to 'public' as 'informix';

grant update on comp_technology to 'public' as 'informix';

grant delete on comp_technology to 'public' as 'informix';

grant index on comp_technology to 'public' as 'informix';

grant index on contact_type to 'public' as 'informix';

grant update on contact_type to 'public' as 'informix';

grant select on contact_type to 'public' as 'informix';

grant insert on contact_type to 'public' as 'informix';

grant delete on contact_type to 'public' as 'informix';

grant index on country_codes to 'public' as 'informix';

grant update on country_codes to 'public' as 'informix';

grant select on country_codes to 'public' as 'informix';

grant insert on country_codes to 'public' as 'informix';

grant delete on country_codes to 'public' as 'informix';

grant delete on contest_prize to 'public' as 'informix';

grant insert on contest_prize to 'public' as 'informix';

grant select on contest_prize to 'public' as 'informix';

grant update on contest_prize to 'public' as 'informix';

grant index on contest_prize to 'public' as 'informix';

grant select on doc_types to 'public' as 'informix';

grant update on doc_types to 'public' as 'informix';

grant insert on doc_types to 'public' as 'informix';

grant index on doc_types to 'public' as 'informix';

grant delete on doc_types to 'public' as 'informix';

grant index on download_tracking to 'public' as 'informix';

grant select on download_tracking to 'public' as 'informix';

grant insert on download_tracking to 'public' as 'informix';

grant delete on download_tracking to 'public' as 'informix';

grant update on download_tracking to 'public' as 'informix';

grant insert on season to 'public' as 'informix';

grant index on season to 'public' as 'informix';

grant delete on season to 'public' as 'informix';

grant update on season to 'public' as 'informix';

grant select on season to 'public' as 'informix';

grant select on stage to 'public' as 'informix';

grant update on stage to 'public' as 'informix';

grant insert on stage to 'public' as 'informix';

grant index on stage to 'public' as 'informix';

grant delete on stage to 'public' as 'informix';

grant index on contest_stage_xref to 'public' as 'informix';

grant insert on contest_stage_xref to 'public' as 'informix';

grant delete on contest_stage_xref to 'public' as 'informix';

grant select on contest_stage_xref to 'public' as 'informix';

grant update on contest_stage_xref to 'public' as 'informix';

grant index on contest_season_xref to 'public' as 'informix';

grant insert on contest_season_xref to 'public' as 'informix';

grant select on contest_season_xref to 'public' as 'informix';

grant update on contest_season_xref to 'public' as 'informix';

grant delete on contest_season_xref to 'public' as 'informix';

grant update on contest_result_calculator_lu to 'public' as 'informix';

grant index on contest_result_calculator_lu to 'public' as 'informix';

grant select on contest_result_calculator_lu to 'public' as 'informix';

grant insert on contest_result_calculator_lu to 'public' as 'informix';

grant delete on contest_result_calculator_lu to 'public' as 'informix';

grant insert on member_ban to 'public' as 'informix';

grant delete on member_ban to 'public' as 'informix';

grant select on member_ban to 'public' as 'informix';

grant update on member_ban to 'public' as 'informix';

grant index on member_ban to 'public' as 'informix';

grant index on key_generation to 'public' as 'informix';

grant delete on key_generation to 'public' as 'informix';

grant select on key_generation to 'public' as 'informix';

grant insert on key_generation to 'public' as 'informix';

grant update on key_generation to 'public' as 'informix';

grant index on license_level to 'public' as 'informix';

grant update on license_level to 'public' as 'informix';

grant select on license_level to 'public' as 'informix';

grant insert on license_level to 'public' as 'informix';

grant delete on license_level to 'public' as 'informix';

grant insert on phase to 'public' as 'informix';

grant delete on phase to 'public' as 'informix';

grant select on phase to 'public' as 'informix';

grant index on phase to 'public' as 'informix';

grant update on phase to 'public' as 'informix';

grant insert on price_tiers to 'public' as 'informix';

grant update on price_tiers to 'public' as 'informix';

grant index on price_tiers to 'public' as 'informix';

grant select on price_tiers to 'public' as 'informix';

grant delete on price_tiers to 'public' as 'informix';

grant select on roles to 'public' as 'informix';

grant delete on roles to 'public' as 'informix';

grant insert on roles to 'public' as 'informix';

grant update on roles to 'public' as 'informix';

grant index on roles to 'public' as 'informix';

grant index on status to 'public' as 'informix';

grant select on status to 'public' as 'informix';

grant insert on status to 'public' as 'informix';

grant update on status to 'public' as 'informix';

grant delete on status to 'public' as 'informix';

grant delete on technology_types to 'public' as 'informix';

grant select on technology_types to 'public' as 'informix';

grant insert on technology_types to 'public' as 'informix';

grant update on technology_types to 'public' as 'informix';

grant index on technology_types to 'public' as 'informix';

grant update on user_contact to 'public' as 'informix';

grant index on user_contact to 'public' as 'informix';

grant delete on user_contact to 'public' as 'informix';

grant insert on user_contact to 'public' as 'informix';

grant select on user_contact to 'public' as 'informix';

grant insert on user_member to 'public' as 'informix';

grant select on user_member to 'public' as 'informix';

grant delete on user_member to 'public' as 'informix';

grant index on user_member to 'public' as 'informix';

grant update on user_member to 'public' as 'informix';

grant update on project_type_lu to 'public' as 'informix';

grant delete on project_type_lu to 'public' as 'informix';

grant select on project_type_lu to 'public' as 'informix';

grant insert on project_type_lu to 'public' as 'informix';

grant update on user_technologies to 'public' as 'informix';

grant delete on user_technologies to 'public' as 'informix';

grant insert on user_technologies to 'public' as 'informix';

grant select on user_technologies to 'public' as 'informix';

grant index on user_technologies to 'public' as 'informix';

grant delete on word_search to 'public' as 'informix';

grant select on word_search to 'public' as 'informix';

grant update on word_search to 'public' as 'informix';

grant insert on word_search to 'public' as 'informix';

grant index on word_search to 'public' as 'informix';

grant index on word_search_ctgy to 'public' as 'informix';

grant update on word_search_ctgy to 'public' as 'informix';

grant insert on word_search_ctgy to 'public' as 'informix';

grant select on word_search_ctgy to 'public' as 'informix';

grant delete on word_search_ctgy to 'public' as 'informix';

grant delete on word_search_doc to 'public' as 'informix';

grant insert on word_search_doc to 'public' as 'informix';

grant select on word_search_doc to 'public' as 'informix';

grant update on word_search_doc to 'public' as 'informix';

grant index on word_search_doc to 'public' as 'informix';

grant index on word_search_excl to 'public' as 'informix';

grant insert on word_search_excl to 'public' as 'informix';

grant update on word_search_excl to 'public' as 'informix';

grant select on word_search_excl to 'public' as 'informix';

grant delete on word_search_excl to 'public' as 'informix';

grant update on command_group_lu to 'public' as 'informix';

grant select on command_group_lu to 'public' as 'informix';

grant insert on command_group_lu to 'public' as 'informix';

grant index on command_group_lu to 'public' as 'informix';

grant delete on command_group_lu to 'public' as 'informix';

grant index on command to 'public' as 'informix';

grant update on command to 'public' as 'informix';

grant delete on command to 'public' as 'informix';

grant insert on command to 'public' as 'informix';

grant select on command to 'public' as 'informix';

grant delete on query to 'public' as 'informix';

grant select on query to 'public' as 'informix';

grant index on query to 'public' as 'informix';

grant insert on query to 'public' as 'informix';

grant update on query to 'public' as 'informix';

grant select on input_lu to 'public' as 'informix';

grant index on input_lu to 'public' as 'informix';

grant delete on input_lu to 'public' as 'informix';

grant insert on input_lu to 'public' as 'informix';

grant update on input_lu to 'public' as 'informix';

grant select on query_input_xref to 'public' as 'informix';

grant index on query_input_xref to 'public' as 'informix';

grant update on query_input_xref to 'public' as 'informix';

grant insert on query_input_xref to 'public' as 'informix';

grant delete on query_input_xref to 'public' as 'informix';

grant delete on command_query_xref to 'public' as 'informix';

grant select on command_query_xref to 'public' as 'informix';

grant index on command_query_xref to 'public' as 'informix';

grant update on command_query_xref to 'public' as 'informix';

grant insert on command_query_xref to 'public' as 'informix';

grant select on message to 'public' as 'informix';

grant update on message to 'public' as 'informix';

grant index on message to 'public' as 'informix';

grant delete on message to 'public' as 'informix';

grant insert on message to 'public' as 'informix';

grant select on tc_user_xref to 'public' as 'informix';

grant delete on tc_user_xref to 'public' as 'informix';

grant index on tc_user_xref to 'public' as 'informix';

grant insert on tc_user_xref to 'public' as 'informix';

grant update on tc_user_xref to 'public' as 'informix';

grant delete on message_to_handle to 'public' as 'informix';

grant select on message_to_handle to 'public' as 'informix';

grant index on message_to_handle to 'public' as 'informix';

grant insert on message_to_handle to 'public' as 'informix';

grant update on message_to_handle to 'public' as 'informix';

grant select on component_inquiry to 'public' as 'informix';

grant update on component_inquiry to 'public' as 'informix';

grant index on component_inquiry to 'public' as 'informix';

grant delete on component_inquiry to 'public' as 'informix';

grant insert on component_inquiry to 'public' as 'informix';

grant index on comp_version_dates to 'public' as 'informix';

grant update on comp_version_dates to 'public' as 'informix';

grant select on comp_version_dates to 'public' as 'informix';

grant insert on comp_version_dates to 'public' as 'informix';

grant delete on comp_version_dates to 'public' as 'informix';

grant insert on comp_level to 'public' as 'informix';

grant update on comp_level to 'public' as 'informix';

grant delete on comp_level to 'public' as 'informix';

grant index on comp_level to 'public' as 'informix';

grant select on comp_level to 'public' as 'informix';

grant delete on user_master_tmp to 'public' as 'informix';

grant index on user_master_tmp to 'public' as 'informix';

grant select on user_master_tmp to 'public' as 'informix';

grant update on user_master_tmp to 'public' as 'informix';

grant insert on user_master_tmp to 'public' as 'informix';

grant insert on user_master to 'public' as 'informix';

grant update on user_master to 'public' as 'informix';

grant delete on user_master to 'public' as 'informix';

grant index on user_master to 'public' as 'informix';

grant select on user_master to 'public' as 'informix';

grant delete on comp_version_dates_history to 'public' as 'informix';

grant select on comp_version_dates_history to 'public' as 'informix';

grant insert on comp_version_dates_history to 'public' as 'informix';

grant update on comp_version_dates_history to 'public' as 'informix';

grant index on comp_version_dates_history to 'public' as 'informix';

grant insert on project_category_lu to 'public' as 'informix';

grant select on project_category_lu to 'public' as 'informix';

grant update on project_category_lu to 'public' as 'informix';

grant delete on project_category_lu to 'public' as 'informix';

grant update on scorecard_type_lu to 'public' as 'informix';

grant delete on scorecard_type_lu to 'public' as 'informix';

grant select on scorecard_type_lu to 'public' as 'informix';

grant insert on scorecard_type_lu to 'public' as 'informix';

grant update on scorecard_status_lu to 'public' as 'informix';

grant delete on scorecard_status_lu to 'public' as 'informix';

grant select on scorecard_status_lu to 'public' as 'informix';

grant insert on scorecard_status_lu to 'public' as 'informix';

grant insert on scorecard to 'public' as 'informix';

grant select on scorecard to 'public' as 'informix';

grant delete on scorecard to 'public' as 'informix';

grant update on scorecard to 'public' as 'informix';

grant select on scorecard_group to 'public' as 'informix';

grant insert on scorecard_group to 'public' as 'informix';

grant delete on scorecard_group to 'public' as 'informix';

grant update on scorecard_group to 'public' as 'informix';

grant insert on status_assignment to 'public' as 'informix';

grant index on status_assignment to 'public' as 'informix';

grant select on status_assignment to 'public' as 'informix';

grant update on status_assignment to 'public' as 'informix';

grant delete on status_assignment to 'public' as 'informix';

grant update on scorecard_question_type_lu to 'public' as 'informix';

grant delete on scorecard_question_type_lu to 'public' as 'informix';

grant select on scorecard_question_type_lu to 'public' as 'informix';

grant insert on scorecard_question_type_lu to 'public' as 'informix';

grant update on review_resp to 'public' as 'informix';

grant index on review_resp to 'public' as 'informix';

grant insert on review_resp to 'public' as 'informix';

grant select on review_resp to 'public' as 'informix';

grant delete on review_resp to 'public' as 'informix';

grant update on scorecard_question to 'public' as 'informix';

grant insert on scorecard_question to 'public' as 'informix';

grant select on scorecard_question to 'public' as 'informix';

grant delete on scorecard_question to 'public' as 'informix';

grant select on project_status_lu to 'public' as 'informix';

grant update on project_status_lu to 'public' as 'informix';

grant insert on project_status_lu to 'public' as 'informix';

grant delete on project_status_lu to 'public' as 'informix';

grant insert on project to 'public' as 'informix';

grant update on project to 'public' as 'informix';

grant delete on project to 'public' as 'informix';

grant select on project to 'public' as 'informix';

grant insert on project_info_type_lu to 'public' as 'informix';

grant update on project_info_type_lu to 'public' as 'informix';

grant delete on project_info_type_lu to 'public' as 'informix';

grant select on project_info_type_lu to 'public' as 'informix';

grant insert on comp_catalog to 'public' as 'informix';

grant delete on comp_catalog to 'public' as 'informix';

grant index on comp_catalog to 'public' as 'informix';

grant select on comp_catalog to 'public' as 'informix';

grant update on comp_catalog to 'public' as 'informix';

grant select on phase_status_lu to 'public' as 'informix';

grant delete on phase_status_lu to 'public' as 'informix';

grant update on phase_status_lu to 'public' as 'informix';

grant insert on phase_status_lu to 'public' as 'informix';

grant select on phase_type_lu to 'public' as 'informix';

grant insert on phase_type_lu to 'public' as 'informix';

grant update on phase_type_lu to 'public' as 'informix';

grant delete on phase_type_lu to 'public' as 'informix';

grant update on project_phase to 'public' as 'informix';

grant delete on project_phase to 'public' as 'informix';

grant select on project_phase to 'public' as 'informix';

grant insert on project_phase to 'public' as 'informix';

grant update on phase_dependency to 'public' as 'informix';

grant select on phase_dependency to 'public' as 'informix';

grant delete on phase_dependency to 'public' as 'informix';

grant insert on phase_dependency to 'public' as 'informix';

grant update on phase_criteria_type_lu to 'public' as 'informix';

grant delete on phase_criteria_type_lu to 'public' as 'informix';

grant insert on phase_criteria_type_lu to 'public' as 'informix';

grant select on phase_criteria_type_lu to 'public' as 'informix';

grant select on phase_criteria to 'public' as 'informix';

grant insert on phase_criteria to 'public' as 'informix';

grant update on phase_criteria to 'public' as 'informix';

grant delete on phase_criteria to 'public' as 'informix';

grant select on resource_role_lu to 'public' as 'informix';

grant delete on resource_role_lu to 'public' as 'informix';

grant insert on resource_role_lu to 'public' as 'informix';

grant update on resource_role_lu to 'public' as 'informix';

grant update on resource to 'public' as 'informix';

grant select on resource to 'public' as 'informix';

grant insert on resource to 'public' as 'informix';

grant delete on resource to 'public' as 'informix';

grant insert on resource_info_type_lu to 'public' as 'informix';

grant select on resource_info_type_lu to 'public' as 'informix';

grant update on resource_info_type_lu to 'public' as 'informix';

grant delete on resource_info_type_lu to 'public' as 'informix';

grant insert on project_info to 'public' as 'informix';

grant update on project_info to 'public' as 'informix';

grant select on project_info to 'public' as 'informix';

grant delete on project_info to 'public' as 'informix';

grant delete on upload_type_lu to 'public' as 'informix';

grant select on upload_type_lu to 'public' as 'informix';

grant insert on upload_type_lu to 'public' as 'informix';

grant update on upload_type_lu to 'public' as 'informix';

grant update on upload_status_lu to 'public' as 'informix';

grant insert on upload_status_lu to 'public' as 'informix';

grant select on upload_status_lu to 'public' as 'informix';

grant delete on upload_status_lu to 'public' as 'informix';

grant update on upload to 'public' as 'informix';

grant insert on upload to 'public' as 'informix';

grant delete on upload to 'public' as 'informix';

grant select on upload to 'public' as 'informix';

grant select on sample_inquiry to 'public' as 'informix';

grant update on sample_inquiry to 'public' as 'informix';

grant delete on sample_inquiry to 'public' as 'informix';

grant insert on sample_inquiry to 'public' as 'informix';

grant index on sample_inquiry to 'public' as 'informix';

grant update on comp_level_phase to 'public' as 'informix';

grant select on comp_level_phase to 'public' as 'informix';

grant index on comp_level_phase to 'public' as 'informix';

grant delete on comp_level_phase to 'public' as 'informix';

grant insert on comp_level_phase to 'public' as 'informix';

grant insert on user_component_score to 'public' as 'informix';

grant delete on user_component_score to 'public' as 'informix';

grant select on user_component_score to 'public' as 'informix';

grant update on user_component_score to 'public' as 'informix';

grant index on user_component_score to 'public' as 'informix';

grant delete on user_rating to 'public' as 'informix';

grant update on user_rating to 'public' as 'informix';

grant select on user_rating to 'public' as 'informix';

grant insert on user_rating to 'public' as 'informix';

grant index on user_rating to 'public' as 'informix';

grant delete on tcs_ratings_history to 'public' as 'informix';

grant insert on tcs_ratings_history to 'public' as 'informix';

grant index on tcs_ratings_history to 'public' as 'informix';

grant update on tcs_ratings_history to 'public' as 'informix';

grant select on tcs_ratings_history to 'public' as 'informix';

grant select on rboard_status_lu to 'public' as 'informix';

grant insert on rboard_status_lu to 'public' as 'informix';

grant delete on rboard_status_lu to 'public' as 'informix';

grant update on rboard_status_lu to 'public' as 'informix';

grant index on rboard_status_lu to 'public' as 'informix';

grant index on rboard_user_audit to 'public' as 'informix';

grant delete on rboard_user_audit to 'public' as 'informix';

grant update on rboard_user_audit to 'public' as 'informix';

grant insert on rboard_user_audit to 'public' as 'informix';

grant select on rboard_user_audit to 'public' as 'informix';

grant index on rboard_notes to 'public' as 'informix';

grant insert on rboard_notes to 'public' as 'informix';

grant select on rboard_notes to 'public' as 'informix';

grant delete on rboard_notes to 'public' as 'informix';

grant update on rboard_notes to 'public' as 'informix';

grant delete on rboard_contact_sched to 'public' as 'informix';

grant insert on rboard_contact_sched to 'public' as 'informix';

grant index on rboard_contact_sched to 'public' as 'informix';

grant select on rboard_contact_sched to 'public' as 'informix';

grant update on rboard_contact_sched to 'public' as 'informix';

grant update on rboard_application to 'public' as 'informix';

grant insert on rboard_application to 'public' as 'informix';

grant select on rboard_application to 'public' as 'informix';

grant index on rboard_application to 'public' as 'informix';

grant delete on rboard_application to 'public' as 'informix';

grant select on submission_status_lu to 'public' as 'informix';

grant delete on submission_status_lu to 'public' as 'informix';

grant update on submission_status_lu to 'public' as 'informix';

grant insert on submission_status_lu to 'public' as 'informix';

grant update on submission to 'public' as 'informix';

grant insert on submission to 'public' as 'informix';

grant delete on submission to 'public' as 'informix';

grant select on submission to 'public' as 'informix';

grant update on rboard_payment to 'public' as 'informix';

grant index on rboard_payment to 'public' as 'informix';

grant delete on rboard_payment to 'public' as 'informix';

grant insert on rboard_payment to 'public' as 'informix';

grant select on rboard_payment to 'public' as 'informix';

grant delete on resource_submission to 'public' as 'informix';

grant update on resource_submission to 'public' as 'informix';

grant insert on resource_submission to 'public' as 'informix';

grant select on resource_submission to 'public' as 'informix';

grant update on comment_type_lu to 'public' as 'informix';

grant select on comment_type_lu to 'public' as 'informix';

grant insert on comment_type_lu to 'public' as 'informix';

grant delete on comment_type_lu to 'public' as 'informix';

grant select on review to 'public' as 'informix';

grant update on review to 'public' as 'informix';

grant delete on review to 'public' as 'informix';

grant insert on review to 'public' as 'informix';

grant select on review_item to 'public' as 'informix';

grant update on review_item to 'public' as 'informix';

grant insert on review_item to 'public' as 'informix';

grant delete on review_item to 'public' as 'informix';

grant delete on review_comment to 'public' as 'informix';

grant select on review_comment to 'public' as 'informix';

grant insert on review_comment to 'public' as 'informix';

grant update on review_comment to 'public' as 'informix';

grant index on contest_type_lu to 'public' as 'informix';

grant insert on contest_type_lu to 'public' as 'informix';

grant delete on contest_type_lu to 'public' as 'informix';

grant update on contest_type_lu to 'public' as 'informix';

grant select on contest_type_lu to 'public' as 'informix';

grant index on contest to 'public' as 'informix';

grant update on contest to 'public' as 'informix';

grant select on contest to 'public' as 'informix';

grant delete on contest to 'public' as 'informix';

grant insert on contest to 'public' as 'informix';

grant insert on contest_project_xref to 'public' as 'informix';

grant update on contest_project_xref to 'public' as 'informix';

grant select on contest_project_xref to 'public' as 'informix';

grant index on contest_project_xref to 'public' as 'informix';

grant delete on contest_project_xref to 'public' as 'informix';

grant insert on prize_type_lu to 'public' as 'informix';

grant delete on prize_type_lu to 'public' as 'informix';

grant update on prize_type_lu to 'public' as 'informix';

grant index on prize_type_lu to 'public' as 'informix';

grant select on prize_type_lu to 'public' as 'informix';

grant insert on offer to 'public' as 'informix';

grant index on offer to 'public' as 'informix';

grant update on offer to 'public' as 'informix';

grant select on offer to 'public' as 'informix';

grant delete on offer to 'public' as 'informix';

grant index on user_contest_prize to 'public' as 'informix';

grant update on user_contest_prize to 'public' as 'informix';

grant delete on user_contest_prize to 'public' as 'informix';

grant insert on user_contest_prize to 'public' as 'informix';

grant select on user_contest_prize to 'public' as 'informix';

grant delete on project_result to 'public' as 'informix';

grant select on project_result to 'public' as 'informix';

grant insert on project_result to 'public' as 'informix';

grant index on project_result to 'public' as 'informix';

grant update on project_result to 'public' as 'informix';

grant insert on user_reliability to 'public' as 'informix';

grant update on user_reliability to 'public' as 'informix';

grant select on user_reliability to 'public' as 'informix';

grant index on user_reliability to 'public' as 'informix';

grant delete on user_reliability to 'public' as 'informix';

grant update on royalty to 'public' as 'informix';

grant insert on royalty to 'public' as 'informix';

grant index on royalty to 'public' as 'informix';

grant delete on royalty to 'public' as 'informix';

grant select on royalty to 'public' as 'informix';

grant select on project_pablo to 'public' as 'informix';

grant delete on project_pablo to 'public' as 'informix';

grant update on project_pablo to 'public' as 'informix';

grant index on project_pablo to 'public' as 'informix';

grant insert on project_pablo to 'public' as 'informix';

grant select on user_event_xref to 'public' as 'informix';

grant index on user_event_xref to 'public' as 'informix';

grant insert on user_event_xref to 'public' as 'informix';

grant update on user_event_xref to 'public' as 'informix';

grant delete on user_event_xref to 'public' as 'informix';

grant insert on rboard_payment_stage to 'public' as 'informix';

grant index on rboard_payment_stage to 'public' as 'informix';

grant delete on rboard_payment_stage to 'public' as 'informix';

grant select on rboard_payment_stage to 'public' as 'informix';

grant update on rboard_payment_stage to 'public' as 'informix';

grant insert on review_item_comment to 'public' as 'informix';

grant update on review_item_comment to 'public' as 'informix';

grant delete on review_item_comment to 'public' as 'informix';

grant select on review_item_comment to 'public' as 'informix';

grant update on deliverable_lu to 'public' as 'informix';

grant insert on deliverable_lu to 'public' as 'informix';

grant delete on deliverable_lu to 'public' as 'informix';

grant select on deliverable_lu to 'public' as 'informix';

grant update on notification_mail_type_lu to 'public' as 'informix';

grant insert on notification_mail_type_lu to 'public' as 'informix';

grant index on notification_mail_type_lu to 'public' as 'informix';

grant delete on notification_mail_type_lu to 'public' as 'informix';

grant select on notification_mail_type_lu to 'public' as 'informix';

grant select on notification_event to 'public' as 'informix';

grant delete on notification_event to 'public' as 'informix';

grant update on notification_event to 'public' as 'informix';

grant insert on notification_event to 'public' as 'informix';

grant index on notification_event to 'public' as 'informix';

grant delete on user_notification_event_xref to 'public' as 'informix';

grant update on user_notification_event_xref to 'public' as 'informix';

grant insert on user_notification_event_xref to 'public' as 'informix';

grant select on user_notification_event_xref to 'public' as 'informix';

grant index on user_notification_event_xref to 'public' as 'informix';

grant index on dual to 'public' as 'informix';

grant delete on dual to 'public' as 'informix';

grant insert on dual to 'public' as 'informix';

grant select on dual to 'public' as 'informix';

grant update on dual to 'public' as 'informix';

grant insert on framework to 'public' as 'informix';

grant update on framework to 'public' as 'informix';

grant delete on framework to 'public' as 'informix';

grant index on framework to 'public' as 'informix';

grant select on framework to 'public' as 'informix';

grant delete on project_audit to 'public' as 'informix';

grant insert on project_audit to 'public' as 'informix';

grant update on project_audit to 'public' as 'informix';

grant select on project_audit to 'public' as 'informix';

grant insert on notification_type_lu to 'public' as 'informix';

grant update on notification_type_lu to 'public' as 'informix';

grant delete on notification_type_lu to 'public' as 'informix';

grant select on notification_type_lu to 'public' as 'informix';

grant insert on notification to 'public' as 'informix';

grant delete on notification to 'public' as 'informix';

grant update on notification to 'public' as 'informix';

grant select on notification to 'public' as 'informix';

grant select on screening_status_lu to 'public' as 'informix';

grant insert on screening_status_lu to 'public' as 'informix';

grant update on screening_status_lu to 'public' as 'informix';

grant delete on screening_status_lu to 'public' as 'informix';

grant index on comp_reg_answer to 'public' as 'informix';

grant insert on comp_reg_answer to 'public' as 'informix';

grant update on comp_reg_answer to 'public' as 'informix';

grant select on comp_reg_answer to 'public' as 'informix';

grant delete on comp_reg_answer to 'public' as 'informix';

grant insert on comp_reg_response to 'public' as 'informix';

grant select on comp_reg_response to 'public' as 'informix';

grant delete on comp_reg_response to 'public' as 'informix';

grant update on comp_reg_response to 'public' as 'informix';

grant index on comp_reg_response to 'public' as 'informix';

grant select on question_style to 'public' as 'informix';

grant update on question_style to 'public' as 'informix';

grant index on question_style to 'public' as 'informix';

grant delete on question_style to 'public' as 'informix';

grant insert on question_style to 'public' as 'informix';

grant select on gp_user_reliability to 'public' as 'informix';

grant index on gp_user_reliability to 'public' as 'informix';

grant delete on gp_user_reliability to 'public' as 'informix';

grant update on gp_user_reliability to 'public' as 'informix';

grant insert on gp_user_reliability to 'public' as 'informix';

grant update on comp_versions to 'public' as 'informix';

grant insert on comp_versions to 'public' as 'informix';

grant select on comp_versions to 'public' as 'informix';

grant delete on comp_versions to 'public' as 'informix';

grant index on comp_versions to 'public' as 'informix';

grant select on widget to 'public' as 'informix';

grant insert on widget to 'public' as 'informix';

grant index on widget to 'public' as 'informix';

grant delete on widget to 'public' as 'informix';

grant update on widget to 'public' as 'informix';

grant select on screening_task to 'public' as 'informix';

grant insert on screening_task to 'public' as 'informix';

grant update on screening_task to 'public' as 'informix';

grant delete on screening_task to 'public' as 'informix';

grant insert on comp_reg_question to 'public' as 'informix';

grant select on comp_reg_question to 'public' as 'informix';

grant index on comp_reg_question to 'public' as 'informix';

grant update on comp_reg_question to 'public' as 'informix';

grant delete on comp_reg_question to 'public' as 'informix';

grant index on project_wager to 'public' as 'informix';

grant insert on project_wager to 'public' as 'informix';

grant select on project_wager to 'public' as 'informix';

grant update on project_wager to 'public' as 'informix';

grant delete on project_wager to 'public' as 'informix';

grant update on widget_version to 'public' as 'informix';

grant select on widget_version to 'public' as 'informix';

grant index on widget_version to 'public' as 'informix';

grant delete on widget_version to 'public' as 'informix';

grant insert on widget_version to 'public' as 'informix';

grant insert on catalog to 'public' as 'informix';

grant select on catalog to 'public' as 'informix';

grant delete on catalog to 'public' as 'informix';

grant index on catalog to 'public' as 'informix';

grant update on catalog to 'public' as 'informix';

grant select on rboard_user to 'public' as 'informix';

grant delete on rboard_user to 'public' as 'informix';

grant insert on rboard_user to 'public' as 'informix';

grant index on rboard_user to 'public' as 'informix';

grant update on rboard_user to 'public' as 'informix';

grant update on category_catalog to 'public' as 'informix';

grant index on category_catalog to 'public' as 'informix';

grant insert on category_catalog to 'public' as 'informix';

grant select on category_catalog to 'public' as 'informix';

grant delete on category_catalog to 'public' as 'informix';

grant insert on project_info_before_moving_price to 'public' as 'informix';

grant delete on project_info_before_moving_price to 'public' as 'informix';

grant select on project_info_before_moving_price to 'public' as 'informix';

grant update on project_info_before_moving_price to 'public' as 'informix';

grant index on project_info_before_moving_price to 'public' as 'informix';

grant index on comp_version_dates_before_moving_price to 'public' as 'informix';

grant update on comp_version_dates_before_moving_price to 'public' as 'informix';

grant select on comp_version_dates_before_moving_price to 'public' as 'informix';

grant delete on comp_version_dates_before_moving_price to 'public' as 'informix';

grant insert on comp_version_dates_before_moving_price to 'public' as 'informix';

grant insert on dr_points to 'public' as 'informix';

grant delete on dr_points to 'public' as 'informix';

grant update on dr_points to 'public' as 'informix';

grant index on dr_points to 'public' as 'informix';

grant select on dr_points to 'public' as 'informix';

grant select on specification_type to 'public' as 'informix';

grant delete on specification_type to 'public' as 'informix';

grant index on specification_type to 'public' as 'informix';

grant insert on specification_type to 'public' as 'informix';

grant update on specification_type to 'public' as 'informix';

grant delete on specification to 'public' as 'informix';

grant index on specification to 'public' as 'informix';

grant select on specification to 'public' as 'informix';

grant update on specification to 'public' as 'informix';

grant insert on specification to 'public' as 'informix';

grant select on response_severity_lu to 'public' as 'informix';

grant update on response_severity_lu to 'public' as 'informix';

grant insert on response_severity_lu to 'public' as 'informix';

grant delete on response_severity_lu to 'public' as 'informix';

grant select on gp_user_contest_prize to 'public' as 'informix';

grant update on gp_user_contest_prize to 'public' as 'informix';

grant delete on gp_user_contest_prize to 'public' as 'informix';

grant index on gp_user_contest_prize to 'public' as 'informix';

grant insert on gp_user_contest_prize to 'public' as 'informix';

grant delete on user_rating_audit to 'public' as 'informix';

grant update on user_rating_audit to 'public' as 'informix';

grant select on user_rating_audit to 'public' as 'informix';

grant index on user_rating_audit to 'public' as 'informix';

grant insert on user_rating_audit to 'public' as 'informix';

grant update on command_execution to 'public' as 'informix';

grant index on command_execution to 'public' as 'informix';

grant delete on command_execution to 'public' as 'informix';

grant insert on command_execution to 'public' as 'informix';

grant select on command_execution to 'public' as 'informix';

grant index on user_reliability_audit to 'public' as 'informix';

grant select on user_reliability_audit to 'public' as 'informix';

grant update on user_reliability_audit to 'public' as 'informix';

grant delete on user_reliability_audit to 'public' as 'informix';

grant insert on user_reliability_audit to 'public' as 'informix';

grant select on dr_points_operation_lu to 'public' as 'informix';

grant update on dr_points_operation_lu to 'public' as 'informix';

grant delete on dr_points_operation_lu to 'public' as 'informix';

grant index on dr_points_operation_lu to 'public' as 'informix';

grant insert on dr_points_operation_lu to 'public' as 'informix';

grant update on dr_points_reference_type_lu to 'public' as 'informix';

grant insert on dr_points_reference_type_lu to 'public' as 'informix';

grant delete on dr_points_reference_type_lu to 'public' as 'informix';

grant select on dr_points_reference_type_lu to 'public' as 'informix';

grant index on dr_points_reference_type_lu to 'public' as 'informix';

grant select on screening_response_lu to 'public' as 'informix';

grant insert on screening_response_lu to 'public' as 'informix';

grant update on screening_response_lu to 'public' as 'informix';

grant delete on screening_response_lu to 'public' as 'informix';

grant select on screening_result to 'public' as 'informix';

grant insert on screening_result to 'public' as 'informix';

grant delete on screening_result to 'public' as 'informix';

grant update on screening_result to 'public' as 'informix';

grant delete on default_scorecard to 'public' as 'informix';

grant select on default_scorecard to 'public' as 'informix';

grant insert on default_scorecard to 'public' as 'informix';

grant update on default_scorecard to 'public' as 'informix';

grant insert on user_role to 'public' as 'informix';

grant index on user_role to 'public' as 'informix';

grant delete on user_role to 'public' as 'informix';

grant select on user_role to 'public' as 'informix';

grant update on user_role to 'public' as 'informix';

grant insert on dr_points_type_lu to 'public' as 'informix';

grant delete on dr_points_type_lu to 'public' as 'informix';

grant update on dr_points_type_lu to 'public' as 'informix';

grant select on dr_points_type_lu to 'public' as 'informix';

grant index on dr_points_type_lu to 'public' as 'informix';

grant insert on comp_jive_category_xref to 'public' as 'informix';

grant select on comp_jive_category_xref to 'public' as 'informix';

grant index on comp_jive_category_xref to 'public' as 'informix';

grant delete on comp_jive_category_xref to 'public' as 'informix';

grant update on comp_jive_category_xref to 'public' as 'informix';

grant delete on comp_forum_xref_bak to 'public' as 'informix';

grant insert on comp_forum_xref_bak to 'public' as 'informix';

grant update on comp_forum_xref_bak to 'public' as 'informix';

grant index on comp_forum_xref_bak to 'public' as 'informix';

grant select on comp_forum_xref_bak to 'public' as 'informix';

grant select on dr_points_status_lu to 'public' as 'informix';

grant insert on dr_points_status_lu to 'public' as 'informix';

grant delete on dr_points_status_lu to 'public' as 'informix';

grant update on dr_points_status_lu to 'public' as 'informix';

grant index on dr_points_status_lu to 'public' as 'informix';

grant delete on resource_info to 'public' as 'informix';

grant select on resource_info to 'public' as 'informix';

grant update on resource_info to 'public' as 'informix';

grant insert on resource_info to 'public' as 'informix';

grant update on team_header to 'public' as 'informix';

grant delete on team_header to 'public' as 'informix';

grant insert on team_header to 'public' as 'informix';

grant index on team_header to 'public' as 'informix';

grant select on team_header to 'public' as 'informix';

grant index on team_properties to 'public' as 'informix';

grant select on team_properties to 'public' as 'informix';

grant delete on team_properties to 'public' as 'informix';

grant insert on team_properties to 'public' as 'informix';

grant update on team_properties to 'public' as 'informix';

grant update on team_position to 'public' as 'informix';

grant delete on team_position to 'public' as 'informix';

grant index on team_position to 'public' as 'informix';

grant select on team_position to 'public' as 'informix';

grant insert on team_position to 'public' as 'informix';

grant insert on team_position_properties to 'public' as 'informix';

grant update on team_position_properties to 'public' as 'informix';

grant index on team_position_properties to 'public' as 'informix';

grant select on team_position_properties to 'public' as 'informix';

grant delete on team_position_properties to 'public' as 'informix';

grant update on team_manager_audit to 'public' as 'informix';

grant select on team_manager_audit to 'public' as 'informix';

grant index on team_manager_audit to 'public' as 'informix';

grant delete on team_manager_audit to 'public' as 'informix';

grant insert on team_manager_audit to 'public' as 'informix';

grant delete on team_manager_audit_details to 'public' as 'informix';

grant index on team_manager_audit_details to 'public' as 'informix';

grant insert on team_manager_audit_details to 'public' as 'informix';

grant select on team_manager_audit_details to 'public' as 'informix';

grant update on team_manager_audit_details to 'public' as 'informix';

grant select on team_manager_audit_custom_details to 'public' as 'informix';

grant index on team_manager_audit_custom_details to 'public' as 'informix';

grant update on team_manager_audit_custom_details to 'public' as 'informix';

grant insert on team_manager_audit_custom_details to 'public' as 'informix';

grant delete on team_manager_audit_custom_details to 'public' as 'informix';

grant insert on facade_audit to 'public' as 'informix';

grant update on facade_audit to 'public' as 'informix';

grant index on facade_audit to 'public' as 'informix';

grant delete on facade_audit to 'public' as 'informix';

grant select on facade_audit to 'public' as 'informix';

grant index on track_contest_result_calculator_lu to 'public' as 'informix';

grant update on track_contest_result_calculator_lu to 'public' as 'informix';

grant select on track_contest_result_calculator_lu to 'public' as 'informix';

grant insert on track_contest_result_calculator_lu to 'public' as 'informix';

grant delete on track_contest_result_calculator_lu to 'public' as 'informix';

grant delete on track_contest_type_lu to 'public' as 'informix';

grant index on track_contest_type_lu to 'public' as 'informix';

grant select on track_contest_type_lu to 'public' as 'informix';

grant insert on track_contest_type_lu to 'public' as 'informix';

grant update on track_contest_type_lu to 'public' as 'informix';

grant insert on track_project_category_xref to 'public' as 'informix';

grant delete on track_project_category_xref to 'public' as 'informix';

grant update on track_project_category_xref to 'public' as 'informix';

grant select on track_project_category_xref to 'public' as 'informix';

grant index on track_project_category_xref to 'public' as 'informix';

grant delete on track_type_lu to 'public' as 'informix';

grant insert on track_type_lu to 'public' as 'informix';

grant select on track_type_lu to 'public' as 'informix';

grant index on track_type_lu to 'public' as 'informix';

grant update on track_type_lu to 'public' as 'informix';

grant index on track_status_lu to 'public' as 'informix';

grant delete on track_status_lu to 'public' as 'informix';

grant select on track_status_lu to 'public' as 'informix';

grant insert on track_status_lu to 'public' as 'informix';

grant update on track_status_lu to 'public' as 'informix';

grant insert on categories to 'public' as 'informix';

grant delete on categories to 'public' as 'informix';

grant update on categories to 'public' as 'informix';

grant select on categories to 'public' as 'informix';

grant index on categories to 'public' as 'informix';

grant insert on comp_user to 'public' as 'informix';

grant index on comp_user to 'public' as 'informix';

grant select on comp_user to 'public' as 'informix';

grant delete on comp_user to 'public' as 'informix';

grant update on comp_user to 'public' as 'informix';

grant delete on user_client to 'public' as 'informix';

grant select on user_client to 'public' as 'informix';

grant update on user_client to 'public' as 'informix';

grant index on user_client to 'public' as 'informix';

grant insert on user_client to 'public' as 'informix';

grant delete on comp_link to 'public' as 'informix';

grant select on comp_link to 'public' as 'informix';

grant update on comp_link to 'public' as 'informix';

grant insert on comp_link to 'public' as 'informix';

grant index on comp_link to 'public' as 'informix';

grant select on points_calculator_lu to 'public' as 'informix';

grant insert on points_calculator_lu to 'public' as 'informix';

grant update on points_calculator_lu to 'public' as 'informix';

grant index on points_calculator_lu to 'public' as 'informix';

grant delete on points_calculator_lu to 'public' as 'informix';

grant insert on track to 'public' as 'informix';

grant delete on track to 'public' as 'informix';

grant update on track to 'public' as 'informix';

grant index on track to 'public' as 'informix';

grant select on track to 'public' as 'informix';

grant delete on track_contest to 'public' as 'informix';

grant index on track_contest to 'public' as 'informix';

grant select on track_contest to 'public' as 'informix';

grant update on track_contest to 'public' as 'informix';

grant insert on track_contest to 'public' as 'informix';

grant select on v_latest_version to 'public' as 'informix';

grant select on link_type_lu to 'public' as 'informix';

grant insert on link_type_lu to 'public' as 'informix';

grant update on link_type_lu to 'public' as 'informix';

grant delete on link_type_lu to 'public' as 'informix';

grant select on linked_project_xref to 'public' as 'informix';

grant insert on linked_project_xref to 'public' as 'informix';

grant update on linked_project_xref to 'public' as 'informix';

grant delete on linked_project_xref to 'public' as 'informix';

grant select on project_spec to 'public' as 'informix';

grant insert on project_spec to 'public' as 'informix';

grant update on project_spec to 'public' as 'informix';

grant delete on project_spec to 'public' as 'informix';


grant select on sale_status_lu to 'public' as 'informix';

grant insert on sale_status_lu to 'public' as 'informix';

grant update on sale_status_lu to 'public' as 'informix';

grant delete on sale_status_lu to 'public' as 'informix';


grant select on sale_type_lu to 'public' as 'informix';

grant insert on sale_type_lu to 'public' as 'informix';

grant update on sale_type_lu to 'public' as 'informix';

grant delete on sale_type_lu to 'public' as 'informix';


grant select on contest_sale to 'public' as 'informix';

grant insert on contest_sale to 'public' as 'informix';

grant update on contest_sale to 'public' as 'informix';

grant delete on contest_sale to 'public' as 'informix';

grant select,insert,update,delete on 'informix'.software_competition_pipeline_info  to public as informix;
grant select,insert,update,delete on 'informix'.software_competition_change_history  to public as informix;

grant select on "informix".audit_action_type_lu to "public" as "informix";
grant update on "informix".audit_action_type_lu to "public" as "informix";
grant insert on "informix".audit_action_type_lu to "public" as "informix";
grant delete on "informix".audit_action_type_lu to "public" as "informix";

grant select on "informix".project_user_audit to "public" as "informix";
grant update on "informix".project_user_audit to "public" as "informix";
grant insert on "informix".project_user_audit to "public" as "informix";
grant delete on "informix".project_user_audit to "public" as "informix";

grant select on "informix".project_info_audit to "public" as "informix";
grant update on "informix".project_info_audit to "public" as "informix";
grant insert on "informix".project_info_audit to "public" as "informix";
grant delete on "informix".project_info_audit to "public" as "informix";

grant select on "informix".project_phase_audit to "public" as "informix";
grant update on "informix".project_phase_audit to "public" as "informix";
grant insert on "informix".project_phase_audit to "public" as "informix";
grant delete on "informix".project_phase_audit to "public" as "informix";

grant select on "informix".third_party_library to "public" as "informix";
grant update on "informix".third_party_library to "public" as "informix";
grant insert on "informix".third_party_library to "public" as "informix";
grant delete on "informix".third_party_library to "public" as "informix";

grant select on "informix".PROJECT_USER_AUDIT_SEQ to "public" as "informix";

grant select,insert,update,delete on "informix".client_billing_config to public as informix;
grant select,insert,update,delete on "informix".client_billing_config_type_lu to public as informix;


----- since vm does not have jive db, in dev/prod we create synonyms for jive tables, here we just create dummy tables

create table 'informix'.jivecategory (

categoryid INTEGER not null,
name varchar(255) not null,
descripition varchar(255),
creationdate decimal not null,
modificationdate decimal not null,
lft  INTEGER not null,
rgt INTEGER not null
)

lock mode page;

revoke all on 'informix'.jivecategory from 'public';


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
grant select on 'informix'.jivecategory to 'public' as 'informix';
grant insert on 'informix'.jivecategory to 'public' as 'informix';
grant update on 'informix'.jivecategory to 'public' as 'informix';
grant delete on 'informix'.jivecategory to 'public' as 'informix';

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