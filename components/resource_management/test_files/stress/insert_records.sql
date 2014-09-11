INSERT INTO project (project_id) values (1);
INSERT INTO project (project_id) values (2);
INSERT INTO project (project_id) values (3);
INSERT INTO project (project_id) values (4);
INSERT INTO project (project_id) values (5);

INSERT INTO submission (submission_id) values(1);
INSERT INTO submission (submission_id) values(2);
INSERT INTO submission (submission_id) values(3);
INSERT INTO submission (submission_id) values(4);
INSERT INTO submission (submission_id) values(5);

INSERT INTO resource_role_lu(resource_role_id, phase_type_id, name, description, create_user, create_date, modify_user, modify_date) values(1, NULL, 'A', 'A', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource_role_lu(resource_role_id, phase_type_id, name, description, create_user, create_date, modify_user, modify_date) values(2, NULL, 'A', 'A', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource_role_lu(resource_role_id, phase_type_id, name, description, create_user, create_date, modify_user, modify_date) values(3, NULL, 'A', 'A', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource_role_lu(resource_role_id, phase_type_id, name, description, create_user, create_date, modify_user, modify_date) values(4, NULL, 'A', 'A', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource_role_lu(resource_role_id, phase_type_id, name, description, create_user, create_date, modify_user, modify_date) values(5, NULL, 'A', 'A', 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO notification_type_lu (notification_type_id, name, description, create_user, create_date, modify_user, modify_date) values(1, 'A', 'A', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO notification_type_lu (notification_type_id, name, description, create_user, create_date, modify_user, modify_date) values(2, 'A', 'A', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO notification_type_lu (notification_type_id, name, description, create_user, create_date, modify_user, modify_date) values(3, 'A', 'A', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO notification_type_lu (notification_type_id, name, description, create_user, create_date, modify_user, modify_date) values(4, 'A', 'A', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO notification_type_lu (notification_type_id, name, description, create_user, create_date, modify_user, modify_date) values(5, 'A', 'A', 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO notification(project_id, external_ref_id, notification_type_id, create_user, create_date, modify_user, modify_date) values(1, 1, 1, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO notification(project_id, external_ref_id, notification_type_id, create_user, create_date, modify_user, modify_date) values(2, 2, 2, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO notification(project_id, external_ref_id, notification_type_id, create_user, create_date, modify_user, modify_date) values(3, 3, 3, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO notification(project_id, external_ref_id, notification_type_id, create_user, create_date, modify_user, modify_date) values(4, 4, 4, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO notification(project_id, external_ref_id, notification_type_id, create_user, create_date, modify_user, modify_date) values(5, 5, 5, 'admin', CURRENT, 'admin', CURRENT);


INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date) values(1, 1, NULL, NULL, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date) values(2, 2, NULL, NULL, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date) values(3, 3, NULL, NULL, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date) values(4, 4, NULL, NULL, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date) values(5, 5, NULL, NULL, 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO resource_submission(resource_id, submission_id, create_user, create_date, modify_user, modify_date) values(1, 1, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource_submission(resource_id, submission_id, create_user, create_date, modify_user, modify_date) values(2, 2, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource_submission(resource_id, submission_id, create_user, create_date, modify_user, modify_date) values(3, 3, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource_submission(resource_id, submission_id, create_user, create_date, modify_user, modify_date) values(4, 4, 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource_submission(resource_id, submission_id, create_user, create_date, modify_user, modify_date) values(5, 5, 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO resource_info_type_lu (resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date) values(1, 'A', 'A', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource_info_type_lu (resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date) values(2, 'A', 'A', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource_info_type_lu (resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date) values(3, 'A', 'A', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource_info_type_lu (resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date) values(4, 'A', 'A', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource_info_type_lu (resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date) values(5, 'A', 'A', 'admin', CURRENT, 'admin', CURRENT);

INSERT INTO resource_info(resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) values(1, 1, 'NONE', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource_info(resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) values(2, 2, 'NONE', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource_info(resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) values(3, 3, 'NONE', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource_info(resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) values(4, 4, 'NONE', 'admin', CURRENT, 'admin', CURRENT);
INSERT INTO resource_info(resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date) values(5, 5, 'NONE', 'admin', CURRENT, 'admin', CURRENT);
