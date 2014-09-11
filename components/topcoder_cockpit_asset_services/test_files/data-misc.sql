INSERT INTO 'informix'.user (user_id, handle, status) VALUES(1, 'cm1', 'a');
INSERT INTO 'informix'.user (user_id, handle, status) VALUES(2, 'cm2', 'a');
INSERT INTO 'informix'.user (user_id, handle, status) VALUES(3, 'cm3', 'a');
INSERT INTO 'informix'.user (user_id, handle, status) VALUES(4, 'tcm1', 'a');
INSERT INTO 'informix'.user (user_id, handle, status) VALUES(5, 'tcm2', 'a');
INSERT INTO 'informix'.user (user_id, handle, status) VALUES(6, 'tcm3', 'a');
INSERT INTO 'informix'.user (user_id, handle, status) VALUES(7, 'cp1', 'a');
INSERT INTO 'informix'.user (user_id, handle, status) VALUES(8, 'cp2', 'a');
INSERT INTO 'informix'.user (user_id, handle, status) VALUES(9, 'cp3', 'a');

INSERT INTO 'informix'.Asset (id, name,container_type,container_id, public) VALUES(1, 'Asset1', 'type1', 1, 't');
INSERT INTO 'informix'.Asset (id, name,container_type,container_id, public) VALUES(2, 'Asset2', 'type2', 2, 't');

INSERT INTO 'informix'.client_project (client_id, project_id, creation_date, creation_user, modification_date, modification_user) VALUES(1, 11, CURRENT, 'admin', CURRENT, 'admin');
INSERT INTO 'informix'.client_project (client_id, project_id, creation_date, creation_user, modification_date, modification_user) VALUES(2, 12, CURRENT, 'admin', CURRENT, 'admin');

INSERT INTO 'informix'.user_client (user_id, client_id) VALUES(1, 1);
INSERT INTO 'informix'.user_client (user_id, client_id) VALUES(2, 1);
INSERT INTO 'informix'.user_client (user_id, client_id) VALUES(3, 2);

INSERT INTO 'informix'.user_account (user_account_id, account_status_id, user_name, password, creation_date, creation_user, modification_date, modification_user) VALUES(1, 1, 'tcm1', 'pass1', CURRENT, 'admin', CURRENT, 'admin');
INSERT INTO 'informix'.user_account (user_account_id, account_status_id, user_name, password, creation_date, creation_user, modification_date, modification_user) VALUES(2, 1, 'tcm2', 'pass2', CURRENT, 'admin', CURRENT, 'admin');
INSERT INTO 'informix'.user_account (user_account_id, account_status_id, user_name, password, creation_date, creation_user, modification_date, modification_user) VALUES(3, 1, 'tcm3', 'pass3', CURRENT, 'admin', CURRENT, 'admin');

INSERT INTO 'informix'.project_manager (project_id, user_account_id, cost, active, creation_date, creation_user, modification_date, modification_user) VALUES(11, 1, 1, 1, CURRENT, 'admin', CURRENT, 'admin');
INSERT INTO 'informix'.project_manager (project_id, user_account_id, cost, active, creation_date, creation_user, modification_date, modification_user) VALUES(11, 2, 1, 1, CURRENT, 'admin', CURRENT, 'admin');
INSERT INTO 'informix'.project_manager (project_id, user_account_id, cost, active, creation_date, creation_user, modification_date, modification_user) VALUES(12, 3, 1, 1, CURRENT, 'admin', CURRENT, 'admin');

INSERT INTO 'informix'.copilot_profile (copilot_profile_id, user_id, copilot_profile_status_id, suspension_count, reliability, activation_date, create_user, create_date, update_user, update_date) VALUES(1, 7, 1, 0, 100, CURRENT, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.copilot_profile (copilot_profile_id, user_id, copilot_profile_status_id, suspension_count, reliability, activation_date, create_user, create_date, update_user, update_date) VALUES(2, 8, 1, 0, 100, CURRENT, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.copilot_profile (copilot_profile_id, user_id, copilot_profile_status_id, suspension_count, reliability, activation_date, create_user, create_date, update_user, update_date) VALUES(3, 9, 1, 0, 100, CURRENT, 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO 'informix'.copilot_project (copilot_project_id, copilot_profile_id, tc_direct_project_id, copilot_type_id, copilot_project_status_id, private_project, completion_date, create_user, create_date, modify_user, modify_date) VALUES(1, 1, 11, 1, 1, 0, CURRENT, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.copilot_project (copilot_project_id, copilot_profile_id, tc_direct_project_id, copilot_type_id, copilot_project_status_id, private_project, completion_date, create_user, create_date, modify_user, modify_date) VALUES(2, 2, 11, 1, 1, 0, CURRENT, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO 'informix'.copilot_project (copilot_project_id, copilot_profile_id, tc_direct_project_id, copilot_type_id, copilot_project_status_id, private_project, completion_date, create_user, create_date, modify_user, modify_date) VALUES(3, 3, 12, 1, 1, 0, CURRENT, 'admin', CURRENT, 'admin', CURRENT);
