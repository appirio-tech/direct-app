database common_oltp;

create procedure "informix".get_current() returning datetime year to fraction(3);
  return CURRENT;
end procedure;
create procedure "informix".nextval(i_seqnum INTEGER)
returning int;
   define v numeric;
   define doCommit varchar(5,0);
   set lock mode to wait 5;
   set isolation to committed read;
   let doCommit = 'true';
   BEGIN
     on exception in (-535)
       let doCommit = 'false';
     end exception with resume;
     begin work;
   END
   update sequence_object set current_value = current_value +1 where id = i_seqnum;
   let v = nvl ( (select current_value from sequence_object where id = i_seqnum), -1);
   if (doCommit = 'true') then
      commit;
   end if;
   return v;
end procedure;
create procedure "informix".proc_user_update(
new_handle varchar(50),
user_id decimal(10,0))
UPDATE user SET handle_lower = lower(new_handle), modify_date = current WHERE user.user_id = user_id;
end procedure;
create procedure "informix".proc_user_update(
user_id DECIMAL(10,0),
old_first_name VARCHAR(64),
new_first_name VARCHAR(64),
old_last_name VARCHAR(64),
new_last_name VARCHAR(64),
old_handle VARCHAR(50),
new_handle VARCHAR(50),
old_status VARCHAR(3),
new_status VARCHAR(3),
old_password VARCHAR(15),
new_password VARCHAR(15),
old_activation_code VARCHAR(32),
new_activation_code VARCHAR(32),
old_middle_name VARCHAR(64),
new_middle_name VARCHAR(64),
old_timezone_id decimal(5,0),
new_timezone_id decimal(5,0)
)
 
      if ((old_first_name != new_first_name) or (old_last_name != new_last_name ) or (old_middle_name != new_middle_name )) then
         insert into audit_user (column_name, old_value, new_value,
user_id)
         values ('NAME', NVL(old_first_name, '') || ' ' || NVL(old_middle_name, '') || ' ' || NVL(old_last_name, ''),
                 NVL(new_first_name, '') || ' ' || NVL(new_middle_name,
'') || ' ' || NVL(new_last_name, ''), user_id);
      End if;
      
      if (old_handle != new_handle) then 
         insert into audit_user (column_name, old_value, new_value,
user_id)
         values ('HANDLE', old_handle, new_handle, user_id);
      End If;

      if (old_status != new_status) then 
         insert into audit_user (column_name, old_value, new_value,
user_id)
         values ('STATUS', old_status, new_status, user_id);
      End If;

      if (old_password != new_password) then 
         insert into audit_user (column_name, old_value, new_value,
user_id)
         values ('PASSWORD', old_password, new_password, user_id);
      End If;

      if (old_activation_code != new_activation_code) then 
         insert into audit_user (column_name, old_value, new_value,
user_id)
         values ('ACTIVATION_CODE', old_activation_code, new_activation_code, user_id);
      End If;

      if (old_timezone_id != new_timezone_id) then 
         insert into audit_user (column_name, old_value, new_value,
user_id)
         values ('TIMEZONE_ID', old_timezone_id, new_timezone_id, user_id);
      End If;
      UPDATE user SET handle_lower = lower(new_handle), modify_date = current WHERE user.user_id = user_id;

end procedure;
revoke execute on procedure proc_user_update(decimal,varchar,varchar,varchar,varchar,varchar,varchar,varchar,varchar,varchar,varchar,varchar,varchar,varchar,varchar,decimal,decimal) from 'public';
create procedure "informix".proc_email_update(
email_id decimal(10,0),
user_id DECIMAL(10,0),
old_email_type_id DECIMAL(5,0),
new_email_type_id DECIMAL(5,0),
old_address VARCHAR(100),
new_address VARCHAR(100),
old_primary_ind DECIMAL(1,0),
new_primary_ind DECIMAL(1,0),
old_status_id DECIMAL(3,0),
new_status_id DECIMAL(3,0)
)
 
      if (old_email_type_id != new_email_type_id) then 
         insert into audit_user (column_name, old_value, new_value, user_id)
         values ('EMAIL_TYPE', old_email_type_id, new_email_type_id, user_id);
      End If;

      if (old_status_id != new_status_id) then 
         insert into audit_user (column_name, old_value, new_value, user_id)
         values ('EMAIL_STATUS', old_status_id, new_status_id, user_id);
      End If;

      if (old_address != new_address) then 
         insert into audit_user (column_name, old_value, new_value, user_id)
         values ('EMAIL_ADDRESS', old_address, new_address, user_id);
      End If;

      if (old_primary_ind != new_primary_ind) then 
         insert into audit_user (column_name, old_value, new_value, user_id)
         values ('EMAIL_PRIMARY_IND', old_primary_ind, new_primary_ind, user_id);
      End If;

	update email set modify_date = current where email.email_id = email_id;
end procedure;
create procedure "informix".proc_phone_update(
phone_id decimal(10,0),
user_id DECIMAL(10,0),
old_phone_type_id DECIMAL(5,0),
new_phone_type_id DECIMAL(5,0),
old_number VARCHAR(64),
new_number VARCHAR(64),
old_primary_ind DECIMAL(1,0),
new_primary_ind DECIMAL(1,0)
)
 
      if (old_phone_type_id != new_phone_type_id) then 
         insert into audit_user (column_name, old_value, new_value, user_id)
         values ('PHONE_TYPE', old_phone_type_id, new_phone_type_id, user_id);
      End If;

      if (old_number != new_number) then 
         insert into audit_user (column_name, old_value, new_value, user_id)
         values ('PHONE_NUMBER', old_number, new_number, user_id);
      End If;

      if (old_primary_ind != new_primary_ind) then 
         insert into audit_user (column_name, old_value, new_value, user_id)
         values ('PHONE_PRIMARY_IND', old_primary_ind, new_primary_ind, user_id);
      End If;
update phone set modify_date = current where phone.phone_id = phone_id;
end procedure;
create procedure "informix".millis_to_time(milli_val decimal(14,0))
 returning datetime year to fraction(3);

  define retval datetime year to fraction(3);
  define num_days int;
  define num_seconds int;
  define millis_in_day int;

  let millis_in_day = 86400000;
  let num_days = trunc(milli_val/millis_in_day,0);
  let num_seconds = (milli_val - (num_days * millis_in_day))/1000;

  let retval = extend(mdy(1,1,1970), year to fraction(3));
  let retval = retval + num_days units day;
  let retval = retval + num_seconds units second;

  return retval;

end procedure;
create procedure "informix".tc_lower( val varchar(255) )
returning varchar(255);
  return lower(val);
end procedure;
create procedure "informix".proc_address_update(
  address_id DECIMAL(10,0),
  old_address_type_id DECIMAL(5,0),
  new_address_type_id DECIMAL(5,0),
  old_address1 VARCHAR(254),
  new_address1 VARCHAR(254),
  old_address2 VARCHAR(254),
  new_address2 VARCHAR(254),
  old_address3 VARCHAR(254),
  new_address3 VARCHAR(254),
  old_city VARCHAR(64),
  new_city VARCHAR(64),
  old_state_code CHAR(2),
  new_state_code CHAR(2),
  old_province VARCHAR(64),
  new_province VARCHAR(64),
  old_zip VARCHAR(15),
  new_zip VARCHAR(15),
  old_country_code CHAR(3),
  new_country_code CHAR(3)
)
      define user_id DECIMAL(10,0);
      let user_id = NVL((select min(x.user_id) from user_address_xref x where x.address_id = address_id), -1);
 
      if (user_id > 0 and old_address1 != new_address1) then 
         insert into audit_user (column_name, old_value, new_value,
user_id)
         values ('ADDRESS1', old_address1, new_address1, user_id);
      End If;
      if (user_id > 0 and old_address2 != new_address2) then 
         insert into audit_user (column_name, old_value, new_value,
user_id)
         values ('ADDRESS2', old_address2, new_address2, user_id);
      End If;
      if (user_id > 0 and old_address3 != new_address3) then 
         insert into audit_user (column_name, old_value, new_value,
user_id)
         values ('ADDRESS3', old_address3, new_address3, user_id);
      End If;
      if (user_id > 0 and old_city != new_city) then 
         insert into audit_user (column_name, old_value, new_value,
user_id)
         values ('ADDRESS_CITY', old_city, new_city, user_id);
      End If;
      if (user_id > 0 and old_state_code != new_state_code) then 
         insert into audit_user (column_name, old_value, new_value,
user_id)
         values ('ADDRESS_STATE', old_state_code, new_state_code, user_id);
      End If;
      if (user_id > 0 and old_province != new_province) then 
         insert into audit_user (column_name, old_value, new_value,
user_id)
         values ('ADDRESS_PROVINCE', old_province, new_province, user_id);
      End If;
      if (user_id > 0 and old_zip != new_zip) then 
         insert into audit_user (column_name, old_value, new_value,
user_id)
         values ('ADDRESS_ZIP', old_zip, new_zip, user_id);
      End If;
      if (user_id > 0 and old_country_code != new_country_code) then 
         insert into audit_user (column_name, old_value, new_value,
user_id)
         values ('ADDRESS_COUNTRY', old_country_code, new_country_code, user_id);
      End If;

      update address set modify_date = current where address.address_id = address_id;

end procedure;

grant execute on procedure ifx_load_module(varchar,varchar) to 'public' as 'informix';

grant execute on procedure ifx_trigger_cols(integer) to 'public' as 'informix';

grant execute on procedure ifx_trigger_action(integer,char) to 'public' as 'informix';

grant execute on procedure ifx_unload_module(varchar,varchar) to 'public' as 'informix';

grant execute on procedure ifx_replace_module(varchar,varchar,varchar) to 'public' as 'informix';

grant execute on procedure systdist(integer,integer) to 'public' as 'informix';

grant execute on procedure get_current() to 'public' as 'informix';

grant execute on procedure nextval(integer) to 'public' as 'informix';

grant execute on procedure proc_user_update(varchar,decimal) to 'public' as 'informix';

grant execute on procedure proc_email_update(decimal,decimal,decimal,decimal,varchar,varchar,decimal,decimal,decimal,decimal) to 'public' as 'informix';

grant execute on procedure proc_phone_update(decimal,decimal,decimal,decimal,varchar,varchar,decimal,decimal) to 'public' as 'informix';

grant execute on procedure millis_to_time(decimal) to 'public' as 'informix';

grant execute on procedure tc_lower(varchar) to 'public' as 'informix';

grant execute on procedure proc_address_update(decimal,decimal,decimal,varchar,varchar,varchar,varchar,varchar,varchar,varchar,varchar,char,char,varchar,varchar,varchar,varchar,char,char) to 'public' as 'informix';

create trigger "informix".trig_audit_address update of address_type_id,address1,address2,address3,city,state_code,province,zip,country_code on "informix".address referencing old as old new as new                                                                 for each row
        (
        execute procedure "informix".proc_address_update(old.address_id ,old.address_type_id ,new.address_type_id ,old.address1 ,new.address1 ,old.address2 ,new.address2 ,old.address3 ,new.address3 ,old.city ,new.city ,old.state_code ,new.state_code ,old.province ,new.province ,old.zip ,new.zip ,old.country_code ,new.country_code ));
create trigger "informix".trig_audit_email update of email_type_id,address,primary_ind,status_id on "informix".email referencing old as old new as new                                                                                                              for each row
        (
        execute procedure "informix".proc_email_update(old.email_id ,old.user_id ,old.email_type_id ,new.email_type_id ,old.address ,new.address ,old.primary_ind ,new.primary_ind ,old.status_id ,new.status_id ));
create trigger "informix".trig_audit_phone update of phone_type_id,phone_number,primary_ind on "informix".phone referencing old as old new as new                                                                                                                   for each row
        (
        execute procedure "informix".proc_phone_update(old.phone_id ,old.user_id ,old.phone_type_id ,new.phone_type_id ,old.phone_number ,new.phone_number ,old.primary_ind ,new.primary_ind ));
create trigger "informix".trig_note_modified update of text,submitted_by,note_type_id on "informix".note referencing old as old                                                                                                                                     for each row
        (
        execute function "informix".get_current() into "informix".note.modify_date);
create trigger "informix".trig_user_insert insert on "informix".user referencing new as n                                                                                                                                                                           for each row
        (
        execute procedure "informix".proc_user_update(n.handle ,n.user_id ));
create trigger "informix".trig_user_preference_update update of preference_id,value,preference_value_id on "informix".user_preference referencing old as o new as n                                                                                                 for each row
        (
        execute function "informix".get_current() into "informix".user_preference.modify_date);
create trigger "informix".trig_audit_user update of first_name,last_name,handle,last_login,status,password,activation_code,middle_name,timezone_id,last_site_hit_date on "informix".user referencing old as old new as new                                          for each row
        (
        execute procedure "informix".proc_user_update(old.user_id ,old.first_name ,new.first_name ,old.last_name ,new.last_name ,old.handle ,new.handle ,old.status ,new.status ,old.password ,new.password ,old.activation_code ,new.activation_code ,old.middle_name ,new.middle_name ,old.timezone_id ,new.timezone_id ));
create trigger "informix".trig_event_reg_modified update of event_id,user_id,eligible_ind on "informix".event_registration referencing old as old                                                                                                                   for each row
        (
        execute function "informix".get_current() into "informix".event_registration.modify_date);
create trigger "informix".trig_demographic_response_modified update of demographic_answer_id,demographic_response on "informix".demographic_response referencing old as old                                                                                         for each row
        (
        execute function "informix".get_current() into "informix".demographic_response.modify_date);
create trigger "informix".trig_event_modified update of event_type_id,event_desc,start_registration,end_registration,terms_of_use_id,survey_id on "informix".event referencing old as old                                                                           for each row
        (
        execute function "informix".get_current() into "informix".event.modify_date);
create trigger "informix".trig_event_inserted insert on "informix".event referencing new as nw                                                                                                                                                                      for each row
        (
        execute function "informix".get_current() into "informix".event.modify_date);
