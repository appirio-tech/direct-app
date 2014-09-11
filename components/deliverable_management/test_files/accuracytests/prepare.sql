INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES(1, 'Submission', 'Submission', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006));
INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES(2, 'Test Case', 'Test Case', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006));
INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES(3, 'Final Fix', 'Final Fix', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006));
INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES(4, 'Review Document', 'Review Document', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006));

INSERT INTO upload_status_lu(upload_status_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES(1, 'Active', 'Active', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006));
INSERT INTO upload_status_lu(upload_status_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES(2, 'Deleted', 'Deleted', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006));

INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES(1, 'Active', 'Active', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006));
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES(2, 'Failed Screening', 'Failed Manual Screening', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006));
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES(3, 'Failed Review', 'Failed Review', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006));
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES(4, 'Completed Without Win', 'Completed Without Win', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006));
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES(5, 'Deleted', 'Deleted', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006)); 

INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES(1, 'Active', 'Active', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006));
INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES(2, 'Failed Screening', 'Failed Manual Screening', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006));
INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES(3, 'Failed Review', 'Failed Review', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006));
INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES(4, 'Completed Without Win', 'Completed Without Win', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006));
INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date)
    VALUES(5, 'Deleted', 'Deleted', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006)); 


INSERT INTO project (project_id)
    VALUES(1);
INSERT INTO project (project_id)
    VALUES(2);
    
INSERT INTO resource_role_lu (resource_role_id)
    VALUES(1);
INSERT INTO resource_role_lu (resource_role_id)
    VALUES(2);    

INSERT INTO phase_type_lu (phase_type_id)
    VALUES(1);
INSERT INTO phase_type_lu (phase_type_id)
    VALUES(2);
INSERT INTO phase_type_lu (phase_type_id)
    VALUES(3);
INSERT INTO phase_type_lu (phase_type_id)
    VALUES(4);

INSERT INTO project_phase (project_phase_id, project_id, phase_type_id) VALUES (1, 1, 1);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id) VALUES (2, 2, 2);
INSERT INTO project_phase (project_phase_id, project_id, phase_type_id) VALUES (3, 2, 3);

INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id)
    VALUES(1, 1, 1, 1);
INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id)
    VALUES(2, 2, 2, 2);

INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
    VALUES(1, 1, 1, 1, 1, 'parameter', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006)); 
INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, create_user, create_date, modify_user, modify_date)
    VALUES(2, 2, 2, 1, 1, 'parameter', 'System', MDY(07,18,2006), 'System', MDY(07,18,2006)); 

INSERT INTO submission (submission_id, upload_id, submission_status_id, submission_type_id, create_user, create_date, modify_user, modify_date)
    VALUES(1, 1, 1, 1, 'System', MDY(07,18,2006), 'System', MDY(07,18,2006)); 
INSERT INTO submission (submission_id, upload_id, submission_status_id, submission_type_id, create_user, create_date, modify_user, modify_date)
    VALUES(2, 1, 1, 1, 'System', MDY(07,18,2006), 'System', MDY(07,18,2006)); 


INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, submission_type_id, required, create_user, create_date, modify_user, modify_date)
    VALUES(1, 1, 1, "deliverable 1", "deliverable desc", 1, 1, 'System', MDY(07,18,2006), 'System', MDY(07,18,2006)); 
INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, submission_type_id, required, create_user, create_date, modify_user, modify_date)
    VALUES(2, 2, 2, "deliverable 2", "deliverable desc", 1, 0, 'System', MDY(07,18,2006), 'System', MDY(07,18,2006)); 
INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, submission_type_id, required, create_user, create_date, modify_user, modify_date)
    VALUES(3, 3, 2, "deliverable 3", "deliverable desc", null, 1, 'System', MDY(07,18,2006), 'System', MDY(07,18,2006)); 
 INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, submission_type_id, required, create_user, create_date, modify_user, modify_date)
    VALUES(4, 4, 1, "deliverable 4", "deliverable desc", 1, 0, 'System', MDY(07,18,2006), 'System', MDY(07,18,2006)); 