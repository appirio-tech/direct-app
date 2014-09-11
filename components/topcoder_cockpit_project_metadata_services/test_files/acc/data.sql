
INSERT INTO tc_direct_project(project_id, name, description, project_status_id, user_id, create_date, modify_date) VALUES(1, 'project1', 'description1', 1, 1, CURRENT, CURRENT);
INSERT INTO tc_direct_project(project_id, name, description, project_status_id, user_id, create_date, modify_date) VALUES(2, 'project2', 'description2', 1, 1, CURRENT, CURRENT);
INSERT INTO tc_direct_project(project_id, name, description, project_status_id, user_id, create_date, modify_date) VALUES(3, 'project3', 'description3', 1, 1, CURRENT, CURRENT);
INSERT INTO tc_direct_project(project_id, name, description, project_status_id, user_id, create_date, modify_date) VALUES(4, 'project4', 'description4', 1, 1, CURRENT, CURRENT);
INSERT INTO tc_direct_project(project_id, name, description, project_status_id, user_id, create_date, modify_date) VALUES(5, 'project5', 'description5', 1, 1, CURRENT, CURRENT);
INSERT INTO tc_direct_project(project_id, name, description, project_status_id, user_id, create_date, modify_date) VALUES(6, 'project6', 'description6', 1, 1, CURRENT, CURRENT);
INSERT INTO tc_direct_project(project_id, name, description, project_status_id, user_id, create_date, modify_date) VALUES(7, 'project7', 'description7', 1, 1, CURRENT, CURRENT);
INSERT INTO tc_direct_project(project_id, name, description, project_status_id, user_id, create_date, modify_date) VALUES(8, 'project8', 'description8', 1, 1, CURRENT, CURRENT);

INSERT INTO audit_action_type_lu (audit_action_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (1, 'Create', 'Create', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO audit_action_type_lu (audit_action_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (2, 'Delete', 'Delete', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO audit_action_type_lu (audit_action_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (3, 'Update', 'Update', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO audit_action_type_lu (audit_action_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (4, 'Create', 'Create', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO audit_action_type_lu (audit_action_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (5, 'Delete', 'Delete', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO audit_action_type_lu (audit_action_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (6, 'Update', 'Update', 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO direct_project_metadata_key(id, name, description, grouping, client_id, single) VALUES(1, 'n 1', 'desc 1', '1', null, '1');
INSERT INTO direct_project_metadata_key(id, name, description, grouping, client_id, single) VALUES(2, 'n 2', 'desc 2', '1', 1, '0');
INSERT INTO direct_project_metadata_key(id, name, description, grouping, client_id, single) VALUES(3, 'n 3', 'desc 3', '1', 2, '1');
INSERT INTO direct_project_metadata_key(id, name, description, grouping, client_id, single) VALUES(4, 'n 4', 'desc 4', '0', 2, '1');

INSERT INTO direct_project_metadata(id, tc_direct_project_id, project_metadata_key_id, metadata_value) VALUES(1, 1, 1, 'v 1');
INSERT INTO direct_project_metadata(id, tc_direct_project_id, project_metadata_key_id, metadata_value) VALUES(2, 1, 1, 'v 12');
INSERT INTO direct_project_metadata(id, tc_direct_project_id, project_metadata_key_id, metadata_value) VALUES(3, 1, 1, 'v 3');
INSERT INTO direct_project_metadata(id, tc_direct_project_id, project_metadata_key_id, metadata_value) VALUES(4, 1, 1, 'v 4');
INSERT INTO direct_project_metadata(id, tc_direct_project_id, project_metadata_key_id, metadata_value) VALUES(5, 2, 1, 'v 1');
INSERT INTO direct_project_metadata(id, tc_direct_project_id, project_metadata_key_id, metadata_value) VALUES(6, 2, 2, 'v 2');
INSERT INTO direct_project_metadata(id, tc_direct_project_id, project_metadata_key_id, metadata_value) VALUES(7, 3, 1, 'v 12');
INSERT INTO direct_project_metadata(id, tc_direct_project_id, project_metadata_key_id, metadata_value) VALUES(8, 3, 3, 'v 12');