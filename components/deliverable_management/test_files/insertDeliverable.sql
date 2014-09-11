INSERT INTO project(project_id) VALUES(1);
INSERT INTO project(project_id) VALUES(2);
INSERT INTO project(project_id) VALUES(3);

INSERT INTO resource_role_lu(resource_role_id) VALUES(1);
INSERT INTO resource_role_lu(resource_role_id) VALUES(2);
INSERT INTO resource_role_lu(resource_role_id) VALUES(3);

INSERT INTO phase_type_lu(phase_type_id) VALUES(1);
INSERT INTO phase_type_lu(phase_type_id) VALUES(2);
INSERT INTO phase_type_lu(phase_type_id) VALUES(3);

INSERT INTO project_phase(project_phase_id, project_id, phase_type_id) VALUES(1, 1, 1);
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id) VALUES(2, 2, 2);
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id) VALUES(3, 3, 3);

INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id) VALUES(1, 2, 1, 1);
INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id) VALUES(2, 2, 2, 2);
INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id) VALUES(3, 3, 3, 3);

INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(1, 'Submission', 'Submission', 'System', CURRENT, 'System', CURRENT);

INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(2, 'Test Case', 'Test Case', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(3, 'Final Fix', 'Final Fix', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(4, 'Review Document', 'Review Document', 'System', CURRENT, 'System', CURRENT);

INSERT INTO upload_status_lu(upload_status_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload_status_lu(upload_status_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(2, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT);

INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(2, 'Failed Screening', 'Failed Manual Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(3, 'Failed Review', 'Failed Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(4, 'Completed Without Win', 'Completed Without Win', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(5, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT);

INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(1, 'Architecture', 'Architecture', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(2, 'Specification', 'Specification', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(3, 'Design', 'Design', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(4, 'Development', 'Development', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(5, 'TestCase', 'TestCase', 'System', CURRENT, 'System', CURRENT);


INSERT INTO upload(upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date) VALUES(1, 2, 2, 1, 1, 'parameter 1', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload(upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date) VALUES(2, 3, 3, 1, 1, 'parameter 2', 'System', CURRENT, 'System', CURRENT);

INSERT INTO submission(submission_id, submission_status_id, submission_type_id, feedback_thumb, user_rank, mark_for_purchase, create_user, create_date, modify_user, modify_date) VALUES(1, 3, 1, 't', 1, 't', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission(submission_id, submission_status_id, submission_type_id, feedback_thumb, user_rank, mark_for_purchase, create_user, create_date, modify_user, modify_date) VALUES(2, 1, 2, 'f', 1, 'f', 'System', CURRENT, 'System', CURRENT);

INSERT INTO upload_submission(upload_id, submission_id) values (1, 2);
INSERT INTO upload_submission(upload_id, submission_id) values (2, 1);

INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, submission_type_id, required, name, description, create_user, create_date, modify_user, modify_date) VALUES(1, 2, 2, 2, 1, 'deliverable 1', 'per submission deliverable', 'System', CURRENT, 'System', CURRENT);

INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, submission_type_id, required, name, description, create_user, create_date, modify_user, modify_date) VALUES(2, 3, 3, null, 0, 'deliverable 2', 'non per submission deliverable', 'System', CURRENT, 'System', CURRENT);