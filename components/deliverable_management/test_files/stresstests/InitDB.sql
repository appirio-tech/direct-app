INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('upload_id_seq', 6, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('upload_type_id_seq', 6, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('upload_status_id_seq', 6, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('submission_id_seq', 6, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('submission_status_id_seq', 6, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('submission_type_id_seq', 6, 20, 0);

INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date)  VALUES(1, 'Submission', 'Submission', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date)  VALUES(2, 'Test Case', 'Test Case', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date)  VALUES(3, 'Final Fix', 'Final Fix', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date)  VALUES(4, 'Review Document', 'Review Document', 'System', CURRENT, 'System', CURRENT);

INSERT INTO upload_status_lu(upload_status_id, name, description, create_user, create_date, modify_user, modify_date)  VALUES(1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload_status_lu(upload_status_id, name, description, create_user, create_date, modify_user, modify_date)  VALUES(2, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT);

INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)  VALUES(1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)  VALUES(2, 'Failed Screening', 'Failed Manual Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)  VALUES(3, 'Failed Review', 'Failed Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)  VALUES(4, 'Completed Without Win', 'Completed Without Win', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)  VALUES(5, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT);

INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date)  VALUES(1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date)  VALUES(2, 'Failed Screening', 'Failed Manual Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date)  VALUES(3, 'Failed Review', 'Failed Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date)  VALUES(4, 'Completed Without Win', 'Completed Without Win', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date)  VALUES(5, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project (project_id) VALUES (1);
INSERT INTO project (project_id) VALUES (2);
INSERT INTO project (project_id) VALUES (3);
INSERT INTO project (project_id) VALUES (4);
INSERT INTO project (project_id) VALUES (5);

INSERT INTO resource_role_lu (resource_role_id) VALUES (1);
INSERT INTO resource_role_lu (resource_role_id) VALUES (2);
INSERT INTO resource_role_lu (resource_role_id) VALUES (3);
INSERT INTO resource_role_lu (resource_role_id) VALUES (4);
INSERT INTO resource_role_lu (resource_role_id) VALUES (5);
 
INSERT INTO phase_type_lu (phase_type_id) VALUES (1);
INSERT INTO phase_type_lu (phase_type_id) VALUES (2);
INSERT INTO phase_type_lu (phase_type_id) VALUES (3);
INSERT INTO phase_type_lu (phase_type_id) VALUES (4);

INSERT INTO project_phase (project_phase_id, project_id, phase_type_id) VALUES (1,1, 1);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id) VALUES (2,2, 1);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id) VALUES (3,4, 1);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id) VALUES (4,4, 2);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id) VALUES (5,5, 1);

INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id) VALUES (1, 1, 1, 1);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id) VALUES (2, 1, 2, 2);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id) VALUES (3, 2, 4, 3);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id) VALUES (4, 4, 4, 4);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id) VALUES (5, 5, 5, 5);

INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date) VALUES (1, 1, 1, 1, 1, 'parameter', 'TopCoder', CURRENT, 'TopCoder', CURRENT);
INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date) VALUES (2, 2, 2, 3, 2, 'parameter', 'TopCoder', CURRENT, 'TopCoder', CURRENT);
INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date) VALUES (3, 2, 3, 3, 1, 'parameter', 'TopCoder', CURRENT, 'TopCoder', CURRENT);
INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date) VALUES (4, 1, 3, 4, 1, 'parameter', 'TopCoder', CURRENT, 'TopCoder', CURRENT);
INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date) VALUES (5, 2, 2, 2, 2, 'parameter', 'TopCoder', CURRENT, 'TopCoder', CURRENT);

INSERT INTO submission (submission_id, upload_id, submission_status_id, submission_type_id, create_user, create_date, modify_user, modify_date) VALUES (1, 1, 4, 4, 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission (submission_id, upload_id, submission_status_id, submission_type_id, create_user, create_date, modify_user, modify_date) VALUES (2, 2, 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission (submission_id, upload_id, submission_status_id, submission_type_id, create_user, create_date, modify_user, modify_date) VALUES (3, 5, 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission (submission_id, upload_id, submission_status_id, submission_type_id, create_user, create_date, modify_user, modify_date) VALUES (4, 1, 4, 4, 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission (submission_id, upload_id, submission_status_id, submission_type_id, create_user, create_date, modify_user, modify_date) VALUES (5, 3, 4, 4, 'System', CURRENT, 'System', CURRENT);

INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date) VALUES (1, 1, 1, 'name1', 'name1', 0, 0, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date) VALUES (2, 1, 1, 'name2', 'name2', 1, 0, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date) VALUES (3, 3, 4, 'name3', 'name3', 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date) VALUES (4, 1, 5, 'name4', 'name4', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date) VALUES (5, 4, 2, 'name5', 'name5', 1, 1, 'System', CURRENT, 'System', CURRENT);
