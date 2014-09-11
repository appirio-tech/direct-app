database common_oltp;

create unique index 'informix'.security_user_i2 on 'informix'.security_user
    (
    user_id
    );

alter table 'informix'.security_user add constraint primary key 
    (login_id)
    constraint pk_security_user;

create unique cluster index 'informix'.user_role_xref_i2 on 'informix'.user_role_xref
    (
    login_id, 
    role_id
    );

alter table 'informix'.user_role_xref add constraint primary key 
    (user_role_id)
    constraint pk_user_role_xref;

create unique cluster index 'informix'.user_grp_xref_i2 on 'informix'.user_group_xref
    (
    login_id, 
    group_id
    );

alter table 'informix'.user_group_xref add constraint primary key 
    (user_group_id)
    constraint pk_user_group_xref;

alter table 'informix'.security_groups add constraint primary key 
    (group_id)
    constraint pk_security_groups;

create unique cluster index 'informix'.group_role_xref_i2 on 'informix'.group_role_xref
    (
    group_id, 
    role_id
    );

alter table 'informix'.group_role_xref add constraint primary key 
    (group_role_id)
    constraint pk_group_role_xref;

alter table 'informix'.security_roles add constraint primary key 
    (role_id)
    constraint pk_security_roles;

alter table 'informix'.security_perms add constraint primary key 
    (role_id, permission)
    constraint pk_security_perms;

alter table 'informix'.address add constraint primary key 
    (address_id)
    constraint u107_20;

alter table 'informix'.address_type_lu add constraint primary key 
    (address_type_id)
    constraint u108_21;

alter table 'informix'.phone_type_lu add constraint primary key 
    (phone_type_id)
    constraint u109_22;

create index 'informix'.email_user_id_idx on 'informix'.email
    (
    user_id, 
    primary_ind
    );

alter table 'informix'.email add constraint primary key 
    (email_id)
    constraint u110_23;

alter table 'informix'.user_address_xref add constraint primary key 
    (user_id, address_id)
    constraint u111_24;

create index 'informix'.phone_user_id_idx on 'informix'.phone
    (
    user_id, 
    primary_ind
    );

alter table 'informix'.phone add constraint primary key 
    (phone_id)
    constraint u112_25;

alter table 'informix'.user_status_lu add constraint primary key 
    (user_status_id)
    constraint u113_26;

alter table 'informix'.terms_of_use add constraint primary key 
    (terms_of_use_id)
    constraint u114_27;

alter table 'informix'.terms_of_use_type add constraint primary key 
    (terms_of_use_type_id)
    constraint u115_28;

alter table 'informix'.user_terms_of_use_xref add constraint primary key 
    (user_id, terms_of_use_id)
    constraint u116_29;

alter table 'informix'.project_role_terms_of_use_xref add constraint primary key 
    (project_id, resource_role_id, terms_of_use_id)
    constraint pk_project_role_terms_of_use_xref;

alter table 'informix'.project_role_terms_of_use_xref add constraint foreign key 
    (terms_of_use_id)
    references 'informix'.terms_of_use
    (terms_of_use_id) 
    constraint project_role_terms_terms_fk;

alter table 'informix'.state add constraint primary key 
    (state_code)
    constraint state_pkey;

alter table 'informix'.country add constraint primary key 
    (country_code)
    constraint country_pkey;

alter table 'informix'.continent add constraint primary key 
    (continent_id)
    constraint continent_pkey;

alter table 'informix'.note add constraint primary key 
    (note_id)
    constraint note_pk;

alter table 'informix'.user_note_xref add constraint primary key 
    (user_id, note_id)
    constraint user_note_xref_pk;

alter table 'informix'.note_type_lu add constraint primary key 
    (note_type_id)
    constraint note_type_lu_pk;

alter table 'informix'.email_type_lu add constraint primary key 
    (email_type_id)
    constraint email_type_lu_pk;

alter table 'informix'.key_generation add constraint primary key 
    (user_def)
    constraint key_generation_pk;

alter table 'informix'.company add constraint primary key 
    (company_id)
    constraint u171_139;

create index 'informix'.sequence_object_idx1 on 'informix'.sequence_object
    (
    id
    );

alter table 'informix'.sequence_object add constraint primary key 
    (name)
    constraint sequence_object_pkey;

alter table 'informix'.company_datasource_xref add constraint primary key 
    (company_id, datasource_id)
    constraint company_datasource_pkey;

alter table 'informix'.datasource_type_lu add constraint primary key 
    (datasource_type_id)
    constraint u131_66;

alter table 'informix'.datasource_lu add constraint primary key 
    (datasource_id)
    constraint u130_61;

alter table 'informix'.achievement_type_lu add constraint primary key 
    (achievement_type_id)
    constraint achv_type_lu_pkey;

alter table 'informix'.preference_group_lu add constraint primary key 
    (preference_group_id)
    constraint preferencegroup_pk;

alter table 'informix'.preference_lu add constraint primary key 
    (preference_id)
    constraint preference_pk;

alter table 'informix'.user_status_type_lu add constraint primary key 
    (user_status_type_id)
    constraint userstatustypelu_pk;

alter table 'informix'.user_status add constraint primary key 
    (user_id, user_status_type_id)
    constraint userstatus_pk;

alter table 'informix'.event_lu add constraint primary key 
    (event_id)
    constraint event_pkey;

alter table 'informix'.user_event_status_lu add constraint primary key 
    (status_id)
    constraint usereventstatuslu_pkey;

alter table 'informix'.user_event add constraint primary key 
    (user_event_id)
    constraint userevent_pkey;

alter table 'informix'.email_status_lu add constraint primary key 
    (status_id)
    constraint email_status_lu_pk;

alter table 'informix'.bounce_type_lu add constraint primary key 
    (bounce_type)
    constraint bounce_type_lu_pkey;

alter table 'informix'.preference_value add constraint primary key 
    (preference_value_id)
    constraint preferencevalue_pk;

alter table 'informix'.preference_type_lu add constraint primary key 
    (preference_type_id)
    constraint preferencetype_pk;

alter table 'informix'.timezone_lu add constraint primary key 
    (timezone_id)
    constraint timezone_lu_pk;

alter table 'informix'.company_terms_of_use_xref add constraint primary key 
    (company_id, terms_of_use_id)
    constraint company_terms_of_use_pk;

alter table 'informix'.user_preference add constraint primary key 
    (user_id, preference_id)
    constraint userpreference_pk;

create index 'informix'.calendar_date_idx on 'informix'.calendar
    (
    date
    );

alter table 'informix'.calendar add constraint primary key 
    (calendar_id)
    constraint calendar_pkey;

create index 'informix'.audituser_userid_indx on 'informix'.audit_user
    (
    user_id
    );

alter table 'informix'.security_status_lu add constraint primary key 
    (security_status_id)
    constraint securitystatuslu_pkey;

alter table 'informix'.registration_type_lu add constraint primary key 
    (registration_type_id)
    constraint registrationtypelu_pkey;

alter table 'informix'.notify_lu add constraint primary key 
    (notify_id)
    constraint notify_lu_pk;

alter table 'informix'.user_notify_xref add constraint primary key 
    (user_id, notify_id)
    constraint usernotifyxref_pk;

alter table 'informix'.registration_type_notify_xref add constraint primary key 
    (registration_type_id, notify_id)
    constraint regtypenotifyxref_pk;

alter table 'informix'.demographic_question add constraint primary key 
    (demographic_question_id)
    constraint demographic_question_pkey;

create index 'informix'.demographicquesans_idx on 'informix'.demographic_answer
    (
    demographic_question_id, 
    demographic_answer_id
    );

alter table 'informix'.demographic_answer add constraint primary key 
    (demographic_answer_id)
    constraint demographic_answer_pkey;

create index 'informix'.demographic_response_idx1 on 'informix'.demographic_response
    (
    demographic_question_id, 
    demographic_answer_id, 
    user_id
    );

alter table 'informix'.demographic_assignment add constraint primary key 
    (demographic_question_id, coder_type_id, registration_type_id)
    constraint demographic_assignment_pkey;

alter table 'informix'.password_recovery add constraint primary key 
    (password_recovery_id)
    constraint password_recovery_pkey;

alter table 'informix'.secret_question add constraint primary key 
    (user_id)
    constraint secret_question_pkey;

alter table 'informix'.event_type_lu add constraint primary key 
    (event_type_id)
    constraint event_type_pkey;

alter table 'informix'.event add constraint primary key 
    (event_id)
    constraint event_prkey;

alter table 'informix'.event_registration add constraint primary key 
    (user_id, event_id)
    constraint eventregistration_pkey;

create index 'informix'.user_handle_idx on 'informix'.user
    (
    handle
    );

create index 'informix'.user_lower_handle_idx on 'informix'.user
    (
    handle_lower
    );

alter table 'informix'.user add constraint primary key 
    (user_id)
    constraint u175_45;

alter table 'informix'.registration_type_preference_xref add constraint primary key 
    (registration_type_id, preference_id)
    constraint regtypepreferencexref_pk;

alter table 'informix'.notify_type_lu add constraint primary key 
    (notify_type_id)
    constraint notifytype_pk;

alter table 'informix'.school_type_lu add constraint primary key 
    (school_type_id)
    constraint schooltypelu_pk;

alter table 'informix'.school add constraint primary key 
    (school_id)
    constraint school_pkey;

alter table 'informix'.school_association_type_lu add constraint primary key 
    (school_association_type_id)
    constraint school_association_type_pkey;

alter table 'informix'.user_school add constraint primary key 
    (user_school_id)
    constraint user_school_pk;

alter table 'informix'.professor_status_lu add constraint primary key 
    (status_id)
    constraint pk_professorstatus;

alter table 'informix'.professor add constraint primary key 
    (user_id)
    constraint pk_professor;

alter table 'informix'.permission_code add constraint primary key 
    (code)
    constraint pk_permission_code;


alter table 'informix'.user_role_xref add constraint foreign key 
    (login_id)
    references 'informix'.security_user
    (login_id) 
    on delete cascade 
    constraint fk_user_role_xref1;

alter table 'informix'.user_role_xref add constraint foreign key 
    (role_id)
    references 'informix'.security_roles
    (role_id) 
    on delete cascade 
    constraint fk_user_role_xref2;

alter table 'informix'.user_role_xref add constraint foreign key 
    (security_status_id)
    references 'informix'.security_status_lu
    (security_status_id) 
    constraint userrolexref_status_fk;

alter table 'informix'.user_group_xref add constraint foreign key 
    (login_id)
    references 'informix'.security_user
    (login_id) 
    on delete cascade 
    constraint fk_user_grp_xref1;

alter table 'informix'.user_group_xref add constraint foreign key 
    (group_id)
    references 'informix'.security_groups
    (group_id) 
    on delete cascade 
    constraint fk_user_grp_xref2;

alter table 'informix'.user_group_xref add constraint foreign key 
    (security_status_id)
    references 'informix'.security_status_lu
    (security_status_id) 
    constraint usergroupxref_status_fk;

alter table 'informix'.group_role_xref add constraint foreign key 
    (group_id)
    references 'informix'.security_groups
    (group_id) 
    on delete cascade 
    constraint fk_grp_role_xref1;

alter table 'informix'.group_role_xref add constraint foreign key 
    (role_id)
    references 'informix'.security_roles
    (role_id) 
    on delete cascade 
    constraint fk_grp_role_xref2;

alter table 'informix'.group_role_xref add constraint foreign key 
    (security_status_id)
    references 'informix'.security_status_lu
    (security_status_id) 
    constraint grouprolexref_status_fk;

alter table 'informix'.security_perms add constraint foreign key 
    (role_id)
    references 'informix'.security_roles
    (role_id) 
    on delete cascade 
    constraint fk_security_perm;

alter table 'informix'.security_perms add constraint foreign key 
    (security_status_id)
    references 'informix'.security_status_lu
    (security_status_id) 
    constraint securityperms_status_fk;

alter table 'informix'.email add constraint foreign key 
    (email_type_id)
    references 'informix'.email_type_lu
    (email_type_id) 
    constraint email_emailtypelu_fk;

alter table 'informix'.email add constraint foreign key 
    (status_id)
    references 'informix'.email_status_lu
    (status_id) 
    constraint email_emailstatuslu_fk;

alter table 'informix'.phone add constraint foreign key 
    (phone_type_id)
    references 'informix'.phone_type_lu
    (phone_type_id) 
    constraint phone_phonetypelu_fk;

alter table 'informix'.user_terms_of_use_xref add constraint foreign key 
    (terms_of_use_id)
    references 'informix'.terms_of_use
    (terms_of_use_id) 
    constraint userterms_terms_fk;

alter table 'informix'.contact add constraint foreign key 
    (company_id)
    references 'informix'.company
    (company_id) 
    constraint contact_company_fk;

alter table 'informix'.contact add constraint foreign key 
    (contact_id)
    references 'informix'.user
    (user_id) 
    constraint contact_user_fk;

alter table 'informix'.note add constraint foreign key 
    (note_type_id)
    references 'informix'.note_type_lu
    (note_type_id) 
    constraint note_note_type_lu_fk;

alter table 'informix'.user_note_xref add constraint foreign key 
    (note_id)
    references 'informix'.note
    (note_id) 
    constraint user_note_xref_note;

alter table 'informix'.user_note_xref add constraint foreign key 
    (user_id)
    references 'informix'.user
    (user_id) 
    constraint user_note_xref_user_fk;

alter table 'informix'.company add constraint foreign key 
    (timezone_id)
    references 'informix'.timezone_lu
    (timezone_id) 
    constraint company_timezone_fk;

alter table 'informix'.datasource_lu add constraint foreign key 
    (datasource_type_id)
    references 'informix'.datasource_type_lu
    (datasource_type_id) 
    constraint fk_datasource_type;

alter table 'informix'.user_achievement add constraint foreign key 
    (user_id)
    references 'informix'.user
    (user_id) 
    constraint achv_user_fk;

alter table 'informix'.user_achievement add constraint foreign key 
    (achievement_type_id)
    references 'informix'.achievement_type_lu
    (achievement_type_id) 
    constraint achv_type_fk;

alter table 'informix'.preference_lu add constraint foreign key 
    (preference_group_id)
    references 'informix'.preference_group_lu
    (preference_group_id) 
    constraint preference_preference_group_fk;

alter table 'informix'.preference_lu add constraint foreign key 
    (preference_type_id)
    references 'informix'.preference_type_lu
    (preference_type_id) 
    constraint preference_preferencetype_fk;

alter table 'informix'.user_status add constraint foreign key 
    (user_id)
    references 'informix'.user
    (user_id) 
    constraint userstatus_user_fk;

alter table 'informix'.user_status add constraint foreign key 
    (user_status_type_id)
    references 'informix'.user_status_type_lu
    (user_status_type_id) 
    constraint userstatus_userstatustype_fk;

alter table 'informix'.user_status add constraint foreign key 
    (user_status_id)
    references 'informix'.user_status_lu
    (user_status_id) 
    constraint userstatus_userstatuslu_fk;

alter table 'informix'.user_event add constraint foreign key 
    (event_id)
    references 'informix'.event_lu
    (event_id) 
    constraint userevent_eventlu_fk;

alter table 'informix'.user_event add constraint foreign key 
    (status_id)
    references 'informix'.user_event_status_lu
    (status_id) 
    constraint userevent_usereventstatuslu_fk;

alter table 'informix'.user_event add constraint foreign key 
    (user_id)
    references 'informix'.user
    (user_id) 
    constraint userevent_user_fk;

alter table 'informix'.preference_value add constraint foreign key 
    (preference_id)
    references 'informix'.preference_lu
    (preference_id) 
    constraint preferencevalue_preference_fk;

alter table 'informix'.company_terms_of_use_xref add constraint foreign key 
    (company_id)
    references 'informix'.company
    (company_id) 
    constraint companytermsofuse_company_fk;

alter table 'informix'.user_preference add constraint foreign key 
    (user_id)
    references 'informix'.user
    (user_id) 
    constraint userpreference_user_fk;

alter table 'informix'.user_preference add constraint foreign key 
    (preference_id)
    references 'informix'.preference_lu
    (preference_id) 
    constraint userpreference_preference_fk;

alter table 'informix'.user_preference add constraint foreign key 
    (preference_value_id)
    references 'informix'.preference_value
    (preference_value_id) 
    constraint userpreference_preferencevalue_fk;

alter table 'informix'.registration_type_lu add constraint foreign key 
    (security_group_id)
    references 'informix'.security_groups
    (group_id) 
    constraint regtype_securitygroup_fkey;

alter table 'informix'.notify_lu add constraint foreign key 
    (notify_type_id)
    references 'informix'.notify_type_lu
    (notify_type_id) 
    constraint notify_notifytype_fk;

alter table 'informix'.user_notify_xref add constraint foreign key 
    (notify_id)
    references 'informix'.notify_lu
    (notify_id) 
    constraint usernotifyxref_notifylu_fk;

alter table 'informix'.user_notify_xref add constraint foreign key 
    (user_id)
    references 'informix'.user
    (user_id) 
    constraint usernotifyxref_user_fk;

alter table 'informix'.registration_type_notify_xref add constraint foreign key 
    (notify_id)
    references 'informix'.notify_lu
    (notify_id) 
    constraint regtypexref_notifylu_fk;

alter table 'informix'.registration_type_notify_xref add constraint foreign key 
    (registration_type_id)
    references 'informix'.registration_type_lu
    (registration_type_id) 
    constraint regtypexref_notify_fk;

alter table 'informix'.demographic_answer add constraint foreign key 
    (demographic_question_id)
    references 'informix'.demographic_question
    (demographic_question_id) 
    constraint demoanswer_demoquestion_fk;

alter table 'informix'.demographic_response add constraint foreign key 
    (user_id)
    references 'informix'.user
    (user_id) 
    constraint demoresponse_user_fk;

alter table 'informix'.demographic_response add constraint foreign key 
    (demographic_question_id)
    references 'informix'.demographic_question
    (demographic_question_id) 
    constraint demoresponse_demoquestion_fk;

alter table 'informix'.demographic_response add constraint foreign key 
    (demographic_answer_id)
    references 'informix'.demographic_answer
    (demographic_answer_id) 
    constraint demoresponse_demoanswer_fk;

alter table 'informix'.demographic_assignment add constraint foreign key 
    (demographic_question_id)
    references 'informix'.demographic_question
    (demographic_question_id) 
    constraint demoassign_demoquestion_fk;

alter table 'informix'.password_recovery add constraint foreign key 
    (user_id)
    references 'informix'.user
    (user_id) 
    constraint password_recovery_user_fk;

alter table 'informix'.secret_question add constraint foreign key 
    (user_id)
    references 'informix'.user
    (user_id) 
    constraint secret_question_user_fk;

alter table 'informix'.event add constraint foreign key 
    (terms_of_use_id)
    references 'informix'.terms_of_use
    (terms_of_use_id) 
    constraint event_terms_fk;

alter table 'informix'.event add constraint foreign key 
    (event_type_id)
    references 'informix'.event_type_lu
    (event_type_id) 
    constraint event_eventtype_fk;

alter table 'informix'.event add constraint foreign key 
    (parent_event_id)
    references 'informix'.event
    (event_id) 
    constraint event_parent_id_fk;

alter table 'informix'.event_registration add constraint foreign key 
    (event_id)
    references 'informix'.event
    (event_id) 
    constraint eventreg_event_fk;

alter table 'informix'.event_registration add constraint foreign key 
    (user_id)
    references 'informix'.user
    (user_id) 
    constraint eventreg_user_fk;

alter table 'informix'.user add constraint foreign key 
    (timezone_id)
    references 'informix'.timezone_lu
    (timezone_id) 
    constraint user_timezonelu_fk;

alter table 'informix'.registration_type_preference_xref add constraint foreign key 
    (preference_id)
    references 'informix'.preference_lu
    (preference_id) 
    constraint regtypexref_preferencelu_fk;

alter table 'informix'.registration_type_preference_xref add constraint foreign key 
    (registration_type_id)
    references 'informix'.registration_type_lu
    (registration_type_id) 
    constraint regtypexref_preference_fk;

alter table 'informix'.school add constraint foreign key 
    (school_type_id)
    references 'informix'.school_type_lu
    (school_type_id) 
    constraint school_schooltype_fk;

alter table 'informix'.user_school add constraint foreign key 
    (school_id)
    references 'informix'.school
    (school_id) 
    constraint user_school_school_fk;

alter table 'informix'.user_school add constraint foreign key 
    (user_id)
    references 'informix'.user
    (user_id) 
    constraint userschool_user_fk;

alter table 'informix'.user_school add constraint foreign key 
    (school_association_type_id)
    references 'informix'.school_association_type_lu
    (school_association_type_id) 
    constraint userschool_schoolassociationtype_fk;

alter table 'informix'.professor add constraint foreign key 
    (user_id)
    references 'informix'.user
    (user_id) 
    constraint fk_professor_user;

alter table 'informix'.professor add constraint foreign key 
    (status_id)
    references 'informix'.professor_status_lu
    (status_id) 
    constraint fk_professor_professorstatus;

alter table 'informix'.permission_code add constraint foreign key 
    (group_id)
    references 'informix'.security_groups
    (group_id) 
    constraint fk_permission_code_security_groups;

alter table 'informix'.group_contest_eligibility add constraint foreign key 
    (contest_eligibility_id) 
    REFERENCES 'informix'.contest_eligibility
    (contest_eligibility_id) 
    constraint contest_eligibility_fk;

alter table 'informix'.client_terms_mapping add constraint foreign key 
    (terms_of_use_id ) 
    REFERENCES 'informix'.terms_of_use(terms_of_use_id)
    constraint client_terms_mapping_terms_fk;


