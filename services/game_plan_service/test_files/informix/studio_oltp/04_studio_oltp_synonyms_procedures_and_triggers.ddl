database studio_oltp;


create synonym "informix".user for common_oltp:"informix".user;
create synonym "informix".contact for common_oltp:"informix".contact;
create synonym "informix".email for common_oltp:"informix".email;
create synonym "informix".phone for common_oltp:"informix".phone;
create synonym "informix".terms_of_use for common_oltp:"informix".terms_of_use;
create synonym "informix".timezone_lu for common_oltp:"informix".timezone_lu;
create synonym "informix".secret_question for common_oltp:"informix".secret_question;
create synonym "informix".event for common_oltp:"informix".event;
create synonym "informix".event_registration for common_oltp:"informix".event_registration;
create synonym "informix".event_type_lu for common_oltp:"informix".event_type_lu;
create synonym "informix".professor for common_oltp:"informix".professor;
create synonym "informix".professor_status_lu for common_oltp:"informix".professor_status_lu;
create synonym "informix".security_user for common_oltp:"informix".security_user;
create synonym "informix".security_roles for common_oltp:"informix".security_roles;
create synonym "informix".user_role_xref for common_oltp:"informix".user_role_xref;
create synonym "informix".group_role_xref for common_oltp:"informix".group_role_xref;
create synonym "informix".user_group_xref for common_oltp:"informix".user_group_xref;
create synonym "informix".user_security_key for common_oltp:"informix".user_security_key;

create synonym 'informix'.project_role_terms_of_use_xref
for common_oltp:'informix'.project_role_terms_of_use_xref;

create synonym 'informix'.user_terms_of_use_xref
for common_oltp:'informix'.user_terms_of_use_xref;

create synonym 'informix'.user_security_key
for common_oltp:'informix'.user_security_key;

create synonym 'informix'.contest_eligibility
for common_oltp:'informix'.contest_eligibility;

create synonym 'informix'.group_contest_eligibility
for common_oltp:'informix'.group_contest_eligibility;

create procedure "informix".get_current() returning datetime year to fraction(3);
  return CURRENT;
end procedure;


create procedure "informix".proc_submission_review_update(
v_submission_id decimal(10,0), v_old_reviewer_id decimal(10,0), v_new_reviewer_id decimal(10,0), 
v_old_text lvarchar, v_new_text lvarchar, v_old_review_status_id decimal(3,0), v_new_review_status_id decimal(3,0)

)
 
      if (v_old_reviewer_id != v_new_reviewer_id) then
         insert into submission_review_audit (submission_id, column_name, old_value, new_value)
         values (v_submission_id, 'REVIEWER_ID', v_old_reviewer_id , v_new_reviewer_id);
      End if;
      if (v_old_text != v_new_text) then
         insert into submission_review_audit (submission_id, column_name, old_value, new_value)
         values (v_submission_id, 'TEXT', v_old_text , v_new_text);
      End if;
      if (v_old_review_status_id != v_new_review_status_id) then
         insert into submission_review_audit (submission_id, column_name, old_value, new_value)
         values (v_submission_id, 'REVIEW_STATUS_ID', v_old_review_status_id , v_new_review_status_id);
      End if;

 
   update submission_review set modify_date = current where submission_id = v_submission_id;
end procedure;

create procedure "informix".proc_submission_update(v_submission_id decimal(10,0), v_old_submitter_id decimal(10,0)
, v_new_submitter_id  decimal(10,0)
, v_old_contest_id  decimal(10,0)
, v_new_contest_id  decimal(10,0)
, v_old_original_file_name varchar(254)
, v_new_original_file_name varchar(254)
, v_old_system_file_name varchar(254)
, v_new_system_file_name varchar(254)
, v_old_path_id  decimal(10,0)
, v_new_path_id  decimal(10,0)
, v_old_submission_type_id  decimal(3,0)
, v_new_submission_type_id decimal(3,0)
, v_old_mime_type_id DECIMAL(5,0)
, v_new_mime_type_id DECIMAL(5,0)
, v_old_rank DECIMAL(5,0)
, v_new_rank DECIMAL(5,0)
, v_old_height int
, v_new_height int
, v_old_width int
, v_new_width int
, v_old_submission_status_id decimal(3,0)
, v_new_submission_status_id decimal(3,0)
, v_old_or_submission_id int
, v_new_or_submission_id int
)
 
      if (v_old_submitter_id != v_new_submitter_id) then
         insert into submission_audit (submission_id, column_name, old_value, new_value)
         values (v_submission_id, 'SUBMITTER_ID', v_old_submitter_id , v_new_submitter_id);
      End if;
      if (v_old_contest_id != v_new_contest_id) then
         insert into submission_audit (submission_id, column_name, old_value, new_value)
         values (v_submission_id, 'CONTEST_ID', v_old_contest_id , v_new_contest_id);
      End if;
      if (v_old_original_file_name != v_new_original_file_name) then
         insert into submission_audit (submission_id, column_name, old_value, new_value)
         values (v_submission_id, 'ORIGINAL_FILE_NAME', v_old_original_file_name , v_new_original_file_name);
      End if;
      if (v_old_system_file_name != v_new_system_file_name) then
         insert into submission_audit (submission_id, column_name, old_value, new_value)
         values (v_submission_id, 'SYSTEM_FILE_NAME', v_old_system_file_name , v_new_system_file_name);
      End if;
      if (v_old_path_id != v_new_path_id) then
         insert into submission_audit (submission_id, column_name, old_value, new_value)
         values (v_submission_id, 'PATH_ID', v_old_path_id , v_new_path_id);
      End if;
      if (v_old_submission_type_id != v_new_submission_type_id) then
         insert into submission_audit (submission_id, column_name, old_value, new_value)
         values (v_submission_id, 'SUBMISSION_TYPE_ID', v_old_submission_type_id , v_new_submission_type_id);
      End if;
      if (v_old_mime_type_id != v_new_mime_type_id) then
         insert into submission_audit (submission_id, column_name, old_value, new_value)
         values (v_submission_id, 'MIME_TYPE_ID', v_old_mime_type_id , v_new_mime_type_id);
      End if;
      if (v_old_rank != v_new_rank) then
         insert into submission_audit (submission_id, column_name, old_value, new_value)
         values (v_submission_id, 'RANK', v_old_rank , v_new_rank);
      End if;
      if (v_old_height != v_new_height) then
         insert into submission_audit (submission_id, column_name, old_value, new_value)
         values (v_submission_id, 'HEIGHT', v_old_height , v_new_height);
      End if;
      if (v_old_width != v_new_width) then
         insert into submission_audit (submission_id, column_name, old_value, new_value)
         values (v_submission_id, 'WIDTH', v_old_width , v_new_width);
      End if;
      if (v_old_submission_status_id != v_new_submission_status_id) then
         insert into submission_audit (submission_id, column_name, old_value, new_value)
         values (v_submission_id, 'SUBMISSION_STATUS_ID', v_old_submission_status_id , v_new_submission_status_id);
      End if;
      if (v_old_or_submission_id != v_new_or_submission_id) then
         insert into submission_audit (submission_id, column_name, old_value, new_value)
         values (v_submission_id, 'OR_SUBMISSION_ID', v_old_or_submission_id , v_new_or_submission_id);
      End if;

   update submission set modify_date = current where submission_id = v_submission_id;
end procedure;


grant  execute on procedure "informix".proc_submission_review_update (decimal,decimal,decimal,lvarchar,lvarchar,decimal,decimal) to "public" as "informix";
grant  execute on function "informix".get_current () to "public" as "informix";
grant  execute on procedure "informix".proc_submission_update (decimal,decimal,decimal,decimal,decimal,varchar,varchar,varchar,varchar,decimal,decimal,decimal,decimal,decimal,decimal,decimal,decimal,integer,integer,integer,integer,decimal,decimal,integer,integer) to "public" as "informix";


create trigger "informix".trig_audit_submission_review update 
    of reviewer_id,text,review_status_id on "informix".submission_review 
    referencing old as old new as new
    for each row
        (
        execute procedure "informix".proc_submission_review_update(old.submission_id 
    ,old.reviewer_id ,new.reviewer_id ,old.text ,new.text ,old.review_status_id 
    ,new.review_status_id ));

create trigger "informix".trig_audit_submission update of submitter_id,
    contest_id,original_file_name,system_file_name,path_id,submission_type_id,
    mime_type_id,rank,height,width,submission_status_id on "informix"
    .submission referencing old as old new as new
    for each row
        (
        execute procedure "informix".proc_submission_update(old.submission_id 
    ,old.submitter_id ,new.submitter_id ,old.contest_id ,new.contest_id 
    ,old.original_file_name ,new.original_file_name ,old.system_file_name 
    ,new.system_file_name ,old.path_id ,new.path_id ,old.submission_type_id 
    ,new.submission_type_id ,old.mime_type_id ,new.mime_type_id ,old.rank 
    ,new.rank ,old.height ,new.height ,old.width ,new.width ,old.submission_status_id 
    ,new.submission_status_id ,old.or_submission_id ,new.or_submission_id 
    ));
