INSERT INTO audit_action_type_lu (audit_action_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (1, 'Create', 'Create', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO audit_action_type_lu (audit_action_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (3, 'Delete', 'Delete', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO audit_action_type_lu (audit_action_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (2, 'Update', 'Update', 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO direct_project_metadata_key(id, name, description, grouping, client_id, single) VALUES(1, 'existedkey', 'description1', '1', null, '1');
INSERT INTO direct_project_metadata_key(id, name, description, grouping, client_id, single) VALUES(1000, 'existedkey', 'description1', '1', null, '1');


INSERT INTO tc_direct_project(project_id, name, description, project_status_id, user_id, create_date, modify_date) VALUES(2000, 'project1', 'description1', 1, 1, CURRENT, CURRENT);

INSERT INTO direct_project_metadata(id, tc_direct_project_id, project_metadata_key_id, metadata_value) VALUES(1, 2000, 1000, 'd1');
INSERT INTO direct_project_metadata(id, tc_direct_project_id, project_metadata_key_id, metadata_value) VALUES(2, 2000, 1000, 'duplicate 1');