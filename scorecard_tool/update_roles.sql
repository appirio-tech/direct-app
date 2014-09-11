delete user_role_xref where role_id = 99099;
delete security_roles where role_id = 99099;

insert into 'informix'.security_roles(role_id, description, create_user_id)
values('99099', 'Scorecard Administrator', null);

insert into 'informix'.user_role_xref(user_role_id, login_id, role_id, create_user_id, security_status_id)
values('99099', '132456', '99099', '132456', '1');